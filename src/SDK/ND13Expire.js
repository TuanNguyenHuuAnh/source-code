import React from 'react';
import iconAlertTimeExpired from './img/icon/iconAlertTimeExpired.svg';
// import './styles.css';

const ND13Expire = (props) => {

    return (<section className="alert-expired-wrapper">
        <div className="alert-wrapper">
            <div className="alert-container">
                <div className="alert-info">
                    <div className="alert-info__body">
                        <img src={iconAlertTimeExpired} alt="Time Expired Alert Icon"/>
                        <p>
                            Liên kết với thông báo này không còn hiệu lực.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </section>);
};

export default ND13Expire;
