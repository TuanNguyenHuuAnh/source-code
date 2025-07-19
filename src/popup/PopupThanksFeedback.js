import React, { Component } from 'react';

class PopupThanksFeedback extends Component {
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
    document.getElementById('popup-thanks-feedback').className = "popup special envelop-confirm-popup";
  }
  render() {

    return (
      <div className="popup special envelop-confirm-popup" id="popup-thanks-feedback">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeThanks} /></i>
                </div>
                <div className="envelop-content__body">
                  <p>
                    Cảm ơn Quý khách <br/> đã gửi góp ý
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
export default PopupThanksFeedback;