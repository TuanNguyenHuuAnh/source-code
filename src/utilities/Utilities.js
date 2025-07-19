// import 'antd/dist/antd.min.css';
// import '../claim.css';
import React, { Component } from "react";

import {ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION, COMPANY_KEY, FE_BASE_URL, PageScreen} from "../constants";
import { CPSaveLog } from '../util/APIUtils';
import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import { Helmet } from "react-helmet";
import { Link } from "react-router-dom";


export const UTILITY_LIST = Object.freeze({
  SO_TAY: -1,
  MANG_LUOI: 0,
  THAM_GIA_BH: 1,
  DONG_PHI_BH: 2,
  GIAI_QUYET_QLBH: 3,
  GIAO_DICH_HD: 4,
  LS_VA_GIA_QUY: 5,
  BIEU_MAU: 6,
  FAQ: 7,
  THOA_THUAN: 8,
  ABOUT: 9,
  CONTACT: 10,
  BANG_PHAN_NHOM_NGHE: 11,
  Y_KIEN: 12,
  GOP_Y: 13,
  CHINH_SACH: 14,
  INITIAL: 99
});

class Utilities extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Project: "mcp",
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
        },
      },
      currentUtility: null,
      renderMeta: false
    };

    this.handlerClickUtility = this.clickOnIcon.bind(this);
  }

  componentDidMount() {
    const jsonState = this.state;
    jsonState.currentUtility = UTILITY_LIST.INITIAL;
    this.setState(jsonState);
    this.cpSaveLog("Tiện ích");
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

  showPotentialErr = () => {
    document.getElementById('error-popup-potential-feedback').className = 'popup error-popup special show';
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

  clickOnIcon(utilityIdx) {
    const jsonState = this.state;
    jsonState.currentUtility = utilityIdx;
    this.setState(jsonState);
    switch (utilityIdx) {
      case UTILITY_LIST.SO_TAY:
        this.props.history.push("/khach-hang-can-biet");
        break;
      case UTILITY_LIST.MANG_LUOI:
        this.props.history.push("/utilities/network");
        break;
      case UTILITY_LIST.THAM_GIA_BH:
        this.props.history.push("/utilities/participate");
        break;
      case UTILITY_LIST.DONG_PHI_BH:
        this.props.history.push("/utilities/policy-payment");
        break;
      case UTILITY_LIST.GIAI_QUYET_QLBH:
        this.props.history.push("/utilities/claim-guide");
        break;
      case UTILITY_LIST.GIAO_DICH_HD:
        this.props.history.push("/utilities/policy-trans");
        break;
      case UTILITY_LIST.LS_VA_GIA_QUY:
        window.open("https://dai-ichi-life.com.vn/thong-tin-quy-dau-tu");
        break;
      case UTILITY_LIST.BIEU_MAU:
        this.props.history.push("/utilities/document");
        break;
      case UTILITY_LIST.FAQ:
        this.props.history.push("/utilities/faq");
        break;
      case UTILITY_LIST.THOA_THUAN:
        this.props.history.push("/terms-of-use");
        break;
      case UTILITY_LIST.CHINH_SACH:
        this.props.history.push("/privacy-policy");
        break;
      case UTILITY_LIST.ABOUT:
        window.open("https://www.dai-ichi-life.com.vn/vi-VN/dai-ichi-life-viet-nam/303/");
        break;
      case UTILITY_LIST.CONTACT:
        this.props.history.push("/utilities/contact");
        break;
      case UTILITY_LIST.BANG_PHAN_NHOM_NGHE:
        this.props.history.push("/utilities/occupation");
        break;
      case UTILITY_LIST.GOP_Y:
        if (getSession(CLIENT_ID)) {
          this.props.history.push("/utilities/feedback");
        } else {
          this.showPotentialErr();
        }
        break;
      case UTILITY_LIST.Y_KIEN:
        break;
      default:
        break;
    }

  }

  render() {
    var jsonState = this.state;
    return (
      <>
        {this.state.renderMeta &&
          <Helmet>
            <title>Tiện ích – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Trang thông tin cung cấp nhiều tiện ích khác nhau giúp khách hàng hiểu rõ hơn về các sản phẩm bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities"} />
            <meta property="og:title" content="Tiện ích - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin cung cấp nhiều tiện ích khác nhau giúp khách hàng hiểu rõ hơn về các sản phẩm bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities"} />
          </Helmet>
        }
        <main className={isLoggedIn() ? "logined" : ""}>
          <div className="main-wrapper">
            {/* Route display */}
            <section className="scbreadcrums">
              <div className="container">
                <div className="breadcrums basic-white">
                    <Link to="/" className="breadcrums__item">
                        <p>Trang chủ</p>
                        <p className='breadcrums__item_arrow'>></p>
                    </Link>
                    <div className="breadcrums__item">
                        <p>Tiện ích</p>
                        <p className='breadcrums__item_arrow'>></p>
                    </div>
                </div>
              </div>
            </section>
            {/* Banner */}
            <div className="scdieukhoan scdanhsachtienich">
              <div className="container">
                <h1>Tiện ích</h1>
              </div>
            </div>
            {/* Main page */}
            {
              (jsonState.currentUtility === UTILITY_LIST.INITIAL || 1) &&
              <section className="utilities-container">
                <div className="utilities-menu-wrapper">
                  {/* Mạng lưới */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.MANG_LUOI)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-mangluoi.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Mạng lưới</h2>
                    </div>
                  </div>
                  {/* Tham gia bảo hiểm */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.THAM_GIA_BH)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-thamgiabaohiem.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Tham gia<br/>bảo hiểm</h2>
                    </div>
                  </div>
                  {/* Đóng phí bảo hiểm */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.DONG_PHI_BH)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-dongphibaohiem.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Đóng phí<br/>bảo hiểm</h2>
                    </div>
                  </div>
                  {/* Giải quyết quyền lợi bảo hiểm */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.GIAI_QUYET_QLBH)} >
                    <div className="icon">
                      <img
                        src="img/icon/10.1/10.1-icon-giaiquyetquyenloi.svg"
                        alt=""
                      />
                    </div>
                    <div className="content">
                      <h2>Giải quyết<br/>quyền lợi<br/>bảo hiểm</h2>
                    </div>
                  </div>
                  {/* Giao dịch hợp đồng */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.GIAO_DICH_HD)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-giaodichhopdong.png" alt="" />
                    </div>
                    <div className="content">
                      <h2>Giao dịch<br/>hợp đồng</h2>
                    </div>
                  </div>
                  {/* Lãi suất và giá đơn vị quỹ */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => {
                    this.handlerClickUtility(UTILITY_LIST.LS_VA_GIA_QUY);
                    this.cpSaveLog(`Web_Open_${PageScreen.RATE_FUND_UNIT}`);
                    trackingEvent(
                        "Tiện ích",
                        `Web_Open_${PageScreen.RATE_FUND_UNIT}`,
                        `Web_Open_${PageScreen.RATE_FUND_UNIT}`,
                    );

                  }} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-laisuat.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Lãi suất và giá<br/>đơn vị quỹ</h2>
                    </div>
                  </div>
                  {/* Biểu mẫu */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.BIEU_MAU)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-bieumau.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Biểu mẫu</h2>
                    </div>
                  </div>
                  {/* TODO 3.7.8 */}
                  {/* Phân nhóm nghề */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.BANG_PHAN_NHOM_NGHE)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-bangphannhom.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Phân nhóm<br/>nghề</h2>
                    </div>
                  </div>
                  {/* Khách hàng cần biết */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.SO_TAY)} >
                    <div className="icon">
                      <img src="img/icon/10.1/icon-handbook.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Khách hàng cần biết</h2>
                    </div>
                  </div>
                  {/* Câu hỏi thường gặp */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.FAQ)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-cauhoi.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Câu hỏi<br/>thường gặp</h2>
                    </div>
                  </div>
                  {/* Góp ý */}
                  {getSession(ACCESS_TOKEN) &&
                    <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.GOP_Y)} >
                      <div className="icon">
                        <img src="img/icon/10.1/gopy.svg" alt="" />
                      </div>
                      <div className="content">
                        <h2>Góp ý</h2>
                      </div>
                    </div>
                  }
                  {/* Điều khoản sử dụng */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.THOA_THUAN)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-dieukhoan.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Điều khoản<br/>sử dụng</h2>
                    </div>
                  </div>
                  {/* Chính sách bảo mật */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.CHINH_SACH)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-privacy.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Chính sách<br/>bảo mật</h2>
                    </div>
                  </div>
                  {/* Về Dai-ichi Life Việt Nam */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.ABOUT)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-daiichi.svg" alt="" />
                    </div>
                    <div className="content">
                      <h2>Về Dai-ichi<br/>Life Việt Nam</h2>
                    </div>
                  </div>
                  {/* Liên hệ */}
                  <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.CONTACT)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-lienhe.svg" alt="" />
                    </div>
                    <div className="content">
                      <p>Liên hệ</p>
                    </div>
                  </div>
                  {/* TODO 3.7.8 */}
                  {/* Ý kiến khách hàng */}
                  {/* <div className="ultilitie__item" style={{ cursor: 'pointer' }} onClick={() => this.handlerClickUtility(UTILITY_LIST.Y_KIEN)} >
                    <div className="icon">
                      <img src="img/icon/10.1/10.1-icon-ykienkhachhang.svg" alt="" />
                    </div>
                    <div className="content">
                      <p>Ý kiến khách hàng</p>
                    </div>
                  </div> */}
                </div>
              </section>
            }
          </div>
        </main>
      </>
    );
  }
}

export default Utilities;
