import React, {Component} from 'react';
import {
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    PERSONAL_INFO_STATE ,
    PageScreen,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    ConsentStatus,
    OTP_EXPIRED, 
    COMPANY_KEY2,
    OTP_INCORRECT,
    OS,
    DCID,
    NOTE_MAPPING,
    CONFIRM_ACTION_MAPPING,
    UPDATE_POLICY_INFO_SAVE_LOCAL,
    FE_BASE_URL,
    ND_13,
    SCREENS,
    OTP_SYSTEM_ERROR,
    IS_MOBILE,
    SDK_ROLE_AGENT,
    SDK_REQUEST_STATUS,
    CLAIM_STEPCODE, 
    SDK_ROLE_PO,
    SDK_REVIEW_PROCCESS,
    CACHE_TRACKING_ID 
} from './sdkConstant';
import {
    CPConsentConfirmation,
    enscryptData,
    genOTP,
    onlineRequestSubmitConfirm
} from './sdkAPI';
import {
    getDeviceId,
    getSession,
    maskPhone,
    trackingEvent, setLocal, getLocal,
    removeLocal,
    cpSaveLog,
    getUrlParameterNoDecode,
    getUrlParameter,
    maskEmail,
    cpSaveLogSDK,
    haveCheckedDeadth,
    haveHC_HS,
    isOlderThan18,
    getBenifits,
    removeSession
} from './sdkCommon';
import ND13ContactFollowConfirmation from "./ModuleND13/ND13ContactFollowConfirmation";
import ND13POContactInfoOver18 from "./ModuleND13/ND13Modal/ND13POContactInfoOver18";
import ND13CancelRequestConfirm from "./ModuleND13/ND13Modal/ND13CancelRequestConfirm/ND13CancelRequestConfirm";
import ND13POConfirm from "./ModuleND13/ND13Modal/ND13POConfirm";
import PopupSuccessfulND13 from "./ModuleND13/ND13Modal/PopupSuccessfulND13";
import POWarningND13 from "./ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import DOTPInput from './components/DOTPInput';
import ThanksPopup from './components/ThanksPopup';
import AES256 from 'aes-everywhere';
import {cloneDeep, isEmpty} from "lodash";
import SuccessChangePaymodePopup from './components/SuccessChangePaymodePopup';
import SuccessChangeFundRatePopup from './components/SuccessChangeFundRatePopup';
import OkPopupTitle from './components/OkPopupTitle';
import AgreeCancelPopup from './components/AgreeCancelPopup';
import ND13Expire from './ND13Expire';
import ExpirePopup from './components/ExpirePopup';

export const ProcessType = "RIN";

const OVER_LAPSE_YEAR = 3; // default 1

const ND13_CONFIRMING_STATUS = {
    NONE: '',
    NEED: '1',
    NO_NEED: '0'
}
let fromNative = '';
let myInterval = '';
let waiting = true;
let expire = false;
let isPayment = false;
class ND13 extends Component {
    constructor(props) {
        super(props);

        this.state = {
            poConfirmingND13: ND13_CONFIRMING_STATUS.NEED, //NONE
            updateInfoState: (this.props.proccessType === 'Claim') && this.props.updateInfoState? this.props.updateInfoState: ND_13.NONE,//this.props.updateInfoState,//ND_13.NONE, //NONE
            trackingId: this.props.trackingId, // ''
            clientListStr: this.props.clientListStr, 
            clientId: this.props.clientId,
            proccessType: this.props.proccessType,
            appType: this.props.appType,
            deviceId: this.props.deviceId,
            apiToken: this.props.apiToken,
            policyNo: this.props.policyNo,
            showOtp: false,
            minutes: 0,
            seconds: 0,
            transactionId: '1',
            showSuccess: false,
            openPopupWarningDecree13: false,
            errorMessage: '',
            phone: '',
            email: '',
            relateModalDataPO: {
                refPurpose: {
                    value: '', error: '',
                }, refOtherPurpose: {
                    value: '', error: '',
                },
            },
            poConfirmData: '',
            liConfirmData: '',
            relateModalData: {},
            clientProfile: [],
            configClientProfile: [],
            toggleBack: false,
            clientName: this.props.clientName,
            DCID: '',
            isSubmitting: false,
            stepName: this.props.stepName,
            isActive: {},
            isWaitConfirm: true,
            isExpired: false,
            sendPOSuccess: false,
            isPayment: false,
            reviewNeedPOConfirm: false,
            linkExpired: false,
            acceptPolicy: false

        }

        this.callBackUpdateND13State  = this.callBackUpdateND13State.bind(this);
        this.callBackUpdateND13StateRefresh = this.callBackUpdateND13StateRefresh.bind(this);
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerUpdateIsWaitConfirm = this.updateIsWaitConfirm.bind(this);
    }
    handlerGoToStep(step){
        const current = this.state;
        current.updateInfoState = step;
        this.setState(current);
        this.props.handlerGoToStep(step);
    }

    componentDidUpdate() {
        if (this.state.proccessType === 'RIN') {
            if (this.props.updateInfoState !== this.state.updateInfoState) {
                console.log('this.props.updateInfoState=', this.props.updateInfoState);
                if (this.props.toggleBack !== this.state.toggleBack) {
                    console.log('if this.state.updateInfoState=', this.state.updateInfoState);
                    this.callBackUpdateND13StateRefresh(this.props.updateInfoState, this.props.toggleBack);
                } else {
                    console.log('else this.state.updateInfoState=', this.state.updateInfoState);
                    this.props.handlerGoToStep(this.state.updateInfoState);
                }
            }
        }
        if ((this.state.clientName !== this.props.clientName) && this.props.clientName) {
            this.setState({clientName: this.props.clientName});
        }
        if (this.props.trackingId && (this.props.trackingId !== this.state.trackingId)) {
            this.setState({trackingId: this.props.trackingId});
        }
        // if ((this.state.proccessType === 'Claim') && (this.state.updateInfoState !== this.props.updateInfoState)) {
        //     this.setState({updateInfoState: this.props.updateInfoState});
        // }
    }




    // getLocalKey = (trackingId) => UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + this.state.clientId + this.state.trackingId;
    // getLocalKey = (trackingId) => this.state.trackingId;
    closeThanks = () => {
        this.setState({showThanks: false});
    }

    componentDidMount() {
        fromNative = getUrlParameter("fromApp");
        let proccessType = this.props.proccessType;
        console.log('stataaaa=', this.state.proccessType);
        console.log('clientIdxx=', this.state.clientId);
        if ((this.props.proccessType === 'Claim') && (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) && (this.props.systemGroupPermission?.[0]?.Role !== SDK_ROLE_AGENT)) {
            this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18});
            return;
        }
        if ((this.state.appType === "CLOSE") || (this.props.trackingId)) {
            console.log('this.props.trackingId = ' + this.props.trackingId);
            if (this.props.trackingId) {
                this.loadND13DataTemp(this.props.clientId, this.props.trackingId, this.props.apiToken, this.props.deviceId, this.props.clientListStr, this.props.proccessType);
            } else {
                this.fetchCPConsentConfirmation(this.props.trackingId, this.props.clientId, this.props.clientListStr, this.props.proccessType, this.props.apiToken, this.props.deviceId);
            }
        } else {
            let AppType = getUrlParameter("AppType");
            if (!AppType) {
                alert("Invalid Url");
                return;
            }
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
                                let policyNo = ParamObject?.policyNo??this.state.policyNo;
                                let clientName = ParamObject?.clientName?ParamObject?.clientName: '';
                                this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, clientName: clientName });
                                // this.fetchCPConsentConfirmation(trackingId, clientId, clientListStr, proccessType, apiToken, deviceId);
                                this.loadND13DataTemp(clientId, trackingId, apiToken, deviceId, clientListStr, proccessType);
                                        
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
                        let policyNo = ParamObject?.policyNo?ParamObject?.policyNo:this.state.policyNo;
                        let clientName = ParamObject?.clientName?ParamObject?.clientName: '';
                        let DCID = ParamObject?.DCID?ParamObject?.DCID: '';
                        this.setState({ trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, clientName: clientName, DCID: DCID });
                        // this.fetchCPConsentConfirmation(trackingId, clientId, clientListStr, proccessType, apiToken, deviceId);
                        this.loadND13DataTemp(clientId, trackingId, apiToken, deviceId, clientListStr, proccessType);
                    }
                }
            }
        }
        // const clientIds = (this.state.insuredLapseList[0]?.Questionaire ?? []).map(item => item?.LifeInsureID);
        // const concateCLientIds = clientIds.join(',');
        // this.fetchCPConsentConfirmation(trackingID, concateCLientIds);

        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        cpSaveLogSDK(`${fromNative?fromNative:'Web'}_${this.props.proccessType}${PageScreen.SDK_ND13}`, this.props.apiToken, this.props.deviceId, this.props.clientId);
        trackingEvent(
            `${fromNative?fromNative:'Web'}_${proccessType}${PageScreen.SDK_ND13}`,
            `${fromNative?fromNative:'Web'}_${proccessType}${PageScreen.SDK_ND13}`,
            `${fromNative?fromNative:'Web'}_${proccessType}${PageScreen.SDK_ND13}`,
        );
    }

    componentWillUnmount() {
        if (myInterval) {
            clearInterval(myInterval);
        }
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
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
        
        if(callback) {
            callback(jsonState);
        }
            
        // setLocal(this.getLocalKey(this.state.trackingId), JSON.stringify(jsonState));
        this.setState(jsonState);
        // this.props.handlerGoToStep(value);
    }

    callBackUpdateND13StateRefresh(value, toggleBack) {
        const setComponentState = this.setState.bind(this);
        let jsonState = this.state;
        jsonState.updateInfoState = value;
        jsonState.toggleBack = toggleBack;
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: jsonState.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: jsonState.clientId,
                Company: COMPANY_KEY,
                ClientList: jsonState.clientListStr? jsonState.clientListStr: getSession(CLIENT_ID),
                ProcessType: jsonState.proccessType,
                DeviceId: jsonState.deviceId, 
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: jsonState.clientId,
                TrackingID: jsonState.trackingId,
                ConsumerTracking: 'Update#FollowConfirmation'
            }
        };
        CPConsentConfirmation(request).then(res => {
            let Response = res.Response;
            if ((Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile)) {
                // this.setState({clientProfile: Response.ClientProfile, updateInfoState: value});
                jsonState.clientProfile = Response.ClientProfile;
                setComponentState(jsonState);
            }
        }).catch(error => {
            console.log(error);
        });
        // this.props.handlerGoToStep(value);
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

    fetchCPConsentConfirmation(TrackingID, clientID, clientListStr, proccessType, apiToken, deviceId) {
        let trId = TrackingID;
        if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
            trId = '';
        }
        if ((proccessType === 'Claim') && (this.props.trackingId === this.props.claimId)) {
            trId = '';
        }
        console.log('fetchCPConsentConfirmation', TrackingID)
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClientID: clientID,
                Company: COMPANY_KEY,
                // ClientList: clientIds ?? this.generateSessionString(getSession(CLIENT_ID), this.state.polID),
                ClientList: clientListStr? clientListStr: getSession(CLIENT_ID),
                ProcessType: proccessType,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientID,
                TrackingID: trId 
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    // const { DOB } = this.state.clientProfile;
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    console.log('clientProfile=', clientProfile);
                    console.log('configClientProfile=', configClientProfile);
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
                    let payment = false;
                    if ((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && !this.props.disableEdit) {
                        const paymentProfile =  !isEmpty(clientProfile)?clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'CLAIM_PAYMENT')):[];
                        payment = !isEmpty(paymentProfile);
                    } else if ((!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo?.dOB)) || (!isOlderThan18(this.props.selectedCliInfo?.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList)))){
                        payment = true;
                    }
                    
                    console.log('consentResultPO=', consentResultPO);
                    const trackingID = TrackingID ?TrackingID: this.state.trackingId;
                    if ((this.props.proccessType === 'RIN')) {
                        const isOpenPopupWarning = (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) || this.haveLIStillNotAgree(clientProfile);
                        this.setState({
                            openPopupWarningDecree13: isOpenPopupWarning
                        });
                    }
                    if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                        const updateInfoState = ND_13.ND13_INFO_CONFIRMATION;
                        this.callBackUpdateND13State(updateInfoState, state => {
                            if (this.haveLIStillNotAgree(clientProfile)) {
                                state.updateInfoState = updateInfoState;
                            } 
                            // state.updateInfoState = updateInfoState;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.reInstamentVerify = false;
                            state.stepName = updateInfoState;
                            state.trackingId = trackingID;
                            state.clientListStr = clientListStr;
                            this.saveND13DataTemp(state.clientId, state.trackingId, JSON.stringify(state), state.apiToken, state.deviceId);
                        });
                        this.callBackUpdateND13ClientProfile(clientProfile);
                        this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
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
                            this.saveND13DataTemp(state.clientId, state.trackingId, JSON.stringify(state), state.apiToken, state.deviceId);
                        });
                        // this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
                    } else if (!isPayment && payment && (proccessType === 'Claim')) {
                        isPayment = true;
                        this.setState({isPayment: true});
                        const updateInfoState = ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18;
                        this.props.setUpdateInfoState(updateInfoState);
                        this.callBackUpdateND13ClientProfile(clientProfile);
                        this.callBackUpdateND13State(updateInfoState, state => {
                            state.updateInfoState = updateInfoState;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.reInstamentVerify = false;
                            state.stepName = updateInfoState;
                            state.trackingId = trackingID;
                            state.clientListStr = clientListStr;
                            this.saveND13DataTemp(state.clientId, state.trackingId, JSON.stringify(state), state.apiToken, state.deviceId);
                            // this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
                        });
                    } else if (proccessType === 'Claim') {
                        if ((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && !this.props.disableEdit) {
                            if (this.props.poConfirmingND13 === '1') {
                                this.props.submitClaimInfo('PO_CONFIRMING_DECREE_13');
                            } else {
                                this.props.submitClaimInfo();
                            }
                        } else {
                            if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                                if(!this.props.disableEdit) {
                                // this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_ROLE_AGENT, "");//Truyền status empty sẽ keep current status
                                    let copyState = cloneDeep(this.state);    
                                    this.saveND13DataTemp(copyState.clientId, copyState.trackingId, JSON.stringify(copyState), copyState.apiToken, copyState.deviceId);
                                    this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "", this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));//Truyền status empty sẽ keep current status
                                    // this.props.setSendPOSuccess();
                                } else {
                                    if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
                                        this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18});
                                        // this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
                                    } /*else {
                                        this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
                                    }*/
                                    
                                }
                            } else {
                                // alert('12');
                                if (this.props.poConfirmingND13 === '1') {
                                    this.props.submitClaimInfo('PO_CONFIRMING_DECREE_13');
                                } else {
                                    this.props.submitClaimInfo();
                                }
                            }

                        }
                        
                    } else {
                        this.genOtp();
                    }
                } else {
                    console.log("System error!");
                }
                if (this.props.saveLocal) {
                    this.props.saveLocal();
                }
            })
            .catch(error => {
                console.log(error);
            });
    }


    // fetchCPConsentConfirmation(TrackingID) {
    //     let request = {
    //         jsonDataInput: {
    //             Action: "CheckCustomerConsent",
    //             APIToken: getSession(ACCESS_TOKEN),
    //             Authentication: AUTHENTICATION,
    //             ClientID: getSession(CLIENT_ID),
    //             Company: COMPANY_KEY,
    //             ClientList: this.generateSessionString(getSession(CLIENT_ID), this.state.selectedCliInfo),
    //             ProcessType: "Claim",
    //             DeviceId: getDeviceId(),
    //             OS: WEB_BROWSER_VERSION,
    //             Project: "mcp",
    //             UserLogin: getSession(USER_LOGIN),
    //             TrackingID: TrackingID ? TrackingID : this.state.trackingId,
    //         }
    //     };

    //     CPConsentConfirmation(request)
    //         .then(res => {
    //             const Response = res.Response;
    //             if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
    //                 const { DOB } = this.state.selectedCliInfo;
    //                 const clientProfile = Response.ClientProfile;
    //                 const configClientProfile = Response.Config;
    //                 const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
    //                 const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
    //                 if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED) {
    //                     const claimSubmissionState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
    //                     this.setState({
    //                         claimSubmissionState,
    //                         clientProfile,
    //                         configClientProfile,
    //                     });
    //                     this.callBackUpdateND13State(claimSubmissionState);
    //                     this.callBackUpdateND13ClientProfile(clientProfile);
    //                 } else if (consentResulLI === ConsentStatus.WAIT_CONFIRM || consentResulLI === ConsentStatus.EXPIRED || consentResulLI === ConsentStatus.DECLINED) {
    //                     const isOver18 = isOlderThan18(DOB);
    //                     const claimSubmissionState = isOver18
    //                         ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
    //                         : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;

    //                     this.setState({
    //                         claimSubmissionState,
    //                         clientProfile,
    //                         configClientProfile,
    //                     });
    //                     this.callBackUpdateND13ClientProfile(clientProfile);
    //                     this.callBackUpdateND13State(claimSubmissionState);
    //                 } else if (!this.state.isPayment) {
    //                     this.setState({ isPayment: true });
    //                     const isOver18 = isOlderThan18(DOB);
    //                     const claimSubmissionState = isOver18
    //                         ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
    //                         : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;

    //                     this.setState({
    //                         claimSubmissionState,
    //                         clientProfile,
    //                         configClientProfile
    //                     });
    //                     this.callBackUpdateND13State(claimSubmissionState);
    //                     this.callBackUpdateND13ClientProfile(clientProfile);
    //                 } else {
    //                     this.confirmCPConsent();
    //                     this.setState({ claimSubmissionState: CLAIM_STATE.INIT });
    //                     this.alertSucceeded();
    //                     this.handlerGoToStep(CLAIM_STATE.DONE);
    //                     removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
    //                     this.onClickConfirmCloseButton();
    //                 }
    //             } else {
    //                 console.log("System error!");
    //             }
    //         })
    //         .catch(error => {
    //             console.log(error);
    //         });
    // }



    fetchCPConsentConfirmationRefresh(TrackingID) {
        console.log('fetchCPConsentConfirmationRefresh', TrackingID);
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId,
                Company: COMPANY_KEY,
                ClientList: this.state.clientListStr? this.state.clientListStr: getSession(CLIENT_ID),
                ProcessType: this.state.proccessType,
                DeviceId: this.state.deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: this.state.clientId,
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
                    const isOpenPopupWarning = ((Response.ClientProfile.length === 1 && consentResultPO !== ConsentStatus.AGREED) || (Response.ClientProfile.length > 1 && (this.haveLIStillNotAgree(clientProfile))));
                    console.log('check customer consent', isOpenPopupWarning, consentResultPO, consentResulLI, Response.clientProfile)
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
                record.RequesterID = this.props.clientId?this.props.clientId:this.state.clientId;
                record.TrackingID = this.props.trackingId?this.props.trackingId:this.state.trackingId;
                record.RelationShip = "Self";
            }

        });

        return records;
    }



    submitCPConsnetConfirmationLI(data, type){
        console.log('submitCPConsnetConfirmationLI', data);
        if ((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || this.props.disableEdit) {
            if (!this.props.disableEdit) {
                this.setState({liConfirmData: data});
                let state = this.state;
                state.liConfirmData = data;
                const updateInfoState = ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18;
                state.updateInfoState = updateInfoState;
                state.stepName = updateInfoState;
                console.log('xstate=', state);
                let copyState = cloneDeep(state);
                console.log('copyState=', copyState);
                this.saveND13DataTemp(copyState.clientId, copyState.trackingId, JSON.stringify(copyState), copyState.apiToken, copyState.deviceId);
                this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "", this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));//Truyền status empty sẽ keep current status
                // this.setState({sendPOSuccess: true});
                return;
            } else {
                if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                    if (this.state.liConfirmData) {
                        console.log('this.state.liConfirmData=', this.state.liConfirmData);
                    }
                    if (this.state.poConfirmData) {
                        console.log('this.state.poConfirmData=', this.state.poConfirmData);
                    }
                    console.log('ND13 submitCPConsnetConfirmationLI liConfirmData ', (this.state.liConfirmData ?? []));
                    console.log('ND13 submitCPConsnetConfirmationLI data ', data);
                    // let needLIConfirm = (this.state.liConfirmData ?? []).filter(item => !item?.needPOConfirm).map(this.processDataLIConfirm);

                    
                    this.state.liConfirmData = data;
                    
                     console.log('ND13 submitCPConsnetConfirmationLI liConfirmData AFTER', (this.state.liConfirmData ?? []));
                    let needLIConfirm = (this.state.liConfirmData ?? []).filter(item => !item?.needPOConfirm).map(this.processDataLIConfirm);
                    console.log('needLIConfirm=', needLIConfirm);
                    let needPOConfirm = [];
                    if (this.state.reviewNeedPOConfirm) {
                        needPOConfirm = (this.state?.poConfirmData ?? []);
                    }
                    let finalData = [...needLIConfirm];
                    if (isEmpty(finalData)) {
                        finalData = [...data];
                    }
                    let onlyPO = false;
                    if (!isEmpty(needPOConfirm)) {
                        if (isEmpty(needLIConfirm)) {
                            onlyPO = true;
                        }
                        finalData = [...needLIConfirm].concat(needPOConfirm);
                    }
                    if (type === 'Parent') {
                        onlyPO = true;
                    }
                    console.log('finalData', finalData);
                    this.props.submitClaimInfoConfirm(finalData, onlyPO);
                }

                // this.submitCPConsentConfirmationLIConfirm(finalData);
                return;
            }
        }
        const needLIConfirm = (data ?? []).filter(item => !item?.needPOConfirm).map(this.processDataLIConfirm);
        const needPOConfirm = (data ?? []).filter(item=> !!item?.needPOConfirm).map(this.processDataPOConfirm);
        const finalData = [...needLIConfirm].concat(needPOConfirm);
        this.submitCPConsentConfirmationLI(finalData, type);
    }

    processDataLIConfirm(data){
        return {...data };
    }
    processDataPOConfirm(data){
        return {...data
            , AnswerPurpose: data?.AnswerPurpose ?? "YY"
        };
    }

    processDataConfirm(data){
        return {...data
            , AnswerPurpose: "YY"
        };
    }

    submitCPConsentConfirmationLI(data, type) {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId,
                Company: COMPANY_KEY,
                ProcessType: this.state.proccessType,
                DeviceId: this.state.deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: this.state.clientId,
                ConsentSubmit: data,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    jsonState.isSubmitting = false;
                    // this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION, (jState)=>{
                    //     jState.isSubmitting = false; 
                    // });
                    if (type !== "Parent") {
                        jsonState.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                    } else {
                        if (this.props.doneClaim) {
                            this.props.doneClaim();
                        }
                    }
                    
                    console.log('savexxxx=',JSON.stringify(jsonState));
                    this.saveND13DataTemp(jsonState.clientId, jsonState.trackingId, JSON.stringify(jsonState), jsonState.apiToken, jsonState.deviceId);
                    this.setState(jsonState);
                } else if (Response.ErrLog === 'SubmitFailed') {
                    this.fetchCPConsentConfirmation(jsonState.trackingId, jsonState.clientId, jsonState.clientListStr, jsonState.proccessType, jsonState.apiToken, jsonState.deviceId);
                } else {
                    jsonState.isSubmitting = false;
                    this.setState(jsonState);
                }
                if (jsonState.proccessType === 'CCI') {
                    this.props.saveLocal();
                }
            })
            .catch(error => {
                // console.log(error);
                jsonState.isSubmitting = false;
                this.setState(jsonState);
            });
    }

    submitCPConsentConfirmationLIConfirm(data) {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId,
                Company: COMPANY_KEY,
                ProcessType: this.state.proccessType,
                DeviceId: this.state.deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: this.state.clientId,
                ConsentSubmit: data,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    jsonState.isSubmitting = false;
                    jsonState.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                    console.log('savexxxx=',JSON.stringify(jsonState));
                    this.saveND13DataTemp(jsonState.clientId, jsonState.trackingId, JSON.stringify(jsonState), jsonState.apiToken, jsonState.deviceId);
                    this.setState(jsonState);
                } else if (Response.ErrLog === 'SubmitFailed') {
                    this.fetchCPConsentConfirmation(jsonState.trackingId, jsonState.clientId, jsonState.clientListStr, jsonState.proccessType, jsonState.apiToken, jsonState.deviceId);
                } else {
                    jsonState.isSubmitting = false;
                    this.setState(jsonState);
                }
                if (jsonState.proccessType === 'CCI') {
                    this.props.saveLocal();
                }
            })
            .catch(error => {
                // console.log(error);
                jsonState.isSubmitting = false;
                this.setState(jsonState);
            });
    }


    submitCPConsentConfirmationPO(data) {
        if ((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || this.props.disableEdit) {
            if (!this.props.disableEdit) {
                this.setState({poConfirmData: data});
                let state = this.state;
                state.poConfirmData = data;
                if (this.haveLIStillNotAgree(this.state.clientProfile)) {
                    const updateInfoState = ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18;
                    state.updateInfoState = updateInfoState;
                    state.stepName = updateInfoState;
                    this.saveND13DataTemp(state.clientId, state.trackingId, JSON.stringify(state), state.apiToken, state.deviceId);
                } else {
                    let copyState = cloneDeep(state);
                    this.saveND13DataTemp(copyState.clientId, copyState.trackingId, JSON.stringify(copyState), copyState.apiToken, copyState.deviceId);
                    // this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
                    this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "", this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));//Truyền status empty sẽ keep current status
                    // this.setState({sendPOSuccess: true});
                }
            } else {//Role PO
                this.setState({reviewNeedPOConfirm: true});
                if (this.haveLIStillNotAgree(this.state.clientProfile) || ((!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo?.dOB)) || (!isOlderThan18(this.props.selectedCliInfo?.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList))))) {
                    console.log('this.state.poConfirmData=', this.state.poConfirmData);
                    this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18});
                } else {
                    const needPOConfirm = (this.state.poConfirmData ?? []);
                    const needLIConfirm = [];
                    if (!isEmpty(this.state.liConfirmData)) {
                        needLIConfirm = (this.state.liConfirmData ?? []).filter(item => !item?.needPOConfirm).map(this.processDataConfirm);
                    }
                    // alert('tracking=', this.props.trackingId);
                    if (isEmpty(needPOConfirm) && isEmpty(needLIConfirm)) {
                        // alert('ca 2 epty')
                        if (data) {
                            this.props.submitClaimInfoConfirm(data, true);
                        } else {
                            this.props.submitClaimInfo();
                        }
                        
                    } else if (!isEmpty(needPOConfirm) && !isEmpty(needLIConfirm)) {
                        const finalData = [...needLIConfirm].concat(needPOConfirm);
                        console.log('finalData', finalData);
                        // alert('x');
                        this.props.submitClaimInfoConfirm(finalData);
                    } else if (!isEmpty(needPOConfirm)) {
                        // alert('y');
                        this.props.submitClaimInfoConfirm(needPOConfirm, true);
                    } else if (!isEmpty(needLIConfirm)) {
                        // alert('z');
                        this.props.submitClaimInfoConfirm(needLIConfirm);
                    }
                    // this.submitConsentPOConfirm(this.state.poConfirmData);
                }
                
            }

            return;
        }
        this.submitConsentPO(data);

    }

    submitConsentPO = (data) => {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);
        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                // Action: "ReInstatementConfirm",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId,
                Company: COMPANY_KEY,
                ProcessType: this.props.proccessType ? this.props.proccessType : this.state.proccessType,
                DeviceId: this.state.deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: this.state.clientId,
                ConsentSubmit: data,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    jsonState.isSubmitting = false;
                    const polID = jsonState.polID;
                    this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION, (jState) => {
                        jState.isSubmitting = false;
                    });

                    if (this.checkPOAndLIEquality(getSession(CLIENT_ID), this.props.selectedCliInfo.clientId)) {
                        if (this.props.doneClaim) {
                            this.props.doneClaim();
                        }
                    } else {
                        this.fetchCPConsentConfirmation(jsonState.trackingId, jsonState.clientId, jsonState.clientListStr, jsonState.proccessType, jsonState.apiToken, jsonState.deviceId);
                    }
                    this.setState(jsonState);

                } else {
                    jsonState.isSubmitting = false;
                    this.setState(jsonState);
                }
                if (jsonState.proccessType === 'CCI') {
                    this.props.saveLocal();
                }
            })
            .catch(error => {
                // console.log(error);
                jsonState.isSubmitting = false;
                this.setState(jsonState);
            });
    }

    submitConsentPOConfirm = (data) => {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);
        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                // Action: "ReInstatementConfirm",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId,
                Company: COMPANY_KEY,
                ProcessType: this.props.proccessType ? this.props.proccessType : this.state.proccessType,
                DeviceId: this.state.deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: this.state.clientId,
                ConsentSubmit: data,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    console.log('Submit xong PO')
                } else {
                    jsonState.isSubmitting = false;
                    this.setState(jsonState);
                }
            })
            .catch(error => {
                // console.log(error);
                jsonState.isSubmitting = false;
                this.setState(jsonState);
            });
    }

    closePopupSucceeded(event) {
        if ((this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) || (this.closeSucceededButtonRef && this.closeSucceededButtonRef.contains(event.target))) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup";
            document.removeEventListener('mousedown', this.handlerClosePopupSucceeded);

            // removeLocal(this.getLocalKey(this.state.polID));
            this.deleteND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId);

            this.props.closeToHome();
        }
    }

    closePopupSucceededRedirect(event) {
        let from = fromNative;
        if (this.props.deleteLocal) {
            this.props.deleteLocal();
        }
        this.setState({showSuccess: false});
        if (!from) {
            if ((this.state.proccessType === 'CSA') || (this.state.proccessType === 'PRM') || (this.state.proccessType === 'FAP') || (this.state.proccessType === 'FPM') || (this.state.proccessType === 'SundryAmountS')) {
                window.location.href = '/update-policy-info';
            } else if ((this.state.proccessType === 'EML') || (this.state.proccessType === 'ADR')) {
                window.location.href = '/update-contact-info';
            } else if (this.state.proccessType === 'RIN') {
                window.location.href = '/reinstatement';
            } else if (this.state.proccessType === 'CCI') {
                setTimeout(() => {
                    window.location.href = '/update-personal-info';
                }, 100);
            } else {
                window.location.href = '/payment-contract';
            }
        } else {
            let from = fromNative;
            let obj = {
                Action: "END_ND13_" + this.state.proccessType,
                ClientID: this.state.clientId,
                PolicyNo: this.state.policyNo,
                TrackingID: this.state.trackingId
            };
            if (from && (from === "Android")) {//for Android
                if (window.AndroidAppCallback) {
                    window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                }
            } else if (from && (from === "IOS")) {//for IOS
                if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                    window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                }
            }
        }
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    setCloseSucceededButtonRef(node) {
        this.closeSucceededButtonRef = node;
    }

    //API save to server db
    saveND13DataTemp(clientID, dKey, dValue, apiToken, deviceId) {
        let request = {
            jsonDataInput: {
                Action: "SaveND13Data",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                DKey: dKey,
                DValue: dValue,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientID
            }
        };
		
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
					console.log("saveND13Data success!" + dKey);
                    console.log("dValue=" + dValue);
                } else {
                    console.log("saveND13Data error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
        if (this.props.setNd13State && dValue && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.props.disableEdit) {
            this.props.setNd13State(dValue)
        }
    }

    loadND13DataTemp(clientID, dKey, apiToken, deviceId, clientListStr, proccessType) {
        let request = {
            jsonDataInput: {
                Action: "GetND13Data",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                DKey: dKey,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientID
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
					console.log("getND13Data success!");
                    if (!isEmpty(Response.Message) && JSON.parse(Response.Message)) {
                        let jsonState = JSON.parse(Response.Message);
                        console.log('data lay len=', JSON.stringify(jsonState));
                        jsonState.deviceId = deviceId;
                        jsonState.apiToken = apiToken;
                        this.setState(jsonState);
                        if (jsonState.updateInfoState !== ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
                            this.setState(jsonState);
                            this.fetchCPConsentConfirmation(dKey, clientID, clientListStr, proccessType, apiToken, deviceId);
                        } /*else if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                            jsonState.updateInfoState = ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18;
                            this.setState(jsonState);
                            this.fetchCPConsentConfirmation(dKey, clientID, clientListStr, proccessType, apiToken, deviceId);
                        }*/
                    } else {
                        if (this.props.fromNoti) {
                            this.setState({linkExpired: true});
                        } else {
                            this.fetchCPConsentConfirmation(dKey, clientID, clientListStr, proccessType, apiToken, deviceId);
                        }
                    }
                } else {
                    console.log("getND13Data error!");
                    this.fetchCPConsentConfirmation(dKey, clientID, clientListStr, proccessType, apiToken, deviceId);
                    
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    deleteND13DataTemp(clientID, dKey, apiToken, deviceId) {
        removeSession(CACHE_TRACKING_ID);
        let request = {
            jsonDataInput: {
                Action: "DeleteND13Data",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                DKey: dKey,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientID
            }
        };
		
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
					console.log("DeleteND13Data success!");
                } else {
                    console.log("DeleteND13Data error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    genOtp = () => {
        //gen otp, email/phone get at backend
        let dcid = this.props.DCID?this.props.DCID: (getSession(DCID)?getSession(DCID): this.state.DCID);
        if (!dcid || (dcid === 'undefined')) {
            dcid = '';
        }
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTPV2',
                APIToken: this.props.apiToken?this.props.apiToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.props.clientId?this.props.clientId: this.state.clientId,
                DeviceId: this.props.deviceId?this.props.deviceId: this.state.deviceId,
                Note: NOTE_MAPPING[this.state.proccessType]?NOTE_MAPPING[this.state.proccessType]: 'VALID_OTP_SDK_ESUBMISSION',
                OS: OS,
                Project: 'mcp',
                UserLogin: this.props.clientId?this.props.clientId: this.state.clientId,
                DCID : dcid,
            }

        }
        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    if (response.Response?.ContactType && response.Response?.ContactType === 'PHONE') {
                        this.setState({showOtp: true, transactionId: response.Response.Message, minutes: 5, seconds: 0, phone: response.Response?.ContactValue});
                    } else if (response.Response?.ContactType && response.Response?.ContactType === 'EMAIL') {
                        this.setState({showOtp: true, transactionId: response.Response.Message, minutes: 5, seconds: 0, email: response.Response?.ContactValue});
                    } else {
                        this.setState({showOtp: true, transactionId: response.Response.Message, minutes: 5, seconds: 0, phone: this.props.phone});
                    }
                    this.startTimer();
                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    this.closeOtp();
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.closeOtp();
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    this.closeOtp();
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
                this.closeOtp();
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            });

    }

    startTimer = () => {
        myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            if (this.state.seconds === 0) {
                if (this.state.minutes === 0) {
                    clearInterval(myInterval)
                } else {
                    this.setState({minutes: this.state.minutes - 1, seconds: 59});
                }
            }
        }, 1000)
        return () => {
            clearInterval(myInterval);
        };

    }

    
    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: '', isSubmitting: false});
        if (this.state.appType === 'CLOSE') {
            if ((this.state.proccessType === 'EML') || (this.state.proccessType === 'ADR')) {
                if (this.props?.stepName) {
                    this.props?.handleSetStepName(3);//VERIFICATION
                }
            } else if ((this.state.proccessType === 'FAP') || (this.state.proccessType === 'FPM')) {
                if (this.props?.stepName) {
                    this.props?.handleSetStepName(this.props?.stepName - 1);//VERIFICATION
                }
            } 
        } else {
            if ((this.state.proccessType === 'FAP') || (this.state.proccessType === 'FPM')||(this.state.proccessType === 'EML') || (this.state.proccessType === 'ADR')) {
                this.triggerAppEndProcess();
            } 
        }
        if ((this.state.proccessType === 'CCI') || (this.state.proccessType === 'Claim')) {
            if (this.props.LifeInsuredID) {
                this.props?.handleSetStepName(PERSONAL_INFO_STATE.VERIFICATION);
            } else {
                let from = fromNative;
                if (from) {
                    let obj = {
                    Action: "BACK_ND13_" + this.state.proccessType,
                    ClientID: this.state.clientId,
                    PolicyNo: this.state.policyNo,
                    TrackingID: this.state.trackingId
                    };
                    if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                    } else if (from && (from === "IOS")) {//for IOS
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                    }
                } else {
                    window.location.href = this.props.callBackCancel;
                }
            }
            
        } 
    }

    triggerAppEndProcess=() => {
        let from = fromNative;
        let obj = {
            Action: "BACK_ND13_" + this.state.proccessType,
            ClientID: this.state.clientId,
            PolicyNo: this.state.policyNo,
            TrackingID: this.state.trackingId
        };
        if (from && (from === "Android")) {//for Android
            if (window.AndroidAppCallback) {
                window.AndroidAppCallback.postMessage(JSON.stringify(obj));
            }
        } else if (from && (from === "IOS")) {//for IOS
            if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
            }
        }
    }

    popupOtpSubmit = (OTP) => {
        this.submitProccessConfirm(OTP);
    }

    submitProccessConfirm = (OTP) => {
        this.setState({isSubmitting: true});
        let submitRequest = {
            jsonDataInput: {
              Company: COMPANY_KEY,
              Note: NOTE_MAPPING[this.state.proccessType],
              Action: CONFIRM_ACTION_MAPPING[this.state.proccessType]?CONFIRM_ACTION_MAPPING[this.state.proccessType]: 'SinglePSProcessConfirm',
              RequestTypeID: this.state.proccessType,
              APIToken: this.state.apiToken,
              Authentication: AUTHENTICATION,
              DeviceId: this.state.deviceId,
              OS: WEB_BROWSER_VERSION,
              Project: "mcp",
              TransactionID: this.props.trackingId?this.props.trackingId: this.state.trackingId,
              UserLogin: this.state.clientId,
              ClientID: this.state.clientId,
              PolicyNo: this.props.policyNo?this.props.policyNo: this.state.policyNo,
              OtpVerified: OTP,
              TransactionVerified: this.state.transactionId
            }
          }

          onlineRequestSubmitConfirm(submitRequest)
          .then(res => {
              if ((res.Response.Result === 'true') && (res.Response.ErrLog === ('Confirm ' + this.state.proccessType + ' is saved successfull.')) && res.Response.Message) {
                // upload images
                  console.log('submitProccessConfirm success', this.state.trackingId);
                  this.deleteND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId);
                  let from = fromNative;
                  //proccess nào đi thẳng từ app qua ND13 thì dùng state
                  if (((this.props.proccessType === 'PRM') || (this.state.proccessType === 'FAP') || (this.state.proccessType === 'FPM') || (this.props.proccessType === 'SundryAmountS')) && from) {
                        this.setState({showOtp: false, minutes: 0, seconds: 0, isSubmitting: false});
                        let obj = {
                            Action: "END_ND13_" + this.state.proccessType,
                            ClientID: this.state.clientId,
                            PolicyNo: this.state.policyNo,
                            TrackingID: this.state.trackingId
                        };
                        if (from && (from === "Android")) {//for Android
                            if (window.AndroidAppCallback) {
                                window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                            }
                        } else if (from && (from === "IOS")) {//for IOS
                            if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                                window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                            }
                        }
                    } else {
                        this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true, isSubmitting: false, errorMessage: ''});
                    }
               
              } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                  this.setState({ errorMessage: OTP_EXPIRED, isSubmitting: false });
              } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                //   this.setState({ showOtp: false, minutes: 0, seconds: 0, isSubmitting: false });
                  this.closeOtp();
                  document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
              } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                  this.setState({ errorMessage: OTP_INCORRECT, isSubmitting: false });
              } else {
                this.setState({ errorMessage: OTP_SYSTEM_ERROR, isSubmitting: false });
              }

          }).catch(error => {
        //   alert(error);
            this.setState({ errorMessage: OTP_SYSTEM_ERROR, isSubmitting: false });
        });

    }

    updateRelateModalData = (relateModalData) => {
        console.log('update relateModalData=', relateModalData);
        this.setState({relateModalData: relateModalData});
    }

    updateRelateModalDataPO = (relateModalDataPO) => {
        console.log('update relateModalDataPO=', relateModalDataPO);
        this.setState({relateModalDataPO: relateModalDataPO});
    }
    
    updateIsActive = (activeObj) => {
        console.log('updateIsActive=', activeObj);
        this.setState({isActive: activeObj});
    }

    updateClientProfile = (clientProfile) => {
        this.setState({clientProfile: clientProfile});
    }

    saveND13AndQuit = () => {
        let state = this.state;
        this.saveND13DataTemp(state.clientId, state.trackingId, JSON.stringify(state), state.apiToken, state.deviceId);
        if (this.props.saveAndQuit) {
            this.props.saveLocalAndQuit();
        } /*else if (this.props.saveLocalAndQuit) {
            this.props.saveLocalAndQuit();
        }*/
    }

    GoHome = () => {
        window.location.href = '/';
    }

    updateIsWaitConfirm = (value) => {
        waiting = value;
        console.log('updateIsWaitConfirm=', value);
        this.setState({isWaitConfirm: value});
    }

    setShowAskReEdit = (value) => {
        this.setState({ showAskReEdit: value });
    }

    calculateTotalInvoiceAmount(data) {
        let totalAmount = 0;
        if (!data || !data.facilityList) {
            return totalAmount;
        }
        // Check if facilityList exists and is an array
        if (data.facilityList && Array.isArray(data.facilityList)) {
            // Loop through each facility
            data.facilityList.forEach(facility => {
                // Only proceed if invoiceList exists and is an array
                if (facility.invoiceList && Array.isArray(facility.invoiceList)) {
                    // Loop through each invoice in the invoiceList
                    facility.invoiceList.forEach(invoice => {
                        // Check if InvoiceAmount is a valid number
                        const amount = parseFloat(invoice.InvoiceAmount);
                        if (!isNaN(amount)) {
                            // Add the valid amount to the total
                            totalAmount += amount;
                        }
                    });
                }
            });
        }

        return totalAmount;
    }

    render() {

        console.log('this.state.clientProfile=', this.state.clientProfile);
        console.log('this.state.configClientProfile=', this.state.configClientProfile);
        const updateIsExpired = (value) => {
            expire = value;
            this.setState({isWaitConfirm: value});
        }

        const handlerAdjust = () => {
            if (this.state.proccessType === 'Claim') {
                this.props.handlerAdjust();
                this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18 });
            } else if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
                this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18 });
            } 
        }
        const handlerBackToPrevStep= () => {
            isPayment = false;
            if (this.props.role === SDK_ROLE_AGENT) {
                if (this.state.updateInfoState > ND_13.ND13_INFO_CONFIRMATION) {
                    if (this.props.proccessType === 'Claim') {
                        if (this.state.poConfirmData) {
                            this.setState({ updateInfoState: this.state.updateInfoState - 1 });
                        } else {
                            this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
                        }
                    } else {
                        this.setState({updateInfoState: this.state.updateInfoState -1});
                    }

                } else {
                    if (this.props.proccessType === 'Claim') {
                        this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
                    } else {
                        this.props.handleSetStepName(PERSONAL_INFO_STATE.VERIFICATION);
                    }
                }
            } else {
                if (this.state.updateInfoState > ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18) {
                    this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18 });
                } else {
                    if (this.props.proccessType === 'Claim') {
                        this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
                    } else {
                        this.props.handleSetStepName(PERSONAL_INFO_STATE.VERIFICATION);
                    }
                }
            }
        }

        const handleCLoseExistLocalConfirm = () => {
            this.setState({...this.state, hasLocalData: false});
            this.deleteND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId);
        }

        const handleAgreeExistLocalConfirm = (polID) => {
            const keyLocal = this.getLocalKey(polID)
            getLocal(keyLocal).then(res=>{
                if(res && res.v){
                    const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
                    dataResponse.hasLocalData = false;
                    dataResponse.isSubmitting = false;
                    this.setState(dataResponse);
                }
            })
        }

        const ReInstamentVerify = () => {
            // document.getElementById('user-rule-popup').className = 'popup special user-rule-popup show';
            this.setState({reInstamentVerify: true});
        }

        const CloseReInstamentVerify = () => {
            this.setState({reInstamentVerify: false});
        }

        const cancelND13 = () => {
            if (this.props.deleteLocal) {
                this.props.deleteLocal();
            }
            if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.DELETE, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
            } else {
                this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.REJECT_CLAIM, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
            }
            let from = fromNative;
            if (!from) {
                removeLocal(UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.policyNo);
                setTimeout(() => {
                    if (this.props.closeToHome) {
                        this.props.closeToHome();
                    } else {
                        window.location.href = '/';
                    }
                }, 200);
            } else {
                let obj = {
                    Action: "CANCEL_ND13_" + this.state.proccessType,
                    ClientID: this.state.clientId,
                    PolicyNo: this.state.policyNo,
                    TrackingID: this.state.trackingId
                };
                if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                } else if (from && (from === "IOS")) {//for IOS
                    console.log('IOS callbackNavigateToPage...........');
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                }
            }
            this.deleteND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId);
            this.setState({isCancelRequest: false});
        }

        const closeSendPOSuccess = () => {
            this.setState({sendPOSuccess: false});
            let from = fromNative;
            if (from) {
                let obj = {
                    Action: "END_ND13_" + this.state.proccessType,
                    ClientID: this.state.clientId,
                    PolicyNo: this.state.policyNo,
                    TrackingID: this.state.trackingId
                };
                if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                } else if (from && (from === "IOS")) {//for IOS
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                }
            } else {
                window.location.href = this.props.callBackSuccess;
            }

        }

        const setShowAskReEdit=(value)=> {
            this.setState({showAskReEdit: value});
        }

        const askAgentReEdit=(event)=> {
            this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.REEDIT, SDK_ROLE_AGENT, "", this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));//Truyền status empty sẽ keep current status
            setShowAskReEdit(false);
        }

        const cancelRequest=()=> {
            this.props.handlerUpdateCloneClaimRequest(SDK_REQUEST_STATUS.REJECT_CLAIM, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
            setShowAskReEdit(false);
        }

        const notAgree = () => {
            // this.props.setCurrentState(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
            setShowRejectConfirm(true);
        }

        const setShowRejectConfirm = (value) => {
            this.setState({ showRejectConfirm: value });
        }

        const rejectClaim = () => {
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REJECT_CLAIM, SDK_ROLE_PO, "", true);//Truyền status empty sẽ keep current status
            setShowRejectConfirm(false);
            setShowThank(true);

        }

        const requestEdit = (event) => {
            // event.preventDefault();
            setShowRejectConfirm(false);

            let copyState = cloneDeep(this.state);
            copyState.updateInfoState = ND_13.NONE;
            copyState.showAskReEdit = false;
            copyState.showRejectConfirm = false;
            this.saveND13DataTemp(copyState.clientId, copyState.trackingId, JSON.stringify(copyState), copyState.apiToken, copyState.deviceId);
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REEDIT, SDK_ROLE_AGENT, "");//Truyền status empty sẽ keep current status
            setShowThank(true);
        }

        const setShowThank = (value) => {
            this.setState({ showThank: value });
            if (!value) {
                this.props.closeToHome();
            }
        }

        const closeLinkExpired = () => {
            this.setState({ linkExpired: false });
            let from = fromNative;
            if (from) {
                let obj = {
                    Action: "CANCEL_ND13_" + this.props.proccessType,
                    ClientID: this.props.clientId,
                    TrackingID: this.props.trackingId
                };
                if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                } else if (from && (from === "IOS")) {//for IOS
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                }
            }
        }

        const updateAcceptPolicy = (value) => {
            this.setState({ acceptPolicy: value });
        }

        console.log('waiting=', (this.state.isWaitConfirm || this.state.isExpired));
        console.log('waiting=' + this.state.isWaitConfirm + ', expire=' + this.state.isExpired);
        return (
            <>
                {this.state.linkExpired ?
                    (fromNative ? (
                        <ExpirePopup closePopup={() => closeLinkExpired()}
                            msg={'Liên kết với thông báo này không còn hiệu lực.'}
                            imgPath={FE_BASE_URL + '/img/icon/iconAlertTimeExpired.svg'}
                        />
                    ) : (<ND13Expire />)
                    )
                :
                <section class="sccontract-warpper submision" id="scrollAnchor">
                    <div class={getSession(IS_MOBILE)?"insurance padding-top0": "insurance padding-top32"}>
                            <div class={(this.state.proccessType === 'Claim')?(getSession(IS_MOBILE)?"heading padding-heading-mobile": "heading padding-heading-web max-height160"): "heading2 margin-top-50"}>
                {/* <main className="logined" id="main-id"> */}
                                {/*Progress bar */}
                                {((this.state.proccessType === 'CCI') || (this.state.proccessType === 'Claim')) && (this.state.updateInfoState >= ND_13.ND13_INFO_CONFIRMATION) &&
                                    // <div className={getSession(IS_MOBILE)?"heading__tab mobile": "heading__tab"} /*style={{zIndex: '180', position: 'fixed', paddingTop:'60px'}}*/>
                                    <div className={getUrlParameter("fromApp") ? "heading__tab mobile" : "heading__tab"}>
                                    <div className="step-container">
                                        <div className="step-wrapper" /*style={{background: '#f5f3f2'}}*/>
                                        <div className="step-btn-wrapper">
                                            {(waiting || expire) &&
                                            <div className="back-btn">
                                                <button>
                                                    <div className="svg-wrapper" onClick={() => handlerBackToPrevStep()}>
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
                                                    
                                                    {!getSession(IS_MOBILE) ? (
                                                        <span className="simple-brown" onClick={() => handlerBackToPrevStep()}>Quay lại</span>
                                                    ):(
                                                        <p style={{ textAlign: 'center', paddingLeft: '16px', minWidth: '250%', fontWeight: '700' }}>Tạo mới yêu cầu</p>
                                                    )}
                                                </button>
                                            </div>
                                            } 
                                            {/* <div className="save-wrap">
                                                <button className="back-text">Lưu</button>
                                            </div> */}
                                        </div>
                                        {this.props.proccessType === 'Claim'?(
                                        <div className="progress-bar">
                                            <div
                                                className={(this.state.stepName >= this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_TYPE)) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>1</span>
                                                </div>
                                                <p>Thông tin sự kiện</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= this.props.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD)) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>2</span>
                                                </div>
                                                <p>Phương thức thanh toán</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= this.props.handlerGetStep(CLAIM_STEPCODE.ATTACHMENT)) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>3</span>
                                                </div>
                                                <p>Kèm <br/>chứng từ</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= this.props.handlerGetStep(CLAIM_STEPCODE.SUBMIT)) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>4</span>
                                                </div>
                                                <p>Hoàn tất yêu cầu</p>
                                            </div>
                                        </div>
                                        ):(
                                        <div className="progress-bar" /*style={{background: '#f5f3f2'}}*/>
                                            <div className={(this.state.stepName >= PERSONAL_INFO_STATE.CHOOSE_LI) ? "step active" : "step"}>
                                            <div className="bullet">
                                                <span>1</span>
                                            </div>
                                            <p>Chọn <br/> khách hàng</p>
                                            </div>
                                            <div className={(this.state.stepName >= PERSONAL_INFO_STATE.UPDATE_INFO) ? "step active" : "step"}>
                                            <div className="bullet">
                                                <span>2</span>
                                            </div>
                                            <p>Nhập <br/> thông tin</p>
                                            </div>
                                            <div className={(this.state.stepName >= PERSONAL_INFO_STATE.ATTACHMENT) ? "step active" : "step"}>
                                            <div className="bullet">
                                                <span>3</span>
                                            </div>
                                            <p>Kèm <br />chứng từ</p>
                                            </div>
                                            <div className={(this.state.stepName >= PERSONAL_INFO_STATE.VERIFICATION) ? "step active" : "step"}>
                                            <div className="bullet">
                                                <span>4</span>
                                            </div>
                                            <p>Xác <br /> nhận</p>
                                            </div>
                                        </div>
                                        )}
                                        {(waiting || expire) &&
                                        // <div className="step-btn-save-quit">
                                        //     <div className='svg-wrapper' style={{cursor: 'pointer'}} >
                                        //         <button onClick={()=>this.saveND13AndQuit()}>
                                        //         <span className="simple-brown" 
                                        //                 >Lưu & Thoát</span>
                                        //         </button>
                                        //     </div>
                                        // </div>
                                        (
                                        !this.props.consultingViewRequest &&
                                        <div className="step-btn-save-quit">
                                            <div className='save-quit-wrapper' onClick={()=>this.saveND13AndQuit()}>
                                            <button>
                                                <span className="simple-brown" >Lưu & thoát</span>
                                            </button>
                                            </div>
                                        </div>
                                        )
                                        }
                                        </div>
                                        
                                    </div>
                                    </div>
                                }
                                </div>
                                
                                {getSession(IS_MOBILE) &&
                                <div className={(getUrlParameter("fromApp") === 'IOS')?'padding-top16 margin-top112': 'padding-top16 margin-top56'}></div>
                                }
                                
                                {this.props.systemGroupPermission?.[0]?.Role === 'AGENT' &&
                                    <div className='ndbh-info' style={{ marginTop: '20px' }}>
                                        <div style={{ display: 'flex' }}>
                                        <p>Tên NĐBH: </p>
                                        <p className='bold-text'>{this.props.selectedCliInfo?.fullName}</p>
                                        </div>
                                        <div style={{ display: 'flex' }}>
                                        <p>Quyền lợi: </p>
                                        <p className='bold-text'>{getBenifits(this.props.claimCheckedMap)}</p>
                                        </div>
                                    </div>
                                }

                                {/*START ND13*/}
                                {(this.state.updateInfoState === ND_13.ND13_INFO_CONFIRMATION) && (this.state.clientName || (this.props.proccessType === 'RIN') || (this.props.proccessType === 'Claim')) && <ND13POConfirm
                                    isSubmitting={this.state.isSubmitting}
                                    selectedCliInfo={this.props.selectedCliInfo}
                                    clientId={this.state.clientId}
                                    claimSubmissionState={this.state.updateInfoState}
                                    currentState={this.props.currentState}
                                    trackingId={this.state.trackingId}
                                    clientListStr={this.state.clientListStr}
                                    deviceId={this.state.deviceId}
                                    apiToken={this.state.apiToken}
                                    policyNo={this.state.policyNo}
                                    from={fromNative}
                                    appType={this.state.appType}
                                    proccessType={this.state.proccessType}
                                    clientName={this.state.clientName}
                                    disableEdit={this.props.disableEdit}
                                    agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                                    isPayment={this.props.isPayment}
                                    systemGroupPermission={this.props.systemGroupPermission}
                                    relateModalDataPO={this.state.relateModalDataPO}
                                    notAgree={()=>notAgree()}
                                    updateRelateModalDataPO={this.updateRelateModalDataPO}
                                    handlerBackToPrevSubmisionState={this.handlerBackToPrevSubmisionState}
                                    onCompletedND13={async (e) => {
                                        console.log('bbbbbbbb=', this.state.clientProfile);
                                        const consentSubmitData = !isEmpty(this.state.clientProfile) && this.state.clientProfile.filter((item) => item.Role === 'PO') ? this.state.clientProfile.filter((item) => item.Role === 'PO')[0] : [];
                                        console.log('PO consentSubmitData=', consentSubmitData);
                                        await this.submitCPConsentConfirmationPO(this.addRequestParamCPConsent([consentSubmitData], "PO", "YY"));
                                    }}/>}

                                {((this.state.updateInfoState === ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18) && (this.props.updateInfoState !== ND_13.ND13_INFO_FOLLOW_CONFIRMATION)) &&
                                    <ND13POContactInfoOver18
                                        clientProfile={this.state.clientProfile}
                                        contactInfo={this.props.liInfo}
                                        TrackingID={this.state.trackingId}
                                        clientIdString={this.state.clientListStr}
                                        clientId={this.state.clientId}
                                        deviceId={this.state.deviceId}
                                        trackingId={this.state.trackingId}
                                        clientListStr={this.state.clientListStr}
                                        apiToken={this.state.apiToken}
                                        policyNo={this.state.policyNo}
                                        from={fromNative}
                                        appType={this.state.appType}
                                        selectedCliInfo={this.props.selectedCliInfo}
                                        totalInvoiceAmount={this.calculateTotalInvoiceAmount(this.props.claimDetailState)}
                                        configClientProfile={this.state.configClientProfile}
                                        onClickCallBack={() => this.setState({isCancelRequest: true})}
                                        paymentMethodState={this.props.paymentMethodState}
                                        submissionState={this.props.submissionState}
                                        handlerUpdateMainState={this.props.handlerUpdateMainState}
                                        poConfirmingND13={this.state.poConfirmingND13}
                                        claimCheckedMap={this.props.claimCheckedMap}
                                        isSubmitting={this.state.isSubmitting}
                                        claimSubmissionState={this.state.updateInfoState}
                                        currentState={this.props.currentState}
                                        proccessType={this.state.proccessType}
                                        relateModalData={this.state.relateModalData}
                                        claimTypeList={this.props.claimTypeList}
                                        disableEdit={this.props.disableEdit}
                                        agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                                        systemGroupPermission={this.props.systemGroupPermission}
                                        isActive={this.state.isActive}
                                        haveShowPayment={this.props.haveShowPayment}
                                        isPayment={this.props.isPayment}
                                        setIsPayment={this.props.setIsPayment}
                                        setHaveShowPayment={this.props.setHaveShowPayment}
                                        updateRelateModalData={this.updateRelateModalData}
                                        updateIsActive={this.updateIsActive}
                                        callBackOnlyPayment={this.props.callBackOnlyPayment}
                                        handlerBackToPrevSubmisionState={this.handlerBackToPrevSubmisionState}
                                        handleSaveLocalAndQuit={this.props.handleSaveLocalAndQuit}
                                        notAgree={()=>notAgree()}
                                        updateAcceptPolicy={(v)=>updateAcceptPolicy(v)}
                                        acceptPolicy={this.state.acceptPolicy}
                                        consultingViewRequest={this.props.consultingViewRequest}
                                        onClickConfirmBtn={async (consentSubmitData, type) => {
                                            // Submit CP consent confirmation
                                            this.submitCPConsnetConfirmationLI(consentSubmitData, type);
                                        }}/>}

                                {this.state.isCancelRequest && <ND13CancelRequestConfirm
                                    onClickExtendBtn={() => {
                                        // clearRequest()
                                        cancelND13();
                                    }

                                    }
                                    onClickCallBack={() => this.setState({isCancelRequest: false})}
                                />}

                                {this.state.openPopupSuccessfulND13 && <PopupSuccessfulND13 onClose={() => {
                                    const jsonState = {...this.state};
                                    jsonState.openPopupSuccessfulND13 = false;
                                    jsonState.updateInfoState = ND_13.INIT;
                                    this.setState(jsonState);
                                    this.props.callBackConfirmation(ND_13.INIT);
                                    this.handlerGoToStep(ND_13.ND13_INFO_FOLLOW_CONFIRMATION);
                                }}/>}
                                {((this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) || (this.props.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION)) && (
                                    <ND13ContactFollowConfirmation
                                    handlerSubmitContact={this.handlerSubmitContact}
                                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                                    poID={this.state.polID}
                                    currentState={this.state.currentState}
                                    contactPersonInfo={this.state.contactState?.contactPersonInfo}
                                    handlerGoToStep={this.handlerGoToStep}
                                    callBackConfirmation={this.callBackConfirmation}
                                    callBackUpdateND13State={this.callBackUpdateND13State}
                                    closeToHome={this.props.closeToHome}
                                    trackingId={this.state.trackingId}
                                    claimId={this.state.claimId}
                                    updateInfoState={this.state.updateInfoState}
                                    onlyPayment={this.state.onlyPayment}
                                    callBackUpdateND13ClientProfile={this.callBackUpdateND13ClientProfile}
                                    callBackTrackingId={this.callBackTrackingId}
                                    callBackLIWating={this.callBackLIWating}
                                    liWating={this.state.liWating}
                                    saveState={this.saveState}
                                    cancelClaim={this.cancelClaim}
                                    clientListStr={this.state.clientListStr}
                                    clientId={this.state.clientId}
                                    deviceId={this.state.deviceId}
                                    apiToken={this.state.apiToken}
                                    policyNo={this.state.policyNo}
                                    genOtp={this.genOtp}
                                    from={fromNative}
                                    appType={this.state.appType}
                                    proccessType={this.state.proccessType}
                                    isActive={this.state.isActive}
                                    updateClientProfile={this.updateClientProfile}
                                    callBackUpdateND13StateRefresh={this.callBackUpdateND13StateRefresh}
                                    updateAllLIAgree = {this.props.updateAllLIAgree}
                                    updateExistLINotAgree = {this.props.updateExistLINotAgree}
                                    handlerAdjust = {()=>handlerAdjust()}
                                    updateIsWaitConfirm={this.handlerUpdateIsWaitConfirm}
                                    updateIsExpired={(v)=>updateIsExpired(v)}
                                    deleteLocal={this.props.deleteLocal}
                                    handlerConfirmCPConsent={this.props.handlerConfirmCPConsent}
                                    disableEdit={this.props.disableEdit}
                                    agentKeyInPOSelfEdit={this.props.agentKeyInPOSelfEdit}
                                    systemGroupPermission={this.props.systemGroupPermission}
                                    notAgree={()=>notAgree()}
                                    claimCheckedMap={this.props.claimCheckedMap}
                                    paymentMethodState={this.props.paymentMethodState}
                                    selectedCliInfo={this.props.selectedCliInfo}
                                    cancelRequest={()=>cancelRequest()}
                                    consultingViewRequest={this.props.consultingViewRequest}
                                />
                                )}
                                {/*END ND13*/}
                    </div>
                </section>
                }
                {/* </main> */}
                {/* {this.state.isCheckPermission && <AlertPopupClaim closePopup={() => closeNotAvailable()}
                                                                  msg={this.state.msgCheckPermission}
                                                                  imgPath={checkPermissionIcon}/>} */}


                {this.state.showOtp &&
                    <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={()=>this.startTimer}
                               closeOtp={this.closeOtp} errorMessage={this.state.errorMessage}
                               popupOtpSubmit={this.popupOtpSubmit} reGenOtp={this.genOtp}
                               maskPhone={maskPhone(this.state.phone)}
                               maskEmail={maskEmail(this.state.email)}
                               isSubmitting={this.state.isSubmitting}
                               />
                }
                {this.state.showThanks && (
                    this.props.proccessType === 'PRM'?(
                        <SuccessChangePaymodePopup closePopup={this.closeThanks} policyNo={this.props.policyNo}
                        screen={SCREENS.UPDATE_POLICY_INFO}/>
                    ): (
                        <ThanksPopup
                        msg={'Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Chúng tôi sẽ xử lý yêu cầu và thông báo kết quả trong thời gian sớm nhất'}
                        closeThanks={this.closeThanks}/>
                    )
                )
                }

                {/* Popup succeeded redirect */}
                {this.state.showSuccess && (
                    ((this.props.proccessType === 'PRM') || (this.props.proccessType === 'SundryAmountS')) ? (
                        <SuccessChangePaymodePopup closePopup={this.handlerClosePopupSucceededRedirect} policyNo={this.state.policyNo}
                            screen={SCREENS.UPDATE_POLICY_INFO} proccessType={this.props.proccessType}/>
                    ) : (
                        (this.props.proccessType === 'FPM')?(
                            <SuccessChangeFundRatePopup closePopup={this.handlerClosePopupSucceededRedirect} policyNo={this.state.policyNo}
                            screen={SCREENS.UPDATE_POLICY_INFO}/>
                        ):(
                        <div className="popup special envelop-confirm-popup show" id="popupSucceededRedirectND13 show">
                            <div className="popup__card">
                                <div className="envelop-confirm-card" ref={this.handlerSetWrapperSucceededRef}>
                                    <div className="envelopcard">
                                        <div className="envelop-content">
                                            <div className="envelop-content__header"
                                                onClick={this.handlerClosePopupSucceededRedirect}
                                            >
                                                <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" /></i>
                                            </div>
                                            <div className="envelop-content__body">
                                                <div>
                                                    <h4 className="popup-claim-submission-h4" style={{textAlign: 'center'}}>Gửi yêu cầu thành
                                                        công</h4>
                                                    <p>&nbsp;</p>
                                                    <p>Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Chúng tôi sẽ xử lý yêu cầu và thông báo kết quả trong thời gian sớm nhất.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="envelopcard_bg">
                                        <img src={FE_BASE_URL + "/img/envelop_nowhite.png"} alt="" />
                                    </div>
                                </div>
                            </div>
                            <div className="popupbg"></div>
                        </div>
                        )
                    )
                )

                }
                {this.state.openPopupWarningDecree13 && 
                <POWarningND13 
                    proccessType = {this.props.proccessType}
                    onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })}/>}
                {this.state.sendPOSuccess &&
                  <OkPopupTitle closePopup={()=>closeSendPOSuccess()} 
                  msg={'Thông tin sẽ được gửi đến BMBH qua thư điện tử, BMBH cần truy cập Dai-ichi Connect để xác nhận và gửi yêu cầu.Nếu BMBH không xác nhận trong vòng 24h, thì yêu cầu sẽ tự động hủy.'} 
                  title={'Đã gửi yêu cầu đến BMBH xác nhận'} 
                  imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                  agreeFunc={()=>closeSendPOSuccess()}
                  agreeText='Đóng' 
                  />
                }
                {this.state.showAskReEdit &&
                    <AgreeCancelPopup closePopup={() => setShowAskReEdit(false)} agreeFunc={(event) => askAgentReEdit(event)}
                        msg={'<p>Yêu cầu điều chỉnh thông tin của Quý khách sẽ được gửi đến Đại lý bảo hiểm thực hiện, hoặc Quý khách có thể hủy yêu cầu trực tuyến này và lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý gần nhất.</p>'}
                        imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} agreeText='Yêu cầu Đại lý BH điều chỉnh' notAgreeText='Hủy yêu cầu QLBH' notAgreeFunc={() => cancelRequest()} />}

                {this.state.showRejectConfirm &&
                    <AgreeCancelPopup closePopup={() => setShowRejectConfirm(false)} agreeFunc={(event) => requestEdit(event)}
                        msg={'<p>Yêu cầu điều chỉnh thông tin của Quý khách sẽ được gửi đến Đại lý bảo hiểm thực hiện, hoặc Quý khách có thể hủy yêu cầu trực tuyến này và lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý gần nhất.</p>'}
                        imgPath={FE_BASE_URL + '/img/popup/reject-confirm.svg'} agreeText='Yêu cầu Đại lý BH điều chỉnh' notAgreeText='Hủy yêu cầu QLBH' notAgreeFunc={() => rejectClaim()} />}
                {this.state.showThank &&
                    <OkPopupTitle closePopup={() => setShowThank(false)}
                        msg={'Cảm ơn Quý khách đã xác nhận thông tin'}
                        imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                        agreeFunc={() => setShowThank(false)}
                        agreeText='Đóng'
                    />
                }                        

            </>


        );

    }

}


export default ND13;