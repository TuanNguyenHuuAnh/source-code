import React, { Component } from 'react';
import Pagination2 from '../History/Paging2';
import { Link, Redirect } from "react-router-dom";
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION, POL_LIST_CLIENT, POL_EXPIRE_DATE, POL_LI_NAME, POL_TOTAL_RECORDS, POL_POLICY_STATUS, POL_CLASS_CD } from '../constants';
import { CPGetPolicyListByCLIID, logoutSession } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import { formatFullName, showMessage, isInteger, setSession, getSession, getDeviceId } from '../util/common';

const PAGE_SIZE = 20;
class PolCompanyList extends Component {
    constructor(props) {
        super(props)
        this.state = {
            jsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    Action: 'PolicyGroup'
                }
            },
            clientProfile: null,
            headers: ['Số hợp đồng', 'Người được bảo hiểm', 'Ngày hiệu lực', 'Tình trạng', 'Số tiền bảo hiểm', 'Phí bảo hiểm', 'Định kỳ đóng phí', 'Ngày đáo hạn'],
            pageOfItemsCom: [],
            searchInput: '',
            clientProfileSearch: [],
            loading: false,
            totalRecords: 0,
            offsetSize: 200,
            offset: 1,
            enable: false
        };
        this.onChangePageCom = this.onChangePageCom.bind(this);
        this.handlerOnChangeSearchInput = this.onChangeSearchInput.bind(this);
        this.handleSearch = this.search.bind(this);
    }

    onChangePageCom(pageOfItemsCom) {
        // update state with new page of items
        this.setState({ pageOfItemsCom: pageOfItemsCom });
    }

    onChangeSearchInput(event) {
        if (event.target.value.length > 0 && !isInteger(event.target.value)) {
            event.target.value = this.state.searchInput;
            return;
        }
        if (event.target.value.length > 6) {
            this.setState({ searchInput: event.target.value, enable: true });
        } else {
            this.setState({ searchInput: event.target.value, enable: false });
        }
    }

    renderTableHeader() {
        return this.state.headers.map((key, index) => {
            return <th className='basic-semibold' key={index}>{key}</th>
        })
    }

    componentDidMount() {
        const apiRequest = Object.assign({}, this.state.jsonInput);
        if (!getSession(POL_LIST_CLIENT)) {
            this.setState({ loading: true });
            CPGetPolicyListByCLIID(apiRequest).then(Res => {
                let Response = Res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                    let jsonState = this.state;
                    jsonState.clientProfile = Response.ClientProfile;
                    jsonState.clientProfileSearch = Response.ClientProfile;
                    jsonState.loading = false;
                    jsonState.totalRecords = Response.Message ? parseInt(Response.Message) : Response.ClientProfile.length;
                    setSession(POL_LIST_CLIENT, JSON.stringify(Response.ClientProfile));
                    setSession(POL_TOTAL_RECORDS, jsonState.totalRecords);
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    this.setState({ loading: false });
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: { authenticated: false, hideMain: false }

                    })

                }
            }).catch(error => {
                this.setState({ loading: false });
                this.props.history.push('/maintainence');
            });
        } else {
            let jsonState = this.state;
            let polList = JSON.parse(getSession(POL_LIST_CLIENT));
            jsonState.clientProfile = polList;
            jsonState.clientProfileSearch = polList;
            jsonState.totalRecords = getSession(POL_TOTAL_RECORDS);
            this.setState(jsonState);
        }

    }

    callbackLoadOffestPolicies = (offset, pageNum) => {
        let apiRequest = Object.assign({}, this.state.jsonInput);
        apiRequest.jsonDataInput.Action = 'PolicyGroup';
        apiRequest.jsonDataInput.SearchPolicy = '';
        apiRequest.jsonDataInput.Offset = offset;
        if (!this.state.clientProfile[(offset - 1) * this.state.offsetSize + 1]) {
            var startIndex = (pageNum - 1) * PAGE_SIZE;
            var endIndex = Math.min(startIndex + PAGE_SIZE - 1, this.state.totalRecords - 1);
            if (!(this.state.clientProfile[(pageNum - 1) * 20])) {
                this.setState({ loading: true });
                CPGetPolicyListByCLIID(apiRequest).then(Res => {
                    let Response = Res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                        let jsonState = this.state;
                        let j = 0;
                        for (let i = (offset - 1) * jsonState.offsetSize; i <= (offset - 1) * jsonState.offsetSize + Response.ClientProfile.length - 1; i++) {
                            jsonState.clientProfile[i] = Response.ClientProfile[j++];
                        }
                        // console.log(jsonState.clientProfile);
                        jsonState.clientProfileSearch = jsonState.clientProfile;
                        jsonState.pageOfItemsCom = jsonState.clientProfile.slice(startIndex, endIndex + 1);
                        jsonState.loading = false;
                        // jsonState.totalRecords = Response.Message? parseInt(Response.Message): 0;
                        setSession(POL_LIST_CLIENT, JSON.stringify(jsonState.clientProfile));
                        this.setState(jsonState);
                    } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                        this.setState({ loading: false });
                        showMessage(EXPIRED_MESSAGE);
                        logoutSession();
                        this.props.history.push({
                            pathname: '/home',
                            state: { authenticated: false, hideMain: false }

                        })

                    }
                }).catch(error => {
                    this.setState({ loading: false });
                    this.props.history.push('/maintainence');
                });
            }

        }

    }

    doSearch() {
        const apiRequest = Object.assign({}, this.state.jsonInput);
        apiRequest.jsonDataInput.Action = 'SearchPolicy';
        apiRequest.jsonDataInput.SearchPolicy = this.state.searchInput;
        this.setState({ loading: true });
        CPGetPolicyListByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                let jsonState = this.state;
                jsonState.clientProfileSearch = Response.ClientProfile;
                jsonState.loading = false;
                this.setState(jsonState);
            } else if (Response.ErrLog === 'EMPTY') {
                let jsonState = this.state;
                jsonState.clientProfileSearch = [];
                jsonState.loading = false;
                this.setState(jsonState);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                this.setState({ loading: false });
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: { authenticated: false, hideMain: false }

                })

            }
        }).catch(error => {
            this.setState({ loading: false });
            this.props.history.push('/maintainence');
        });
    }
    search(event) {
        event.preventDefault();
        if (this.state.loading) return;
        let clientProfile = [];
        if (this.state.searchInput === "") {
            this.setState({ clientProfileSearch: this.state.clientProfile, pageOfItemsCom: this.state.clientProfile });
        } else {
            //Fix lỗi chức năng search danh sách hợp đồng công ty bị impact sau khi phân trang
            clientProfile = this.state.clientProfile.filter(pol => {
                if (pol && pol.PolicyID) {
                    return (pol.PolicyID.indexOf(this.state.searchInput) >= 0);
                }
            });
            if (clientProfile) {
                this.setState({ clientProfileSearch: clientProfile });
            } else {
                this.doSearch();
            }
            
        }
    }

    sortListByStatus(obj) {
        obj.sort((a, b) => {
            if (a.PolicyStatus < b.PolicyStatus) return 1;
            else if (a.PolicyStatus > b.PolicyStatus) return -1;
            else return 0;
        });
        return obj;
    }

    sortListEffectDate(obj) {
        obj.sort((a, b) => {
            if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) < new Date(b.PolIssEffDate)) return 1;
            else if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) > new Date(b.PolIssEffDate)) return -1;
            else return 0;
        });
        return obj;
    }

    render() {
        var logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }
        const cacheCurrentPol = (polExpireDate, policyLIName, policyStatus, PolicyClassCD) => {
            setSession(POL_EXPIRE_DATE, polExpireDate);
            setSession(POL_LI_NAME, policyLIName);
            setSession(POL_POLICY_STATUS, policyStatus);
            setSession(POL_CLASS_CD, PolicyClassCD);
        }
        const clearSearch = () => {
            this.setState({ clientProfileSearch: this.state.clientProfile, pageOfItemsCom: this.state.clientProfile, searchInput: '', enable: false });
        }
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }} />;
        }
        return (
            <main className={logined ? "logined" : "logined nodata"} id="main-id">
                <div className="breadcrums">
                    <div className="breadcrums__item">
                        <p>Thông tin hợp đồng</p>
                        <p className='breadcrums__item_arrow'>></p>
                    </div>
                    <div className="breadcrums__item">
                        <p>Danh sách hợp đồng theo công ty</p>
                        <p className='breadcrums__item_arrow'>></p>
                    </div>

                </div>

                <h4 className='com-title'>Danh sách hợp đồng</h4>

                <form onSubmit={this.handleSearch}>
                    <section className='com-search' style={{ 'padding-right': '8px' }}>
                        <div className="common-searchbar">
                            <div className="search-bar">
                                <div className="input">
                                    <div className="input__content">
                                        <input style={{ fontFamily: 'Inter', fontWeight: '500', fontSize: '16px' }} placeholder="Số hợp đồng" type="search" maxLength="200"
                                            value={this.state.searchInput} onChange={(event) => this.handlerOnChangeSearchInput(event)} />
                                    </div>
                                    <i className="icon" onClick={() => clearSearch()} style={{ cursor: 'pointer' }}><img src="../img/icon/reload-icon.svg" alt="reload" /></i>
                                </div>

                            </div>
                            {this.state.loading || !this.state.enable ? (
                                <button className="searchbtn btn btn-primary disabled com-title" disabled>
                                    Tìm kiếm
                                </button>
                            ) : (
                                <button className="searchbtn btn btn-primary com-title" style={{ 'padding-right': '8px' }}>
                                    Tìm kiếm
                                </button>
                            )}
                        </div>
                    </section>
                </form>
                <div><LoadingIndicator area="policyList-by-cliID" /></div>
                <section className='com-title'>
                    <div>
                        {((!this.state.loading) && (this.state.pageOfItemsCom === null || this.state.pageOfItemsCom.length === 0)) ? (
                            <div className="insurance" style={{ marginTop: '-33px', paddingLeft: '0px', marginLeft: '-6px' }}>
                                <div className="empty">
                                    <div className="icon">
                                        <img src="../../../img/empty-result.svg" alt="no-data" />
                                    </div>
                                    <p style={{ paddingTop: '20px' }}>Chưa tìm thấy kết quả phù hợp.<br /> Vui lòng thử lại từ khoá khác!</p>
                                </div>
                            </div>
                        ) : (

                            <table className='compollist'>
                                <thead>{this.renderTableHeader()}</thead>
                                <tbody>
                                    {
                                        this.state.pageOfItemsCom.map((compol, index) => {
                                            if (compol) {
                                                const { PolicyID, PolicyLIName, PolIssEffDate, PolicyStatus, FaceAmount, PolExpiryDate, PolicyStatusCode, PolMPremAmt, Frequency, PolicyClassCD } = compol //destructuring
                                                return (
                                                    <tr key={PolicyID}>
                                                        <td><Link className='basic-semicom' onClick={() => cacheCurrentPol(PolExpiryDate, PolicyLIName, PolicyStatus, PolicyClassCD)} to={'/compolicyinfo/' + PolicyID.trim()}>{PolicyID}</Link></td>
                                                        <td>{formatFullName(PolicyLIName)}</td>
                                                        <td>{PolIssEffDate}</td>
                                                        {(PolicyStatusCode === '1') || (PolicyStatusCode === '3') ? (
                                                            <td className='status-active basic-semibold' style={{ minWidth: '120px' }}>{PolicyStatus}</td>
                                                        ) : (
                                                            <td className='status-inactive basic-semibold' style={{ minWidth: '120px' }}>{PolicyStatus}</td>
                                                        )}
                                                        <td className='basic-red basic-semibold' style={{ textAlign: 'right' }}>{FaceAmount}</td>
                                                        <td style={{ textAlign: 'right' }}>{PolMPremAmt}</td>
                                                        <td>{Frequency}</td>
                                                        <td>{PolicyStatus !== 'Hết hiệu lực' ? PolExpiryDate : '-'}</td>
                                                    </tr>
                                                )
                                            }

                                        })
                                    }
                                </tbody>
                            </table>
                        )

                        }

                    </div>
                    <div className="paging-container" id="paging-container-com-id">
                        {this.state.clientProfileSearch !== null && (
                            this.state.searchInput !== "" ? (
                                <Pagination2 totals={this.state.clientProfileSearch.length} offsetSize={this.state.offsetSize} items={this.state.clientProfileSearch} onChangePage={this.onChangePageCom} pageSize={PAGE_SIZE} offset={this.state.offset} callbackLoadOffestPolicies={this.callbackLoadOffestPolicies} />
                            ) : (
                                <Pagination2 totals={this.state.totalRecords} offsetSize={this.state.offsetSize} items={this.state.clientProfileSearch} onChangePage={this.onChangePageCom} pageSize={PAGE_SIZE} offset={this.state.offset} callbackLoadOffestPolicies={this.callbackLoadOffestPolicies} />
                            )

                        )}
                    </div>
                </section>
            </main>
        )
    }
}

export default PolCompanyList;