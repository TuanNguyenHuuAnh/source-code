import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  LINK_SUB_MENU_NAME_ID,
  LINK_SUB_MENU_NAME,
  CLIENT_PROFILE,
  CLASSPO,
  POINT,
  TOTAL_CART_POINT,
  FE_BASE_URL,
  AUTHENTICATION, USER_LOGIN, PageScreen
} from '../constants';
import {CPSaveLog, iibGetMasterDataByType} from '../util/APIUtils';
import MasterCategoryPoint from './MasterCategoryPoint';
import {formatMoney, getDeviceId, getSession, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";

class ExchangePoint extends Component {
  constructor(props) {
    super(props);
    this.state = {
      masterProfile: null,
      classPO: getSession(CLASSPO),
      giftcart: '0',
      point: getSession(POINT),
      renderMeta: false
    }
  };

  getMasterCategoryInfo = () => {
    const masterRequest = {
      Project: 'mcp',
      Type: "category"
    }
    iibGetMasterDataByType(masterRequest).then(res => {
      //console.log(res);
      let Response = res.GetMasterDataByTypeResult;
      //console.log(Response);
      if (Response.Message === 'SUCCESSFUL' && Response.ClientProfile) {
        this.setState({
          masterProfile: Response.ClientProfile
        });
      } else {
        //Alert
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.props.history.push('/maintainence');
    });

  }

  updatePoint = () => {
    if (this.state.point !== parseFloat(getSession(POINT))) {
      this.setState({ point: parseFloat(getSession(POINT)) });
    }
  }

  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.LOYALTY_GUIDE_GIFTS}`);
    trackingEvent(
        "Điểm thưởng",
        `Web_Open_${PageScreen.LOYALTY_GUIDE_GIFTS}`,
        `Web_Open_${PageScreen.LOYALTY_GUIDE_GIFTS}`,
    );
    this.getMasterCategoryInfo();
    setTimeout(this.updatePoint, 1000);
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.LOYALTY_GUIDE_GIFTS}`);
    trackingEvent(
        "Điểm thưởng",
        `Web_Close_${PageScreen.LOYALTY_GUIDE_GIFTS}`,
        `Web_Close_${PageScreen.LOYALTY_GUIDE_GIFTS}`,
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
      this.setState({renderMeta: true});
    }).catch(error => {
      this.setState({renderMeta: true});
    });
  }

  render() {
    const selectedSubMenu = (id, subMenuName) => {
      // setSession(LINK_SUB_MENU_NAME, subMenuName);
      // setSession(LINK_SUB_MENU_NAME_ID, id);
    }
    const showPointExChangeRule = () => {
      document.getElementById('point-exchange-rule-popup').className = "popup special point-exchange-rule-popup show";
    }
    let clientProfile = null;
    if (getSession(CLIENT_PROFILE)) {
      clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
    }
    let fullName = '';
    if (clientProfile !== null) {
      fullName = clientProfile[0].FullName;
    }
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    return (

      <main className={logined ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Đổi điểm thưởng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Trang thông tin hướng dẫn đổi điểm thưởng lấy quà tặng hấp dẫn từ Chương trình tích lũy điểm thưởng &#8243;Gắn bó dài lâu&#8243; của Dai-ichi Life Việt Nam." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Đổi điểm thưởng" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/point-exchange"} />
            <meta property="og:title" content="Đổi điểm thưởng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin hướng dẫn đổi điểm thưởng lấy quà tặng hấp dẫn từ Chương trình tích lũy điểm thưởng &#8243;Gắn bó dài lâu&#8243; của Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/point-exchange"} />
          </Helmet>
        }
        <div className="breadcrums">
          <Link to="/" className="breadcrums__item">
              <p>Trang chủ</p>
              <p className='breadcrums__item_arrow'>></p>
          </Link>
          <div className="breadcrums__item">
            <p><Link to="/point" className='breadcrums__link'>Điểm thưởng</Link></p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
          <div className="breadcrums__item">
            <p>Đổi điểm thưởng</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
        </div>

        <section className="scpromotionpointcard">
          <div className="container">
            <div className="promotionpointcard-wrapper">
              <div className="promotionpointcard">
                {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
                  <div className="card">
                    <div className="card__header">
                      <h4 className="basic-bold">{fullName}</h4>
                      <div className="ranktag"><p className="basic-semibold">{this.state.classPO}</p></div>
                    </div>
                    <div className="card__footer">
                      <div className="card__footer-item">
                        <div className="cardfootertab">
                          <p>Điểm thưởng: <span className="basic-red">{formatMoney(this.state.point)}</span></p>
                          <Link to="/point-history" onClick={() => selectedSubMenu('p3', 'Lịch sử điểm thưởng')}><a className="basic-semibold simple-brown">Lịch sử điểm &gt;</a></Link>
                        </div>
                        <span className="line"></span>
                        <div className="cardfootertab">
                          <p>Giỏ quà của tôi: <span className="basic-red">{formatMoney(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : '0')}</span></p>
                          <Link to="/gift-cart" onClick={() => selectedSubMenu('p2', 'Giỏ quà của tôi')}><a className="basic-semibold simple-brown">Xem chi tiết &gt;</a></Link>
                        </div>
                      </div>
                    </div>
                  </div>
                }
              </div>
            </div>
          </div>
        </section>

        <section className="scpoint-step">
          <div className="container">
            <h1>Hướng dẫn đổi điểm</h1>
            <div className="pointstep-wrapper">
              <div className="point-step">
                <div className="point-step__item">
                  <div className="step">
                    <div className="bullet"><span>1</span></div>
                    <p>Chọn quà/dịch vụ</p>
                  </div>
                </div>
                <div className="point-step__item">
                  <div className="step">
                    <div className="bullet"><span>2</span></div>
                    <p>Chọn mệnh giá</p>
                  </div>
                </div>
                <div className="point-step__item">
                  <div className="step">
                    <div className="bullet"><span>3</span></div>
                    <p>Nhập thông tin nhận quà</p>
                  </div>
                </div>
                <div className="point-step__item">
                  <div className="step">
                    <div className="bullet"><span>4</span></div>
                    <p>Xác nhận đổi điểm</p>
                  </div>
                </div>
              </div>
              <span id="pointrule-invoke" className="point-rule-invoke simple-brown basic-semibold" onClick={() => showPointExChangeRule()}>Xem quy tắc điểm thưởng ></span>
            </div>
          </div>
        </section>
        <MasterCategoryPoint masterProfile={this.state.masterProfile} />
      </main>

    )
  }
}

export default ExchangePoint;
