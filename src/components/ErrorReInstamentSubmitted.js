import React, { useEffect } from 'react';
import {SCREENS, FE_BASE_URL} from '../constants';
import '../common/Common.css';
import { Link } from "react-router-dom";
import parse from 'html-react-parser';

function ErrorReInstamentSubmitted({ closePopup, msg, imgPath, screen, forceContinue }) {

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

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-no-have">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-3px'}}>
                    {screen && (screen === SCREENS.REINSTATEMENT)?(
                        <Link to="/reinstatement-change" onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></Link>
                      ):(
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                      )}  
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                        <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <p>{msg ? parse(msg) : ''}</p>

                        <div className="btn-wrapper" style={{textAlign: 'center', paddingTop: '20px'}}>
                            <a className="btn btn-primary" style={{textAlign: 'center', justifyContent: 'center'}} onClick={()=>forceContinue()}>Tiếp tục</a>
                        </div>

                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default ErrorReInstamentSubmitted;