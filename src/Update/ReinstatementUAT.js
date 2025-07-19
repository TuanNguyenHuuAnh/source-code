import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLASSPO,
    CLASSPOREVERSEMAP,
    CLIENT_ID,
    CLIENT_PROFILE,
    COMPANY_KEY,
    DCID,
    EMAIL,
    EXPAND_ID_LAPSE_LIST,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FULL_NAME,
    FUND_FROM_LIMIT,
    FUND_STATE,
    FUND_TO_LIMIT,
    OS,
    OTP_EXPIRED,
    OTP_INCORRECT,
    PAGE_POLICY_PAYMENT, PAGE_REGULATIONS_PAYMENT, PageScreen,
    POL_LIST_IL_CLIENT,
    POL_LIST_LAPSE,
    SCREENS,
    SUBMIT_IN_24_RIN,
    RIN_DRAFT,
    TWOFA,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    WEB_BROWSER_VERSION,
    ConsentStatus,
    UPDATE_POLICY_INFO_SAVE_LOCAL,
    COMPANY_KEY2, RELOAD_BELL, DOB, POID,
    NUM_OF_MANUAL_RETRY,
    POLICY_NO,
    CLIENT_CLASS,
    ND_13
} from '../constants';
import {Redirect, withRouter} from 'react-router-dom';
import './UpdateContactInfo.css';
import {
    CPGetPolicyInfoByPOLID,
    CPGetPolicyListByCLIID, CPSaveLog,
    genOTP,
    GetConfiguration,
    logoutSession,
    onlineRequestSubmit,
    verifyOTP,
    CPConsentConfirmation
} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import {
    formatFullName,
    getCurrentDate,
    getDeviceId,
    getSession,
    isInteger,
    maskPhone,
    setSession,
    showMessage, trackingEvent, setLocal, getLocal,
    removeLocal,
    removeSession,
    deleteND13DataTemp
} from '../util/common';
import DOTPInput from '../components/DOTPInput';
import AlertPopup from '../components/AlertPopup';
import AlertPopupPhone from '../components/AlertPopupPhone';
import AlertPopupOriginal from '../components/AlertPopupOriginal';
import AlertPercentError from '../components/AlertPercentError';
import NoticeChangeFundPopup from '../components/NoticeChangeFundPopup';
import NoticeChangeRatePopup from '../components/NoticeChangeRatePopup';
import SuccessChangeFundRatePopup from '../components/SuccessChangeFundRatePopup';
import SuccessReInstamentPopup from '../components/SuccessReInstamentPopup';
import NumberFormat from 'react-number-format';
import AuthenticationPopup from '../components/AuthenticationPopup';
import {FundModel} from '../model/FundModel';
import ThanksPopup from '../components/ThanksPopup';
import UserAgree from '../components/UserAgree';
import AlertPopupError from '../components/AlertPopupError';
import ErrorReInstamentSubmitted from '../components/ErrorReInstamentSubmitted';
import {Helmet} from "react-helmet";
import {isEmpty} from "lodash";
import AlertPopupClaim from "../components/AlertPopupClaim";
import checkPermissionIcon from '../img/popup/check-permission.png';
import ImageViewerBase64 from "../Followup/ImageViewerBase64";
import POWarningND13 from "../SDK/ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ND13 from "../SDK/ND13";
import ConfirmChangePopup from '../components/ConfirmChangePopup';
import parse from 'html-react-parser';
import AES256 from 'aes-everywhere';
import ReloadScreen from '../components/ReloadScreen';
import ChangePayMode from '../SDK/ChangePayMode/ChangePayMode';
import ConfirmPopup from '../components/ConfirmPopup';
import DecreaseSACancelRider from '../SDK/DecreaseSACancelRider/DecreaseSACancelRider';
import ChangePayment from '../SDK/ChangePayment/ChangePayment';

let allFunds = ["ILPF1", "ILPF2", "ILPF3", "ILPF6", "ILPF7"];
let allFundsMap = {
    "ILPF1": "Quỹ Tăng trưởng",
    "ILPF2": "Quỹ Phát triển",
    "ILPF3": "Quỹ Bảo toàn",
    "ILPF6": "Quỹ Dẫn đầu",
    "ILPF7": "Quỹ Tài chính năng động"
};


export const ProcessType = "RIN";

const OVER_LAPSE_YEAR = 1; 

const ND13_CONFIRMING_STATUS = {
    NONE: '',
    NEED: '1',
    NO_NEED: '0'
}

export const INITIAL_STATE_UPDATE_POLICY_INFO = () => {
    return {
            jsonInput: {
                jsonDataInput: {
                    ClientID: getSession(CLIENT_ID)
                }
            },
            updateFund: false,
            updateInvestRate: false,
            updatePeriodicFee: false,
            updateReinstatement: false,
            insuranceFee: false,
            decreaseSACancelRider: false,
            updateLoan: false,
            enabled: false,
            clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
            stepName: FUND_STATE.CATEGORY_INFO,
            slectedStepName: FUND_STATE.NONE,
            polListProfile: null,
            polListLapse: null,
            toggle: false,
            noPhone: false,
            noEmail: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            noValidPolicy: false,
            invalidFund: false,
            percentError: false,
            totalToFundError: false,
            totalFundCalInTempError: false,
            showOtp: false,
            submitting: false,
            isPolicyLapse: false,
            minutes: 0,
            seconds: 0,
            OTP: '',
            errorMessage: '',
            submitIn24: false,
            msgPopup: '',
            popupImgPath: '',
            msg: '',
            imgPath: '',
            selectedFundType: '',
            PolicyInfo: null,
            polID: '',
            lastChosenPolID: '',
            showFundDetails: false,
            showFundDetailsMap: {},
            showNotice: false,
            currentFunds: [],
            chosenFunds: {},
            remainFunds: ["ILPF1", "ILPF2", "ILPF3", "ILPF6", "ILPF7"],
            currentFundsMap: {},
            currentFundsChangeMap: {},
            currentFundsExpandMap: {},
            currentFundsExchangeItemsMap: {},
            remainFundsItems: [],
            loading: false,
            exId: 0,
            toggleRender: false,
            acceptPolicy: false,
            noChangeInvestRate: false,
            showThanks: false,
            forThisOnly: null,
            noTwofa: false,
            insuredLapseList: null,
            expandIdLapseList: getSession(EXPAND_ID_LAPSE_LIST) ? JSON.parse(getSession(EXPAND_ID_LAPSE_LIST)) : [],
            countDayLapse: "0",
            yearLapse: "0",
            polExpiryDate: "",
            AnsweredListMap: {},
            AnsweredValueListMap: {},
            AnsweredErrorListMap: {},
            uploadSelected: null,
            errorUpload: "",
            reInstamentVerify: false,
            tempFee: '0',
            apiError: false,
            lapseAboveOneYear: false,
            polLapseSelected: null,
            isCheckPermission: false,
            msgCheckPermission: '',
            attachmentState: {
                previewVisible: false,
                previewImage: "",
                previewTitle: "",
                attachmentList: [],
                disabledButton: true,
            },
            otp: '',
            poConfirmingND13: ND13_CONFIRMING_STATUS.NEED, //NONE
            updateInfoState: ND_13.NONE, //NONE
            trackingId: '', // ''
            hasLocalData: false,
            clientListStr: '', // undefined
            isNonePaymentList : false,
            isReloadPaymentList: false,
            countRetry: 0,
            isRetrying: false,
            timeOut: true,
            appType: '',
            proccessType: '',
            showOtpRIN: false,
            transactionId: '1',
            openPopupWarningDecree13: false,
            polListPayMode: null,
            showConfirmClear: false,
            toggleBack: false,
            allLIAgree: false,
            aLINotAgree: false,
            notiLinkExpired: false
        }
    };

class Reinstatement extends Component {
    constructor(props) {
        super(props);

        this.state = INITIAL_STATE_UPDATE_POLICY_INFO();
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
        this.submitCPConsnetConfirmationLI = this.submitCPConsnetConfirmationLI.bind(this);
        this.handlerGoToStep = this.handlerGoToStep.bind(this);
        this.updateAllLIAgree = this.updateAllLIAgree.bind(this);
        this.updateExistLINotAgree = this.updateExistLINotAgree.bind(this);
        this.handlerAdjust = this.handlerAdjust.bind(this);
        this.handlerUpdateNoValidPolicy = this.updateNoValidPolicy.bind(this);
        this.handlerResetState = this.resetState.bind(this);
    }

    handlerGoToStep(state){
        const current = this.state;
        current.updateInfoState = state;
        this.setState(current);
    }

    updateAllLIAgree(value){
        this.setState({allLIAgree: value});
    }

    updateExistLINotAgree(value){
        this.setState({aLINotAgree: value});
    }

    // componentDidUpdate(prevProps, prevState) {
    //     // TODO: when the task related to 'ND 13' has done, this action will be removed.
    //     // It's invoked in some specially step like ND_13.ND13_INFO_FOLLOW_CONFIRMATION or VERIFICATION
    //     const listeningState = ['poConfirmingND13', 'trackingId', 'updateInfoState','insuredLapseList', 'clientListStr'];
    //     const showLog = this.isStateChange(prevState,listeningState);
    //     if(showLog){
    //         this.logStateChange(listeningState)
    //     }
    //     // setLocal(this.getLocalKey(this.state.polID), JSON.stringify(this.state));
    // }




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

    fetchCheckConfigPermission() {
        let request = {
            jsonDataInput: {
                Action: 'GetConfigPermission',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                Project: 'mcp',
                DCID: getSession(DCID),
                FunctionID: '6',
            }
        }
        GetConfiguration(request).then(res => {
            let Response = res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                if (!isEmpty(Response.ClientProfile)) {
                    // console.log('Response', Response);

                    this.setState({
                        isCheckPermission: Response.ClientProfile[0]?.isMaintain === '1',
                        msgCheckPermission: Response.ClientProfile[0]?.Message,
                    })
                }
            }
        }).catch(error => {
            console.log(error);
        });
    }

    directToND13Contact = () => {
        //remove otp and move to ND13_FOLLOWING_CONFIRMATION screen
        this.submitReInstament();
    }



    genOtp = () => {
        //gen otp
        let note = 'VALID_OTP';
        if (this.state.updateFund === true) {
            note = 'VALID_OTP_MOVE_FUND';
        } else if (this.state.updateInvestRate === true) {
            note = 'VALID_OTP_CHANGE_RATE_FUND';
        } else if (this.state.updateReinstatement === true) {
            note = 'VALID_OTP_REINSTATEMENT';
        }
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                CellPhone: getSession(CELL_PHONE),
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: note,
                OS: OS,
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }

        }

        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({showOtp: true, transactionId: response.Response.Message, minutes: 5, seconds: 0});
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


    popupOtpSubmit = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: this.state.note,
                OS: '',
                OTP: OTP,
                Project: 'mcp',
                TransactionID: this.state.transactionId,
                UserLogin: getSession(USER_LOGIN)
            }

        }

        verifyOTP(verifyOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    console.log('verifyOTP success')
                    this.setState({...this.state, showOtp: false, minutes: 0, seconds: 0, errorMessage: '', otp: OTP});
                    if (this.state.updateFund) {
                        // alert('complete move fund');
                    } else if (this.state.updateInvestRate) {
                        // alert('complete change rate');
                        let reqType = 'FAP';
                        if (this.state.forThisOnly) {
                            reqType = 'FPM';
                        }
                        let jsonState = this.state;
                        let newItems = [];
                        let oldItems = [];
                        for (let i = 0; i < allFunds.length; i++) {
                            let fundOriginal = jsonState.currentFundsMap[allFunds[i]];
                            let fundChange = jsonState.currentFundsChangeMap[allFunds[i]];


                            let fundItem = null;
                            let oldFundItem = null;
                            if (fundChange) {
                                fundItem = {
                                    Action: 'Update',
                                    fund_id: allFunds[i],
                                    FundNameCode: allFundsMap[allFunds[i]],
                                    fund_val_each_funf: fundChange.value,
                                    NewRate: (fundChange.percent === '') ? 0 : fundChange.percent,
                                    OldRate: fundOriginal.percent,
                                    Order: '' + (i + 1)
                                }
                                oldFundItem = {
                                    FundNameCode: allFundsMap[allFunds[i]],
                                    cdi_alloc_pct: fundOriginal.percent,
                                    Order: '' + (i + 1)
                                }
                            } else {
                                let indx = this.findFundIdInFundArray(allFunds[i], jsonState.remainFundsItems);
                                if (indx >= 0) {
                                    let fundRemain = jsonState.remainFundsItems[indx];
                                    if ((fundRemain.percent !== '') && (fundRemain.percent > 0)) {
                                        fundItem = {
                                            Action: 'New',
                                            fund_id: allFunds[i],
                                            FundNameCode: allFundsMap[allFunds[i]],
                                            fund_val_each_funf: fundRemain.value,
                                            NewRate: fundRemain.percent,
                                            OldRate: '0',
                                            Order: '' + (i + 1)
                                        }
                                        oldFundItem = {
                                            FundNameCode: allFundsMap[allFunds[i]],
                                            cdi_alloc_pct: '0',
                                            Order: '' + (i + 1)
                                        }
                                    } else {
                                        if (reqType === 'FPM') {
                                            fundItem = {
                                                Action: 'New',
                                                fund_id: allFunds[i],
                                                FundNameCode: allFundsMap[allFunds[i]],
                                                fund_val_each_funf: '0',
                                                NewRate: '0',
                                                OldRate: '0',
                                                Order: '' + (i + 1)
                                            }
                                            oldFundItem = {
                                                FundNameCode: allFundsMap[allFunds[i]],
                                                cdi_alloc_pct: '0',
                                                Order: '' + (i + 1)
                                            }
                                        }
                                    }

                                }

                            }
                            if (fundItem && oldFundItem) {
                                newItems.push(fundItem);
                                oldItems.push(oldFundItem);
                            }

                        }
                        let submitRequest = {
                            jsonDataInput: {
                                ClientClass: CLASSPOREVERSEMAP[getSession(CLASSPO)],
                                ClientID: getSession(CLIENT_ID),
                                ClientName: getSession(FULL_NAME),
                                FromSystem: 'DCW',
                                DeviceId: getDeviceId(),
                                ContactEmail: getSession(EMAIL),
                                NewValue: newItems,
                                OldValue: oldItems,
                                PolicyList: [
                                    {
                                        Order: '1',
                                        PolicyNo: jsonState.polID.trim()
                                    }
                                ],
                                RequestTypeID: reqType,
                                Action: "RequestFund",
                                APIToken: getSession(ACCESS_TOKEN),
                                Authentication: AUTHENTICATION,
                                Company: COMPANY_KEY,
                                Project: "mcp",
                                UserLogin: getSession(USER_LOGIN),
                                SubmitDate: getCurrentDate(),
                                ProccessType: "PSP",
                                UserId: "DConnect",
                                IsAuto: "0",
                                OtpVerified: this.state.otp,
                            }
                        };
                        onlineRequestSubmit(submitRequest)
                            .then(res => {
                                if (res.Response.Result === 'true' && res.Response.ErrLog === 'Submit OS is saved successfull.') {
                                    this.setState({showThanks: true});
                                }
                            }).catch(error => {
                            // alert(error);
                        });
                    } else if (this.state.updateReinstatement) {
                        // submit reinstament
                        this.submitReInstament();
                    }
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({errorMessage: OTP_EXPIRED});
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.setState({showOtp: false, minutes: 0, seconds: 0});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({errorMessage: OTP_INCORRECT});
                } else {
                    this.setState({errorMessage: OTP_INCORRECT});
                }
            }).catch(error => {
            // alert(JSON.stringify(error));
        });


    }

    submitReInstament = () => {
        let list = [];

        for (let x in this.state.insuredLapseList[0].Questionaire) {
            let questionaire = {};
            let item = this.state.insuredLapseList[0].Questionaire[x];
            questionaire.LifeInsure = item.LifeInsure;
            let questionList = [];
            for (let i = 0; i < item.QuestionList.length; i++) {
                let question;
                if (this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][item.QuestionList[i].QuestionNo] === true)) {
                    question = {
                        QuestionNo: item.QuestionList[i].QuestionNo,
                        Answer: '1',
                        Comment: this.state.AnsweredValueListMap[item.LifeInsure][item.QuestionList[i].QuestionNo]
                    }

                } else {
                    question = {
                        QuestionNo: item.QuestionList[i].QuestionNo,
                        Answer: '0',
                        Comment: ''
                    }
                }
                questionList.push(question);
            }
            questionaire.QuestionList = questionList;
            list.push(questionaire);
        }

        const clientIds = (this.state.insuredLapseList[0]?.Questionaire ?? []).map(item => item?.LifeInsureID);
        const concateCLientIds = clientIds.join(',')
        let submitRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                // Action: "SubmitReInstatement",
                Action: "InitReInstatement",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: "Web",
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                ClientID: getSession(CLIENT_ID),
                ClientName: getSession(FULL_NAME),
                ClientClass: CLASSPOREVERSEMAP[getSession(CLASSPO)],
                RequestTypeID: "RIN",
                FromSystem: "DCW",
                PolicyNo: this.state.polID.trim(),
                ReInsFee: this.state.tempFee,
                DateLapse: this.state.polExpiryDate,
                Questionaire: list,
                OtpVerified: this.state.otp,
            }
        }
        // console.log(submitRequest);
        onlineRequestSubmit(submitRequest)
            .then(res => {
                const currentState = this.state;
                // let clientListIds = getSession(CLIENT_ID) + ',' + concateCLientIds;
                let clientListIds = concateCLientIds;
                currentState.clientListStr = clientListIds;
                let trackingID = '';
                if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Submit RIN is saved successfull.') && res.Response.Message) {
                    // upload images
                    trackingID = res.Response.Message;
                    console.log('onlineRequestSubmit done', trackingID);
                    const doSomethingInAsync = () => {
                        currentState.trackingId = trackingID;
                        if(currentState.poConfirmingND13 === ND13_CONFIRMING_STATUS.NEED){
                            console.log('Show popup confirm ND 13');
                            this.setState({...this.setState,submitting: false});
                            this.fetchCPConsentConfirmation(trackingID, clientListIds);
                            // this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION, (jState) => {
                            //     jState.submitting = false;
                            //     jState.trackingId = trackingID;
                            //     currentState.submitting = false;
                            // });
                            setLocal(this.getLocalKey(currentState.polID), JSON.stringify(currentState));
                        }
                        else{
                            this.setState({showThanks: true});
                        }
                    };
                    this.uploadImages(trackingID, doSomethingInAsync);
                } else {
                    this.setState({showOtp: false, minutes: 0, seconds: 0, apiError: true});
                }

            }).catch(error => {
            // alert(error);
        });
    }

    uploadImages = (transactionId, callback) => {
        if (this.state.attachmentState.attachmentList.length === 0) {
            // this.setState({showThanks: true});
            callback();
        }
        else{
            for (let i = 0; i < this.state.attachmentState.attachmentList.length; i++) {
                const matches = this.state.attachmentState.attachmentList[i].imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
                let uploadRequest = {
                    jsonDataInput: {
                        Action: "SubmitDocumentImage",
                        APIToken: getSession(ACCESS_TOKEN),
                        Authentication: AUTHENTICATION,
                        ClientID: getSession(CLIENT_ID),
                        Company: COMPANY_KEY,
                        DeviceId: getDeviceId(),
                        OS: "Web",
                        Project: "mcp",
                        UserLogin: getSession(USER_LOGIN),
                        TransactionID: transactionId,
                        DocProcessID: "RIN",
                        DocTypeID: "HealthDocument",
                        DocTypeName: "Health Document",
                        NumberOfPage: this.state.attachmentState.attachmentList.length,
                        Image: (matches.length === 3) ? matches[2] : '',
                        OtpVerified: this.state.otp,
                    }
                }
                onlineRequestSubmit(uploadRequest)
                    .then(res => {
                        if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Successfull') && res.Response.Message) {
                            if (i === this.state.attachmentState.attachmentList.length - 1) {
                                // this.setState({showThanks: true});
                                callback();
                            }
                        } else {
                            this.setState({showOtp: false, minutes: 0, seconds: 0, apiError: true});
                        }
                    }).catch(error => {
                    // alert(error);
                });
    
            }
        }
        
    }

    findFundIdInFundArray = (fundId, list) => {
        if (!fundId || !list) {
            return -1;
        }
        const indexOfFund = list.findIndex(fund => {
            return fund.id === fundId;
        });
        return indexOfFund;
    }


    closeThanks = () => {
        this.setState({showThanks: false});
        removeLocal(this.getLocalKey(this.state.polID));
        window.location.href = '/reinstatement';
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

    closeOtpRIN = () => {
        this.setState({showOtpRIN: false, minutes: 0, seconds: 0});
    }

    radioReinstatement = () => {
        let jsonState = this.state;
        jsonState.updateReinstatement = true;
        jsonState.updateInvestRate = false;
        jsonState.updateFund = false;
        jsonState.updatePeriodicFee = false;
        jsonState.insuranceFee = false;
        jsonState.decreaseSACancelRider = false;
        jsonState.updateLoan = false;
        jsonState.noValidPolicy = false;

        this.state.stepName = FUND_STATE.CATEGORY_INFO;
        jsonState.enabled = true;

        this.setState(jsonState);
        this.checkPolicyLaspe(false);
        // checkSubmitedIn24h();
        //goToChoosePolicy();
    }

    checkPolicyLaspe = (retry) => {
        if(retry) {
            const jsonState = this.state;
            jsonState.countRetry = this.state.countRetry + 1;
            jsonState.isNonePaymentList = false;
            jsonState.isReloadPaymentList = false;
            jsonState.isRetrying = true;
            jsonState.loading = false;
            this.setState(jsonState);
        }
        if (!getSession(POL_LIST_LAPSE)) {
            let jsonState = this.state;
            jsonState.loading = true;
            const TIMEOUT = "TIMEOUT";
            let submitRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "GetPolicyLapse",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    ClientID: getSession(CLIENT_ID)
                }
            };
            CPGetPolicyListByCLIID(submitRequest).then(Res => {
                let Response = Res.Response;
                // console.log('3',Response);
                // Response.ErrLog = 'TIMEOUT';
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile && Response.ClientProfile.length > 0) {
                    setSession(POL_LIST_LAPSE, JSON.stringify(Response.ClientProfile));
                    jsonState.loading = false;
                    jsonState.polListLapse = Response.ClientProfile;
                    jsonState.noValidPolicy = false;
                    jsonState.timeOut = false;
                    jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                    this.setState(jsonState);
                } else if (Response.ErrLog === TIMEOUT) {
                    setStateTimeoutOrEmpty(Response);
                    jsonState.stepName = FUND_STATE.CATEGORY_INFO;


                } else {
                    jsonState.loading = false;
                    jsonState.polListLapse = null;
                    jsonState.noValidPolicy = true;
                    this.setState(jsonState);
                }
            }).catch(error => {
                jsonState.loading = false;
                jsonState.polListLapse = null;
                this.setState(jsonState);
                return null;
            }).finally(() => {
                const jsonState = this.state;
                jsonState.isRetrying = false;
                this.setState(jsonState);
            });
        } else {
            let jsonState = this.state;
            let polListLapse = JSON.parse(getSession(POL_LIST_LAPSE));
            if (!polListLapse) {
                jsonState.noValidPolicy = true;
            } else {
                jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                jsonState.noValidPolicy = false;
            }
            jsonState.polListLapse = polListLapse;
            this.setState(jsonState);
        }
    }

    setStateTimeoutOrEmpty = (res) => {
        const jsonState = this.state;
        jsonState.loading = false;
        jsonState.isNonePaymentList = true;
        jsonState.isReloadPaymentList = jsonState.countRetry < NUM_OF_MANUAL_RETRY;
        this.setState(jsonState);
    }

    componentDidMount() {
        if (this.props.match.params.info) {
            let infoArr = this.props.match.params.info.split('-');
            if (infoArr && infoArr.length === 3 ) {
                let proccessType = infoArr[0];
                let userLogin = infoArr[1];
                let trackingId = infoArr[2];
                if (proccessType && userLogin && trackingId) {
                    this.loadND13Data(userLogin, trackingId, getSession(ACCESS_TOKEN), getDeviceId(), proccessType);
                }
                
            }
        } else {
            this.fetchCheckConfigPermission();
            this.getValidILEpolicy();
        }
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE}`,
            `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE}`,
        );
        this.radioReinstatement();
    }

    componentDidUpdate (prevProps) {

        if (this.props.match.params.info && (this.props.match.params.info !== prevProps.match.params.info)) {
            let infoArr = this.props.match.params.info.split('-');
            if (infoArr && infoArr.length === 3 ) {
                let proccessType = infoArr[0];
                let userLogin = infoArr[1];
                let trackingId = infoArr[2];
                if (proccessType && userLogin && trackingId) {
                    this.loadND13Data(userLogin, trackingId, getSession(ACCESS_TOKEN), getDeviceId(), proccessType);
                }
                
            }
        }
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
        this.cpSaveLog(`Web_Close_${PageScreen.POL_TRANS_REINST}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Close_${PageScreen.POL_TRANS_REINST}`,
            `Web_Close_${PageScreen.POL_TRANS_REINST}`,
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
                                    Action: "CheckRequestSubmitND13",
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

    getValidILEpolicy() {
        if (!getSession(POL_LIST_IL_CLIENT)) {
            let jsonState = this.state;
            jsonState.loading = true;
            const listRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "GetILProductList",
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            };
            CPGetPolicyListByCLIID(listRequest).then(Res1 => {
                let Response = Res1.Response;
                // console.log('2',Response);
                // Response.ErrLog = 'Err';
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                    setSession(POL_LIST_IL_CLIENT, JSON.stringify(Response.ClientProfile));
                    jsonState.loading = false;
                    jsonState.polListProfile = Response.ClientProfile;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    jsonState.polListProfile = null;
                    this.setState(jsonState);
                }
            }).catch(error => {
                jsonState.loading = false;
                jsonState.polListProfile = null;
                this.setState(jsonState);
                return null;
            }).finally(()=>{
                // callback();
            });
        } else {
            let jsonState = this.state;
            jsonState.polListProfile = JSON.parse(getSession(POL_LIST_IL_CLIENT));
            this.setState(jsonState);
        }
    }

    getPolicyListPayMode() {

        const apiRequest = {//Dummy lấy danh sách hợp đồng, khi có API sẽ update request
            jsonDataInput: {
                Company: COMPANY_KEY,
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                APIToken: getSession(ACCESS_TOKEN),
                Project: 'mcp',
                ClientID: getSession(CLIENT_ID),
                UserLogin: getSession(USER_LOGIN)
            }
        }
        CPGetPolicyListByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                const jsonState = this.state;
                jsonState.polListPayMode = Response.ClientProfile;
                jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: { authenticated: false, hideMain: false }

                })

            }

        }).catch(error => {
            this.props.history.push('/maintainence');
        }).finally(() => {

        });
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
                ProcessType,
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
                            state.proccessType = 'RIN';
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
                ProcessType,
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
                ProcessType,
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
        window.location.href = '/reinstatement';
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
        window.location.href = '/reinstatement';
    }

    handlerAdjust = () => {
        if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
            this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18, toggleBack: !this.state.toggleBack});
        } 
    }

    updateNoValidPolicy = (value) => {
        this.setState({noValidPolicy: value});
    }

    resetState=()=> {
        const newState = INITIAL_STATE_UPDATE_POLICY_INFO();
        this.setState(newState);
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

        const radioInvestRate = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CHOOSE_CONTRACT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CHOOSE_CONTRACT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CHOOSE_CONTRACT}`,
            );
            let jsonState = this.state;
            jsonState.updateInvestRate = true;
            jsonState.updateFund = false;
            jsonState.updatePeriodicFee = false;
            jsonState.insuranceFee = false;
            jsonState.updateReinstatement = false;
            jsonState.decreaseSACancelRider = false;
            jsonState.updateLoan = false;
            jsonState.noValidPolicy = false;

            this.state.stepName = FUND_STATE.CATEGORY_INFO;
            jsonState.enabled = true;

            this.setState(jsonState);
            // document.getElementById('radioFund').checked = false; rem for disable Fund funciton
            // document.getElementById('radioPeriodicFee').checked = false;
            // document.getElementById('radioInsuranceFee').checked = false;
            goToChoosePolicy();
        }

        const radioPeriodicFee = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`,
                `Web_Open_${PageScreen.POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT}`,
            );
            let jsonState = this.state;
            jsonState.updatePeriodicFee = true;
            jsonState.updateInvestRate = false;
            jsonState.updateFund = false;
            jsonState.insuranceFee = false;
            jsonState.updateReinstatement = false;
            jsonState.decreaseSACancelRider = false;
            jsonState.updateLoan = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'PRM';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            this.state.polListLapse = null;

            this.setState(jsonState);
            // this.getPolicyListPayMode();
          }

          const radioDecreaseSACancelRider = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`,
                `Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`,
            );
            let jsonState = this.state;
            jsonState.decreaseSACancelRider = true;
            jsonState.updatePeriodicFee = false;
            jsonState.updateInvestRate = false;
            jsonState.updateFund = false;
            jsonState.insuranceFee = false;
            jsonState.updateReinstatement = false;
            jsonState.updateLoan = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'CSA';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            this.state.polListLapse = null;

            this.setState(jsonState);
          }
          
        const radioReinstatement = () => {
            let jsonState = this.state;
            jsonState.updateReinstatement = true;
            jsonState.updateInvestRate = false;
            jsonState.updateFund = false;
            jsonState.updatePeriodicFee = false;
            jsonState.insuranceFee = false;
            jsonState.decreaseSACancelRider = false;
            jsonState.updateLoan = false;
            jsonState.noValidPolicy = false;

            this.state.stepName = FUND_STATE.CATEGORY_INFO;
            jsonState.enabled = true;

            this.setState(jsonState);
            checkPolicyLaspe(false);
            // checkSubmitedIn24h();
            //goToChoosePolicy();
        }

        //Payment
        const radioPaymentLoan = (e) => {
            e.preventDefault();
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`);
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`,
                `Web_Open_${PageScreen.POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT}`,
            );
            let jsonState = this.state;
            jsonState.updateLoan = true;
            jsonState.decreaseSACancelRider = false;
            jsonState.updatePeriodicFee = false;
            jsonState.updateInvestRate = false;
            jsonState.updateFund = false;
            jsonState.insuranceFee = false;
            jsonState.updateReinstatement = false;
            jsonState.noValidPolicy = false;
            jsonState.appType = 'CLOSE';
            jsonState.proccessType = 'PML';
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.enabled = true;
            this.state.polListLapse = null;

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

        const checkPolicyLaspe = (retry) => {
            if(retry) {
                const jsonState = this.state;
                jsonState.countRetry = this.state.countRetry + 1;
                jsonState.isNonePaymentList = false;
                jsonState.isReloadPaymentList = false;
                jsonState.isRetrying = true;
                jsonState.loading = false;
                this.setState(jsonState);
            }
            if (!getSession(POL_LIST_LAPSE)) {
                let jsonState = this.state;
                jsonState.loading = true;
                const TIMEOUT = "TIMEOUT";
                let submitRequest = {
                    jsonDataInput: {
                        Company: COMPANY_KEY,
                        Action: "GetPolicyLapse",
                        APIToken: getSession(ACCESS_TOKEN),
                        Authentication: AUTHENTICATION,
                        DeviceId: getDeviceId(),
                        Project: "mcp",
                        UserLogin: getSession(USER_LOGIN),
                        ClientID: getSession(CLIENT_ID)
                    }
                };
                CPGetPolicyListByCLIID(submitRequest).then(Res => {
                    let Response = Res.Response;
                    // console.log('3',Response);
                    // Response.ErrLog = 'TIMEOUT';
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile && Response.ClientProfile.length > 0) {
                        setSession(POL_LIST_LAPSE, JSON.stringify(Response.ClientProfile));
                        jsonState.loading = false;
                        jsonState.polListLapse = Response.ClientProfile;
                        jsonState.noValidPolicy = false;
                        jsonState.timeOut = false;
                        jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                        this.setState(jsonState);
                    } else if (Response.ErrLog === TIMEOUT) {
                        setStateTimeoutOrEmpty(Response);
                        jsonState.stepName = FUND_STATE.CATEGORY_INFO;


                    } else {
                        jsonState.loading = false;
                        jsonState.polListLapse = null;
                        jsonState.noValidPolicy = true;
                        this.setState(jsonState);
                    }
                }).catch(error => {
                    jsonState.loading = false;
                    jsonState.polListLapse = null;
                    this.setState(jsonState);
                    return null;
                }).finally(() => {
                    const jsonState = this.state;
                    jsonState.isRetrying = false;
                    this.setState(jsonState);
                });
            } else {
                let jsonState = this.state;
                let polListLapse = JSON.parse(getSession(POL_LIST_LAPSE));
                if (!polListLapse) {
                    jsonState.noValidPolicy = true;
                } else {
                    jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                    jsonState.noValidPolicy = false;
                }
                jsonState.polListLapse = polListLapse;
                this.setState(jsonState);
            }
        }

        const setStateTimeoutOrEmpty = (res) => {
            const jsonState = this.state;
            // jsonState.jsonPolicySearchResponse = res;
            jsonState.loading = false;
            jsonState.isNonePaymentList = true;
            jsonState.isReloadPaymentList = jsonState.countRetry < NUM_OF_MANUAL_RETRY;
            this.setState(jsonState);
        }

        const toggleCheckCellPhone = () => {
            this.setState({isCellPhoneChecked: !this.state.isCellPhoneChecked});
        }
        const toggleCheckHomePhone = () => {
            this.setState({isHomePhoneChecked: !this.state.isHomePhoneChecked});
        }
        const toggleCheckBusinessPhone = () => {
            this.setState({isBusinessPhoneChecked: !this.state.isBusinessPhoneChecked});
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


        // const backToPrevUpdateInfoState = () => {
        //     const jsonState = this.state;
        //     const DOB = this.state.clientProfile.dob;
        //     if (updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
        //         const newClaimSubmissionState = this.isOlderThan18(DOB)
        //         ? ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18
        //         : ND_13.ND13_INFO_PO_CONTACT_INFO_UNDER_18;
        //         jsonState.isPayment = false;
        //         jsonState.updateInfoState = newClaimSubmissionState;
        //         this.setState(jsonState);
        //         this.props.callBackUpdateND13State(newClaimSubmissionState);
        //         this.props.handlerGoToStep(ND_13.SUBMIT);
        //     } else if ((updateInfoState === ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18) || (updateInfoState === ND_13.ND13_INFO_PO_CONTACT_INFO_UNDER_18) || (updateInfoState === ND_13.ND13_INFO_CONFIRMATION)) {
        //         jsonState.updateInfoState = ND_13.INIT;
        //         jsonState.isPayment = false;
        //         this.setState(jsonState);
        //         this.props.callBackUpdateND13State(ND_13.INIT);
        //     }
        // }
        

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

        const showCardInfo = (polID, index, AgentName, PolicyClassCD) => {
            const jsonState = this.state;
            jsonState.polID = polID;
            jsonState.isEmpty = false;
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            this.setState(jsonState);
        }

        const showCardInfoLapse = (polID, CountDayLapse, YearLapse, PolExpiryDate, item) => {
            const jsonState = this.state;
            jsonState.polID = polID;
            jsonState.isEmpty = false;
            jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
            jsonState.countDayLapse = CountDayLapse;
            jsonState.yearLapse = YearLapse;
            jsonState.polExpiryDate = PolExpiryDate;
            jsonState.polLapseSelected = item;
            // jsonState.trackingId = getSession(USER_LOGIN) + polID;
            setSession(POLICY_NO, polID);

            this.setState(jsonState);
            /*const jsonRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "CheckRequestSubmitND13",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    RequestTypeID: "RIN",
                    PolicyNo: polID,
                    FromSystem: "DCW",
                    OtpVerified: this.state.otp,
                }
            };

            onlineRequestSubmit(jsonRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    // this.setState({submitIn24: true});
                    setSession(SUBMIT_IN_24_RIN + this.state.polID, SUBMIT_IN_24_RIN);
                    return;
                } else if (Response.Result === 'true' && Response.ErrLog === "DRAFT") {
                    setSession(RIN_DRAFT + this.state.polID, Response.Message);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    // this.setState({trackingId: Response.Message, hasLocalData: true, appType: 'CLOSE', proccessType: 'RIN', stepName: FUND_STATE.SDK});
                    // currentState.trackingId = Response.Message;


                    alert(Response.ErrLog);
            const keyLocal = this.getLocalKey(polID)
            getLocal(keyLocal).then(res=>{
                if(res && res.v){
                    alert('vv');
                    // const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
                    // dataResponse.hasLocalData = true;
                    // dataResponse.submitting = false;
                    // this.setState(dataResponse);
                    jsonState.hasLocalData = true;
                    this.setState(jsonState);
                } else {
                    this.setState(jsonState);
                }
            })




                    // jsonState.hasLocalData = true;
                    // this.setState(jsonState);

                    // currentState.appType = 'CLOSE';
                    // currentState.proccessType = 'RIN';
                    // currentState.stepName = FUND_STATE.SDK;
                    // loadND13DataTemp(getSession(USER_LOGIN), Response.Message, getSession(ACCESS_TOKEN), getDeviceId());
                } else {
                    removeSession(RIN_DRAFT + this.state.polID);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    this.setState({hasLocalData: false});
                    jsonState.hasLocalData = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
            });*/
        }
        

        const handleCheckLocalWhenClickOnCard = (polID, callback) => {
            const currentState = this.state;
            // const keyLocal = this.getLocalKey(polID)
            // getLocal(keyLocal).then( res => {
            //     if( res && res.v){
            //         // show popup confirm : You have already made a request, do you want continue?
            //         currentState.hasLocalData = true;
            //     }
            //     else{
            //         currentState.polID = polID;
            //     }
            // }).catch(err => {
            //     // do nothing
            // }).finally(()=>{
            //     callback(currentState);
            // });

            ////
            const jsonRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "CheckRequestSubmitND13",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    RequestTypeID: "RIN",
                    PolicyNo: polID,
                    FromSystem: "DCW",
                    OtpVerified: this.state.otp,
                }
            };

            onlineRequestSubmit(jsonRequest).then(Res => {
                const currentState = this.state;
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    // this.setState({submitIn24: true});
                    setSession(SUBMIT_IN_24_RIN + this.state.polID, SUBMIT_IN_24_RIN);
                    return;
                } else if (Response.Result === 'true' && Response.ErrLog === "DRAFT") {
                    setSession(RIN_DRAFT + this.state.polID, Response.Message);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    this.setState({trackingId: Response.Message, hasLocalData: true, appType: 'CLOSE', proccessType: 'RIN', stepName: FUND_STATE.SDK});
                    currentState.trackingId = Response.Message;
                    currentState.hasLocalData = true;
                    currentState.appType = 'CLOSE';
                    currentState.proccessType = 'RIN';
                    currentState.stepName = FUND_STATE.SDK;
                    // loadND13DataTemp(getSession(USER_LOGIN), Response.Message, getSession(ACCESS_TOKEN), getDeviceId());
                } else {
                    removeSession(RIN_DRAFT + this.state.polID);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    this.setState({hasLocalData: false});
                    currentState.hasLocalData = false;
                }
            }).catch(error => {
            });
            ////
            callback(currentState);
        }
        const handleClearExistLocalConfirm = () => {
            removeLocal(this.getLocalKey(this.state.polID));
            deleteND13DataTemp(getSession(CLIENT_ID), this.state.trackingId, getSession(ACCESS_TOKEN), getDeviceId());
            let newState = INITIAL_STATE_UPDATE_POLICY_INFO();
            newState.polID = this.state.polID;
            newState.updateReinstatement = this.state.updateReinstatement;
            newState.updateInvestRate = this.state.updateInvestRate;
            newState.updatePeriodicFee = this.state.updatePeriodicFee;
            newState.stepName = this.state.stepName;
            newState.polListLapse = this.state.polListLapse;
            newState.countDayLapse = this.state.countDayLapse;
            newState.yearLapse = this.state.yearLapse;
            newState.polExpiryDate = this.state.polExpiryDate;
            newState.polLapseSelected = this.state.polLapseSelected;
            newState.stepName = FUND_STATE.UPDATE_INFO;
            this.setState(newState);
            getQuestionaire();
        }

        const handleCLoseExistLocalConfirm = () => {
            this.setState({hasLocalData: false});
        }

        const handleAgreeExistLocalConfirm = (polID) => {
            // const keyLocal = this.getLocalKey(polID)
            // getLocal(keyLocal).then(res=>{
            //     if(res && res.v){
            //         alert('vv');
            //         const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
            //         dataResponse.hasLocalData = false;
            //         dataResponse.submitting = false;
            //         this.setState(dataResponse);
            //         loadND13DataTemp(getSession(USER_LOGIN), getSession(RIN_DRAFT + polID), getSession(ACCESS_TOKEN), getDeviceId());
            //     }
            // })
            if (this.state.trackingId) {
                loadND13DataTemp(getSession(USER_LOGIN), this.state.trackingId, getSession(ACCESS_TOKEN), getDeviceId());
            } else {
                this.setState({stepName: FUND_STATE.UPDATE_INFO});
            }
            
        }

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

        const getFundList = () => {
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
            if (jsonState.lastChosenPolID !== jsonState.polID) {
                jsonState.lastChosenPolID = jsonState.polID + '';
                //Reset fund info
                jsonState.showFundDetails = false;
                jsonState.showFundDetailsMap = {};
                jsonState.currentFunds = [];
                jsonState.chosenFunds = {};
                jsonState.remainFunds = ["ILPF1", "ILPF2", "ILPF3", "ILPF6", "ILPF7"];
                jsonState.currentFundsMap = {};
                jsonState.currentFundsChangeMap = {};
                jsonState.currentFundsExpandMap = {};
                jsonState.currentFundsExchangeItemsMap = {};
                jsonState.remainFundsItems = [];
                //End reset fund info
                let request = {
                    jsonDataInput: {
                        Company: COMPANY_KEY,
                        OS: WEB_BROWSER_VERSION,
                        Authentication: AUTHENTICATION,
                        DeviceId: getDeviceId(),
                        APIToken: getSession(ACCESS_TOKEN),
                        Project: 'mcp',
                        ClientID: getSession(CLIENT_ID),
                        UserLogin: getSession(USER_LOGIN),
                        UserID: getSession(USER_LOGIN),
                        PolID: this.state.polID,
                        Action: 'PolicyFundChange'
                    }
                }
                CPGetPolicyInfoByPOLID(request).then(Res => {
                    //console.log(Res);
                    let Response = Res.Response;
                    //console.log(Response3.ClientProfile);
                    if (Response.ErrLog === 'SUCCESSFUL') {
                        jsonState.ValueResponse = Response;
                        // jsonState.ClientProfileValue = Response.ClientProfile;
                        jsonState.PolicyInfo = Response.ClientProfile;
                        jsonState.stepName = FUND_STATE.UPDATE_INFO;
                        let fundId = '';
                        if (jsonState.PolicyInfo) {
                            for (let i = 0; i < jsonState.PolicyInfo.length; i++) {
                                fundId = jsonState.PolicyInfo[i].fund_id.split(';')[1].trim();
                                jsonState.currentFunds.push(fundId);
                                jsonState.currentFundsExpandMap[fundId] = false;
                                // let exId = jsonState.exId;
                                // jsonState.exId = exId + 1;
                                let tempFund = new FundModel(fundId, allFundsMap[fundId], 0, 0, false, 0, 0);//[id, toFund, percent, tempValue, show, price, numOfUnits]
                                let fundExArr = jsonState.currentFundsExchangeItemsMap[fundId];
                                if (!fundExArr) {
                                    fundExArr = [];
                                }
                                fundExArr.push(tempFund);
                                jsonState.currentFundsExchangeItemsMap[fundId] = fundExArr;
                                // exId = jsonState.exId;
                                // jsonState.exId = exId + 1;
                                let percent = 0;
                                let valueFund = 0;
                                let price = 0;
                                let numOfUnits = 0;
                                if (jsonState.PolicyInfo[i].cdi_alloc_pct.split(';')[1]) {
                                    percent = parseInt(jsonState.PolicyInfo[i].cdi_alloc_pct.split(';')[1]);

                                }
                                if (jsonState.PolicyInfo[i].fund_val_each_funf.split(';')[1]) {
                                    valueFund = parseInt(jsonState.PolicyInfo[i].fund_val_each_funf.split(';')[1].replaceAll('.', '').replaceAll(',', '.'));
                                }
                                if (jsonState.PolicyInfo[i].fia_unit_pric_amt.split(';')[1]) {
                                    price = parseFloat(jsonState.PolicyInfo[i].fia_unit_pric_amt.split(';')[1].replaceAll('.', '').replaceAll(',', '.'));
                                }
                                if (jsonState.PolicyInfo[i].fia_unit_cum_qty.split(';')[1]) {
                                    numOfUnits = parseFloat(jsonState.PolicyInfo[i].fia_unit_cum_qty.split(';')[1].replaceAll('.', '').replaceAll(',', '.'));
                                }

                                let orginalFund = new FundModel(fundId, allFundsMap[fundId], percent, valueFund, false, price, numOfUnits);//[id, fromFund, percent, value, show, price, numOfUnits]
                                jsonState.currentFundsMap[fundId] = orginalFund;
                                let orginalChangeFund = new FundModel(fundId, allFundsMap[fundId], '', valueFund, false, price, numOfUnits);//[id, fromFund, percent, value, show, price, numOfUnits]
                                jsonState.currentFundsChangeMap[fundId] = orginalChangeFund;
                            }

                        }

                        for (let j = 0; j < jsonState.currentFunds.length; j++) {
                            if (jsonState.remainFunds.includes(jsonState.currentFunds[j])) {
                                jsonState.remainFunds = jsonState.remainFunds.filter(item => {
                                    return item !== jsonState.currentFunds[j];
                                });
                            }
                        }
                        let remainArr = [];
                        for (let j = 0; j < jsonState.remainFunds.length; j++) {
                            // let exId = jsonState.exId;
                            // jsonState.exId = exId + 1;
                            let tempFund = new FundModel(jsonState.remainFunds[j], allFundsMap[jsonState.remainFunds[j]], '', 0, false, 0, 0);//[id, FundName, percent, tempValue, show, price, numOfUnits]
                            remainArr.push(tempFund);
                        }
                        jsonState.remainFundsItems = remainArr;
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
            } else {
                jsonState.stepName = FUND_STATE.UPDATE_INFO;
                this.setState(jsonState);
            }


        }

        const validateMoveFundInfo = () => {
            if (!this.state.currentFundsExchangeItemsMap || getMapSize(this.state.currentFundsExchangeItemsMap) < 1) {
                return false;
            }
            let jsonState = this.state;
            let totalFromFund = 0;
            let totalToFundCalIntemp = 0;

            for (let fundId in this.state.currentFundsExchangeItemsMap) {
                let itemArr = this.state.currentFundsExchangeItemsMap[fundId];
                let totalPercent = 0;
                let fromFund = jsonState.currentFundsMap[fundId];
                if (fromFund) {
                    totalFromFund += fromFund.numOfUnits * fromFund.price;
                    // totalFromFund += fromFund.value;
                }
                for (let t = 0; t < itemArr.length; t++) {
                    totalPercent += parseInt(itemArr[t].percent);
                    totalToFundCalIntemp += itemArr[t].percent * itemArr[t].value * 0.01;

                }
                if (totalFromFund < FUND_FROM_LIMIT) {
                    jsonState.totalToFundError = true;
                    this.setState(jsonState);
                    return false;
                }

                if (totalToFundCalIntemp < FUND_TO_LIMIT) {
                    jsonState.totalFundCalInTempError = true;
                    this.setState(jsonState);
                    return false;
                }

                if (totalPercent > 100) {
                    jsonState.percentError = true;
                    this.setState(jsonState);
                    return false;
                }
            }

            return true;
        }

        const submitMoveFund = () => {
            if (!validateMoveFundInfo()) {
                return;
            }
            this.setState({stepName: FUND_STATE.VERIFICATION});
        }

        const completeMoveFund = () => {
            if (getSession(VERIFY_CELL_PHONE) === '1') {
                this.setState({submitting: true});
                this.genOtp();
                return;
            }
            // alert('move complete');
        }

        const validateChangeRateFundInfo = () => {
            let jsonState = this.state;
            let haveDiff = false;
            let totalPercent = 0;
            for (let fundId in this.state.currentFundsChangeMap) {
                let fund = this.state.currentFundsChangeMap[fundId];
                let fundPercent = (isInteger(fund.percent) && (fund.percent > 0)) ? fund.percent : 0;
                totalPercent += parseInt(fundPercent);
                let orignalFund = this.state.currentFundsMap[fundId];
                let oriFundPercent = isInteger(orignalFund.percent) ? parseInt(orignalFund.percent) : 0;
                if (oriFundPercent !== parseInt(fundPercent)) {
                    haveDiff = true;
                }
            }


            for (let i = 0; i < this.state.remainFundsItems.length; i++) {
                let fund = this.state.remainFundsItems[i];
                let fundPercent = (isInteger(fund.percent) && (fund.percent > 0)) ? fund.percent : 0;
                totalPercent += parseInt(fundPercent);
                if (parseInt(fundPercent) > 0) {
                    haveDiff = true;
                }
            }

            if (!haveDiff) {
                jsonState.noChangeInvestRate = true;
                this.setState(jsonState);
                return false;
            }

            if (totalPercent !== 100) {
                jsonState.percentError = true;
                this.setState(jsonState);
                return false;
            }

            return true;

        }

        const submitChangeFund = () => {
            if (!validateChangeRateFundInfo()) {
                return;
            }
            // alert('submitChangeFund');
            this.setState({stepName: FUND_STATE.VERIFICATION});
        }

        const dropdownFundDetails = (fundId, id) => {
            const jsonState = this.state;
            if (this.state.loading) {
                return;
            }
            if (document.getElementById(id).className === "dropdown show") {
                document.getElementById(id).className = "dropdown";
                jsonState.showFundDetailsMap[fundId] = false;
                jsonState.currentFundsExpandMap[fundId] = false;
            } else {
                document.getElementById(id).className = "dropdown show";
                jsonState.showFundDetailsMap[fundId] = true;
                jsonState.currentFundsExpandMap[fundId] = true;
            }
            this.setState(jsonState);
        }

        const chooseAFund = (fromFund, toFund, exId) => {

            let jsonState = this.state;
            let fundAddingArr = jsonState.currentFundsExchangeItemsMap[fromFund];
            if (fundAddingArr) {
                const indexObj = findFundIdInToFundArray(exId, fundAddingArr);
                if (indexObj >= 0) {
                    fundAddingArr[indexObj].fundName = toFund;
                    fundAddingArr[indexObj].show = false;
                }

                jsonState.currentFundsExchangeItemsMap[fromFund] = fundAddingArr;
            }
            this.setState(jsonState);
        }

        const enterPercent = (fromFund, exId, fundValue, obj) => {
            let jsonState = this.state;
            if (obj.value < 0 || obj.value > 100) {
                jsonState.toggleRender = !jsonState.toggleRender;
                this.setState(jsonState);
                return;
            }

            let fundAddingArr = jsonState.currentFundsExchangeItemsMap[fromFund];
            if (fundAddingArr) {
                const indexObj = findFundIdInToFundArray(exId, fundAddingArr);
                if (indexObj >= 0) {
                    fundAddingArr[indexObj].percent = obj.value;
                    fundAddingArr[indexObj].value = (obj.value * fundValue * 0.01).toFixed(0);
                }

                jsonState.currentFundsExchangeItemsMap[fromFund] = fundAddingArr;
            }
            this.setState(jsonState);
        }

        const enterPercentChangeRemainItem = (exId, fundValue, obj) => {
            let jsonState = this.state;
            let value = obj.value;
            if (!isInteger(obj.value) || (obj.value < 0)) {
                value = 0;
            }
            const indexObj = findFundIdInToFundArray(exId, jsonState.remainFundsItems);
            if (indexObj >= 0) {
                jsonState.remainFundsItems[indexObj].percent = value;
                jsonState.remainFundsItems[indexObj].value = (value * fundValue * 0.01).toFixed(0);
            }

            this.setState(jsonState);
        }


        const enterPercentChangeItem = (fundId, fundValue, obj) => {
            let jsonState = this.state;
            let value = obj.value;
            if (!isInteger(obj.value) || (obj.value < 0)) {
                value = 0;
            }

            let fund = jsonState.currentFundsChangeMap[fundId];
            if (fund) {
                fund.percent = value;
                fund.value = (value * fundValue * 0.01).toFixed(0);
                jsonState.currentFundsChangeMap[fundId] = fund;
            }

            this.setState(jsonState);
        }

        const toggleFundList = (fundId, exId) => {
            let jsonState = this.state;
            let fundAddingArr = jsonState.currentFundsExchangeItemsMap[fundId];
            if (fundAddingArr) {
                const indexObj = findFundIdInToFundArray(exId, fundAddingArr);
                if (indexObj >= 0) {
                    fundAddingArr[indexObj].show = !fundAddingArr[indexObj].show;
                }

                jsonState.currentFundsExchangeItemsMap[fundId] = fundAddingArr;
            }
            this.setState(jsonState);

        }

        const addExchangeFund = (fundId) => {
            let jsonState = this.state;
            let fundAddingArr = jsonState.currentFundsExchangeItemsMap[fundId];
            if (fundAddingArr) {
                // let exId = jsonState.exId;
                // jsonState.exId = exId + 1;
                let tempFund = new FundModel(fundId, '', 0, 0, false, 0, 0);
                fundAddingArr.push(tempFund);
                jsonState.currentFundsExchangeItemsMap[fundId] = fundAddingArr;
            }
            this.setState(jsonState);
        }

        const removeExchangeFund = (fundId, exId) => {
            let jsonState = this.state;
            let fundAddingArr = jsonState.currentFundsExchangeItemsMap[fundId];
            if (fundAddingArr) {
                if (fundAddingArr.length <= 1) {
                    return;
                }

                const indexObj = findFundIdInToFundArray(exId, fundAddingArr);
                if (indexObj >= 0) {
                    fundAddingArr.splice(indexObj, 1);
                }

                jsonState.currentFundsExchangeItemsMap[fundId] = fundAddingArr;
            }
            this.setState(jsonState);
        }

        const findFundNameInToFundArray = (fundName, list) => {
            if (!fundName || !list) {
                return -1;
            }
            const indexOfFund = list.findIndex(fund => {
                return fund.fundName === fundName;
            });
            return indexOfFund;
        }

        const findFundIdInToFundArray = (id, list) => {
            if (!id || !list) {
                return -1;
            }
            const indexOfFund = list.findIndex(fund => {
                return fund.id === id;
            });
            return indexOfFund;
        }

        const hasEmptyFund = (list) => {
            if (!list) {
                return false;
            }
            const indexOfFund = list.findIndex(fund => {
                return (!fund.fundName || fund.percent === 0);
            });
            if (indexOfFund >= 0) {
                return true;
            }
            return false;
        }

        function getMapSize(x) {
            let len = 0;
            for (const count in x) {
                len++;
            }

            return len;
        }

        const hasAValidFundInMap = () => {
            if (!this.state.currentFundsExchangeItemsMap || getMapSize(this.state.currentFundsExchangeItemsMap) === 0) {
                return false;
            }

            for (let x in this.state.currentFundsExchangeItemsMap) {
                let list = this.state.currentFundsExchangeItemsMap[x];
                const indexOfFund = list.findIndex(fund => {
                    return (fund.id != '' && fund.percent > 0);
                });
                if (indexOfFund >= 0) {
                    return true;
                }
            }

            return false;
        }

        const hasAValidFund = (list) => {
            if (!list) {
                return false;
            }
            const indexOfFund = list.findIndex(fund => {
                return (fund.id != '' && fund.percent > 0);
            });
            if (indexOfFund >= 0) {
                return true;
            }
            return false;
        }

        const hasAValidFundInChangePercentMap = () => {
            for (let x in this.state.currentFundsChangeMap) {
                let fund = this.state.currentFundsChangeMap[x];
                if (fund.percent > 0) {
                    return true;
                }
            }
            for (let i = 0; i < this.state.remainFundsItems.length; i++) {
                if (this.state.remainFundsItems[i].percent > 0) {
                    return true;
                }
            }

            return false;
        }

        const allFundValidFundInChangePercentMap = () => {
            for (let x in this.state.currentFundsChangeMap) {
                let fund = this.state.currentFundsChangeMap[x];
                if (fund.percent % 5 !== 0) {
                    return false;
                }
            }
            for (let i = 0; i < this.state.remainFundsItems.length; i++) {
                if (this.state.remainFundsItems[i].percent % 5 !== 0) {
                    return false;
                }
            }

            return true;
        }

        const toKeys = (map) => {
            let array = [];
            for (let x in map) {
                array.push(x);
            }
            return array;
        }

        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }

        const radioApplyRate = (id) => {
            if (id === 'radioApplyRateOnly') {
                this.setState({forThisOnly: true});
                // document.getElementById('radioApplyRateOnly').checked = true;
                // document.getElementById('radioApplyRate').checked = false;
            } else {
                this.setState({forThisOnly: false});
                // document.getElementById('radioApplyRateOnly').checked = false;
                // document.getElementById('radioApplyRate').checked = true;
            }
        }

        const submitUpdateFundRate = () => {
            if (getSession(VERIFY_CELL_PHONE) === '1') {
                this.setState({submitting: true});
                this.genOtp();
                return;
            }
        }

        const withValueCap = (inputObj) => {
            const {value} = inputObj;
            if (value <= 100) return true;
            return false;
        }

        const EnterInfoToReinstament = () => {
            if (!getSession(CELL_PHONE)) {
                //yêu cầu cập nhật số dt
                this.setState({ noPhone: true });
                return;
            }
            if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
                //yêu cầu bật 2fa
                this.setState({ noTwofa: true });
                return;
            }
            // if (getSession(SUBMIT_IN_24_RIN + this.state.polID)) {
            //     this.setState({submitIn24: true});
            //     return;
            // }
            // if (getSession(RIN_DRAFT)) {
            //     this.setState({appType:'CLOSE', proccessType: 'RIN', stepName:FUND_STATE.SDK});
            //     return;
            // }
            if (isInteger(this.state.polLapseSelected.YearLapse) && (parseInt(this.state.polLapseSelected.YearLapse) >= OVER_LAPSE_YEAR)) {
                this.setState({ lapseAboveOneYear: true });
                return;
            }
            const jsonRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "CheckRequestSubmitND13",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    RequestTypeID: "RIN",
                    PolicyNo: this.state.polID,
                    FromSystem: "DCW",
                    OtpVerified: this.state.otp,
                }
            };

            onlineRequestSubmit(jsonRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    setSession(SUBMIT_IN_24_RIN + this.state.polID, SUBMIT_IN_24_RIN);
                    this.setState({ submitIn24: true });
                    return;
                } else if (Response.Result === 'true' && Response.ErrLog === "DRAFT") {
                    setSession(RIN_DRAFT + this.state.polID, Response.Message);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    // this.setState({trackingId: Response.Message, hasLocalData: true, appType: 'CLOSE', proccessType: 'RIN', stepName: FUND_STATE.SDK});
                    // currentState.trackingId = Response.Message;
                    // this.setState({ trackingId: Response.Message });



                    const keyLocal = this.getLocalKey(this.state.polID);
                    getLocal(keyLocal).then(res => {
                        if (res && res.v) {
                            const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
                            dataResponse.hasLocalData = true;
                            dataResponse.submitting = false;
                            dataResponse.stepName = FUND_STATE.CHOOSE_POLICY;
                            this.setState(dataResponse);
                            // jsonState.hasLocalData = true;
                            // this.setState(jsonState);
                            return;
                        } else {
                            removeSession(RIN_DRAFT + this.state.polID);
                            removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                            getQuestionaire();
                        }
                    })

                } else {
                    const keyLocal = this.getLocalKey(this.state.polID);
                    getLocal(keyLocal).then(res => {
                        if (res && res.v) {
                            const dataResponse = JSON.parse(AES256.decrypt(res.v, COMPANY_KEY2));
                            dataResponse.hasLocalData = true;
                            dataResponse.submitting = false;
                            dataResponse.stepName = FUND_STATE.CHOOSE_POLICY;
                            this.setState(dataResponse);
                            return;
                        } else {
                            removeSession(RIN_DRAFT + this.state.polID);
                            removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                            getQuestionaire();
                        }
                    })
                }
            }).catch(error => {
            });

            ///

           
            // this.setState({stepName: FUND_STATE.UPDATE_INFO});
        }

        const ForceContinueToReInstament = () => {
            if (isInteger(this.state.polLapseSelected.YearLapse) && (parseInt(this.state.polLapseSelected.YearLapse) >= OVER_LAPSE_YEAR)) {
                this.setState({lapseAboveOneYear: true});
            }
            getQuestionaire();
            this.setState({stepName: FUND_STATE.UPDATE_INFO, submitIn24: false});
        }

        const getQuestionaire = () => {
            let submitRequest = {
                jsonDataInput: {
                    Action: "GetQuestionaire",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    PolicyNo: this.state.polID,
                    CountDayLapse: this.state.countDayLapse,
                    YearLapse: this.state.yearLapse,
                    Company: COMPANY_KEY,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    OtpVerified: this.state.otp,
                }
            }
            onlineRequestSubmit(submitRequest)
                .then(res => {
                    if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Successfull.') && res.Response.ClientProfile) {
                        const clientIds = (res.Response.ClientProfile[0]?.Questionaire ?? []).map(item => item?.LifeInsureID);
                        const concateCLientIds = clientIds.join(',');
                        this.setState({insuredLapseList: res.Response.ClientProfile, clientListStr: concateCLientIds, proccessType: 'RIN', stepName: FUND_STATE.UPDATE_INFO});
                        this.fetchCPConsentConfirmationRefresh(this.state.trackingId, concateCLientIds);
                    } else {
                        this.setState({insuredLapseList: null, stepName: FUND_STATE.UPDATE_INFO});
                    }
                }).catch(error => {
                //  alert(error);
            });
        }

        const toggleCardInfo = (InsureID) => {
            if (this.state.expandIdLapseList.indexOf(InsureID) >= 0) {
                let index = this.state.expandIdLapseList.indexOf(InsureID);
                let copyList = this.state.expandIdLapseList;
                copyList.splice(index, 1);
                setSession(EXPAND_ID_LAPSE_LIST, JSON.stringify(copyList));

                this.setState({expandIdLapseList: copyList});
            } else {
                let copyList = this.state.expandIdLapseList;
                copyList.push(InsureID);
                setSession(EXPAND_ID_LAPSE_LIST, JSON.stringify(copyList));

                this.setState({expandIdLapseList: copyList});
                // if (!this.state.insuredImageList[InsureID]) {
                //   getHCCardImg(InsureID);
                // }
            }

        }

        const AnswerYes = (LifeInsure, QuestionNo) => {
            let answerListMap = this.state.AnsweredListMap;
            let answerList = [];
            if (answerListMap[LifeInsure]) {
                answerList = answerListMap[LifeInsure];
            }
            answerList[QuestionNo] = true;
            answerListMap[LifeInsure] = answerList;
            this.setState({AnsweredListMap: answerListMap});
        }

        const AnswerNo = (LifeInsure, QuestionNo) => {
            let answerListMap = this.state.AnsweredListMap;
            let answerList = [];
            if (answerListMap[LifeInsure]) {
                answerList = answerListMap[LifeInsure];
            }
            answerList[QuestionNo] = false;
            answerListMap[LifeInsure] = answerList;
            this.setState({AnsweredListMap: answerListMap});
        }

        const EnterAnswer = (event, QuestionNo, LifeInsure) => {
            let answerValueListMap = this.state.AnsweredValueListMap;
            let answerValueList = [];
            if (answerValueListMap[LifeInsure]) {
                answerValueList = answerValueListMap[LifeInsure];
            }
            answerValueList[QuestionNo] = event.target.value;
            answerValueListMap[LifeInsure] = answerValueList;
            this.setState({AnsweredValueListMap: answerValueListMap});
        }

        const validateAnswers = () => {
            let valid = true;
            let errorUpload = '';
            let copyList = this.state.expandIdLapseList;
            let answeredErrorListMap = this.state.AnsweredErrorListMap;
            for (let x in this.state.insuredLapseList[0].Questionaire) {
                let item = this.state.insuredLapseList[0].Questionaire[x];
                let answeredErrorList = [];
                if (answeredErrorListMap[item.LifeInsure]) {
                    answeredErrorList = answeredErrorListMap[item.LifeInsure];
                }
                for (let i = 0; i < item.QuestionList.length; i++) {
                    if (this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][item.QuestionList[i].QuestionNo] === true) && (!this.state.AnsweredValueListMap[item.LifeInsure] || !this.state.AnsweredValueListMap[item.LifeInsure][item.QuestionList[i].QuestionNo])) {
                        answeredErrorList[item.QuestionList[i].QuestionNo] = "Vui lòng nhập thông tin chi tiết";
                        if (copyList.indexOf(item.LifeInsure) < 0) {
                            copyList.push(item.LifeInsure);
                            setSession(EXPAND_ID_LAPSE_LIST, JSON.stringify(copyList));
                        }
                        valid = false;
                    } else {
                        answeredErrorList[item.QuestionList[i].QuestionNo] = "";
                    }
                }
                answeredErrorListMap[item.LifeInsure] = answeredErrorList;

            }
            if ((this.state.uploadSelected === true) && (this.state.attachmentState.disabledButton)) {
                valid = false;
                errorUpload = 'Vui lòng cung cấp thêm hình ảnh chứng từ';
            }
            this.setState({
                AnsweredErrorListMap: answeredErrorListMap,
                errorUpload: errorUpload,
                expandIdLapseList: copyList
            });
            return valid;
        }

        const VerifySubmitInfo = () => {
            let valid = validateAnswers();
            if (!valid) {
                return;
            }
            GetTempFee();
            this.setState({stepName: FUND_STATE.VERIFICATION, errorUpload: ''});
        }

        const GetTempFee = () => {
            let submitRequest = {
                jsonDataInput: {
                    Action: "GetFeeReInstatement",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    PolicyNo: this.state.polID,
                    Company: COMPANY_KEY,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    OtpVerified: this.state.otp,
                }
            };
            onlineRequestSubmit(submitRequest)
                .then(res => {
                    if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Successfull.') && res.Response.Message) {
                        this.setState({tempFee: res.Response.Message});
                    } else {
                        this.setState({tempFee: '0'});
                    }
                }).catch(error => {
            });
        }
        const isPassAnwserList = () => {
            if (getMapSize(this.state.AnsweredListMap) <= 0) {
                return false;
            }
            if (!this.state.insuredLapseList || !this.state.insuredLapseList[0].Questionaire) {
                return false;
            }
            if (getMapSize(this.state.AnsweredListMap) < this.state.insuredLapseList[0].Questionaire.length) {//Nếu chưa chọn lời ít nhất 1 câu của tất cả LifeInsure
                return false;
            }
            for (let x in this.state.insuredLapseList[0].Questionaire) {
                let item = this.state.insuredLapseList[0].Questionaire[x];
                if (!this.state.AnsweredListMap[item.LifeInsure]) {
                    return false;
                }
                const nonEmptyElements = this.state.AnsweredListMap[item.LifeInsure].filter(element => element !== '' && element !== null && element !== undefined);
                const count = nonEmptyElements.length;
                if (count < item.QuestionList.length) { //Nếu chưa chọn hết tất cả câu trả lời thì
                    return false;
                }
                for (let i = 0; i < item.QuestionList.length; i++) {
                    if (this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][item.QuestionList[i].QuestionNo] === true)) {
                        if (getMapSize(this.state.AnsweredValueListMap) === 0) {
                            return false;
                        } else if (!this.state.AnsweredValueListMap[item.LifeInsure][item.QuestionList[i].QuestionNo]) {
                            return false;
                        }
                        
                    } 
                }
            }
            return true;
        }
        const isPassAttach = () => {
            if (this.state.uploadSelected === null) {
                return false;
            }
            if ((this.state.uploadSelected === true) && (this.state.attachmentState.disabledButton)) {
              return false;
            }
            return true;
        }

        const showCanvas = () => {
            document.getElementById('input-file-id').click();
        }

        const showUpload = () => {
            this.setState({uploadSelected: true});
        }

        const hideUpload = () => {
            this.setState({
                uploadSelected: false,
                attachmentState: {
                    previewVisible: false,
                    previewImage: "",
                    previewTitle: "",
                    attachmentList: [],
                    disabledButton: true,
                },
            });
        }

        const handleUploadChange = (event) => {
            const validateImage = ["image/jpeg", "image/jpg", "image/png"];
            let files = event.target.files;
            let fileSize = ((files[0].size / 1024) / 1024).toFixed(4);
            if (validateImage.includes(files[0].type) && fileSize <= 10) {
                let reader = new FileReader();
                reader.readAsDataURL(files[0]);

                reader.onload = (e) => {

                    this.setState({
                        selectedFile: e.target.result, errorUpload: ''
                    })
                }
            } else {
                this.setState({errorUpload: 'Chỉ được upload file jpeg/jpg/png không quá 10MB'});
            }

        }
        const handDragFileOver = (event) => {
            event.preventDefault();
            document.getElementById('img-upload-id').className = "img-upload active";
        }
        const handleDragFileLeave = (event) => {
            event.preventDefault();
            document.getElementById('img-upload-id').className = "img-upload";
        }

        const handleDropFile = (event) => {
            event.preventDefault();
            const validateImage = ["image/jpeg", "image/jpg", "image/png"];
            let files = Object.values(event.dataTransfer.files);
            let fileSize = ((files[0].size / 1024) / 1024).toFixed(4);
            if (validateImage.includes(files[0].type) && fileSize <= 10) {
                let reader = new FileReader();
                reader.readAsDataURL(files[0]);

                reader.onload = (e) => {

                    this.setState({
                        selectedFile: e.target.result, errorUpload: ''
                    })
                }
            } else {
                this.setState({errorUpload: 'Chỉ được upload file jpeg/jpg/png không quá 10MB'});
            }
        }

        const ReInstamentVerify = () => {
            // document.getElementById('user-rule-popup').className = 'popup special user-rule-popup show';
            this.setState({reInstamentVerify: true});
        }

        const CloseReInstamentVerify = () => {
            this.setState({reInstamentVerify: false});
        }

        const VerifyReInstamentSubmit = () => {
            const json = this.state;
            json.submitting = true;
            json.reInstamentVerify = false;
            this.setState(json); // close agreement.
            // PO_CONFIRMING_DECREE_13
            // if(this.state.poConfirmingND13 === ND13_CONFIRMING_STATUS.NEED){
            //     console.log('Show popup confirm ND 13')
            //     this.fetchCPConsentConfirmation(json.trackingId);
            //     this.callBackUpdateND13State(ND_13.ND13_INFO_FOLLOW_CONFIRMATION)
            //     setLocal(this.getLocalKey(json.polID), JSON.stringify(json));
            // }
            // else{
                //TODO: When the task has done. Remove the rems below.
            // this.setState({submitting: true, reInstamentVerify: false});
            // this.genOtp();
            
            //new method replace genOTP
            this.directToND13Contact();
            // }
        }

        const closeApiError = () => {
            this.setState({apiError: false});
        }

        const closeLapseAboveOneYear = () => {
            this.setState({lapseAboveOneYear: false});
        }

        const checkSubmitedIn24h = () => {
            // if (getSession(SUBMIT_IN_24_RIN)) {
            //     return;
            // }
            const jsonRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "CheckRequestSubmitND13",
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: "Web",
                    Project: "mcp",
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    RequestTypeID: "RIN",
                    PolicyNo: this.state.polID,
                    FromSystem: "DCW",
                    OtpVerified: this.state.otp,
                }
            };

            onlineRequestSubmit(jsonRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    // this.setState({submitIn24: true});
                    setSession(SUBMIT_IN_24_RIN + this.state.polID, SUBMIT_IN_24_RIN);
                    return;
                } else if (Response.Result === 'true' && Response.ErrLog === "DRAFT") {
                    setSession(RIN_DRAFT + this.state.polID, Response.Message);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                    this.setState({trackingId: Response.Message});
                    
                    loadND13DataTemp(getSession(USER_LOGIN), Response.Message, getSession(ACCESS_TOKEN), getDeviceId());
                } else {
                    removeSession(RIN_DRAFT + this.state.polID);
                    removeSession(SUBMIT_IN_24_RIN + this.state.polID);
                }
            }).catch(error => {
            });
        }

        const loadND13DataTemp = (userLogin, dKey, apiToken, deviceId) => {
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
                                this.setState({trackingId: dKey, appType: 'CLOSE', clientListStr: jsonState.clientListStr, proccessType: 'RIN', stepName: FUND_STATE.SDK})
                            }
                        } else {
                            removeSession(RIN_DRAFT + this.state.polID);
                        }
                    } 
                })
                .catch(error => {
                    console.log(error);
                });
        }
    
        let enableToVerify = false;
        if (this.state.updateFund && hasAValidFundInMap()) {
            enableToVerify = true;
        }
        let passAnswer = isPassAnwserList() && isPassAttach();
        if (this.state.updateReinstatement) {
            passAnswer = isPassAnwserList() && isPassAttach();
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
            if (this.state.updateFund) {
                msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện Chuyển đổi quỹ. Quý khách vui lòng kiểm tra lại.';
            } else if (this.state.updateInvestRate) {
                msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi tỷ lệ đầu tư. Quý khách vui lòng kiểm tra lại.';
            } else if (this.state.updateReinstatement) {
                msg = 'Quý khách không có hợp đồng cần khôi phục hiệu lực';
            } else if (this.state.updatePeriodicFee) {
                msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi định kỳ đóng phí. Quý khách vui lòng kiểm tra lại.';
                imgPath = 'img/popup/no-policy.svg';
            }
            imgPath = 'img/popup/no-policy.svg';
        }

        if (this.state.noPhone) {
            // msgPopup = 'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            msgPopup = 'Yêu cầu thay đổi này cần Số điện thoại di động để nhận mã xác thực. Quý khách vui lòng cập nhật thông tin trên ứng dụng hoặc liên hệ văn phòng Dai-ichi Life gần nhất.';
            popupImgPath = 'img/popup/no-phone.svg';
            if (this.state.updateReinstatement) {
                msgPopup = 'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            }
        }

        if (this.state.showNotice) {
            popupImgPath = 'img/popup/notice.png';
        }

        if (this.state.totalToFundError) {
            msgPopup = 'Tổng giá trị quỹ hiện tại không đủ 1.050.000 VNĐ để thực hiện yêu cầu.';
            popupImgPath = 'img/popup/invalid-fund.svg';
        }

        if (this.state.totalFundCalInTempError) {
            msgPopup = 'Tổng giá trị quỹ chuyển đi tạm tính không đủ 1.000.000 VNĐ. Quý khách vui lòng điều chỉnh tỷ lệ chuyển.';
            popupImgPath = 'img/popup/invalid-fund.svg';
        }

        if (this.state.percentError) {
            if (this.state.updateFund) {
                msgPopup = 'Tỷ lệ chuyển đi từ một Quỹ lớn hơn 100%. Quý khách vui lòng điều chỉnh lại.';
            } else if (this.state.updateInvestRate) {
                msgPopup = 'Tỷ lệ đầu tư mới khác 100%. Quý khách vui lòng nhập lại.';
            }

            popupImgPath = 'img/popup/invalid-fund.svg';
        }

        if (this.state.noChangeInvestRate) {
            msgPopup = 'Quý khách vui lòng nhập tỷ lệ đầu tư mới';
            popupImgPath = 'img/popup/invalid-fund.svg';
        }
        if (this.state.apiError) {
            msgPopup = 'Có lỗi xảy ra, vui lòng thử lại';
            popupImgPath = 'img/popup/quyenloi-popup.svg';
        }
        if (this.state.lapseAboveOneYear) {
            msgPopup = 'Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Văn phòng Dai-ichi Life Việt Nam gần nhất để được hướng dẫn thủ tục khôi phục hiệu lực hợp đồng';
            popupImgPath = 'img/popup/quyenloi-popup.svg';
        }

        if (this.state.submitIn24) {
            msgPopup = 'Hợp đồng này đã có yêu cầu khôi <br/> phục hợp đồng. Quý khách có muốn <br/> tiếp tục tạo yêu cầu không?';
            popupImgPath = '../../img/popup/submited-in-24.svg';
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
                    <meta property="og:url" content={FE_BASE_URL + "/reinstatement"}/>
                    <meta property="og:title" content="Điều chỉnh thông tin hợp đồng - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/reinstatement"}/>
                </Helmet>
                <main className="logined" id="main-id">
                    <div className="main-warpper basic-mainflex">
                        <section className="qlbh-sccard-wrapper sccard-warpper hopdong-sccard-wrapper"
                                 style={{paddingLeft: '0', paddingRight: '0'}}>
                            <div className="stepform stepform-42" style={{marginTop: '0px'}}>
                                <div className="stepform__body" style={{paddingTop: '4px', boxShadow: 'none'}}>
                                    <div className="info">
                                        <div className="info__title" style={{marginBottom: '0px'}}>
                                            <h4 className="basic-uppercase">chọn loại thông tin</h4>
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
                                                                               checked={this.state.updateReinstatement}
                                                                               onClick={() => radioReinstatement()}
                                                                               id='radioReinstatement'/>
                                                                        <div className="checkmark"></div>
                                                                        <p className="big-text"
                                                                           style={{marginLeft: '3px'}}>Khôi phục hiệu
                                                                            lực hợp đồng</p>
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
                                    <p>Khôi phục hiệu lực</p>
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
                                                <p>Khôi phục hiệu lực</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                        </div>
                                        <LoadingIndicator area="policyList-by-cliID"/>
                                        <div className="heading__tab">
                                            <div className="step-container">
                                                <div className="step-wrapper" style={{marginLeft: '16px'}}>
                                                    {(this.state.stepName > FUND_STATE.CATEGORY_INFO) && (this.state.stepName < FUND_STATE.SDK) && !this.state.allLIAgree && !this.state.aLINotAgree &&
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
                                    </div>
                                </div>
                                {this.state.stepName === FUND_STATE.CATEGORY_INFO && 
                                    (this.state.timeOut) &&
                                        <ReloadScreen  getPolicyList = {(e) => checkPolicyLaspe(e)} isNonePaymentList = {this.state.isNonePaymentList}
                                        isReloadPaymentList = {this.state.isReloadPaymentList} isRetrying = {this.state.isRetrying} msg = {'Thông tin chi tiết sẽ hiển thị khi bạn chọn một thông tin ở bên trái.'} />
                                    }
                                {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                                    this.state.updateReinstatement? (
                                        <section style={{
                                            width: '100%',
                                            maxWidth: '600px',
                                            borderRadius: '8px',
                                            height: '100%',
                                            paddingTop: '175px'
                                        }}>
                                            <div className="personform">
                                                <h5 className="basic-bold basic-text-upper">chọn hợp đồng áp dụng</h5>

                                                <div className="card-warpper"
                                                     style={{marginBottom: '-5px', marginTop: '8px'}}>
                                                    {this.state.polListLapse && this.state.polListLapse.map((item, index) => (
                                                        <div className="item" style={{padding: '6px 0'}}
                                                             key={"fund-policy" + index}>
                                                            <div
                                                                className={(this.state.polID === item.PolicyID) ? "card choosen" : "card"}
                                                                onClick={() => showCardInfoLapse(item.PolicyID, item.CountDayLapse, item.YearLapse, item.PolExpiryDate, item)}
                                                                id={index}>
                                                                <div className="card__header">
                                                                    <h4 className="basic-bold">{item.ProductName}</h4>
                                                                    <p>Dành
                                                                        cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (formatFullName(item.PolicyLIName)) : ''}</p>
                                                                    <div className="d-flex" style={{justifyContent: 'space-between'}}>
                                                                    {item.PolicyStatus.length < 25 ?
                                                                        <p>Hợp đồng: {item.PolicyID}</p> :
                                                                        <p className="policy">Hợp
                                                                            đồng: {item.PolicyID}</p>}
                                                                    {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
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
                                                                        <p>Ngày mất hiệu lực</p>
                                                                        <p>{item.PolExpiryDate}</p>
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
                                                <img className="decor-clip" src="img/mock.svg" alt=""/>
                                                <img className="decor-person" src="img/person.png" alt=""/>
                                            </div>
                                            <div className="other_option" id="other-option-toggle"
                                                 onClick={() => goBack()}>
                                                <p>Tiếp tục</p>
                                                <i><img src="img/icon/arrow-left.svg" alt=""/></i>
                                            </div>
                                        </section>
                                    ) : (
                                        this.state.updatePeriodicFee?(
                                            <ChangePayMode 
                                            appType={this.state.appType}
                                            trackingId={this.state.trackingId}
                                            clientListStr={this.state.clientListStr??getSession(CLIENT_ID)}
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
                                        ):(
                                            this.state.decreaseSACancelRider?(
                                                <DecreaseSACancelRider 
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
                                            ): (
                                                this.state.updateLoan?(
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
                                                ): (
                                                <section style={{
                                                    width: '100%',
                                                    maxWidth: '600px',
                                                    borderRadius: '8px',
                                                    height: '100%',
                                                    paddingTop: '175px'
                                                }}>
                                                    <div className="personform">
                                                        <h5 className="basic-bold basic-text-upper">chọn hợp đồng áp dụng</h5>
        
                                                        <div className="card-warpper" style={{marginBottom: '-22px'}}>
                                                            {this.state.polListProfile && this.state.polListProfile.map((item, index) => (
                                                                <div className="item" style={{padding: '6px 0'}}
                                                                     key={"fund-policy" + index}>
                                                                    <div
                                                                        className={(this.state.polID === item.PolicyID) ? "card choosen" : "card"}
                                                                        onClick={() => showCardInfo(item.PolicyID, index, item.AgentName, item.PolicyClassCD)}
                                                                        id={index}>
                                                                        <div className="card__header">
                                                                            <h4 className="basic-bold">{item.ProductName}</h4>
                                                                            <p>Dành
                                                                                cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (formatFullName(item.PolicyLIName)) : ''}</p>
                                                                            {item.PolicyStatus.length < 25 ?
                                                                                <p>Hợp đồng: {item.PolicyID}</p> :
                                                                                <p className="policy">Hợp
                                                                                    đồng: {item.PolicyID}</p>}
                                                                            {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                                                <div className="dcstatus">
                                                                                    <p className="inactive">{item.PolicyStatus}</p>
                                                                                </div>) : (
                                                                                <div className="dcstatus">
                                                                                    {item.PolicyStatus.length < 25 ?
                                                                                        <p className="active">{item.PolicyStatus}</p> :
                                                                                        <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                                                </div>
                                                                            )}
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
                                                        <img className="decor-clip" src="img/mock.svg" alt=""/>
                                                        <img className="decor-person" src="img/person.png" alt=""/>
                                                    </div>
                                                    <div className="other_option" id="other-option-toggle"
                                                         onClick={() => goBack()}>
                                                        <p>Tiếp tục</p>
                                                        <i><img src="img/icon/arrow-left.svg" alt=""/></i>
                                                    </div>
                                                </section>
                                                )
                                            )
                                        )

                                    )

                                ) : (
                                    this.state.updateFund ? (

                                        this.state.stepName === FUND_STATE.UPDATE_INFO ? (

                                            this.state.PolicyInfo &&
                                            <div className='stepform'>
                                                <div className="an-hung-page">
                                                    <div className="bo-hop-dong-container">
                                                        <div className="title" style={{paddingRight: '12px'}}>
                                                            <h4 className='basic-quy-top-padding basic-text-upper'>nhập
                                                                thông tin điều chỉnh</h4>
                                                            <i className="info-icon" onClick={() => showNotice()}
                                                            ><img src={FE_BASE_URL + "/img/icon/Information-step.svg"}
                                                                  alt="information-icon" className="info-icon"
                                                            /></i>
                                                        </div>
                                                        {this.state.PolicyInfo.map((item, index) => {
                                                            let fundName = item.FundNameCode.split(';')[1].replaceAll("Qu? T?ng tr??ng", "Quỹ Tăng trưởng").replaceAll("Qu? Phát tri?n", "Quỹ Phát triển").replaceAll('Quỹ Bảo tồn', 'Quỹ Bảo toàn').trim();
                                                            let fundId = item.fund_id.split(';')[1].trim();
                                                            let units = item.fia_unit_cum_qty.split(';')[1] ? parseFloat(item.fia_unit_cum_qty.split(';')[1].replaceAll('.', '').replaceAll(',', '.')) : 0;
                                                            let price = item.fia_unit_pric_amt.split(';')[1] ? parseFloat(item.fia_unit_pric_amt.split(';')[1].replaceAll('.', '').replaceAll(',', '.')) : 0;
                                                            let fundValue = units * price;
                                                            return (
                                                                <div
                                                                    className={index === 0 ? 'card-item-width-adjsut' : 'card-item-width-adjsut top-dash-line'}
                                                                    key={"fund-item-" + index}>
                                                                    <div
                                                                        className={(this.state.currentFundsExpandMap[fundId] === false) ? "dropdown" : "dropdown show"}
                                                                        id={'FundDetails' + index}
                                                                        style={{padding: '0px'}}>
                                                                        <div className="dropdown__content"
                                                                             onClick={() => dropdownFundDetails(fundId, 'FundDetails' + index)}>
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 0px 0px 12px',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272',
                                                                                fontWeight: '600'
                                                                            }}>{fundName}</p>
                                                                            {this.state.currentFundsExpandMap[fundId] === false ?
                                                                                <p className="feeDetailsArrow" style={{
                                                                                    padding: '20px 20px 0px',
                                                                                    right: '12px'
                                                                                }}><img
                                                                                    src="../img/icon/arrow-left-grey.svg"
                                                                                    alt="arrow-left-icon"
                                                                                    style={{right: '0px'}}/></p> :
                                                                                <p className="feeDetailsArrowDown"
                                                                                   style={{
                                                                                       padding: '20px 20px 0px',
                                                                                       right: '12px'
                                                                                   }}><img
                                                                                    src="../img/icon/arrow-down-grey.svg"
                                                                                    alt="arrow-down-icon"
                                                                                    style={{right: '0px'}}/></p>}
                                                                        </div>
                                                                        <div
                                                                            className={!this.state.currentFundsExpandMap[fundId] ? "card__footer dropdown__items" : "card__footer dropdown__items2"}>
                                                                            <div className="card__footer-item">
                                                                                <p style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>{item.cdi_alloc_pct.split(';')[0]}</p>
                                                                                <p style={{paddingRight: '0px'}}>{item.cdi_alloc_pct.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>{item.fia_unit_cum_qty.split(';')[0]}</p>
                                                                                <p style={{paddingRight: '0px'}}>{item.fia_unit_cum_qty.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>{item.fia_unit_pric_amt.split(';')[0]}</p>
                                                                                <p style={{paddingRight: '0px'}}>{item.fia_unit_pric_amt.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>{item.fund_val_each_funf.split(';')[0]}</p>
                                                                                <p style={{paddingRight: '0px'}}>{item.fund_val_each_funf.split(';')[1]}</p>
                                                                            </div>

                                                                        </div>

                                                                    </div>
                                                                    {this.state.showFundDetailsMap[fundId] &&
                                                                        <div className="fund__body-content"
                                                                             key={"fund-tab" + index}>
                                                                            {this.state.currentFundsExchangeItemsMap[fundId] &&
                                                                                this.state.currentFundsExchangeItemsMap[fundId].map((exItem, idx) => {
                                                                                    return (
                                                                                        <div
                                                                                            key={"sub-fund-tab" + index + "-" + idx}>
                                                                                            {this.state.currentFundsExchangeItemsMap[fundId].length > 1 ? (
                                                                                                <div
                                                                                                    className='fund-title'>
                                                                                                    <p className='basic-semibold'>Quỹ
                                                                                                        chuyển đến</p>
                                                                                                    <i className="circle-minus"
                                                                                                       onClick={() => removeExchangeFund(fundId, exItem.id)}>
                                                                                                        <img
                                                                                                            src="img/icon/minus.svg"
                                                                                                            alt="minus"/>
                                                                                                    </i>
                                                                                                </div>
                                                                                            ) : (
                                                                                                <div
                                                                                                    className='fund-title-top'>
                                                                                                    <p className='basic-semibold'>Quỹ
                                                                                                        chuyển đến</p>
                                                                                                </div>
                                                                                            )

                                                                                            }


                                                                                            <div
                                                                                                className="tab-warpper">
                                                                                                <div className="tab">
                                                                                                    <div
                                                                                                        className={exItem.show ? "special-dropdown show" : "special-dropdown"}
                                                                                                        id={"fund-dropdown-list" + index + "-" + idx}>
                                                                                                        <div
                                                                                                            className="dropdown__content">
                                                                                                            <div
                                                                                                                className="tab-content"
                                                                                                                onClick={() => toggleFundList(fundId, exItem.id)}>
                                                                                                                {exItem.fundName !== '' ? (
                                                                                                                    <p>{exItem.fundName}</p>
                                                                                                                ) : (
                                                                                                                    <p>Chọn
                                                                                                                        Quỹ</p>
                                                                                                                )}


                                                                                                                <i className="icon-down"><img
                                                                                                                    src="img/icon/arrow-down.svg"
                                                                                                                    alt=""/></i>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div
                                                                                                            className="dropdown__items">
                                                                                                            <div
                                                                                                                className="tab-item-warpper">
                                                                                                                <div
                                                                                                                    className="tab-head">
                                                                                                                    <p className="basic-semibold">Chọn
                                                                                                                        Quỹ</p>
                                                                                                                    <i className="close"><img
                                                                                                                        src="img/icon/close-icon.svg"
                                                                                                                        alt=""/></i>
                                                                                                                </div>
                                                                                                                {this.state.remainFunds.map((fund, index) => {
                                                                                                                    return (
                                                                                                                        this.state.currentFundsExchangeItemsMap[fundId] && findFundIdInToFundArray(fund, this.state.currentFundsExchangeItemsMap[fundId]) >= 0 ? (
                                                                                                                            <div
                                                                                                                                className="tab-item choosen"
                                                                                                                                data-tab={item}
                                                                                                                                key={"fund-" + index + "-" + idx}>
                                                                                                                                <p>{fund}</p>
                                                                                                                                <i className='basic-text2 simple-brown2'>Đã
                                                                                                                                    chuyển</i>
                                                                                                                            </div>
                                                                                                                        ) : (
                                                                                                                            <div
                                                                                                                                className="tab-item"
                                                                                                                                data-tab={item}
                                                                                                                                key={"fund-" + index + "-" + idx}
                                                                                                                                onClick={() => chooseAFund(fundId, fund, exItem.id)}>
                                                                                                                                <p>{fund}</p>
                                                                                                                                <i className='basic-text2 simple-brown2'></i>
                                                                                                                            </div>
                                                                                                                        )

                                                                                                                    )
                                                                                                                })}

                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div
                                                                                                            className="bg-dropdown"></div>
                                                                                                        <div
                                                                                                            className="input fund">
                                                                                                            <div
                                                                                                                className="input__content">
                                                                                                                {exItem.percent > 0 &&
                                                                                                                    <label
                                                                                                                        htmlFor="">Tỷ
                                                                                                                        lệ
                                                                                                                        số
                                                                                                                        đơn
                                                                                                                        vị
                                                                                                                        Quỹ
                                                                                                                        chuyển</label>
                                                                                                                }
                                                                                                                <NumberFormat
                                                                                                                    id='txtInputPercent'
                                                                                                                    placeholder='Tỷ lệ số đơn vị Quỹ chuyển'
                                                                                                                    allowNegative={false}
                                                                                                                    isAllowed={withValueCap}
                                                                                                                    value={exItem.percent > 0 ? exItem.percent : ''}
                                                                                                                    onValueChange={(obj) => enterPercent(fundId, exItem.id, fundValue, obj)}/>
                                                                                                            </div>
                                                                                                            <i className='basic-text2'>%</i>
                                                                                                        </div>
                                                                                                        <div
                                                                                                            className="input disabled fund">
                                                                                                            <div
                                                                                                                className="input__content">
                                                                                                                <label
                                                                                                                    htmlFor="">Đơn
                                                                                                                    vị
                                                                                                                    chuyển
                                                                                                                    tạm
                                                                                                                    tính</label>
                                                                                                                <NumberFormat
                                                                                                                    value={exItem.value}
                                                                                                                    thousandSeparator={'.'}
                                                                                                                    decimalSeparator={','}
                                                                                                                    suffix={' VNĐ'}
                                                                                                                    placeholder='Đơn vị chuyển tạm tính'
                                                                                                                    disabled/>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>

                                                                                            </div>
                                                                                        </div>
                                                                                    )
                                                                                })}

                                                                            {this.state.currentFundsExchangeItemsMap[fundId] && (this.state.currentFundsExchangeItemsMap[fundId].length < allFunds.length) && !hasEmptyFund(this.state.currentFundsExchangeItemsMap[fundId]) ? (
                                                                                <div className="fund-tab"
                                                                                     style={{alignItems: 'flex-start'}}
                                                                                     onClick={() => addExchangeFund(fundId)}>
                                                                                    <button className="add-address"
                                                                                            id="add-new-address">
                                                                                        <i className="circle-plus">
                                                                                            <img src="img/icon/plus.svg"
                                                                                                 alt="circle-plus"
                                                                                                 className="plus-sign"/>
                                                                                        </i>
                                                                                        <span
                                                                                            className="basic-semibold">Thêm Quỹ chuyển đến</span>
                                                                                    </button>
                                                                                </div>
                                                                            ) : (
                                                                                <div className="fund-tab"
                                                                                     style={{alignItems: 'flex-start'}}>
                                                                                    <button className="add-address"
                                                                                            id="add-new-address">
                                                                                        <i className="circle-plus">
                                                                                            <img
                                                                                                src="img/icon/plus-disable.svg"
                                                                                                alt="circle-plus"
                                                                                                className="plus-sign"/>
                                                                                        </i>
                                                                                        <span
                                                                                            className="basic-semibold">Thêm Quỹ chuyển đến</span>
                                                                                    </button>
                                                                                </div>
                                                                            )}

                                                                        </div>

                                                                    }
                                                                </div>
                                                            )
                                                        })}


                                                        <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                        <img className="decor-person" src="../img/person.png" alt=""/>

                                                    </div>

                                                </div>
                                            </div>


                                        ) : (
                                            this.state.stepName === FUND_STATE.VERIFICATION &&
                                            <div className='stepform'>
                                                <div className="an-hung-page">
                                                    <div className="bo-hop-dong-container">
                                                        <div className="title" style={{paddingRight: '12px'}}>
                                                            <h4 className='basic-quy-top-padding basic-text-upper'>xác
                                                                nhận thông tin điều chỉnh</h4>
                                                            <i className="info-icon" onClick={() => showNotice()}
                                                            ><img src={FE_BASE_URL + "/img/icon/Information-step.svg"}
                                                                  alt="information-icon" className="info-icon"
                                                            /></i>
                                                        </div>

                                                        {this.state.currentFundsExchangeItemsMap && getMapSize(this.state.currentFundsExchangeItemsMap) > 0 && toKeys(this.state.currentFundsExchangeItemsMap).map((fundId, index) => {
                                                            return (
                                                                <div className="fund__body-content"
                                                                     key={"fund-tab-verify" + index}>
                                                                    {this.state.currentFundsExchangeItemsMap[fundId] && (this.state.currentFundsExchangeItemsMap[fundId].length < allFunds.length) && hasAValidFund(this.state.currentFundsExchangeItemsMap[fundId]) &&
                                                                        <>
                                                                            <h5>Từ {allFundsMap[fundId]}</h5>
                                                                            <br/><br/>
                                                                        </>
                                                                    }

                                                                    {this.state.currentFundsExchangeItemsMap[fundId] &&
                                                                        this.state.currentFundsExchangeItemsMap[fundId].map((exItem, idx) => {
                                                                            if (exItem.fundName && exItem.percent > 0) {
                                                                                return (
                                                                                    <div className='fund-content'
                                                                                         key={"sub-fund-tab-verify" + index + "-" + idx}>
                                                                                        <div className='fund-title-top'>
                                                                                            <p className='basic-semibold'>Đến
                                                                                                Quỹ</p>
                                                                                        </div>
                                                                                        <div className="tab-warpper">
                                                                                            <div className="tab">

                                                                                                <div
                                                                                                    className="input disabled fund">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            value={exItem.fundName}
                                                                                                            disabled/>
                                                                                                    </div>
                                                                                                </div>


                                                                                                <div
                                                                                                    className="input disabled fund">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <label
                                                                                                            htmlFor="">Tỷ
                                                                                                            lệ số đơn vị
                                                                                                            Quỹ
                                                                                                            chuyển</label>
                                                                                                        <NumberFormat
                                                                                                            id='txtInputPercentVerify'
                                                                                                            value={exItem.percent}
                                                                                                            disabled/>
                                                                                                    </div>
                                                                                                    <i className='basic-text2'>%</i>
                                                                                                </div>

                                                                                                <div
                                                                                                    className="input disabled fund">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <label
                                                                                                            htmlFor="">Đơn
                                                                                                            vị chuyển
                                                                                                            tạm
                                                                                                            tính</label>
                                                                                                        <NumberFormat
                                                                                                            value={exItem.value}
                                                                                                            thousandSeparator={'.'}
                                                                                                            decimalSeparator={','}
                                                                                                            suffix={' VNĐ'}
                                                                                                            disabled/>
                                                                                                    </div>
                                                                                                </div>

                                                                                            </div>

                                                                                        </div>
                                                                                    </div>
                                                                                )
                                                                            }

                                                                        })}

                                                                </div>
                                                            )

                                                        })}


                                                        <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                        <img className="decor-person" src="../img/person.png" alt=""/>

                                                    </div>

                                                </div>
                                            </div>
                                        )

                                    ) : (
                                        this.state.updateInvestRate ? (
                                            this.state.stepName === FUND_STATE.UPDATE_INFO ? (

                                                <div className='stepform' style={{marginTop: '175px'}}>
                                                    <div className="an-hung-page" style={{padding: '0 10px'}}>
                                                        <div className="bo-hop-dong-container">
                                                            <div className="title"
                                                                 style={{paddingLeft: '16px', paddingBottom: '0px'}}>
                                                                <h4 className='basic-quy-top-padding basic-text-upper'>nhập
                                                                    thông tin điều chỉnh</h4>
                                                                <i className="info-icon" onClick={() => showNotice()}
                                                                ><img
                                                                    src={FE_BASE_URL + "/img/icon/Information-step.svg"}
                                                                    alt="information-icon" className="info-icon"
                                                                /></i>
                                                            </div>
                                                            {this.state.PolicyInfo && this.state.PolicyInfo.map((item, index) => {
                                                                let fundName = item.FundNameCode.split(';')[1].replaceAll("Qu? T?ng tr??ng", "Quỹ Tăng trưởng").replaceAll("Qu? Phát tri?n", "Quỹ Phát triển").replaceAll('Quỹ Bảo tồn', 'Quỹ Bảo toàn').trim();
                                                                let fundId = item.fund_id.split(';')[1].trim();
                                                                let units = item.fia_unit_cum_qty.split(';')[1] ? parseFloat(item.fia_unit_cum_qty.split(';')[1].replaceAll('.', '').replaceAll(',', '.')) : 0;
                                                                let price = item.fia_unit_pric_amt.split(';')[1] ? parseFloat(item.fia_unit_pric_amt.split(';')[1].replaceAll('.', '').replaceAll(',', '.')) : 0;
                                                                let fundValue = units * price;
                                                                return (
                                                                    <div
                                                                        className={index === 0 ? 'card-item-width-adjsut' : 'card-item-width-adjsut top-dash-line'}
                                                                        key={"fund-item-" + index}>
                                                                        <div
                                                                            className={this.state.currentFundsExpandMap[fundId] === false ? "dropdown" : "dropdown show"}
                                                                            id={'FundDetails' + index}
                                                                            style={{padding: '0px'}}>
                                                                            <div className="dropdown__content"
                                                                                 onClick={() => dropdownFundDetails(fundId, 'FundDetails' + index)}>
                                                                                <p className="card-dropdown-title"
                                                                                   style={{
                                                                                       padding: (index === 0) ? '12px 0px 8px 2px' : '20px 0px 8px 2px',
                                                                                       fontSize: '1.6rem'
                                                                                   }}>{allFundsMap[fundId]}</p>
                                                                                {this.state.currentFundsExpandMap[fundId] === false ?
                                                                                    <p className="feeDetailsArrow"
                                                                                       style={{
                                                                                           padding: '0 20px 8px',
                                                                                           right: '12px',
                                                                                           marginTop: '-4px'
                                                                                       }}><img
                                                                                        src="../img/icon/arrow-left-grey.svg"
                                                                                        alt="arrow-left-icon"
                                                                                        style={{right: '0px'}}/></p> :
                                                                                    <p className="feeDetailsArrowDown"
                                                                                       style={{
                                                                                           padding: '10px 20px 0px',
                                                                                           right: '12px',
                                                                                           marginTop: '-4px'
                                                                                       }}><img
                                                                                        src="../img/icon/arrow-down-grey.svg"
                                                                                        alt="arrow-down-icon"
                                                                                        style={{right: '0px'}}/></p>}
                                                                            </div>
                                                                            <div
                                                                                className={!this.state.currentFundsExpandMap[fundId] ? "card__footer dropdown__items" : "card__footer dropdown__items2"}
                                                                                style={{
                                                                                    paddingBottom: '0px',
                                                                                    paddingLeft: '0px'
                                                                                }}>
                                                                                <div className="card__footer-item">
                                                                                    <p style={{
                                                                                        display: 'flex',
                                                                                        alignItems: 'center',
                                                                                        color: '#727272'
                                                                                    }}>{item.cdi_alloc_pct.split(';')[0]}</p>
                                                                                    <p style={{paddingRight: '0px'}}>{item.cdi_alloc_pct.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item">
                                                                                    <p style={{
                                                                                        display: 'flex',
                                                                                        alignItems: 'center',
                                                                                        color: '#727272'
                                                                                    }}>Số lượng đơn vị Quỹ</p>
                                                                                    <p style={{paddingRight: '0px'}}>{item.fia_unit_cum_qty.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item">
                                                                                    <p style={{
                                                                                        display: 'flex',
                                                                                        alignItems: 'center',
                                                                                        color: '#727272'
                                                                                    }}>{item.fia_unit_pric_amt.split(';')[0]}</p>
                                                                                    <p style={{paddingRight: '0px'}}>{item.fia_unit_pric_amt.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item">
                                                                                    <p style={{
                                                                                        display: 'flex',
                                                                                        alignItems: 'center',
                                                                                        color: '#727272'
                                                                                    }}>{item.fund_val_each_funf.split(';')[0]}</p>
                                                                                    <p style={{paddingRight: '0px'}}>{item.fund_val_each_funf.split(';')[1]}</p>
                                                                                </div>

                                                                            </div>

                                                                        </div>
                                                                        {this.state.showFundDetailsMap[fundId] &&
                                                                            <div className="fund__body-content"
                                                                                 key={"fund-tab" + index}
                                                                                 style={{paddingLeft: '0px'}}>
                                                                                {this.state.currentFundsChangeMap[fundId] &&

                                                                                    <div key={"sub-fund-tab" + index}>
                                                                                        <div className='fund-title-top'>
                                                                                            <p className='basic-semibold'>Thay
                                                                                                đổi tỷ lệ đầu tư</p>
                                                                                        </div>


                                                                                        <div className="tab-warpper"
                                                                                             style={{marginTop: '0'}}>
                                                                                            <div className="tab">
                                                                                                <div
                                                                                                    className="special-dropdown show"
                                                                                                    id={"fund-dropdown-list" + index}>
                                                                                                    <div
                                                                                                        className={(this.state.currentFundsChangeMap[fundId].percent % 5 !== 0) ? "input fund basic-border-red" : "input fund"}>
                                                                                                        <div
                                                                                                            className="input__content">
                                                                                                            {this.state.currentFundsChangeMap[fundId].percent > 0 &&
                                                                                                                <label
                                                                                                                    htmlFor="">Tỷ
                                                                                                                    lệ
                                                                                                                    đầu
                                                                                                                    tư
                                                                                                                    mới</label>
                                                                                                            }
                                                                                                            <NumberFormat
                                                                                                                id='txtInputPercent'
                                                                                                                placeholder={(this.state.currentFundsChangeMap[fundId].percent === '') ? 'Tỷ lệ đầu tư mới' : '0%'}
                                                                                                                allowNegative={false}
                                                                                                                isAllowed={withValueCap}
                                                                                                                value={((this.state.currentFundsChangeMap[fundId].percent === '') || (this.state.currentFundsChangeMap[fundId].percent === 0)) ? '' : this.state.currentFundsChangeMap[fundId].percent}
                                                                                                                suffix={((this.state.currentFundsChangeMap[fundId].percent === '') || (this.state.currentFundsChangeMap[fundId].percent === 0)) ? '' : '%'}
                                                                                                                onValueChange={(obj) => enterPercentChangeItem(fundId, this.state.currentFundsChangeMap[fundId].value, obj)}
                                                                                                                onClick={(this.state.currentFundsChangeMap[fundId].percent === '') ? ((obj) => enterPercentChangeItem(fundId, this.state.currentFundsChangeMap[fundId].value, obj)) : ''}/>
                                                                                                        </div>
                                                                                                        {this.state.currentFundsChangeMap[fundId].percent === '' &&
                                                                                                            <img
                                                                                                                src="../../img/icon/edit.svg"
                                                                                                                alt=""/>
                                                                                                        }
                                                                                                    </div>
                                                                                                    {(this.state.currentFundsChangeMap[fundId].percent % 5 !== 0) &&
                                                                                                        <span style={{
                                                                                                            color: 'red',
                                                                                                            margin: '0 0 8px 0'
                                                                                                        }}>Tỷ lệ đầu tư là bội số của 5</span>
                                                                                                    }
                                                                                                </div>
                                                                                            </div>

                                                                                        </div>
                                                                                    </div>


                                                                                }
                                                                            </div>
                                                                        }
                                                                    </div>
                                                                )
                                                            })}
                                                            {this.state.remainFundsItems && this.state.remainFundsItems.map((item, index) => {
                                                                return (
                                                                    <div
                                                                        className='card-item-width-adjsut top-dash-line'
                                                                        key={"remain-fund-item-" + index}>
                                                                        <div
                                                                            className={!this.state.currentFundsExpandMap[item.id] ? "dropdown" : "dropdown show"}
                                                                            id={'RemainFundDetails' + index}
                                                                            style={{padding: '0px'}}>
                                                                            <div className="dropdown__content"
                                                                                 onClick={() => dropdownFundDetails(item.id, 'RemainFundDetails' + index)}>
                                                                                <p className="card-dropdown-title"
                                                                                   style={{
                                                                                       padding: '20px 0px 8px 0px',
                                                                                       fontSize: '1.6rem'
                                                                                   }}>{item.fundName}</p>
                                                                                {!this.state.currentFundsExpandMap[item.id] ?
                                                                                    <p className="feeDetailsArrow"
                                                                                       style={{
                                                                                           padding: '20px 20px 0px',
                                                                                           right: '12px'
                                                                                       }}><img
                                                                                        src="../img/icon/arrow-left-grey.svg"
                                                                                        alt="arrow-left-icon"
                                                                                        style={{right: '0px'}}/></p> :
                                                                                    <p className="feeDetailsArrowDown"
                                                                                       style={{
                                                                                           padding: '20px 20px 0px',
                                                                                           right: '12px'
                                                                                       }}><img
                                                                                        src="../img/icon/arrow-down-grey.svg"
                                                                                        alt="arrow-down-icon"
                                                                                        style={{right: '0px'}}/></p>}
                                                                            </div>
                                                                            <div
                                                                                className="card__footer dropdown__items"
                                                                                style={{
                                                                                    paddingBottom: '0px',
                                                                                    paddingLeft: '0px'
                                                                                }}>
                                                                                <div className="card__footer-item">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        {this.state.showFundDetailsMap[item.id] &&
                                                                            <div className="fund__body-content"
                                                                                 key={"remain-fund-tab" + index}
                                                                                 style={{paddingLeft: '0px'}}>

                                                                                <div
                                                                                    key={"remain-sub-fund-tab" + index}>
                                                                                    <div className='fund-title-top'>
                                                                                        <p className='basic-semibold'>Thay
                                                                                            đổi tỷ lệ đầu tư</p>
                                                                                    </div>


                                                                                    <div className="tab-warpper"
                                                                                         style={{marginTop: '0'}}>
                                                                                        <div className="tab">
                                                                                            <div
                                                                                                className="special-dropdown show"
                                                                                                id={"remain-fund-dropdown-list" + index}>
                                                                                                <div
                                                                                                    className="input fund">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        {item.percent > 0 &&
                                                                                                            <label
                                                                                                                htmlFor="">Tỷ
                                                                                                                lệ đầu
                                                                                                                tư
                                                                                                                mới</label>
                                                                                                        }
                                                                                                        <NumberFormat
                                                                                                            id='txtInputPercent'
                                                                                                            placeholder={(item.percent === '') ? 'Tỷ lệ đầu tư mới' : '0%'}
                                                                                                            allowNegative={false}
                                                                                                            isAllowed={withValueCap}
                                                                                                            value={((item.percent === '') || (item.percent === 0)) ? '' : item.percent}
                                                                                                            suffix={((item.percent === '') || (item.percent === 0)) ? '' : '%'}
                                                                                                            onValueChange={(obj) => enterPercentChangeRemainItem(item.id, item.value, obj)}
                                                                                                            onClick={(item.percent === '') ? ((obj) => enterPercentChangeRemainItem(item.id, item.value, obj)) : ''}/>
                                                                                                    </div>
                                                                                                    {item.percent === '' &&
                                                                                                        <img
                                                                                                            src="../../img/icon/edit.svg"
                                                                                                            alt=""/>
                                                                                                    }
                                                                                                </div>
                                                                                                {(item.percent % 5 !== 0) &&
                                                                                                    <span style={{
                                                                                                        color: 'red',
                                                                                                        margin: '0 0 8px 0'
                                                                                                    }}>Tỷ lệ đầu tư là bội số của 5</span>
                                                                                                }
                                                                                            </div>
                                                                                        </div>

                                                                                    </div>
                                                                                </div>
                                                                            </div>


                                                                        }
                                                                    </div>
                                                                )
                                                            })}

                                                            <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                            <img className="decor-person" src="../img/person.png"
                                                                 alt=""/>

                                                        </div>

                                                    </div>
                                                </div>


                                            ) : (
                                                this.state.stepName === FUND_STATE.VERIFICATION &&
                                                <div className='stepform' style={{marginTop: '175px'}}>
                                                    <div className="an-hung-page" style={{padding: '0 10px'}}>
                                                        <div className="bo-hop-dong-container">
                                                            <div className="title" style={{paddingLeft: '12px'}}>
                                                                <h4 className='basic-quy-top-padding basic-text-upper'>xác
                                                                    nhận thông tin điều chỉnh</h4>
                                                                <i className="info-icon" onClick={() => showNotice()}
                                                                ><img
                                                                    src={FE_BASE_URL + "/img/icon/Information-step.svg"}
                                                                    alt="information-icon" className="info-icon"
                                                                /></i>
                                                            </div>

                                                            {this.state.currentFundsChangeMap && getMapSize(this.state.currentFundsChangeMap) > 0 && toKeys(this.state.currentFundsChangeMap).map((fundId, index) => {
                                                                return (
                                                                    <div className="fund__body-content"
                                                                         key={"fund-tab-verify" + index}>
                                                                        {this.state.currentFundsChangeMap[fundId] &&
                                                                            <>
                                                                                <h5>{allFundsMap[fundId]}</h5>
                                                                                <div className='fund-content'
                                                                                     key={"sub-fund-tab-verify" + index}>
                                                                                    <div className="tab-warpper">
                                                                                        <div className="tab">


                                                                                            <div
                                                                                                className="input disabled fund">
                                                                                                <div
                                                                                                    className="input__content">
                                                                                                    <label htmlFor="">Tỷ
                                                                                                        lệ đầu tư
                                                                                                        mới</label>
                                                                                                    <NumberFormat
                                                                                                        id='txtInputPercentVerify'
                                                                                                        suffix={'%'}
                                                                                                        value={(this.state.currentFundsChangeMap[fundId].percent !== '') ? this.state.currentFundsChangeMap[fundId].percent : 0}
                                                                                                        disabled/>
                                                                                                </div>
                                                                                                <i className='basic-text2'>%</i>
                                                                                            </div>


                                                                                        </div>

                                                                                    </div>
                                                                                </div>
                                                                            </>
                                                                        }
                                                                        <div className='bottom-dash-line'></div>
                                                                    </div>
                                                                )

                                                            })}


                                                            {this.state.remainFundsItems && this.state.remainFundsItems.length > 0 && this.state.remainFundsItems.map((fund, index) => {
                                                                return (
                                                                    <div className="fund__body-content"
                                                                         key={"fund-tab-verify" + index + getMapSize(this.state.currentFundsChangeMap)}>

                                                                        <>
                                                                            <h5>{fund.fundName}</h5>
                                                                            <br/>
                                                                            <div className='fund-content'
                                                                                 key={"sub-fund-tab-verify" + (getMapSize(this.state.currentFundsChangeMap) + index)}>
                                                                                <div className="tab-warpper">
                                                                                    <div className="tab">


                                                                                        <div
                                                                                            className={index === this.state.remainFundsItems.length - 1 ? "input disabled fund-last" : "input disabled fund"}>
                                                                                            <div
                                                                                                className="input__content">
                                                                                                <label htmlFor="">Tỷ lệ
                                                                                                    đầu tư mới</label>
                                                                                                <NumberFormat
                                                                                                    id='txtInputPercentVerify'
                                                                                                    suffix={'%'}
                                                                                                    value={(fund.percent !== '') ? fund.percent : 0}
                                                                                                    disabled/>
                                                                                            </div>
                                                                                            <i className='basic-text2'>%</i>
                                                                                        </div>


                                                                                    </div>

                                                                                </div>
                                                                            </div>
                                                                        </>

                                                                        <div
                                                                            className={index !== this.state.remainFundsItems.length - 1 ? 'bottom-dash-line' : ''}></div>
                                                                    </div>
                                                                )

                                                            })}


                                                            <div className="checkbox-item rate-fund-apply">
                                                                <h5 className='basic-semibold'>Áp dụng cho:</h5>
                                                                <div className="round-checkbox"
                                                                     style={{paddingTop: '8px'}}>
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={(this.state.forThisOnly === false) ? true : false}
                                                                               onClick={() => radioApplyRate('radioApplyRate')}
                                                                               id='radioApplyRate'/>
                                                                        <div className="checkmark"
                                                                             style={{marginBottom: '2px'}}></div>
                                                                        <p className="text">Lần đóng phí này và tiếp
                                                                            theo</p>
                                                                    </label>
                                                                </div>
                                                                <div className="round-checkbox"
                                                                     style={{paddingBottom: '17px'}}>
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={(this.state.forThisOnly === true) ? true : false}
                                                                               onClick={() => radioApplyRate('radioApplyRateOnly')}
                                                                               id='radioApplyRateOnly'/>
                                                                        <div className="checkmark"
                                                                             style={{marginBottom: '2px'}}></div>
                                                                        <p className="text">Chỉ lần đóng phí này</p>
                                                                    </label>
                                                                </div>
                                                            </div>


                                                            <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                            <img className="decor-person" src="../img/person.png"
                                                                 alt=""/>

                                                        </div>

                                                    </div>
                                                </div>
                                            )
                                        ) : (
                                            this.state.stepName === FUND_STATE.UPDATE_INFO ? (
                                                <div className='stepform' style={{marginTop: '175px'}}>

                                                    <div className="an-hung-page" style={{padding: '0 10px'}}>
                                                        <div className="bo-hop-dong-container page-eleven"
                                                             style={{alignItems: 'flex-start'}}>
                                                            <div className="title" style={{
                                                                paddingLeft: '16px',
                                                                paddingBottom: '0px',
                                                                paddingTop: '41px'
                                                            }}>
                                                                <h4 className='basic-upload-padding basic-text-upper'>Nhập
                                                                    thông tin sức khỏe</h4>
                                                            </div>
                                                            <div className="customer-informationthe-bh info__body"
                                                                 style={{width: '100%'}}>
                                                                <div
                                                                    className="customer-informationthe-bh-bottom__body">
                                                                    {this.state.insuredLapseList && this.state.insuredLapseList[0].Questionaire &&
                                                                        this.state.insuredLapseList[0].Questionaire.map((item, index) => {
                                                                            if ((this.state.expandIdLapseList.indexOf(item.LifeInsure) >= 0)) {
                                                                                return (
                                                                                    <>
                                                                                        <div
                                                                                            className={(index === 0) && (this.state.insuredLapseList[0].Questionaire.length > 1) ? "information-card-item border-bottom-zero" : "information-card-item"}>
                                                                                            <div
                                                                                                className="information-card-item-header"
                                                                                                onClick={() => toggleCardInfo(item.LifeInsure)}>
                                                                                                <div
                                                                                                    className="information-card-item-header-text basic-semibold"
                                                                                                    id={"hc-lapse-card-item-" + index}>
                                                                                                    {formatFullName(item.LifeInsure)}
                                                                                                </div>
                                                                                                <div
                                                                                                    className="information-card-item-header-arrow">
                                                                                                    <img
                                                                                                        src="img/icon/arrow-down-grey.svg"
                                                                                                        alt="arrow-down"
                                                                                                        className="bottom-mar"
                                                                                                    />
                                                                                                </div>
                                                                                            </div>

                                                                                            {item.QuestionList && item.QuestionList.map((element, idx) => {
                                                                                                return (
                                                                                                    <div
                                                                                                        className="content"
                                                                                                        style={{paddingTop: '8px'}}>

                                                                                                        <div
                                                                                                            className={idx === 0 ? "list__item" : "list__item top-dash-line"}>
                                                                                                            {/* <div className="dot"></div> */}
                                                                                                            <p className={idx === 0 ? 'questionaire padding-top-first-questionaire' : 'questionaire'}>{element.Content.replaceAll('@DateLapse', this.state.polExpiryDate)}</p>
                                                                                                        </div>
                                                                                                        <div
                                                                                                            className="item__content">
                                                                                                            <div
                                                                                                                className="tab">
                                                                                                                <div
                                                                                                                    className="tab__content">
                                                                                                                    <div
                                                                                                                        className="checkbox-warpper"
                                                                                                                        style={{marginTop: '-4px'}}>

                                                                                                                        <div
                                                                                                                            className="checkbox-item">
                                                                                                                            <div
                                                                                                                                className="round-checkbox">
                                                                                                                                <label
                                                                                                                                    className="customradio"
                                                                                                                                    style={{alignItems: 'center'}}
                                                                                                                                    onClick={() => AnswerYes(item.LifeInsure, element.QuestionNo)}>
                                                                                                                                    <input
                                                                                                                                        type="checkbox"
                                                                                                                                        checked={this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] === true) ? true : false}/>
                                                                                                                                    <div
                                                                                                                                        className="checkmark"></div>
                                                                                                                                    <p className="text">Có</p>
                                                                                                                                </label>
                                                                                                                            </div>
                                                                                                                        </div>

                                                                                                                        <div
                                                                                                                            className="checkbox-item"
                                                                                                                            style={{marginLeft: '10px'}}>
                                                                                                                            <div
                                                                                                                                className="round-checkbox">
                                                                                                                                <label
                                                                                                                                    className="customradio"
                                                                                                                                    style={{alignItems: 'center'}}
                                                                                                                                    onClick={() => AnswerNo(item.LifeInsure, element.QuestionNo)}>
                                                                                                                                    <input
                                                                                                                                        type="checkbox"
                                                                                                                                        checked={this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] === false) ? true : false}/>
                                                                                                                                    <div
                                                                                                                                        className="checkmark"></div>
                                                                                                                                    <p className="text">Không</p>
                                                                                                                                </label>
                                                                                                                            </div>
                                                                                                                        </div>
                                                                                                                        <div
                                                                                                                            className="checkbox-item"></div>
                                                                                                                        <div
                                                                                                                            className="checkbox-item"></div>
                                                                                                                        <div
                                                                                                                            className="checkbox-item"></div>
                                                                                                                        <div
                                                                                                                            className="checkbox-item"></div>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>

                                                                                                        {this.state.AnsweredListMap[item.LifeInsure] && this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] &&
                                                                                                            <div
                                                                                                                className="input-wrapper-item"
                                                                                                                style={{paddingBottom: '12px'}}>
                                                                                                                <div
                                                                                                                    className={this.state.AnsweredErrorListMap[item.LifeInsure] && this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo] && (((this.state.AnsweredValueListMap[item.LifeInsure] && this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo]) ? this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo] : "") === "") ? "input validate" : "input"}
                                                                                                                    style={{
                                                                                                                        height: '59px',
                                                                                                                        paddingTop: '10px'
                                                                                                                    }}>
                                                                                                                    <div
                                                                                                                        className="input__content">
                                                                                                                        {(this.state.AnsweredValueListMap[item.LifeInsure] && this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo]) &&
                                                                                                                            <label
                                                                                                                                style={{color: 'hsla(0, 0%, 45%, .7)'}}>Nhập
                                                                                                                                thông
                                                                                                                                tin
                                                                                                                                chi
                                                                                                                                tiết</label>
                                                                                                                        }
                                                                                                                        <input
                                                                                                                            type="search"
                                                                                                                            placeholder='Nhập thông tin chi tiết'
                                                                                                                            maxLength={500}
                                                                                                                            style={{padding: '0px'}}
                                                                                                                            value={(this.state.AnsweredValueListMap[item.LifeInsure] && this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo]) ? this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo] : ""}
                                                                                                                            onChange={(event) => EnterAnswer(event, element.QuestionNo, item.LifeInsure)}/>
                                                                                                                    </div>
                                                                                                                    <i><img
                                                                                                                        src="img/icon/edit.svg"
                                                                                                                        alt=""/></i>
                                                                                                                </div>
                                                                                                                {/*this.state.errorAddressRoad.length > 0 && <span style={{ color: 'red', 'line-height': '22px' }}>{this.state.errorAddressRoad}</span>*/}
                                                                                                            </div>
                                                                                                        }
                                                                                                        {(this.state.AnsweredListMap[item.LifeInsure] && this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] && this.state.AnsweredErrorListMap[item.LifeInsure] && this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo]) && (((this.state.AnsweredValueListMap[item.LifeInsure] && this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo]) ? this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo] : "") === "") &&
                                                                                                            <p className='inline-red'
                                                                                                               style={{padding: '0 0 6px'}}>{this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo]}</p>
                                                                                                        }

                                                                                                    </div>

                                                                                                )
                                                                                            })}

                                                                                        </div>
                                                                                        {(index !== this.state.insuredLapseList[0].Questionaire.length - 1) &&
                                                                                            <div
                                                                                                className='border-line-dash-expand'></div>
                                                                                        }
                                                                                    </>

                                                                                )
                                                                            } else {
                                                                                return (
                                                                                    <>
                                                                                        <div
                                                                                            className={index === 0 ? "information-card-item border-bottom-zero" : "information-card-item"}
                                                                                            onClick={() => toggleCardInfo(item.LifeInsure)}>
                                                                                            <div
                                                                                                className="information-card-item-header">
                                                                                                <div
                                                                                                    className="information-card-item-header-text"
                                                                                                    id={"hc-lapse-card-item-" + index}>
                                                                                                    {formatFullName(item.LifeInsure)}
                                                                                                </div>
                                                                                                <div
                                                                                                    className="information-card-item-header-arrow">
                                                                                                    <img
                                                                                                        src="img/icon/arrow-left-grey.svg"
                                                                                                        alt="arrow-left"/>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        {(index !== this.state.insuredLapseList[0].Questionaire.length - 1) &&
                                                                                            <div
                                                                                                className='border-line-dash'></div>
                                                                                        }

                                                                                    </>

                                                                                )
                                                                            }

                                                                        })}
                                                                </div>
                                                                <div
                                                                    className="customer-informationthe-bh consulting-service">
                                                                    <div className='channel-dash-no-margin'></div>
                                                                    <div
                                                                        className="customer-informationthe-bh-bottom__body"
                                                                        style={{
                                                                            alignItems: 'flex-start',
                                                                            justifyContent: 'space-between'
                                                                        }}>

                                                                        <div className="title-no">
                                                                            <h4 className='basic-upload-padding basic-text-upper'>Giấy
                                                                                tờ/ chứng từ đính kèm</h4>
                                                                        </div>
                                                                        <div className="list__item">
                                                                            <p className='sum-questionaire'>Quý khách có
                                                                                muốn gửi chứng từ liên quan đến thăm
                                                                                khám, xét nghiệm, điều trị cho chúng
                                                                                tôi?</p>
                                                                        </div>
                                                                        <div className="item__content">
                                                                            <div className="tab">
                                                                                <div className="tab__content">
                                                                                    <div className="checkbox-warpper">

                                                                                        <div className="checkbox-item"
                                                                                             style={{paddingLeft: '16px'}}>
                                                                                            <div
                                                                                                className="round-checkbox">
                                                                                                <label
                                                                                                    className="customradio"
                                                                                                    style={{alignItems: 'center'}}
                                                                                                    onClick={() => showUpload()}>
                                                                                                    <input
                                                                                                        type="checkbox"
                                                                                                        checked={(this.state.uploadSelected === true) ? true : false}/>
                                                                                                    <div
                                                                                                        className="checkmark"></div>
                                                                                                    <p className="text basic-padding-right">Có</p>
                                                                                                </label>
                                                                                            </div>
                                                                                        </div>

                                                                                        <div
                                                                                            className="checkbox-item restore-margin-left-answer-no">
                                                                                            <div
                                                                                                className="round-checkbox">
                                                                                                <label
                                                                                                    className="customradio"
                                                                                                    style={{alignItems: 'center'}}
                                                                                                    onClick={() => hideUpload()}>
                                                                                                    <input
                                                                                                        type="checkbox"
                                                                                                        checked={(this.state.uploadSelected === false) ? true : false}/>
                                                                                                    <div
                                                                                                        className="checkmark"></div>
                                                                                                    <p className="text">Không</p>
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


                                                            {this.state.uploadSelected &&
                                                                <div className="info__body" style={{width: '100%'}}>
                                                                    <div className="title-no">
                                                                        <h4 className='attach-title'>Vui lòng đính kèm
                                                                            chứng từ</h4>
                                                                    </div>
                                                                    <div className="item">
                                                                        <div className="item__content"
                                                                             style={{margin: '8px 16px 19px'}}>
                                                                            {(this.state.attachmentState.attachmentList
                                                                                && this.state.attachmentState.attachmentList.length > 0) ?
                                                                                <div
                                                                                    className="img-upload-wrapper not-empty">
                                                                                    <div className="img-upload-item">
                                                                                        <div className="img-upload"
                                                                                             id={"img-upload-claimAttachment"}
                                                                                             onClick={() => {
                                                                                                 document.getElementById("claimAttInput").click()
                                                                                             }}
                                                                                             onDragOver={(event) => this.handlerDragOver("img-upload-claimAttachment", event)}
                                                                                             onDragLeave={(event) => this.handlerDragLeave("img-upload-claimAttachment", event)}
                                                                                             onDrop={(event) => this.handlerDrop(event, this.handlerUpdateAttachmentList)}>
                                                                                            <button
                                                                                                className="circle-plus"
                                                                                                style={{padding: '0'}}>
                                                                                                <img style={{
                                                                                                    width: '24px',
                                                                                                    height: '24px'
                                                                                                }}
                                                                                                     src="../../../img/icon/zl_plug.svg"
                                                                                                     alt="circle-plus"/>
                                                                                            </button>
                                                                                            <p className="basic-grey">
                                                                                                Kéo & thả tệp tin hoặc
                                                                                                <span
                                                                                                    className="basic-red basic-semibold">&nbsp;chọn tệp</span>
                                                                                            </p>
                                                                                            <input id={"claimAttInput"}
                                                                                                   className="inputfile"
                                                                                                   type="file"
                                                                                                   multiple={true}
                                                                                                   hidden={true}
                                                                                                   accept="image/*"
                                                                                                   onChange={(e) => this.handlerUploadAttachment(e, this.handlerUpdateAttachmentList)}/>
                                                                                        </div>
                                                                                    </div>
                                                                                    {this.state.attachmentState.attachmentList.map((att, attIdx) => (
                                                                                        <div className="img-upload-item"
                                                                                             key={attIdx}
                                                                                             style={{padding: '6px'}}>
                                                                                            <div className="file">
                                                                                                <div
                                                                                                    className="img-wrapper">
                                                                                                    <img
                                                                                                        src={att.imgData}
                                                                                                        alt=""/>
                                                                                                </div>
                                                                                                <div
                                                                                                    className="deletebtn"
                                                                                                    onClick={() => this.handlerDeleteAttachment(attIdx)}>X
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    ))}
                                                                                </div> :
                                                                                <div className="img-upload-wrapper">
                                                                                    <div className="img-upload-item">
                                                                                        <div className="img-upload"
                                                                                             id={"img-upload-empty"}
                                                                                             onClick={() => {
                                                                                                 document.getElementById("claimAttEmptyInput").click()
                                                                                             }}
                                                                                             onDragOver={(event) => this.handlerDragOver("img-upload-empty", event)}
                                                                                             onDragLeave={(event) => this.handlerDragLeave("img-upload-empty", event)}
                                                                                             onDrop={(event) => this.handlerDrop(event, this.handlerUpdateAttachmentList)}>
                                                                                            <button
                                                                                                className="circle-plus"
                                                                                                style={{padding: '0'}}>
                                                                                                <img style={{
                                                                                                    width: '24px',
                                                                                                    height: '24px'
                                                                                                }}
                                                                                                     src="../../../img/icon/zl_plug.svg"
                                                                                                     alt="circle-plus"/>
                                                                                            </button>
                                                                                            <p className="basic-grey">
                                                                                                Kéo & thả tệp tin hoặc
                                                                                                <span
                                                                                                    className="basic-red basic-semibold">&nbsp;chọn tệp</span>
                                                                                            </p>
                                                                                            <input
                                                                                                id={"claimAttEmptyInput"}
                                                                                                className="inputfile"
                                                                                                type="file"
                                                                                                multiple={true}
                                                                                                hidden={true}
                                                                                                accept="image/*"
                                                                                                onChange={(e) => this.handlerUploadAttachment(e, this.handlerUpdateAttachmentList)}/>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            }
                                                                        </div>
                                                                        {this.state.errorUpload &&
                                                                            <p className='inline-red' style={{
                                                                                padding: '0 16px 16px',
                                                                                marginTop: '-14px'
                                                                            }}>{this.state.errorUpload}</p>
                                                                        }
                                                                    </div>
                                                                </div>
                                                            }

                                                            <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                            <img className="decor-person" src="../img/person.png"
                                                                 alt=""/>

                                                        </div>

                                                    </div>

                                                </div>
                                            ) : (
                                                this.state.stepName === FUND_STATE.VERIFICATION ? (
                                                    <div className='stepform' style={{marginTop: '175px'}}>

                                                        <div className="an-hung-page" style={{padding: '0 10px'}}>
                                                            <div className="bo-hop-dong-container page-eleven"
                                                                 style={{alignItems: 'flex-start'}}>
                                                                <div className="title" style={{
                                                                    paddingLeft: '16px',
                                                                    paddingBottom: '0px',
                                                                    paddingTop: '41px'
                                                                }}>
                                                                    <h4 className='basic-upload-padding basic-text-upper'>Nhập
                                                                        thông tin sức khỏe</h4>
                                                                </div>
                                                                <div className="customer-informationthe-bh info__body"
                                                                     style={{width: '100%'}}>
                                                                    <div
                                                                        className="customer-informationthe-bh-bottom__body">
                                                                        {this.state.insuredLapseList && this.state.insuredLapseList[0].Questionaire &&
                                                                            this.state.insuredLapseList[0].Questionaire.map((item, index) => {
                                                                                if ((this.state.expandIdLapseList.indexOf(item.LifeInsure) >= 0)) {
                                                                                    return (
                                                                                        <>
                                                                                            <div
                                                                                                className={(index === 0) && (this.state.insuredLapseList[0].Questionaire.length > 1) ? "information-card-item border-bottom-zero" : "information-card-item"}>
                                                                                                <div
                                                                                                    className="information-card-item-header"
                                                                                                    onClick={() => toggleCardInfo(item.LifeInsure)}>
                                                                                                    <div
                                                                                                        className="information-card-item-header-text basic-semibold"
                                                                                                        id={"hc-lapse-card-item-" + index}>
                                                                                                        {formatFullName(item.LifeInsure)}
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="information-card-item-header-arrow">
                                                                                                        <img
                                                                                                            src="img/icon/arrow-down-grey.svg"
                                                                                                            alt="arrow-down"
                                                                                                            className="bottom-mar"
                                                                                                        />
                                                                                                    </div>
                                                                                                </div>

                                                                                                {item.QuestionList && item.QuestionList.map((element, idx) => {
                                                                                                    return (
                                                                                                        <div
                                                                                                            className="content"
                                                                                                            style={{paddingTop: '8px'}}>

                                                                                                            <div
                                                                                                                className={idx === 0 ? "list__item" : "list__item top-dash-line"}>
                                                                                                                {/* <div className="dot"></div> */}
                                                                                                                <p className={idx === 0 ? 'questionaire padding-top-first-questionaire' : 'questionaire'}>{element.Content.replaceAll('@DateLapse', this.state.polExpiryDate)}</p>
                                                                                                            </div>
                                                                                                            <div
                                                                                                                className="item__content">
                                                                                                                <div
                                                                                                                    className="tab">
                                                                                                                    <div
                                                                                                                        className="tab__content">
                                                                                                                        <div
                                                                                                                            className="checkbox-warpper"
                                                                                                                            style={{marginTop: '-4px'}}>

                                                                                                                            <div
                                                                                                                                className="checkbox-item">
                                                                                                                                <div
                                                                                                                                    className="round-checkbox">
                                                                                                                                    <label
                                                                                                                                        className="customradio"
                                                                                                                                        style={{alignItems: 'center'}}>
                                                                                                                                        <input
                                                                                                                                            type="checkbox"
                                                                                                                                            checked={this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] === true) ? true : false}
                                                                                                                                            disabled/>
                                                                                                                                        <div
                                                                                                                                            className="checkmark-readonly"></div>
                                                                                                                                        <p className="text">Có</p>
                                                                                                                                    </label>
                                                                                                                                </div>
                                                                                                                            </div>

                                                                                                                            <div
                                                                                                                                className="checkbox-item"
                                                                                                                                style={{marginLeft: '8px'}}>
                                                                                                                                <div
                                                                                                                                    className="round-checkbox">
                                                                                                                                    <label
                                                                                                                                        className="customradio"
                                                                                                                                        style={{alignItems: 'center'}}>
                                                                                                                                        <input
                                                                                                                                            type="checkbox"
                                                                                                                                            checked={this.state.AnsweredListMap[item.LifeInsure] && (this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] === false) ? true : false}
                                                                                                                                            disabled/>
                                                                                                                                        <div
                                                                                                                                            className="checkmark-readonly"></div>
                                                                                                                                        <p className="text">Không</p>
                                                                                                                                    </label>
                                                                                                                                </div>
                                                                                                                            </div>
                                                                                                                            <div
                                                                                                                                className="checkbox-item"></div>
                                                                                                                            <div
                                                                                                                                className="checkbox-item"></div>
                                                                                                                            <div
                                                                                                                                className="checkbox-item"></div>
                                                                                                                            <div
                                                                                                                                className="checkbox-item"></div>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>

                                                                                                            {this.state.AnsweredListMap[item.LifeInsure] && this.state.AnsweredListMap[item.LifeInsure][element.QuestionNo] &&
                                                                                                                <div
                                                                                                                    className="input-wrapper-item"
                                                                                                                    style={{paddingBottom: '12px'}}>
                                                                                                                    <div
                                                                                                                        className={this.state.AnsweredErrorListMap[item.LifeInsure] && this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo] ? "input validate" : "input"}
                                                                                                                        style={{
                                                                                                                            height: '59px',
                                                                                                                            paddingTop: '10px',
                                                                                                                            background: '#EDEBEB'
                                                                                                                        }}>
                                                                                                                        <div
                                                                                                                            className="input__content">
                                                                                                                            {this.state.AnsweredErrorListMap[item.LifeInsure] && this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo] ? (
                                                                                                                                <label
                                                                                                                                    className='inline-red'>{this.state.AnsweredErrorListMap[item.LifeInsure][element.QuestionNo]}</label>
                                                                                                                            ) : (
                                                                                                                                <label>Nhập
                                                                                                                                    thông
                                                                                                                                    tin
                                                                                                                                    chi
                                                                                                                                    tiết</label>
                                                                                                                            )}
                                                                                                                            <input
                                                                                                                                className='basic-light-black'
                                                                                                                                type="search"
                                                                                                                                maxLength={500}
                                                                                                                                style={{padding: '0px'}}
                                                                                                                                value={(this.state.AnsweredValueListMap[item.LifeInsure] && this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo]) ? this.state.AnsweredValueListMap[item.LifeInsure][element.QuestionNo] : ""}
                                                                                                                                disabled/>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                    {/*this.state.errorAddressRoad.length > 0 && <span style={{ color: 'red', 'line-height': '22px' }}>{this.state.errorAddressRoad}</span>*/}
                                                                                                                </div>
                                                                                                            }


                                                                                                        </div>

                                                                                                    )
                                                                                                })}

                                                                                            </div>
                                                                                            {(index !== this.state.insuredLapseList[0].Questionaire.length - 1) &&
                                                                                                <div
                                                                                                    className='border-line-dash-expand'></div>
                                                                                            }
                                                                                        </>

                                                                                    )
                                                                                } else {
                                                                                    return (
                                                                                        <>
                                                                                            <div
                                                                                                className={index === 0 ? "information-card-item border-bottom-zero" : "information-card-item"}
                                                                                                onClick={() => toggleCardInfo(item.LifeInsure)}>
                                                                                                <div
                                                                                                    className="information-card-item-header">
                                                                                                    <div
                                                                                                        className="information-card-item-header-text"
                                                                                                        id={"hc-lapse-card-item-" + index}>
                                                                                                        {formatFullName(item.LifeInsure)}
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="information-card-item-header-arrow">
                                                                                                        <img
                                                                                                            src="img/icon/arrow-left-grey.svg"
                                                                                                            alt="arrow-left"/>
                                                                                                    </div>
                                                                                                </div>

                                                                                            </div>
                                                                                            {(index !== this.state.insuredLapseList[0].Questionaire.length - 1) &&
                                                                                                <div
                                                                                                    className='border-line-dash'></div>
                                                                                            }
                                                                                        </>

                                                                                    )
                                                                                }

                                                                            })}
                                                                    </div>
                                                                    <div
                                                                        className="customer-informationthe-bh consulting-service">
                                                                        <div className='channel-dash-no-margin'></div>
                                                                        <div
                                                                            className="customer-informationthe-bh-bottom__body"
                                                                            style={{
                                                                                alignItems: 'flex-start',
                                                                                justifyContent: 'space-between'
                                                                            }}>

                                                                            <div className="title-no">
                                                                                <h4 className='basic-upload-padding basic-text-upper'>Giấy
                                                                                    tờ/ chứng từ đính kèm</h4>
                                                                            </div>
                                                                            <div className="list__item">
                                                                                <p className='sum-questionaire'>Quý
                                                                                    khách có muốn gửi chứng từ liên quan
                                                                                    đến thăm khám, xét nghiệm, điều trị
                                                                                    cho chúng tôi?</p>
                                                                            </div>
                                                                            <div className="item__content">
                                                                                <div className="tab">
                                                                                    <div className="tab__content">
                                                                                        <div
                                                                                            className="checkbox-warpper">

                                                                                            <div
                                                                                                className="checkbox-item"
                                                                                                style={{paddingLeft: '16px'}}>
                                                                                                <div
                                                                                                    className="round-checkbox">
                                                                                                    <label
                                                                                                        className="customradio"
                                                                                                        style={{alignItems: 'center'}}>
                                                                                                        <input
                                                                                                            type="checkbox"
                                                                                                            checked={(this.state.uploadSelected === true) ? true : false}
                                                                                                            disabled/>
                                                                                                        <div
                                                                                                            className="checkmark-readonly"></div>
                                                                                                        <p className="text basic-padding-right">Có</p>
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div
                                                                                                className="checkbox-item restore-margin-left-answer-no">
                                                                                                <div
                                                                                                    className="round-checkbox">
                                                                                                    <label
                                                                                                        className="customradio"
                                                                                                        style={{alignItems: 'center'}}>
                                                                                                        <input
                                                                                                            type="checkbox"
                                                                                                            checked={(this.state.uploadSelected === false) ? true : false}
                                                                                                            disabled/>
                                                                                                        <div
                                                                                                            className="checkmark-readonly"></div>
                                                                                                        <p className="text">Không</p>
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


                                                                {this.state.uploadSelected &&
                                                                    <div className="info__body" style={{width: '100%'}}>
                                                                        <div className="title-no">
                                                                            <h4 className='attach-title'>Vui lòng đính
                                                                                kèm chứng từ</h4>
                                                                        </div>
                                                                        <div className="item">
                                                                            <div className="item__content"
                                                                                 style={{margin: '14px 16px 24px'}}>
                                                                                {(this.state.attachmentState.attachmentList
                                                                                        && this.state.attachmentState.attachmentList.length > 0) &&
                                                                                    <div
                                                                                        className="img-upload-wrapper not-empty"
                                                                                        style={{
                                                                                            background: '#EDEBEB',
                                                                                            padding: '6px'
                                                                                        }}>
                                                                                        {/*{this.state.attachmentState.attachmentList.map((att, attIdx) => (*/}
                                                                                        {/*    <div*/}
                                                                                        {/*        className="img-upload-item"*/}
                                                                                        {/*        key={attIdx}*/}
                                                                                        {/*        style={{padding: '6px'}}>*/}
                                                                                        {/*        <div className="file">*/}
                                                                                        {/*            <div*/}
                                                                                        {/*                className="img-wrapper">*/}
                                                                                        {/*                <img*/}
                                                                                        {/*                    src={att.imgData}*/}
                                                                                        {/*                    alt=""/>*/}
                                                                                        {/*            </div>*/}
                                                                                        {/*        </div>*/}
                                                                                        {/*    </div>*/}
                                                                                        {/*))}*/}
                                                                                        {!isEmpty(this.state.attachmentState.attachmentList) && <ImageViewerBase64 images={this.state.attachmentState.attachmentList}/>}
                                                                                    </div>
                                                                                }
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                }
                                                                {this.state.tempFee && parseFloat(this.state.tempFee) > 0 &&
                                                                    <div className='top-dash-line-fee'>
                                                                        <div className='channel-dash-no-margin'
                                                                             style={{marginBottom: '0'}}></div>
                                                                        <div className="title-fee">
                                                                            <h4 className='basic-upload-padding basic-text-upper'>Phí
                                                                                bảo hiểm cần đóng tạm tính</h4>
                                                                        </div>
                                                                        <div className="input-wrapper-item" style={{
                                                                            paddingBottom: '12px',
                                                                            paddingTop: '12px'
                                                                        }}>
                                                                            <div className="input" style={{
                                                                                height: '59px',
                                                                                paddingTop: '10px',
                                                                                background: '#EDEBEB'
                                                                            }}>
                                                                                <div className="input__content">
                                                                                    <label>Số tiền</label>
                                                                                    <NumberFormat
                                                                                        thousandSeparator={'.'}
                                                                                        decimalSeparator={','}
                                                                                        suffix={' VND'}
                                                                                        value={this.state.tempFee}
                                                                                        disabled style={{
                                                                                        padding: '1px 0px',
                                                                                        color: '#727272'
                                                                                    }}/>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                }

                                                                <img className="decor-clip" src="../img/mock.svg"
                                                                     alt=""/>
                                                                <img className="decor-person" src="../img/person.png"
                                                                     alt=""/>

                                                            </div>

                                                        </div>

                                                    </div>
                                                ) : (
                                                    <></>
                                                )

                                            )
                                        )

                                    )


                                )}

                                {this.state.stepName === FUND_STATE.CHOOSE_POLICY && !this.state.updatePeriodicFee && !this.state.decreaseSACancelRider && !this.state.updateLoan &&
                                    <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                        <div className="bottom-text"
                                             style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                            <p style={{textAlign: 'justify'}}>
                                                <span className="red-text basic-bold">Lưu ý: </span><span
                                                style={{color: '#727272'}}>
                                                {this.state.updateReinstatement ? (
                                                    '(Các) Người được bảo hiểm cần bổ sung khai báo thông tin sức khỏe bằng Tờ khai sức khỏe (theo mẫu) nếu được yêu cầu. Nếu Quý khách yêu cầu khôi phục hợp đồng có thời gian mất hiệu lực trên 06 tháng, vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.'
                                                ) : (
                                                    'Các yêu cầu cập nhật điều chỉnh không áp dụng với hợp đồng đã mất hiệu lực.'
                                                )}
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                }
                                {(this.state.stepName === FUND_STATE.UPDATE_INFO) && !this.state.updateReinstatement &&
                                    <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                        <div className="bottom-text"
                                             style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                            <p style={{textAlign: 'justify'}}>
                                                <span className="red-text basic-bold">Lưu ý: </span><span
                                                style={{color: '#727272'}}>
                          Giá trị Quỹ hiển thị đang tạm tính dựa trên Giá đơn vị Quỹ gần nhất.
                        </span>
                                            </p>
                                        </div>
                                    </div>
                                }
                                {this.state.stepName === FUND_STATE.VERIFICATION && !this.state.updateReinstatement &&
                                    <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                        <div className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                             style={{
                                                 'maxWidth': '594px',
                                                 backgroundColor: '#f5f3f2',
                                                 paddingLeft: '6px'
                                             }}>
                                            <div
                                                className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                style={{flex: '0 0 auto', height: '20px', cursor: 'pointer'}}
                                                onClick={() => acceptPolicy()}>
                                                <div className="checkmark">
                                                    <img src="img/icon/check.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="policy-info-tac" style={{
                                                textAlign: 'justify',
                                                paddingLeft: '12px'
                                            }}>
                                                <p style={{textAlign: 'left', fontWeight: "bold"}}>Tôi đồng ý và xác
                                                    nhận:</p>
                                                <ul className="list-information">
                                                    <li className="sub-list-li">
                                                        - Tất cả thông tin trên đây
                                                        là đầy đủ, đúng sự thật và
                                                        hiểu rằng yêu cầu này chỉ
                                                        có hiệu lực kể từ ngày được
                                                        Dai-ichi Life Việt Nam chấp
                                                        nhận.
                                                    </li>
                                                    <li className="sub-list-li">
                                                        - Với xác nhận hoàn tất giao
                                                        dịch, đồng ý với <a
                                                        style={{display: 'inline'}}
                                                        className="red-text basic-bold"
                                                        href={PAGE_POLICY_PAYMENT}
                                                        target='_blank'>Điều khoản
                                                        Dịch vụ và Giao dịch điện
                                                        tử</a> và<a
                                                        style={{display: 'inline'}}
                                                        className="red-text basic-bold"
                                                        href={PAGE_REGULATIONS_PAYMENT}
                                                        target='_blank'> Quy định
                                                        bảo vệ và xử lý dữ liệu của
                                                        Dai-ichi Life Việt Nam.</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                }
                                {this.state.updateFund ? (
                                    this.state.polListProfile && (this.state.stepName >= FUND_STATE.CHOOSE_POLICY) &&
                                    <div className="bottom-btn" style={{maxWidth: '578px', paddingBottom: '36px'}}>
                                        {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                                            this.state.polID ? (
                                                <button className="btn btn-primary" onClick={() => {
                                                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`);
                                                    trackingEvent(
                                                        "Giao dịch hợp đồng",
                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`,
                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`,
                                                    );
                                                    getFundList();
                                                }}>Tiếp
                                                    tục</button>
                                            ) : (
                                                <button className="btn btn-primary disabled" disabled>Tiếp tục</button>
                                            )
                                        ) : (
                                            this.state.stepName === FUND_STATE.UPDATE_INFO ? (
                                                enableToVerify ? (
                                                    <button className="btn btn-primary"
                                                            onClick={() => {
                                                                this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`);
                                                                trackingEvent(
                                                                    "Giao dịch hợp đồng",
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`,
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`,
                                                                );
                                                                submitMoveFund();
                                                            }}>Tiếp tục</button>
                                                ) : (
                                                    <button className="btn btn-primary disabled" disabled>Tiếp
                                                        tục</button>
                                                )
                                            ) : (
                                                this.state.stepName === FUND_STATE.VERIFICATION &&
                                                ((this.state.acceptPolicy && (this.state.forThisOnly !== null)) ? (
                                                    <button className="btn btn-primary"
                                                            onClick={() => {
                                                                this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`);
                                                                trackingEvent(
                                                                    "Giao dịch hợp đồng",
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`,
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`,
                                                                );
                                                                completeMoveFund();
                                                            }}>Tiếp tục</button>
                                                ) : (
                                                    <button className="btn btn-primary disabled" disabled>Tiếp
                                                        tục</button>
                                                ))
                                            )


                                        )}

                                    </div>

                                ) : (
                                    this.state.updateInvestRate ? (
                                        this.state.polListProfile && (this.state.stepName >= FUND_STATE.CHOOSE_POLICY) &&
                                        <div className="bottom-btn" style={{maxWidth: '578px', paddingBottom: '36px'}}>
                                            {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                                                this.state.polID ? (
                                                    <button className="btn btn-primary"
                                                            onClick={() => {
                                                                // console.log("PolTransChangeRateContact");
                                                                this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`);
                                                                trackingEvent(
                                                                    "Giao dịch hợp đồng",
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`,
                                                                    `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_CONTACT}`,
                                                                );
                                                                getFundList();
                                                            }}>Tiếp tục</button>
                                                ) : (
                                                    <button className="btn btn-primary disabled" disabled>Tiếp tục</button>
                                                )
                                            ) : (
                                                this.state.stepName === FUND_STATE.UPDATE_INFO ? (
                                                    allFundValidFundInChangePercentMap() ? (
                                                        <button className="btn btn-primary"
                                                                onClick={() => {
                                                                    // console.log("submitChangeFund");
                                                                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`);
                                                                    trackingEvent(
                                                                        "Giao dịch hợp đồng",
                                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`,
                                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_REVIEW}`,
                                                                    );
                                                                    submitChangeFund();
                                                                }}>Tiếp tục</button>
                                                    ) : (
                                                        <button className="btn btn-primary disabled" disabled>Tiếp
                                                            tục</button>
                                                    )
                                                ) : (
                                                    this.state.stepName === FUND_STATE.VERIFICATION &&
                                                    ((this.state.acceptPolicy && (this.state.forThisOnly !== null)) ? (
                                                        <button className="btn btn-primary"
                                                                onClick={() => {
                                                                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`);
                                                                    trackingEvent(
                                                                        "Giao dịch hợp đồng",
                                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`,
                                                                        `Web_Open_${PageScreen.POL_TRANS_CHANGE_RATE_SUBMIT}`,
                                                                    );
                                                                    submitUpdateFundRate();
                                                                }}>Xác nhận</button>
                                                    ) : (
                                                        <button className="btn btn-primary disabled" disabled>Xác
                                                            nhận</button>
                                                    ))
                                                )


                                            )}

                                        </div>
                                    ) : (
                                        this.state.polListLapse && (this.state.stepName >= FUND_STATE.CHOOSE_POLICY) &&
                                        <div className="bottom-btn" style={{maxWidth: '578px', paddingBottom: '36px'}}>
                                            {this.state.stepName === FUND_STATE.CHOOSE_POLICY ? (
                                                this.state.polID ? (
                                                    <button className="btn btn-primary"
                                                            onClick={() => EnterInfoToReinstament()}>Tiếp tục</button>
                                                ) : (
                                                    <button className="btn btn-primary disabled" disabled>Tiếp tục</button>
                                                )
                                            ) : (
                                                this.state.stepName === FUND_STATE.UPDATE_INFO ?
                                                    (passAnswer ? (<button className="btn btn-primary"
                                                                           onClick={() => VerifySubmitInfo()}>Tiếp
                                                        tục</button>) : (
                                                        <button className="btn btn-primary disabled" disabled>Tiếp
                                                            tục</button>)) :
                                                    (
                                                        this.state.stepName === FUND_STATE.VERIFICATION &&
                                                        <button className="btn btn-primary"
                                                                onClick={() => ReInstamentVerify()}>Xác nhận</button>

                                                    )


                                            )}

                                        </div>
                                    )

                                )}

                                {/*START ND13*/}
                                {this.state.appType && this.state.trackingId && (this.state.stepName === FUND_STATE.SDK) && <ND13
                                            appType={this.state.appType}
                                            trackingId={this.state.trackingId}
                                            clientListStr={this.state.clientListStr}
                                            clientId={getSession(CLIENT_ID)}
                                            proccessType={this.state.proccessType}
                                            deviceId={getDeviceId()}
                                            apiToken={getSession(ACCESS_TOKEN)}
                                            policyNo={this.state.polID}
                                            phone={getSession(CELL_PHONE)}
                                            updateInfoState={this.state.updateInfoState}
                                            handlerGoToStep = {this.handlerGoToStep}
                                            toggleBack = {this.state.toggleBack}
                                            updateAllLIAgree = {this.updateAllLIAgree}
                                            updateExistLINotAgree = {this.updateExistLINotAgree}
                                            handlerAdjust = {this.handlerAdjust}
                                />}

                                {/*END ND13*/}

                            </div>)}
                        
                        </section>
                    </div>

                </main>
                {this.state.isCheckPermission && <AlertPopupClaim closePopup={() => closeNotAvailable()}
                                                                  msg={this.state.msgCheckPermission}
                                                                  imgPath={checkPermissionIcon}/>}
                {this.state.noPhone &&
                    <AlertPopupPhone closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}
                                     screen={SCREENS.REINSTATEMENT}/>
                }
                {(this.state.invalidFund || this.state.totalToFundError || this.state.totalFundCalInTempError || this.state.noChangeInvestRate) &&
                    <AlertPopup closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}/>
                }
                {this.state.updateInvestRate && this.state.percentError &&
                    <AlertPercentError closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}/>
                }
                {this.state.noTwofa &&
                    <AuthenticationPopup closePopup={closeNoTwofa}
                                         msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                                         screen={SCREENS.REINSTATEMENT}/>
                }
                {
                    this.state.showNotice &&
                    (
                        this.state.updateFund ? (
                            <NoticeChangeFundPopup closePopup={closeNotice} msg={this.state.msgPopup}
                                                   imgPath={popupImgPath}/>
                        ) : (
                            <NoticeChangeRatePopup closePopup={closeNotice} msg={this.state.msgPopup}
                                                   imgPath={popupImgPath}/>
                        )
                    )

                }
                {this.state.showOtp &&
                    <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                               closeOtp={this.closeOtp} errorMessage={this.state.errorMessage}
                               popupOtpSubmit={this.popupOtpSubmit} reGenOtp={this.genOtp}
                               maskPhone={maskPhone(getSession(CELL_PHONE))}/>
                }
                {this.state.showOtpRIN &&
                    <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                               closeOtp={this.closeOtpRIN} errorMessage={this.state.errorMessage}
                               popupOtpSubmit={this.popupOtpSubmitReIns} reGenOtp={this.genOtpV2}
                               maskPhone={maskPhone(getSession(CELL_PHONE))}/>
                }
                {this.state.showThanks && (
                    this.state.updateReinstatement ? (
                        <SuccessReInstamentPopup closePopup={this.closeThanks} policyNo={this.state.polID}
                                                 screen={SCREENS.REINSTATEMENT} fee={this.state.tempFee}/>
                    ) : (
                        (
                            this.state.forThisOnly ? (
                                <SuccessChangeFundRatePopup closePopup={this.closeThanks} policyNo={this.state.polID}
                                                            screen={SCREENS.REINSTATEMENT}/>
                            ) : (
                                <ThanksPopup
                                    msg={'Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Chúng tôi sẽ xử lý yêu cầu và thông báo kết quả trong thời gian sớm nhất'}
                                    closeThanks={this.closeThanks}/>
                            )
                        )
                    )
                )
                }
                {this.state.reInstamentVerify &&
                    <UserAgree closePopup={() => CloseReInstamentVerify()}
                               popupSubmit={() => VerifyReInstamentSubmit()}/>
                }

                {this.state.apiError &&
                    <AlertPopupError closePopup={closeApiError} msg={msgPopup} imgPath={popupImgPath}/>
                }

                {this.state.lapseAboveOneYear &&
                    <AlertPopupError closePopup={closeLapseAboveOneYear} msg={msgPopup} imgPath={popupImgPath}
                                     screen={SCREENS.REINSTATEMENT}/>
                }
                {this.state.submitIn24 &&
                    <ErrorReInstamentSubmitted closePopup={() => closeSubmitIn24} msg={msgPopup} imgPath={popupImgPath}
                                               screen={SCREENS.REINSTATEMENT}
                                               forceContinue={() => ForceContinueToReInstament()}/>
                }

                {this.state.hasLocalData &&
                    <ConfirmChangePopup closePopup={()=>handleCLoseExistLocalConfirm()} go={() => handleAgreeExistLocalConfirm(this.state.polID)}
                                        msg={parse('Quý khách <span classname="basic-bold">' + (this.state.polLapseSelected ? this.state.polLapseSelected.PolicyLIName : '') + '</span> có một yêu cầu đang tạo. Quý khách có muốn tiếp tục với yêu cầu này?')}
                                        agreeText='Tiếp tục' notAgreeText='Xóa và tạo mới'
                                        notAgreeFunc={()=>handleClearExistLocalConfirm()}/>}


                {/* Popup succeeded redirect */}
                <div className="popup special envelop-confirm-popup" id="popupSucceededRedirect">
                    <div className="popup__card">
                        <div className="envelop-confirm-card" ref={this.handlerSetWrapperSucceededRef}>
                            <div className="envelopcard">
                                <div className="envelop-content">
                                    <div className="envelop-content__header"
                                        onClick={this.handlerClosePopupSucceededRedirect}
                                    >
                                        <i className="closebtn"><img src="../../img/icon/close.svg" alt=""/></i>
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
                                        <i className="closebtn"><img src="../../img/icon/close.svg" alt=""/></i>
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
                {this.state.showConfirmClear &&
                    <ConfirmPopup closePopup={() => closeShowConfirmClear()} go={() => agreeClear()} />
                }
                {this.state.notiLinkExpired &&
                    <AlertPopupOriginal closePopup={closePopupError} msg='Liên kết với thông báo này không còn hiệu lực' imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}
                                     screen={SCREENS.REINSTATEMENT}/>
                }
            </>


        );

    }

}


export default withRouter(Reinstatement);