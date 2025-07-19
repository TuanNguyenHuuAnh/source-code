import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  COMPANY_KEY,
  AUTHENTICATION,
  DOCTOR_CARD_LOCAL_IMG,
  SUPER_MARKET_LOCAL_IMG,
  ECOMMERCE_LOCAL_IMG,
  GIVE_POINT_LOCAL_IMG,
  FEE_REFUND_LOCAL_IMG,
  FE_BASE_URL,
  PageScreen, WELLNESS_GIFT_IMG
} from '../constants';
import {CPSaveLog, getPointAccountHistory, logoutSession} from '../util/APIUtils';
import Pagination from '../History/Paging';
import './HistoryPoint.css';
import {
  getHistoryIcon,
  formatMoney,
  roundUp,
  showMessage,
  getSession,
  getDeviceId,
  trackingEvent
} from '../util/common';
import { Link, Redirect } from 'react-router-dom';
import { Helmet } from "react-helmet";
import iconInvest from '../img/icon/9.1/iconInvest.svg';

class HistoryPoint extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      selected: false,
      DEClientProfile: null,
      INClientProfile: null,
      LPType: 'IN',
      OffsetIN: '1',
      OffsetDE: '1',
      pageOfItemsDE: [],
      pageOfItemsIN: [],
      showIN: true,
      showDE: false,
      renderMeta: false
    };
    this.onChangePageDE = this.onChangePageDE.bind(this);
    this.onChangePageIN = this.onChangePageIN.bind(this);

  }
  onChangePageDE(pageOfItemsDE) {
    // update state with new page of items
    this.setState({ pageOfItemsDE: pageOfItemsDE });
  }
  onChangePageIN(pageOfItemsIN) {
    // update state with new page of items
    this.setState({ pageOfItemsIN: pageOfItemsIN });
  }


  cpGetPointHistory = (offset, lpType) => {
    const submitRequest = {
      jsonDataInput: {
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        OS: 'Samsung_SM-A125F-Android-11',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN),
        Offset: offset,
        Company: COMPANY_KEY,
        LPType: lpType
      }
    }
    //console.log(JSON.stringify(submitRequest));
    getPointAccountHistory(submitRequest).then(Res => {
      //console.log(JSON.stringify(Res));
      let Response = Res.CPGetPointAccountByCLIIDResult;
      //console.log("Response", Response);

      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        if (lpType === 'DE') {
          jsonState.DEClientProfile = Response.ClientProfile;
        } else {
          jsonState.INClientProfile = Response.ClientProfile;
        }
        jsonState.LPType = lpType;
        jsonState.loaded = true;
        this.setState(jsonState);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        //document.getElementById('session-expired-popup').className = 'popup option-popup show';
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }
        })
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  componentDidMount() {
    this.cpGetPointHistory('1', 'IN');
    this.cpSaveLog(`Web_Open_${PageScreen.TRANS_HISTORY_PAGE}`);
    trackingEvent(
        "Điểm thưởng",
        `Web_Open_${PageScreen.TRANS_HISTORY_PAGE}`,
        `Web_Open_${PageScreen.TRANS_HISTORY_PAGE}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.TRANS_HISTORY_PAGE}`);
    trackingEvent(
        "Điểm thưởng",
        `Web_Close_${PageScreen.TRANS_HISTORY_PAGE}`,
        `Web_Close_${PageScreen.TRANS_HISTORY_PAGE}`,
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
    const ShowTabIN = () => {
      var jsonState = this.state;
      jsonState.showIN = true;
      jsonState.showDE = false;
      this.setState(jsonState);
      document.getElementById('de-head-tab').className = 'scpointhistory__head-tab';
      document.getElementById('in-head-tab').className = 'scpointhistory__head-tab active';
      if (this.state.INClientProfile === null) {
        this.cpGetPointHistory('1', 'IN');
      } else {
        var jsonState = this.state;
        jsonState.LPType = 'IN';
        jsonState.loaded = true;
        this.setState(jsonState);
      }
      document.getElementById('point-history-in-id').className = 'point-history';
      document.getElementById('point-history-de-id').className = 'point-history hide';
    }
    const ShowTabDE = () => {
      var jsonState = this.state;
      jsonState.showIN = false;
      jsonState.showDE = true;
      this.setState(jsonState);
      document.getElementById('in-head-tab').className = 'scpointhistory__head-tab';
      document.getElementById('de-head-tab').className = 'scpointhistory__head-tab active';
      if (this.state.DEClientProfile === null) {
        this.cpGetPointHistory('1', 'DE');
      } else {
        var jsonState = this.state;
        jsonState.LPType = 'DE';
        jsonState.loaded = true;
        this.setState(jsonState);
      }

      document.getElementById('point-history-de-id').className = 'point-history';
      document.getElementById('point-history-in-id').className = 'point-history hide';

    }

    const goToPage = (offset, type) => {
      if (type === 'IN') {
        if (offset !== this.state.OffsetIN) {
          this.cpGetPointHistory(offset, type);
        }
      } else {
        if (offset !== this.state.OffsetDE) {
          this.cpGetPointHistory(offset, type);
        }
      }

    }

    const goToPageDE = (offset) => {
      if (offset !== this.state.OffsetIN) {
        this.cpGetPointHistory(offset, 'DE');
      }
    }
    if (!getSession(CLIENT_ID)) {
      return <Redirect to={{
        pathname: '/home'
      }} />;
    }

    return (
      <main className="logined">
        {this.state.renderMeta &&
          <Helmet>
            <title>Lịch sử điểm thưởng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/point-history"} />
            <meta property="og:title" content="Lịch sử điểm thưởng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/point-history"} />
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
            <p>Lịch sử điểm thưởng</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
        </div>

        <section className="scpointhistory">
          <div className="scpointhistory__head">
            <div className="scpointhistory__head-tab active" id="in-head-tab" onClick={() => ShowTabIN()}>
              <h4>Điểm tích lũy</h4>
            </div>
            <div className="scpointhistory__head-tab" id="de-head-tab" onClick={() => ShowTabDE()}>
              <h4>Điểm sử dụng</h4>
            </div>
          </div>
          <div className="container">
            <div className="pointhistory-wrapper">
              <div className="point-history" id="point-history-in-id">

                {this.state.pageOfItemsIN &&
                  this.state.pageOfItemsIN.map((item, index) => {
                    return (
                      <div className="point-history__item" key={index}>
                        <div className="icon-field">
                          <img src={getHistoryIcon(item.Description)} alt="" />
                        </div>
                        <div className="content-field">
                          <p className="point-tag">{parseInt(item.Point) >=0?'+':''}{formatMoney(roundUp(parseInt(item.Point) / 1000))}</p>
                          <span><h4 className="basic-semibold max-width-history" style={{ paddingBottom: '20px' }}>{item.Description}</h4></span>

                          {item.Description && (item.Description.length <= 42) ? (
                            <div className="calendar" style={{ marginTop: '-10px' }}>
                              <div className="calendar-icon">
                                <img src="img/icon/9.1/9.1-icon-calendar.svg" alt="" />
                              </div>
                              <p>{item.CreateDate}</p>
                            </div>
                          ) : (
                            <div className="calendar">
                              <div className="calendar-icon">
                                <img src="img/icon/9.1/9.1-icon-calendar.svg" alt="" />
                              </div>
                              <p>{item.CreateDate}</p>
                            </div>
                          )}

                        </div>
                      </div>

                    )
                  })
                }

              </div>
              <div className="point-history hide" id="point-history-de-id">
                {this.state.pageOfItemsDE &&
                  this.state.pageOfItemsDE.map((item, index) => {
                    return (
                      <div className="point-history__item" key={index}>
                        <div className="icon-field">
                          {item.Description.indexOf('[Garmin]') >= 0 &&
                              <img src={WELLNESS_GIFT_IMG} alt="" />
                          }
                          {item.Description.indexOf('Đóng phí') >= 0 &&
                            <img src={FEE_REFUND_LOCAL_IMG} alt="" />
                          }
                          {((item.Description.indexOf('Tặng điểm thưởng cho') >= 0) || (item.Description.indexOf('Tặng điểm cho') >= 0)) &&
                            <img src={GIVE_POINT_LOCAL_IMG} alt="" />
                          }
                          {((item.Description.indexOf('Nạp thẻ điện thoại') >= 0) || (item.Description.indexOf('Thẻ Vinaphone') >= 0) || (item.Description.indexOf('Thẻ Mobifone') >= 0) || (item.Description.indexOf('Thẻ Vietel') >= 0)) &&
                            <img src="img/icon/9.1/9.1-icon-tdt.svg" alt="" />
                          }
                          {item.Description.indexOf('Phiếu quà tặng điện tử') >= 0 &&
                            <img src={ECOMMERCE_LOCAL_IMG} alt="" />
                          }
                          {(item.Description.indexOf('Phiếu siêu thị') >= 0) || (item.Description.indexOf('Phiếu quà tặng giấy') >= 0) &&
                            <img src={SUPER_MARKET_LOCAL_IMG} alt="" />
                          }
                          {item.Description.indexOf('Phiếu khám sức khỏe') >= 0 &&
                            <img src={DOCTOR_CARD_LOCAL_IMG} alt="" />
                          }
                          {item.Description.indexOf('Tất toán điểm thưởng') >= 0 &&
                            <img src="img/icon/9.1/9.1-menulist-icon-thamgiahd2.svg" alt="" />
                          }

                          {item.Description.indexOf('Quỹ Đầu tư') >= 0 &&
                              <img src={iconInvest} alt="" />
                          }
                        </div>
                        <div className="content-field">
                          <p className="point-tag">-{formatMoney(roundUp(parseInt(item.Point) / 1000))}</p>
                          <p>Mã giao dịch: {item.OrderID}</p>
                          <span><h4 className="basic-semibold" >{item.Description.indexOf('Tặng điểm thưởng cho người') >= 0 ? "Tặng điểm cho người thân" : item.Description}</h4></span>

                          <p className="quantity">Số lượng: {item.Quantity}</p>
                          <div className="calendar">
                            <div className="calendar-icon">
                              <img src="img/icon/9.1/9.1-icon-calendar.svg" alt="" />
                            </div>
                            <p>{item.CreateDate}</p>
                          </div>
                        </div>
                      </div>

                    )
                  })
                }

              </div>
            </div>
          </div>
          {this.state.showIN && (
            <div className="paging-container" id="paging-container-in-id">
              {this.state.INClientProfile !== null && (
                <Pagination items={this.state.INClientProfile} onChangePage={this.onChangePageIN} />
              )}
            </div>
          )}
          {this.state.showDE && (
            <div className="paging-container" id="paging-container-de-id">
              {this.state.DEClientProfile !== null && (
                <Pagination items={this.state.DEClientProfile} onChangePage={this.onChangePageDE} />
              )}
            </div>
          )}
        </section>
      </main>

    )
  }
}

export default HistoryPoint;
