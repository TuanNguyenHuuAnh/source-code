import React, { useEffect } from 'react';
import '../common/Common.css';
import {FE_BASE_URL} from "../constants";

function PayModeNoticePopup({ closePopup, title, msg, imgPath }) {

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

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn" style={{marginTop: '-16px'}}>
                        <img src={`${FE_BASE_URL}/img/icon/close-icon.svg`} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <h4 style={{fontSize: '16px', fontWeight: '700'}}>{title}</h4>
                        <p style={{margin: '0 12px', textAlign: 'center'}}>{msg}</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default PayModeNoticePopup;