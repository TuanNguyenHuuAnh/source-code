import React, { useEffect } from 'react';
import { Link } from "react-router-dom";
import '../common/Common.css';
import { SCREENS } from '../constants';

function SuccessReInstamentPopup({ closePopup, policyNo, screen, fee }) {

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
                <div className={fee && (parseFloat(fee) > 0) ? "point-error-card popup-max-352": "point-error-card popup-max-352 padding-bottom-27"}>
                    <div className="close-btn">
                        {screen && (screen === SCREENS.UPDATE_POLICY_INFO) ? (
                            <Link to="/update-policy-info-change" onClick={() => close()}><img src="../../img/icon/close.svg" alt="" /></Link>
                        ) : (
                            <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                        )}
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src="../img/popup/reinstament-success.png" />
                        </div>
                    </div>
                    <div className="content">
                        <h4>Gửi yêu cầu thành công!</h4>
                        {fee && (parseFloat(fee) > 0) ?
                            (<p style={{ padding: '12px', paddingTop: '0px', marginTop: '-8px' }}>Cám ơn Quý khách đã nộp yêu cầu khôi<br />phục hợp đồng cho chúng tôi. Vui lòng<br />đóng phí bảo hiểm tạm tính để khôi phục là <br /> <span className="basic-red basic-bold">{parseInt(fee).toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</span></p>) :
                            (<p style={{ padding: '12px', paddingTop: '0px', marginTop: '-8px' }}>Cám ơn Quý khách đã nộp yêu cầu khôi<br />phục hợp đồng. Chúng tôi sẽ xử lý và liên hệ<br />Quý khách trong thời gian sớm nhất.</p>)
                        }
                        {fee && (parseFloat(fee) > 0) &&
                            <>
                                <Link to={"/mypayment/" + policyNo} style={{ paddingTop: '11px' }}>
                                    <div className="btn-wrapper" style={{ textAlign: 'center' }}>
                                        <a className="btn btn-primary" style={{ justifyContent: 'center', fontWeight: '600' }}>Đóng phí ngay</a>
                                    </div>
                                </Link>
                                <Link to={"/utilities/policy-payment-la"} style={{ marginTop: '20px' }}>
                                    <div className="btn-wrapper" style={{ textAlign: 'center' }}><span className="back" style={{ padding: '16px', color: '#985801', cursor: 'pointer', fontWeight: '600', fontSize: '15px', lineHeight: '18px' }}>Đóng phí bằng hình thức khác</span></div>
                                </Link>
                            </>
                        }

                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default SuccessReInstamentPopup;