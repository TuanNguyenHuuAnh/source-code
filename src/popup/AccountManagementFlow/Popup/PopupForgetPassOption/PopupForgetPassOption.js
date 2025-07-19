import React, {useState} from 'react';
import illustrationPassword from '../../img/Illustration_Password.svg';
import './PopupForgetPassOption.css';
import {
    FORGOT_BOTH,
    FORGOT_PASSWORD_ONLY,
    FORGOT_PASSWORD_PO,
    FORGOT_PASSWORD_POTENTIAL,
    FORGOT_USERNAME_ONLY
} from "../../../../constants";
import {getSession, setSession} from "../../../../util/common";


const PopupForgetPassOption = () => {
    const [selectedButton, setSelectedButton] = useState(null);

    const handleButtonClick = (buttonId) => {
        setSelectedButton(buttonId);
        setTimeout(() => {
            document.getElementById('popup_forget-pass').className = "popup popup_forget-pass";
            if (getSession(FORGOT_PASSWORD_POTENTIAL)) {
                if (document.getElementById('login-id')) {
                    document.getElementById('login-id').className = "login";
                }
                if (document.getElementById('existed-email-popup')) {
                    document.getElementById('existed-email-popup').className = "popup special existed-email-popup show";
                }
            }
            if (getSession(FORGOT_PASSWORD_PO)) {
                if (document.getElementById('login-id')) {
                    document.getElementById('login-id').className = "login";
                }
                if (document.getElementById('signup-popup-existed')) {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed show";
                }
            }
            if (buttonId === 'password') {
                sessionStorage.removeItem(FORGOT_USERNAME_ONLY);
                sessionStorage.removeItem(FORGOT_BOTH);
                setSession(FORGOT_PASSWORD_ONLY, FORGOT_PASSWORD_ONLY);
            } else if (buttonId === 'username') {
                sessionStorage.removeItem(FORGOT_PASSWORD_ONLY);
                sessionStorage.removeItem(FORGOT_BOTH);
                setSession(FORGOT_USERNAME_ONLY, FORGOT_USERNAME_ONLY);
            } else {
                sessionStorage.removeItem(FORGOT_PASSWORD_ONLY);
                sessionStorage.removeItem(FORGOT_USERNAME_ONLY);
                setSession(FORGOT_BOTH, FORGOT_BOTH);
            }
            setSelectedButton(null);
        }, 1000);
    };

    const closePopbtn = () => {
        if (document.getElementById('popup_forget-pass')) {
            document.getElementById('popup_forget-pass').className = "popup special popup_forget-pass";
        }
        window.location.href = '/';
    }

    return (
        <div className="popup popup_forget-pass" id="popup_forget-pass">
            <div className="popup__card_forget-pass">
                <div className="optioncard">
                    <div className="close-btn">
                        <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn"
                             onClick={() => closePopbtn()}/>
                    </div>
                    <p className="title">Quên thông tin đăng nhập</p>
                    <img src={illustrationPassword} alt="illustrationPassword"/>
                    <p className="description">Quý khách vui lòng chọn thông tin đăng nhập muốn đặt lại.</p>
                    <button
                        className={selectedButton === 'username' ? 'btn selected' : 'btn'}
                        onClick={() => handleButtonClick('username')}
                    >
                        Tên đăng nhập
                    </button>
                    <button
                        className={selectedButton === 'password' ? 'btn selected' : 'btn'}
                        onClick={() => handleButtonClick('password')}
                    >
                        Mật khẩu
                    </button>
                    <button
                        className={selectedButton === 'username-password' ? 'btn selected' : 'btn'}
                        onClick={() => handleButtonClick('username-password')}
                    >
                        Tên đăng nhập & mật khẩu
                    </button>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    );
}

export default PopupForgetPassOption;
