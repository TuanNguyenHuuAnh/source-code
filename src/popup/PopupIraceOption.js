import React, { useEffect } from 'react';
import '../common/Common.css';

function PopupIraceOption({ firstFunc, secondFunc }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        firstFunc();
        closePopup();
    }

    const notAgree = () => {
        secondFunc();
        closePopup();
    }

    const closePopup = () => {
        document.getElementById('popup-irace-option-id').className = "popup option-popup";
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, []);

    return (
        <div className="popup option-popup" id="popup-irace-option-id">
            <div className="popup__card opt">

                <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px', height: '350px' }}>
                <div className="content">

                    <p className="basic-bold" style={{ textAlign: 'center', fontSize:'15px', lineHeight: '20px' }}>Quý khách đã có tài khoản Dai-ichi Life - Cung Đường Yêu Thương trước đó?</p>
                    <p>Nếu chưa có, Quý khách sẽ tham gia với tư cách <span className='basic-bold'>thành viên mới</span> của Dai-ichi Life -<br/>Cung Đường Yêu Thương.</p>
                    <p>Nếu đã có, Quý khách vui lòng liên kết với Dai-ichi Connect để nhận ngay những thông tin hữu ích được cá nhân hóa từ Dai-ichi Life.</p>
                    </div>
                    <div className="btn-wrapper" style={{paddingTop: '16px'}}>
                        <button className="btn btn-primary"  style={{fontSize:'15px'}} onClick={() => agree()}>Chưa có</button>
                        <button className="btn btn-nobg"  style={{fontSize:'15px'}} onClick={() => notAgree()}>Đã có</button>

                    </div>
                    <i className="closebtn option-closebtn" style={{marginTop:'-8px', marginRight:'-5px'}}><img src="../../img/icon/close.svg" alt="" onClick={() => close()} /></i>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    )
}

export default PopupIraceOption;