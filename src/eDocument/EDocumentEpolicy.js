import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    API_BASE_URL,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    COMPANY_KEY2,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    PageScreen,
    POL_LIST_EPOLICY,
    POL_LIST_EPOLICY_OFFSET,
    USER_LOGIN,
    WEB_BROWSER_VERSION
} from '../constants';
import {CPGetPolicyListByCLIID, CPSaveLog, getEPolicyPdf, logoutSession} from '../util/APIUtils';
import {formatFullName, getDeviceId, getSession, setSession, showMessage, trackingEvent} from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import AES256 from 'aes-everywhere';
import b64toBlob from 'b64-to-blob';
import {Helmet} from "react-helmet";
import {Redirect} from 'react-router-dom';

const DELTA = 1000;

class EDocumentEpolicy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            clientProfile: getSession(POL_LIST_EPOLICY) ? JSON.parse(getSession(POL_LIST_EPOLICY)) : null,
            ruleProfile: null,
            pdfProfile: null,
            policyID: '',
            isEmpty: true,
            pdfUrl: '',
            DocTypeID: '',
            DocTypeName: '',
            StartPage: '',
            displayPdf: false,
            showRuleDetail: false,
            selectedIndex: 0,
            PDFFile: null,
            renderMeta: false,
            Offset: getSession(POL_LIST_EPOLICY_OFFSET) ? parseInt(getSession(POL_LIST_EPOLICY_OFFSET)) : 1,
            disableMore: false,
            loading: false,
            lastDisplayedIndex: 0, // Chỉ số của bản ghi cuối cùng đã hiển thị
        };
    }

    sortListByStatus = (obj) => {
        obj.sort((a, b) => {
            if (a.PolicyStatus < b.PolicyStatus) return 1; else if (a.PolicyStatus > b.PolicyStatus) return -1; else return 0;
        });
        return obj;
    }

    sortListEffectDate = (obj) => {
        obj.sort((a, b) => {
            if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) < new Date(b.PolIssEffDate)) return 1; else if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) > new Date(b.PolIssEffDate)) return -1; else return 0;
        });
        return obj;
    }
    cpGetEPolicys = (offset) => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'ePolicyList',
                Company: COMPANY_KEY,
                OS: WEB_BROWSER_VERSION,
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                Offset: offset + DELTA
            }
        }
        this.setState({loading: true});
        CPGetPolicyListByCLIID(submitRequest)
            .then(Res => {
                let Response = Res.Response;
                //console.log("CPGetPolicyListByCLIID!!!", Response);
                if (Response.Result === 'true') {
                    if (Response.ClientProfile) {
                        let profile = this.sortListEffectDate(this.sortListByStatus(Response.ClientProfile));
                        let list = null;
                        if (this.state.clientProfile) {
                            list = this.state.clientProfile.concat(profile);
                        } else {
                            list = profile;
                        }
                        setSession(POL_LIST_EPOLICY, JSON.stringify(list));
                        if (Response.ClientProfile?.length < 200) {
                            this.setState({disableMore: true});
                        }
                        this.setState(prevState => ({
                            clientProfile: list,
                            Offset: offset,
                            loading: false,
                        }));
                    } else {
                        this.setState({disableMore: true, Offset: offset, loading: false});
                    }
                    setSession(POL_LIST_EPOLICY_OFFSET, offset);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home', state: {authenticated: false, hideMain: false}
                    })
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });
    }

    componentDidMount() {
        if (getSession(POL_LIST_EPOLICY)) {
            this.setState({
                clientProfile: JSON.parse(getSession(POL_LIST_EPOLICY)),
                Offset: (getSession(POL_LIST_EPOLICY_OFFSET) ? parseInt(getSession(POL_LIST_EPOLICY_OFFSET)) : 1)
            });
        } else {
            this.cpGetEPolicys(1);
        }
        this.cpSaveLog(`Web_Open_${PageScreen.E_POLICY_POL_LIST}`);
        trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.E_POLICY_POL_LIST}`, `Web_Open_${PageScreen.E_POLICY_POL_LIST}`,);
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.E_POLICY_POL_LIST}`);
        trackingEvent("Thư viện tài liệu", `Web_Close_${PageScreen.E_POLICY_POL_LIST}`, `Web_Close_${PageScreen.E_POLICY_POL_LIST}`,);
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

    handleLoadMore = () => {
        // Kiểm tra xem đã hiển thị hết tất cả 200 bản ghi chưa
        if (this.state.lastDisplayedIndex === this.state.clientProfile?.length) {
            // Nếu đã hiển thị hết, gọi hàm cpGetEPolicys để lấy tiếp dữ liệu
            this.cpGetEPolicys(this.state.Offet + 1);
        } else {
            // Nếu chưa hiển thị hết, tăng giá trị của lastDisplayedIndex lên 200
            const { lastDisplayedIndex } = this.state;
            this.setState({ lastDisplayedIndex: lastDisplayedIndex + 200 }, () => {
                // Sau khi tăng giá trị của lastDisplayedIndex, kiểm tra xem đã hiển thị hết tất cả 200 bản ghi chưa
                if (this.state.lastDisplayedIndex === this.state.clientProfile?.length) {
                    // Nếu đã hiển thị hết, gọi hàm cpGetEPolicys để lấy tiếp dữ liệu
                    this.cpGetEPolicys(this.state.Offet + 1);
                }
            });
        }
    };

    render() {
        const {clientProfile, lastDisplayedIndex} = this.state;

        const displayedData = clientProfile ? clientProfile.slice(0, lastDisplayedIndex + 200) : [];

        let logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }

        const getEpolicyPdfHeader = (policyID) => {
            const submitRequest = {
                jsonDataInput: {
                    Offset: '1',
                    Action: 'GetPDFIndex',
                    Project: 'mcp',
                    APIToken: getSession(ACCESS_TOKEN),
                    ClientID: getSession(CLIENT_ID),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    PolicyNo: policyID,
                    DeviceToken: 'Web',
                    UserLogin: getSession(USER_LOGIN),
                }
            }
            getEPolicyPdf(submitRequest).then(Res => {
                //console.log(JSON.stringify(Res));
                let Response = Res.Response;

                if (Response.Result === 'true' && Response.ClientProfile) {
                    this.setState({
                        policyID: policyID, isEmpty: false, pdfProfile: Response.ClientProfile
                    });
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home', state: {authenticated: false, hideMain: false}

                    })

                } else {
                    this.setState({
                        policyID: policyID, isEmpty: true, pdfProfile: null
                    });
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        }

        const cpGetEPolicyRules = (polID) => {
            const submitRequest = {
                jsonDataInput: {
                    Action: 'GetPDFAppendix',
                    Company: COMPANY_KEY,
                    PolicyNo: polID,
                    OS: WEB_BROWSER_VERSION,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                }
            }
            getEPolicyPdf(submitRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ClientProfile) {
                    this.setState({ruleProfile: Response.ClientProfile});
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home', state: {authenticated: false, hideMain: false}

                    })

                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        }

        const showCardInfo = (polID, index) => {
            polID = polID.trim();
            const jsonState = this.state;
            jsonState.policyID = polID;

            this.setState(jsonState);
            if (document.getElementById(index) && document.getElementById(index).className === "card choosen") {
                document.getElementById(index).className = "card";
                jsonState.policyID = '';
                jsonState.isEmpty = true;
                this.setState(jsonState);
            } else {
                jsonState.isEmpty = false;
                this.state.clientProfile.forEach((pol, i) => {
                    if (document.getElementById(i)) {
                        if (i === index) {
                            document.getElementById(i).className = "card choosen";
                        } else {
                            document.getElementById(i).className = "card";
                        }
                    } 

                });
                // this.setState(jsonState);
                getEpolicyPdfHeader(polID);
                cpGetEPolicyRules(polID);


            }
        }
        const goToIndex = (URL, DocTypeID, DocTypeName, StartPage) => {
            this.setState({
                displayPdf: true, pdfUrl: URL, DocTypeID: DocTypeID, DocTypeName: DocTypeName, StartPage: StartPage
            });
            let urlArr = URL.split("\\");
            let filePath = '';
            for (let i = 0; i < urlArr.length; i++) {
                filePath = filePath + '|' + urlArr[i];
            }
            filePath = AES256.encrypt(filePath, COMPANY_KEY2);
            filePath = filePath.replaceAll('/', '|');
            let path = API_BASE_URL + '/v1/edocument/' + filePath + '#page=' + StartPage;
            const newWindow = window.open(path);
            newWindow.document.title = "Thư viện tài liệu";
        }

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

        const downloaEpolicyPdf = (URL) => {
            let urlArr = URL.split("\\");
            let filePath = '';
            for (let i = 0; i < urlArr.length; i++) {
                filePath = filePath + '|' + urlArr[i];
            }
            filePath = AES256.encrypt(filePath, COMPANY_KEY2);
            filePath = filePath.replaceAll('/', '|');
            let path = API_BASE_URL + '/v1/edocument/' + filePath;
            fetch(path).then(response => {
                response.blob().then(blob => {
                    let url = window.URL.createObjectURL(blob);
                    let a = document.createElement('a');
                    a.href = url;
                    a.download = 'Hợp đồng bảo hiểm nhân thọ.pdf';
                    a.click();
                });
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        }

        const getPDFDetail = (url) => {
            const submitRequest = {
                jsonDataInput: {
                    OS: WEB_BROWSER_VERSION,
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'GetPDFFile',
                    FileName: url,
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                }
            }
            getEPolicyPdf(submitRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                    viewRulePdf(Response.Message);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else {
                }
            }).catch(error => {
                //this.props.history.push('/maintainence');
            });
        }

        const openNewTab = (response) => {
            const contentType = 'application/pdf';
            const sliceSize = 512;
            const byteCharacters = atob(response);
            const byteArrays = [];
            for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                const slice = byteCharacters.slice(offset, offset + sliceSize);
                const byteNumbers = new Array(slice.length);
                for (let i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }
                const byteArray = new Uint8Array(byteNumbers);
                byteArrays.push(byteArray);
            }

            let blob = new Blob(byteArrays, {type: contentType});
            blob = b64toBlob(response, contentType);
            const blobUrl = URL.createObjectURL(blob);

            window.open(blobUrl);
        }

        const viewRulePdf = (PDFFile) => {
            openNewTab(PDFFile);
        }

        const showRuleDetail = (index) => {
            this.setState({showRuleDetail: true, selectedIndex: index});
        }
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }}/>;
        }
        return (<>
            {this.state.renderMeta && <Helmet>
                <title>Bộ hợp đồng – Dai-ichi Life Việt Nam</title>
                <meta name="description"
                      content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                <meta name="robots" content="noindex, nofollow"/>
                <meta property="og:type" content="website"/>
                <meta property="og:url" content={FE_BASE_URL + "/epolicy"}/>
                <meta property="og:title" content="Bộ hợp đồng - Dai-ichi Life Việt Nam"/>
                <meta property="og:description"
                      content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                <meta property="og:image"
                      content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                <link rel="canonical" href={FE_BASE_URL + "/epolicy"}/>
            </Helmet>}
            <main className={logined ? "logined" : "logined nodata"} id="main-id">

                <div className="main-warpper insurancepage basic-mainflex">
                    <section className="sccard-warpper">
                        <h5 className="basic-bold">Vui lòng chọn hợp đồng:</h5>
                        <div className="card-warpper">
                            <LoadingIndicator area="policyList-by-cliID"/>
                            {displayedData && displayedData.map((item, index) => {
                                return (<div className="item" key={"item" + index}>
                                        <div className="card" onClick={() => showCardInfo(item.PolicyID, index)}
                                             id={index}>
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                                <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                                                {item.PolicyStatus.length < 25 ? <p>Hợp đồng: {item.PolicyID}</p> :
                                                    <p className="policy">Hợp đồng: {item.PolicyID}</p>}
                                                {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                    <div className="dcstatus">
                                                        <p className="inactive">{item.PolicyStatus}</p>
                                                    </div>) : (<div className="dcstatus">
                                                    {item.PolicyStatus.length < 25 ?
                                                        <p className="active">{item.PolicyStatus}</p> :
                                                        <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                </div>)}
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
                                                    <p className="basic-red basic-bold">{item.FaceAmount} VNĐ</p>
                                                </div>
                                                <div className="card__footer-item">
                                                    <p>Đại lý bảo hiểm</p>
                                                    <p>{item.AgentName ? item.AgentName.toUpperCase() : ''}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })}
                            {displayedData && displayedData.length > 0 &&
                                <LoadingIndicator area="policyList-by-cliID"/>}
                            {displayedData && displayedData.length > 0 && <div className="bottom-btn">
                                <button className={`btn btn-primary ${this.state.loading || this.state.disableMore? 'disabled' : ''}`}
                                        disabled={this.state.loading || this.state.disableMore} onClick={this.handleLoadMore}>
                                    Xem thêm
                                </button>
                            </div>}
                            <p style={{ textAlign: 'center', marginTop: 16 }}>{`Tổng số hợp đồng: ${displayedData.length}`}</p>
                        </div>
                        <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                            <p>Tiếp tục</p>
                            <i><img src="img/icon/arrow-left.svg" alt=""/></i>
                        </div>
                    </section>
                    <section className={this.state.isEmpty ? "sccontract-warpper no-data" : "sccontract-warpper"}>
                        {this.state.isEmpty ? (<div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                            <div className="breadcrums__item">
                                <p>Thư viện tài liệu</p>
                                <span>&gt;</span>
                            </div>
                            <div className="breadcrums__item">
                                <p>Bộ hợp đồng</p>
                                <span>&gt;</span>
                            </div>
                        </div>) : (<div className="breadcrums" style={{backgroundColor: '#f1f1f1'}}>
                                <div className="breadcrums__item">
                                    <p>Thư viện tài liệu</p>
                                    <span>&gt;</span>
                                </div>
                                {this.state.showRuleDetail ? (
                                    <div className="breadcrums__item" style={{cursor: 'pointer'}}
                                         onClick={() => this.setState({showRuleDetail: false})}>
                                        <p>Bộ hợp đồng</p>
                                        <span>&gt;</span>
                                    </div>) : (<div className="breadcrums__item">
                                    <p>Bộ hợp đồng</p>
                                    <span>&gt;</span>
                                </div>)}

                                {this.state.showRuleDetail && <div className="breadcrums__item">
                                    <p>Phụ lục sửa đổi, bổ sung Quy tắc và điều khoản</p>
                                    <span>&gt;</span>
                                </div>}
                            </div>
                        )}
                        <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                            <p>Chọn thông tin</p>
                            <i><img src="img/icon/return_option.svg" alt=""/></i>
                        </div>
                        <LoadingIndicator area="epolicy-pdf"/>
                        {this.state.isEmpty ? (<div className="insurance">
                            <div className="empty">
                                <div className="icon">
                                    <img src="img/no-data(6).svg" alt="no-data"/>
                                </div>
                                <p style={{paddingTop: '20px'}}>Thông tin chi tiết sẽ hiển thị khi bạn
                                    chọn một hợp đồng ở bên trái.</p>
                            </div>
                        </div>) : (

                            !this.state.showRuleDetail ? (<div className="an-hung-page">
                                <div className="bo-hop-dong-container">
                                    {this.state.pdfProfile && this.state.pdfProfile[0] && this.state.pdfProfile[0].lstPDFIndex.map((item, index) => {
                                        return (<>
                                            {item.DocTypeID === 'Insight' ? (
                                                <div className="bo-hop-dong-container-body"
                                                     style={{cursor: 'pointer'}}
                                                     onClick={() => goToIndex(item.URL, item.DocTypeID, item.DocTypeName, item.StartPage)}>
                                                    <div
                                                        className="bo-hop-dong-container-body__content-title">
                                                        {item.DocTypeName}
                                                    </div>
                                                    <div
                                                        className="bo-hop-dong-container-body__content-arrow">
                                                        <img src="img/icon/arrow-left-grey.svg"
                                                             alt="arrow-left"
                                                             style={{maxWidth: '14px'}}/>
                                                    </div>
                                                </div>) : (<div className="bo-hop-dong-container-body"
                                                                style={{cursor: 'pointer'}}
                                                                onClick={() => goToIndex(this.state.pdfProfile[0].URL, item.DocTypeID, item.DocTypeName, item.StartPage)}>
                                                <div
                                                    className="bo-hop-dong-container-body__content-title">
                                                    {item.DocTypeName}
                                                </div>
                                                <div
                                                    className="bo-hop-dong-container-body__content-arrow">
                                                    <img src="img/icon/arrow-left-grey.svg"
                                                         alt="arrow-left"
                                                         style={{maxWidth: '14px'}}/>
                                                </div>
                                            </div>)}

                                            <div className="border"></div>
                                        </>)
                                    })}
                                    {this.state.ruleProfile && this.state.ruleProfile.map((item, index) => {
                                        return (<>

                                            <div className="bo-hop-dong-container-body"
                                                 style={{cursor: 'pointer'}}
                                                 onClick={() => showRuleDetail(index)}>
                                                <div
                                                    className="bo-hop-dong-container-body__content-rule-title">
                                                    {item.Title}
                                                </div>
                                                <div
                                                    className="bo-hop-dong-container-body__content-arrow">
                                                    <img src="img/icon/arrow-left-brown.svg"
                                                         alt="arrow-left" style={{
                                                        width: '14px', height: '14px', maxWidth: '14px'
                                                    }}/>
                                                </div>
                                            </div>
                                            <div className="border"></div>
                                        </>)
                                    })}

                                </div>
                                <div className="bottom-btn"
                                     onClick={() => downloaEpolicyPdf(this.state.pdfProfile[0].URL)}>
                                    <button className="btn btn-primary">Tải về</button>
                                </div>
                            </div>) : (<div className="an-hung-page">
                                <div className="bo-hop-dong-container">
                                    {this.state.ruleProfile && this.state.ruleProfile[this.state.selectedIndex] && this.state.ruleProfile[this.state.selectedIndex].lstPDFIndex.map((item, index) => {
                                        return (<>

                                            <div className="bo-hop-dong-container-body"
                                                 style={{cursor: 'pointer'}}
                                                 onClick={() => getPDFDetail(item.URL)}>
                                                <div
                                                    className="bo-hop-dong-container-body__content-title">
                                                    {item.Product}
                                                </div>
                                                <div
                                                    className="bo-hop-dong-container-body__content-arrow">
                                                    <img src="img/icon/arrow-left-grey.svg"
                                                         alt="arrow-left"/>
                                                </div>
                                            </div>
                                            <div className="border"></div>
                                        </>)
                                    })}
                                </div>
                            </div>)
                        )}
                    </section>
                </div>
            </main>
        </>)
    }
}

export default EDocumentEpolicy;
