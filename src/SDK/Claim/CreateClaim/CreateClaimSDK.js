// import 'antd/dist/antd.min.css';
import '../claim.css';
// import '../sdk.css';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLAIM_GUID,
    CLAIM_SAVE_LOCAL,
    CLAIM_TYPE,
    CLIENT_ID,
    COMPANY_KEY,
    COMPANY_KEY2,
    COMPANY_KEY3,
    ConsentStatus,
    DCID,
    EMAIL,
    FE_BASE_URL,
    PageScreen,
    POL_LIST_CLIENT4CLAIM,
    SCREENS,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    WEB_BROWSER_VERSION,
    OS,
    PUBLIC_KEY_DLVN,
    FULL_NAME,
    GENDER,
    TWOFA,
    DOB,
    POL_LIST_CLIENT,
    DEVICE_ID,
    ADDRESS,
    OTHER_ADDRESS,
    POID,
    CLAIM_STEPCODE,
    ONLY_PAYMENT,
    NUM_OF_RETRY,
    SDK_REQUEST_STATUS,
    SDK_ROLE_PO,
    CODE_SUCCESS,
    SDK_REVIEW_PROCCESS,
    IS_MOBILE,
    LIITEM,
    ND_13,
    SDK_ROLE_AGENT,
    AUTHORZIE_STATUS_CODE_APPROVE,
    AUTHORZIE_STATUS_CODE_NOT_APPROVE,
    AUTHORZIE_STATUS_CODE_NOT_CREATE,
    CLAIM_EXPIRE_STATUS_MASTER_LIST,
    SYSTEM_DCONNECT,
    REVIEW_LINK,
    SIGNATURE,
    AGENT_CLAIM_EXPIRE_STATUS_MASTER_LIST
} from '../../sdkConstant'
import { Component } from 'react';
import { withRouter } from 'react-router-dom';
import {
    CPConsentConfirmation,
    CPSaveLog,
    getBankMasterData,
    GetConfiguration,
    getZipCodeMasterData,
    iibGetMasterDataByType,
    CPSubmitForm,
    getTokenPO,
    checkAccount,
    getPermissionByGroupAndProccessType,
    checkData,
    requestClaimRecordInfor,
    requestClaimGetInfor,
    getPolicyListEligibleRequestClaimByClientId,
    saveClaimRequest,
    saveImageClaim,
    completeClaimRequest,
    requestClaimGetList,
    authorizationGetInfor,
    getCustomerProfile,
    getMircoToken,
    requestClaimGetTempInfo 
} from '../../sdkAPI';
import {
    checkMapHaveTrue,
    formatDate,
    getDeviceId,
    getLocal,
    getSession,
    isCheckedOnlyAccident,
    isCheckedOnlyIllness,
    noCheckedAny,
    removeLocal,
    setLocal,
    setSession,
    trackingEvent,
    decodeHtmlEntity,
    genPassword,
    getOSAndBrowserInfo,
    setDeviceId,
    checkValue,
    is18Plus,
    sumInvoiceNew,
    convertDateToISO,
    isOlderThan18,
    cpSaveLog,
    haveCheckedDeadth,
    haveHC_HS,
    buildMicroRequest,
    getOperatingSystem,
    getUrlParameter,
    convertDateToUSFormat,
    getBenifits,
    getOSVersion,
    removeSession,
    isHC3,
    clearSession,
    haveLIStillNotAgree,
    compressJson,
    decompressJson,
    callbackAppOpenLink
} from '../../sdkCommon';
import AlertPopupClaim from '../../components/AlertPopupClaim';
import AuthenticationPopupClaim from '../../components/AuthenticationPopupClaim';
import AlertPopupHight from '../../components/AlertPopupHight';
import AlertPopup from '../../components/AlertPopup';
import ConfirmChangePopup from '../../components/ConfirmChangePopup';
import ContactPersonDetail from './ClaimProcess/ClaimDetail/ContactPersonDetail';
import htmlParse from 'html-react-parser';
import AES256 from 'aes-everywhere';
import moment from 'moment'

import LifeInsureList from './Initial/LifeInsureList';
import PolList from './Initial/PolList';

import ClaimType from './ClaimProcess/ClaimDetail/ClaimType';
import ClaimDetail from './ClaimProcess/ClaimDetail/ClaimDetail';
import PaymentMethod from './ClaimProcess/PaymentMethod/PaymentMethod';
import Attachment from './ClaimProcess/Attachment/Attachment';
import ClaimSubmission from './ClaimProcess/Submission/ClaimSubmission';
import { Helmet } from "react-helmet";
import { cloneDeep, isEmpty } from "lodash";
import checkPermissionIcon from "../../img/popup/check-permission.png";
import POWarningND13 from "../../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import NodeForge from 'node-forge';
import CreateRequest from './SDKCreateClaimRequest/CreateRequest';
import CreateAuthorization from './SDKCreateClaimAuthorization/CreateAuthorization';
import ConfirmAuthorization from './SDKConfirmClaimAuthorization/ConfirmAuthorization';
import SDKTrackingClaimRequest from './SDKTrackingClaimRequest/SDKTrackingClaimRequest';
import FatcaPopup from './ClaimProcess/ClaimDetail/FatcaPopup';
import ND13 from '../../ND13';
import ThanksPopupSDK from '../../components/ThanksPopupSDK';
import OkPopupTitle from '../../components/OkPopupTitle';
import LoadingIndicator from '../../LoadingIndicator2';
import SDKClaimInit from './Initial/SDKClaimInit';
import AgreeCancelPopup from '../../components/AgreeCancelPopup';

const haveFatca = true;
let consultingViewRequest = false;
export const CLAIM_STATE = Object.freeze({
    CREATE_REQUEST: 0,
    CREATE_AUTHORIZION: 1,
    CONFIRM_AUTHORIZION: 2,
    INIT: 3,
    INIT_CLAIM: 4,
    CLAIM_TYPE: 5,
    CLAIM_DETAIL: 6,
    PAYMENT_METHOD: 7,
    CONTACT: 8,
    ATTACHMENT: 9,
    SUBMIT: 10,
    DONE: 11,
    ND13_INFO_CONFIRMATION: 12,
    ND13_INFO_PO_CONTACT_INFO_OVER_18: 13,
    ND13_INFO_PO_CONTACT_INFO_UNDER_18: 14,
    ND13_INFO_FOLLOW_CONFIRMATION: 15,
})
export const TREAMENT_TYPE = Object.freeze({
    'IN': { desc: 'Nội trú', submitType: 'Inpatient' },
    'OUT': { desc: 'Ngoại trú', submitType: 'Outpatient' },
    'DENT': { desc: 'Nha khoa', submitType: 'Dental' },
})

export const PAYMENT_METHOD_CASE = Object.freeze({
    FULL_ABOVE_18: 0, HC_HS_ABOVE_18: 1, CLAIM_LIFE_OR_HC_HS_UNDER_18: 2, DEAD: 3
})

export const PAYMENT_METHOD_STEP = Object.freeze({
    INIT: 0, STEP1: 1, STEP2: 2
})

export const INITIAL_STATE = () => {
    return {
        claimSubmissionState: CLAIM_STATE.INIT,
        zipCodeList: null,
        bankList: null,
        nationList: [],
        hospitalList: [],
        hospitalResultList: [],
        relationShipList: [],
        selectedCliID: '',
        selectedCliShortName: '',
        selectedCliIndex: '',
        currentState: CLAIM_STATE.CREATE_REQUEST,
        currentStepCode: CLAIM_STEPCODE.INIT,
        pinStep: 0,
        nd13State: '',
        polID: '',
        PolicyStatus: '',
        claimTypeList: null,
        responseLIList: [],
        liInfo: {
            clientId: '', dOB: '', idNum: '', fullName: '', cellPhone: '', email: '',
        },
        selectedCliInfo: {
            clientId: '', dOB: '', idNum: '', gender: '', fullName: '', address: '', cellPhone: '', email: '', isLIS: '',
        },
        initClaimState: {
            occupation: '', selectedLIPolList: null, disabledButton: true,
        },
        claimTypeState: {
            isSickClaim: false,
            isAccidentClaim: null,
            isDeathClaim: null,
            isSamePoLi: false,
            isHealthCare: false,
            isIllness: false,
            isAccident: false,
            isDeath: false,
            isPermanentDisability: false,//rewamp
            isFatalDisease: false,
            isSupportFee: false,
            poDisplayShortName: "",
            disabledButton: true
        },
        claimDetailState: {
            sickInfo: {
                sickFoundTime: '', firstSympton: '', sickFoundFacility: '',//cơ sở y tế thăm khám lần đầu tiên
                sickFoundFacilityChosen: '', cityName: '', sickFoundFacilityOther: '', errors: {
                    dateValid: '', errorList: []
                }
            },
            facilityList: [{
                cityCode: undefined,
                cityName: '',
                name: '',
                treatmentType: '',
                startDate: '',
                endDate: '',
                diagnosis: [],
                otherDiagnostic: '', //Kết quả chuẩn đoán khác
                selectedHospital: '', //Chọn cơ sở y tế
                selectedHospitalChosen: '',
                otherHospital: '',
                invoiceList: [{
                    invoiceAmount: '', invoiceNumber: '', errorList: []
                }],
                isOtherCompanyRelated: '',
                otherCompanyInfo: {
                    companyName: '', paidAmount: ''
                },
                errors: {
                    dateValid: '', errorList: []
                }
            }],
            accidentInfo: {
                hour: null,
                date: '',
                cityCode: undefined,
                cityName: '',
                districtCode: undefined,
                districtName: '',
                address: '',
                accidentDescription: '',
                healthStatus: '',
                selectedNation: '', //Chọn quốc gia
                errors: {
                    dateValid: '', errorList: []
                }
            },
            deathInfo: {
                hour: null,
                date: '',
                cityCode: undefined,
                cityName: '',
                districtCode: undefined,
                districtName: '',
                address: '',
                deathDescription: '',
                errors: {
                    dateValid: '', errorList: []
                }
            },
            isOtherCompanyRelated: '',
            otherCompanyInfoList: [],
            otherCompanyInfo: {
                companyName: '', productName: '', policyNo: '',
            },
            isTreatmentAt: null,
            disabledButton: true,
            isCheckedClaimTypeTreatment: false,
            errorMsg: '',
            errorList: []
        },
        paymentMethodState: {
            lifeBenState: {
                paymentMethodId: '', paymentMethodName: '', transferTypeState: {
                    cityCode: undefined,
                    cityName: '',
                    bankAccountNo: '',
                    bankAccountName: '',
                    bankId: undefined,
                    bankName: '',
                    bankBranchName: '',
                    bankOfficeName: ''
                }, cashTypeState: {
                    receiverName: '',
                    receiverPin: '',
                    receiverPinDate: '',
                    receiverPinLocation: '',
                    bankId: undefined,
                    bankName: '',
                    bankBranchName: '',
                    bankOfficeName: '',
                    cityCode: undefined,
                    cityName: '', // currDateInputType: false,
                },
            },
            healthCareBenState: {
                paymentMethodId: '', paymentMethodName: '', transferTypeState: {
                    cityCode: undefined,
                    cityName: '',
                    bankAccountNo: '',
                    bankAccountName: '',
                    bankId: undefined,
                    bankName: '',
                    bankBranchName: '',
                    bankOfficeName: ''
                }, cashTypeState: {
                    receiverName: '',
                    receiverPin: '',
                    receiverPinDate: '',
                    receiverPinLocation: '',
                    bankId: undefined,
                    bankName: '',
                    bankBranchName: '',
                    bankOfficeName: '',
                    cityCode: undefined,
                    cityName: '', // currDateInputType: false,
                },
            },
            paymentMethodCase: '',
            paymentMethodStep: PAYMENT_METHOD_STEP.INIT,
            choseReceiver: null,
            currentChoseReceiver: null,
            disabledButton: true,
            goneInPaymentMethod: false
        },
        contactState: {
            contactPersonInfo: {
                fullName: '',
                pin: '',
                dob: '',
                email: '',
                phone: '',
                poRelation: undefined,
                poRelationCode: '',
                role: '',
                address: '',
                errors: {
                    dob: '', email: '',
                }
            }, disabledButton: true
        },
        attachmentState: {
            previewVisible: false,
            previewImage: "",
            previewTitle: "",
            eventAttachment: [],
            costAttachment: [],
            benefitAttachment: [],
            attachmentData: [],
            disabledButton: true,
        },
        submissionState: {
            fatcaState: {
                isAmericanNationality: false, isAmericanResidence: false, isAmericanTax: false,
            },
            disabledButton: false,
            relateModalData: {
                poDateOfBirth: {
                    value: '', error: '',
                }, poCellPhone: {
                    value: '', error: '',
                }, poIdentityCard: {
                    value: '', error: '',
                }, poGuardianName: {
                    value: '', error: '',
                }, guardianName: {
                    value: '', error: '',
                }, dateOfBirth: {
                    value: '', error: '',
                }, cellPhone: {
                    value: '', error: '',
                }, email: {
                    value: '', error: '',
                }, relationship: {
                    value: '', error: '',
                }, relation: {
                    value: '', error: '',
                }, relationCode: {
                    value: '', error: '',
                }, refPurpose: {
                    value: '', error: '',
                }, refOtherPurpose: {
                    value: '', error: '',
                }, identityCard: {
                    value: '', error: '',
                },
            }
        },

        jsonResponse: null,
        ClientProfile: null,
        noValidPolicy: false,
        noPhone: false,
        noEmail: false,
        noVerifyPhone: false,
        noVerifyEmail: false,
        claimCheckedMap: {},
        claimCheckedLstBenMap: {},
        isAccidentClaim: null,
        isVietnam: true,
        haveCreatingData: false,
        changeSelectLI: false,
        changeSelectLIIndex: '',
        changeSelectLIItem: undefined,
        saveAndQuit: false,
        role: '',
        intervalId: '',
        toggle: false,
        isCheckPermission: false,
        msgCheckPermission: '',
        openPopupWarningDecree13: false,
        trackingId: '',
        claimId: '',
        poConfirmingND13: '0',
        ND13ClientProfile: null,
        onlyPayment: false,
        liWating: null,
        apiToken: '',
        deviceId: '',
        clientId: '',
        fullName: '',
        agentCode: '',
        agentName: '',
        agentPhone: '',
        sourceSystem: '',
        systemGroupPermission: null,
        currentStateIndex: 0,
        requestId: '',
        authorizedId: '',
        selectedAgent: null,
        invalidDataRequest: false,
        dataDescrypt: null,
        selfKeyInConfirm: false,
        isSubmitting: false,
        haveShowPayment: false,
        isPayment: false,
        processType: 'Claim',
        lockingBy: '',
        disableEdit: false,
        showConfirmAuthorization: false,
        effectiveDate: '',
        consentDisabled: false,
        showSuccess: false,
        sendPOSuccess: false,
        updateInfoState: ND_13.NONE,
        popupConfirmClaimCalled: false,
        trackingClaimData: null,
        dataRequest: null,
        authorzieStatus: '',
        authorizationId: '',
        claimExpired: false,
        tabStatus: '',
        callBackCancel: '/',
        callBackSuccess: '/',
        agentConfirmed: false,
        requestIdMap: {},
        appData: {},
        effectiveDate: '',
        showConfirm: false,
        initClaim: false,
        waittingAgentEdit: false,
        claimExpireAgentMsg: '',
        agentKeyInPOSelfEdit: false,
        status: '',
        showThank: '',
        signature: '',
        idNum: '',
        tempJson: '',
        tempRequestId: '',
        consultingViewRequest: false

    }
};

let cssSystem = '';
class CreateClaimSDK extends Component {

    constructor(props) {
        super(props);
        this.state = INITIAL_STATE();

        this.handlerClickOnLICard = this.clickOnLICard.bind(this);
        this.handlerInputOccupation = this.onChangeInputOccupation.bind(this);
        this.handlerLoadedPolList = this.onLoadPolList.bind(this);
        this.handlerStartClaimProcess = this.startClaimProcess.bind(this);

        this.handlerUpdateMainState = this.updateMainState.bind(this);

        this.handlerBackToPrevStep = this.backToPrevStep.bind(this);

        this.handlerGoToStep = this.goToStep.bind(this);

        this.handlerChangeClaimTypeOption = this.onChangeClaimType.bind(this);
        this.handlerSubmitClaimType = this.submitClaimType.bind(this);

        this.handlerSubmitClaimDetail = this.submitClaimDetail.bind(this);

        this.handlerSubmitPaymentMethod = this.submitPaymentMethod.bind(this);

        this.handlerSubmitContact = this.submitContact.bind(this);

        this.handlerLoadedAttachmentData = this.onLoadAttachmentData.bind(this);
        this.handlerSubmitAttachment = this.submitAttachment.bind(this);

        this.handlerSubmitClaimSubmission = this.submitClaimSubmission.bind(this);

        this.handlerResetState = this.resetState.bind(this);

        this.handleLoadMaster = this.loadMaster.bind(this);

        this.handleAnswerYes = this.answerYes.bind(this);
        this.handleAnswerNo = this.answerNo.bind(this);
        this.handleInVietNam = this.inVietNam.bind(this);
        this.handleNotInVietNam = this.notInVietNam.bind(this);
        this.handleSelectedHospital = this.SelectedHospital.bind(this);
        this.handleSelectedHospitalChosen = this.SelectedHospitalChosen.bind(this);
        this.handleSelectedSickInfoPlaceChosen = this.SelectedSickInfoPlaceChosen.bind(this);
        this.handleSelectOtherSickInfoPlace = this.SelectOtherSickInfoPlace.bind(this);
        this.handleSelectedDiagnosticResult = this.SelectedDiagnosticResult.bind(this);
        this.handleUpdateSubContactState = this.updateSubContactState.bind(this);
        this.handleSaveLocalAndQuit = this.saveLocalAndQuit.bind(this);
        this.handlerClearBelowBenifit = this.clearBelowBenifit.bind(this);
        this.handlerSelectOtherHospital = this.SelectOtherHospital.bind(this);
        this.handlerSelectOtherDiagnostic = this.SelectOtherDiagnostic.bind(this);
        this.handlerUpdateAttachmentData = this.updateAttachmentData.bind(this);
        this.handleEnterOtherCompanyName = this.enterOtherCompanyName.bind(this);
        this.handleEnterOtherCompanyPaid = this.enterOtherCompanyPaid.bind(this);
        this.handleIsOtherCompanyRelated = this.isOtherCompanyRelated.bind(this);
        this.handleLoadClaimTypeList = this.loadClaimTypeList.bind(this);
        this.handleCloseToHome = this.closeToHome.bind(this);
        this.callBackConfirmation = this.onCallBackConfirmation.bind(this);
        this.callBackTrackingId = this.onCallBackTrackingId.bind(this);
        this.callBackClaimID = this.onCallBackClaimID.bind(this);
        this.callBackOnlyPayment = this.onCallBackOnlyPayment.bind(this);
        this.callBackLIWating = this.onCallBackLIWating.bind(this);
        this.callPOConfirmingND13 = this.onPOConfirmingND13.bind(this);
        this.callBackUpdateND13State = this.onUpdateClaimSubmissionState.bind(this);
        this.callBackUpdateND13ClientProfile = this.onUpdateND13ClientProfile.bind(this);
        this.saveState = this.save.bind(this);
        this.cancelClaim = this.onCancelClaim.bind(this);
        this.handleUpdateCurrentStateIndex = this.updateCurrentStateIndex.bind(this);
        this.handleSetClaimRequestId = this.setClaimRequestId.bind(this);
        this.handleSetAuthorizedId = this.setAuthorizedId.bind(this);
        this.handleChooseAgent = this.chooseAgent.bind(this);
        this.handleSetSystemGroupPermission = this.setSystemGroupPermission.bind(this);
        this.handleGoNextStep = this.goNextStep.bind(this);
        this.handleLoadState = this.loadState.bind(this);
        this.handleSetSelfKeyInConfirm = this.setSelfKeyInConfirm.bind(this);
        this.handleSetResponseLIList = this.setResponseLIList.bind(this);
        this.handleOnChangeFatcaState = this.onChangeFatcaState.bind(this);
        this.handleOnClickConfirmCloseButton = this.onClickConfirmCloseButton.bind(this);

        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerSetCloseSucceededButtonRef = this.setCloseSucceededButtonRef.bind(this);
        this.handlerOpenPopupSucceeded = this.openPopupSucceeded.bind(this);
        this.handlerClosePopupSucceeded = this.closePopupSucceeded.bind(this);
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerUpdateClaimRequest = this.updateClaimRequest.bind(this);
        this.handlerUpdateCloneClaimRequest = this.updateCloneClaimRequest.bind(this);
        this.handlerGetStep = this.getStep.bind(this);
        this.handlerSetClientId = this.setClientId.bind(this);
        this.handlerSetClientFullName = this.setClientFullName.bind(this);
        this.handlerConfirmCPConsent = this.confirmCPConsent.bind(this);
    }

    componentDidMount() {
        // clearSession();
        // this.clearState();
        if (this.props.match.params.info) {
            let infoArray = this.props.match.params.info.split('-');
            if (infoArray && infoArray.length === 2) {
                if (getSession(ACCESS_TOKEN)) {
                    this.setState({ apiToken: getSession(ACCESS_TOKEN) });
                }
                this.setState({ disableEdit: true, isSubmitting: false });
                this.requestClaimGetInforNoti(infoArray[1]);
                return;
            }
        }
        if (this.props.paramKey && this.props.paramData) {
            this.setState(INITIAL_STATE());
            this.setState({ loading: true });
            this.getDataInfo(this.props.paramData, this.props.paramKey);
        } else if (this.props.data) {
            let dParam = decodeHtmlEntity(this.props.data);
            console.log("props.data=", this.props.data);
            let data = JSON.parse(AES256.decrypt(dParam, COMPANY_KEY3));
            console.log("data=", data);
            console.log('clientId=', data.poClientId);
            if (data?.callBackCancel) {
                this.setState({ callBackCancel: data?.callBackCancel });
            }
            if (data?.callBackSuccess) {
                this.setState({ callBackSuccess: data?.callBackSuccess });
            }
            this.setState({ agentCode: data.agentCode, agentPhone: data.agentPhone, agentName: data.agentName, poClientId: data.poClientId ? data.poClientId : '', platform: data.platform, sourceSystem: data.sourceSystem });
            cssSystem = data?.sourceSystem?.toLowerCase();
            if (data.requestId) {
                const token = data?.token;
                if (token || getSession(ACCESS_TOKEN) || this.apiToken) {
                    this.requestClaimGetInfor(data.requestId, data, '', token);
                } else {
                    consultingViewRequest = true;
                    this.getTokenAndLoadRequestId(data);
                }
            } else if (data.link) {
                this.requestClaimGetInforLink(data);
            } else if (data?.status && data?.agentCode) {
                this.setState({ dataRequest: data });
                this.getTokenForAgent(data);

            } else {
                this.setState(INITIAL_STATE());
                if (data?.callBackCancel) {
                    this.setState({ callBackCancel: data?.callBackCancel });
                }
                if (data?.callBackSuccess) {
                    this.setState({ callBackSuccess: data?.callBackSuccess });
                }
                this.setState({ dataDescrypt: data });
                if (data.poClientId) {
                    this.setState({ clientId: data.poClientId, deviceId: data.deviceId, agentCode: data.agentCode, agentName: data.agentName, agentPhone: data.agentPhone, sourceSystem: data.sourceSystem, loading: true });
                    this.getToken(data);
                    
                } else {
                    this.setState({ clientId: data.poClientId, deviceId: data.deviceId, agentCode: data.agentCode, agentName: data.agentName, agentPhone: data.agentPhone, sourceSystem: data.sourceSystem, loading: true });
                    this.getPermission(data);
                }
            }
        } else {
            this.handlerResetState(getSession(CLIENT_ID), getDeviceId(), getSession(ACCESS_TOKEN));
            // let intervalId = setInterval(this.save, 10000);
            this.setState({ intervalId: intervalId });
            cpSaveLog(`Web_Open_${PageScreen.CLAIM_LIST_INSURED}`);
            trackingEvent("Yêu cầu quyền lợi", `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`, `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`,);
            this.fetchCheckConfigPermission();
        }
    }

    componentWillUnmount() {
        if (this.state.intervalId) {
            clearInterval(this.state.intervalId);
        }
        cpSaveLog(`Web_Close_${PageScreen.CLAIM_LIST_INSURED}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`, `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`,);

    }

    setNd13State(value) {
        let state = this.state;
        state.nd13State = value;
        this.setState(state);
    }

    setClientId(value) {
        this.setState({ clientId: value });
    }

    setClientFullName(value) {
        this.setState({ fullName: value });
    }

    onChangeFatcaState(event) {
        const jsonState = this.state;
        const fatcaState = jsonState.submissionState.fatcaState;
        const target = event.target;
        fatcaState[target.id] = target.checked;
        this.setState(jsonState);
    }

    setUpdateInfoState(value) {
        this.setState({ updateInfoState: value });
    }

    onClickConfirmCloseButton() {
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        if (!this.state.disableEdit || !this.state.agentKeyInPOSelfEdit) {
            const jsonState = this.state;
            let fatcaState = jsonState.submissionState.fatcaState;
            fatcaState['isAmericanNationality'] = false;
            fatcaState['isAmericanResidence'] = false;
            fatcaState['isAmericanTax'] = false;
            // jsonState.popupConfirmClaimCalled = false;
            jsonState.submissionState.fatcaState = fatcaState;
            this.setState(jsonState);
        }
    }

    setResponseLIList = (value) => {
        this.setState({ responseLIList: value });
    }

    goNextStep() {
        this.setState({ currentState: this.state.currentState + 1 })
    }

    setClaimRequestId(value) {
        this.setState({ requestId: value });
    }

    setAuthorizedId(value) {
        this.setState({ authorizedId: value });
    }

    chooseAgent(value) {
        this.setState({ selectedAgent: value });
    }


    updateCurrentStateIndex = (index) => {
        this.setState({ currentStateIndex: index })
    }

    setSelfKeyInConfirm = (value) => {
        this.setState({ selfKeyInConfirm: value });
    }

    setNoValidPolicy = (value) => {
        this.setState({ noValidPolicy: value });
    }

    setShowConfirmAuthorization = (value) => {
        this.setState({ showConfirmAuthorization: value });
    }

    getToken = (data) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response1=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                const initState = Response1.SystemGroupPermission?.[0]?.StepOrder;
                const stepCode = Response1.SystemGroupPermission?.[0]?.StepCode;
                //Gen API token
                if (Response1.SystemGroupPermission?.[0]?.SourceSystem !== 'DConnect') {
                    getMircoToken(jsonData, encryptedText).then(Res => {
                        if (Res.code === CODE_SUCCESS && Res.data) {
                            console.log('resss=', Res.data);
                            let token = Res?.data?.newAccessToken;
                            console.log('gen success=' + token);
                            this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, data.poClientId, token);
                            trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                            this.setState({ apiToken: token, signature: data?.poClientId });
                            setSession(ACCESS_TOKEN, token);
                            setSession(SIGNATURE, data?.poClientId);
                            setDeviceId(data?.deviceId);
                            this.loadProfileCustomerForce(data.poClientId, data.deviceId, token, Response1.SystemGroupPermission, initState, stepCode);

                        } else {
                            console.log('Gen token error');
                        }
                    }).catch(error => {
                        console.log(error);
                    });
                } else {
                    let state = this.state;
                    state.systemGroupPermission = Response1.SystemGroupPermission;
                    state.currentState = initState;
                    state.stepCode = stepCode;
                    this.loadProfileCustomer(data.poClientId, data.deviceId, data?.token || data?.apiToken || getSession(ACCESS_TOKEN), Response1.SystemGroupPermission, initState, stepCode, data, state);
                    // this.setState({ systemGroupPermission: Response1.SystemGroupPermission, currentState: initState, currentStepCode: stepCode });
                    // this.loadMaster(data.poClientId, data.deviceId, data?.apiToken, Response1.SystemGroupPermission)
                }

            }
        }).catch(error => {
            console.log(error);
        });

    }

    getPermission = (data) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response2=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                const initState = Response1.SystemGroupPermission?.[0]?.StepOrder;
                const stepCode = Response1.SystemGroupPermission?.[0]?.StepCode;
                this.setState({ systemGroupPermission: Response1.SystemGroupPermission, currentState: initState, currentStepCode: stepCode, dataDescrypt: data });
            }
        }).catch(error => {
            console.log(error);
        });

    }

    getPermissionOnly = (data, lockingBy, status, state ) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response2=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                let disableEdit = (Response1.SystemGroupPermission?.[0]?.Role !== lockingBy);
                if (!lockingBy) {
                    disableEdit = false;
                }
                if (lockingBy && (lockingBy !== 'PO') && (Response1.SystemGroupPermission?.[0]?.TriggerMode === 'PO') && (Response1.SystemGroupPermission?.[0]?.Role === 'PO')) {
                    // this.setState({ agentKeyInPOSelfEdit: true });
                    state.agentKeyInPOSelfEdit = true;
                } else {
                    // this.setState({ agentKeyInPOSelfEdit: false });
                    state.agentKeyInPOSelfEdit = false;
                }
                let stepCode = '';//this.getStepCodebyStepAndGroup(this.state.currentState, Response1.SystemGroupPermission);
                if ((status === SDK_REQUEST_STATUS.POREVIEW) || (status === SDK_REQUEST_STATUS.REEDIT)) {
                    stepCode = 'REVIEW';
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                        state.updateInfoState = ND_13.NONE;
                    }
                } else if (status === SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN) {
                    stepCode = 'SDK_DLCN';
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                        stepCode = 'REVIEW';
                    }
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                        state.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                        // this.setState({updateInfoState: ND_13.ND13_INFO_FOLLOW_CONFIRMATION});
                        stepCode = 'SDK_DLCN';
                    } else {
                        state.updateInfoState = ND_13.NONE;
                        // this.setState({updateInfoState: ND_13.NONE});
                    }

                } else if (status === SDK_REQUEST_STATUS.REJECT_DLCN) {
                    // this.setState({updateInfoState: ND_13.ND13_INFO_FOLLOW_CONFIRMATION});
                    if ((Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO)) {
                        state.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                    } else {
                        state.updateInfoState = ND_13.NONE;
                    }
                    stepCode = 'SDK_DLCN';
                } else {
                    stepCode = this.getStepCodebyStepAndGroup(this.state.currentState, Response1.SystemGroupPermission);
                }
                state.systemGroupPermission = Response1.SystemGroupPermission;
                state.disableEdit = disableEdit;
                state.currentState = this.getStepbyGroup(stepCode, Response1.SystemGroupPermission);
                // this.setState({ systemGroupPermission: Response1.SystemGroupPermission, disableEdit: disableEdit });//set dong stepcode thi tot hon
                // this.handlerGoToStep(this.getStepbyGroup(stepCode, Response1.SystemGroupPermission));
                if (Response1.SystemGroupPermission?.[0]?.Role !== SDK_ROLE_PO) {
                    this.setState(state);
                } 
                // this.setState({ showConfirmAuthorization: true });
                let from = getUrlParameter("fromApp");
                if (from) {
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                        this.loadProfileCustomer(data?.poClientId, data?.deviceId, state.apiToken || data?.token, Response1.SystemGroupPermission, null, null, data, state);
                    } else {
                        this.loadProfileCustomerForce(data.poClientId, data.deviceId, token, Response1.SystemGroupPermission, null, null);
                    }
                } else {
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                        let noPhone = getSession(CELL_PHONE) ? false : true;
                        let noEmail = getSession(EMAIL) ? false : true;
                        let noVerifyPhone = (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) === '1') && getSession(TWOFA) && (getSession(TWOFA) === '1')) ? false : true;
                        let noVerifyEmail = (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) === '1')) ? false : true;
                        if (noPhone || noEmail || noVerifyPhone || noVerifyEmail) {
                            this.setState({
                                noPhone: noPhone,
                                noEmail: noEmail,
                                noVerifyPhone: noVerifyPhone,
                                noVerifyEmail: noVerifyEmail
                            });
                            if (noVerifyPhone || noVerifyEmail) {
                                setSession(REVIEW_LINK, window.location.href);
                            }
                        } else {
                            this.setState({
                                noPhone: noPhone,
                                noEmail: noEmail,
                                noVerifyPhone: noVerifyPhone,
                                noVerifyEmail: noVerifyEmail
                            });
                            //Bo sung check da uy quyen chua de show man hinh xac nhan uy quyen
                            // this.setState({ showConfirmAuthorization: true });
                        }
                    }
                    this.reLoadMaster();
                }
                
            }
        }).catch(error => {
            console.log(error);
        });

    }

    getPermissionLoadStateRequestId = (data, lockingBy, status, state) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response2=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                let disableEdit = (Response1.SystemGroupPermission?.[0]?.Role !== lockingBy);
                if (!lockingBy) {
                    disableEdit = false;
                }
                if (lockingBy && (lockingBy !== 'PO') && (Response1.SystemGroupPermission?.[0]?.TriggerMode === 'PO') && (Response1.SystemGroupPermission?.[0]?.Role === 'PO')) {
                    state.agentKeyInPOSelfEdit = true;
                }
                let stepCode = '';//this.getStepCodebyStepAndGroup(this.state.currentState, Response1.SystemGroupPermission);
                if ((status === SDK_REQUEST_STATUS.POREVIEW) || (status === SDK_REQUEST_STATUS.REEDIT)) {
                    stepCode = 'REVIEW';
                } else if (status === SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN) {
                    stepCode = 'SDK_DLCN';
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                        stepCode = 'REVIEW';
                    }
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                        state.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                    } else {
                        state.updateInfoState = ND_13.NONE;
                    }

                } else if (status === SDK_REQUEST_STATUS.REJECT_DLCN) {
                    state.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                    stepCode = 'SDK_DLCN';
                } else {
                    stepCode = this.getStepCodebyStepAndGroup(this.state.currentState, Response1.SystemGroupPermission);
                }
                state.systemGroupPermission = Response1.SystemGroupPermission;
                state.disableEdit = disableEdit;
                state.currentState = this.getStepbyGroup(stepCode, Response1.SystemGroupPermission);                
                this.setState(state);
                // if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                //     this.checkAuthorization();
                // }
                // this.setState({ showConfirmAuthorization: true });
                this.reLoadMaster();
                if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                    let noPhone = getSession(CELL_PHONE) ? false : true;
                    let noEmail = getSession(EMAIL) ? false : true;
                    let noVerifyPhone = (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) === '1') && getSession(TWOFA) && (getSession(TWOFA) === '1')) ? false : true;
                    let noVerifyEmail = (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) === '1')) ? false : true;
                    if (noPhone || noEmail || noVerifyPhone || noVerifyEmail) {
                        this.setState({
                            noPhone: noPhone,
                            noEmail: noEmail,
                            noVerifyPhone: noVerifyPhone,
                            noVerifyEmail: noVerifyEmail
                        });
                        if (noVerifyPhone || noVerifyEmail) {
                            setSession(REVIEW_LINK, window.location.href);
                        }
                    } else {
                        this.setState({
                            noPhone: noPhone,
                            noEmail: noEmail,
                            noVerifyPhone: noVerifyPhone,
                            noVerifyEmail: noVerifyEmail
                        });
                        //Bo sung check da uy quyen chua de show man hinh xac nhan uy quyen
                        // this.setState({ showConfirmAuthorization: true });
                    }
                }
            }
        }).catch(error => {
            console.log(error);
        });

    }

    getPermissionLoadState = (data, lockingBy, status, state) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response2=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                let disableEdit = (Response1.SystemGroupPermission?.[0]?.Role !== lockingBy);
                if (!lockingBy) {
                    disableEdit = false;
                }
                if (lockingBy && (lockingBy !== 'PO') && (Response1.SystemGroupPermission?.[0]?.TriggerMode === 'PO') && (Response1.SystemGroupPermission?.[0]?.Role === 'PO')) {
                    // disableEdit = false;
                    this.setState({ agentKeyInPOSelfEdit: true });
                } else {
                    this.setState({ agentKeyInPOSelfEdit: false });
                }
                let stepCode = '';//this.getStepCodebyStepAndGroup(this.state.currentState, Response1.SystemGroupPermission);
                if ((status === SDK_REQUEST_STATUS.POREVIEW) || (status === SDK_REQUEST_STATUS.REEDIT)) {
                    stepCode = 'REVIEW';
                } else if (status === SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN) {
                    stepCode = 'SDK_DLCN';
                    if (Response1.SystemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
                        stepCode = 'REVIEW';
                    }
                    state.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                } else {
                    stepCode = this.getStepCodebyStepAndGroup(state.currentState, Response1.SystemGroupPermission);
                }
                state.systemGroupPermission = Response1.SystemGroupPermission;
                state.disableEdit = disableEdit;
                state.currentState = this.getStepbyGroup(stepCode, Response1.SystemGroupPermission);
                this.setState(state);
                getMircoToken(jsonData, encryptedText).then(Res => {
                    if (Res.code === CODE_SUCCESS && Res.data) {
                        console.log('resss=', Res.data);
                        let token = Res?.data?.newAccessToken;
                        console.log('gen success=' + token);
                        this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, data.poClientId, token);
                        trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                        this.setState({ apiToken: token, signature: data?.poClientId});
                        setSession(ACCESS_TOKEN, token);
                        setSession(SIGNATURE, data?.poClientId);
                        setDeviceId(data?.deviceId);
                        this.loadProfileCustomerForce(state?.clientId, getDeviceId(), token, Response1.SystemGroupPermission, state.currentState, stepCode, true);

                    } else {
                        console.log('Gen token error');
                    }
                }).catch(error => {
                    console.log(error);
                });
                // this.reLoadMaster();
            }
        }).catch(error => {
            console.log(error);
        });

    }


    getPermissionForTracking = (data, lockingBy) => {
        //rsa
        let paramData = JSON.stringify(data);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        getPermissionByGroupAndProccessType(jsonData, encryptedText).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response2=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                let disableEdit = (Response1.SystemGroupPermission?.[0]?.Role !== lockingBy);
                if (!lockingBy) {
                    disableEdit = false;
                }
                if (lockingBy && (lockingBy !== 'PO') && (Response1.SystemGroupPermission?.[0]?.TriggerMode === 'PO') && (Response1.SystemGroupPermission?.[0]?.Role === 'PO')) {
                    this.setState({ agentKeyInPOSelfEdit: true });
                } else {
                    this.setState({ agentKeyInPOSelfEdit: false });
                }
                this.setState({ systemGroupPermission: Response1.SystemGroupPermission, disableEdit: disableEdit, currentState: this.getStepbyGroup(CLAIM_STEPCODE.TRACKING_CLAIM, Response1.SystemGroupPermission), tabStatus: data?.status });//set dong stepcode thi tot hon
                this.requestClaimGetList(data, Response1.SystemGroupPermission);

            }
        }).catch(error => {
            console.log(error);
        });

    }

    getDataInfo = (ensData, key) => {
        let jsonData = {
            data: ensData
        }
        checkData(jsonData, key).then(Response => {
            console.log('Response=', Response);
            if (Response.sourceSystem !== 'DConnect') {
                clearSession();
                this.clearState();
            }
            if (!Response.sourceSystem || !Response.processType) {
                this.setState({ invalidDataRequest: true });
                return;
            }
            this.setState({ appData: AES256.encrypt(JSON.stringify(Response), COMPANY_KEY3) })
            this.setState({ agentCode: Response.agentCode, agentPhone: Response.agentPhone, agentName: Response.agentName, poClientId: Response.poClientId ? Response.poClientId : '', platform: Response.platform, sourceSystem: Response.sourceSystem });
            //if (Response.requestId && !Response.status) {
            if (Response.requestId) {
                const token = Response?.token;
                if (Response.poClientId) {
                    setDeviceId(Response.deviceId);
                    this.requestClaimGetInfor(Response.requestId, Response, '', token);
                    // this.getPermissionByKeyValue(ensData, key, token, Response.poClientId, Response.deviceId, Response);
                } else {
                    this.getTokenAndLoadRequestId(Response);  
                }

            } else if (Response?.status && Response?.agentCode) {
                this.setState({ dataRequest: Response });
                this.getTokenForAgent(Response);

            } else if (!Response.poClientId) {
                this.getPermission(Response);
                return;
            } else {
                if (Response.requestId) {
                    this.requestClaimGetInfor(Response.requestId);
                }
                this.setState({ clientId: Response.poClientId });
                if (Response.deviceId) {
                    setDeviceId(Response.deviceId);
                }
                const token = Response?.token;
                this.getPermissionByKeyValue(ensData, key, token, Response.poClientId, Response.deviceId, Response);            
            }
            cssSystem = Response.sourceSystem?.toLowerCase();
            this.setState({ sourceSystem: Response.sourceSystem });
        }).catch(error => {
            console.log(error);
        });

    }

    setCurrentState = (value) => {
        this.setState({ currentState: value });
    }

    getPermissionByKeyValue =(ensData, key, token, clientId, deviceId, data) => {
        let jsonData = {
            data: ensData
        }
        getPermissionByGroupAndProccessType(jsonData, key).then(Resp => {
            let Response1 = Resp.Response;
            console.log('Response3=', Response1);
            if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                const initState = Response1.SystemGroupPermission?.[0]?.StepOrder;
                const stepCode = Response1.SystemGroupPermission?.[0]?.StepCode;
                let state = this.state;
                if (token && clientId) {
                    // if (data) {
                    //     this.handlerGoToStep(this.getStepbyGroup('REVIEW', Response1.SystemGroupPermission));
                    // }
                    state.systemGroupPermission = Response1.SystemGroupPermission;
                    state.systemGroupPermission = Response1.SystemGroupPermission;
                    state.currentState = initState;
                    state.stepCode = stepCode;
                    this.loadProfileCustomer(clientId, deviceId, token, Response1.SystemGroupPermission, initState, stepCode, data, state);
                    // this.loadProfileCustomer(data.poClientId, data.deviceId, data?.token || getSession(ACCESS_TOKEN), Response1.SystemGroupPermission, initState, stepCode, null, this.state);

                } else {
                    // this.setState({systemGroupPermission: Response1.SystemGroupPermission, currentState: initState});
                    this.getTokenApp(ensData, key, Response1.SystemGroupPermission, initState, stepCode, clientId, deviceId);
                }
            }
        }).catch(error => {
            console.log(error);
        });

    }

    loadProfileCustomer = (clientId, deviceId, token, systemGroupPermission, currentState, stepCode, data, state) => {
        let profileRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GetClientProfile',
                APIToken: token,
                Authentication: AUTHENTICATION,
                DeviceId: deviceId,
                UserLogin: clientId,
                OS: getOSAndBrowserInfo(),
                Project: 'mcp',
            }
        };
        if (!state) {
            state = this.state;
        }
        if (token) {
            state.apiToken = token;
        }
        setSession(ACCESS_TOKEN, token);
        setSession(SIGNATURE, clientId);
        checkAccount(profileRequest).then(response => {
            console.log('profile response=', JSON.stringify(response));
            if (response.Response.Result === 'true' && response.Response.ErrLog === 'Successfull' && response.Response.ClientProfile) {
                //clear danh sach hop dong cua user truoc neu dang login
                if (getSession(POL_LIST_CLIENT)) {
                    sessionStorage.removeItem(POL_LIST_CLIENT);
                }
                setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                //setSession(USER_LOGIN, UserLogin);
                setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix impact for rewamp username
                // setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                this.setState({ clientId: response.Response.ClientProfile[0].ClientID, fullName: response.Response.ClientProfile[0].FullName });
                if (state) {
                    state.fullName = response.Response.ClientProfile[0].FullName;
                    state.clientId = response.Response.ClientProfile[0].ClientID;
                }
                setSession(GENDER, response.Response.ClientProfile[0].Gender);
                setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
                setSession(EMAIL, response.Response.ClientProfile[0].Email);
                setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                const isPhoneVerified = (response.Response.ClientProfile[0]?.VerifyCellPhone === '1') && (response.Response.ClientProfile[0].TwoFA === '1');
                const isEmailVerified = response.Response.ClientProfile[0]?.VerifyEmail === '1';
                const hasCellPhone = response.Response.ClientProfile[0].CellPhone;
                const hasEmail = response.Response.ClientProfile[0].Email;
                // this.setState({
                //     noPhone: !hasCellPhone,
                //     noEmail: !hasEmail,
                //     noVerifyPhone: !isPhoneVerified,
                //     noVerifyEmail: !hasEmail
                // });
                // setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                // setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
                if (response.Response.ClientProfile[0].POID) {
                    setSession(POID, response.Response.ClientProfile[0].POID);
                    this.setState({idNum: response.Response.ClientProfile[0].POID});
                }
                if (response.Response.ClientProfile[0].DOB) {
                    setSession(DOB, response.Response.ClientProfile[0].DOB);
                }
                if (response.Response.ClientProfile[0].DCID) {
                    setSession(DCID, response.Response.ClientProfile[0].DCID);
                }
                let disableEdit = (systemGroupPermission?.[0]?.Role !== this.state.lockingBy);
                if (!this.state.lockingBy) {
                    disableEdit = false;
                }
                console.log('claim disableEdit=', disableEdit);
                // if (!isPhoneVerified || !isEmailVerified || !hasCellPhone || !hasEmail) {
                //     return;
                // }
                this.checkAuthorizationLoadState(state, clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail, data);
                // this.setState(state);
                // this.loadState(clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail);
            } 
        }).catch(error => {
            console.log(error);
        });
    }

    loadProfileCustomerForce = (customerId, deviceId, token, systemGroupPermission, currentState, stepCode, noSetDisableEdit) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: deviceId,
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: token
        }
        let data = {
            customerId: customerId,
        }
        setSession(ACCESS_TOKEN, token);
        setSession(SIGNATURE, customerId);
        let request = buildMicroRequest(metadata, data);

        getCustomerProfile(request).then(Res => {
            console.log('getCustomerProfile=', Res.data);
            if (Res.code === CODE_SUCCESS && Res.data) {
                //clear danh sach hop dong cua user truoc neu dang login
                if (getSession(POL_LIST_CLIENT)) {
                    sessionStorage.removeItem(POL_LIST_CLIENT);
                }
                let item = Res.data?.[0];
                setSession(CLIENT_ID, item?.customerId);
                setSession(USER_LOGIN, item?.customerId);
                setSession(CELL_PHONE, item?.phone ? item?.phone : '');
                setSession(FULL_NAME, item?.customerName);
                this.setState({ clientId: item?.customerId, fullName: item?.customerName });
                setSession(GENDER, item?.gender);
                setSession(ADDRESS, item?.address);
                if (item?.email) {
                    setSession(EMAIL, item?.email);
                }
                if (item?.verifyPhone) {
                    setSession(VERIFY_CELL_PHONE, item?.verifyPhone);
                }
                if (item?.verifyEmail) {
                    setSession(VERIFY_EMAIL, item?.verifyEmail);
                }
                if (item?._2fa) {
                    setSession(TWOFA, item?._2fa);
                }
                if (item?.idNum) {
                    setSession(POID, item?.idNum);
                    this.setState({idNum: item?.idNum});
                }
                if (item?.dob) {
                    setSession(DOB, item?.dob);
                }
                if (item?.dcid) {
                    setSession(DCID, item?.dcid);
                }
                if (noSetDisableEdit) {
                    if ((currentState !== null) && (stepCode !== null)) {
                        this.setState({ systemGroupPermission: systemGroupPermission, currentState: currentState, currentStepCode: stepCode });
                    } else {
                        this.setState({ systemGroupPermission: systemGroupPermission });
                    }
                } else {
                    let disableEdit = (this.state.systemGroupPermission?.[0]?.Role !== this.state.lockingBy);
                    if (!this.state.lockingBy) {
                        disableEdit = false;
                    }
                    if ((currentState !== null) && (stepCode !== null)) {
                        this.setState({ systemGroupPermission: systemGroupPermission, currentState: currentState, currentStepCode: stepCode, disableEdit: disableEdit });
                        this.setState({ systemGroupPermission: systemGroupPermission, currentState: currentState, currentStepCode: stepCode });
                    } else {
                        this.setState({ systemGroupPermission: systemGroupPermission, disableEdit: disableEdit });
                    }
                }
                setTimeout(() => {
                    this.loadState(customerId, deviceId, token, 1, 1, 1, 1);
                }, 50);
                // this.loadState(customerId, deviceId, token, 1, 1, 1, 1);
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    getTokenApp = (ensData, ensKey, systemGroupPermission, currentState, stepCode, clientId, deviceId) => {
        let jsonData = {
            data: ensData
        }
        //Gen API token
        getMircoToken(jsonData, ensKey).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('resss=', Res.data);
                let token = Res?.data?.newAccessToken;
                // let deviceId = Response.DeviceId || getDeviceId();
                // let clientId = Response.UserLogin;
                console.log('gen success=' + token);
                this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, clientId, token);
                trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                this.setState({ apiToken: token, deviceId: deviceId, clientId: clientId, signature: clientId });
                setSession(ACCESS_TOKEN, token);
                setSession(SIGNATURE, clientId);
                setDeviceId(deviceId);
                let profileRequest = {
                    jsonDataInput: {
                        Company: COMPANY_KEY,
                        Action: 'GetClientProfile',
                        APIToken: token,
                        Authentication: AUTHENTICATION,
                        DeviceId: deviceId ? deviceId : getDeviceId(),
                        UserLogin: clientId,
                        OS: getOSAndBrowserInfo(),
                        Project: 'mcp',
                    }
                };
                console.log('abc=', JSON.stringify(profileRequest));
                if (this.state.sourceSystem === 'DConnect') {
                    checkAccount(profileRequest).then(response => {
                        if (response.Response.Result === 'true' && response.Response.ErrLog === 'Successfull' && response.Response.ClientProfile) {
                            //clear danh sach hop dong cua user truoc neu dang login
                            if (getSession(POL_LIST_CLIENT)) {
                                sessionStorage.removeItem(POL_LIST_CLIENT);
                            }
                            setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                            //setSession(USER_LOGIN, UserLogin);
                            setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix impact for rewamp username
                            // setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                            setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                            setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                            this.setState({ clientId: response.Response.ClientProfile[0].ClientID, fullName: response.Response.ClientProfile[0].FullName });
                            setSession(GENDER, response.Response.ClientProfile[0].Gender);
                            setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                            setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
                            setSession(EMAIL, response.Response.ClientProfile[0].Email);
                            setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                            setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                            setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                            const isPhoneVerified = response.Response.ClientProfile[0]?.VerifyCellPhone === '1';
                            const isEmailVerified = response.Response.ClientProfile[0]?.VerifyEmail === '1';
                            const hasCellPhone = response.Response.ClientProfile[0].CellPhone;
                            const hasEmail = response.Response.ClientProfile[0].Email;
                            // setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                            // setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
                            if (response.Response.ClientProfile[0].POID) {
                                setSession(POID, response.Response.ClientProfile[0].POID);
                                this.setState({idNum: response.Response.ClientProfile[0].POID});
                            }
                            if (response.Response.ClientProfile[0].DOB) {
                                setSession(DOB, response.Response.ClientProfile[0].DOB);
                            }
                            if (response.Response.ClientProfile[0].DCID) {
                                setSession(DCID, response.Response.ClientProfile[0].DCID);
                            }
                            let disableEdit = (this.state.systemGroupPermission?.[0]?.Role !== this.state.lockingBy);
                            if (!this.state.lockingBy) {
                                disableEdit = false;
                            }
                            console.log('claim disableEdit=', disableEdit);
                            this.setState({ systemGroupPermission: systemGroupPermission, currentState: currentState, currentStepCode: stepCode, disableEdit: disableEdit });
                            this.loadState(clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail);
                        }
                    }).catch(error => {
                        console.log(error);
                    });
                } else {
                    this.loadProfileCustomerForce(clientId, deviceId, token, systemGroupPermission, currentState, stepCode);
                }


            } else {
                console.log('Gen token error');
            }
        }).catch(error => {
            console.log(error);
        });
    }

    // getTokenForAgentCreateClaim = (data) => {

    //     let copyData = cloneDeep(data);
    //     copyData.poClientId = copyData.agentCode;
    //     //rsa
    //     let paramData = JSON.stringify(copyData);
    //     let pass = genPassword(32);
    //     let ensData = AES256.encrypt(paramData, pass);
    //     const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
    //     const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
    //         md: NodeForge.md.sha256.create(),
    //         mgf1: {
    //             md: NodeForge.md.sha1.create()
    //         }
    //     });
    //     const encryptedText = NodeForge.util.encode64(encryptedBytes);
    //     console.log('Enscrypted=');
    //     console.log(encryptedText);
    //     //end rsa
    //     let jsonData = {
    //         data: ensData
    //     }
    //     console.log("data=", data);
    //     console.log("key=", encryptedText);
    //     if (data.deviceId) {
    //         setSession(DEVICE_ID, data.deviceId);
    //     }
    //     //Gen API token
    //     getMircoToken(jsonData, encryptedText).then(Res => {
    //         if (Res.code === CODE_SUCCESS && Res.data) {
    //             console.log('resss=', Res.data);
    //             let token = Res?.data?.newAccessToken;
    //             console.log('gen agent token success=' + token);
    //             this.setState({ apiToken: token, signature: copyData.agentCode });
    //             setSession(ACCESS_TOKEN, token);
    //             setSession(SIGNATURE, copyData.agentCode);
    //             this.getPermissionByKeyValue(ensData, encryptedText, token, "", data.deviceId, copyData);
    //         }

    //     }).catch(error => {
    //         console.log(error);
    //     });

    // }


    getTokenForAgent = (data) => {
        let copyData = cloneDeep(data);
        copyData.poClientId = copyData.agentCode;
        //rsa
        let paramData = JSON.stringify(copyData);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        //Gen API token
        getMircoToken(jsonData, encryptedText).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('resss=', Res.data);
                let token = Res?.data?.newAccessToken;
                console.log('gen agent token success=' + token);
                this.setState({ apiToken: token, signature: copyData.agentCode});
                setSession(ACCESS_TOKEN, token);
                setSession(SIGNATURE, copyData.agentCode);
                // this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, data.poClientId, token);
                // trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                // this.setState({ apiToken: token });
                this.getPermissionForTracking(data);
            }

        }).catch(error => {
            console.log(error);
        });

    }

    getTokenAndLoadRequestId = (data) => {
        let copyData = cloneDeep(data);
        if (!copyData.poClientId) {
            copyData.poClientId = copyData.agentCode;
        }
        
        //rsa
        let paramData = JSON.stringify(copyData);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        //Gen API token
        getMircoToken(jsonData, encryptedText).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('resss=', Res.data);
                let token = Res?.data?.newAccessToken;
                console.log('gen agent token success=' + token);
                this.setState({ apiToken: token, signature: copyData.poClientId});
                setSession(ACCESS_TOKEN, token);
                setSession(SIGNATURE, copyData.poClientId);
                //Load requestId
                this.requestClaimGetInfor(data.requestId, data, '', token);
            }

        }).catch(error => {
            console.log(error);
        });

    }

    cpSaveLogPartner = (functionName, clientId, apiToken) => {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClientID: clientId,
                DeviceId: this.state.deviceId ? this.state.deviceId : getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: clientId
            }
        }
        CPSaveLog(masterRequest).then(res => {
        }).catch(error => {
            console.log('error=' + error);
        });
    }

    onCallBackConfirmation(data) {
        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + data.CustomerID).then(Res => {
            if (Res?.v) {
                let dataResponse = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
                // this.setState({
                //     ...dataResponse,
                //     trackingId: dataResponse.TrackingID,
                // });
            }
        }).catch(error => {
            console.log(error);
        });
    }

    onCallBackTrackingId(value) {
        this.setState({
            trackingId: value,
        });
    }

    onCallBackClaimID(value) {
        this.setState({
            claimId: value,
        });
    }

    onCallBackOnlyPayment(value) {
        this.setState({
            onlyPayment: value,
        });
        setInterval(this.save, 200);
    }

    onCallBackLIWating(value) {
        this.setState({
            liWating: value,
        });
        setInterval(this.save, 200);
    }

    onPOConfirmingND13(value) {
        this.setState({
            poConfirmingND13: value,
        });
    }

    onUpdateClaimSubmissionState(value) {
        let jsonState = this.state;
        jsonState.claimSubmissionState = value;
        setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(this.jsonState));
        this.setState(jsonState);
    }

    onUpdateND13ClientProfile(value) {
        this.setState({
            ND13ClientProfile: value
        });
    }

    fetchCheckConfigPermission() {
        let request = {
            jsonDataInput: {
                Action: 'GetConfigPermission',
                Authentication: AUTHENTICATION,
                DeviceId: this.state.deviceId ? this.state.deviceId : getDeviceId(),
                Project: 'mcp',
                DCID: getSession(DCID),
                FunctionID: '6',
            }
        }
        GetConfiguration(request).then(res => {
            let Response = res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                if (!isEmpty(Response.ClientProfile)) {
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

    // cpSaveLog(functionName) {
    //     const masterRequest = {
    //         jsonDataInput: {
    //             OS: "Web",
    //             APIToken: this.state.apiToken || getSession(ACCESS_TOKEN),
    //             Authentication: AUTHENTICATION,
    //             ClientID: this.state.clientId || getSession(CLIENT_ID),
    //             DeviceId: this.state.deviceId?this.state.deviceId:getDeviceId(),
    //             DeviceToken: "",
    //             function: functionName,
    //             Project: "mcp",
    //             UserLogin: getSession(USER_LOGIN)
    //         }
    //     }
    //     CPSaveLog(masterRequest).then(res => {
    //         this.setState({renderMeta: true});
    //     }).catch(error => {
    //         this.setState({renderMeta: true});
    //     });
    // }

    getClaimList() {
        let request = {
            Action: "GetClaimICD", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {

            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({ hospitalResultList: Response.ClientProfile });
            }
        }).catch(error => {
        });

    }

    getHospitalList() {
        let request = {
            Action: "GetHospitalMedic", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {
            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({ hospitalList: Response.ClientProfile });
            }
        }).catch(error => {
        });
    }

    getPolList(clientId, deviceId, apiToken) {
        this.getPolicyListEligibleRequestClaimByClientId(clientId, deviceId, apiToken);
    }

    getPolicyListEligibleRequestClaimByClientId = (clientId, deviceId, apiToken) => {
        let noPhone = getSession(CELL_PHONE) ? false : true;
        let noEmail = getSession(EMAIL) ? false : true;
        let noVerifyPhone = (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) === '1') && getSession(TWOFA) && (getSession(TWOFA) === '1')) ? false : true;
        let noVerifyEmail = (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) === '1')) ? false : true;
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: deviceId,
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.state.systemGroupPermission?.[0]?.SoureSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: apiToken
        }
        let data = {
            customerId: clientId || getSession(CLIENT_ID),
            agentCode: this.state.agentCode
        }

        let request = buildMicroRequest(metadata, data);
        getPolicyListEligibleRequestClaimByClientId(request).then(Res => {
            console.log('getPolicyListEligibleRequestClaimByClientId=', Res.data);
            if (Res.code === CODE_SUCCESS && Res.data) {
                let noValidPol = true;
                let profile = Res.data;
                if (profile && profile.length > 0) {
                    noValidPol = false;
                }
                if (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                    this.setState({
                        noPhone: noPhone,
                        noEmail: noEmail,
                        noVerifyPhone: noVerifyPhone,
                        noVerifyEmail: noVerifyEmail,
                        ClientProfile: profile,
                        noValidPolicy: noValidPol
                    });
                    setSession(POL_LIST_CLIENT4CLAIM, JSON.stringify(profile));
                } else {
                    if (noValidPol) {
                        this.setState({
                            ClientProfile: profile,
                            noValidPolicy: noValidPol,
                            initClaim: false
                        });
                    } else {
                        this.setState({
                            ClientProfile: profile,
                            noValidPolicy: noValidPol,
                            showConfirm: true,
                            initClaim: true
                        });
                    }

                }

            } else {
                if (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                    this.setState({
                        noPhone: noPhone,
                        noEmail: noEmail,
                        noVerifyPhone: noVerifyPhone,
                        noVerifyEmail: noVerifyEmail
                    });
                }
                this.setState({
                    noValidPolicy: true,
                    initClaim: false
                });
            }
        }).catch(error => {
            console.log(error);
        });

    }

    getCountries() {
        let request = {
            Action: "Countries", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {
            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({ nationList: Response.ClientProfile });
            }
        }).catch(error => {
        });
    }

    getRelationShips() {
        let request = {
            Action: "RelationShip", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {
            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({ relationShipList: Response.ClientProfile });
            }
        }).catch(error => {
        });
    }

    updateMainState(subStateName, editedState) {
        const jsonState = this.state;
        jsonState[subStateName] = editedState;
        this.setState(jsonState);
    }

    updateAttachmentData(attachmentState) {
        this.setState({ attachmentState: attachmentState });
    }

    resetState(clientId, deviceId, apiToken, systemGroupPermission) {
        // Reset state to initial values
        this.setState(INITIAL_STATE());

            // Get ZipCode master data

            if (systemGroupPermission) {
                this.setState({ systemGroupPermission: systemGroupPermission });
            }
            const zipCodeRequest = {
                jsonDataInput: {
                    Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
                }
            };
            getZipCodeMasterData(zipCodeRequest)
                .then(Res => {
                    const Response = Res.GetMasterDataByTypeResult;
                    if (Response.Result === 'true' && Response.ClientProfile !== null) {
                        this.setState({ zipCodeList: Response.ClientProfile });
                    }
                })
                .catch(error => {
                    this.props.history.push('/maintainence');
                });

            // Get bank master data
            const bankRequest = {
                jsonDataInput: {
                    Project: 'mcp', Action: 'Bank',
                }
            };
            getBankMasterData(bankRequest)
                .then(Res => {
                    const Response = Res.GetMasterDataByTypeResult;
                    if (Response.Result === 'true' && Response.ClientProfile !== null) {
                        this.setState({ bankList: Response.ClientProfile });
                    }
                })
                .catch(error => {
                    this.props.history.push('/maintainence');
                });

        // Get other required data
        this.getPolList(clientId, deviceId, apiToken);
        this.getClaimList();
        this.getHospitalList();
        this.getCountries();
        this.getRelationShips();
    }

    clearMasterState(state) {
        state.zipCodeList = null;
        state.bankList = null;
        state.nationList = [];
        state.hospitalList = [];
        state.hospitalResultList = [];
        state.relationShipList = [];
        return state;
    }

    clearPopupState(state) {
        state.disableEdit = false;
        state.showConfirmAuthorization = false;
        state.consentDisabled = false;
        state.showSuccess = false;
        state.sendPOSuccess = false;
        state.popupConfirmClaimCalled = false;
        state.callBackCancel = '/';
        state.callBackSuccess = '/';
        state.agentConfirmed = false;
        state.showConfirm = false;
        state.initClaim = false;
        state.showThank = false;
        state.consultingViewRequest = false;
        state.showStatus = true;//Default show trạng thái ở màn hình PO review
        return state;
    }

    clearPopupStateOnly(state) {
        state.disableEdit = false;
        state.showConfirmAuthorization = false;
        state.consentDisabled = false;
        state.showSuccess = false;
        state.sendPOSuccess = false;
        state.popupConfirmClaimCalled = false;
        state.agentConfirmed = false;
        state.showConfirm = false;
        state.initClaim = false;
        state.showThank = false;
        state.consultingViewRequest = false;
        state.showStatus = true;//Default show trạng thái ở màn hình PO review
        return state;
    }

    loadMaster(clientId, deviceId, apiToken, systemGroupPermission) {
        // Reset state to initial values
        // this.setState(INITIAL_STATE());

        // Check if phone and email are verified
        // const isPhoneVerified = getSession(VERIFY_CELL_PHONE) === '1';
        // const isEmailVerified = getSession(VERIFY_EMAIL) === '1';
        // const hasCellPhone = getSession(CELL_PHONE);
        // const hasEmail = getSession(EMAIL);

        // If both phone and email are verified and available

        // Get ZipCode master data

        if (systemGroupPermission) {
            this.setState({ systemGroupPermission: systemGroupPermission });
        }
        const zipCodeRequest = {
            jsonDataInput: {
                Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
            }
        };
        getZipCodeMasterData(zipCodeRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ zipCodeList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });

        // Get bank master data
        const bankRequest = {
            jsonDataInput: {
                Project: 'mcp', Action: 'Bank',
            }
        };
        getBankMasterData(bankRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ bankList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });

        // Get other required data
        this.getPolList(clientId, deviceId, apiToken);
        this.getClaimList();
        this.getHospitalList();
        this.getCountries();
        this.getRelationShips();
    }

    reLoadMaster() {
        // Get ZipCode master data

        const zipCodeRequest = {
            jsonDataInput: {
                Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
            }
        };
        getZipCodeMasterData(zipCodeRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ zipCodeList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });

        // Get bank master data
        const bankRequest = {
            jsonDataInput: {
                Project: 'mcp', Action: 'Bank',
            }
        };
        getBankMasterData(bankRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ bankList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });

        // Get other required data
        // this.getPolList(clientId, deviceId, apiToken);
        this.getClaimList();
        this.getHospitalList();
        this.getCountries();
        this.getRelationShips();
    }

    loadState(clientId, deviceId, apiToken, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail) {
        // Check if phone and email are verified
        // const isPhoneVerified = getSession(VERIFY_CELL_PHONE) === '1';
        // const isEmailVerified = getSession(VERIFY_EMAIL) === '1';
        // const hasCellPhone = getSession(CELL_PHONE);
        // const hasEmail = getSession(EMAIL);
        // If both phone and email are verified and available
        // if (isPhoneVerified && isEmailVerified && hasCellPhone && hasEmail) {
        //     // Get life insured list for claim
        //     const apiRequest = {
        //         jsonDataInput: {
        //             Company: COMPANY_KEY,
        //             Authentication: AUTHENTICATION,
        //             DeviceId: deviceId,
        //             APIToken: apiToken,
        //             Project: 'mcp',
        //             Action: 'LifeInsuredList',
        //             ClientID: clientId,
        //             UserLogin: clientId
        //         }
        //     };
        //     getLIListForClaim(apiRequest)
        //         .then(Res => {
        //             const Response = Res.Response;
        //             if (Response.Result === 'true' && Response.ClientProfile !== null) {
        //                 this.setState({responseLIList: Response.ClientProfile});
        //                     // if (this.props.match.params.info) {
        //                     //     let infoArr = this.props.match.params.info.split('-');
        //                     //     if (infoArr.length === 3 ) {
        //                     //         const liItem = getSession(LIITEM + infoArr[0]);
        //                     //         if (liItem) {
        //                     //             this.clickOnLICardNoti(infoArr[1], JSON.parse(liItem), infoArr[2]);
        //                     //         }
        //                     //     }
        //                     // } else if (this.props.match.params.id && this.props.match.params.index) {
        //                     //     const liItem = getSession(LIITEM + this.props.match.params.id);
        //                     //     if (liItem) {
        //                     //         this.clickOnLICard(this.props.match.params.index, JSON.parse(liItem));
        //                     //     }
        //                     // }
        //             } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        //                 // showMessage(EXPIRED_MESSAGE);
        //                 // logoutSession();
        //                 // this.props.history.push({
        //                 //     pathname: '/home', state: {authenticated: false, hideMain: false}
        //                 // });
        //             }
        //         })
        //         .catch(error => {
        //             // this.props.history.push('/maintainence');
        //         });

        //     // Get ZipCode master data
        //     const zipCodeRequest = {
        //         jsonDataInput: {
        //             Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
        //         }
        //     };
        //     getZipCodeMasterData(zipCodeRequest)
        //         .then(Res => {
        //             const Response = Res.GetMasterDataByTypeResult;
        //             if (Response.Result === 'true' && Response.ClientProfile !== null) {
        //                 this.setState({zipCodeList: Response.ClientProfile});
        //             }
        //         })
        //         .catch(error => {
        //             console.log(error);
        //             // this.props.history.push('/maintainence');
        //         });

        //     // Get bank master data
        //     const bankRequest = {
        //         jsonDataInput: {
        //             Project: 'mcp', Action: 'Bank',
        //         }
        //     };
        //     getBankMasterData(bankRequest)
        //         .then(Res => {
        //             const Response = Res.GetMasterDataByTypeResult;
        //             if (Response.Result === 'true' && Response.ClientProfile !== null) {
        //                 this.setState({bankList: Response.ClientProfile});
        //             }
        //         })
        //         .catch(error => {
        //             console.log(error);
        //             // this.props.history.push('/maintainence');
        //         });
        // }

        // Get ZipCode master data

        const zipCodeRequest = {
            jsonDataInput: {
                Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
            }
        };
        getZipCodeMasterData(zipCodeRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ zipCodeList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });

        // Get bank master data
        const bankRequest = {
            jsonDataInput: {
                Project: 'mcp', Action: 'Bank',
            }
        };
        getBankMasterData(bankRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ bankList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });
        // Get other required data
        this.getPolList(clientId, deviceId, apiToken);
        this.getClaimList();
        this.getHospitalList();
        this.getCountries();
        this.getRelationShips();
    }

    clickOnLICard(index, item) {
        let state = this.state;
        if (state.selectedCliID && (state.selectedCliID !== item.clientId) && (state.currentState > this.getStep(CLAIM_STEPCODE.INIT_CLAIM))) {
            state.changeSelectLI = true;
            state.changeSelectLIIndex = index;
            state.changeSelectLIItem = item;
            state.selectedCliID = item.clientId;
            this.setState(state);
            return;
        }
        this.requestClaimGetTempInfo(item.clientId, index, item);
        // if (this.state.systemGroupPermission?.[0].Role === SDK_ROLE_PO) {
        //     this.requestClaimGetTempInfo(item.clientId, index, item);
        // } else {
        //     //Reset state
        //     state.initClaimState.occupation = '';
        //     state.initClaimState.disabledButton = true;
        //     state.claimTypeState = INITIAL_STATE().claimTypeState;
        //     state.claimDetailState = INITIAL_STATE().claimDetailState;
        //     state.paymentMethodState = INITIAL_STATE().paymentMethodState;
        //     state.attachmentState = INITIAL_STATE().attachmentState;
        //     state.submissionState = INITIAL_STATE().submissionState;
        //     state.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        //     state.selectedCliID = item.clientId;
        //     state.selectedCliInfo = item;
        //     state.liInfo = item;
        //     state.selectedCliIndex = index;
        //     state.claimCheckedMap = {};
        //     state.openPopupWarningDecree13 = false;
        //     state.trackingId = '';
        //     state.claimId = '';
        //     state.poConfirmingND13 = '0';
        //     state.ND13ClientProfile = null;
        //     if (item.fullName) {
        //         const arrFulName = item.fullName?.trim().split(" ");
        //         if (arrFulName) {
        //             state.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        //         }
        //     }
        //     state.claimTypeState.isSamePoLi = ((getSession(CLIENT_ID) ? getSession(CLIENT_ID).trim() : this.state.clientId?.trim()) === (state.selectedCliID ? state.selectedCliID.trim() : ''));
        //     state.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        //     // let clientProfile = null;
        //     // if (state !== null) {
        //     //     clientProfile = state.responseLIList;
        //     // }
        //     this.setState(state);
        // }
        
        
        // getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.clientId).then(Res => {
        //     if (Res && Res.v) {
        //         let data = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
        //         //Check claim status
        //         if (data.claimId) {
        //             const submitRequest = {
        //                 jsonDataInput: {
        //                     Action: 'CheckClaimStatus',
        //                     APIToken: this.state.apiToken,
        //                     Authentication: AUTHENTICATION,
        //                     ClaimID: data.claimId,
        //                     Company: COMPANY_KEY,
        //                     DeviceId: this.state.deviceId ? this.state.deviceId : getDeviceId(),
        //                     OS: OS,
        //                     Project: 'mcp',
        //                     UserLogin: this.state.clientId
        //                 }
        //             }
        //             CPSubmitForm(submitRequest).then(Res => {
        //                 let Response = Res.Response;
        //                 if ((Response.Result === 'true') && (Response.ErrLog === 'CANCEL')) {
        //                     removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.clientId);//Xóa claim tạm khi backend đã mark Hủy
        //                     this.resetState(this.state.clientId, getDeviceId(), this.state.apiToken);
        //                     return;
        //                 } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        //                     this.props.history.push('/');
        //                 } else if (Response.Result === 'true') {
        //                     dataResponse.changeSelectLIIndex = index;
        //                     dataResponse.changeSelectLIItem = item;
        //                     dataResponse.selectedCliID = item.clientId;
        //                     dataResponse.haveCreatingData = true;
        //                     this.setState(dataResponse);
        //                     return;
        //                 }
        //             }).catch(error => {
        //             });
        //         } else {
        //             dataResponse.changeSelectLIIndex = index;
        //             dataResponse.changeSelectLIItem = item;
        //             dataResponse.selectedCliID = item.clientId;
        //             dataResponse.haveCreatingData = true;
        //             this.setState(dataResponse);
        //             return;
        //         }

        //     }
        // }).catch(error => {
        //     console.log(error);
        // });

        // Reset state
        // dataResponse.initClaimState.occupation = '';
        // dataResponse.initClaimState.disabledButton = true;
        // dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        // dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        // dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        // dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        // dataResponse.submissionState = INITIAL_STATE().submissionState;
        // dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        // dataResponse.selectedCliID = item.clientId;
        // dataResponse.selectedCliInfo = item;
        // dataResponse.liInfo = item;
        // dataResponse.selectedCliIndex = index;
        // dataResponse.claimCheckedMap = {};
        // dataResponse.openPopupWarningDecree13 = false;
        // dataResponse.trackingId = '';
        // dataResponse.claimId = '';
        // dataResponse.poConfirmingND13 = '0';
        // dataResponse.ND13ClientProfile = null;
        // if (item.fullName) {
        //     const arrFulName = item.fullName?.trim().split(" ");
        //     if (arrFulName) {
        //         dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        //     }
        // }
        // dataResponse.claimTypeState.isSamePoLi = ((getSession(CLIENT_ID) ? getSession(CLIENT_ID).trim() : this.state.clientId?.trim()) === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        // dataResponse.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        // let clientProfile = null;
        // if (dataResponse !== null) {
        //     clientProfile = dataResponse.responseLIList;
        // }
        // this.setState(dataResponse);
        // this.goNextStep();
    }
    clickOnLICardNoti(index, item, claimID) {
        let dataResponse = this.state;
        // if (dataResponse.selectedCliID && (dataResponse.selectedCliID !== item.ClientID) && (dataResponse.currentState > CLAIM_STATE.INIT_CLAIM) && (dataResponse.claimId === claimID)) {
        //     dataResponse.changeSelectLI = true;
        //     dataResponse.changeSelectLIIndex = index;
        //     dataResponse.changeSelectLIItem = item;
        //     this.setState(dataResponse);
        //     return;
        // }
        // getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.clientId).then(Res => {
        //     if (Res && Res.v) {
        //         let data = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
        //         if (data.claimId === claimID) {
        //             dataResponse.changeSelectLIIndex = index;
        //             dataResponse.changeSelectLIItem = item;
        //             dataResponse.selectedCliID = item.clientId;
        //             this.setState(dataResponse);
        //             this.clickOnLICardGo(dataResponse.changeSelectLIIndex, dataResponse.changeSelectLIItem);
        //             return;
        //         }
        //     }
        // }).catch(error => {
        // });
        // Reset state
        dataResponse.tempJson = '';
        dataResponse.tempRequestId = '';
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.clientId;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '0';
        dataResponse.ND13ClientProfile = null;
        if (item.fullName) {
            const arrFulName = item.fullName.trim().split(" ");
            dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        }
        dataResponse.claimTypeState.isSamePoLi = ((getSession(USER_LOGIN) ? getSession(USER_LOGIN).trim() : this.state.clientId?.trim()) === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        // dataResponse.jsonInput.jsonDataInput.ClientID = dataResponse.selectedCliID;
        dataResponse.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        let clientProfile = null;
        if (dataResponse !== null) {
            clientProfile = dataResponse.responseLIList;
        }
        this.setState(dataResponse);


    }
    clickOnLICardChange(index, item) {
        let dataResponse = this.state;
        // getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.clientId).then(Res => {

        //     if (Res.v) {
        //         // dataResponse = JSON.parse(getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID));
        //         dataResponse.changeSelectLIIndex = index;
        //         dataResponse.changeSelectLIItem = item;
        //         dataResponse.haveCreatingData = true;
        //         this.setState(dataResponse);
        //         return;
        //     }
        // }).catch(error => {

        // });
        // Reset state
        dataResponse.tempJson = '';
        dataResponse.tempRequestId = '';
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.clientId;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        const arrFulName = item.fullName.trim().split(" ");
        dataResponse.claimTypeState.isSamePoLi = ((getSession(USER_LOGIN) ? getSession(USER_LOGIN).trim() : this.state.clientId?.trim()) === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        dataResponse.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '0';
        dataResponse.ND13ClientProfile = null;

        this.setState(dataResponse);
    }

    clickOnLICardGo(index, item) {
        let dataResponse = this.state;
        dataResponse.changeSelectLI = false;
        try {
            this.setState(dataResponse);
        } catch (e) {
            // alert(e);
        }

        // console.log('dataResponse?.tempJson',dataResponse?.tempJson);
        if (dataResponse?.tempJson) {
            
            let tempJson = decompressJson(dataResponse?.tempJson);
            tempJson.changeSelectLI = false;
            const zipCodeList = dataResponse.zipCodeList;
            const bankList = dataResponse.bankList;
            const nationList = dataResponse.nationList;
            const hospitalList = dataResponse.hospitalList;
            const hospitalResultList = dataResponse.hospitalResultList;
            const relationShipList = dataResponse.relationShipList;
            tempJson.zipCodeList = zipCodeList;
            tempJson.bankList = bankList;
            tempJson.nationList = nationList;
            tempJson.hospitalList = hospitalList;
            tempJson.hospitalResultList = hospitalResultList;
            tempJson.relationShipList = relationShipList;
            tempJson.tempJson = '';

            tempJson.apiToken = dataResponse.apiToken;              
            if(tempJson.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO && (dataResponse.status === SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN || dataResponse.status === SDK_REQUEST_STATUS.REJECT_DLCN)) {
                tempJson.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                tempJson.currentState = this.getStepbyGroup('SDK_DLCN', tempJson.systemGroupPermission);
            }

            this.setState(tempJson);
        }
    }

    clickOnLICardNew(index, item) {
        let dataResponse = this.state;
        if (dataResponse.tempJson && dataResponse?.tempRequestId) {
            this.deleteClaimRequest(dataResponse?.tempRequestId);
        }
        // Reset state
        dataResponse.tempJson = '';
        dataResponse.tempRequestId = '';
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.clientId;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        const arrFulName = item.fullName.trim().split(" ");
        dataResponse.claimTypeState.isSamePoLi = ((getSession(USER_LOGIN) ? getSession(USER_LOGIN).trim() : this.state.clientId?.trim()) === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        dataResponse.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.requestId = '';
        dataResponse.poConfirmingND13 = '0';
        dataResponse.ND13ClientProfile = null;
        this.setState(dataResponse);

    }

    onLoadPolList(polList) {
        let initClaimState = Object.assign({}, this.state.initClaimState);
        initClaimState.selectedLIPolList = polList;
        this.setState({ initClaimState: initClaimState });
    }

    onChangeInputOccupation(event) {
        const jsonState = this.state;
        const inputValue = event.target.value;
        if (inputValue !== null && inputValue !== undefined && inputValue.length >= 2) {
            jsonState.initClaimState.disabledButton = false;
        } else {
            jsonState.initClaimState.disabledButton = true;
        }
        jsonState.initClaimState.occupation = inputValue;
        this.setState(jsonState);
    }

    onCancelClaim() {
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID);
        setTimeout(() => {
            window.location.href = '/';
        }, 100);
    }

    backToPrevStep(currentStep) {
        const jsonState = this.state;
        // if (currentStep > CLAIM_STATE.DONE) {
        //     return;
        // }
        if (currentStep > this.getStep(CLAIM_STEPCODE.INIT_CLAIM)) {
            if (currentStep === this.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD)) {
                if (jsonState.paymentMethodState.paymentMethodStep >= PAYMENT_METHOD_STEP.INIT) {
                    if (jsonState.paymentMethodState.choseReceiver) {
                        // jsonState.paymentMethodState.choseReceiver = null;
                        jsonState.paymentMethodState.currentChoseReceiver = null;
                    }
                    if (jsonState.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.INIT) {
                        jsonState.paymentMethodState.paymentMethodStep = -1;
                        jsonState.currentState = currentStep - 1;
                    } else {
                        jsonState.paymentMethodState.paymentMethodStep = jsonState.paymentMethodState.paymentMethodStep - 1;
                    }

                    jsonState.paymentMethodState.disabledButton = false;
                } else {
                    jsonState.currentState = currentStep - 1;
                }
            } else {
                jsonState.currentState = currentStep - 1;
            }
            if (currentStep <= this.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD)) {
                jsonState.contactState.contactPersonInfo.role = '';
            }
            this.setState(jsonState);
        }
    }

    goToStep(step, pinStep) {
        const jsonState = this.state;
        jsonState.currentState = step;

        // if (step <= CLAIM_STATE.PAYMENT_METHOD) {
        if (step <= this.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD)) {
            jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.INIT;
            // jsonState.paymentMethodState.choseReceiver = null;
            jsonState.paymentMethodState.currentChoseReceiver = null;
            jsonState.contactState.contactPersonInfo.role = '';
        }
        if (pinStep || (pinStep > 0)) {
            jsonState.pinStep = pinStep;
        }
        this.setState(jsonState);
    }

    startClaimProcess() {
        // Move to "Thông tin sự kiện - 1"
        cpSaveLog(`Web_Open_${PageScreen.CLAIM_CHOICE_BENEFIT}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Close_${PageScreen.CLAIM_CHOICE_BENEFIT}`, `Web_Close_${PageScreen.CLAIM_CHOICE_BENEFIT}`,);
        // const jsonState = this.state;
        // jsonState.currentState = CLAIM_STATE.CLAIM_TYPE;
        // this.setState(jsonState);
        this.goNextStep();
        setTimeout(() => {
            this.updateClaimRequest(SDK_REQUEST_STATUS.INITIAL, this.state.systemGroupPermission?.[0]?.Role, "");//Truyền status empty sẽ keep current status
        }, 100);


    }

    onChangeClaimType(event, name) {
        const jsonState = this.state;
        const claimTypeState = jsonState.claimTypeState;
        const claimCheckedMap = jsonState.claimCheckedMap;
        const target = event.target;
        if (target.checked) {
            claimCheckedMap[target.id] = name;
        } else {
            claimCheckedMap[target.id] = '';
        }
        if (isCheckedOnlyAccident(claimCheckedMap)) {
            claimTypeState.isAccidentClaim = true;
        } else if (isCheckedOnlyIllness(claimCheckedMap)) {
            claimTypeState.isAccidentClaim = false;
        }
        if (noCheckedAny(claimCheckedMap)) {
            claimTypeState.isAccidentClaim = null;
        }
        let isDisabledButton = true;
        if (checkMapHaveTrue(claimCheckedMap) && (jsonState.claimTypeState.isAccidentClaim !== null)) {
            isDisabledButton = false;
        } else {
            isDisabledButton = true;
        }

        jsonState.claimTypeState.disabledButton = isDisabledButton;
        this.setState(jsonState);
    }

    answerYes() {
        let jsonState = this.state;
        const claimCheckedMap = jsonState.claimCheckedMap;
        // jsonState.isAccidentClaim = true;
        if (checkMapHaveTrue(claimCheckedMap)) {
            jsonState.claimTypeState.disabledButton = false;
        } else {
            jsonState.claimTypeState.disabledButton = true;
        }
        jsonState.claimTypeState.isAccidentClaim = true;
        this.setState(jsonState);
    }

    answerNo() {
        let jsonState = this.state;
        const claimCheckedMap = jsonState.claimCheckedMap;
        if (checkMapHaveTrue(claimCheckedMap)) {
            jsonState.claimTypeState.disabledButton = false;
        } else {
            jsonState.claimTypeState.disabledButton = true;
        }
        jsonState.claimTypeState.isAccidentClaim = false;
        this.setState(jsonState);
    }

    inVietNam() {
        this.setState({ isVietnam: true });
    }

    notInVietNam() {
        this.setState({ isVietnam: false });
    }

    SelectedHospital(index, value) {
        let jsonState = this.state;
        jsonState.claimDetailState.facilityList[index].selectedHospital = value;
        this.setState(jsonState);
    }

    SelectedHospitalChosen(index, value) {
        let jsonState = this.state;
        let cityObj = this.state.hospitalList.find((city) => city.HospitalName === value);
        if (cityObj) {
            jsonState.claimDetailState.facilityList[index].selectedHospital = value;
            jsonState.claimDetailState.facilityList[index].selectedHospitalChosen = value;
            jsonState.claimDetailState.facilityList[index].cityName = cityObj.StateName;
            jsonState.claimDetailState.facilityList[index].districtName = cityObj.DistrictName;
            jsonState.claimDetailState.facilityList[index].otherHospital = '';
        } else {
            jsonState.claimDetailState.facilityList[index].selectedHospital = jsonState.claimDetailState.facilityList[index].selectedHospitalChosen;
        }
        this.setState(jsonState);
    }

    SelectedSickInfoPlaceChosen(value) {
        let jsonState = this.state;
        let cityObj = this.state.hospitalList.find((city) => city.HospitalName === value);
        if (cityObj) {
            jsonState.claimDetailState.sickInfo.sickFoundFacility = value;
            jsonState.claimDetailState.sickInfo.sickFoundFacilityChosen = value;
            jsonState.claimDetailState.sickInfo.cityName = cityObj.StateName;
            jsonState.claimDetailState.sickInfo.districtName = cityObj.DistrictName;
            jsonState.claimDetailState.sickInfo.sickFoundFacilityOther = '';
        } else {
            jsonState.claimDetailState.sickInfo.sickFoundFacility = jsonState.claimDetailState.sickInfo.sickFoundFacilityChosen;
        }
        this.setState(jsonState);
    }

    SelectOtherSickInfoPlace(value) {
        let jsonState = this.state;
        jsonState.claimDetailState.sickInfo.sickFoundFacilityOther = value;
        jsonState.claimDetailState.sickInfo.cityName = '';
        this.setState(jsonState);
    }

    isEnableButton(claimDetailState) {
        let errors, sickInfoOthers, accidentInfoOthers, deathInfoOthers;
        // Thông tin bệnh
        ({ errors, ...sickInfoOthers } = claimDetailState.sickInfo);
        if (!this.state.claimTypeState.isAccidentClaim && Object.entries(sickInfoOthers)
            .filter(([_, v]) => (v && (((typeof v === "string") && (v.trim().length > 0)) || (v instanceof Array && v.length > 0) || (v instanceof moment)))).length > 0) {
            return true;
        }
        // Thông tin khám/điều trị tại Cơ sở y tế
        for (const facility of claimDetailState.facilityList) {
            let { errors, endDate, ...commonFacilityInfo } = facility;
            if (claimDetailState.isTreatmentAt === true) {
                if (facility.treatmentType || ((typeof facility.selectedHospital === "string") && facility.selectedHospital.trim().length > 0) || (typeof facility.diagnosis === "string" && facility.diagnosis.trim().length > 0) || facility.diagnosis.length > 0) {
                    return true;
                }
                if ((facility.treatmentType === "IN") && endDate) {
                    return true;
                }
                if (facility.invoiceList) {
                    for (const invoice of facility.invoiceList) {
                        if ((typeof invoice.invoiceNumber === "string") && (invoice.invoiceNumber.trim().length > 0) || invoice.invoiceAmount) {
                            return true;
                        }
                    }
                }
                // Công ty bảo hiểm khác
                if (facility.isOtherCompanyRelated === 'yes' && (((facility.otherCompanyInfo.companyName !== null) && (facility.otherCompanyInfo.companyName !== undefined) && (facility.otherCompanyInfo.companyName.trim().length > 0)) || (facility.otherCompanyInfo.paidAmount))) {
                    return true;
                }
                if (facility.isOtherCompanyRelated === 'no') {
                    return true;
                }
            }

        }
        // Tai nạn
        ({ errors, ...accidentInfoOthers } = claimDetailState.accidentInfo);
        if (this.state.claimTypeState.isAccidentClaim) {
            if (Object.entries(accidentInfoOthers)
                .filter(([k, v]) => ((v && (((typeof v === "string") && (v.trim().length > 0)) || ((typeof v !== "string") && v.length > 0) || (v instanceof Array && v.length > 0) || (v instanceof moment))))).length > 0) {
                return true;
            }
        }
        // Tử vong
        if (this.state.claimCheckedMap[CLAIM_TYPE.DEATH]) {
            ({ errors, ...deathInfoOthers } = claimDetailState.deathInfo);
            if (Object.entries(deathInfoOthers)
                .filter(([k, v]) => (v && (((typeof v === "string") && (v.trim().length > 0)) || (v instanceof Array && v.length > 0) || (v instanceof moment)))).length > 0) {
                return true;
            }
        }
        return false;
    }

    SelectOtherHospital(index, value) {
        let jsonState = this.state;
        jsonState.claimDetailState.facilityList[index].otherHospital = value;
        jsonState.claimDetailState.facilityList[index].cityName = '';
        jsonState.claimDetailState.facilityList[index].cityCode = '';
        this.setState(jsonState);
    }

    SelectOtherDiagnostic(index, value) {
        let jsonState = this.state;

        let resultList = jsonState.hospitalResultList;
        let item = {
            "ICDCode": "", "ICDName": value, "ICDNameSearch": value
        }
        resultList.push(item);
        jsonState.claimDetailState.facilityList[index].otherDiagnostic = value;
        if (typeof jsonState.claimDetailState.facilityList[index].diagnosis === 'object') {
            jsonState.claimDetailState.facilityList[index].diagnosis.push(value);
        }
        jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
        this.setState(jsonState);
    }

    SelectedDiagnosticResult(index, value) {
        let jsonState = this.state;
        if (value.length === 0) {
            jsonState.claimDetailState.facilityList[index].diagnosis = '';
        } else {
            jsonState.claimDetailState.facilityList[index].diagnosis = value;
        }
        jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
        this.setState(jsonState);
    }

    enterOtherCompanyName(index, value) {
        let jsonState = this.state;
        jsonState.claimDetailState.facilityList[index].otherCompanyInfo.companyName = value;
        jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
        this.setState(jsonState);
    }

    enterOtherCompanyPaid(index, value) {
        // Create a deep copy of the state
        const jsonState = { ...this.state };

        // Create a deep copy of the facilityList array and otherCompanyInfo object
        const updatedFacilityList = jsonState.claimDetailState.facilityList.map((facility, i) => {
            if (i === index) {
                // Create a deep copy of the otherCompanyInfo object
                const updatedOtherCompanyInfo = { ...facility.otherCompanyInfo, paidAmount: value };

                // Create a deep copy of the facility object with the updated otherCompanyInfo
                return { ...facility, otherCompanyInfo: updatedOtherCompanyInfo };
            }
            return facility;
        });

        // Update the state with the new facilityList and disabledButton value
        jsonState.claimDetailState.facilityList = updatedFacilityList;
        jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);

        // Set the state with the updated values
        this.setState(jsonState);
    }

    isOtherCompanyRelated(index, value) {
        let jsonState = this.state;
        jsonState.claimDetailState.facilityList[index].isOtherCompanyRelated = value;
        if (value === "no") {
            jsonState.claimDetailState.facilityList[index].otherCompanyInfo.companyName = '';
            jsonState.claimDetailState.facilityList[index].otherCompanyInfo.paidAmount = '';
        }
        jsonState.claimDetailState.disabledButton = !this.isEnableButton(jsonState.claimDetailState);
        this.setState(jsonState);
    }

    generateConsentResults(data) {
        const result = {};
        data.forEach((item, index) => {
            const role = item.Role;
            let key;
            if (role === 'PO') {
                key = 'ConsentResultPO';
            } else {
                key = `ConsentResultLI`;
            }
            result[key] = item.ConsentResult;
        });

        return result;
    }

    removeDuplicateCustomers(data) {
        return data.filter((item, index, self) => index === self.findIndex((t) => (t.CustomerID === item.CustomerID)));
    }

    checkPOAndLIEquality(PO, ClientID) {
        return ClientID === PO;
    }

    generateSessionString(CLIENT_ID, selectedCliId) {
        if (this.checkPOAndLIEquality(CLIENT_ID, selectedCliId)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${selectedCliId}`;
        }
    }

    fetchCPConsentConfirmationChecking(TrackingID) {
        const request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId || getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(this.state.clientId || getSession(CLIENT_ID), this.state.selectedCliInfo?.clientId),
                ProcessType: "Claim",
                DeviceId: this.state.deviceId ? this.state.deviceId : getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID || "",
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const consentResultPO = this.generateConsentResults(Response.ClientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(Response.ClientProfile)?.ConsentResultLI;
                    const isOpenPopupWarning = ((Response.ClientProfile.length === 1 && consentResultPO !== ConsentStatus.AGREED) || (Response.ClientProfile.length > 1 && (consentResultPO !== ConsentStatus.AGREED || consentResulLI !== ConsentStatus.AGREED)));
                    this.setState({
                        openPopupWarningDecree13: isOpenPopupWarning,
                    });
                    this.setState({ poConfirmingND13: (isOpenPopupWarning ? '1' : '0') });
                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.setState({
                        openPopupWarningDecree13: false,
                    });
                    // setSession(PO_CONFIRMING_DECREE_13, '1');
                    this.setState({ poConfirmingND13: '0' });
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    fetchCPConsentConfirmationFetch(data, TrackingID, onlyPO) {
        const jsonState = this.state;
        const setComponentState = this.setState.bind(this);
        const request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId || getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(this.state.clientId || getSession(CLIENT_ID), this.state.selectedCliInfo?.clientId),
                ProcessType: "Claim",
                DeviceId: this.state.deviceId ? this.state.deviceId : getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID || "",
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    console.log('Fetch Ok......');
                    if (!isEmpty(data)) {
                        if (onlyPO) {
                            const consentResultPO = this.generateConsentResults(Response.ClientProfile)?.ConsentResultPO;
                            if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED) {
                                this.submitCPConsentConfirmationPOConfirmOnly(data);
                            } else if (this.haveLIStillNotAgree(Response.ClientProfile)) {
                                this.submitCPConsentConfirmationLIConfirm(data, onlyPO);
                            } else {
                                jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
                                jsonState.trackingId = TrackingID;
                                setComponentState(jsonState);
                                // alertSucceeded();
                                this.confirmCPConsent();
                                //Kiem tra co can deleteND13DataTemp cho nay
                                // this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
                                this.setState(jsonState);
                            }
                        } else {
                            this.submitCPConsentConfirmationLIConfirm(data, onlyPO);
                        }

                    }
                }
            })
            .catch(error => {
                console.log(error);
            });
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

    fetchCPConsentConfirmation(TrackingID) {
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(getSession(CLIENT_ID), this.state.selectedCliInfo?.clientId),
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ? TrackingID : this.state.trackingId,
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                console.log('Response ND13=', Response);
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const { DOB } = this.state.selectedCliInfo;
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
                    if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED) {
                        const claimSubmissionState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile,
                        });
                        this.callBackUpdateND13State(claimSubmissionState);
                        this.callBackUpdateND13ClientProfile(clientProfile);
                    } else if (consentResulLI === ConsentStatus.WAIT_CONFIRM || consentResulLI === ConsentStatus.EXPIRED || consentResulLI === ConsentStatus.DECLINED) {
                        const claimSubmissionState = CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18;

                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile,
                        });
                        this.callBackUpdateND13ClientProfile(clientProfile);
                        this.callBackUpdateND13State(claimSubmissionState);
                    } else if (!this.state.isPayment) {
                        this.setState({ isPayment: true });
                        const claimSubmissionState = CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18;

                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile
                        });
                        this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
                        this.callBackUpdateND13State(claimSubmissionState);
                        this.callBackUpdateND13ClientProfile(clientProfile);
                    } else {
                        this.confirmCPConsent();
                        this.setState({ claimSubmissionState: CLAIM_STATE.INIT });
                        this.alertSucceeded();
                        this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
                        this.onClickConfirmCloseButton();
                    }
                } else {
                    console.log("System error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
    }


    submitClaimType() {

        cpSaveLog(`Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`, `Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`,);
        // Move to "Thông tin sự kiện - 2"
        let jsonState = this.state;
        const hasDeathClaim = this.state.claimCheckedMap[CLAIM_TYPE.DEATH];
        //this.props.claimCheckedMap[CLAIM_TYPE.DEATH]
        if (!hasDeathClaim) {
            console.log("CLAIM_STATE.CLAIM_TYPE has ND13");
            this.fetchCPConsentConfirmationChecking();
        } else {
            console.log("CLAIM_STATE.CLAIM_DETAIL no ND13");
        }

        const claimCheckedMap = jsonState.claimCheckedMap;
        if (claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] || claimCheckedMap[CLAIM_TYPE.HS] || claimCheckedMap[CLAIM_TYPE.ACCIDENT] || claimCheckedMap[CLAIM_TYPE.TPD] || claimCheckedMap[CLAIM_TYPE.ILLNESS]) {
            jsonState.claimDetailState.isTreatmentAt = true;
            jsonState.claimDetailState.isCheckedClaimTypeTreatment = true;
        } else {
            jsonState.claimDetailState.isTreatmentAt = null;
            jsonState.claimDetailState.isCheckedClaimTypeTreatment = false;
        }
        if (jsonState.pinStep || (jsonState.pinStep > 0)) {
            jsonState.currentState = pinStep;
            jsonState.pinStep = 0;
        } else {
            jsonState.currentState = jsonState.currentState + 1;//CLAIM_DETAIL;
        }
        if (!this.state.disableEdit || ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.agentKeyInPOSelfEdit)) {
            let status = (this.state?.status === SDK_REQUEST_STATUS.REEDIT) ? SDK_REQUEST_STATUS.REEDIT : SDK_REQUEST_STATUS.EDIT;
            if (this.state?.status === 'POREVIEW') {
                status = 'POREVIEW';
            }
            jsonState.status = status;
            let role = this.state.lockingBy;
            if (role !== SDK_REVIEW_PROCCESS) {
                role = this.state.systemGroupPermission?.[0]?.Role;
            }
            this.updateClaimRequest(status, role, "");//Truyền status empty sẽ keep current status
        }
        this.setState(jsonState);
    }

    loadClaimTypeList(claimTypeList) {
        this.setState({ claimTypeList: claimTypeList });
    }

    submitClaimDetail() {
        cpSaveLog(`Web_Open_${PageScreen.CLAIM_PAYMENT}`);
        // Move to "Phương thức thanh toán"
        // const jsonState = this.state;
        // jsonState.currentState = jsonState.currentState + 1;//CLAIM_STATE.PAYMENT_METHOD;
        // this.setState(jsonState);
        if (this.state.pinStep || (this.state.pinStep > 0)) {
            this.setState({ currentState: this.state.pinStep, pinStep: 0 });
        } else {
            this.goNextStep();
            // let jsonState = this.state;
            // jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP1;
            // jsonState.currentState = jsonState.currentState + 1;
            // this.setState(jsonState);
        }
        if (!this.state.disableEdit || ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.agentKeyInPOSelfEdit)) {
            let status = (this.state?.status === SDK_REQUEST_STATUS.REEDIT) ? SDK_REQUEST_STATUS.REEDIT : SDK_REQUEST_STATUS.EDIT;
            if (this.state?.status === 'POREVIEW') {
                status = 'POREVIEW';
            }
            this.setState({ status: status });
            let role = this.state.lockingBy;
            if (role !== SDK_REVIEW_PROCCESS) {
                role = this.state.systemGroupPermission?.[0]?.Role;
            }
            this.updateClaimRequest(status, role, "");//Truyền status empty sẽ keep current status
        }
    }

    submitPaymentMethod() {
        if (this.state.isSubmitting) {
            return;
        }
        // Move to "Contact"
        // cpSaveLog(`Web_Open_${PageScreen.CLAIM_CONTACT}`);
        const jsonState = this.state;

        if ((jsonState.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18)) {
            if ((jsonState.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.INIT) && !jsonState.claimTypeState.isSamePoLi) {
                jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP1;
            } else if (jsonState.paymentMethodState.currentChoseReceiver !== jsonState.paymentMethodState.choseReceiver) {
                jsonState.paymentMethodState.currentChoseReceiver = jsonState.paymentMethodState.choseReceiver;
                jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP2;
            } else {
                    if (jsonState.paymentMethodState.choseReceiver === 'PO') {
                        if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P2') && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName) {
                        jsonState.paymentMethodState.healthCareBenState.paymentMethodName = jsonState.paymentMethodState.lifeBenState.paymentMethodName;
                        jsonState.paymentMethodState.healthCareBenState.paymentMethodId = jsonState.paymentMethodState.lifeBenState.paymentMethodId
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName;
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId;
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankOfficeName;

                        //Reset P1
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = '';
                
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = undefined;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = '';
                
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = '';
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = '';
                        } else if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P1') && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName) {
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation;
                
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankOfficeName;
                
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode;
                        jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName;

                        //Reset P2
                        jsonState.paymentMethodState.healthCareBenState.paymentMethodName = '';
                        jsonState.paymentMethodState.healthCareBenState.paymentMethodId = '';
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = undefined;
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = '';
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = '';
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = '';
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = undefined;
                
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = '';
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = '';
                        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = '';

                        }
                    }
                    jsonState.currentState = jsonState.currentState + 1;//CLAIM_DETAIL;
                    jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP2;
                    // jsonState.currentState = jsonState.currentState + 1;//CLAIM_STATE.CONTACT;
                    // jsonState.currentState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
                    if (jsonState.pinStep || (jsonState.pinStep > 0)) {
                        jsonState.currentState = this.state.pinStep;
                        jsonState.pinStep = 0;
                    }

            }
        } else {
            if (jsonState.paymentMethodState.choseReceiver && !jsonState.paymentMethodState.currentChoseReceiver) {
                jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP2;
                jsonState.paymentMethodState.currentChoseReceiver = jsonState.paymentMethodState.choseReceiver;
            } else {
                jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP1;
                jsonState.currentState = jsonState.currentState + 1;//CLAIM_DETAIL;
                // jsonState.currentState = jsonState.currentState + 1;//CLAIM_STATE.CONTACT;
                // jsonState.currentState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
                if (jsonState.pinStep || (jsonState.pinStep > 0)) {
                    jsonState.currentState = this.state.pinStep;
                    jsonState.pinStep = 0;
                }
            }
        }
        
        if (!this.state.disableEdit || ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.agentKeyInPOSelfEdit)) {
            let status = (this.state?.status === SDK_REQUEST_STATUS.REEDIT) ? SDK_REQUEST_STATUS.REEDIT : SDK_REQUEST_STATUS.EDIT;
            if (this.state?.status === 'POREVIEW') {
                status = 'POREVIEW';
            }
            jsonState.status = status;
            let role = this.state.lockingBy;
            if (role !== SDK_REVIEW_PROCCESS) {
                role = this.state.systemGroupPermission?.[0]?.Role;
            }
            this.updateClaimRequest(status, role, "");//Truyền status empty sẽ keep current status
        }
        this.setState(jsonState);
    }

    submitContact() {
        // cpSaveLog(`Web_Open_${PageScreen.CLAIM_ATTACH_DOCUMENT}`);

        // Move to "Kèm chứng từ"
        // const jsonState = this.state;
        // jsonState.currentState = jsonState.currentState + 1;// CLAIM_STATE.ATTACHMENT;
        // this.setState(jsonState);
        if (this.state.pinStep || (this.state.pinStep > 0)) {
            this.setState({ currentState: this.state.pinStep, pinStep: 0 });
        } else {
            this.goNextStep();
        }
        if (!this.state.disableEdit || ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.agentKeyInPOSelfEdit)) {
            let status = (this.state?.status === SDK_REQUEST_STATUS.REEDIT) ? SDK_REQUEST_STATUS.REEDIT : SDK_REQUEST_STATUS.EDIT;
            if (this.state?.status === 'POREVIEW') {
                status = 'POREVIEW';
            }
            this.setState({status: status});
            let role = this.state.lockingBy;
            if (role !== SDK_REVIEW_PROCCESS) {
                role = this.state.systemGroupPermission?.[0]?.Role;
            }
            this.updateClaimRequest(status, role, "");//Truyền status empty sẽ keep current status

        }
    }

    onLoadAttachmentData(attachmentData) {
        const jsonState = this.state;
        jsonState.attachmentState.attachmentData = attachmentData;
        this.setState(jsonState);
    }

    submitAttachment() {
        // Move to "Gửi yêu cầu"
        // const jsonState = this.state;
        // jsonState.currentState = CLAIM_STATE.SUBMIT;
        // this.setState(jsonState);
        // this.setState({currentState: this.state.currentState + 1/*CLAIM_STATE.SUBMIT*/, claimSubmissionState: CLAIM_STATE.INIT});
        // cpSaveLog(`Web_Open_${PageScreen.CLAIM_REVIEW}`);
        if (this.state.pinStep || (this.state.pinStep > 0)) {
            this.setState({ currentState: this.state.pinStep, pinStep: 0 });
        } else {
            this.goNextStep();
        }
        if (!this.state.disableEdit || ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.agentKeyInPOSelfEdit)) {
            let status = (this.state?.status === SDK_REQUEST_STATUS.REEDIT) ? SDK_REQUEST_STATUS.REEDIT : SDK_REQUEST_STATUS.EDIT;
            if (this.state?.status === 'POREVIEW') {
                status = 'POREVIEW';
            }
            this.setState({status: status});
            let role = this.state.lockingBy;
            if (role !== SDK_REVIEW_PROCCESS) {
                role = this.state.systemGroupPermission?.[0]?.Role;
            }
            this.updateClaimRequest(status, role, "");//Truyền status empty sẽ keep current status
        }
    }

    submitClaimSubmission() {
        // Submit Claim
        cpSaveLog(`Web_Open_${PageScreen.CLAIM_SUBMISSION}`);

    }

    clearBelowBenifit() {
        const newState = INITIAL_STATE();
        let state = this.state;
        const claimDetailState = newState.claimDetailState;
        const contactState = newState.contactState;
        const paymentMethodState = newState.paymentMethodState;
        const attachmentState = newState.attachmentState;
        const isVietnam = newState.isVietnam;

        state.claimDetailState = claimDetailState;
        state.contactState = contactState;
        state.paymentMethodState = paymentMethodState;
        state.attachmentState = attachmentState;
        state.isVietnam = isVietnam;
        // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(this.state));
        // this.setState(copyState);

    }

    updateSubContactState(subStateName, editedState) {
        let jsonState = this.state;
        let isInvalid = Object.entries(editedState)
            .filter(([_, v]) => v === null || v === undefined || !v).length > 0;
        jsonState.contactState.disabledButton = isInvalid;
        jsonState.contactState[subStateName] = editedState;
        // this.setState(jsonState);
        this.handlerUpdateMainState("contactState", jsonState.contactState);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.handlerStartClaimProcess();
    }

    saveClaim = () => {
        this.setState({ isSubmitting: true });
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let state = cloneDeep(this.state);
        console.log('saveClaim cloneDeep state=', state);
        state.isSubmitting = false;
        state = this.clearPopupStateOnly(state);
        state = this.clearMasterState(state);
        let jsonState = compressJson(state);
        console.log('json compress=', jsonState.length);

        let data = {
            sourceSystem: this.state.systemGroupPermission?.[0]?.SourceSystem,
            requestId: this.state.requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID),
            customerName: getSession(FULL_NAME),
            agentCode: this.state.agentCode,
            agentName: this.state.agentName,
            agentPhone: this.state.agentPhone,
            lifeInsuredName: this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName : '',
            lifeInsuredId: this.state.selectedCliInfo.clientId ? this.state.selectedCliInfo.clientId : '',
            processType: this.state.processType,
            status: '',//Không cập nhật status
            link: '',
            claimTypeVN: getBenifits(this.state.claimCheckedMap),
            jsonState: jsonState
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ requestId: Res.data?.[0]?.requestId });
                console.log('save successful...');
                this.closeToHome();
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
            this.setState({ isSubmitting: false });
        }).catch(error => {
            this.setState({ isSubmitting: false });
            console.log(error);
        });

    }

    /**
     * Update claim info for steps
     * @param {*} status 
     * @param {*} lockingBy 
     * @param {*} link 
     * @param {*} clearJson 
     * @param {*} showThanks 
     * @returns 
     */
    updateClaimRequest = (status, lockingBy, link, clearJson, showThanks) => {
        if ((status === SDK_REQUEST_STATUS.CREATE_REQUEST) || (status === SDK_REQUEST_STATUS.INITIAL)) {
            return;//Ignore save at these step
        }

        if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && this.state.disableEdit) {
            return;
        }
        this.setState({ isSubmitting: true, status: status });
        if (lockingBy && (lockingBy !== this.state.lockingBy)) {
            this.setState({ lockingBy: lockingBy });
        }
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let jsonState = '';
        if (!clearJson && (status !== SDK_REQUEST_STATUS.CREATE_REQUEST) && (status !== SDK_REQUEST_STATUS.INITIAL)) {
            let state = cloneDeep(this.state);
            state.isSubmitting = false;
            state.status = status;
            if ((status === SDK_REQUEST_STATUS.POREVIEW) && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && !this.state.disableEdit) {
                state = this.clearPopupState(state);//clear status show popup and call back
            } else {
                state = this.clearPopupStateOnly(state);//clear status show popup only
            }
            state = this.clearMasterState(state);
            jsonState = compressJson(state);
            console.log('json compress=', jsonState.length);
        }

        let data = {
            sourceSystem: this.state.systemGroupPermission?.[0]?.SourceSystem,
            requestId: this.state.requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID),
            customerName: getSession(FULL_NAME),
            agentCode: this.state.agentCode,
            agentName: this.state.agentName,
            agentPhone: this.state.agentPhone,
            lifeInsuredName: this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName : '',
            lifeInsuredId: this.state.selectedCliInfo.clientId ? this.state.selectedCliInfo.clientId : '',
            processType: this.state.processType,
            status: status,
            link: link,
            claimTypeVN: getBenifits(this.state.claimCheckedMap),
            lockingBy: lockingBy,
            jsonState: jsonState
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ requestId: Res.data?.[0]?.requestId });
                if ((status === SDK_REQUEST_STATUS.POREVIEW) && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && !this.state.disableEdit) {
                    this.setSendPOSuccess();
                }
                if (showThanks) {
                    this.setState({ showThank: true });
                }
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
            this.setState({ isSubmitting: false });
        }).catch(error => {
            this.setState({ isSubmitting: false });
            console.log(error);
        });

    }

    /**
     * Save claim for steps
     * @param {*} status 
     * @param {*} lockingBy 
     * @param {*} link 
     * @param {*} currentState 
     * @returns 
     */
    updateCloneClaimRequest = (status, lockingBy, link, currentState) => {
        if ((status === SDK_REQUEST_STATUS.CREATE_REQUEST) || (status === SDK_REQUEST_STATUS.INITIAL)) {
            return;//Ignore save at these step
        }
        if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && this.state.disableEdit) {
            return;
        }
        this.setState({ isSubmitting: true });
        let state = cloneDeep(this.state);
        if (currentState) {
            state.currentState = currentState;
        }
        state.isSubmitting = false;
        state.status = status;
        if ((status === SDK_REQUEST_STATUS.POREVIEW) && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && !this.state.disableEdit) {
            state = this.clearPopupState(state);//clear status show popup and call back
        } else {
            state = this.clearPopupStateOnly(state);//clear status show popup only
        }
        state = this.clearMasterState(state);
        state.lockingBy = lockingBy;
        if (lockingBy && (lockingBy !== this.state.lockingBy)) {
            this.setState({ lockingBy: lockingBy });
        }
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let jsonState = '';
        if ((status !== SDK_REQUEST_STATUS.CREATE_REQUEST) && (status !== SDK_REQUEST_STATUS.INITIAL)) {
            jsonState = compressJson(state);
            console.log('json compress=', jsonState.length);
        }
        let data = {
            sourceSystem: this.state.systemGroupPermission?.[0]?.SourceSystem,
            requestId: this.state.requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID),
            customerName: getSession(FULL_NAME),
            agentCode: this.state.agentCode,
            agentName: this.state.agentName,
            agentPhone: this.state.agentPhone,
            lifeInsuredName: this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName : '',
            lifeInsuredId: this.state.selectedCliInfo.clientId ? this.state.selectedCliInfo.clientId : '',
            processType: this.state.processType,
            status: status,
            link: link,
            claimTypeVN: getBenifits(this.state.claimCheckedMap),
            lockingBy: lockingBy,
            jsonState: jsonState
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ requestId: Res.data?.[0]?.requestId });
                if ((status === SDK_REQUEST_STATUS.POREVIEW) && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && !this.state.disableEdit) {
                    this.setSendPOSuccess();
                }
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            this.setState({ isSubmitting: false });
            console.log(error);
        });
        this.setState({ isSubmitting: false });
    }

    deleteClaimRequest = (requestId) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID),
            customerName: getSession(FULL_NAME),
            lifeInsuredName: this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName : '',
            lifeInsuredId: this.state.selectedCliInfo.clientId ? this.state.selectedCliInfo.clientId : '',
            processType: this.state.processType,
            status: 'DELETE',
            jsonState: ''
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
            } 
        }).catch(error => {
            console.log(error);
        });

    }


    markDeleteClaimRequest = (requestId, customerId) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }

        let data = {
            sourceSystem: this.state.systemGroupPermission?.[0]?.SourceSystem,
            requestId: requestId,
            customerId: customerId,
            // customerName: getSession(FULL_NAME),
            // agentCode: this.state.agentCode,
            // agentName: this.state.agentName,
            // agentPhone: this.state.agentPhone,
            // lifeInsuredName: this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName : '',
            // lifeInsuredId: this.state.selectedCliInfo.clientId ? this.state.selectedCliInfo.clientId : '',
            processType: this.state.processType,
            status: 'DELETE',
            jsonState: ''
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('Delete successful!');
                let refreshTrackingClaim = this.state.trackingClaimData?.filter(ite => (ite.requestId !== requestId));
                this.setState({ trackingClaimData: refreshTrackingClaim });

                // this.setState({ requestId: Res.data?.[0]?.requestId });
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
            // this.setState({isSubmitting: false});
        }).catch(error => {
            // this.setState({isSubmitting: false});
            console.log(error);
        });

    }

    requestClaimGetInforNoti = (requestId) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID)
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                if (CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status)) {//Expired or Delete
                    this.setState({ claimExpired: true });
                } else if (Res.data?.[0]?.jsonState) {
                    if (Res.data?.[0]?.status === SDK_REQUEST_STATUS.REEDIT) {
                        this.setState({ waittingAgentEdit: true });
                        return;
                    }
                    console.log('jsonXXX size=', Res.data?.[0]?.jsonState?.length);
                    let state = decompressJson(Res.data?.[0]?.jsonState);
                    state.status = Res.data?.[0]?.status;
                    state.apiToken = getSession(ACCESS_TOKEN);
                    state.signature = this.state.clientId || getSession(CLIENT_ID);
                    setSession(SIGNATURE, state.signature);
                    state.effectiveDate = Res.data?.[0]?.effectiveDate;
                    state.callBackCancel = '/';
                    state.callBackSuccess = '/';
                    state.disableEdit = true;
                    state = this.clearPopupStateOnly(state);
                    // this.setState(state);
                    let json = {
                        sourceSystem: "DConnect",
                        group: "PO",
                        platform: "Web",
                        poClientId: getSession(CLIENT_ID),
                        agentPhone: getSession(CELL_PHONE),
                        deviceId: getDeviceId(),
                        processType: "Claim",
                        apiToken: getSession(ACCESS_TOKEN)
                    }
                    // this.checkAuthorization();
                    // this.getPermissionLoadStateRequestId(json, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state);
                    this.checkAuthorizationLoadNoti(json, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state);
                }

                // this.setState({ showConfirmAuthorization: true });
                // if (Res.data?.[0]?.lockingBy) {
                //     this.setState({lockingBy: Res.data?.[0]?.lockingBy});
                // }
                // this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                // this.goNextStep();
                // getPolList (this.props.clientId, getDeviceId(), token);
            } /*else if (Res.code === CODE_ERROR_AUTHEN) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    requestClaimGetInforByRequestIdAndRole = (requestId, role, d, token) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: requestId
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                if (CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status)) {//Expired or Delete
                    this.setState({ claimExpired: true });
                } else if (Res.data?.[0]?.jsonState) {
                    if ((role === SDK_ROLE_PO) && (Res.data?.[0]?.status === SDK_REQUEST_STATUS.REEDIT)) {
                        this.setState({ waittingAgentEdit: true });
                        return;
                    }
                    let state = decompressJson(Res.data?.[0]?.jsonState);
                    if (Res.data?.[0]?.status === SDK_REQUEST_STATUS.REEDIT) {
                        state.updateInfoState = ND_13.NONE;
                        state.showAskReEdit = false;
                        state.showRejectConfirm = false;
                    }
                    state.status = Res.data?.[0]?.status;
                    console.log('xaaaa=', state);
                    if (token) {
                        state.apiToken = token;
                        state.deviceId = getDeviceId();
                    }
                    state.isSubmitting = false;
                    
                    this.reLoadMaster();
                    if (d) { //Nếu có param data
                        this.getPermissionOnly(d, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state);
                    } else {
                        this.setState(state);
                    }

                }
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    requestClaimGetInfor = (requestId, d, status, token) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: d?.deviceId || getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature || d.poClientId,
            accessToken: token || this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: requestId,
            customerId: d?.poClientId
        }
        if (d?.sourceSystem !== 'DConnect') {
            data = {
                requestId: requestId,
                agentCode: d?.agentCode
            }
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                if (AGENT_CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status) && (d?.sourceSystem !== 'DConnect')) {//Expired or Delete
                    let msg = '';
                    if (Res.data?.[0]?.status === 'CONFIRM') {
                        msg = 'Yêu cầu Giải quyết quyền lợi này đã nộp, vui lòng theo dõi ở mục “Danh sách yêu cầu quyền lợi bảo hiểm”';
                    } else if (["CANCEL", "REJECT_CLAIM", "REJECT_DLCN"].includes(Res.data?.[0]?.status)) {
                        msg = 'Thông tin Yêu cầu Giải quyết quyền lợi này đã bị hủy, do bị từ chối hoặc không được hoàn tất trong 24 giờ.';
                    }
                    this.setState({ claimExpireAgentMsg: msg });
                } else if(CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status) && (d?.sourceSystem === 'DConnect')) {
                    this.setState({ claimExpired: true });
                } else if (Res.data?.[0]?.jsonState) {
                    if ((d?.sourceSystem === 'DConnect') && !isEmpty(requestId) && (Res.data?.[0]?.status === SDK_REQUEST_STATUS.REEDIT)) {
                        this.setState({ waittingAgentEdit: true });
                        return;
                    }
                    let state = decompressJson(Res.data?.[0]?.jsonState);
                    state.consultingViewRequest = consultingViewRequest;
                    // alert('3=' + state.consultingViewRequest)
                    state.status = Res.data?.[0]?.status;
                    if (Res.data?.[0]?.status === 'REEDIT') {
                        state.updateInfoState = ND_13.NONE;
                        state.showAskReEdit = false;
                        state.showRejectConfirm = false;
                    }
                    console.log('xaaaa=', state);
                    if (token) {
                        state.apiToken = token;
                        state.deviceId = d?.deviceId || getDeviceId();
                        state.effectiveDate = Res.data?.[0]?.effectiveDate;
                    }
                    state = this.clearPopupStateOnly(state);
                    if (d?.sourceSystem !== 'DConnect') {
                        setSession(CLIENT_ID, state.clientId);
                        setSession(FULL_NAME, state.fullName);
                        setSession(POID, state?.idNum);
                        state.signature = d?.agentCode;
                        setSession(SIGNATURE, d?.agentCode);
                    } else {
                        state.signature = d?.poClientId;
                        setSession(SIGNATURE, d?.poClientId);
                    }
                    state.isSubmitting = false;
                    
                    
                    if (d) { //Nếu có param data
                        let data = cloneDeep(d);
                        if (d?.sourceSystem !== 'DConnect') {
                            data.poClientId = data.agentCode;
                        }
                        // alert('4=' + state.consultingViewRequest);
                        // if (consultingViewRequest) {
                        //     state.consultingViewRequest = consultingViewRequest;
                        //     this.setState(state);
                        // }
                        this.getPermissionOnly(data, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state);
                    } else {
                        // alert('5');
                        this.setState(state);
                    }

                }
                // if (Res.data?.[0]?.lockingBy) {
                //     this.setState({lockingBy: Res.data?.[0]?.lockingBy});
                // }
                // this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                // this.goNextStep();
                // getPolList (this.props.clientId, getDeviceId(), token);
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    requestClaimGetInforAgent = (requestId, d, status, token) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: requestId,
            agentCode: this.state.agentCode
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                if (AGENT_CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status)) {//Expired or Delete
                    let msg = '';
                    if (Res.data?.[0]?.status === 'CONFIRM') {
                        msg = 'Yêu cầu Giải quyết quyền lợi này đã nộp, vui lòng theo dõi ở mục “Danh sách yêu cầu quyền lợi bảo hiểm”';
                    } else if (["CANCEL", "REJECT_CLAIM", "REJECT_DLCN"].includes(Res.data?.[0]?.status)) {
                        msg = 'Thông tin Yêu cầu Giải quyết quyền lợi này đã bị hủy, do bị từ chối hoặc không được hoàn tất trong 24 giờ.';
                    }
                    this.setState({ claimExpireAgentMsg: msg });
                } else if (Res.data?.[0]?.jsonState) {
                    let state = decompressJson(Res.data?.[0]?.jsonState);
                    if (Res.data?.[0]?.status === 'REEDIT') {
                        state.updateInfoState = ND_13.NONE;
                        state.showAskReEdit = false;
                        state.showRejectConfirm = false;
                    }
                    state.status = Res.data?.[0]?.status;
                    console.log('requestClaimGetInforAgent xaaaa=', state);
                    state.apiToken = this.state.apiToken;
                    state.isSubmitting = false;
                    state.agentKeyInPOSelfEdit = false;
                    state.consultingViewRequest = true;
                    // this.setState(state);
                    if (d) { //Nếu có param data
                        let data = cloneDeep(d);
                        if (d?.sourceSystem !== 'DConnect') {
                            data.poClientId = state.clientId;
                        }
                        this.getPermissionLoadState(data, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state)
                    }

                }
                // if (Res.data?.[0]?.lockingBy) {
                //     this.setState({lockingBy: Res.data?.[0]?.lockingBy});
                // }
                // this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                // this.goNextStep();
                // getPolList (this.props.clientId, getDeviceId(), token);
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    requestClaimGetInforLink = (d) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            link: d.link,
            customerId: this.state.clientId || getSession(CLIENT_ID)
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                if (CLAIM_EXPIRE_STATUS_MASTER_LIST.includes(Res.data?.[0]?.status)) {//Expired or Delete
                    this.setState({ claimExpired: true });
                } else if (Res.data?.[0]?.jsonState) {
                    if (Res.data?.[0]?.status === SDK_REQUEST_STATUS.REEDIT) {
                        this.setState({ waittingAgentEdit: true });
                        return;
                    }
                    let state = decompressJson(Res.data?.[0]?.jsonState);
                    state.status = Res.data?.[0]?.status;
                    state.callBackCancel = '/';
                    state.callBackSuccess = '/';
                    state = this.clearPopupStateOnly(state);
                    state.apiToken = getSession(ACCESS_TOKEN);
                    state.signature = this.state.clientId || getSession(CLIENT_ID);
                    setSession(SIGNATURE, state.signature);
                    state.effectiveDate = Res.data?.[0]?.effectiveDate;
                    state.isSubmitting = false;
                    // this.setState(state);
                    this.reLoadMaster();
                    this.getPermissionOnly(d, Res.data?.[0]?.lockingBy, Res.data?.[0]?.status, state);
                }
                // if (Res.data?.[0]?.lockingBy) {
                //     this.setState({lockingBy: Res.data?.[0]?.lockingBy});
                // }
                // this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                // this.goNextStep();
                // getPolList (this.props.clientId, getDeviceId(), token);
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }


    requestClaimGetTempInfo = (lifeInsuredId, index, item) => {
        let state = this.state;
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            sourceSystem: this.state.systemGroupPermission?.[0]?.SourceSystem,
            lifeInsuredId: lifeInsuredId,
            customerId: this.state.clientId || getSession(CLIENT_ID)
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimGetTempInfo(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data ) {
                if (Res.data?.[0].requestId && Res.data?.[0].jsonState) {
                    let tempJson = Res.data?.[0]?.jsonState;
                    state.tempJson = tempJson;
                    state.tempRequestId = Res.data?.[0].requestId;
                    state.changeSelectLIIndex = index;
                    state.changeSelectLIItem = item;
                    state.selectedCliID = item.clientId;
                    state.haveCreatingData = true;
                    state.status = Res.data?.[0]?.status;
                    this.setState(state);
                    return;
                } 

            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });
        //Reset state
        state.initClaimState.occupation = '';
        state.initClaimState.disabledButton = true;
        state.claimTypeState = INITIAL_STATE().claimTypeState;
        state.claimDetailState = INITIAL_STATE().claimDetailState;
        state.paymentMethodState = INITIAL_STATE().paymentMethodState;
        state.attachmentState = INITIAL_STATE().attachmentState;
        state.submissionState = INITIAL_STATE().submissionState;
        state.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        state.selectedCliID = item.clientId;
        state.selectedCliInfo = item;
        state.liInfo = item;
        state.selectedCliIndex = index;
        state.claimCheckedMap = {};
        state.openPopupWarningDecree13 = false;
        state.trackingId = '';
        state.claimId = '';
        state.poConfirmingND13 = '0';
        state.ND13ClientProfile = null;
        if (item.fullName) {
            const arrFulName = item.fullName?.trim().split(" ");
            if (arrFulName) {
                state.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
            }
        }
        state.claimTypeState.isSamePoLi = ((getSession(CLIENT_ID) ? getSession(CLIENT_ID).trim() : this.state.clientId?.trim()) === (state.selectedCliID ? state.selectedCliID.trim() : ''));
        state.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
        // let clientProfile = null;
        // if (state !== null) {
        //     clientProfile = state.responseLIList;
        // }
        this.setState(state);
    }


    requestClaimGetList = (d, systemGroupPermission) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: this.state.platform ? this.state.platform : WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            agentCode: d?.agentCode,
            status: '',
            requestId: d?.requestId ? d?.requestId : ''
        }
        let request = buildMicroRequest(metadata, data);
        requestClaimGetList(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('tracking data=', Res.data);
                //filter item status không là DELETE
                let trackingData = Res.data?.filter(ite => (!["REQUEST", "INITIAL", "DELETE"].includes(ite.status)));
                if (d?.status !== 'ALL') {
                    // if (d?.status === 'EDIT') {
                    //     trackingData = trackingData?.filter(ite => (["REQUEST", "INITIAL", "EDIT"].includes(ite.status)));
                    // } else {
                    //     trackingData = trackingData?.filter(ite => (ite.status === d?.status));
                    // }
                    if (d?.status === 'POREVIEW') {
                        trackingData = trackingData?.filter(ite => (["POREVIEW", "WAITINGCONFIRMDLCN"].includes(ite.status)));
                    } else if (d?.status === 'CANCEL') {
                        trackingData = trackingData?.filter(ite => (["REJECT_CLAIM", "REJECT_DLCN", "CANCEL", "REJECT_AUTHORIZED"].includes(ite.status)));
                    } else {
                        trackingData = trackingData?.filter(ite => (ite.status === d?.status));
                    }
                }
                this.setState({ trackingClaimData: trackingData });
                this.setState({ systemGroupPermission: systemGroupPermission });
                console.log('requestClaimGetList systemGroupPermission=', systemGroupPermission);
                this.handlerGoToStep(this.getStepbyGroup(CLAIM_STEPCODE.TRACKING_CLAIM, systemGroupPermission));
                // this.handlerGoToStep(14);
                // if (Res.data?.[0]?.jsonState) {
                //     let state = JSON.parse(Res.data?.[0]?.jsonState);
                //     console.log('xaaaa=', state);
                //     this.setState(state);
                //     this.getPermissionOnly(d, Res.data?.[0]?.lockingBy);
                // }
                // if (Res.data?.[0]?.lockingBy) {
                //     this.setState({lockingBy: Res.data?.[0]?.lockingBy});
                // }
                // this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                // this.goNextStep();
                // getPolList (this.props.clientId, getDeviceId(), token);
            } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
        }).catch(error => {
            console.log(error);
        });

    }

    // updateTrackingClaimData=(value) => {
    //     this.setState({trackingClaimData: value});
    // }

    changeSubPage(currentState) {
        let systemGroupPermission = this.state.systemGroupPermission;
        if (systemGroupPermission?.length === 0) {
            return (<AuthenticationPopupClaim closePopup={this.closeToHome}
                msg={'Không có quyền thực hiện yêu cầu.'}
            />)
        }
        console.log('currentState=', currentState);
        console.log('systemGroupPermission=', systemGroupPermission);
        let stepCode = systemGroupPermission?.[currentState]?.StepCode;
        // if (this.state.dataRequest && this.state.dataRequest.status && (stepCode === CLAIM_STEPCODE.CREATE_REQUEST)) {
        //     stepCode =CLAIM_STEPCODE.TRACKING_CLAIM;
        // }
        // if (currentState === 14) {//Harcode, ngay mai fix config
        //     stepCode = CLAIM_STEPCODE.TRACKING_CLAIM;
        // }
        console.log('stepCode=', stepCode);
        console.log('a=', ((stepCode === CLAIM_STEPCODE.REVIEW) || (stepCode === CLAIM_STEPCODE.SUBMIT) || (stepCode === CLAIM_STEPCODE.DONE) || (stepCode === CLAIM_STEPCODE.ND13_INFO_CONFIRMATION) || (stepCode === CLAIM_STEPCODE.ND13_INFO_PO_CONTACT_INFO_OVER_18) || (stepCode === CLAIM_STEPCODE.ND13_INFO_PO_CONTACT_INFO_UNDER_18)));
        console.log('b=', (stepCode === CLAIM_STEPCODE.REVIEW));
        let clientId = this.state.clientId ? this.state.clientId : getSession(CLIENT_ID);
        console.log('clientId=', this.state.clientId);
        console.log('la step DLCN: ', stepCode);
        // alert('stepCode=' + stepCode);
        // alert('this.state.systemGroupPermission.Role=' + this.state.systemGroupPermission?.[0].Role);
        switch (stepCode) {
            case CLAIM_STEPCODE.TRACKING_CLAIM:
                return this.state.trackingClaimData && (<SDKTrackingClaimRequest clientId={this.state.clientId || getSession(CLIENT_ID)}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    agentCode={this.state.agentCode}
                    agentName={this.state.agentName}
                    agentPhone={this.state.agentPhone}
                    sourceSystem={this.state.sourceSystem}
                    processType="Claim"
                    systemGroupPermission={this.state.systemGroupPermission}
                    currentState={this.state.currentState}
                    handlerGoToStep={this.handlerGoToStep}
                    dataDescrypt={this.state.dataDescrypt}
                    data={this.props.data}
                    handleSetClaimRequestId={this.handleSetClaimRequestId}
                    state={{ ...this.state }}
                    setCurrentState={(v) => this.setCurrentState(v)}
                    handlerResetState={this.handlerResetState}
                    handleLoadMaster={this.handleLoadMaster}
                    handleSetSystemGroupPermission={this.handleSetSystemGroupPermission}
                    handleGoNextStep={this.handleGoNextStep}
                    handleLoadState={this.handleLoadState}
                    handleSetSelfKeyInConfirm={this.handleSetSelfKeyInConfirm}
                    selfKeyInConfirm={this.state.selfKeyInConfirm}
                    handlerSetClientId={this.handlerSetClientId}
                    handlerSetClientFullName={this.handlerSetClientFullName}
                    clearState={() => this.clearState()}
                    trackingClaimData={this.state.trackingClaimData}
                    requestClaimGetList={() => this.requestClaimGetList()}
                    requestClaimGetInforAgent={(requestId, data, status) => this.requestClaimGetInforAgent(requestId, data, status)}
                    getPermissionOnly={(dataRequest) => this.getPermissionOnly(dataRequest)}
                    dataRequest={this.state.dataRequest}
                    tabStatus={this.state.tabStatus}
                    markDeleteClaimRequest={(requestId, customerId) => this.markDeleteClaimRequest(requestId, customerId)}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    closeToHome={this.handleCloseToHome}
                    cssSystem={cssSystem} />)
            case CLAIM_STEPCODE.CREATE_REQUEST:
                return (<CreateRequest clientId={this.state.clientId || getSession(CLIENT_ID)}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    agentCode={this.state.agentCode}
                    agentName={this.state.agentName}
                    agentPhone={this.state.agentPhone}
                    sourceSystem={this.state.sourceSystem}
                    processType="Claim"
                    systemGroupPermission={this.state.systemGroupPermission}
                    currentState={this.state.currentState}
                    handlerGoToStep={this.handlerGoToStep}
                    dataDescrypt={this.state.dataDescrypt}
                    data={this.props.data}
                    appData={this.state.appData}
                    handleSetClaimRequestId={this.handleSetClaimRequestId}
                    state={{ ...this.state }}
                    setCurrentState={(v) => this.setCurrentState(v)}
                    handlerResetState={this.handlerResetState}
                    handleLoadMaster={this.handleLoadMaster}
                    handleSetSystemGroupPermission={this.handleSetSystemGroupPermission}
                    handleGoNextStep={this.handleGoNextStep}
                    handleLoadState={this.handleLoadState}
                    handleSetSelfKeyInConfirm={this.handleSetSelfKeyInConfirm}
                    selfKeyInConfirm={this.state.selfKeyInConfirm}
                    handlerSetClientId={this.handlerSetClientId}
                    handlerSetClientFullName={this.handlerSetClientFullName}
                    clearState={() => this.clearState()}
                    selectedCliInfo={this.state.selectedCliInfo}
                    updateApiToken={(value) => this.updateApiToken(value)}
                    updateAgentConfirmed={(value) => this.updateAgentConfirmed(value)}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    paramKey={this.props.paramKey}
                    updateSignature={(v)=>this.updateSignature(v)}
                    closeToHome={this.handleCloseToHome}
                    updateIdNum={(v)=>this.updateIdNum(v)}
                    setNoValidPolicy={(v)=>this.setNoValidPolicy(v)}
                    cssSystem={cssSystem} />)
            case CLAIM_STEPCODE.CREATE_AUTHORIZION:
                return (<CreateAuthorization clientId={this.state.clientId || getSession(CLIENT_ID)}
                    apiToken={this.state.apiToken} agentCode={this.state.agentCode}
                    agentName={this.state.agentName}
                    agentPhone={this.state.agentPhone}
                    sourceSystem={this.state.sourceSystem}
                    processType="Claim"
                    systemGroupPermission={this.state.systemGroupPermission}
                    currentStateIndex={this.state.currentStateIndex}
                    handleUpdateCurrentStateIndex={this.handleUpdateCurrentStateIndex}
                    handlerGoToStep={this.handlerGoToStep}
                    handleSetClaimRequestId={this.handleSetClaimRequestId}
                    handleSetAuthorizedId={this.handleSetAuthorizedId}
                    state={{ ...this.state }}
                    handleChooseAgent={this.handleChooseAgent}
                    handleSetSystemGroupPermission={this.handleSetSystemGroupPermission}
                    handleGoNextStep={this.handleGoNextStep}
                    handleLoadState={this.handleLoadState}
                    cssSystem={cssSystem}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    selectedAgent={this.state.selectedAgent}
                    closeToHome={this.handleCloseToHome}
                    selectedCliInfo={this.state.selectedCliInfo} />)
            // case CLAIM_STEPCODE.REQUEST_AUTHORIZION:
            //     return (<RequestAuthorization clientId={this.state.clientId} agentCode={this.state.agentCode} agentName={this.state.agentName} agentPhone={this.state.agentPhone} sourceSystem={this.state.sourceSystem} processType="Claim" systemGroupPermission={this.state.systemGroupPermission} currentStateIndex={this.state.currentStateIndex} handleUpdateCurrentStateIndex={this.handleUpdateCurrentStateIndex} handlerGoToStep={this.handlerGoToStep}/>)
            case CLAIM_STEPCODE.CONFIRM_AUTHORIZION:
                return (<ConfirmAuthorization
                    clientId={this.state.clientId || getSession(CLIENT_ID)}
                    apiToken={this.state.apiToken}
                    agentCode={this.state.agentCode}
                    agentName={this.state.agentName}
                    agentPhone={this.state.agentPhone}
                    sourceSystem={this.state.sourceSystem}
                    processType="Claim"
                    systemGroupPermission={this.state.systemGroupPermission}
                    state={{ ...this.state }}
                    handlerGoToStep={this.handlerGoToStep}
                    handleSetSystemGroupPermission={this.handleSetSystemGroupPermission}
                    handleGoNextStep={this.handleGoNextStep}
                    handleLoadState={this.handleLoadState}
                    cssSystem={cssSystem}
                    handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                    closeToHome={this.handleCloseToHome}
                    setCurrentState={(v) => this.setCurrentState(v)}
                    setShowConfirmAuthorization={(v) => this.setShowConfirmAuthorization(v)}
                    // checkAuthorization={()=>this.checkAuthorization()}
                    authorzieStatus={this.state.authorzieStatus}
                    handleSetAuthorizedId={this.handleSetAuthorizedId}
                    authorizationId={this.state.authorizationId}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    effectiveDate={this.state.effectiveDate}
                    fullName={this.state.fullName}
                    handlerGetStep={this.handlerGetStep} />)
            case CLAIM_STEPCODE.INIT:
                return (this.state.initClaim && <SDKClaimInit clientId={this.state.clientId || getSession(CLIENT_ID)}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    agentCode={this.state.agentCode}
                    agentName={this.state.agentName}
                    agentPhone={this.state.agentPhone}
                    sourceSystem={this.state.sourceSystem}
                    processType="Claim"
                    systemGroupPermission={this.state.systemGroupPermission}
                    currentState={this.state.currentState}
                    handlerGoToStep={this.handlerGoToStep}
                    dataDescrypt={this.state.dataDescrypt}
                    data={this.props.data}
                    handleSetClaimRequestId={this.handleSetClaimRequestId}
                    state={{ ...this.state }}
                    setCurrentState={(v) => this.setCurrentState(v)}
                    handleLoadMaster={this.handleLoadMaster}
                    handleSetSystemGroupPermission={this.handleSetSystemGroupPermission}
                    handleGoNextStep={this.handleGoNextStep}
                    handleLoadState={this.handleLoadState}
                    selectedCliInfo={this.state.selectedCliInfo}
                    updateAgentConfirmed={(value) => this.updateAgentConfirmed(value)}
                    agentConfirmed={this.state.agentConfirmed}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    noValidPolicy={this.state.noValidPolicy}
                    selectedCliID={this.state.selectedCliID}
                    cssSystem={cssSystem} />)


            case CLAIM_STEPCODE.INIT_CLAIM:
                return (<section className="sccontract-warpper white-bg">
                    {getUrlParameter("fromApp") && (
                        <div className='app-header-back'>
							<i className='margin-left8' onClick={() => this.goBackLI()}><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                            <p className='viewer-back-title'>Tạo mới yêu cầu</p>
                        </div>	
                    )

                    }

                    {!getUrlParameter("fromApp") &&
                        <div className="other_option" id="other-option-toggle" onClick={this.goBack}>
                            <p>Chọn thông tin</p>
                            <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
                        </div>
                    }
                    <div className={getUrlParameter("fromApp") === 'Android'?'padding-top64 margin-top56':'padding-top32 margin-top56'}></div>
                    <div className="scbenefits">
                        <div className="benefit-wrapper">
                            <form onSubmit={(event) => this.handleSubmit(event)}>
                                <div className="input disabled benefit-jobtitle">
                                    <div className="input__content" style={{ paddingLeft: '6px' }}>
                                        {this.state.selectedCliInfo.fullName &&
                                            <label htmlFor="" style={{ paddingLeft: '2px' }}>Người được bảo hiểm</label>}
                                        <input type="search" name="inputOccupation" id="inputOccupation" required
                                            placeholder="Người được bảo hiểm" maxLength="120"
                                            value={this.state.selectedCliInfo.fullName ? this.state.selectedCliInfo.fullName.trim() : ''}
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div className="input disabled benefit-jobtitle">
                                    <div className="input__content" style={{ paddingLeft: '6px' }}>
                                        {this.state.selectedCliInfo.dOB &&
                                            <label htmlFor="" style={{ paddingLeft: '2px' }}>Ngày sinh</label>}
                                        <input type="search" name="inputDOB" id="inputDOB" required
                                            placeholder="Ngày sinh" maxLength="50"
                                            value={formatDate(this.state.selectedCliInfo.dOB)} disabled
                                        />
                                    </div>
                                </div>
                                <div className="input disabled benefit-jobtitle">
                                    <div className="input__content" style={{ paddingLeft: '6px' }}>
                                        {this.state.selectedCliInfo.idNum &&
                                            <label htmlFor="" style={{ paddingLeft: '2px' }}>Số giấy tờ nhân
                                                thân</label>}
                                        <input type="search" name="inputOccupation" id="inputOccupation" required
                                            placeholder="Số giấy tờ nhân thân" maxLength="50"
                                            value={this.state.selectedCliInfo.idNum ? this.state.selectedCliInfo.idNum.trim() : ''}
                                            disabled
                                        />
                                    </div>
                                </div>
                                <div className="input benefit-jobtitle">
                                    <div className="input__content" style={{ paddingLeft: '6px' }}>
                                        {this.state.initClaimState.occupation &&
                                            <label htmlFor="" style={{ paddingLeft: '2px' }}>Nghề nghiệp hiện
                                                tại</label>}
                                        <input type="search" name="inputOccupation" id="inputOccupation" required
                                            placeholder="Nghề nghiệp hiện tại" maxLength="50"
                                            value={this.state.initClaimState.occupation}
                                            onChange={this.handlerInputOccupation}
                                            disabled={this.state.disableEdit && !this.state.agentKeyInPOSelfEdit}
                                        />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                                <div className="benefit-contract">
                                    <h4 className="basic-semibold">Danh sách hợp đồng</h4>
                                    <PolList
                                        selectedCliId={this.state.selectedCliID}
                                        selectedCliInfo={this.state.selectedCliInfo}
                                        apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                                        systemGroupPermission={this.state.systemGroupPermission}
                                        clientId={this.state.clientId || getSession(CLIENT_ID)}
                                        setNoValidPolicy={(v) => this.setNoValidPolicy(v)}
                                        callBackCancel={this.state.callBackCancel}
                                        agentCode={this.state.agentCode}
                                        handlerLoadedPolList={this.handlerLoadedPolList} />
                                </div>
                                <div className="bottom-text" style={{ 'maxWidth': '594px' }}>
                                    <p style={{ textAlign: 'justify' }}>
                                        <span className="red-text basic-bold">Lưu ý:</span>
                                        <span style={{ 'display': 'block', color: '#727272' }}>
                                            - Quyền lợi về Chăm sóc sức khỏe sẽ được phản hồi đến Quý khách
                                            tối đa 15 ngày kể từ ngày nộp đầy đủ hồ sơ
                                            và 45 ngày đối với hồ sơ cần liên hệ Bệnh viện lấy thêm thông tin.
                                        </span>
                                        <span style={{ 'display': 'block', color: '#727272' }}>
                                            - Quyền lợi về Tử vong/ Bệnh hiểm nghèo/ Thương tật và Nằm viện do Tai nạn sẽ được phản hồi đến Quý khách
                                            tối đa 30 ngày kể từ ngày nộp đầy đủ hồ sơ.
                                        </span>
                                        {getSession(IS_MOBILE)?(
                                            <span style={{ 'display': 'block', color: '#727272' }}>
                                                - Tham khảo <a style={{ display: 'inline' }} className="red-text basic-bold" href='#' onClick={()=>callbackAppOpenLink(CLAIM_GUID, getUrlParameter("fromApp"))}
                                                    target='_blank'>Hướng dẫn giải quyết quyền lợi bảo hiểm.</a>
                                            </span>
                                        ):(
                                            <span style={{ 'display': 'block', color: '#727272' }}>
                                                - Tham khảo <a style={{ display: 'inline' }} className="red-text basic-bold" href={CLAIM_GUID}
                                                    target='_blank'>Hướng dẫn giải quyết quyền lợi bảo hiểm.</a>
                                            </span>
                                        )}

                                    </p>
                                </div>
                                {getSession(IS_MOBILE) &&
                                    <div className='nd13-padding-bottom64'></div>
                                }
                                <div className="bottom-btn">
                                    <button
                                        className={(((this.state.initClaimState.disabledButton === undefined) || this.state.initClaimState.disabledButton) && !this.state.disableEdit && !this.state.agentKeyInPOSelfEdit) ? "btn btn-primary disabled" : "btn btn-primary"}
                                        id="startClaimProcess"
                                        disabled={((this.state.initClaimState.disabledButton === undefined) || this.state.initClaimState.disabledButton) && !this.state.disableEdit && !this.state.agentKeyInPOSelfEdit}
                                        onClick={(event) => this.handleSubmit(event)}>Tiếp tục
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </section>);

            case CLAIM_STEPCODE.CLAIM_TYPE:
                return (<ClaimType
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerChangeClaimTypeOption={this.handlerChangeClaimTypeOption}
                    handlerSubmitClaimType={this.handlerSubmitClaimType}
                    loadClaimTypeList={this.handleLoadClaimTypeList}
                    claimCheckedMap={this.state.claimCheckedMap}
                    claimTypeState={this.state.claimTypeState}
                    // selectedCliID={this.state.selectedCliID}
                    answerYes={this.handleAnswerYes}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    selectedCliInfo={this.state.selectedCliInfo}
                    systemGroupPermission={this.state.systemGroupPermission}
                    disableEdit={this.state.disableEdit}
                    handlerGetStep={this.handlerGetStep}
                    clientId={this.state.clientId || getSession(CLIENT_ID)}
                    selectedCliId={this.state.selectedCliID}
                    claimTypeList={this.state.claimTypeList}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    agentCode={this.state.agentCode}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    sourceSystem={this.state.sourceSystem}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                    answerNo={this.handleAnswerNo} />);
            case CLAIM_STEPCODE.CLAIM_DETAIL:
                return (<ClaimDetail
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerUpdateMainState={this.handlerUpdateMainState}
                    handlerSubmitClaimDetail={this.handlerSubmitClaimDetail}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    handlerClearBelowBenifit={this.handlerClearBelowBenifit}
                    SelectedHospital={this.handleSelectedHospital}
                    SelectedHospitalChosen={this.handleSelectedHospitalChosen}
                    SelectedDiagnosticResult={this.handleSelectedDiagnosticResult}
                    SelectOtherHospital={this.handlerSelectOtherHospital}
                    SelectOtherDiagnostic={this.handlerSelectOtherDiagnostic}
                    enterOtherCompanyName={this.handleEnterOtherCompanyName}
                    enterOtherCompanyPaid={this.handleEnterOtherCompanyPaid}
                    isOtherCompanyRelated={this.handleIsOtherCompanyRelated}
                    SelectedSickInfoPlaceChosen={this.handleSelectedSickInfoPlaceChosen}
                    SelectOtherSickInfoPlace={this.handleSelectOtherSickInfoPlace}
                    zipCodeList={this.state.zipCodeList}
                    nationList={this.state.nationList}
                    hospitalList={this.state.hospitalList}
                    hospitalResultList={this.state.hospitalResultList}
                    claimDetailState={this.state.claimDetailState}
                    claimCheckedMap={this.state.claimCheckedMap}
                    selectedCliInfo={this.state.selectedCliInfo}
                    claimTypeState={this.state.claimTypeState} isAccidentClaim={this.state.isAccidentClaim}
                    inVietNam={this.handleInVietNam} notInVietNam={this.handleNotInVietNam}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    systemGroupPermission={this.state.systemGroupPermission}
                    disableEdit={this.state.disableEdit}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    handlerGetStep={this.handlerGetStep}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    sourceSystem={this.state.sourceSystem}
                    status={this.state.status}
                    pinStep={this.state.pinStep}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                    isVietnam={this.state.isVietnam} />);
            case CLAIM_STEPCODE.PAYMENT_METHOD:
                return (<PaymentMethod
                    handlerSubmitPaymentMethod={this.handlerSubmitPaymentMethod}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    selectedCliInfo={this.state.selectedCliInfo}
                    claimTypeState={this.state.claimTypeState}
                    claimCheckedMap={this.state.claimCheckedMap}
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerUpdateMainState={this.handlerUpdateMainState}
                    zipCodeList={this.state.zipCodeList}
                    bankList={this.state.bankList}
                    contactState={this.state.contactState}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    systemGroupPermission={this.state.systemGroupPermission}
                    disableEdit={this.state.disableEdit}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    handlerGetStep={this.handlerGetStep}
                    claimTypeList={this.state.claimTypeList}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    isSubmitting={this.state.isSubmitting}
                    sourceSystem={this.state.sourceSystem}
                    status={this.state.status}
                    pinStep={this.state.pinStep}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                    fullName={this.state.fullName}
                    idNum={this.state.idNum}
                    paymentMethodState={this.state.paymentMethodState} />);
            case CLAIM_STEPCODE.CONTACT:
                return (<ContactPersonDetail
                    handlerSubmitContact={this.handlerSubmitContact}
                    handleUpdateSubContactState={this.handleUpdateSubContactState}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    selectedCliInfo={this.state.selectedCliInfo}
                    claimTypeState={this.state.claimTypeState}
                    claimCheckedMap={this.state.claimCheckedMap}
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerUpdateMainState={this.handlerUpdateMainState}
                    zipCodeList={this.state.zipCodeList}
                    bankList={this.state.bankList}
                    currentState={this.state.currentState}
                    paymentMethodState={this.state.paymentMethodState}
                    contactPersonInfo={this.state.contactState.contactPersonInfo}
                    relationShipList={this.state.relationShipList}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    systemGroupPermission={this.state.systemGroupPermission}
                    disableEdit={this.state.disableEdit}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    handlerGetStep={this.handlerGetStep}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                    isSubmitting={this.state.isSubmitting}
                    sourceSystem={this.state.sourceSystem}
                    status={this.state.status}
                    pinStep={this.state.pinStep}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                    fullName={this.state.fullName}
                    idNum={this.state.idNum}
                    contactState={this.state.contactState} />);
            // case CLAIM_STEPCODE.ND13_INFO_FOLLOW_CONFIRMATION:
            case CLAIM_STEPCODE.ATTACHMENT:
                return (<Attachment
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerUpdateMainState={this.handlerUpdateMainState}
                    handlerLoadedAttachmentData={this.handlerLoadedAttachmentData}
                    handlerSubmitAttachment={this.handlerSubmitAttachment}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    updateAttachmentData={this.handlerUpdateAttachmentData}
                    claimTypeState={this.state.claimTypeState}
                    claimCheckedMap={this.state.claimCheckedMap}
                    isAccidentClaim={this.state.isAccidentClaim}
                    apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                    systemGroupPermission={this.state.systemGroupPermission}
                    selectedCliInfo={this.state.selectedCliInfo}
                    disableEdit={this.state.disableEdit}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    handlerGetStep={this.handlerGetStep}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                    isSubmitting={this.state.isSubmitting}
                    sourceSystem={this.state.sourceSystem}
                    status={this.state.status}
                    pinStep={this.state.pinStep}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                    attachmentState={this.state.attachmentState} />);
            case CLAIM_STEPCODE.REVIEW:
            case CLAIM_STEPCODE.SUBMIT:
            case CLAIM_STEPCODE.DONE:
                return (this.state.showConfirmAuthorization && (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) ? (
                    <ConfirmAuthorization
                        systemGroupPermission={this.state.systemGroupPermission}
                        requestId={this.state.requestId}
                        effectiveDate={this.state.effectiveDate}
                        selectedCliInfo={this.state.selectedCliInfo}
                        agentName={this.state.agentName}
                        agentCode={this.state.agentCode}
                        agentPhone={this.state.agentPhone}
                        claimCheckedMap={this.state.claimCheckedMap}
                        handlerGetStep={this.handlerGetStep}
                        setCurrentState={(v) => this.setCurrentState(v)}
                        handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                        closeToHome={this.handleCloseToHome}
                        processType="Claim"
                        setShowConfirmAuthorization={(v) => this.setShowConfirmAuthorization(v)}
                        // checkAuthorization={()=>this.checkAuthorization()}
                        authorzieStatus={this.state.authorzieStatus}
                        handleSetAuthorizedId={this.handleSetAuthorizedId}
                        clientId={this.state.clientId || getSession(CLIENT_ID)}
                        authorizationId={this.state.authorizationId}
                        callBackCancel={this.state.callBackCancel}
                        callBackSuccess={this.state.callBackSuccess}
                        sourceSystem={this.state.sourceSystem}
                    />
                ) : (
                    <ClaimSubmission
                        handlerSubmitContact={this.handlerSubmitContact}
                        handlerBackToPrevStep={this.handlerBackToPrevStep}
                        handlerGoToStep={this.handlerGoToStep}
                        handlerUpdateMainState={this.handlerUpdateMainState}
                        handlerSubmitClaimSubmission={this.handlerSubmitClaimSubmission}
                        handlerResetState={this.handlerResetState}
                        selectedCliInfo={this.state.selectedCliInfo}
                        initClaimState={this.state.initClaimState}
                        claimTypeState={this.state.claimTypeState}
                        claimDetailState={this.state.claimDetailState}
                        paymentMethodState={this.state.paymentMethodState}
                        attachmentState={this.state.attachmentState}
                        claimCheckedMap={this.state.claimCheckedMap}
                        contactState={this.state.contactState}
                        isVietnam={this.state.isVietnam}
                        claimCheckedLstBenMap={this.state.claimCheckedLstBenMap}
                        hospitalResultList={this.state.hospitalResultList}
                        hospitalList={this.state.hospitalList}
                        claimTypeList={this.state.claimTypeList}
                        handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                        closeToHome={this.handleCloseToHome}
                        submissionState={this.state.submissionState}
                        currentState={this.state.currentState}
                        claimSubmissionState={this.state.claimSubmissionState}
                        callBackConfirmation={this.callBackConfirmation}
                        callBackTrackingId={this.callBackTrackingId}
                        callBackClaimID={this.callBackClaimID}
                        liInfo={this.state.liInfo}
                        trackingId={this.state.trackingId}
                        claimId={this.state.claimId}
                        clientProfile={this.state.ClientProfile}
                        poConfirmingND13={this.state.poConfirmingND13}
                        ND13ClientProfile={this.state.ND13ClientProfile}
                        callBackUpdateND13State={this.callBackUpdateND13State}
                        callBackUpdateND13ClientProfile={this.callBackUpdateND13ClientProfile}
                        handleUpdateSubContactState={this.handleUpdateSubContactState}
                        callPOConfirmingND13={this.callPOConfirmingND13}
                        saveState={this.saveState}
                        callBackOnlyPayment={this.callBackOnlyPayment}
                        onlyPayment={this.state.onlyPayment}
                        apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                        systemGroupPermission={this.state.systemGroupPermission}
                        handleOnChangeFatcaState={this.handleOnChangeFatcaState}
                        handleOnClickConfirmCloseButton={this.handleOnClickConfirmCloseButton}
                        disableEdit={this.state.disableEdit}
                        agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                        handlerGetStep={this.handlerGetStep}
                        handleGoNextStep={this.handleGoNextStep}
                        submitClaimInfo={(status) => this.submitClaimInfo(status)}
                        handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                        popupConfirmClaimCalled={this.state.popupConfirmClaimCalled}
                        callBackCancel={this.state.callBackCancel}
                        callBackSuccess={this.state.callBackSuccess}
                        agentCode={this.state.agentCode}
                        agentPhone={this.state.agentPhone}
                        agentName={this.state.agentName}
                        requestId={this.state.requestId}
                        effectiveDate={this.state.effectiveDate}
                        isSubmitting={this.state.isSubmitting}
                        sourceSystem={this.state.sourceSystem}
                        updateInfoState={this.state.updateInfoState}
                        setUpdateInfoState={(v) => this.setUpdateInfoState(v)}
                        consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                        updatePinStep={(v)=>this.updatePinStep(v)}
                    />));

            case CLAIM_STEPCODE.SDK_DLCN:
                return (<ND13
                    handlerSubmitContact={this.handlerSubmitContact}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    selectedCliInfo={this.state.selectedCliInfo}
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    currentState={this.state.currentState}
                    contactPersonInfo={this.state.contactState.contactPersonInfo}
                    claimCheckedMap={this.state.claimCheckedMap}
                    handlerGoToStep={this.handlerGoToStep}
                    callBackConfirmation={this.callBackConfirmation}
                    callBackUpdateND13State={this.callBackUpdateND13State}
                    closeToHome={this.handleCloseToHome}
                    trackingId={this.state.trackingId ? this.state.trackingId : this.state.requestId}
                    claimId={this.state.requestId}
                    claimSubmissionState={this.state.claimSubmissionState}
                    onlyPayment={this.state.onlyPayment}
                    callBackUpdateND13ClientProfile={this.callBackUpdateND13ClientProfile}
                    callBackTrackingId={this.callBackTrackingId}
                    callBackLIWating={this.callBackLIWating}
                    liWating={this.state.liWating}
                    saveState={this.saveState}
                    cancelClaim={this.cancelClaim}
                    systemGroupPermission={this.state.systemGroupPermission}
                    deviceId={this.state.deviceId}
                    apiToken={this.state.apiToken}
                    phone={getSession(CELL_PHONE)}
                    proccessType={this.state.processType}
                    appType={'CLOSE'}
                    // appType={this.props.appType}
                    clientListStr={(getSession(CLIENT_ID) || this.state.clientId) !== this.state.selectedCliInfo?.clientId ? ((getSession(CLIENT_ID) || this.state.clientId) + ',' + this.state.selectedCliInfo?.clientId) : (getSession(CLIENT_ID) || this.state.clientId)}
                    clientId={(getSession(CLIENT_ID) || this.state.clientId)}
                    clientName={getSession(FULL_NAME)}
                    role={this.state.systemGroupPermission?.[0]?.Role}
                    disableEdit={this.state.disableEdit}
                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                    handlerUpdateClaimRequest={this.handlerUpdateClaimRequest}
                    handlerUpdateCloneClaimRequest={this.handlerUpdateCloneClaimRequest}
                    handlerGetStep={this.handlerGetStep}
                    setCurrentState={(v) => this.setCurrentState(v)}
                    submitClaimInfoConfirm={(data, onlyPO) => this.submitClaimInfoConfirm(data, onlyPO)}
                    submitClaimInfo={(status) => this.submitClaimInfo(status)}
                    handlerConfirmCPConsent={this.handlerConfirmCPConsent}
                    updateInfoState={this.state.updateInfoState}
                    isPayment={this.state.isPayment}
                    haveShowPayment={this.state.haveShowPayment}
                    setIsPayment={(v) => this.setIsPayment(v)}
                    poConfirmingND13={this.state.poConfirmingND13}
                    setHaveShowPayment={(v) => this.setHaveShowPayment(v)}
                    setSendPOSuccess={() => this.setSendPOSuccess()}
                    claimTypeList={this.state.claimTypeList}
                    paymentMethodState={this.state.paymentMethodState}
                    setNd13State={(v) => this.setNd13State(v)}
                    claimDetailState={this.state.claimDetailState}
                    callBackCancel={this.state.callBackCancel}
                    callBackSuccess={this.state.callBackSuccess}
                    setUpdateInfoState={(v) => this.setUpdateInfoState(v)}
                    sourceSystem={this.state.sourceSystem}
                    stepName={this.state.currentState}
                    doneClaim={() => this.doneClaim()}
                    saveAndQuit={()=>this.saveAndQuit()}
                    handlerAdjust={()=>this.handlerAdjust()}
                    saveLocalAndQuit={()=>this.saveLocalAndQuit()}
                    consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                />);
            default:
                return (
                    <main>
                        <LoadingIndicator area="submit-init-claim" />

                    </main>
                );
        }
    }

    goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    LIContinue = () => {
        this.goToStep(this.handlerGetStep(CLAIM_STEPCODE.INIT_CLAIM));
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    goBackLI = () => {
        this.goToStep(this.handlerGetStep(CLAIM_STEPCODE.INIT));
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    closeNotAvailable = () => {
        this.setState({
            msgCheckPermission: '', isCheckPermission: false
        });
        this.props.history.push('/');
    }

    saveLocalAndQuit = () => {
        this.setState({ saveAndQuit: true });
    }

    save = () => {
        // if (this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) {
        //     return;
        // }
        // let jsonState = this.state;
        // if ((jsonState.currentState > this.getStep(CLAIM_STEPCODE.INIT_CLAIM))) {
        //     setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + jsonState.selectedCliID, JSON.stringify(jsonState));
        // }
    }

    closeToHome = () => {
        // this.props.history.push("/home");
        let from = getUrlParameter("fromApp");
        if (from) {
            let obj = {
                Action: "END_ND13_" + this.state.processType,
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
            window.location.href = this.state.callBackSuccess;
        }
    }

    setSystemGroupPermission = (value) => {
        if (value) {
            this.setState({ systemGroupPermission: value });
        }
    }

    getStep = (stepCode) => {
        if (this.state.systemGroupPermission) {
            for (let i = 0; i < this.state.systemGroupPermission.length; i++) {
                if (this.state.systemGroupPermission[i].StepCode === stepCode) {
                    return this.state.systemGroupPermission[i].StepOrder;
                }
            }
        }
        return 0;
    }

    getStepbyGroup = (stepCode, systemGroupPermission) => {
        if (systemGroupPermission) {
            for (let i = 0; i < systemGroupPermission.length; i++) {
                if (systemGroupPermission[i].StepCode === stepCode) {
                    console.log('getStepbyGroup ok StepCode=', stepCode);
                    return systemGroupPermission[i].StepOrder;
                }
            }
        }
        console.log('getStepbyGroup khong tim thay StepCode=', stepCode);
        return 0;
    }

    getStepCodebyStepAndGroup = (step, systemGroupPermission) => {
        if (systemGroupPermission) {
            for (let i = 0; i < systemGroupPermission.length; i++) {
                if (systemGroupPermission[i].StepOrder === step) {
                    console.log('getStepCodebyStepAndGroup ok StepCode=', step);
                    return systemGroupPermission[i].StepCode;
                }
            }
        }
        console.log('getStepbyGroup khong tim thay StepCode=', stepCode);
        return 0;
    }

    async submitClaimInfo(status) {
        if (this.state.isSubmitting) {
            return;
        }
        const jsonState = this.state;
        const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const submitClaimInfoRequest = this.prepareSubmitClaimInfoRequest();
        jsonState.isSubmitting = true;
        if (document.getElementById('fatca_id')) {
            document.getElementById('fatca_id').disabled = true;
        }
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        this.setState(jsonState);
        const DOB = this.state.selectedCliInfo.dOB;
        if (getSession(ONLY_PAYMENT + this.state.selectedCliInfo.clientId)) {
            removeSession(ONLY_PAYMENT + this.state.selectedCliInfo.clientId);
        }

        let Exception = null;
        let failMessage = null;
        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while (!isSuccess && remainRetryTimes > 0) {
            await saveClaimRequest(submitClaimInfoRequest).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    isSuccess = true;
                    let claimID = Res.data?.[0]?.claimId;
                    jsonState.claimId = claimID;
                    jsonState.trackingId = Res.data?.[0]?.trackingId;
                    let submitClaimImageRequestList = this.prepareSubmitClaimImageRequestList(claimID, submitClaimInfoRequest);
                    this.sequentiallyPostImage(submitClaimImageRequestList, jsonState, status, claimID, Res.data?.[0]?.trackingId);

                } else {
                    jsonState.isSubmitting = false;
                    setComponentState(jsonState);
                    this.onClickConfirmCloseButton();
                }
            }).catch(error => {
                failMessage = error.message;
                Exception = error;
                jsonState.isSubmitting = false;
                this.setState(jsonState);
                this.onClickConfirmCloseButton();
                jsonState.hasError = true;
                this.setState(jsonState);
            }).finally(() => {
                remainRetryTimes--;
                this.setState({ isSubmitting: false });
                // success or after retry ${NUM_OF_RETRY} times.
                const isWritingLog = isSuccess || remainRetryTimes <= 0;
                if (isWritingLog) {
                    console.log('Writing log submitClaimInfo')
                    if (isSuccess) {
                        cpSaveLog("Web_Succ_SubmitClaimData", `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`, !isEmpty(Exception) ? Exception : '');
                    } else {
                        cpSaveLog("Web_Err_SubmitClaimData", `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`, !isEmpty(Exception) ? Exception : '');
                    }
                }

            });
        }
    }

    async submitClaimInfoConfirm(data, onlyPO) {
        if (this.state.isSubmitting) {
            return;
        }

        const jsonState = this.state;
        const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const submitClaimInfoRequest = this.prepareSubmitClaimInfoRequest();
        jsonState.isSubmitting = true;
        if (document.getElementById('fatca_id')) {
            document.getElementById('fatca_id').disabled = true;
        }
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        this.setState(jsonState);
        const DOB = this.state.selectedCliInfo.dOB;
        // if (getSession(ONLY_PAYMENT + this.state.selectedCliInfo.clientId)) {
        //     removeSession(ONLY_PAYMENT + this.state.selectedCliInfo.clientId);
        // }

        let Exception = null;
        let failMessage = null;
        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while (!isSuccess && remainRetryTimes > 0) {
            await saveClaimRequest(submitClaimInfoRequest).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    isSuccess = true;
                    let claimID = Res.data?.[0]?.claimId;
                    jsonState.claimId = claimID;
                    jsonState.trackingId = Res.data?.[0]?.trackingId;
                    this.setState({ trackingId: Res.data?.[0]?.trackingId });
                    let submitClaimImageRequestList = this.prepareSubmitClaimImageRequestList(claimID, submitClaimInfoRequest);
                    this.sequentiallyPostImageCornfirm(submitClaimImageRequestList, jsonState, claimID, Res.data?.[0]?.trackingId, data, onlyPO);

                } else {
                    jsonState.isSubmitting = false;
                    setComponentState(jsonState);
                    this.onClickConfirmCloseButton();
                }
            }).catch(error => {
                failMessage = error.message;
                Exception = error;
                jsonState.isSubmitting = false;
                this.setState(jsonState);
                this.onClickConfirmCloseButton();
                jsonState.hasError = true;
                this.setState(jsonState);
            }).finally(() => {
                remainRetryTimes--;
                // success or after retry ${NUM_OF_RETRY} times.
                const isWritingLog = isSuccess || remainRetryTimes <= 0;
                if (isWritingLog) {
                    console.log('Writing log submitClaimInfo')
                    if (isSuccess) {
                        cpSaveLog("Web_Succ_SubmitClaimData", `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`, !isEmpty(Exception) ? Exception : '');
                    } else {
                        cpSaveLog("Web_Err_SubmitClaimData", `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`, !isEmpty(Exception) ? Exception : '');
                    }
                }

            });
        }
    }

    updateTrackingIdAndSubmitCPConsent = (data, trackingId, onlyPO) => {
        if (isEmpty(data)) {
            return data;
        }
        let result = [];
        for (let i = 0; i < data.length; i++) {
            let d = data[i];
            if (d.TrackingID) {
                d.TrackingID = trackingId;
                result.push(d);
            }
        }
        if (!onlyPO) {
            this.updateClaimRequest(SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
        }
        this.fetchCPConsentConfirmationFetch(result, trackingId, onlyPO);
    }

    submitCPConsentConfirmationLIConfirm(data, onlyPO) {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);
        const setComponentState = this.setState.bind(this);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId || getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: this.state.processType,
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
                    if (!onlyPO) {
                        jsonState.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                        let nd13State = jsonState.nd13State;
                        if (nd13State && nd13State?.updateInfoState) {
                            nd13State.updateInfoState = ND_13.ND13_INFO_FOLLOW_CONFIRMATION;
                        }
                        if ((this.state.systemGroupPermission?.[0].Role === SDK_ROLE_PO) && (!this.state.disableEdit || this.state.agentKeyInPOSelfEdit)) {
                            if (this.state.disableEdit) {
                                this.updateClaimRequest(SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN, SDK_REVIEW_PROCCESS, "");
                            } else {
                                this.updateClaimRequest(SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN, SDK_ROLE_PO, "");
                            }
                        }
                        
                        // console.log('savexxxx=',JSON.stringify(jsonState));
                        // this.saveND13DataTemp(jsonState.clientId, jsonState.trackingId, JSON.stringify(nd13State), jsonState.apiToken, jsonState.deviceId);
                    } else {
                        jsonState.isSubmitting = false;
                        jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
                        setComponentState(jsonState);
                        // alertSucceeded();
                        this.confirmCPConsent();
                        this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);

                    }
                    this.setState(jsonState);
                } else if (Response.ErrLog === 'SubmitFailed') {
                    this.fetchCPConsentConfirmation(jsonState.trackingId, jsonState.clientId, jsonState.clientListStr, jsonState.proccessType, jsonState.apiToken, jsonState.deviceId);
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

    submitCPConsentConfirmationPOConfirmOnly(data) {
        const jsonState = this.state;
        const setComponentState = this.setState.bind(this);
        jsonState.isSubmitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: this.state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: this.state.clientId || getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: this.state.processType,
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
                    jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
                    setComponentState(jsonState);
                    // alertSucceeded();
                    this.confirmCPConsent();
                    this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                    removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
                    this.setState(jsonState);
                } else if (Response.ErrLog === 'SubmitFailed') {
                    this.fetchCPConsentConfirmation(jsonState.trackingId, jsonState.clientId, jsonState.clientListStr, jsonState.proccessType, jsonState.apiToken, jsonState.deviceId);
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

    async openPopupSucceeded() {
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        this.setState({ popupConfirmClaimCalled: true });
        if (this.state.pinStep) {
            return;
        }
        if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && !this.state.disableEdit) {
            if (this.state.poConfirmingND13 === '1') {
                this.submitClaimInfo('PO_CONFIRMING_DECREE_13');
            } else {
                this.submitClaimInfo();
            }
        } else {
            if (haveCheckedDeadth(this.state.claimCheckedMap) || this.state.consentDisabled) {
                if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT)) {
                    this.updateCloneClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
                    // this.setSendPOSuccess();
                } else if (this.state.agentKeyInPOSelfEdit) {
                    this.submitClaimInfo();
                }
            } else if ((this.state.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList) && ((this.state.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(DOB)) || (!isOlderThan18(DOB) && !haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList)))) {
                // if ((!haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList) && ((this.state.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(DOB)) || (!isOlderThan18(DOB) && !haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList)))) {
                //     this.setHaveShowPayment(true);
                // }
                this.goNextStep();
                // if (this.state.poConfirmingND13 === '1') {
                //     this.goNextStep();
                // } else {
                //     this.setSendPOSuccess();
                //     this.updateClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
                // }
            } else {
                if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && !this.state.disableEdit) {
                    this.updateCloneClaimRequest(SDK_REQUEST_STATUS.POREVIEW, SDK_REVIEW_PROCCESS, "");//Truyền status empty sẽ keep current status
                    // this.setSendPOSuccess();
                } else if ((this.state.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.state.disableEdit && this.state.agentKeyInPOSelfEdit) {
                    this.submitClaimInfo();
                }
            }
        }
    }

    setSendPOSuccess = () => {
        this.setState({ sendPOSuccess: true });
    }

    closeSendPOSuccess = () => {
        this.setState({ sendPOSuccess: false });
        this.closeToHome();
    }

    closePopupSucceeded(event) {
        if ((this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) || (this.closeSucceededButtonRef && this.closeSucceededButtonRef.contains(event.target))) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup";
            document.removeEventListener('mousedown', this.handlerClosePopupSucceeded);

            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
            this.closeToHome();
        }
    }

    closePopupSucceededRedirect(event) {
        // this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
        window.location.href = '/followup-claim-info';
        this.handlerGoToStep(CLAIM_STATE.DONE);
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
    }

    prepareSubmitClaimImageRequestList(claimID, submitClaimInfoRequest) {
        let jsonDataInput = {};
        jsonDataInput['claimId'] = claimID;
        console.log(submitClaimInfoRequest?.data.claimType + '|' + submitClaimInfoRequest?.data.claimId)
        let claimType = submitClaimInfoRequest.data.claimType;
        jsonDataInput['claimType'] = claimType;
        if (this.state.requestId) {
            jsonDataInput['claimId'] = this.state.requestId;
        }
        jsonDataInput['customerId'] = this.state.clientId || getSession(CLIENT_ID);
        let docProcId = '';
        if ((claimType.indexOf(CLAIM_TYPE.HEALTH_CARE) >= 0) || (claimType.indexOf(CLAIM_TYPE.HS) >= 0)) {
            docProcId = 'CHN';
        } else {
            docProcId = 'CLN';
        }
        const result = [];
        const attachmentState = this.state.attachmentState;

        attachmentState.attachmentData.forEach((docType) => {
            let req = JSON.parse(JSON.stringify(jsonDataInput));
            let jData = req;
            jData['docProcessId'] = docProcId;
            jData['docTypeId'] = docType.DocID;
            jData['docTypeName'] = docType.DocTypeName;

            docType.attachmentList.forEach((attachment, attIdx) => {
                let tReq = JSON.parse(JSON.stringify(jData));
                let tJData = tReq;
                const matches = attachment.imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
                tJData['numberOfPage'] = attIdx + '';
                tJData['docNumber'] = attIdx + '';
                tJData['imageEncode'] = matches.length === 3 ? matches[2] : '';

                let metadata = {
                    clientKey: AUTHENTICATION,
                    deviceId: getDeviceId(),
                    operationSystem: getOperatingSystem(),
                    operatingSystem: getOperatingSystem(),
                    operatingSystemVersion: getOSVersion(),
                    platform: WEB_BROWSER_VERSION,
                    system: this.state.sourceSystem || SYSTEM_DCONNECT,
                    signature: getSession(SIGNATURE) || this.state.signature,
                    accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
                }
                const request = buildMicroRequest(metadata, tJData);
                result.push(request);
            });
        });
        console.log('result=', result);
        return result;
    }

    alertSucceeded() {
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        if (document.getElementById("popupSucceeded")) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup show";
        }

        const jsonState = this.state;
        jsonState.popupSucceededCalled = true;
        // jsonState.popupConfirmClaimCalled = false;
        this.setState(jsonState);
        document.addEventListener('mousedown', this.handlerClosePopupSucceeded);
    }

    prepareSubmitClaimInfoRequest() {
        const jsonDataInput = {};
        const selectedCliInfo = this.state.selectedCliInfo;
        const initClaimState = this.state.initClaimState;
        const claimTypeState = this.state.claimTypeState;
        const claimDetailState = this.state.claimDetailState;
        const sickInfo = claimDetailState.sickInfo;
        const contactPersonInfo = this.state.contactState.contactPersonInfo;
        const paymentMethodState = this.state.paymentMethodState;
        const fatcaState = this.state.submissionState.fatcaState;
        const claimCheckedMap = this.state.claimCheckedMap;

        // jsonDataInput['Action'] = "ClaimForm_Requester";

        //remove if else to fix issue miss LIFullname, birthday, CCCD
        jsonDataInput['fullName'] = checkValue(getSession(FULL_NAME));
        jsonDataInput['idNum'] = checkValue(getSession(POID));
        jsonDataInput['address'] = checkValue(getSession(ADDRESS));
        jsonDataInput['cellPhone'] = checkValue(getSession(CELL_PHONE));
        jsonDataInput['email'] = checkValue(getSession(EMAIL));

        jsonDataInput['liFullName'] = checkValue(selectedCliInfo.fullName);
        jsonDataInput['liClientId'] = checkValue(selectedCliInfo.clientId);
        jsonDataInput['lifeInsuredId'] = checkValue(selectedCliInfo.clientId);
        jsonDataInput['liBirthDate'] = checkValue(selectedCliInfo.dOB);
        jsonDataInput['liIDNum'] = checkValue(selectedCliInfo.idNum);
        jsonDataInput['liGender'] = checkValue(selectedCliInfo.gender);


        jsonDataInput['occupation'] = checkValue(initClaimState.occupation);

        let claType = '';
        let lstBenefit = [];
        console.log('...claimCheckedMap=', claimCheckedMap);
        for (const it in claimCheckedMap) {
            if (claimCheckedMap[it]) {
                let claimBenifit = this.state.claimTypeList.filter(item => item.claimType === it);
                console.log('...it=', it);
                if (claimBenifit) {
                    lstBenefit.push(claimBenifit[0]);
                }
                if (!claType) {
                    claType = it;
                } else {
                    claType = claType + ';' + it;
                }
                console.log('...lstBenefit=', lstBenefit);
            }

        }

        jsonDataInput['benefitList'] = lstBenefit;

        let reason = '';
        if (claimCheckedMap[CLAIM_TYPE.DEATH] && (claimTypeState.isAccidentClaim !== null)) {
            if (claimTypeState.isAccidentClaim) {
                reason = ';DeadOfAccident';
            } else {
                reason = ';DeadOfIllness';
            }
        }
        jsonDataInput['claimReasonChoice'] = claType + reason;
        jsonDataInput['claimSubType'] = claimTypeState.isAccidentClaim ? 'Accident' : '';
        jsonDataInput['claimType'] = claType;
        let docProcId = '';
        if ((claType.indexOf(CLAIM_TYPE.HEALTH_CARE) >= 0) || (claType.indexOf(CLAIM_TYPE.HS) >= 0)) {
            docProcId = 'CHN';
        } else {
            docProcId = 'CLN';
        }
        jsonDataInput['docProcessId'] = docProcId;
        if (claimCheckedMap[CLAIM_TYPE.ILLNESS] || (claimTypeState.isAccidentClaim === false)) {
            let hosCode = '';
            let hos = this.state.hospitalList.filter(result => result.HospitalName === sickInfo.sickFoundFacility);
            if (hos && hos.length > 0) {
                hosCode = hos[0].ProviderCode;
            }
            if (hosCode) {
                hosCode = hosCode + " | ";
            }
            jsonDataInput['illnessProgression'] = checkValue(claimDetailState.sickInfo.firstSympton);
            jsonDataInput['illnessDate'] = checkValue(claimDetailState.sickInfo.sickFoundTime);
            jsonDataInput['illnessPlace'] = claimDetailState.sickInfo.sickFoundFacility ? (hosCode + claimDetailState.sickInfo.sickFoundFacility + " (" + checkValue(claimDetailState.sickInfo.cityName) + ")") : '';
        }


        let treatmentList = [];
        if (claimDetailState.isTreatmentAt) {
            claimDetailState.facilityList.forEach(facility => {
                let icdListTemp = undefined;
                let icdList = [];
                if (facility.diagnosis && facility.diagnosis.length > 0) {
                    for (let d of facility.diagnosis) {
                        icdListTemp = this.state.hospitalResultList.filter(result => result.ICDName === d);
                        if (icdListTemp && (icdListTemp.length > 0)) {
                            //start tạm convert chu hoa thường trong khi chua co api
                            let icdItem = icdListTemp[0];
                            if (icdItem) {
                                let icdItemStr = JSON.stringify(icdItem).replaceAll('ICDID', 'icdId').replaceAll('ICDCode', 'icdCode').replaceAll('ICDName', 'icdName').replaceAll('ICDNameSearch', 'icdNameSearch')
                                icdItem = JSON.parse(icdItemStr);
                            }
                            //end
                            icdList.push(icdItem);
                        }

                    }
                }

                let hospitalCode = '';
                let hospital = this.state.hospitalList.filter(result => result.HospitalName === facility.selectedHospital);
                if (hospital && hospital.length > 0) {
                    hospitalCode = hospital[0].ProviderCode;
                }

                treatmentList.push({
                    strProvince: checkValue(facility.cityName),
                    strDistrict: checkValue(facility.districtName),
                    taxInvoiceList: facility.invoiceList,
                    icdList: icdList,
                    treatmentHospital: checkValue(facility.selectedHospital),
                    treatmentHospitalCode: hospitalCode,
                    patientType: facility.treatmentType ? TREAMENT_TYPE[facility.treatmentType].submitType : '',
                    treatmentDateFrom: checkValue(facility.startDate ? moment(facility.startDate).format('YYYY-MM-DDT00:00:00.000[Z]') : ""),
                    treatmentDateTo: checkValue(facility.endDate ? moment(facility.endDate).format('YYYY-MM-DDT00:00:00.000[Z]') : ""),
                    diagnostic: checkValue(facility.diagnosis).toString(),
                    treatmentAmount: sumInvoiceNew(facility.invoiceList),
                    treatmentType: claimTypeState.isAccidentClaim ? 'AccidentProgression' : 'IllnessProgression',
                    otherInsurance: facility.otherCompanyInfo.companyName,
                    otherInsuranceAmount: facility.otherCompanyInfo.paidAmount
                })
            });
        }
        jsonDataInput['treatmentHistorys'] = treatmentList;

        jsonDataInput['accidentDate'] = (claimDetailState.accidentInfo.date ? convertDateToISO(claimDetailState.accidentInfo.date) + ' ' : '') + (claimDetailState.accidentInfo.hour ? moment(claimDetailState.accidentInfo.hour).format("HH:mm") : '');
        if (jsonDataInput['accidentDate']) {
            jsonDataInput['accidentNational'] = claimDetailState.accidentInfo.selectedNation ? claimDetailState.accidentInfo.selectedNation : 'Việt Nam';
        } else {
            jsonDataInput['accidentNational'] = '';
        }
        jsonDataInput['accidentProvince'] = checkValue(claimDetailState.accidentInfo.cityName);
        jsonDataInput['accidentDistrict'] = checkValue(claimDetailState.accidentInfo.districtName);
        jsonDataInput['accidentPlace'] = checkValue(claimDetailState.accidentInfo.address);

        jsonDataInput['accidentReason'] = checkValue(claimDetailState.accidentInfo.accidentDescription);
        jsonDataInput['accidentIllnessDesc'] = checkValue(claimDetailState.accidentInfo.healthStatus);
        jsonDataInput['lossDate'] = (claimDetailState.deathInfo.date ? convertDateToISO(claimDetailState.deathInfo.date) + ' ' : '') + (claimDetailState.deathInfo.hour ? moment(claimDetailState.deathInfo.hour).format("HH:mm") : '');
        jsonDataInput['lossProvince'] = checkValue(claimDetailState.deathInfo.cityName);
        jsonDataInput['lossDistrict'] = checkValue(claimDetailState.deathInfo.districtName);
        jsonDataInput['lossPlace'] = checkValue(claimDetailState.deathInfo.address);
        jsonDataInput['lossProgression'] = checkValue(claimDetailState.deathInfo.deathDescription);

        let relationShip = "";
        let relationShipCode = "";
        let role = contactPersonInfo.role;
        if (!role) {
            role = paymentMethodState.choseReceiver;
        }
        if (!role) {
            role = 'PO';
        }
        if (claimCheckedMap[CLAIM_TYPE.DEATH]) {
            relationShip = checkValue(contactPersonInfo.poRelation);
            relationShipCode = checkValue(contactPersonInfo.poRelationCode);
            role = "OTHER";

        }
        let contactDeath = {
            'fullName': checkValue(contactPersonInfo.fullName),
            'idNum': checkValue(contactPersonInfo.pin),
            'dob': contactPersonInfo.dob ? convertDateToUSFormat(contactPersonInfo.dob) : '',
            'email': checkValue(contactPersonInfo.email),
            'phone': checkValue(contactPersonInfo.phone),
            'relationShip': relationShip,
            'relationShipCode': relationShipCode,
            'role': role,
            'address': checkValue(contactPersonInfo.address),
        }
        jsonDataInput['contactClaim'] = contactDeath;


        // if (claimDetailState.isOtherCompanyRelated === "no") {
        //     jsonDataInput['OthersInsurance'] = [];
        // } else {
        //     let othersInsuranceList = [];
        //     // claimDetailState.otherCompanyInfoList.forEach(otherCompanyInfo => {
        //     othersInsuranceList.push({
        //         OtherInsurance: checkValue(claimDetailState.otherCompanyInfo.companyName),
        //         PolicyInsurance: checkValue(claimDetailState.otherCompanyInfo.productName),
        //         ProductInsurance: checkValue(claimDetailState.otherCompanyInfo.policyNo),
        //     });
        //     // });
        //     jsonDataInput['OthersInsurance'] = othersInsuranceList;
        // }

        if (haveHC_HS(claimCheckedMap, this.state.claimTypeList)) {
            //Mượn field của life để mang thông tin HC trong TH LI < 18, PO, DEATH
            if (!is18Plus(this.state.selectedCliInfo.dOB) || this.state.claimTypeState.isSamePoLi || claimCheckedMap[CLAIM_TYPE.DEATH]) {
                if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                    let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                    jsonDataInput['hcPaymentMethodId'] = "P1";
                    jsonDataInput['hcPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                    jsonDataInput['hcBankProvince'] = checkValue(cashTypeState.cityName);
                    jsonDataInput['hcBankCode'] = checkValue(cashTypeState.cityCode);
                    jsonDataInput['hcAccountName'] = checkValue(cashTypeState.receiverName);
                    jsonDataInput['hcPayIssueId'] = checkValue(cashTypeState.receiverPin);
                    jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? convertDateToISO(cashTypeState.receiverPinDate) : '';
                    jsonDataInput['hcPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                    jsonDataInput['hcBankName'] = checkValue(cashTypeState.bankName);
                    jsonDataInput['hcBankBranch'] = checkValue(cashTypeState.bankBranchName);
                    jsonDataInput['hcBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                    // jsonDataInput['tinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                } else {
                    let transferTypeState = paymentMethodState.lifeBenState.transferTypeState;
                    if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                        jsonDataInput['hcPaymentMethodId'] = "P2";
                        jsonDataInput['hcPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                        jsonDataInput['hcBankProvince'] = checkValue(transferTypeState.cityName);
                        jsonDataInput['hcBankCode'] = checkValue(transferTypeState.cityCode);
                        jsonDataInput['hcAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                        jsonDataInput['hcAccountName'] = checkValue(transferTypeState.bankAccountName);
                        jsonDataInput['hcBankName'] = checkValue(transferTypeState.bankName);
                        jsonDataInput['hcBankBranch'] = checkValue(transferTypeState.bankBranchName);
                        jsonDataInput['hcBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                    } else {
                        if (paymentMethodState.healthCareBenState && paymentMethodState.healthCareBenState.paymentMethodId === 'P1') {
                            let cashTypeState = paymentMethodState.healthCareBenState.cashTypeState;
                            jsonDataInput['hcPaymentMethodId'] = "P1";
                            jsonDataInput['hcPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                            jsonDataInput['hcBankProvince'] = checkValue(cashTypeState.cityName);
                            jsonDataInput['hcBankCode'] = checkValue(cashTypeState.cityCode);
                            jsonDataInput['hcAccountName'] = checkValue(cashTypeState.receiverName);
                            jsonDataInput['hcPayIssueId'] = checkValue(cashTypeState.receiverPin);
                            jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? convertDateToISO(cashTypeState.receiverPinDate) : '';
                            jsonDataInput['hcPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                            jsonDataInput['hcBankName'] = checkValue(cashTypeState.bankName);
                            jsonDataInput['hcBankBranch'] = checkValue(cashTypeState.bankBranchName);
                            jsonDataInput['hcBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                            // jsonDataInput['tinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                        } else {
                            let transferTypeState = paymentMethodState.healthCareBenState.transferTypeState;
                            if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                                jsonDataInput['hcPaymentMethodId'] = "P2";
                                jsonDataInput['hcPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                                jsonDataInput['hcBankProvince'] = checkValue(transferTypeState.cityName);
                                jsonDataInput['hcBankCode'] = checkValue(transferTypeState.cityCode);
                                jsonDataInput['hcAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                                jsonDataInput['hcAccountName'] = checkValue(transferTypeState.bankAccountName);
                                jsonDataInput['hcBankName'] = checkValue(transferTypeState.bankName);
                                jsonDataInput['hcBankBranch'] = checkValue(transferTypeState.bankBranchName);
                                jsonDataInput['hcBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                            }
                        }


                    }
                }
            } else {
                if (paymentMethodState.healthCareBenState && paymentMethodState.healthCareBenState.paymentMethodId === 'P1') {
                    let cashTypeState = paymentMethodState.healthCareBenState.cashTypeState;
                    jsonDataInput['hcPaymentMethodId'] = "P1";
                    jsonDataInput['hcPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                    jsonDataInput['hcBankProvince'] = checkValue(cashTypeState.cityName);
                    jsonDataInput['hcBankCode'] = checkValue(cashTypeState.cityCode);
                    jsonDataInput['hcAccountName'] = checkValue(cashTypeState.receiverName);
                    jsonDataInput['hcPayIssueId'] = checkValue(cashTypeState.receiverPin);
                    jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? convertDateToISO(cashTypeState.receiverPinDate) : '';
                    jsonDataInput['hcPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                    jsonDataInput['hcBankName'] = checkValue(cashTypeState.bankName);
                    jsonDataInput['hcBankBranch'] = checkValue(cashTypeState.bankBranchName);
                    jsonDataInput['hcBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                    // jsonDataInput['tinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                } else {
                    let transferTypeState = paymentMethodState.healthCareBenState.transferTypeState;
                    if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                        jsonDataInput['hcPaymentMethodId'] = "P2";
                        jsonDataInput['hcPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                        jsonDataInput['hcBankProvince'] = checkValue(transferTypeState.cityName);
                        jsonDataInput['hcBankCode'] = checkValue(transferTypeState.cityCode);
                        jsonDataInput['hcAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                        jsonDataInput['hcAccountName'] = checkValue(transferTypeState.bankAccountName);
                        jsonDataInput['hcBankName'] = checkValue(transferTypeState.bankName);
                        jsonDataInput['hcBankBranch'] = checkValue(transferTypeState.bankBranchName);
                        jsonDataInput['hcBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                    }
                }
            }

        }

        if (claimCheckedMap[CLAIM_TYPE.TPD] || claimCheckedMap[CLAIM_TYPE.ILLNESS] || claimCheckedMap[CLAIM_TYPE.ACCIDENT] || claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(claimCheckedMap, this.state.claimTypeList)) {
            if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                jsonDataInput['paymentMethodId'] = "P1";
                jsonDataInput['paymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                jsonDataInput['bankProvince'] = checkValue(cashTypeState.cityName);
                jsonDataInput['accountName'] = checkValue(cashTypeState.receiverName);
                jsonDataInput['payIssueId'] = checkValue(cashTypeState.receiverPin);
                jsonDataInput['payDateIssue'] = cashTypeState.receiverPinDate ? convertDateToISO(cashTypeState.receiverPinDate) : '';
                jsonDataInput['payPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                jsonDataInput['bankName'] = checkValue(cashTypeState.bankName);
                jsonDataInput['bankBranch'] = checkValue(cashTypeState.bankBranchName);
                jsonDataInput['bankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                // jsonDataInput['tinhThanhCMND'] = checkValue(cashTypeState.cityName);
            } else {
                let transferTypeState = paymentMethodState.lifeBenState.transferTypeState;
                if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                    jsonDataInput['paymentMethodId'] = "P2";
                    jsonDataInput['paymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                    jsonDataInput['bankProvince'] = checkValue(transferTypeState.cityName);
                    jsonDataInput['accountNumber'] = checkValue(transferTypeState.bankAccountNo);
                    jsonDataInput['accountName'] = checkValue(transferTypeState.bankAccountName);
                    jsonDataInput['bankName'] = checkValue(transferTypeState.bankName);
                    jsonDataInput['bankBranch'] = checkValue(transferTypeState.bankBranchName);
                    jsonDataInput['bankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                }
            }
        }

        jsonDataInput['poNational'] = fatcaState.isAmericanNationality ? "Hoa Kỳ" : "Việt Nam";
        jsonDataInput['isAddressAmerican'] = fatcaState.isAmericanResidence ? "1" : "0";
        jsonDataInput['isTaxAmerican'] = fatcaState.isAmericanTax ? "1" : "0";
        // if (this.state.claimId) {
        //     jsonDataInput['oldClaimId'] = this.state.claimId;
        // }
        if (this.state.requestId) {
            jsonDataInput['claimId'] = this.state.requestId;
        }

        if (this.state.trackingId) {
            jsonDataInput['trackingId'] = this.state.trackingId;
        }
        // alert(this.state.clientId + '|' + getSession(CLIENT_ID));
        jsonDataInput['customerId'] = this.state.clientId || getSession(CLIENT_ID);
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        const request = buildMicroRequest(metadata, jsonDataInput);
        return request;
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    setCloseSucceededButtonRef(node) {
        this.closeSucceededButtonRef = node;
    }

    async sequentiallyPostImage(submitClaimImageRequestList, jsonState, status, claimId, trackingId) {
        // const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const DOB = this.state.selectedCliInfo.dOB;
        this._countImgSuccess = 0;
        let count = 0;
        let reasonFail = null;
        let Exception = null;
        const TRUE = 'true';
        let requestTimes = NUM_OF_RETRY + 1;
        let totalImage = 0;
        if (submitClaimImageRequestList) {
            totalImage = submitClaimImageRequestList.length
        }
        for (let i = 0; i < submitClaimImageRequestList.length; i++) {
            requestTimes = NUM_OF_RETRY + 1;// first request + num_of_retry.
            let isSuccess = false;
            while (requestTimes > 0 && !isSuccess) {// while loop will break if request times = 0 or request has been successed.
                // start action request
                try {
                    const response = await saveImageClaim(submitClaimImageRequestList[i]);
                    console.log(`sequentiallyPostImage[${i}]- remain retry times: ${requestTimes}`, response);
                    if ((response.code === CODE_SUCCESS) && response.data) {
                        count++;
                        isSuccess = true;
                    }
                    else {
                        reasonFail = response.message;
                    }
                } catch (err) {
                    Exception = err;
                    reasonFail = err.message;
                }
                if (isSuccess) {
                    // this.cpSaveLog({functionName:"Web_Succ_SubmitClaimImage"
                    //     , Description: `Status: Success, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY + 1 - requestTimes}`
                    //     , Exception:reasonFail});
                } else {
                    cpSaveLog("Web_Err_SubmitClaimImage"
                        , `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY + 1 - requestTimes}`
                        , reasonFail ? reasonFail : ''
                    );
                }
                requestTimes--; // remain retry times.
                // end action request 
            }
        }
        this._countImgSuccess = count;

        // write log.
        const isSuccess = count === totalImage;
        console.log('sequentiallyPostImage...............');
        if (isSuccess) {
            cpSaveLog("Web_Succ_SubmitClaimImage"
                , `Status: Success, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , reasonFail ? reasonFail : ''
            );
        } else {
            cpSaveLog("Web_Err_SubmitClaimImage"
                , `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , reasonFail ? reasonFail : ''
            );
        }

        console.log('.... _countImgSuccess=', this._countImgSuccess);
        if (this._countImgSuccess === 0) {
            this.setState({ allImageFail: true });
        } else {
            if (haveCheckedDeadth(this.state.claimCheckedMap) || this.state.consentDisabled) {
                jsonState.isSubmitting = false;
                jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
                jsonState.trackingId = trackingId;
                setComponentState(jsonState);
                // alertSucceeded();
                this.confirmCPConsent();
                this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
            } else if ((status === 'PO_CONFIRMING_DECREE_13') || (!haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList) && ((this.state.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(DOB)) || (!isOlderThan18(DOB) && !haveCheckedDeadth(this.state.claimCheckedMap) && haveHC_HS(this.state.claimCheckedMap, this.state.claimTypeList)))) {
                jsonState.isSubmitting = false;
                jsonState.trackingId = trackingId;
                setComponentState(jsonState);
                if ((this.state.systemGroupPermission?.[0].Role === SDK_ROLE_PO) && (!this.state.disableEdit || this.state.agentKeyInPOSelfEdit)) {
                    if (this.state.disableEdit) {
                        this.updateClaimRequest(SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN, SDK_REVIEW_PROCCESS, "");
                    } else {
                        this.updateClaimRequest(SDK_REQUEST_STATUS.WAITINGCONFIRMDLCN, SDK_ROLE_PO, "");
                    }
                }
                this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.SDK_DLCN));
                //rem this
                // this.fetchCPConsentConfirmation(trackingId);

            } else {
                jsonState.isSubmitting = false;
                jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
                jsonState.TrackingID = trackingId;
                setComponentState(jsonState);
                // alertSucceeded();
                this.confirmCPConsent();
                this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
            }
            this.onClickConfirmCloseButton();
        }
    }

    async sequentiallyPostImageCornfirm(submitClaimImageRequestList, jsonState, claimId, trackingId, data, onlyPO) {
        // const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const DOB = this.state.selectedCliInfo.dOB;
        this._countImgSuccess = 0;
        let count = 0;
        let reasonFail = null;
        let Exception = null;
        const TRUE = 'true';
        let requestTimes = NUM_OF_RETRY + 1;
        let totalImage = 0;
        if (submitClaimImageRequestList) {
            totalImage = submitClaimImageRequestList.length
        }
        for (let i = 0; i < submitClaimImageRequestList.length; i++) {
            requestTimes = NUM_OF_RETRY + 1;// first request + num_of_retry.
            let isSuccess = false;
            while (requestTimes > 0 && !isSuccess) {// while loop will break if request times = 0 or request has been successed.
                // start action request
                try {
                    const response = await saveImageClaim(submitClaimImageRequestList[i]);
                    console.log(`sequentiallyPostImage[${i}]- remain retry times: ${requestTimes}`, response);
                    if ((response.code === CODE_SUCCESS) && response.data) {
                        count++;
                        isSuccess = true;
                    }
                    else {
                        reasonFail = response.message;
                    }
                } catch (err) {
                    Exception = err;
                    reasonFail = err.message;
                }
                if (isSuccess) {
                    // this.cpSaveLog({functionName:"Web_Succ_SubmitClaimImage"
                    //     , Description: `Status: Success, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY + 1 - requestTimes}`
                    //     , Exception:reasonFail});
                } else {
                    cpSaveLog("Web_Err_SubmitClaimImage"
                        , `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY + 1 - requestTimes}`
                        , reasonFail ? reasonFail : ''
                    );
                }
                requestTimes--; // remain retry times.
                // end action request 
            }
        }
        this._countImgSuccess = count;

        // write log.
        const isSuccess = count === totalImage;
        console.log('sequentiallyPostImage...............');
        if (isSuccess) {
            this.setState({ claimId: claimId, trackingId: trackingId });
            this.updateTrackingIdAndSubmitCPConsent(data, trackingId, onlyPO);
            // if (!isEmpty(data)) {
            //     this.submitCPConsentConfirmationLIConfirm(data);
            // }
            cpSaveLog("Web_Succ_SubmitClaimImage"
                , `Status: Success, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , reasonFail ? reasonFail : ''
            );
        } else {
            cpSaveLog("Web_Err_SubmitClaimImage"
                , `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , reasonFail ? reasonFail : ''
            );
        }

        console.log('.... _countImgSuccess=', this._countImgSuccess);
        if (this._countImgSuccess === 0) {
            this.setState({ allImageFail: true });
        }
    }

    doneClaim() {
        let jsonState = this.state;
        const setComponentState = this.setState.bind(this);
        jsonState.isSubmitting = false;
        jsonState.claimSubmissionState = this.handlerGetStep(CLAIM_STEPCODE.INIT);
        setComponentState(jsonState);
        // alertSucceeded();
        this.confirmCPConsent();
        // this.handlerGoToStep(this.handlerGetStep(CLAIM_STEPCODE.DONE));
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliInfo?.clientId);
    }
    // async confirmCPConsent() {
    //     const jsonState = this.state;

    //     jsonState.isSubmitting = true;
    //     this.setState(jsonState);
    //     let failMessage = null;
    //     let Exception = null;

    //     const request = {
    //         jsonDataInput: {
    //             Action: "ConfirmClaim",
    //             APIToken: getSession(ACCESS_TOKEN),
    //             Authentication: AUTHENTICATION,
    //             claimId: this.state.claimId,
    //             TrackingID: this.state.TrackingID,
    //             Company: COMPANY_KEY,
    //             DeviceId: getDeviceId(),
    //             OS: WEB_BROWSER_VERSION,
    //             Project: "mcp",
    //             UserLogin: getSession(USER_LOGIN),
    //         }
    //     };

    //     const TRUE = 'true';
    //     let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
    //     let isSuccess = false;
    //     while (!isSuccess && remainRetryTimes > 0) {
    //         await postClaimInfo(request).then(res => {
    //             const Response = res.Response;
    //             if (Response.Result === TRUE && Response.Message !== null) {
    //                 console.log('confirm claim success: ', Response.Message);
    //                 isSuccess = true;
    //             }
    //             else {
    //                 failMessage = res.Response.ErrLog;
    //             }
    //         }).catch(error => {
    //             console.log(error);
    //             Exception = error;
    //             failMessage = error.message;
    //         }).finally(() => {
    //             remainRetryTimes--;
    //             jsonState.isSubmitting = false;
    //             this.setState(jsonState);

    //             // success or after retry ${NUM_OF_RETRY} times.
    //             const isWritingLog = isSuccess || remainRetryTimes <= 0;
    //             if (isWritingLog) {
    //                 console.log('Writing log confirmCPConsent')
    //                 if (isSuccess) {
    //                     cpSaveLog("Web_Succ_ConfirmClaimData",
    //                         `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
    //                         Exception? Exception: ''
    //                     );
    //                 } else {
    //                     cpSaveLog("Web_Err_ConfirmClaimData",
    //                         `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
    //                         Exception? Exception: ''
    //                     );
    //                 }
    //             }

    //         });
    //     }
    //     if (getSession(ONLY_PAYMENT + this.state.selectedCliInfo?.clientId)) {
    //         removeSession(ONLY_PAYMENT + this.state.selectedCliInfo?.clientId);
    //     }
    // }

    async confirmCPConsent() {
        this.setState({ isSubmitting: true });
        let failMessage = null;
        let Exception = null;
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            claimId: this.state.claimId || this.state.requestId,
            customerId: this.state.clientId,
            trackingId: this.state.trackingId
        }

        let request = buildMicroRequest(metadata, data);

        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while (!isSuccess && remainRetryTimes > 0) {
            await completeClaimRequest(request).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data && Res.data?.[0]?.claimId) {
                    console.log('confirm claim success: ', Res.message);
                    isSuccess = true;
                    this.updateClaimRequest(SDK_REQUEST_STATUS.CONFIRM, SDK_ROLE_PO, "", true);//Truyền status empty sẽ keep current status
                    this.deleteND13DataTemp(this.state.clientId, this.state.trackingId, this.state.apiToken, this.state.deviceId);
                }
                else {
                    failMessage = Res.message;
                }
            }).catch(error => {
                console.log(error);
                Exception = error;
                failMessage = error.message;
            }).finally(() => {
                remainRetryTimes--;
                this.setState({ isSubmitting: false });

                // success or after retry ${NUM_OF_RETRY} times.
                const isWritingLog = isSuccess || remainRetryTimes <= 0;
                if (isWritingLog) {
                    console.log('Writing log confirmCPConsent')
                    if (isSuccess) {
                        this.setShowSuccess(true);
                        cpSaveLog("Web_Succ_ConfirmClaimData",
                            `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception ? Exception : ''
                        );
                    } else {
                        cpSaveLog("Web_Err_ConfirmClaimData",
                            `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception ? Exception : ''
                        );
                    }
                }

            });
        }
        if (getSession(ONLY_PAYMENT + this.state.selectedCliInfo?.clientId)) {
            removeSession(ONLY_PAYMENT + this.state.selectedCliInfo?.clientId);
        }
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

    setShowSuccess = (value) => {
        this.setState({ showSuccess: value });
    }
    closeSuccess = () => {
        this.setShowSuccess(false);
        this.closeToHome();
    }

    closeNotFoundCustomer = () => {
        this.setState({ notFoundCustomer: false });
        this.closeToHome();
    }

    setIsPayment = (value) => {
        this.setState({ isPayment: value });
    }

    setHaveShowPayment = (value) => {
        this.setState({ haveShowPayment: value });
    }

    clearState = () => {
        let state = this.state;
        state.initClaimState.occupation = '';
        state.initClaimState.disabledButton = true;
        state.claimTypeState = INITIAL_STATE().claimTypeState;
        state.claimDetailState = INITIAL_STATE().claimDetailState;
        state.paymentMethodState = INITIAL_STATE().paymentMethodState;
        state.attachmentState = INITIAL_STATE().attachmentState;
        state.submissionState = INITIAL_STATE().submissionState;
        state.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        state.selectedCliID = '';
        state.selectedCliInfo = '';
        state.liInfo = '';
        state.selectedCliIndex = '';
        state.claimCheckedMap = {};
        state.openPopupWarningDecree13 = false;
        state.trackingId = '';
        state.claimId = '';
        state.poConfirmingND13 = '0';
        state.ND13ClientProfile = null;
        state.currentState = CLAIM_STATE.CREATE_REQUEST;
        state.changeSelectLIIndex = '';
        state.changeSelectLIItem = '';
        state.currentStepCode = CLAIM_STEPCODE.INIT;
        this.setState(state);
        console.log('state sau clear=', this.state);
    }

    checkAuthorization = () => {
        // event.preventDefault();
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || this.state.signature,
            accessToken: this.state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: this.state.requestId,
            customerId: this.state.clientId || getSession(CLIENT_ID),
            authorizadCode: this.state.agentCode,
        }

        let request = buildMicroRequest(metadata, data);


        authorizationGetInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS) {
                if (Res.data && Res.data[0]) {
                    if (Res.data[0]?.status !== 'Approval') {
                        this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_APPROVE, authorizationId: Res.data[0]?.authorizationId });
                    } else {
                        this.setState({ showConfirmAuthorization: false, authorzieStatus: AUTHORZIE_STATUS_CODE_APPROVE, authorizationId: Res.data[0]?.authorizationId });
                    }
                } else {
                    let data = {
                        customerId: this.state.clientId || getSession(CLIENT_ID),
                        authorizadCode: this.state.agentCode,
                    }
                    let request = buildMicroRequest(metadata, data);
                    authorizationGetInfor(request).then(Res2 => {
                        if (Res2.code === CODE_SUCCESS) {
                            if (Res2?.data) {
                                let authorizeItem =  Res2?.data?.some(item => isEmpty(item.requestId) && (item.status === AUTHORZIE_STATUS_CODE_APPROVE) );
                                if (!isEmpty(authorizeItem)) {
                                    this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                } else {
                                    this.setState({ showConfirmAuthorization: false, authorzieStatus: AUTHORZIE_STATUS_CODE_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                }
                            } else {
                                this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE, authorizationId: Res2?.data?.[0]?.authorizationId });
                            }
                        }
                    }).catch(error => {
                        console.log(error);
                    });

                    // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE });
                }
            } /*else if (Res.code === CODE_ERROR) {
            } else if (Res.code === CODE_EXPIRED_TOKEN) {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home', state: {authenticated: false, hideMain: false}
    
                })
    
            }*/
        }).catch(error => {
            console.log(error);
        });

    }

    checkAuthorizationLoadState = (state, clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail, d) => {
        // this.setState({isPhoneVerified: isPhoneVerified, isEmailVerified: isEmailVerified, hasCellPhone: hasCellPhone, hasEmail: hasEmail})
        if (!d?.requestId) {
            state.isPhoneVerified = isPhoneVerified;
            state.isEmailVerified = isEmailVerified;
            state.hasCellPhone = hasCellPhone;
            state.hasEmail = hasEmail;
            this.setState(state);
            this.loadState(clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail);
            
            // this.handleGoNextStep();
        } else {
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),
                platform: WEB_BROWSER_VERSION,
                system: this.state.sourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE) || state.signature,
                accessToken: state.apiToken || getSession(ACCESS_TOKEN)
            }
            let data = {
                requestId: state.requestId,
                customerId: state.clientId || getSession(CLIENT_ID),
                authorizadCode: state.agentCode,
            }
            let request = buildMicroRequest(metadata, data);
            authorizationGetInfor(request).then(Res => {
                let showConfirmAuthorization = false;
                let authorzieStatus = '';
                let authorizationId = '';
                if (Res.code === CODE_SUCCESS) {
                    if (Res.data && Res?.data?.[0]) {
                        if (Res.data[0]?.status !== 'Approval') {
                            showConfirmAuthorization = true;
                            authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_APPROVE;
                            authorizationId = Res.data[0]?.authorizationId;

                        } else {
                            showConfirmAuthorization = false;
                            authorzieStatus = AUTHORZIE_STATUS_CODE_APPROVE;
                            authorizationId = Res.data[0]?.authorizationId;
                        }
                        state.showConfirmAuthorization = showConfirmAuthorization;
                        state.authorzieStatus = authorzieStatus;
                        state.authorizationId = authorizationId;
                        this.setState(state);

                    } else {
                        let data = {
                            customerId: state.clientId || getSession(CLIENT_ID),
                            authorizadCode: state.agentCode,
                        }
                        let request = buildMicroRequest(metadata, data);
                        authorizationGetInfor(request).then(Res2 => {
                             if (Res2.code === CODE_SUCCESS) {
                                if (Res2?.data) {
                                    let authorizeItem = Res2?.data?.some(item => isEmpty(item.requestId) && (item.status === AUTHORZIE_STATUS_CODE_APPROVE));
                                    if (!isEmpty(authorizeItem)) {
                                        showConfirmAuthorization = true;
                                        authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_APPROVE;
                                        authorizationId = Res2?.data?.[0]?.authorizationId;
                                        // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                    } else {
                                        showConfirmAuthorization = false;
                                        authorzieStatus = AUTHORZIE_STATUS_CODE_APPROVE;
                                        authorizationId = Res2?.data?.[0]?.authorizationId;
                                        // this.setState({ showConfirmAuthorization: false, authorzieStatus: AUTHORZIE_STATUS_CODE_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                    }
                                } else {
                                    showConfirmAuthorization = true;
                                    authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_CREATE;
                                    authorizationId = Res2?.data?.[0]?.authorizationId;
                                    // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                }
                            }
                            state.showConfirmAuthorization = showConfirmAuthorization;
                            state.authorzieStatus = authorzieStatus;
                            state.authorizationId = authorizationId;
                            this.setState(state);
                        }).catch(error => {
                            console.log(error);
                        });
                        this.loadState(clientId, deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail);
                        // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE });
                    }
                } /*else if (Res.code === CODE_ERROR) {
            } else if (Res.code === CODE_EXPIRED_TOKEN) {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home', state: {authenticated: false, hideMain: false}
    
                })
    
            }*/
                this.setState(state);
            }).catch(error => {
                console.log(error);
            });
        }


    }


    checkAuthorizationLoadNoti = (d, lockingBy, status, state) => {
        // event.preventDefault();
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE) || state.signature,
            accessToken: state.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            requestId: state.requestId,
            customerId: state.clientId || getSession(CLIENT_ID),
            authorizadCode: state.agentCode,
        }

        let request = buildMicroRequest(metadata, data);


        authorizationGetInfor(request).then(Res => {
            let showConfirmAuthorization = false;
            let authorzieStatus = '';
            let authorizationId = '';
            if (Res.code === CODE_SUCCESS) {
                if (Res.data && Res.data[0]) {
                    if (Res.data[0]?.status !== 'Approval') {
                        showConfirmAuthorization = true;
                        authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_APPROVE;
                        authorizationId = Res.data[0]?.authorizationId;
                        
                    } else {
                        showConfirmAuthorization = false;
                        authorzieStatus = AUTHORZIE_STATUS_CODE_APPROVE;
                        authorizationId = Res.data[0]?.authorizationId;
                    }
                    state.showConfirmAuthorization = showConfirmAuthorization;
                    state.authorzieStatus = authorzieStatus;
                    state.authorizationId = authorizationId;
                    // this.setState({ showConfirmAuthorization: showConfirmAuthorization, authorzieStatus: authorzieStatus, authorizationId: authorizationId });
                    this.getPermissionLoadStateRequestId(d, lockingBy, status, state);
                } else {
                    let data = {
                        customerId: state.clientId || getSession(CLIENT_ID),
                        authorizadCode: state.agentCode,
                    }
                    let request = buildMicroRequest(metadata, data);
                    authorizationGetInfor(request).then(Res2 => {
                        if (Res2.code === CODE_SUCCESS) {
                            if (Res2?.data) {
                                let authorizeItem =  Res2?.data?.some(item => isEmpty(item.requestId) && (item.status === AUTHORZIE_STATUS_CODE_APPROVE) );
                                if (!isEmpty(authorizeItem)) {
                                    showConfirmAuthorization = true;
                                    authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_APPROVE;
                                    authorizationId = Res2?.data?.[0]?.authorizationId;
                                    // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                } else {
                                    showConfirmAuthorization = false;
                                    authorzieStatus = AUTHORZIE_STATUS_CODE_APPROVE;
                                    authorizationId = Res2?.data?.[0]?.authorizationId;
                                    // this.setState({ showConfirmAuthorization: false, authorzieStatus: AUTHORZIE_STATUS_CODE_APPROVE, authorizationId: Res2?.data?.[0]?.authorizationId });
                                }
                            } else {
                                showConfirmAuthorization = true;
                                authorzieStatus = AUTHORZIE_STATUS_CODE_NOT_CREATE;
                                authorizationId = Res2?.data?.[0]?.authorizationId;
                                // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE, authorizationId: Res2?.data?.[0]?.authorizationId });
                            }
                        }
                        state.showConfirmAuthorization = showConfirmAuthorization;
                        state.authorzieStatus = authorzieStatus;
                        state.authorizationId = authorizationId;
                        this.getPermissionLoadStateRequestId(d, lockingBy, status, state);
                    }).catch(error => {
                        console.log(error);
                    });

                    // this.setState({ showConfirmAuthorization: true, authorzieStatus: AUTHORZIE_STATUS_CODE_NOT_CREATE });
                }
            } /*else if (Res.code === CODE_ERROR) {
            } else if (Res.code === CODE_EXPIRED_TOKEN) {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home', state: {authenticated: false, hideMain: false}
    
                })
    
            }*/
        }).catch(error => {
            console.log(error);
        });

    }


    updateApiToken = (value) => {
        this.setState({ apiToken: value });
    }

    updateAgentConfirmed = (value) => {
        this.setState({ agentConfirmed: value });
    }

    updateRequestIdMap = (key, value) => {
        let state = this.state;
        let requestIdMap = state.requestIdMap;
        requestIdMap[key] = value;
        state.requestIdMap = requestIdMap;
        this.setState(state);
    }
    updateSignature = (value)=> {
        this.setState({signature: value});
    }

    updateIdNum = (value)=> {
        this.setState({idNum: value});
    }

    saveAndQuit = () => {
        let jsonState = this.state;
        jsonState.saveAndQuit = false;
        this.saveClaim();
    };

    handlerAdjust = () => {
        if (this.state.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
            this.setState({updateInfoState: ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18, toggleBack: !this.state.toggleBack});
        } 
    }

    updatePinStep = (value) => {
        this.setState({pinStep: value});
    }

    render() {
        console.log('this.state.selectedCliInfo=', this.state.selectedCliInfo);
        console.log('this.state.showConfirm=', this.state.showConfirm);
        console.log('noValidPolicy=', this.state.noValidPolicy);
        console.log('claim callBackCancel=' + this.state.callBackCancel);
        // if (this.state.systemGroupPermission && this.state.systemGroupPermission?.[0]?.SourceSystem) {
        //     cssSystem = this.state.systemGroupPermission?.[0]?.SourceSystem?.toLowerCase();
        //     console.log('csssss=', cssSystem);
        // }

        // const getStep= (stepCode) => {
        //     if (this.state.systemGroupPermission) {
        //         for (let i = 0; i < this.state.systemGroupPermission.length; i++) {
        //             if (this.state.systemGroupPermission[i].StepCode === stepCode) {
        //                 return this.state.systemGroupPermission[i].StepOrder
        //             }
        //         }
        //     }
        //     return 0;
        // }

        const setShowThank = (value) => {
            this.setState({ showThank: value });
            if (!value) {
                this.closeToHome();
            }
        }

        const closePopupExpireAgentMsg = () => {
            this.setState({ claimExpireAgentMsg: '' });
            this.closeToHome();
        }

        const closePopup = () => {
            this.setState({ noValidPolicy: false, noPhone: false, noEmail: false, noVerifyPhone: false, noVerifyEmail: false });
            if (this.state.clientId) {
                closeToHome();
            } else {
                closeToHome(true);
            }
        }

        const closeToHome = (notGo) => {
            if (notGo) {
                return;
            }
            let from = getUrlParameter("fromApp");
            if (from) {
                let obj = {
                    Action: "END_ND13_" + this.props.processType,
                    ClientID: this.state.customerId
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
                window.location.href = this.state.callBackCancel;
            }
        }

        const closeToAccount = () => {
            // window.location.href = '/account';
            this.setState({
                noVerifyPhone: false,
                noVerifyEmail: false
            });
        };

        const closeChangeSelectLI = () => {
            this.setState({ changeSelectLI: false });
        };

        const closeHaveCreatingData = () => {
            this.setState({ haveCreatingData: false });
        };

        const agreeChangeSelectLi = () => {
            clickOnLICardChange(this.state.changeSelectLIIndex, this.state.changeSelectLIItem);
        };

        const clickOnLICardChange = (index, item) => {
            let dataResponse = this.state;
            // getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.clientId)
            //     .then(Res => {
            //         if (Res.v) {
            //             dataResponse.changeSelectLIIndex = index;
            //             dataResponse.changeSelectLIItem = item;
            //             dataResponse.haveCreatingData = true;
            //             this.setState(dataResponse);
            //             return;
            //         }
            //     })
            //     .catch(error => {
            //     });
            // Reset state
            dataResponse.tempJson = '';
            dataResponse.tempRequestId = '';
            dataResponse.initClaimState.occupation = '';
            dataResponse.initClaimState.disabledButton = true;
            dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
            dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
            dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
            dataResponse.attachmentState = INITIAL_STATE().attachmentState;
            dataResponse.submissionState = INITIAL_STATE().submissionState;
            dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
            dataResponse.selectedCliID = item.clientId;
            dataResponse.selectedCliInfo = item;
            dataResponse.liInfo = item;
            dataResponse.claimCheckedMap = {};
            const arrFulName = item.fullName.trim().split(" ");
            dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN) ? getSession(USER_LOGIN).trim() : this.state.clientId?.trim() === dataResponse.selectedCliID);
            dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
            dataResponse.currentState = this.getStep(CLAIM_STEPCODE.INIT_CLAIM);
            dataResponse.openPopupWarningDecree13 = false;
            dataResponse.trackingId = '';
            dataResponse.claimId = '';
            dataResponse.poConfirmingND13 = '';
            dataResponse.ND13ClientProfile = null;

            this.setState(dataResponse);
        };

        const agreeLoadCreatingLI = () => {
            this.clickOnLICardGo(this.state.changeSelectLIIndex, this.state.changeSelectLIItem);
            // this.fetchCPConsentConfirmation();
        };

        const agreeLoadCreatingLINew = () => {
            this.clickOnLICardNew(this.state.changeSelectLIIndex, this.state.changeSelectLIItem);
        };

        const saveAndQuit = () => {
            let jsonState = this.state;
            jsonState.saveAndQuit = false;
            this.saveClaim();
            // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(jsonState));
            // setTimeout(1000, this.closeToHome());
        };

        const Quit = () => {
            this.setState({ saveAndQuit: false });
            setTimeout(25, this.closeToHome());
        };

        const GoHome = () => {
            this.props.history.push('/');
        };

        const closeSaveAndQuit = () => {
            this.setState({ saveAndQuit: false });
        };

        const closeClaimExpired = () => {
            this.setState({ claimExpired: false });
            this.closeToHome();
        }

        const closeWaittingAgentEdit = () => {
            this.setState({ waittingAgentEdit: false });
            this.closeToHome();
        }


        const setShowConfirm = (value) => {
            this.setState({ showConfirm: value });
            if (!value) {
                closeToHome(true);
            }
        }

        const authorizePO = () => {
            this.setState({ showConfirm: false });
            this.goNextStep();
        }
        let fromMobile = getUrlParameter("fromApp");
        let nodata = fromMobile && (this.state.currentState === this.getStep(CLAIM_STEPCODE.INIT)) ? ' nodata margin-top0' : '';
        if (fromMobile) {
            if (isEmpty(nodata)) {
                nodata = 'margin-top0';
                // if (fromMobile === 'Android') {
                //     nodata = nodata + ' padding-top0'
                // }
            }
            nodata = nodata + ' dc-mobile';
        }
        const screenClaim = window.location.pathname;//SCREENS.CREATE_CLAIM;
        console.log('window.location.pathname=', window.location.pathname);
        
        // const {id, index} = this.props.match.params;

        if (!this.state.noValidPolicy && !this.state.noPhone && !this.state.noEmail && !this.state.noVerifyPhone && !this.state.noVerifyEmail) {
            return (<div className={cssSystem}>
                <Helmet>
                    <title>Tạo mới yêu cầu – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                        content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                    <meta name="robots" content="noindex, nofollow" />
                    <meta property="og:type" content="website" />
                    <meta property="og:url" content={FE_BASE_URL + "/myclaim"} />
                    <meta property="og:title" content="Tạo mới yêu cầu - Dai-ichi Life Việt Nam" />
                    <meta property="og:description"
                        content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                    <meta property="og:image"
                        content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
                    <link rel="canonical" href={FE_BASE_URL + "/myclaim"} />
                </Helmet>
                {(this.state.currentState >= this.getStep(CLAIM_STEPCODE.INIT)) && ((this.state.currentState !== this.getStep(CLAIM_STEPCODE.TRACKING_CLAIM))) ? (
                    <main className={getSession(ACCESS_TOKEN) && !fromMobile && (this.state.systemGroupPermission?.[0].SourceSystem === 'DConnect') ? 'logined' + nodata : nodata} id="main-id">
                        {fromMobile && (this.state.currentState === this.getStep(CLAIM_STEPCODE.INIT)) && (
                            <div className='app-header-back'>
                                <i className='margin-left8' onClick={() => closeToHome()}><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' /></i>
                                <p className='viewer-back-title'>Tạo mới yêu cầu</p>
                            </div>
                        )
                        }
                        <div className={fromMobile ? "main-warpper basic-mainflex dconnect-sdk" : "main-warpper basic-mainflex e-claim dconnect-sdk"}>
                            {((fromMobile && (this.state.currentState === this.getStep(CLAIM_STEPCODE.INIT))) || !fromMobile) &&
                                <section className={fromMobile ?"sccard-warpper padding-top0": "sccard-warpper"}>
                                    {fromMobile &&
                                        <div className='margin-top56'></div>
                                    }
                                    <h5 className="basic-semibold" style={{ paddingBottom: '4px' }}>Vui lòng chọn người được
                                        bảo hiểm:</h5>
                                    <LifeInsureList
                                        responseLIList={this.state.responseLIList}
                                        selectedCliID={this.state.selectedCliID}
                                        selectedCliIndex={this.state.selectedCliIndex}
                                        handlerClickOnLICard={this.handlerClickOnLICard}
                                        handleSetResponseLIList={this.handleSetResponseLIList}
                                        clientId={this.state.clientId || getSession(CLIENT_ID)}
                                        apiToken={this.state.apiToken || getSession(ACCESS_TOKEN)}
                                        systemGroupPermission={this.state.systemGroupPermission}
                                        requestIdMap={this.state.requestIdMap}
                                        updateRequestIdMap={(key, value) => this.updateRequestIdMap(key, value)}
                                        agentCode={this.state.agentCode}
                                        agentName={this.state.agentName}
                                        agentPhone={this.state.agentPhone}
                                        sourceSystem={this.state.sourceSystem}
                                        processType="Claim"
                                        currentState={this.state.currentState}
                                        selectedCliInfo={this.state.selectedCliInfo}
                                        disableEdit={this.state.disableEdit}
                                        handleSetClaimRequestId={this.handleSetClaimRequestId}
                                        state={{ ...this.state }}
                                        consultingViewRequest={this.state.consultingViewRequest || consultingViewRequest}
                                    />
                                    {getUrlParameter("fromApp") && this.state.selectedCliID &&
                                        <div className="bottom-btn">
                                            <button className="btn btn-primary" onClick={() => this.LIContinue()}
                                            >Tiếp tục
                                            </button>
                                        </div>
                                    }
                                    {!getUrlParameter("fromApp") &&
                                        <div className="other_option" id="other-option-toggle" onClick={() => this.goBack()}>
                                            <p>Tiếp tục</p>
                                            <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt="" /></i>
                                        </div>
                                    }
 
                                </section>
                            }
                            {this.changeSubPage(this.state.currentState)}
                            {haveFatca &&
                                <FatcaPopup
                                    submissionState={this.state.submissionState}
                                    handleOnChangeFatcaState={this.handleOnChangeFatcaState}
                                    handleOnClickConfirmCloseButton={this.handleOnClickConfirmCloseButton}
                                    selectedCliInfo={this.state.selectedCliInfo}
                                    paymentMethodState={this.state.paymentMethodState}
                                    poConfirmingND13={this.state.poConfirmingND13}
                                    claimCheckedMap={this.state.claimCheckedMap}
                                    systemGroupPermission={this.state.systemGroupPermission}
                                    claimTypeList={this.state.claimTypeList}
                                    handlerOpenPopupSucceeded={this.handlerOpenPopupSucceeded}
                                    isSubmitting={this.state.isSubmitting}
                                    agentKeyInPOSelfEdit={this.state.agentKeyInPOSelfEdit}
                                    pinStep={this.state.pinStep}
                                />
                            }
                        </div>
                        {this.state.changeSelectLI &&
                            <ConfirmChangePopup closePopup={() => closeChangeSelectLI()} go={() => agreeChangeSelectLi()}
                                msg='Dữ liệu đã điền sẽ bị mất nếu đổi Người được bảo hiểm, Quý khách có đồng ý?'
                                agreeText='Đồng ý' notAgreeText='Không đồng ý' />}
                        {this.state.haveCreatingData &&
                            <ConfirmChangePopup closePopup={() => closeHaveCreatingData()} go={() => agreeLoadCreatingLI()}
                                msg={htmlParse('Quý khách <span classname="basic-bold">' + (this.state.changeSelectLIItem ? this.state.changeSelectLIItem.fullName : '') + '</span> có một yêu cầu đang tạo. Quý khách có muốn tiếp tục với yêu cầu này?')}
                                agreeText='Tiếp tục' notAgreeText='Xóa và tạo mới'
                                notAgreeFunc={() => agreeLoadCreatingLINew()} />}
                        {this.state.saveAndQuit &&
                            <ConfirmChangePopup closePopup={() => closeSaveAndQuit()} go={() => saveAndQuit()}
                                msg={htmlParse('Quý khách có muốn Lưu lại yêu cầu bồi thường trước khi Thoát hay không?')}
                                agreeText='Lưu và Thoát' notAgreeText='Thoát' notAgreeFunc={() => Quit()} />}
                        {this.state.openPopupWarningDecree13 && <POWarningND13 onClickExtendBtn={() => this.setState({
                            openPopupWarningDecree13: false
                        })} />}
                        {this.state.sendPOSuccess &&
                            <OkPopupTitle closePopup={() => this.closeSendPOSuccess()}
                                msg={'Thông tin sẽ được gửi đến BMBH qua thư điện tử, BMBH cần truy cập Dai-ichi Connect để xác nhận và gửi yêu cầu.Nếu BMBH không xác nhận trong vòng 24h, thì yêu cầu sẽ tự động hủy.'}
                                title={'Đã gửi yêu cầu đến BMBH xác nhận'}
                                imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                                agreeFunc={() => this.closeSendPOSuccess()}
                                agreeText='Đóng'
                            />
                        }
                        {this.state.showSuccess &&
                            <ThanksPopupSDK closeThanks={() => this.closeSuccess()}
                                msg={'<p>Vui lòng theo dõi tình trạng <br/> hồ sơ tại chức năng <br/> <span style="font-weight: 700">Theo dõi yêu cầu</span>.</p>'}
                            />
                        }
                        {this.state.claimExpired &&
                            <OkPopupTitle closePopup={() => closeClaimExpired()}
                                msg={'Yêu cầu này đã được Quý khách xác nhận hoặc đã hết thời gian thực hiện. Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài Dai-ichi Life Việt Nam để biết thêm chi tiết.'}
                                imgPath={FE_BASE_URL + '/img/popup/key-expired.svg'}
                                agreeFunc={() => closeClaimExpired()}
                                agreeText='Quay về trang chủ'
                            />
                        }
                        {this.state.claimExpireAgentMsg &&
                            <AlertPopupHight closePopup={() => closePopupExpireAgentMsg()}
                                msg={this.state.claimExpireAgentMsg}
                                imgPath={FE_BASE_URL + '/img/popup/confirm-claim.svg'}
                            />
                        }
                        {/* {this.state.notFoundCustomer &&
                            <OkPopupTitle closePopup={() => this.closeNotFoundCustomer()}
                                msg={'Không tìm thấy khách hàng.'}
                                imgPath={FE_BASE_URL + '/img/popup/contact-info.svg'}
                                agreeFunc={() => this.closeNotFoundCustomer()}
                                agreeText='Đóng'
                                />
                        } */}
                        {this.state.showThank &&
                            <OkPopupTitle closePopup={() => setShowThank(false)}
                                msg={'Cảm ơn Quý khách đã xác nhận thông tin'}
                                imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                                agreeFunc={() => setShowThank(false)}
                                agreeText='Đóng'
                            />
                        }
                    </main>
                ) : (
                    <>
                        {this.changeSubPage(this.state.currentState)}
                        {this.state.claimExpired &&
                            <OkPopupTitle closePopup={() => closeClaimExpired()}
                                msg={'Yêu cầu này đã được Quý khách xác nhận hoặc đã hết thời gian thực hiện. Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài Dai-ichi Life Việt Nam để biết thêm chi tiết.'}
                                imgPath={FE_BASE_URL + '/img/popup/key-expired.svg'}
                                agreeFunc={() => closeClaimExpired()}
                                agreeText='Quay về trang chủ'
                            />
                        }
                        {this.state.claimExpireAgentMsg &&
                            <AlertPopupHight closePopup={() => closePopupExpireAgentMsg()}
                                msg={this.state.claimExpireAgentMsg}
                                imgPath={FE_BASE_URL + '/img/popup/confirm-claim.svg'}
                            />
                        }
                        {this.state.waittingAgentEdit &&
                            <OkPopupTitle closePopup={() => closeWaittingAgentEdit()}
                                msg={'Yêu cầu này đã được chuyển cho Đại lý bảo hiểm điều chỉnh thông tin.'}
                                imgPath={FE_BASE_URL + '/img/popup/key-expired.svg'}
                                agreeFunc={() => closeWaittingAgentEdit()}
                                agreeText='Quay về trang chủ'
                            />
                        }
                        {this.state.showConfirm &&
                            <AgreeCancelPopup closePopup={() => setShowConfirm(false)} agreeFunc={() => authorizePO()}
                                title={'Cam kết của Đại lý bảo hiểm'}
                                msg={'<p>Tôi hiểu rằng tôi đang thực hiện khai báo thông tin Yêu cầu giải quyết quyền lợi bảo hiểm thay cho Khách hàng của tôi.</p><p>Tôi xác nhận yêu cầu quyền lợi bảo hiểm này đã được thực hiện theo yêu cầu và đồng ý của BMBH/NĐBH, đồng thời tôi cam kết bảo mật tất cả thông tin của Khách hàng trong yêu cầu này.</p>'}
                                imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} agreeText='Xác nhận' notAgreeText='Thoát' notAgreeFunc={() => setShowConfirm(false)} />}

                    </>
                )}
            </div>);
        } else {
            return (<div className={cssSystem}>
                <Helmet>
                    <title>Tạo mới yêu cầu – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                        content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                    <meta name="robots" content="noindex, nofollow" />
                    <meta property="og:type" content="website" />
                    <meta property="og:url" content={FE_BASE_URL + "/myclaim"} />
                    <meta property="og:title" content="Tạo mới yêu cầu - Dai-ichi Life Việt Nam" />
                    <meta property="og:description"
                        content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                    <meta property="og:image"
                        content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
                    <link rel="canonical" href={FE_BASE_URL + "/myclaim"} />
                </Helmet>
                {(this.state.noValidPolicy || !this.state.responseLIList) ? (
                    <AlertPopupHight closePopup={() => closePopup()}
                        msg={'Hợp đồng bảo hiểm của Quý Khách đã hết hiệu lực và không còn trong thời gian nộp YCQL'}
                        imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'}
                        screen={SCREENS.HOME} />) : (this.state.noPhone && this.state.noEmail ? (
                            <AlertPopupClaim closePopup={()=>this.closeToHome()}
                                msg={'Quý khách chưa có Số điện thoại và Email để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                imgPath={FE_BASE_URL + '/img/popup/no-phone-and-email.svg'} />) : (this.state.noPhone ? (
                                    <AlertPopupClaim closePopup={()=>this.closeToHome()}
                                        msg={'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                        imgPath={FE_BASE_URL + '/img/popup/no-phone.svg'} />) : (this.state.noEmail ? (
                                            <AlertPopupClaim closePopup={()=>this.closeToHome()}
                                                msg={'Quý khách chưa có địa chỉ Email để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                                imgPath={FE_BASE_URL + '/img/popup/no-email.svg'} />) : (this.state.noVerifyPhone && this.state.noVerifyEmail ? (
                                                    <AuthenticationPopupClaim closePopup={()=>this.closeToHome()}
                                                        agree={closeToAccount}
                                                        msg={'Số điện thoại và Email của Quý khách chưa được xác thực'}
                                                        screen={screenClaim} />) : (this.state.noVerifyPhone ? (
                                                            <AuthenticationPopupClaim closePopup={()=>this.closeToHome()}
                                                                agree={closeToAccount}
                                                                msg={'Số điện thoại của Quý khách chưa được xác thực'}
                                                                screen={screenClaim} />) : (this.state.noVerifyEmail ? (
                                                                    <AuthenticationPopupClaim closePopup={closeToHome}
                                                                        agree={closeToAccount}
                                                                        msg={'Email của Quý khách chưa được xác thực'}
                                                                        screen={screenClaim} />) : (
                                                                    <AlertPopup closePopup={()=>this.closeToHome()}
                                                                        msg={'Quý khách không còn hợp đồng nào đang hiệu lực. Vui lòng liên hệ tổng đài hoặc văn phòng Dai-ichi Life gần nhất.'}
                                                                        imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}
                                                                        screen={screenClaim} />)

                                                        ))))))}
                {this.state.isCheckPermission && <AlertPopupClaim closePopup={this.closeNotAvailable.bind(this)}
                    msg={this.state.msgCheckPermission}
                    imgPath={checkPermissionIcon} />}
                {/*-----------------------------------------------------------------------------------------------------------------------------------*/}
                {this.state.openPopupWarningDecree13 && <POWarningND13 onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })} />}
                {this.state.invalidDataRequest &&
                    <AuthenticationPopupClaim closePopup={()=>this.closeToHome()}
                        msg={'Yêu cầu không hợp lệ.'}
                        screen={screenClaim} />
                }
            </div>);
        }
    }

}

export default withRouter(CreateClaimSDK);
