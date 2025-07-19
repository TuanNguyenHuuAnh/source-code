import React, { Component } from 'react';

class PopupNoXML extends Component {
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
    document.getElementById('popup-no-xml').className = "popup special envelop-confirm-popup";
  }
  render() {

    return (
      <div className="popup special envelop-confirm-popup" id="popup-no-xml">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeThanks} /></i>
                </div>
                <div className="envelop-content__body">
                  <p>
                    Quý khách có yêu cầu <br/> nhận Hóa đơn, xin vui lòng <br/> gọi cho chúng tôi theo số <br/> (028) 38 100 888 nhấn <br/> phím 1 để được phục vụ. <br/> Cảm ơn Quý khách.
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
export default PopupNoXML;