import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FUND_STATE,
    PageScreen,
    POL_LIST_IL_CLIENT,
    SCREENS,
    USER_LOGIN,
    CHANGE_PAY_MODE_SAVE_LOCAL,
    COMPANY_KEY2,
    IS_MOBILE,
    GENDER
} from '../sdkConstant';
// import '../Update/UpdateContactInfo.css';
import {
    CPGetPolicyListByCLIID, CPSaveLog,
    logoutSession,
    enscryptData
} from '../sdkAPI';
import {
    getDeviceId,
    getSession,
    setSession,
    showMessage, trackingEvent, setLocal, getLocal,
    removeLocal,
    getUrlParameterNoDecode,
    getUrlParameter,
    cpSaveLogSDK
} from '../sdkCommon';
import AlertPopupPhone from '../../components/AlertPopupPhone';
import AuthenticationPopup from '../../components/AuthenticationPopup';
import AlertPopupError from '../../components/AlertPopupError';
import {Helmet} from "react-helmet";
import POWarningND13 from "../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ConfirmChangePopup from '../../components/ConfirmChangePopup';
import parse from 'html-react-parser';
import AES256 from 'aes-everywhere';
import ChangeSundryAmountWrapper from './ChangeSundryAmountWrapper';

let fromNative = '';
class ChangeSundryAmount extends Component {
    constructor(props) {
        super(props);

        this.state = {
            enabled: false,
            stepName: FUND_STATE.NONE,
            slectedStepName: FUND_STATE.NONE,
            toggle: false,
            noPhone: false,
            noEmail: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            noValidPolicy: false,
            submitting: false,
            errorMessage: '',
            msgPopup: '',
            popupImgPath: '',
            msg: '',
            imgPath: '',
            polID: '',
            loading: false,
            exId: 0,
            toggleRender: false,
            acceptPolicy: false,
            showThanks: false,
            noTwofa: false,
            trackingId: this.props.trackingId, // ''
            clientListStr: this.props.clientListStr, 
            clientId: this.props.clientId,
            proccessType: 'SundryAmountS',
            appType: this.props.appType,
            deviceId: this.props.deviceId,
            apiToken: this.props.apiToken,
            phone: this.props.phone,
            twoFA: this.props.twoFA,
            clientClass: this.props.clientClass,
            clientName: this.props.clientName,
            email: this.props.email,
            DCID: this.props.DCID
        }
        this.handlerUpdateMainState = this.updateMainState.bind(this);
        this.callBackUpdateND13State  = this.callBackUpdateND13State.bind(this);
        this.getLocalKey = this.getLocalKey.bind(this)
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerGoToStep = this.handlerGoToStep.bind(this);
    }

    handlerGoToStep(state){
        const current = this.state;
        current.updateInfoState = state;
        this.setState(current);
    }

    getLocalKey = (polID) => CHANGE_PAY_MODE_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + polID;

    componentDidMount() {
        fromNative = getUrlParameter("fromApp");
        let AppType = getUrlParameter("AppType");
        if (this.state.appType === "CLOSE") {
            if (!this.state.phone) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!this.state.twoFA || (this.state.twoFA === '0') || this.state.twoFA === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            if (getSession(GENDER) === 'C') {
                //không dành cho khách hàng doanh nghiệp
                this.setState({isCompany: true});
                return;
            }
        } else {
            
            if (!AppType) {
                alert("Invalid Url");
                return;
            }
            this.setState({appType: AppType});
            let ParamData = getUrlParameterNoDecode("Data");

            if (ParamData) {
                if (AppType === "External") {
                    let enscriptRequest = {
                        jsonDataInput: {
                            Action: "DecryptAES",
                            Authentication: AUTHENTICATION,
                            Company: COMPANY_KEY,
                            Data: ParamData,
                            DeviceId: getDeviceId(),
                            Project: "mcp"
                        }
                    }
                    enscryptData(enscriptRequest).then(Res => {
                        let Response = Res.Response;
                        console.log(Res.Response);
                        if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                            console.log(Response.Message);
                            let ParamObject = JSON.parse(Response.Message);
                            if (ParamObject) {
                                let trackingId = ParamObject?.TrackingID;
                                let clientListStr = ParamObject?.ClientList;
                                let clientId = ParamObject?.ClientID;
                                let proccessType = ParamObject?.ProcessType;
                                let deviceId = ParamObject?.DeviceId;
                                let apiToken = ParamObject?.APIToken;
                                let policyNo = ParamObject?.policyNo;
                                let cphone = ParamObject?.phone;
                                let twoFA = ParamObject?.twoFA;
                                if (!cphone) {
                                    //yêu cầu cập nhật số dt
                                    this.setState({noPhone: true});
                                    return;
                                }
                                if (!twoFA || (twoFA === '0') ||twoFA === 'undefined') {
                                    //yêu cầu bật 2fa
                                    this.setState({noTwofa: true});
                                    return;
                                }
                                let clientClass = ParamObject?.clientClass;
                                let clientName = ParamObject?.clientName;
                                let email = ParamObject?.email;
                                let DCID = ParamObject?.DCID;
                                this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, phone: cphone, twoFA: twoFA, clientClass: clientClass, clientName: clientName, email: email, DCID: DCID });
                                        
                            }
                        }
                    }).catch(error => {

                    });

                } else if (AppType === "Internal") {
                    let ParamObject = JSON.parse(AES256.decrypt(ParamData, COMPANY_KEY2));
                    if (ParamObject) {
                        let trackingId = ParamObject?.TrackingID;
                        let clientListStr = ParamObject?.ClientList;
                        let clientId = ParamObject?.ClientID;
                        let proccessType = ParamObject?.ProcessType;
                        let deviceId = ParamObject?.DeviceId;
                        let apiToken = ParamObject?.APIToken;
                        let policyNo = ParamObject?.policyNo;
                        let cphone = ParamObject?.phone;
                        let twoFA = ParamObject?.twoFA;
                        let gender = ParamObject?.Gender;
                        if (!cphone) {
                            //yêu cầu cập nhật số dt
                            this.setState({noPhone: true});
                            return;
                        }
                        if (!twoFA || (twoFA === '0') ||twoFA === 'undefined') {
                            //yêu cầu bật 2fa
                            this.setState({noTwofa: true});
                            return;
                        }
                        if (gender === 'C') {
                            //không dành cho khách hàng doanh nghiệp
                            this.setState({isCompany: true});
                            return;
                        }
                        let clientClass = ParamObject?.clientClass;
                        let clientName = ParamObject?.clientName;
                        let email = ParamObject?.email;
                        let DCID = ParamObject?.DCID;
                        this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, phone: cphone, twoFA: twoFA, clientClass: clientClass, clientName: clientName, email: email, DCID: DCID });
                    }
                }
            }
        }

        this.setState({stepName: FUND_STATE.CHOOSE_POLICY});
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        cpSaveLogSDK(`Web_Open_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`,
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`,
        );
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
        cpSaveLogSDK(`Web_Close_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Close_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`,
            `Web_Close_${PageScreen.POL_TRANS_CHANGE_SUNDRY_AMOUNT}`,
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

    closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeNoEmail();
            this.closeNoPhone();
            this.closeNoVerifyEmail();
            this.closeNoVerifyPhone();
        }
    }

    closeNoPhone = () => {
        this.setState({noPhone: false});
    }

    closeNoEmail = () => {
        this.setState({noEmail: false});
    }

    closeNoVerifyPhone = () => {
        this.setState({noVerifyPhone: false});
    }

    closeNoVerifyEmail = () => {
        this.setState({noVerifyEmail: false});
    }

    closeSubmitIn24 = () => {
        this.setState({submitIn24: false});
    }

    updateMainState(subStateName, editedState) {
        const jsonState = this.state;
        jsonState[subStateName] = editedState;
        this.setState(jsonState);
    }

    updateState(jsonState) {
        jsonState.attachmentState.disabledButton = !(jsonState.attachmentState.attachmentList && (jsonState.attachmentState.attachmentList.length > 0));
        this.setState(jsonState);
        this.handlerUpdateMainState("attachmentState", this.state.attachmentState);
    }

    callBackUpdateND13State(value, callback) {
        let jsonState = this.state;
        jsonState.updateInfoState = value;
        console.log('callBackUpdateND13State', value)
        if(callback)
            callback(jsonState);
        setLocal(this.getLocalKey(this.state.polID), JSON.stringify(jsonState));
        console.log('appType=' + jsonState.appType);
        this.setState(jsonState);
    }

    closePopupSucceeded(event) {
        if ((this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) || (this.closeSucceededButtonRef && this.closeSucceededButtonRef.contains(event.target))) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup";
            document.removeEventListener('mousedown', this.handlerClosePopupSucceeded);

            removeLocal(this.getLocalKey(this.state.polID));
            this.props.closeToHome();
        }
    }

    closePopupSucceededRedirect(event) {
        window.location.href = '/update-policy-info';
        removeLocal(this.getLocalKey(this.state.polID));
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    setCloseSucceededButtonRef(node) {
        this.closeSucceededButtonRef = node;
    }

    render() {
        const closePopupError = () => {
            this.setState({
                noValidPolicy: false,
                totalToFundError: false,
                totalFundCalInTempError: false,
                percentError: false,
                noChangeInvestRate: false,
                noPhone: false,
                isCompany: false
            });
        }

        const closeNoTwofa = () => {
            this.setState({noTwofa: false});
        }


        const closeApiError = () => {
            this.setState({apiError: false});
        }


        let msgPopup = '';
        let popupImgPath = '';

        if (this.state.noPhone) {
            msgPopup = 'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            popupImgPath = FE_BASE_URL + '/img/popup/no-phone.svg';
        }

        return (
            <>
                <Helmet>
                    <title>Điều chỉnh thông tin hợp đồng – Dai-ichi Life Việt Nam</title>
                    <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta name="robots" content="noindex, nofollow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL + "/update-policy-info"}/>
                    <meta property="og:title" content="Điều chỉnh thông tin hợp đồng - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/update-policy-info"}/>
                </Helmet>
                {this.state.appType !== 'CLOSE'?(
                <main className={getSession(IS_MOBILE)?"logined margin-top-nagative-60": "logined"} id="main-id">
                    <div className="main-warpper basic-mainflex">

                        <section className="sccontract-warpper additional-information-sccontract-warpper">
                        {this.state.apiToken && this.state.clientName &&
                            <ChangeSundryAmountWrapper
                                appType={this.state.appType}
                                trackingId={this.state.trackingId}
                                clientListStr={this.state.clientListStr?this.state.clientListStr:getSession(CLIENT_ID)}
                                clientId={this.state.clientId}
                                proccessType={this.state.proccessType}
                                deviceId={this.state.deviceId}
                                apiToken={this.state.apiToken}
                                // policyNo={this.state.polID} 
                                // polID={this.state.polID} 
                                phone={this.state.phone}
                                twoFA={this.state.twoFA}
                                clientClass={this.state.clientClass}
                                clientName={this.state.clientName}
                                email={this.state.email}
                                DCID={this.state.DCID}
                                from={fromNative}
                                handlerUpdateNoValidPolicy={this.props.handlerUpdateNoValidPolicy}
                                handlerResetState={this.props.handlerResetState}
                            />
                        }
                        </section>
                    </div>

                </main>
                ):(
                    this.state.apiToken && this.state.clientName &&
                    <ChangeSundryAmountWrapper
                        appType={this.state.appType}
                        trackingId={this.state.trackingId}
                        clientListStr={this.state.clientListStr??getSession(CLIENT_ID)}
                        clientId={this.state.clientId}
                        proccessType={this.state.proccessType}
                        deviceId={this.state.deviceId}
                        apiToken={this.state.apiToken}
                        // policyNo={this.state.polID} 
                        // polID={this.state.polID} 
                        phone={this.state.phone}
                        twoFA={this.state.twoFA}
                        clientClass={this.state.clientClass}
                        clientName={this.state.clientName}
                        email={this.state.email}
                        DCID={this.state.DCID}
                        handlerUpdateNoValidPolicy={this.props.handlerUpdateNoValidPolicy}
                        handlerResetState={this.props.handlerResetState}
                    />
                        

                )

                }

                {this.state.noPhone &&
                    <AlertPopupPhone closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}
                                     screen={SCREENS.UPDATE_POLICY_INFO}/>
                }

                {this.state.noTwofa &&
                    <AuthenticationPopup closePopup={closeNoTwofa}
                                         msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                                         screen={SCREENS.UPDATE_POLICY_INFO}/>
                }

                {/* {this.state.isCompany &&
                    <AlertPopupError closePopup={closePopupError} msg={'Chức năng chỉ dành cho khách hàng cá nhân. Quý khách vui lòng liên hệ với văn phòng Dai-ichi Life Việt Nam để được hướng dẫn.'} imgPath={FE_BASE_URL + '/img/popup/notvalid.svg'}
                                     screen={SCREENS.UPDATE_POLICY_INFO}/>
                } */}

                {this.state.apiError &&
                    <AlertPopupError closePopup={closeApiError} msg={msgPopup} imgPath={popupImgPath}/>
                }

                {/* Popup succeeded redirect */}
                <div className="popup special envelop-confirm-popup" id="popupSucceededRedirect">
                    <div className="popup__card">
                        <div className="envelop-confirm-card" ref={this.handlerSetWrapperSucceededRef}>
                            <div className="envelopcard">
                                <div className="envelop-content">
                                    <div className="envelop-content__header"
                                        onClick={this.handlerClosePopupSucceededRedirect}
                                    >
                                        <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></i>
                                    </div>
                                    <div className="envelop-content__body">
                                        <div>
                                            <h4 className="popup-claim-submission-h4">Gửi yêu cầu thành
                                                công</h4>
                                            <p>Vui lòng theo dõi tình trạng <br/> hồ sơ tại chức năng <br/> Theo
                                                dõi yêu
                                                cầu.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="envelopcard_bg">
                                <img src="../../img/envelop_nowhite.png" alt=""/>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>

                {/* Popup succeeded */}
                <div className="popup special envelop-confirm-popup" id="popupSucceeded">
                    <div className="popup__card">
                        <div className="envelop-confirm-card" ref={this.handlerSetWrapperSucceededRef}>
                            <div className="envelopcard">
                                <div className="envelop-content">
                                    <div className="envelop-content__header"
                                        ref={this.handlerSetCloseSucceededButtonRef}
                                    >
                                        <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></i>
                                    </div>
                                    <div className="envelop-content__body">
                                        <div>
                                            <h4 className="popup-claim-submission-h4">Gửi yêu cầu thành
                                                công</h4>
                                            <p>Vui lòng theo dõi tình trạng <br/> hồ sơ tại chức năng <br/> Theo
                                                dõi yêu
                                                cầu.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="envelopcard_bg">
                                <img src="../../img/envelop_nowhite.png" alt=""/>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>
            </>


        );

    }

}


export default ChangeSundryAmount;