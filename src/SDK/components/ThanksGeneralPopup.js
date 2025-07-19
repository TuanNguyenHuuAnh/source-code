import React, { useEffect } from 'react';
import { Link } from "react-router-dom";
import parse from 'html-react-parser';

function ThanksGeneralPopup({ closePopup, msg, screen }) {

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
    }, []);

    return (
      <div className="popup special envelop-confirm-popup show">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  {screen?(
                    <Link to={screen} onClick={()=>close()}><i className="closebtn"><img src="../img/icon/close.svg" alt=""/></i></Link>
                  ):(
                    <img  src="../../img/icon/close-icon.svg" alt="closebtn" className="closebtn" onClick={() => close()} />
                  )}
                  
                </div>
                <div className="envelop-content__body">
                  {msg ? parse(msg) : ''}
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src="../img/envelop_nowhite.png" alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
}

export default ThanksGeneralPopup;