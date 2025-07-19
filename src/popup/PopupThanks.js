import React, { Component } from 'react';

class PopupThanks extends Component {
  constructor(props) {
    super(props);
    this.closeThanksEsc = this.closeThanksEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeThanksEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeThanksEsc, false);
  }
  closeThanksEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeThanks();
    }

  }
  closeThanks = () => {
    document.getElementById('popup-thanks').className = "popup special envelop-confirm-popup";
  }
  render() {

    return (
      <div className="popup special envelop-confirm-popup" id="popup-thanks">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeThanks} /></i>
                </div>
                <div className="envelop-content__body">
                  <p>
                    Dai-ichi Life Việt Nam cảm ơn Quý khách đã sử dụng dịch vụ. Tài khoản sẽ được gửi đến số điện thoại
                    của Quý khách.
                  </p>
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src="../img/envelop_nowhite.png" alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupThanks;