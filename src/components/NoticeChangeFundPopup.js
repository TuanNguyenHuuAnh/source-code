import React, { useEffect } from 'react';
import '../common/Common.css';

function NoticeChangeFundPopup({ closePopup, msg, imgPath }) {

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
                <div className="point-error-card">
                    <div className="close-btn">
                        <img src="img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <h4 style={{fontSize: '16px', fontWeight: '700'}}>Lưu ý khi chuyển đổi quỹ</h4>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{fontSize: '13px'}}>Quý khách được miễn phí 04 lần chuyển đổi quỹ trong mỗi năm hợp đồng</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{fontSize: '13px'}}>Số tiền chuyển đổi Quỹ tối thiểu mỗi lần là 1.000.000 đồng (một triệu đồng)</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{fontSize: '13px'}}>Phí chuyển đổi Quỹ từ lần mua thứ 05 trở đi trong mỗi năm hợp đồng tối thiếu 50.000 đồng/lần và được khấu trừ từ Giá trị của Quỹ chuyển đổi trước khi mua đơn vị Quỹ mới.</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default NoticeChangeFundPopup;