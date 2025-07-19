import React, {Component} from 'react';
import {login, verifyOTP, genOTP, changePass} from '../util/APIUtils';
import {
    EXPIRED_MESSAGE,
    USER_LOGIN,
    CLIENT_ID,
    ACCESS_TOKEN,
    COMPANY_KEY,
    LOGIN_SOCIAL,
    CLIENT_PROFILE,
    CELL_PHONE,
    POID,
    DOB,
    OTP_EMAIL,
    FULL_NAME,
    GENDER,
    ADDRESS,
    EMAIL,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    TWOFA,
    LINK_FB,
    LINK_GMAIL,
    AUTHENTICATION,
    DCID
} from '../constants';
import {showMessage, setSession, getSession, getDeviceId, buildParamsAndRedirect} from '../util/common';

class PopupVerifyOtpEmailSocial extends Component {
    constructor(props) {
        super(props);
        this.state = {
            OTP: ''
        };
        this.handleInputOtpEmailChange = this.handleInputOtpEmailChange.bind(this);
        this.popupOtpEmailSubmit = this.popupOtpEmailSubmit.bind(this);
        this.closeOtpPopupEsc = this.closeOtpPopupEsc.bind(this);
    }

    handleInputOtpEmailChange(event) {

        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        this.setState({
            [inputName]: inputValue
        });
        if (inputValue !== '') {
            document.getElementById('btn-verify-otp-email-social').className = "btn btn-primary";
        } else {
            document.getElementById('btn-verify-otp-email-social').className = "btn btn-primary disabled";
        }
    }

    popupOtpEmailSubmit(event) {
        event.preventDefault();
        if (document.getElementById('btn-verify-otp-email-social').className === "btn btn-primary disabled") {
            return;
        }
        setSession(OTP_EMAIL, this.state.OTP);
        var loginRequest = {
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
        }
        login(loginRequest)
            .then(response => {
                //console.log(response.Response);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGEPASS') {
                    setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                    if (getSession(LOGIN_SOCIAL)) {
                        sessionStorage.removeItem(LOGIN_SOCIAL);
                        document.getElementById('error-message-social-expire-id').className = 'error-message';
                        document.getElementById('error-message-social-id').className = 'error-message';
                        document.getElementById('otp-social-id').value = '';
                        document.getElementById('otp-email-popup-social').className = "popup special otp-popup";
                        setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                        setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                        setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);
                        setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                        setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                        setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                        setSession(GENDER, response.Response.ClientProfile[0].Gender);
                        setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                        setSession(EMAIL, response.Response.ClientProfile[0].Email);
                        setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                        setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                        setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                        setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                        setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);

                        if (response.Response.ClientProfile[0].POID) {
                            setSession(POID, response.Response.ClientProfile[0].POID);
                        }
                        if (response.Response.ClientProfile[0].DOB) {
                            setSession(DOB, response.Response.ClientProfile[0].DOB);
                        }
                        if (response.Response.ClientProfile[0].DCID) {
                            setSession(DCID, response.Response.ClientProfile[0].DCID);
                        }
                        this.props.parentCallback(response.Response);
                        if (this.props.path) {
                            buildParamsAndRedirect(this.props.path);
                        }
                    } else {
                        document.getElementById('otp-email-popup').className = "popup special otp-popup";
                        document.getElementById('new-password-popup').className = "popup special new-password-popup show";
                    }


                    //Alert.success("You're successfully logged in!");
                } else if (response.Response.Result === 'true' || response.Response.ErrLog === 'RECACTCHA_REQUIRED_OTP') {
                    var loginRequest2 = {
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
                    }
                    login(loginRequest2, true)
                        .then(response => {
                            //console.log(response.Response);
                            if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGEPASS') {
                                setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                                if (getSession(LOGIN_SOCIAL)) {
                                    sessionStorage.removeItem(LOGIN_SOCIAL);
                                    document.getElementById('error-message-social-expire-id').className = 'error-message';
                                    document.getElementById('error-message-social-id').className = 'error-message';
                                    document.getElementById('otp-social-id').value = '';
                                    document.getElementById('otp-email-popup-social').className = "popup special otp-popup";
                                    setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                                    setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                                    setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);
                                    setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                                    setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                                    setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                                    setSession(GENDER, response.Response.ClientProfile[0].Gender);
                                    setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                                    setSession(EMAIL, response.Response.ClientProfile[0].Email);
                                    setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                                    setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                                    setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                                    setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                                    setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);

                                    if (response.Response.ClientProfile[0].POID) {
                                        setSession(POID, response.Response.ClientProfile[0].POID);
                                    }
                                    if (response.Response.ClientProfile[0].DOB) {
                                        setSession(DOB, response.Response.ClientProfile[0].DOB);
                                    }
                                    if (response.Response.ClientProfile[0].DCID) {
                                        setSession(DCID, response.Response.ClientProfile[0].DCID);
                                    }
                                    this.props.parentCallback(response.Response);
                                    if (this.props.path) {
                                        buildParamsAndRedirect(this.props.path);
                                    }
                                } else {
                                    document.getElementById('otp-email-popup').className = "popup special otp-popup";
                                    document.getElementById('new-password-popup').className = "popup special new-password-popup show";
                                }


                                //Alert.success("You're successfully logged in!");
                            }
                        }).catch(error => {

                    });

                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else {
                    document.getElementById('error-message-social-id').className = 'error-message validate';
                }

                //localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                //this.props.history.push("/");
            }).catch(error => {
            // Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });

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
        document.getElementById('error-message-social-expire-id').className = 'error-message';
        document.getElementById('error-message-social-id').className = 'error-message';
        document.getElementById('otp-social-id').value = '';
        document.getElementById('otp-email-popup-social').className = "popup special otp-popup";
    }

    render() {

        return (
            <div className="popup special otp-popup" id="otp-email-popup-social">
                <form onSubmit={this.popupOtpEmailSubmit}>
                    <div className="popup__card">
                        <div className="popup-otp-card">
                            <div className="header">
                                <p>Nhập mã OTP</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt=""
                                                             onClick={this.closeOtpPopup}/></i>
                            </div>
                            <div className="body">
                                <div className="error-message" id="error-message-social-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p>Mã OTP không đúng, Quý khách vui lòng nhập lại.</p>
                                </div>
                                <div className="error-message" id="error-message-social-expire-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p>Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.</p>
                                </div>
                                <p className="basic-text2 bigheight">
                                    Mã <span className="basic-text2 basic-bold">OTP</span> đã được gửi đến
                                    <span className="basic-text2 basic-bold">&nbsp;Email</span> của Quý khách. Vui lòng
                                    nhập
                                    <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác nhận.
                                </p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend">
                                            <div className="input__content">
                                                <label className="--" htmlFor="">Mã OTP</label>
                                                <input type="search" name="OTP" id="otp-social-id"
                                                       onChange={this.handleInputOtpEmailChange} required/>
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                        </div>
                                    </div>
                                </div>
                                <div className="btn-wrapper">
                                    <button className="btn btn-primary disabled" id="btn-verify-otp-email-social">Xác
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

export default PopupVerifyOtpEmailSocial;