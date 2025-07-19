import iconGreenCheck from "../img/icon/iconGreenCheck.svg";
import iconGreyCheck from "../img/icon/iconGreyCheck.svg";
import React, {Component} from "react";
import {
    removeAccents,
    getSession,
    getDeviceId,
    showMessage,
} from "../util/common";
import {
    USER_NAME,
    COMPANY_KEY,
    API_TOKEN,
    AUTHENTICATION,
    USER_LOGIN,
    ACCESS_TOKEN,
    EXPIRED_MESSAGE
} from '../constants';
import { changePass } from "../util/APIUtils";
import SuccessPopup from './SuccessPopup';



class ChangePasswordPopup extends Component {
    constructor(props) {
        super(props);
        this.state = {

            changePasswordPopup: false,
            loading : false,
            disabledConfirm : true,
            password: '',
            rePassword: '',
            showPassword: false, 
            showRePassword: false, 
            showRegister: false,
            openSuccessfulAccount: false,

            passwordValidations: [{label: 'Ít nhất 8 ký tự', isValid: false}, 
                                {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false},
                                {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false}, 
                                {label: 'Có hoa & thường (Vd: A, a, B)', isValid: false}],
        };

    };

    closePopup = () => {
        this.props.closePopbtn();
    };

    callBackSuccessRoute = () => {
        this.props.successRoute();
    }

    handleInputNewPassChange = (event) => {
        const {name, value} = event.target;
        const inputName = name.trim();
        let inputValue = value.trim();

        // Validate password and get validation criteria
        const passwordValidations = this.getRegisterPasswordValidations(inputValue);

        inputValue = removeAccents(inputValue);

        this.setState({
            password : inputValue,
            passwordValidations: passwordValidations
        });

        // Check if all criteria are met to enable the button
        const isButtonEnabled = passwordValidations.every((validation) => validation.isValid) && inputValue === this.state.rePassword;

        this.setState({disabledConfirm : !isButtonEnabled});
    };

    handleInputNewRePassChange = (event) => {
        const {inputName, value} = event.target;
        let inputValue = value.trim();

        // Remove accents
        inputValue = removeAccents(inputValue);

        // Set input value to new password input
        const newPasswordInput = document.getElementById('new-re-passsword');
        if (newPasswordInput) {
            newPasswordInput.value = inputValue;
        }

        // Update state with new input value
        this.setState({ rePassword: inputValue});

        // Check if all criteria are met to enable the button
        const isButtonEnabled = inputValue === this.state.password;

        this.setState({disabledConfirm : !isButtonEnabled});
    };

    getRegisterPasswordValidations = (value) => {
        return [{
            label: 'Ít nhất 8 ký tự', isValid: value.length >= 8
        }, {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: /\d/.test(value)}, {
            label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: /[^\p{L}\p{N}]/u.test(value)
        }, {label: 'Có hoa & thường (Vd: A, a, B)', isValid: /([a-z].*[A-Z])|([A-Z].*[a-z])/.test(value)},];
    };

    handleTogglePassword = () => {
        this.setState({showPassword : !this.state.showPassword});
    }

    handleToggleRePassword = () => {
        this.setState({showRePassword : !this.state.showRePassword});
    }

    handleChangePassword = () => {
        const newPassword = document.getElementById('new-passsword');
        const newRePassword = document.getElementById('new-re-passsword');
        const passNotMatchId = document.getElementById('pass-not-match-id');
        const labelRePassword = document.getElementById('label-re-password');

        if (newRePassword.value !== newPassword.value) {
            passNotMatchId.className = 'error-message validate';
            labelRePassword.className = 'lable-red';
            return;
        }

        this.setState({loading : true , disabledConfirm : true})
        const currentUserName = this.props.currentUserName;
        const changePassRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'AccountChangeUserName',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: '',
                OldPassword: "",
                Password: this.state.password, // Password: AES256.encrypt(state.password, COMPANY_KEY2),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: currentUserName,
                NewUserName: currentUserName,
                Note: ""
            }
        };

        changePass(changePassRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                    this.setState({loading : false});
                    passNotMatchId.className = 'error-message';
                    labelRePassword.className = '';
                    newPassword.value = '';
                    newRePassword.value = '';
                    this.setState({openSuccessfulAccount : true});

                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    this.setState({loading : false , disabledConfirm : false});
                    this.closePopup();
                }
            })
            .catch(error => {
                this.setState({loading : false , disabledConfirm : false});
                this.closePopup();
            });
    }

    renderValidationPasswordCriteria = () => {
        return (<ul>
            {this.state.passwordValidations.map((validation, index) => (
                <div key={index} style={{display: 'flex', alignItems: 'center', marginTop: 8}}>
                    <img src={validation.isValid ? iconGreenCheck : iconGreyCheck}
                         alt={`${validation.isValid ? "iconGreenCheck" : "iconGreyCheck"}`}
                         style={{marginRight: 8}}/>
                    <li
                        style={{color: validation.isValid ? 'green' : '#727272', fontSize: 12, fontWeight: 500}}>
                        {validation.label}
                    </li>
                </div>))}
        </ul>);
    };


    render(){
        return( <>
        <div className="signupFlowModal__overlay">
            <div className="signupFlowModal__container" style={{position : 'relative'}}>

                <div className="close-btn">
                    <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn" style={{
                        position : 'absolute',
                        right : '1.8rem',
                        top : '1.8rem'
                    }}
                        onClick={() => this.closePopup()}/>
                </div>

                <div className="signupFlowModal__header">
                    <div className="signupFlowModal__title">
                        Mật khẩu đăng nhập
                    </div>
                </div>

                <div className="signupFlowModal__content">
                    <div className="body">
                        <p style={{fontSize: 15, fontWeight: 400, lineHeight: '21px', textAlign: 'left'}}>Quý khách vui lòng đặt lại mật khẩu cho tài khoản.</p>

                        <div className="input-wrapper" style={{marginTop: 12}}>
                            <div className="input-wrapper-item">
                                <div className="input special-input-extend password-input" id="new-password-input">
                                    <div className="input__content">
                                        <label htmlFor="" style={{textAlign: 'left'}}>Mật khẩu</label>
                                        <input name="password" id="new-passsword"
                                                value={this.state.password}
                                                type={this.state.showPassword ? "text" : "password"}
                                                onChange={this.handleInputNewPassChange} required/>
                                    </div>

                                    <i className="password-toggle" onClick={this.handleTogglePassword}></i>
                                </div>
                                {this.renderValidationPasswordCriteria()}
                            </div>

                            <div className="input-wrapper-item" style={{marginTop: 16}}>
                                <div className="input special-input-extend password-input" id="new-re-password-input">
                                    <div className="input__content">
                                        <label htmlFor="" id="label-re-password" style={{textAlign: 'left'}}>Xác nhận mật khẩu</label>
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
                    </div>
                </div>

                <div className="signupFlowModal__footer">
                    <button className={`signupFlowModal__customBtn ${this.state.disabledConfirm ? 'disabled' : ''}`}
                            disabled={this.state.disabledConfirm} onClick={this.handleChangePassword}>Lưu</button>
                </div>

            </div>
            {this.state.openSuccessfulAccount && <SuccessPopup onCallBack={() => { this.callBackSuccessRoute()}} msgHeader={'CẬP NHẬT THÀNH CÔNG'} msgContent={"Quý khách đã thay đổi mật khẩu thành công."} />}
        </div>
            
        </>)
    }
}

export default ChangePasswordPopup;