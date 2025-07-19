import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    PageScreen,
    USER_LOGIN
} from '../constants';
import 'antd/dist/antd.min.css';
import {CPGetPolicyListByCLIID, CPSaveLog, logoutSession, Pay_HistoryPaymentDetail} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import Pagination from './Paging';
import {formatFullName, getDeviceId, getSession, showMessage, trackingEvent} from '../util/common';
import {Helmet} from "react-helmet";
import {Redirect} from 'react-router-dom';

class ClaimHistory extends Component {

    constructor(props) {
        super(props);

        this.state = {
            jsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    Company: '',
                    OS: 'Samsung_SM-G973F-Android-7.1.2',
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    PolicyNo: '',
                    Action: 'ClaimHistory',
                    Offset: '1',
                    Filter: 'All'
                }
            },
            ClientProfile: null,
            dtProposal: null,
            polID: '',
            jsonResponse: null,
            HistoryPaymentResponse: null,
            offset: '1',
            filter: 'All',
            isEmpty: true,
            noData: false,
            pageOfItems: [],
            renderMeta: false
        };
        this.onChangePage = this.onChangePage.bind(this);
    }

    onChangePage(pageOfItems) {
        // update state with new page of items
        this.setState({pageOfItems: pageOfItems});
    }


    componentDidMount() {
        const apiRequest = Object.assign({}, this.state.jsonInput);
        CPGetPolicyListByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                const jsonState = this.state;
                jsonState.jsonResponse = Response;
                jsonState.ClientProfile = Response.ClientProfile;
                this.setState(jsonState);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}

                })

            }
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_HISTORY}`);
        trackingEvent(
            "Lịch sử hợp đồng",
            `Web_Open_${PageScreen.CLAIM_HISTORY}`,
            `Web_Open_${PageScreen.CLAIM_HISTORY}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.CLAIM_HISTORY}`);
        trackingEvent(
            "Lịch sử hợp đồng",
            `Web_Close_${PageScreen.CLAIM_HISTORY}`,
            `Web_Close_${PageScreen.CLAIM_HISTORY}`,
        );
    }

    cpSaveLog(functionName) {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN)
            }
        }
        CPSaveLog(masterRequest).then(res => {
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    render() {
        const callHistoryClaimAPI = () => {
            const jsonState = this.state;
            jsonState.jsonInput2.jsonDataInput.PolicyNo = this.state.polID.trim();
            jsonState.jsonInput2.jsonDataInput.Offset = this.state.offset;
            jsonState.jsonInput2.jsonDataInput.Filter = this.state.filter;
            jsonState.dtProposal = null;
            jsonState.pageOfItems = [];
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            Pay_HistoryPaymentDetail(apiRequest).then(Res => {
                let Response1 = Res.Response;
                console.log('Pay_HistoryPaymentDetail', Response1);
                if (Response1.ErrLog === 'successfull') {
                    jsonState.HistoryPaymentResponse = Response1;
                    jsonState.dtProposal = Response1.dtProposal;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}

                    })
                } else {
                    jsonState.noData = true;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const showCardInfo = (polID, index) => {
            const jsonState = this.state;
            jsonState.polID = polID;

            //console.log(polID);
            this.setState(jsonState);
            if (document.getElementById(index).className === "card choosen") {
                document.getElementById(index).className = "card";
                jsonState.polID = '';
                jsonState.isEmpty = true;
                this.setState(jsonState);
            } else {
                jsonState.isEmpty = false;
                this.setState(jsonState);
                this.state.ClientProfile.forEach((polID, i) => {
                    if (i === index) {
                        document.getElementById(i).className = "card choosen";
                    } else {
                        document.getElementById(i).className = "card";
                    }
                });
                callHistoryClaimAPI();
            }
        };
        const showFilter = () => {
            const jsonState = this.state;
            document.getElementById(jsonState.filter).className = "filter-pop-tick ticked";

            if (document.getElementById("filter").className === "specialfilter show") {
                document.getElementById("filter").className = "specialfilter";
            } else {
                document.getElementById("filter").className = "specialfilter show";
            }
        };

        const showTatca = () => {
            document.getElementById("All").className = "filter-pop-tick ticked";
            document.getElementById("Approval").className = "filter-pop-tick";
            document.getElementById("Declined").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.filter = 'All';
            this.setState(jsonState);
            callHistoryClaimAPI();
        };
        const showHoanThanh = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("Approval").className = "filter-pop-tick ticked";
            document.getElementById("Declined").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.filter = 'Approval';
            this.setState(jsonState);
            callHistoryClaimAPI();
        };
        const showTuchoi = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("Approval").className = "filter-pop-tick";
            document.getElementById("Declined").className = "filter-pop-tick ticked";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.filter = 'Declined';
            this.setState(jsonState);
            callHistoryClaimAPI();
        };
        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }}/>;
        }
        return (
            <div>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Giải quyết quyền lợi – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta name="robots" content="noindex, nofollow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/claim-history"}/>
                        <meta property="og:title" content="Giải quyết quyền lợi - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/claim-history"}/>
                    </Helmet>
                }
                <main className="logined" id="main-id">
                    <div className="main-warpper insurancepage basic-mainflex">
                        <section className="sccard-warpper">
                            <h5 className="basic-bold">Vui lòng chọn hợp đồng:</h5>
                            <div className="card-warpper">
                                <LoadingIndicator area="policyList-by-cliID"/>
                                {this.state.ClientProfile !== null && this.state.ClientProfile.map((item, index) => (
                                    <div className="item">
                                        <div className="card" onClick={() => showCardInfo(item.PolicyID, index)}
                                             id={index}>
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                                <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                                                <div className="d-flex" style={{justifyContent: 'space-between'}}>
                                                    {item.PolicyStatus.length < 25 ?
                                                        <p>Hợp đồng: {item.PolicyID}</p> :
                                                        <p className="policy">Hợp đồng: {item.PolicyID}</p>}
                                                    {(item.PolicyStatus === 'Mất hiệu lực' || item.PolicyStatus === 'Hết hiệu lực') ? (
                                                        <div className="dcstatus">
                                                            <p className="inactive">{item.PolicyStatus}</p>
                                                        </div>) : (
                                                        <div className="dcstatus">
                                                            {item.PolicyStatus.length < 25 ?
                                                                <p className="active">{item.PolicyStatus}</p> :
                                                                <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                        </div>
                                                    )}
                                                </div>
                                                <div className="choose">
                                                    <div className="dot"></div>
                                                </div>
                                            </div>
                                            <div className="card__footer">
                                                <div className="card__footer-item">
                                                    <p>Ngày hiệu lực</p>
                                                    <p>{item.PolIssEffDate}</p>
                                                </div>
                                                <div className="card__footer-item">
                                                    <p>Số tiền bảo hiểm</p>
                                                    <p className="basic-red basic-semibold">{item.FaceAmount} VNĐ</p>
                                                </div>
                                                <div className="card__footer-item">
                                                    <p>Đại lý bảo hiểm</p>
                                                    <p>{item.AgentName ? item.AgentName.toUpperCase() : ''}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Tiếp tục</p>
                                <i><img src="img/icon/arrow-left.svg" alt=""/></i>
                            </div>
                        </section>
                        <section className="sccontract-warpper">
                            {this.state.isEmpty || this.state.noData ? (
                                <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                                    <div className="breadcrums__item">
                                        <p>Lịch sử hợp đồng</p>
                                        <span>&gt;</span>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Giải quyết quyền lợi</p>
                                        <span>&gt;</span>
                                    </div>
                                </div>) : (
                                <div className="breadcrums" style={{backgroundColor: '#f1f1f1'}}>
                                    <div className="breadcrums__item">
                                        <p>Lịch sử hợp đồng</p>
                                        <span>&gt;</span>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Giải quyết quyền lợi</p>
                                        <span>&gt;</span>
                                    </div>
                                </div>

                            )}
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="img/icon/return_option.svg" alt=""/></i>
                            </div>
                            {this.state.isEmpty ? (
                                <div className="sccontract-container">
                                    <div className="insurance">
                                        <div className="empty">
                                            <div className="icon">
                                                <img src="img/icon/empty.svg" alt=""/>
                                            </div>
                                            <p style={{paddingTop: '20px'}}>Thông tin chi tiết sẽ hiển thị khi bạn
                                                chọn một hợp đồng ở bên trái.</p>
                                        </div>
                                    </div>
                                </div>
                            ) : (
                                <div
                                    style={this.state.noData ? {backgroundColor: '#ffffff'} : {backgroundColor: '#f1f1f1'}}>
                                    <div className="dpbh-container">

                                        <div className="dpbh-container_header">
                                            <div className="dpbh-container_header-title">

                                            </div>
                                            <div className="specialfilter" id="filter">
                                                <div className="filter_button" onClick={() => showFilter()}>
                                                    <div className="icon-left">
                                                        <img src="img/icon/filter-icon.svg" alt="filter-icon"/>
                                                    </div>
                                                    <div className="text">
                                                        <p>Bộ lọc</p>
                                                    </div>
                                                    <div className="icon-right">
                                                        <img
                                                            src="img/icon/arrow-white-down.svg"
                                                            alt="arrow-down-icon"
                                                        />
                                                    </div>
                                                </div>
                                                <div className="filter_content">
                                                    <div className="filter-pop">

                                                        <div className="filter-pop-tick" onClick={() => showTatca()}
                                                             id="All">
                                                            <p className="content">Tất cả</p>
                                                            <div className="img-wrapper">
                                                                <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                            </div>
                                                        </div>
                                                        <div className="filter-pop-tick" onClick={() => showHoanThanh()}
                                                             id="Approval">
                                                            <p className="content">Chấp nhận chi trả</p>
                                                            <div className="img-wrapper">
                                                                <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                            </div>
                                                        </div>
                                                        <div className="filter-pop-tick" onClick={() => showTuchoi()}
                                                             id="Declined">
                                                            <p className="content">Từ chối chi trả</p>
                                                            <div className="img-wrapper">
                                                                <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="bg"></div>
                                            </div>
                                        </div>


                                        <div className="dpbh-container_body">
                                            <LoadingIndicator area="dtProposal-info"/>
                                            {this.state.pageOfItems !== null && this.state.pageOfItems.map((item, index) => (
                                                <div className="dpbh-container_body-card-container">
                                                    <div className="card-item">
                                                        <div className="card-item-header">
                                                            <div className="card-item-header-left">
                                                                <div className="card-item-header-left-icon">
                                                                    <img src="img/icon/Calendar.svg" alt="calender"/>
                                                                </div>
                                                                <div className="card-item-header-left-text">
                                                                    {item.CreatedDate}
                                                                </div>
                                                            </div>
                                                            {['APPROVAL', 'SUBMITTED', 'APPROVED'].includes(item.Status) ?
                                                                <div
                                                                    className="card-item-header-right">{item.StatusVN}</div> : (
                                                                        ['TEMPSAVED', 'CANCEL'].includes(item.Status)? (
                                                                            <div className="card-item-header-right-disabled">{item.StatusVN}</div>
                                                                        ): (
                                                                            ['WFHOLD', 'DECLINED', 'WAITHC'].includes(item.Status) ?(
                                                                                <div className="card-item-header-right-wfhold">{item.StatusVN}</div>
                                                                            ):(
                                                                                <div className="card-item-header-right-waitwf">{item.StatusVN}</div>
                                                                            )
                                                                        )
                                                                    )
                                                                }
                                                        </div>
                                                        <div className="card-border"></div>
                                                        <div className="card-item-content">
                                                            <div className="card-item-content-left">
                                                                <p>Số Hồ sơ</p>
                                                                <p>Mã yêu cầu</p>
                                                                <p>Người được bảo hiểm</p>
                                                                <p>Sự kiện bảo hiểm</p>
                                                                <p>Số tiền</p>
                                                                {item.Channel === 'mCP' && <div
                                                                    className="flex-center card__footer-claim-details-wrapper"
                                                                    onClick={(e) => {
                                                                        this.props.history.push(`/followup-claim-info/${item.ClaimID}`);
                                                                    }}>
                                                                    <p className="primary-text basic-semibold card__footer-claim-details-label" style={{ marginBottom: 0 }}>Xem chi tiết hồ sơ</p>
                                                                    <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                                                </div>}
                                                            </div>
                                                            <div className="card-item-content-right">
                                                                {(item.HCMSID !== '' && item.HCMSID !== null && item.HCMSID !== undefined) ?
                                                                    <p>{item.HCMSID}</p> : <p>-</p>}
                                                                {(item.ClaimID !== '' && item.ClaimID !== null && item.ClaimID !== undefined) ?
                                                                    <p>{item.ClaimID}</p> : <p>-</p>}
                                                                <p className="basic-text-upper">{item.FullName}</p>
                                                                {item.ClaimTypeVN ?(
                                                                    <p>{item.ClaimTypeVN}</p>
                                                                ): (
                                                                    <p></p>
                                                                )
                                                                }
                                                                <p className="semi-red">{item.ClaimAmt ? item.ClaimAmt : '-'}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {index === this.state.pageOfItems.length - 1 ? (
                                                        <div className="border-nodash-zero"></div>
                                                    ) : (
                                                        <div className="border-nodash"></div>
                                                    )}
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                    <div className="paging-container basic-expand-footer">
                                        {(this.state.ClientProfile !== null && this.state.dtProposal !== null) ? (
                                            <Pagination items={this.state.dtProposal} onChangePage={this.onChangePage}/>
                                        ) : (
                                            <div className="sccontract-container">
                                                <div className="insurance">
                                                    <div className="empty">
                                                        <div className="icon">
                                                            <img src="img/icon/no-content.svg" alt=""/>
                                                        </div>
                                                        <h4 className="basic-semibold"
                                                            style={{fontSize: '1.4rem', fontWeight: '500'}}>Hợp đồng
                                                            chưa có lịch sử giải quyết quyền lợi</h4>
                                                    </div>
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            )}
                        </section>
                    </div>
                </main>

            </div>

        );

    }

}


export default ClaimHistory;