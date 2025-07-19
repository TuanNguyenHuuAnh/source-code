// import 'antd/dist/antd.css';
// import '../claim.css';
import './utilities.css';
import React, { Component } from "react";
import { Link } from 'react-router-dom';
import { CPSaveLog } from '../util/APIUtils';
import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import {CLIENT_ID, FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, USER_LOGIN, PageScreen} from "../constants";
import AlertPopupInvalidUser from '../components/AlertPopupInvalidUser';
import { Helmet } from "react-helmet";

export const TABS = Object.freeze({
  CONTACT_CHANGE: 0,
  POLICY_PAYMENT: 1,
});

class PolicyTrans extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentTab: TABS.CONTACT_CHANGE,
      invalidTypeUser: false,
      renderMeta: false
    };

    this.handlerChangeTab = this.onChangeTab.bind(this);
    this.handlerCreateNewRequest = this.onClickCreateNewRequest.bind(this);

  }

  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.GUIDE_CONTRACT_TRANS}`);
    trackingEvent(
        "Tiện ích",
        `Web_Open_${PageScreen.GUIDE_CONTRACT_TRANS}`,
        `Web_Open_${PageScreen.GUIDE_CONTRACT_TRANS}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.GUIDE_CONTRACT_TRANS}`);
    trackingEvent(
        "Tiện ích",
        `Web_Close_${PageScreen.GUIDE_CONTRACT_TRANS}`,
        `Web_Close_${PageScreen.GUIDE_CONTRACT_TRANS}`,
    );
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

  onChangeTab(tabId) {
    var jsonState = this.state;
    jsonState.currentTab = tabId;
    this.setState(jsonState);
  }

  onClickCreateNewRequest() {
    if (isLoggedIn()) {
      if (getSession(CLIENT_ID) === null
        || getSession(CLIENT_ID) === undefined
        || getSession(CLIENT_ID) === '') {
        document.getElementById("error-popup-only-for-existed").className = "popup error-popup special show";
      } else {
        if (this.state.currentTab === TABS.POLICY_PAYMENT) {
          this.props.history.push("/payment-contract");
        } else {
          this.props.history.push("/update-policy-info");
        }
      }
    } else {
      this.props.history.push("/");
    }
  }


  render() {
    var jsonState = this.state;
    let msgPopup = '';
    let popupImgPath = '';
    if (jsonState.invalidTypeUser) {
      msgPopup = 'Chức năng chỉ dành cho khách hàng cá nhân. Quý khách vui lòng liên hệ văn phòng Dai-ichi Life Việt Nam để được hướng dẫn.';
      popupImgPath = '../../img/popup/no-policy.svg';
    }
    const closeInvalidUserType = () => {
      this.setState({ invalidTypeUser: false });
    }
    return (
      <>
        {this.state.renderMeta &&
          <Helmet>
            <title>Giao dịch hợp đồng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Thông tin về giao dịch hợp đồng, bao gồm điều chỉnh và thanh toán hợp đồng các sản phẩm bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Giao dịch hợp đồng" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities/policy-trans"} />
            <meta property="og:title" content="Giao dịch hợp đồng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Thông tin về giao dịch hợp đồng, bao gồm điều chỉnh và thanh toán hợp đồng các sản phẩm bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities/policy-trans"} />
          </Helmet>
        }
        <main className={isLoggedIn() ? "logined" : ""}>
          {/* <main className="basic-expand-footer"> */}
          <div className="main-warpper page-ten">
            {/* Route display */}
            <section className="scbreadcrums">
              <div className="container">
                <div className="breadcrums basic-white">
                  <Link to="/" className="breadcrums__item">
                      <p>Trang chủ</p>
                      <p className='breadcrums__item_arrow'>></p>
                  </Link>
                  <Link to="/utilities" className="breadcrums__item">
                      <p>Tiện ích</p>
                      <p className='breadcrums__item_arrow'>></p>
                  </Link>

                  <div className="breadcrums__item">
                    <p>Giao dịch hợp đồng</p>
                    <span>&gt;</span>
                  </div>
                </div>
              </div>
            </section>
            {/* Banner */}
            <section className="scdieukhoan scquyenloibaohiem">
              <div className="container">
                <h1>Giao dịch hợp đồng</h1>
              </div>
            </section>
            {/* Main page */}
            <div className="gdhd-page10-page">
              <div className="tab-pane-container">
                <section className="policy-menu">
                  <button className={jsonState.currentTab === TABS.CONTACT_CHANGE ? "policy-menu__item active" : "policy-menu__item"}
                          onClick={() => this.handlerChangeTab(TABS.CONTACT_CHANGE, '')}>
                    <h2>ĐIỀU CHỈNH HỢP ĐỒNG</h2>
                  </button>

                  <button className={jsonState.currentTab === TABS.POLICY_PAYMENT ? "policy-menu__item active" : "policy-menu__item"}
                          onClick={() => this.handlerChangeTab(TABS.POLICY_PAYMENT, '')}>
                    <h2>THANH TOÁN HỢP ĐỒNG</h2>
                  </button>

                </section>
                {/* Contents */}
                <section className="tab-pane-container-content">
                  <div id="thoathuan" className={jsonState.currentTab === TABS.CONTACT_CHANGE ? "tabpane-tab active" : "tabpane-tab"}>
                    <div className="tab-pane-container-content-one">
                      <ul className="list-information">
                        <li className="main-menu-li">
                          Quý khách kiểm tra thông tin hợp đồng, nếu có thông tin nào chưa chính xác, cần liên hệ Tư vấn
                          hoặc trực tiếp Dai-ichi Life Việt Nam để được hướng dẫn thủ tục cập nhật kịp thời.
                        </li>
                        <li className="main-menu-li">
                          Quý khách có thể yêu cầu điều chỉnh/bổ sung các chi tiết của hợp đồng. Mỗi loại sản phẩm có thể có
                          một số điều kiện để được điều chỉnh/bổ sung được quy định chi tiết trong Quy tắc và Điều khoản sản
                          phẩm. Các điều chỉnh/bổ sung có thể gồm:
                          <br />
                          <ul className="sub-list-2">
                            <li className="sub-list-2-li">Thông tin nhân thân của Bên mua bảo hiểm, Người được bảo hiểm</li>
                            <li className="sub-list-2-li">
                              Người thụ hưởng Chuyển nhượng hợp đồng (thay đổi bên mua bảo hiểm)
                            </li>
                            <li className="sub-list-2-li">
                              Các chi tiết liên quan đến sản phẩm, phí bảo hiểm:
                              <br />
                              <div className="small-list">
                                <p>- Định kỳ đóng phí</p>
                                <p>- Thêm/hủy sản phẩm bổ sung</p>
                                <p>- Tăng/giảm số tiền bảo hiểm</p>
                                <p>- Chuyển đổi quỹ (sản phẩm bảo hiểm Liên kết Đơn vị)</p>
                              </div>
                            </li>
                          </ul>
                        </li>
                        <li className="main-menu-li">
                          Khôi phục hiệu lực nếu hợp đồng đã mất hiệu lực do không đóng phí bảo hiểm
                        </li>
                      </ul>
                      <div className="border-list"></div>
                      <ul className="list-information" style={{ paddingTop: '16px' }}>
                        <h3>Thủ tục điều chỉnh</h3>
                        <li className="main-menu-li">
                          Quý khách yêu cầu bằng cách:
                          <br />
                          <ul className="sub-list-2">
                            <li className="sub-list-2-li">Gửi yêu cầu qua Dai-ichi Connect</li>
                            {/* TODO Connect Mang luoi, Bieu mau */}
                            <li className="sub-list-2-li">
                              Chọn Phiếu yêu cầu phù hợp <Link to={{ pathname: '/utilities/document' }} className="simple-brown2" style={{ display: "inline" }}> ở đây</Link>,
                              hoàn tất yêu cầu và gửi về <Link to={{ pathname: '/utilities/network' }} className="simple-brown2" style={{ display: "inline" }}>văn phòng gần nhất</Link>.
                            </li>
                          </ul>
                        </li>
                        <li className="main-menu-li">
                          Dai-ichi Life Việt Nam kiểm tra thông tin, thực hiện yêu cầu và gửi xác nhận đến Quý khách (tùy
                          thuộc loại điều chỉnh/bổ sung, Dai-ichi Life Việt Nam có thể hướng dẫn cung cấp thêm thông tin,
                          giấy tờ hoặc phí bảo hiểm trước khi xác nhận chính thức).
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div id="camket" className={jsonState.currentTab === TABS.POLICY_PAYMENT ? "tabpane-tab active" : "tabpane-tab"}>
                    <div className="tab-pane-container-content-two">
                      <p>
                        Mỗi sản phẩm có thể có những khoản thanh toán khác nhau liên quan đến giá trị hợp đồng (quyền lợi
                        thanh toán cụ thể được quy định chi tiết trong Quy tắc và Điều khoản sản phẩm). Các khoản thanh toán
                        có thể gồm:
                      </p>

                      <div className="list-information">
                        <ul className="sub-list">
                          <li className="sub-list-li">Tiền mặt định kỳ</li>
                          <li className="sub-list-li">Lãi chia tích lũy</li>
                          <li className="sub-list-li">Rút tiền từ giá trị tài khoản</li>
                          <li className="sub-list-li">Tạm ứng từ giá trị hoàn lại</li>
                          <li className="sub-list-li">Quyền lợi đáo hạn hợp đồng</li>
                        </ul>
                      </div>

                      <div className="vertical-step-list">
                        <div className="vertical-step-item">
                          <div className="bullet"><span>1</span></div>
                          <div className="vertical-step-content">
                            <p>Chuẩn bị chứng từ</p>
                            <p>Bao gồm:</p>
                            <ul className="list-information">
                              <div className="sub-list basic-big">
                                <li className="sub-list-li">
                                  Quyền lợi đáo hạn: <span className="simple-brown basic-semibold basic-text2"> Phiếu xác nhận quyền lợi bảo hiểm đáo hạn </span>
                                </li>
                                <li className="sub-list-li">
                                  Quyền lợi khác: <span className="simple-brown basic-semibold basic-text2"> Phiếu yêu cầu thanh toán quyền lợi hợp đồng bảo hiểm </span>
                                </li>
                                <li className="sub-list-li">
                                  Giấy tờ chứng minh nhân thân của bên mua bảo hiểm (bản chính - nhân viên Dai-ichi Life
                                  Việt Nam sẽ đối chiếu khi thanh toán tại văn phòng).
                                </li>
                              </div>
                            </ul>
                          </div>
                        </div>
                        <div className="vertical-step-item">
                          <div className="bullet"><span>2</span></div>
                          <div className="vertical-step-content">
                            <p>Nộp hồ sơ đầy đủ cho Dai-ichi Life Việt Nam</p>
                            <span className="simple-brown basic-semibold basic-text2 basic-bottom-margin basic-block">
                              Tìm Văn phòng Giao dịch DLVN gần bạn.
                            </span>
                          </div>
                        </div>
                        <div className="vertical-step-item">
                          <div className="bullet"><span>3</span></div>
                          <div className="vertical-step-content">
                            <p>Nhận thông báo Giải quyết quyền lợi bảo hiểm</p>

                            <ul className="list-information">
                              <div className="sub-list">
                                <li className="sub-list-li">Bằng tiền mặt tại văn phòng của DLVN</li>
                                <li className="sub-list-li">Chuyển khoản</li>
                                <li className="sub-list-li">Chuyển phí hợp đồng bảo hiểm</li>
                                <li className="sub-list-li">Hoàn trả tạm ứng giá trị hoàn lại</li>
                              </div>
                            </ul>
                          </div>
                        </div>
                      </div>

                      <div className="border-list"></div>
                      <ul className="list-information" style={{ paddingTop: '16px' }}>
                        <h3>Thủ tục yêu cầu thanh toán</h3>
                        <li className="main-menu-li">
                          Quý khách yêu cầu bằng cách:
                          <br />
                          <ul className="sub-list-2">
                            <li className="sub-list-2-li">Gửi yêu cầu qua Dai-ichi Connect</li>
                            <li className="sub-list-2-li">
                              Chọn Phiếu yêu cầu phù hợp <Link to={{ pathname: '/utilities/document' }} className="simple-brown2" style={{ display: "inline" }}> ở đây</Link>,
                              hoàn tất yêu cầu và gửi về <Link to={{ pathname: '/utilities/network' }} className="simple-brown2" style={{ display: "inline" }}>văn phòng gần nhất</Link>.
                            </li>
                          </ul>
                        </li>
                        <li className="main-menu-li">
                          Dai-ichi Life Việt Nam kiểm tra thông tin, thực hiện yêu cầu và gửi xác nhận đến Quý khách
                          (tùy thuộc loại thanh toán Dai-ichi Life Việt Nam có thể hướng dẫn cung cấp thêm thông tin, giấy tờ trước khi xác nhận thanh toán).
                        </li>
                        <li className="main-menu-li">
                          Dai-ichi Life Việt Nam chuyển tiền theo phương thức đã Quý khách chỉ định (tiền mặt, chuyển khoản, chuyển đóng phí, …)
                        </li>
                      </ul>
                    </div>
                  </div>
                </section>
              </div>
            </div>
          </div>

          <div className="bottom-btn" onClick={() => this.handlerCreateNewRequest()} >
            <button className="btn btn-primary">Tạo mới yêu cầu</button>
          </div>
        </main>
        {jsonState.invalidTypeUser &&
          <AlertPopupInvalidUser closePopup={() => closeInvalidUserType()} msg={msgPopup} imgPath={popupImgPath} />
        }
      </>

    );
  }
}

export default PolicyTrans;
