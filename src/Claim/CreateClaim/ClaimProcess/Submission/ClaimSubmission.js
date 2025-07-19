// import 'antd/dist/antd.min.css';

import React, {Component} from 'react';
import moment from 'moment';
import NumberFormat from 'react-number-format';
import LoadingIndicator from '../../../../common/LoadingIndicator2';
import {
    checkValue,
    formatDate,
    formatDateMMddyy,
    getDeviceId,
    getSession,
    is18Plus,
    removeLocal,
    setLocal,
    showMessage,
    sumInvoice, sumInvoiceNew, haveCheckedDeadth, haveHC_HS,
    setSession,
    removeSession
} from '../../../../util/common';

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
    PO_CONFIRMING_DECREE_13,
    POID,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    RELOAD_BELL,
    ONLY_PAYMENT,
    NUM_OF_RETRY
} from '../../../../constants';

import {CPConsentConfirmation, postClaimImage, postClaimInfo, CPSaveLog} from '../../../../util/APIUtils';

import {CLAIM_STATE, TREAMENT_TYPE} from '../../CreateClaim';
import ND13POContactInfoOver18 from "../../../ClaimND13/ND13Modal/ND13POContactInfoOver18";
import ND13CancelRequestConfirm from "../../../ClaimND13/ND13Modal/ND13CancelRequestConfirm/ND13CancelRequestConfirm";
import ND13POContactInfoUnder18 from "../../../ClaimND13/ND13Modal/ND13POContactInfoUnder18";
import ND13POConfirm from "../../../ClaimND13/ND13Modal/ND13POConfirm";
import PopupSuccessfulND13 from "../../../ClaimND13/ND13Modal/PopupSuccessfulND13";
import ImageViewerBase64 from "../../../../Followup/ImageViewerBase64";
import {isEmpty} from "lodash";
import AlertPopupND13ConfirmPayment from "../../../../components/AlertPopupND13ConfirmPayment";
import AlertPopupUploadAllImageFail from "../../../../components/AlertPopupUploadAllImageFail";

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

            stepName: CLAIM_STATE.SUBMIT,
            popupConfirmClaimCalled: false,
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
            allImageFail: false
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
            this.fetchCPConsentConfirmationRefresh(this.props.trackingId);
            // }
        }
        
        
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

    openPopupConfirmClaim() {
        document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup show";
        if (document.getElementById('fatca_id')) {
            document.getElementById('fatca_id').disabled = false;
        }
        const jsonState = this.state;
        jsonState.popupConfirmClaimCalled = true;
        this.setState(jsonState);
    }

    onClickConfirmCloseButton() {
        if (document.getElementById("popupConfirmClaimSubmission")) {
            document.getElementById("popupConfirmClaimSubmission").className = "popup special hoso-popup";
        }
        const jsonState = this.state;
        let fatcaState = jsonState.submissionState.fatcaState;
        fatcaState['isAmericanNationality'] = false;
        fatcaState['isAmericanResidence'] = false;
        fatcaState['isAmericanTax'] = false;
        jsonState.popupConfirmClaimCalled = false;
        jsonState.submissionState.fatcaState = fatcaState;
        this.setState(jsonState);
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    setCloseSucceededButtonRef(node) {
        this.closeSucceededButtonRef = node;
    }

    prepareSubmitClaimInfoRequest() {
        const apiRequest = Object.assign({}, this.state.submitClaimInfoJsonInput);
        const jsonDataInput = apiRequest.jsonDataInput;
        const selectedCliInfo = this.props.selectedCliInfo;
        const initClaimState = this.props.initClaimState;
        const claimTypeState = this.props.claimTypeState;
        const claimDetailState = this.props.claimDetailState;
        const sickInfo = claimDetailState.sickInfo;
        const contactPersonInfo = this.props.contactState.contactPersonInfo;
        const paymentMethodState = this.props.paymentMethodState;
        const fatcaState = this.state.submissionState.fatcaState;
        const claimCheckedMap = this.props.claimCheckedMap;

        jsonDataInput['Action'] = "ClaimForm_Requester";

        //remove if else to fix issue miss LIFullname, birthday, CCCD
        jsonDataInput['FullName'] = checkValue(getSession(FULL_NAME));
        jsonDataInput['IDNum'] = checkValue(getSession(POID));
        jsonDataInput['Address'] = checkValue(getSession(ADDRESS));
        jsonDataInput['CellPhone'] = checkValue(getSession(CELL_PHONE));
        jsonDataInput['Email'] = checkValue(getSession(EMAIL));

        jsonDataInput['LIFullName'] = checkValue(selectedCliInfo.FullName);
        jsonDataInput['LIClientID'] = checkValue(selectedCliInfo.ClientID);
        jsonDataInput['LifeInsuredID'] = checkValue(selectedCliInfo.ClientID);
        jsonDataInput['LIBirthDate'] = checkValue(selectedCliInfo.DOB);
        jsonDataInput['LIIDNum'] = checkValue(selectedCliInfo.POID);
        jsonDataInput['LIGender'] = checkValue(selectedCliInfo.Gender);


        jsonDataInput['Occupation'] = checkValue(initClaimState.occupation);

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

        jsonDataInput['lstBenefit'] = lstBenefit;

        let reason = '';
        if (claimCheckedMap[CLAIM_TYPE.DEATH] && (claimTypeState.isAccidentClaim !== null)) {
            if (claimTypeState.isAccidentClaim) {
                reason = ';DeadOfAccident';
            } else {
                reason = ';DeadOfIllness';
            }
        }
        jsonDataInput['claimReasonChoice'] = claType + reason;
        jsonDataInput['ClaimSubType'] = claimTypeState.isAccidentClaim ? 'Accident' : '';
        jsonDataInput['ClaimType'] = claType;
        let docProcId = '';
        if ((claType.indexOf(CLAIM_TYPE.HEALTH_CARE) >= 0) || (claType.indexOf(CLAIM_TYPE.HS) >= 0)) {
            docProcId = 'CHN';
        } else {
            docProcId = 'CLN';
        }
        jsonDataInput['DocProcessID'] = docProcId;
        if (claimCheckedMap[CLAIM_TYPE.ILLNESS] || (claimTypeState.isAccidentClaim === false)) {
            let hosCode = '';
            let hos = this.props.hospitalList.filter(result => result.HospitalName === sickInfo.sickFoundFacility);
            if (hos && hos.length > 0) {
                hosCode = hos[0].ProviderCode;
            }
            if (hosCode) {
                hosCode = hosCode + " | ";
            }
            jsonDataInput['IllnessProgression'] = checkValue(claimDetailState.sickInfo.firstSympton);
            jsonDataInput['IllnessDate'] = checkValue(claimDetailState.sickInfo.sickFoundTime);
            jsonDataInput['IllnessPlace'] = claimDetailState.sickInfo.sickFoundFacility ? (hosCode + claimDetailState.sickInfo.sickFoundFacility + " (" + checkValue(claimDetailState.sickInfo.cityName) + ")") : '';
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
                    TaxInvoiceList: facility.invoiceList,
                    ICDList: icdList,
                    TreatmentHospital: checkValue(facility.selectedHospital),
                    TreatmentHospitalCode: hospitalCode,
                    PatientType: facility.treatmentType ? TREAMENT_TYPE[facility.treatmentType].submitType : '',
                    TreatmentDateFrom: checkValue(facility.startDate ? moment(facility.startDate).format('YYYY-MM-DDT00:00:00.000[Z]') : ""), // TreatmentDateFrom: checkValue(facility.startDate ? facility.startDate?.split('T') && facility.startDate?.split('T')[0] + 'T00:00:00.000Z' : ""),
                    TreatmentDateTo: checkValue(facility.endDate ? moment(facility.endDate).format('YYYY-MM-DDT00:00:00.000[Z]') : ""), // TreatmentDateTo:  checkValue(facility.endDate ? facility.endDate?.split('T') && facility.endDate?.split('T')[0] + 'T00:00:00.000Z' : ""),
                    Diagnostic: checkValue(facility.diagnosis).toString(),
                    TreatmentAmount: sumInvoiceNew(facility.invoiceList),
                    TreatmentType: claimTypeState.isAccidentClaim ? 'AccidentProgression' : 'IllnessProgression',
                    OtherInsurance: facility.otherCompanyInfo.companyName,
                    OtherInsuranceAmount: facility.otherCompanyInfo.paidAmount
                })
            });
        }
        jsonDataInput['TreatmentHistorys'] = treatmentList;

        jsonDataInput['AccidentDate'] = (claimDetailState.accidentInfo.date ? formatDateMMddyy(claimDetailState.accidentInfo.date) + ' ' : '') + (claimDetailState.accidentInfo.hour ? moment(claimDetailState.accidentInfo.hour).format("HH:mm") : '');
        if (jsonDataInput['AccidentDate']) {
            jsonDataInput['AccidentNational'] = claimDetailState.accidentInfo.selectedNation ? claimDetailState.accidentInfo.selectedNation : 'Việt Nam';
        } else {
            jsonDataInput['AccidentNational'] = '';
        }
        jsonDataInput['AccidentProvince'] = checkValue(claimDetailState.accidentInfo.cityName);
        jsonDataInput['AccidentDistrict'] = checkValue(claimDetailState.accidentInfo.districtName);
        jsonDataInput['AccidentPlace'] = checkValue(claimDetailState.accidentInfo.address);

        jsonDataInput['AccidentReason'] = checkValue(claimDetailState.accidentInfo.accidentDescription);
        jsonDataInput['AccidentIllnessDesc'] = checkValue(claimDetailState.accidentInfo.healthStatus);
        jsonDataInput['LossDate'] = (claimDetailState.deathInfo.date ? formatDateMMddyy(claimDetailState.deathInfo.date) + ' ' : '') + (claimDetailState.deathInfo.hour ? moment(claimDetailState.deathInfo.hour).format("HH:mm") : '');
        jsonDataInput['LossProvince'] = checkValue(claimDetailState.deathInfo.cityName);
        jsonDataInput['LossDistrict'] = checkValue(claimDetailState.deathInfo.districtName);
        jsonDataInput['LossPlace'] = checkValue(claimDetailState.deathInfo.address);
        jsonDataInput['LossProgression'] = checkValue(claimDetailState.deathInfo.deathDescription);

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
            'FullName': checkValue(contactPersonInfo.fullName),
            'IDNum': checkValue(contactPersonInfo.pin),
            'DoB': contactPersonInfo.dob ? formatDateMMddyy(contactPersonInfo.dob) : '',
            'Email': checkValue(contactPersonInfo.email),
            'Phone': checkValue(contactPersonInfo.phone),
            'RelationShip': relationShip,
            'RelationShipCode': relationShipCode,
            'Role': role,
            'Address': checkValue(contactPersonInfo.address),
        }
        jsonDataInput['ContactDeath'] = contactDeath;


        if (claimDetailState.isOtherCompanyRelated === "no") {
            jsonDataInput['OthersInsurance'] = [];
        } else {
            let othersInsuranceList = [];
            // claimDetailState.otherCompanyInfoList.forEach(otherCompanyInfo => {
            othersInsuranceList.push({
                OtherInsurance: checkValue(claimDetailState.otherCompanyInfo.companyName),
                PolicyInsurance: checkValue(claimDetailState.otherCompanyInfo.productName),
                ProductInsurance: checkValue(claimDetailState.otherCompanyInfo.policyNo),
            });
            // });
            jsonDataInput['OthersInsurance'] = othersInsuranceList;
        }

        if ((claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] || claimCheckedMap[CLAIM_TYPE.HS])) {
            //Mượn field của life để mang thông tin HC trong TH LI < 18, PO, DEATH
            if (!is18Plus(this.props.selectedCliInfo.DOB) || this.props.claimTypeState.isSamePoLi || claimCheckedMap[CLAIM_TYPE.DEATH]) {
                if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                    let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                    jsonDataInput['HCPaymentMethodID'] = "P1";
                    jsonDataInput['HCPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                    jsonDataInput['HCBankProvince'] = checkValue(cashTypeState.cityName);
                    jsonDataInput['HCBankCode'] = checkValue(cashTypeState.cityCode);
                    jsonDataInput['HCAccountName'] = checkValue(cashTypeState.receiverName);
                    jsonDataInput['HCPayIssueID'] = checkValue(cashTypeState.receiverPin);
                    jsonDataInput['HCPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
                    jsonDataInput['HCPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                    jsonDataInput['HCBankName'] = checkValue(cashTypeState.bankName);
                    jsonDataInput['HCBankBranch'] = checkValue(cashTypeState.bankBranchName);
                    jsonDataInput['HCBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                    jsonDataInput['TinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                } else {
                    let transferTypeState = paymentMethodState.lifeBenState.transferTypeState;
                    if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                        jsonDataInput['HCPaymentMethodID'] = "P2";
                        jsonDataInput['HCPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                        jsonDataInput['HCBankProvince'] = checkValue(transferTypeState.cityName);
                        jsonDataInput['HCBankCode'] = checkValue(transferTypeState.cityCode);
                        jsonDataInput['HCAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                        jsonDataInput['HCAccountName'] = checkValue(transferTypeState.bankAccountName);
                        jsonDataInput['HCBankName'] = checkValue(transferTypeState.bankName);
                        jsonDataInput['HCBankBranch'] = checkValue(transferTypeState.bankBranchName);
                        jsonDataInput['HCBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                    } else {
                        if (paymentMethodState.healthCareBenState && paymentMethodState.healthCareBenState.paymentMethodId === 'P1') {
                            let cashTypeState = paymentMethodState.healthCareBenState.cashTypeState;
                            jsonDataInput['HCPaymentMethodID'] = "P1";
                            jsonDataInput['HCPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                            jsonDataInput['HCBankProvince'] = checkValue(cashTypeState.cityName);
                            jsonDataInput['HCBankCode'] = checkValue(cashTypeState.cityCode);
                            jsonDataInput['HCAccountName'] = checkValue(cashTypeState.receiverName);
                            jsonDataInput['HCPayIssueID'] = checkValue(cashTypeState.receiverPin);
                            jsonDataInput['HCPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
                            jsonDataInput['HCPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                            jsonDataInput['HCBankName'] = checkValue(cashTypeState.bankName);
                            jsonDataInput['HCBankBranch'] = checkValue(cashTypeState.bankBranchName);
                            jsonDataInput['HCBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                            jsonDataInput['TinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                        } else {
                            let transferTypeState = paymentMethodState.healthCareBenState.transferTypeState;
                            if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                                jsonDataInput['HCPaymentMethodID'] = "P2";
                                jsonDataInput['HCPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                                jsonDataInput['HCBankProvince'] = checkValue(transferTypeState.cityName);
                                jsonDataInput['HCBankCode'] = checkValue(transferTypeState.cityCode);
                                jsonDataInput['HCAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                                jsonDataInput['HCAccountName'] = checkValue(transferTypeState.bankAccountName);
                                jsonDataInput['HCBankName'] = checkValue(transferTypeState.bankName);
                                jsonDataInput['HCBankBranch'] = checkValue(transferTypeState.bankBranchName);
                                jsonDataInput['HCBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                            }
                        }


                    }
                }
            } else {
                if (paymentMethodState.healthCareBenState && paymentMethodState.healthCareBenState.paymentMethodId === 'P1') {
                    let cashTypeState = paymentMethodState.healthCareBenState.cashTypeState;
                    jsonDataInput['HCPaymentMethodID'] = "P1";
                    jsonDataInput['HCPaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                    jsonDataInput['HCBankProvince'] = checkValue(cashTypeState.cityName);
                    jsonDataInput['HCBankCode'] = checkValue(cashTypeState.cityCode);
                    jsonDataInput['HCAccountName'] = checkValue(cashTypeState.receiverName);
                    jsonDataInput['HCPayIssueID'] = checkValue(cashTypeState.receiverPin);
                    jsonDataInput['HCPayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
                    jsonDataInput['HCPayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                    jsonDataInput['HCBankName'] = checkValue(cashTypeState.bankName);
                    jsonDataInput['HCBankBranch'] = checkValue(cashTypeState.bankBranchName);
                    jsonDataInput['HCBankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                    jsonDataInput['TinhThanhCMND_HC'] = checkValue(cashTypeState.cityName);
                } else {
                    let transferTypeState = paymentMethodState.healthCareBenState.transferTypeState;
                    if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                        jsonDataInput['HCPaymentMethodID'] = "P2";
                        jsonDataInput['HCPaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                        jsonDataInput['HCBankProvince'] = checkValue(transferTypeState.cityName);
                        jsonDataInput['HCBankCode'] = checkValue(transferTypeState.cityCode);
                        jsonDataInput['HCAccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                        jsonDataInput['HCAccountName'] = checkValue(transferTypeState.bankAccountName);
                        jsonDataInput['HCBankName'] = checkValue(transferTypeState.bankName);
                        jsonDataInput['HCBankBranch'] = checkValue(transferTypeState.bankBranchName);
                        jsonDataInput['HCBankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                    }
                }
            }

        }

        if (claimCheckedMap[CLAIM_TYPE.TPD] || claimCheckedMap[CLAIM_TYPE.ILLNESS] || claimCheckedMap[CLAIM_TYPE.ACCIDENT] || claimCheckedMap[CLAIM_TYPE.DEATH]) {
            if (paymentMethodState.lifeBenState && paymentMethodState.lifeBenState.paymentMethodId === 'P1') {
                let cashTypeState = paymentMethodState.lifeBenState.cashTypeState;
                jsonDataInput['PaymentMethodID'] = "P1";
                jsonDataInput['PaymentMethod'] = "Nhận bằng CMND tại ngân hàng";
                jsonDataInput['BankProvince'] = checkValue(cashTypeState.cityName);
                jsonDataInput['AccountName'] = checkValue(cashTypeState.receiverName);
                jsonDataInput['PayIssueID'] = checkValue(cashTypeState.receiverPin);
                jsonDataInput['PayDateIssue'] = cashTypeState.receiverPinDate ? formatDateMMddyy(cashTypeState.receiverPinDate) : '';
                jsonDataInput['PayPlaceIsssue'] = checkValue(cashTypeState.receiverPinLocation);
                jsonDataInput['BankName'] = checkValue(cashTypeState.bankName);
                jsonDataInput['BankBranch'] = checkValue(cashTypeState.bankBranchName);
                jsonDataInput['BankDealRoom'] = checkValue(cashTypeState.bankOfficeName);
                jsonDataInput['TinhThanhCMND'] = checkValue(cashTypeState.cityName);
            } else {
                let transferTypeState = paymentMethodState.lifeBenState.transferTypeState;
                if (transferTypeState.cityName && transferTypeState.bankAccountNo && transferTypeState.bankAccountName) {
                    jsonDataInput['PaymentMethodID'] = "P2";
                    jsonDataInput['PaymentMethod'] = "Nhận qua chuyển khoản ngân hàng";
                    jsonDataInput['BankProvince'] = checkValue(transferTypeState.cityName);
                    jsonDataInput['AccountNumber'] = checkValue(transferTypeState.bankAccountNo);
                    jsonDataInput['AccountName'] = checkValue(transferTypeState.bankAccountName);
                    jsonDataInput['BankName'] = checkValue(transferTypeState.bankName);
                    jsonDataInput['BankBranch'] = checkValue(transferTypeState.bankBranchName);
                    jsonDataInput['BankDealRoom'] = checkValue(transferTypeState.bankOfficeName);
                }
            }
        }

        jsonDataInput['PONational'] = fatcaState.isAmericanNationality ? "Hoa Kỳ" : "Việt Nam";
        jsonDataInput['IsAddressAmerican'] = fatcaState.isAmericanResidence ? "1" : "0";
        jsonDataInput['IsTaxAmerican'] = fatcaState.isAmericanTax ? "1" : "0";
        if (this.props.claimId) {
            jsonDataInput['ClaimID'] = this.props.claimId;
        }
        if (this.props.trackingId) {
            jsonDataInput['TrackingID'] = this.props.trackingId;
        }
        return apiRequest;
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
        jsonState.popupConfirmClaimCalled = false;
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

    async sequentiallyPostImage(submitClaimImageRequestList, jsonState, status, Response) {
        const alertSucceeded = this.alertSucceeded.bind(this);
        const setComponentState = this.setState.bind(this);
        const  DOB  = this.props.selectedCliInfo.DOB;
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
                jsonState.TrackingID = Response?.TrackingID;
                setComponentState(jsonState);
                alertSucceeded();
                this.confirmCPConsent();
                this.props.handlerGoToStep(CLAIM_STATE.DONE);
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
            } else if ((status === 'PO_CONFIRMING_DECREE_13') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap) && ((this.props.paymentMethodState.choseReceiver === 'PO') && this.isOlderThan18(DOB)) || (!this.isOlderThan18(DOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap))) ) {
                jsonState.isSubmitting = false;
                jsonState.TrackingID = Response?.TrackingID;
                setComponentState(jsonState);
                this.fetchCPConsentConfirmation(Response?.TrackingID);
                this.props.callBackTrackingId(Response?.TrackingID);
                this.props.callBackClaimID(Response.Message);//ClaimID
                // this.props.saveState();
            } else {
                jsonState.isSubmitting = false;
                jsonState.claimSubmissionState = CLAIM_STATE.INIT;
                jsonState.TrackingID = Response?.TrackingID;
                setComponentState(jsonState);
                alertSucceeded();
                this.confirmCPConsent();
                this.props.handlerGoToStep(CLAIM_STATE.DONE);
                removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
        if (getSession(ONLY_PAYMENT + this.props.selectedCliInfo.ClientID)) {
            removeSession(ONLY_PAYMENT + this.props.selectedCliInfo.ClientID);
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
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
                            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
                    removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
                        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
        const  DOB  = this.props.selectedCliInfo.DOB;
        if (getSession(ONLY_PAYMENT + this.props.selectedCliInfo.ClientID)) {
            removeSession(ONLY_PAYMENT + this.props.selectedCliInfo.ClientID);
        }

        let Exception = null;
        let failMessage = null;
        let remainRetryTimes = NUM_OF_RETRY + 1; // first request + num_of_retry.
        let isSuccess = false;
        while(!isSuccess && remainRetryTimes > 0){
            await postClaimInfo(submitClaimInfoRequest).then(Res => {
                let Response = Res.Response;
                if (Res.Response.Result === 'true' && Res.Response.Message !== null) {
                    isSuccess = true;
                    let claimID = Response.Message;
                    jsonState.ClaimID = Response.Message;
                    
                    let submitClaimImageRequestList = this.prepareSubmitClaimImageRequestList(claimID, submitClaimInfoRequest);
                    this.sequentiallyPostImage(submitClaimImageRequestList, jsonState, status, Response);
                   
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    jsonState.isSubmitting = false;
                    setComponentState(jsonState);
                    this.onClickConfirmCloseButton();
                    showMessage(EXPIRED_MESSAGE);
                } else if (Res.Response.Result === 'false' && Response.ErrLog === 'EMPTY') {
                    jsonState.isSubmitting = false;
                    setComponentState(jsonState);
                    showMessage("Thông tin Claim rỗng!");
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

            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
            this.props.closeToHome();
        }
    }

    closePopupSucceededRedirect(event) {
        // this.props.handlerGoToStep(CLAIM_STATE.ND13_INFO_FOLLOW_CONFIRMATION);
        window.location.href = '/followup-claim-info';
        this.props.handlerGoToStep(CLAIM_STATE.DONE);
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
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
            const newClaimSubmissionState = this.isOlderThan18(this.props.selectedCliInfo.DOB)
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
            removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + this.props.selectedCliInfo.ClientID);
            setTimeout(() => {
                window.location.href = '/';
            }, 100);
        }

        return (<>
            {this.state.claimSubmissionState === CLAIM_STATE.INIT &&
                <section className="sccontract-warpper submision" id="scrollAnchor">
                    <div className="insurance">
                        <div className="heading">
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
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="../../img/icon/return_option.svg" alt=""/></i>
                            </div>
                            <div className="heading__tab">
                                <div className="step-container">
                                    <div className="step-wrapper">
                                        <div className="step-btn-wrapper">
                                            <div className="back-btn"
                                                 onClick={() => this.props.handlerBackToPrevStep(this.state.stepName)}>
                                                <button>
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
                                        <div className="progress-bar">
                                            <div
                                                className={(this.state.stepName >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>1</span>
                                                </div>
                                                <p>Thông tin sự kiện</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>2</span>
                                                </div>
                                                <p>Phương thức thanh toán</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>3</span>
                                                </div>
                                                <p>Kèm <br/>chứng từ</p>
                                            </div>
                                            <div
                                                className={(this.state.stepName >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                                                <div className="bullet">
                                                    <span>4</span>
                                                </div>
                                                <p>Hoàn tất yêu cầu</p>
                                            </div>
                                        </div>
                                        <div className="step-btn-save-quit">
                                            <div>
                                                <button>
                                                <span className="simple-brown" style={{zIndex: '30'}}
                                                      onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="stepform">
                            <div className="stepform__body">
                                {/* QUYỀN LỢI YÊU CẦU bẢO HIỂM */}
                                <div className="info">
                                    <div className="info__title">
                                        <h4>QUYỀN LỢI YÊU CẦU BẢO HIỂM</h4>
                                    </div>
                                    <div className="info__body">
                                        <div className="tabflex-wrapper">
                                            <div className="tabflex">
                                                <h5>Người được bảo hiểm</h5>
                                                {this.props.selectedCliInfo.FullName && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{this.props.selectedCliInfo.FullName}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Ngày sinh</h5>
                                                {this.props.selectedCliInfo.DOB && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{formatDate(this.props.selectedCliInfo.DOB)}</p>}
                                            </div>
                                            <div className="tabflex">
                                                <h5>Số giấy tờ nhân thân</h5>
                                                {this.props.selectedCliInfo.POID && <p style={{
                                                    maxWidth: "300px", textAlign: "right"
                                                }}>{this.props.selectedCliInfo.POID.trim()}</p>}
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
                                <div className="top-dash-line-short-margin"></div>
                                {/* THÔNG TIN SỰ KIỆN */}
                                <div className="info">
                                    <div className="info__title">
                                        <h4>THÔNG TIN SỰ KIỆN BẢO HIỂM</h4>
                                        <span className="update-btn simple-brown4"
                                              onClick={() => this.props.handlerGoToStep(CLAIM_STATE.CLAIM_DETAIL)}>Cập nhật</span>
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
                                                                <i><img src="../../img/icon/edit.svg"
                                                                        alt=""/></i>
                                                            </div>
                                                        </div>
                                                        <div className="tab__content">
                                                            <div className="yellow-dropdown dropdown"
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
                                                                                maxWidth: "300px", textAlign: "right"
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
                                                                <i><img src="../../img/icon/edit.svg"
                                                                        alt=""/></i>
                                                            </div>
                                                        </div>
                                                        <div className="tab__content">
                                                            <div className="yellow-dropdown dropdown"
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
                                                                            { this.props.claimDetailState.accidentInfo.districtCode !== 'ZZZ' &&
                                                                            <div className="tab">
                                                                                <p className="simple-brown">-
                                                                                    Quận/Huyện</p>
                                                                                <p className="simple-brown"
                                                                                   style={{
                                                                                       maxWidth: "300px",
                                                                                       textAlign: "right"
                                                                                   }}>{accidentInfo.districtName}</p>
                                                                            </div>}
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
                                                                                {/* {accidentInfo.address + (accidentInfo.districtName !== null && accidentInfo.districtName !== undefined && accidentInfo.districtName !== "" ? ", " + accidentInfo.districtName : "") + (accidentInfo.cityName !== null && accidentInfo.cityName !== undefined && accidentInfo.cityName !== "" ? ", " + accidentInfo.cityName : "")}</p> */}
                                                                                {accidentInfo.address + (this.props.claimDetailState.accidentInfo.districtCode !== 'ZZZ' && accidentInfo.districtName !== null && accidentInfo.districtName !== undefined && accidentInfo.districtName !== "" ? ", " + accidentInfo.districtName : "") + (accidentInfo.cityName !== null && accidentInfo.cityName !== undefined && accidentInfo.cityName !== "" ? ", " + accidentInfo.cityName : "")}</p>
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
                                                                <i><img src="../../img/icon/edit.svg"
                                                                        alt=""/></i>
                                                            </div>
                                                        </div>
                                                        <div className="tab__content">
                                                            <div className="yellow-dropdown dropdown"
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
                                                                        {this.props.claimDetailState.deathInfo.districtCode !== 'ZZZ' &&
                                                                        <div className="tab">
                                                                            <p className="simple-brown">-
                                                                                Quận/Huyện</p>
                                                                            <p className="simple-brown" style={{
                                                                                maxWidth: "300px", textAlign: "right"
                                                                            }}>{deathInfo.districtName}</p>

                                                                        </div>
                                                                        }

                                                                        <div className="tab">
                                                                            <p className="simple-brown">- Địa
                                                                                chỉ cụ
                                                                                thể</p>
                                                                            <p className="simple-brown" style={{
                                                                                maxWidth: "300px", textAlign: "right"
                                                                            }}>
                                                                                {/* {deathInfo.address + (deathInfo.districtName !== null && deathInfo.districtName !== undefined && deathInfo.districtName !== "" ? ", " + deathInfo.districtName : "") + (deathInfo.cityName !== null && deathInfo.cityName !== undefined && deathInfo.cityName !== "" ? ", " + deathInfo.cityName : "")} */}
                                                                                {deathInfo.address + (this.props.claimDetailState.deathInfo.districtCode !== 'ZZZ' && deathInfo.districtName !== null && deathInfo.districtName !== undefined && deathInfo.districtName !== "" ? ", " + deathInfo.districtName : "") + (deathInfo.cityName !== null && deathInfo.cityName !== undefined && deathInfo.cityName !== "" ? ", " + deathInfo.cityName : "")}
                                                                                </p>
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
                                        <span className="update-btn simple-brown4"
                                              onClick={() => this.props.handlerGoToStep(CLAIM_STATE.CLAIM_DETAIL)}>Cập nhật</span>
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
                                                {/* <div className="tabflex">
                                                    <h5>Quận/Huyện</h5>
                                                    <p style={{
                                                        maxWidth: "300px", textAlign: "right"
                                                    }}>{item.districtName}</p>
                                                </div> */}
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
                                                {sumInvoice(item.invoiceList) > 0 && <div className="tabflex">
                                                    <h5>Tổng chi phí và điều trị</h5>
                                                    <p className="claim-submission-text"
                                                       style={{maxWidth: "300px", textAlign: "right"}}>
                                                        <NumberFormat displayType="search" type="search"
                                                                      value={sumInvoice(item.invoiceList)}
                                                                      thousandsGroupStyle="thousand"
                                                                      thousandSeparator={'.'}
                                                                      suffix={' VNĐ'}
                                                                      decimalSeparator=","
                                                                      decimalScale="0"/>
                                                    </p>
                                                </div>}
                                                {item.invoiceList && item.invoiceList.map((it, idx) => {
                                                    if (it.InvoiceNumber) {
                                                        return (<>
                                                            <div className="tabflex"
                                                                 key={'sub-faci-invoice-' + idx}>
                                                                <h5>Số hoá
                                                                    đơn {item.invoiceList.length > 1 ? idx + 1 : ''}</h5>
                                                                <p style={{
                                                                    maxWidth: "300px", textAlign: "right"
                                                                }}>
                                                                    {it.InvoiceNumber}
                                                                </p>
                                                            </div>
                                                            <div className="tabflex">
                                                                <h5>Số tiền</h5>
                                                                <p className="claim-submission-text"
                                                                   style={{
                                                                       maxWidth: "300px", textAlign: "right"
                                                                   }}>
                                                                    <NumberFormat displayType="search"
                                                                                  type="search"
                                                                                  value={it.InvoiceAmount}
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
                                                            <NumberFormat displayType="search"
                                                                          type="search"
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

                            <img className="decor-clip" src="../../img/mock.svg" alt=""/>
                            <img className="decor-person" src="../../img/person.png" alt=""/>
                        </div>

                        {/* Thông tin thanh toán */}
                        <div className="optionalform-wrapper">
                            <div className="optionalform">
                                <div className="optionalform__title">
                                    <h5 className="basic-bold">Thông tin thanh toán</h5>
                                    <span className="update-btn simple-brown4"
                                          onClick={() => this.props.handlerGoToStep(CLAIM_STATE.PAYMENT_METHOD)}>Cập nhật</span>
                                </div>
                                <div className="optionalform__body">
                                    <div className="tab-wrapper">
                                        {/* Phương thức thanh toán cho quyền lợi nhân thọ */}
                                        {(((lifeBenState.paymentMethodId === 'P2') && (lifeBenState.transferTypeState.cityName && lifeBenState.transferTypeState.bankName && lifeBenState.transferTypeState.bankBranchName && lifeBenState.transferTypeState.bankAccountNo)) || ((lifeBenState.paymentMethodId === 'P1') && lifeBenState.cashTypeState.cityName && lifeBenState.cashTypeState.bankName && lifeBenState.cashTypeState.bankBranchName && lifeBenState.cashTypeState.receiverName && lifeBenState.cashTypeState.receiverPin && lifeBenState.cashTypeState.receiverPinDate && lifeBenState.cashTypeState.receiverPinLocation)) &&
                                            <div className="tab">
                                                <div className="tab__content">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            <input placeholder={lifeBenState.paymentMethodName}
                                                                   disabled
                                                                   type="search"/>
                                                        </div>
                                                        <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                </div>
                                                <div className="tab__content">
                                                    <div className="yellow-dropdown dropdown"
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
                                            </div>}
                                        {/* Phương thức thanh toán cho quyền lợi sức khỏe */}
                                        {(((lifeBenState.paymentMethodId !== healthCareBenState.paymentMethodId) || ((lifeBenState.paymentMethodId === healthCareBenState.paymentMethodId) && 
                                        (((lifeBenState.paymentMethodId === 'P2') && 
                                        !((lifeBenState.transferTypeState.cityName === healthCareBenState.transferTypeState.cityName) && (lifeBenState.transferTypeState.bankName === healthCareBenState.transferTypeState.bankName) && (lifeBenState.transferTypeState.bankBranchName === healthCareBenState.transferTypeState.bankBranchName) && (lifeBenState.transferTypeState.bankAccountNo === healthCareBenState.transferTypeState.bankAccountNo) && (lifeBenState.transferTypeState.bankName === healthCareBenState.transferTypeState.bankName) && (lifeBenState.transferTypeState.bankBranchName === healthCareBenState.transferTypeState.bankBranchName) && (lifeBenState.transferTypeState.bankAccountNo === healthCareBenState.transferTypeState.bankAccountNo)))) 
                                        || (!lifeBenState.cashTypeState.bankName) && (lifeBenState.paymentMethodId === 'P1') && !((lifeBenState.cashTypeState.cityName === healthCareBenState.cashTypeState.cityName) && (lifeBenState.cashTypeState.bankName === healthCareBenState.cashTypeState.bankName) && (lifeBenState.cashTypeState.bankBranchName === healthCareBenState.cashTypeState.bankBranchName) && (lifeBenState.cashTypeState.receiverName === healthCareBenState.cashTypeState.receiverName) && (lifeBenState.cashTypeState.receiverPin === healthCareBenState.cashTypeState.receiverPin) && (lifeBenState.cashTypeState.receiverPinDate === healthCareBenState.cashTypeState.receiverPinDate) && (lifeBenState.cashTypeState.receiverPinLocation === healthCareBenState.cashTypeState.receiverPinLocation)))) && ((healthCareBenState.paymentMethodId === 'P2') && (healthCareBenState.transferTypeState.cityName && healthCareBenState.transferTypeState.bankName && healthCareBenState.transferTypeState.bankBranchName && healthCareBenState.transferTypeState.bankAccountNo)) || ((healthCareBenState.paymentMethodId === 'P1') && healthCareBenState.cashTypeState.cityName && healthCareBenState.cashTypeState.bankName && healthCareBenState.cashTypeState.bankBranchName && healthCareBenState.cashTypeState.receiverName && healthCareBenState.cashTypeState.receiverPin && healthCareBenState.cashTypeState.receiverPinDate && healthCareBenState.cashTypeState.receiverPinLocation)) &&
                                            <div className="tab">
                                                <div className="tab__content">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            <input
                                                                placeholder={healthCareBenState.paymentMethodName}
                                                                disabled type="search"/>
                                                        </div>
                                                        <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                </div>
                                                <div className="tab__content">
                                                    <div className="yellow-dropdown dropdown"
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
                                            </div>}
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
                                    <span className="update-btn simple-brown4"
                                          onClick={() => this.props.handlerGoToStep(CLAIM_STATE.CONTACT)}>Cập nhật</span>
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
                                            }}>{contactPersonInfo.dob ? formatDate(contactPersonInfo.dob) : ''}</p>
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
                                    <span className="update-btn simple-brown4"
                                          onClick={() => this.props.handlerGoToStep(CLAIM_STATE.ATTACHMENT)}>Cập nhật</span>
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
                        <div className="bottom-btn">
                            {this.state.isSubmitting?(
                            <button className="btn btn-primary disabled" id="submitClaimDetail"
                                    >Tiếp tục
                            </button>
                            ):(
                                <button className="btn btn-primary" id="submitClaimDetail"
                                        onClick={this.handlerOpenPopupConfirmClaim}>Tiếp tục
                                </button>
                            )}
                        </div>
                    </div>

                    {/* Popup confirm */}
                    <div className="popup confirm special" id="popupConfirmClaimSubmission">
                        <div className="popup__card">
                            <div className="confirm-card">
                                <div className="confirm-card-header">
                                    <div className="confirm-card-close-btn"
                                         onClick={this.handlerOnClickConfirmCloseButton}>
                                        <img src="../../img/icon/close-icon.svg" alt="closebtn"
                                             className="local-popup-btn"/>
                                    </div>
                                </div>
                                <div className="confirm-card-body">
                                    <p>Theo luật thuế FATCA của Hoa Kỳ, nếu người nhận tiền có các thông tin dưới
                                        đây, vui
                                        lòng chọn:</p>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox" htmlFor="isAmericanNationality">
                                            <input type="checkbox" name="isAmericanNationality"
                                                   id="isAmericanNationality"
                                                   checked={!!fatcaState.isAmericanNationality}
                                                   onChange={(event) => this.handlerOnChangeFatcaState(event)}/>
                                            <div className="checkmark">
                                                <img src="../../img/icon/check.svg" alt=""/>
                                            </div>
                                        </label>
                                        <p className="text">Quốc tịch Hoa Kỳ</p>
                                    </div>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox">
                                            <input type="checkbox" name="isAmericanResidence"
                                                   id="isAmericanResidence"
                                                   checked={!!fatcaState.isAmericanResidence}
                                                   onChange={(event) => this.handlerOnChangeFatcaState(event)}/>
                                            <div className="checkmark">
                                                <img src="../../img/icon/check.svg" alt=""/>
                                            </div>
                                        </label>
                                        <p className="text">Địa chỉ thường trú tại Hoa Kỳ</p>
                                    </div>
                                    <div className="checkbox-warpper">
                                        <label className="checkbox">
                                            <input type="checkbox" name="isAmericanTax" id="isAmericanTax"
                                                   checked={!!fatcaState.isAmericanTax}
                                                   onChange={(event) => this.handlerOnChangeFatcaState(event)}/>
                                            <div className="checkmark">
                                                <img src="../../img/icon/check.svg" alt=""/>
                                            </div>
                                        </label>
                                        <p className="text">Thực hiện khai báo thuế tại Hoa Kỳ</p>
                                    </div>
                                    <div className="dash-border"></div>
                                    <div className="title-head">
                                        <p>Xác nhận và cam kết của Người yêu cầu giải quyết quyền lợi bảo hiểm:</p>
                                    </div>
                                    <div className="content">
                                        <p>
                                            Tôi cam kết các thông tin đã cung cấp trong yêu cầu quyền lợi bảo hiểm
                                            này là
                                            hoàn toàn đầy đủ và chính xác. <br/>
                                            Tôi cho phép bất kỳ nhân viên y tế, cơ sở y tế, doanh nghiệp bảo hiểm,
                                            tổ chức
                                            hay cá nhân nào khác được
                                            cung cấp cho Dai-ichi Life Việt Nam bất kỳ thông tin, tài liệu nào liên
                                            quan đến
                                            Người được bảo hiểm
                                            và/hoặc Bên mua bảo hiểm theo yêu cầu của Dai-ichi Life Việt Nam.
                                        </p>
                                    </div>
                                </div>
                                <div className="btn-footer" style={{flexDirection: 'column'}}>
                                    {jsonState.isSubmitting && <div style={{
                                        display: 'flex', justifyContent: 'center', alignItems: 'center'
                                    }}>
                                        <LoadingIndicator area="submit-init-claim"/>
                                    </div>}
                                    <button
                                        className={`${jsonState.isSubmitting ? 'disabled' : ''} btn btn-primary `}
                                        disabled={jsonState.isSubmitting}
                                        style={{
                                            display: 'flex',
                                            justifyContent: 'center',
                                            alignItems: 'center',
                                            marginTop: 16
                                        }}
                                        id="fatca_id" onClick={this.handlerOpenPopupSucceeded}>
                                        {(this.state.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap) && ((this.props.paymentMethodState.choseReceiver === 'PO') && this.isOlderThan18(this.props.selectedCliInfo.DOB)) || (!this.isOlderThan18(this.props.selectedCliInfo.DOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap))) ? 'Tiếp tục' : 'Hoàn Thành'}
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="popupbg"></div>
                    </div>
                </section>}

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

            {this.state.claimSubmissionState === CLAIM_STATE.ND13_INFO_CONFIRMATION && <ND13POConfirm
                contactInfo={this.props.selectedCliInfo}
                isSubmitting={this.state.isSubmitting}
                selectedCliInfo={this.props.selectedCliInfo}
                claimSubmissionState={this.state.claimSubmissionState}
                currentState={this.props.currentState}
                handlerBackToPrevSubmisionState={this.handlerBackToPrevSubmisionState}
                onCompletedND13={async (e) => {
                    // Submit CP consent confirmation
                    const consentSubmitData = !isEmpty(this.state.clientProfile) && this.state.clientProfile.filter((item) => item.Role === 'PO') ? this.state.clientProfile.filter((item) => item.Role === 'PO')[0] : [];
                    await this.submitCPConsentConfirmationPO(this.addRequestParamCPConsent([consentSubmitData], "PO", "YY"));
                    // console.log('nn=consentSubmitData', consentSubmitData);

                    // if (this.checkPOAndLIEquality(getSession(CLIENT_ID), this.props.selectedCliInfo)) {
                    //     this.confirmCPConsent();
                    //     document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";

                    //     const jsonState = {...this.state};
                    //     let fatcaState = jsonState.submissionState.fatcaState;
                    //     fatcaState['isAmericanNationality'] = false;
                    //     fatcaState['isAmericanResidence'] = false;
                    //     fatcaState['isAmericanTax'] = false;
                    //     this.setState(jsonState);
                    // } else {
                    //     this.fetchCPConsentConfirmation(this.state.TrackingID);
                    // }
                    this.props.callBackTrackingId(this.state.TrackingID);
                    this.props.callBackClaimID(this.state.ClaimID);
                }}/>}

            {this.state.claimSubmissionState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18 &&
                <ND13POContactInfoOver18
                    clientProfile={this.state.clientProfile}
                    contactInfo={this.props.liInfo}
                    TrackingID={this.props.trackingId}
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
                    claimSubmissionState={this.state.claimSubmissionState}
                    currentState={this.props.currentState}
                    callBackOnlyPayment={this.props.callBackOnlyPayment}
                    handlerBackToPrevSubmisionState={this.handlerBackToPrevSubmisionState}
                    handleSaveLocalAndQuit={this.props.handleSaveLocalAndQuit}
                    onClickConfirmBtn={async (consentSubmitData, status) => {
                        // Submit CP consent confirmation
                        // console.log("consentSubmitData", consentSubmitData);
                        if (status === 'NotND13') {
                            this.submitCPConsentConfirmationOnlyPayment(consentSubmitData);
                        } else {
                            // Submit CP consent confirmation
                            this.submitCPConsentConfirmationOver18(consentSubmitData);
                        }
                    }}/>}

            {this.state.claimSubmissionState === CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18 &&
                <ND13POContactInfoUnder18
                    clientProfile={this.state.clientProfile}
                    totalInvoiceAmount={this.calculateTotalInvoiceAmount(this.props.claimDetailState)}
                    configClientProfile={this.state.configClientProfile}
                    contactInfo={this.props.liInfo}
                    onClickCallBack={() => this.setState({isCancelRequest: true})}
                    paymentMethodState={this.props.paymentMethodState}
                    submissionState={this.props.submissionState}
                    handlerUpdateMainState={this.props.handlerUpdateMainState}
                    poConfirmingND13={this.state.poConfirmingND13}
                    claimCheckedMap={this.props.claimCheckedMap}
                    selectedCliInfo={this.props.selectedCliInfo}
                    handlerGoToStep={this.props.handlerGoToStep}
                    isSubmitting={this.state.isSubmitting}
                    claimSubmissionState={this.state.claimSubmissionState}
                    currentState={this.props.currentState}
                    callBackOnlyPayment={this.props.callBackOnlyPayment}
                    handlerBackToPrevSubmisionState={this.handlerBackToPrevSubmisionState}
                    handleSaveLocalAndQuit={this.props.handleSaveLocalAndQuit}
                    TrackingID={this.props.trackingId}
                    onClickConfirmBtn={async (consentSubmitData, status) => {
                        // console.log("Submit CP consent confirmation", consentSubmitData);
                        if (status === 'NotND13') {
                            this.submitCPConsentConfirmationOnlyPayment(consentSubmitData);
                        } else {
                            // Submit CP consent confirmation
                            this.submitCPConsentConfirmation(consentSubmitData, status);
                        }
                    }}
                />}

            {this.state.isCancelRequest && <ND13CancelRequestConfirm
                onClickExtendBtn={() => clearRequest()}
                onClickCallBack={() => this.setState({isCancelRequest: false})}
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
        </>);
    }

}

export default ClaimSubmission;
