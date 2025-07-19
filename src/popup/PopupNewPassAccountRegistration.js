import React, {Component} from 'react';
import {changePass, getPointByClientID, login2, logoutSession, signup} from '../util/APIUtils';
import {
    ACCESS_TOKEN,
    AKTIVOLABS_ID,
    API_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLASSPO,
    CLASSPOMAP,
    CLIENT_ID,
    CLIENT_PROFILE,
    COMPANY_KEY,
    COMPANY_KEY2,
    DCID,
    EXPIRED_MESSAGE,
    FORGOT_PASSWORD,
    FULL_NAME,
    LOGIN_TIME,
    OTP_EMAIL,
    POID,
    POINT,
    PREVIOUS_SCREENS,
    TRANSACTION_ID,
    USER_DEVICE_TOKEN,
    USER_LOGIN,
    USER_NAME,
    ICALLBACK
} from '../constants';
import {
    buildParamsAndRedirect,
    getDeviceId,
    getLinkId,
    getLinkPartner,
    getSession,
    removeAccents,
    roundDown,
    setSession,
    showMessage,
    validateEmail
} from '../util/common';
import iconGreyCheck from '../img/icon/iconGreyCheck.svg';
import iconGreenCheck from '../img/icon/iconGreenCheck.svg';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import AES256 from "aes-everywhere";

class PopupNewPassAccountRegistration extends Component {
    constructor(props) {
        super(props);
        this.state = {
            password: '',
            rePassword: '',
            passwordValidations: [
                {label: 'Ít nhất 8 ký tự', isValid: false},
                {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false},
                {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false},
                {label: 'Có hoa & thường (Vd: A, a, B)', isValid: false},
            ],
            loading: false,
            disabledConfirm: true,
            showPassword: '',
            showRePassword: '',
            showRegister: false,
        };
        this.handleInputNewPassChange = this.handleInputNewPassChange.bind(this);
        this.handleInputNewRePassChange = this.handleInputNewRePassChange.bind(this);
        this.popupNewPassSubmit = this.popupNewPassSubmit.bind(this);
        this.closeNewPassEsc = this.closeNewPassEsc.bind(this);
        this.getRegisterPasswordValidations = this.getRegisterPasswordValidations.bind(this);
        this.renderValidationCriteria = this.renderValidationCriteria.bind(this);
        this.processRegistration = this.processRegistration.bind(this);
    }

    handleInputNewPassChange(event) {
        const target = event.target;
        const inputName = target.name;
        let inputValue = target.value.trim();

        // Validate password and get validation criteria
        const passwordValidations = this.getRegisterPasswordValidations(inputValue);

        inputValue = removeAccents(inputValue);

        const newPasswordInput = document.getElementById('new-passsword');
        if (newPasswordInput) {
            newPasswordInput.value = inputValue;
        }

        this.setState({
            [inputName]: inputValue,
            passwordValidations: passwordValidations,
        });

        // Check if all criteria are met to enable the button
        const isButtonEnabled =
            passwordValidations.every((validation) => validation.isValid) &&
            inputValue === this.state.rePassword;

        // Update button's CSS
        // document.getElementById('btn-new-pass').className = isButtonEnabled ? "btn btn-primary" : "btn btn-primary disabled";
        if (isButtonEnabled) {
            this.setState({
                disabledConfirm: false,
            });
        } else {
            this.setState({
                disabledConfirm: true,
            });
        }

    }

    getRegisterPasswordValidations(value) {
        const validations = [
            {
                label: 'Ít nhất 8 ký tự',
                isValid: value.length >= 8,
            },
            {
                label: 'Có ký tự số (Vd: 1, 2, 3)',
                isValid: /\d/.test(value),
            },
            {
                label: 'Có ký tự đặc biệt (Vd: #, $, %)',
                // isValid: /[\W_]/.test(value),
                isValid: /[^\p{L}\p{N}]/u.test(value),
            },
            {
                label: 'Có hoa & thường (Vd: A, a, B)',
                isValid: /([a-z].*[A-Z])|([A-Z].*[a-z])/.test(value),
            },
        ];

        return validations;
    }

    // Render validation criteria
    renderValidationCriteria() {
        return (
            <ul>
                {this.state.passwordValidations.map((validation, index) => (
                    <div key={index} style={{display: 'flex', alignItems: 'center', marginTop: 8}}>
                        <img src={validation.isValid ? iconGreenCheck : iconGreyCheck}
                             alt={`${validation.isValid ? "iconGreenCheck" : "iconGreyCheck"}`}
                             style={{marginRight: 8}}/>
                        <li
                            style={{color: validation.isValid ? 'green' : '#727272', fontSize: 12, fontWeight: 500}}>
                            {validation.label}
                        </li>
                    </div>
                ))}
            </ul>
        );
    }


    handleInputNewRePassChange(event) {
        const target = event.target;
        const inputName = target.name;
        let inputValue = target.value.trim();

        inputValue = removeAccents(inputValue);

        const newPasswordInput = document.getElementById('new-re-passsword');
        if (newPasswordInput) {
            newPasswordInput.value = inputValue;
        }
        this.setState({
            [inputName]: inputValue,
        });

        // Check if all criteria are met to enable the button
        const isButtonEnabled = inputValue === this.state.password;

        // Update button's CSS
        // document.getElementById('btn-new-pass').className = isButtonEnabled ? "btn btn-primary" : "btn btn-primary disabled";
        if (isButtonEnabled) {
            this.setState({
                disabledConfirm: false,
            });
        } else {
            this.setState({
                disabledConfirm: true,
            });
        }
    }

    processRegistration() {
        this.setState({loading: true});

        console.log("processRegistration");

        const agent = window.btoa(navigator.userAgent);
        const registerRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'RegisterAccountStep2',
                Authentication: AUTHENTICATION,
                APIToken: getSession(API_TOKEN),
                DeviceId: getDeviceId(),
                DeviceToken: agent,
                FullName: getSession(FULL_NAME),
                OS: '',
                Password: AES256.encrypt(this.state.password, COMPANY_KEY2),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME),
            }

        }
        signup(registerRequest)
            .then(response => {
                console.log("signup", response);
                this.setState({loading: false});
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CLIRESSTEP2SUCC') {
                    this.handleLogin();
                } else if (response.Response.ErrLog === 'CLIIDEMPTY' || response.Response.ErrLog === 'PASSWORDEMPTY' || response.Response.ErrLog === 'USERNAMEEXIST' || response.Response.ErrLog === 'CLIRESSTEP2FAIL') {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
                this.setState({loading: false});
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            //this.props.history.push('/maintainence');
        });
    }


    popupNewPassSubmit(event) {
        event.preventDefault();

        const newPassword = document.getElementById('new-passsword');
        const newRePassword = document.getElementById('new-re-passsword');
        const passNotMatchId = document.getElementById('pass-not-match-id');
        const labelRePassword = document.getElementById('label-re-password');

        if (newRePassword.value !== newPassword.value) {
            passNotMatchId.className = 'error-message validate';
            labelRePassword.className = 'lable-red';
            return;
        }

        this.setState({
            loading: true,
            disabledConfirm: true,
        });

        const changePassRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                PhoneNumber: getSession(CELL_PHONE) || '',
                Action: validateEmail(getSession(USER_LOGIN)) ? 'ChangePassword' : 'ChangePasswordActiveAccount',
                APIToken: getSession(API_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(USER_LOGIN),
                DeviceId: getDeviceId(),
                OS: '',
                OldPassword: '',
                // Password: this.state.password,
                Password: AES256.encrypt(this.state.password, COMPANY_KEY2),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }
        };

        if (getSession(OTP_EMAIL)) {
            sessionStorage.removeItem(OTP_EMAIL);
        }

        changePass(changePassRequest)
            .then(response => {
                console.log("changePass", response);

                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGEPASSSUCC') {
                    console.log("changePass-processRegistration", response);

                    this.processRegistration();

                    document.getElementById('new-password-login-popup').className = "popup special new-password-login-popup";
                    passNotMatchId.className = 'error-message';
                    labelRePassword.className = '';
                    newPassword.value = '';
                    newRePassword.value = '';

                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    this.setState({
                        loading: false,
                        disabledConfirm: false,
                    });
                }
            })
            .catch(error => {
                this.setState({
                    loading: false,
                    disabledConfirm: false,
                });
            });
    }

    handleLogin() {
        const loginRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CPLOGIN',
                APIToken: '',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: getSession(USER_DEVICE_TOKEN) || '',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME),
                OS: '',
                Project: 'mcp',
                Password: AES256.encrypt(this.state.password, COMPANY_KEY2),
                RecaptchaToken: "",
                Captcha: "",
                PassEnscr: '1',
            }
        };

        loginRequest.jsonDataInput.Action = 'CPLOGIN';

        login2(loginRequest, false)
            .then(response => {
                console.log('login2', response.Response);
                const clientProfile = response?.Response?.ClientProfile?.[0];
                if (response.Response.Result === 'true' && response.Response.ErrLog.toLowerCase() === 'login successfull' && clientProfile) {
                    document.getElementById('new-password-registration').className = 'popup special new-password-registration';

                    setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                    setSession(CLIENT_ID, clientProfile.ClientID);
                    setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                    setSession(CELL_PHONE, clientProfile.CellPhone);
                    setSession(FULL_NAME, clientProfile.FullName);

                    const additionalFields = [
                        'Gender',
                        'Address',
                        'OtherAddress',
                        'Email',
                        'VerifyCellPhone',
                        'VerifyEmail',
                        'TwoFA',
                        'LinkFaceBook',
                        'LinkGmail',
                        'POID',
                        'DOB',
                        'DCID'
                    ];

                    additionalFields.forEach(field => {
                        if (clientProfile[field]) {
                            setSession(field.toUpperCase(), clientProfile[field]);
                        }
                    });

                    const pointRequest = {
                        jsonDataInput: {
                            Company: COMPANY_KEY,
                            OS: '',
                            APIToken: response.Response.NewAPIToken,
                            Authentication: AUTHENTICATION,
                            ClientID: clientProfile.ClientID,
                            DeviceId: getDeviceId(),
                            Project: 'mcp',
                            UserLogin: getSession(USER_LOGIN),
                        }
                    };

                    getPointByClientID(pointRequest)
                        .then(res => {
                            const {CPGetPointByCLIIDResult: Response} = res;
                            if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                                setSession(CLASSPO, CLASSPOMAP[Response.ClassPO] ? CLASSPOMAP[Response.ClassPO] : Response.ClassPO);
                                setSession(POINT, roundDown(parseFloat(Response.Point) / 1000));
                                this.props.parentCallback(true, clientProfile, Response.Point);
                            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                                showMessage(EXPIRED_MESSAGE);
                                logoutSession();
                            } else {
                                this.props.parentCallback(true, clientProfile, 0);
                            }

                            if (this.props.path) {
                                buildParamsAndRedirect(this.props.path);
                            }
                            getLinkPartner(AKTIVOLABS_ID);
                        })
                        .catch(error => {
                            if (this.props.path) {
                                buildParamsAndRedirect(this.props.path);
                            }
                        });

                    setSession(LOGIN_TIME, new Date().getTime() / 1000);
                    if (getSession(PREVIOUS_SCREENS) && ((getSession(PREVIOUS_SCREENS) === '/song-vui-khoe')) || (getSession(PREVIOUS_SCREENS) === '/home') || (getSession(PREVIOUS_SCREENS) === '/') || getSession(ICALLBACK)) {
						// document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
						getLinkId();
					}
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'CLINOEXIST' || response.Response.ErrLog === 'CLIEXISTNOACTIVE')) {
                    this.setState({
                        showRegister: true,
                    });
                }
            })
            .catch(error => {
                // warning();
            });
    }

    componentDidMount() {
        document.addEventListener("keydown", this.closeNewPassEsc, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.closeNewPassEsc, false);
    }

    closeNewPassEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeNewPass();
        }

    }

    // Hàm để cập nhật class của phần tử DOM
    updateDOMClass = (elementId, className) => {
        document.getElementById(elementId).className = className;
    }

    closeNewPass = () => {
        // Xóa giá trị của các trường nhập mật khẩu mới và nhập lại mật khẩu mới
        document.getElementById("new-passsword").value = '';
        document.getElementById("new-re-passsword").value = '';

        // Đặt lại class cho các phần tử DOM để ẩn thông báo lỗi và đặt lại kiểu nhãn
        this.updateDOMClass('pass-not-match-id', 'error-message');
        this.updateDOMClass('label-re-password', '');
        this.updateDOMClass('new-password-registration', 'popup special new-password-registration');

        // Đặt lại trạng thái của state
        this.setState({
            password: '',
            rePassword: '',
            passwordValidations: [
                {label: 'Ít nhất 8 ký tự', isValid: false},
                {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false},
                {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false},
                {label: 'Có hoa & thường (Vd: A, a, B)', isValid: false},
            ],
            loading: false,
            disabledConfirm: true,
            showPassword: '',
            showRePassword: '',
            showRegister: false,
        });
    }

    handleTogglePassword = () => {
        this.setState((prevState) => ({
            showPassword: !prevState.showPassword,
        }));
    }

    handleToggleRePassword = () => {
        this.setState((prevState) => ({
            showRePassword: !prevState.showRePassword,
        }));
    }

    registerAccount = () => {
        if (getSession(FORGOT_PASSWORD)) {
            sessionStorage.removeItem(FORGOT_PASSWORD);
        }
        if (getSession(TRANSACTION_ID)) {
            sessionStorage.removeItem(TRANSACTION_ID);
        }
        document.getElementById('option-popup').className = "popup option-popup show";
    }

    render() {
        return (
            <div className="popup special new-password-registration" id="new-password-registration">
                <form onSubmit={this.popupNewPassSubmit}>
                    <div className="popup__card">
                        <div className="new-password-card">
                            <div className="header">
                                <p>Mật khẩu đăng nhập</p>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt=""
                                                             onClick={this.closeNewPass}/></i>
                            </div>
                            <div className="body">
                                <p style={{fontSize: 15, fontWeight: 400, lineHeight: '21px', textAlign: 'left'}}>Quý
                                    khách vui lòng thiết lập mật khẩu cho tài khoản.</p>
                                <div className="input-wrapper" style={{marginTop: 12}}>
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend password-input"
                                             id="new-password-input">
                                            <div className="input__content">
                                                <label htmlFor="">Mật khẩu</label>
                                                <input name="password" id="new-passsword"
                                                       value={this.state.password}
                                                       type={this.state.showPassword ? "text" : "password"}
                                                       onChange={this.handleInputNewPassChange} required/>
                                            </div>
                                            <i className="password-toggle" onClick={this.handleTogglePassword}></i>
                                        </div>
                                        {this.renderValidationCriteria()}
                                    </div>
                                    <div className="input-wrapper-item">
                                        <div className="input special-input-extend password-input"
                                             id="new-re-password-input">
                                            <div className="input__content">
                                                <label htmlFor="" id="label-re-password">Xác nhận mật khẩu</label>
                                                <input name="rePassword" id="new-re-passsword"
                                                       value={this.state.rePassword}
                                                       type={this.state.showRePassword ? "text" : "password"}
                                                       onChange={this.handleInputNewRePassChange} required/>
                                            </div>
                                            <i className="password-toggle" onClick={this.handleToggleRePassword}></i>
                                        </div>
                                    </div>
                                    <div className="error-message" id="pass-not-match-id">
                                        <i className="icon">
                                            <img src="img/icon/warning_sign.svg" alt=""/>
                                        </i>
                                        <p>Mật khẩu không trùng khớp</p>
                                    </div>
                                </div>
                                {this.state.loading && <LoadingIndicatorBasic/>}
                                <div className="btn-wrapper">
                                    <button
                                        className={`btn btn-primary ${this.state.disabledConfirm ? 'disabled' : ''}`}
                                        disabled={this.state.disabledConfirm} id="btn-new-pass">Đổi mật khẩu
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </form>
                {this.state.showRegister &&
                    <div className="error-message validate">
                        <i className="icon">
                            <img src="img/icon/warning_sign.svg" alt=""/>
                        </i>
                        <p style={{'lineHeight': '20px'}}>Tài khoản không tồn tại. Vui lòng kiểm tra Tên đăng nhập hoặc
                            bấm vào đây để <span className="light-register"
                                                 onClick={this.registerAccount}>Đăng ký</span></p>
                    </div>
                }
            </div>
        )
    }
}

export default PopupNewPassAccountRegistration;