import React, { useEffect } from 'react';
import '../common/Common.css';
import { Link } from 'react-router-dom';
import { PREVIOUS_SCREENS, FE_BASE_URL } from '../constants';
import { getSession, removeSession, setSession } from '../util/common';

function PopupAskAuthen({ msg, imgPath, buttonName, linkToGo, screenPath }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        if (getSession(PREVIOUS_SCREENS)) {
            removeSession(PREVIOUS_SCREENS);
        }
        closePopup();
    }

    const closeToGo = () => {
        if (linkToGo === screenPath) {
            topFunction();
        }
        closePopup();
    }

    const topFunction = () => {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

    const closePopup = () => {
        document.getElementById('popup-ask-authen-id').className = "popup special point-error-popup";
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        setSession(PREVIOUS_SCREENS, screenPath);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, []);

    return (

        <div className="popup special point-error-popup" id="popup-ask-authen-id">
            <div className="popup__card">
                <div className="health-popup-card">
                    <div className="close-btn" style={{ marginTop: '-3px', marginRight: '-3px' }}>
                        {screenPath ? (
                            <Link to={screenPath} onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" /></Link>
                        ) : (
                            <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                        )}
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <p style={{ marginLeft: '16px', marginRight: '16px' }}>{msg ? msg : ''}</p>
                        <div className="btn-wrapper">
                            <Link className="btn btn-primary" to={linkToGo} onClick={() => closeToGo()}>{buttonName}</Link>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>

    )
}

export default PopupAskAuthen;