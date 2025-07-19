import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  COMPANY_KEY,
  AUTHENTICATION,
  FE_BASE_URL,
  PageScreen
} from '../constants';
import 'antd/dist/antd.min.css';
import { CPGetPolicyListByCLIID, logoutSession, CPSaveLog } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Pay_HistoryPaymentDetail } from '../util/APIUtils';
import {formatFullName, showMessage, getSession, getDeviceId, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';

class PaymentHistory extends Component {

  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN)
        }
      },
      jsonInput2: {
        jsonDataInput: {
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Company: '',
          OS: 'Samsung_SM-G973F-Android-7.1.2',
          Project: 'mcp',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN),
          PolicyNo: '',
          Action: 'PaymentHistory',
          Offset: '1',
          Filter: 'All'
        }
      },
      ClientProfile: null,
      dtProposal: null,
      polID: '',
      jsonResponse: null,
      HistoryPaymentResponse: null,
      offset: 1,
      filter: 'All',
      isEmpty: true,
      loadMore: false,
      renderMeta: false
    }
  }


  componentDidMount() {
    const apiRequest = Object.assign({}, this.state.jsonInput);
    //console.log(apiRequest);
    CPGetPolicyListByCLIID(apiRequest).then(Res => {
      //console.log(Res);
      let Response = Res.Response;
      if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        jsonState.jsonResponse = Response;
        jsonState.ClientProfile = Response.ClientProfile;
        //console.log(Response);
        this.setState(jsonState);
        //Alert.success("Welcome To DSHD Screen!");

      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }

        })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
    this.cpSaveLog(`Web_Open_${PageScreen.PAYMENT_FEE_HISTORY}`);
    trackingEvent(
        "Đóng phí bảo hiểm",
        `Web_Open_${PageScreen.PAYMENT_FEE_HISTORY}`,
        `Web_Open_${PageScreen.PAYMENT_FEE_HISTORY}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.PAYMENT_FEE_HISTORY}`);
    trackingEvent(
        "Đóng phí bảo hiểm",
        `Web_Close_${PageScreen.PAYMENT_FEE_HISTORY}`,
        `Web_Close_${PageScreen.PAYMENT_FEE_HISTORY}`,
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
    var showCardInfo = (polID, index) => {
      var jsonState = this.state;
      jsonState.polID = polID;
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      //console.log(polID);
      this.setState(jsonState);
      if (document.getElementById(index).className === "card choosen") {
        document.getElementById(index).className = "card";
        jsonState.polID = '';
        jsonState.isEmpty = true;
        this.setState(jsonState);
      } else {
        jsonState.isEmpty = false;
        this.setState(jsonState);
        this.state.ClientProfile.forEach((polID, i) => {
          if (i === index) {
            document.getElementById(i).className = "card choosen";
          } else {
            document.getElementById(i).className = "card";
          }
        });
        callHistoryPaymentAPI();
      }
    }
    var callHistoryPaymentAPI = () => {
      var jsonState = this.state;
      jsonState.jsonInput2.jsonDataInput.PolicyNo = this.state.polID.trim();
      jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
      jsonState.jsonInput2.jsonDataInput.Offset = this.state.offset;
      jsonState.jsonInput2.jsonDataInput.Filter = this.state.filter;
      //console.log(this.state.jsonInput2.jsonDataInput.PolicyNo);
      const apiRequest = Object.assign({}, this.state.jsonInput2);
      //console.log(apiRequest);
      Pay_HistoryPaymentDetail(apiRequest).then(Res => {
        //console.log(Res);
        let Response1 = Res.Response;
        if (Response1.ErrLog === 'successfull') {
          jsonState.HistoryPaymentResponse = Response1;
          if (Response1.dtProposal.length < 10) {
            jsonState.loadMore = false;
          } else {
            jsonState.loadMore = true;
          }
          if (jsonState.dtProposal === null) { jsonState.dtProposal = [] };
          for (let i = 0; i < Response1.dtProposal.length; i++) {
            jsonState.dtProposal.push(Response1.dtProposal[i]);
          }
          this.setState(jsonState);
          //console.log(this.state.HistoryPaymentResponse);
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }

          })

        } else {
          jsonState.loadMore = false;
          this.setState(jsonState);
        }
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
    }
    var showFilter = () => {
      var jsonState = this.state;
      document.getElementById(jsonState.filter).className = "filter-pop-tick ticked";
      if (document.getElementById("filter").className === "specialfilter show") {
        document.getElementById("filter").className = "specialfilter";
      } else {
        document.getElementById("filter").className = "specialfilter show";
      }
    }
    var showTatca = () => {

      document.getElementById("All").className = "filter-pop-tick ticked";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'All';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showNganhang = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick ticked";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'Bank';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showTienmat = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick ticked";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'PostOffice';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showDiemthuong = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick ticked";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'Loyalty';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showVidientu = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick ticked";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'Wallet';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showQLHD = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick ticked";
      document.getElementById("Others").className = "filter-pop-tick";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'Policy';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var showKhac = () => {
      document.getElementById("All").className = "filter-pop-tick";
      document.getElementById("Bank").className = "filter-pop-tick";
      document.getElementById("Wallet").className = "filter-pop-tick";
      document.getElementById("PostOffice").className = "filter-pop-tick";
      document.getElementById("Loyalty").className = "filter-pop-tick";
      document.getElementById("Policy").className = "filter-pop-tick";
      document.getElementById("Others").className = "filter-pop-tick ticked";
      document.getElementById("filter").className = "specialfilter";
      var jsonState = this.state;
      jsonState.filter = 'Others';
      jsonState.offset = 1;
      jsonState.dtProposal = null;
      jsonState.loadMore = false;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }
    var loadMore = () => {
      //document.getElementById("page1").className = "page-number active";
      var jsonState = this.state;
      jsonState.offset++;
      this.setState(jsonState);
      callHistoryPaymentAPI();
    }

    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    if (!getSession(CLIENT_ID)) {
      return <Redirect to={{
        pathname: '/home'
      }} />;
    }
    return (
      <div>
        {this.state.renderMeta &&
          <Helmet>
            <title>Đóng phí bảo hiểm – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/payment-history"} />
            <meta property="og:title" content="Đóng phí bảo hiểm - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/payment-history"} />
          </Helmet>
        }
        <main className="logined" id="main-id">
          <div className="main-warpper insurancepage basic-mainflex">
            <section className="sccard-warpper">
              <h5 className="basic-bold">Vui lòng chọn hợp đồng:</h5>
              <div className="card-warpper">
                <LoadingIndicator area="policyList-by-cliID" />
                {this.state.ClientProfile !== null && this.state.ClientProfile.map((item, index) => (
                  <div className="item">
                    <div className="card" onClick={() => showCardInfo(item.PolicyID, index)} id={index}>
                      <div className="card__header">
                        <h4 className="basic-bold">{item.ProductName}</h4>
                        <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                        <div className="d-flex" style={{justifyContent: 'space-between'}}>
                          {item.PolicyStatus.length < 25 ?
                              <p>Hợp đồng: {item.PolicyID}</p> :
                              <p className="policy">Hợp đồng: {item.PolicyID}</p>}
                          {(item.PolicyStatus === 'Mất hiệu lực' || item.PolicyStatus === 'Hết hiệu lực') ? (
                              <div className="dcstatus">
                                <p className="inactive">{item.PolicyStatus}</p>
                              </div>) : (
                              <div className="dcstatus">
                                {item.PolicyStatus.length < 25 ?
                                    <p className="active" >{item.PolicyStatus}</p> :
                                    <p className="activeLong" >{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                              </div>
                          )}
                        </div>
                        <div className="choose">
                          <div className="dot"></div>
                        </div>
                      </div>
                      <div className="card__footer">
                        <div className="card__footer-item">
                          <p>Ngày hiệu lực</p>
                          <p>{item.PolIssEffDate}</p>
                        </div>
                        <div className="card__footer-item">
                          <p>Số tiền bảo hiểm</p>
                          <p className="basic-red basic-semibold">{item.FaceAmount} VNĐ</p>
                        </div>
                        <div className="card__footer-item">
                          <p>Đại lý bảo hiểm</p>
                          <p>{item.AgentName ? item.AgentName.toUpperCase() : ''}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
              <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                <p>Tiếp tục</p>
                <i><img src="img/icon/arrow-left.svg" alt="" /></i>
              </div>
            </section>
            <section className="sccontract-warpper">
              {this.state.isEmpty ? (
                <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                  <div className="breadcrums__item">
                    <p>Lịch sử hợp đồng</p>
                    <span>&gt;</span>
                  </div>
                  <div className="breadcrums__item">
                    <p>Đóng phí bảo hiểm</p>
                    <span>&gt;</span>
                  </div>
                </div>) : (
                <div className="breadcrums" style={{ backgroundColor: '#f1f1f1' }}>
                  <div className="breadcrums__item">
                    <p>Lịch sử hợp đồng</p>
                    <span>&gt;</span>
                  </div>
                  <div className="breadcrums__item">
                    <p>Đóng phí bảo hiểm</p>
                    <span>&gt;</span>
                  </div>
                </div>

              )}
              <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                <p>Chọn thông tin</p>
                <i><img src="img/icon/return_option.svg" alt="" /></i>
              </div>
              {this.state.isEmpty ? (
                <div className="sccontract-container">
                  <div className="insurance">
                    <div className="empty">
                      <div className="icon">
                        <img src="img/icon/empty.svg" alt="" />
                      </div>
                      <p style={{ paddingTop: '20px' }}>Thông tin chi tiết sẽ hiển thị khi bạn chọn một hợp đồng ở bên trái.</p>
                    </div>
                  </div>
                </div>
              ) : (
                <div>
                  <div className="dpbh-container">
                    <div className="dpbh-container_header">
                      <div className="dpbh-container_header-title">Danh sách giao dịch</div>
                      <div className="specialfilter" id="filter">
                        <div className="filter_button" onClick={() => showFilter()} >
                          <div className="icon-left">
                            <img src="img/icon/filter-icon.svg" alt="filter-icon" />
                          </div>
                          <div className="text">
                            <p>Bộ lọc</p>
                          </div>
                          <div className="icon-right">
                            <img
                              src="img/icon/arrow-white-down.svg"
                              alt="arrow-down-icon"
                            />
                          </div>
                        </div>
                        <div className="filter_content">
                          <div className="filter-pop">

                            <div className="filter-pop-tick" onClick={() => showTatca()} id="All">
                              <p className="content">Tất cả</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showNganhang()} id="Bank">
                              <p className="content">Chuyển khoản/Thẻ</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showTienmat()} id="PostOffice">
                              <p className="content">Tiền mặt</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showVidientu()} id="Wallet">
                              <p className="content">Ví điện tử</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showDiemthuong()} id="Loyalty">
                              <p className="content">Điểm thưởng</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showQLHD()} id="Policy">
                              <p className="content">Quyền lợi hợp đồng</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                            <div className="filter-pop-tick" onClick={() => showKhac()} id="Others">
                              <p className="content">Khác</p>
                              <div className="img-wrapper">
                                <img src="img/icon/green-ticked.svg" alt="ticked" />
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="bg"></div>
                      </div>
                    </div>
                    <div className="dpbh-container_body">
                      <LoadingIndicator area="dtProposal-info" />
                      {this.state.dtProposal !== null && this.state.dtProposal.map((item, index) => (
                        <div className="dpbh-container_body-card-container">
                          <div className="card-item">
                            <div className="card-item-header dpbh-header">
                              <div className="card-item-header-left-icon">
                                <img src="img/icon/Calendar.svg" alt="calender" />
                              </div>
                              <div className="card-item-header-left-text">
                                {item.ReceiptDate}
                              </div>
                            </div>
                            <div className="card-border"></div>
                            <div className="card-item-content">
                              <div className="card-item-content-left">
                                <p>Số phí đóng</p>
                                <p>Phương thức</p>
                                {item.Channel === 'mCP' && <div
                                    className="flex-center card__footer-claim-details-wrapper"
                                    onClick={(e) => {
                                        this.props.history.push(`/followup-claim-info/${item.ClaimID}`);
                                    }}>
                                  <p className="primary-text basic-semibold card__footer-claim-details-label" style={{ marginBottom: 0 }}>Xem chi tiết hồ sơ</p>
                                  <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                </div>}
                              </div>
                              <div className="card-item-content-right">
                                <p className="semi-red">{item.CollectionAmount} VNĐ</p>
                                <p>{item.ReceiptType}</p>
                              </div>
                            </div>
                          </div>
                          {index === this.state.dtProposal.length - 1 ? (
                            <div className="border-nodash-zero"></div>
                          ) : (
                            <div className="border-nodash"></div>
                          )}

                        </div>
                      ))}
                    </div>
                  </div>
                  <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>

                    {this.state.loadMore === true &&
                      <div style={{ paddingTop: '36px' }}>
                        <LoadingIndicator area="dtProposal-info" />
                        <button className="btn btn-primary fullwidth" style={{ width: '605px' }} onClick={() => loadMore()} id="page1">
                          <p style={{ padding: '250px' }}>Xem thêm</p>
                        </button>
                      </div>}
                  </div>
                </div>
              )}
            </section>
          </div>
        </main>

      </div>

    );

  }

}


export default PaymentHistory;