import React, { Component } from 'react';
import {genOTP, verifyOTP} from '../util/APIUtils';
import {setSession, getSession, getDeviceId, showMessage, maskPhoneNumber} from '../util/common';
import {
    USER_LOGIN,
    CLIENT_ID,
    COMPANY_KEY,
    CELL_PHONE,
    TRANSACTION_ID,
    AUTHENTICATION,
    EXPIRED_MESSAGE, API_TOKEN, ACCOUNT_STATUS, OTP_EXPIRED, OTP_INCORRECT, FORGOT_PASSWORD
} from '../constants';
import DOTPInput from "../components/DOTPInput";
import {isEmpty} from "lodash";

class PopupRestorePass extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showOtp: false,
            minutes: 0,
            seconds: 0,
            errorMessage: '',
            transactionId: '',
        };

        this.closeRestorePassEsc = this.closeRestorePassEsc.bind(this);
        this.restorePass = this.restorePass.bind(this);
    }

    restorePass() {
        document.getElementById('restore-password').className = "popup option-popup";
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
        //console.log(genOTPRequest);
        genOTP(genOTPRequest)
            .then(response => {
                console.log("genOTP", response);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.props.callbackStartTimer();
                    this.setState({
                        showOtp: true,
                        minutes: 5,
                        seconds: 0,
                        transactionId: response.Response.Message,
                    });
                    this.startTimer();
                    setSession(TRANSACTION_ID, response.Response.Message);
                    // document.getElementById('otp-popup').className = "popup special otp-popup show";

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
                //this.props.history.push('/maintainence');
            });
    }

    popupVerifyOTP = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(API_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),//this.state.POID
                DeviceId: getDeviceId(),
                // Note: getSession(ACCOUNT_STATUS),
                Note: getSession(ACCOUNT_STATUS) === FORGOT_PASSWORD ? 'ForgotPasswordNew' : 'RegisterAccount',
                OS: '',
                OTP: OTP,
                Project: 'mcp',
                TransactionID: this.state.transactionId,
                UserLogin: getSession(USER_LOGIN)
            }

        }
        verifyOTP(verifyOTPRequest)
            .then(response => {
                console.log("verifyOTP", response);

                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({
                        errorMessage: '',
                        showOtp: false,
                        minutes: 0,
                        seconds: 0,
                    });
                    if (!isEmpty(response?.Response?.NewAPIToken)) {
                        setSession(API_TOKEN, response?.Response?.NewAPIToken);
                    }
                    document.getElementById('new-password-login-popup').className = 'popup special new-password-login-popup show';
                    document.getElementById('restore-password').className = "popup option-popup";

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

    reGenOtp() {
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
                UserLogin: getSession(USER_LOGIN),
            }

        }
        //console.log(genOTPRequest);
        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    // Alert.success("Gửi lại mã mới OTP thành công!");
                    setSession(TRANSACTION_ID, response.Response.Message);
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
            //this.props.history.push('/maintainence');
        });
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

    componentDidMount() {
        document.addEventListener("keydown", this.closeRestorePassEsc, false);
    }
    componentWillUnmount() {
        document.removeEventListener("keydown", this.closeRestorePassEsc, false);
    }
    closeRestorePassEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeRestorePass();
        }

    }
    closeRestorePass = () => {
        document.getElementById('restore-password').className = "popup option-popup";
    }

    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0});
    }

    render() {

        return (
            <div className="popup option-popup" id="restore-password">
                <div className="popup__card">
                    <div className="optioncard">
                        <p>Quý khách đã có tài khoản với công ty Dai-ichi Life Việt Nam, vui lòng đăng nhập hoặc đặt lại thông tin đăng nhập để sử dụng</p>
                        <div className="btn-wrapper">
                            <button className="btn btn-primary" id="existed" onClick={this.restorePass}>Đặt lại thông tin đăng nhập</button>
                            <button className="btn btn-nobg" id="nonexisted" onClick={this.closeRestorePass}>Đăng nhập</button>
                        </div>
                        <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeRestorePass} /></i>
                    </div>
                </div>
                <div className="popupbg"></div>
                {this.state.showOtp &&
                    <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds}
                               startTimer={this.startTimer} closeOtp={this.closeOtp}
                               errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupVerifyOTP}
                               reGenOtp={this.reGenOtp} length={6} width={'36px'}
                    />
                }
            </div>

        )
    }
}
export default PopupRestorePass;