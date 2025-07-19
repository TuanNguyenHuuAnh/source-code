import React, {Component} from 'react';
import {changePass} from '../util/APIUtils';
import {
    API_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FORGOT_PASSWORD,
    OTP_EMAIL,
    USER_LOGIN
} from '../constants';
import {getDeviceId, getSession, removeAccents, showMessage, validateEmail} from '../util/common';
import iconGreyCheck from '../img/icon/iconGreyCheck.svg';
import iconGreenCheck from '../img/icon/iconGreenCheck.svg';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";

class PopupNewPass extends Component {
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
            showPassword: false,
            showRePassword: false,
        };
        this.handleInputNewPassChange = this.handleInputNewPassChange.bind(this);
        this.handleInputNewRePassChange = this.handleInputNewRePassChange.bind(this);
        this.popupNewPassSubmit = this.popupNewPassSubmit.bind(this);
        this.closeNewPassEsc = this.closeNewPassEsc.bind(this);
        this.getRegisterPasswordValidations = this.getRegisterPasswordValidations.bind(this);
        this.renderValidationCriteria = this.renderValidationCriteria.bind(this);
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
                Action: 'ChangePasswordActiveAccount',
                APIToken: getSession(API_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(USER_LOGIN),
                DeviceId: getDeviceId(),
                OS: '',
                OldPassword: '',
                Password: this.state.password,
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
                    newPassword.value = '';
                    newRePassword.value = '';
                    document.getElementById('new-password-popup').className = "popup special new-password-popup";
                    passNotMatchId.className = 'error-message';
                    labelRePassword.className = '';

                    const popupClass = getSession(FORGOT_PASSWORD) ? 'popup-change-pass-succ' : 'popup-thanks';
                    document.getElementById(popupClass).className = "popup option-popup show";

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
                        disabledConfirm: false,
                    });
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

        this.updateDOMClass('new-password-popup', 'popup special new-password-popup');

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
            showPassword: false,
            showRePassword: false,
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

    render() {
        return (
            <div className="popup special new-password-popup" id="new-password-popup">
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
                                                <input
                                                    value={this.state.password}
                                                    type={this.state.showPassword ? "text" : "password"}
                                                    name="password"
                                                    id="new-passsword"
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
                                                <input
                                                    value={this.state.rePassword}
                                                    type={this.state.showRePassword ? "text" : "password"}
                                                       name="rePassword"
                                                       id="new-re-passsword"
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
                                    <button className={`btn btn-primary ${this.state.disabledConfirm ? 'disabled' : ''}`}
                                            disabled={this.state.disabledConfirm} id="btn-new-pass">Đổi mật khẩu
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

export default PopupNewPass;