import React, { useEffect } from 'react';
import {SCREENS} from '../constants';
import '../common/Common.css';
import { Link } from "react-router-dom";

function ErrorPopup({ closePopup, msg, imgPath, screen }) {

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
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-no-have">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn" style={{marginTop: '-3px', marginRight: '-3px'}}>
                    {screen && (screen === SCREENS.UPDATE_CONTACT_INFO)?(
                        <Link to="/update-contact-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt=""/></Link>
                      ):(
                        <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                      )}  
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                        <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <p>{msg?msg: ''}</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default ErrorPopup;