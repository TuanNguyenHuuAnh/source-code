import React, {useEffect, useState} from 'react';
import './styles.css';
import TroubleIcon from '../../../../img/icon/PrevampAccount/Trouble.svg';

const TroubleDetectedModal = () => {
    const [isOpenPopup, setOpenPopup] = useState(true);

    const closeOptionPopupEsc = (event) => {
        if (event.keyCode === 27) {
            closeOptionPopup();
        }
    };

    const closeOptionPopup = () => {
        setOpenPopup(false);
        window.location.href= '/';
    };

    useEffect(() => {
        document.addEventListener("keydown", closeOptionPopupEsc, false);

        return () => {
            document.removeEventListener("keydown", closeOptionPopupEsc, false);
        };
    }, []);


    return (
        <>
            {isOpenPopup && <div className="popup special show" id="modal-common">
                <div className="popup__card">
                    <div className="modal-common-card">
                        <div className="close-btn">
                            <img src="../../../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={closeOptionPopup} />
                        </div>
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src={TroubleIcon} alt="popup-TroubleIcon" />
                            </div>
                        </div>
                        <div className="content">
                            <p className="modal-trouble-title">Có lỗi xác thực thông tin</p>
                            <p className="modal-trouble-content">
                                Quý khách vui lòng liên hệ <br /> <span>(028) 3810 0888</span> hoặc gửi yêu cầu để được hỗ trợ.
                            </p>
                            <div className="btn-wrapper">
                                <button className="btn btn-confirm" onClick={closeOptionPopup}>Đóng</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>}
        </>
    );
};

export default TroubleDetectedModal;
