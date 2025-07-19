import React, { Component } from 'react';
import { Link } from "react-router-dom";
import './PopupContinue.css';
import { formatMoney } from '../util/common';
import { LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID } from '../constants';

class PopupExchangeMobileCardConfirmSucess extends Component {
  constructor(props) {
    super(props);
    this.closeExchangeMobileCardSuccessEsc = this.closeExchangeMobileCardSuccessEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeExchangeMobileCardSuccessEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeExchangeMobileCardSuccessEsc, false);
  }
  closeExchangeMobileCardSuccessEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeExchangeMobileCardSuccess();
    }

  }
  closeExchangeMobileCardSuccess = () => {
    document.getElementById('exchange-mobile-card-popup-success').className = "popup refund-confirm-popup special";
  }
  render() {
    const selectedSubMenu = (id, subMenuName) => {
      // setSession(LINK_SUB_MENU_NAME, subMenuName);
      // setSession(LINK_SUB_MENU_NAME_ID, id);
    }
    return (
      <div className="popup refund-confirm-popup special" id="exchange-mobile-card-popup-success">
        <div className="popup__card">
          <div className="refund-confirm-card">
            <div className="close-btn">
              <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeExchangeMobileCardSuccess} />
            </div>
            <div className="picture-wrapper">
              <div className="picture">
                <img src="../img/popup/doi-diem-thuong.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content">
              <p>
                Quý khách đã chọn đổi <span className="basic-red basic-bold special">{formatMoney(this.props.usePoint)} điểm thưởng</span> cho
                <span className="basic-bold">&nbsp;Thẻ điện thoại {this.props.network} trị giá {this.props.price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')}</span>
              </p>
              <div className="yellow-tab">
                <div className="yellow-tab__item">
                  <p>Số điểm còn lại</p>
                  <p>{formatMoney(this.props.point)} điểm</p>
                </div>
              </div>
              <Link to={"/gift-cart"} onClick={() => selectedSubMenu('p2', 'Giỏ quà của tôi')}>
                <div className="btn-wrapper">
                  <a className="btn btn-primary">Tới giỏ quà</a>
                </div>
              </Link>
              <Link to={"/category/" + this.props.categorycd} onClick={this.closeExchangeMobileCardSuccess}>
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
export default PopupExchangeMobileCardConfirmSucess;