import React, { useEffect } from 'react';
import '../common/Common.css';
import { Link } from 'react-router-dom';
import {SCREENS, PREVIOUS_SCREENS} from '../constants';
import { setSession } from '../util/common';

function AuthenticationPopupClaim({ closePopup, msg, screen }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }


    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        setSession(PREVIOUS_SCREENS, screen);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="phone-confirm-card">
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        {screen && (screen === SCREENS.UPDATE_POLICY_INFO) ? (
                            <Link to="/update-policy-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt="" /></Link>
                        ) : (
                            screen && (screen === SCREENS.UPDATE_CONTACT_INFO)?(
                                <Link to="/update-contact-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt=""/></Link>
                            ):(
                                <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                            )
                        )}
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src="../../img/icon/9.2/9.2-shield.svg" alt="popup-hosobg" />
                        </div>
                    </div>
                    <div className="content">
                        <p style={{marginLeft: '8px', marginRight: '8px', fontSize: '15px'}}>{msg ? msg : ''}</p>
                        <div className="btn-wrapper">
                            <Link className="btn btn-primary" to={"/account"} onClick={() => close()}>Xác thực ngay</Link>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>

    )
}

export default AuthenticationPopupClaim;