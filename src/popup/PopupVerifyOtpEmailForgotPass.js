import React, {Component} from 'react';
import {login, forgotPass} from '../util/APIUtils';
import {
    OTP_EMAIL,
    FORGOT_PASSWORD,
    USER_LOGIN,
    ACCESS_TOKEN,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    AUTHENTICATION
} from '../constants';
import LoadingIndicator from '../common/LoadingIndicator2';
import {showMessage, setSession, getSession, getDeviceId} from '../util/common';

class PopupVerifyOtpEmailForgotPass extends Component {
    constructor(props) {
        super(props);
        this.state = {
            OTP: ''
        };
        this.handleInputOtpEmailChange = this.handleInputOtpEmailChange.bind(this);
        this.popupOtpEmailSubmit = this.popupOtpEmailSubmit.bind(this);
        this.closeOtpPopupEsc = this.closeOtpPopupEsc.bind(this);
        this.showErrorMessage = this.showErrorMessage.bind(this);
        this.resetFormAndShowPopup = this.resetFormAndShowPopup.bind(this);
        this.handleLoginWithCaptcha = this.handleLoginWithCaptcha.bind(this);
        this.handleLoginResponse = this.handleLoginResponse.bind(this);
    }

    handleInputOtpEmailChange(event) {

        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        this.setState({
            [inputName]: inputValue
        });
        if (inputValue !== '') {
            document.getElementById('btn-verify-otp-email-forgot').className = "btn btn-primary";
        } else {
            document.getElementById('btn-verify-otp-email-forgot').className = "btn btn-primary disabled";
        }
    }

    popupOtpEmailSubmit(event) {
        event.preventDefault();

        const verifyOtpButton = document.getElementById('btn-verify-otp-email-forgot');
        if (verifyOtpButton.className === 'btn btn-primary disabled') {
            return;
        }

        setSession(OTP_EMAIL, this.state.OTP);

        const loginRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CPLOGIN',
                APIToken: '',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: '',
                UserLogin: getSession(USER_LOGIN),
                OS: '',
                Project: 'mcp',
                Password: this.state.OTP
            }
        };

        login(loginRequest)
            .then(response => {
                this.handleLoginResponse(response);
            })
            .catch(error => {
                // Handle error
            });
    }

// Function to handle the login response
    handleLoginResponse(response) {
        if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
            showMessage(EXPIRED_MESSAGE);
            return;
        }

        const popupIds = ['error-message-email-forgot-expire-id', 'error-message-email-forgot-id', 'error-message-email-max-wrong-exceed-id'];
        popupIds.forEach(id => {
            document.getElementById(id).className = 'error-message';
        });

        if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGEPASS' && response.Response.ClientProfile !== null) {
            this.resetFormAndShowPopup();
        } else if (response.Response.Result === 'true' || response.Response.ErrLog === 'RECACTCHA_REQUIRED_OTP') {
            this.handleLoginWithCaptcha(response);
        } else {
            document.getElementById('error-message-email-forgot-id').className = 'error-message validate';
        }
    }

// Function to handle login with captcha
    handleLoginWithCaptcha(response) {
        const loginRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CPLOGIN',
                APIToken: '',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: '',
                UserLogin: getSession(USER_LOGIN),
                OS: '',
                Project: 'mcp',
                Password: this.state.OTP,
                Captcha: response.Response.Captcha
            }
        };

        login(loginRequest, true)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGEPASS') {
                    this.resetFormAndShowPopup();
                } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'WRONGPASSEXCEED') {
                    this.showErrorMessage('error-message-email-max-wrong-exceed-id');
                } else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    this.showErrorMessage('error-message-email-forgot-expire-id');
                } else {
                    this.showErrorMessage('error-message-email-forgot-id');
                }
            })
            .catch(error => {
                // Handle error
            });
    }

// Function to reset form and show popup
    resetFormAndShowPopup() {
        const otpInput = document.getElementById('otp-email-forgot-id');
        otpInput.value = '';
        document.getElementById('otp-email-forgot-popup').className = 'popup special otp-popup';
        document.getElementById('new-password-popup').className = 'popup special new-password-popup show';
        this.props.callbackSetEmail('');
        document.getElementById('existed-email').value = '';
        document.getElementById('btn-existed-email').className = 'btn btn-primary disabled';
        this.props.callbackResetTimer();
    }

// Function to show error message
    showErrorMessage(elementId) {
        document.getElementById(elementId).className = 'error-message validate';
    }


    componentDidMount() {
        document.addEventListener("keydown", this.closeOtpPopupEsc, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.closeOtpPopupEsc, false);
    }

    closeOtpPopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeOtpPopup();
        }

    }
    closeOtpPopup = () => {
        document.getElementById('error-message-email-forgot-expire-id').className = 'error-message';
        document.getElementById('error-message-email-forgot-id').className = 'error-message';
        document.getElementById('error-message-email-max-wrong-exceed-id').className = 'error-message';
        document.getElementById('otp-email-forgot-id').value = '';
        document.getElementById('btn-verify-otp-email-forgot').className = "btn btn-primary disabled";
        document.getElementById('otp-email-forgot-popup').className = "popup special otp-popup";
        if (getSession(FORGOT_PASSWORD)) {
            sessionStorage.removeItem(FORGOT_PASSWORD);
        }
        this.props.callbackSetEmail('');
        this.props.callbackResetTimer();
        document.getElementById('existed-email').value = '';
        document.getElementById('btn-existed-email').className = "btn btn-primary disabled";
    }

    render() {
        const reGenOtpEmail = () => {
            const forgotPassRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'ForgotPassword',
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: '',
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN)
                }

            }
            forgotPass(forgotPassRequest)
                .then(response => {
                    if (response.Response.Result === 'true') {
                        this.props.callbackStartTimer();
                    }
                }).catch(error => {
                //this.props.history.push('/maintainence');
            });
        }
        return (
            <div className="popup special otp-popup" id="otp-email-forgot-popup">
                <form onSubmit={this.popupOtpEmailSubmit}>
                    <div className="popup__card">
                        <div className="popup-otp-card">
                            <div className="header">
                                <p>Nhập mã OTP</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt=""
                                                             onClick={this.closeOtpPopup}/></i>
                            </div>
                            <div className="body">
                                <div className="error-message" id="error-message-email-forgot-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p>Mã OTP không đúng, Quý khách vui lòng nhập lại.</p>
                                </div>
                                <div className="error-message" id="error-message-email-forgot-expire-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p>Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.</p>
                                </div>
                                <div className="error-message" id="error-message-email-max-wrong-exceed-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p>Tài khoản của Quý khách đang bị khóa do nhập sai thông tin nhiều lần. Vui lòng
                                        thử lại sau 30 phút.</p>
                                </div>
                                <p className="basic-text2 bigheight">
                                    Mã <span className="basic-text2 basic-bold">OTP</span> sẽ được gửi qua email của Quý
                                    khách nếu <br/>
                                    tên đăng nhập tồn tại trong hệ thống.<br/> Vui lòng nhập <span
                                    className="basic-text2 basic-bold">OTP</span> để xác nhận.
                                </p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend">
                                            <div className="input__content">
                                                <label className="--" htmlFor="">Mã OTP</label>
                                                <input type="search" name="OTP" id="otp-email-forgot-id"
                                                       onChange={this.handleInputOtpEmailChange} required/>
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                        </div>
                                    </div>
                                </div>
                                {this.props.minutes === 0 && this.props.seconds === 0
                                    ? (
                                        <p className="tag">Bạn chưa nhận được mã OTP? <span className="simple-brown"
                                                                                            onClick={() => reGenOtpEmail()}>Gửi lại mã mới</span>
                                        </p>
                                    ) : (<p style={{
                                        textAlign: 'center',
                                        paddingTop: '20px'
                                    }}>{this.props.minutes * 60 + this.props.seconds}</p>)
                                }
                                <LoadingIndicator area="verify-otp"/>
                                <div className="btn-wrapper">
                                    <button className="btn btn-primary disabled" id="btn-verify-otp-email-forgot">Xác
                                        nhận
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </form>
            </div>
        )
    }
}

export default PopupVerifyOtpEmailForgotPass;