import React, {useEffect, useState} from 'react';
import '../styles.css';
import {changePass, getPointByClientID, logoutSession, signup} from '../../../util/APIUtils';
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
    validateEmail,
    formatTime,
    removeSession,
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
import PopupSuccessfulSignUp from '../../../popup/PopupSuccessfulSignUp';

import ReactGA from 'react-ga4';
import PopupExistAccount from '../Popup/PopupExistAccount/PopupExistAccount';



const initialState = {
    password: '', rePassword: '', passwordValidations: [{label: 'Ít nhất 8 ký tự', isValid: false}, {
        label: 'Có ký tự số (Vd: 1, 2, 3)', isValid: false
    }, {label: 'Có ký tự đặc biệt (Vd: #, $, %)', isValid: false}, {
        label: 'Có hoa & thường (Vd: A, a, B)', isValid: false
    },], loading: false, showPassword: false, showRePassword: false, showRegister: false,
};

const SignUpFlowModalPotential = (props) => {
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
    const [userNameSuggestion, setUserNameSuggestion] = useState("");
    const [errExistedEmail, setErrExistedEmail] = useState('');
    const [popupAccountAlreadyExist, setPopupAccountAlreadyExist] = useState({isOpen: false, content:''});
    const [errorLock, setErrorLock] = useState(false);
    const [countdown, setCountdown] = useState(0);// 30 minutes in seconds
    // const [countdownInterval, setCountdownInterval] = useState(null);

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

    // const setCount = (seconds) => {
    //     setCountdown(seconds);
    // }

    // const startCountdown = () => {
    //     let myInterval = setInterval(() => {
    //         if (countdown > 0) {
    //             console.log('interval if startCountdown=',countdown);
    //             setCountdown(countdown - 1);
    //             console.log('interval if startCountdown2=',countdown);
    //         }
    //         else {
    //             clearInterval(myInterval)
    //         }
    //     }, 1000)
    //     return () => {
    //         clearInterval(myInterval);
    //     };

    // }

    // const startCountdown = () => {
    //     console.log('start startCountdown=', countdown);
    //     if(countdownInterval){
    //         console.log('xxxx');
    //         clearInterval(countdownInterval);
    //     }
    //     console.log('after if startCountdown');
    //     let cntdInterval = setInterval(() => {
    //         if (countdown > 0) {
    //             console.log('interval if startCountdown=',countdown);
    //             setCount(countdown - 1);
    //             console.log('interval if startCountdown2=',countdown);
    //         } else {
    //             console.log('interval else clear startCountdown');
    //             // clearInterval(countdownInterval);
    //             //setCountdown(0);
    //             // Additional actions when the countdown reaches 0
    //             // this.resetState();
    //         }
    //     }, 10000); // Update every 1 second
    //     setCountdownInterval(cntdInterval);
    //     console.log('interval else clear startCountdown');
    //     // this.setState({...this.setState, countdownInterval, disabledConfirm: true})
    // }

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

    const popupOtpLoginSubmit = (OTP) => {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: 'mcp',
                Action: 'CheckOTPV2',
                Note: 'RegisterAccountStep1',
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
                setLoading(false);
                const {Response} = response;
                // setUserNameSuggestion(Response.UsernameSuggestion);
                setUserName(Response.UsernameSuggestion);
                const userNameValidations = getRegisterUserNameValidations(Response.UsernameSuggestion);
                setUserNameValidations(userNameValidations);
                // Check if all criteria are met to enable the button
                const isButtonEnabled = userNameValidations.every((validation) => validation.isValid);
                setDisabledConfirm(!isButtonEnabled);
                if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL') {
                    const {Message, NewAPIToken} = Response;

                    if (Message) {
                        const [userLogin, userName] = Message.split('|');
                        setSession(USER_LOGIN, userLogin);
                        setSession(USER_NAME, userName);
                    }

                    if (NewAPIToken) {
                        setSession(API_TOKEN, NewAPIToken);
                    }
                    resetState();
                    setCurrentStep(currentStep + 1);
                    setTitle('Tên đăng nhập');

                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (Response.ErrLog === 'OTPEXPIRY') {
                    setErrorMessage(OTP_EXPIRED);
                } else if (Response.ErrLog === 'OTPLOCK' || Response.ErrLog === 'OTP Wrong 3 times') {
                    // document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                    setErrorMessage('');
                    setCountdown(1800); // Set countdown to 30 minutes (1800 seconds)
                    // startCountdown();
                    setErrorLock(true);
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
            isValid: !value || (value.length < 5) || (value.length > 30) ? false : true,
            // && /[a-zA-Z0-9]/.test(value) && /\d/.test(value),
        }, {
            label: 'Tên đăng nhập viết liền không dấu và không phải là một dãy số.', isValid: value && (value.trim() === '') ? false : value && (!/\s/.test(value) && (/[^a-zA-Z0-9]/.test(value) || /[a-zA-Z]/.test(value))),
        }];
    };

    const handleInputChange = (event) => {
        if (document.getElementById('user_name_input')) {
            document.getElementById('user_name_input').className = 'input special-input-extend password-input';
        }
        setErrExistedEmail(false) ;

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

        setSession(USER_NAME, inputValue);

    };

    const CheckUserName = (userName) => {
        let inputValue = userName;

        // Validate userName and get validation criteria
        const userNameValidations = getRegisterUserNameValidations(inputValue);

        inputValue = removeAccents(inputValue);
        setUserName(inputValue);
        setUserNameValidations(userNameValidations);

        const newUserNameInput = document.getElementById('new-userName');
        if (newUserNameInput) {
            newUserNameInput.value = inputValue;
        }
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
                    <li className={validation.isValid?'hint-check-correct':'hint-check'}>
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
        const isValidPass = passwordValidations.every((validation) => validation.isValid) && inputValue === state.rePassword;
        // Check if all criteria are met to enable the button
        const isButtonEnabled = inputValue === state.rePassword;

        setDisabledConfirm(!isButtonEnabled || !isValidPass);
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

        // Validate password and get validation criteria
        const passwordValidations = getRegisterPasswordValidations(inputValue);

        // Check if all criteria are met to enable the button
        const isValidPass = passwordValidations.every((validation) => validation.isValid) && inputValue === state.password;
        // Check if all criteria are met to enable the button
        const isButtonEnabled = inputValue === state.password;

        setDisabledConfirm(!isButtonEnabled || !isValidPass);
    };

    const processRegistration = () => {
        setLoading(true);

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
                Password: "", // Password: AES256.encrypt(state.password, COMPANY_KEY2),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME)?getSession(USER_NAME):'',
            }
        };

        signup(registerRequest)
            .then(response => {
                setLoading(false);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CLIRESSTEP2SUCC') {
                    // handleLogin();
                    setCurrentStep(currentStep + 1);
                    setDisabledConfirm(true);
                    setTitle('Mật khẩu đăng nhập');
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'CLIEMPTY')) {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                } else if(response.Response.Result === 'true' && response.Response.ErrLog === 'USERNAMEEXIST'){
                    setLoading(false);
                    // setPopupAccountAlreadyExist({isOpen: true
                    //     , content:`Tên đăng nhập này đã được chọn. Quý khách vui lòng sử dụng một tên đăng nhập khác. VD: ${response.Response?.UsernameSuggestion}`})
                    document.getElementById('user_name_input').className = 'input special-input-extend password-input validate';
                    setErrExistedEmail(`<p style="margin-top: 4px; text-align: left; font-size: 12px; color: #DE181F; line-height: 15px;">Tên đăng nhập này đã được chọn. Quý khách vui lòng sử dụng một tên đăng nhập khác. VD: ${response.Response?.UsernameSuggestion} </p>`)
                    setLoading(false);
                    setDisabledConfirm(true);
                    // setOpenExceptionAccount(true);
                }
                else {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                }
            })
            .catch(error => {
                console.error("Error occurred during registration:", error);
                // Handle error here, if needed
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
                UserName: getSession(USER_NAME),
                OS: '',
                Project: 'mcp',
                Password: AES256.encrypt(state.password, COMPANY_KEY2),
                RecaptchaToken: "",
                Captcha: "",
                PassEnscr: '1',
            }
        };
        removeSession(EMAIL);
        removeSession(CELL_PHONE);
        setOpenSuccessfulAccount(false);
        signup(loginRequest, false)
            .then(response => {
                const clientProfile = response?.Response?.ClientProfile?.[0];
                if (response.Response.Result === 'true' && response.Response.ErrLog.toLowerCase() === 'login successfull' && clientProfile) {
                    // document.getElementById('new-password-registration').className = 'popup special new-password-registration';
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
                                // setTimeout(() => {
                                //     document.getElementById('new-password-registration').className = 'popup special new-password-registration';
                                //     buildParamsAndRedirect(props.parentPath);
                                // }, 2500);
                                buildParamsAndRedirect(props.parentPath);
                            } 
                            document.getElementById('new-password-registration').className = 'popup special new-password-registration';
                            if (getSession(DCID)) {
                                getLinkPartner(AKTIVOLABS_ID);
                            }
                            
                        })
                        .catch(error => {
                            if (props.parentPath) {
                                buildParamsAndRedirect(props.parentPath);
                            }
                            document.getElementById('new-password-registration').className = 'popup special new-password-registration';
                        });

                    setSession(LOGIN_TIME, new Date().getTime() / 1000);
                    if (getSession(PREVIOUS_SCREENS) && ((getSession(PREVIOUS_SCREENS) === '/song-vui-khoe')) || (getSession(PREVIOUS_SCREENS) === '/home') || (getSession(PREVIOUS_SCREENS) === '/') || getSession(ICALLBACK)) {
						// document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
						getLinkId();
					}
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
        if (newPassword && newRePassword && (newRePassword.value !== newPassword.value)) {
            // if (passNotMatchId) {
            //     passNotMatchId.className = 'error-message validate';
            // }
            if (labelRePassword) {
                labelRePassword.className = 'lable-red';
            }
            return;
        }

        setLoading(true);
        setDisabledConfirm(true);
        const changePassRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'AccountChangeUserName',
                APIToken: getSession(API_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: '',
                OldPassword: '', // Password: AES256.encrypt(state.password, COMPANY_KEY2),
                Password: state.password,
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                UserName: getSession(USER_NAME),
                NewUserName: getSession(USER_NAME),
                Note:"AccountRegisterCompleted"
            }
        };

        if (getSession(OTP_EMAIL)) {
            sessionStorage.removeItem(OTP_EMAIL);
        }

        changePass(changePassRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                    setLoading(false);
                    props.clearState();
                    setOpenSuccessfulAccount(true);
                    setTitle('Xác thực OTP');
                    // if (passNotMatchId) {
                    //     passNotMatchId.className = 'error-message';
                    // }
                    if (labelRePassword) {
                        labelRePassword.className = '';
                    }
                    if (newPassword) {
                        newPassword.value = '';
                    }
                    if (newRePassword) {
                        newRePassword.value = '';
                    }
                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    setLoading(false);
                    setDisabledConfirm(false);
                }
            })
            .catch(error => {
                console.log('error=', error);
                setLoading(false);
                setDisabledConfirm(false);
            });
    };

    useEffect(() => {
        if (getSession(USER_NAME)) {
            CheckUserName(getSession(USER_NAME));
        }
        document.addEventListener("keydown", closeNewPassEsc, false);
        return () => {
            document.removeEventListener("keydown", closeNewPassEsc, false);
        };
    }, []);

    useEffect(() => {
        console.log('useEffect countDown=',countdown);
        if (countdown === 0) {
            setErrorLock(false);
        }
        // if (getSession(USER_NAME)) {
        //     CheckUserName(getSession(USER_NAME));
        // }
        // document.addEventListener("keydown", closeNewPassEsc, false);
        // return () => {
        //     document.removeEventListener("keydown", closeNewPassEsc, false);
        // };
    }, [countdown]);
    
    useInterval(
        () => {
            setCountdown((countdown) => countdown - 1);
        },
        countdown > 0 ? 1000 : null
    );

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
                <div className="signupFlowModal__steps">
                    {steps.map((step, index) => (<div
                        key={index}
                        className={`signupFlowModal__step ${index <= currentStep ? 'signupFlowModal__step--active' : ''}`}
                    ></div>))}
                </div>

                {/* Content */}
                <div className="signupFlowModal__content">
                    {currentStep === 0 && <div className="genOTPModal__content">
                        {errorMessage && <div className="genOTPModal__error-message genOTPModal__validate">
                            <i className="icon">
                                <img src="../img/icon/warning_sign.svg" alt=""/>
                            </i>
                            <p>{errorMessage}</p>
                        </div>}
                        {errorLock &&
                            <div className="genOTPModal__error-message genOTPModal__validate">
                                <i className="icon" style={{marginRight: 12, width: '24px', height: '24px'}}>
                                    <img src="../img/icon/warning_sign.svg" alt=""/>
                                </i>
                                <p style={{margin: 0}}>Thông tin nhập đã sai quá 5 lần. Chức năng
                                    tạm ngừng hoạt động trong 30 phút. <span
                                        style={{color: '#FAE340', fontSize: 13}}>{formatTime(countdown)}</span>
                                </p>
                            </div>
                        }
                        <div className="genOTPModal__otp-picture-wrapper">
                            <div className="genOTPModal__picture" style={{marginLeft: '34%'}}>
                                <img src="../img/otp-verify.svg"/>
                            </div>
                        </div>
                        <h5 className="basic-text2 bigheight">
                            Mã <span className="basic-text2 basic-bold">OTP</span> đã gửi đến {maskPhone ? ' số điện thoại ' : ' email '}
                            <span
                                className="basic-text2 basic-bold">{maskPhone ? maskPhone : (maskEmail ? maskEmail : '')}</span> của
                            Quý khách. <br/> Vui lòng nhập
                            <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác thực.
                        </h5>
                        <div className="genOTPModal__otp-focus">
                            <OTPInput value={OTP} autoFocus OTPLength={6} otpType="number"
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
                            <div className="input special-input-extend password-input" id='user_name_input'>
                                <div className="input__content">
                                    <label htmlFor="" style={{textAlign: 'left'}}>Tên đăng nhập</label>
                                    <input
                                        // placeholder= {`gợi ý: ${userNameSuggestion}`}
                                        value={userName}
                                        type="text"
                                        name="userName"
                                        id="new-userName"
                                        onChange={handleInputChange} required/>
                                </div>
                            </div>
                        </div>
                        {errExistedEmail && <div dangerouslySetInnerHTML={{__html: errExistedEmail}}/>}
                        {renderValidationUserNameCriteria()}
                    </div>}
                    {currentStep === 2 && <div className="body">
                        <p style={{fontSize: 15, fontWeight: 400, lineHeight: '21px', textAlign: 'left'}}>Quý
                            khách vui lòng thiết lập mật khẩu cho tài khoản.</p>
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
                    {loading ? <LoadingIndicatorBasic/> : <LoadingIndicator area="signup-area"/>}
                    
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
        {openSuccessfulAccount && <PopupSuccessfulSignUp onCallBack={() => {
                handleLogin();
            }}/>}
        {openExceptionAccount && <PopupExceptionAccount onCallBack={() => setOpenExceptionAccount(false)}/>}
        {popupAccountAlreadyExist?.isOpen && <PopupExistAccount onCallBack={()=> setPopupAccountAlreadyExist({isOpen: false})} content={popupAccountAlreadyExist?.content} />}
    </>);
};

export default SignUpFlowModalPotential;

