import React, {Component} from "react";
import iconArrownRightNew from '../img/icon/iconArrownRight_new.svg';
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
    USER_NAME,
    CLIENT_PROFILE,
    VERIFY_EMAIL,
    VERIFY_CELL_PHONE,
    CELL_PHONE,
    OTP_INCORRECT,
    DCID
} from "../constants";
import {checkAccountInfo, genOTP, submitAccountUpdate, verifyOTP} from '../util/APIUtils';
import {
    getDeviceId,
    getSession,
    maskEmail,
    maskPhoneNumber,
    setSession,
    showMessage,
    VALID_EMAIL_REGEX,
    removeAccents,
    maskPhone
} from "../util/common";
import DOTPInput from '../components/DOTPInput';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import iconGreenCheck from "../img/icon/iconGreenCheck.svg";
import iconGreyCheck from "../img/icon/iconGreyCheck.svg";
import ChangeUserNamePopup from "./ChangeUserNamePopup"
import { event } from "react-ga";
import ChangePasswordPopup from "./ChangePasswordPopup";

class ChangeUserNameAndPassword extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userName : "",
            changeUserNamePopup: false,
            changePasswordPopup: false,
            loading : false,
            disabledConfirm : true,
            password: '',
            rePassword: '',
            showPassword: false, 
            showRePassword: false, 
            showRegister: false,
            willChangeUserName : false,
            willChangePassword : false,
            checkOtpPhoneChange : false,
            checkOtpEmailChange : false,
            TransactionId: '',
            minutes: 0,
            seconds: 0,
            errorMessage: '',


            userNameValidations : [{ label: 'Tên đăng nhập bao gồm từ 5 đến 30 ký tự.', isValid: false},
                                    {label: 'Tên đăng nhập viết liền không dấu và không phải là một dãy số.', isValid: false}],

            passwordValidations: [{label: 'Ít nhất 8 ký tự', isValid: false}, 
                                {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false},
                                {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false}, 
                                {label: 'Có hoa & thường (Vd: A, a, B)', isValid: false}],
        };
    };

    genOtpChange = () => {
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTPV2',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DCID : getSession(DCID),
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: (getSession(VERIFY_CELL_PHONE) === '1') ? 'VALID_OTP_PHONE' : 'VALID_OTP_EMAIL',
                OS: '',
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }
        }
        //console.log(genOTPRequest);
        genOTP(genOTPRequest)
          .then(resGen => {
            if (resGen.Response.Result === 'true' && resGen.Response.ErrLog === 'SUCCESSFUL') {
                let {ChannelOTP} = resGen.Response;
                if (ChannelOTP?.PhoneOTP === 1) {
                  this.setState({ checkOtpPhoneChange: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
                } else {
                  this.setState({ checkOtpEmailChange: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
                }
                this.startTimer();
              //this.setState({checkOtp: true});
            } else if (resGen.Response.NewAPIToken === 'invalidtoken' || resGen.Response.ErrLog === 'APIToken is invalid') {
              showMessage(EXPIRED_MESSAGE);
    
            } else if (resGen.Response.ErrLog === 'OTP Exceed' || resGen.Response.ErrLog === 'OTPLOCK') {
              document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (resGen.Response.ErrLog === 'OTP Wrong 3 times') {
              document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else {
              document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }
          }).catch(error => {
            //this.props.history.push('/maintainence');
          });
    }

    popupAccOtpSubmit = (OTP) => {
        const verifyOTPRequest = {
          jsonDataInput: {
            Company: COMPANY_KEY,
            Action: 'CheckOTP',
            APIToken: getSession(ACCESS_TOKEN),
            Authentication: AUTHENTICATION,
            ClientID: getSession(CLIENT_ID),//this.state.POID
            DeviceId: getDeviceId(),
            Note: this.state.checkOtpPhoneChange ? 'VALID_OTP_PHONE' : 'VALID_OTP_EMAIL',
            OS: '',
            OTP: OTP,
            Project: 'mcp',
            TransactionID: this.state.TransactionId,
            UserLogin: getSession(USER_LOGIN)
          }
        }
    
        verifyOTP(verifyOTPRequest)
          .then(response => {
            if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
              if (this.state.willChangeUserName === true) {
                this.setState({ changeUserNamePopup : true, checkOtpPhoneChange: false, minutes: 0, seconds: 0 });
              } else if (this.state.willChangePassword === true) {
                this.setState({ changePasswordPopup : true, checkOtpEmailChange: false, minutes: 0, seconds: 0 });
              }

            } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
              showMessage(EXPIRED_MESSAGE);
            } else if (response.Response.ErrLog === 'OTPEXPIRY') {
              this.setState({ errorMessage: OTP_EXPIRED });
            }
            else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
              this.setState({ checkOtpPhoneChange: false, checkOtpEmailChange: false, minutes: 0, seconds: 0 });
              document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            }
            else {
              this.setState({ errorMessage: OTP_INCORRECT });
            }
          }).catch(error => {
            //this.props.history.push('/maintainence');
          });
    }

    closeAccOtpPopup = () => {
        this.setState({ willChangeUserName : false, 
                        willChangePassword : false, 
                        checkOtpPhoneChange: false, 
                        checkOtpEmailChange: false, minutes: 0, seconds: 0 });
    }

    onClickChangeUserNamePopup() {
        this.setState({willChangeUserName : true});
        this.genOtpChange();
    };

    onClickPasswordPopup() {
        this.setState({willChangePassword : true})
        this.genOtpChange();
    };

    closePopbtn = () => {
        this.setState({changeUserNamePopup : false , 
                    changePasswordPopup : false,
                    checkOtpPhoneChange: false, 
                    checkOtpEmailChange: false,
                    willChangeUserName : false, 
                    willChangePassword : false,
                    userName : getSession(USER_NAME)});
    };

    successRoute = () => {
        this.props.success();
    }

    //Handle OTP start
    startTimer = () => {
        let myInterval = setInterval(() => {
        if (this.state.seconds > 0) {
            this.setState({ seconds: this.state.seconds - 1 });
        }
        else if (this.state.seconds === 0) {
            if (this.state.minutes === 0) {
            clearInterval(myInterval)
            } else {
            this.setState({ minutes: this.state.minutes - 1, seconds: 59 });
            }
        }
        }, 1000)
        return () => {
        clearInterval(myInterval);
        };
    }

    componentDidMount() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
        const ClientInfo = JSON.parse(getSession(CLIENT_PROFILE))[0];
        const userName = ClientInfo.UserName;
        setSession(USER_NAME, userName);
        this.setState({userName : userName});
    };

    render() {
        const jsonState = this.state;
        return (
            <>
            <div className="form">
                <div className="form__item form__item-header">
                    <div className="form__item above">
                        <p className="basic-black">Đổi tên đăng nhập <br></br> và mật khẩu</p>
                        <a className="simple-brown2" style={{alignContent : 'center'}} onClick={() => this.props.close()}>Quay lại</a>
                    </div>
                    <div className="form__item" style={{ borderTop: '1px solid #e6e6e6' }}>
                        <p style={{ fontSize: '1.6rem', flexGrow : '1' }}>Tên đăng nhập</p>
                        <p style={{ fontSize: '1.6rem', alignItems: 'center', cursor: 'pointer', flexGrow : '0', overflow : 'hidden', textOverflow : 'ellipsis' }} onClick={() => this.onClickChangeUserNamePopup()}>
                            {jsonState.userName ? jsonState.userName : '---'}
                        </p>
                        <img src={iconArrownRightNew} alt="iconArrownRightNew" style={{ marginLeft: 4 }}/>
                    </div>
                    <div className="form__item" style={{ borderTop: '1px solid #e6e6e6' }}>
                        <p style={{ fontSize: '1.6rem' }}>Mật khẩu</p>
                        <p style={{ fontSize: '1.6rem', display: 'flex', alignItems: 'center', cursor: 'pointer' }} onClick={() => this.onClickPasswordPopup()}>
                            {/* {jsonState.orgPhone ? jsonState.orgPhone : '---'} */}
                            ********
                            <img src={iconArrownRightNew} alt="iconArrownRightNew" style={{ marginLeft: 4 }}/>
                        </p>
                    </div>
                </div>
            </div>
            <div style={{height : 'calc(100vh - 480px)'}}></div>

            {this.state.changeUserNamePopup === true && <ChangeUserNamePopup closePopbtn = {this.closePopbtn} successRoute = {this.successRoute}/>}
            {this.state.changePasswordPopup === true && <ChangePasswordPopup closePopbtn = {this.closePopbtn} successRoute = {this.successRoute}/>}
            {this.state.checkOtpPhoneChange === true && <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer} closeOtp={this.closeAccOtpPopup} errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupAccOtpSubmit} reGenOtp={this.reGenOtp} maskPhone={maskPhone(getSession(CELL_PHONE))} />}
            {this.state.checkOtpEmailChange === true && <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer} closeOtp={this.closeAccOtpPopup} errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupAccOtpSubmit} reGenOtp={this.reGenOtp} maskEmail={maskEmail(getSession(EMAIL))} />}
            </>
        )
    };
}
export default ChangeUserNameAndPassword;