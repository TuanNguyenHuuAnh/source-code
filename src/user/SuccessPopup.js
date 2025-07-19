import React, {useEffect} from 'react';
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
    FE_BASE_URL
} from "../constants";
import {checkAccountInfo, genOTP, submitAccountUpdate, verifyOTP} from '../util/APIUtils';
import {
    getDeviceId,
    getSession,
    maskEmail,
    maskPhoneNumber,
    setSession,
    showMessage,
    VALID_EMAIL_REGEX
} from "../util/common";

const SuccessPopup = ({ onCallBack, msgHeader , msgContent }) => {
    useEffect(() => {
        const closeThanksEsc = (event) => {
            if (event.keyCode === 27) {
                closeThanks();
            }
        };

        document.addEventListener("keydown", closeThanksEsc, false);

        return () => {
            document.removeEventListener("keydown", closeThanksEsc, false);
        };
    }, []);

    const closeThanks = () => {
        onCallBack();
    };

    return (
        <div className="popup special envelop-confirm-popup show" id="popup-thanks-email">
            <div className="popup__card">
              <div className="envelop-confirm-card">
                <div className="envelopcard">
                  <div className="envelop-content">
                    <div className="envelop-content__header">
                      <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={closeThanks}/></i>
                    </div>
                    <div className="envelop-content__body">
                      <p className="basic-bold">{msgHeader}</p>
                      <p>&nbsp;</p>
                      <p>
                        {msgContent}
                      </p>
                    </div>
                  </div>
                </div>
                <div className="envelopcard_bg">
                  <img src="../img/envelop_nowhite.png" alt="" />
                </div>
              </div>
            </div>
            <div className="popupbg"></div>
        </div>
    );
};

export default SuccessPopup;