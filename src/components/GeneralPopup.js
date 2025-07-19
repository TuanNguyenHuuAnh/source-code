import React, { useEffect } from 'react';
import '../common/Common.css';
import { Link, useHistory } from 'react-router-dom';
import { PREVIOUS_SCREENS, FE_BASE_URL, DOCTOR_ID } from '../constants';
import { getSession, removeSession, setSession } from '../util/common';
import parse from 'html-react-parser';

function GeneralPopup({ closePopup, msg, imgPath, buttonName, linkToGo, screenPath, doctorId }) {

    const history = useHistory();

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        if (getSession(PREVIOUS_SCREENS)) {
            removeSession(PREVIOUS_SCREENS);
        }
        closePopup();

    }

    const closeToGo = () => {
        if (linkToGo === screenPath) {
            topFunction();
        }
        closePopup();
        if (linkToGo) {
            if(doctorId !== ''){
                topFunction();
                setSession(DOCTOR_ID, doctorId);
                history.push(linkToGo);
            }
            else{
                history.push(linkToGo);

            }
        }
    }

    const topFunction = () => {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        setSession(PREVIOUS_SCREENS, screenPath);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="health-popup-card">
                    <div className="close-btn" style={{ marginTop: '-3px', marginRight: '-3px' }}>
                        {screenPath ? (
                            <Link to={screenPath} onClick={() => close()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" /></Link>
                        ) : (
                            <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                        )}
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <p style={{ marginLeft: '16px', marginRight: '16px' }}>{msg ? parse(msg) : ''}</p>
                        <div className="btn-wrapper">
                            {linkToGo?(
                                <a className="btn btn-primary" onClick={() => closeToGo()}>{buttonName}</a>
                            ):(
                                <a className="btn btn-primary" onClick={() => closeToGo()}>{buttonName}</a>
                            )}
                            
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>

    )
}

export default GeneralPopup;