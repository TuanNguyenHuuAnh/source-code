import React, { useEffect } from 'react';
// import '../common/Common.css';
import {FE_BASE_URL, SCREENS} from '../sdkConstant';

function AlertPopup({ closePopup, msg, imgPath, screen }) {

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

        <div className="alert popup special point-error-popup show">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture" style={{minHeight: '210px'}}>
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{marginLeft: '5px', marginRight: '5px'}}>
                        <p>{msg?msg: ''}</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AlertPopup;