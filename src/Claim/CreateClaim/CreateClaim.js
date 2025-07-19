// import 'antd/dist/antd.min.css';
import '../claim.css';
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
    ConsentStatus,
    DCID,
    EMAIL,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    LIITEM,
    PageScreen,
    PO_CONFIRMING_DECREE_13,
    POL_LIST_CLIENT4CLAIM,
    SCREENS,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    WEB_BROWSER_VERSION,
    OS
} from '../../constants'
import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';
import {
    CPConsentConfirmation,
    CPGetPolicyListByCLIID,
    CPSaveLog,
    getBankMasterData,
    GetConfiguration,
    getLIListForClaim,
    getZipCodeMasterData,
    iibGetMasterDataByType,
    logoutSession,
    CPSubmitForm
} from '../../util/APIUtils';
import {
    checkMapHaveTrue,
    filterValidEpolicy,
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
    showMessage,
    trackingEvent
} from '../../util/common';
import AlertPopupClaim from '../../components/AlertPopupClaim';
import AuthenticationPopupClaim from '../../components/AuthenticationPopupClaim';
import AlertPopupHight from '../../components/AlertPopupHight';
import AlertPopup from '../../components/AlertPopup';
import ConfirmChangePopup from '../../components/ConfirmChangePopup';
import ContactPersonDetail from './ClaimProcess/ClaimDetail/ContactPersonDetail';
import parse from 'html-react-parser';
import AES256 from 'aes-everywhere';
import moment from 'moment'

import LifeInsureList from './Initial/LifeInsureList';
import PolList from './Initial/PolList';

import ClaimType from './ClaimProcess/ClaimDetail/ClaimType';
import ClaimDetail from './ClaimProcess/ClaimDetail/ClaimDetail';
import PaymentMethod from './ClaimProcess/PaymentMethod/PaymentMethod';
import Attachment from './ClaimProcess/Attachment/Attachment';
import ClaimSubmission from './ClaimProcess/Submission/ClaimSubmission';
import {Helmet} from "react-helmet";
import {isEmpty} from "lodash";
import checkPermissionIcon from "../../img/popup/check-permission.png";
import POWarningND13 from "../ClaimND13/ND13Modal/POWarningND13/POWarningND13";
import ND13ContactFollowConfirmation from "../ClaimND13/ND13ContactFollowConfirmation";

export const CLAIM_STATE = Object.freeze({
    INIT: 0,
    INIT_CLAIM: 1,
    CLAIM_TYPE: 2,
    CLAIM_DETAIL: 3,
    PAYMENT_METHOD: 4,
    CONTACT: 5,
    ATTACHMENT: 6,
    SUBMIT: 7,
    DONE: 8,
    ND13_INFO_CONFIRMATION: 9,
    ND13_INFO_PO_CONTACT_INFO_OVER_18: 10,
    ND13_INFO_PO_CONTACT_INFO_UNDER_18: 11,
    ND13_INFO_FOLLOW_CONFIRMATION: 12,
})
export const TREAMENT_TYPE = Object.freeze({
    'IN': {desc: 'Nội trú', submitType: 'Inpatient'},
    'OUT': {desc: 'Ngoại trú', submitType: 'Outpatient'},
    'DENT': {desc: 'Nha khoa', submitType: 'Dental'},
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
        currentState: CLAIM_STATE.INIT,
        polID: '',
        PolicyStatus: '',
        claimTypeList: null,
        responseLIList: [],
        liInfo: {
            ClientID: '', DOB: '', POID: '', FullName: '', CellPhone: '', Email: '',
        },
        selectedCliInfo: {
            ClientID: '', DOB: '', POID: '', Gender: '', FullName: '', Address: '', CellPhone: '', Email: '', IsLIS: '',
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
                    InvoiceAmount: '', InvoiceNumber: '', errorList: []
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
        poConfirmingND13: '',
        ND13ClientProfile: null,
        onlyPayment: false,
        liWating: null
    }
};

class CreateClaim extends Component {

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
        
    }

    componentDidMount() {
        this.handlerResetState();
        let intervalId = setInterval(this.save, 10000);
        this.setState({intervalId: intervalId});
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_LIST_INSURED}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`, `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`,);
        this.fetchCheckConfigPermission();
    }

    componentWillUnmount() {
        if (this.state.intervalId) {
            clearInterval(this.state.intervalId);
        }
        this.cpSaveLog(`Web_Close_${PageScreen.CLAIM_LIST_INSURED}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`, `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`,);

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

    getClaimList() {
        let request = {
            Action: "GetClaimICD", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {

            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({hospitalResultList: Response.ClientProfile});
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
                this.setState({hospitalList: Response.ClientProfile});
            }
        }).catch(error => {
        });
    }

    getPolList() {
        let noPhone = getSession(CELL_PHONE) ? false : true;
        let noEmail = getSession(EMAIL) ? false : true;
        let noVerifyPhone = (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) === '1')) ? false : true;
        let noVerifyEmail = (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) === '1')) ? false : true;

        if (!getSession(POL_LIST_CLIENT4CLAIM)) {
            let apiRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    Action: 'GetPolicy4Claim',
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            };
            CPGetPolicyListByCLIID(apiRequest).then(Res => {
                let Response = Res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {


                    let noValidPol = true;
                    let profile = Response.ClientProfile;
                    if (profile && profile.length > 0) {
                        noValidPol = false;
                    }
                    this.setState({
                        ClientProfile: profile,
                        noValidPolicy: noValidPol,
                        noPhone: noPhone,
                        noEmail: noEmail,
                        noVerifyPhone: noVerifyPhone,
                        noVerifyEmail: noVerifyEmail
                    });
                    setSession(POL_LIST_CLIENT4CLAIM, JSON.stringify(Response.ClientProfile));
                } else if (Response.ErrLog === 'EMPTY') {
                    this.setState({
                        noValidPolicy: true,
                        noPhone: noPhone,
                        noEmail: noEmail,
                        noVerifyPhone: noVerifyPhone,
                        noVerifyEmail: noVerifyEmail
                    });
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
        } else {
            let noValidPol = true;
            let profile = filterValidEpolicy(JSON.parse(getSession(POL_LIST_CLIENT4CLAIM)));
            if (profile && profile.length > 0) {
                noValidPol = false;
            }
            this.setState({
                ClientProfile: profile,
                noValidPolicy: noValidPol,
                noPhone: noPhone,
                noEmail: noEmail,
                noVerifyPhone: noVerifyPhone,
                noVerifyEmail: noVerifyEmail
            });
        }
        // this.cpSaveLog("Danh sách hợp đồng");

    }

    getCountries() {
        let request = {
            Action: "Countries", Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {
            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({nationList: Response.ClientProfile});
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
                this.setState({relationShipList: Response.ClientProfile});
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
        this.setState({attachmentState: attachmentState});
    }

    resetState() {
        // Reset state to initial values
        this.setState(INITIAL_STATE());

        // Check if phone and email are verified
        const isPhoneVerified = getSession(VERIFY_CELL_PHONE) === '1';
        const isEmailVerified = getSession(VERIFY_EMAIL) === '1';
        const hasCellPhone = getSession(CELL_PHONE);
        const hasEmail = getSession(EMAIL);

        // If both phone and email are verified and available
        if (isPhoneVerified && isEmailVerified && hasCellPhone && hasEmail) {
            // Get life insured list for claim
            const apiRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    Action: 'LifeInsuredList',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            };
            getLIListForClaim(apiRequest)
                .then(Res => {
                    const Response = Res.Response;

                    if (Response.Result === 'true' && Response.ClientProfile !== null) {
                        this.setState({responseLIList: Response.ClientProfile});
                        if (this.props.match.params.info) {
                            let infoArr = this.props.match.params.info.split('-');
                            if (infoArr.length === 3 ) {
                                const liItem = getSession(LIITEM + infoArr[0]);
                                if (liItem) {
                                    this.clickOnLICardNoti(infoArr[1], JSON.parse(liItem), infoArr[2]);
                                }
                            }
                        } else if (this.props.match.params.id && this.props.match.params.index) {
                            const liItem = getSession(LIITEM + this.props.match.params.id);
                            if (liItem) {
                                this.clickOnLICard(this.props.match.params.index, JSON.parse(liItem));
                            }
                        }
                    } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                        showMessage(EXPIRED_MESSAGE);
                        logoutSession();
                        this.props.history.push({
                            pathname: '/home', state: {authenticated: false, hideMain: false}
                        });
                    }
                })
                .catch(error => {
                    this.props.history.push('/maintainence');
                });

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
                        this.setState({zipCodeList: Response.ClientProfile});
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
                        this.setState({bankList: Response.ClientProfile});
                    }
                })
                .catch(error => {
                    this.props.history.push('/maintainence');
                });
        }

        // Get other required data
        this.getPolList();
        this.getClaimList();
        this.getHospitalList();
        this.getCountries();
        this.getRelationShips();
    }

    clickOnLICard(index, item) {
        let dataResponse = this.state;
        if (dataResponse.selectedCliID && (dataResponse.selectedCliID !== item.ClientID) && (dataResponse.currentState > CLAIM_STATE.INIT_CLAIM)) {
            dataResponse.changeSelectLI = true;
            dataResponse.changeSelectLIIndex = index;
            dataResponse.changeSelectLIItem = item;
            this.setState(dataResponse);
            return;
        }
        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID).then(Res => {
            if (Res && Res.v) {
                
                let data = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
                //Check claim status
                if (data.claimId) {
                    const submitRequest = {
                        jsonDataInput: {
                            Action: 'CheckClaimStatus',
                            APIToken: getSession(ACCESS_TOKEN),
                            Authentication: AUTHENTICATION,
                            ClaimID: data.claimId,
                            Company: COMPANY_KEY,
                            DeviceId: getDeviceId(),
                            OS: OS,
                            Project: 'mcp',
                            UserLogin: getSession(USER_LOGIN)
                        }
                    }
                    CPSubmitForm(submitRequest).then(Res => {
                        let Response = Res.Response;
                        if ((Response.Result === 'true') && (Response.ErrLog === 'CANCEL')) {
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID);//Xóa claim tạm khi backend đã mark Hủy
                            this.resetState();
                            return;
                        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                            this.props.history.push('/');
                        } else if (Response.Result === 'true') {
                            dataResponse.changeSelectLIIndex = index;
                            dataResponse.changeSelectLIItem = item;
                            dataResponse.selectedCliID = item.ClientID;
                            dataResponse.haveCreatingData = true;
                            this.setState(dataResponse);
                            return;
                        }
                    }).catch(error => {
                    });
                } else {
                    dataResponse.changeSelectLIIndex = index;
                    dataResponse.changeSelectLIItem = item;
                    dataResponse.selectedCliID = item.ClientID;
                    dataResponse.haveCreatingData = true;
                    this.setState(dataResponse);
                    return;
                }

            }
        }).catch(error => {
        });
        // Reset state
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.ClientID;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '';
        dataResponse.ND13ClientProfile = null;
        if (item.FullName) {
            const arrFulName = item.FullName.trim().split(" ");
            dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        }
        dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
        let clientProfile = null;
        if (dataResponse !== null) {
            clientProfile = dataResponse.responseLIList;
        }
        this.setState(dataResponse);

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
        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID).then(Res => {
            if (Res && Res.v) {
                let data = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
                if (data.claimId === claimID) {
                    dataResponse.changeSelectLIIndex = index;
                    dataResponse.changeSelectLIItem = item;
                    dataResponse.selectedCliID = item.ClientID;
                    this.setState(dataResponse);
                    this.clickOnLICardGo(dataResponse.changeSelectLIIndex, dataResponse.changeSelectLIItem);
                    return;
                }
            }
        }).catch(error => {
        });
        // Reset state
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.ClientID;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '';
        dataResponse.ND13ClientProfile = null;
        if (item.FullName) {
            const arrFulName = item.FullName.trim().split(" ");
            dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        }
        dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        // dataResponse.jsonInput.jsonDataInput.ClientID = dataResponse.selectedCliID;
        dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
        let clientProfile = null;
        if (dataResponse !== null) {
            clientProfile = dataResponse.responseLIList;
        }
        this.setState(dataResponse);


    }
    clickOnLICardChange(index, item) {
        let dataResponse = this.state;
        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID).then(Res => {

            if (Res.v) {
                // dataResponse = JSON.parse(getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID));
                dataResponse.changeSelectLIIndex = index;
                dataResponse.changeSelectLIItem = item;
                dataResponse.haveCreatingData = true;
                this.setState(dataResponse);
                return;
            }
        }).catch(error => {

        });
        // Reset state
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.ClientID;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        const arrFulName = item.FullName.trim().split(" ");
        dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '';
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

        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID).then(Res => {
            if (Res.v) {
                // let responseLIList = [...dataResponse.responseLIList];
                dataResponse = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
                // dataResponse.selectedCliID = item.ClientID;
                // dataResponse.selectedCliInfo = item;
                // dataResponse.liInfo = item;
                dataResponse.changeSelectLI = false;
                // dataResponse.responseLIList = responseLIList;
                // dataResponse.claimSubmissionState = CLAIM_STATE.INIT;

                // let data = JSON.parse(AES256.decrypt(Res.v, COMPANY_KEY2));
                //Check claim status
                if (dataResponse.claimId) {
                    const submitRequest = {
                        jsonDataInput: {
                            Action: 'CheckClaimStatus',
                            APIToken: getSession(ACCESS_TOKEN),
                            Authentication: AUTHENTICATION,
                            ClaimID: dataResponse.claimId,
                            Company: COMPANY_KEY,
                            DeviceId: getDeviceId(),
                            OS: OS,
                            Project: 'mcp',
                            UserLogin: getSession(USER_LOGIN)
                        }
                    }
                    CPSubmitForm(submitRequest).then(Res => {
                        let Response = Res.Response;
                        if ((Response.Result === 'true') && (Response.ErrLog === 'CANCEL')) {
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID);//Xóa claim tạm khi backend đã mark Hủy
                            this.resetState();
                            return;
                        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                            this.props.history.push('/');
                        } 
                    }).catch(error => {
                    });
                } 

            } else {
                // Reset state
                dataResponse.initClaimState.occupation = '';
                dataResponse.initClaimState.disabledButton = true;
                dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
                dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
                dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
                dataResponse.attachmentState = INITIAL_STATE().attachmentState;
                dataResponse.submissionState = INITIAL_STATE().submissionState;
                dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
                dataResponse.selectedCliID = item.ClientID;
                dataResponse.selectedCliInfo = item;
                dataResponse.liInfo = item;
                dataResponse.selectedCliIndex = index;
                dataResponse.claimCheckedMap = {};
                const arrFulName = item.FullName.trim().split(" ");
                dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
                dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
                dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
                dataResponse.openPopupWarningDecree13 = false;
                dataResponse.trackingId = '';
                dataResponse.claimId = '';
                dataResponse.poConfirmingND13 = '';
                dataResponse.ND13ClientProfile = null;
            }
            try {
                this.setState(dataResponse);
            } catch (e) {
                // alert(e);
            }

        }).catch(error => {
            console.log(error);
        });
    }

    clickOnLICardNew(index, item) {
        let dataResponse = this.state;
        getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID).then(Res => {
            if (Res.v) {
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID);
            }
        }).catch(error => {
        });
        // Reset state
        dataResponse.initClaimState.occupation = '';
        dataResponse.initClaimState.disabledButton = true;
        dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
        dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
        dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
        dataResponse.attachmentState = INITIAL_STATE().attachmentState;
        dataResponse.submissionState = INITIAL_STATE().submissionState;
        dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
        dataResponse.selectedCliID = item.ClientID;
        dataResponse.selectedCliInfo = item;
        dataResponse.liInfo = item;
        dataResponse.selectedCliIndex = index;
        dataResponse.claimCheckedMap = {};
        const arrFulName = item.FullName.trim().split(" ");
        dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === (dataResponse.selectedCliID ? dataResponse.selectedCliID.trim() : ''));
        dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
        dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
        dataResponse.openPopupWarningDecree13 = false;
        dataResponse.trackingId = '';
        dataResponse.claimId = '';
        dataResponse.poConfirmingND13 = '';
        dataResponse.ND13ClientProfile = null;
        this.setState(dataResponse);

    }

    onLoadPolList(polList) {
        let initClaimState = Object.assign({}, this.props.initClaimState);
        initClaimState.selectedLIPolList = polList;
        this.setState({initClaimState: initClaimState});
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
        if (currentStep > CLAIM_STATE.DONE) {
            return;
        }
        if (currentStep > CLAIM_STATE.INIT_CLAIM) {
            if (currentStep === CLAIM_STATE.PAYMENT_METHOD) {
                if (jsonState.paymentMethodState.paymentMethodStep > PAYMENT_METHOD_STEP.INIT) {
                    if (jsonState.paymentMethodState.choseReceiver) {
                        jsonState.paymentMethodState.choseReceiver = null;
                    } else {
                        jsonState.paymentMethodState.paymentMethodStep = jsonState.paymentMethodState.paymentMethodStep - 1;
                        jsonState.paymentMethodState.disabledButton = false;
                    }
                } else {
                    jsonState.currentState = currentStep - 1;
                }
            } else {
                jsonState.currentState = currentStep - 1;
            }
            if (currentStep <= CLAIM_STATE.PAYMENT_METHOD) {
                jsonState.contactState.contactPersonInfo.role = '';
            }
            this.setState(jsonState);
        }
    }

    goToStep(step) {
        const jsonState = this.state;
        jsonState.currentState = step;

        if (step <= CLAIM_STATE.PAYMENT_METHOD) {
            jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.INIT;
            jsonState.paymentMethodState.choseReceiver = null;
            jsonState.contactState.contactPersonInfo.role = '';
        }
        this.setState(jsonState);
    }

    startClaimProcess() {
        // Move to "Thông tin sự kiện - 1"
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_CHOICE_BENEFIT}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Close_${PageScreen.CLAIM_CHOICE_BENEFIT}`, `Web_Close_${PageScreen.CLAIM_CHOICE_BENEFIT}`,);
        const jsonState = this.state;
        jsonState.currentState = CLAIM_STATE.CLAIM_TYPE;
        this.setState(jsonState);
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
        this.setState({isVietnam: true});
    }

    notInVietNam() {
        this.setState({isVietnam: false});
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
        ({errors, ...sickInfoOthers} = claimDetailState.sickInfo);
        if (!this.state.claimTypeState.isAccidentClaim && Object.entries(sickInfoOthers)
            .filter(([_, v]) => (v && (((typeof v === "string") && (v.trim().length > 0)) || (v instanceof Array && v.length > 0) || (v instanceof moment)))).length > 0) {
            return true;
        }
        // Thông tin khám/điều trị tại Cơ sở y tế
        for (const facility of claimDetailState.facilityList) {
            let {errors, endDate, ...commonFacilityInfo} = facility;
            if (claimDetailState.isTreatmentAt === true) {
                if (facility.treatmentType || ((typeof facility.selectedHospital === "string") && facility.selectedHospital.trim().length > 0) || (typeof facility.diagnosis === "string" && facility.diagnosis.trim().length > 0) || facility.diagnosis.length > 0) {
                    return true;
                }
                if ((facility.treatmentType === "IN") && endDate) {
                    return true;
                }
                if (facility.invoiceList) {
                    for (const invoice of facility.invoiceList) {
                        if ((typeof invoice.InvoiceNumber === "string") && (invoice.InvoiceNumber.trim().length > 0) || invoice.InvoiceAmount) {
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
        ({errors, ...accidentInfoOthers} = claimDetailState.accidentInfo);
        if (this.state.claimTypeState.isAccidentClaim) {
            if (Object.entries(accidentInfoOthers)
                .filter(([k, v]) => ((v && (((typeof v === "string") && (v.trim().length > 0)) || ((typeof v !== "string") && v.length > 0) || (v instanceof Array && v.length > 0) || (v instanceof moment))))).length > 0) {
                return true;
            }
        }
        // Tử vong
        if (this.state.claimCheckedMap[CLAIM_TYPE.DEATH]) {
            ({errors, ...deathInfoOthers} = claimDetailState.deathInfo);
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
        const jsonState = {...this.state};

        // Create a deep copy of the facilityList array and otherCompanyInfo object
        const updatedFacilityList = jsonState.claimDetailState.facilityList.map((facility, i) => {
            if (i === index) {
                // Create a deep copy of the otherCompanyInfo object
                const updatedOtherCompanyInfo = {...facility.otherCompanyInfo, paidAmount: value};

                // Create a deep copy of the facility object with the updated otherCompanyInfo
                return {...facility, otherCompanyInfo: updatedOtherCompanyInfo};
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

    generateSessionString(CLIENT_ID, selectedCliInfo) {
        if (this.checkPOAndLIEquality(CLIENT_ID, selectedCliInfo)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${selectedCliInfo}`;
        }
    }

    fetchCPConsentConfirmation(TrackingID) {
        const request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(getSession(CLIENT_ID), this.state.selectedCliID),
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
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
                    this.setState({poConfirmingND13: (isOpenPopupWarning ? '1' : '0')});
                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.setState({
                        openPopupWarningDecree13: false,
                    });
                    // setSession(PO_CONFIRMING_DECREE_13, '1');
                    this.setState({poConfirmingND13: '0'});
                }
            })
            .catch(error => {
                console.log(error);
            });
    }


    submitClaimType() {

        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`);
        trackingEvent("Yêu cầu quyền lợi", `Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`, `Web_Open_${PageScreen.CLAIM_KEY_IN_TREATMENT}`,);
        // Move to "Thông tin sự kiện - 2"
        let jsonState = this.state;
        const hasDeathClaim = this.state.claimCheckedMap[CLAIM_TYPE.DEATH];
        //this.props.claimCheckedMap[CLAIM_TYPE.DEATH]
        if (jsonState.currentState === CLAIM_STATE.CLAIM_TYPE && !hasDeathClaim) {
            console.log("CLAIM_STATE.CLAIM_TYPE has ND13");
            this.fetchCPConsentConfirmation();
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
        jsonState.currentState = CLAIM_STATE.CLAIM_DETAIL;
        this.setState(jsonState);
    }

    loadClaimTypeList(claimTypeList) {
        this.setState({claimTypeList: claimTypeList});
    }

    submitClaimDetail() {
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_PAYMENT}`);
        // Move to "Phương thức thanh toán"
        const jsonState = this.state;
        jsonState.currentState = CLAIM_STATE.PAYMENT_METHOD;
        this.setState(jsonState);
    }

    submitPaymentMethod() {
        // Move to "Contact"
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_CONTACT}`);

        const jsonState = this.state;
        if ((jsonState.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18)) {
            if ((jsonState.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.INIT) && !jsonState.claimTypeState.isSamePoLi) {
                jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.STEP1;
            } else {
                jsonState.currentState = CLAIM_STATE.CONTACT;
                // jsonState.currentState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
            }
        } else {
            jsonState.currentState = CLAIM_STATE.CONTACT;
            // jsonState.currentState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
        }

        this.setState(jsonState);
    }

    submitContact() {
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_ATTACH_DOCUMENT}`);

        // Move to "Kèm chứng từ"
        const jsonState = this.state;
        jsonState.currentState = CLAIM_STATE.ATTACHMENT;
        this.setState(jsonState);
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
        this.setState({currentState: CLAIM_STATE.SUBMIT, claimSubmissionState: CLAIM_STATE.INIT});
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_REVIEW}`);
    }

    submitClaimSubmission() {
        // Submit Claim
        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_SUBMISSION}`);

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
        setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(this.state));
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

    changeSubPage(currentState) {
        switch (currentState) {
            case CLAIM_STATE.INIT:
                return (<section className="sccontract-warpper">
                    <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                        <div className="breadcrums__item">
                            <p>Yêu cầu quyền lợi</p>
                            <span>&gt;</span>
                        </div>
                        <div className="breadcrums__item">
                            <p>Tạo mới yêu cầu</p>
                            <span>&gt;</span>
                        </div>
                    </div>
                    <div className="other_option" id="other-option-toggle" onClick={this.goBack}>
                        <p>Chọn thông tin</p>
                        <i><img src="../../img/icon/return_option.svg" alt=""/></i>
                    </div>
                    <div className="sccontract-container">
                        <div className="insurance">
                            <div className="empty">
                                <div className="icon">
                                    <img src="../../img/icon/empty.svg" alt=""/>
                                </div>
                                <p style={{paddingTop: '20px'}}>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
                            </div>
                        </div>
                    </div>
                </section>);

            case CLAIM_STATE.INIT_CLAIM:
                return (<section className="sccontract-warpper white-bg">
                    <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                        <div className="breadcrums__item">
                            <p>Yêu cầu quyền lợi</p>
                            <span>&gt;</span>
                        </div>
                        <div className="breadcrums__item">
                            <p>Tạo mới yêu cầu</p>
                            <span>&gt;</span>
                        </div>
                    </div>
                    <div className="other_option" id="other-option-toggle" onClick={this.goBack}>
                        <p>Chọn thông tin</p>
                        <i><img src="../../img/icon/return_option.svg" alt=""/></i>
                    </div>
                    <div className="scbenefits">
                        <div className="benefit-wrapper">
                            <form onSubmit={(event) => this.handleSubmit(event)}>
                                <div className="input disabled benefit-jobtitle">
                                    <div className="input__content" style={{paddingLeft: '6px'}}>
                                        {this.state.selectedCliInfo.DOB &&
                                            <label htmlFor="" style={{paddingLeft: '2px'}}>Ngày sinh</label>}
                                        <input type="search" name="inputDOB" id="inputDOB" required
                                               placeholder="Ngày sinh" maxLength="50"
                                               value={formatDate(this.state.selectedCliInfo.DOB)} disabled
                                        />
                                    </div>
                                </div>
                                <div className="input disabled benefit-jobtitle">
                                    <div className="input__content" style={{paddingLeft: '6px'}}>
                                        {this.state.selectedCliInfo.POID &&
                                            <label htmlFor="" style={{paddingLeft: '2px'}}>Số giấy tờ nhân
                                                thân</label>}
                                        <input type="search" name="inputOccupation" id="inputOccupation" required
                                               placeholder="Số giấy tờ nhân thân" maxLength="50"
                                               value={this.state.selectedCliInfo.POID ? this.state.selectedCliInfo.POID.trim() : ''}
                                               disabled
                                        />
                                    </div>
                                </div>
                                <div className="input benefit-jobtitle">
                                    <div className="input__content" style={{paddingLeft: '6px'}}>
                                        {this.state.initClaimState.occupation &&
                                            <label htmlFor="" style={{paddingLeft: '2px'}}>Nghề nghiệp hiện
                                                tại</label>}
                                        <input type="search" name="inputOccupation" id="inputOccupation" required
                                               placeholder="Nghề nghiệp hiện tại" maxLength="50"
                                               value={this.state.initClaimState.occupation}
                                               onChange={this.handlerInputOccupation}
                                        />
                                    </div>
                                    <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                </div>
                                <div className="benefit-contract">
                                    <h4 className="basic-semibold">Danh sách hợp đồng</h4>
                                    <PolList
                                        selectedCliId={this.state.selectedCliID}
                                        selectedCliInfo={this.state.selectedCliInfo}
                                        handlerLoadedPolList={this.handlerLoadedPolList}/>
                                </div>
                                <div className="bottom-text" style={{'maxWidth': '594px'}}>
                                    <p style={{textAlign: 'justify'}}>
                                        <span className="red-text basic-bold">Lưu ý:</span>
                                        <span style={{'display': 'block', color: '#727272'}}>
                      - Quyền lợi về Chăm sóc sức khỏe sẽ được phản hồi đến Quý khách
                      tối đa 15 ngày kể từ ngày nộp đầy đủ hồ sơ
                      và 45 ngày đối với hồ sơ cần liên hệ Bệnh viện lấy thêm thông tin.
                    </span>
                                        <span style={{'display': 'block', color: '#727272'}}>
                      - Quyền lợi về Tử vong/ Bệnh hiểm nghèo/ Thương tật và Nằm viện do Tai nạn sẽ được phản hồi đến Quý khách
                      tối đa 30 ngày kể từ ngày nộp đầy đủ hồ sơ.
                    </span>
                                        <span style={{'display': 'block', color: '#727272'}}>
                      - Tham khảo <a style={{display: 'inline'}} className="red-text basic-bold" href={CLAIM_GUID}
                                     target='_blank'>Hướng dẫn giải quyết quyền lợi bảo hiểm.</a>
                    </span>
                                    </p>
                                </div>
                                <div className="bottom-btn">
                                    <button
                                        className={((this.state.initClaimState.disabledButton === undefined) || this.state.initClaimState.disabledButton) ? "btn btn-primary disabled" : "btn btn-primary"}
                                        id="startClaimProcess"
                                        disabled={(this.state.initClaimState.disabledButton === undefined) || this.state.initClaimState.disabledButton}
                                        onClick={(event) => this.handleSubmit(event)}>Tiếp tục
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </section>);

            case CLAIM_STATE.CLAIM_TYPE:
            case CLAIM_STATE.CLAIM_DETAIL:
            case CLAIM_STATE.PAYMENT_METHOD:
            case CLAIM_STATE.CONTACT:
            case CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION:
            case CLAIM_STATE.ATTACHMENT:
            case CLAIM_STATE.SUBMIT:
            case CLAIM_STATE.DONE:
                return (((currentState === CLAIM_STATE.CLAIM_TYPE) && <ClaimType
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerChangeClaimTypeOption={this.handlerChangeClaimTypeOption}
                    handlerSubmitClaimType={this.handlerSubmitClaimType}
                    loadClaimTypeList={this.handleLoadClaimTypeList}
                    claimCheckedMap={this.state.claimCheckedMap}
                    claimTypeState={this.state.claimTypeState} selectedCliID={this.state.selectedCliID}
                    answerYes={this.handleAnswerYes}
                    answerNo={this.handleAnswerNo}/>) || ((currentState === CLAIM_STATE.CLAIM_DETAIL) &&
                    <ClaimDetail
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
                        isVietnam={this.state.isVietnam}/>) || ((currentState === CLAIM_STATE.PAYMENT_METHOD) &&
                    <PaymentMethod
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
                        paymentMethodState={this.state.paymentMethodState}/>) || ((currentState === CLAIM_STATE.CONTACT) &&
                    <ContactPersonDetail
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
                        contactState={this.state.contactState}/>) || ((currentState === CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION) &&
                    <ND13ContactFollowConfirmation
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
                        trackingId={this.state.trackingId}
                        claimId={this.state.claimId}
                        claimSubmissionState={this.state.claimSubmissionState}
                        onlyPayment={this.state.onlyPayment}
                        callBackUpdateND13ClientProfile={this.callBackUpdateND13ClientProfile}
                        callBackTrackingId={this.callBackTrackingId}
                        callBackLIWating={this.callBackLIWating}
                        liWating={this.state.liWating}
                        saveState={this.saveState}
                        cancelClaim={this.cancelClaim}
                    />) || ((currentState === CLAIM_STATE.ATTACHMENT) && <Attachment
                    handlerBackToPrevStep={this.handlerBackToPrevStep}
                    handlerUpdateMainState={this.handlerUpdateMainState}
                    handlerLoadedAttachmentData={this.handlerLoadedAttachmentData}
                    handlerSubmitAttachment={this.handlerSubmitAttachment}
                    handleSaveLocalAndQuit={this.handleSaveLocalAndQuit}
                    updateAttachmentData={this.handlerUpdateAttachmentData}
                    claimTypeState={this.state.claimTypeState}
                    claimCheckedMap={this.state.claimCheckedMap}
                    isAccidentClaim={this.state.isAccidentClaim}
                    attachmentState={this.state.attachmentState}/>) || (((currentState === CLAIM_STATE.SUBMIT) || (currentState === CLAIM_STATE.DONE) || (currentState === CLAIM_STATE.ND13_INFO_CONFIRMATION) || (currentState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18) || (currentState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18)) &&
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
                    />));
            default:
        }
    }

    goBack = () => {
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
        this.setState({saveAndQuit: true});
    }

    save = () => {
        let jsonState = this.state;
        if ((jsonState.currentState > CLAIM_STATE.INIT_CLAIM)) {
            setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + jsonState.selectedCliID, JSON.stringify(jsonState));
        }
    }

    closeToHome = () => {
        this.props.history.push("/home");
    }

    render() {
        const closeToHome = () => {
            this.props.history.push("/home");
        };

        const closeChangeSelectLI = () => {
            this.setState({changeSelectLI: false});
        };

        const closeHaveCreatingData = () => {
            this.setState({haveCreatingData: false});
        };

        const agreeChangeSelectLi = () => {
            clickOnLICardChange(this.state.changeSelectLIIndex, this.state.changeSelectLIItem);
        };

        const clickOnLICardChange = (index, item) => {
            let dataResponse = this.state;
            getLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + item.ClientID)
                .then(Res => {
                    if (Res.v) {
                        dataResponse.changeSelectLIIndex = index;
                        dataResponse.changeSelectLIItem = item;
                        dataResponse.haveCreatingData = true;
                        this.setState(dataResponse);
                        return;
                    }
                })
                .catch(error => {
                });
            // Reset state
            dataResponse.initClaimState.occupation = '';
            dataResponse.initClaimState.disabledButton = true;
            dataResponse.claimTypeState = INITIAL_STATE().claimTypeState;
            dataResponse.claimDetailState = INITIAL_STATE().claimDetailState;
            dataResponse.paymentMethodState = INITIAL_STATE().paymentMethodState;
            dataResponse.attachmentState = INITIAL_STATE().attachmentState;
            dataResponse.submissionState = INITIAL_STATE().submissionState;
            dataResponse.claimSubmissionState = INITIAL_STATE().claimSubmissionState;
            dataResponse.selectedCliID = item.ClientID;
            dataResponse.selectedCliInfo = item;
            dataResponse.liInfo = item;
            dataResponse.claimCheckedMap = {};
            const arrFulName = item.FullName.trim().split(" ");
            dataResponse.claimTypeState.isSamePoLi = (getSession(USER_LOGIN).trim() === dataResponse.selectedCliID);
            dataResponse.claimTypeState.poDisplayShortName = arrFulName.splice(arrFulName.length - 2).join(" ");
            dataResponse.currentState = CLAIM_STATE.INIT_CLAIM;
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
            setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(jsonState));
            setTimeout(25, GoHome());
        };

        const Quit = () => {
            this.setState({saveAndQuit: false});
            setTimeout(25, GoHome());
        };

        const GoHome = () => {
            this.props.history.push('/');
        };

        const closeSaveAndQuit = () => {
            this.setState({saveAndQuit: false});
        };

        const screenClaim = SCREENS.CREATE_CLAIM;
        const {id, index} = this.props.match.params;

        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{pathname: '/home'}}/>;
        }

        if (!this.state.noValidPolicy && !this.state.noPhone && !this.state.noEmail && !this.state.noVerifyPhone && !this.state.noVerifyEmail && this.state.responseLIList) {
            return (<>
                <Helmet>
                    <title>Tạo mới yêu cầu – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta name="robots" content="noindex, nofollow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL + "/myclaim"}/>
                    <meta property="og:title" content="Tạo mới yêu cầu - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/myclaim"}/>
                </Helmet>
                <main className="logined" id="main-id">
                    <div className="main-warpper basic-mainflex e-claim">
                        <section className="sccard-warpper">
                            <h5 className="basic-semibold" style={{paddingBottom: '4px'}}>Vui lòng chọn người được
                                bảo hiểm:</h5>
                            <LifeInsureList
                                responseLIList={this.state.responseLIList}
                                selectedCliID={this.state.selectedCliID}
                                selectedCliIndex={this.state.selectedCliIndex}
                                handlerClickOnLICard={this.handlerClickOnLICard}/>
                            <div className="other_option" id="other-option-toggle" onClick={this.goBack}>
                                <p>Tiếp tục</p>
                                <i><img src="../../img/icon/arrow-left.svg" alt=""/></i>
                            </div>
                        </section>
                        {this.changeSubPage(this.state.currentState)}
                    </div>
                </main>
                {this.state.changeSelectLI &&
                    <ConfirmChangePopup closePopup={() => closeChangeSelectLI()} go={() => agreeChangeSelectLi()}
                                        msg='Dữ liệu đã điền sẽ bị mất nếu đổi Người được bảo hiểm, Quý khách có đồng ý?'
                                        agreeText='Đồng ý' notAgreeText='Không đồng ý'/>}
                {this.state.haveCreatingData &&
                    <ConfirmChangePopup closePopup={() => closeHaveCreatingData()} go={() => agreeLoadCreatingLI()}
                                        msg={parse('Quý khách <span classname="basic-bold">' + (this.state.changeSelectLIItem ? this.state.changeSelectLIItem.FullName : '') + '</span> có một yêu cầu đang tạo. Quý khách có muốn tiếp tục với yêu cầu này?')}
                                        agreeText='Tiếp tục' notAgreeText='Xóa và tạo mới'
                                        notAgreeFunc={() => agreeLoadCreatingLINew()}/>}
                {this.state.saveAndQuit &&
                    <ConfirmChangePopup closePopup={() => closeSaveAndQuit()} go={() => saveAndQuit()}
                                        msg={parse('Quý khách có muốn Lưu lại yêu cầu bồi thường trước khi Thoát hay không?')}
                                        agreeText='Lưu và Thoát' notAgreeText='Thoát' notAgreeFunc={() => Quit()}/>}
                {this.state.openPopupWarningDecree13 && <POWarningND13 onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })}/>}
            </>);
        } else {
            return (<>
                <Helmet>
                    <title>Tạo mới yêu cầu – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta name="robots" content="noindex, nofollow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL + "/myclaim"}/>
                    <meta property="og:title" content="Tạo mới yêu cầu - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/myclaim"}/>
                </Helmet>
                {(this.state.noValidPolicy || !this.state.responseLIList) ? (
                    <AlertPopupHight closePopup={closeToHome}
                                     msg={'Quý Khách không còn hợp đồng nào đang hiệu lực. Vui lòng liên hệ tổng đài hoặc văn phòng Dai-ichi Life gần nhất.'}
                                     imgPath={'img/popup/no-policy.svg'}
                                     screen={SCREENS.HOME}/>) : (this.state.noPhone && this.state.noEmail ? (
                    <AlertPopupClaim closePopup={closeToHome}
                                     msg={'Quý khách chưa có Số điện thoại và Email để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                     imgPath={'img/popup/no-phone-and-email.svg'}/>) : (this.state.noPhone ? (
                    <AlertPopupClaim closePopup={closeToHome}
                                     msg={'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                     imgPath={'img/popup/no-phone.svg'}/>) : (this.state.noEmail ? (
                    <AlertPopupClaim closePopup={closeToHome}
                                     msg={'Quý khách chưa có địa chỉ Email để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                     imgPath={'img/popup/no-email.svg'}/>) : (this.state.noVerifyPhone && this.state.noVerifyEmail ? (
                    <AuthenticationPopupClaim closePopup={closeToHome}
                                              msg={'Số điện thoại và Email của Quý khách chưa được xác thực'}
                                              screen={screenClaim}/>) : (this.state.noVerifyPhone ? (
                    <AuthenticationPopupClaim closePopup={closeToHome}
                                              msg={'Số điện thoại của Quý khách chưa được xác thực'}
                                              screen={screenClaim}/>) : (this.state.noVerifyEmail ? (
                        <AuthenticationPopupClaim closePopup={closeToHome}
                                                  msg={'Email của Quý khách chưa được xác thực'}
                                                  screen={screenClaim}/>) : (
                        <AlertPopup closePopup={closeToHome}
                                    msg={'Quý khách không còn hợp đồng nào đang hiệu lực. Vui lòng liên hệ tổng đài hoặc văn phòng Dai-ichi Life gần nhất.'}
                                    imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}
                                    screen={screenClaim}/>)

                ))))))}
                {this.state.isCheckPermission && <AlertPopupClaim closePopup={this.closeNotAvailable.bind(this)}
                                                                  msg={this.state.msgCheckPermission}
                                                                  imgPath={checkPermissionIcon}/>}
                {/*-----------------------------------------------------------------------------------------------------------------------------------*/}
                {this.state.openPopupWarningDecree13 && <POWarningND13 onClickExtendBtn={() => this.setState({
                    openPopupWarningDecree13: false
                })}/>}

            </>);
        }
    }

}

export default CreateClaim;
