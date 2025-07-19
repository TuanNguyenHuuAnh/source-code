import React, { Component } from 'react';

class PopupErrorPotentialFeedback extends Component {
  constructor(props) {
    super(props);
    this.closeOnlyForExixtedEsc = this.closeOnlyForExixtedEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeOnlyForExixtedEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeOnlyForExixtedEsc, false);
  }
  closeOnlyForExixtedEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeOnlyForExixted();
    }

  }
  closeOnlyForExixted = () => {
    document.getElementById('error-popup-potential-feedback').className = "popup error-popup special";
  }
  render() {

    return (
      <div className="popup error-popup special" id="error-popup-potential-feedback">
        <div className="popup__card">
          <div className="error-popup-card">
            <div className="close-btn">
              <img src="../img/icon/close-icon.svg" alt="closebtn" className="closebtn" onClick={this.closeOnlyForExixted} />
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p className="bigheight">Chức năng dành cho Khách hàng có hợp đồng với Dai-ichi Life Việt Nam. Vui lòng đăng nhập bằng Mã khách hàng do công ty cung cấp để gửi Góp ý</p>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupErrorPotentialFeedback;