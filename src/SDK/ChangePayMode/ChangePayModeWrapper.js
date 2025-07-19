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
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    CHANGE_PAY_MODE_SAVE_LOCAL,
    ND_13,
    IS_MOBILE,
    SCREENS
} from '../../constants';
import {
    CPSaveLog,
    logoutSession,
    onlineRequestSubmit,
    CPGetPolicyInfoByPOLID
} from '../../util/APIUtils';
import LoadingIndicator from '../../common/LoadingIndicator2';
import {
    getDeviceId,
    getSession,
    showMessage, trackingEvent, setLocal, getLocal,
    removeLocal
} from '../../util/common';
import POWarningND13 from "../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ND13 from "../ND13";
import PaymodePolList from './PaymodePolList';
import PaymodeDetail from './PaymodeDetail';
import AlertPopupPhone from '../../components/AlertPopupPhone';
import SuccessChangePaymodePopup from '../../components/SuccessChangePaymodePopup';
import ErrorReInstamentSubmitted from '../../components/ErrorReInstamentSubmitted';

export const ProcessType = "PRM";

const ND13_CONFIRMING_STATUS = {
    NONE: '',
    NEED: '1',
    NO_NEED: '0'
}

class ChangePayModeWrapper extends Component {
    constructor(props) {
        super(props);

        this.state = {
            enabled: false,
            stepName: FUND_STATE.NONE,
            slectedStepName: FUND_STATE.NONE,
            polListProfile: null,
            polListLapse: null,
            toggle: false,
            noPhone: false,
            noEmail: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            noValidPolicy: false,
            showOtp: false,
            submitting: false,
            minutes: 0,
            seconds: 0,
            OTP: '',
            errorMessage: '',
            submitIn24: false,
            msg: '',
            imgPath: '',
            polID: '',
            loading: false,
            // acceptPolicy: false,
            showThanks: false,
            forThisOnly: null,
            noTwofa: false,
            otp: '',
            poConfirmingND13: ND13_CONFIRMING_STATUS.NEED, //NONE
            updateInfoState: ND_13.NONE, //NONE
            hasLocalData: false,
            openPopupWarningDecree13: false,
            polListPayMode: null,
            trackingId: this.props.trackingId, // ''
            clientListStr: this.props.clientListStr, 
            clientId: this.props.clientId,
            proccessType: this.props.proccessType,
            appType: this.props.appType,
            deviceId: this.props.deviceId,
            apiToken: this.props.apiToken,
            clientClass: this.props.clientClass,
            clientName: this.props.clientName,
            email: this.props.email,
            ClientProfile: null,
            PolicyLIName: '',
            Frequency: '',
            PolMPremAmt: '',
            IsChangeFrequency: true,
            noChangeFrequency: false,
            FrequencyCode: '',
            PolSndryAmt: '',
            IsDegrading: false,
            hideForAndroid: false
        }
        this.handlerUpdateMainState = this.updateMainState.bind(this);
        this.handlerUploadAttachment = this.uploadAttachment.bind(this);
        this.handlerDragOver = this.dragFileOver.bind(this);
        this.handlerDragLeave = this.dragFileLeave.bind(this);
        this.handlerDrop = this.dropFile.bind(this);
        this.handlerUpdateAttachmentList = this.updateAttachmentList.bind(this);
        this.handlerDeleteAttachment = this.deleteAttachment.bind(this);

        this.callBackUpdateND13State  = this.callBackUpdateND13State.bind(this);
        this.getLocalKey = this.getLocalKey.bind(this)
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerGoToStep = this.handlerGoToStep.bind(this);
        this.handlerPolListPayMode = this.updatePolListPayMode.bind(this);
        this.handlerHideForKeyBoardAndroid = this.hideForKeyBoardAndroid.bind(this);
        this.handlerUpdateTrackingId = this.updateTrackingId.bind(this);
    }

    handlerGoToStep(state){
        const current = this.state;
        current.updateInfoState = state;
        this.setState(current);
    }

    hideForKeyBoardAndroid(hide) {
        // alert('hideForKeyBoardAndroid=' + hide);
        this.setState({hideForAndroid: hide});
    }

    getLocalKey = (polID) => CHANGE_PAY_MODE_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + polID;

    closeThanks = () => {
        this.setState({showThanks: false});
    }

    startTimer = () => {
        let myInterval = setInterval(() => {
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
        this.setState({showOtp: false, minutes: 0, seconds: 0});
    }

    componentDidMount() {
        this.setState({stepName: FUND_STATE.CHOOSE_POLICY});
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);

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

    dragFileOver(htmlId, event) {
        event.preventDefault();
        document.getElementById(htmlId).className = "img-upload active";
    }

    dragFileLeave(htmlId, event) {
        event.preventDefault();
        document.getElementById(htmlId).className = "img-upload";
    }

    dropFile(event, updateAttachmentListByDrag) {
        event.preventDefault();
        const validateImage = ["image/jpeg", "image/jpg", "image/png"];
        const files = Object.values(event.dataTransfer.files);
        let promisedFiles = [];
        let haveInvalidFile = false;
        for (let file of files) {
            let fileSize = ((file.size / 1024) / 1024).toFixed(4);
            let filePromise = new Promise(resolve => {
                if (validateImage.includes(file.type) && fileSize <= 5) {
                    let reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onloadend = () => resolve(reader.result);
                } else {
                    haveInvalidFile = true;
                }
            });
            promisedFiles.push(filePromise);
        }
        if (haveInvalidFile) {
            this.setState({errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png không quá 5MB'});
        } else {
            this.setState({errorUpload: ''});
        }
        Promise.all(promisedFiles).then(promisedFiles => {
            // fileContents will be an array containing
            // the contents of the files, perform the
            // character replacements and other transformations
            // here as needed
            const jsonState = this.state;
            const arrAttData = promisedFiles.map(base64 => {
                return {imgData: base64}
            });
            const attachments = jsonState.attachmentState.attachmentList;
            if (attachments !== null && attachments !== undefined) {
                attachments.push(...arrAttData);
                this.updateState(jsonState);
            }
            event.target.value = null;
        });
    }

    uploadAttachment(event, updateAttachmentList) {
        const validateImage = ["image/jpeg", "image/jpg", "image/png"];
        let promisedFiles = [];
        let haveInvalidFile = false;
        for (const processingFile of event.target.files) {
            let fileSize = ((processingFile.size / 1024) / 1024).toFixed(4);
            let filePromise = new Promise(resolve => {
                if (validateImage.includes(processingFile.type) && fileSize <= 5) {
                    let reader = new FileReader();
                    reader.readAsDataURL(processingFile);
                    reader.onloadend = () => resolve(reader.result);
                } else {
                    haveInvalidFile = true;
                }
            });
            promisedFiles.push(filePromise);
        }
        if (haveInvalidFile) {
            this.setState({errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png không quá 5MB'});
        } else {
            this.setState({errorUpload: ''});
        }
        Promise.all(promisedFiles).then(promisedFiles => {
            const arrAttData = promisedFiles.map(base64 => {
                return {imgData: base64}
            });
            const jsonState = this.state;
            const attachments = jsonState.attachmentState.attachmentList;

            if (attachments !== null && attachments !== undefined) {
                attachments.push(...arrAttData);
                this.updateState(jsonState);
            }
            event.target.value = null;
        });
    }

    updateAttachmentList(event, val) {
        const jsonState = this.state;
        const attachments = jsonState.attachmentState.attachmentList;

        if (attachments !== null && attachments !== undefined) {
            attachments.push({imgData: val});
            this.updateState(jsonState);
        }
        event.target.value = null;
    }

    deleteAttachment(attachmentIndex) {
        const jsonState = this.state;
        const attachments = jsonState.attachmentState.attachmentList;
        attachments.splice(attachmentIndex, 1);
        jsonState.attachmentState.attachmentList = attachments;
        this.updateState(jsonState);
    }

    updateTrackingId(value) {
        this.setState({trackingId: value});
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

    addRequestParamCPConsent(records, role, answerPurpose) {
        console.log('addRequestParamCPConsent',this.state);
        records.forEach(record => {
            if (record) {
                if (record.Role === role) {
                    record.AnswerPurpose = answerPurpose;
                }
                record.RequesterID = this.state.clientId;
                record.TrackingID = this.state.trackingId;
                record.RelationShip = "Self";
            }

        });

        return records;
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

    callAPIChangeFreqByPolicyID(PolicyId) {
        var jsonState = this.state;
        const apiRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                DeviceId: this.state.deviceId,
                APIToken: this.state.apiToken,
                Action: 'PolicyChangeFreq',
                OS: WEB_BROWSER_VERSION,
                UserID: this.state.clientId,
                PolID: PolicyId,
                ClientID: this.state.clientId,
                UserLogin: this.state.clientId
            }
        };
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            let Response = Res.Response;
            // jsonState.SearchResult = true;
            // jsonState.loadingpaymentDetai = true;
            if (Response.ErrLog && Response.ErrLog === 'SUCCESSFUL') {
                if (Res.Response.ClientProfile) {
                    jsonState.ClientProfile = Response.ClientProfile;
                    jsonState.ClientProfile.sort((a, b) => {
                        return b.FrequencyCode.localeCompare(a.FrequencyCode);
                    });
                }
                jsonState.stepName = FUND_STATE.UPDATE_INFO;
                jsonState.submitIn24 = false;
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
            } else {
                jsonState.submitIn24 = false;
                jsonState.ClientProfile = null;
                jsonState.noChangeFrequency = true;
                this.setState(jsonState);
            }
        }).catch(error => {

        });
    }

    setStepName = (stepName) => {
        this.setState({stepName: stepName});
    }

    updatePolListPayMode = (profile) => {
        this.setState({polListPayMode: profile});
    }

    render() {
        console.log(this.state.updateInfoState + '|' + ND_13.ND13_INFO_CONFIRMATION);
        console.log((this.state.updateInfoState !== ND_13.ND13_INFO_CONFIRMATION));
        console.log('---------------updateInfoState=', this.state.updateInfoState);
        const handlerBackToPrevStep = () => {
            if (this.state.stepName === FUND_STATE.UPDATE_INFO) {
                this.setState({stepName: this.state.stepName - 1, enabled: true});
            } else {
                if ((this.state.stepName === FUND_STATE.CHOOSE_POLICY) && (this.props.appType !== 'CLOSE')) {
                    let from = this.props.from;
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
                    this.setState({stepName: this.state.stepName - 1, enabled: false});
                    if (this.state.stepName === FUND_STATE.CHOOSE_POLICY) {
                        // window.location.href = '/update-policy-info';
                        this.props.handlerResetState();
                    }
                }
                
            }
        }

        const closePopupError = () => {
            this.setState({
                noChangeFrequency: false
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

        const closeSubmitIn24 = () => {
            this.setState({submitIn24: false});
        }

        const showCardInfo = (polID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, forceContinue) => {
            const jsonState = this.state;
            jsonState.polID = polID;
            jsonState.PolicyLIName = PolicyLIName;
            jsonState.Frequency = Frequency;
            jsonState.PolMPremAmt = PolMPremAmt;
            jsonState.IsChangeFrequency = IsChangeFrequency;
            jsonState.PolicyClassCD = PolicyClassCD;
            jsonState.FrequencyCode = FrequencyCode;
            jsonState.PolSndryAmt = PolSndryAmt;
            jsonState.IsDegrading = IsDegrading;
            jsonState.isEmpty = false;
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            this.setState(jsonState);

        }


        const ChangeFreq = () => {
            if (!this.state.IsChangeFrequency) {
                this.setState({noChangeFrequency: true});
                return;
            }
            const jsonRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "CheckRequestSubmit",
                    APIToken: this.props.apiToken,
                    Authentication: AUTHENTICATION,
                    DeviceId: this.props.deviceId,
                    OS: "Web",
                    Project: "mcp",
                    ClientID: this.props.clientId,
                    UserLogin: this.props.clientId,
                    RequestTypeID: this.props.proccessType,
                    PolicyNo: this.state.polID,
                    FromSystem: "DCW"
                }
            };
            onlineRequestSubmit(jsonRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    this.setState({ submitIn24: true});
                    return;
                } else {
                    this.callAPIChangeFreqByPolicyID(this.state.polID);
                }

            }).catch(error => {
                console.log(error);
            });

            
        }

        const ForceContinue = () => {
            closeSubmitIn24();
            this.callAPIChangeFreqByPolicyID(this.state.polID);
        }

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

  
        let msg = '';
        let imgPath = '';
        if (!this.state.enabled) {
            msg = 'Vui lòng chọn loại thông tin cần thay đổi ở bên trái.';
            imgPath = 'img/icon/empty.svg';
        }

      
        return (
            <>
                <LoadingIndicator area="policyList-by-cliID"/>
                {(this.state.noValidPolicy) ? (
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
                ) : (
                <div className="sccontract-container" style={{backgroundColor: '#f5f3f2'}}>
                    <LoadingIndicator area="zip-code-list"/>
                    <div className="insurance" style={{background: '#F5F3F2'}}>
                        <div className='heading' style={{
                            filter: 'drop-shadow(0px 4px 0px 8px rgba(0, 0, 0, 0.125))',
                            paddingBottom: '0'
                        }}>
                            {!getSession(IS_MOBILE) &&
                            <div className="breadcrums">
                                <div className="breadcrums__item">
                                    <p>Giao dịch hợp đồng</p>
                                    <p className='breadcrums__item_arrow'>{">"}</p>
                                </div>
                                <div className="breadcrums__item">
                                    <p>Điều chỉnh thông tin hợp đồng</p>
                                    <p className='breadcrums__item_arrow'>{">"}</p>
                                </div>
                            </div>
                            }
                            {!getSession(IS_MOBILE) &&
                                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                    <p>Chọn thông tin</p>
                                    <i><img src="../img/icon/return_option.svg" alt=""/></i>
                                </div>
                            }
                            <LoadingIndicator area="policyList-by-cliID"/>
                            {!this.state.hideForAndroid &&
                            <div className="heading__tab e-claim">
                                <div className="step-container">
                                    <div className="step-wrapper" style={{marginLeft: '16px'}}>
                                        {((this.state.stepName >= FUND_STATE.CHOOSE_POLICY) && (this.state.stepName < FUND_STATE.SDK)) && 
                                            <div className="step-btn-wrapper">
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
                                                            <p style={{textAlign: 'center', minWidth: '300%', fontWeight: '700'}}>Tạo mới yêu cầu</p>
                                                        )}
                                                        
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
                                                <p>Loại thông tin</p>
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
                                        </div>
                                    </div>
                                </div>
                            </div>
                            }
                        </div>
                    </div>
                    {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                        this.state.deviceId && 
                        <PaymodePolList 
                        polListPayMode={this.state.polListPayMode} 
                        polID={this.state.polID} 
                        clientId={this.state.clientId}
                        proccessType={this.state.proccessType}
                        deviceId={this.state.deviceId}
                        apiToken={this.state.apiToken}
                        appType={this.state.appType}
                        from={this.props.from}
                        handlerPolListPayMode={this.handlerPolListPayMode}
                        handlerUpdateNoValidPolicy={this.props.handlerUpdateNoValidPolicy}
                        showCardInfo={showCardInfo} goBack={goBack}/>

                    ) : (
                        ((this.state.stepName === FUND_STATE.UPDATE_INFO) || (this.state.stepName === FUND_STATE.VERIFICATION) || (this.state.stepName === FUND_STATE.SDK)) && (
                            this.state.ClientProfile &&
                            <PaymodeDetail 
                                stepName={this.state.stepName} 
                                setStepName={this.setStepName} 
                                polID={this.state.polID} 
                                PolicyLIName= {this.state.PolicyLIName} 
                                Frequency= {this.state.Frequency} 
                                PolMPremAmt= {this.state.PolMPremAmt} 
                                ClientProfile={this.state.ClientProfile}
                                clientId={this.props.clientId}
                                proccessType={this.props.proccessType}
                                deviceId={this.props.deviceId}
                                clientClass={this.props.clientClass}
                                clientName={this.props.clientName}
                                email={this.props.email}
                                PolicyClassCD={this.state.PolicyClassCD}
                                apiToken={this.props.apiToken}
                                FrequencyCode={this.state.FrequencyCode}
                                PolSndryAmt={this.state.PolSndryAmt}
                                IsDegrading={this.state.IsDegrading}
                                DCID={this.state.DCID?this.state.DCID: this.props.DCID}
                                appType={this.state.appType}
                                phone={this.props.phone}
                                from={this.props.from}
                                handlerHideForKeyBoardAndroid={this.handlerHideForKeyBoardAndroid}
                                handlerUpdateTrackingId={this.handlerUpdateTrackingId}
                                // acceptPolicy={this.state.acceptPolicy}
                            />
                        ) 
                    )}

                    {this.state.stepName === FUND_STATE.CHOOSE_POLICY && (
                        this.state?.polListPayMode?.[0] && !this.state?.polListPayMode?.[0]?.IsDegrading ? (
                            <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                <div className="bottom-text"
                                        style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                    <p style={{textAlign: 'justify'}}>
                                        <span className="red-text basic-bold">Lưu ý: </span><span
                                        style={{color: '#727272'}}>
                                        Yêu cầu này chỉ áp dụng với Hợp đồng đang hiệu lực và ứng dụng chưa hỗ trợ yêu cầu thay đổi định kỳ đóng phí cho hợp đồng đang có định kỳ đóng phí là “Năm”.
                                        </span>
                                    </p>
                                </div>
                            </div>
                        ): (
                            <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'left', alignItems: 'left'}}>
                                <div className="bottom-text"
                                        style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                    <p style={{textAlign: 'justify'}}>
                                        <span className="red-text basic-bold">Lưu ý: </span><span
                                        style={{color: '#727272'}}>
                                        Yêu cầu này chỉ áp dụng với Hợp đồng đang hiệu lực.
                                        </span>
                                    </p>
                                </div>
                            </div>
                        ))
                    }
                    {getSession(IS_MOBILE)&& !this.state.hideForAndroid &&
                        <div className='nd13-padding-bottom32'></div>
                    }
                    {
                        this.state.stepName === FUND_STATE.CHOOSE_POLICY &&
                        <div className="bottom-btn" style={{maxWidth: '578px', paddingBottom: '36px'}}>
                                {this.state.polID ? (
                                    <button className="btn btn-primary" onClick={() => {
                                        this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`);
                                        trackingEvent(
                                            "Giao dịch hợp đồng",
                                            `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`,
                                            `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`,
                                        );
                                        // getFundList();
                                        ChangeFreq();
                                    }}>Tiếp
                                        tục</button>
                                ) : (
                                    <button className="btn btn-primary disabled">Tiếp tục</button>
                                )}

                        </div>

                    }
                    {this.state.appType && (this.state.appType !== 'CLOSE') && this.state.polID && this.state.apiToken && this.state.clientName && (this.state.stepName === FUND_STATE.SDK) && 
                    <ND13
                                appType={this.state.appType}
                                trackingId={this.state.trackingId}
                                clientListStr={this.props.clientListStr?this.props.clientListStr:getSession(CLIENT_ID)}
                                clientId={this.props.clientId}
                                proccessType={this.state.proccessType}
                                deviceId={this.props.deviceId}
                                apiToken={this.props.apiToken}
                                policyNo={this.state.polID}
                                polID={this.state.polID}
                                clientName={this.state.clientName}
                    />}

                    {/*END ND13*/}
                </div>)}

                {this.state.showThanks && (
                    <SuccessChangePaymodePopup closePopup={this.closeThanks} policyNo={this.state.polID}
                                                        screen={SCREENS.UPDATE_POLICY_INFO}/>
                )
                }

                {this.state.noChangeFrequency &&
                    <AlertPopupPhone closePopup={closePopupError} msg={'Hợp đồng này hiện chưa thỏa điều kiện để yêu cầu thay đổi định kỳ đóng phí tại Dai-ichi Connect. Quý khách vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.'} imgPath={FE_BASE_URL + '/img/popup/no-phone.svg'}
                         />
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
                                <img src={FE_BASE_URL + "/img/envelop_nowhite.png"} alt=""/>
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
                {this.state.openPopupWarningDecree13 && <POWarningND13 proccessType={this.state.proccessType} onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })}/>}
                {this.state.submitIn24 &&
                <ErrorReInstamentSubmitted closePopup={() => closeSubmitIn24()} msg={'Hợp đồng này đã có yêu cầu thay đổi định kỳ đóng phí. Quý khách có muốn tiếp tục tạo yêu cầu không?'} imgPath={'../../img/popup/submited-in-24.svg'}
                                            screen={''}
                                            forceContinue={() => ForceContinue()}/>
                }
            </>


        );

    }

}


export default ChangePayModeWrapper;