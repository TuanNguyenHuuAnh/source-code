import React, { Component } from 'react';
import './PopupCloseButton.css';
class PopupNoClaimBenefit extends Component {
    closePopupNoClaimBenefit() {
        document.getElementById('step-start-popup').className = "popup step-start-popup";
    }

    render() {
        return (
            <div className="popup step-start-popup" id="step-start-popup">
                <div className="popup__card">
                    <div className="step-start-popup-information">
                        {/* <div className="step-start-popup-information-close-btn" onClick={this.closePopupNoClaimBenefit}>
                            <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn" />
                        </div> */}
                        <div className="step-start-popup-information-title-picture">
                            <img src="/img/popup/quyenloi-popup.svg" alt="popup-popup" />
                        </div>
                        <div className="step-start-popup-information-content">
                            <div className="step-start-popup-information-content-header">
                                Người được bảo hiểm không có quyền lợi phát sinh.
                            </div>
                            <div className="step-start-popup-information-content-full">
                                <p>
                                    Quý khách vui lòng liên hệ Tổng đài Dịch vụ<br />
                                    Khách hàng (028)38100888 để được hỗ trợ
                                </p>
                            </div>
                        </div>
                        <div className="chose-again-btn">
                            <button className="btn btn-primary" style={{ justifyContent: 'center' }}
                                onClick={this.closePopupNoClaimBenefit}>Chọn lại</button>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div >
        )
    }
}

export default PopupNoClaimBenefit;