import React, { useEffect } from 'react';
// import '../common/Common.css';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';

function AgreeChangePopup({ closePopup, msg, imgPath, agreeText, notAgreeText, agreeFunc, notAgreeFunc }) {

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

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-alert">
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
                        <h4  style={{fontSize: '16px', fontWeight: '700', marginBottom: '10px'}}>Quý khách có chắc chắn chấm dứt quyền lợi này không?</h4>

                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{textAlign: 'left', fontSize: '13px'}}>Khi chấm dứt Quyền lợi điều trị Nội trú, Quyền lợi điều trị Ngoại trú và Quyền lợi Chăm sóc răng (nếu có) cũng được chấm dứt tương ứng.</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{textAlign: 'left', fontSize: '13px'}}>Khi chấm dứt Quyền lợi điều trị Ngoại trú, Quyền lợi Chăm sóc răng (nếu có) cũng được chấm dứt tương ứng.</p>
                        </div>


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
            <div className="popupbg"></div>
        </div>


    )
}

export default AgreeChangePopup;