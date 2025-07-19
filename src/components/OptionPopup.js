import React, { useEffect } from 'react';
import '../common/Common.css';

function OptionPopup({ closePopup, firstFunc, msg, firstText, secondText, secondFunc, title }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        firstFunc();
        closePopup();
    }

    const notAgree = () => {
        secondFunc();
        closePopup();
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (
        <div className="popup option-popup show">
            <div className="popup__card opt">

                <div className={title?"optioncard option-card-have-title":"optioncard option-card-no-title"}>
                <div className="content">
                {title &&
                    <h5 className="basic-bold" style={{ textAlign: 'center', paddingTop: '20px', fontSize:'15px' }}>{title}</h5>
                }
                    {msg}
                    </div>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary"  style={{fontSize:'15px'}} onClick={() => agree()}>{firstText}</button>
                        {secondFunc ? (
                            <button className="btn btn-nobg"  style={{fontSize:'15px'}} onClick={() => notAgree()}>{secondText}</button>
                        ) : (
                            <button className="btn btn-nobg"  style={{fontSize:'15px'}} onClick={() => close()}>{secondText}</button>
                        )}

                    </div>
                    <i className="closebtn option-closebtn" style={{marginTop:'-11px', marginRight:'-7px'}}><img src="../../img/icon/close.svg" alt="" onClick={() => close()} /></i>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default OptionPopup;