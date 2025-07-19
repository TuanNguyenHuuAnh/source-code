import React, { useEffect } from 'react';
// import '../common/Common.css';
import { FE_BASE_URL, PREVIOUS_SCREENS } from '../sdkConstant';
import { setSession } from '../sdkCommon';

function ConfirmPopup({ closePopup, go, msg }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        go();
        closePopup();
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [msg]);

    return (
        <div className="popup option-popup  show">
            <div className="popup__card">
                <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px', height: '225px' }}>
                    <p>Dữ liệu đã điền sẽ bị mất nếu quay lại, Quý khách có đồng ý?</p>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>Đồng ý</button>
                        <button className="btn btn-nobg" onClick={() => close()}>Không đồng ý</button>
                    </div>
                    <i className="closebtn option-closebtn" style={{ marginTop: '-11px', marginRight: '-7px'}}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={() => close()} /></i>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default ConfirmPopup;