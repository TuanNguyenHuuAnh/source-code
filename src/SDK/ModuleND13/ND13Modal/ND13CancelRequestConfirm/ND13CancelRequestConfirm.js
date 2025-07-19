import React from 'react';
import './styles.css';
import {DATA_DECREE} from "../../../sdkConstant";
import iconClose from "../../../img/icon/close-icon.svg";

const ND13CancelRequestConfirm = (props) => {
    const {
        onClickExtendBtn = () => {},
        onClickCallBack = () => {},
    } = props;

    return (
        <>
            <div className="popup special show" id="modal-common">
                <div className="popup__card">
                    <div className="modal-common-card">
                        <div className="picture-wrapper">
                            <div className="modal-header" style={{ background: '#ffffff' }}>
                                <i className="close-btn"> <img src={iconClose} alt="closebtn" className="btn"
                                                               onClick={onClickCallBack}/></i>
                            </div>
                            <div className="picture">
                                <img src={DATA_DECREE.CANCEL_REQUEST_DECREE.image} alt="popup-hosobg" />
                            </div>
                        </div>
                        <div className="content">
                            <p className="modal-title" style={{
                                fontSize: '16px',
                                fontWeight: 700,
                                lineHeight: '22px',
                                textAlign: 'center',

                            }}>{DATA_DECREE.CANCEL_REQUEST_DECREE.title}</p>
                            <p className="modal-body-content" style={{
                                padding: '0 30px',
                                fontSize: '14px',
                                fontWeight: 500,
                                lineHeight: '21px',
                                textAlign: 'center',
                                marginTop: '8px',
                            }}>
                                {DATA_DECREE.CANCEL_REQUEST_DECREE.msg}
                            </p>
                            <div className="btn-wrapper">
                            <button className="btn btn-confirm" onClick={onClickCallBack}>Quay lại</button>
                            <button className="btn btn-callback" onClick={onClickExtendBtn}>Hủy yêu cầu</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>
        </>
    );
};

export default ND13CancelRequestConfirm;
