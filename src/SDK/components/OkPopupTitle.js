import React, { useEffect } from 'react';
// import '../common/Common.css';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';

function OkPopupTitle({ closePopup, msg, title, imgPath, agreeText, agreeFunc}) {

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
                        <h4  style={{fontSize: '14px', fontWeight: '400', marginBottom: '16px', lineHeight: '1.5'}}>{parse(msg)}</h4>
                    </div>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>{agreeText}</button>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default OkPopupTitle;