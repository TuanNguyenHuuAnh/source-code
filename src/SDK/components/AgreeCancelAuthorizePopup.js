import React, { useEffect } from 'react';
// import '../common/Common.css';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';
import LoadingIndicator from '../LoadingIndicator2';

function AgreeCancelAuthorizePopup({ closePopup, imgPath, agreeText, notAgreeText, agreeFunc, notAgreeFunc }) {

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            close();
        }

    }
    const close = () => {
        closePopup();
    }

    const agree = () => {
        agreeFunc();
        // closePopup();
    }

    const notAgree = () => {
        notAgreeFunc();
        // closePopup();
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, []);

    return (

        <div className="popup special point-error-popup show">
            <div className="popup__card">
                <div className="point-error-card" style={{paddingBottom: '26px'}}>
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        <img style={{maxWidth: '14px'}} src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{paddingTop: '24px'}}>
                        <h4 style={{fontSize: '14px', fontWeight: '700', textAlign: 'center', lineHeight: '1.5'}}>Yêu cầu này sẽ không thể thực hiện tiếp nếu không có sự xác nhận</h4>
                        <div style={{fontSize: '12px', fontWeight: '500', marginBottom: '10px', border: '1px solid #E2E2E2', borderRadius: '6px'}}>
                            <p style={{fontSize: '12px', textAlign: 'left', padding: '4px 12px'}}>Quý khách vui lòng Thực hiện lại, hoặc hủy Yêu cầu này và nộp lại Yêu cầu theo ba cách sau :</p>
                            <p style={{fontSize: '12px', textAlign: 'left', padding: '4px 12px'}}>1. Nộp Yêu cầu trên Dai-ichi Connect</p>
                            <p style={{fontSize: '12px', textAlign: 'left', padding: '4px 12px'}}>2. Nộp Yêu cầu tại Văn phòng/Tổng Đại lý</p>
                            <p style={{fontSize: '12px', textAlign: 'left', padding: '4px 12px'}}>3. Đề nghị Đại lý làm lại Yêu cầu</p>
                        </div>
                        <p style={{fontSize: '12px', padding: '4px 8px'}}>Trường hợp cần trao đổi thêm, Quý khách vui lòng liên hệ Tổng đài (028) 38 100 888 hoặc thư điện tử
                            <span>
                                <a style={{fontSize: '12px'}} href="mailto: bhchamsocsuckhoe@dai-ichi-life.com.vn" className="simple-brown2">
                                    bhchamsocsuckhoe@dai-ichi-life.com.vn
                                </a>
                            </span>
                        </p>
                    </div>
					<div className="btn-wrapper">
                        <button className="btn btn-primary" onClick={() => agree()}>{agreeText}</button>
                        {notAgreeFunc ? (
                            <button className="btn btn-nobg" onClick={() => notAgree()}>{notAgreeText}</button>
                        ) : (
                            <button className="btn btn-nobg" onClick={() => close()}>{notAgreeText}</button>
                        )}

                    </div>
                </div>
            </div>
            <div><LoadingIndicator area="submit-init-claim" /></div>
            <div><LoadingIndicator area="epolicy-pdf" /></div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AgreeCancelAuthorizePopup;