import React, {useEffect, useState} from 'react';
import '../styles.css';
import {getPointByClientID, login2, logoutSession, removeUndefinedFields, signup} from '../../../util/APIUtils';
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
    formatTime,
    removeSpecialCharactersAndSpace
} from '../../../util/common';
import {
    ACCESS_TOKEN,
    ADDRESS,
    AKTIVOLABS_ID,
    API_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLASSPO,
    CLASSPOMAP,
    CLIENT_ID,
    CLIENT_NOT_FOUND_MESSAGE,
    CLIENT_PROFILE,
    COMPANY_KEY,
    COMPANY_KEY2,
    DCID,
    DOB,
    EMAIL,
    EXPIRED_MESSAGE,
    FULL_NAME,
    GENDER,
    LINK_FB,
    LINK_GMAIL,
    LOGIN_TIME,
    LOGINED,
    OTHER_ADDRESS,
    OTP_EMAIL,
    OTP_EXPIRED,
    POID,
    POINT,
    POL_LIST_CLIENT,
    PREVIOUS_SCREENS,
    TWOFA,
    USER_DEVICE_TOKEN,
    USER_LOGIN,
    USER_NAME,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    ICALLBACK,
    FE_BASE_URL 
} from '../../../constants';
import OTPInput from "otp-input-react";
import LoadingIndicator from "../../../common/LoadingIndicator2";
import iconGreenCheck from "../../../img/icon/iconGreenCheck.svg";
import iconGreyCheck from "../../../img/icon/iconGreyCheck.svg";
import LoadingIndicatorBasic from "../../../common/LoadingIndicatorBasic";
import AES256 from "aes-everywhere";
import PopupSuccessfulAccount from "../Popup/PopupSuccessfulAccount/PopupSuccessfulAccount";
import PopupExceptionAccount from "../Popup/PopupExceptionAccount/PopupExceptionAccount";
import ReactGA from 'react-ga4';

const initialState = {
    password: '', rePassword: '', passwordValidations: [{label: 'Ít nhất 8 ký tự', isValid: false}, {
        label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false
    }, {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false}, {
        label: 'Có hoa & thường (Vd: A, a, B)', isValid: false
    },], loading: false, showPassword: false, showRePassword: false, showRegister: false,
};

const ForgotBothModalPotential = (props) => {
    const {maskPhone, maskEmail, fullname, resetState, minutes, seconds, transactionId, handleGenOTP, isOpenPopup} = props;
    const [state, setState] = useState(initialState);
    const [currentStep, setCurrentStep] = useState(0);
    const [errorMessage, setErrorMessage] = useState('');
    const [OTP, setOTP] = useState('');
    const [userName, setUserName] = useState('');
    const [disabledConfirm, setDisabledConfirm] = useState(true);
    const [title, setTitle] = useState('Xác thực OTP');
    const [loading, setLoading] = useState(false);
    const [openSuccessfulAccount, setOpenSuccessfulAccount] = useState(false);
    const [openExceptionAccount, setOpenExceptionAccount] = useState(false);
    const [countdown, setCountdown] = useState(0);

    const [userNameValidations, setUserNameValidations] = useState([{
        label: 'Tên đăng nhập bao gồm từ 5 đến 30 ký tự.', isValid: false
    }, {label: 'Tên đăng nhập viết liền không dấu và không phải là một dãy số.', isValid: false}]);

    const steps = [{
        title: 'Step 1', content: 'Content of Step 1',
    }, {
        title: 'Step 2', content: 'Content of Step 2',
    }, {
        title: 'Step 3', content: 'Content of Step 3',
    },];

    const handleNext = () => {
        if (currentStep === steps.length - 1) {
            handlePopupNewPassSubmit();
        } else if (currentStep === 1) {
            processRegistration();
        } else {
            setCurrentStep(currentStep + 1);
        }
    };

    const popupOtpLoginSubmit = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: 'mcp',
                Action: 'CheckOTPV2',
                Note: 'ForgotPasswordNew',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: '',
                OTP,
                TransactionID: transactionId || '',
                Project: 'mcp',
                FullName: fullname,
                UserLogin: getSession(USER_LOGIN),
            }
        };

        signup(verifyOTPRequest)
            .then(response => {
                console.log("popupOtpLoginSubmit", response);
                setLoading(false);

                const {Response} = response;

                if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL') {
                    const {UserName, NewAPIToken} = Response;

                    if (NewAPIToken) {
                        setSession(API_TOKEN, NewAPIToken);
                    }
                    if (UserName) {
                        // setSession(USER_NAME, UserName);
                        setUserName(UserName);
                        const userNameValidations = getRegisterUserNameValidations(UserName);
                        setUserNameValidations(userNameValidations);
                        // Check if all criteria are met to enable the button
                        const isButtonEnabled = userNameValidations.every((validation) => validation.isValid);
                        setDisabledConfirm(!isButtonEnabled);
                    }

                    // if (Response.UsernameSuggestion) {
                    //     setUserName(Response.UsernameSuggestion);
                    //     const userNameValidations = getRegisterUserNameValidations(response.Response.UsernameSuggestion);
                    //     setUserNameValidations(userNameValidations);
                    //     // Check if all criteria are met to enable the button
                    //     const isButtonEnabled = userNameValidations.every((validation) => validation.isValid);
                    //     setDisabledConfirm(!isButtonEnabled);
                    // }

                    setSession(DCID, response.Response?.DCID);
                    setErrorMessage('');
                    resetState();
                    setCurrentStep(currentStep + 1);
                    setTitle('Tên đăng nhập');

                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (Response.ErrLog === 'OTPEXPIRY') {
                    setErrorMessage(OTP_EXPIRED);
                } else if (Response.ErrLog === 'OTPLOCK' || Response.ErrLog === 'OTP Wrong 3 times') {
                    // document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                    setErrorMessage(`Thông tin nhập đã sai quá 5 lần. Chức năng tạm ngừng hoạt động trong 30 phút.`);
                    setCountdown(1800);
                } else {
                    const errorMessage = Response.ErrLog === 'OTPINCORRECT' ? `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${Response.Message} lần thử.` : `Mã OTP không đúng hoặc đã hết hạn. Quý khách còn ${Response.Message} lần thử.`;
                    setErrorMessage(errorMessage);
                }
            });
    };

    const reGenOtp = () => {
        handleGenOTP();
    };

    const handleOtpChange = (newOtp) => {
        if (newOtp.length === 6) {
            popupOtpLoginSubmit(newOtp);
        }
        setOTP(newOtp);
    };

    const getRegisterUserNameValidations = (value) => {
        return [{
            label: 'Tên đăng nhập bao gồm từ 5 đến 30 ký tự.',
            isValid: value.trim() === '' ? false : value.length >= 5 && value.length <= 30,
            // && /[a-zA-Z0-9]/.test(value) && /\d/.test(value),
        }, {
            label: 'Tên đăng nhập viết liền không dấu và không phải là một dãy số.', isValid: value.trim() === '' ? false : !/\s/.test(value) && (/[^a-zA-Z0-9]/.test(value) || /[a-zA-Z]/.test(value)),
        }];
    };

    const handleInputChange = (event) => {
        const target = event.target;
        let inputValue = target.value.toLowerCase();
        inputValue = removeSpecialCharactersAndSpace(inputValue);
        inputValue = removeAccents(inputValue);

        // Validate userName and get validation criteria
        const userNameValidations = getRegisterUserNameValidations(inputValue);

        setUserName(inputValue);
        setUserNameValidations(userNameValidations);

        // const newUserNameInput = document.getElementById('new-userName');
        // if (newUserNameInput) {
        //     newUserNameInput.value = inputValue;
        // }
        // // Check if all criteria are met to enable the button
        const isButtonEnabled = userNameValidations.every((validation) => validation.isValid);
        setDisabledConfirm(!isButtonEnabled);
    };

    const renderValidationUserNameCriteria = () => {
        return (<ul>
            {userNameValidations.map((validation, index) => (
                <div key={index} style={{display: 'flex', alignItems: 'center', marginTop: 8}}>
                    <img src={validation.isValid ? iconGreenCheck : iconGreyCheck}
                         alt={`${validation.isValid ? "iconGreenCheck" : "iconGreyCheck"}`}
                         style={{marginRight: 8}}/>
                    <li
                        style={{
                            color: validation.isValid ? 'green' : '#727272',
                            fontSize: 12,
                            fontWeight: 500,
                            textAlign: 'left',
                            lineHeight: '14.52px'
                        }}>
                        {validation.label}
                    </li>
                </div>))}
        </ul>);
    }

    const renderValidationCriteria = () => {
        return (<ul>
            {state.passwordValidations.map((validation, index) => (
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

    const closeNewPassEsc = (event) => {
        if (event.keyCode === 27) {
            closeNewPass();
        }
    };

    const handleInputNewPassChange = (event) => {
        const {name, value} = event.target;
        const inputName = name.trim();
        let inputValue = value.trim();

        // Validate password and get validation criteria
        const passwordValidations = getRegisterPasswordValidations(inputValue);

        inputValue = removeAccents(inputValue);

        setState((prevState) => ({
            ...prevState, [inputName]: inputValue, passwordValidations: passwordValidations,
        }));

        // Check if all criteria are met to enable the button
        const isButtonEnabled = passwordValidations.every((validation) => validation.isValid) && inputValue === state.rePassword;

        setDisabledConfirm(!isButtonEnabled);
    };

    const getRegisterPasswordValidations = (value) => {
        return [{
            label: 'Ít nhất 8 ký tự', isValid: value.length >= 8
        }, {label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: /\d/.test(value)}, {
            label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: /[^\p{L}\p{N}]/u.test(value)
        }, {label: 'Có hoa & thường (Vd: A, a, B)', isValid: /([a-z].*[A-Z])|([A-Z].*[a-z])/.test(value)},];
    };

    const closeNewPass = () => {
        // Reset input values
        setState(initialState);
    };

    const handleTogglePassword = () => {
        setState((prevState) => ({
            ...prevState, showPassword: !prevState.showPassword,
        }));
    };

    const handleToggleRePassword = () => {
        setState((prevState) => ({
            ...prevState, showRePassword: !prevState.showRePassword,
        }));
    };

    const handleInputNewRePassChange = (event) => {
        const {name, value} = event.target;
        let inputValue = value.trim();

        // Remove accents
        inputValue = removeAccents(inputValue);

        // Set input value to new password input
        const newPasswordInput = document.getElementById('new-re-passsword');
        if (newPasswordInput) {
            newPasswordInput.value = inputValue;
        }

        // Update state with new input value
        setState((prevState) => ({
            ...prevState, [name]: inputValue,
        }));

        // Check if all criteria are met to enable the button
        const isButtonEnabled = inputValue === state.password;

        setDisabledConfirm(!isButtonEnabled);
    };

    const processRegistration = () => {
        setLoading(true);
        const agent = window.btoa(navigator.userAgent);
        const registerRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'AccountChangeUserName',
                Note: 'AccountChangeUserName',
                APIToken: getSession(API_TOKEN)?getSession(API_TOKEN):'',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME),
                NewUserName: userName?userName:'',
                DCID: getSession(DCID) && (getSession(DCID) !== 'undefined') ? getSession(DCID) : '',
            }
        };

        signup(registerRequest)
            .then(response => {
                console.log("RegisterAccountStep2", response);
                setLoading(false);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                    // handleLogin();
                    setCurrentStep(currentStep + 1);
                    setDisabledConfirm(true);
                    setTitle('Đặt lại mật khẩu');
                } else if ((response.Response.Result === 'true') && (response.Response.ErrLog === 'USERNAMEEXIST')) {
                    const {UserLogin, UsernameSuggestion} = response.Response;
                    if (UserLogin) {
                        setSession(USER_LOGIN, UserLogin);
                    }
                    if (UsernameSuggestion) {
                        setSession(USER_NAME, UsernameSuggestion);
                    }
                    setErrorMessage('Tên đăng nhập này đã được chọn. Quý khách vui lòng sử dụng một tên đăng nhập khác. VD: ' + UsernameSuggestion);
                    setLoading(false);
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'USERNAMEEXIST')) {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                } else {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                }
            })
            .catch(error => {
                setLoading(false);
                setOpenExceptionAccount(true);
            });
    };

    const handleLogin = () => {
        const loginRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CPLOGINV2', // Action: 'CPLOGIN',
                APIToken: '',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                DeviceToken: getSession(USER_DEVICE_TOKEN) || '',
                UserLogin: getSession(USER_LOGIN),
                UserName: userName,
                OS: '',
                Project: 'mcp',
                Password: AES256.encrypt(state.password, COMPANY_KEY2),
                RecaptchaToken: "",
                Captcha: "",
                PassEnscr: '1',
            }
        };

        signup(loginRequest, false)
            .then(response => {
                console.log('login2', response.Response);
                const clientProfile = response?.Response?.ClientProfile?.[0];
                if (response.Response.Result === 'true' && response.Response.ErrLog.toLowerCase() === 'login successfull' && clientProfile) {
                    if (getSession(POL_LIST_CLIENT)) {
                        sessionStorage.removeItem(POL_LIST_CLIENT);
                    }
                    setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                    setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                    setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix impact for rewamp username
                    setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                    setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                    setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                    setSession(GENDER, response.Response.ClientProfile[0].Gender);
                    setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                    setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
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
					if (response.Response.ClientProfile[0].UserName) {
						setSession(USER_NAME, response.Response.ClientProfile[0].UserName);
					}
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
                                props.callBackSuccessful();
                                setSession(LOGINED, LOGINED);
                            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                                showMessage(EXPIRED_MESSAGE);
                                logoutSession();
                            } else {
                                props.callBackSuccessful();
                                setSession(LOGINED, LOGINED);
                            }

                            if (props.parentPath) {
                                buildParamsAndRedirect(props.parentPath);
                            }
                            if (getSession(DCID)) {
                                getLinkPartner(AKTIVOLABS_ID);
                            }
                        })
                        .catch(error => {
                            if (props.parentPath) {
                                buildParamsAndRedirect(props.parentPath);
                            }
                        });

                    setSession(LOGIN_TIME, new Date().getTime() / 1000);
                    if (getSession(PREVIOUS_SCREENS) && ((getSession(PREVIOUS_SCREENS) === '/song-vui-khoe')) || (getSession(PREVIOUS_SCREENS) === '/home') || (getSession(PREVIOUS_SCREENS) === '/') || getSession(ICALLBACK)) {
						// document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
						getLinkId();
					}
                    document.getElementById('new-password-registration').className = 'popup special new-password-registration';
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'CLINOEXIST' || response.Response.ErrLog === 'CLIEXISTNOACTIVE')) {
                    showMessage(CLIENT_NOT_FOUND_MESSAGE);
                    setState((prevState) => ({
                        ...prevState, showRegister: true,
                    }));
                } else if (response.Response.ErrLog === 'WRONGPASS') {
                    document.getElementById('check-pass-id').className = 'error-message validate';
                }
            })
            .catch(error => {
                // warning();
            });
    };

    const handlePopupNewPassSubmit = () => {
        const newPassword = document.getElementById('new-passsword');
        const newRePassword = document.getElementById('new-re-passsword');
        // const passNotMatchId = document.getElementById('pass-not-match-id');
        const labelRePassword = document.getElementById('label-re-password');

        if (newRePassword.value !== newPassword.value) {
            // passNotMatchId.className = 'error-message validate';
            labelRePassword.className = 'lable-red';
            return;
        }

        setLoading(true);
        setDisabledConfirm(true);

        if (getSession(OTP_EMAIL)) {
            sessionStorage.removeItem(OTP_EMAIL);
        }
        const changePassRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'AccountChangeUserName',
                Note: 'AccountChangeCompleted',
                APIToken: getSession(API_TOKEN)?getSession(API_TOKEN):'',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME)?getSession(USER_NAME):'',
                NewUserName: getSession(USER_NAME)?getSession(USER_NAME):'',
                DCID: getSession(DCID) && (getSession(DCID) !== 'undefined') ? getSession(DCID) : '',
                Password: AES256.encrypt(state.password, COMPANY_KEY2),
            }
        };

        signup(removeUndefinedFields(changePassRequest))
            .then(response => {
                console.log("processRegistration", response);
                setLoading(false);

                const {Response} = response;

                if (Response.Result === 'true' && Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                    setLoading(false);
                    props?.clearState();
                    setOpenSuccessfulAccount(true);
                    setTitle('Xác thực OTP');
                    // passNotMatchId.className = 'error-message';
                    labelRePassword.className = '';
                    newPassword.value = '';
                    newRePassword.value = '';

                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'CLIEMPTY' || response.Response.ErrLog === 'USERNAMEEXIST')) {
                    showMessage(EXPIRED_MESSAGE);
                    setLoading(false);
                    setDisabledConfirm(false);

                } else {
                    showMessage(EXPIRED_MESSAGE);
                    setLoading(false);
                    setDisabledConfirm(false);

                }
            })
            .catch(error => {
                console.error("Error occurred during signup:", error);
                showMessage(EXPIRED_MESSAGE);
                setLoading(false);
                setDisabledConfirm(false);
            });
        ;
    };

    useEffect(() => {
        document.addEventListener("keydown", closeNewPassEsc, false);
        return () => {
            document.removeEventListener("keydown", closeNewPassEsc, false);
        };
    }, []);

    useInterval(
        () => {
            setCountdown((countdown) => countdown - 1);
        },
        countdown > 0 ? 1000 : null
    );
    
    function useInterval(callback, delay) {
        const intervalRef = React.useRef(null);
        const savedCallback = React.useRef(callback);
        React.useEffect(() => {
            savedCallback.current = callback;
        }, [callback]);
        React.useEffect(() => {
            const tick = () => savedCallback.current();
            if (typeof delay === 'number') {
                intervalRef.current = window.setInterval(tick, delay);
                return () => window.clearInterval(intervalRef.current);
            } else {
                return () => window.clearInterval(intervalRef.current);
            }
        }, [delay]);
        return intervalRef;
    }
    
    return (<>
        {isOpenPopup &&
        <div className="signupFlowModal__overlay">
            <div className="signupFlowModal__container">
                {/* Header */}
                <div className="signupFlowModal__header">
                    <div className="signupFlowModal__title">{title}</div>
                    <div className="close-btn" style={{width: '14px', height: '14px', marginTop: '-30px', cursor: 'pointer'}}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={props.showCloseConfirm()} />
                    </div>
                </div>

                {/* Steps */}
                {/* <div className="signupFlowModal__steps">
                    {steps.map((step, index) => (<div
                        key={index}
                        className={`signupFlowModal__step ${index <= currentStep ? 'signupFlowModal__step--active' : ''}`}
                    ></div>))}
                </div> */}

                {/* Content */}
                <div className="signupFlowModal__content">
                    {currentStep === 0 && <div className="genOTPModal__content">
                        {errorMessage && <div className="genOTPModal__error-message genOTPModal__validate">
                            <i className="icon">
                                <img src="../img/icon/warning_sign.svg" alt=""/>
                            </i>
                            <p>{errorMessage}
                                {countdown > 0 && 
                                    <span style={{color: '#FAE340', fontSize: 13}}> {formatTime(countdown)}</span>
                                }
                            </p>
                        </div>}

                        <div className="genOTPModal__otp-picture-wrapper">
                            <div className="genOTPModal__picture" style={{marginLeft: '34%'}}>
                                <img src="../img/otp-verify.svg"/>
                            </div>
                        </div>
                        <h5 className="basic-text2 bigheight">
                            Mã <span className="basic-text2 basic-bold">OTP</span> đã gửi đến <span className="basic-text2 basic-bold">{maskPhone ? ' số điện thoại ' : ' email '}</span>
                            của Quý khách nếu có tồn tại trong hệ thống. <br/> Vui lòng nhập
                            <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác thực.
                        </h5>
                        <div className="genOTPModal__otp-focus">
                            <OTPInput value={OTP} onChange={setOTP} autoFocus OTPLength={6} otpType="number"
                                      disabled={false}
                                      onChange={handleOtpChange}
                                      inputStyles={{
                                          borderBottom: '2px solid #C4C4C4',
                                          borderTop: '0px solid #ffffff',
                                          borderLeft: '0px solid #ffffff',
                                          borderRight: '0px solid #ffffff',
                                          width: '50px',
                                          marginRight: '8px',
                                      }}
                                      required/>

                        </div>
                        {minutes === 0 && seconds === 0 ? (
                            <p className="genOTPModal__tag">Bạn chưa nhận được mã OTP? <br/><span
                                className="simple-brown basic-bold" onClick={() => reGenOtp()}>Gửi lại</span></p>) : (
                            <p className="genOTPModal__tag">Bạn chưa nhận được mã OTP?
                                <br/><span className="genOTPModal__tag"
                                           onClick={() => reGenOtp()}>Gửi lại mã mới sau</span>&nbsp;
                                <span style={{fontWeight: 700}}>{`${minutes * 60 + seconds}s`}</span>
                            </p>)}
                        <br/>
                        <br/>
                        <LoadingIndicator area="verify-otp"/>
                    </div>}

                    {currentStep === 1 && <div className="username-wrapper">
                        <p className="username-wrapper-content">Quý khách có thể đặt Tên đăng nhập tùy chọn để sử dụng cho những lần đăng nhập sau.</p>
                        <div className="input-wrapper-item" style={{marginTop: 12}}>
                            <div className="input special-input-extend password-input">
                                <div className="input__content">
                                    <label htmlFor="" style={{textAlign: 'left'}}>Tên đăng nhập</label>
                                    <input
                                        value={userName}
                                        type="text"
                                        name="userName"
                                        id="new-userName"
                                        onChange={handleInputChange} required/>
                                </div>
                            </div>
                        </div>
                        {errorMessage && <span style={{color: 'red', paddingTop: '16px', textAlign: 'left', justifyItems: 'left', display: 'flex', lineHeight: '15px', fontSize: '12px' }}>{errorMessage}</span>}
                        {renderValidationUserNameCriteria()}
                    </div>}
                    {currentStep === 2 && <div className="body">
                        <p style={{fontSize: 15, fontWeight: 400, lineHeight: '21px', textAlign: 'left'}}>Quý khách vui lòng đặt lại mật khẩu cho tài khoản.</p>
                        <div className="input-wrapper" style={{marginTop: 12}}>
                            <div className="input-wrapper-item">
                                <div className="input special-input-extend password-input"
                                     id="new-password-input">
                                    <div className="input__content">
                                        <label htmlFor="" style={{textAlign: 'left'}}>Mật khẩu</label>
                                        <input name="password" id="new-passsword"
                                               value={state.password}
                                               type={state.showPassword ? "text" : "password"}
                                               onChange={handleInputNewPassChange} required/>
                                    </div>
                                    <i className="password-toggle" onClick={handleTogglePassword}></i>
                                </div>
                                {renderValidationCriteria()}
                            </div>
                            <div className="input-wrapper-item" style={{marginTop: 16}}>
                                <div className="input special-input-extend password-input"
                                     id="new-re-password-input">
                                    <div className="input__content">
                                        <label htmlFor="" id="label-re-password" style={{textAlign: 'left'}}>Xác nhận
                                            mật khẩu</label>
                                        <input name="rePassword" id="new-re-passsword"
                                               value={state.rePassword}
                                               type={state.showRePassword ? "text" : "password"}
                                               onChange={handleInputNewRePassChange} required/>
                                    </div>
                                    <i className="password-toggle" onClick={handleToggleRePassword}></i>
                                </div>
                            </div>
                            {/* <div className="error-message" id="pass-not-match-id">
                                <i className="icon">
                                    <img src="img/icon/warning_sign.svg" alt=""/>
                                </i>
                                <p>Mật khẩu không trùng khớp</p>
                            </div> */}
                            {state.password && state.rePassword && (state.password !== state.rePassword) &&
                            <div style={{ paddingTop: '12px' }}>
                                <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                                    Mật khẩu không khớp
                                </span>
                            </div>
                            }
                        </div>
                    </div>}
                    {loading && <LoadingIndicatorBasic/>}
                </div>
                {currentStep !== 0 && <div className="signupFlowModal__footer">
                    <button className={`signupFlowModal__customBtn ${disabledConfirm ? 'disabled' : ''}`}
                            disabled={disabledConfirm} onClick={handleNext}>
                        {currentStep < steps.length - 1 ? 'Tiếp tục' : ' Tạo mật khẩu'}
                    </button>
                </div>}
            </div>
        </div>
        }
        {openSuccessfulAccount && <PopupSuccessfulAccount loading={loading} onCallBack={() => {
                handleLogin();
            }}/>}
        {openExceptionAccount && <PopupExceptionAccount onCallBack={() => setOpenExceptionAccount(false)}/>}
    </>);
};

export default ForgotBothModalPotential;

