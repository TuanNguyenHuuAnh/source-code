import React from 'react';
import {
    MAGP_FILE_FOLDER_URL,
    ACCESS_TOKEN,
    CLIENT_ID,
    USER_LOGIN,
    CLIENT_PROFILE,
    EXPIRED_MESSAGE,
    POL_LIST_PAYMENT_CLIENT,
    AUTHENTICATION,
    COMPANY_KEY,
    FE_BASE_URL,
    PageScreen,
    NUM_OF_MANUAL_RETRY
} from '../constants';
import { logoutSession, CPGetPolicyInfoByPOLID, CPGetPolicyListByCLIID, CPSaveLog } from '../util/APIUtils';
import PaymentList from './PaymentList';
import PaymentDetail from './PaymentDetail';
import LoadingIndicator from '../common/LoadingIndicator2';
import { responsiveArray } from 'antd/lib/_util/responsiveObserve';
import {showMessage, setSession, getSession, getDeviceId, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';
import ReloadScreen from '../components/ReloadScreen';

class MyPayment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            jsonInput: {
                jsonDataInput: {
                    Action: "PolicyPayment",
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonPolicySearch: {
                jsonDataInput: {
                    Company: '',
                    Project: 'mcp',
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'PolicyPayment',
                    OS: 'Samsung_SM-A125F-Android-11',
                    UserID: getSession(USER_LOGIN),
                    PolID: ''
                }
            },
            jsonPolicySearchResponse: null,
            PolicyClientProfile: null,
            PolicyClientProfileSelected: null,
            PolicyInfo: null,
            UserProfile: JSON.parse(getSession(CLIENT_PROFILE)),
            SearchResult: false,
            loading: false,
            loadingpaymentDetai: false,
            PolIDSelected: "",
            isLogin: getSession(ACCESS_TOKEN) ? 'logined' : '',
            renderMeta: false,
            isNonePaymentList: false,
            isReloadPaymentList: false,
            countRetry: 0,
            isRetrying: false,
        };
    }
    componentDidMount() {
        if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {

        }
        if (this.state.loading === false) {
            this.getPolicyList(false);
        }
        var id = this.props.match.params.id;
        if (id && id !== "") {
            if (!this.state.PolIDSelected || this.state.PolIDSelected === "") {
                var jsonState = this.state;
                jsonState.PolIDSelected = id;
                this.setState(jsonState);
            }
        }
        this.cpSaveLog(`Web_Open_${PageScreen.PAYMENT_PAGE}`);
        trackingEvent(
            "Đóng phí bảo hiểm",
            `Web_Open_${PageScreen.PAYMENT_PAGE}`,
            `Web_Open_${PageScreen.PAYMENT_PAGE}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.PAYMENT_PAGE}`);
        trackingEvent(
            "Đóng phí bảo hiểm",
            `Web_Close_${PageScreen.PAYMENT_PAGE}`,
            `Web_Close_${PageScreen.PAYMENT_PAGE}`,
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
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }
    getPolicyList(retry) {
        if(retry){
            const jsonState = this.state;
            jsonState.countRetry = this.state.countRetry + 1;
            jsonState.isNonePaymentList = false;
            jsonState.isReloadPaymentList = false;
            jsonState.isRetrying = true;
            jsonState.loading = false;
            this.setState(jsonState);
        }

        var CryptoJS = require("crypto-js");
        var data = [{ id: 1, name: 'Anil' }, { id: 2, name: 'Sunil' }]
        var ciphertext = CryptoJS.AES.encrypt(JSON.stringify(data), 'my-secret-key@123').toString();
        var bytes = CryptoJS.AES.decrypt(ciphertext, 'my-secret-key@123');
        var decryptedData = JSON.parse(bytes.toString(CryptoJS.enc.Utf8));

        if (!getSession(POL_LIST_PAYMENT_CLIENT)) {
            const apiRequest = Object.assign({}, this.state.jsonInput);
            CPGetPolicyListByCLIID(apiRequest).then(Res => {
                let Response = Res.Response;
                var jsonState = this.state;
                jsonState.loading = true;
                const TIMEOUT = "TIMEOUT";
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                    jsonState.jsonPolicySearchResponse = Response;
                    jsonState.PolicyClientProfile = Response.ClientProfile;
                    setSession(POL_LIST_PAYMENT_CLIENT, JSON.stringify(Response.ClientProfile));
                    this.setState(jsonState);
                    //Rem to remove select default due to the company data issue
                    if (this.props.match.params.id && this.props.match.params.id !== "") {
                        jsonState.PolIDSelected = this.props.match.params.id;

                    }
                    if (jsonState.PolIDSelected === "") {
                        jsonState.PolIDSelected = Response.ClientProfile[0].PolID;
                        this.callbackPolicySelected(Response.ClientProfile[0].PolicyID);
                    }
                    else if (jsonState.PolIDSelected !== "") {


                        this.callbackPolicySelected(jsonState.PolIDSelected);
                    }

                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: { authenticated: false, hideMain: false }

                    })
                } else if (Response.ErrLog === TIMEOUT){
                    this.setStateTimeoutOrEmpty(Response);
                } else {
                    jsonState.PolicyClientProfile = null;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            }).finally(()=>{
                const jsonState = this.state;
                jsonState.isRetrying = false;
                this.setState(jsonState);
            });;
        } else {
            let jsonState = this.state;
            jsonState.PolicyClientProfile = JSON.parse(getSession(POL_LIST_PAYMENT_CLIENT));
            if (this.props.match.params.id && this.props.match.params.id !== "") {
                jsonState.PolIDSelected = this.props.match.params.id;
            }
            if (jsonState.PolIDSelected === "") {
                jsonState.PolIDSelected = jsonState.PolicyClientProfile[0].PolID;
                this.callbackPolicySelected(jsonState.PolicyClientProfile[0].PolicyID);
            }
            jsonState.loading = true;
            if (jsonState.PolIDSelected !== "") {

                this.callbackPolicySelected(jsonState.PolIDSelected);
            }
            this.setState(jsonState);
        }
    }

    setStateTimeoutOrEmpty(Response){
        const jsonState = this.state;
        jsonState.jsonPolicySearchResponse = Response;
        jsonState.isNonePaymentList = true;
        jsonState.isReloadPaymentList = jsonState.countRetry < NUM_OF_MANUAL_RETRY;
        this.setState(jsonState);
    }


    callAPISearchByPolicyID(PolicyId) {
        var jsonState = this.state;
        jsonState.jsonPolicySearch.jsonDataInput.PolID = PolicyId.trim();
        jsonState.jsonPolicySearch.jsonDataInput.Action = 'PolicyClient';
        jsonState.jsonPolicySearch.jsonDataInput.Company = COMPANY_KEY;
        this.setState(jsonState);

        const apiRequest = Object.assign({}, this.state.jsonPolicySearch);;
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            this.setState(jsonState);
            let Response5 = Res.Response;
            jsonState.jsonPolicySearchResponse = Response5;
            jsonState.SearchResult = true;
            jsonState.loadingpaymentDetai = true;
            if (Response5.ErrLog && Response5.ErrLog === 'SUCCESSFUL') {
                if (Res.Response.PolicyInfo) {
                    jsonState.PolicyInfo = Res.Response.PolicyInfo;
                }
                else {
                    jsonState.PolicyInfo = null;
                }
                if (Res.Response.ClientProfile) {
                    jsonState.PolicyClientProfileSelected = Response5.ClientProfile;
                }
                else {
                    jsonState.PolicyClientProfileSelected = null;
                }
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: { authenticated: false, hideMain: false }

                })

            } else {
                //var msg = `Failed! ${Response5.ErrLog}`;
                jsonState.PolicyInfo = null;
                jsonState.PolicyClientProfileSelected = null;
                this.setState(jsonState);
            }
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }


    callbackPolicySelected = (PolicyId) => {
        this.goDetail();
        if (PolicyId !== "") {
            var jsonState = this.state;
            jsonState.SearchResult = false;
            jsonState.loadingpaymentDetai = true;
            jsonState.loading = true;
            jsonState.PolIDSelected = PolicyId;
            this.setState(jsonState);
            this.callAPISearchByPolicyID(PolicyId);
        }
    }
    goDetail() {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.remove("nodata");
        }
    }


    render() {

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }} />;
        }
        return (
            <div>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Hợp đồng của tôi – Dai-ichi Life Việt Nam</title>
                        <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                        <meta name="robots" content="noindex, nofollow" />
                        <meta property="og:type" content="website" />
                        <meta property="og:url" content={FE_BASE_URL + "/mypayment"} />
                        <meta property="og:title" content="Hợp đồng của tôi - Dai-ichi Life Việt Nam" />
                        <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                        <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
                        <link rel="canonical" href={FE_BASE_URL + "/mypayment"} />
                    </Helmet>
                }
                <main className={this.state.isLogin} id="main-id">
                    <div className="main-warpper basic-mainflex">
                        <section className="sccard-warpper">
                            <h5 className="basic-bold">Chọn hợp đồng để thanh toán:</h5>
                            {this.state.loading === false ? (<LoadingIndicator area="policyList-by-cliID" />)
                                : (<PaymentList PolicyClientProfile={this.state.PolicyClientProfile} parentCallback={this.callbackPolicySelected} PolIDSelected={this.state.PolIDSelected} />)}

                        </section>
                        <section className="sccontract-warpper">
                            {this.state.loadingpaymentDetai === false ? (
                                <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                                    <div className="breadcrums__item">
                                        <p>Đóng phí bảo hiểm</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Hợp đồng của tôi</p>
                                        <span></span>
                                    </div>
                                </div>) : (
                                (this.state.PolicyInfo === null && this.state.PolicyClientProfileSelected === null) ?
                                    <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                                        <div className="breadcrums__item">
                                            <p>Đóng phí bảo hiểm</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                        <div className="breadcrums__item">
                                            <p>Hợp đồng của tôi</p>
                                            <span></span>
                                        </div>
                                    </div> :
                                    <div className="breadcrums" style={{ backgroundColor: '#f1f1f1' }}>
                                        <div className="breadcrums__item">
                                            <p>Đóng phí bảo hiểm</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                        <div className="breadcrums__item">
                                            <p>Hợp đồng của tôi</p>
                                            <span></span>
                                        </div>
                                    </div>

                            )}
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="img/icon/return_option.svg" alt="" /></i>
                            </div>
                            {this.state.loadingpaymentDetai === false? (
                                <ReloadScreen  getPolicyList = {(e) => this.getPolicyList(e)} isNonePaymentList = {this.state.isNonePaymentList}
                                isReloadPaymentList = {this.state.isReloadPaymentList} isRetrying = {this.state.isRetrying} msg = {'Thông tin chi tiết sẽ hiển thị khi bạn chọn một hợp đồng ở bên trái.'} /> 
                            
                            ) : (this.state.PolicyInfo && this.state.PolicyClientProfileSelected && this.state.UserProfile && <PaymentDetail UserProfile={this.state.UserProfile} PolicyInfo={this.state.PolicyInfo}
                                    PolicyClientProfile={this.state.PolicyClientProfileSelected} SearchResult={this.state.SearchResult}
                                />)}
                        </section>
                    </div>
                </main>

            </div>
        )
    }
}


export default MyPayment;