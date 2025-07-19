import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    COMPANY_KEY,
    DCID,
    EMAIL,
    FE_BASE_URL,
    FULL_NAME,
    FUND_STATE,
    OS,
    OTP_EXPIRED,
    OTP_INCORRECT,
    PageScreen,
    SCREENS,
    TWOFA,
    USER_LOGIN,
    ConsentStatus,
    UPDATE_POLICY_INFO_SAVE_LOCAL,
    COMPANY_KEY2, 
    CLIENT_CLASS,
    ND_13,
    SUBMISSION_TYPE_MAPPING,
    WEB_BROWSER_VERSION
} from '../constants';
import {Redirect, withRouter} from 'react-router-dom';
import './UpdateContactInfo.css';
import {
    CPSaveLog,
    genOTP,
    onlineRequestSubmit,
    CPConsentConfirmation
} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import {
    getDeviceId,
    getSession,
    trackingEvent, setLocal, getLocal,
    removeLocal,
} from '../util/common';
import AlertPopup from '../components/AlertPopup';
import AlertPopupPhone from '../components/AlertPopupPhone';
import AlertPopupOriginal from '../components/AlertPopupOriginal';
import AuthenticationPopup from '../components/AuthenticationPopup';
import AlertPopupError from '../components/AlertPopupError';
import {Helmet} from "react-helmet";
import {isEmpty} from "lodash";
import POWarningND13 from "../SDK/ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import AES256 from 'aes-everywhere';
import ConfirmPopup from '../components/ConfirmPopup';
import ChangePayment from '../SDK/ChangePayment/ChangePayment';


const ND13_CONFIRMING_STATUS = {
    NONE: '',
    NEED: '1',
    NO_NEED: '0'
}

export const INITIAL_STATE_UPDATE_POLICY_INFO = () => {
    return {
            updateLoan: false,
            updateSurrender: false,
            updateMaturity: false,
            updatePremiumRefund: false,
            updatePartialWithdrawal: false,
            updateCoupon: false,
            updateDividend: false,
            enabled: false,
            stepName: FUND_STATE.CATEGORY_INFO,
            slectedStepName: FUND_STATE.NONE,
            toggle: false,
            noPhone: false,
            noEmail: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            noValidPolicy: false,
            showOtp: false,
            submitting: false,
            isPolicyLapse: false,
            minutes: 0,
            seconds: 0,
            OTP: '',
            errorMessage: '',
            msgPopup: '',
            popupImgPath: '',
            msg: '',
            imgPath: '',
            loading: false,
            exId: 0,
            toggleRender: false,
            acceptPolicy: false,
            noTwofa: false,
            apiError: false,
            otp: '',
            isReloadPaymentList: false,
            countRetry: 0,
            isRetrying: false,
            timeOut: true,
            proccessType: ''
        }
    };

class PaymentContract extends Component {
    constructor(props) {
        super(props);

        this.state = INITIAL_STATE_UPDATE_POLICY_INFO();
        this.handlerResetState = this.resetState.bind(this);
        this.handlerUpdateNoValidPolicy = this.updateNoValidPolicy.bind(this);
    }

    handlerGoToStep(state){
        const current = this.state;
        current.updateInfoState = state;
        this.setState(current);
    }

    logStateChange(key){
        let obj = {};
        for(let i = 0; i < key.length; i++){
            const k = key[i];
            obj[k] = this.state[k];
        }

        console.log(obj);
    }

    isStateChange(prev, key){
        for(let i = 0; i < key.length; i++){
            const k = key[i];
            if(prev[k]!== this.state[k]) return true;
        }
        return false;
    }
    

    getLocalKey = (polID) => UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + polID;

    componentDidMount() {
        // if (this.props.match.params.info) {
        //     let infoArr = this.props.match.params.info.split('-');
        //     if (infoArr && infoArr.length === 3 ) {
        //         let proccessType = infoArr[0];
        //         let userLogin = infoArr[1];
        //         let trackingId = infoArr[2];
        //         if (proccessType && userLogin && trackingId) {
        //             this.loadND13Data(userLogin, trackingId, getSession(ACCESS_TOKEN), getDeviceId(), proccessType);
        //         }
                
        //     }
        // } 
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
        );
    }

    componentDidUpdate (prevProps) {

        // if (this.props.match.params.info && (this.props.match.params.info !== prevProps.match.params.info)) {
        //     let infoArr = this.props.match.params.info.split('-');
        //     if (infoArr && infoArr.length === 3 ) {
        //         let proccessType = infoArr[0];
        //         let userLogin = infoArr[1];
        //         let trackingId = infoArr[2];
        //         if (proccessType && userLogin && trackingId) {
        //             this.loadND13Data(userLogin, trackingId, getSession(ACCESS_TOKEN), getDeviceId(), proccessType);
        //         }
                
        //     }
        // }
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
        this.cpSaveLog(`Web_Close_${PageScreen.POL_TRANS_REINST}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            `Web_Close_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
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

    loadND13Data = (userLogin, dKey, apiToken, deviceId, proccessType) => {
        let request = {
            jsonDataInput: {
                Action: "GetND13Data",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                DKey: dKey,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: userLogin
            }
        };
        console.log('UpdatePolicyInfo loadND13DataTemp request=', request);
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    console.log("UpdatePolicyInfo getND13Data success!=" , Response.Message);
                    
                    if (!isEmpty(Response.Message)) {
                        let jsonState = JSON.parse(Response.Message);
                        if (jsonState && jsonState.clientListStr) {
                            const jsonRequest = {
                                jsonDataInput: {
                                    Company: COMPANY_KEY,
                                    Action: "CheckRequestSubmit",
                                    APIToken: apiToken,
                                    Authentication: AUTHENTICATION,
                                    DeviceId: deviceId,
                                    OS: "Web",
                                    Project: "mcp",
                                    ClientID: userLogin,
                                    UserLogin: userLogin,
                                    RequestTypeID: proccessType,
                                    PolicyNo: jsonState.policyNo,
                                    FromSystem: "DCW",
                                }
                            };
                
                            onlineRequestSubmit(jsonRequest).then(Res => {
                                let Response = Res.Response;
                                if (Response.Result === 'true' && Response.ErrLog === "DRAFT") {
                                    if (Response.Message === dKey) {
                                        const keyLocal = this.getLocalKey(jsonState.policyNo);
                                        getLocal(keyLocal).then(res => {
                                            if (res && res.v) {
                                                const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
                                                if (dataResponse?.trackingId && dataResponse?.trackingId === dKey) {
                                                    this.setState({trackingId: dKey, appType: 'CLOSE', clientListStr: jsonState.clientListStr, proccessType: proccessType, stepName: FUND_STATE.SDK})
                                                } else {
                                                    //Liên kết với thông báo này không còn hiệu lực
                                                    this.setState({notiLinkExpired: true});
                                                }
                                            } else {
                                                //Liên kết với thông báo này không còn hiệu lực
                                                this.setState({notiLinkExpired: true});
                                            }
                                        })
                                    } else {
                                        //Liên kết với thông báo này không còn hiệu lực
                                        this.setState({notiLinkExpired: true});
                                    }
                                } else {
                                    //Liên kết với thông báo này không còn hiệu lực
                                    this.setState({notiLinkExpired: true});
                                }
                            }).catch(error => {
                                document.getElementById("popup-exception").className = "popup special point-error-popup show";
                            });
                            
                        }
                    } else {
                        //Liên kết với thông báo này không còn hiệu lực
                        this.setState({notiLinkExpired: true});
                    }
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            })
            .catch(error => {
                console.log(error);
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            });
    }

    closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeNoEmail();
            this.closeNoPhone();
            this.closeNoVerifyEmail();
            this.closeNoVerifyPhone();
            this.closeSubmitIn24();
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

    generateSessionString(CLIENT_ID, polID)  {
        if (this.checkPOAndLIEquality(CLIENT_ID, polID)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${polID}`;
        }
    }

    checkPOAndLIEquality(PO, LI) {
        const clientIDLI = LI;
        const poIDPO = PO;
        return clientIDLI === poIDPO;
    }


    generateConsentResults(data) {
        const result = {};
        data.forEach((item, index) => {
            const role = item.Role;
            let key;
            if (role === 'PO') {
                key = 'ConsentResultPO';
            } else {
                // key = `ConsentResultLI_${index + 1}`;
                key = 'ConsentResultLI';
            }
            if (item.ConsentRuleID === 'ND13') {
                result[key] = item.ConsentResult;
            }
            
        });
        return result;
    }

    haveLIStillNotAgree(data) {
        let result = false;
        console.log('haveLIStillNotAgree=', data);
        data.forEach((item, index) => {
            const role = item.Role;
            if ((role === 'LI') && (item.ConsentRuleID === 'ND13') && (item.ConsentResult !== ConsentStatus.AGREED)) {
                console.log('haveLIStillNotAgree true');
                result = true;
                return result;
            }
            
        });
        console.log('haveLIStillNotAgree =', result);
        return result;
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

    callBackUpdateND13ClientProfile(data){
        //TODO: do nothing
    }

    isOlderThan18(dob) {
        const birthDate = new Date(dob);
        const currentDate = new Date();

        // Calculate the age
        let age = currentDate.getFullYear() - birthDate.getFullYear();
        const monthDiff = currentDate.getMonth() - birthDate.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < birthDate.getDate())) {
            age--;
        }
        // Check if the person is older than 18
        return age >= 18;
    }

    fetchCPConsentConfirmation(TrackingID, clientIds) {
        console.log('fetchCPConsentConfirmation', TrackingID)
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                // ClientList: clientIds ?? this.generateSessionString(getSession(CLIENT_ID), this.state.polID),
                ClientList: clientIds ?? getSession(CLIENT_ID),
                ProcessType: this.state.proccessType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ?? this.state.trackingId,
            }
        };
        console.log('fetchCPConsentConfirmation request', request)
        const clientListStr = clientIds;

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    // const { DOB } = this.state.clientProfile;
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;

                    const trackingID = TrackingID ?? this.state.trackingId;
                    if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                        const updateInfoState = ND_13.ND13_INFO_CONFIRMATION;
                        this.callBackUpdateND13State(updateInfoState, state => {
                            state.updateInfoState = updateInfoState;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.reInstamentVerify = false;
                            state.stepName = updateInfoState;
                            state.trackingId = trackingID;
                            state.clientListStr = clientListStr;

                            state.appType = 'CLOSE';
                            // state.proccessType = 'RIN';
                            state.stepName = FUND_STATE.SDK
                        });
                        console.log('xx');
                        this.callBackUpdateND13ClientProfile(clientProfile);
                    } else if (this.haveLIStillNotAgree(clientProfile)) {
                        // const isOver18 = this.isOlderThan18(DOB);
                        const updateInfoState = ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18;

                        this.callBackUpdateND13ClientProfile(clientProfile);
                        this.callBackUpdateND13State(updateInfoState, state => {
                            state.updateInfoState = updateInfoState;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.reInstamentVerify = false;
                            state.stepName = updateInfoState;
                            state.trackingId = trackingID;
                            state.clientListStr = clientListStr;

                            state.appType = 'CLOSE';
                            state.proccessType = 'RIN';
                            state.stepName = FUND_STATE.SDK
                        });
                    } else {
                        // this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION, state=>{
                        //     setLocal(this.getLocalKey(state?.polID), JSON.stringify(state));
                        // })
                        // // this.confirmCPConsent();
                        // // this.setState({ updateInfoState: ND_13.INIT });
                        // // this.alertSucceeded();
                        // // this.props.handlerGoToStep(ND_13.DONE);
                        // // removeLocal(this.getLocalKey(this.state.polID));
                        // this.onClickConfirmCloseButton();
                        this.genOtpV2();
                    }
                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.setState({
                        openPopupWarningDecree13: false,
                    });
                    this.genOtpV2();
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    fetchCPConsentConfirmationRefresh(TrackingID, clientIds) {
        console.log('fetchCPConsentConfirmationRefresh', TrackingID);
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: clientIds? clientIds: this.state.clientListStr,
                ProcessType: this.state.proccessType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ? TrackingID : this.state.trackingId,
                ConsumerTracking : 'UpdatePolicy#fetchCPConsentConfirmationRefresh'
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
                    // const isOpenPopupWarning = ((Response.ClientProfile.length === 1 && consentResultPO !== ConsentStatus.AGREED) || (Response.ClientProfile.length > 1 && (this.haveLIStillNotAgree(clientProfile))));
                    // this.setState({
                    //     openPopupWarningDecree13: isOpenPopupWarning,
                    // });
                    const isOpenPopupWarning = (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) || this.haveLIStillNotAgree(clientProfile);
                    this.setState({
                        openPopupWarningDecree13: isOpenPopupWarning
                    });
                    console.log('fetchCPConsentConfirmationRefresh check customer consent', isOpenPopupWarning, consentResultPO, consentResulLI, Response.clientProfile)
                    // const poConfirmingND13 = isOpenPopupWarning ? ND13_CONFIRMING_STATUS.NEED : ND13_CONFIRMING_STATUS.NO_NEED; //prod mode
                    const poConfirmingND13 = ND13_CONFIRMING_STATUS.NEED; // develop mode
                    this.setState({consentDisabled: false, configClientProfile: Response.Config, clientProfile: Response.ClientProfile, poConfirmingND13})

                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.setState({consentDisabled: true, poConfirmingND13: ND13_CONFIRMING_STATUS.NO_NEED})
                } else {
                    console.log("System error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
    }



    addRequestParamCPConsent(records, role, answerPurpose) {
        console.log('addRequestParamCPConsent',this.state);
        records.forEach(record => {
            if (record) {
                if (record.Role === role) {
                    record.AnswerPurpose = answerPurpose;
                }
                record.RequesterID = getSession(CLIENT_ID);
                record.TrackingID = this.state.trackingId;
                record.RelationShip = "Self";
            }

        });

        return records;
    }



    submitCPConsnetConfirmationLI(data){
        console.log('submitCPConsnetConfirmationLI', data);
        const needLIConfirm = (data ?? []).filter(item => !item?.needPOConfirm).map(this.processDataLIConfirm);
        const needPOConfirm = (data ?? []).filter(item=> !!item?.needPOConfirm).map(this.processDataPOConfirm);
        const finalData = [...needLIConfirm].concat(needPOConfirm);
        console.log('finalData', finalData);
        // this.submitCPConsentConfirmationLI(finalData);
    }

    processDataLIConfirm(data){
        return {...data };
    }
    processDataPOConfirm(data){
        return {...data
            , AnswerPurpose: data?.AnswerPurpose ?? "YY"
        };
    }

    submitCPConsentConfirmationLI(data) {
        const jsonState = this.state;
        jsonState.submitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: this.state.proccessType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                ConsentSubmit: data,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    jsonState.submitting = false;
                    this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION, (jState)=>{
                        jState.submitting = false; 
                    });
                    this.setState(jsonState);
                } else {
                    jsonState.submitting = false;
                    this.setState(jsonState);
                }
            })
            .catch(error => {
                // console.log(error);
                jsonState.submitting = false;
                this.setState(jsonState);
            });
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

    genOtpV2 = () => {
        //gen otp, email/phone get at backend
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTPV2',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: 'VALID_OTP_REINSTATEMENT',
                OS: OS,
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                DCID : getSession(DCID),
            }

        }

        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({showOtpRIN: true, transactionId: response.Response.Message, minutes: 5, seconds: 0});
                    this.startTimer();
                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
            document.getElementById("popup-exception").className = "popup special point-error-popup show";
        });

    }

    popupOtpSubmitReIns = (OTP) => {
        this.submitReInstatmentConfirm(OTP);
    }

    submitReInstatmentConfirm = (OTP) => {
        let submitRequest = {
            jsonDataInput: {
              Company: COMPANY_KEY,
              Action: "ReInstatementConfirm",
              APIToken: getSession(ACCESS_TOKEN),
              Authentication: AUTHENTICATION,
              DeviceId: getDeviceId(),
              OS: WEB_BROWSER_VERSION,
              Project: "mcp",
              TransactionID: this.state.trackingId,
              UserLogin: getSession(USER_LOGIN),
              ClientID: getSession(CLIENT_ID),
              PolicyNo: this.state.polID,
              OtpVerified: OTP,
              TransactionVerified: this.state.transactionId
            }
          }
          onlineRequestSubmit(submitRequest)
          .then(res => {
              const currentState = this.state;
              let trackingID = '';
              if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Confirm RIN is saved successfull.') && res.Response.Message) {
                //   trackingID = res.Response.Message;
                  console.log('submitReInstatmentConfirm success', this.state.trackingId);
                //   this.deleteND13DataTemp(getSession(CLIENT_ID), this.state.trackingId, getSession(ACCESS_TOKEN), getDeviceId());
                //   document.getElementById("popupSucceededRedirectND13").className = "popup special envelop-confirm-popup show";
                this.setState({showOtpRIN: false, minutes: 0, seconds: 0, showThanks: true});
                // this.closeOtpRIN();

              } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                  this.setState({ errorMessage: OTP_EXPIRED });
              } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                  this.setState({ showOtp: false, minutes: 0, seconds: 0 });
                  document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
              } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                  this.setState({ errorMessage: OTP_INCORRECT });
              } else {
                  this.setState({ errorMessage: OTP_INCORRECT });
              }

          }).catch(error => {
          // alert(error);
      });

    }

    saveLocalUpdateReInstament=()=> {
        function getCircularReplacer() {
            const seen = new WeakSet();
            return (key, value) => {
                if (typeof value === "object" && value !== null) {
                    if (seen.has(value)) {
                        return;
                    }
                    seen.add(value);
                }
                return value;
            };
        }
        
        let currentState = {...this.state};
        const jsonString = JSON.stringify(currentState, getCircularReplacer());
        setLocal(this.getLocalKey(currentState.polID), jsonString);
        window.location.href = '/update-policy-info';
    }

    handlerAdjust = () => {
        if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
            this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18, toggleBack: !this.state.toggleBack});
        } 
    }

    resetState=()=> {
        const newState = INITIAL_STATE_UPDATE_POLICY_INFO();
        this.setState(newState);
    }

    updateNoValidPolicy = (value) => {
        this.setState({noValidPolicy: value});
    }

    render() {
        const showConfirmClear = () => {
            this.setState({ showConfirmClear: true });
            // if (this.state.stepName === FUND_STATE.UPDATE_INFO) {
            //     this.setState({stepName: this.state.stepName - 1, enabled: true});
            // } else {
            //     this.setState({stepName: this.state.stepName - 1, enabled: false});
            // }
        }
        const closeShowConfirmClear = () => {
            this.setState({ showConfirmClear: false });
        }
        const agreeClear=() => {
            removeLocal(this.getLocalKey(this.state.polID))
            const newState = INITIAL_STATE_UPDATE_POLICY_INFO();
            this.setState(newState);
        }


        //Payment
        const radioPaymentLoan = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );

            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updateLoan = true;
            jsonState.updateMaturity = false;
            jsonState.updateSurrender = false;
            jsonState.updatePremiumRefund = false;
            jsonState.updateCoupon = false;
            jsonState.updateDividend = false;
            jsonState.updatePartialWithdrawal = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Loan';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const radioPaymentMaturity = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updateMaturity = true;
            jsonState.updateLoan = false;
            jsonState.updateSurrender = false;
            jsonState.updatePremiumRefund = false;
            jsonState.updateCoupon = false;
            jsonState.updateDividend = false;
            jsonState.updatePartialWithdrawal = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Maturity';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const radioPaymentSurrender = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updateSurrender = true;
            jsonState.updateMaturity = false;
            jsonState.updateLoan = false;
            jsonState.updatePremiumRefund = false;
            jsonState.updateCoupon = false;
            jsonState.updatePartialWithdrawal = false;
            jsonState.updateDividend = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Surrender';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const radioPaymentPremiumRefund = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updatePremiumRefund = true;
            jsonState.updateSurrender = false;
            jsonState.updateMaturity = false;
            jsonState.updateCoupon = false;
            jsonState.updateLoan = false;
            jsonState.updatePartialWithdrawal = false;
            jsonState.updateDividend = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Premium Refund';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const radioPaymentCoupon = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updateCoupon = true;
            jsonState.updatePremiumRefund = false;
            jsonState.updateSurrender = false;
            jsonState.updateMaturity = false;
            jsonState.updateLoan = false;
            jsonState.updatePartialWithdrawal = false;
            jsonState.updateDividend = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Coupon';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }
        
        const radioPartialWithdrawal = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updatePartialWithdrawal = true;
            jsonState.updateLoan = false;
            jsonState.updateMaturity = false;
            jsonState.updateSurrender = false;
            jsonState.updatePremiumRefund = false;
            jsonState.updateCoupon = false;
            jsonState.updateDividend = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Partial Withdrawal';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const radioDividend = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMENT}`,
            );
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({noPhone: true});
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({noTwofa: true});
                return;
            }
            let jsonState = this.state;
            jsonState.stepName = FUND_STATE.CATEGORY_INFO;
            this.setState(jsonState);
            jsonState.updateDividend = true;
            jsonState.updatePartialWithdrawal = false;
            jsonState.updateLoan = false;
            jsonState.updateMaturity = false;
            jsonState.updateSurrender = false;
            jsonState.updatePremiumRefund = false;
            jsonState.updateCoupon = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'Dividend';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            jsonState.noPhone = false;
            jsonState.noEmail = false;
            this.setState(jsonState);
        }

        const goToChoosePolicy = () => {
            let jsonState = this.state;
            if (jsonState.loading) {
                setTimeout(goToChoosePolicy, 2000);
                return;
            }
            if (!jsonState.polListProfile || jsonState.polListProfile.length < 1) {
                this.setState({noValidPolicy: true});
                return;
            }
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.toggleRender = !jsonState.toggleRender;
            this.setState(jsonState);
        }

        const closeNotAvailable = () => {
            // console.log('closeNotAvailable');

            this.setState({
                msgCheckPermission: '',
                isCheckPermission: false
            });
            this.props.history.push('/');
        }

        const goToChooseLapsePolicy = () => {
            let jsonState = this.state;
            if (jsonState.loading) {
                setTimeout(goToChooseLapsePolicy, 2000);
                return;
            }
            if (!jsonState.polListLapse || jsonState.polListLapse.length < 1) {
                this.setState({noValidPolicy: true});
                return;
            }
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.toggleRender = !jsonState.toggleRender;
            this.setState(jsonState);
        }

        const handlerBackToPrevStep = () => {
            if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
                this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18, toggleBack: !this.state.toggleBack});
            } else {
                if (this.state.updateReinstatement && this.state.stepName === FUND_STATE.UPDATE_INFO) {
                    showConfirmClear();
                } else {
                    if (this.state.stepName === FUND_STATE.UPDATE_INFO) {
                        this.setState({stepName: this.state.stepName - 1, enabled: true});
                    } else if (this.state.stepName === FUND_STATE.CHOOSE_POLICY) {
                        agreeClear();
                    } else {
                        this.setState({stepName: this.state.stepName - 1, enabled: false});
                    }
    
                }
            }

        }

        const closePopupError = () => {
            this.setState({
                noValidPolicy: false,
                totalToFundError: false,
                totalFundCalInTempError: false,
                percentError: false,
                noChangeInvestRate: false,
                noPhone: false,
                notiLinkExpired: false
            });
        }

        const closeNoTwofa = () => {
            this.setState({noTwofa: false});
        }

        const closeNotice = () => {
            this.setState({showNotice: false});
        }

        const showNotice = () => {
            this.setState({showNotice: true});
        }

        const toggleAllAddress = () => {
            if (document.getElementById('addr0').className === 'address-hearder choosen') {
                document.getElementById('addr0').className = 'address-hearder';
                if (this.state.polListProfile) {
                    for (let i = 0; i < this.state.polListProfile.length; i++) {
                        document.getElementById('ad-' + i).className = 'card';
                    }
                }
            } else {
                document.getElementById('addr0').className = 'address-hearder choosen';
                if (this.state.polListProfile) {
                    for (let i = 0; i < this.state.polListProfile.length; i++) {
                        document.getElementById('ad-' + i).className = 'card choosen';
                    }
                }
            }
        }

        const closeSubmitIn24 = () => {
            this.setState({submitIn24: false});
        }

        if (document.getElementById('cellPhoneId')) {
            if (this.state.isCellPhoneChecked) {
                document.getElementById('cellPhoneId').checked = true;
            } else {
                document.getElementById('cellPhoneId').checked = false;
            }
        }
        if (document.getElementById('homePhoneId')) {
            if (this.state.isHomePhoneChecked) {
                document.getElementById('homePhoneId').checked = true;
            } else {
                document.getElementById('homePhoneId').checked = false;
            }
        }
        if (document.getElementById('bussinessPhoneId')) {
            if (this.state.isBusinessPhoneChecked) {
                document.getElementById('bussinessPhoneId').checked = true;
            } else {
                document.getElementById('bussinessPhoneId').checked = false;
            }
        }

        if (this.state.updatePhone && (this.state.stepName === FUND_STATE.UPDATE_INFO) && (!document.getElementById('cellPhoneId') || !document.getElementById('homePhoneId') || !document.getElementById('bussinessPhoneId'))) {
            this.setState({toggle: !this.state.toggle});
        }

     

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }


        function getMapSize(x) {
            let len = 0;
            for (const count in x) {
                len++;
            }

            return len;
        }

        const closeApiError = () => {
            this.setState({apiError: false});
        }

 
        let msg = '';
        let imgPath = '';
        let msgPopup = '';
        let popupImgPath = '';
        if (!this.state.enabled) {
            msg = 'Vui lòng chọn loại thông tin cần thay đổi ở bên trái.';
            imgPath = 'img/icon/empty.svg';
        }
        if (this.state.noValidPolicy) {
            msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu ' + SUBMISSION_TYPE_MAPPING[this.state.proccessType] + '. Quý khách vui lòng kiểm tra lại.';
            imgPath = 'img/popup/no-policy.svg';
        }

        if (this.state.noPhone) {
            // msgPopup = 'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            msgPopup = 'Yêu cầu thay đổi này cần Số điện thoại di động để nhận mã xác thực. Quý khách vui lòng cập nhật thông tin trên ứng dụng hoặc liên hệ văn phòng Dai-ichi Life gần nhất.';
            popupImgPath = 'img/popup/no-phone.svg';
        }

        if (this.state.showNotice) {
            popupImgPath = 'img/popup/notice.png';
        }

        if (this.state.apiError) {
            msgPopup = 'Có lỗi xảy ra, vui lòng thử lại';
            popupImgPath = 'img/popup/quyenloi-popup.svg';
        }

        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: "/home"
            }}/>;
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
                <main className="logined" id="main-id">
                    <div className="main-warpper basic-mainflex payment">
                        <section className="qlbh-sccard-wrapper sccard-warpper hopdong-sccard-wrapper"
                                 style={{paddingLeft: '0', paddingRight: '0'}}>
                            <div className="stepform stepform-42" style={{marginTop: '0px'}}>
                                <div className="stepform__body" style={{paddingTop: '4px', boxShadow: 'none'}}>
                                    <div className="info">
                                        <div className="info__title" style={{marginBottom: '0px'}}>
                                            <h4>CHỌN LOẠI THANH TOÁN</h4>
                                        </div>
                                        <div className="info__body">
                                            <div className="checkbox-wrap basic-column">
                                                <div className="tab-wrapper">
                                                    <div className="tab">
                                                        <div className="info__title">
                                                            <h5 className='policy-change-title basic-semibold'></h5>
                                                        </div>
                                                        <div className="checkbox-warpper" style={{marginTop: '-17px'}}>
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updateMaturity}
                                                                               onClick={(e) => radioPaymentMaturity(e)}
                                                                               id='radioMaturity'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                           Nhận quyền lợi bảo hiểm đáo hạn
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updateDividend}
                                                                               onClick={(e) => radioDividend(e)}
                                                                               id='radioDividend'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Nhận lãi chia tích lũy
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updateCoupon}
                                                                               onClick={(e) => radioPaymentCoupon(e)}
                                                                               id='radioCoupon'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Nhận quyền lợi tiền mặt định kỳ
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updateLoan}
                                                                               onClick={(e) => radioPaymentLoan(e)}
                                                                               id='radioLoan'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Tạm ứng từ giá trị hoàn lại
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updatePartialWithdrawal}
                                                                               onClick={(e) => radioPartialWithdrawal(e)}
                                                                               id='radioPartialWithdrawal'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Rút một phần giá trị tài khoản/giá trị quỹ
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {/* <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updateSurrender}
                                                                               onClick={(e) => radioPaymentSurrender(e)}
                                                                               id='radioSurrender'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Chấm dứt hợp đồng bảo hiểm trước hạn
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div> */}
                                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={this.state.updatePremiumRefund}
                                                                               onClick={(e) => radioPaymentPremiumRefund(e)}
                                                                               id='radioPremiumRefund'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text" style={{marginLeft: '3px'}}>
                                                                            Nhận phí bảo hiểm đóng dư
                                                                        </p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                        <div className="bottom-text"
                                             style={{'maxWidth': '389px', paddingLeft: '20px', paddingRight: '20px'}}>
                                            <p style={{textAlign: 'justify'}}>
                                                <span className="red-text basic-bold">Lưu ý: </span><span
                                                style={{color: '#727272'}}>
                            Quý khách có yêu cầu thanh toán khác, vui lòng liên hệ Tổng đài hoặc Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.
                            </span>
                                            </p>
                                        </div>
                                    </div>
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Tiếp tục</p>
                                <i><img src="../img/icon/arrow-left.svg" alt=""/></i>
                            </div>
                        </section>

                        <section className="sccontract-warpper additional-information-sccontract-warpper">
                            <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                                <div className="breadcrums__item">
                                    <p>Giao dịch hợp đồng</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                                <div className="breadcrums__item">
                                    <p>Thanh toán giá trị hợp đồng</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                            </div>
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="../img/icon/return_option.svg" alt=""/></i>
                            </div>
                            <LoadingIndicator area="policyList-by-cliID"/>
                            {(this.state.noValidPolicy) ? (
                                this.state.updateReinstatement ? (
                                    <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
                                        <div className="insurance">
                                            <div className="empty">
                                                <div className={this.state.noValidPolicy ? "picture" : "icon"}
                                                     style={{marginTop: '-45px'}}>
                                                    <img src={imgPath} alt=""/>
                                                </div>
                                                {this.state.noValidPolicy ? (
                                                    <h5 className='msg-alert-fixed'>Quý khách không có hợp
                                                        đồng <br/> cần khôi phục hiệu lực.</h5>
                                                ) : (
                                                    <p style={{paddingTop: '20px'}}>{msg}</p>
                                                )}


                                            </div>
                                        </div>
                                    </div>
                                ) : (
                                    <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
                                        <div className="insurance">
                                            <div className="empty">
                                                <div className={this.state.noValidPolicy ? "picture" : "icon"}
                                                     style={{marginTop: '-45px'}}>
                                                    <img src={imgPath} alt=""/>
                                                </div>
                                                {this.state.noValidPolicy ? (
                                                    <h5 className='msg-alert-fixed'>{msg}</h5>
                                                ) : (
                                                    <p style={{paddingTop: '20px'}}>{msg}</p>
                                                )}


                                            </div>
                                        </div>
                                    </div>
                                )

                            ) : (
                            <div className="sccontract-container" style={{backgroundColor: '#f5f3f2'}}>
                                <LoadingIndicator area="zip-code-list"/>
                                <div className="insurance">
                                    <div className='heading' style={{
                                        filter: 'drop-shadow(0px 4px 0px 8px rgba(0, 0, 0, 0.125))',
                                        paddingBottom: '0'
                                    }}>
                                        <div className="breadcrums">
                                            <div className="breadcrums__item">
                                                <p>Giao dịch hợp đồng</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                            <div className="breadcrums__item">
                                                <p>Thanh toán giá trị hợp đồng</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                        </div>
                                        <LoadingIndicator area="policyList-by-cliID"/>
                                        {this.state.stepName === FUND_STATE.CATEGORY_INFO &&
                                        <div className="heading__tab">
                                            <div className="step-container">
                                                <div className="step-wrapper" style={{marginLeft: '16px'}}>
                                                    {this.state.stepName > FUND_STATE.CATEGORY_INFO && !this.state.allLIAgree && !this.state.aLINotAgree &&
                                                        <div className="step-btn-wrapper">
                                                            <div className="back-btn">
                                                                <button onClick={() => handlerBackToPrevStep()}>
                                                                    <div className="svg-wrapper">
                                                                        <svg
                                                                            width="11"
                                                                            height="15"
                                                                            viewBox="0 0 11 15"
                                                                            fill="none"
                                                                            xmlns="http://www.w3.org/2000/svg"
                                                                        >
                                                                            <path
                                                                                d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                                                                                fill="#985801"
                                                                                stroke="#985801"
                                                                                strokeWidth="0.2"
                                                                            />
                                                                        </svg>
                                                                    </div>
                                                                    <span className="simple-brown">Quay lại</span>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    }
                                                    <div className="progress-bar">
                                                        <div
                                                            className={(this.state.stepName >= FUND_STATE.CATEGORY_INFO) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>1</span>
                                                            </div>
                                                            <p>Loại thanh toán</p>
                                                        </div>
                                                        <div
                                                            className={(this.state.stepName >= FUND_STATE.CHOOSE_POLICY) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>2</span>
                                                            </div>
                                                            <p>Chọn hợp đồng</p>
                                                        </div>
                                                        <div
                                                            className={(this.state.stepName >= FUND_STATE.UPDATE_INFO) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>3</span>
                                                            </div>
                                                            <p>Nhập thông tin</p>
                                                        </div>
                                                        <div
                                                            className={(this.state.stepName >= FUND_STATE.VERIFICATION) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>4</span>
                                                            </div>
                                                            <p>Xác nhận</p>
                                                        </div>
                                                        {this.state.updateReinstatement && (this.state.stepName >= FUND_STATE.UPDATE_INFO) && !this.state.allLIAgree && !this.state.aLINotAgree &&
                                                            <div className="step-btn-save-quit" style={{zIndex: '9999', display: 'flex', paddingTop: '10px'}}>
                                                                <div>
                                                                    <button>
                                                                        <span className="simple-brown" style={{ zIndex: '30' }}
                                                                            onClick={this.saveLocalUpdateReInstament}>Lưu & thoát</span>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                         } 
                                                        </div>
                                                </div>
                                            </div>
                                        </div>
                                        }
                                    </div>
                                </div>
                                {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                                    //this.state.updateLoan &&(
                                        <ChangePayment 
                                        appType={this.state.appType}
                                        trackingId={this.state.trackingId}
                                        clientListStr={this.state.clientListStr}
                                        clientId={getSession(CLIENT_ID)}
                                        proccessType={this.state.proccessType}
                                        deviceId={getDeviceId()}
                                        apiToken={getSession(ACCESS_TOKEN)}
                                        policyNo={this.state.polID} 
                                        polID={this.state.polID} 
                                        phone={getSession(CELL_PHONE)}
                                        twoFA={getSession(TWOFA)}
                                        clientClass={getSession(CLIENT_CLASS)}
                                        clientName={getSession(FULL_NAME)}
                                        email={getSession(EMAIL)}
                                        DCID={getSession(DCID)}
                                        handlerUpdateNoValidPolicy={this.handlerUpdateNoValidPolicy}
                                        handlerResetState={this.handlerResetState} 
                                        />
                                    //)

                                ) : (
                                    <></>
                                )}

                            </div>)}
                        
                        </section>
                    </div>

                </main>
                {this.state.noPhone &&
                    <AlertPopupPhone closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}
                                     screen={SCREENS.PAYMENT_CONTRACT}/>
                }
                {(this.state.invalidFund || this.state.totalToFundError || this.state.totalFundCalInTempError || this.state.noChangeInvestRate) &&
                    <AlertPopup closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}/>
                }
                {this.state.noTwofa &&
                    <AuthenticationPopup closePopup={closeNoTwofa}
                                         msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                                         screen={SCREENS.PAYMENT_CONTRACT}/>
                }

                {this.state.apiError &&
                    <AlertPopupError closePopup={closeApiError} msg={msgPopup} imgPath={popupImgPath}/>
                }

                {this.state.openPopupWarningDecree13 && <POWarningND13 proccessType={this.state.proccessType} onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })}/>}
                {this.state.showConfirmClear &&
                    <ConfirmPopup closePopup={() => closeShowConfirmClear()} go={() => agreeClear()} />
                }
                {this.state.notiLinkExpired &&
                    <AlertPopupOriginal closePopup={closePopupError} msg='Liên kết với thông báo này không còn hiệu lực' imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}
                                     screen={SCREENS.PAYMENT_CONTRACT}/>
                }
            </>


        );

    }

}


export default withRouter(PaymentContract);