// import 'antd/dist/antd.min.css';
// import '../claim.css';
import moment from 'moment';
import React, {Component} from "react";
import LoadingIndicator from '../../src/common/LoadingIndicator2';
import {
    ACCESS_TOKEN,
    API_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    EMAIL,
    EXPIRED_MESSAGE,
    OTP_EXPIRED,
    TRANSACTION_ID,
    USER_LOGIN,
    FE_BASE_URL,
    ADDRESS
} from "../constants";
import {checkAccountInfo, genOTP, submitAccountUpdate, verifyOTP} from '../util/APIUtils';
import {
    getDeviceId,
    getSession,
    maskEmail,
    maskPhoneNumber,
    setSession,
    showMessage,
    VALID_EMAIL_REGEX
} from "../util/common";
import PopupEditingAccountProfile from "./PopupEditingAccountProfile";
import DOTPInput from "../components/DOTPInput";
import {isEmpty} from "lodash";
import iconArrownRightNew from '../img/icon/iconArrownRight_new.svg';
import { DatePicker } from 'antd';
import dayjs from 'dayjs';
import ClosePopupConfirm from '../components/ClosePopupConfirm';
import AddressPopup  from './AddressPopup';


const INITIAL_ERROR_STATE = () => {
    return {
        name: "", gender: "", dob: "", phone: "", email: "",
    }
}

class AccountUpdateForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            jsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    Action: "CheckEmailPhone",
                },
            },
            familyName: "",
            givenName: "",
            gender: "",
            dob: "",
            phone: "",
            email: "",
            address: getSession(ADDRESS),
            originPhone: "",
            originEmail: "",
            errors: INITIAL_ERROR_STATE(),
            isSubmitting: false,
            isSubmitEditing: false,
            openEditingPopup: false,
            showOtp: false,
            minutes: 0,
            seconds: 0,
            transactionId: '',
            errorMessage: '',
            orgFamilyName:"",
            orgGivenName: "",
            orgGender: "",
            orgDob: "",
            orgPhone: "",
            orgEmail: "",
            changedMail: false,
            changedPhone: false,
            closeConfirm: false,
            showAddressPopup: false,

        };

        this.hanlderChangeInput = this.changeInput.bind(this);
        this.hanlderSubmission = this.submitForm.bind(this);
        this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
        this.handlerSetCloseButtonRef = this.setCloseButtonRef.bind(this);
        this.handlerClosePopup = this.closePopup.bind(this);
        this.handlerOnChangeAddress = this.onChangeAddress.bind(this);
    }

    componentDidMount() {
        const currentClientInfo = JSON.parse(JSON.stringify(this.props.currentClientInfo));
        const jsonState = this.state;
        let fNameArr = currentClientInfo.FullName.split(" ");
        jsonState.familyName = fNameArr[0];
        jsonState.orgFamilyName = fNameArr[0];
        fNameArr.splice(0, 1);
        jsonState.givenName = fNameArr.join(" ");
        jsonState.orgGivenName = fNameArr.join(" ");
        jsonState.gender = currentClientInfo.Gender;
        jsonState.orgGender = currentClientInfo.Gender;
        moment.locale(window.navigator.userLanguage || window.navigator.language);
        if (currentClientInfo.DOB !== null && currentClientInfo.DOB !== undefined && currentClientInfo.DOB !== "") {
            jsonState.dob = moment(currentClientInfo.DOB, "DD/MM/yyyy").format("yyyy-MM-DD");
            jsonState.orgDob = moment(currentClientInfo.DOB, "DD/MM/yyyy").format("yyyy-MM-DD");
        }
        jsonState.phone = currentClientInfo.CellPhone;
        jsonState.orgPhone = currentClientInfo.CellPhone;
        jsonState.email = currentClientInfo.Email;
        jsonState.orgEmail = currentClientInfo.Email;
        jsonState.originPhone = currentClientInfo.CellPhone;
        jsonState.originEmail = currentClientInfo.Email;
        this.setState(jsonState);
    }

    resetState = () => {
        this.setState({
            jsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    Project: "mcp",
                    UserLogin: getSession(USER_LOGIN),
                    Action: "CheckEmailPhone",
                },
            },
            // familyName: "",
            // givenName: "",
            // gender: "",
            // dob: "",
            // phone: "",
            // email: "",
            // originPhone: "",
            // originEmail: "",

            phone: this.state.orgPhone,
            email: this.state.orgEmail,

            errors: INITIAL_ERROR_STATE(),
            isSubmitting: false,
            isSubmitEditing: false,
            openEditingPopup: false,
            showOtp: false,
            minutes: 0,
            seconds: 0,
            transactionId: '',
            errorMessage: '',
            isValidPhone: false,
            isValidEmail: false,
            closeConfirm: false,
        });
    }

    // closeOtp = () => {
    //     this.setState(this.resetState());
    // }

    closeConfirmPopup = () => {
        this.setState({closeConfirm : false})
    }

    closeAllPopup = () => {
        this.setState(this.resetState());
    }

    changeInput(inputName, inputValue) {
        const jsonState = this.state;
        switch (inputName) {
            case "familyName":
                jsonState.familyName = inputValue;
                jsonState.errors.name = "";
                break;
            case "givenName":
                jsonState.givenName = inputValue;
                break;
            case "gender":
                jsonState.gender = inputValue;
                jsonState.errors.gender = "";
                break;
            case "dob":
                jsonState.dob = inputValue;
                jsonState.errors.dob = "";
                break;
            case "phone":
                jsonState.phone = inputValue;
                jsonState.errors.phone = "";
                break;
            case "email":
                jsonState.email = inputValue;
                jsonState.errors.email = "";
                break;
            default:
                break;
        }
        this.setState(jsonState);
        //console.log(this.state);
    }

    validateCellphone() {
        const jsonState = this.state;
        jsonState.errors = INITIAL_ERROR_STATE();


        if (jsonState.phone === null || jsonState.phone === undefined || jsonState.phone.length < 10 || !/^\d+$/.test(jsonState.phone) || jsonState.phone[0] !== "0" ) {
            jsonState.errors.phone = ["Số điện thoại phải có 10 chữ số và bắt đầu bằng chữ số 0.", <br/>, "Quý khách vui lòng kiểm tra lại."];
            this.setState(jsonState);
            return false;
        } else {
            jsonState.errors.phone = "";
            this.setState(jsonState);
        }
        return true;
    }

    validateEmail() {
        const jsonState = this.state;
        jsonState.errors = INITIAL_ERROR_STATE();

        if (jsonState.email === null || jsonState.email === undefined || jsonState.email === "" || !VALID_EMAIL_REGEX.test(jsonState.email)) {
            jsonState.errors.email = "Email phải đúng định dạng add@example.com. Quý khách vui lòng kiểm tra và nhập lại.";
            this.setState(jsonState);
            return false;
        } else {
            jsonState.errors.email = "";
            this.setState(jsonState);
        }
        return true;
    }

    validateInput() {
        const jsonState = this.state;
        jsonState.errors = INITIAL_ERROR_STATE();
        let haveErrors = 0;

        if (jsonState.familyName === null || jsonState.familyName === undefined || jsonState.familyName === "" || jsonState.givenName === null || jsonState.givenName === undefined || jsonState.givenName === "") {
            jsonState.errors.name = "Quý khách cần nhập thông tin Họ và Tên.";
            haveErrors++;
            // this.setState(jsonState);
            // return false;
        } 
        // else {
        //     jsonState.errors.name = "";
        //     this.setState(jsonState);
        // }

        if ( !/^[\p{L}\s]*$/u.test(jsonState.familyName) || !/^[\p{L}\s]*$/u.test(jsonState.givenName)){
            jsonState.errors.name = "Họ và tên không được có ký tự đặc biệt.";
            haveErrors++;
            // this.setState(jsonState);
            // return false;
          } 
        //   else {
        //     jsonState.errors.name = "";
        //     this.setState(jsonState);
        // }

        if (jsonState.gender === null || jsonState.gender === undefined || jsonState.gender === "") {
            jsonState.errors.gender = "Quý khách cần chọn thông tin giới tính.";
            haveErrors++;
            // this.setState(jsonState);
            // return false;
        } 
        // else {
        //     jsonState.errors.gender = "";
        //     this.setState(jsonState);
        // }

        if (jsonState.dob === null || jsonState.dob === undefined || jsonState.dob === "") {
            jsonState.errors.dob = "Quý khách cần nhập thông tin ngày sinh.";
            haveErrors++;
            // this.setState(jsonState);
            // return false;
        } 
        // else {
        //     jsonState.errors.gender = "";
        //     this.setState(jsonState);
        // }
        if (haveErrors){
            this.setState(jsonState);
            return false;
        }
        return true;
    }

    startTimer() {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            } else if (this.state.seconds === 0) {
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

    handleGenOTP() {
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                CellPhone: this.state.isValidPhone ? this.state.phone : '',
                Email: this.state.isValidEmail ? this.state.email : '',
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: this.state.isValidPhone ? 'OTP_UPDATE_PHONE' : this.state.isValidEmail ? 'OTP_UPDATE_EMAIL' : '',
                OS: '',
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
            }
        }
        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    setSession(TRANSACTION_ID, response.Response.Message);
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    this.setState({
                        showOtp: true,
                        minutes: 5,
                        seconds: 0,
                        isSubmitEditing: false,
                        transactionId: response.Response.Message,
                    });
                    this.startTimer();
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);

                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
        });
        let JsonState = this.state;
        JsonState.POID = '';
        JsonState.POYOB = '';
        this.setState(JsonState);

    }

    popupVerifyOTP = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: this.state.isValidPhone ? 'UpdatePotentialPhone' : 'UpdatePotentialEmail',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                // Note: this.state.isValidPhone ? 'OTP_UPDATE_PHONE' : this.state.isValidEmail ? 'OTP_UPDATE_EMAIL' : '',
                OS: '',
                Email: this.state.isValidEmail ? this.state.email : '',
                CellPhone: this.state.isValidPhone ? this.state.phone : '',
                OTP: OTP,
                Project: 'mcp',
                TransactionID: this.state.transactionId,
                UserLogin: getSession(USER_LOGIN)
            }

        }
        verifyOTP(verifyOTPRequest)
            .then(response => {

                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGESSUCC') {
                    // if (!isEmpty(response?.Response?.NewAPIToken)) {
                    //     setSession(API_TOKEN, response?.Response?.NewAPIToken);
                    // }
                    // this.onSubmitInput();
                    const newState = {...this.state};
                    const {isValidPhone} = this.state;
                    isValidPhone? (newState.changedPhone = this.onlyChangePhoneOrEmail()) : (newState.changedMail = this.onlyChangePhoneOrEmail());
                    newState.openEditingPopup = false;
                    newState.showOtp = false;
                    newState.minutes = 0;
                    newState.seconds = 0;
                    newState.errorMessage = '';
                    newState.isValidPhone = false;
                    newState.isValidEmail = false;
                    this.setState(newState);
                    // Hiển thị popup thông báo cập nhật thành công
                    document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup show";
                    document.addEventListener('mousedown', this.handlerClosePopup);
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({errorMessage: OTP_EXPIRED});
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.setState({showOtp: false, minutes: 0, seconds: 0});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({errorMessage: `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${response.Response.Message} lần thử.`});
                } else {
                    this.setState({errorMessage: `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${response.Response.Message} lần thử.`});
                }
            }).catch(error => {
        });

    }

    onCheckAccountInfo() {
        const {jsonInput, phone, email, isValidPhone, isValidEmail} = this.state;
        if (isValidPhone && (this.state.orgPhone == phone)) {
            this.setState({isValidPhone : false});
            this.setState({openEditingPopup : false});
        }
        else if (isValidEmail && (this.state.orgEmail == email)) {
            this.setState({isValidEmail : false});
            this.setState({openEditingPopup : false});
        }
        else if ((isValidPhone && this.validateCellphone()) || (isValidEmail && this.validateEmail())) {
            this.setState({isSubmitEditing: true});

            const checkPhoneEmailApiRequest = {...jsonInput};
            const fieldToUpdate = isValidPhone ? 'CellPhone' : 'Email';
            checkPhoneEmailApiRequest.jsonDataInput[fieldToUpdate] = isValidPhone ? phone : email;

            checkPhoneEmailApiRequest.jsonDataInput.Action = isValidPhone ? 'CheckPhone' : 'CheckEmail';

            checkAccountInfo(checkPhoneEmailApiRequest)
                .then((response) => {
                    this.handleApiResponse(response, fieldToUpdate);
                })
                .catch(() => {
                    this.handleError();
                });
        }
    }

    handleApiResponse(response, fieldToUpdate) {
        const {Response} = response;
        const {isValidPhone} = this.state;
        const newState = {...this.state};

        if (Response.Result === 'true' && Response.ErrLog === 'EXIST') {
            newState.errors[isValidPhone ? 'phone' : 'email'] = isValidPhone ? "Số điện thoại này đang thuộc một tài khoản Dai-ichi Connect khác. Vui lòng sử dụng số điện thoại khác hoặc liên hệ (028) 3810 0888 để được hỗ trợ." 
                                                                                : "Email này đang thuộc một tài khoản Dai-ichi Connect khác. Vui lòng sử dụng số điện thoại khác hoặc liên hệ (028) 3810 0888 để được hỗ trợ.";

            newState.isSubmitEditing = false;
            this.setState(newState);
        } else if (Response.Result === 'true' && Response.ErrLog === 'NOEXIST') {
            // alert(JSON.stringify(Response));
            // alert(Response.ContactType);
            // if ((Response.ContactType === 'EMAIL') || (Response.ContactType === 'PHONE')) {
                setSession(TRANSACTION_ID, Response.TransactionID);
                // document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                this.setState({
                    showOtp: true,
                    minutes: 5,
                    seconds: 0,
                    isSubmitEditing: false,
                    transactionId: Response.TransactionID,
                    openEditingPopup: false
                });
                this.startTimer();
                newState = {...this.state};
                newState.isSubmitEditing = false;
                this.setState(newState);
            // } 
            // this.handleGenOTP();
        } else if (Response.Result === 'true' && (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid')) {
            showMessage(EXPIRED_MESSAGE);

        } else if (Response.Result === 'true' && Response.ErrLog === 'OTP Exceed') {
            this.setState({
                showOtp: false,
                minutes: 0,
                seconds: 0,
                isSubmitEditing: false,
                openEditingPopup: false,
                isValidPhone: false,
                isValidEmail: false
            });
            // newState = {...this.state};
            document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
        } else if (Response.Result === 'true' && (Response.ErrLog === 'OTPLOCK' || Response.ErrLog === 'OTP Wrong 3 times')) {
            document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        } else {
            document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }

    }

    handleError() {
        const {isValidPhone} = this.state;
        const newState = {...this.state};
        newState.errors[isValidPhone ? 'phone' : 'email'] = "Có lỗi xảy ra, Quý khách vui lòng thử lại.";
        newState.isSubmitEditing = false;
        this.setState(newState);
    }

    onSubmitInput() {
        const {jsonInput, phone, email, familyName, givenName, gender, dob, isValidPhone, isValidEmail} = this.state;
        if ((isValidPhone && this.validateCellphone()) || (isValidEmail && this.validateEmail())) {
            this.setState({isSubmitEditing: true});
                const submitAccountUpdateRequest = {...jsonInput};
                submitAccountUpdateRequest.jsonDataInput['Action'] = "UpdatePotentialAccount";
                submitAccountUpdateRequest.jsonDataInput['FullName'] = `${familyName} ${givenName}`;
                submitAccountUpdateRequest.jsonDataInput['Gender'] = gender;
                submitAccountUpdateRequest.jsonDataInput['DOB'] = moment(dob).format("MM/DD/yyyy");
                // submitAccountUpdateRequest.jsonDataInput['CellPhone'] = phone;
                // submitAccountUpdateRequest.jsonDataInput['Email'] = email;

                submitAccountUpdate(submitAccountUpdateRequest)
                    .then((Res) => {
                        const {Response} = Res;

                        const newState = {...this.state};

                        if (Response.Result !== 'true' || Response.ErrLog !== 'CHANGESSUCC') {
                            newState.errors.email = "Cập nhật thất bại. Quý khách vui lòng thử lại.";
                            newState.isSubmitEditing = false;
                            this.setState(newState);
                            return;
                        }

                        // Hiển thị popup thông báo cập nhật thành công
                        const {isValidPhone} = this.state;
                        isValidPhone? (newState.changedPhone = this.onlyChangePhoneOrEmail()) : (newState.changedMail = this.onlyChangePhoneOrEmail());

                        document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup show";
                        document.addEventListener('mousedown', this.handlerClosePopup);

                        newState.openEditingPopup = false;
                        newState.showOtp = false;
                        newState.minutes = 0;
                        newState.seconds = 0;
                        newState.errorMessage = '';
                        newState.isValidPhone = false;
                        newState.isValidEmail = false;
                        this.setState(newState);

                    })
                    .catch((error) => {
                        const newState = {...this.state};
                        newState.errors.email = "Có lỗi xảy ra, Quý khách vui lòng thử lại.";
                        newState.isSubmitEditing = false;
                        this.setState(newState);
                    });
            }
    }

    submitForm() {
        const {jsonInput, phone, email, familyName, givenName, gender, dob, address} = this.state;
        if (this.validateInput()) {
            this.setState({isSubmitting: true});
            this.callSubmitAccountUpdate(jsonInput, familyName, givenName, gender, dob, address);
        }
    }

    reGenOtp() {
        this.handleGenOTP();
    }

    callSubmitAccountUpdate(jsonInput, familyName, givenName, gender, dob, address) {
        const submitAccountUpdateRequest = {...jsonInput};
        submitAccountUpdateRequest.jsonDataInput['Action'] = "UpdatePotentialAccount";
        submitAccountUpdateRequest.jsonDataInput['FullName'] = `${familyName} ${givenName}`;
        submitAccountUpdateRequest.jsonDataInput['Gender'] = gender;
        submitAccountUpdateRequest.jsonDataInput['DOB'] = moment(dob).format("MM/DD/yyyy");
        if (address) {
            submitAccountUpdateRequest.jsonDataInput['Address'] = address;
        }

        submitAccountUpdate(submitAccountUpdateRequest)
            .then((Res) => {
                const {Response} = Res;
                
                const newState = {...this.state};

                if (Response.Result !== 'true' || Response.ErrLog !== 'CHANGESSUCC') {
                    newState.errors.email = "Cập nhật thất bại. Quý khách vui lòng thử lại.";
                    newState.isSubmitting = false;
                    this.setState(newState);
                    return;
                }
                if (address) {
                    setSession(ADDRESS, address);
                }
                // Hiển thị popup thông báo cập nhật thành công
                document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup show";
                document.addEventListener('mousedown', this.handlerClosePopup);
            })
            .catch((error) => {
                const newState = {...this.state};
                newState.errors.email = "Có lỗi xảy ra, Quý khách vui lòng thử lại.";
                newState.isSubmitting = false;
                this.setState(newState);
            });
    }

    setWrapperRef(node) {
        this.wrapperRef = node;
    }

    setCloseButtonRef(node) {
        this.closeButtonRef = node;
    }

    closePopup(event) {
        if ((this.wrapperRef && !this.wrapperRef.contains(event.target)) || (this.closeButtonRef && this.closeButtonRef.contains(event.target))) {
            document.getElementById("popupSucceeded").className = "popup special envelop-confirm-popup";
            document.removeEventListener('mousedown', this.handlerClosePopupSucceeded);
            let changePhone = this.state.originPhone !== this.state.phone ? true : false;
            let changeEmail = this.state.originEmail !== this.state.email ? true : false;
            this.setState({dob : moment(this.state.dob).local().format("YYYY-MM-DD HH:mm:ss.S")});
            // const dob_temp = this.state.dob? this.state.dob.format('yyyy-MM-DD') : "";
            // this.setState({dob : dob_temp});
            this.props.handlerCloseAccountUpdateForm(JSON.parse(JSON.stringify(this.state)), changePhone, changeEmail);
        }
    }

    onClickCellphonePopup(event) {
        event.preventDefault();

        this.setState({
            openEditingPopup: true, isValidPhone: true,
        });
    }

    onClickEmailPopup(event) {
        event.preventDefault();

        this.setState({
            openEditingPopup: true, isValidEmail: true,
        });
    }

    onClickAddressPopup(event) {
        event.preventDefault();

        this.setState({
            showAddressPopup: true,
        });
    }

    onlyChangePhoneOrEmail() {
        const jsonState = this.state;
        if (jsonState.orgFamilyName == jsonState.familyName && jsonState.orgGivenName == jsonState.givenName &&
            jsonState.orgGender == jsonState.gender && jsonState.orgDob == jsonState.dob)
        {
            return true;
        }
        else {
            return false;
        }
    }

    onChangeAddress(value) {
        let jsonState = this.state;
        jsonState.address = value;
        jsonState.showAddressPopup = false;
        this.setState(jsonState);
        // this.setState({address: value});
    }

    render() {
        const disabledDate=(current) => {
            return current && ((current > dayjs().endOf('day')));
        }
        const jsonState = this.state;
        let todayDate = new Date();
        let todayDateStr = new Date(todayDate.getTime() - (todayDate.getTimezoneOffset() * 60000))
            .toISOString()
            .split("T")[0];

        return (<>
            <div className="form">
                <div className="form__item form__item-header">
                    <div className="above">
                        <p className="basic-black">Thông tin khách hàng</p>
                        <a className="simple-brown2" onClick={() => this.props.handlerCloseAccountUpdateForm(null)}>Quay
                            lại</a>
                    </div>

                    <div className="input basic-expand-margin">
                        <div className="input__content">
                            <label>Họ<span style={{color: "red"}}>*</span></label>
                            <input type="search" className="basic-black" value={jsonState.familyName}
                                   maxLength="100"
                                   onChange={(event) => this.hanlderChangeInput("familyName", event.target.value)}/>
                        </div>
                        <i><img src="img/icon/edit.svg" alt=""/></i>
                    </div>
                    <div className="input basic-expand-margin1">
                        <div className="input__content">
                            <label>Tên<span style={{color: "red"}}>*</span></label>
                            <input type="search" className="basic-black" value={jsonState.givenName} maxLength="100"
                                   onChange={(event) => this.hanlderChangeInput("givenName", event.target.value)}/>
                        </div>
                        <i><img src="img/icon/edit.svg" alt=""/></i>
                    </div>
                    {jsonState.errors.name.length > 0 && <span style={{
                        color: 'red', lineheight: '12px', marginBottom: '10px'
                    }}>{jsonState.errors.name}</span>}

                    <div className="item">
                        <h5 className="item__title">Giới tính<span style={{color: "red"}}>*</span></h5>
                        <div className="item__content">
                            <div className="tab">
                                <div className="tab__content">
                                    <div className="checkbox-warpper">
                                        <div className="checkbox-item">
                                            <div className="round-checkbox">
                                                <label className="customradio">
                                                    <input type="checkbox" checked={jsonState.gender === "M"}
                                                           onChange={(event) => this.hanlderChangeInput("gender", "M")}/>
                                                    <div className="checkmark"></div>
                                                    <p claas="text">Nam</p>
                                                </label>
                                            </div>
                                        </div>
                                        <div className="checkbox-item">
                                            <div className="round-checkbox">
                                                <label className="customradio">
                                                    <input type="checkbox" checked={jsonState.gender === "F"}
                                                           onChange={(event) => this.hanlderChangeInput("gender", "F")}/>
                                                    <div className="checkmark"></div>
                                                    <p claas="text">Nữ</p>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    {jsonState.errors.gender.length > 0 && <span style={{
                        color: 'red', lineheight: '12px', marginBottom: '10px'
                    }}>{jsonState.errors.gender}</span>}

                    <div className="input basic-expand-margin1">
                        <div className="input__content" style={{width: '100%'}}>
                            <label for="">Ngày sinh<span style={{color: "red"}}>*</span></label>
                            {/* <input type='date' placeholder="Ngày sinh" max={todayDateStr}
                                   value={jsonState.dob}
                                   onChange={(event) => this.hanlderChangeInput("dob", event.target.value)}/> */}
                            <DatePicker selected={jsonState.dob} value={jsonState.dob?moment(jsonState.dob):""}
                                format="DD/MM/YYYY"
                                placeholder='Chọn ngày'
                                disabledDate={disabledDate}
                                style={{
                                width: '100%',
                                margin: '0px',
                                padding: '0px',
                                fontSize: '1.4rem',
                                border: '0'
                                }}
                                onChange={(date) => this.hanlderChangeInput("dob", date)}/>
                        </div>
                    </div>
                    {jsonState.errors.dob.length > 0 && <span style={{
                        color: 'red', lineheight: '12px', marginBottom: '10px'
                    }}>{jsonState.errors.dob}</span>}
                    <div className="form__item" style={{ borderTop: '1px solid #e6e6e6' }}>
                        <p style={{ fontSize: '1.6rem' }}>Số điện thoại</p>
                        <p style={{ fontSize: '1.6rem', display: 'flex', alignItems: 'center', cursor: 'pointer' }} onClick={(event) => this.onClickCellphonePopup(event)}>
                            {jsonState.orgPhone ? jsonState.orgPhone : '---'}
                            <img src={iconArrownRightNew} alt="iconArrownRightNew" style={{ marginLeft: 4 }}/>
                        </p>
                    </div>
                    <div className="form__item">
                        <p style={{ fontSize: '1.6rem', flexGrow : '1' }}>Email</p>
                        <p style={{ fontSize: '1.6rem', alignItems: 'center', cursor: 'pointer', flexGrow : '0', overflow : 'hidden', textOverflow : 'ellipsis' }} onClick={(event) => this.onClickEmailPopup(event)}>
                            {(jsonState.orgEmail && jsonState.orgEmail !== 'undefined') ? getSession(EMAIL) : '---'}
                        </p>
                        <img src={iconArrownRightNew} alt="iconArrownRightNew" style={{ marginLeft: 4 }}/>
                    </div>
                    <div className="dropdown__content">
                        <div className="form__item">
                            <p style={{ fontSize: '1.6rem' }}>Địa chỉ liên hệ</p>
                            <p className="basic-text-ali" style={{ fontSize: '1.6rem' }} onClick={(event) => this.onClickAddressPopup(event)}>{jsonState.address ? jsonState.address : '---'}</p>
                        </div>
                        <p className="arrow">></p>
                    </div>
                </div>
            </div>
            <div className="bottom-btn">
                {jsonState.isSubmitting ? <LoadingIndicator area="update-account-loading-area"/> :
                    <button className="btn btn-primary" onClick={this.hanlderSubmission}>Lưu</button>}
            </div>

            {/* Popup Success */}
            <div className="popup special envelop-confirm-popup" id="popupSucceeded">
                <div className="popup__card">
                    <div className="envelop-confirm-card" ref={this.handlerSetWrapperRef}>
                        <div className="envelopcard">
                            <div className="envelop-content">
                                <div className="envelop-content__header" ref={this.handlerSetCloseButtonRef}>
                                    <i className="closebtn"><img src="img/icon/close.svg" alt=""/></i>
                                </div>
                                <div className="envelop-content__body">
                                    {this.state.changedPhone && <p>Quý khách đã thay đổi số điện thoại thành công.</p>}
                                    {this.state.changedMail && <p>Quý khách đã thay đổi email thành công.​</p>}
                                    {!(this.state.changedPhone || this.state.changedMail) && <p>Quý khách đã cập nhật thông tin thành công</p>}
                                </div>
                            </div>
                        </div>
                        <div className="envelopcard_bg">
                            <img src="img/envelop_nowhite.png" alt=""/>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
                {this.state.openEditingPopup &&
                    <PopupEditingAccountProfile jsonState={this.state} setState={(value) => this.setState(value)}
                                                onSubmitInput={(e) => this.onCheckAccountInfo(e)}
                                                isValidEmail={this.state.isValidEmail}
                                                isValidPhone={this.state.isValidPhone}
                                                isSubmitEditing={this.state.isSubmitEditing}
                                                onClose={() => {
                                                    this.setState({
                                                        // openEditingPopup: false,
                                                        // isValidEmail: false,
                                                        // isValidPhone: false,
                                                        // phone: this.state.orgPhone,
                                                        // email: this.state.orgEmail,
                                                        closeConfirm : true,
                                                    });
                                                }}/>}
                {this.state.showAddressPopup &&
                    <AddressPopup
                    // onSubmitInput={(e) => this.onSubmitInput(e)}
                    // isSubmitEditing={this.state.isSubmitEditing}
                    handlerOnChangeAddress={this.handlerOnChangeAddress}
                    onClose={() => {
                        this.setState({
                            showAddressPopup : false,
                        });
                    }}
                    />
                }
                {this.state.showOtp && <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds}
                                                  startTimer={this.startTimer} closeOtp={() => {this.setState({closeConfirm : true})}}
                                                  errorMessage={this.state.errorMessage}
                                                  popupOtpSubmit={this.popupVerifyOTP}
                                                  reGenOtp={this.reGenOtp} length={6} width={'36px'}
                                                  maskPhone={this.state.isValidPhone ? maskPhoneNumber(this.state.phone) : ""}
                                                  maskEmail={this.state.isValidEmail ? maskEmail(this.state.email) : ""}
                />}
                {this.state.closeConfirm && <ClosePopupConfirm closePopup={this.closeConfirmPopup} closeAllPopup = {this.closeAllPopup}/>}
            </div>
        </>);
    }
}

export default AccountUpdateForm;
