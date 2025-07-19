import React, { Component } from 'react';
import { verifyOTP, genOTP, logoutSession } from '../util/APIUtils';
import { USER_LOGIN, CLIENT_ID, ACCESS_TOKEN, TRANSACTION_ID, CELL_PHONE, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION } from '../constants';
import '../common/Common.css';
import LoadingIndicator from '../common/LoadingIndicator2';
import {showMessage, setSession, getSession, getDeviceId} from '../util/common';

class PopupVerifyOtp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            OTP: ''
        };
        this.handleInputOtpChange = this.handleInputOtpChange.bind(this);
        this.popupOtpSubmit = this.popupOtpSubmit.bind(this);
        this.closeOtpPopupEsc = this.closeOtpPopupEsc.bind(this);

    }

    handleInputOtpChange(event) {

        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        this.setState({
            [inputName]: inputValue
        });
        if (inputValue !== '') {
            document.getElementById('btn-verify-otp').className = "btn btn-primary";
        } else {
            document.getElementById('btn-verify-otp').className = "btn btn-primary disabled";
        }
    }
    popupOtpSubmit(event) {
        event.preventDefault();
        if (document.getElementById('btn-verify-otp').className === "btn btn-primary disabled") {
            return;
        }
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),//this.state.POID
                DeviceId: getDeviceId(),
                Note: 'VALID_OTP_PHONE',
                OS: '',
                OTP: this.state.OTP,
                Project: 'mcp',
                TransactionID: getSession(TRANSACTION_ID),
                UserLogin: getSession(USER_LOGIN)
            }

        }

        verifyOTP(verifyOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    //Alert.success("Verify OTP success!");
                    document.getElementById('error-message-phone-expire-id').className = 'error-message';
                    document.getElementById('error-message-phone-id').className = 'error-message';
                    document.getElementById('otp-phone-id').value = '';
                    document.getElementById('otp-popup').className = "popup special otp-popup";
                    document.getElementById('new-password-popup').className = "popup special new-password-popup show";
                    this.props.callbackResetTimer();
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                }
                else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    document.getElementById('error-message-phone-expire-id').className = 'error-message validate';
                    document.getElementById('error-message-phone-id').className = 'error-message';
                }
                else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('otp-popup').className = "popup special otp-popup";
                    document.getElementById('error-message-phone-id').className = 'error-message';
                    document.getElementById('error-message-phone-expire-id').className = 'error-message';
                    document.getElementById('otp-phone-id').value = '';
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPINCORRECT') {
                    document.getElementById('error-message-phone-id').className = 'error-message validate';
                    document.getElementById('error-message-phone-expire-id').className = 'error-message';
                }
                else {
                    document.getElementById('error-message-phone-id').className = 'error-message validate';
                    document.getElementById('error-message-phone-expire-id').className = 'error-message';
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
        document.getElementById('error-message-phone-expire-id').className = 'error-message';
        document.getElementById('error-message-phone-id').className = 'error-message';
        document.getElementById('otp-phone-id').value = '';
        document.getElementById('otp-popup').className = "popup special otp-popup";
        this.props.callbackResetTimer();
    }
    render() {
        const reGenOtp = () => {
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
        return (
            <div className="popup special otp-popup" id="otp-popup">
                <form onSubmit={this.popupOtpSubmit}>
                    <div className="popup__card">
                        <div className="popup-otp-card">
                            <div className="header">
                                <p>Nhập mã OTP</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeOtpPopup} /></i>
                            </div>
                            <div className="body">
                                <div className="error-message" id="error-message-phone-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt="" />
                                    </i>
                                    <p>Mã OTP không đúng, Quý khách vui lòng nhập lại.</p>
                                </div>
                                <div className="error-message" id="error-message-phone-expire-id">
                                    <i className="icon">
                                        <img src="../img/icon/warning_sign.svg" alt="" />
                                    </i>
                                    <p>Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.</p>
                                </div>
                                <p className="basic-text2 bigheight">
                                    Mã <span className="basic-text2 basic-bold">OTP</span> đã được gửi đến
                                    <span className="basic-text2 basic-bold">&nbsp;Số điện thoại</span> của Quý khách. Vui lòng nhập
                                    <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác nhận.
                                </p>
                                <div className="input-wrapper">
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend">
                                            <div className="input__content">
                                                <label className="--" htmlFor="">Mã OTP</label>
                                                <input type="search" name="OTP" id="otp-phone-id" onChange={this.handleInputOtpChange} required />
                                            </div>
                                            <i><img src="../img/icon/edit.svg" alt="" /></i>
                                        </div>
                                    </div>
                                </div>
                                {this.props.minutes === 0 && this.props.seconds === 0
                                    ? (
                                        <p className="tag">Bạn chưa nhận được mã OTP? <span className="simple-brown" onClick={() => reGenOtp()}>Gửi lại mã mới</span></p>
                                    ) : (<p className="tag padding-clock"><i className="sand-clock"><img src="../img/icon/sand_clock.svg" />{this.props.minutes}:{this.props.seconds < 10 ? `0${this.props.seconds}` : this.props.seconds}</i></p>)
                                }
                                <LoadingIndicator area="verify-otp" />
                                <div className="btn-wrapper">
                                    <button className="btn btn-primary disabled" id="btn-verify-otp">Xác nhận</button>
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
export default PopupVerifyOtp;