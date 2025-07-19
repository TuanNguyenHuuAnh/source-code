import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    ADDRESS,
    AUTHENTICATION,
    CELL_PHONE,
    CLASSPO,
    CLASSPOREVERSEMAP,
    CLIENT_ID,
    CLIENT_PROFILE,
    COMPANY_KEY,
    DCID,
    EMAIL,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FULL_NAME,
    OS,
    OTHER_ADDRESS,
    OTP_EXPIRED,
    OTP_INCORRECT,
    PAGE_POLICY_PAYMENT, PAGE_REGULATIONS_PAYMENT, PageScreen,
    POL_LIST_INFORCE_CLIENT,
    REQUEST_GET_INFO_PSPROCESS_RESPONSE,
    SCREENS,
    SUBMIT_IN_24,
    TWOFA,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    WEB_BROWSER_VERSION,
    ConsentStatus,
    FUND_STATE
} from '../constants';
import {Select} from 'antd';
import './UpdateContactInfo.css';
import {
    CPGetPolicyListByCLIID, CPSaveLog,
    genOTP,
    GetConfiguration,
    getZipCodeMasterData,
    onlineRequestSubmit,
    onlineRequestSubmitUserInfo,
    verifyOTP,
    CPConsentConfirmation,
    onlineRequestSubmitConfirm
} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import {Link, Redirect} from 'react-router-dom';
import {
    formatFullName,
    getDeviceId,
    getSession,
    maskEmail,
    maskPhone,
    setSession,
    showMessage,
    trackingEvent
} from '../util/common';
import DOTPInput from '../components/DOTPInput';
import ErrorPopup from '../components/ErrorPopup';
import AlertPopupHight from '../components/AlertPopupHight';
import AlertPopupError from '../components/AlertPopupError';
import AuthenticationPopup from '../components/AuthenticationPopup';
import AlertPopupPhone from '../components/AlertPopupPhone';
import {Helmet} from "react-helmet";
import {find, isEmpty} from "lodash";
import AlertPopupClaim from "../components/AlertPopupClaim";
import checkPermissionIcon from '../img/popup/check-permission.png';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import ND13 from "../SDK/ND13";

// export const VALID_EMAIL_REGEX =
//     RegExp(/^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i);
export const VALID_EMAIL_REGEX =
    RegExp(/^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i && /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/);

export const CONTACT_STATE = Object.freeze({
    CATEGORY_INFO: 1,
    UPDATE_INFO: 2,
    VERIFICATION: 3
})

let polListProfileReview = [];

class UpdateContactInfo extends Component {
    constructor(props) {
        super(props);

        this.state = {
            jsonInput: {
                jsonDataInput: {
                    ClientID: getSession(CLIENT_ID)
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    Email: '',
                    UserLogin: getSession(USER_LOGIN),
                    Project: 'mcp',
                    ContactType: 'Yêu cầu thay đổi địa chỉ',
                    Action: 'ContactForm',
                    ClientID: getSession(CLIENT_ID),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    ContactPhone: getSession(CLIENT_PROFILE) ? JSON.parse(getSession(CLIENT_PROFILE))[0].cellPhone : "",
                    APIToken: getSession(ACCESS_TOKEN)
                }
            },
            jsonInput3: {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'AlterationForm',
                    Authentication: AUTHENTICATION,
                    BusinessAddress: getSession(OTHER_ADDRESS) ? getSession(OTHER_ADDRESS) : '',
                    BusinessPhone: getSession(CLIENT_PROFILE) ? JSON.parse(getSession(CLIENT_PROFILE))[0].BusinessPhone : "",
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    HomeAddress: getSession(ADDRESS) ? getSession(ADDRESS) : '',
                    HomePhone: getSession(CLIENT_PROFILE) ? JSON.parse(getSession(CLIENT_PROFILE))[0].HomePhone : "",
                    CellPhone: getSession(CELL_PHONE),
                    Email: getSession(EMAIL),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
            dtProposal: null,
            polID: '',
            buttonTieptuc: false,
            updateAddress: false,
            updateEmail: false,
            updatePhone: false,
            updateAddressIndex: '',
            homeAddress: {},
            oldHomeAddress: {},
            homeAddressRoad: '',
            homeFullAddress: '',
            homeContactType: '',
            oldHomeAddressRoad: '',
            oldHomeFullAddress: '',
            otherAddress: {},
            oldOtherAddress: {},
            otherAddressRoad: '',
            oldOtherAddressRoad: '',
            zipCodeList: null,
            homeCityCode: '',
            homeDistrictCode: '',
            homeWardCode: '',
            oldHomeCityCode: '',
            oldHomeDistrictCode: '',
            oldHomeWardCode: '',
            oldOtherCityCode: '',
            oldOtherDistrictCode: '',
            oldOtherWardCode: '',
            homeNumber: '',
            homeCity: '',
            homeDistrict: '',
            homeWard: '',
            oldHomeNumber: '',
            oldHomeCity: '',
            oldHomeDistrict: '',
            oldHomeWard: '',
            otherNumber: '',
            otherCity: '',
            otherDistrict: '',
            otherWard: '',
            oldOtherNumber: '',
            oldOtherCity: '',
            oldOtherDistrict: '',
            oldOtherWard: '',
            Email: '',
            oldEmail: '',
            cellPhone: getSession(CELL_PHONE),
            homePhone: getSession(CLIENT_PROFILE) ? JSON.parse(getSession(CLIENT_PROFILE))[0].HomePhone : "",
            businessPhone: getSession(CLIENT_PROFILE) ? JSON.parse(getSession(CLIENT_PROFILE))[0].BusinessPhone : "",
            nextScreen: false,
            isValidcellphone: true,
            isValidemail: true,
            isValidhomephone: true,
            isValidbusinessphone: true,
            errorCellphone: '',
            errorEmail: '',
            errorHomephone: '',
            errorBusinessphone: '',
            errorCity: '',
            errorDistrict: '',
            errorWard: '',
            errorAddressRoad: '',
            enabled: false,
            isCellPhoneChecked: false,
            isHomePhoneChecked: false,
            isBusinessPhoneChecked: false,
            stepName: CONTACT_STATE.CATEGORY_INFO,
            polListProfile: null,
            polListProfileReview: [],
            toggle: false,
            noPhone: false,
            noEmail: false,
            no2FA: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            showOtp: false,
            submitting: false,
            minutes: 0,
            seconds: 0,
            OTP: '',
            errorMessage: '',
            submitIn24: false,
            otpPhone: false,
            otpEmail: false,
            loading: false,
            note: '',
            apiError: false,
            acceptPolicy: false,
            ClientProfileList: null,
            ClientPolicyInfo: null,
            noValidPolicy: false,
            loadingEpolicy: false,
            isCheckPermission: false,
            msgCheckPermission: '',
            ContactType: '',
            ContactCode: '',
            ZipCode: '',
            CityCode: '',
            DistrictCode: '',
            WardCode: '',
            OldZipCode: '',
            ClientPolicyList: [],
            proccessType: '',
            clientListStr: '',
            trackingId: '',
            openPopupWarningDecree13: false,
            configClientProfile: null,
            appType: '',
            isSubmitting: false,
            showDistrict: false
        }
        this.handleInputPhone = this.handleInputPhone.bind(this);
        this.handleInputemail = this.handleInputemail.bind(this);
        this.handleInputHomeaddress = this.handleInputHomeaddress.bind(this);
        this.handleInputOtheraddress = this.handleInputOtheraddress.bind(this);
        this.handleInputHomePhone = this.handleInputHomePhone.bind(this);
        this.handleInputBusinessPhone = this.handleInputBusinessPhone.bind(this);
        this.handlerOnChangeCity = this.onChangeCity.bind(this);
        this.handlerOnChangeDistrict = this.onChangeDistrict.bind(this);
        this.handlerOnChangeWard = this.onChangeWard.bind(this);
        this.handlerSubmitUpdateEmail = this.submitUpdateInfo.bind(this);
        this.handlesubmitUpdateInfo = this.submitUpdateInfo.bind(this);
        this.handlerSubmitPhone = this.submitPhone.bind(this);
        this.handleSubmitUpdatePhone = this.submitUpdatePhone.bind(this);
        this.handleClosePopupEsc = this.closePopupEsc.bind(this);
        this.handleFilterDataByStatus = this.filterDataByStatus.bind(this);
        this.handleSetStepName = this.setStepName.bind(this);
    }

    setStepName = (step) => {
        this.setState({stepName: step});
    }

    isEnableSubmitBtn = () => {
        if (this.state.updateEmail) {
            if (this.state.Email === this.state.oldEmail) {
                return false;
            } else {
                return true;
            }
        } else if (this.state.updateAddress) {
            if (!this.state.homeCityCode || !this.state.homeDistrictCode || !this.state.homeWardCode || !this.state.homeAddressRoad.trim()) {
                return false;
            } else {
                return true;
            }
        }

    }

    handleInputPhone(event) {
        const target = event.target;
        const inputValue = target.value.trim();
        const jsonInput = this.state;
        jsonInput.cellPhone = inputValue;
        jsonInput.jsonInput3.jsonDataInput.CellPhone = inputValue;
        this.setState(jsonInput);
    }

    handleInputHomePhone(event) {
        const target = event.target;
        const inputValue = target.value.trim();
        const jsonInput = this.state;
        jsonInput.homePhone = inputValue;
        jsonInput.jsonInput3.jsonDataInput.HomePhone = inputValue;
        this.setState(jsonInput);
    }

    handleInputBusinessPhone(event) {
        const target = event.target;
        const inputValue = target.value.trim();
        const jsonInput = this.state;
        jsonInput.businessPhone = inputValue;
        jsonInput.jsonInput3.jsonDataInput.BusinessPhone = inputValue;
        this.setState(jsonInput);
    }

    handleInputemail(event) {
        const target = event.target;
        const inputValue = target.value.trim();
        const jsonInput = this.state;
        if (this.state.ClientPolicyInfo && (this.state.ClientPolicyInfo.length > 0)) {
            this.state.ClientPolicyInfo[0].ContactValue = inputValue;
        }
        jsonInput.Email = inputValue;
        this.setState(jsonInput);
    }

    handleInputHomeaddress(event) {
        const target = event.target;
        const inputValue = target.value;
        const jsonInput = this.state;
        if (jsonInput.homeAddress) {
            jsonInput.homeAddress.CLI_ADDR_LN_1_TXT = inputValue;
        }
        jsonInput.homeNumber = inputValue;
        jsonInput.homeAddressRoad = inputValue;
        this.setState(jsonInput);
    }

    handleInputOtheraddress(event) {
        const target = event.target;
        const inputValue = target.value;//.trim();
        const jsonInput = this.state;
        if (jsonInput.otherAddress !== null && jsonInput.otherAddress.trim() !== '') {
            jsonInput.otherAddress.CLI_ADDR_LN_1_TXT = inputValue;
        }
        jsonInput.otherNumber = inputValue;
        this.setState(jsonInput);
    }

    onChangeCity(value) {
        let jsonState = this.state;
        let cityObj = jsonState.zipCodeList.find((city) => city.CityCode === value);
        if (cityObj !== null) {
            if (jsonState.homeAddress) {
                jsonState.homeAddress.CLI_CITY_NM_TXT = cityObj.CityName;
            }
            if(cityObj.lstDistrict && cityObj.lstDistrict.length === 1){
                let districtObj = cityObj.lstDistrict.find( (district) => district.DistrictCode === "ZZZ");
                if(districtObj != null){
                    jsonState.showDistrict = false;
                    jsonState.homeDistrict = '';
                } else{
                    jsonState.showDistrict = true;
                    jsonState.homeDistrict = undefined;
                }
            }
            jsonState.homeCityCode = cityObj.CityCode;
            jsonState.homeCity = cityObj.CityName;
            jsonState.homeDistrict = undefined;
            jsonState.homeWard = undefined;

            this.setState(jsonState);
        }
    }

    onChangeDistrict(value) {
        
        const jsonState = this.state;
        let cityObj = jsonState.zipCodeList.find((city) => city.CityCode === jsonState.homeCityCode);
        if (jsonState.homeAddress) {
            jsonState.homeAddress.districtCode = value;
        }
        jsonState.homeDistrictCode = value;
        jsonState.homeDistrict = (cityObj == null) ? '' : (
            (cityObj.lstDistrict.find((district) => district.DistrictCode === value) == null) ? '' :
                cityObj.lstDistrict.find((district) => district.DistrictCode === value).DistrictName
        );
        jsonState.homeWard = undefined;
        this.setState(jsonState);
    }

    onChangeWard(value) {
        
        const jsonState = this.state;
        const cityHomeObj = jsonState.zipCodeList.find((city) => city.CityCode === jsonState.homeCityCode);
        if(cityHomeObj && cityHomeObj.lstDistrict.length == 1){
            jsonState.homeDistrictCode = cityHomeObj.lstDistrict[0].districtCode;
        }

        let districtHomeObj = (cityHomeObj !== null && cityHomeObj !== undefined) ? cityHomeObj.lstDistrict.find((homedistrict) => homedistrict.DistrictCode === jsonState.homeDistrictCode) : '';
        if (jsonState.homeAddress) {
            jsonState.homeAddress.wardCode = value;
        }

        if(districtHomeObj == null){
            districtHomeObj = cityHomeObj.lstDistrict.find((homedistrict) => homedistrict.DistrictCode === 'ZZZ') ;
            jsonState.homeDistrictCode = districtHomeObj.DistrictCode;
            jsonState.homeDistrict = districtHomeObj.DistrictName;
        }
        jsonState.homeWardCode = value;
        jsonState.homeWard = (districtHomeObj == null) ? '' : (
            (districtHomeObj.lstWard.find((ward) => ward.WardCode === value) == null) ? '' :
                districtHomeObj.lstWard.find((ward) => ward.WardCode === value).WardName
        );
        this.setState(jsonState);
    }

    validateInput() {
        let isInValid = true;
        let errorCity = '';
        let errorDistrict = '';
        let errorWard = '';
        let errorAddressRoad = '';
        let errorEmail = '';

        if (this.state.updateAddress === true) {
            errorCity = !this.state.homeCity ? "Tỉnh/Thành phố không được bỏ trống" : "";
            errorDistrict = !this.state.homeDistrict ? "Quận/Huyện phố không được bỏ trống" : "";
            errorWard = !this.state.homeWard ? "Phường/Xã phố không được bỏ trống" : "";
            errorAddressRoad = this.state.homeAddressRoad.trim() === "" ? "Địa chỉ chi tiết không được bỏ trống" : "";
        }

        if (this.state.updateEmail === true) {
            errorEmail = this.state.Email.length <= 0 ? "Email không hợp lệ" : (this.state.Email.length > 0 && !VALID_EMAIL_REGEX.test(this.state.Email)) ? "Email không hợp lệ" : "";
        }

        if ((errorCity !== "") || (errorDistrict !== "") || (errorWard !== "") || (errorAddressRoad !== "") || (errorEmail !== "")) {
            isInValid = false;
        } else {
            isInValid = true;
        }

        this.setState({
            errorCity: errorCity,
            errorDistrict: errorDistrict,
            errorWard: errorWard,
            errorAddressRoad: errorAddressRoad,
            errorEmail: errorEmail
        });
        return isInValid;
    }

    validatePhoneInput() {
        const jsonState = this.state;

        jsonState.errorCellphone = (jsonState.isCellPhoneChecked && (jsonState.cellPhone !== undefined) && !/^0[0-9]{9}/.test(jsonState.cellPhone)) ? "Số điện thoại phải bắt đầu với 0 và có độ dài 10 chữ số" : "";

        jsonState.errorHomephone = ((jsonState.isHomePhoneChecked && (jsonState.homePhone === undefined || jsonState.homePhone.length === 0)) || (jsonState.isHomePhoneChecked && jsonState.homePhone !== undefined && jsonState.homePhone.length > 0 && jsonState.homePhone.replace("(", "").replace(")", "").replaceAll(" ", "").length > 11) || ((jsonState.isHomePhoneChecked && jsonState.homePhone !== undefined && jsonState.homePhone.length > 0) && !/^\d+$/.test(jsonState.homePhone.replace("(", "").replace(")", "").replaceAll(" ", "")))) ? "Số điện thoại nhà phải có độ dài tối đa 11 kí tự và là chữ số" : "";

        jsonState.errorBusinessphone = (jsonState.isBusinessPhoneChecked && ((jsonState.businessPhone === undefined || jsonState.businessPhone.length === 0 || jsonState.businessPhone.replace("(", "").replace(")", "").replace(" ", "").length > 11) || ((jsonState.businessPhone.length > 0) && !/^\d+$/.test(jsonState.businessPhone.replace("(", "").replace(")", "").replace(" ", ""))))) ? "Số điện thoại cơ quan phải có độ dài tối đa 11 kí tự và là chữ số" : "";

        let isValid = true;

        if (jsonState.errorCellphone !== "") {
            jsonState.isValidcellphone = false;
            isValid = false;
        } else {
            jsonState.isValidcellphone = true;
        }

        if (jsonState.errorHomephone !== "") {
            jsonState.isValidhomephone = false;
            isValid = false;
        } else {
            jsonState.isValidhomephone = true;
        }
        if (jsonState.errorBusinessphone !== "") {
            jsonState.isValidbusinessphone = false;
            isValid = false;
        } else {
            jsonState.isValidbusinessphone = true;
        }

        this.setState(jsonState);
        return isValid;
    }

    nextConfirmUpdateInfo(type) {
        if (type === "Email") {
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_INFO}`,
                `Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_INFO}`,
            );
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_INFO}`);
        } else if (type === "Address") {
            trackingEvent(
                "Giao dịch hợp đồng",
                `Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_INFO}`,
                `Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_INFO}`,
            );
            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_INFO}`);
        }

        if (getSession(SUBMIT_IN_24)) {
            this.setState({submitIn24: true});
            return;
        }
        if (!this.validateInput()) {
            return;
        }
        const jsonState = this.state;
        jsonState.stepName = CONTACT_STATE.VERIFICATION;
        jsonState.errorEmail = '';
        jsonState.errorCity = '';
        jsonState.errorDistrict = '';
        jsonState.errorWard = '';
        jsonState.errorAddressRoad = '';
        this.setState(jsonState);
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

    submitUpdateInfo = () => {
        if (getSession(SUBMIT_IN_24)) {
            this.setState({submitIn24: true});
            return;
        }
        if (!this.validateInput()) {
            return;
        }
        this.setState({errorEmail: ''});
        if (!getSession(CELL_PHONE)) {
            //yêu cầu cập nhật số dt
            this.setState({noPhone: true});
            return;
        }
        if ((getSession(VERIFY_CELL_PHONE) === '0') || getSession(VERIFY_CELL_PHONE) === 'undefined') {
            this.setState({no2FA: true});
            return;
        }
        //All find then init request
        this.submitChangeAlterationForm();

    }


    submitChangeAlterationForm = () => {
        const jsonState = this.state;
        let jsonRequest = "";
        if (jsonState.updateAddress !== undefined && jsonState.updateAddress == true) {
            jsonRequest = {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'InitRequestSubmit',
                    Authentication: AUTHENTICATION,
                    ContactEmail: getSession(EMAIL),
                    ClientClass: CLASSPOREVERSEMAP[getSession(CLASSPO)],
                    ClientID: getSession(CLIENT_ID),
                    ClientName: formatFullName(getSession(FULL_NAME)),
                    Company: COMPANY_KEY,
                    DeviceId: getDeviceId(),
                    FromSystem: "DCW",
                    NewValue: {
                        Address: (jsonState.homeAddressRoad ? jsonState.homeAddressRoad.trim() : "") + ", " + jsonState.homeWard + (jsonState.homeDistrictCode === 'ZZZ' ? "" : (", " + jsonState.homeDistrict)) + ", " + jsonState.homeCity,
                        Address1: jsonState.homeAddressRoad.trim(),
                        AddressType: jsonState.ContactType,
                        AddressTypeCode: jsonState.ContactCode,
                        City: jsonState.homeCity,
                        CityCode: jsonState.homeCityCode,
                        Country: "Việt Nam",
                        CountryCode: "VN",
                        District: jsonState.homeDistrictCode === 'ZZZ' ? '' : jsonState.homeDistrict,
                        DistrictCode: jsonState.homeDistrictCode === 'ZZZ' ? '' : jsonState.homeDistrictCode,
                        Ward: jsonState.homeWard,
                        WardCode: jsonState.homeWardCode,
                        ZipCode: jsonState.homeCityCode + '-' + jsonState.homeDistrictCode + '-' + jsonState.homeWardCode,
                        IsBilling: "true",
                    },
                    OldValue: {
                        Address: jsonState.oldHomeFullAddress.trim(),
                        Address1: jsonState.oldHomeAddressRoad.trim(),
                        AddressType: jsonState.ContactType,
                        AddressTypeCode: jsonState.ContactCode,
                        City: jsonState.oldHomeCity,
                        CityCode: jsonState.oldHomeCityCode,
                        Country: "Việt Nam",
                        CountryCode: "VN",
                        District: jsonState.oldHomeDistrict,
                        DistrictCode: jsonState.oldHomeDistrictCode,
                        Ward: jsonState.oldHomeWard,
                        WardCode: jsonState.oldHomeWardCode,
                        ZipCode: jsonState.ZipCode,
                        IsBilling: "",
                    },
                    OS: "",
                    // PolicyList: !isEmpty(jsonState.ClientPolicyList) ? [jsonState.ClientPolicyList[0]] : [],
                    PolicyList: jsonState.ClientPolicyList,
                    RequestTypeID: "ADR",
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    // OtpVerified: this.state.otp,
                }
            };
        } else if ((jsonState.updateEmail !== undefined) && (jsonState.updateEmail === true)) {
            let polList = [];
            for (let i = 0; i < jsonState.polListProfile.length; i++) {
                let pol = {
                    Order: i + 1,
                    PolicyNo: jsonState.polListProfile[i].PolicyID
                };
                polList.push(pol);
            }
            jsonRequest = {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'InitRequestSubmit',
                    Authentication: AUTHENTICATION,
                    ContactEmail: getSession(EMAIL),
                    ClientClass: CLASSPOREVERSEMAP[getSession(CLASSPO)],
                    ClientID: getSession(CLIENT_ID),
                    ClientName: formatFullName(getSession(FULL_NAME)),
                    Company: COMPANY_KEY,
                    DeviceId: getDeviceId(),
                    FromSystem: "DCW",
                    NewValue: {
                        Email: jsonState.Email
                    },
                    OldValue: {
                        Email: jsonState.oldEmail
                    },
                    OS: "",
                    PolicyList: !isEmpty(polList) ? [polList[0]] : [],
                    RequestTypeID: "EML",
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    // OtpVerified: this.state.otp,
                }
            };
        }

        onlineRequestSubmitUserInfo(jsonRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'Submit OS is saved successfull.') {
                let state = this.state;
                state.trackingId = Response.Message;
                state.clientListStr = getSession(CLIENT_ID);
                state.appType = 'CLOSE';
                state.proccessType = this.state.proccessType;
                state.stepName = FUND_STATE.SDK
                this.setState(state);
            } else {
                this.setState({isSubmitting: false});
            }

        }).catch(error => {
            //console.log("onlineRequestSubmit__error__" + error);
        });

    }

    genOtp = () => {
        //gen otp
        let note = 'VALID_OTP_CHANGE_ADDRESS';
        let genOTPRequest = '';
        let otpPhone = true;
        let otpEmail = false;
        if (this.state.updatePhone === true) {
            note = 'VALID_OTP_CHANGE_PHONE';
            genOTPRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'GenOTPV2',
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    Email: getSession(EMAIL),
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Note: note,
                    OS: OS,
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    DCID : getSession(DCID)
                }

            }
            otpEmail = true;
            otpPhone = false;
        } else {
            if (this.state.updateEmail === true) {
                note = 'VALID_OTP_CHANGE_EMAIL';
            }
            genOTPRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'GenOTPV2',
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    // CellPhone: getSession(CELL_PHONE),
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Note: note,
                    OS: OS,
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    DCID : getSession(DCID)
                }

            }

        }

        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({
                        showOtp: true,
                        transactionId: response.Response.Message,
                        minutes: 5,
                        seconds: 0,
                        otpPhone: otpPhone,
                        otpEmail: otpEmail,
                        note: note
                    });
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
                ClientID: getSession(CLIENT_ID),//this.state.POID
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
                    this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: '', otp: OTP});
                    this.submitChangeAlterationForm();

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
            //this.props.history.push('/maintainence');
        });

    }

    submitPSConfirm = (OTP) => {
        this.setState({isSubmitting: true});
        let submitRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: "PSConfirm",
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
                TransactionVerified: this.state.transactionId,
                RequestTypeID: this.state.proccessType
            }
            }
            onlineRequestSubmitConfirm(submitRequest)
            .then(res => {
                if ((res.Response.Result === 'true') && (res.Response.ErrLog.indexOf('is saved successfull.' > 0)) && res.Response.Message) {
                    console.log('PSConfirm success', this.state.trackingId);
                    this.setState({showOtp: false, minutes: 0, seconds: 0, nextScreen: true, isSubmitting: false});
                } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({ errorMessage: OTP_EXPIRED });
                } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.setState({ showOtp: false, minutes: 0, seconds: 0, isSubmitting: false});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({ errorMessage: OTP_INCORRECT, isSubmitting: false });
                } else {
                    this.setState({ errorMessage: OTP_INCORRECT, isSubmitting: false });
                }

            }).catch(error => {
            // alert(error);
            this.setState({isSubmitting: false});
        });

    }

    startTimer = () => {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            else if (this.state.seconds === 0) {
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

    submitPhone() {
        if (!this.validatePhoneInput()) {
            return;
        }
        this.setState({stepName: CONTACT_STATE.VERIFICATION});
    }

    submitUpdateEmail() {
        this.submitUpdateInfo();
    }

    submitUpdatePhone() {
        this.submitUpdateInfo();
    }

    loadInfoDidMount() {

        if (getSession(REQUEST_GET_INFO_PSPROCESS_RESPONSE)) {
            let Response = JSON.parse(getSession(REQUEST_GET_INFO_PSPROCESS_RESPONSE));
            if (Response) {
                this.setState({
                    ClientProfileList: Response.ClientProfile,
                    ClientPolicyInfo: Response.PolicyInfo,
                    Email: Response.PolicyInfo[0].ContactValue,
                    oldEmail: Response.PolicyInfo[0].ContactValue,
                    loading: false
                });
                return;
            }
        }
        this.validateInput();
        const jsonState = this.state;
        this.setState({loading: true});
        const jsonRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: "RequestGetInfoPSProcess",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: "",
                OS: "",
                Project: "mcp",
                ClientID: getSession(CLIENT_ID),
                UserLogin: getSession(USER_LOGIN),
                RequestTypeID: "",
                FromSystem: "DCW",
                OtpVerified: this.state.otp,
            }
        };

        onlineRequestSubmit(jsonRequest).then(Res => {
            let Response = Res.Response;
            // console.log("onlineRequestSubmit", Response);

            if (Response.Result === 'true' && Response.ClientProfile !== null) {
                if (Response.PolicyInfo && (Response.PolicyInfo.length > 0)) {
                    jsonState.Email = Response.PolicyInfo[0].ContactValue;
                    jsonState.oldEmail = Response.PolicyInfo[0].ContactValue;
                    jsonState.loading = false;
                    setSession(REQUEST_GET_INFO_PSPROCESS_RESPONSE, JSON.stringify(Response));
                    this.setState({
                        ClientProfileList: Response.ClientProfile,
                        ClientPolicyInfo: Response.PolicyInfo,
                        Email: Response.PolicyInfo[0].ContactValue,
                        oldEmail: Response.PolicyInfo[0].ContactValue,
                        loading: false
                    });
                } else {
                    this.setState({
                        ClientProfileList: Response.ClientProfile,
                        ClientPolicyInfo: Response.PolicyInfo,
                        loading: false,
                        Email: '',
                        oldEmail: ''
                    });
                }

            }

        }).catch(error => {
            this.setState({loading: false});
        });
    }

    componentDidMount() {
        this.getValidILEpolicy();
        this.loadInfoDidMount();
        document.addEventListener("keydown", this.handleClosePopupEsc, false);
        this.fetchCheckConfigPermission();
        this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_HOME}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Open_${PageScreen.POL_TRANS_HOME}`,
            `Web_Open_${PageScreen.POL_TRANS_HOME}`,
        );
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleClosePopupEsc, false);
        this.cpSaveLog(`Web_Close_${PageScreen.POL_TRANS_HOME}`);
        trackingEvent(
            "Giao dịch hợp đồng",
            `Web_Close_${PageScreen.POL_TRANS_HOME}`,
            `Web_Close_${PageScreen.POL_TRANS_HOME}`,
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
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }

    filterDataByStatus (inputData) {
        const activeData = inputData.filter(item => item.PolicyStatus === "Đang hiệu lực");

        if (activeData.length > 0) {
            return activeData;
        }
        return inputData;
    }

    getValidILEpolicy() {
        this.setState({loadingEpolicy: true});

        if (!getSession(POL_LIST_INFORCE_CLIENT)) {
            const listRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: "GetPolicyInforce",
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
                // console.log("CPGetPolicyListByCLIID", Response);
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                    setSession(POL_LIST_INFORCE_CLIENT, JSON.stringify(Response.ClientProfile));
                    this.setState({
                        loadingEpolicy: false,
                        polListProfile: Response.ClientProfile,
                        noValidPolicy: ((!(Response.ClientProfile && Response.ClientProfile.length > 0)))
                    });
                } else {
                    this.setState({loadingEpolicy: false, polListProfile: null, noValidPolicy: true});
                }
            }).catch(error => {
                this.setState({loadingEpolicy: false, polListProfile: null, noValidPolicy: true});
                return null;
            });
        } else {
            let polListProfile = JSON.parse(getSession(POL_LIST_INFORCE_CLIENT));
            // console.log("polListProfile", polListProfile);

            this.setState({
                loadingEpolicy: false,
                polListProfile: polListProfile,
                noValidPolicy: ((polListProfile && polListProfile.length > 0) ? false : true)
            });
        }
    }

    closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeNoEmail();
            this.closeNoPhone();
            this.closeNoVerifyEmail();
            this.closeNo2FA();
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

    closeNo2FA = () => {
        this.setState({no2FA: false});
    }

    closeNoVerifyEmail = () => {
        this.setState({noVerifyEmail: false});
    }

    closeSubmitIn24 = () => {
        this.setState({submitIn24: false});
    }

    closeNotAvailable = () => {
        this.setState({
            msgCheckPermission: '',
            isCheckPermission: false
        });
        this.props.history.push('/');
    }

    getAdress = (addressType) => {
        return this.state.ClientProfileList && this.state.ClientProfileList.map((item, index) => {
            if (item.ContactCode === addressType) {
                return item.FullAddress;
            }
        })
    }

    fetchCPConsentConfirmation(TrackingID) {
        console.log('fetchCPConsentConfirmation', TrackingID)
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: getSession(CLIENT_ID),
                ProcessType: this.state.proccessType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID ?TrackingID: this.state.trackingId,
            }
        };
        console.log('fetchCPConsentConfirmation request', request)
        const clientListStr = getSession(CLIENT_ID);

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    // const { DOB } = this.state.clientProfile;
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = this.generateConsentResults(clientProfile)?.ConsentResultLI;

                    const trackingID = TrackingID ?TrackingID: this.state.trackingId;
                    if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                        let state = this.state;
                        state.clientProfile = clientProfile;
                        state.configClientProfile = configClientProfile;
                        state.trackingId = trackingID;
                        state.clientListStr = clientListStr;

                        state.appType = 'CLOSE';
                        state.proccessType = this.state.proccessType;
                        state.stepName = FUND_STATE.SDK
                        this.setState(state);
                        console.log('xx');
                    } else {
                        this.genOtpV2();
                    }
                } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                    this.genOtpV2();
                }
            })
            .catch(error => {
                console.log(error);
            });
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

    genOtpV2 = () => {
        let noteStr = 'VALID_OTP_CHANGE_EMAIL';
        if (this.state.updateAddress) {
            noteStr = 'VALID_OTP_CHANGE_ADDRESS';
        }

        //gen otp, email/phone get at backend
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTPV2',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: noteStr,
                OS: OS,
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                DCID : getSession(DCID),
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
    
    render() {
        let jsonState = this.state;
        const errorEmail = jsonState.errorEmail;
        jsonState = this.state;
        const {Option} = Select;
        const zipCodeList = this.state.zipCodeList;
        const oldZipCode = this.state.OldZipCode;
        let cityHomeObj;
        let districtHomeObj;
        let wardHomeObj;
        let oldCityCode;
        let oldDistrictCode;
        let oldWardCode;

        if(oldZipCode){
            const oldZipCodeArray = oldZipCode.split("-");
            if(oldZipCodeArray){
                if(oldZipCodeArray.length == 2){
                    oldCityCode = oldZipCodeArray[0].trim();
                    oldWardCode = oldZipCodeArray[1].trim();
                    oldDistrictCode = '';
                } else if(oldZipCodeArray.length == 3){
                    oldCityCode = oldZipCodeArray[0].trim();                    
                    oldDistrictCode = oldZipCodeArray[1].trim();
                    oldWardCode = oldZipCodeArray[2].trim();
                }
            }
        }

        if (zipCodeList !== null) {
            cityHomeObj = zipCodeList.find((city) => city?.CityCode === jsonState?.homeCityCode);
            if(jsonState?.homeDistrictCode && jsonState?.homeDistrictCode !== ''){
                districtHomeObj = (cityHomeObj !== null && cityHomeObj !== undefined) ? cityHomeObj?.lstDistrict?.find((homedistrict) => homedistrict?.DistrictCode === jsonState?.homeDistrictCode) : '';
            } else{
                districtHomeObj = (cityHomeObj !== null && cityHomeObj !== undefined) ? cityHomeObj?.lstDistrict?.find((homedistrict) => homedistrict?.DistrictCode === oldDistrictCode) : '';
            }
                        
            if(districtHomeObj){
                if(jsonState?.homeWardCode && jsonState?.homeWardCode !== ''){
                    wardHomeObj = (jsonState?.homeWardCode && districtHomeObj !== null && districtHomeObj !== undefined) ? districtHomeObj?.lstWard?.find((homeWard) => homeWard?.WardCode === jsonState?.homeWardCode) : '';
                }
                else {
                    wardHomeObj = (jsonState?.homeWardCode && districtHomeObj !== null && districtHomeObj !== undefined) ? districtHomeObj?.lstWard?.find((homeWard) => homeWard?.WardCode === oldWardCode) : '';
                }
            }
            else{
                let districtZZZ = (cityHomeObj !== null && cityHomeObj !== undefined) ? cityHomeObj?.lstDistrict?.find((homedistrict) => homedistrict?.DistrictCode === 'ZZZ') : '';
                           
                if(districtZZZ){
                    if(jsonState?.homeWardCode && jsonState?.homeWardCode !== ''){
                        wardHomeObj = (jsonState?.homeWardCode && districtZZZ !== null && districtZZZ !== undefined) ? districtZZZ?.lstWard?.find((homeWard) => homeWard?.WardCode === jsonState?.homeWardCode) : '';
                    }
                    else {
                        wardHomeObj = (oldWardCode && districtZZZ !== null && districtZZZ !== undefined) ? districtZZZ?.lstWard?.find((homeWard) => homeWard?.WardCode === oldWardCode) : '';
                    }
                }
            }
            
        }
        const clearAddressRoad = () => {
            this.setState({homeAddressRoad: ''});
        }

        const clearEmail = () => {
            this.setState({Email: ''});
        }

        const buttonTieptuc = () => {
            const jsonState = this.state;

            // if (jsonState.loadingEpolicy) {
            //     setTimeout(buttonTieptuc, 100);
            //     return;
            // }

            if (!jsonState.polListProfile || jsonState.polListProfile.length < 1) {
                this.setState({ noValidPolicy: true });
            } else if (getSession(SUBMIT_IN_24)) {
                this.setState({ submitIn24: true });
            } else if (!getSession(CELL_PHONE)) {
                // Yêu cầu cập nhật số điện thoại
                this.setState({ noPhone: true });
            } else if (!getSession(TWOFA) || getSession(TWOFA) === '0' || getSession(TWOFA) === 'undefined') {
                this.setState({ no2FA: true });
            } else {
                if (jsonState.updateEmail) {
                    trackingEvent(
                        "Giao dịch hợp đồng",
                        `Web_Open_${PageScreen.POL_TRANS_EMAIL}`,
                        `Web_Open_${PageScreen.POL_TRANS_EMAIL}`,
                    );
                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_EMAIL}`);
                } else {
                    trackingEvent(
                        "Giao dịch hợp đồng",
                        `Web_Open_${PageScreen.POL_TRANS_ADDRESS}`,
                        `Web_Open_${PageScreen.POL_TRANS_ADDRESS}`,
                    );
                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_ADDRESS}`);
                }
                this.setState({ buttonTieptuc: true, stepName: CONTACT_STATE.UPDATE_INFO });
            }
        };


        const radioEmail = () => {
            const jsonState = this.state;
            if (jsonState.loading) {
                return;
            }

            jsonState.updateEmail = true;

            jsonState.updatePhone = false;
            jsonState.updateAddress = false;
            jsonState.updateAddressIndex = '';
            jsonState.proccessType = 'EML';

            jsonState.enabled = true;

            this.setState(jsonState);

            checkSubmitedIn24h();
        };

        const radioAddress = async (i, item) => {
            if (this.state.loading) {
                return;
            }

            const zipCodeRequest = {
                jsonDataInput: {
                    Project: 'mcp',
                    Type: 'city_district',
                    Action: 'ZipCode',
                }
            };

            try {
                const Res = await getZipCodeMasterData(zipCodeRequest);
                const Response = Res.GetMasterDataByTypeResult;

                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    const { ClientProfileList } = this.state;
                    const updateAddressIndex = `${i}`;
                    const addrCodeArr = ClientProfileList?.[updateAddressIndex]?.ZipCode.split('-') || [];
                    const homeAddressRoad = ClientProfileList?.[updateAddressIndex]?.Street.trim().endsWith(',') ? ClientProfileList?.[updateAddressIndex]?.Street.trim().substring(0, ClientProfileList?.[updateAddressIndex]?.Street.trim().length - 1) : ClientProfileList?.[updateAddressIndex]?.Street;
                    this.setState({
                        updateEmail: false,
                        updatePhone: false,
                        updateAddress: true,
                        updateAddressIndex,
                        OldZipCode: item.ZipCode,
                        ZipCode: item.ZipCode,
                        ContactCode: item.ContactCode,
                        ContactType: item.ContactType,
                        zipCodeList: Response.ClientProfile,
                        homeCityCode: addrCodeArr[0] || '',
                        homeDistrictCode: addrCodeArr[1] || '',
                        homeWardCode: addrCodeArr[2] || '',
                        homeAddressRoad: homeAddressRoad, //ClientProfileList?.[updateAddressIndex]?.Street,
                        proccessType: 'ADR'
                    });

                    if (addrCodeArr[0]) {
                        const cityObj = this.state.zipCodeList.find(city => city.CityCode === addrCodeArr[0]);
                        if (cityObj) {
                            if (this.state.homeAddress) {
                                this.setState({
                                    'homeAddress.CLI_CITY_NM_TXT': cityObj.CityName,
                                    homeCity: cityObj.CityName,
                                    oldHomeCity: cityObj.CityName,
                                });
                            }
                        }

                        const homeDistrictObj = cityObj?.lstDistrict.find(district => district.DistrictCode === addrCodeArr[1]);
                        if (homeDistrictObj) {
                            this.setState({
                                homeDistrict: homeDistrictObj.DistrictName,
                                oldHomeDistrict: homeDistrictObj.DistrictName,
                            });

                            if (this.state.homeAddress) {
                                this.setState({ 'homeAddress.wardCode': addrCodeArr[2] });
                            }

                            const ward = homeDistrictObj.lstWard.find(ward => ward.WardCode === addrCodeArr[2]);
                            if (ward) {
                                this.setState({
                                    homeWard: ward.WardName,
                                    oldHomeWard: ward.WardName,
                                });
                            }
                        }

                        if (this.state.homeAddress) {
                            this.setState({ 'homeAddress.CLI_ADDR_LN_1_TXT': this.state.homeAddressRoad });
                        }

                        this.setState({
                            homeNumber: this.state.homeAddressRoad,
                            oldHomeNumber: this.state.homeAddressRoad,
                        });
                    }
                } else {
                    const msg = `Error! ${Response.ErrLog}`;
                }

                if (!this.state.updateEmail && !this.state.updateAddress) {
                    this.setState({ enabled: false });
                } else {
                    this.setState({ enabled: true });
                }

                setTimeout(checkSubmitedIn24h, 200);
            } catch (error) {
                // Handle error (e.g., alert or redirect)
                // alert(error);
                // this.props.history.push('/maintenance');
            }
        };


        const checkSubmitedIn24h = () => {
            if (getSession(SUBMIT_IN_24)) {
                return;
            }
            let proccessType = '';
            if (this.state.updateAddress) {
                proccessType = 'ADR';
            } else if (this.state.updateEmail) {
                proccessType = 'EML';
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
                    FromSystem: "DCW",
                    RequestTypeID: proccessType,
                    // OtpVerified: this.state.otp,
                }
            };

            onlineRequestSubmit(jsonRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
                    setSession(SUBMIT_IN_24, SUBMIT_IN_24);
                }
            }).catch(error => {
            });
        }

        const closePopup = () => {
            this.setState({nextScreen: false});
            this.props.history.push('/update-contact-info-change');
        };

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
            if (this.state.stepName === CONTACT_STATE.UPDATE_INFO) {
                this.setState({stepName: this.state.stepName - 1, Email:this.state.oldEmail/*, enabled: true*/});
            } else {
                this.setState({stepName: this.state.stepName - 1/*, enabled: false*/});
            }
        }

        const closeNoPhone = () => {
            this.setState({noPhone: false});
        }

        const closeNoEmail = () => {
            this.setState({noEmail: false});
        }

        const closeNo2FA = () => {
            this.setState({no2FA: false});
        }

        const toggleAllAddress = () => {
            let jsonState = this.state;
            if (this.state.polListProfile && this.state.polListProfileReview && (this.state.polListProfile.length === this.state.polListProfileReview.length)) {
                if (jsonState.polListProfile !== null) {
                    let tempPolList = [];
                    jsonState.ClientPolicyList = tempPolList;
                    let list = [];
                    jsonState.polListProfileReview = list;
                    polListProfileReview = list;
                    this.setState(jsonState);
                }
            } else {
                if ((jsonState.polListProfile !== null) && jsonState.polListProfile.length > 0) {
                    let tempPolList = [];
                    for (let i = 0; i < this.state.polListProfile.length; i++) {
                        let obj = {
                            Order: i + 1,
                            PolicyNo: jsonState.polListProfile[i].PolicyID.trim()
                        };
                        tempPolList.push(obj);
                    }


                    jsonState.ClientPolicyList = tempPolList;
                    let list = [];
                    jsonState.polListProfileReview = Object.assign(list, jsonState.polListProfile);
                    polListProfileReview = jsonState.polListProfileReview;
                    this.setState(jsonState);
                }

            }
        }
        const closeNoVerifyEmail = () => {
            this.setState({noVerifyEmail: false});
        }

        const closeSubmitIn24 = () => {
            this.setState({submitIn24: false});
        }

        const closeNoValidPolicy = () => {
            this.setState({noValidPolicy: false});
        }

        const closeApiError = () => {
            this.setState({apiError: false});
        }

        const selectedPolicy = (policyNo) => {
            const jsonState = this.state;
            let policy = jsonState.ClientPolicyList.find((pol) => pol.PolicyNo === policyNo.trim());
            const index = jsonState.ClientPolicyList.indexOf(policy);
            let obj = "";
            if (policy === undefined) {
                if (jsonState.ClientPolicyList.length === 0) {
                    obj = {
                        Order: 1,
                        PolicyNo: policyNo.trim()
                    };
                } else {
                    obj = {
                        Order: jsonState.ClientPolicyList.length + 1,
                        PolicyNo: policyNo.trim()
                    };
                }
                jsonState.ClientPolicyList.push(obj);

                let polObj = jsonState.polListProfile.find((pol) => pol.PolicyID.trim() === policyNo.trim());
                if (polObj !== undefined)
                    jsonState.polListProfileReview.push(polObj);
            } else {
                jsonState.ClientPolicyList.splice(index, 1);
                jsonState.polListProfileReview.splice(index, 1);
            }
            this.setState(jsonState);
        }

        const includesItem = (item, list) => {
            let polObj = list.find((pol) => pol.PolicyID.trim() === item.PolicyID.trim());
            if (polObj !== undefined) return true;
            return false;
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

        if (this.state.updatePhone && (this.state.stepName === CONTACT_STATE.UPDATE_INFO) && (!document.getElementById('cellPhoneId') || !document.getElementById('homePhoneId') || !document.getElementById('bussinessPhoneId'))) {
            this.setState({toggle: !this.state.toggle});
        }
        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }
        let msgPopup = '';
        let popupImgPath = '';
        if (this.state.noPhone) {
            msgPopup = 'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            popupImgPath = '../../img/popup/no-phone.svg';
        }

        if (this.state.submitIn24) {
            msgPopup = 'Chúng tôi đang thực hiện một yêu cầu thay đổi thông tin khác của Quý khách. Vui lòng quay trở lại sau';
            popupImgPath = '../../img/popup/submited-in-24.svg';
        }


        if (this.state.noValidPolicy) {
            msgPopup = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu này. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật';
            popupImgPath = 'img/popup/no-policy.svg';
        }

        if (this.state.apiError) {
            msgPopup = 'Gửi yêu cầu không thành công, vui lòng thử lại';
            popupImgPath = 'img/popup/quyenloi-popup.svg';
        }

        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: "/home"
            }}/>;
        }

        return (
            <div>
                <Helmet>
                    <title>Điều chỉnh thông tin liên hệ – Dai-ichi Life Việt Nam</title>
                    <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta name="robots" content="noindex, nofollow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL + "/update-contact-info"}/>
                    <meta property="og:title" content="Điều chỉnh thông tin liên hệ - Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/update-contact-info"}/>
                </Helmet>
                {((this.state.clientProfile !== null) && (this.state.clientProfile !== undefined) && (this.state.clientProfile !== '')) ? (
                    <main className="logined nodata">
                        <div className="main-warpper insurancepage page-four">
                            <section className="gdhd-warpper-dcttlh">
                                <div className="insurance">
                                    <div className='heading heading-41-page' style={{paddingBottom: '0'}}>
                                        <div className="breadcrums">
                                            <div className="breadcrums__item">
                                                <p>Giao dịch hợp đồng</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                            <div className="breadcrums__item">
                                                <p>Điều chỉnh thông tin liên hệ</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>

                                        </div>
                                        <div className="heading__tab">
                                            <div className="step-container">
                                                <div className="step-wrapper">
                                                    {(this.state.stepName !== CONTACT_STATE.CATEGORY_INFO) && (this.state.stepName < FUND_STATE.SDK) &&
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
                                                            className={(this.state.stepName >= CONTACT_STATE.CATEGORY_INFO) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>1</span>
                                                            </div>
                                                            <p>Loại thông tin</p>
                                                        </div>
                                                        <div
                                                            className={(this.state.stepName >= CONTACT_STATE.UPDATE_INFO) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>2</span>
                                                            </div>
                                                            <p>Nhập thông tin</p>
                                                        </div>
                                                        <div
                                                            className={(this.state.stepName >= CONTACT_STATE.VERIFICATION) ? "step active" : "step"}>
                                                            <div className="bullet">
                                                                <span>3</span>
                                                            </div>
                                                            <p>Xác nhận</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {this.state.stepName < FUND_STATE.SDK &&
                                    (this.state.stepName === CONTACT_STATE.CATEGORY_INFO ? (
                                        <div className="gdhd-page">
                                            <div className="gdhd-form" style={{marginTop: '222px'}}>
                                                <div className="gdhd-form__body">
                                                    <div className="gdhdform-wrapper">
                                                        <div className="gdhdform-situation4">
                                                            <div className="gdhdform-situation4__title">
                                                                <h5 className="basic-bold">chọn loại thông tin</h5>
                                                            </div>
                                                            <div className="gdhdform-situation4__body">
                                                                <div className="otp-checkbox-wrapper">
                                                                    <div className="otp-check">
                                                                        <div className="checkbox-item"
                                                                             style={{paddingBottom: '3px'}}>

                                                                            <div style={{marginTop: '-4px'}}>
                                                                                <div className="round-checkbox">
                                                                                    <label className="customradio">
                                                                                        {this.state.updateEmail ? (
                                                                                            <input type="checkbox"
                                                                                                   checked={true}
                                                                                                   id='radioEmail'/>
                                                                                        ) : (
                                                                                            <input type="checkbox"
                                                                                                   checked={false}
                                                                                                   onClick={() => radioEmail()}
                                                                                                   id='radioEmail'/>
                                                                                        )}

                                                                                        <div
                                                                                            className="checkmark"></div>
                                                                                        <div
                                                                                            className='exchange__policy__form__item'
                                                                                            style={{marginBottom: '2px'}}
                                                                                        >
                                                                                            <h5 style={{
                                                                                                fontSize: '16px',
                                                                                                paddingLeft: '6px'
                                                                                            }}>Email</h5>
                                                                                        </div>
                                                                                    </label>
                                                                                </div>
                                                                                {this.state.updateEmail && (this.state.ClientPolicyInfo != null) ? (

                                                                                    <div>
                                                                                        <div
                                                                                            className='exchange__policy__form__item'>
                                                                                            <p className="text" style={{
                                                                                                cursor: 'default',
                                                                                                color: '#414141',
                                                                                                paddingLeft: '0px'
                                                                                            }}>Email</p><p
                                                                                            style={{color: '#414141'}}>{this.state.Email}</p>
                                                                                        </div>
                                                                                    </div>


                                                                                ) : (
                                                                                    <div style={{height: '12px'}}>
                                                                                    </div>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                        <div className="border-line"
                                                                             style={{margin: '0px'}}></div>
                                                                        <div className="checkbox-item">
                                                                            <h5 style={{
                                                                                paddingTop: '20px',
                                                                                fontSize: '16px'
                                                                            }}>Địa chỉ liên hệ</h5>
                                                                            {jsonState.ClientProfileList != null && (
                                                                                jsonState.ClientProfileList.map((item, index) => (
                                                                                    <div className="round-checkbox"
                                                                                         key={'radio-address-' + index}
                                                                                         style={{
                                                                                             marginRight: '-12px',
                                                                                             marginTop: '4px'
                                                                                         }}>
                                                                                        <label className="customradio">
                                                                                            <input type="checkbox"
                                                                                                   checked={this.state.updateAddressIndex === '' + index}
                                                                                                   onClick={() => radioAddress(index, item)}
                                                                                                   id={'radioAddress' + index}
                                                                                                   key={'rd' + index}/>
                                                                                            <div className="checkmark"
                                                                                                 // style={{marginBottom: '12px'}}
                                                                                            ></div>

                                                                                            <p className="text" style={{
                                                                                                minWidth: '120px',
                                                                                                color: '#414141',
                                                                                                paddingLeft: '6px',
                                                                                                marginBottom: '12px'
                                                                                            }}>Địa chỉ {index + 1}</p>
                                                                                            <p className="text"
                                                                                               style={(item.FullAddress?.length > 46) ? {
                                                                                                   color: '#414141',
                                                                                                   textAlign: 'right',
                                                                                                   paddingLeft: '27px',
                                                                                                   width: '420px',
                                                                                                   marginBottom: '12px'
                                                                                               } : {
                                                                                                   color: '#414141',
                                                                                                   textAlign: 'right',
                                                                                                   paddingLeft: '27px',
                                                                                                   width: '420px',
                                                                                                   height: '44px',
                                                                                                   marginBottom: '12px'
                                                                                               }}>{item.FullAddress ? item.FullAddress : ''}</p>
                                                                                        </label>
                                                                                    </div>
                                                                                ))
                                                                            )}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <img className="decor-clip" src="img/mock.svg" alt=""/>
                                                <img className="decor-person" src="img/person.png" alt=""/>
                                            </div>
                                            <div style={{height: '20px'}}>
                                                <LoadingIndicator area="submit-loading"/>
                                                <LoadingIndicator area="zip-code-list"/>
                                                <LoadingIndicator area="online-submit-area"/>
                                            </div>

                                            {this.state.enabled ?
                                                <div className="bottom-btn">
                                                    <button className="btn btn-primary" onClick={() => buttonTieptuc()}
                                                            id='tieptuc' style={{fontWeight: '600'}}>Tiếp tục
                                                    </button>
                                                </div> :
                                                <div className="bottom-btn">
                                                    <button className="btn btn-primary disabled" disabled id='tieptuc'
                                                            style={{fontWeight: '600'}}>Tiếp tục
                                                    </button>
                                                </div>}
                                        </div>
                                        // Loại thông tin
                                    ) : (
                                        <div>
                                            {this.state.updatePhone === true ? (
                                                this.state.stepName !== CONTACT_STATE.VERIFICATION ? (
                                                    <section className="scupdate-infoform">
                                                        <div className="container">
                                                            <div className="update-infoform"
                                                                 style={{marginTop: '222px'}}>
                                                                <div className="personform">
                                                                    <div className="content-wrappper"
                                                                         style={{paddingBottom: '20px'}}>
                                                                        <div className="content"
                                                                             style={{borderBottom: '0px dashed rgba(5, 4, 4, 0.3)'}}>
                                                                            <h4 className="basic-bold"
                                                                                style={{paddingTop: '5px'}}>Nhập thông
                                                                                tin điều chỉnh</h4>
                                                                        </div>
                                                                        <div className="checkbox-wrap basic-column">
                                                                            <div className="tab-wrapper">
                                                                                <div className="tab"
                                                                                     style={{padding: '0'}}>
                                                                                    <div className="checkbox-warpper"
                                                                                         onClick={() => toggleCheckCellPhone()}>
                                                                                        <label className="checkbox"
                                                                                               htmlFor="cellPhone">
                                                                                            <input type="checkbox"
                                                                                                   name="cellPhone"
                                                                                                   id="cellPhoneId"/>
                                                                                            <div className="checkmark">
                                                                                                <img
                                                                                                    src="img/icon/check.svg"
                                                                                                    alt=""/>
                                                                                            </div>
                                                                                        </label>
                                                                                        <p className="basic-text2">Số
                                                                                            điện thoại di động</p>
                                                                                    </div>
                                                                                    <div className="input-wrapper">
                                                                                        <div
                                                                                            className="input-wrapper-item">
                                                                                            {this.state.isCellPhoneChecked === true ? (
                                                                                                <div
                                                                                                    className={!this.state.isValidcellphone ? "input validate" : "input"}>
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            className="black"
                                                                                                            type="search"
                                                                                                            name="cellphone"
                                                                                                            value={this.state.cellPhone}
                                                                                                            maxLength="10"
                                                                                                            onChange={this.handleInputPhone}
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/edit.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>
                                                                                            ) : (
                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="cellphone"
                                                                                                            value={this.state.clientProfile[0].CellPhone.trim()}
                                                                                                            maxLength="14"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>

                                                                                                </div>
                                                                                            )}
                                                                                            {!this.state.isValidcellphone &&
                                                                                                <span style={{
                                                                                                    color: 'red',
                                                                                                    'line-height': '40px',
                                                                                                    paddingTop: '24px',
                                                                                                    paddingBottom: '24px'
                                                                                                }}>{'Số điện thoại chưa đúng'}</span>}
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div className="tab"
                                                                                     style={{padding: '0'}}>
                                                                                    <div className="checkbox-warpper"
                                                                                         onClick={() => toggleCheckHomePhone()}>
                                                                                        <label className="checkbox"
                                                                                               htmlFor="homePhone">
                                                                                            <input type="checkbox"
                                                                                                   name="homePhone"
                                                                                                   id="homePhoneId"/>
                                                                                            <div className="checkmark">
                                                                                                <img
                                                                                                    src="img/icon/check.svg"
                                                                                                    alt=""/>
                                                                                            </div>
                                                                                        </label>
                                                                                        <p className="basic-text2">Số
                                                                                            điện thoại nhà</p>
                                                                                    </div>
                                                                                    <div className="input-wrapper">
                                                                                        <div
                                                                                            className="input-wrapper-item">
                                                                                            {this.state.isHomePhoneChecked ? (
                                                                                                <div
                                                                                                    className={!this.state.isValidhomephone ? "input validate" : "input"}>
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            className="black"
                                                                                                            type="search"
                                                                                                            name="homephone"
                                                                                                            value={this.state.homePhone}
                                                                                                            maxLength="14"
                                                                                                            onChange={this.handleInputHomePhone}
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/edit.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>
                                                                                            ) : (
                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="homephone"
                                                                                                            value={this.state.clientProfile[0].HomePhone.trim()}
                                                                                                            maxLength="14"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>

                                                                                                </div>
                                                                                            )}
                                                                                            {!this.state.isValidhomephone &&
                                                                                                <span style={{
                                                                                                    color: 'red',
                                                                                                    'line-height': '40px',
                                                                                                    paddingTop: '24px',
                                                                                                    paddingBottom: '24px'
                                                                                                }}>{'Số điện thoại chưa đúng'}</span>}
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div className="tab"
                                                                                     style={{padding: '0'}}>
                                                                                    <div className="checkbox-warpper"
                                                                                         onClick={() => toggleCheckBusinessPhone()}>
                                                                                        <label className="checkbox"
                                                                                               htmlFor="bussinessPhone">
                                                                                            <input type="checkbox"
                                                                                                   name="bussinessPhone"
                                                                                                   id="bussinessPhoneId"/>
                                                                                            <div className="checkmark">
                                                                                                <img
                                                                                                    src="img/icon/check.svg"
                                                                                                    alt=""/>
                                                                                            </div>
                                                                                        </label>
                                                                                        <p className="basic-text2">Số
                                                                                            điện thoại cơ quan</p>
                                                                                    </div>
                                                                                    <div className="input-wrapper">
                                                                                        <div
                                                                                            className="input-wrapper-item">
                                                                                            {this.state.isBusinessPhoneChecked ? (
                                                                                                <div
                                                                                                    className={!this.state.isValidbusinessphone ? "input validate" : "input"}>
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            className="black"
                                                                                                            type="search"
                                                                                                            name="bussinessPhone"
                                                                                                            value={this.state.businessPhone}
                                                                                                            maxLength="14"
                                                                                                            onChange={this.handleInputBusinessPhone}
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/edit.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>
                                                                                            ) : (
                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="bussinessPhone"
                                                                                                            value={this.state.clientProfile[0].BusinessPhone.trim()}
                                                                                                            maxLength="14"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>

                                                                                                </div>
                                                                                            )}
                                                                                            {!this.state.isValidbusinessphone &&
                                                                                                <span style={{
                                                                                                    color: 'red',
                                                                                                    'line-height': '40px',
                                                                                                    paddingTop: '24px',
                                                                                                    paddingBottom: '24px'
                                                                                                }}>{'Số điện thoại chưa đúng'}</span>}
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <img className="decor-clip" src="img/mock.svg"
                                                                         alt=""/>
                                                                    <img className="decor-person" src="img/person.png"
                                                                         alt=""/>
                                                                </div>
                                                            </div>
                                                            <div style={{
                                                                display: 'flex',
                                                                justifyContent: 'center',
                                                                alignItems: 'center'
                                                            }}>
                                                                <div className="bottom-text" style={{
                                                                    'maxWidth': '600px',
                                                                    backgroundColor: '#f5f3f2'
                                                                }}>
                                                                    <p style={{textAlign: 'justify'}}>
                                                                        <span
                                                                            className="red-text basic-bold">Lưu ý: </span><span
                                                                        style={{color: '#727272'}}>
                                      Yêu cầu điều chỉnh sẽ được cập nhật cho tất cả Hợp đồng bảo hiểm mà Quý khách đang là Bên mua bảo hiểm/ Người được bảo hiểm tại Dai-ichi Life Việt Nam.
                                    </span>
                                                                    </p>
                                                                </div>
                                                            </div>

                                                            <LoadingIndicator area="submit-loading"/>
                                                            <div className="bottom-btn">
                                                                <button
                                                                    className={jsonState.isCellPhoneChecked || jsonState.isHomePhoneChecked || jsonState.isBusinessPhoneChecked ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                    disabled={!(jsonState.isCellPhoneChecked || jsonState.isHomePhoneChecked || jsonState.isBusinessPhoneChecked)}
                                                                    onClick={(event) => {
                                                                        this.handlerSubmitPhone();
                                                                    }}>Tiếp tục
                                                                </button>
                                                            </div>
                                                        </div>

                                                    </section>
                                                    // Nhập thông tin điều chỉnh - Phone - Màn hình điều chỉnh
                                                ) : (
                                                    <section className="scupdate-infoform">
                                                        <div className="container">
                                                            <div className="update-infoform"
                                                                 style={{marginTop: '124px'}}>
                                                                <div className="personform">
                                                                    <div className="content-wrappper"
                                                                         style={{paddingBottom: '20px'}}>
                                                                        <div className="content"
                                                                             style={{borderBottom: '0px dashed rgba(5, 4, 4, 0.3)'}}>
                                                                            <h4 className="basic-bold"
                                                                                style={{paddingTop: '3px'}}>Xác nhận
                                                                                thông tin điều chỉnh</h4>

                                                                        </div>
                                                                        <div className="checkbox-wrap basic-column">
                                                                            <div className="tab-wrapper">
                                                                                {this.state.isCellPhoneChecked === true &&
                                                                                    <div className="tab"
                                                                                         style={{padding: '0'}}>
                                                                                        <div
                                                                                            className="checkbox-warpper">
                                                                                            <p className="basic-text2"
                                                                                               style={{paddingLeft: '0px'}}>Số
                                                                                                điện thoại di động</p>
                                                                                        </div>
                                                                                        <div className="input-wrapper">
                                                                                            <div
                                                                                                className="input-wrapper-item">
                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="cellphone"
                                                                                                            value={this.state.cellPhone}
                                                                                                            maxLength="10"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/phone-grey.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                }
                                                                                {this.state.isHomePhoneChecked &&
                                                                                    <div className="tab"
                                                                                         style={{padding: '0'}}>
                                                                                        <div
                                                                                            className="checkbox-warpper">
                                                                                            <p className="basic-text2"
                                                                                               style={{paddingLeft: '0px'}}>Số
                                                                                                điện thoại nhà</p>
                                                                                        </div>
                                                                                        <div className="input-wrapper">
                                                                                            <div
                                                                                                className="input-wrapper-item">
                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="homephone"
                                                                                                            value={this.state.homePhone}
                                                                                                            maxLength="14"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/phone-grey.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                }
                                                                                {this.state.isBusinessPhoneChecked &&
                                                                                    <div className="tab"
                                                                                         style={{padding: '0'}}>
                                                                                        <div
                                                                                            className="checkbox-warpper">
                                                                                            <p className="basic-text2"
                                                                                               style={{paddingLeft: '0px'}}>Số
                                                                                                điện thoại cơ quan</p>
                                                                                        </div>
                                                                                        <div className="input-wrapper">
                                                                                            <div
                                                                                                className="input-wrapper-item">

                                                                                                <div
                                                                                                    className="input disabled">
                                                                                                    <div
                                                                                                        className="input__content">
                                                                                                        <input
                                                                                                            type="search"
                                                                                                            name="bussinessPhone"
                                                                                                            value={this.state.businessPhone}
                                                                                                            maxLength="14"
                                                                                                            disabled
                                                                                                        />
                                                                                                    </div>
                                                                                                    <i><img
                                                                                                        src="img/icon/phone-grey.svg"
                                                                                                        alt=""/></i>
                                                                                                </div>

                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                }
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <img className="decor-clip" src="img/mock.svg"
                                                                         alt=""/>
                                                                    <img className="decor-person" src="img/person.png"
                                                                         alt=""/>
                                                                </div>
                                                            </div>
                                                            <div style={{
                                                                display: 'flex',
                                                                justifyContent: 'center',
                                                                alignItems: 'center'
                                                            }}>
                                                                <div className="bottom-text" style={{
                                                                    'maxWidth': '600px',
                                                                    backgroundColor: '#f5f3f2'
                                                                }}>
                                                                    <p style={{textAlign: 'justify'}}>
                                                                        <span
                                                                            className="red-text basic-bold">Lưu ý: </span><span
                                                                        style={{color: '#727272'}}>
                                      Yêu cầu điều chỉnh sẽ được cập nhật cho tất cả Hợp đồng bảo hiểm mà Quý khách đang là Bên mua bảo hiểm/ Người được bảo hiểm tại Dai-ichi Life Việt Nam.
                                    </span>
                                                                    </p>
                                                                </div>
                                                            </div>

                                                            <LoadingIndicator area="submit-loading"/>
                                                            <div className="bottom-btn">
                                                                <button
                                                                    className={jsonState.isCellPhoneChecked || jsonState.isHomePhoneChecked || jsonState.isBusinessPhoneChecked ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                    disabled={!(jsonState.isCellPhoneChecked || jsonState.isHomePhoneChecked || jsonState.isBusinessPhoneChecked)}
                                                                    onClick={(event) => {
                                                                        //if (event.target.className === "btn btn-primary") {
                                                                        this.handleSubmitUpdatePhone();
                                                                        //}
                                                                    }}>Tiếp tục
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </section>
                                                    // Nhập thông tin điều chỉnh - Phone - Màn hình xác nhận
                                                )
                                            ) : (
                                                <div>
                                                    {this.state.updateEmail === true ? (
                                                        this.state.stepName !== CONTACT_STATE.VERIFICATION ? (
                                                            <section className="scupdate-infoform">
                                                                <div className="container">
                                                                    <div className="update-infoform"
                                                                         style={{marginTop: '124px'}}>
                                                                        <div className="personform">
                                                                            <div className="content-wrappper">
                                                                                <div className="content"
                                                                                     style={{paddingBottom: '0px'}}>
                                                                                    <h4 className="basic-bold" style={{
                                                                                        paddingTop: '5px',
                                                                                        paddingBottom: '20px'
                                                                                    }}>Nhập thông tin điều chỉnh</h4>
                                                                                    <div className="input-wrapper">
                                                                                        <div
                                                                                            className="input-wrapper-item">
                                                                                            <div className="input ">
                                                                                                <div
                                                                                                    className="input__content">
                                                                                                    <label>Email</label>
                                                                                                    <input
                                                                                                        className="black"
                                                                                                        name="email"
                                                                                                        value={this.state.Email}
                                                                                                        maxLength="50"
                                                                                                        onChange={this.handleInputemail}
                                                                                                        type="search"/>

                                                                                                </div>
                                                                                                {this.state.Email && this.state.Email.length > 0 ? (
                                                                                                    <i onClick={() => clearEmail()}
                                                                                                       style={{
                                                                                                           width: '10px',
                                                                                                           height: '10px'
                                                                                                       }}><img
                                                                                                        src="img/icon/close-icon.svg"
                                                                                                        alt=""/></i>
                                                                                                ) : (
                                                                                                    <i><img
                                                                                                        src="img/icon/edit.svg"
                                                                                                        alt=""/></i>
                                                                                                )}
                                                                                            </div>
                                                                                            {errorEmail.length > 0 &&
                                                                                                <span style={{
                                                                                                    color: 'red',
                                                                                                    'line-height': '22px'
                                                                                                }}>{errorEmail}</span>}
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <img className="decor-clip"
                                                                                 src="img/mock.svg" alt=""/>
                                                                            <img className="decor-person"
                                                                                 src="img/person.png" alt=""/>
                                                                        </div>
                                                                    </div>
                                                                    <div style={{
                                                                        display: 'flex',
                                                                        justifyContent: 'center',
                                                                        alignItems: 'center'
                                                                    }}>
                                                                        <div className="bottom-text" style={{
                                                                            'maxWidth': '600px',
                                                                            backgroundColor: '#f5f3f2'
                                                                        }}>
                                                                            <p style={{textAlign: 'justify'}}>
                                                                                <span className="red-text basic-bold">Lưu ý: </span><span
                                                                                style={{color: '#727272'}}>
                                          Yêu cầu điều chỉnh sẽ được cập nhật cho tất cả Hợp đồng bảo hiểm mà Quý khách đang là Bên mua bảo hiểm/ Người được bảo hiểm tại Dai-ichi Life Việt Nam.
                                        </span>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                    <LoadingIndicator area="submit-loading"/>
                                                                    <div className="bottom-btn">
                                                                        <button
                                                                            className={this.isEnableSubmitBtn() ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                            disabled={!this.isEnableSubmitBtn()}
                                                                            onClick={(event) => {
                                                                                this.nextConfirmUpdateInfo("Email");
                                                                            }}>Tiếp tục
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </section>
                                                            // Nhập thông tin điều chỉnh - Email - Màn hình điều chỉnh
                                                        ) : (
                                                            <section className="scverify-infoform">
                                                                <div className="container">
                                                                    <div className="update-infoform"
                                                                         style={{marginTop: '124px'}}>
                                                                        <div className="personform">
                                                                            <div className="content-wrappper">
                                                                                <div className="content"
                                                                                     style={{paddingBottom: '0px'}}>
                                                                                    <h4 className="basic-bold" style={{
                                                                                        paddingTop: '5px',
                                                                                        paddingBottom: '20px'
                                                                                    }}>Xác nhận thông tin điều
                                                                                        chỉnh</h4>
                                                                                    <div className="input-wrapper">
                                                                                        <div
                                                                                            className="input-wrapper-item">
                                                                                            <div className="input "
                                                                                                 style={{background: '#EDEBEB'}}>
                                                                                                <div
                                                                                                    className="input__content">
                                                                                                    <label>Email</label>
                                                                                                    <input
                                                                                                        className="basic-lighter-black"
                                                                                                        name="email"
                                                                                                        value={this.state.Email}
                                                                                                        maxLength="50"
                                                                                                        type="search"
                                                                                                        disabled/>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <img className="decor-clip"
                                                                                 src="img/mock.svg" alt=""/>
                                                                            <img className="decor-person"
                                                                                 src="img/person.png" alt=""/>
                                                                        </div>
                                                                    </div>
                                                                    <div style={{
                                                                        display: 'flex',
                                                                        justifyContent: 'center',
                                                                        alignItems: 'center'
                                                                    }}>
                                                                        <div
                                                                            className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                                            style={{
                                                                                'maxWidth': '600px',
                                                                                backgroundColor: '#f5f3f2',
                                                                                display: 'flex'
                                                                            }}>
                                                                            <div className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                                                 style={{flex: '0 0 auto',height: '20px', cursor: 'pointer', margin: 0}} onClick={() => acceptPolicy()}>
                                                                                <div className="checkmark">
                                                                                    <img src="img/icon/check.svg" alt=""/>
                                                                                </div>
                                                                            </div>
                                                                            <div className="contact-info-tac" style={{
                                                                                textAlign: 'justify',
                                                                                paddingLeft: '12px'
                                                                            }}>
                                                                                <p style={{ fontWeight: "bold" }}>Tôi đồng ý và xác nhận:</p>
                                                                                <ul className="list-information" style={{ textAlign: 'justify' }}>
                                                                                    <li className="sub-list-li">
                                                                                        - Tất cả thông tin trên đây
                                                                                        là đầy đủ, đúng sự thật và
                                                                                        hiểu rằng yêu cầu này chỉ
                                                                                        có hiệu lực kể từ ngày được
                                                                                        Dai-ichi Life Việt Nam chấp
                                                                                        nhận.
                                                                                    </li>
                                                                                    <li className="sub-list-li">
                                                                                        - Thông tin liên hệ sẽ được
                                                                                        cập nhật cho (các) Hợp đồng
                                                                                        bảo hiểm của Bên mua bảo
                                                                                        hiểm/ Người được bảo hiểm.
                                                                                    </li>
                                                                                    <li className="sub-list-li">
                                                                                        - Với xác nhận hoàn tất giao
                                                                                        dịch, đồng ý với <a
                                                                                        style={{display: 'inline'}}
                                                                                        className="red-text basic-bold"
                                                                                        href={PAGE_POLICY_PAYMENT}
                                                                                        target='_blank'>Điều khoản
                                                                                        Dịch vụ và Giao dịch điện
                                                                                        tử.</a>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <LoadingIndicator area="submit-loading"/>
                                                                    <div className="bottom-btn">

                                                                        {(this.state.acceptPolicy ? (
                                                                            <button
                                                                                className={this.isEnableSubmitBtn() ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                                disabled={!this.isEnableSubmitBtn()}
                                                                                onClick={(event) => {
                                                                                    trackingEvent(
                                                                                        "Giao dịch hợp đồng",
                                                                                        `Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_SUBMIT}`,
                                                                                        `Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_SUBMIT}`,
                                                                                    );
                                                                                    this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_EMAIL_CONTACT_SUBMIT}`);
                                                                                    this.handlerSubmitUpdateEmail();
                                                                                }}>Xác nhận</button>
                                                                        ) : (
                                                                            <button className="btn btn-primary disabled"
                                                                                    disabled>Xác nhận</button>
                                                                        ))}

                                                                    </div>
                                                                </div>
                                                            </section>
                                                            // Nhập thông tin điều chỉnh - Email - Màn hình xác nhận
                                                        )
                                                    ) : (
                                                        this.state.updateAddress === true ? (
                                                            this.state.stepName !== CONTACT_STATE.VERIFICATION ? (
                                                                <div>
                                                                    <section className="scupdate-infoform">
                                                                        <div className="container">
                                                                            <div className="update-infoform"
                                                                                 style={{marginTop: '124px'}}>
                                                                                <div className="personform">
                                                                                    <div className="content-wrappper">
                                                                                        <div className="content">
                                                                                            <h4 className="basic-bold"
                                                                                                style={{
                                                                                                    paddingTop: '5px',
                                                                                                    paddingBottom: '20px'
                                                                                                }}>Nhập Thông tin điều
                                                                                                chỉnh</h4>
                                                                                            <div
                                                                                                className="input-wrapper">
                                                                                                <div
                                                                                                    className="dropdown inputdropdown">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Tỉnh/
                                                                                                                        Thành
                                                                                                                        Phố</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Tỉnh/ Thành Phố"
                                                                                                                        optionFilterProp="cityname"
                                                                                                                        onChange={this.handlerOnChangeCity}
                                                                                                                        value={(cityHomeObj && cityHomeObj.CityName) ? cityHomeObj.CityName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                    >
                                                                                                                        {zipCodeList && zipCodeList.map((city) => (
                                                                                                                            city &&
                                                                                                                            <Option
                                                                                                                                key={city.CityCode}
                                                                                                                                cityname={city.CityName}>{city.CityName}</Option>
                                                                                                                        ))}
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorCity.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorCity}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div>
                                                                                                {this.state.showDistrict && (
                                                                                                <div
                                                                                                    className="dropdown inputdropdown disabled">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Quận/
                                                                                                                        Huyện</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Quận/ Huyện"
                                                                                                                        optionFilterProp="districtname"
                                                                                                                        onChange={this.handlerOnChangeDistrict}
                                                                                                                        value={districtHomeObj && districtHomeObj.DistrictName ? districtHomeObj.DistrictName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.districtname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                    >
                                                                                                                        {(cityHomeObj != null) && cityHomeObj.lstDistrict.map((district) => (
                                                                                                                            <Option
                                                                                                                                key={district.DistrictCode}
                                                                                                                                districtname={district.DistrictName}>{district.DistrictName}</Option>
                                                                                                                        ))}
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorDistrict.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorDistrict}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div>)}
                                                                                                <div
                                                                                                    className="dropdown inputdropdown disabled">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Phường/
                                                                                                                        Xã</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Phường/ Xã"
                                                                                                                        optionFilterProp="wardname"
                                                                                                                        onChange={this.handlerOnChangeWard}
                                                                                                                        // onFocus={onFocus}
                                                                                                                        // onBlur={onBlur}
                                                                                                                        // onSearch={onSearch}
                                                                                                                        value={wardHomeObj && wardHomeObj.WardName ? wardHomeObj.WardName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.wardname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                    >
                                                                                                                        {(districtHomeObj !== null && districtHomeObj !== undefined && districtHomeObj !== '') ? districtHomeObj.lstWard.map((ward) => (
                                                                                                                            <Option
                                                                                                                                key={ward.WardCode}
                                                                                                                                wardname={ward.WardName}>{ward.WardName}</Option>
                                                                                                                        )): 
                                                                                                                            (cityHomeObj && !this.state.showDistrict ) && cityHomeObj.lstDistrict.find((district) => district.DistrictCode === 'ZZZ').lstWard.map((ward) =>(
                                                                                                                            
                                                                                                                            <Option
                                                                                                                                key={ward.WardCode}
                                                                                                                                wardname={ward.WardName}>{ward.WardName}</Option>
                                                                                                                        ))
                                                                                                                        }
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorWard.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorWard}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div>
                                                                                                <div
                                                                                                    className="input-wrapper-item">
                                                                                                    <div
                                                                                                        className="input ">
                                                                                                        <div
                                                                                                            className="input__content">
                                                                                                            <label>Địa
                                                                                                                chỉ chi
                                                                                                                tiết (số
                                                                                                                nhà, tên
                                                                                                                đường...)</label>
                                                                                                            <input
                                                                                                                className="basic-light-black"
                                                                                                                value={(this.state.homeAddressRoad) ? this.state.homeAddressRoad : ''}
                                                                                                                onChange={this.handleInputHomeaddress}
                                                                                                                type="search"
                                                                                                                style={{padding: '0px'}}
                                                                                                                maxLength="80"/>
                                                                                                        </div>
                                                                                                        {this.state.homeAddressRoad && this.state.homeAddressRoad.length > 0 ? (
                                                                                                            <i onClick={() => clearAddressRoad()}
                                                                                                               style={{
                                                                                                                   width: '10px',
                                                                                                                   height: '10px'
                                                                                                               }}><img
                                                                                                                src="img/icon/close-icon.svg"
                                                                                                                alt=""/></i>
                                                                                                        ) : (
                                                                                                            <i><img
                                                                                                                src="img/icon/edit.svg"
                                                                                                                alt=""/></i>
                                                                                                        )}

                                                                                                    </div>
                                                                                                    {this.state.errorAddressRoad.length > 0 &&
                                                                                                        <span style={{
                                                                                                            color: 'red',
                                                                                                            'line-height': '22px'
                                                                                                        }}>{this.state.errorAddressRoad}</span>}
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div className="content"
                                                                                             style={{paddingBottom: '0px'}}>
                                                                                            <h4 className="basic-bold">Hợp
                                                                                                đồng được điều
                                                                                                chỉnh</h4>
                                                                                            <div
                                                                                                className={this.state.polListProfile && this.state.polListProfileReview && (this.state.polListProfile.length === this.state.polListProfileReview.length) ? "address-hearder choosen" : "address-hearder"}
                                                                                                id='addr0'>
                                                                                                <div
                                                                                                    className="address__header"
                                                                                                    style={{
                                                                                                        textAlign: 'left',
                                                                                                        marginLeft: '-12px'
                                                                                                    }}>
                                                                                                    <p className="basic-semibold">Thay
                                                                                                        đổi toàn bộ hợp
                                                                                                        đồng của tôi</p>
                                                                                                    <div
                                                                                                        className="square-choose"
                                                                                                        onClick={() => toggleAllAddress()}>
                                                                                                        <div
                                                                                                            className="checkmark">
                                                                                                            <img
                                                                                                                src="img/icon/check.svg"
                                                                                                                alt=""/>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div
                                                                                                className="card-warpper">
                                                                                                <LoadingIndicator
                                                                                                    area="policyList-by-cliID"/>
                                                                                                {this.state.polListProfile !== null && this.state.polListProfile.map((item, index) => (
                                                                                                    <div
                                                                                                        className="item"
                                                                                                        style={{padding: '12px 0px 0px 0px'}}
                                                                                                        key={'pol-' + index}>
                                                                                                        <div
                                                                                                            className={includesItem(item, this.state.polListProfileReview) ? "card choosen" : "card"}
                                                                                                            id={'ad-' + index}
                                                                                                            key={'ad-' + index}
                                                                                                            onClick={() => selectedPolicy(item.PolicyID, index)}>
                                                                                                            <div
                                                                                                                className="card__header">
                                                                                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                                                                                                <p>Dành
                                                                                                                    cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (formatFullName((item.PolicyLIName))) : ''}</p>
                                                                                                                {item.PolicyStatus.length < 25 ?
                                                                                                                    <p>Hợp
                                                                                                                        đồng: {item.PolicyID}</p> :
                                                                                                                    <p className="policy">Hợp
                                                                                                                        đồng: {item.PolicyID}</p>}
                                                                                                                {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                                                                                    <div
                                                                                                                        className="dcstatus">
                                                                                                                        <p className="inactive">{item.PolicyStatus}</p>
                                                                                                                    </div>) : (
                                                                                                                    <div
                                                                                                                        className="dcstatus">
                                                                                                                        {item.PolicyStatus.length < 25 ?
                                                                                                                            <p className="active">{item.PolicyStatus}</p> :
                                                                                                                            <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                                                                                    </div>
                                                                                                                )}
                                                                                                                <div
                                                                                                                    className="square-choose">
                                                                                                                    <div
                                                                                                                        className="checkmark">
                                                                                                                        <img
                                                                                                                            src="img/icon/check.svg"
                                                                                                                            alt=""/>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            <div
                                                                                                                className="card__footer">
                                                                                                                <div
                                                                                                                    className="card__footer-item">
                                                                                                                    <p className='basic-width-160'>Địa
                                                                                                                        chỉ
                                                                                                                        liên
                                                                                                                        hệ
                                                                                                                        hiện
                                                                                                                        tại</p>
                                                                                                                    <p className='basic-text-right basic-width-350'>{this.getAdress(item.AddressType)}</p>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                ))}
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                    <img className="decor-clip"
                                                                                         src="img/mock.svg" alt=""/>
                                                                                    <img className="decor-person"
                                                                                         src="img/person.png" alt=""/>
                                                                                </div>
                                                                            </div>
                                                                            <LoadingIndicator area="submit-loading"/>
                                                                            <div className="bottom-btn">
                                                                                <button
                                                                                    className={this.isEnableSubmitBtn() && this.state.polListProfileReview && (this.state.polListProfileReview.length > 0) ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                                    disabled={!(this.isEnableSubmitBtn() && this.state.polListProfileReview && (this.state.polListProfileReview.length > 0))}
                                                                                    onClick={(event) => {
                                                                                        if (event.target.className === "btn btn-primary") {
                                                                                            this.nextConfirmUpdateInfo('Address');
                                                                                        }
                                                                                    }}>Tiếp tục
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </section>
                                                                </div>
                                                                // Nhập thông tin điều chỉnh - Address - Màn hình điều chỉnh
                                                            ) : (
                                                                <div>
                                                                    <section className="scupdate-infoform">
                                                                        <div className="container">
                                                                            <div className="update-infoform"
                                                                                 style={{marginTop: '124px'}}>
                                                                                <div className="personform">
                                                                                    <div className="content-wrappper">
                                                                                        <div className="content">
                                                                                            <h4 className="basic-bold"
                                                                                                style={{
                                                                                                    paddingTop: '5px',
                                                                                                    paddingBottom: '20px'
                                                                                                }}>Xác nhận thông tin
                                                                                                điều chỉnh</h4>
                                                                                            <div
                                                                                                className="input-wrapper">
                                                                                                <div
                                                                                                    className="dropdown inputdropdown">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px',
                                                                                                                    background: '#EDEBEB'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Tỉnh/
                                                                                                                        Thành
                                                                                                                        Phố</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Tỉnh/ Thành Phố"
                                                                                                                        optionFilterProp="cityname"
                                                                                                                        onChange={this.handlerOnChangeCity}
                                                                                                                        value={(cityHomeObj && cityHomeObj.CityName) ? cityHomeObj.CityName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                        disabled>
                                                                                                                        {zipCodeList && zipCodeList.map((city) => (
                                                                                                                            city &&
                                                                                                                            <Option
                                                                                                                                key={city.CityCode}
                                                                                                                                cityname={city.CityName}>{city.CityName}</Option>
                                                                                                                        ))}
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorCity.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorCity}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div>
                                                                                                {this.state.showDistrict && (
                                                                                                <div
                                                                                                    className="dropdown inputdropdown disabled">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px',
                                                                                                                    background: '#EDEBEB'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Quận/
                                                                                                                        Huyện</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Quận/ Huyện"
                                                                                                                        optionFilterProp="districtname"
                                                                                                                        onChange={this.handlerOnChangeDistrict}
                                                                                                                        value={districtHomeObj && districtHomeObj.DistrictName ? districtHomeObj.DistrictName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.districtname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                        disabled>
                                                                                                                        {(cityHomeObj != null) && cityHomeObj.lstDistrict.map((district) => (
                                                                                                                            <Option
                                                                                                                                key={district.DistrictCode}
                                                                                                                                districtname={district.DistrictName}>{district.DistrictName}</Option>
                                                                                                                        ))}
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorDistrict.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorDistrict}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div> )}
                                                                                                <div
                                                                                                    className="dropdown inputdropdown disabled">
                                                                                                    <div
                                                                                                        className="dropdown__content">
                                                                                                        <div
                                                                                                            className="input-wrapper-item">
                                                                                                            <div
                                                                                                                className="input "
                                                                                                                style={{
                                                                                                                    height: '59px',
                                                                                                                    paddingTop: '10px',
                                                                                                                    background: '#EDEBEB'
                                                                                                                }}>
                                                                                                                <div
                                                                                                                    className="input__content"
                                                                                                                    style={{'width': '100%',}}>
                                                                                                                    <label
                                                                                                                        style={{paddingTop: '20px'}}>Chọn
                                                                                                                        Phường/
                                                                                                                        Xã</label>
                                                                                                                    <Select
                                                                                                                        showSearch
                                                                                                                        size='large'
                                                                                                                        style={{
                                                                                                                            width: '100%',
                                                                                                                            margin: '0'
                                                                                                                        }}
                                                                                                                        width='100%'
                                                                                                                        bordered={false}
                                                                                                                        placeholder="Chọn Phường/ Xã"
                                                                                                                        optionFilterProp="wardname"
                                                                                                                        onChange={this.handlerOnChangeWard}
                                                                                                                        // onFocus={onFocus}
                                                                                                                        // onBlur={onBlur}
                                                                                                                        // onSearch={onSearch}
                                                                                                                        value={wardHomeObj && wardHomeObj.WardName ? wardHomeObj.WardName : ''}
                                                                                                                        filterOption={(input, option) =>
                                                                                                                            option.wardname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                                                                        }
                                                                                                                        disabled>
                                                                                                                        {(districtHomeObj !== null && districtHomeObj !== undefined && districtHomeObj !== '') ? districtHomeObj.lstWard.map((ward) => (
                                                                                                                            <Option
                                                                                                                                key={ward.WardCode}
                                                                                                                                wardname={ward.WardName}>{ward.WardName}</Option>
                                                                                                                        )): 
                                                                                                                            (cityHomeObj && cityHomeObj.lstDistrict.length == 1 ) && cityHomeObj.lstDistrict.find((district) => district.DistrictCode === 'ZZZ').lstWard.map((ward) =>(
                                                                                                                            
                                                                                                                            <Option
                                                                                                                                key={ward.WardCode}
                                                                                                                                wardname={ward.WardName}>{ward.WardName}</Option>
                                                                                                                        ))
                                                                                                                        }
                                                                                                                                                                                                                                                   
                                                                                                                        {/* {(districtHomeObj !== null && districtHomeObj !== undefined && districtHomeObj !== '') && districtHomeObj.lstWard.map((ward) => (
                                                                                                                            <Option
                                                                                                                                key={ward.WardCode}
                                                                                                                                wardname={ward.WardName}>{ward.WardName}</Option>
                                                                                                                        ))} */}
                                                                                                                    </Select>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            {this.state.errorWard.length > 0 &&
                                                                                                                <span
                                                                                                                    style={{
                                                                                                                        color: 'red',
                                                                                                                        'line-height': '22px'
                                                                                                                    }}>{this.state.errorWard}</span>}
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="dropdown__items"></div>
                                                                                                </div>
                                                                                                <div
                                                                                                    className="input-wrapper-item">
                                                                                                    <div
                                                                                                        className="input "
                                                                                                        style={{
                                                                                                            height: '59px',
                                                                                                            paddingTop: '10px',
                                                                                                            background: '#EDEBEB'
                                                                                                        }}>
                                                                                                        <div
                                                                                                            className="input__content">
                                                                                                            <label>Địa
                                                                                                                chỉ chi
                                                                                                                tiết (số
                                                                                                                nhà, tên
                                                                                                                đường...)</label>
                                                                                                            <input
                                                                                                                className="basic-light-black"
                                                                                                                value={(this.state.homeAddressRoad) ? this.state.homeAddressRoad : ''}
                                                                                                                onChange={this.handleInputHomeaddress}
                                                                                                                type="search"
                                                                                                                style={{padding: '0px'}}
                                                                                                                disabled/>
                                                                                                        </div>
                                                                                                        <i><img
                                                                                                            src="img/icon/edit.svg"
                                                                                                            alt=""/></i>
                                                                                                    </div>
                                                                                                    {this.state.errorAddressRoad.length > 0 &&
                                                                                                        <span style={{
                                                                                                            color: 'red',
                                                                                                            'line-height': '22px'
                                                                                                        }}>{this.state.errorAddressRoad}</span>}
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div className="content"
                                                                                             style={{paddingBottom: '0px'}}>
                                                                                            <h4 className="basic-bold">Hợp

                                                                                                đồng được điều
                                                                                                chỉnh</h4>
                                                                                            <div
                                                                                                className="address-hearder"
                                                                                                id='addr0'
                                                                                                onClick={() => toggleAllAddress()}>
                                                                                                <div
                                                                                                    className="address__header"
                                                                                                    style={{
                                                                                                        textAlign: 'left',
                                                                                                        marginLeft: '-12px'
                                                                                                    }}>
                                                                                                    <p className="basic-semibold">Thay
                                                                                                        đổi toàn bộ hợp
                                                                                                        đồng của tôi</p>
                                                                                                    {/* <div className="square-choose">
                                                  <div className="checkmark">
                                                    <img src="img/icon/check.svg" alt="" />
                                                  </div>
                                                </div> */}
                                                                                                </div>
                                                                                            </div>

                                                                                            <div
                                                                                                className="card-warpper">
                                                                                                <LoadingIndicator
                                                                                                    area="policyList-by-cliID"/>
                                                                                                {this.state.polListProfileReview !== null && this.state.polListProfileReview.map((item, index) => (
                                                                                                    <div
                                                                                                        className="item"
                                                                                                        style={{padding: '12px 0px 0px 0px'}}
                                                                                                        key={'other-pol-' + index}>
                                                                                                        <div
                                                                                                            className="card choosen disabled"
                                                                                                            id={'ad-' + index}
                                                                                                            key={'ad-' + index}
                                                                                                            style={{borderColor: '#e0dedc'}}>
                                                                                                            <div
                                                                                                                className="card__header">
                                                                                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                                                                                                <p>Dành
                                                                                                                    cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (formatFullName((item.PolicyLIName))) : ''}</p>
                                                                                                                {item.PolicyStatus.length < 25 ?
                                                                                                                    <p>Hợp
                                                                                                                        đồng: {item.PolicyID}</p> :
                                                                                                                    <p className="policy">Hợp
                                                                                                                        đồng: {item.PolicyID}</p>}
                                                                                                                {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                                                                                    <div
                                                                                                                        className="dcstatus">
                                                                                                                        <p className="inactive">{item.PolicyStatus}</p>
                                                                                                                    </div>) : (
                                                                                                                    <div
                                                                                                                        className="dcstatus">
                                                                                                                        {item.PolicyStatus.length < 25 ?
                                                                                                                            <p className="active">{item.PolicyStatus}</p> :
                                                                                                                            <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                                                                                    </div>
                                                                                                                )}
                                                                                                                <div
                                                                                                                    className="square-choose-readonly">
                                                                                                                    <div
                                                                                                                        className="checkmark">
                                                                                                                        <img
                                                                                                                            src="img/icon/check.svg"
                                                                                                                            alt=""/>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            <div
                                                                                                                className="card__footer">
                                                                                                                <div
                                                                                                                    className="card__footer-item">
                                                                                                                    <p className='basic-width-160'>Địa
                                                                                                                        chỉ
                                                                                                                        liên
                                                                                                                        hệ
                                                                                                                        hiện
                                                                                                                        tại</p>
                                                                                                                    <p className='basic-text-right basic-width-350'>{(item.AddressType === 'OF') ? getSession(OTHER_ADDRESS) : getSession(ADDRESS)}</p>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                ))}
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                    <img className="decor-clip"
                                                                                         src="img/mock.svg" alt=""/>
                                                                                    <img className="decor-person"
                                                                                         src="img/person.png" alt=""/>
                                                                                </div>
                                                                            </div>
                                                                            <div style={{
                                                                                display: 'flex',
                                                                                justifyContent: 'center',
                                                                                alignItems: 'center'
                                                                            }}>
                                                                                <div
                                                                                    className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                                                    style={{
                                                                                        'maxWidth': '600px',
                                                                                        backgroundColor: '#f5f3f2',
                                                                                        display: 'flex'
                                                                                    }}>
                                                                                    <div className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                                                         style={{flex: '0 0 auto',height: '20px', cursor: 'pointer', margin: 0}} onClick={() => acceptPolicy()}>
                                                                                        <div className="checkmark">
                                                                                            <img src="img/icon/check.svg" alt=""/>
                                                                                        </div>
                                                                                    </div>
                                                                                    <p style={{
                                                                                        textAlign: 'justify',
                                                                                        paddingLeft: '12px'
                                                                                    }}>
                                                                                        <div
                                                                                            className="contact-info-tac"
                                                                                            style={{
                                                                                                textAlign: 'justify',
                                                                                            }}>
                                                                                            <p style={{ fontWeight: "bold" }}>Tôi đồng ý và xác nhận:</p>
                                                                                            <ul className="list-information" style={{ textAlign: 'justify' }}>
                                                                                                <li className="sub-list-li">
                                                                                                    - Tất cả thông tin
                                                                                                    trên đây
                                                                                                    là đầy đủ, đúng sự
                                                                                                    thật và
                                                                                                    hiểu rằng yêu cầu
                                                                                                    này chỉ
                                                                                                    có hiệu lực kể từ
                                                                                                    ngày được
                                                                                                    Dai-ichi Life Việt
                                                                                                    Nam chấp
                                                                                                    nhận.
                                                                                                </li>
                                                                                                <li className="sub-list-li">
                                                                                                    - Thông tin liên hệ
                                                                                                    sẽ được
                                                                                                    cập nhật cho (các)
                                                                                                    Hợp đồng
                                                                                                    bảo hiểm của Bên
                                                                                                    mua bảo
                                                                                                    hiểm/ Người được bảo
                                                                                                    hiểm.
                                                                                                </li>
                                                                                                <li className="sub-list-li">
                                                                                                    - Với xác nhận hoàn
                                                                                                    tất giao
                                                                                                    dịch, đồng ý với
                                                                                                    <a
                                                                                                    style={{display: 'inline'}}
                                                                                                    className="red-text basic-bold"
                                                                                                    href={PAGE_POLICY_PAYMENT}
                                                                                                    target='_blank'> Điều
                                                                                                    khoản
                                                                                                    Dịch vụ và Giao dịch
                                                                                                    điện
                                                                                                    tử.</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </p>
                                                                                </div>
                                                                            </div>
                                                                            <LoadingIndicator area="submit-loading"/>
                                                                            <div className="bottom-btn">
                                                                                <button
                                                                                    className={this.isEnableSubmitBtn() && this.state.acceptPolicy ? "btn btn-primary" : "btn btn-primary disabled"}
                                                                                    disabled={!(this.isEnableSubmitBtn() && this.state.acceptPolicy)}
                                                                                    onClick={(event) => {
                                                                                        if (event.target.className === "btn btn-primary") {
                                                                                            trackingEvent(
                                                                                                "Giao dịch hợp đồng",
                                                                                                `Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_SUBMIT}`,
                                                                                                `Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_SUBMIT}`,
                                                                                            );
                                                                                            this.cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_ADDRESS_CONTACT_SUBMIT}`);
                                                                                            this.handlesubmitUpdateInfo();
                                                                                        }
                                                                                    }}>Xác nhận
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </section>
                                                                </div>
                                                                // Nhập thông tin điều chỉnh - Address - Màn hình xác nhận
                                                            )) : (
                                                            <div></div>
                                                        )
                                                    )}
                                                </div>
                                            )}
                                        </div>
                                    ))}
                                {/*START ND13*/}
                                {this.state.appType && this.state.trackingId && (this.state.stepName === FUND_STATE.SDK) && <ND13
                                            appType={this.state.appType}
                                            trackingId={this.state.trackingId}
                                            clientListStr={this.state.clientListStr}
                                            clientId={getSession(CLIENT_ID)}
                                            proccessType={this.state.proccessType}
                                            deviceId={getDeviceId()}
                                            apiToken={getSession(ACCESS_TOKEN)}
                                            clientName={getSession(FULL_NAME)}
                                            phone={getSession(CELL_PHONE)}
                                            policyNo={this.state.polListProfile?this.state.polListProfile[0]?.PolicyID:''}
                                            stepName={this.state.stepName}
                                            handlerGoToStep = {this.handlerGoToStep}
                                            toggleBack = {this.state.toggleBack}
                                            updateAllLIAgree = {this.updateAllLIAgree}
                                            updateExistLINotAgree = {this.updateExistLINotAgree}
                                            handlerAdjust = {this.handlerAdjust}
                                            handleSetStepName = {this.handleSetStepName}
                                />}

                                {/*END ND13*/}
                                </div>

                            </section>
                        </div>
                        {this.state.nextScreen === true && (
                            <div className="popup envelop show" id="popup">
                                <div className="popup__card envelop-infomation">
                                    <button className="envelop-infomation__close-button" onClick={() => closePopup()}>
                                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="" width="12px" height="12px"/>
                                    </button>
                                    <div className="envelop-infomation__content" style={{marginTop: '0', marginBottom: '92px'}}>
                                        <p>Cảm ơn Quý khách đã cập nhật thông tin cho chúng tôi. Thông tin mới sẽ được
                                            cập nhật vào tất cả Hợp đồng mà Quý khách là bên mua bảo hiểm tại Dai-ichi
                                            Life Việt Nam.</p>
                                    </div>
                                </div>
                                <div className="popupbg"></div>
                            </div>
                        )}
                        {this.state.isCheckPermission && <AlertPopupClaim closePopup={this.closeNotAvailable.bind(this)}
                                                                          msg={this.state.msgCheckPermission}
                                                                          imgPath={checkPermissionIcon}/>}
                        {this.state.noPhone &&
                            <AlertPopupPhone closePopup={closeNoPhone} msg={msgPopup} imgPath={popupImgPath}
                                             screen={SCREENS.UPDATE_CONTACT_INFO}/>
                        }
                        {this.state.noEmail &&
                            <ErrorPopup closePopup={closeNoEmail}
                                        msg={'Quý khách chưa có thông tin Email để nhận mã xác thực. Vui lòng liên hệ văn phòng DLVN gần nhất để cập nhật.'}/>
                        }
                        {this.state.no2FA &&
                            <AuthenticationPopup closePopup={closeNo2FA}
                                                 msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                                                 screen={SCREENS.UPDATE_CONTACT_INFO}/>
                        }
                        {this.state.noVerifyEmail &&
                            <AuthenticationPopup closePopup={closeNoVerifyEmail}
                                                 msg={'Quý khách chưa xác thực nhận mã OTP qua email. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}/>
                        }
                        {this.state.submitIn24 &&
                            <ErrorPopup closePopup={() => closeSubmitIn24} msg={msgPopup} imgPath={popupImgPath}
                                        screen={SCREENS.UPDATE_CONTACT_INFO}/>
                        }
                        {this.state.noValidPolicy &&
                            <AlertPopupHight closePopup={() => closeNoValidPolicy} msg={msgPopup} imgPath={popupImgPath}
                                             screen={SCREENS.HOME}/>
                        }
                        {this.state.apiError &&
                            <AlertPopupError closePopup={closeApiError} msg={msgPopup} imgPath={popupImgPath}
                                             screen={SCREENS.UPDATE_CONTACT_INFO}/>
                        }
                        {this.state.showOtp &&
                            (this.state.otpPhone ? (
                                <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} 
                                           startTimer={this.startTimer} closeOtp={this.closeOtp}
                                           errorMessage={this.state.errorMessage} popupOtpSubmit={this.submitPSConfirm}
                                           reGenOtp={this.genOtp} maskPhone={maskPhone(getSession(CELL_PHONE))}/>
                            ) : (
                                <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} 
                                           startTimer={this.startTimer} closeOtp={this.closeOtp}
                                           errorMessage={this.state.errorMessage} popupOtpSubmit={this.submitPSConfirm}
                                           reGenOtp={this.genOtp} maskEmail={maskEmail(getSession(EMAIL))}/>
                            ))

                        }
                    </main>
                ) : (
                    <div>
                        <main className="logined nodata">
                            <div className="main-warpper insurancepage">
                                <div className="breadcrums">
                                    <div className="breadcrums__item">
                                        <p>Giao dịch hợp đồng</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Điều chỉnh thông tin liên hệ</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                </div>
                                <div className="yeu-cau-no-data">
                                    <div className="icon">
                                        <img src="img/icon/yeu-cau-empty.svg" alt="yeu-cau-empty"/>
                                    </div>
                                    <div className="content">
                                        <p>Hiện tại không có yêu cầu nào đang xử lý</p>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>
                )}
            </div>
        );
    }
}

export default UpdateContactInfo;