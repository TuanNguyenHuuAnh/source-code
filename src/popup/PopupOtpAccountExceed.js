import React, { Component } from 'react';
class PopupOtpAccountExceed extends Component {
    constructor(props) {
        super(props);
        this.closeOptionPopupEsc = this.closeOptionPopupEsc.bind(this);
    }

    componentDidMount() {
        document.addEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    componentWillUnmount() {
        document.removeEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    closeOptionPopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeOptionPopup();
        }

    }
    closeOptionPopup() {
        document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup";
        window.location.href = '/';
    }
    render() {
        return (


            <div className="popup special point-error-popup" id="option-popup-account-exceed-otp">
                <div className="popup__card">
                    <div className="phone-confirm-card">
                        <div className="close-btn" style={{marginTop: '-16px'}}>
                            <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeOptionPopup} />
                        </div>
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src="../img/icon/9.2/9.2-shield.svg" alt="popup-hosobg" />
                            </div>
                        </div>
                        <div className="content">
                            <p>Rất tiếc!</p>
                            <p>Quý khách đã sử dụng hết số lượng OTP cho phép trong một ngày. Vui lòng thử lại vào ngày hôm sau. Xin cảm ơn.</p>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}
export default PopupOtpAccountExceed;