import React, { useEffect } from 'react';
import '../common/Common.css';

function NoticeChangeRatePopup({ closePopup, msg, imgPath }) {

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

        <div className="popup special point-error-popup show" id="point-error-popup-sorry-alert">
            <div className="popup__card">
                <div className="point-error-card" style={{paddingBottom: '26px'}}>
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        <img src="img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <h4  style={{fontSize: '16px', fontWeight: '700', marginBottom: '10px'}}>Lưu ý khi thay đổi tỷ lệ đầu tư</h4>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{textAlign: 'left', fontSize: '13px'}}>Thay đổi tỷ lệ đầu tư vào các Quỹ có thể thực hiện bất kỳ lúc nào.</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{textAlign: 'left', fontSize: '13px'}}>Tỷ lệ đầu tư mới vào mỗi Quỹ là bội số của 5% và tổng tỷ lệ đầu tư vào các Quỹ phải là 100%</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{textAlign: 'left', fontSize: '13px'}}>Quý khách sẽ được miễn phí thay đổi tỷ lệ đầu tư giữa các Quỹ</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default NoticeChangeRatePopup;