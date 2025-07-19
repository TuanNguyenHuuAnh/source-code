import React, { Component } from 'react';
import { getMasterDataByType } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import './SideBar2.css';
import { CLIENT_ID } from '../constants';
import { getSession } from '../util/common';

class PointMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: '0'

    };
  }

  render() {
    const callbackAppHeader = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    const activeSelectedItem = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      // this.props.callbackMenu(menuName, pid);
      // this.props.callbackSubMenu(subMenuName, id);
    }

    return (
      <div className="nav-dropdown__items">
        <div className="nav-dropdown-tab-wrapper">
          <Link className="nav-dropdown-tab" to={"/point-exchange"} onClick={() => activeSelectedItem(true, 'ah3', 'p1', 'Điểm thưởng', 'Đổi điểm thưởng')}><span className={this.props.activeIndex === 'p1' ? 'bold' : ''} id='p1'>Đổi điểm thưởng</span></Link>
          {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
            <Link className="nav-dropdown-tab" to={"/gift-cart"} onClick={() => activeSelectedItem(true, 'ah3', 'p2', 'Điểm thưởng', 'Giỏ quà của tôi')}><span className={this.props.activeIndex === 'p2' ? 'bold' : ''} id='p2'>Giỏ quà của tôi</span></Link>
          }
          {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
            <Link className="nav-dropdown-tab" to={"/point-history"} onClick={() => activeSelectedItem(true, 'ah3', 'p3', 'Điểm thưởng', 'Lịch sử điểm thưởng')}><span className={this.props.activeIndex === 'p3' ? 'bold' : ''} id='p3'>Lịch sử điểm thưởng</span></Link>
          }
        </div>
      </div>
    )
  }
}

export default PointMenu;
