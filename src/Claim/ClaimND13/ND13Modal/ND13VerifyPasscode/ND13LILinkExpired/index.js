import React from 'react';
import iconAlertTimeExpired from '../../../../../img/icon/iconAlertTimeExpired.svg';
import './styles.css';

const ND13LILinkExpired = (props) => {

    return (<section className="alert-expired-wrapper">
        <div className="alert-wrapper">
            <div className="alert-container">
                <div className="alert-info">
                    <div className="alert-info__body">
                        <img src={iconAlertTimeExpired} alt="Time Expired Alert Icon"/>
                        <p>
                            Quý khách đã xác nhận thông tin hoặc đã hết thời gian thực hiện. Quý khách vui lòng liên hệ Bên mua bảo hiểm hoặc Tổng đài Dai-ichi Life Việt Nam để biết thêm chi tiết.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </section>);
};

export default ND13LILinkExpired;
