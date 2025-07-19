import React, { useEffect } from 'react';
import {FE_BASE_URL} from '../sdkConstant';
import parse from 'html-react-parser';

function AlertMsgTitlePopup({ closePopup, imgPath, go, title, msg, agreeText, notAgreeText, notAgreeFunc }) {

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
        // closePopup();
    }

    const notAgree = () => {
        notAgreeFunc();
        // closePopup();
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
                <div className="point-error-card" style={{paddingBottom: '26px'}}>
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{paddingTop: '24px'}}>
                        {title && 
                        <h4 style={{fontSize: '16px', fontWeight: '700'}}>{title}</h4>
                        }
                        <h4  style={{fontSize: '16px', fontWeight: '500', marginBottom: '10px'}}>{parse(msg)}</h4>
                    </div>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>{agreeText}</button>
                        {notAgreeFunc ? (
                            <button className="btn btn-nobg" onClick={() => notAgree()}>{notAgreeText}</button>
                        ) : (
                            <button className="btn btn-nobg" onClick={() => close()}>{notAgreeText}</button>
                        )}

                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default AlertMsgTitlePopup;