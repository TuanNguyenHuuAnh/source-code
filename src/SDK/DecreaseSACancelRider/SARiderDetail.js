import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import {maskPhone, deleteND13DataTemp, getUrlParameter, getSession, formatFullName, topFunction, cpSaveLogSDK } from '../../util/common';
import {callbackAppOpenLink,trackingEvent} from '../sdkCommon';
import {onlineRequestSubmitESubmissionCSA, genOTP, CPConsentConfirmation, onlineRequestSubmitConfirm, CPGetPolicyInfoByPOLID} from '../../util/APIUtils';
import LoadingIndicator from '../../common/LoadingIndicator2';
import POWarningND13 from "../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ND13 from "../ND13";
import DOTPInput from '../../components/DOTPInput';
import PayModeNoticePopup from '../../components/PayModeNoticePopup';
import parse from 'html-react-parser';
import SuccessChangePaymodePopup from '../../components/SuccessChangePaymodePopup';
import {
    FUND_STATE,
    COMPANY_KEY,
    AUTHENTICATION,
    WEB_BROWSER_VERSION,
    FE_BASE_URL,
    ConsentStatus,
    CONFIRM_ACTION_MAPPING,
    NOTE_MAPPING,
    OTP_INCORRECT,
    OTP_EXPIRED,
    PAGE_POLICY_PAYMENT,
    SCREENS,
    IS_MOBILE,
    SUB_STATE,
    FULL_NAME,
    CLIENT_CLASS,
    PageScreen
  } from '../../constants';
  import ProductCategoryGroupByLI from './ProductCategoryGroupByLI';
  import ProductCategoryGroupByLIReview from './ProductCategoryGroupByLIReview';
  import {isEmpty} from "lodash";
  import ThanksPopup from '../../components/ThanksPopup';

let from = '';
class SARiderDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            radioPayModeYear: false,
            radioPayModeHaflYear: false,
            newFrequency: '',
            polMPremAmt: '',
            polMPremAmtMax: '',
            polMPremAmtAddtional: '',
            newFrequencyCode: '',
            newPolMPremAmt: 0,
            exceedMax: false,
            errMessage: '',
            OTP: '',
            trackingId: '',
            openPopupWarningDecree13: false,
            clientProfile: null,
            configClientProfile: null,
            clientListStr: '',
            appType: '',
            proccessType: '',
            stepName: this.props.stepName,
            showNotice: false,
            acceptPolicy: false,
            isValidInput: false,
            agree: false,
            tickDecreaseSAMap: {},
            tickCancelRiderMap: {},
            faceAmountMap: {},
            tickBenefitMap: {},
            showProductDetailMap: {'0-0': true, '1-0': true},
            dropdownFeeDetailsMap: {},
            liHCMap: {},
            isSubmitting: false
        };

        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerToggleDecreaseSA = this.toggleDecreaseSA.bind(this);
        this.handlerInputFaceAmount = this.inputFaceAmount.bind(this);
        this.handlerToggleCancelRider = this.toggleCancelRider.bind(this);
        this.handlerToggleBenefit = this.toggleBenefit.bind(this);
        this.handlerDropdownContent = this.dropdownContent.bind(this);
        this.handlerDropdownFeeDetails = this.dropdownFeeDetails.bind(this);
        this.handlerUntickCancelRider = this.untickCancelRider.bind(this);
        this.handlerUntickDecreaseSA = this.untickDecreaseSA.bind(this);
        this.handlerUpdateliHCMap = this.updateliHCMap.bind(this);
        this.handlerDeleteTickBenefitMapItem = this.deleteTickBenefitMapItem.bind(this);
        this.handlerSetBenefit = this.setBenefit.bind(this);
    }

    updateliHCMap(value) {
        this.setState({liHCMap: value});
    }

    deleteTickBenefitMapItem(keys) {
        let tickBenefitMap = {...this.state.tickBenefitMap};
        for (let i = 0; i < keys.length; i++) {
            if (tickBenefitMap[keys[i]]) {
                delete tickBenefitMap[keys[i]];
                console.log('after delete=', tickBenefitMap);
            } else {
                console.log('deleteTickBenefitMapItem not found=', keys[i]);
            }
        }
        this.setState({tickBenefitMap: tickBenefitMap});
    }

    updateValidInput(value) {
        this.setState({isValidInput: value});
    }

    componentDidMount() {
        from = getUrlParameter("fromApp");
        cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            from
        );
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

    popupOtpSubmit = (OTP) => {
        this.submitProccessConfirm(OTP);
    }

    submitProccessConfirm = (OTP) => {
        let submitRequest = {
            jsonDataInput: {
              Company: COMPANY_KEY,
              Note: this.props.proccessType + 'ProcessConfirm',
              Action: CONFIRM_ACTION_MAPPING[this.props.proccessType]?CONFIRM_ACTION_MAPPING[this.props.proccessType]: 'SinglePSProcessConfirm',
              RequestTypeID: this.props.proccessType,
              APIToken: this.props.apiToken,
              Authentication: AUTHENTICATION,
              DeviceId: this.props.deviceId,
              OS: WEB_BROWSER_VERSION,
              Project: "mcp",
              TransactionID: this.state.trackingId,
              UserLogin: this.props.clientId,
              ClientID: this.props.clientId,
              PolicyNo: this.props.polID,
              OtpVerified: OTP,
              TransactionVerified: this.state.transactionId
            }
          }
          console.log(submitRequest);
          onlineRequestSubmitConfirm(submitRequest)
          .then(res => {
            console.log(res.Response);
              console.log('Confirm ' + this.props.proccessType + ' is saved successfull.');
              if ((res.Response.Result === 'true') && (res.Response.ErrLog === ('Confirm ' + this.props.proccessType + ' is saved successfull.')) && res.Response.Message) {
                  // upload images
                  console.log('submitProccessConfirm success', this.state.trackingId);
                  deleteND13DataTemp(this.props.clientId, this.state.trackingId, this.props.apiToken, this.props.deviceId);
                  this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});

            } else if (res.Response.ErrLog === 'OTP Exceed') {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                this.setState({errMessage: OTP_INCORRECT});
            } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                this.setState({errMessage: OTP_EXPIRED});
            } else {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }

          }).catch(error => {
        //   alert(error);
      });

    }

    closePopupSucceededRedirect(event) {
        this.setState({showSuccess: false});

        if (this.props.appType === 'CLOSE') {
            window.location.href = '/update-policy-info';
        } else {
            let obj = {
                Action: "END_ND13_" + this.props.proccessType,
                ClientID: this.props.clientId,
                PolicyNo: this.props.polID,
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

    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0, errMessage: ''});
    }
    
    closeThanks = () => {
        this.setState({showThanks: false});
    }

    inputFaceAmount = (index, value) =>{
        let jsonState = this.state;
        jsonState.faceAmountMap[index] = value;
        this.setState(jsonState);
        let validate = this.validateInput();
        this.updateValidInput(validate);
    }

    toggleDecreaseSA = (key, item) => {
        let tickDecreaseSAMap = this.state.tickDecreaseSAMap;
        if (tickDecreaseSAMap[key]) {
            tickDecreaseSAMap[key] = '';
        } else {
            tickDecreaseSAMap[key] = item;
        }
        this.setState({tickDecreaseSAMap: tickDecreaseSAMap});
        let validate = this.validateInput();
        this.updateValidInput(validate);
    }

    toggleCancelRider = (key, item) => {
        let tickCancelRiderMap = this.state.tickCancelRiderMap;
        if (tickCancelRiderMap[key]) {
            tickCancelRiderMap[key] = '';
        } else {
            tickCancelRiderMap[key] = item;
        }
        this.setState({tickCancelRiderMap: tickCancelRiderMap});
        let validate = this.validateInput();
        this.updateValidInput(validate);
    }

    untickDecreaseSA = (key) => {
        let tickDecreaseSAMap = this.state.tickDecreaseSAMap;
        if (tickDecreaseSAMap[key]) {
            tickDecreaseSAMap[key] = '';
            this.setState({tickDecreaseSAMap: tickDecreaseSAMap});
            let validate = this.validateInput();
            this.updateValidInput(validate);
        } 
    }

    untickCancelRider = (key) => {
        let tickCancelRiderMap = this.state.tickCancelRiderMap;
        if (tickCancelRiderMap[key]) {
            tickCancelRiderMap[key] = '';
            this.setState({tickCancelRiderMap: tickCancelRiderMap});
            let validate = this.validateInput();
            this.updateValidInput(validate);
        } 
    }
    
    toggleBenefit = (key, item) => {
        let tickBenefitMap = this.state.tickBenefitMap;
        if (tickBenefitMap[key]) {
            tickBenefitMap[key] = '';
        } else {
            tickBenefitMap[key] = item;
        }
        this.setState({tickBenefitMap: tickBenefitMap});
        let validate = this.validateInput();
        this.updateValidInput(validate);
    }

    setBenefit = (key, item) => {
        let tickBenefitMap = this.state.tickBenefitMap;
        tickBenefitMap[key] = item;
        this.setState({tickBenefitMap: tickBenefitMap});
        let validate = this.validateInput();
        this.updateValidInput(validate);
    }

    validateInput = () => {
        if (isEmpty(this.state.tickDecreaseSAMap)) {
            if (isEmpty(this.state.tickCancelRiderMap) && isEmpty(this.state.tickBenefitMap)) {
                return false;
            } else {
                for (let key in this.state.tickCancelRiderMap) {
                    if (this.state.tickCancelRiderMap[key]) {
                        return true;
                    }
                }

                for (let key in this.state.tickBenefitMap) {
                    if (this.state.tickBenefitMap[key]) {
                        return true;
                    }
                }


            } 
        } else {
            let count = 0;
            for (let key in this.state.tickDecreaseSAMap) {
                if (this.state.tickDecreaseSAMap[key]) {
                    if (!this.state.faceAmountMap[key] || (this.state.faceAmountMap[key] <= 0) || (this.state.faceAmountMap[key] >= parseInt(this.state.tickDecreaseSAMap[key]?.FaceAmount.replaceAll('.', '')))) {
                        return false;
                    }
                    count++;
                }
            }
            if (count > 0) {
                return true;
            }
            for (let key in this.state.tickCancelRiderMap) {
                if (this.state.tickCancelRiderMap[key]) {
                    return true;
                }
            }

            for (let key in this.state.tickBenefitMap) {
                if (this.state.tickBenefitMap[key]) {
                    return true;
                }
            }
            return false;
        }
    } 

    dropdownContent = (index) => {
        let jsonState = this.state;
        if (!jsonState.showProductDetailMap) {
            jsonState.showProductDetailMap = {};
        }
        if (jsonState.showProductDetailMap[index]) {
            jsonState.showProductDetailMap[index] = false;
        } else {
            jsonState.showProductDetailMap[index] = true;
        }
        this.setState(jsonState);
    }
    dropdownFeeDetails = (LifeInsureID) => {
        let jsonState = this.state;
        if (jsonState.dropdownFeeDetailsMap[LifeInsureID]) {
            jsonState.dropdownFeeDetailsMap[LifeInsureID] = false;
        } else {
            jsonState.dropdownFeeDetailsMap[LifeInsureID] = true;
        }
        this.setState(jsonState);
    }

    render() {
        // function scrollToTop() {
        //     window.scrollTo({
        //         top: 0,
        //         behavior: 'smooth'
        //     });
        // }
        
        // if (this.props.ProfileGroupBy && (this.props.stepName === FUND_STATE.VERIFICATION)) {
        //     scrollToTop();
        // }
        console.log('stepname=', this.props.stepName);
        console.log('subStepname=', this.props.subStepName);
        console.log('this.state.tickBenefitMap=', this.state.tickBenefitMap);
        const openPolicyPayment=(url)=> {
            if (getSession(IS_MOBILE)) {
                window.location.replace(url);
                window.location.reload();
            } else {
                window.open(url);
            }
        }
        const agree = () => {
            this.setState({agree: !this.state.agree});
        }
        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }
        const closeNotice = () => {
            this.setState({ showNotice: false });
        }
        const showNotice = () => {
            this.setState({ showNotice: true });
        }
        const radioFrequency = (Frequency, PolMPremAmtMax, PolMPremAmt, FrequencyCode) => {
            this.setState({newFrequency: Frequency, polMPremAmtMax: PolMPremAmtMax, polMPremAmt: PolMPremAmt, newFrequencyCode: FrequencyCode, polMPremAmtAddtional: '', newPolMPremAmt: PolMPremAmt});
        }

        const changeSASubmit = () => {
            if (!this.state.isValidInput) {
                return;
            }
            if (this.state.isSubmitting) {
                return;
            }
            this.setState({isSubmitting: true});
            let products = [];
            let productChangeSAMap = {};
            for (let key in this.state.tickDecreaseSAMap) {
                if (this.state.tickDecreaseSAMap[key] && this.state.faceAmountMap[key]) {
                    let item = this.state.tickDecreaseSAMap[key];
                    let clientChange = {
                        ClientId: item?.LifeInsureID,
                        ClientName: formatFullName(item?.PolicyLIName),
                        ProcessTypeCode: 'ChangeSAS',
                        FaceAmount: item.FaceAmount?.replaceAll('.', ','),
                        NewFaceAmount: this.state.faceAmountMap[key]?parseInt(this.state.faceAmountMap[key])?.toLocaleString('en-US').replace(/,/g, ','):'',
                        HCType: item?.HCCode?item?.HCCode: '',
                    }
                    let product = {};
                    if (!productChangeSAMap[item?.PlanCode.trim()]) {
                        product = {
                            PlanID: item?.PlanCode.trim(),
                            PlanName: item?.ProductName,
                            HCType: item?.HCCode?item?.HCCode: '',
                            ClientList: [
                                clientChange
                            ]
                        }
                    } else {
                        product = productChangeSAMap[item?.PlanCode.trim()];
                        let clientList = product.ClientList;
                        clientList.push(clientChange);
                        product.ClientList = clientList;
                        
                    }
                    productChangeSAMap[item?.PlanCode.trim()] = product;
                }
            }
			
            let productCancelRiderMap = {};
			for (let key in this.state.tickCancelRiderMap) {
                if (this.state.tickCancelRiderMap[key]) {
                    let item = this.state.tickCancelRiderMap[key];
                    let clientChange = {
                        ClientId: item?.LifeInsureID,
                        ClientName: formatFullName(item?.PolicyLIName),
                        ProcessTypeCode: 'CancelRidersS',
                        FaceAmount: '',
                        NewFaceAmount: '',
                        HCType: item?.HCCode?item?.HCCode: ''
                    }
                    let product = {};
                    if (!productCancelRiderMap[item?.PlanCode.trim()]) {
                        product = {
                            PlanID: item?.PlanCode.trim(),
                            PlanName: item?.ProductName,
                            ClientList: [
                                clientChange
                            ]
                        }
                    } else {
                        product = productCancelRiderMap[item?.PlanCode.trim()];
                        let clientList = product.ClientList;
                        clientList.push(clientChange);
                        product.ClientList = clientList;
                    }
                    productCancelRiderMap[item?.PlanCode.trim()] = product;
                }
            }

            let productBenefitMap = {};
            for (let key in this.state.tickBenefitMap) {
                if (this.state.tickBenefitMap[key]) {
                    let item = this.state.tickBenefitMap[key];
                    let clientChange = {
                        ClientId: item?.LifeInsureID,
                        ClientName: formatFullName(item?.PolicyLIName),
                        ProcessTypeCode: 'CancelRidersS',
                        FaceAmount: '',
                        NewFaceAmount: '',
                        HCType: item?.HCCode?item?.HCCode: ''
                    }
                    let product = {};
                    if (!productBenefitMap[item?.PlanCode.trim()]) {
                        product = {
                            PlanID: item?.PlanCode.trim(),
                            PlanName: item?.ProductName?.split('-')[1],
                            ClientList: [
                                clientChange
                            ]
                        }
                    } else {
                        product = productBenefitMap[item?.PlanCode.trim()];
                        let clientList = product.ClientList;
                        clientList.push(clientChange);
                        product.ClientList = clientList;
                    }
                    productBenefitMap[item?.PlanCode.trim()] = product;
                }
            }

            console.log('productChangeSAMap=', productChangeSAMap);
            if (productChangeSAMap) {
                let changeSAArray = Object.values(productChangeSAMap);
                console.log('changeSAArray=', changeSAArray);
                // products.push(changeSAArray);
                products = [...products, ...changeSAArray];
            }

            if (productCancelRiderMap) {
                let cancelRiderArray = Object.values(productCancelRiderMap);
                // products.push(cancelRiderArray);
                products = [...products, ...cancelRiderArray];
            }

            if (productBenefitMap) {
                let benefitArray = Object.values(productBenefitMap);
                // products.push(benefitArray);
                products = [...products, ...benefitArray];
            }

            let submitRequest = {
                jsonDataInput: {
                    OtpVerified: this.state.OTP,
                    APIToken: this.props.apiToken,
                    Action: "SubmitSinglePSProcess",
                    Authentication: AUTHENTICATION,
                    ClientClass: this.props.clientClass?this.props.clientClass: 'NOVIP',
                    ClientID: this.props.clientId,
                    ConsentList: [
                        {
                            ConsentRuleID: 'CSA_ConfirmChangeSundry',
                            ConsentResult: this.state.agree ? 'Y' : 'N',
                        },
                        {
                            ConsentRuleID: 'CSA_Agreement',
                            ConsentResult: 'Y'
                        }
                    ],
                    ClientName: this.props.clientName,
                    Company: COMPANY_KEY,
                    DeviceId: this.props.deviceId,
                    FromSystem: from ? "DCA" : "DCW",
                    NewValue: products,
                    OldValue: null,
                    OS: WEB_BROWSER_VERSION,
                    PolicyNo: this.props.polID,
                    Project: "mcp",
                    RequestTypeID: this.props.proccessType,
                    UserLogin: this.props.clientId
                }
            }
             console.log(submitRequest);
             onlineRequestSubmitESubmissionCSA(submitRequest)
                .then(res => {
                    if (res.Response.Result === 'true' && res.Response.ErrLog === 'Submit OS is saved successfull.') {
                        this.setState({trackingId: res.Response.Message});
                        fetchCPConsentConfirmation(res.Response.Message);
                    } else {
                        this.setState({isSubmitting: false});
                    }
                }).catch(error => {
                    this.setState({isSubmitting: false});
            });
            
            cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
            trackingEvent(
                "Giao dịch hợp đồng",
                `${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`,
                `${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`,
                from
            );
         
        }

        const startTimer = () => {
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

        const genOtpV2 = () => {
            //gen otp, email/phone get at backend
            const genOTPRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'GenOTPV2',
                    APIToken: this.props.apiToken,
                    Authentication: AUTHENTICATION,
                    ClientID: this.props.clientId,
                    DeviceId: this.props.deviceId,
                    Note: this.props.proccessType + 'ProcessConfirm',
                    OS: WEB_BROWSER_VERSION,
                    Project: 'mcp',
                    UserLogin: this.props.clientId,
                    DCID : this.props.DCID,
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
                    this.setState({isSubmitting: false});
                }).catch(error => {
                    this.setState({isSubmitting: false});
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                });
    
        }

        const closeOtp = () => {
            this.setState({showOtp: false, minutes: 0, seconds: 0});
        }

        const fetchCPConsentConfirmation = (TrackingID) => {
            console.log('fetchCPConsentConfirmation', TrackingID);
            let request = {
                jsonDataInput: {
                    Action: "CheckCustomerConsent",
                    APIToken: this.props.apiToken,
                    Authentication: AUTHENTICATION,
                    ClientID: this.props.clientId,
                    Company: COMPANY_KEY,
                    ClientList: this.props.clientId,
                    ProcessType: this.props.proccessType,
                    DeviceId: this.props.deviceId,
                    OS: WEB_BROWSER_VERSION,
                    Project: "mcp",
                    UserLogin: this.props.clientId,
                    TrackingID: TrackingID,
                }
            };
            console.log('fetchCPConsentConfirmation request', request)
   
            CPConsentConfirmation(request)
                .then(res => {
                    console.log('res=', res);
                    const Response = res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                        // const { DOB } = this.state.clientProfile;
                        const clientProfile = Response.ClientProfile;
                        const configClientProfile = Response.Config;
                        const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
    
                        const trackingID = TrackingID;
                        if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                            let state = this.state;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.trackingId = trackingID;
                            state.clientListStr = this.props.clientId;

                            // state.appType = 'CLOSE';
                            state.appType = this.props.appType;
                            state.proccessType = 'CSA';
                            state.stepName = FUND_STATE.SDK
                            this.setState(state);
                            this.props.setStepName(FUND_STATE.SDK);
                            this.setState({isSubmitting: false});
                        } else {
                            genOtpV2();
                        }
                    } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                        this.setState({
                            openPopupWarningDecree13: false,
                        });
                        genOtpV2();
                    }
                })
                .catch(error => {
                    this.setState({isSubmitting: false});
                });
        }

        return (
            <>
            {this.state.stepName !== FUND_STATE.SDK &&
                <section style={{
                    width: '100%',
                    maxWidth: '600px',
                    borderRadius: '8px',
                    height: '100%'
                }}>
                    <div className="stepform" style={{marginTop: '236px'}}>
                        <div className="contractform__head2">
                            <h3 className="contractform__head-title">THÔNG TIN HỢP ĐỒNG</h3>
                            <div className="contractform__head-content">
                                <div className="item">
                                    <p className="item-label">Hợp đồng</p>
                                    <p className="item-content basic-big">{this.props.polID} </p>
                                </div>
                                <div className="contractform__head-content">
                                    <div className="item">
                                        <p className="item-label">Dành cho</p>
                                        <p className="item-content basic-big">{this.props.PolicyLIName} </p>
                                    </div>
                                    {((this.props.stepName === FUND_STATE.UPDATE_INFO) || (this.props.stepName === FUND_STATE.VERIFICATION)) &&
                                        <>
                                        <div className="item">
                                            <p className="item-label">Định kỳ đóng phí</p>
                                            <p className="item-content">{this.props.Frequency}</p>
                                        </div>
                                        <div className="item">
                                            <p className="item-label">Phí định kỳ/cơ bản định kỳ</p>
                                            <p className="item-content basic-red basic-semibold">{this.props.PolMPremAmt} VNĐ</p>
                                        </div>
                                        {(this.props.PolicyClassCD !== 'TL') && (this.props.PolicyClassCD !== 'VE') && (this.props.PolicyClassCD !== 'MTL') &&
                                            <div className="item">
                                                <p className="item-label">Phí dự tính định kỳ</p>
                                                <p className="item-content basic-red basic-semibold">{this.props.PolSndryAmt} VNĐ</p>
                                            </div>
                                        }
                                        </>
                                    }
                                </div>

                            </div>
                        </div>
                        <img style={{ minWidth: '100%' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                        {(this.props.stepName === FUND_STATE.UPDATE_INFO)?(
                            (this.props.subStepName === SUB_STATE.AGREE)?(
                                <>
                                <h3 className="contractform__head-title" style={{paddingLeft: '20px'}}>THÔNG TIN PHÍ BẢO HIỂM SAU THAY ĐỔI</h3>
                                <div className="policy-info-tac" style={{
                                        textAlign: 'justify',
                                        lineHeight: '20px',
                                        color: '#727272',
                                        fontWeight: '700',
                                        fontFamily: 'Inter, sans-serif',
                                        paddingLeft: '12px',
                                        paddingTop: '12px'
                                    }}>
                                    <p style={{marginLeft: '8px', marginRight: '8px'}}>Phí định kỳ/cơ bản định kỳ sẽ được điều chỉnh theo yêu cầu nêu trên của Quý khách, vui lòng xác nhận nội dung sau đây nếu có:</p>
                                </div>
                                <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                    <div className={this.state.agree ? "bottom-text choosen" : "bottom-text"}
                                            style={{
                                                'maxWidth': '594px',
                                                padding: '6px'
                                            }}>
                                        <div
                                            className={this.state.agree ? "square-choose fill-red" : "square-choose"}
                                            style={{flex: '0 0 auto', height: '20px', cursor: 'pointer'}}
                                            onClick={() => agree()}>
                                            <div className="checkmark">
                                                <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                            </div>
                                        </div>
                                        <div className="policy-info-tac" style={{
                                            textAlign: 'justify',
                                            paddingLeft: '12px'
                                        }}>
                                            <p style={{textAlign: 'left', fontWeight: "500", paddingBottom: '16px'}}>Tôi yêu cầu điều chỉnh Phí dự tính định kỳ bằng với Phí định kỳ/cơ bản định kỳ sau thay đổi</p>
                                        </div>
                                    </div>
                                </div>
                                </>
                                

                            ):(
                                <h3 className="contractform__head-title" style={{paddingLeft: '20px'}}>ĐIỀU CHỈNH SẢN PHẨM</h3>
                            )
                        ):(
                            <h3 className="contractform__head-title" style={{paddingLeft: '20px'}}>XÁC NHẬN THÔNG TIN ĐIỀU CHỈNH</h3>
                        )}
                        
                        {/* <div className="contractform__body"> */}

                        {this.props.ProfileGroupBy && (this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName !== SUB_STATE.AGREE) &&
                            <ProductCategoryGroupByLI 
                            ClientProfileProducts={this.props.ProfileGroupBy}
                            isConfirm={this.props.isConfirm}
                            tickDecreaseSAMap={this.state.tickDecreaseSAMap}
                            tickCancelRiderMap={this.state.tickCancelRiderMap}
                            faceAmountMap={this.state.faceAmountMap}
                            tickBenefitMap={this.state.tickBenefitMap}
                            showProductDetailMap={this.state.showProductDetailMap}
                            dropdownFeeDetailsMap={this.state.dropdownFeeDetailsMap}
                            PlanCodeHCMap={this.props.PlanCodeHCMap}
                            liHCMap={this.state.liHCMap}
                            toggleDecreaseSA={this.handlerToggleDecreaseSA}
                            inputFaceAmount={this.handlerInputFaceAmount}
                            toggleCancelRider={this.handlerToggleCancelRider}
                            handlerUntickCancelRider={this.handlerUntickCancelRider}
                            handlerUntickDecreaseSA={this.handlerUntickDecreaseSA}
                            toggleBenefit={this.handlerToggleBenefit}
                            dropdownContent={this.handlerDropdownContent}
                            dropdownFeeDetails={this.handlerDropdownFeeDetails}
                            handlerUpdateliHCMap={this.handlerUpdateliHCMap}
                            handlerDeleteTickBenefitMapItem={this.handlerDeleteTickBenefitMapItem}
                            handlerSetBenefit={this.handlerSetBenefit}
                            />
                        }
                        {this.props.ProfileGroupBy && (this.props.stepName === FUND_STATE.VERIFICATION) && 
                            <ProductCategoryGroupByLIReview 
                            ClientProfileProducts={this.props.ProfileGroupBy}
                            tickDecreaseSAMap={this.state.tickDecreaseSAMap}
                            tickCancelRiderMap={this.state.tickCancelRiderMap}
                            faceAmountMap={this.state.faceAmountMap}
                            tickBenefitMap={this.state.tickBenefitMap}
                            agree={this.state.agree}
                            proccessType={this.props.proccessType}
                            />
                        }

                        {this.state.agree && (this.props.stepName === FUND_STATE.VERIFICATION) &&
                            <>
                                <img style={{ minWidth: '100%' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                                <h3 className="contractform__head-title" style={{marginLeft: '12px', marginRight: '12px'}}>THÔNG TIN PHÍ BẢO HIỂM SAU THAY ĐỔI</h3>
                                <div className='paymode-margin-bottom' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginLeft: '12px', marginRight: '12px' }}>
                                    <div className={"bottom-text choosen"}
                                        style={{
                                            'maxWidth': '594px',
                                            padding: '6px'
                                        }}>
                                        <div
                                            className={"square-choose-disabled fill-grey"}
                                            style={{ flex: '0 0 auto', height: '20px' }}
                                        >
                                            <div className="checkmark">
                                                <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                            </div>
                                        </div>
                                        <div className="policy-info-tac" style={{
                                            textAlign: 'justify',
                                            paddingLeft: '12px'
                                        }}>
                                            <p style={{ textAlign: 'left', fontWeight: "500", paddingBottom: '16px' }}>Tôi yêu cầu điều chỉnh Phí dự tính định kỳ bằng với Phí định kỳ/cơ bản định kỳ sau thay đổi</p>
                                        </div>
                                    </div>
                                </div>
                            </>
                        }
                        {/* </div> */}

                        <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt=""/>
                        <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                        
                    </div>
                    {(this.props.stepName === FUND_STATE.UPDATE_INFO) && !this.props.IsDegrading &&(
                        (this.props.subStepName === SUB_STATE.AGREE)?(
                            <>

                            </>
                        ):(
                            <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                <div className="bottom-text"
                                        style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                    <p style={{textAlign: 'justify'}}>
                                        <span className="red-text basic-bold">Lưu ý: </span><span
                                        style={{color: '#727272'}}>
                                        Nếu Quý khách có yêu cầu tăng số tiền bảo hiểm hoặc thay đổi chương trình bảo hiểm chăm sóc sức khỏe, vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.
                                        </span>
                                    </p>
                                </div>
                            </div>
                        )
                    )

                    }
                    {this.props.stepName === FUND_STATE.VERIFICATION && (
                        <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
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
                                        <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
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
                                            - Tất cả thông tin trên đây là đầy đủ, đúng sự thật và hiểu rằng yêu cầu này chỉ có hiệu lực kể từ ngày được Dai-ichi Life Việt Nam (DLVN) chấp nhận.
                                        </li>
                                        <li className="sub-list-li">
                                            - Việc thay đổi số tiền bảo hiểm của sản phẩm bảo hiểm chính/thay đổi sản phẩm bổ trợ có thể sẽ làm thay đổi số tiền bảo hiểm hoặc chấm dứt hiệu lực của sản phẩm bổ trợ khác để phù hợp với các quy định hiện hành của DLVN và Quy tắc và điều khoản Hợp đồng bảo hiểm.
                                        </li>
                                        <li className="sub-list-li">
                                        - Yêu cầu thay đổi giảm số tiền bảo hiểm của sản phẩm bảo hiểm Liên kết đơn vị An Thịnh Đầu Tư sẽ có hiệu lực kể từ ngày đến hạn nộp phí nếu yêu cầu thực hiện trong thời gian gia hạn đóng phí.
                                        </li>
                                        <li className="sub-list-li">
                                            - Với xác nhận hoàn tất giao dịch, đồng ý với 
                                            {getSession(IS_MOBILE)?(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href='#'
                                                onClick={()=>callbackAppOpenLink(PAGE_POLICY_PAYMENT, from)}
                                                >{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            ):(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href={PAGE_POLICY_PAYMENT}
                                                target='_blank'>{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            )}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    )

                    }
                    <div className="bottom-btn" style={{paddingBottom: '36px'}}>
                        { this.props.stepName === FUND_STATE.UPDATE_INFO ? (
                            this.state.isValidInput ? (
                                (this.props.subStepName === SUB_STATE.INIT) && (this.props.PolicyClassCD !== 'TL') && (this.props.PolicyClassCD !== 'VE') && (this.props.PolicyClassCD !== 'MTL') ?(
                                    <button className="btn btn-primary" onClick={() => this.props.setSubStepName(SUB_STATE.AGREE)} style={{zIndex: '197'}}>Tiếp tục</button>
                                ):(
                                    <button className="btn btn-primary" onClick={() => this.props.setStepName(FUND_STATE.VERIFICATION)} style={{zIndex: '197'}}>Tiếp tục</button>
                                )
                                
                            ): (
                                <button className="btn btn-primary disabled" disabled>Tiếp tục</button>
                            )
                            
                        ): (
                            this.state.acceptPolicy && !this.state.isSubmitting ? (
                                <button className="btn btn-primary" onClick={() => changeSASubmit()} style={{zIndex: '189'}}>Xác nhận</button>
                            ): (
                                <button className="btn btn-primary disabled" disabled>Xác nhận</button>
                            )

                        )
                        }
                        
                    </div>
                </section>
            }
            {/*START ND13*/}
                {this.state.appType && this.state.trackingId && (this.state.stepName === FUND_STATE.SDK) && <ND13
                appType={this.state.appType}
                trackingId={this.state.trackingId}
                clientListStr={this.props.clientId}
                clientId={this.props.clientId}
                proccessType={this.state.proccessType}
                deviceId={this.props.deviceId}
                apiToken={this.props.apiToken}
                policyNo={this.props.polID}
                phone={this.props.phone}
                clientName={this.props.clientName?this.props.clientName: getSession(FULL_NAME)}
            />}

            {/*END ND13*/}
            {this.state.showOtp &&
                <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                            closeOtp={this.closeOtp} errorMessage={this.state.errMessage}
                            popupOtpSubmit={this.popupOtpSubmit} reGenOtp={genOtpV2}
                            maskPhone={maskPhone(this.props.phone)}
                            />
            } 
            {this.state.openPopupWarningDecree13 && <POWarningND13 proccessType={this.state.proccessType} onClickExtendBtn={() => this.setState({
                openPopupWarningDecree13: false
            })}/>}
            {/* Popup succeeded redirect */}
            {this.state.showSuccess &&
                <ThanksPopup
                msg={'Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Chúng tôi sẽ xử lý yêu cầu và thông báo kết quả trong thời gian sớm nhất'}
                closeThanks={this.handlerClosePopupSucceededRedirect}/>
            }
            {this.state.showNotice &&
            <PayModeNoticePopup closePopup={closeNotice} title='Phí dự tính định kỳ' msg={parse('Phí dự tính định kỳ bao gồm Phí định kỳ/ <br/>cơ bản định kỳ (tạm tính) và Phí đóng <br/> thêm định kỳ (nếu có).')} imgPath={FE_BASE_URL + '/img/popup/fee-time.svg'} />
            }
        </>
        )
    }
}

export default SARiderDetail;
