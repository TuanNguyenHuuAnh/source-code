
import {
    getSession,
    removeAccents,
    roundDown,
    setSession,
    getDeviceId
} from "../util/common";
import {
    USER_NAME,
    COMPANY_KEY,
    API_TOKEN,
    AUTHENTICATION,
    USER_LOGIN,
    ACCESS_TOKEN,
    DCID,
    CLIENT_PROFILE
} from '../constants';
import { changePass } from "../util/APIUtils";
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import React, {useEffect, useState} from 'react';
import iconGreenCheck from "../img/icon/iconGreenCheck.svg";
import iconGreyCheck from "../img/icon/iconGreyCheck.svg";
import PopupExistAccount from '../popup/AccountManagementFlow/Popup/PopupExistAccount/PopupExistAccount';
import SuccessPopup from './SuccessPopup';


const ChangeUserNamePopup = ({closePopbtn, successRoute}) => {

    const [userName, setUserName] = useState('');
    const [disabledConfirm, setDisabledConfirm] = useState(true);
    const [loading, setLoading] = useState(false);
    const [openSuccessfulAccount, setOpenSuccessfulAccount] = useState(false);
    const [popupAccountAlreadyExist, setPopupAccountAlreadyExist] = useState({isOpen: false, content:''});
    const [userNameValidations, setUserNameValidations] = useState([{
        label: 'Tên đăng nhập bao gồm từ 5 đến 30 ký tự.', isValid: false
    }, {label: 'Tên đăng nhập viết liền không dấu và không phải là một dãy số.', isValid: false}]);

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
        let inputValue = target.value.trim();

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

        // setSession(USER_NAME, inputValue);

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

        // setSession(USER_NAME, inputValue);

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

    const handleChangeUserName = () => {
        if(getSession(USER_NAME).toLowerCase() == userName.toLowerCase()) {
            closePopup();
        }
        else {
            setLoading(true);
            const newUserName = userName;
            const changeUserNameRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'AccountChangeUserName',
                    APIToken: getSession(ACCESS_TOKEN)?getSession(ACCESS_TOKEN):'',
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    OS: '',
                    OldPassword: "",
                    Password: "", // Password: AES256.encrypt(state.password, COMPANY_KEY2),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    UserName: getSession(USER_NAME)?getSession(USER_NAME):'',
                    NewUserName: newUserName?newUserName:'',
                    Note: "",
                    DCID: getSession(DCID) && (getSession(DCID) !== 'undefined') ? getSession(DCID) : '',
                }
            };        
            changePass(changeUserNameRequest)
                .then(response => {
                    if (response.Response.Result === 'true' && response.Response.ErrLog === 'ACCOUNTCHANGEUSERNAMESUCC') {
                        //reset UserName of ClientProfile
                        const resetUserNameClientProfile = JSON.parse(getSession(CLIENT_PROFILE));
                        resetUserNameClientProfile[0].UserName = newUserName;
                        setSession(CLIENT_PROFILE, JSON.stringify(resetUserNameClientProfile));
                        setSession(USER_NAME, newUserName);
                        setLoading(false);
                        setOpenSuccessfulAccount(true);
                    }
                    else if (response.Response.Result === 'true' && response.Response.ErrLog === "USERNAMEEXIST") {
                        setLoading(false);
                        setPopupAccountAlreadyExist({isOpen: true, 
                                                    content:`Tên đăng nhập này đã được chọn. Quý khách vui lòng sử dụng một tên đăng nhập khác. VD: ${response.Response?.UsernameSuggestion}`})
                    }
                    
                })
                .catch(error => {
                    setLoading(false);
                    setDisabledConfirm(false);
                });
        }
    };

    const callBackSuccessRoute = () => {
        successRoute();
    }

    const closePopup = () => {
        closePopbtn();
    }

    useEffect(() => {
        if (getSession(USER_NAME)) {
            CheckUserName(getSession(USER_NAME));
        }
        setUserName(getSession(USER_NAME));

    }, []);

    return (<>
    <div className="signupFlowModal__overlay">
        <div className="signupFlowModal__container" style={{position : 'relative'}}>
            <div className="close-btn">
                <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn" style={{
                    position : 'absolute',
                    right : '1.8rem',
                    top : '1.8rem'
                }}
                    onClick={() => closePopup()}/>
            </div>

            <div className="signupFlowModal__header">
                <div className="signupFlowModal__title">
                    Tên đăng nhập
                </div>
            </div>

            <div className="signupFlowModal__content">
                <div className="username-wrapper">
                    <p className="username-wrapper-content">Quý khách có thể đặt Tên đăng nhập dễ nhớ theo 
                        mong muốn để sử dụng cho những lần đăng nhập về sau.</p>
                    <div className="input-wrapper-item" style={{marginTop: 12}}>
                        <div className="input special-input-extend password-input">
                            <div className="input__content">
                                <label htmlFor="" style={{textAlign: 'left'}}>Tên đăng nhập</label>
                                <input
                                    placeholder= ''
                                    value={userName}
                                    type="text"
                                    name="userName"
                                    id="new-userName"
                                    onChange={handleInputChange} 
                                    required/>
                            </div>
                        </div>
                    </div>
                {renderValidationUserNameCriteria()}
                </div>
                {loading && <LoadingIndicatorBasic/>}
            </div>

            <div className="signupFlowModal__footer">
                <button className={`signupFlowModal__customBtn ${disabledConfirm ? 'disabled' : ''}`}
                        disabled={disabledConfirm} onClick={handleChangeUserName}>Lưu</button>
            </div>
        </div>
        {popupAccountAlreadyExist?.isOpen && <PopupExistAccount onCallBack={()=> setPopupAccountAlreadyExist({isOpen: false})} content={popupAccountAlreadyExist?.content} />}
        {openSuccessfulAccount && <SuccessPopup onCallBack={() => { callBackSuccessRoute()}} msgHeader={'CẬP NHẬT THÀNH CÔNG'} msgContent={"Quý khách đã thay đổi tên đăng nhập thành công."} />}
    </div>
    </>)
}

export default ChangeUserNamePopup;