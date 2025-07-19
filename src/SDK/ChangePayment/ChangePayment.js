import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    IS_MOBILE,
    FE_BASE_URL,
    FUND_STATE,
    PageScreen,
    SCREENS,
    USER_LOGIN,
    CHANGE_PAY_MODE_SAVE_LOCAL,
    COMPANY_KEY2
} from '../../constants';
// import '../Update/UpdateContactInfo.css';
import {
    CPGetPolicyListByCLIID, 
    CPSaveLog,
    logoutSession,
    enscryptData
} from '../../util/APIUtils';
import {
    getDeviceId,
    getSession,
    trackingEvent, setLocal,
    removeLocal,
    getUrlParameterNoDecode,
    getUrlParameter,
    setViewportScale,
    cpSaveLogSDK
} from '../../util/common';
import AlertPopupPhone from '../../components/AlertPopupPhone';
import AuthenticationPopup from '../../components/AuthenticationPopup';
import {Helmet} from "react-helmet";
import AES256 from 'aes-everywhere';
import ChangePaymentWrapper from './ChangePaymentWrapper';
import { setSession } from '../sdkCommon';
import { DCID } from '../sdkConstant';


export const ND_13 = Object.freeze({
    NONE: 0,
    ND13_INFO_CONFIRMATION: 9,
    ND13_INFO_PO_CONTACT_INFO_OVER_18: 10,
    // ND13_INFO_PO_CONTACT_INFO_UNDER_18: 11,
    ND13_INFO_FOLLOW_CONFIRMATION: 12,
})


let fromNative = '';
class ChangePayment extends Component {
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
            // noValidPolicy: false,
            msgPopup: '',
            popupImgPath: '',
            msg: '',
            imgPath: '',
            polID: '',
            loading: false,
            noTwofa: false,
            trackingId: this.props.trackingId, // ''
            clientListStr: this.props.clientListStr, 
            clientId: this.props.clientId,
            proccessType: this.props.proccessType?this.props.proccessType: '',
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
        let proccessType = this.props.proccessType;
        if (fromNative) {
            setViewportScale(1.0);
        }
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
            // this.loadND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId, this.state.clientListStr, this.state.proccessType);
            
        } else {
            
            if (!AppType) {
                alert("Invalid Url");
                return;
            }
            this.setState({appType: AppType});
            // let ParamData = getUrlParameter("Data");
            let ParamData = getUrlParameterNoDecode("Data");

            // ParamData = "OF8YGHE10%2BySX1KwBFarUPukEU7wCUPUho1PlEtym5iWF5xoh4IXOzLPF8XN33aGgX48IfQIdSF9X87PMl0CPNlCi%2FE2M15PJuZ5tuII%2FC3h43qQnVF1QtlnaoVBFQ%2FtLA9u%2BXZI8vdP%2FWW3CEmdprlLRZ16oloPiw7A2PsEJMIG%2FEPWQmkRGQngyZXmEVVfMlWbqL9NeeGbzZcayRqQLEralQz1jsKlsXAz9XbrYYN%2FlyWOR82%2BNi16zrZd6dpmefL9eK%2BlnB%2BXntxLvYl3eH77CEQUIT3QUtNjeXe0ecJiWDqrXYvjjuahEZ7lvx4G0YZLTo1jmHiAB52V%2BjWsrw%3D%3D";
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
                        if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                            let ParamObject = JSON.parse(Response.Message);
                            if (ParamObject) {
                                let trackingId = ParamObject?.TrackingID;
                                let clientListStr = ParamObject?.ClientList;
                                let clientId = ParamObject?.ClientID;
                                proccessType = ParamObject?.ProcessType;
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
                                let dcid = ParamObject?.DCID;
                                setSession(DCID, dcid);
                                this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, phone: cphone, twoFA: twoFA, clientClass: clientClass, clientName: clientName, email: email, DCID: dcid });
                                // this.fetchCPConsentConfirmation(trackingId, clientId, clientListStr, proccessType, apiToken, deviceId);
                                // this.loadND13DataTemp(clientId, trackingId, apiToken, deviceId, clientListStr, proccessType);
                                        
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
                        proccessType = ParamObject?.ProcessType;
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
                        let dcid = ParamObject?.DCID;
                        setSession(DCID, dcid);
                        this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, phone: cphone, twoFA: twoFA, clientClass: clientClass, clientName: clientName, email: email, DCID: dcid });
                        // this.fetchCPConsentConfirmation(trackingId, clientId, clientListStr, proccessType, apiToken, deviceId);
                        // this.loadND13DataTemp(clientId, trackingId, apiToken, deviceId, clientListStr, proccessType);
                    }
                }
            }
        }

        this.setState({stepName: FUND_STATE.CHOOSE_POLICY});
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        // this.cpSaveLog(`${fromNative?fromNative:'Web'}_${proccessType}_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
        cpSaveLogSDK(`${fromNative?fromNative:'Web'}_${proccessType}_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${fromNative?fromNative:'Web'}_${proccessType}_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            `${fromNative?fromNative:'Web'}_${proccessType}_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            fromNative
        );
    }

    // componentDidUpdate() {
    //     // if (this.props.proccessType !== this.state.proccessType) {
    //     //     this.setState({proccessType: this.props.proccessType, stepName: FUND_STATE.NONE});
    //     // }
    // }

    componentWillUnmount() {
        let proccessType = this.props.proccessType?this.props.proccessType:this.state.proccessType;
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
        // this.cpSaveLog(`${fromNative?fromNative:'Web'}_${proccessType}_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
        cpSaveLogSDK(`${fromNative?fromNative:'Web'}_${proccessType}_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${fromNative?fromNative:'Web'}_${proccessType}_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            `${fromNative?fromNative:'Web'}_${proccessType}_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            fromNative
        );
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
        window.location.href = '/payment-contract';
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
                noPhone: false
            });
        }

        const closeNoTwofa = () => {
            this.setState({noTwofa: false});
            // window.location.href = '/payment-contract';
        }

        let msg = '';
        let imgPath = '';
        let msgPopup = '';
        let popupImgPath = '';
        if (!this.state.enabled) {
            msg = 'Vui lòng chọn loại thông tin cần thay đổi ở bên trái.';
            imgPath = FE_BASE_URL + '/img/icon/empty.svg';
        }

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
                    <meta property="og:url" content={FE_BASE_URL + "/payment-contract"}/>
                    <meta property="og:title" content="Điều chỉnh thông tin hợp đồng - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/payment-contract"}/>
                </Helmet>
                {this.state.appType !== 'CLOSE'?(
                <main className={getSession(IS_MOBILE)?"logined padding-top-0 payment-mobile-bg":"logined padding-top-0"} id="main-id">
                    <div className="main-warpper basic-mainflex payment">

                        <section className="sccontract-warpper additional-information-sccontract-warpper" style={{overflowY: 'unset'}}>
                        {this.state.apiToken &&
                            <ChangePaymentWrapper
                                appType={this.state.appType}
                                trackingId={this.state.trackingId}
                                clientListStr={this.state.clientListStr?this.state.clientListStr:getSession(CLIENT_ID)}
                                clientId={this.state.clientId}
                                proccessType={this.props.proccessType?this.props.proccessType: this.state.proccessType}
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
                                // stepName={this.state.stepName}
                                handlerUpdateNoValidPolicy={this.props.handlerUpdateNoValidPolicy}
                                handlerResetState={this.props.handlerResetState}
                            />
                        }
                        </section>
                    </div>

                </main>
                ):(
                    this.state.apiToken &&
                    <ChangePaymentWrapper
                        appType={this.state.appType}
                        trackingId={this.state.trackingId}
                        clientListStr={this.state.clientListStr}
                        clientId={this.state.clientId}
                        proccessType={this.props.proccessType?this.props.proccessType: this.state.proccessType}
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
                        // stepName={this.state.stepName}
                        handlerUpdateNoValidPolicy={this.props.handlerUpdateNoValidPolicy}
                        handlerResetState={this.props.handlerResetState}
                    />
                        

                )

                }

                {this.state.noPhone &&
                    <AlertPopupPhone closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}
                                     screen={SCREENS.PAYMENT_CONTRACT}/>
                }

                {this.state.noTwofa &&
                    <AuthenticationPopup closePopup={()=>closeNoTwofa()}
                                         msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                                         screen={SCREENS.PAYMENT_CONTRACT}/>
                }

            </>


        );

    }

}


export default ChangePayment;