import React, { Component } from 'react';
import '../common/Common.css';

class PopupErrorNotFoundPolicyHolder extends Component {
  constructor(props) {
    super(props);
    this.closeNotFoundPolicyHolderEsc = this.closeNotFoundPolicyHolderEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeNotFoundPolicyHolderEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeNotFoundPolicyHolderEsc, false);
  }
  closeNotFoundPolicyHolderEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeNotFoundPolicyHolder();
    }

  }
  closeNotFoundPolicyHolder = () => {
    document.getElementById('error-popup-not-found-policy-holder').className = "popup error-popup special";
  }
  render() {

    return (
      <div className="popup error-popup special" id="error-popup-not-found-policy-holder">
        <div className="popup__card">
          <div className="error-popup-card">
            <div className="close-btn">
              <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeNotFoundPolicyHolder} />
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="../../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p>Rất tiếc!</p>
              <p className="bigheight">Chúng tôi không tìm thấy hợp đồng nào. <br/> Bạn kiểm tra lại thông tin nhé!</p>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupErrorNotFoundPolicyHolder;