import React, {useState} from 'react';
import '../styles.css';
import {removeUndefinedFields, signup} from '../../../util/APIUtils';
import {getDeviceId, getSession, removeAccents, setSession, showMessage, formatTime, removeSpecialCharactersAndSpace} from '../../../util/common';
import {
    API_TOKEN,
    AUTHENTICATION,
    COMPANY_KEY,
    DCID,
    EXPIRED_MESSAGE,
    FULL_NAME,
    OTP_EXPIRED,
    USER_LOGIN,
    USER_NAME,
    FE_BASE_URL 
} from '../../../constants';
import OTPInput from "otp-input-react";
import LoadingIndicator from "../../../common/LoadingIndicator2";
import iconGreenCheck from "../../../img/icon/iconGreenCheck.svg";
import iconGreyCheck from "../../../img/icon/iconGreyCheck.svg";
import LoadingIndicatorBasic from "../../../common/LoadingIndicatorBasic";
import PopupSuccessfulAccount from "../Popup/PopupSuccessfulAccount/PopupSuccessfulAccount";
import PopupExceptionAccount from "../Popup/PopupExceptionAccount/PopupExceptionAccount";

const ForgotUsernameModalPotential = (props) => {
    const {maskPhone, maskEmail, resetState, minutes, seconds, transactionId, handleGenOTP, isOpenPopup} = props;
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
    },];

    const handleNext = () => {
        if (currentStep === steps.length - 1) {
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
                FullName: getSession(FULL_NAME),
                UserLogin: getSession(USER_LOGIN),
            }
        };

        signup(verifyOTPRequest)
            .then(response => {
                setLoading(false);

                const {Response} = response;

                if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL') {
                    const {UserName, NewAPIToken} = Response;

                    if (UserName) {
                        // setSession(USER_NAME, UserName);
                        setUserName(UserName);
                        const userNameValidations = getRegisterUserNameValidations(UserName);
                        setUserNameValidations(userNameValidations);
                        // Check if all criteria are met to enable the button
                        const isButtonEnabled = userNameValidations.every((validation) => validation.isValid);
                        setDisabledConfirm(!isButtonEnabled);
                    }

                    if (NewAPIToken) {
                        setSession(API_TOKEN, NewAPIToken);
                    }

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
                    //document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
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

    const processRegistration = () => {
        setLoading(true);
        const registerRequest = {
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
                NewUserName: userName?userName:'',
                DCID: getSession(DCID) && (getSession(DCID) !== 'undefined') ? getSession(DCID) : '',
            }
        };

        signup(removeUndefinedFields(registerRequest))
            .then(response => {
                console.log("AccountChangeUserName", response);
                setLoading(false);
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                    const {Message, NewAPIToken} = Response;

                    if (Message) {
                        const [userLogin, userName] = Message.split('|');
                        setSession(USER_LOGIN, userLogin);
                        setSession(USER_NAME, userName);
                    }

                    if (NewAPIToken) {
                        setSession(API_TOKEN, NewAPIToken);
                    }

                    setLoading(false);
                    props?.clearState();
                    setOpenSuccessfulAccount(true);
                    setTitle('Xác thực OTP');
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
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'CLIEMPTY')) {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                } else {
                    setLoading(false);
                    setOpenExceptionAccount(true);
                }
            })
            .catch(error => {
                console.error("Error occurred during signup:", error);
                // Handle error here, if needed
                setLoading(false);
                setOpenExceptionAccount(true);
            });
    };

    const redirectHome = () => {
        window.location.href = '/';
    };

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
                    {loading && <LoadingIndicatorBasic/>}
                </div>
                {currentStep !== 0 && <div className="signupFlowModal__footer">
                    <button className={`signupFlowModal__customBtn ${disabledConfirm ? 'disabled' : ''}`}
                            disabled={disabledConfirm} onClick={handleNext}>
                        Tiếp tục
                    </button>
                </div>}
            </div>
        </div>
    }
    {openSuccessfulAccount && <PopupSuccessfulAccount onCallBack={() => {
        redirectHome();
    }}/>}
    {openExceptionAccount && <PopupExceptionAccount onCallBack={() => setOpenExceptionAccount(false)}/>}

    </>);
};

export default ForgotUsernameModalPotential;

