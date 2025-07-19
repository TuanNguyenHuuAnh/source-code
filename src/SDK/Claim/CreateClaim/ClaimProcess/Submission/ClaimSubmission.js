// import 'antd/dist/antd.min.css';

import React, {Component} from 'react';
import moment from 'moment';
import NumberFormat from 'react-number-format';
import LoadingIndicator from '../../../../LoadingIndicator2.js';
import OkPopupTitle from '../../../../components/OkPopupTitle.js';
import AgreeCancelPopup from '../../../../components/AgreeCancelPopup';
import {
    checkValue,
    formatDate,
    formatDateMMddyy,
    getDeviceId,
    getSession,
    is18Plus,
    removeLocal,
    showMessage,
    sumInvoice, sumInvoiceNew, haveCheckedDeadth, haveHC_HS,
    setSession,
    removeSession,
    getBenifits,
    isOlderThan18,
    getOperatingSystem,
    getOSVersion,
    formatDateString,
    getUrlParameter,
    cpSaveLog
} from '../../../../sdkCommon.js';

import {
    ACCESS_TOKEN,
    ADDRESS,
    AUTHENTICATION,
    CELL_PHONE,
    CLAIM_SAVE_LOCAL,
    CLAIM_TYPE,
    CLIENT_ID,
    COMPANY_KEY,
    ConsentStatus,
    EMAIL,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FULL_NAME,
    POID,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    RELOAD_BELL,
    ONLY_PAYMENT,
    NUM_OF_RETRY,
    CLAIM_STEPCODE,
    SDK_ROLE_PO,
    SDK_REQUEST_STATUS,
    SDK_ROLE_AGENT,
    SYSTEM_DCONNECT,
    IS_MOBILE,
    PageScreen,
    SIGNATURE,
    ND_13 
} from '../../../../sdkConstant.js';

import {CPConsentConfirmation, postClaimImage, postClaimInfo, CPSaveLog} from '../../../../sdkAPI.js';

import {CLAIM_STATE, TREAMENT_TYPE} from '../../CreateClaimSDK';
import ND13CancelRequestConfirm from "../../../../ModuleND13/ND13Modal/ND13CancelRequestConfirm/ND13CancelRequestConfirm.js";
import PopupSuccessfulND13 from "../../../../ModuleND13/ND13Modal/PopupSuccessfulND13.js";
import ImageViewerBase64 from "../../../../Followup/ImageViewerBase64.js";
import {isEmpty} from "lodash";
import AlertPopupND13ConfirmPayment from "../../../../components/AlertPopupND13ConfirmPayment.js";
import AlertPopupUploadAllImageFail from "../../../../components/AlertPopupUploadAllImageFail.js";

class ClaimSubmission extends Component {
    _isMounted = false;
    _countImgSuccess = 0;
    constructor(props) {
        super(props);


        this.state = {
            submitClaimImageJsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    ClientID: getSession(CLIENT_ID),
                }
            },
            submitClaimInfoJsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    DocProcessType: 'CLN',
                    UserLogin: getSession(USER_LOGIN),
                    ClientID: getSession(CLIENT_ID),
                    OS: WEB_BROWSER_VERSION
                }
            },

            stepName: this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW),//CLAIM_STATE.SUBMIT,
            popupConfirmClaimCalled: this.props.popupConfirmClaimCalled,
            isSubmitting: false,
            popupSucceededCalled: false,

            isSamePoLi: false,
            poDisplayShortName: "",
            // submissionState: {
            //     fatcaState: {
            //         isAmericanNationality: false, isAmericanResidence: false, isAmericanTax: false,
            //     }, disabledButton: false,
            // },
            submissionState:this.props.submissionState,
            jsonResponse: null,
            claimSubmissionState: this.props.claimSubmissionState,
            TrackingID: '',
            isCancelRequest: false,
            openPopupSuccessfulND13: false,
            clientProfile:  props.ND13ClientProfile,
            configClientProfile:  null,
            ClaimID: '',
            isPayment: false,
            poConfirmingND13: this.props.poConfirmingND13,
            consentDisabled: false,
            allImageFail: false,
            showRejectConfirm: false,
            showThank: false,
            showStatus: true,
            endStep: false,
            showCancelConfirm: false
        }

        this.handlerOpenPopupConfirmClaim = this.openPopupConfirmClaim.bind(this);
        this.handlerOnClickConfirmCloseButton = this.onClickConfirmCloseButton.bind(this);
        this.handlerOnChangeFatcaState = this.onChangeFatcaState.bind(this);

        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerSetCloseSucceededButtonRef = this.setCloseSucceededButtonRef.bind(this);
        this.handlerOpenPopupSucceeded = this.openPopupSucceeded.bind(this);
        this.handlerClosePopupSucceeded = this.closePopupSucceeded.bind(this);
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerBackToPrevSubmisionState = this.backToPrevSubmisionState.bind(this);
    }

    componentDidUpdate(prevProps) {
        if (this.props.clientProfile !== prevProps.clientProfile) {
            this.setState({ clientProfile: this.props.clientProfile });
        }

        if (this.props.trackingId !== prevProps.trackingId) {
            this.setState({ TrackingID: this.props.trackingId });
        }
    }

    componentDidMount() {
        cpSaveLog(`Web_Open_${PageScreen.CLAIM_REVIEW}`);
        this._isMounted = true;
        const jsonState = this.state;
        jsonState.submissionState = this.props.submissionState;
        jsonState.isSamePoLi = this.props.claimTypeState.isSamePoLi;
        jsonState.poDisplayShortName = this.props.claimTypeState.poDisplayShortName;
        if (this._isMounted) {
            this.setState(jsonState);
            if (document.getElementById('scrollAnchor')) {
                document.getElementById('scrollAnchor').scrollIntoView({behavior: 'smooth'});
            }
            // if (!this.state.configClientProfile) {
            // this.fetchCPConsentConfirmationRefresh(this.props.trackingId);
            // }
        }
        // if (!this.state.disableEdit) {
        //     alert('review');
        //     this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.EDIT, this.props.systemGroupPermission?.[0]?.Role, "");//Truyền status empty sẽ keep current status
        // }
    }
    componentWillUnmount() {
        this._isMounted = false;
    }
    
    updateState(jsonState) {
        this.setState(jsonState);
        this.props.handlerUpdateMainState("submissionState", this.state.submissionState);
    }

    calculateTotalInvoiceAmount(data) {
        let totalAmount = 0;

        // Check if facilityList exists and is an array
        if (data.facilityList && Array.isArray(data.facilityList)) {
            // Loop through each facility
            data.facilityList.forEach(facility => {
                // Only proceed if invoiceList exists and is an array
                if (facility.invoiceList && Array.isArray(facility.invoiceList)) {
                    // Loop through each invoice in the invoiceList
                    facility.invoiceList.forEach(invoice => {
                        // Check if invoiceAmount is a valid number
                        const amount = parseFloat(invoice.invoiceAmount);
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

    openPopupConfirmClaim(pinStep) {
        if (this.props.disableEdit && !pinStep) {
            if (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) {
                if ((this.state.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo.dOB)) || (!isOlderThan18(this.props.selectedCliInfo.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList)))) {
                    this.props.handleGoNextStep();
                } else {
                    this.props.submitClaimInfo();
                }
            } else {
                if ((this.state.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo.dOB)) || (!isOlderThan18(this.props.selectedCliInfo.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList)))) {
                    if (this.props.updateInfoState === ND_13.ND13_INFO_FOLLOW_CONFIRMATION) {
                        this.props.setUpdateInfoState(ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18);
                    }
                    this.props.handleGoNextStep();
                } else {
                    this.setState({endStep: true});
                }
            }
            return;
        }
        if (pinStep) {
            this.props.updatePinStep(pinStep);
        }
        document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup show";
        if (document.getElementById('fatca_id')) {
            document.getElementById('fatca_id').disabled = false;
        }

        // const jsonState = this.state;
        // // jsonState.popupConfirmClaimCalled = true;
        // this.setState(jsonState);
    }

    onClickConfirmCloseButton() {
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        if (!this.props.disableEdit) {
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

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    setCloseSucceededButtonRef(node) {
        this.closeSucceededButtonRef = node;
    }

    prepareSubmitClaimInfoRequest() {
        const jsonDataInput = [];
        const selectedCliInfo = this.props.selectedCliInfo;
        const initClaimState = this.props.initClaimState;
        const claimTypeState = this.props.claimTypeState;
        const claimDetailState = this.props.claimDetailState;
        const sickInfo = claimDetailState.sickInfo;
        const contactPersonInfo = this.props.contactState.contactPersonInfo;
        const paymentMethodState = this.props.paymentMethodState;
        const fatcaState = this.state.submissionState.fatcaState;
        const claimCheckedMap = this.props.claimCheckedMap;

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
        for (const it in claimCheckedMap) {
            if (claimCheckedMap[it]) {
                let claimBenifit = this.props.claimTypeList.filter(item => item.ClaimType === it);
                if (claimBenifit) {
                    lstBenefit.push(claimBenifit[0]);
                }
                if (!claType) {
                    claType = it;
                } else {
                    claType = claType + ';' + it;
                }
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
            let hos = this.props.hospitalList.filter(result => result.HospitalName === sickInfo.sickFoundFacility);
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
                        icdListTemp = this.props.hospitalResultList.filter(result => result.ICDName === d);
                        if (icdListTemp && (icdListTemp.length > 0)) {
                            icdList.push(icdListTemp[0]);
                        }

                    }
                }

                let hospitalCode = '';
                let hospital = this.props.hospitalList.filter(result => result.HospitalName === facility.selectedHospital);
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

        jsonDataInput['accidentDate'] = (claimDetailState.accidentInfo.date ? formatDateMMddyy(claimDetailState.accidentInfo.date) + ' ' : '') + (claimDetailState.accidentInfo.hour ? moment(claimDetailState.accidentInfo.hour).format("HH:mm") : '');
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
        jsonDataInput['lossDate'] = (claimDetailState.deathInfo.date ? formatDateMMddyy(claimDetailState.deathInfo.date) + ' ' : '') + (claimDetailState.deathInfo.hour ? moment(claimDetailState.deathInfo.hour).format("HH:mm") : '');
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
            'DoB': contactPersonInfo.dob ? formatDateMMddyy(contactPersonInfo.dob) : '',
            'email': checkValue(contactPersonInfo.email),
            'Phone': checkValue(contactPersonInfo.phone),
            'RelationShip': relationShip,
            'RelationShipCode': relationShipCode,
            'Role': role,
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

        if ((claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] || claimCheckedMap[CLAIM_TYPE.HS])) {
            //Mượn field của life để mang thông tin HC trong TH LI < 18, PO, DEATH
            if (!is18Plus(this.props.selectedCliInfo.dOB) || this.props.claimTypeState.isSamePoLi || claimCheckedMap[CLAIM_TYPE.DEATH]) {
                if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                    let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                    jsonDataInput['hcPaymentMethodId'] = "P1";
                    jsonDataInput['hcPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                    jsonDataInput['hcBankProvince'] = checkValue(cashTypeState.cityName);
                    jsonDataInput['hcBankCode'] = checkValue(cashTypeState.cityCode);
                    jsonDataInput['hcAccountName'] = checkValue(cashTypeState.receiverName);
                    jsonDataInput['hcPayIssueId'] = checkValue(cashTypeState.receiverPin);
                    jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
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
                            jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
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
                    jsonDataInput['hcPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
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

        if (claimCheckedMap[CLAIM_TYPE.TPD] || claimCheckedMap[CLAIM_TYPE.ILLNESS] || claimCheckedMap[CLAIM_TYPE.ACCIDENT] || claimCheckedMap[CLAIM_TYPE.DEATH]) {
            if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                jsonDataInput['paymentMethodId'] = "P1";
                jsonDataInput['paymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                jsonDataInput['bankProvince'] = checkValue(cashTypeState.cityName);
                jsonDataInput['accountName'] = checkValue(cashTypeState.receiverName);
                jsonDataInput['payIssueID'] = checkValue(cashTypeState.receiverPin);
                jsonDataInput['payDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
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
        if (this.props.claimId) {
            jsonDataInput['oldClaimId'] = this.props.claimId;
        }
        if (this.props.requestId) {
            jsonDataInput['claimId'] = this.props.requestId;
        }
        
        if (this.props.trackingId) {
            jsonDataInput['trackingId'] = this.props.trackingId;
        }
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),              
            platform: WEB_BROWSER_VERSION,
            system: this.state.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE),
            accessToken: this.state.apiToken
        }
        const request = buildMicroRequest(metadata, data);
        return request;
    }

    isHealthCareOrHS(claimCheckedMap, receiverName) {
        const isHealthCare = claimCheckedMap[CLAIM_TYPE.HEALTH_CARE];
        const isHS = claimCheckedMap[CLAIM_TYPE.HS];
        const fullName = getSession(FULL_NAME);

        if ((isHealthCare || isHS) && receiverName?.toLowerCase() === fullName?.toLowerCase()) {
            return true;
        } else {
            return false;
        }
    }


    prepareSubmitClaimImageRequestList(claimID, submitClaimInfoRequest) {
        const orgApiRequest = JSON.parse(JSON.stringify(this.state.submitClaimImageJsonInput));
        let jsonDataInput = orgApiRequest.jsonDataInput;
        jsonDataInput['ClaimID'] = claimID;
        let claimType = submitClaimInfoRequest.jsonDataInput.ClaimType;
        jsonDataInput['ClaimType'] = claimType;
        let docProcId = '';
        if ((claimType.indexOf(CLAIM_TYPE.HEALTH_CARE) >= 0) || (claimType.indexOf(CLAIM_TYPE.HS) >= 0)) {
            docProcId = 'CHN';
        } else {
            docProcId = 'CLN';
        }
        const result = [];
        const attachmentState = this.props.attachmentState;

        attachmentState.attachmentData.forEach((docType) => {
            let req = JSON.parse(JSON.stringify(orgApiRequest));
            let jData = req.jsonDataInput;
            jData['DocProcessID'] = docProcId;
            jData['DocTypeID'] = docType.DocID;
            jData['DocTypeName'] = docType.DocTypeName;

            docType.attachmentList.forEach((attachment, attIdx) => {
                let tReq = JSON.parse(JSON.stringify(req));
                let tJData = tReq.jsonDataInput;
                const matches = attachment.imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
                tJData['NumberOfPage'] = attIdx + '';
                tJData['DocNumber'] = attIdx + '';
                tJData['Image'] = matches.length === 3 ? matches[2] : '';
                result.push(tReq);
            });
        });
        return result;
    }

    alertSucceeded() {
        document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup show";
        const jsonState = this.state;
        jsonState.popupSucceededCalled = true;
        jsonState.popupConfirmClaimCalled = true;
        this.setState(jsonState);
        document.addEventListener('mousedown', this.handlerClosePopupSucceeded);
    }

    cpSaveLog({functionName ,Description, Exception}) {
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
                UserLogin: getSession(USER_LOGIN),
                Description,
                Exception
            }
        }
        console.log('cpSaveLog=', masterRequest);
        CPSaveLog(masterRequest).then(res => {
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    async sequentiallyPostImage(submitClaimImageRequestList, jsonState, status, claimId, trackingId) {
        const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const  DOB  = this.props.selectedCliInfo.dOB;
        this._countImgSuccess = 0;
        let count = 0;
        let reasonFail = null;
        let Exception = null;
        const TRUE = 'true';
        let requestTimes = NUM_OF_RETRY + 1;
        let totalImage = 0;
        if(submitClaimImageRequestList){
            totalImage = submitClaimImageRequestList.length
        }
        for (let i = 0; i < submitClaimImageRequestList.length; i++) {
            requestTimes = NUM_OF_RETRY + 1;// first request + num_of_retry.
            let isSuccess = false;
            while(requestTimes > 0 && !isSuccess){// while loop will break if request times = 0 or request has been successed.
                // start action request
                try {
                    const response = await postClaimImage(submitClaimImageRequestList[i]);
                    console.log(`sequentiallyPostImage[${i}]- remain retry times: ${requestTimes}`, response);
                    if ((response.SubmitPropsImageResult.Result === TRUE) && (response.SubmitPropsImageResult.Message === TRUE)) {
                        count++;
                        isSuccess = true;
                    }
                    else{
                        reasonFail = response.SubmitPropsImageResult.ErrLog;
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
                    this.cpSaveLog({functionName:"Web_Err_SubmitClaimImage"
                        , Description: `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY + 1 - requestTimes}`
                        , Exception:reasonFail});
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
            this.cpSaveLog({functionName:"Web_Succ_SubmitClaimImage"
                , Description: `Status: Success, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , Exception:reasonFail});
        } else {
            this.cpSaveLog({functionName:"Web_Err_SubmitClaimImage"
                , Description: `Status: Failed, Total_images:${totalImage}, Total_success_images: ${count}, Total_fail_images: ${totalImage - count}, Number_retry: ${NUM_OF_RETRY - requestTimes}`
                , Exception:reasonFail});
        }
        


        
        
        console.log('.... _countImgSuccess=', this._countImgSuccess);
        if (this._countImgSuccess === 0) {
            this.setState({allImageFail: true});
        } else {
            if (haveCheckedDeadth(this.props.claimCheckedMap) || this.state.consentDisabled) {
                jsonState.isSubmitting = false;
                jsonState.claimSubmissionState = CLAIM_STATE.INIT;
                jsonState.TrackingID = trackingId;
                setComponentState(jsonState);
                alertSucceeded();
                this.confirmCPConsent();
                this.props.handlerGoToStep(CLAIM_STATE.DONE);
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
            } else if ((status === 'PO_CONFIRMING_DECREE_13') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && this.isOlderThan18(DOB)) || (!this.isOlderThan18(DOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList))) ) {
                jsonState.isSubmitting = false;
                jsonState.TrackingID = trackingId;
                setComponentState(jsonState);
                this.fetchCPConsentConfirmation(trackingId);
                this.props.callBackTrackingId(trackingId);
                this.props.callBackClaimID(claimId);//ClaimID
                // this.props.saveState();
            } else {
                jsonState.isSubmitting = false;
                jsonState.claimSubmissionState = CLAIM_STATE.INIT;
                jsonState.TrackingID = trackingId;
                setComponentState(jsonState);
                alertSucceeded();
                this.confirmCPConsent();
                this.props.handlerGoToStep(CLAIM_STATE.DONE);
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
            }
            this.onClickConfirmCloseButton();
        }
    }

    async confirmCPConsent() {
        const jsonState = this.state;

        jsonState.isSubmitting = true;
        this.setState(jsonState);
        let failMessage = null;
        let Exception = null;

        const request = {
            jsonDataInput: {
                Action: "ConfirmClaim",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClaimID: this.state.ClaimID,
                TrackingID: this.state.TrackingID,
                Company: COMPANY_KEY,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
            }
        };

        const TRUE = 'true';
        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while(!isSuccess && remainRetryTimes > 0){
            await postClaimInfo(request).then(res=>{
                const Response = res.Response;
                if (Response.Result === TRUE && Response.Message !== null) {
                    console.log('confirm claim success: ', Response.Message);
                    isSuccess = true;
                }
                else{
                    failMessage = res.Response.ErrLog;
                }
            }).catch(error =>{
                console.log(error);
                Exception = error;
                failMessage = error.message;
            }).finally(()=>{
                remainRetryTimes--;
                jsonState.isSubmitting = false;
                this.setState(jsonState);

                // success or after retry ${NUM_OF_RETRY} times.
                const isWritingLog = isSuccess || remainRetryTimes <= 0;
                if(isWritingLog){
                    console.log('Writing log confirmCPConsent')
                    if (isSuccess) {
                        this.cpSaveLog({
                            functionName: "Web_Succ_ConfirmClaimData",
                            Description: `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception
                        });
                    } else {
                        this.cpSaveLog({
                            functionName: "Web_Err_ConfirmClaimData",
                            Description: `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception
                        });
                    }
                }

            });
        }
        if (getSession(ONLY_PAYMENT + this.props.selectedCliInfo.clientId)) {
            removeSession(ONLY_PAYMENT + this.props.selectedCliInfo.clientId);
        }
    }

    updateConsentResult(records) {
        records.forEach(record => {
            if (record.ConsentResult === ConsentStatus.EXPIRED) {
                record.ConsentResult = ConsentStatus.WAIT_CONFIRM;
            }
        });

        return records;
    }

    checkExpired(records) {
        for (let i = 0; i < records.length; i++) {
            if (records[i].Role === "PO" && records[i].ConsentResult === "Expired") {
                return true;
            }
        }
        return false;
    }

    addRequestParamCPConsent(records, role, answerPurpose) {
        records.forEach(record => {
            if (record) {
                if (record.Role === role) {
                    record.AnswerPurpose = answerPurpose;
                }
                record.RequesterID = getSession(CLIENT_ID);
                record.TrackingID = this.state.TrackingID?this.state.TrackingID: this.props.trackingId;
                record.RelationShip = "Self";
            }

        });

        return records;
    }
    submitCPCornfirm(data, action) {
        const request = {
            jsonDataInput: {
                Action: action,
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: "Claim",
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
                    console.log('submitCPCornfirm success.......');
                } 
            })
            .catch(error => {
            });
    }
    submitCPConsentConfirmation(data, status) {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);
        let action = 'SubmitCustomerConsent';
        let action2 = 'SubmitCustomerConsent';
        if (data.length > 1) {
            if (data[0].ConsentRuleID === 'ND13') {
                action = 'SubmitCustomerConsent';
            } else if (data[0].ConsentRuleID === 'CLAIM_PAYMENT'){
                action = 'SubmitPaymentConfirm';
            }
            this.submitCPCornfirm([data[0]], action);
            if (data[1].ConsentRuleID === 'ND13') {
                action2 = 'SubmitCustomerConsent';
            } else if (data[1].ConsentRuleID === 'CLAIM_PAYMENT'){
                action2 = 'SubmitPaymentConfirm';
            }
            const request = {
                jsonDataInput: {
                    Action: action2,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    Company: COMPANY_KEY,
                    ProcessType: "Claim",
                    DeviceId: getDeviceId(),
                    OS: WEB_BROWSER_VERSION,
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    ConsentSubmit: [data[1]],
                }
            };
    
            CPConsentConfirmation(request)
                .then(res => {
                    const Response = res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                        jsonState.isSubmitting = false;
                        if (status ===  "Parent") {
                            this.confirmCPConsent();
                            document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";
    
                            const jsonState = {...this.state};
                            let fatcaState = jsonState.submissionState.fatcaState;
                            fatcaState['isAmericanNationality'] = false;
                            fatcaState['isAmericanResidence'] = false;
                            fatcaState['isAmericanTax'] = false;
                            this.setState(jsonState);
                            // this.props.callBackTrackingId(this.state.TrackingID);
                            // this.props.callBackClaimID(this.state.ClaimID);
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
                        } else {
                            jsonState.openPopupSuccessfulND13 = true;
                            jsonState.clientProfile = data[0];
                            this.props.callBackUpdateND13State(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                            this.props.callBackConfirmation(CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18);
                            // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(this.state));
                            this.setState(jsonState);
    
                            // this.props.callBackTrackingId(this.state.TrackingID);
                            this.props.callBackClaimID(this.state.ClaimID);
                            this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        }
                        setTimeout(() => {
                            setSession(RELOAD_BELL, RELOAD_BELL);
                        }, 5000);
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
        } else if (data.length === 1){
            // console.log('data.length='+ data.length);
            let action = 'SubmitCustomerConsent';
            if (data[0].ConsentRuleID === 'ND13') {
                action = 'SubmitCustomerConsent';
            } else if (data[0].ConsentRuleID === 'CLAIM_PAYMENT'){
                action = 'SubmitPaymentConfirm';
            }
            const request = {
                jsonDataInput: {
                    Action: action,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    Company: COMPANY_KEY,
                    ProcessType: "Claim",
                    DeviceId: getDeviceId(),
                    OS: WEB_BROWSER_VERSION,
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    ConsentSubmit: data,
                }
            };
            console.log('request=', request);
            CPConsentConfirmation(request)
                .then(res => {
                    const Response = res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                        jsonState.isSubmitting = false;
                        if (status ===  "Parent") {
                            this.confirmCPConsent();
                            document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";
    
                            const jsonState = {...this.state};
                            let fatcaState = jsonState.submissionState.fatcaState;
                            fatcaState['isAmericanNationality'] = false;
                            fatcaState['isAmericanResidence'] = false;
                            fatcaState['isAmericanTax'] = false;
                            this.setState(jsonState);
                            // this.props.callBackTrackingId(this.state.TrackingID);
                            // this.props.callBackClaimID(this.state.ClaimID);
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
                        } else {
                            jsonState.openPopupSuccessfulND13 = true;
                            jsonState.clientProfile = data[0];
                            this.props.callBackUpdateND13State(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                            this.props.callBackConfirmation(CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18);
                            // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(this.state));
                            this.setState(jsonState);
    
                            // this.props.callBackTrackingId(this.state.TrackingID);
                            this.props.callBackClaimID(this.state.ClaimID);
                            this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        }
                        setTimeout(() => {
                            setSession(RELOAD_BELL, RELOAD_BELL);
                        }, 5000);
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
        } else {
            console.log('data.length='+ data.length);
            this.confirmCPConsent();
            document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";

            const jsonState = {...this.state};
            let fatcaState = jsonState.submissionState.fatcaState;
            fatcaState['isAmericanNationality'] = false;
            fatcaState['isAmericanResidence'] = false;
            fatcaState['isAmericanTax'] = false;
            this.setState(jsonState);
            // this.props.callBackTrackingId(this.state.TrackingID);
            // this.props.callBackClaimID(this.state.ClaimID);
            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
            // setSession(RELOAD_BELL, RELOAD_BELL);
            setTimeout(() => {
                setSession(RELOAD_BELL, RELOAD_BELL);
            }, 5000);
        }

    }

    submitCPConsentConfirmationOnlyPayment(data) {
        const jsonState = this.state;

        jsonState.isSubmitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitPaymentConfirm",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: "Claim",
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
                    jsonState.isSubmitting = false;
                    this.confirmCPConsent();
                    document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";

                    let fatcaState = jsonState.submissionState.fatcaState;
                    fatcaState['isAmericanNationality'] = false;
                    fatcaState['isAmericanResidence'] = false;
                    fatcaState['isAmericanTax'] = false;
                    this.setState(jsonState);
                    // this.props.callBackClaimID(this.state.ClaimID);
                    removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
                    // setSession(RELOAD_BELL, RELOAD_BELL);
                    setTimeout(() => {
                        setSession(RELOAD_BELL, RELOAD_BELL);
                    }, 5000);
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

    submitCPConsentConfirmationOver18(data) {
        const jsonState = this.state;

        jsonState.isSubmitting = true;
        this.setState(jsonState);
        let action = 'SubmitCustomerConsent';
        let action2 = 'SubmitCustomerConsent';
        if (data.length > 1) {
            if (data[0].ConsentRuleID === 'ND13') {
                action = 'SubmitCustomerConsent';
            } else if (data[0].ConsentRuleID === 'CLAIM_PAYMENT'){
                action = 'SubmitPaymentConfirm';
            }
            this.submitCPCornfirm([data[0]], action);
            if (data[1].ConsentRuleID === 'ND13') {
                action2 = 'SubmitCustomerConsent';
            } else if (data[1].ConsentRuleID === 'CLAIM_PAYMENT'){
                action2 = 'SubmitPaymentConfirm';
            }
            const request = {
                jsonDataInput: {
                    Action: action2,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    Company: COMPANY_KEY,
                    ProcessType: "Claim",
                    DeviceId: getDeviceId(),
                    OS: WEB_BROWSER_VERSION,
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    ConsentSubmit: [data[1]],
                }
            };
    
            CPConsentConfirmation(request)
                .then(res => {
                    const Response = res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                        jsonState.isSubmitting = false;
                        //const jsonState = {...this.state};
                        jsonState.openPopupSuccessfulND13 = true;
                        jsonState.clientProfile = this.updateData(jsonState.clientProfile, data[0]);
                        this.props.callBackUpdateND13State(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        this.props.callBackConfirmation(CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18);
                        // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(jsonState));
                        // this.props.saveState();
                        this.setState(jsonState);
                        // this.props.callBackTrackingId(this.state.TrackingID);
                        this.props.callBackClaimID(this.state.ClaimID);
                        this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        this.setState(jsonState);
                        // setSession(RELOAD_BELL, RELOAD_BELL);
                        setTimeout(() => {
                            setSession(RELOAD_BELL, RELOAD_BELL);
                        }, 5000);
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
    
        } else {
            let action = 'SubmitCustomerConsent';
            if (data[0].ConsentRuleID === 'ND13') {
                action = 'SubmitCustomerConsent';
            } else if (data[0].ConsentRuleID === 'CLAIM_PAYMENT'){
                action = 'SubmitPaymentConfirm';
            }
            const request = {
                jsonDataInput: {
                    Action: action,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    Company: COMPANY_KEY,
                    ProcessType: "Claim",
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
                        jsonState.isSubmitting = false;
                        //const jsonState = {...this.state};
                        jsonState.openPopupSuccessfulND13 = true;
                        jsonState.clientProfile = this.updateData(jsonState.clientProfile, data[0]);
                        this.props.callBackUpdateND13State(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        this.props.callBackConfirmation(CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18);
                        // setLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.state.selectedCliID, JSON.stringify(jsonState));
                        // this.props.saveState();
                        this.setState(jsonState);
                        // this.props.callBackTrackingId(this.state.TrackingID);
                        this.props.callBackClaimID(this.state.ClaimID);
                        this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
                        this.setState(jsonState);
                        // setSession(RELOAD_BELL, RELOAD_BELL);
                        setTimeout(() => {
                            setSession(RELOAD_BELL, RELOAD_BELL);
                        }, 5000);
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
    }

    submitCPConsentConfirmationPO(data) {
        const jsonState = this.state;
        jsonState.isSubmitting = true;
        this.setState(jsonState);

        const request = {
            jsonDataInput: {
                Action: "SubmitCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: "Claim",
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
                    jsonState.isSubmitting = false;
                    if (this.checkPOAndLIEquality(getSession(CLIENT_ID), this.props.selectedCliInfo)) {
                        this.confirmCPConsent();
                        document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";

                        //const jsonState = {...this.state};
                        let fatcaState = jsonState.submissionState.fatcaState;
                        fatcaState['isAmericanNationality'] = false;
                        fatcaState['isAmericanResidence'] = false;
                        fatcaState['isAmericanTax'] = false;
                        this.setState(jsonState);
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
                        // setSession(RELOAD_BELL, RELOAD_BELL);
                        setTimeout(() => {
                            setSession(RELOAD_BELL, RELOAD_BELL);
                        }, 5000);
                    } else {
                        this.fetchCPConsentConfirmation(this.state.TrackingID);
                    }
                    this.setState(jsonState);
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

    checkPOAndLIEquality(PO, LI) {
        const clientIDLI = LI.ClientID;
        const poIDPO = PO;
        return clientIDLI === poIDPO;
    }

    generateSessionString(CLIENT_ID, selectedCliInfo)  {
        if (this.checkPOAndLIEquality(CLIENT_ID, selectedCliInfo)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${selectedCliInfo?.ClientID}`;
        }
    }

    // removeDuplicateCustomers(data) {
    //     return data.filter((item, index, self) =>
    //             index === self.findIndex((t) => (
    //                 t.CustomerID === item.CustomerID
    //             ))
    //     );
    // }

    fetchCPConsentConfirmation(TrackingID) {
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(getSession(CLIENT_ID), this.props.selectedCliInfo),
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ? TrackingID : this.state.TrackingID,
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const { DOB } = this.props.selectedCliInfo;
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
                    if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                        const claimSubmissionState = CLAIM_STATE.ND13_INFO_CONFIRMATION;
                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile,
                        });
                        this.props.callBackUpdateND13State(claimSubmissionState);
                        this.props.callBackUpdateND13ClientProfile(clientProfile);
                    } else if (consentResulLI === ConsentStatus.WAIT_CONFIRM || consentResulLI === ConsentStatus.EXPIRED || consentResulLI === ConsentStatus.DECLINED) {
                        const isOver18 = this.isOlderThan18(DOB);
                        const claimSubmissionState = isOver18
                            ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
                            : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;

                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile,
                        });
                        this.props.callBackUpdateND13ClientProfile(clientProfile);
                        this.props.callBackUpdateND13State(claimSubmissionState);
                    } else if (!this.state.isPayment) {
                        this.setState({isPayment: true});
                        const isOver18 = this.isOlderThan18(DOB);
                        const claimSubmissionState = isOver18
                            ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
                            : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;
                      
                        this.setState({
                            claimSubmissionState,
                            clientProfile,
                            configClientProfile
                        });
                        this.props.callBackUpdateND13State(claimSubmissionState);
                        this.props.callBackUpdateND13ClientProfile(clientProfile);
                    } else {
                        this.confirmCPConsent();
                        this.setState({ claimSubmissionState: CLAIM_STATE.INIT });
                        this.alertSucceeded();
                        this.props.handlerGoToStep(CLAIM_STATE.DONE);
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
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

    fetchCPConsentConfirmationRefresh(TrackingID) {
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: this.generateSessionString(getSession(CLIENT_ID), this.props.selectedCliInfo),
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ? TrackingID : this.state.TrackingID,
            }
        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                console.log('Response claimsubmis=', Response);
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;
                    const isOpenPopupWarning = ((Response.ClientProfile.length === 1 && consentResultPO !== ConsentStatus.AGREED) || (Response.ClientProfile.length > 1 && (consentResultPO !== ConsentStatus.AGREED || consentResulLI !== ConsentStatus.AGREED)));
                    this.setState({consentDisabled: false, configClientProfile: Response.Config, clientProfile: Response.ClientProfile, poConfirmingND13: isOpenPopupWarning ? '1' : '0'})
                    this.props.callPOConfirmingND13(isOpenPopupWarning ? '1' : '0');

                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.setState({consentDisabled: true, poConfirmingND13: '0'})
                } else {
                    console.log("System error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
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
        const  DOB  = this.props.selectedCliInfo.dOB;
        if (getSession(ONLY_PAYMENT + this.props.selectedCliInfo.clientId)) {
            removeSession(ONLY_PAYMENT + this.props.selectedCliInfo.clientId);
        }

        let Exception = null;
        let failMessage = null;
        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while(!isSuccess && remainRetryTimes > 0){
            await saveClaimRequest(submitClaimInfoRequest).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    isSuccess = true;
                    let claimID = Res.data?.[0]?.claimId;
                    jsonState.ClaimID = claimID;
                    jsonState.TrackingID = Res.data?.[0]?.trackingId;
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
            }).finally(()=>{
                remainRetryTimes--;
                // success or after retry ${NUM_OF_RETRY} times.
                const isWritingLog = isSuccess || remainRetryTimes <= 0;
                if(isWritingLog){
                    console.log('Writing log submitClaimInfo')
                    if (isSuccess) {
                        this.cpSaveLog({
                            functionName: "Web_Succ_SubmitClaimData",
                            Description: `Status: Success, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception
                        });
                    } else {
                        this.cpSaveLog({
                            functionName: "Web_Err_SubmitClaimData",
                            Description: `Status: Failed, Number_retry:${NUM_OF_RETRY - remainRetryTimes}`,
                            Exception
                        });
                    }
                }
            });
        }
    }

    async openPopupSucceeded() {
        if (this.state.poConfirmingND13 === '1') {
            this.submitClaimInfo('PO_CONFIRMING_DECREE_13');
            
        } else {
            this.submitClaimInfo();
        }
        //this.submitClaimInfo('PO_CONFIRMING_DECREE_13');
    }

    closePopupSucceeded(event) {
        if ((this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) || (this.closeSucceededButtonRef && this.closeSucceededButtonRef.contains(event.target))) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup";
            document.removeEventListener('mousedown', this.handlerClosePopupSucceeded);

            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
            this.props.closeToHome();
        }
    }

    closePopupSucceededRedirect(event) {
        // this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
        window.location.href = '/followup-claim-info';
        this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.DONE));
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
    }

    onChangeFatcaState(event) {
        const jsonState = this.state;
        const fatcaState = jsonState.submissionState.fatcaState;
        const target = event.target;
        fatcaState[target.id] = target.checked;
        this.setState(jsonState);
    }

    updateData = (existingData, newData) => {
        const index = existingData.findIndex(item => item.Role === "LI");
        if (index !== -1) {
            existingData[index] = newData;
        } else {
            existingData.push(newData);
        }
        return existingData;
    }

    backToPrevSubmisionState(claimSubmissionState) {
        const jsonState = this.state;
        if (claimSubmissionState === CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION) {
            const newClaimSubmissionState = this.isOlderThan18(this.props.selectedCliInfo.dOB)
            ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
            : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;
            jsonState.isPayment = false;
            jsonState.claimSubmissionState = newClaimSubmissionState;
            this.setState(jsonState);
            this.props.callBackUpdateND13State(newClaimSubmissionState);
            this.props.handlerGoToStep(CLAIM_STATE.SUBMIT);
        } else if ((claimSubmissionState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18) || (claimSubmissionState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18) || (claimSubmissionState === CLAIM_STATE.ND13_INFO_CONFIRMATION)) {
            jsonState.claimSubmissionState = CLAIM_STATE.INIT;
            jsonState.isPayment = false;
            this.setState(jsonState);
            this.props.callBackUpdateND13State(CLAIM_STATE.INIT);
        }

    }

    render() {
        console.log('this.props.systemGroupPermission?.[0]?.Role=' + this.props.systemGroupPermission?.[0]?.Role);
        console.log('review disableEdit=', this.props.disableEdit);
        if (this.state.claimSubmissionState !== this.props.claimSubmissionState) {
            this.setState({claimSubmissionState: this.props.claimSubmissionState});
        }
        const claimTypeState = this.props.claimTypeState;
        const claimDetailState = this.props.claimDetailState;
        const contactState = this.props.contactState;

        const sickInfo = claimDetailState.sickInfo;
        const facilityList = claimDetailState.facilityList;
        const accidentInfo = claimDetailState.accidentInfo;
        const deathInfo = claimDetailState.deathInfo;
        const contactPersonInfo = contactState.contactPersonInfo;
        const paymentMethodState = this.props.paymentMethodState;
        const healthCareBenState = paymentMethodState.healthCareBenState;
        const lifeBenState = paymentMethodState.lifeBenState;

        const attachmentState = this.props.attachmentState;
        const jsonState = this.state;
        const fatcaState = jsonState.submissionState.fatcaState;

        const claimCheckedMap = this.props.claimCheckedMap;

        let benefits = '';
        for (const it in claimCheckedMap) {
            if (claimCheckedMap[it]) {
                if (!benefits) {
                    benefits = claimCheckedMap[it];
                } else {
                    benefits = benefits + ',' + claimCheckedMap[it];
                }
            }
        }

        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }

        const closeStatus=()=> {
            this.setState({showStatus: false});
        }
        const notAgree = () => {
            // this.props.setCurrentState(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
            setShowRejectConfirm(true);
        }

        const setShowRejectConfirm = (value) => {
            this.setState({ showRejectConfirm: value });
        }

        const rejectClaim=()=> {
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REJECT_CLAIM, SDK_ROLE_PO, "", true);//Truyền status empty sẽ keep current status
            setShowRejectConfirm(false);
            setShowThank(true);

        }

        const rejectClaimPOEditOn=()=> {
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REJECT_CLAIM, SDK_ROLE_PO, "", true);//Truyền status empty sẽ keep current status
            setTimeout(() => {
                this.props.closeToHome();
            }, 100);
        }

        const requestEdit=(event)=> {
            // event.preventDefault();
            setShowRejectConfirm(false);
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REEDIT, SDK_ROLE_AGENT, "", false, true);//Truyền status empty sẽ keep current status
            // setShowThank(true);
        }

        const cancelConfirm=()=> {
            this.setState({showCancelConfirm: true});
        }

        const closeCancelConfirm=()=> {
            this.setState({showCancelConfirm: false});
        }

        const setShowThank=(value)=> {
            this.setState({showThank: value});
            if (!value) {
                this.props.closeToHome();
            }
        }

        // Create a reusable function:
        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

        const filterAttachments = (documents) => {
            let documentsWithAttachments = [];
            documents.forEach(doc => {
                if (doc.attachmentList.length > 0) {
                    documentsWithAttachments.push(doc);
                }
            });

            return documentsWithAttachments;
        }

        const updateData = (existingData, newData) => {
            const index = existingData.findIndex(item => item.Role === "LI");
            if (index !== -1) {
                existingData[index] = newData;
            } else {
                existingData.push(newData);
            }
            return existingData;
        }

        const closeConfirmPayment = () => {
            this.setState({confirmPayment: false});
        }

        const closeAllImageFail = () => {
            this.setState({allImageFail: false});
            window.location.href = '/myclaim';
        }
        
        const clearRequest = () => {
            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.clientId);
            setTimeout(() => {
                window.location.href = '/';
            }, 100);
        }

        const styles = {
            icon: {
                marginRight: '10px',
            }, text: {
                fontSize: '13px', fontWeight: 500, lineHeight: '19px', textAlign: 'left', color: '#ffffff',
            },
            conClose: {
                cursor: 'pointer'
            }
        };
        // alert(!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit));
        // alert(this.props.disableEdit + '|' + this.props.agentKeyInPOSelfEdit);
        return (<>
            <section className="sccontract-warpper submision" id="scrollAnchor">
                <div><LoadingIndicator area="submit-init-claim"/></div>
                <div className="insurance">
                    {/* {this.state.showStatus && */}
                    <div className={getSession(IS_MOBILE)?"heading padding-heading-mobile": (((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || (this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit))?"heading max-height80": "heading max-height160")}>
                        {(this.props.sourceSystem === 'DConnect') &&
                        !getSession(IS_MOBILE) &&
                        <div className="breadcrums">
                        <div className="breadcrums__item">
                            <p>Yêu cầu quyền lợi</p>
                            <span>&gt;</span>
                        </div>
                        <div className="breadcrums__item">
                            <p>Tạo mới yêu cầu</p>
                            <span>&gt;</span>
                        </div>
                        </div>
                        }
                        {!getSession(IS_MOBILE) &&
                        <div className="other_option" id="other-option-toggle" onClick={()=>goBack()}>
                        <p>Chọn thông tin</p>
                        <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
                        </div>
                        }
                        {this.props.disableEdit && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && getSession(IS_MOBILE) &&
                        <div className='agent-back'>
                            <i onClick={() => this.props.closeToHome()}><img src={`${FE_BASE_URL}/img/btn-back-agent.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                            <p className='viewer-back-title'>Chi tiết hồ sơ YCQL</p>
                        </div>
                        }
                        {this.props.disableEdit && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && getSession(IS_MOBILE) &&
                        <div className={getUrlParameter("fromApp") ? "heading__tab mobile margin-top56" : "heading__tab"}>
                            <div className="step-container">
                                <div className="step-wrapper">
                                    <div className="step-btn-wrapper">
                                        <div className="back-btn"
                                                >
                                            <button>
                                                <div className="svg-wrapper" onClick={() => this.props.closeToHome()}>
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
												<p style={{textAlign: 'center', minWidth: '200%', fontWeight: '700'}}>Yêu cầu Quyền lợi bảo hiểm</p>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        }
                        {!this.props.disableEdit &&
                        <div className={getUrlParameter("fromApp") ? "heading__tab mobile" : "heading__tab"}>
                            <div className="step-container">
                                <div className="step-wrapper">
                                    <div className="step-btn-wrapper">
                                        <div className="back-btn"
                                                >
                                            <button>
                                                <div className="svg-wrapper" onClick={() => this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>
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
                                                    <span className="simple-brown" onClick={() => this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Quay lại</span>
                                                ):(
                                                    <p style={{textAlign: 'center', paddingLeft: '16px', minWidth: '250%', fontWeight: '700'}}>Tạo mới yêu cầu</p>
                                                )}
                                            </button>
                                        </div>
                                        {getSession(IS_MOBILE) &&
                                            <div className="step-btn-save-quit">
                                                <div className='save-quit-wrapper'>
                                                    <button>
                                                    <span className="simple-brown" style={{zIndex: '30'}}  onClick={this.props.handleSaveLocalAndQuit}
                                                            >Lưu & thoát</span>
                                                    </button>
                                                </div>
                                            </div>
                                        }
                                    </div>
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
                                            className={(this.state.stepName >= this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW)) ? "step active" : "step"}>
                                            <div className="bullet">
                                                <span>4</span>
                                            </div>
                                            <p>Hoàn tất yêu cầu</p>
                                        </div>
                                    </div>
                                    {!getSession(IS_MOBILE) &&
                                        <div className="step-btn-save-quit">
                                            <div className='save-quit-wrapper'>
                                                <button>
                                                <span className="simple-brown" style={{zIndex: '30'}}  onClick={this.props.handleSaveLocalAndQuit}
                                                        >Lưu & thoát</span>
                                                </button>
                                            </div>
                                        </div>
                                    }
                                </div>
                    
                            </div>
                        </div>
                        }
                        {(this.state.showStatus && this.props.disableEdit && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO)) &&
                        <div style={{display: 'flex', textAlign: 'center', alignItems: 'center', justifyContent: 'center'}}>
                        <div className="follow-confirm-wrapper" style={{margin: '16px 0'}}>
                            <img style={styles.icon} src={FE_BASE_URL + '/img/icon/iconStatusBell.svg'} alt="bell" />
                            <div>
                                <p style={styles.text}>Quý khách vui lòng kiểm tra và xác nhận các thông tin được kê khai trong yêu cầu này.</p>
                                <p style={styles.text}>Yêu cầu trực tuyến này sẽ tự động hủy sau 24h nếu không được xác nhận.</p>
                            </div>
                            <img width={'12px'} height={'12px'} onClick={()=>closeStatus()} style={styles.conClose} src={FE_BASE_URL + '/img/icon/iconStatusClose.svg'} alt="close" />
                        </div>
                        </div>
                        }
                    </div>
                    {getSession(IS_MOBILE) &&
                    <div className={(getUrlParameter("fromApp") === 'IOS')?(this.props.disableEdit?'padding-top96 margin-top112':'padding-top64 margin-top112'): (this.props.disableEdit?'padding-top96 margin-top56':'padding-top64 margin-top56')}></div>
                    }
                    {this.props.systemGroupPermission?.[0]?.Role === 'AGENT' &&
                        <div className='ndbh-info'>
                        <div style={{ display: 'flex' }}>
                            <p>Tên NĐBH:</p>
                            <p className='bold-text'>{this.props.selectedCliInfo?.fullName}</p>
                        </div>
                        <div style={{ display: 'flex' }}>
                            <p>Quyền lợi:</p>
                            <p className='bold-text'>{getBenifits(this.props.claimCheckedMap)}</p>
                        </div>
                        </div>
                    }
                    <div className={this.state.showStatus? "stepform" : "stepform margin-top56"}>
                        <div className="stepform__body">
                            {/* THÔNG TIN YÊU CẦU */}
                            {(this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || this.props.disableEdit ? (
                            <div className="info">
                                <div className="info__title">
                                    <h4>THÔNG TIN YÊU CẦU</h4>
                                </div>
                                <div className="info__body">
                                    <div className="tabflex-wrapper">
                                        <div className="tabflex">
                                            <h5>Mã yêu cầu</h5>
                                            {this.props.requestId && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.requestId}</p>}
                                        </div>
                                        {this.props.effectiveDate &&
                                        <div className="tabflex">
                                            <h5>Ngày nộp</h5>
                                            {this.props.effectiveDate && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{formatDateString(this.props.effectiveDate)}</p>}
                                        </div>
                                        }
                                        <div className="tabflex">
                                            <h5>Người được bảo hiểm</h5>
                                            {this.props.selectedCliInfo.fullName && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.selectedCliInfo.fullName}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Ngày sinh</h5>
                                            {this.props.selectedCliInfo.dOB && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{formatDate(this.props.selectedCliInfo.dOB)}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Số giấy tờ nhân thân</h5>
                                            {this.props.selectedCliInfo.idNum && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.selectedCliInfo.idNum.trim()}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Nghề nghiệp</h5>
                                            {this.props.initClaimState.occupation && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.initClaimState.occupation}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Quyền lợi được chọn</h5>
                                            <p style={{maxWidth: "300px", textAlign: "right"}}>{benefits}</p>
                                        </div>
                                    </div>
                                </div>

                                {(this.props.disableEdit || (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT)) &&
                                <>
                                {/*Thông tin đại lý */}
                                <div className="info__title">
                                    <h4>THÔNG TIN ĐẠI LÝ BẢO HIỂM</h4>
                                </div>
                                <div className="info__body">
                                    <div className="tabflex-wrapper">
                                        <div className="tabflex">
                                            <h5>Họ và tên</h5>
                                            {this.props.agentName && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.agentName}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Mã số Đại lý bảo hiểm</h5>
                                            {this.props.agentCode && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.agentCode}</p>}
                                        </div>
                                        <div className="tabflex">
                                            <h5>Số điện thoại</h5>
                                            {this.props.agentPhone && <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{this.props.agentPhone}</p>}
                                        </div>
                                    </div>
                                </div>
                                </>
                                }
                            </div>
                            ):(
                                <div className="info">
                                    <div className="info__title">
                                        <h4>QUYỀN LỢI YÊU CẦU BẢO HIỂM</h4>
                                    </div>
                                    <div className="info__body">
                                        <div className="tabflex-wrapper">
                                            <div className="tabflex">
                                                <h5>Người được bảo hiểm</h5>
                                                {this.props.selectedCliInfo.fullName && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{this.props.selectedCliInfo.fullName}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Ngày sinh</h5>
                                                {this.props.selectedCliInfo.dOB && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{formatDate(this.props.selectedCliInfo.dOB)}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Số giấy tờ nhân thân</h5>
                                                {this.props.selectedCliInfo.idNum && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{this.props.selectedCliInfo.idNum.trim()}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Nghề nghiệp</h5>
                                                {this.props.initClaimState.occupation && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{this.props.initClaimState.occupation}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Quyền lợi được chọn</h5>
                                                <p style={{maxWidth: "300px", textAlign: "right"}}>{benefits}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                            <div className="top-dash-line-short-margin"></div>
                            {/* THÔNG TIN SỰ KIỆN */}
                            <div className="info">
                                <div className="info__title">
                                    <h4>THÔNG TIN SỰ KIỆN BẢO HIỂM</h4>
                                    {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                    <span className="update-btn simple-brown4"
                                            onClick={() => this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL), this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                    }
                                </div>
                                <div className="info__body">
                                    <div className="item">
                                        <div className="item__content">
                                            <div className="tab">
                                                {/* Sự kiện bảo hiểm 1 */}
                                                {!claimTypeState.isAccidentClaim && <>
                                                    <div className="tab__content">
                                                        <div className="input disabled">
                                                            <div className="input__content">
                                                                <label>Sự kiện bảo hiểm</label>
                                                                <input placeholder="Bệnh" disabled
                                                                        type="search"/>
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"}
                                                                    alt=""/></i>
                                                        </div>
                                                    </div>
                                                    <div className="tab__content">
                                                        <div className="yellow-dropdown dropdown show"
                                                                id="sickDetailDropdown">
                                                            <div className="dropdown__content"
                                                                    onClick={(e) => {
                                                                        document.getElementById('sickDetailDropdown').className === 'yellow-dropdown dropdown' ? document.getElementById('sickDetailDropdown').className = 'yellow-dropdown dropdown show' : document.getElementById('sickDetailDropdown').className = 'yellow-dropdown dropdown'
                                                                    }}>
                                                                <p className="simple-brown basic-semibold">Chi
                                                                    tiết</p>
                                                                <span
                                                                    className="icon simple-brown basic-semibold">&gt;</span>
                                                            </div>
                                                            <div className="dropdown__items">
                                                                <div className="yellowtab">
                                                                    <div className="tab">
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>Thời điểm khởi phát bệnh</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{sickInfo.sickFoundTime ? formatDate(sickInfo.sickFoundTime) : ''}</p>
                                                                    </div>

                                                                    <div className="tab">
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>Triệu chứng/ chẩn đoán đầu
                                                                            tiên</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{sickInfo.firstSympton}</p>
                                                                    </div>

                                                                    <div className="tab">
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px"
                                                                        }}>Cơ sở y tế phát hiện bệnh</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{sickInfo.sickFoundFacility}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </>}
                                                {/* Sự kiện bảo hiểm 2 */}
                                                {claimTypeState.isAccidentClaim && <>
                                                    <div className="tab__content">
                                                        <div className="input disabled">
                                                            <div className="input__content">
                                                                <label>Sự kiện bảo hiểm</label>
                                                                <input placeholder="Tai nạn" disabled
                                                                        type="search"/>
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"}
                                                                    alt=""/></i>
                                                        </div>
                                                    </div>
                                                    <div className="tab__content">
                                                        <div className="yellow-dropdown dropdown show"
                                                                id="accidentDetailDropdown">
                                                            <div className="dropdown__content"
                                                                    onClick={(e) => {
                                                                        document.getElementById('accidentDetailDropdown').className === 'yellow-dropdown dropdown' ? document.getElementById('accidentDetailDropdown').className = 'yellow-dropdown dropdown show' : document.getElementById('accidentDetailDropdown').className = 'yellow-dropdown dropdown'
                                                                    }}>
                                                                <p className="simple-brown basic-semibold">Chi
                                                                    tiết</p>
                                                                <span
                                                                    className="icon simple-brown basic-semibold">&gt;</span>
                                                            </div>
                                                            <div className="dropdown__items">
                                                                <div className="yellowtab">
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Diễn
                                                                            biến tai
                                                                            nạn</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{accidentInfo.accidentDescription}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Thời
                                                                            gian</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{accidentInfo.date ? formatDate(accidentInfo.date) : ''} {accidentInfo.hour ? moment(accidentInfo.hour).format("HH:mm") : ""}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Nơi xảy
                                                                            ra tai
                                                                            nạn</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{this.props.isVietnam ? 'Việt Nam' : ''}</p>
                                                                    </div>
                                                                    {this.props.isVietnam ? (<>
                                                                        <div className="tab">
                                                                            <p className="simple-brown">-
                                                                                Tỉnh/Thành phố</p>
                                                                            <p className="simple-brown"
                                                                                style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right"
                                                                                }}>{accidentInfo.cityName}</p>
                                                                        </div>
                                                                        <div className="tab">
                                                                            <p className="simple-brown">-
                                                                                Quận/Huyện</p>
                                                                            <p className="simple-brown"
                                                                                style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right"
                                                                                }}>{accidentInfo.districtName}</p>
                                                                        </div>
                                                                    </>) : (<div className="tab">
                                                                        <p className="simple-brown">-
                                                                            Quốc
                                                                            gia</p>
                                                                        <p className="simple-brown"
                                                                            style={{
                                                                                maxWidth: "300px", textAlign: "right"
                                                                            }}>{accidentInfo.selectedNation}</p>
                                                                    </div>)}

                                                                    <div className="tab">
                                                                        <p className="simple-brown">- Địa
                                                                            chỉ cụ
                                                                            thể</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>
                                                                            {accidentInfo.address + (accidentInfo.districtName !== null && accidentInfo.districtName !== undefined && accidentInfo.districtName !== "" ? ", " + accidentInfo.districtName : "") + (accidentInfo.cityName !== null && accidentInfo.cityName !== undefined && accidentInfo.cityName !== "" ? ", " + accidentInfo.cityName : "")}</p>
                                                                    </div>
                                                                    {accidentInfo.healthStatus &&
                                                                        <div className="tab">
                                                                            <p className="simple-brown">Tình
                                                                                trạng
                                                                                thương tật/sức khỏe hiện tại
                                                                                của
                                                                                người được bảo hiểm</p>
                                                                            <p className="simple-brown"
                                                                                style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right",
                                                                                    width: '50%'
                                                                                }}>{accidentInfo.healthStatus}</p>
                                                                        </div>}
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Sau tai
                                                                            nạn,
                                                                            người được bảo hiểm có được
                                                                            khám/điều
                                                                            trị tại cơ sở y tế ?</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px",
                                                                            textAlign: "right",
                                                                            width: '50%'
                                                                        }}>{(this.props.claimDetailState.isTreatmentAt !== null) && this.props.claimDetailState.isTreatmentAt ? 'Có' : 'Không'}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </>}
                                                {/* Sự kiện bảo hiểm 3 */}
                                                {claimCheckedMap[CLAIM_TYPE.DEATH] && <>
                                                    <div className="tab__content">
                                                        <div className="input disabled">
                                                            <div className="input__content">
                                                                <label>Sự kiện bảo hiểm</label>
                                                                <input placeholder="Tử vong" disabled
                                                                        type="search"/>
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"}
                                                                    alt=""/></i>
                                                        </div>
                                                    </div>
                                                    <div className="tab__content">
                                                        <div className="yellow-dropdown dropdown show"
                                                                id="deathDetailDropdown">
                                                            <div className="dropdown__content"
                                                                    onClick={(e) => {
                                                                        document.getElementById('deathDetailDropdown').className === 'yellow-dropdown dropdown' ? document.getElementById('deathDetailDropdown').className = 'yellow-dropdown dropdown show' : document.getElementById('deathDetailDropdown').className = 'yellow-dropdown dropdown'
                                                                    }}>
                                                                <p className="simple-brown basic-semibold">Chi
                                                                    tiết</p>
                                                                <span
                                                                    className="icon simple-brown basic-semibold">&gt;</span>
                                                            </div>
                                                            <div className="dropdown__items">
                                                                <div className="yellowtab">
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Thời
                                                                            gian</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{deathInfo.date ? formatDate(deathInfo.date) : ''} {deathInfo.hour ? moment(deathInfo.hour).format("HH:mm") : ""}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>Nơi xảy ra tử vong</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">-
                                                                            Tỉnh/Thành
                                                                            phố</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{deathInfo.cityName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">-
                                                                            Quận/Huyện</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{deathInfo.districtName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">- Địa
                                                                            chỉ cụ
                                                                            thể</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{deathInfo.address + (deathInfo.districtName !== null && deathInfo.districtName !== undefined && deathInfo.districtName !== "" ? ", " + deathInfo.districtName : "") + (deathInfo.cityName !== null && deathInfo.cityName !== undefined && deathInfo.cityName !== "" ? ", " + deathInfo.cityName : "")}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Nguyên
                                                                            nhân tử
                                                                            vong</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{deathInfo.deathDescription}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </>}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="top-dash-line-short-margin"></div>
                            {/* Thông tin khám điều trị tại cơ sở y tế */}
                            {claimDetailState.isTreatmentAt && <div className="info">
                                <div className="info__title">
                                    <h4>Thông tin khám/điều trị</h4>
                                    {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                    <span className="update-btn simple-brown4"
                                            onClick={() => this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.CLAIM_DETAIL), this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                    }
                                </div>
                                <div className="info__body">
                                    {facilityList && facilityList.map((item, index) => (
                                        <div className="tabflex-wrapper" key={'sub-faci-' + index}>
                                            <div className="tabflex">
                                                <h5 className="special">Cơ sở y
                                                    tế {facilityList.length > 1 && index + 1}</h5>
                                                <p className="special basic-uppercase" style={{
                                                    maxWidth: "300px", textAlign: "right", lineHeight: '19px'
                                                }}>
                                                    {item.selectedHospital}
                                                </p>
                                            </div>
                                            <div className="tabflex">
                                                <h5>Tỉnh/Thành phố</h5>
                                                <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{item.cityName}</p>
                                            </div>
                                            <div className="tabflex">
                                                <h5>Quận/Huyện</h5>
                                                <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{item.districtName}</p>
                                            </div>
                                            <div className="tabflex">
                                                <h5>Ngày khám/điều trị</h5>
                                                <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{item.startDate ? formatDate(item.startDate) : ''}{item.endDate && (item.endDate ? ' - ' + formatDate(item.endDate) : '')}</p>
                                            </div>
                                            <div className="tabflex">
                                                <h5>Hình thức điều trị</h5>
                                                {item.treatmentType && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{TREAMENT_TYPE[item.treatmentType].desc}</p>}
                                            </div>

                                            <div className="tabflex">
                                                <h5>Chẩn đoán</h5>
                                                <p style={{
                                                    maxWidth: "300px", textAlign: "right", lineHeight: "17px"
                                                }}>{item.diagnosis.toString()}</p>
                                            </div>
                                            {sumInvoiceNew(item.invoiceList) > 0 && <div className="tabflex">
                                                <h5>Tổng chi phí và điều trị</h5>
                                                <p className="claim-submission-text"
                                                    style={{maxWidth: "300px", textAlign: "right"}}>
                                                    <NumberFormat displayType="text" 
                                                                    value={sumInvoiceNew(item.invoiceList)}
                                                                    thousandsGroupStyle="thousand"
                                                                    thousandSeparator={'.'}
                                                                    suffix={' VNĐ'}
                                                                    decimalSeparator=","
                                                                    decimalScale="0"/>
                                                </p>
                                            </div>}
                                            {item.invoiceList && item.invoiceList.map((it, idx) => {
                                                if (it.invoiceNumber) {
                                                    return (<>
                                                        <div className="tabflex"
                                                                key={'sub-faci-invoice-' + idx}>
                                                            <h5>Số hoá
                                                                đơn {item.invoiceList.length > 1 ? idx + 1 : ''}</h5>
                                                            <p style={{
                                                                maxWidth: "300px", textAlign: "right"
                                                            }}>
                                                                {it.invoiceNumber}
                                                            </p>
                                                        </div>
                                                        <div className="tabflex">
                                                            <h5>Số tiền</h5>
                                                            <p className="claim-submission-text"
                                                                style={{
                                                                    maxWidth: "300px", textAlign: "right"
                                                                }}>
                                                                <NumberFormat displayType="text"
                                                                                /*type="search" */
                                                                                value={it.invoiceAmount}
                                                                                thousandsGroupStyle="thousand"
                                                                                thousandSeparator={'.'}
                                                                                suffix={' VNĐ'}
                                                                                decimalSeparator=","
                                                                                decimalScale="0"/>
                                                            </p>
                                                        </div>
                                                    </>)
                                                }

                                            })}
                                            {/* Bảo hiểm tại công ty khác */}
                                            {item.isOtherCompanyRelated && <>
                                                <div className="tabflex">
                                                    <h5>Đã được chi trả tại công ty bảo hiểm khác</h5>
                                                    <p style={{maxWidth: "300px", textAlign: "right"}}>
                                                        {item.isOtherCompanyRelated === "yes" ? "Có" : "Không"}
                                                    </p>
                                                </div>
                                                {item.otherCompanyInfo.companyName && <div className="tabflex">
                                                    <h5>Công ty bảo hiểm đã chi trả</h5>
                                                    <p style={{
                                                        maxWidth: "300px", textAlign: "right"
                                                    }}>
                                                        {item.otherCompanyInfo.companyName}
                                                    </p>
                                                </div>}
                                                {item.otherCompanyInfo.paidAmount && <div className="tabflex">
                                                    <h5>Số tiền được chi trả</h5>
                                                    <p className="claim-submission-text"
                                                        style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>
                                                        <NumberFormat displayType="text"
                                                                        value={item.otherCompanyInfo.paidAmount}
                                                                        thousandsGroupStyle="thousand"
                                                                        thousandSeparator={'.'}
                                                                        suffix={' VNĐ'}
                                                                        decimalSeparator=","
                                                                        decimalScale="0"/>
                                                    </p>
                                                </div>}
                                            </>}
                                        </div>))}
                                </div>
                            </div>}
                            {/* } */}
                        </div>

                        <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt=""/>
                        <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                    </div>

                    {/* Thông tin thanh toán */}
                    <div className="optionalform-wrapper">
                        <div className="optionalform">
                            <div className="optionalform__title">
                                <h5 className="basic-bold">Thông tin thanh toán</h5>
                                {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                <span className="update-btn simple-brown4"
                                        onClick={() => this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD), this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                }
                            </div>
                            <div className="optionalform__body">
                                <div className="tab-wrapper">
                                    {/* Phương thức thanh toán cho quyền lợi nhân thọ */}
                                    {(((lifeBenState.paymentMethodId === 'P2') && (lifeBenState.transferTypeState.cityName && lifeBenState.transferTypeState.bankName && lifeBenState.transferTypeState.bankBranchName && lifeBenState.transferTypeState.bankAccountNo)) || ((lifeBenState.paymentMethodId === 'P1') && lifeBenState.cashTypeState.cityName && lifeBenState.cashTypeState.bankName && lifeBenState.cashTypeState.bankBranchName && lifeBenState.cashTypeState.receiverName && lifeBenState.cashTypeState.receiverPin && lifeBenState.cashTypeState.receiverPinDate && lifeBenState.cashTypeState.receiverPinLocation)) &&
                                        <>
                                            {!haveCheckedDeadth(this.props.claimCheckedMap) &&
                                            <div className="tab">
                                                <div className="tab__content">
                                                    <p>Người nhận thanh toán:<span className='basic-bold'>{((lifeBenState.transferTypeState?.bankAccountName || lifeBenState.cashTypeState?.receiverName)?.toLowerCase()?.trim() === (this.props.fullName || getSession(FULL_NAME))?.toLowerCase()?.trim()) ? ' Bên mua bảo hiểm' : ' Người được bảo hiểm'}</span></p>
                                                </div>
                                            </div>
                                            }
                                            <div className="tab">
                                                <div className="tab__content">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            <input placeholder={lifeBenState.paymentMethodName}
                                                                disabled
                                                                type="search" />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                </div>
                                                <div className="tab__content">
                                                    <div className="yellow-dropdown dropdown show"
                                                        id="lifeBenPaymentDetailDropdown">
                                                        <div className="dropdown__content" onClick={(e) => {
                                                            document.getElementById('lifeBenPaymentDetailDropdown').className === 'yellow-dropdown dropdown' ? document.getElementById('lifeBenPaymentDetailDropdown').className = 'yellow-dropdown dropdown show' : document.getElementById('lifeBenPaymentDetailDropdown').className = 'yellow-dropdown dropdown'
                                                        }}>
                                                            <p className="simple-brown basic-semibold">Chi tiết</p>
                                                            <span
                                                                className="icon simple-brown basic-semibold">&gt;</span>
                                                        </div>
                                                        {(lifeBenState.paymentMethodId === 'P2') &&
                                                            <div className="dropdown__items">
                                                                <div className="yellowtab">
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Tỉnh/Thành
                                                                            phố</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.cityName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Ngân hàng</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.bankName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Chi nhánh</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.bankBranchName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Phòng giao
                                                                            dịch</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.bankOfficeName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Số tài khoản</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.bankAccountNo}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Tên chủ tài
                                                                            khoản</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.transferTypeState.bankAccountName}</p>
                                                                    </div>
                                                                </div>
                                                            </div>}
                                                        {(lifeBenState.paymentMethodId === 'P1') &&
                                                            <div className="dropdown__items">
                                                                <div className="yellowtab">
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Tỉnh/Thành
                                                                            phố</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.cityName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Ngân hàng</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.bankName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Chi nhánh</p>
                                                                        <p className="simple-brown"
                                                                            style={{
                                                                                maxWidth: "300px", textAlign: "right"
                                                                            }}>
                                                                            {lifeBenState.cashTypeState.bankBranchName}
                                                                        </p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Phòng giao
                                                                            dịch</p>
                                                                        <p className="simple-brown"
                                                                            style={{
                                                                                maxWidth: "300px", textAlign: "right"
                                                                            }}>
                                                                            {lifeBenState.cashTypeState.bankOfficeName}
                                                                        </p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Họ tên người
                                                                            nhận</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.receiverName}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">CMND/CCCD</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.receiverPin}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Ngày cấp</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.receiverPinDate ? formatDate(lifeBenState.cashTypeState.receiverPinDate) : ''}</p>
                                                                    </div>
                                                                    <div className="tab">
                                                                        <p className="simple-brown">Nơi cấp</p>
                                                                        <p className="simple-brown" style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>{lifeBenState.cashTypeState.receiverPinLocation}</p>
                                                                    </div>
                                                                </div>
                                                            </div>}
                                                    </div>
                                                </div>
                                            </div>
                                        </>

                                    }
                                    {/* Phương thức thanh toán cho quyền lợi sức khỏe */}
                                    {(((lifeBenState.paymentMethodId !== healthCareBenState.paymentMethodId) || ((lifeBenState.paymentMethodId === healthCareBenState.paymentMethodId) &&
                                        (((lifeBenState.paymentMethodId === 'P2') &&
                                            !((lifeBenState.transferTypeState.cityName === healthCareBenState.transferTypeState.cityName) && (lifeBenState.transferTypeState.bankName === healthCareBenState.transferTypeState.bankName) && (lifeBenState.transferTypeState.bankBranchName === healthCareBenState.transferTypeState.bankBranchName) && (lifeBenState.transferTypeState.bankAccountNo === healthCareBenState.transferTypeState.bankAccountNo) && (lifeBenState.transferTypeState.bankName === healthCareBenState.transferTypeState.bankName) && (lifeBenState.transferTypeState.bankBranchName === healthCareBenState.transferTypeState.bankBranchName) && (lifeBenState.transferTypeState.bankAccountNo === healthCareBenState.transferTypeState.bankAccountNo))))
                                        || (!lifeBenState.cashTypeState.bankName) && (lifeBenState.paymentMethodId === 'P1') && !((lifeBenState.cashTypeState.cityName === healthCareBenState.cashTypeState.cityName) && (lifeBenState.cashTypeState.bankName === healthCareBenState.cashTypeState.bankName) && (lifeBenState.cashTypeState.bankBranchName === healthCareBenState.cashTypeState.bankBranchName) && (lifeBenState.cashTypeState.receiverName === healthCareBenState.cashTypeState.receiverName) && (lifeBenState.cashTypeState.receiverPin === healthCareBenState.cashTypeState.receiverPin) && (lifeBenState.cashTypeState.receiverPinDate === healthCareBenState.cashTypeState.receiverPinDate) && (lifeBenState.cashTypeState.receiverPinLocation === healthCareBenState.cashTypeState.receiverPinLocation)))) && ((healthCareBenState.paymentMethodId === 'P2') && (healthCareBenState.transferTypeState.cityName && healthCareBenState.transferTypeState.bankName && healthCareBenState.transferTypeState.bankBranchName && healthCareBenState.transferTypeState.bankAccountNo)) || ((healthCareBenState.paymentMethodId === 'P1') && healthCareBenState.cashTypeState.cityName && healthCareBenState.cashTypeState.bankName && healthCareBenState.cashTypeState.bankBranchName && healthCareBenState.cashTypeState.receiverName && healthCareBenState.cashTypeState.receiverPin && healthCareBenState.cashTypeState.receiverPinDate && healthCareBenState.cashTypeState.receiverPinLocation)) &&
                                        <>
                                            {!haveCheckedDeadth(this.props.claimCheckedMap) &&
                                            <div className="tab">
                                                <div className="tab__content">
                                                    <p>Người nhận thanh toán:<span className='basic-bold'>{((healthCareBenState.transferTypeState?.bankAccountName || healthCareBenState.cashTypeState?.receiverName)?.toLowerCase()?.trim() === (this.props.fullName || getSession(FULL_NAME))?.toLowerCase()?.trim()) ? ' Bên mua bảo hiểm' : ' Người được bảo hiểm'}</span></p>
                                                </div>
                                            </div>
                                            }                                     
                                            <div className="tab">
                                            <div className="tab__content">
                                                <div className="input disabled">
                                                    <div className="input__content">
                                                        <input
                                                            placeholder={healthCareBenState.paymentMethodName}
                                                            disabled type="search" />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                </div>
                                            </div>
                                            <div className="tab__content">
                                                <div className="yellow-dropdown dropdown show"
                                                    id="healthCareBenPaymentDetailDropdown">
                                                    <div className="dropdown__content" onClick={(e) => {
                                                        document.getElementById('healthCareBenPaymentDetailDropdown').className === 'yellow-dropdown dropdown' ? document.getElementById('healthCareBenPaymentDetailDropdown').className = 'yellow-dropdown dropdown show' : document.getElementById('healthCareBenPaymentDetailDropdown').className = 'yellow-dropdown dropdown'
                                                    }}>
                                                        <p className="simple-brown basic-semibold">Chi tiết</p>
                                                        <span
                                                            className="icon simple-brown basic-semibold">&gt;</span>
                                                    </div>
                                                    {(healthCareBenState.paymentMethodId === 'P2') &&
                                                        <div className="dropdown__items">
                                                            <div className="yellowtab">
                                                                <div className="tab">
                                                                    <p className="simple-brown">Tỉnh/Thành
                                                                        phố</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.cityName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Ngân hàng</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.bankName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Chi nhánh</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.bankBranchName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Phòng giao
                                                                        dịch</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.bankOfficeName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Số tài khoản</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.bankAccountNo}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Tên chủ tài
                                                                        khoản</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.transferTypeState.bankAccountName}</p>
                                                                </div>
                                                            </div>
                                                        </div>}
                                                    {(healthCareBenState.paymentMethodId === 'P1') &&
                                                        <div className="dropdown__items">
                                                            <div className="yellowtab">
                                                                <div className="tab">
                                                                    <p className="simple-brown">Tỉnh/Thành
                                                                        phố</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.cityName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Ngân hàng</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.bankName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Chi nhánh</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.bankBranchName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Phòng giao
                                                                        dịch</p>
                                                                    <p className="simple-brown"
                                                                        style={{
                                                                            maxWidth: "300px", textAlign: "right"
                                                                        }}>
                                                                        {healthCareBenState.cashTypeState.bankOfficeName}
                                                                    </p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Họ tên người
                                                                        nhận</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.receiverName}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">CMND/CCCD</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.receiverPin}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Ngày cấp</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.receiverPinDate ? formatDate(healthCareBenState.cashTypeState.receiverPinDate) : ''}</p>
                                                                </div>
                                                                <div className="tab">
                                                                    <p className="simple-brown">Nơi cấp</p>
                                                                    <p className="simple-brown" style={{
                                                                        maxWidth: "300px", textAlign: "right"
                                                                    }}>{healthCareBenState.cashTypeState.receiverPinLocation}</p>
                                                                </div>
                                                            </div>
                                                        </div>}
                                                </div>
                                            </div>
                                        </div>
                                        </>

                                        }
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="optionalform-wrapper">
                        <div className="optionalform">
                            {/* Thông tin liên hệ */}
                            {/* {(this.props.claimTypeState.isDeathClaim && this.props.claimTypeState.isSamePoLi) && */}
                            <div className="optionalform__title">
                                <h5 className="basic-bold">Thông tin liên hệ</h5>
                                {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                <span className="update-btn simple-brown4"
                                        onClick={() => this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.CONTACT), this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                }
                            </div>
                            <div className="optionalform__body">
                                <div className="tabflex-wrapper">
                                    <div className="tabflex">
                                        <h5 className="special">Họ tên</h5>
                                        <p className="special basic-uppercase" style={{
                                            maxWidth: "300px", textAlign: "right"
                                        }}>{contactPersonInfo.fullName}</p>
                                    </div>
                                    <div className="tabflex">
                                        <h5>CMND/ CCCD</h5>
                                        <p style={{
                                            maxWidth: "300px", textAlign: "right"
                                        }}>{contactPersonInfo.pin}</p>
                                    </div>
                                    <div className="tabflex">
                                        <h5>Ngày sinh</h5>
                                        <p style={{
                                            maxWidth: "300px", textAlign: "right"
                                        }}>{contactPersonInfo.dob ? formatDateString(contactPersonInfo.dob) : ''}</p>
                                    </div>
                                    <div className="tabflex">
                                        <h5>Email</h5>
                                        <p style={{
                                            maxWidth: "300px", textAlign: "right"
                                        }}>{contactPersonInfo.email}</p>
                                    </div>
                                    <div className="tabflex">
                                        <h5>Điện thoại</h5>
                                        <p style={{
                                            maxWidth: "300px", textAlign: "right"
                                        }}>{contactPersonInfo.phone}</p>
                                    </div>
                                    {this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && !this.props.claimTypeState.isSamePoLi &&
                                        <div className="tabflex">
                                            <h5>Quan hệ với bên mua bảo hiểm</h5>
                                            <p style={{
                                                maxWidth: "300px", textAlign: "right"
                                            }}>{contactPersonInfo.poRelation}</p>
                                        </div>}
                                    <div className="tabflex">
                                        <h5>Địa chỉ liên hệ</h5>
                                        <p style={{
                                            maxWidth: "300px", textAlign: "right", lineHeight: '17px'
                                        }}>{contactPersonInfo.address}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* Giấy tờ/ chứng từ đính kèm */}
                    <div className="optionalform-wrapper">
                        <div className="optionalform">
                            <div className="optionalform__title">
                                <h5 className="basic-bold">Giấy tờ/ chứng từ đính kèm</h5>
                                {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                <span className="update-btn simple-brown4"
                                        onClick={() => this.props.handlerGoToStep(this.props.handlerGetStep(CLAIM_STEPCODE.ATTACHMENT), this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                }
                            </div>
                            <div className="optionalform__body">
                                {!isEmpty(attachmentState.attachmentData) && filterAttachments(attachmentState.attachmentData).map((docType, docTypeIdx) => {
                                    return (<div className="imglist" key={'sub-att-' + docTypeIdx}>
                                        <div className="imglist__title">
                                            <h5 className="basic-semibold">{docType.DocTypeName}</h5>
                                        </div>
                                        <div className="imglist__content">
                                            <div className="imgtab-wrapper">
                                                {!isEmpty(docType.attachmentList) && <ImageViewerBase64 images={docType.attachmentList}/>}
                                            </div>
                                        </div>
                                    </div>);
                                })}
                            </div>
                        </div>
                    </div>
                    {/* thông tin fatca */}
                    {this.props.disableEdit &&
                        <div className="optionalform-wrapper">
                            <div className="optionalform">
                                <div className="optionalform__title">
                                    <h5 className="basic-bold">THÔNG TIN FATCA</h5>
                                    {!(this.props.disableEdit&& !this.props.agentKeyInPOSelfEdit) &&
                                    <span className="update-btn simple-brown4"
                                        onClick={() => this.openPopupConfirmClaim(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW))}>Cập nhật</span>
                                    }
                                </div>
                                <div className="optionalform__body">
                                    <p>Theo luật thuế FATCA của Hoa Kỳ, nếu người nhận tiền có các thông tin dưới
                                        đây, vui
                                        lòng chọn:</p>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox2">
                                            <input type="checkbox" name="isAmericanNationality"
                                                checked={!!this.props.submissionState.fatcaState.isAmericanNationality}
                                                disabled
                                            />
                                            <div className="checkmark">
                                                <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                            </div>
                                        </label>
                                        <p className="text">Quốc tịch Hoa Kỳ</p>
                                    </div>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox2">
                                            <input type="checkbox" name="isAmericanResidence"
                                                checked={!!this.props.submissionState.fatcaState.isAmericanResidence}
                                                disabled
                                            />
                                            <div className="checkmark">
                                                <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                            </div>
                                        </label>
                                        <p className="text">Địa chỉ thường trú tại Hoa Kỳ</p>
                                    </div>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox2">
                                            <input type="checkbox" name="isAmericanTax" checked={!!this.props.submissionState.fatcaState.isAmericanTax}
                                                disabled
                                            />
                                            <div className="checkmark">
                                                <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                            </div>
                                        </label>
                                        <p className="text">Thực hiện khai báo thuế tại Hoa Kỳ</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    }
                    {(this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.props.disableEdit &&
                        <>
                            <div className={getUrlParameter("fromApp")?'paymode-margin-bottom nd13-padding-bottom120': 'paymode-margin-bottom'} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginLeft: '4px', marginRight: '4px' }}>
                                <div className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                    style={{
                                        'maxWidth': '594px',
                                        backgroundColor: '#f5f3f2',
                                        paddingLeft: '6px'
                                    }}>
                                    <div
                                        className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                        style={{ flex: '0 0 auto', height: '20px', cursor: 'pointer' }}
                                        onClick={() => acceptPolicy()}>
                                        <div className="checkmark">
                                            <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                        </div>
                                    </div>
                                    <div className="policy-info-tac" style={{
                                        textAlign: 'justify',
                                        paddingLeft: '12px'
                                    }}>
                                        <p style={{ textAlign: 'left', fontWeight: "bold" }}>Tôi xác nhận và cam kết những điều sau:</p>
                                        <div className="content">
                                            <div className='list__item'>
                                                <div className="dot-grey"></div>
                                                <p style={{textAlign: 'left'}}>
                                                    Tôi cam kết các thông tin đã cung cấp trong yêu cầu quyền lợi bảo hiểm này là hoàn toàn đầy đủ và chính xác.
                                                </p>
                                            </div>
                                            <div className='list__item'>
                                                <div className="dot-grey"></div>
                                                <p style={{textAlign: 'left'}}>
                                                    Tôi cho phép bất kỳ nhân viên y tế, cơ sở y tế, doanh nghiệp bảo hiểm, tổ chức hay cá nhân nào khác được cung cấp cho Dai-ichi Life Việt Nam bất kỳ thông tin, tài liệu nào liên quan đến Người được bảo hiểm và/hoặc Bên mua bảo hiểm theo yêu cầu của Dai-ichi Life Việt Nam.
                                                </p>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </>
                    }
                    {getSession(IS_MOBILE) && !this.props.disableEdit &&
                        <div className='nd13-padding-bottom120'></div>
                    }
                    <div className={getSession(IS_MOBILE)?"bottom-btn mb-display-block":"bottom-btn"}>
                        {(this.state.isSubmitting || this.props.isSubmitting || this.state.endStep || ((this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.props.disableEdit && !this.state.acceptPolicy) || (this.state.isSamePoLi && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && this.props.consultingViewRequest))?(
                            <button className="btn btn-primary disabled" id="submitClaimDetail" disabled>
                                    {(this.props.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo?.dOB)) || (!isOlderThan18(this.props.selectedCliInfo?.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList))) ? 'Tiếp tục' : 'Hoàn Thành'}
                            </button>
                        ):(
                            <button className="btn btn-primary" id="submitClaimDetail"
                                    onClick={()=>this.openPopupConfirmClaim()}>
                                        {(this.props.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo?.dOB)) || (!isOlderThan18(this.props.selectedCliInfo?.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList))) ? 'Tiếp tục' : 'Hoàn Thành'}
                            </button>
                        )}
                        {getSession(IS_MOBILE) && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.props.disableEdit &&
                            (
                                this.props.agentKeyInPOSelfEdit ? (
                                    <button className="btn btn-callback" onClick={() => cancelConfirm()}>Hủy</button>
                                ) : (
                                    <button className="btn btn-callback" onClick={() => notAgree()}>Hủy/Điều chỉnh thông tin</button>
                                )
                            )
                        }
                    </div>
                    {!getSession(IS_MOBILE) && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && this.props.disableEdit &&
                        (
                            this.props.agentKeyInPOSelfEdit ? (
                                <button className="btn btn-callback" onClick={() => cancelConfirm()}>Hủy</button>
                            ) : (
                                <button className="btn btn-callback" onClick={() => notAgree()}>Hủy/Điều chỉnh thông tin</button>
                            )
                        )
                    }
                </div>
            </section>

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
                    <div className="envelop-confirm-card" ref={()=>this.handlerSetWrapperSucceededRef}>
                        <div className="envelopcard">
                            <div className="envelop-content">
                                <div className="envelop-content__header"
                                     ref={()=>this.handlerSetCloseSucceededButtonRef}
                                >
                                    <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={(event)=>this.closePopupSucceeded(event)}/></i>
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


            {/* {this.state.isCancelRequest && <ND13CancelRequestConfirm
                onClickExtendBtn={() => clearRequest()}
                onClickCallBack={() => this.setState({isCancelRequest: false})}
            />} */}
            {this.state.showCancelConfirm && <ND13CancelRequestConfirm
                onClickExtendBtn={() => rejectClaimPOEditOn()}
                onClickCallBack={() => closeCancelConfirm()}
            />}
            {this.state.openPopupSuccessfulND13 && <PopupSuccessfulND13 onClose={() => {
                const jsonState = {...this.state};
                jsonState.openPopupSuccessfulND13 = false;
                jsonState.claimSubmissionState = CLAIM_STATE.INIT;
                this.setState(jsonState);
                this.props.callBackConfirmation(CLAIM_STATE.INIT);
                this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
            }}/>}
            {this.state.confirmPayment &&
                <AlertPopupND13ConfirmPayment closePopup={closeConfirmPayment} msg={"<p className='basic-bold'>Quý khách vừa hoàn tất khai báo <br/> xác nhận đồng ý nghị định 13.</p>"} imgPath={FE_BASE_URL + "/img/popup/nd13_agreed_popup.png"} subMsg={"<p>Tiếp theo vui lòng xác nhận về cam kết xác nhận thanh toán Quyền lợi bảo hiểm.</p>"} />
            }
            {this.state.allImageFail &&
                <AlertPopupUploadAllImageFail closePopup={closeAllImageFail} msg={"<p className='basic-bold basic-font-size-16'>Xin Lỗi Quý Khách. <br/> Hệ Thống Đang Bị Gián Đoạn</p>"} imgPath={FE_BASE_URL + "/img/popup/disconnect.svg"} subMsg={"<p><br/>Quý khách vui lòng thử lại sau, hoặc lập phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý</p><p><br/>Trường hợp Quý khách cần trao đổi thêm, Quý khách vui lòng liên hệ Tổng đài (028)38 100 888 hoặc thư điện tử <span className='simple-brown' style={{textDecoration: 'none'}}>bhchamsocsuckhoe@dai-ichi-life.com.vn</span></p>"} />
            }
            {this.state.showRejectConfirm &&
                <AgreeCancelPopup closePopup={() => setShowRejectConfirm(false)} agreeFunc={(event) => requestEdit(event)}
                    msg={'<p>Yêu cầu điều chỉnh thông tin của Quý khách sẽ được gửi đến Đại lý bảo hiểm thực hiện, hoặc Quý khách có thể hủy yêu cầu trực tuyến này và lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý gần nhất.</p>'}
                    imgPath={FE_BASE_URL + '/img/popup/reject-confirm.svg'} agreeText='Yêu cầu Đại lý BH điều chỉnh' notAgreeText='Hủy yêu cầu QLBH' notAgreeFunc={() => rejectClaim()} />}
            {this.state.showThank &&
                <OkPopupTitle closePopup={() => setShowThank(false)}
                    msg={'Cảm ơn Quý khách đã xác nhận thông tin'}
                    imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                    agreeFunc={() => setShowThank(false)}
                    agreeText='Đóng'
                />
            }
        </>);
    }

}

export default ClaimSubmission;
