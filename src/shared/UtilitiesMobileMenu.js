import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {ACCESS_TOKEN, USER_LOGIN, CLIENT_ID, FE_BASE_URL, PageScreen, AUTHENTICATION} from '../constants';
import {getDeviceId, getSession, trackingEvent, isMobile} from '../util/common';
import {CPSaveLog} from "../util/APIUtils";

class UtilitiesMobileMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: '0'

    };
  }

    cpSaveLog(functionName) {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN)
            }
        }
        CPSaveLog(masterRequest).then(res => {
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }

  render() {
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    const callbackAppHeader = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    const activeSelectedItemUtilMobile = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      // this.props.callbackMenu(menuName, pid);
      // this.props.callbackSubMenu(subMenuName, id);
      hideMobileHeader();
    }
    const activeSelectedItemUtilMobileExternal = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      this.props.callbackMenu(menuName, pid);
      this.props.callbackSubMenu(subMenuName, id);
      hideMobileHeader();
    }
    const hideMobileHeader = () => {
      document.getElementById('header-id').className = logined ? 'logined' : '';
    }
    const showPotentialErr = ()=> {
      document.getElementById('error-popup-potential-feedback').className = 'popup error-popup special show';
    }
    return (
      <div className="dropdown__items">
        <Link className="mobile-dropdown-tab" to={"/utilities/network"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u1', 'Tiện ích', 'Mạng lưới')}><span className={this.props.activeIndex === 'u1' ? 'bold' : ''} id='u1'>Mạng lưới</span></Link>
        <Link className="mobile-dropdown-tab" to={"/utilities/participate"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u2', 'Tiện ích', 'Tham gia bảo hiểm')}><span className={this.props.activeIndex === 'u2' ? 'bold' : ''} id='u2'>Tham gia bảo hiểm</span></Link>
        <Link className="mobile-dropdown-tab" to={"/utilities/policy-payment"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u3', 'Tiện ích', 'Đóng phí bảo hiểm')}><span className={this.props.activeIndex === 'u3' ? 'bold' : ''} id='u3'>Đóng phí bảo hiểm</span></Link>
        <Link className="mobile-dropdown-tab" to={"/utilities/claim-guide"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u4', 'Tiện ích', 'Giải quyết quyền lợi bảo hiểm')}><span className={this.props.activeIndex === 'u4' ? 'bold' : ''} id='u4'>Giải quyết quyền lợi bảo hiểm</span></Link>
        <Link className="mobile-dropdown-tab" to={"/utilities/policy-trans"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u5', 'Tiện ích', 'Giao dịch hợp đồng')}><span className={this.props.activeIndex === 'u5' ? 'bold' : ''} id='u5'>Giao dịch hợp đồng</span></Link>

        <a className="mobile-dropdown-tab" href="https://dai-ichi-life.com.vn/thong-tin-quy-dau-tu"
          target='_blank' rel='noreferrer' onClick={() => {
            activeSelectedItemUtilMobileExternal(true, 'ah5', 'u6', 'Tiện ích', 'Lãi suất và giá quỹ đơn vị');
            this.cpSaveLog(`Web_Open_${PageScreen.RATE_FUND_UNIT}`);
            trackingEvent(
                "Tiện ích",
                `${PageScreen.RATE_FUND_UNIT}`,
                `${PageScreen.RATE_FUND_UNIT}`,
            );
        }}>
          <span className={this.props.activeIndex === 'u6' ? 'bold' : ''} id='u6'>Lãi suất và giá quỹ đơn vị</span>
        </a>

        <Link className="mobile-dropdown-tab" to={"/utilities/document"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u7', 'Tiện ích', 'Biểu mẫu')}><span className={this.props.activeIndex === 'u7' ? 'bold' : ''} id='u7'>Biểu mẫu</span></Link>
        <Link className="mobile-dropdown-tab" to={"/utilities/occupation"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u11', 'Tiện ích', 'Phân nhóm nghề')}><span className={this.props.activeIndex === 'u11' ? 'bold' : ''} id='u11'>Phân nhóm nghề</span></Link>
        {isMobile()?(
          <Link className="mobile-dropdown-tab" to={"/khach-hang-can-biet"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u0', 'Tiện ích', 'Khách hàng cần biết')}><span className={this.props.activeIndex === 'u0' ? 'bold' : ''} id='u0'>Khách hàng cần biết</span></Link>
        ):(
          <a className="mobile-dropdown-tab" href='/khach-hang-can-biet'
             target='_blank' rel='noreferrer' onClick={() => {
              this.cpSaveLog(`Web_Open_${PageScreen.CUSTOMER_NEED_KNOW}`);
              trackingEvent(
                  "Tiện ích",
                  `${PageScreen.CUSTOMER_NEED_KNOW}`,
                  `${PageScreen.CUSTOMER_NEED_KNOW}`,
              );
              activeSelectedItemUtilMobileExternal(true, 'ah5', 'u0', 'Tiện ích', 'Khách hàng cần biết');
          }}>
              <span className={this.props.activeIndex === 'u0' ? 'bold' : ''} id='u0'>Khách hàng cần biết</span>
          </a>
        )}
        <Link className="mobile-dropdown-tab" to={"/utilities/faq"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u8', 'Tiện ích', 'Câu hỏi thường gặp')}><span className={this.props.activeIndex === 'u8' ? 'bold' : ''} id='u8'>Câu hỏi thường gặp</span></Link>
        {getSession(ACCESS_TOKEN) && (
              getSession(CLIENT_ID)?(
                <Link className="mobile-dropdown-tab" to={"/utilities/feedback"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u13', 'Tiện ích', 'Góp ý')}><span className={this.props.activeIndex === 'u13' ? 'bold' : ''} id='u13'>Góp ý</span></Link>
              ): (
                <Link className="mobile-dropdown-tab" to={window.location.pathname} onClick={() => showPotentialErr()}><span className={this.props.activeIndex === 'u13' ? 'bold' : ''} id='u13'>Góp ý</span></Link>
              )
        )
        }
          <a className="mobile-dropdown-tab" href={`${FE_BASE_URL + '/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/huong-dan-su-dung-dai-ichi-Connect_v2.pdf'}`}
             target='_blank' rel='noreferrer' onClick={() => {
              this.cpSaveLog(`Web_Open_${PageScreen.USER_MANUAL}`);
              trackingEvent(
                  "Tiện ích",
                  `${PageScreen.USER_MANUAL}`,
                  `${PageScreen.USER_MANUAL}`,
              );
              activeSelectedItemUtilMobileExternal(true, 'ah5', 'u14', 'Tiện ích', 'Hướng dẫn sử dụng Dai-ichi Connect');
          }}>
              <span className={this.props.activeIndex === 'u14' ? 'bold' : ''} id='u14'>Hướng dẫn sử dụng <br/>Dai-ichi Connect</span>
          </a>

        <Link className="mobile-dropdown-tab" to={"/terms-of-use"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u9', 'Tiện ích', 'Điều khoản sử dụng')}><span className={this.props.activeIndex === 'u9' ? 'bold' : ''} id='u9'>Điều khoản sử dụng</span></Link>
        <Link className="mobile-dropdown-tab" to={"/privacy-policy"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u9_', 'Tiện ích', 'Điều khoản sử dụng')}><span className={this.props.activeIndex === 'u9_' ? 'bold' : ''} id='u9_'>Chính sách bảo mật</span></Link>
        <a className="mobile-dropdown-tab" href="https://www.dai-ichi-life.com.vn/vi-VN/dai-ichi-life-viet-nam/303/"
          target='_blank' rel='noreferrer' onClick={() => {
            activeSelectedItemUtilMobileExternal(true, 'ah5', 'u10', 'Tiện ích', 'Về Dai-ichi Life Việt Nam');
            this.cpSaveLog(`Web_Open_${PageScreen.ABOUT_DLVN}`);
            trackingEvent(
                "Home",
                `${PageScreen.ABOUT_DLVN}`,
                `${PageScreen.ABOUT_DLVN}`,
            );
          }}>
          <span className={this.props.activeIndex === 'u10' ? 'bold' : ''} id='u10'>Về Dai-ichi Life Việt Nam</span>
        </a>

        <Link className="mobile-dropdown-tab" to={"/utilities/contact"} onClick={() => activeSelectedItemUtilMobile(true, 'ah5', 'u12', 'Tiện ích', 'Liên hệ')}><span className={this.props.activeIndex === 'u12' ? 'bold' : ''} id='u12'>Liên hệ</span></Link>
        
      </div>
    )
  }
}

export default UtilitiesMobileMenu;
