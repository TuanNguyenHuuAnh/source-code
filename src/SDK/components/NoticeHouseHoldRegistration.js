import React, { useEffect } from 'react';
// import '../common/Common.css';

function NoticeHouseHoldRegistration({ closePopup, imgPath }) {

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
                    <div className="close-btn" style={{marginTop: '-17px'}}>
                        <img src="img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content">
                        <div className="list__item">
                            <p style={{fontSize: '13px', textAlign: 'left'}}>Quý khách vui lòng bổ sung Quyết định cải chính hộ tịch hoặc Giấy xác nhận của UBND Xã/ Phường (cần được chứng thực về nội dung) khi:</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{fontSize: '13px', textAlign: 'left'}}>Thay đổi Họ/Tên</p>
                        </div>
                        <div className="list__item">
                            <div className="dot"></div>
                            <p style={{fontSize: '13px', textAlign: 'left'}}>Thay đổi từ 2 thông tin cá nhân trở lên (gồm Họ/Tên và Số giấy tờ tùy thân hoặc Họ/Tên và Ngày tháng năm sinh hoặc Số giấy tờ tùy thân và Ngày tháng năm sinh)</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default NoticeHouseHoldRegistration;