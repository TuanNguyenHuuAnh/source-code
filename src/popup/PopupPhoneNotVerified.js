import React, { Component } from 'react';
import { Link } from "react-router-dom";
 
class PopupPhoneNotVerified extends Component {
  constructor(props) {
    super(props);
    this.closePhoneConfirmEsc = this.closePhoneConfirmEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closePhoneConfirmEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closePhoneConfirmEsc, false);
  }
  closePhoneConfirmEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closePhoneConfirm();
      }

  } 
  closePhoneConfirm = () => {
    document.getElementById('phone-confirm-popup-id').className = "popup special phone-confirm-popup";
  }
    render() {

        return (
          <div className="popup special phone-confirm-popup" id="phone-confirm-popup-id">
          <div className="popup__card">
            <div className="phone-confirm-card">
              <div className="close-btn">
                <img src="img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closePhoneConfirm}/>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src="img/icon/9.2/9.2-shield.svg" alt="popup-hosobg" />
                </div>
              </div>
              <div className="content">
                <p>Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến</p>
                <Link to={"/account"} ><button className="btn btn-primary">Xác thực</button></Link>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupPhoneNotVerified;