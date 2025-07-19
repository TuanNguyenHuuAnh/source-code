// import 'antd/dist/antd.css';
// import '../claim.css';
import React, { Component } from "react";
import {FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen} from '../constants';
import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import { CPSaveLog } from '../util/APIUtils';
import { Helmet } from "react-helmet";
import { Link } from "react-router-dom";


class InsParticipation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      renderMeta: false
    }
  };
  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.GUIDE_PARTIC_INSURANCE}`);
    trackingEvent(
        "Tiện ích",
        `Web_Open_${PageScreen.GUIDE_PARTIC_INSURANCE}`,
        `Web_Open_${PageScreen.GUIDE_PARTIC_INSURANCE}`,
    );
  }
  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.GUIDE_PARTIC_INSURANCE}`);
    trackingEvent(
        "Tiện ích",
        `Web_Close_${PageScreen.GUIDE_PARTIC_INSURANCE}`,
        `Web_Close_${PageScreen.GUIDE_PARTIC_INSURANCE}`,
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
  render() {
    return (
      <main className={isLoggedIn() ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Tham gia bảo hiểm – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Trang thông tin hướng dẫn đăng ký tham gia bảo hiểm của Dai-ichi Life Việt Nam nhằm chăm sóc sức khỏe và bảo vệ an toàn tài chính." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Tham gia bảo hiểm" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities/participate"} />
            <meta property="og:title" content="Tham gia bảo hiểm – Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin hướng dẫn đăng ký tham gia bảo hiểm của Dai-ichi Life Việt Nam nhằm chăm sóc sức khỏe và bảo vệ an toàn tài chính." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities/participate"} />
          </Helmet>
        }

        {/* <main className="basic-expand-footer"> */}
        <div className="main-warpper page-ten">
          <div className="gdhd-warpper-dcttlh">
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
                    <p>Tham gia bảo hiểm</p>
                    <span>&gt;</span>
                  </div>
                </div>
              </div>
            </section>
            {/* Banner */}
            <div className="scdieukhoan">
              <h1>Tham gia bảo hiểm</h1>
            </div>
            {/* Main page */}
            <section className="sc-tgbh">
              <div className="container">
                <div className="tgbh-page">
                  <div className="tgbh-form">
                    <div className="tgbh-form-content">
                      <p>
                        Chăm sóc sức khỏe và bảo vệ an toàn tài chính là nhu cầu rất thiết thực của tất cả mọi người. Với
                        mong muốn được đồng hành cùng Quý khách thực hiện kế hoạch tài chính tuyệt vời cho bản thân và gia
                        đình, Dai-ichi Life Việt Nam không ngừng nghiên cứu và ra mắt các sản phẩm với quyền lợi bảo hiểm
                        đa dạng theo nhu cầu.
                      </p>
                      <h2>Làm sao để tham gia?</h2>
                      <p>
                        Việc tham gia bảo hiểm cũng đơn giản và nhanh chóng. Tư vấn viên sẽ hỗ trợ tư vấn và hướng dẫn cụ thể:
                      </p>
                      <br />
                      <br />
                      <div className="progress-bar">
                        <div className="step active">
                          <div className="bullet">
                            <span>1</span>
                          </div>
                          <p>Tham khảo những sản phẩm phù hợp với nhu cầu của bạn nhất</p>
                        </div>

                        <div className="step active">
                          <div className="bullet">
                            <span>2</span>
                          </div>
                          <p>Xác định nhu cầu bảo hiểm và lập kế hoạch tài chính</p>
                        </div>

                        <div className="step active">
                          <div className="bullet">
                            <span>3</span>
                          </div>
                          <p>Lập yêu cầu bảo hiểm và đóng phí bảo hiểm đầu tiên</p>
                        </div>
                        <div className="step active">
                          <div className="bullet">
                            <span>4</span>
                          </div>
                          <p>
                            Dai-ichi Life Việt Nam thẩm định hồ sơ. Tùy trường hợp, Quý khách được yêu cầu cung cấp thêm thông tin/giấy tờ
                            và/hoặc mời khám y tế.
                          </p>
                        </div>
                        <div className="step active">
                          <div className="bullet">
                            <span>5</span>
                          </div>
                          <p>Nếu chấp nhận bảo hiểm, Hợp đồng bảo hiểm sẽ được phát hành và gửi đến bạn.</p>
                        </div>
                      </div>
                      <div className="last-text-container">
                        <div className="last-text-container-text">Liên hệ với Dai-ichi Việt Nam</div>
                        <div className="last-text-container-icon">
                          <a href="mailto: customer.services@dai-ichi-life.com.vn"><img src="/img/icon/email-yellow.svg" alt="email" /></a>
                          <a href="tel: 02838100888"><img src="/img/icon/phone-yellow.svg" alt="phone" /></a>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </div>
      </main>
    );
  }
}

export default InsParticipation;
