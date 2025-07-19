import React, { Component } from 'react';

class PopupException extends Component {
  constructor(props) {
    super(props);


    this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
    this.handlerSetCloseButtonRef = this.setCloseButtonRef.bind(this);
    this.handlerClosePopup = this.closePopup.bind(this);
  }

  componentDidMount() {
    if (this.props.hasError) {
      document.addEventListener('mousedown', this.handlerClosePopup);
      document.getElementById('popup-exception').className = "popup special point-error-popup show";
    } else {
      document.getElementById('popup-exception').className = "popup special point-error-popup";
    }
  }

  componentDidUpdate() {
    if (this.props.hasError) {
      document.addEventListener('mousedown', this.handlerClosePopup);
      document.getElementById('popup-exception').className = "popup special point-error-popup show";
    } else {
      document.getElementById('popup-exception').className = "popup special point-error-popup";
    }
  }

  setWrapperRef(node) {
    this.wrapperSucceededRef = node;
  }

  setCloseButtonRef(node) {
    this.closeButtonRef = node;
  }

  closePopup(event) {
    // if (this.closeButtonRef && this.wrapperSucceededRef
    //   && (!this.wrapperSucceededRef.contains(event.target) || this.closeButtonRef.contains(event.target))
    //   && document.getElementById("popup-exception").className === "popup special point-error-popup show") {
    //   document.getElementById("popup-exception").className = "popup special point-error-popup";
    //   document.removeEventListener('mousedown', this.handlerClosePopup);
    // }
    if (this.closeButtonRef && this.wrapperSucceededRef) {
      if (this.closeButtonRef.contains(event.target)) {
        document.getElementById("popup-exception").className = "popup special point-error-popup";
        document.removeEventListener('mousedown', this.handlerClosePopup);
        this.props.handlerClosePopup();
      } else if (!this.wrapperSucceededRef.contains(event.target)) {
        document.getElementById("popup-exception").className = "popup special point-error-popup";
        document.removeEventListener('mousedown', this.handlerClosePopup);
        this.props.handlerClosePopup();
      }
    }
  }

  render() {
    const closePopbtn=()=>{
      document.getElementById('popup-exception').className = "popup special point-error-popup";
    }
    return (
      <div className="popup special point-error-popup" id="popup-exception">
        <div className="popup__card">
          <div className="point-error-card" ref={this.handlerSetWrapperRef}>
            <div className="close-btn" ref={this.handlerSetCloseButtonRef}>
              <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn" onClick={()=>closePopbtn()}/>
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="/img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p>Rất tiếc!</p>
              <p>Có lỗi xảy ra, Quý khách vui lòng thử lại sau.</p>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupException;
