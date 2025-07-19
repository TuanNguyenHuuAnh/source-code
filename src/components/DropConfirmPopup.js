import React, { useEffect } from 'react';
import { FE_BASE_URL } from '../constants';


function DropConfirmPopup({ closePopup, go, msg }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        go();
        closePopup();
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
                <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px', height: '180px', maxWidth: '340px', paddingTop: '0' }}>
                    <p>{msg}</p>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>Đồng ý</button>
                    </div>
                    <i className="closebtn option-closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={() => close()} /></i>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default DropConfirmPopup;