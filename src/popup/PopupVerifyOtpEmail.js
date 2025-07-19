import React, { Component } from 'react';
import { login, verifyOTP, genOTP, login2 } from '../util/APIUtils';
import { OTP_EMAIL, FORGOT_PASSWORD, USER_LOGIN, CLIENT_ID, ACCESS_TOKEN, TRANSACTION_ID, LOGIN_SOCIAL, CLIENT_PROFILE, CELL_PHONE, DEFAULT_PASS, POID, DOB, GENDER, ADDRESS, EMAIL, VERIFY_CELL_PHONE, FULL_NAME, VERIFY_EMAIL, TWOFA, LINK_FB, LINK_GMAIL, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION, DCID } from '../constants';
import LoadingIndicator from '../common/LoadingIndicator2';
import '../common/Common.css';
import {showMessage, setSession, getSession, getDeviceId, buildParamsAndRedirect} from '../util/common';
import ReactGA from 'react-ga4';

class PopupVerifyOtpEmail extends Component {
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
            document.getElementById('btn-verify-otp-email').className = "btn btn-primary";
        } else {
            document.getElementById('btn-verify-otp-email').className = "btn btn-primary disabled";
        }
    }
    popupOtpEmailSubmit(event) {
        event.preventDefault();
        if (document.getElementById('btn-verify-otp-email').className === "btn btn-primary disabled") {
            return;
        }

        var tranId = 1;
        if (getSession(TRANSACTION_ID) && getSession(TRANSACTION_ID) !== null && getSession(TRANSACTION_ID) !== 'null') {
            tranId = getSession(TRANSACTION_ID);
        }
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: '',
                //DeviceId: getDeviceId(),
                Note: 'VALID_OTP_EMAIL',
                OS: '',
                OTP: this.state.OTP,
                Project: 'mcp',
                TransactionID: tranId,
                UserLogin: getSession(USER_LOGIN)
            }

        }
        verifyOTP(verifyOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.props.callbackResetTimer();
                    var pass = getSession(DEFAULT_PASS) ? getSession(DEFAULT_PASS) : DEFAULT_PASS;
                    if (getSession(LOGIN_SOCIAL)) {
                        //pass = DEFAULT_PASS;
                        sessionStorage.removeItem(LOGIN_SOCIAL);
                    }

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
                            Password: pass
                        }
                    };

                    login(loginRequest)
                        .then(response => {
                            if (response.Response.Result === 'true' && response.Response.ErrLog === 'Login successfull') {
                                //Alert.success("You're successfully logged in!");
                                document.getElementById('error-message-email-expire-id').className = 'error-message';
                                document.getElementById('error-message-email-id').className = 'error-message';
                                document.getElementById('otp-email-id').value = '';
                                document.getElementById('otp-email-popup').className = "popup special otp-popup";
                                document.getElementById('popup-thanks-email').className = "popup option-popup show";

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
                                    ReactGA.set({ userId: response.Response.ClientProfile[0].DCID });
                                }
                                this.props.parentCallback(true, response.Response.ClientProfile, 0);
                                if (this.props.path) {
                                    buildParamsAndRedirect(this.props.path);
                                }
                            } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'RECACTCHA_REQUIRED_OTP') {
                                
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
                                        Password: pass,
                                        Captcha: response.Response.Captcha
                                    }
                                };

                                login2(loginRequest2, true)
                                .then(response => {
                                    if (response.Response.Result === 'true' && response.Response.ErrLog === 'Login successfull') {
                                        //Alert.success("You're successfully logged in!");
                                        document.getElementById('error-message-email-expire-id').className = 'error-message';
                                        document.getElementById('error-message-email-id').className = 'error-message';
                                        document.getElementById('otp-email-id').value = '';
                                        document.getElementById('otp-email-popup').className = "popup special otp-popup";
                                        document.getElementById('popup-thanks-email').className = "popup option-popup show";
        
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
                                            ReactGA.set({ userId: response.Response.ClientProfile[0].DCID });
                                        }
                                        this.props.parentCallback(true, response.Response.ClientProfile, 0);
                                        if (this.props.path) {
                                            buildParamsAndRedirect(this.props.path);
                                        }
                                    } else {
                                        document.getElementById("popup-exception").className = "popup special point-error-popup show";
                                    }
        
                                    //localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                                    //this.props.history.push("/");
                                }).catch(error => {
                                    //this.props.history.push('/maintainence');
                                });
                            } else {
                                //var msg = `Login failed! ${response.Response.errLog}`;
                                //Alert.error(msg);
                            }

                            //localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                            //this.props.history.push("/");
                        }).catch(error => {
                            //this.props.history.push('/maintainence');
                        });

                }
                else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                }
                else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    document.getElementById('error-message-email-expire-id').className = 'error-message validate';
                    document.getElementById('error-message-email-id').className = 'error-message';
                    document.getElementById('btn-verify-otp-email').className = "btn btn-primary disabled";
                }
                else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('otp-email-popup').className = "popup special otp-popup";
                    document.getElementById('error-message-email-expire-id').className = 'error-message';
                    document.getElementById('error-message-email-id').className = 'error-message';
                    document.getElementById('otp-email-id').value = '';
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                }
                else {
                    document.getElementById('error-message-email-id').className = 'error-message validate';
                    document.getElementById('error-message-email-expire-id').className = 'error-message';
                    document.getElementById('btn-verify-otp-email').className = "btn btn-primary disabled";
                }
            }).catch(error => {
                //this.props.history.push('/maintainence');
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
        document.getElementById('error-message-email-expire-id').className = 'error-message';
        document.getElementById('error-message-email-id').className = 'error-message';
        document.getElementById('otp-email-id').value = '';
        document.getElementById('btn-verify-otp-email').className = "btn btn-primary disabled";
        document.getElementById('otp-email-popup').className = "popup special otp-popup";
        if (getSession(FORGOT_PASSWORD)) {
            sessionStorage.removeItem(FORGOT_PASSWORD);
        }
        this.props.callbackResetTimer();
    }

    render() {
        const reGenOtpEmail=()=> {
            const genOTPRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'GenOTP',
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(USER_LOGIN),
                    Email: getSession(USER_LOGIN),
                    DeviceId: getDeviceId(),
                    Note: 'VALID_OTP_EMAIL',
                    OS: '',
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                }
    
            }
    
            //console.log(genOTPRequest);
            genOTP(genOTPRequest)
                .then(response => {
                    if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                        // Alert.success("Gửi lại mã mới OTP thành công!");
                        if (getSession(FORGOT_PASSWORD)) {
                            setSession(TRANSACTION_ID, response.Response.Message);
                        } else {
                            this.props.callbackStartTimer();
                            setSession(TRANSACTION_ID, response.Response.Message);
                        }
                    } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Exceed') {
                        document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                    } else if (response.Response.ErrLog === 'OTP Wrong 3 times') {
                        document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                    } else {
                        document.getElementById("popup-exception").className = "popup special point-error-popup show";
                    }
                }).catch(error => {
                    //alert('error:' + error);
                });
        }
        return (
            <div className="popup special otp-popup" id="otp-email-popup">
                <form onSubmit={this.popupOtpEmailSubmit}>
                    <div className="popup__card">
                        <div className="popup-otp-card">
                            <div className="header">
                                <p>Nhập mã OTP</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeOtpPopup} /></i>
                            </div>
                            <div className="body">
                                <div className="error-message" id="error-message-email-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt="" />
                                    </i>
                                    <p>Mã OTP không đúng, Quý khách vui lòng nhập lại.</p>
                                </div>
                                <div className="error-message" id="error-message-email-expire-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt="" />
                                    </i>
                                    <p>Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.</p>
                                </div>
                                <p className="basic-text2 bigheight">
                                    Mã <span className="basic-text2 basic-bold">OTP</span> đã được gửi đến
                                    <span className="basic-text2 basic-bold">&nbsp;Email</span> của Quý khách. Vui lòng nhập
                                    <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác nhận.
                                </p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend">
                                            <div className="input__content">
                                                <label className="--" htmlFor="">Mã OTP</label>
                                                <input type="search" name="OTP" id="otp-email-id" onChange={this.handleInputOtpEmailChange} required />
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt="" /></i>
                                        </div>
                                    </div>
                                </div>
                                {this.props.minutes === 0 && this.props.seconds === 0
                                    ? (
                                        <p className="tag">Bạn chưa nhận được mã OTP? <span className="simple-brown" onClick={()=>reGenOtpEmail()}>Gửi lại mã mới</span></p>
                                    ) : (<p className="tag padding-clock"><i className="sand-clock"><img src="../img/icon/sand_clock.svg" />{this.props.minutes}:{this.props.seconds < 10 ? `0${this.props.seconds}` : this.props.seconds}</i></p>)
                                }
                                <LoadingIndicator area="verify-otp" />
                                <div className="btn-wrapper">
                                    <button className="btn btn-primary disabled" id="btn-verify-otp-email">Xác nhận</button>
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
export default PopupVerifyOtpEmail;