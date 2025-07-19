import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../constants';
import { Link } from 'react-router-dom';
import { CLIENT_ID } from '../constants';
import { getSession } from '../util/common';

class PointMobileMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: '0'

    };
  }

  render() {
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    const callbackAppHeader = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    const activeSelectedItemMoblie = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      // this.props.callbackMenu(menuName, pid);
      // this.props.callbackSubMenu(subMenuName, id);
      hideMobileHeader();
    }
    const hideMobileHeader = () => {
      document.getElementById('header-id').className = logined ? 'logined' : '';
    }

    return (
      <div className="dropdown__items">
        <Link className="mobile-dropdown-tab" to={"/point-exchange"} onClick={() => activeSelectedItemMoblie(true, 'ah3', 'p1', 'Điểm thưởng', 'Đổi điểm thưởng')}><span className={this.props.activeIndex === 'p1' ? 'bold' : ''} id='p1'>Đổi điểm thưởng</span></Link>
        {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
          <Link className="mobile-dropdown-tab" to={"/gift-cart"} onClick={() => activeSelectedItemMoblie(true, 'ah3', 'p2', 'Điểm thưởng', 'Giỏ quà của tôi')}><span className={this.props.activeIndex === 'p2' ? 'bold' : ''} id='p2'>Giỏ quà của tôi</span></Link>
        }
        {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
          <Link className="mobile-dropdown-tab" to={"/point-history"} onClick={() => activeSelectedItemMoblie(true, 'ah3', 'p3', 'Điểm thưởng', 'Lịch sử điểm thưởng')}><span className={this.props.activeIndex === 'p3' ? 'bold' : ''} id='p3'>Lịch sử điểm thưởng</span></Link>
        }
      </div>
    )
  }
}

export default PointMobileMenu;
