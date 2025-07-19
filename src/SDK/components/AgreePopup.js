import React, { useEffect } from 'react';
// import '../common/Common.css';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';

function AgreePopup({ closePopup, msg, agreeText, agreeFunc}) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        agreeFunc();
        // closePopup();
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (
        <div className="popup option-popup  show">
        <div className="popup__card">
            <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px', height: '180px', paddingTop: '22px' }}>
                <p>{msg}</p>
                <div className="btn-wrapper">
                    <button className="btn btn-primary" onClick={() => agree()}>{agreeText}</button>
                </div>
                <i className="closebtn option-closebtn" style={{ marginTop: '-7px', marginRight: '-7px'}}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={() => close()} /></i>
            </div>
        </div>
        <div className="popupbg"></div>
        </div>
    )
}

export default AgreePopup;