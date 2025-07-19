import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import {SCREENS, PREVIOUS_SCREENS, FE_BASE_URL} from '../sdkConstant';
import {setSession} from '../../util/common';
import parse from 'html-react-parser';

function InfoPopup({ closePopup, msg, screen, imgPath }) {

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

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-authentication">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn" style={{marginTop: '-16px'}}>
                        {screen && (screen === SCREENS.UPDATE_POLICY_INFO) ? (
                            <Link to="/update-policy-info-change" onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" /></Link>
                        ) : (
                            screen && (screen === SCREENS.UPDATE_CONTACT_INFO)?(
                                <Link to="/update-contact-info-change" onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></Link>
                            ):(
                                screen && (screen === SCREENS.PAYMENT_CONTRACT)?(
                                    <Link to="/payment-contract-change" onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></Link>
                                ):(
                                    <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                                )
                            )
                        )}
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} alt="" />
                        </div>
                    </div>
                    <div className="content">
                        {parse(msg ? msg : '')}
                        {/* <p style={{marginLeft: '16px', marginRight: '16px'}}>{msg ? msg : ''}</p> */}
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>

    )
}

export default InfoPopup;