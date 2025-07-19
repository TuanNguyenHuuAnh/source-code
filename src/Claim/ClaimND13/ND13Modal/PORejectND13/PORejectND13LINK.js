import React, {useEffect, useState} from 'react';
import './styles.css';
import {DATA_DECREE} from "../../../../constants";
import iconClose from "../../../../img/icon/close-icon.svg";

const PORejectND13LINK = (props) => {
    const {
        onClickConfirmBtn = () => {},
        onClickCallBack = () => {},
        confirmLabel = 'Thực hiện lại',
        callBackLabel = 'Hủy yêu cầu',
    } = props;

    const [isOpenPopup, setOpenPopup] = useState(true);

    const closeOptionPopupEsc = (event) => {
        if (event.keyCode === 27) {
            closeOptionPopup();
        }
    };

    const closeOptionPopup = () => {
        setOpenPopup(false);
        onClickConfirmBtn();
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
                        <div className="modal-header" style={{ background: '#ffffff' }}>
                            <i className="close-btn"> <img src={iconClose} alt="closebtn" className="btn"
                                                           onClick={closeOptionPopup}/></i>
                        </div>
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src={DATA_DECREE.PROFILE_WARNING.image} alt="popup-hosobg" />
                            </div>
                        </div>
                        <div className="content">
                            <p className="modal-title" style={{
                                fontSize: '14px',
                                fontWeight: 700,
                                lineHeight: '20px',
                                textAlign: 'center',

                            }}>{DATA_DECREE.PROFILE_WARNING.title}</p>
                            <p className="modal-body-content" style={{
                                fontSize: '14px',
                                fontWeight: 500,
                                lineHeight: '20px',
                                textAlign: 'center',
                                marginTop: 8

                            }}>
                                {DATA_DECREE.PROFILE_WARNING.msg}
                                {/* <span className="modal-body-link"> bhchamsocsuckhoe@dai-ichi-life.com.vn</span>. */}
                            </p>
                            <div className="btn-wrapper">
                                <button className="btn btn-confirm" onClick={onClickConfirmBtn}>{confirmLabel}</button>
                                <button className="btn btn-callback" onClick={onClickCallBack}>{callBackLabel}</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>}
        </>
    );
};

export default PORejectND13LINK;
