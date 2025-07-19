import React, { Component } from 'react';
import { Link } from "react-router-dom";
import './PopupContinue.css';
 
class PopupOnlyOneGivePointOneTime extends Component {
  constructor(props) {
    super(props);
    this.closeOnlyOneFeeRefundEsc = this.closeOnlyOneFeeRefundEsc.bind(this);
  }

  componentDidMount(){
    document.addEventListener("keydown", this.closeOnlyOneFeeRefundEsc, false);
  }
  componentWillUnmount(){
    document.removeEventListener("keydown", this.closeOnlyOneFeeRefundEsc, false);
  }
  closeOnlyOneFeeRefundEsc=(event)=> {
    if (event.keyCode === 27) {
        this.closeOnlyOneFeeRefund();
    }
    
  } 
  closeOnlyOneFeeRefund = () => {
    document.getElementById('point-error-popup-only-one-give-point-a-time').className = "popup special point-error-popup";
  }
    render() {

        return (
          <div className="popup refund-confirm-popup special" id="point-error-popup-only-one-give-point-a-time">
          <div className="popup__card">
            <div className="refund-confirm-card">
              <div className="close-btn">
                <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn"  onClick={this.closeOnlyOneFeeRefund}/>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src="../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                </div>
              </div>
              <div className="content">
                <p>Quý khách chỉ tặng điểm cho <br/> 1 người thân/lần, vui lòng chọn loại quà khác.</p>
                <Link to={"/gift-cart"} onClick={this.closeOnlyOneFeeRefund}>
                <div className="btn-wrapper">
                  <a className="btn btn-primary">Tới giỏ quà</a>
                </div>
                </Link>
                <div className="btn-wrapper"><span className="back" onClick={this.closeOnlyOneFeeRefund}>Quay lại</span></div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupOnlyOneGivePointOneTime;