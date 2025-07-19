import React, {Component} from 'react';
import {checkPolicyInfo, genOTP, enscryptData} from '../util/APIUtils';
import {
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FORGOT_PASSWORD,
    FORGOT_PASSWORD_ONLY,
    FORGOT_USERNAME_ONLY,
    TRANSACTION_ID,
    USER_LOGIN,
    FORGOT_BOTH,
    TEMP_OF_USE, 
    PRIVACY_POLICY,
    FE_BASE_URL,
    ACCESS_TOKEN,
    OTP_DISPLAYED
} from '../constants';
import {getDeviceId, getSession, maskPhoneNumber, setSession, showMessage, maskEmail, isInteger, getDeviceKey, trackingEventAsTemplate } from '../util/common';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import TroubleDetectedModal from "./AccountManagementFlow/Popup/TroubleDetectedModal/TroubleDetectedModal";
import SignUpFlowModalPO from "./AccountManagementFlow/SignUp/SignUpFlowModalPO";
import ForgotUsernameModalPO from "./AccountManagementFlow/Forgot/ForgotUsernameModalPO";
import ForgotPasswordModalPO from "./AccountManagementFlow/Forgot/ForgotPasswordModalPO";
import ForgotBothModalPO from "./AccountManagementFlow/Forgot/ForgotBothModalPO";
import PopupAccountPOExisted from './PopupAccountPOExisted';
import ClosePopupConfirm from '../components/ClosePopupConfirm';
import { loadCaptchaEnginge3, LoadCanvasTemplate3, validateCaptcha } from '../util/captcha3';

class PopupExisted extends Component {
    constructor(props) {
        super(props);
        this.state = {
            POID: '',
            POYOB: '',
            clientID: '',
            validErr: '',
            errorMessage: '',
            minutes: 0,
            seconds: 0,
            transactionId: '',
            loading: false,
            disabledConfirm: true,
            phoneNumber: '',
            countdown: 0, // 30 minutes in seconds
            errorCount: 5,
            isOpenTrouble: false,
            showSignUpModal: false,
            showForgotUsernameModal: false,
            showForgotPasswordModal: false,
            showForgotBothModal: false,
            showExceptionPopup: false,
            countdownInterval: null,
            contactType: '',
            contactValue: '',
            showPopupAccountPOExisted: false,
            isPhone: false,
            openExceptionAccount: false,
            closeConfirm: false,
            isOpenPopup: true,
            captcha: '',
            errorCaptcha: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.popupExistedSubmit = this.popupExistedSubmit.bind(this);
        // this.closeSignUpPopupExistedEsc = this.closeSignUpPopupExistedEsc.bind(this);
        this.closeSignUpPopupExisted = this.closeSignUpPopupExisted.bind(this);
        this.resetFormAndShowPopup = this.resetFormAndShowPopup.bind(this);
        this.startTimer = this.startTimer.bind(this);
        this.showErrorMessage = this.showErrorMessage.bind(this);
        this.callBackSuccessful = this.callBackSuccessful.bind(this);
        this.resetStateSignUp = this.resetStateSignUp.bind(this);
        this.handleReGenOtp = this.handleReGenOtp.bind(this);
        this.handleShowCloseConfirm = this.showCloseConfirm.bind(this);
        this.handleCloseAllPopup = this.closeAllPopup.bind(this);
        this.handleCloseConfirmPopup = this.closeConfirmPopup.bind(this);
        this.handleClearState = this.clearState.bind(this);
        this.handlerChangeInput = this.onChangeInput.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        let inputValue = target.value.trim();
        let errMsg = '';
        console.log(inputName);
        console.log(inputName === 'POYOB')
        if (inputName === 'POYOB') {
            if(inputValue && !isInteger(inputValue)) {
                inputValue = this.state.POYOB;
                errMsg = 'Năm sinh phải là chữ số';
            }
        }
        // Loại bỏ các ký tự không phải số nếu inputName là 'POID' hoặc 'POYOB'
        // if (inputName === 'POID' || inputName === 'POYOB') {
        //     inputValue = inputValue.replace(/\D/g, ''); // Chỉ giữ lại các ký tự số
        // }

        // Cập nhật state với giá trị đã được chỉnh sửa
        this.setState({
            [inputName]: inputValue
        }, () => {
            // Sau khi cập nhật state, kiểm tra điều kiện để enable hoặc disable nút confirm
            const { POID, POYOB } = this.state;
            const disabledConfirm = (POID.trim() === '') || (POYOB.trim().length !== 4) || !this.state.captcha;

            this.setState({
                disabledConfirm: disabledConfirm,
                validErr: '',
                errorMessage: '',
                errMsg: errMsg
            });
        });

        // Reset các class của các element khác nếu cần
        document.getElementById('option-popup-account-exceed').className = 'popup option-popup';
        document.getElementById('signup-exist-polock-incorrect').className = 'error-message';
        document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
        document.getElementById('po-id-info').className = 'input special-input-extend';
        document.getElementById('poyob-id-info').className = 'input special-input-extend';
    }



    callBackSuccessful() {
        this.props.parentCallback(true);
        const popupExisted = document.getElementById('signup-popup-existed');

        if (popupExisted) {
            popupExisted.className = "popup special signup-popup-existed";
            popupExisted.className = "popup special otp-popup";
        }
        this.resetState();
    }

    resetStateSignUp() {
        const popupExisted = document.getElementById('signup-popup-existed');

        if (popupExisted) {
            popupExisted.className = "popup special signup-popup-existed";
            popupExisted.className = "popup special otp-popup";
        }
    }

    clearState() {
        this.setState({
            minutes: 0,
            seconds: 0,
            isOpenPopup: false,
            captcha: ''
        });
    }

    popupExistedSubmit(event) {
        event.preventDefault();

        this.setState({
            loading: true,
            validErr: '',
            disabledConfirm: true,
        });
        if (getDeviceKey()) {
            this.postCheckPolicyInfo(getDeviceKey());
        } else {
            this.enscriptDeviceKeyAndPost(getDeviceId());
        }
        

    }

    enscriptDeviceKeyAndPost(keyStr) {
        let enscriptRequest = {
            jsonDataInput: {
                APIToken: getSession(ACCESS_TOKEN),
                Action: "EncryptAES",
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                Data: keyStr,
                DeviceId: getDeviceId(),
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN)
            }
        }
        enscryptData(enscriptRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                this.postCheckPolicyInfo(Response.Message);
                console.log('DeviceKey=', Response.Message);
            }
        }).catch(error => {
        });
    }

    postCheckPolicyInfo(deviceKey) {
        const policyInfoRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                POYOB: this.state.POYOB,
                Action: 'CheckPolicyInformationNew',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceKey: deviceKey,
                Note: getSession(FORGOT_PASSWORD) ? 'ForgotPassword' : 'RegisterAccount',
                OS: '',
                POID: this.state.POID,
                Project: 'mcp',
            }
        };

        checkPolicyInfo(policyInfoRequest)
            .then(response => {
                console.log("checkPolicyInfo", response);
                if (response.Response.Result === 'true' && this.isHandledErrorLog(response.Response.ErrLog)) {
                    document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
                    this.handleTrueResponse(response);
                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
                    if (document.getElementById('signup-popup-existed')) {
                        document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    }
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                    this.setState({
                        loading: false,
                        disabledConfirm: false,
                    });
                } else if (response.Response.ErrLog === 'POID/POYOBINVALID') {
                    this.setState({errorCount: response.Response.numOfRemaining});
                    document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message validate';
                    this.setState({
                        loading: false,
                        disabledConfirm: false,
                    });
                } else if (response.Response.Result === 'false') {
                    document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
                    this.handleFalseResponse();
                } else if (response.Response.ErrLog === 'OTP Wrong 3 times') {
                    if (document.getElementById('signup-popup-existed')) {
                        document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    }
                    if (document.getElementById('option-popup-account-exceed-otp-3-times')) {
                        document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                    }
                } else {
                    // Handle other cases...
                    if (document.getElementById('signup-popup-existed')) {
                        document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    }
                    this.setState({isOpenTrouble: true});
                }
            })
            .catch(error => {
                // Handle error...
                if (document.getElementById('signup-popup-existed')) {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                }
                this.setState({
                    loading: false,
                    disabledConfirm: false,
                    isOpenTrouble: true
                });
                document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
            });
    }

    isHandledErrorLog(errLog) {
        const handledErrorLogs = ['ACCOUNTREGISTER', 'ACCOUNTEXIST', 'ACCOUNTEXCEED', 'POID/POYOBINVALID', 'CELLPHONEINVALID', 'POLICYINVALID', 'POIDINVALID', 'POYOBINVALID', 'LIYOBINVALID', 'POLICYISPENDING'];
        return handledErrorLogs.includes(errLog);
    }

    handleAccountRegisterResponse(response) {
        const { Message, TransactionID, ContactValue, UserLogin, ChannelOTP } = response?.Response || {};
        if (!Message) return;

        if (!ContactValue) {
            console.log('handleAccountRegisterResponse !ContactValue');
            this.setState({
                isOpenTrouble: true,
                loading: false,
                disabledConfirm: false
            });
        } else {
            console.log('handleAccountRegisterResponse ContactValue');
            this.setState({contactType: ChannelOTP?.PhoneOTP === 1? 'PHONE': 'EMAIL' , contactValue : ContactValue})
            setSession(TRANSACTION_ID, TransactionID);
            const showForgotUsernameModal = getSession(FORGOT_USERNAME_ONLY);
            const showForgotPasswordModal = getSession(FORGOT_PASSWORD_ONLY);
            const showForgotBothModal = getSession(FORGOT_BOTH); 

            this.setState({
                // showSignUpModal: !showForgotUsernameModal || !showForgotPasswordModal,
                showForgotUsernameModal: !!showForgotUsernameModal,
                showForgotPasswordModal: !!showForgotPasswordModal,
                showForgotBothModal: !!showForgotBothModal,
                minutes: 5,
                seconds: 0,
                loading: false,
                disabledConfirm: false,
                transactionId: TransactionID
            });
            trackingEventAsTemplate(OTP_DISPLAYED, `'Web_'${OTP_DISPLAYED}`, 'Web', 'signup', 'existing');
            this.startTimer();
        }
        if (document.getElementById('signup-popup-existed')) {
            document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
        }
        let showPOExist = true;
        if (getSession(FORGOT_PASSWORD) === FORGOT_PASSWORD) {
            showPOExist = false;
        }
        let open = true;
        if (showPOExist) {
            open = false;
        }
        this.setState({
            clientID: UserLogin,
            phoneNumber: ContactValue,
            loading: false,
            disabledConfirm: false,
            showPopupAccountPOExisted : showPOExist,
            isOpenPopup: open
        });


        setSession(USER_LOGIN, UserLogin);
        setSession(CLIENT_ID, UserLogin);
        // this.setState({showForgotBothModal : true});
        // document.getElementById('signup-popup-existed').className = 'popup special signup-popup-existed';
        // document.getElementById('restore-password').className = 'popup option-popup show';
        // document.getElementById('signup-popup-existed').className = 'popup special signup-popup-existed'

    }

    handleAccountExistResponse(response) {
        const { Response } = response || {};
        const { Message } = Response || {};

        // if (!Message) return;
        const {UserLogin, ContactValue, TransactionID, ChannelOTP} = Response;
        const {EmailOTP, PhoneOTP} = ChannelOTP;
        // const [clientId = '', phoneNumberCustom = ''] = Message.split('|');
        if (!ContactValue) {
            this.setState({
                isOpenTrouble: true,
                loading: false,
                disabledConfirm: false,
                clientID: UserLogin,
                phoneNumber: ContactValue,
            });
        } else {
            // this.handleGenOTP(UserLogin, ContactValue);
            setSession(USER_LOGIN, UserLogin);
            setSession(CLIENT_ID, UserLogin);
            this.props.callbackStartTimer();
            setSession(TRANSACTION_ID, TransactionID);
            if (document.getElementById('signup-popup-existed')) {
                document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
            }

            const showForgotUsernameModal = getSession(FORGOT_USERNAME_ONLY);
            const showForgotPasswordModal = getSession(FORGOT_PASSWORD_ONLY);
            this.setState({
                showSignUpModal: !showForgotUsernameModal || !showForgotPasswordModal,
                showForgotUsernameModal: getSession(FORGOT_PASSWORD) && !!showForgotUsernameModal,
                showForgotPasswordModal: getSession(FORGOT_PASSWORD) && !!showForgotPasswordModal,
                showForgotBothModal: getSession(FORGOT_PASSWORD) && !showForgotUsernameModal && !showForgotPasswordModal,
                minutes: 5,
                seconds: 0,
                loading: false,
                disabledConfirm: false,
                transactionId: TransactionID,
                isPhone: PhoneOTP === 1,
                contactValue: ContactValue,
                contactType: PhoneOTP === 1? 'PHONE': 'EMAIL',
                clientID: UserLogin,
                phoneNumber: ContactValue,
            });
            trackingEventAsTemplate(OTP_DISPLAYED, `'Web_'${OTP_DISPLAYED}`, 'Web', 'signup', 'existing');
            this.startTimer();
        }

    }

    handleAccountExceedResponse(response) {
        document.getElementById('option-popup-account-exceed').className = "popup option-popup show";
        this.setState({
            loading: false,
            disabledConfirm: false,
        });
    }

    handlePoidPoyobInvalidResponse(response) {
        if ((this.state.errorCount === 1) || (response.Response.ErrLog === 'ACCOUNTEXCEED')) {
            this.setState({
                countdown: response.Response.RemainSeconds > 0 ? response.Response.RemainSeconds : 1800,
                loading: false,
                disabledConfirm: false
            }); // Set countdown to 30 minutes (1800 seconds)
            this.startCountdown();
            document.getElementById('signup-exist-polock-incorrect').className = 'error-message validate';
            document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message';
        } else {
            this.setState({
                loading: false,
                disabledConfirm: false,
                errorCount: response.Response.numOfRemaining,
            });
            document.getElementById('signup-exist-polock-incorrect').className = 'error-message';
            document.getElementById('signup-exist-poinfo-incorrect').className = 'error-message validate';
            document.getElementById('po-id-info').className = 'input special-input-extend validate';
            document.getElementById('poyob-id-info').className = 'input special-input-extend validate';
        }
    }

    handleTrueResponse(response) {
        switch (response.Response.ErrLog) {
            case 'ACCOUNTREGISTER':
                this.handleAccountRegisterResponse(response);
                break;
            case 'ACCOUNTEXIST':
                this.handleAccountExistResponse(response);
                break;
            case 'ACCOUNTEXCEED':
                this.handlePoidPoyobInvalidResponse(response);
                break;
            case 'POID/POYOBINVALID':
            case 'POIDINVALID':
            case 'POYOBINVALID':
                this.handlePoidPoyobInvalidResponse(response);
                break;
            case 'POLICYISPENDING':
                this.setState({
                    loading: false,
                    disabledConfirm: false,
                });
                document.getElementById('option-popup-choose-account-warning').className = "popup option-popup show";
                break;
            default:
                // Handle other cases...
                break;
        }
    }

    handleFalseResponse() {
        if (this.state.errorCount === 1) {
            this.setState({countdown: 1800}); // Set countdown to 30 minutes (1800 seconds)
            this.startCountdown();
        } else {
            this.setState({
                loading: false,
                disabledConfirm: false,
                errorCount: this.state.errorCount - 1,
            });
        }
    }

    formatTime(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
    }

    handleGenOTP(clientID, phoneNumber) {
        //gen otp
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTP',
                Authentication: AUTHENTICATION,
                CellPhone: phoneNumber,
                ClientID: clientID,
                DeviceId: getDeviceId(),
                Note: 'AutoRegisterAccount',
                OS: '',
                Project: 'mcp',
                UserLogin: clientID
            }

        }
        genOTP(genOTPRequest)
            .then(response => {
                console.log('handleGenOTP=', response);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.props.callbackStartTimer();
                    setSession(TRANSACTION_ID, response.Response.Message);
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";

                    const showForgotUsernameModal = getSession(FORGOT_USERNAME_ONLY);
                    const showForgotPasswordModal = getSession(FORGOT_PASSWORD_ONLY);

                    this.setState({
                        showSignUpModal: !showForgotUsernameModal || !showForgotPasswordModal,
                        showForgotUsernameModal: getSession(FORGOT_PASSWORD) && !!showForgotUsernameModal,
                        showForgotPasswordModal: getSession(FORGOT_PASSWORD) && !!showForgotPasswordModal,
                        showForgotBothModal: getSession(FORGOT_PASSWORD) && !showForgotUsernameModal && !showForgotPasswordModal,
                        minutes: 5,
                        seconds: 0,
                        loading: false,
                        disabledConfirm: false,
                        transactionId: response.Response.Message,
                    });
                    this.startTimer();
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);

                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
        });
        let JsonState = this.state;
        JsonState.POID = '';
        JsonState.POYOB = '';
        this.setState(JsonState);

    }

    handleReGenOtp() {
        this.handleGenOTP(this.state.clientID, this.state.phoneNumber);
    }

    resetState = () => {
        this.setState({
            POID: '',
            POYOB: '',
            clientID: '',
            validErr: '',
            errorMessage: '',
            minutes: 0,
            seconds: 0,
            transactionId: '',
            loading: false,
            disabledConfirm: true,
            phoneNumber: '',
            countdown: 0, // 30 minutes in seconds
            errorCount: 5,
            isOpenTrouble: false,
            showSignUpModal: false,
            showForgotUsernameModal: false,
            showForgotPasswordModal: false,
            showForgotBothModal: false,
            showExceptionPopup: false,
            countdownInterval: null,
            contactType: '',
            contactValue: '',
            showPopupAccountPOExisted: false,
            isPhone: false,
            openExceptionAccount: false,
            closeConfirm: false,
            isOpenPopup: true
        });
    }

    startCountdown() {
        if(this.state.countdownInterval){
            clearInterval(this.state.countdownInterval);
        }
        let countdownInterval = setInterval(() => {
            if (this.state.countdown > 0) {
                this.setState({countdown: this.state.countdown - 1});
            } else {
                clearInterval(countdownInterval);
                // Additional actions when the countdown reaches 0
                this.resetState();
            }
        }, 1000); // Update every 1 second
        this.state.countdownInterval = countdownInterval;
        this.setState({...this.setState, countdownInterval, disabledConfirm: true})
    }

    setIsOpenPopup(value) {
        this.setState({isOpenPopup: value});
    }

    showCloseConfirm() {
        this.setState({closeConfirm: true, isOpenPopup: false});
    }

    closeConfirmPopup() {
        this.setState({closeConfirm: false, isOpenPopup: true});
    }
    
    closeAllPopup() {
        this.resetState();
        window.location.href = '/';
    }

    componentDidMount() {
        loadCaptchaEnginge3(6, 'grey', 'white');
        if (this.state.errorCount === 1) {
            this.startCountdown();
        }
    }

    componentWillUnmount() {
        clearInterval(this.state.countdownInterval); // Clear the interval on component unmount
    }

    closeSignUpPopupExisted() {
        window.location.href = '/';
    }

    startTimer() {
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

    forgotForgotBothModalPO = () => {
        this.setState({showPopupAccountPOExisted : false, isOpenPopup: true, showForgotBothModal : true});
        const signupPopup = document.getElementById('signup-popup-existed');
        const signupForm = document.getElementById('signup-popup-existed-form');

        signupPopup.classList = "popup special signup-popup-existed";
        signupForm.reset();
    }

    // Function to show error message
    showErrorMessage(elementId) {
        document.getElementById(elementId).className = 'error-message validate';
    }

    resetFormAndShowPopup() {
        document.getElementById('new-password-login-popup').className = 'popup special new-password-login-popup show';
        document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed";

        this.props.callbackResetTimer();
        this.props.callbackSetEmail('');
    }

    callExceptionPopup() {
        this.setState({
            showExceptionPopup: true,
        });
    }

    onChangeInput(event) {
        const value = event.target.value;
        this.setState({captcha: value});
        this.validateInput(value);
    }

    validateInput(value) {
        let eMsg = !validateCaptcha(value, false) ? "Mã bảo vệ không đúng" : "";
        this.setState({ errorCaptcha: eMsg })

        const { POID, POYOB } = this.state;
        const disabledConfirm = (POID.trim() === '') || (POYOB.trim().length !== 4) || !this.state.captcha || (eMsg.length > 0);

        this.setState({
            disabledConfirm: disabledConfirm,
            validErr: '',
            errorMessage: ''
        });
    }

    render() {
        return (
            <div className="popup special signup-popup-existed" id="signup-popup-existed">
                <form onSubmit={this.popupExistedSubmit} id="signup-popup-existed-form">
                    <div className="popup__card">
                        <div className="popup-existed-card">
                            <div className="header">
                                <p>Xác thực thông tin</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt=""
                                                             onClick={this.closeSignUpPopupExisted}/></i>
                            </div>
                            <div className="body">
                                <div className="error-message" id="signup-exist-poinfo-incorrect" style={{
                                    height: '50px', padding: '16px 6px', marginBottom: 24
                                }}>
                                    <i className="icon" style={{marginRight: 12}}>
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p style={{margin: 0, fontSize: 13}}>Thông tin xác thực của Quý khách không
                                        khớp.
                                        Quý khách còn <span style={{ fontSize: 13, color: '#ffffff' }}>{this.state.errorCount}</span> lần thử.</p>
                                </div>

                                <div className="error-message" id="signup-exist-polock-incorrect" style={{
                                    height: '50px', padding: '16px 6px', marginBottom: 24
                                }}>
                                    <i className="icon" style={{marginRight: 12}}>
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p style={{margin: 0, fontSize: 13}}>Thông tin nhập đã sai quá 5 lần. Chức năng
                                        tạm ngừng hoạt động trong 30 phút. <span
                                            style={{color: '#FAE340', fontSize: 13}}>{this.formatTime(this.state.countdown)}</span>
                                    </p>
                                </div>

                                <div
                                    className={this.state.validErr.length > 0 ? "error-message validate" : "error-message"}
                                    id="signup-exist-valid-incorrect">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p id="signup-exist-valid-p">{this.state.validErr}</p>
                                </div>
                                <p>Quý khách vui lòng nhập các thông tin đã được đăng ký với <br/> Dai-ichi Life Việt Nam.</p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend" id="po-id-info">
                                            <div className="input__content">
                                                <label htmlFor="">Số CCCD/CMND/Hộ chiếu<span className="basic-red">*</span></label>
                                                <input type="search" name="POID" id="po-id"
                                                    value={this.state.POID}
                                                    onChange={this.handleInputChange} required
                                                    autoFocus />
                                            </div>

                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                        </div>
                                    </div>
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend" id="poyob-id-info">
                                            <div className="input__content">
                                                <label htmlFor="">Năm sinh bên mua bảo hiểm<span className="basic-red">*</span></label>
                                                <input type="search" name="POYOB" id="poyob-id"
                                                    value={this.state.POYOB}
                                                    onChange={this.handleInputChange}
                                                    required autoFocus
                                                    maxLength={4}
                                                />
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                        </div>
                                    </div>
                                    {this.state.errMsg &&
                                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                            {this.state.errMsg}
                                        </span>
                                    }
                                </div>
                                <div className="captcha-container" style={{ margin: '16px 0 4px 0', display: '-webkit-inline-box' }}>
                                    <div className="captcha-container-input" style={{width: '36%'}}>
                                        <div className="input special-input-extend expand-input">
                                        <div className="input__content">
                                            <label for="" className="text-code">Mã bảo vệ *</label>
                                            <input type="search" name="captcha" value={this.state.captcha} maxLength="6"
                                            onChange={(event) => this.handlerChangeInput(event)}
                                            />
                                        </div>
                                        </div>
                                    </div>
                                    <div className="captcha-container-captcha" style={{display:'flex', verticalAlign:'middle', alignItems: 'center', margin: '8px'}}>
                                        <div className="capt-img">
                                        <LoadCanvasTemplate3 />
                                        </div>
                                        <div className="reload-icon" id="reload_href" style={{paddingLeft: '8px', cursor:'pointer'}} onClick={()=>loadCaptchaEnginge3(6, 'grey', 'white')}>
                                        <img src={FE_BASE_URL + "/img/icon/reload-icon.svg"} alt="reload" />
                                        </div>
                                    </div>
                                </div>
                                {this.state.errorCaptcha.length > 0 && <span style={{ color: 'red', margin: '12px 0 0 0', display: 'flex' }}>{this.state.errorCaptcha}</span>}
                                {this.state.loading && <div style={{marginTop: 16}}><LoadingIndicatorBasic/></div>}
                                <div className="btn-wrapper">
                                    <button
                                        className={`btn btn-primary ${this.state.disabledConfirm ? 'disabled' : ''}`}
                                        disabled={this.state.disabledConfirm} id="popup-existed-btn">Tiếp tục
                                    </button>
                                </div>
                                <p className='padding-top-20'>Bằng việc chọn "Tiếp tục", bạn đã đồng ý với 
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold simple-brown"
                                                href={TEMP_OF_USE}
                                                target='_blank'>{' Thỏa thuận sử dụng ứng dụng '}
                                                </a> 
 &                                              <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold simple-brown"
                                                href={PRIVACY_POLICY}
                                                target='_blank'>{' Chính sách bảo mật thông tin '}
                                                </a> 
 của Dai-ichi Life Việt Nam.</p>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </form>
                {(this.state.errorMessage.length > 0) &&
                    <div className="error-message validate">
                        <i className="icon">
                            <img src="../../../img/icon/warning_sign.svg" alt=""/>
                        </i>
                        <p style={{'lineHeight': '20px'}}>{this.state.errorMessage}</p>
                    </div>
                }

                {this.state.showSignUpModal && <SignUpFlowModalPO
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskPhone={this.state.contactType == 'PHONE' ? maskPhoneNumber(this.state.contactValue) : null}
                    maskEmail= {this.state.contactType == 'EMAIL' ? maskEmail(this.state.contactValue) : null}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateSignUp}
                    handleReGenOtp={()=> this.handleGenOTP(this.state.clientID, this.state.phoneNumber)}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                    clearState={this.handleClearState}
                />}

                {this.state.showForgotUsernameModal && <ForgotUsernameModalPO
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskPhone={this.state.contactType == 'PHONE' ? maskPhoneNumber(this.state.contactValue) : null}
                    maskEmail= {this.state.contactType == 'EMAIL' ? maskEmail(this.state.contactValue) : null}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateSignUp}
                    handleReGenOtp={()=> this.handleGenOTP(this.state.clientID, this.state.phoneNumber)}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                    clearState={this.handleClearState}
                />}

                {this.state.showForgotPasswordModal && <ForgotPasswordModalPO
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskPhone={this.state.contactType == 'PHONE' ? maskPhoneNumber(this.state.contactValue) : null}
                    maskEmail= {this.state.contactType == 'EMAIL' ? maskEmail(this.state.contactValue) : null}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateSignUp}
                    handleReGenOtp={this.handleReGenOtp}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                    clearState={this.handleClearState}
                />}

                {this.state.showForgotBothModal && <ForgotBothModalPO
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskPhone={this.state.contactType == 'PHONE' ? maskPhoneNumber(this.state.contactValue) : null}
                    maskEmail= {this.state.contactType == 'EMAIL' ? maskEmail(this.state.contactValue) : null}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateSignUp}
                    handleReGenOtp={this.handleReGenOtp}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                    clearState={this.handleClearState}
                />}

                {this.state.showPopupAccountPOExisted && <PopupAccountPOExisted 
                    isOpenPopup={this.state.isOpenPopup}
                    forgotForgotBothModalPO = {this.forgotForgotBothModalPO}
                    closePopupAccountPOExisted = {this.closeSignUpPopupExisted}
                />}

                {this.state.isOpenTrouble && <TroubleDetectedModal />}
                {this.state.closeConfirm && <ClosePopupConfirm closePopup={this.handleCloseConfirmPopup} closeAllPopup = {this.handleCloseAllPopup}/>}
            </div>

        )
    }
}

export default PopupExisted;