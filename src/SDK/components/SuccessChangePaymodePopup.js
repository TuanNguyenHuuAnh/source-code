import React, { useEffect } from 'react';
import { Link } from "react-router-dom";
// import '../common/Common.css';
import {SCREENS, SUBMISSION_TYPE_MAPPING} from '../sdkConstant';

function SuccessChangePaymodePopup({ closePopup, policyNo, screen,  proccessType }) {

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
    }, []);

    return (

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-alert">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn">
                    {screen && (screen === SCREENS.UPDATE_POLICY_INFO)?(
                        <Link to="/update-policy-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt=""/></Link>
                      ):(
                        <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                      )}  
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src="../img/popup/change-fund-rate-success.png" />
                        </div>
                    </div>
                    <div className="content">
                        <h4>Gửi yêu cầu thành công!</h4>
                        <p style={{padding: '12px'}}>Cám ơn Quý khách đã nộp yêu cầu {SUBMISSION_TYPE_MAPPING[proccessType]}. Quý khách có thể đóng phí bảo hiểm hoặc tham khảo các hình thức đóng phí bên dưới.</p>
                        <Link to={"/mypayment/" + policyNo} style={{ paddingTop: '1px' }}>
                            <div className="btn-wrapper" style={{textAlign: 'center'}}>
                                <a className="btn btn-primary" style={{justifyContent:'center'}}>Đóng phí trực tuyến</a>
                            </div>
                        </Link>
                        <Link to={"/utilities/policy-payment-la"} style={{ marginTop: '20px' }}>
                            <div className="btn-wrapper" style={{textAlign: 'center'}}><span className="back" style={{padding: '16px', color: '#985801', cursor: 'pointer', fontWeight: '600px', fontSize: '15px', lineHeight: '18px'}}>Đóng phí bằng hình thức khác</span></div>
                        </Link>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default SuccessChangePaymodePopup;