import React, {useEffect} from 'react';
import './styles.css';
import {DATA_DECREE} from "../../../sdkConstant";

const ND13InsuranceRequestCancelConfirm = (props) => {
    const {
        onClickExtendBtn = () => {},
        onClickCallBack = () => {},
    } = props;
    useEffect(() => {
        // document.querySelectorAll('.no-track').forEach(button => {
        //     const newButton = button.cloneNode(true);
        //     button.parentNode.replaceChild(newButton, button);
        // });
    }, []);
    return (
        <>
            <div className="popup special show" id="modal-common">
                <div className="popup__card">
                    <div className="modal-common-card">
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src={DATA_DECREE.INSURANCE_REQUEST_CANCEL_CONFIRM_DECREE.image} alt="popup-hosobg" />
                            </div>
                        </div>
                        <div className="content">
                            <p className="modal-title">{DATA_DECREE.INSURANCE_REQUEST_CANCEL_CONFIRM_DECREE.title}</p>
                            <br/>
                            <p className="modal-body-content">
                                {DATA_DECREE.INSURANCE_REQUEST_CANCEL_CONFIRM_DECREE.msg}
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

export default ND13InsuranceRequestCancelConfirm;
