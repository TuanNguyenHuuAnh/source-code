import React, { Component } from 'react';

class PopupThanksAccount extends Component {
  constructor(props) {
    super(props);
    this.closeThanksEsc = this.closeThanksEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closeThanksEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closeThanksEsc, false);
  }
  closeThanksEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closeThanks();
      }

  }     
    closeThanks = () => {
        // document.getElementById('popup-thanks-email').className = "popup special envelop-confirm-popup";
        this.props.onCallBack();
    }
    render() {

        return (
            <div className="popup special envelop-confirm-popup show" id="popup-thanks-account">
            <div className="popup__card">
              <div className="envelop-confirm-card">
                <div className="envelopcard">
                  <div className="envelop-content">
                    <div className="envelop-content__header">
                      <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeThanks}/></i>
                    </div>
                    <div className="envelop-content__body">
                      <p className="basic-bold">ĐĂNG KÝ THÀNH CÔNG</p>
                      <p>&nbsp;</p>
                      <p>
                        Mã khách hàng của Quý khách để sử dụng khi đăng nhập là: <span>{this.props.customerId}</span>
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
export default PopupThanksAccount;