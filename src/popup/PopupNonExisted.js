import React, {Component, useEffect} from 'react';
import {genOTP, signup} from '../util/APIUtils';
import {
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    COMPANY_KEY,
    COMPANY_KEY2,
    DEFAULT_PASS,
    EMAIL,
    EXPIRED_MESSAGE,
    FULL_NAME,
    TRANSACTION_ID,
    USER_LOGIN,
    USER_RULE_AGREE,
    FORGOT_PASSWORD,
    FORGOT_PASSWORD_NEW,
    ACCOUNT_STATUS,
    FORGOT_PASSWORD_POTENTIAL,
    FORGOT_PASSWORD_PO
} from '../constants';
import {getDeviceId, getSession, maskEmail, maskPhoneNumber, setSession, showMessage} from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import AES256 from 'aes-everywhere';
import {withRouter} from 'react-router-dom';
import './PopupNonExisted.css';
import SignUpFlowModalPotential from "./AccountManagementFlow/SignUp/SignUpFlowModalPotential";
import ClosePopupConfirm from '../components/ClosePopupConfirm';

class PopupNonExisted extends Component {
    constructor(props) {
        super(props);
        this.state = {
            contact: '',
            fullname: '',
            userRuleAgrred: this.props.ruleChecked,
            loading: false,
            isPhone: false,
            isValidPhone: false,
            isValidEmail: false,
            isDisabled: true,
            errMsg: '',
            errPhoneEmail: '',
            errFullNameMsg: '',
            errEmailMsg: '',
            errPhoneMsg: '',
            minutes: 0,
            seconds: 0,
            transactionId: '',
            showSignUpModal: false,
            isValidFullName: false,
            countdown: 30 * 60, // 30 minutes in seconds,
            isOpenPopup: true
        };
        this.handleInputContactChange = this.handleInputContactChange.bind(this);
        this.handleInputFullNameChange = this.handleInputFullNameChange.bind(this);
        this.popupNonExistedSubmit = this.popupNonExistedSubmit.bind(this);
        this.callBackSuccessful = this.callBackSuccessful.bind(this);
        this.resetStateSignUp = this.resetStateSignUp.bind(this);
        this.generationOTP = this.generationOTP.bind(this);
        this.handleBeforeUnload = this.handleBeforeUnload.bind(this);
        this.handleShowCloseConfirm = this.showCloseConfirm.bind(this);
        this.handleCloseAllPopup = this.closeAllPopup.bind(this);
        this.handleCloseConfirmPopup = this.closeConfirmPopup.bind(this);
        this.handleClearState = this.clearState.bind(this);
    }

    componentDidMount(){
        window.addEventListener('beforeunload', this.handleBeforeUnload);
    }

    componentWillUnmount(){
        window.removeEventListener('beforeunload', this.handleBeforeUnload);
    }
    handleBeforeUnload(e){
        const hasData = this.state.fullname !== '' || this.state.contact !==''
        console.log(this.state.fullname, this.state.contact, hasData)
        if(hasData){
            e.preventDefault();
            e.returnValue = '';
        }
    }

    handleInputFullNameChange(event) {
        const {name, value} = event.target;
        const inputValue = value.trim();

        // Kiểm tra ít nhất hai từ trong Họ và tên
        const nameWords = inputValue.split(' ');
        // if (nameWords.length < 2) {
        //     return; // Nếu ít hơn hai từ, không cập nhật state và kết thúc hàm
        // }

        // Kiểm tra fullName không chứa ký tự đặc biệt
        const isValidInput = nameWords.length >= 2 && /^[\p{L}\s]*$/u.test(inputValue);
        const errFullNameMsg = isValidInput ? '' : '<p style="font-size: 13px; color: #DE181F; line-height: 15px; margin-top: 12px; margin-bottom: 12px;">Họ và tên phải bao gồm ít nhất hai từ và không được chứa ký tự đặc biệt hay ký tự số.</p>';

        this.setState({
            [name]: inputValue,
            errFullNameMsg: errFullNameMsg,
            isDisabled: inputValue === '' || !this.state.contact || !isValidInput || !(this.state.isValidEmail || this.state.isValidPhone),
            errMsg: '',
            isValidFullName: isValidInput
        });

        document.getElementById('non-existed-fullname-input').className = isValidInput ? 'input special-input-extend' : 'input special-input-extend validate';

    }


    handleInputContactChange(event) {
        const {name, value} = event.target;
        const inputValue = value.trim();

        const phoneInput = /^\d+$/.test(inputValue);
        
        let isValidPhone = true;
        let isValidEmail = true;
        let isPhone = false;

        if (inputValue) {
            if (phoneInput) {
                isValidPhone = /^0\d{9}$/.test(inputValue);
                isValidEmail = true;
                isPhone = true;
            } else {
                isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(inputValue);
                isValidPhone = true;
            }
            
            

    
        }

        const {fullname} = this.state;
        const isValidInput = (inputValue !== '' && ((isValidPhone && phoneInput)|| (isValidEmail && !phoneInput)))? true : false;

        this.setState({
            [name]: inputValue,
            isValidPhone,
            isValidEmail,
            isPhone,
            // errFullNameMsg: '',
            errPhoneMsg: isValidPhone ? '' : '<p style="font-size: 13px; color: #DE181F; line-height: 15px; margin-top: 12px; margin-bottom: 12px;">Số điện thoại phải có 10 chữ số và bắt đầu bằng chữ số 0. Quý khách vui lòng kiểm tra lại.</p>',
            errEmailMsg: isValidEmail ? '' : '<p style="font-size: 13px; color: #DE181F; line-height: 15px; margin-top: 12px; margin-bottom: 12px;">Email chưa đúng cấu trúc (vd: diachi@email.vn). Quý khách vui lòng kiểm tra lại.</p>',
            isDisabled: !isValidInput || !this.state.fullname || !this.state.isValidFullName,
            //errMsg: isValidInput ? '' : '<p style="font-size: 13px; color: #ffffff; line-height: 19px; font-weight: : 500">Địa chỉ email/Số điện thoại không đúng định dạng. Quý khách vui lòng thử lại.</p>',
        });

        setSession(FULL_NAME, fullname);
        if (isValidEmail) {
            setSession(EMAIL, value);
        }
        if (isValidPhone) {
            setSession(CELL_PHONE, value);
        }
        document.getElementById('contact-info').className = isValidInput ? 'input special-input-extend' : 'input special-input-extend validate';
    }


    popupNonExistedSubmit(event) {
        event.preventDefault();

        const {fullname, password, contact} = this.state;

        this.setState({loading: true});

        if (getSession(USER_RULE_AGREE) !== null) {
            sessionStorage.removeItem(USER_RULE_AGREE);
        }

        const agent = window.btoa(navigator.userAgent);
        const registerRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'RegisterAccountStep1',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: agent,
                FullName: fullname,
                OS: '',
                Password: AES256.encrypt(password, COMPANY_KEY2),
                Project: 'mcp',
                UserLogin: contact
            }
        };

        signup(registerRequest)
            .then(response => {
                this.setState({loading: false});

                const errMsgMap = {
                    'CLIRESSTEP1SUCC': () => {
                        setSession(USER_LOGIN, contact);
                        setSession(DEFAULT_PASS, password);
                        this.setState({
                            showSignUpModal: true,
                            minutes: 5,
                            seconds: 0,
                            loading: false,
                            isDisabled: true,
                            transactionId: response.Response.Message,
                        });
                        this.startTimer();
                        if (this.state.errMsg) {
                            document.getElementById('email-exist-id').className = 'error-message';
                        }

                        document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted";
                        this.props.callbackStartTimer();
                        this.props.parentCallback(false);
                    }, 'CLIEXIST': () => {
                        setSession(USER_LOGIN, contact);
                        this.props.callbackSetEmail(contact);
                        this.closeSignUpPopupNoExisted();
                        document.getElementById('popup-ask-forgot-pass').className = "popup option-popup show";
                    }, 'PHONEEXIST': () => {
                        document.getElementById('contact-info').className = 'input special-input-extend validate';
                        this.setState({
                            errMsg: '<p style="line-height: 21px;">Số điện thoại này đã được sử dụng để đăng ký. Quý khách vui lòng thay đổi hoặc sử dụng tính năng <span style="font-weight: 700; color: #ffffff">Quên thông tin đăng nhập</span> để tiếp tục.</p>',
                            errPhoneEmail: 'Số điện thoại'
                        });
                    }, 'EMAILEXIST': () => {
                        document.getElementById('contact-info').className = 'input special-input-extend validate';
                        this.setState({
                            errMsg: '<p style="line-height: 21px;">Địa chỉ email này đã được sử dụng để đăng ký. Quý khách vui lòng thay đổi hoặc sử dụng tính năng <span style="font-weight: 700; color: #ffffff">Quên thông tin đăng nhập</span> để tiếp tục.</p>',
                            errPhoneEmail: 'Địa chỉ email'
                        });
                    }, 'OTP Wrong 3 times': () => {
                        document.getElementById('contact-info').className = 'input special-input-extend validate';
                        this.setState({
                            errMsg: '<p style="line-height: 21px;">Mã OTP đã nhập sai quá 5 lần. Chức năng tạm ngừng hoạt động trong 30 phút.</p>',
                        });
                    }
                };

                const handleResponse = errMsgMap[response.Response.ErrLog];
                if (handleResponse) handleResponse();
            })
            .catch(error => {
                //this.props.history.push('/maintainence');
            });
    }

    startTimer() {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            if (this.state.seconds === 0) {
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

    resetState() {
        const popupNonExisted = document.getElementById('signup-popup-nonexisted');
        const fullNameInput = document.getElementById('non-existed-fullname');
        const contactInput = document.getElementById('non-existed-contact');

        if (popupNonExisted) {
            popupNonExisted.className = "popup special signup-popup-nonexisted";
        }

        if (fullNameInput) {
            fullNameInput.value = '';
        }

        if (contactInput) {
            contactInput.value = '';
        }

        this.props.callbackShowLogin(true);

        this.setState({
            contact: '',
            fullname: '',
            userRuleAgrred: this.props.ruleChecked,
            loading: false,
            isValidPhone: false,
            isValidEmail: false,
            isDisabled: true,
            errMsg: '',
            errFullNameMsg: '',
            showSignUpModal: false,
            minutes: 0,
            seconds: 0,
            transactionId: '',
        });

        this.props.parentCallback(false);
    }

    clearState() {
        this.setState({
            minutes: 0,
            seconds: 0,
            isOpenPopup: false
        });
    }

    showCloseConfirm() {
        this.setState({closeConfirm: true, isOpenPopup: false});
    }

    closeConfirmPopup() {
        this.setState({closeConfirm: false, isOpenPopup: true});
    }

    closeAllPopup() {
        // this.resetState();
        window.location.href = '/';
    }

    generationOTP() {
        //gen otp
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTP',
                Authentication: AUTHENTICATION,
                CellPhone: getSession(CELL_PHONE)?getSession(CELL_PHONE):'',
                Email: getSession(EMAIL)?getSession(EMAIL):'',
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
                        showSignUpModal: true,
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

    resetFormAndShowPopup() {
        const popupNonExisted = document.getElementById('signup-popup-nonexisted');
        const newPasswordRegistration = document.getElementById('new-password-registration');

        if (popupNonExisted) {
            popupNonExisted.className = "popup special signup-popup-nonexisted";
            popupNonExisted.className = "popup special otp-popup";
        }

        if (newPasswordRegistration) {
            newPasswordRegistration.className = 'popup special new-password-registration show';
        }

        this.resetState();
    }

    callBackSuccessful() {
        this.props.parentCallback(true);
        this.resetStateSignUp();
        this.resetState();
    }

    resetStateSignUp() {
        const popupNonExisted = document.getElementById('signup-popup-nonexisted');

        if (popupNonExisted) {
            popupNonExisted.className = "popup special signup-popup-nonexisted";
            popupNonExisted.className = "popup special otp-popup";
        }
    }

    closeSignUpPopupNoExisted = () => {
        this.resetState();
    }

    showUserRule = () => {
        // document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted";
        window.open('/terms-of-use', '_blank');
    }

    showUserPolicy = () => {
        // document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted";
        window.open('/privacy-policy', '_blank');
    }

    render() {
        const forgotPassword = (event) => {
            event.preventDefault();
            // this.props.callbackShowLogin(false);
            setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
            setSession(ACCOUNT_STATUS, FORGOT_PASSWORD_NEW);
            if (document.getElementById('option-popup')) {
                document.getElementById('option-popup').className = "popup option-popup show";
            }
            if (document.getElementById('signup-popup-nonexisted')) {
                document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted";
            }
        }

        const noHavePolicy = () => {
            setSession(FORGOT_PASSWORD_POTENTIAL);
            sessionStorage.removeItem(FORGOT_PASSWORD_PO);
            if (document.getElementById('popup_forget-pass')) {
                document.getElementById('popup_forget-pass').className = "popup popup_forget-pass show";
            }
            if (document.getElementById('signup-popup-nonexisted')) {
                document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted";
            }
        }

        return (<div className="popup special signup-popup-nonexisted" id="signup-popup-nonexisted">
            <form onSubmit={this.popupNonExistedSubmit}>
                <div className="popup__card">
                    <div className="popup-nonexisted-card">
                        <div className="header">
                            <p>Đăng ký</p>
                            <i className="closebtn"><img src="img/icon/close.svg" alt=""
                                                         onClick={this.closeSignUpPopupNoExisted}/></i>
                        </div>
                        <div className="body">
                            {this.state.errMsg && <div className="error-message validate" id="email-exist-id">
                                <i className="icon">
                                    <img src="../img/icon/warning_sign.svg" alt=""/>
                                </i>

                                {this.state.errPhoneEmail? (
                                    <p style={{lineHeight: '21px'}}>{this.state.errPhoneEmail} này đã được sử dụng để đăng ký. Quý khách vui lòng thay đổi hoặc sử dụng tính năng <span style={{fontWeight: '700', color: '#ffffff', cursor: 'pointer'}} onClick={(event) => noHavePolicy(event)}>Quên thông tin đăng nhập</span> để tiếp tục.</p>
                                ): (
                                    <div dangerouslySetInnerHTML={{__html: this.state.errMsg}}/> 
                                )}
                                
                                

                            </div>}
                            <div className="input-wrapper">
                                <div className="input-wrapper-item">
                                    <div className="input special-input-extend" id="non-existed-fullname-input">
                                        <div className="input__content">
                                            <label htmlFor="">Họ và tên</label>
                                            <input id="non-existed-fullname" name="fullname"
                                                   onChange={this.handleInputFullNameChange} required/>
                                        </div>
                                        <i><img src="img/icon/edit.svg" alt=""/></i>
                                    </div>
                                </div>
                                {this.state.errFullNameMsg &&
                                    <div dangerouslySetInnerHTML={{__html: this.state.errFullNameMsg}}/>}

                                <div className="input-wrapper-item">
                                    <div className="input special-input-extend" id="contact-info">
                                        <div className="input__content">
                                            <label htmlFor="">Email/Số điện thoại</label>
                                            <input type="search" id="non-existed-contact" name="contact"
                                                   onChange={this.handleInputContactChange} required/>
                                        </div>
                                        <i><img src="img/icon/edit.svg" alt=""/></i>
                                    </div>
                                </div>
                                {this.state.errEmailMsg &&
                                    <div dangerouslySetInnerHTML={{__html: this.state.errEmailMsg}}/>}
                                {this.state.errPhoneMsg &&
                                    <div dangerouslySetInnerHTML={{__html: this.state.errPhoneMsg}}/>}
                            </div>
                            {this.state.loading ? (<LoadingIndicator area="signup-area"/>) : (
                                <div className="btn-wrapper" style={{marginTop: 16}}>
                                    <button
                                        className={`btn btn-primary ${this.state.isDisabled ? 'disabled' : ''} `}
                                        id="btn-non-exixted" disabled={this.state.isDisabled}>Tiếp tục
                                    </button>
                                </div>)}
                            <p className="terms-of-use-content">Bằng việc chọn “Tiếp tục”, bạn đã đồng ý với <span
                                className="terms-of-use-brown" onClick={this.showUserRule}>
                                    Thỏa thuận sử dụng ứng dụng
                                </span> & <span
                                className="terms-of-use-brown" onClick={this.showUserPolicy}>
                                    Chính sách bảo mật thông tin
                                </span> của Dai-ichi Life Việt Nam.</p>
                            <p className="popup-registration-content">Bạn đã có tài khoản? <span
                                className="popup-registration-login"
                                onClick={this.closeSignUpPopupNoExisted}>Đăng nhập</span>
                            </p>


                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </form>
            {this.state.showSignUpModal && <SignUpFlowModalPotential
                fullname={this.state.fullname}
                seconds={this.state.seconds}
                minutes={this.state.minutes}
                transactionId={this.state.transactionId}
                maskEmail={!this.state.isPhone ? maskEmail(this.state.contact) : null}
                maskPhone={this.state.isPhone ? maskPhoneNumber(this.state.contact) : null}
                parentPath={this.props.path}
                isOpenPopup={this.state.isOpenPopup}
                callBackSuccessful={this.callBackSuccessful}
                resetState={this.resetStateSignUp}
                handleGenOTP={this.generationOTP}
                clearState={this.handleClearState}
                showCloseConfirm={()=>this.handleShowCloseConfirm}
            />}
            {this.state.closeConfirm && <ClosePopupConfirm closePopup={this.handleCloseConfirmPopup} closeAllPopup = {this.handleCloseAllPopup}/>}
        </div>)
    }
}

export default withRouter(PopupNonExisted);