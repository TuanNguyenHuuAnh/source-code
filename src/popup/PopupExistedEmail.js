import React, {Component} from 'react';
import {forgotPass, genOTP, verifyOTP} from '../util/APIUtils';
import {getDeviceId, getSession, maskEmail, setSession, showMessage, isMobile, maskPhoneNumber} from '../util/common';
import {
    API_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FORGOT_PASSWORD,
    FORGOT_PASSWORD_ONLY,
    FORGOT_USERNAME_ONLY,
    OTP_EXPIRED,
    TRANSACTION_ID,
    USER_LOGIN,
    DCID,
    USER_NAME,
    FE_BASE_URL
} from '../constants';
import {isEmpty, isInteger} from "lodash";
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import ForgotUsernameModalPotential from "./AccountManagementFlow/Forgot/ForgotUsernameModalPotential";
import ForgotPasswordModalPotential from "./AccountManagementFlow/Forgot/ForgotPasswordModalPotential";
import ForgotBothModalPotential from "./AccountManagementFlow/Forgot/ForgotBothModalPotential";
import { loadCaptchaEnginge, LoadCanvasTemplate, validateCaptcha } from '../util/captcha';
import ClosePopupConfirm from '../components/ClosePopupConfirm';

class PopupExistedEmail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            errorMessage: '',
            showForgotUsernameModal: false,
            showForgotPasswordModal: false,
            showForgotBothModal: false,
            minutes: 0,
            seconds: 0,
            loading: false,
            disabledConfirm: true,
            transactionId: '',
            countdown: 30 * 60, // 30 minutes in seconds
            errorCount: 5,
            isInvalid: false,
            isLock: false,
            captcha: '',
            errorCaptcha: '',
            isOpenPopup: true
        };
        this.handleInputEmailExistedChange = this.handleInputEmailExistedChange.bind(this);
        this.popupExistedEmailSubmit = this.popupExistedEmailSubmit.bind(this);
        // this.closeChangePassPopupExistedEmailEsc = this.closeChangePassPopupExistedEmailEsc.bind(this);
        // this.closeChangePassPopupExistedEmail = this.closeChangePassPopupExistedEmail.bind(this);
        this.forgotPassword = this.forgotPassword.bind(this);
        this.resetFormAndShowPopup = this.resetFormAndShowPopup.bind(this);
        this.popupOtpLoginSubmit = this.popupOtpLoginSubmit.bind(this);
        this.closeOtp = this.closeOtp.bind(this);
        this.startTimer = this.startTimer.bind(this);
        this.showErrorMessage = this.showErrorMessage.bind(this);
        this.callBackSuccessful = this.callBackSuccessful.bind(this);
        this.resetStateForgot = this.resetStateForgot.bind(this);
        this.generationOTP = this.generationOTP.bind(this);
        this.handlerChangeInput = this.onChangeInput.bind(this);
        this.handlerValidateInput = this.validateInput.bind(this);
        this.handleShowCloseConfirm = this.showCloseConfirm.bind(this);
        this.handleCloseAllPopup = this.closeAllPopup.bind(this);
        this.handleCloseConfirmPopup = this.closeConfirmPopup.bind(this);
        this.handleClearState = this.clearState.bind(this);
    }

    handleInputEmailExistedChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();

        this.setState({
            [inputName]: inputValue
        });

        if (inputValue.length > 2 && !this.state.isLock && !this.state.errorCaptcha && this.state.captcha) {
            this.setState({
                disabledConfirm: false,
            });
        } else {
            this.setState({
                disabledConfirm: true,
            });
        }
    }

    componentDidMount() {
        loadCaptchaEnginge(6, 'grey', 'white');
        // document.addEventListener("keydown", this.closeChangePassPopupExistedEmailEsc, false);
    }

    componentWillUnmount() {
        // document.removeEventListener("keydown", this.closeChangePassPopupExistedEmailEsc, false);
    }

    closeChangePassPopupExistedEmailEsc(event) {
        if (event.keyCode === 27) {
            // this.closeChangePassPopupExistedEmail();
            const popupOption = document.getElementById('option-popup');
            if (popupOption) {
                popupOption.className = "popup option-popup";
            }
            // setSession(SHOW_LOGIN);
        }

    }

    closeChangePassPopupExistedEmail() {
        if (document.getElementById('existed-email-popup')) {
            document.getElementById('existed-email-popup').className = "popup special existed-email-popup";
        }
        if (document.getElementById('login-id')) {
            document.getElementById('login-id').className = "login show";
        }
        if (document.getElementById('existed-email')) {
            document.getElementById('existed-email').value = '';
        }
        if (document.getElementById('btn-existed-email')) {
            document.getElementById('btn-existed-email').className = "btn btn-primary disabled";
        }
        sessionStorage.removeItem(FORGOT_PASSWORD);
        try {
            this.resetState();
        } catch(error) {
            console.log(error);
        }
        window.location.href = '/';
    }

    resetState = () => {
        this.setState({
            email: '',
            errorMessage: '',
            showForgotUsernameModal: false,
            showForgotPasswordModal: false,
            showForgotBothModal: false,
            minutes: 0,
            seconds: 0,
            loading: false,
            disabledConfirm: true,
            transactionId: '',
            countdown: 30 * 60, // 30 minutes in seconds
            errorCount: 5,
            isInvalid: false,
            isLock: false,
            captcha: '',
            errorCaptcha: 'Nhập Mã bảo vệ',
            isOpenPopup: true
        });
    }

    startCountdown() {
        let countdownInterval = setInterval(() => {
            if (this.state.countdown > 0) {
                this.setState({countdown: this.state.countdown - 1});
            } else {
                clearInterval(countdownInterval);
                // Additional actions when the countdown reaches 0
                this.resetState();
            }
        }, 1000); // Update every 1 second
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

    // ----------------------------------

    popupOtpLoginSubmit = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(API_TOKEN)?getSession(API_TOKEN):'',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),//this.state.POID
                DeviceId: getDeviceId(),
                Note: 'ForgotPasswordNew',
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
                    this.setState({showForgotUsernameModal: false, showForgotPasswordModal: false, showForgotBothModal: false, minutes: 0, seconds: 0, errorMessage: '', otp: OTP});
                    if (!isEmpty(response?.Response?.NewAPIToken)) {
                      setSession(API_TOKEN, response?.Response?.NewAPIToken)
                    }
                    this.resetFormAndShowPopup();
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({errorMessage: OTP_EXPIRED});
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.setState({showForgotUsernameModal: false, showForgotPasswordModal: false, showForgotBothModal: false, minutes: 0, seconds: 0});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({errorMessage: `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${response.Response.Message} lần thử.`});
                } else {
                    this.setState({errorMessage: `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${response.Response.Message} lần thử.`});
                }
            }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    popupExistedEmailSubmit(event) {
        event.preventDefault();
        if (document.getElementById('btn-existed-email').className === "btn btn-primary disabled") {
            return;
        }
        this.forgotPassword();
    }

    formatTime(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
    }

    forgotPassword = () => {
        this.setState({
            loading: true,
            disabledConfirm: true,
        });
        const forgotPassRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'ForgotPasswordNew',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: '',
                Project: 'mcp',
                UserLogin: this.state.email
            }
        };
        forgotPass(forgotPassRequest)
            .then(response => {
                const { Result, ErrLog, Message, UserLogin, UserName } = response.Response;

                console.log("forgotPass", response);
                if (Result === 'true') {
                    if (ErrLog === "FORGOTPASSSUCC") {
                        const showForgotUsernameModal = getSession(FORGOT_USERNAME_ONLY);
                        const showForgotPasswordModal = getSession(FORGOT_PASSWORD_ONLY);

                        this.setState({
                            showForgotUsernameModal: !!showForgotUsernameModal,
                            showForgotPasswordModal: !!showForgotPasswordModal,
                            showForgotBothModal: !showForgotUsernameModal && !showForgotPasswordModal,
                            minutes: 5,
                            seconds: 0,
                            loading: false,
                            disabledConfirm: false,
                            transactionId: Message,
                        });

                        // setSession(USER_LOGIN, this.state.email);
                        setSession(USER_LOGIN, UserLogin);
                        setSession(DCID, response.Response.DCID);
                        setSession(USER_NAME, UserName);
                        
                        this.props.callbackSetEmail(this.state.email);
                        this.props.callbackStartTimer();
                        document.getElementById('existed-email-popup').className = "popup special existed-email-popup";
                        document.getElementById('existed-email').value = '';
                        this.startTimer();
                    } else if ((ErrLog === "EMAILINVALID") || (ErrLog ===  "PHONEINVALID")) {
                        const errorCount = this.state.errorCount;

                        if (errorCount === 1) {
                            this.setState({
                                countdown: 1800,
                                loading: false,
                                disabledConfirm: true,
                                isLock: true,
                                isInvalid: false,
                                errorCount: 5,
                            }); // Set countdown to 30 minutes (1800 seconds)
                            this.startCountdown();
                        } else {
                            this.setState({
                                loading: false,
                                disabledConfirm: false,
                                errorCount: errorCount - 1,
                                isInvalid: true,
                                isLock: false,
                            });
                        }
                        loadCaptchaEnginge(6, 'grey', 'white');
                    } else if (response.Response.ErrLog === 'OTP Exceed') {
                        this.setState({
                            loading: false,
                            disabledConfirm: false,
                        });
                        document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                    } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                        this.setState({
                            loading: false,
                            disabledConfirm: false,
                        });
                        document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                        loadCaptchaEnginge(6, 'grey', 'white');
                    } else {
                        this.setState({
                            loading: false,
                            disabledConfirm: false,
                        });
                        document.getElementById("popup-exception").className = "popup special point-error-popup show";
                        loadCaptchaEnginge(6, 'grey', 'white');
                    }
                } else {
                    // Handle the case where the forgot password request was not successful
                    console.error("Forgot password request failed:", ErrLog);
                    this.setState({
                        loading: false,
                        disabledConfirm: false,
                    });
                    loadCaptchaEnginge(6, 'grey', 'white');
                }
            })
            .catch(error => {
                // Handle network or unexpected errors
                console.error("An error occurred during the forgot password request:", error);
                this.setState({
                    loading: false,
                    disabledConfirm: false,
                });
                loadCaptchaEnginge(6, 'grey', 'white');
            });
    }

    // Function to reset form and show popup
    resetFormAndShowPopup() {
        document.getElementById('existed-email-popup').className = "popup special existed-email-popup";
        document.getElementById('existed-email-popup').className = "popup special otp-popup";
        document.getElementById('new-password-login-popup').className = 'popup special new-password-login-popup show';
        document.getElementById('existed-email').value = '';
        document.getElementById('btn-existed-email').className = 'btn btn-primary disabled';

        this.props.callbackResetTimer();
        this.props.callbackSetEmail('');
    }

    closeOtp = () => {
        this.resetState();
    }

    // Function to show error message
    showErrorMessage(elementId) {
        document.getElementById(elementId).className = 'error-message validate';
    }

    callBackSuccessful() {
        this.props.parentCallback(true);
        this.resetStateForgot();
        this.resetState();
    }

    resetStateForgot() {
        const popupExistedEmail = document.getElementById('existed-email-popup');

        if (popupExistedEmail) {
            popupExistedEmail.className = "popup special existed-email-popup";
            popupExistedEmail.className = "popup special otp-popup";
        }
    }

    clearState() {
        this.setState({
            minutes: 0,
            seconds: 0,
            isOpenPopup: false,

        });
    }

    generationOTP() {
        //gen otp
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTP',
                Authentication: AUTHENTICATION,
                CellPhone: getSession(CELL_PHONE),
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: 'AutoRegisterAccount',
                OS: '',
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }

        }
        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({
                        showForgotUsernameModal: true,
                        minutes: 5,
                        seconds: 0,
                        loading: false,
                        disabledConfirm: false,
                        transactionId: response.Response.Message,
                    });
                    this.startTimer();
                    setSession(TRANSACTION_ID, response.Response.Message);

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
    };

    onChangeInput(event) {
        const value = event.target.value;
        this.setState({captcha: value});
        this.validateInput(value);
      }

    validateInput(value) {
        let eMsg = !validateCaptcha(value, false) ? "Mã bảo vệ không đúng" : "";
        this.setState({ errorCaptcha: eMsg })
        if (this.state.email.length > 2 && !this.state.isLock && !eMsg && this.state.captcha) {
            this.setState({
                disabledConfirm: false,
            });
        } else {
            this.setState({
                disabledConfirm: true,
            });
        }
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

    render() {
        return (
            <div className="popup special existed-email-popup" id="existed-email-popup">
                <form onSubmit={this.popupExistedEmailSubmit}>
                    <div className="popup__card restore">
                        <div className="popup-existed-email-card">
                            <div className="header">
                                {isMobile()?(
                                    <p>Khôi phục <br/> Thông tin đăng nhập​</p>
                                ):(
                                    <p>Khôi phục Thông tin đăng nhập​</p>
                                )}
                                <i className="closebtn"><img src="../img/icon/close.svg" alt=""
                                                             onClick={this.closeChangePassPopupExistedEmail}/></i>
                            </div>
                            <div className="body">
                                {this.state.isInvalid && <div className="error-message validate" id="signup-exist-poinfo-incorrect" style={{
                                    height: '50px', padding: '16px 6px', marginBottom: 24
                                }}>
                                    <i className="icon" style={{marginRight: 12}}>
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p style={{margin: 0, fontSize: 13, textAlign: 'left'}}>Thông tin vừa nhập không khớp với bất kỳ tài khoản nào.
                                        Quý khách còn <span style={{ fontSize: 13, color: '#ffffff' }}>{this.state.errorCount}</span> lần thử.</p>
                                </div>}

                                {this.state.isLock && <div className="error-message validate" id="signup-exist-polock-incorrect" style={{
                                    height: '50px', padding: '16px 6px', marginBottom: 24
                                }}>
                                    <i className="icon" style={{marginRight: 12}}>
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p style={{margin: 0, fontSize: 13}}>Tính năng tạm khóa do Quý khách đã nhập sai quá nhiều lần. Vui lòng thử lại sau <span
                                            style={{color: '#FAE340', fontSize: 13}}>{this.formatTime(this.state.countdown)}</span>
                                    </p>
                                </div>}

                                <p className="basic-text2"
                                   style={{fontSize: 15, fontWeight: 400, lineHeight: '21px', marginBottom: 16}}>
                                    Quý khách vui lòng nhập Email hoặc Số điện thoại đã dùng để đăng ký tài khoản.
                                </p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend" id="email-info">
                                            <div className="input__content">
                                                <label className="--" htmlFor="">Email/Số điện thoại​</label>
                                                <input type="search" id="existed-email" name="email"
                                                       onChange={this.handleInputEmailExistedChange} required/>
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                        </div>
                                    </div>
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
                                        <LoadCanvasTemplate />
                                        </div>
                                        <div className="reload-icon" id="reload_href" style={{paddingLeft: '8px', cursor:'pointer'}} onClick={()=>loadCaptchaEnginge(6, 'grey', 'white')}>
                                        <img src={FE_BASE_URL + "/img/icon/reload-icon.svg"} alt="reload" />
                                        </div>
                                    </div>
                                </div>
                                {this.state.errorCaptcha.length > 0 && <span style={{ color: 'red', margin: '12px 0 0 0', display: 'flex' }}>{this.state.errorCaptcha}</span>}
                                {this.state.loading && <LoadingIndicatorBasic/>}
                                <div className="btn-wrapper">
                                    <button
                                        className={this.state.disabledConfirm ? "btn btn-primary disabled" : "btn btn-primary"}
                                        disabled={this.state.disabledConfirm}
                                        id="btn-existed-email">Xác nhận
                                    </button>
                                </div>
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

                {this.state.showForgotUsernameModal && <ForgotUsernameModalPotential
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskEmail={(typeof this.state.email === 'string' && this.state.email.includes('@'))?maskEmail(this.state.email): ''}
                    maskPhone={(typeof this.state.email === 'string' && !this.state.email.includes('@'))?maskPhoneNumber(this.state.email): ''}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateForgot}
                    handleGenOTP={this.generationOTP}
                    callBackException={this.callExceptionPopup}
                    clearState={this.handleClearState}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                />}

                {this.state.showForgotPasswordModal && <ForgotPasswordModalPotential
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskEmail={(typeof this.state.email === 'string' && this.state.email.includes('@'))?maskEmail(this.state.email): ''}
                    maskPhone={(typeof this.state.email === 'string' && !this.state.email.includes('@'))?maskPhoneNumber(this.state.email): ''}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateForgot}
                    handleGenOTP={this.generationOTP}
                    clearState={this.handleClearState}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                />}

                {this.state.showForgotBothModal && <ForgotBothModalPotential
                    seconds={this.state.seconds}
                    minutes={this.state.minutes}
                    transactionId={this.state.transactionId}
                    maskEmail={(typeof this.state.email === 'string' && this.state.email.includes('@'))?maskEmail(this.state.email): ''}
                    maskPhone={(typeof this.state.email === 'string' && !this.state.email.includes('@'))?maskPhoneNumber(this.state.email): ''}
                    parentPath={this.props.path}
                    isOpenPopup={this.state.isOpenPopup}
                    callBackSuccessful={this.callBackSuccessful}
                    resetState={this.resetStateForgot}
                    handleGenOTP={this.generationOTP}
                    clearState={this.handleClearState}
                    showCloseConfirm={()=>this.handleShowCloseConfirm}
                />}
                {this.state.closeConfirm && <ClosePopupConfirm closePopup={this.handleCloseConfirmPopup} closeAllPopup = {this.handleCloseAllPopup}/>}
            </div>

        )
    }
}

export default PopupExistedEmail;