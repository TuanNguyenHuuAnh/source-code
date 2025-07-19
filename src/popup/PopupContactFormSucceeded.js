import React, { Component } from 'react';

class PopupContactFormSucceeded extends Component {
  constructor(props) {
    super(props);


    this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
    this.handlerSetCloseButtonRef = this.setCloseButtonRef.bind(this);
    this.handlerClosePopup = this.closePopup.bind(this);
  }

  componentDidMount() {
    if (this.props.isSucceeded) {
      document.addEventListener('mousedown', this.handlerClosePopup);
      document.getElementById('popup-claim-form-succeeded').className = "popup special envelop-confirm-popup show";
    } else {
      document.getElementById('popup-claim-form-succeeded').className = "popup special envelop-confirm-popup";
    }
  }

  componentDidUpdate() {
    if (this.props.isSucceeded) {
      document.addEventListener('mousedown', this.handlerClosePopup);
      document.getElementById('popup-claim-form-succeeded').className = "popup special envelop-confirm-popup show";
    } else {
      document.getElementById('popup-claim-form-succeeded').className = "popup special envelop-confirm-popup";
    }
  }

  setWrapperRef(node) {
    this.wrapperSucceededRef = node;
  }

  setCloseButtonRef(node) {
    this.closeButtonRef = node;
  }

  closePopup(event) {
    if (this.closeButtonRef && this.wrapperSucceededRef) {
      if (this.closeButtonRef.contains(event.target)) {
        document.getElementById("popup-claim-form-succeeded").className = "popup special envelop-confirm-popup";
        document.removeEventListener('mousedown', this.handlerClosePopup);
        this.props.handlerClosePopup();
      } else if (!this.wrapperSucceededRef.contains(event.target)) {
        document.getElementById("popup-claim-form-succeeded").className = "popup special envelop-confirm-popup";
        document.removeEventListener('mousedown', this.handlerClosePopup);
        this.props.handlerClosePopup();
      }
    }
  }

  render() {
    return (
      <div className="popup special envelop-confirm-popup" id="popup-claim-form-succeeded">
        <div className="popup__card">
          <div className="envelop-confirm-card" ref={this.handlerSetWrapperRef}>
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn" ref={this.handlerSetCloseButtonRef}>
                    <img src="/img/icon/close.svg" alt="" />
                  </i>
                </div>
                <div className="envelop-content__body">
                  <p>Cảm ơn Quý khách đã gửi yêu cầu liên hệ</p>
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src="/img/envelop_nowhite.png" alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>
    )
  }
}
export default PopupContactFormSucceeded;
