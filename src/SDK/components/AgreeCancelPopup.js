import React, { useEffect } from 'react';
// import '../common/Common.css';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';
import LoadingIndicator from '../LoadingIndicator2';

function AgreeCancelPopup({ closePopup, msg, imgPath, agreeText, notAgreeText, agreeFunc, notAgreeFunc, title }) {

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
                        <img style={{maxWidth: '14px'}} src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{paddingTop: '24px'}}>
                        {title && 
                        <h4 style={{fontSize: '16px', fontWeight: '700', textAlign: 'center'}}>{title}</h4>
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
            <div><LoadingIndicator area="submit-init-claim" /></div>
            <div><LoadingIndicator area="epolicy-pdf" /></div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AgreeCancelPopup;