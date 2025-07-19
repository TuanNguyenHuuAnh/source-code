import React, { Component } from 'react';
import { Link } from "react-router-dom";
import './PopupContinue.css';
import { formatMoney } from '../util/common';
import { LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID } from '../constants';

class PopupGiftWellnessConfirmSuccess extends Component {
  constructor(props) {
    super(props);
    this.closeRefundConfirmSuccessEsc = this.closeRefundConfirmSuccessEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeRefundConfirmSuccessEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeRefundConfirmSuccessEsc, false);
  }
  closeRefundConfirmSuccessEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeGiftWellnessConfirmSuccess();
    }

  }
  closeGiftWellnessConfirmSuccess = () => {
    document.getElementById('gift-wellness-confirm-popup-success').className = "popup gift-wellness-confirm-popup special";
  }
  render() {
    const selectedSubMenu = (id, subMenuName) => {
    }
    return (
      <div className="popup gift-wellness-confirm-popup special" id="gift-wellness-confirm-popup-success">
        <div className="popup__card">
          <div className="refund-confirm-card">
            <div className="close-btn">
              <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeGiftWellnessConfirmSuccess} />
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="../img/popup/doi-diem-thuong.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p>
                Quý khách đã chọn đổi <span className="basic-red basic-bold special">{formatMoney(this.props.usePoint)} điểm thưởng</span> thành <span className="basic-red basic-bold special">{this.props.qty} </span>
                <span className="basic-bold">{this.props.shortDescription}</span>
              </p>
              <div className="yellow-tab" style={{paddingTop: '12px', paddingBottom:'12px'}}>
                <div className="yellow-tab__item">
                  <p>Số điểm còn lại</p>
                  <p>{formatMoney(this.props.point)} điểm</p>
                </div>
              </div>
              <Link to={"/gift-cart"} onClick={() => selectedSubMenu('p2', 'Giỏ quà của tôi')} style={{paddingTop: '1px'}}>
                <div className="btn-wrapper">
                  <a className="btn btn-primary">Tới giỏ quà</a>
                </div>
              </Link>
              <Link to={"/point-exchange"} style={{marginTop: '-3px'}}>
                <div className="btn-wrapper"><span className="back">Tiếp tục chọn quà</span></div>
              </Link>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupGiftWellnessConfirmSuccess;