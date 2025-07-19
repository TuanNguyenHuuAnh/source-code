import React, { useEffect } from 'react';
import '../common/Common.css';
import { Link } from 'react-router-dom';
import { PREVIOUS_SCREENS, FE_BASE_URL } from '../constants';
import { getSession, removeSession, setSession } from '../util/common';
import parse from 'html-react-parser';

function GeneralPopupPartner({ closePopup, msg, imgPath, buttonName, linkToGo, screenPath, showEdoctorLogin }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
        showEdoctorLogin('', '');
    }

    const closeToGo = () => {
        if (linkToGo === screenPath) {
            topFunction();
        }
        closePopup();
    }

    const topFunction = () => {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

    // useEffect(() => {
    //     document.addEventListener("keydown", closePopupEsc, false);
    //     setSession(PREVIOUS_SCREENS, screenPath);
    //     return () => {
    //         document.removeEventListener("keydown", closePopupEsc, false);
    //     }
    // }, [msg]);

    return (

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="health-popup-card">
                    <div className="close-btn" style={{ marginTop: '-3px', marginRight: '-3px' }}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
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
                                <Link className="btn btn-primary" to={linkToGo} onClick={() => closeToGo()}>{buttonName}</Link>
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

export default GeneralPopupPartner;