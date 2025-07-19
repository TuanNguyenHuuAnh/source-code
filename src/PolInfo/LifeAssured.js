import 'antd/dist/antd.min.css';
import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  LINK_SUB_MENU_NAME,
  LINK_SUB_MENU_NAME_ID,
  AUTHENTICATION,
  COMPANY_KEY,
  FE_BASE_URL,
  LIITEM,
  PageScreen
} from '../constants';
import { CPGetClientProfileByCLIID, CPGetPolicyInfoByPOLID, logoutSession, CPSaveLog } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import LoadingIndicatorBasic from '../common/LoadingIndicatorBasic';

import { Link, Redirect } from 'react-router-dom';
import { format } from 'date-fns';
import {showMessage, setSession, getSession, getDeviceId, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";

class LifeAssured extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: '',
          Authentication: AUTHENTICATION,
          Action: 'LifeInsuredList',
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN)
        }
      },
      jsonInput2: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          Action: 'PolicyProductLifeInsure',
          ClientID: getSession(CLIENT_ID),
          UserID: getSession(USER_LOGIN),
          LifeInsureID: '',
          IsLIS: ''
        }
      },
      cliID: this.props.match.params.clientId,
      isLIS: '',
      CellPhone: '',
      DOB: '',
      Email: '',
      FullName: '',
      Gender: '',
      POID: '',
      polID: '',
      PolicyStatus: '',
      jsonResponse: null,
      jsonResponse2: null,
      clientProfile: null,
      ClientProfileQLBH: null,
      liIndex: 0,
      isShowQLBH: false,
      isShowTTCN: false,
      isEmpty: true,
      PolicyClassCD: '',
      insuredId: '',
      renderMeta: false,
      loading: false
    }
  }
  componentDidMount() {
    const apiRequest = Object.assign({}, this.state.jsonInput);
    //console.log(apiRequest);
    CPGetClientProfileByCLIID(apiRequest).then(Res => {
      //console.log(Res);
      let Response = Res.Response;
      if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        jsonState.jsonResponse = Response;
        jsonState.clientProfile = Response.ClientProfile;

        //Go from HCCard start
        if (this.props.match.params.id && (this.state.insuredId === '')) {
          for (let i = 0; i < Response.ClientProfile.length; i++) {
            if (Response.ClientProfile[i].ClientID === this.props.match.params.id) {
              var jsonState = this.state;
              jsonState.cliID = this.props.match.params.id;
              jsonState.isLIS = Response.ClientProfile[i].IsLIS;
              jsonState.CellPhone = Response.ClientProfile[i].CellPhone;
              jsonState.DOB = Response.ClientProfile[i].DOB.substr(0, 10);
              jsonState.Email = Response.ClientProfile[i].Email;
              jsonState.FullName = Response.ClientProfile[i].FullName;
              jsonState.PolicyClassCD = Response.ClientProfile[i].PolicyClassCD;
              if (Response.ClientProfile[i].Gender === 'M') {
                jsonState.Gender = 'Nam';
              } else {
                jsonState.Gender = 'Nữ';
              }
              jsonState.POID = Response.ClientProfile[i].POID;
              jsonState.isShowQLBH = false;
              jsonState.isShowTTCN = false;
              jsonState.isEmpty = false;

              jsonState.jsonInput2.jsonDataInput.LifeInsureID = this.props.match.params.id;
              jsonState.jsonInput2.jsonDataInput.IsLIS = Response.ClientProfile[i].IsLIS;
              jsonState.isShowQLBH = true;
              jsonState.isShowTTCN = false;
              this.setState(jsonState);
              const apiRequest = Object.assign({}, this.state.jsonInput2);
              CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                  var jsonState = this.state;
                  jsonState.jsonResponse2 = Response;
                  jsonState.ClientProfileQLBH = Response.ClientProfile;
                  //console.log(Response);
                  this.setState(jsonState);
                  document.getElementById('QLBHTab').className = "menu-list__item active";
                  document.getElementById('TTCNTab').className = "menu-list__item";
                  if (document.getElementById(i)) {
                    document.getElementById(i).className = "card choosen";
                  }
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
            }
          }

        }
        //Go from HCCard end
        this.setState(jsonState);
        //Alert.success("Welcome To NDBH Screen!");

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
    this.cpSaveLog(`Web_Open_${PageScreen.LIST_INSURED}`);
    trackingEvent(
        "Yêu cầu quyền lợi",
        `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`,
        `Web_Open_${PageScreen.CLAIM_LIST_INSURED}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.LIST_INSURED}`);
    trackingEvent(
        "Yêu cầu quyền lợi",
        `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`,
        `Web_Close_${PageScreen.CLAIM_LIST_INSURED}`,
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
    const selectedSubMenu = (id, subMenuName) => {
      setSession(LINK_SUB_MENU_NAME, subMenuName);
      setSession(LINK_SUB_MENU_NAME_ID, id);
    }
    var clickOnCard = (cliID, index, CellPhone, DOB, Email, FullName, Gender, POID, PolicyClassCD, isLIS, item) => {
      var jsonState = this.state;
      jsonState.cliID = cliID;
      jsonState.isLIS = isLIS;
      jsonState.CellPhone = CellPhone;
      jsonState.DOB = DOB.substr(0, 10);
      jsonState.Email = Email;
      jsonState.FullName = FullName;
      jsonState.PolicyClassCD = PolicyClassCD;
      jsonState.liIndex = index;
      setSession(LIITEM + cliID.trim(), JSON.stringify(item));
      if (Gender === 'M') {
        jsonState.Gender = 'Nam';
      } else {
        jsonState.Gender = 'Nữ';
      }
      jsonState.POID = POID;
      //console.log(cliID);
      jsonState.isShowQLBH = false;
      jsonState.isShowTTCN = false;
      this.setState(jsonState);
      if (document.getElementById(index).className === "card choosen") {
        document.getElementById(index).className = "card";
        jsonState.cliID = '';
        jsonState.CellPhone = '';
        jsonState.DOB = '';
        jsonState.Email = '';
        jsonState.FullName = '';
        jsonState.Gender = '';
        jsonState.POID = '';
        jsonState.isShowQLBH = false;
        jsonState.isShowTTCN = false;
        jsonState.isEmpty = true;
        //console.log(cliID);
        this.setState(jsonState);
        document.getElementById('QLBHTab').className = "menu-list__item";
        document.getElementById('TTCNTab').className = "menu-list__item";

      } else {
        jsonState.isEmpty = false;
        this.setState(jsonState);
        //document.getElementById(index).className = "card choosen";
        this.state.clientProfile.forEach((cliID, i) => {
          if (i === index) {
            document.getElementById(i).className = "card choosen";
          } else {
            document.getElementById(i).className = "card";
          }
        });
        //document.getElementById('QLBHTab').className = "menu-list__item active";
        //document.getElementById('TTCNTab').className = "menu-list__item";
        buttonClickQLBH(cliID);
      }
    }
    var buttonClickQLBH = (cliID) => {
      var jsonState = this.state;
      jsonState.jsonInput2.jsonDataInput.LifeInsureID = cliID.trim();
      jsonState.jsonInput2.jsonDataInput.IsLIS = this.state.isLIS;
      jsonState.isShowQLBH = true;
      jsonState.isShowTTCN = false;
      jsonState.loading = true;
      this.setState(jsonState);
      const apiRequest = Object.assign({}, this.state.jsonInput2);
      CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
        let Response = Res.Response;
        if (Response.Result === 'true' && Response.ClientProfile !== null) {
          var jsonState = this.state;
          jsonState.jsonResponse2 = Response;
          jsonState.ClientProfileQLBH = Response.ClientProfile;
          //console.log(Response);
          jsonState.loading = false;
          this.setState(jsonState);
          document.getElementById('QLBHTab').className = "menu-list__item active";
          document.getElementById('TTCNTab').className = "menu-list__item";
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.setState({ loading: false });
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }

          })

        }
      }).catch(error => {
        this.setState({ loading: false });
        this.props.history.push('/maintainence');
      });
    }
    var buttonClickTTCN = (cliID) => {
      document.getElementById('QLBHTab').className = "menu-list__item";
      document.getElementById('TTCNTab').className = "menu-list__item active";
      var jsonState = this.state;
      jsonState.isShowQLBH = false;
      jsonState.isShowTTCN = true;
      this.setState(jsonState);
    }
    const toTitleCase = str => str.split(" ").map(
      e => e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase()).join(" ");
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
            <title>Người được bảo hiểm – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/lifeassured"} />
            <meta property="og:title" content="Người được bảo hiểm - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/lifeassured"} />
          </Helmet>
        }
        <main className="logined" id="main-id">
          <div className="main-warpper basic-mainflex">
            <section className="sccard-warpper">
              <h5 className="basic-bold">Vui lòng chọn người được bảo hiểm:</h5>
              <div className="card-warpper">
                <LoadingIndicator area="claim-li-list" />
                {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                  <div className="item">
                    <div className="card" onClick={() => clickOnCard(item.ClientID, index, item.CellPhone, item.DOB, item.Email, item.FullName, item.Gender, item.POID, item.PolicyClassCD, item.IsLIS, item)} id={index}>
                      <div className="card__header" id={item.ClientID}>
                        <h4 className="basic-bold">{toTitleCase(item.FullName.toLowerCase())}</h4>
                        <p>Mã khách hàng: {item.ClientID}</p>
                        <p>Ngày sinh: {format(new Date(item.DOB), 'dd/MM/yyyy')}</p>

                        <div className="choose">
                          <div className="dot"></div>
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
              <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                <div className="breadcrums__item">
                  <p>Thông tin hợp đồng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>Người được bảo hiểm</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
              </div>
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
                      <p style={{ paddingTop: '20px' }}>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
                    </div>
                  </div>
                </div>
              ) : (

                <div className="menu-list">
                  <button className="menu-list__item" onClick={() => buttonClickQLBH(this.state.cliID)} id="QLBHTab">
                    <p>QUYỀN LỢI BẢO HIỂM</p>
                  </button>
                  <button className="menu-list__item" onClick={() => buttonClickTTCN(this.state.cliID)} id="TTCNTab">
                    <p>THÔNG TIN CÁ NHÂN</p>
                  </button>
                </div>
              )}

              <div className="qlbh-wrapper" id="QLBH">

                {this.state.isShowQLBH === true && this.state.ClientProfileQLBH !== null && this.state.ClientProfileQLBH.map((item, index) => (
                  <div className="card-extend-container" key={"QLBH-Item" + index}>
                    <div className="card-extend-wrapper">
                      <div className="item" >

                        {(
                          item.IsMainCvg === '1' ? (
                            item.LISName === '1' ?
                            <div className="card__label card__label-linear">
                              <p className="basic-bold">Song hành bảo vệ</p>
                            </div> :
                          <div className="card__label">
                            <p className="basic-bold">Bảo hiểm chính</p>
                          </div>) : (
                          item.LISName === '1' ?
                            <div className="card__label card__label-linear">
                              <p className="basic-bold">Song hành bảo vệ</p>
                            </div> :
                            <div className="card__label card__label-brown">
                              <p className="basic-bold">Bảo hiểm bổ sung</p>
                            </div>
                        ))}
                        <div className="card" style={{ cursor: 'default' }}>
                          <div className="card__header">
                            <h4 className="basic-bold">{item.ProductName.split(';')[1]}</h4>
                            <p>Hợp đồng: {item.PolicyID}</p>
                          </div>
                          <div className="card__footer">
                            <div className="card__footer-item">
                              <p>Tình trạng</p>
                              <div className="dcstatus">
                                {item.PolicyStatus.split(';')[1] === 'Đang hiệu lực' ? (
                                  <p className="inactive">{item.PolicyStatus.split(';')[1]}</p>
                                ) : (
                                  <p className="active">{item.PolicyStatus.split(';')[1]}</p>
                                )}
                              </div>
                            </div>

                            <div className="card__footer-item">
                              <p>Ngày hiệu lực</p>
                              <p>{format(new Date(item.PolIssEffDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                            </div>
                            <div className="card__footer-item">
                              <p>Thời hạn bảo hiểm đến</p>
                              <p>{format(new Date(item.PolExpiryDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                            </div>
                            <div className="card__footer-item">
                              <p>Số tiền bảo hiểm</p>
                              {item.FaceAmount.split(';')[1] === 'Chi tiết theo điều khoản Hợp đồng' ?
                                <p className="basic-red basic-semibold" style={{ color: 'red' }}>{item.FaceAmount.split(';')[1]}</p> :
                                <p className="basic-red basic-semibold" style={{ color: 'red' }}>{item.FaceAmount.split(';')[1]} VNĐ</p>}
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}

              </div>

              <div className="qlbh-wrapper" id="TTCN">
                {this.state.isShowTTCN === true ? (
                  <div className="personal-infomation consulting-service">
                    <div className="form">
                      <div className="form__item">
                        <p>Ngày sinh</p>
                        <p>{format(new Date(this.state.DOB), 'dd/MM/yyyy')}</p>
                      </div>
                      <div className="form__item">
                        <p>Giới tính</p>
                        <p>{this.state.Gender}</p>
                      </div>
                      <div className="form__item">
                        <p>Số giấy tờ tùy thân</p>
                        <p>{this.state.POID}</p>
                      </div>
                      <div className="form__item">
                        <p>Điện thoại</p>
                        <p>{this.state.CellPhone}</p>
                      </div>
                      <div className="form__item">
                        <p>Email</p>
                        <p>{this.state.Email}</p>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div>
                  </div>

                )}
              </div>
              {this.state.isShowQLBH === true ? (
                <>
                  {this.state.loading &&
                    <LoadingIndicatorBasic />
                  }
                  <div className="background-footer-wrapper" style={{ backgroundColor: '#ffffff', margin: '0px', padding: '60px', paddingBottom: '0px', paddingRight: '0px', paddingLeft: '0px' }}>

                    <div className="background-footer">
                      <h2 className="title basic-bold">cần giải quyết quyền lợi?</h2>
                      <div className="content">
                        <p>
                          Bạn có thể tạo yêu cầu giải quyết quyền lợi trực tuyến trên
                          Dai-ichi Connect
                        </p>
                      </div>
                      {this.state.loading ? (
                        <button></button>
                      ) : (
                        <Link to={"/myclaim" + "/" + this.state.jsonInput2.jsonDataInput.LifeInsureID + "/" + this.state.liIndex} onClick={() => selectedSubMenu('item-14-0', 'Tạo mới yêu cầu')}><button></button></Link>
                      )}

                    </div>
                  </div>
                </>
              ) : (
                <div>
                </div>
              )}
            </section>
          </div>
        </main>
      </div>
    );
  }
}


export default LifeAssured;