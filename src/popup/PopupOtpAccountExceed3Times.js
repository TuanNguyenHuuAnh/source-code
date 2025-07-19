import React, {Component} from 'react';
import { FE_BASE_URL } from '../constants';

class PopupOtpAccountExceed3Times extends Component {
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
        document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup";
    }

    render() {
        return (


            <div className="popup special point-error-popup" id="option-popup-account-exceed-otp-3-times">
                <div className="popup__card">
                    <div className="phone-confirm-card">
                        <div className="close-btn">
                            <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn"
                                 onClick={this.closeOptionPopup}/>
                        </div>
                        <div className="picture-wrapper">
                            <div className="picture">
                                <img src={FE_BASE_URL + "/img/icon/9.2/9.2-shield.svg"} alt="popup-hosobg"/>
                            </div>
                        </div>
                        <div className="content">
                            {/*<p style={{ fontWeight: 'bold' }}>Rất tiếc!</p>*/}
                            <p>Thông tin nhập đã sai quá 5 lần. Chức năng tạm ngừng hoạt động trong 30 phút.</p>
                            {/* <div className="btn-wrapper">
                                <button className="btn btn-primary" id="existed" onClick={this.closeOptionPopup}>Đóng
                                </button>
                            </div> */}
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}

export default PopupOtpAccountExceed3Times;