import React, { useState, useEffect } from 'react';
import '../common/Common.css';
import {FE_BASE_URL} from '../constants';


function ExceptionPopup({closeOtp }) {
    const [wrapperSucceededRef, setWrapperSucceededRef] = useState(null);
    const [closeButtonRef, setCloseButtonRef] = useState(null);
    const closeOtpPopupEsc = (event) => {
        if (event.keyCode === 27) {
            closeOtpPopup();
        }

    }
    const closeOtpPopup = () => {
        closeOtp();
    }

    const closePopup=(event)=> {
        if (closeButtonRef && wrapperSucceededRef) {
          if (this.closeButtonRef.contains(event.target)) {
            document.removeEventListener("mousedown", closeOtpPopup);
            closeOtpPopup();
          } else if (!wrapperSucceededRef.contains(event.target)) {
            document.removeEventListener("mousedown", closeOtpPopup);
            closeOtpPopup();
          }
        }
      }
    useEffect(() => {
        document.addEventListener("mousedown", closeOtpPopup);
        document.addEventListener("keydown", closeOtpPopupEsc, false);
        return () => {
            document.removeEventListener("mousedown", closeOtpPopup);
            document.removeEventListener("keydown", closeOtpPopupEsc, false);
        }
    }, []);

    return (

        <div className="popup special point-error-popup show" id="exception-popup-id">
        <div className="popup__card">
          <div className="point-error-card" ref={setWrapperSucceededRef}>
            <div className="close-btn" ref={setCloseButtonRef}>
              <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="closebtn" onClick={()=>closeOtpPopup()}/>
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="/img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p>Rất tiếc!</p>
              <p>Có lỗi xảy ra, Quý khách vui lòng thử lại sau.</p>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>
            

    )
}

export default ExceptionPopup;