import React from 'react';
import '../common/Common.css';
import { FE_BASE_URL } from '../constants';

function ClosePopupConfirm({ closeAllPopup, closePopup }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const closeAll = () => {
        closeAllPopup()
    }

    const topFunction = () => {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }

    // useEffect(() => {
    //     document.addEventListener("keydown", closePopupEsc, false);
    //     setSession(PREVIOUS_SCREENS, screenPath);
    //     return () => {
    //         document.removeEventListener("keydown", closePopupEsc, false);
    //     }
    // }, [msg]);

    return (

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="health-popup-card shadow_box">
                    <div className="close-btn" style={{ marginTop: '-3px', marginRight: '-3px' }}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src="../img/popup/no-policy-new.svg" />
                        </div>
                    </div>
                    <div className="content">
                        <p style={{
                            fontWeight : "600", 
                            fontSize: "22px", 
                            textAlign: "center", 
                            marginBottom: "10px",
                            fontSize: "1.6rem"}}>Quý khách muốn thoát?</p>    
                        <p style={{ marginLeft: '16px', 
                                    marginRight: '16px', 
                                    fontSize: '1.4rem',
                                    fontFamily: 'Inter, sans-serif'}}>Toàn bộ những thông tin đã khai báo sẽ <br></br> không được lưu trữ.</p>
                        <div className="btn-wrapper">
                            <div className='btn btn-primary ' onClick={() => close()}>Ở lại</div>
                            <div className='btn btn-nobg' style={{
                                color: "#985801" , 
                                height: "14px",
                                marginBottom: "4px"}}
                                onClick={() => closeAll()}>Thoát</div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>

    )
}

export default ClosePopupConfirm;