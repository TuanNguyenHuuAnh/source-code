import React, { useEffect } from 'react';
// import '../common/Common.css';
import {FE_BASE_URL} from '../sdkConstant'

function ConfirmChangePopup({ closePopup, go, msg, agreeText, notAgreeText, notAgreeFunc, imgPath }) {

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

    const notAgree = () => {
        notAgreeFunc();
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
                <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px', height: '225px' }}>
                    {imgPath &&
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src={imgPath} />
                            </div>
                        </div>
                    }
                    <p>{msg}</p>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>{agreeText}</button>
                        {notAgreeFunc ? (
                            <button className="btn btn-nobg" onClick={() => notAgree()}>{notAgreeText}</button>
                        ) : (
                            <button className="btn btn-nobg" onClick={() => close()}>{notAgreeText}</button>
                        )}

                    </div>
                    <i className="closebtn option-closebtn" style={{ marginTop: '-7px', marginRight: '-7px'}}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={() => close()} /></i>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default ConfirmChangePopup;