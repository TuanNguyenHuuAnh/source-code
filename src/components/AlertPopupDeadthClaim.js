import React, { useEffect } from 'react';
import '../common/Common.css';

function AlertPopupShort({ closePopup, msg, imgPath }) {

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

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn">
                        <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <p>Chức năng không thực hiện được do <br/> Quý khách đang có Yêu cầu giải quyết <br/> quyền lợi bảo hiểm</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AlertPopupShort;