import React, { useEffect } from 'react';
// import '../common/Common.css';
import {SCREENS} from '../sdkConstant';
import { Link } from "react-router-dom";

function AlertPopupError({ closePopup, msg, imgPath, screen }) {

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
                <div className="point-error-card" style={{paddingTop: '33px'}}>
                    <div className="close-btn" style={{ marginTop: '-10px', marginRight: '-8px'}}>
                      {screen && (screen === SCREENS.UPDATE_POLICY_INFO)?(
                        <Link to="/update-policy-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt=""/></Link>
                      ):(
                        screen && (screen === SCREENS.UPDATE_CONTACT_INFO)?(
                            <Link to="/update-contact-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt=""/></Link>
                        ):(
                            <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                        )
                      )}  
                        
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{ marginLeft: '5px', marginRight: '5px'}}>
                        <p>{msg?msg: ''}</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AlertPopupError;