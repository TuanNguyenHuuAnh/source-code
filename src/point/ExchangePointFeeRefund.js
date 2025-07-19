import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, POINT, POID, DOB, GIFT_CART_FEE_REFUND, TOTAL_CART_POINT, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION, FE_BASE_URL } from '../constants';
import { CPGetPolicyListByCLIID, CPGetPolicyInfoByPOLID, logoutSession, getProductByCategory } from '../util/APIUtils';
import PopupRefundConfirmSuccess from '../popup/PopupRefundConfirmSuccess';
import '../common/Common.css';
import './ExchangePointFeeRefund.css';
import { checkIsNumber, formatFullName, formatMoney, showMessage, setSession, getSession, getDeviceId } from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Link } from 'react-router-dom';
import { Helmet } from "react-helmet";

class ExchangePointFeeRefund extends Component {
  constructor(props) {
    super(props);
    this.state = {
      productClientProfile: null,
      clientProfile: null,
      loaded: false,
      index: -1,
      PolID: '',
      ProductName: '',
      PolicyLIName: '',
      PolicyStatus: '',
      PolIssEffDate: '',
      FaceAmount: '',
      AgentName: '',
      PolMPremAmt: 0,
      APL: 0,
      OPL: 0,
      jsonPoint: {
        PolMPremAmtPoint: 0,
        APLPoint: 0,
        OPLPoint: 0,
        TotalPoint: 0
      },

      submited: false,
      renderMeta: false
    }
    this.handleInputPointFeeRefundChange = this.handleInputPointFeeRefundChange.bind(this);
    this.handlePointFeeRefundSubmit = this.handlePointFeeRefundSubmit.bind(this);
    this.handleGetPolicyInfoSubmit = this.handleGetPolicyInfoSubmit.bind(this);
  }

  handleInputPointFeeRefundChange(event) {
    const target = event.target;
    const inputName = target.name;
    var inputValue = target.value;
    //Check PolMPremAmtPoint
    if (target.id === 'PolMPremAmtPointID' && !checkIsNumber(inputValue)) {
      let total = 0;
      if (inputValue === '') {
        document.getElementById('PolMPremAmtPointID').value = '';
        inputValue = 0;
        total = parseFloat(inputValue) + parseFloat(this.state.jsonPoint.APLPoint) + parseFloat(this.state.jsonPoint.OPLPoint);
        var jsonState = this.state;
        jsonState.jsonPoint[inputName] = inputValue;
        jsonState.jsonPoint.TotalPoint = total;
        this.setState(jsonState);
        if (total <= 0) {
          document.getElementById('btn-point-fee-refund').className = "btn btn-primary disabled";
        }
      } else {
        let prem = this.state.jsonPoint.PolMPremAmtPoint + '';
        if (prem.length < 6) {
          document.getElementById('PolMPremAmtPointID').value = prem.replace('.', ',');
        } else {
          document.getElementById('PolMPremAmtPointID').value = this.state.jsonPoint.PolMPremAmtPoint;
        }

      }
      return;
    } else if (target.id === 'PolMPremAmtPointID') {
      let inputLength = inputValue.length;
      let copyInputValue = inputValue;
      inputValue = inputValue.replace('.', '').replace(',', '.');
      if ((inputLength >= 4) && copyInputValue.indexOf(',') < 0) {
        document.getElementById('PolMPremAmtPointID').value = formatMoney(inputValue, 0);
      } else {
        document.getElementById('PolMPremAmtPointID').value = copyInputValue;
      }

    }

    //Check OPLPoint
    if (target.id === 'OPLPointID' && !checkIsNumber(inputValue)) {
      let total = 0;
      if (inputValue === '') {
        document.getElementById('OPLPointID').value = '';
        inputValue = 0;
        total = parseFloat(this.state.jsonPoint.PolMPremAmtPoint) + parseFloat(this.state.jsonPoint.APLPoint) + parseFloat(inputValue);
        var jsonState = this.state;
        jsonState.jsonPoint[inputName] = inputValue;
        jsonState.jsonPoint.TotalPoint = total;
        this.setState(jsonState);
        if (total <= 0) {
          document.getElementById('btn-point-fee-refund').className = "btn btn-primary disabled";
        }
      } else {
        let prem = this.state.jsonPoint.OPLPoint + '';
        if (prem.length < 6) {
          document.getElementById('OPLPointID').value = prem.replace('.', ',');
        } else {
          document.getElementById('OPLPointID').value = this.state.jsonPoint.OPLPoint;
        }

      }
      return;
    } else if (target.id === 'OPLPointID') {
      let inputLength = inputValue.length;
      let copyInputValue = inputValue;
      inputValue = inputValue.replace('.', '').replace(',', '.');
      if ((inputLength >= 4) && copyInputValue.indexOf(',') < 0) {
        document.getElementById('OPLPointID').value = formatMoney(inputValue, 0);
      } else {
        document.getElementById('OPLPointID').value = copyInputValue;
      }

    }

    //Check APLPoint
    if (target.id === 'APLPointID' && !checkIsNumber(inputValue)) {
      let total = 0;
      if (inputValue === '') {
        document.getElementById('APLPointID').value = '';
        inputValue = 0;
        total = parseFloat(this.state.jsonPoint.PolMPremAmtPoint) + parseFloat(inputValue) + parseFloat(this.state.jsonPoint.OPLPoint);
        var jsonState = this.state;
        jsonState.jsonPoint[inputName] = inputValue;
        jsonState.jsonPoint.TotalPoint = total;
        this.setState(jsonState);
        if (total <= 0) {
          document.getElementById('btn-point-fee-refund').className = "btn btn-primary disabled";
        }
      } else {
        let prem = this.state.jsonPoint.APLPoint + '';
        if (prem.length < 6) {
          document.getElementById('APLPointID').value = prem.replace('.', ',');
        } else {
          document.getElementById('APLPointID').value = this.state.jsonPoint.APLPoint;
        }

      }
      return;
    } else if (target.id === 'APLPointID') {
      let inputLength = inputValue.length;
      let copyInputValue = inputValue;
      inputValue = inputValue.replace('.', '').replace(',', '.');
      if ((inputLength >= 4) && copyInputValue.indexOf(',') < 0) {
        document.getElementById('APLPointID').value = formatMoney(inputValue, 0);
      } else {
        document.getElementById('APLPointID').value = copyInputValue;
      }

    }

    var stateObj = this.state;
    stateObj.jsonPoint[inputName] = inputValue;
    var totalPoint = 0;
    if (inputName === 'PolMPremAmtPoint') {
      totalPoint = parseFloat(inputValue) + parseFloat(this.state.jsonPoint.APLPoint) + parseFloat(this.state.jsonPoint.OPLPoint);
    } else if (inputName === 'APLPoint') {
      totalPoint = parseFloat(this.state.jsonPoint.PolMPremAmtPoint) + parseFloat(inputValue) + parseFloat(this.state.jsonPoint.OPLPoint);
    } else if (inputName === 'OPLPoint') {
      totalPoint = parseFloat(this.state.jsonPoint.PolMPremAmtPoint) + parseFloat(this.state.jsonPoint.APLPoint) + parseFloat(inputValue);
    }
    stateObj.jsonPoint.TotalPoint = totalPoint;
    this.setState(stateObj);
    var check = true;
    if (totalPoint <= 0) {
      check = false;
    } else {
      check = true;
    }
    if (check) {
      document.getElementById('btn-point-fee-refund').className = "btn btn-primary";
    } else {
      document.getElementById('btn-point-fee-refund').className = "btn btn-primary disabled";
    }
  }

  cpGetProducts = () => {
    const submitRequest = {
      jsonDataInput: {
        OS: 'Samsung_SM-A125F-Android-11',
        APIToken: getSession(ACCESS_TOKEN),
        Action: 'GetProductCategory',
        Authentication: AUTHENTICATION,
        Category: this.props.categorycd,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN),
      }
    }
    getProductByCategory(submitRequest).then(Res => {
      //console.log(Res);
      let Response = Res.CPGetProductByCategoryResult;


      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        this.getPolicyInfo(Response.ClientProfile);
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


  addFeeRefunds = () => {
    if (document.getElementById('btn-point-fee-refund').className === "btn btn-primary disabled") {
      return;
    }
    var totalCards = 0;
    if (getSession(TOTAL_CART_POINT)) {
      totalCards = parseFloat(getSession(TOTAL_CART_POINT));
    }
    if (!getSession(POINT) || (parseFloat(getSession(POINT)) < parseFloat(this.state.jsonPoint.TotalPoint) + totalCards)) {
      document.getElementById('error-not-enough-point-give').className = "error validate";
      return;
    } else {
      document.getElementById('error-not-enough-point-give').className = "error";
    }
    var giftCard = null;
    var products = [];
    var strDt = '';
    var t = 0;
    for (var i = 0; i < this.state.productClientProfile.length; i++) {
      if (this.state.productClientProfile[i].ProductID === '18' && this.state.jsonPoint.PolMPremAmtPoint > 0) {
        let mdiscount = parseInt(parseFloat(this.state.jsonPoint.PolMPremAmtPoint) * 1000 - parseFloat(this.state.jsonPoint.PolMPremAmtPoint) * 1000 * parseFloat(this.state.productClientProfile[i].Discount));
        strDt = "{\"policynoowner\":\"" + this.state.PolID.trim() + "\",\"pol_sndry_amt\":\"" + parseFloat(this.state.jsonPoint.PolMPremAmtPoint) * 1000 + "\",\"orderid\":\"" + 'tracking_number' + "\"}" + "|" + this.state.productClientProfile[i].strDetail + "|" + this.state.PolID.trim() + "|" + mdiscount / 1000 + "|1|" + this.props.categorycd;
        let product = {
          Discount: this.state.productClientProfile[i].Discount,
          MDiscount: mdiscount,
          category: this.props.categorycd,
          categoryName: 'Đóng phí/ Hoàn trả tạm ứng',
          fullDescription: '',
          shortDescription: this.state.productClientProfile[i].shortDescription,
          IMAGENAME: this.state.productClientProfile[i].IMAGENAME,
          IMAGEURL: this.state.productClientProfile[i].IMAGEURL,
          Shipping: this.state.productClientProfile[i].Shipping,
          ProductCode: '',
          Quantity: this.state.productClientProfile[i].Quantity,
          Price: parseInt(parseFloat(this.state.jsonPoint.PolMPremAmtPoint) * 1000),
          ProductID: this.state.productClientProfile[i].ProductID,
          strDetail: strDt
        };
        products[t++] = product;
      } else if (this.state.productClientProfile[i].ProductID === '20' && this.state.jsonPoint.OPLPoint > 0) {
        let mdiscount = parseInt(parseFloat(this.state.jsonPoint.OPLPoint) * 1000 - parseFloat(this.state.jsonPoint.OPLPoint) * 1000 * parseFloat(this.state.productClientProfile[i].Discount));
        strDt = "{\"policynoowner\":\"" + this.state.PolID.trim() + "\",\"pol_sndry_amt\":\"" + parseFloat(this.state.jsonPoint.OPLPoint) * 1000 + "\",\"orderid\":\"" + 'tracking_number' + "\"}" + "|" + this.state.productClientProfile[i].strDetail + "|" + this.state.PolID.trim() + "|" + mdiscount / 1000 + "|1|" + this.props.categorycd;
        let product = {
          Discount: this.state.productClientProfile[i].Discount,
          MDiscount: mdiscount,
          category: this.props.categorycd,
          categoryName: 'Đóng phí/ Hoàn trả tạm ứng',
          fullDescription: '',
          shortDescription: this.state.productClientProfile[i].shortDescription,
          IMAGENAME: this.state.productClientProfile[i].IMAGENAME,
          IMAGEURL: this.state.productClientProfile[i].IMAGEURL,
          Shipping: this.state.productClientProfile[i].Shipping,
          ProductCode: '',
          Quantity: this.state.productClientProfile[i].Quantity,
          Price: parseInt(parseFloat(this.state.jsonPoint.OPLPoint) * 1000),
          ProductID: this.state.productClientProfile[i].ProductID,
          strDetail: strDt
        };
        products[t++] = product;
      } else if (this.state.productClientProfile[i].ProductID === '19' && this.state.jsonPoint.APLPoint > 0) {
        let mdiscount = parseInt(parseFloat(this.state.jsonPoint.APLPoint) * 1000 - parseFloat(this.state.jsonPoint.APLPoint) * 1000 * parseFloat(this.state.productClientProfile[i].Discount));
        strDt = "{\"policynoowner\":\"" + this.state.PolID.trim() + "\",\"pol_sndry_amt\":\"" + parseFloat(this.state.jsonPoint.APLPoint) * 1000 + ",\"orderid\":\"" + 'tracking_number' + "\"}" + "|" + this.state.productClientProfile[i].strDetail + "|" + this.state.PolID.trim() + "|" + mdiscount / 1000 + "|1|" + this.props.categorycd;
        let product = {
          Discount: this.state.productClientProfile[i].Discount,
          MDiscount: mdiscount,
          category: this.props.categorycd,
          categoryName: 'Đóng phí/ Hoàn trả tạm ứng',
          fullDescription: '',
          shortDescription: this.state.productClientProfile[i].shortDescription,
          IMAGENAME: this.state.productClientProfile[i].IMAGENAME,
          IMAGEURL: this.state.productClientProfile[i].IMAGEURL,
          Shipping: this.state.productClientProfile[i].Shipping,
          ProductCode: '',
          Quantity: this.state.productClientProfile[i].Quantity,
          Price: parseInt(parseFloat(this.state.jsonPoint.APLPoint) * 1000),
          ProductID: this.state.productClientProfile[i].ProductID,
          strDetail: strDt
        };
        products[t++] = product;
      }

    }

    if (getSession(GIFT_CART_FEE_REFUND)) {
      giftCard = JSON.parse(getSession(GIFT_CART_FEE_REFUND));
      if (giftCard && giftCard.length > 0 && products.length > 0) {
        for (let i = 0; i < products.length; i++) {
          giftCard.push(products[i]);
        }
        setSession(GIFT_CART_FEE_REFUND, JSON.stringify(giftCard));
      } else if (products.length > 0) {
        setSession(GIFT_CART_FEE_REFUND, JSON.stringify(products));
      }
    } else if (products.length > 0) {
      setSession(GIFT_CART_FEE_REFUND, JSON.stringify(products));
    }

    if (getSession(TOTAL_CART_POINT)) {
      setSession(TOTAL_CART_POINT, (parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat(this.state.jsonPoint.TotalPoint)).toFixed(1));
    } else {
      setSession(TOTAL_CART_POINT, parseFloat(this.state.jsonPoint.TotalPoint).toFixed(1));
    }
    document.getElementById('refund-confirm-popup-success').className = "popup refund-confirm-popup special show";

  }


  handlePointFeeRefundSubmit(event) {
    event.preventDefault();
    this.addFeeRefunds();
  }
  handleGetPolicyInfoSubmit(event) {
    event.preventDefault();
    if (document.getElementById('btn-get-policy-info').className === "btn btn-primary disabled") {
      return;
    }
    var jsonState = this.state;
    jsonState.loaded = false;
    this.setState(jsonState);
    this.cpGetProducts();
  }

  getPolicyList = () => {
    const policyRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        APIToken: getSession(ACCESS_TOKEN),
        Project: 'mcp',
        ClientID: getSession(CLIENT_ID),
        UserLogin: getSession(USER_LOGIN)
      }
    }
    CPGetPolicyListByCLIID(policyRequest).then(Res => {
      //console.log(Res);
      let Response = Res.Response;
      if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        //jsonState.jsonResponse = Response;
        jsonState.clientProfile = Response.ClientProfile;
        jsonState.loaded = true;
        //console.log(Response);
        this.setState(jsonState);

      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }

  getPolicyInfo = (productClientProfile) => {
    const policyRequest = {
      jsonDataInput: {
        Project: 'mcp',
        Authentication: AUTHENTICATION,
        APIToken: getSession(ACCESS_TOKEN),
        DeviceId: getDeviceId(),
        Action: 'PolicyClient',
        OS: 'Samsung_SM-A125F-Android-11', //Must hardcode android for PolicyClient action
        UserID: getSession(USER_LOGIN),
        PolID: this.state.PolID ? this.state.PolID.trim() : '',
        POID: getSession(POID) ? getSession(POID).trim() : '',
        DOB: getSession(DOB) ? getSession(DOB).split(' ')[0] : '',
        Company: COMPANY_KEY,
      }
    }


    CPGetPolicyInfoByPOLID(policyRequest).then(res => {
      //console.log(res);
      let Response = res.Response;
      //console.log(Response);
      if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.PolicyInfo !== null) {//&& Response.PolicyInfo[0] !== null){
        var jsonState = this.state;
        //jsonState.PolID = Response.PolicyInfo[0].PolicyID;
        jsonState.productClientProfile = productClientProfile;
        jsonState.PolMPremAmt = Response.PolicyInfo[0].PolMPremAmt;
        jsonState.APL = Response.PolicyInfo[0].APL;
        jsonState.OPL = Response.PolicyInfo[0].OPL;

        jsonState.loaded = true;
        jsonState.submited = true;
        this.setState(jsonState);
      } 
      else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false}

        // })

      }
    }).catch(error => {
      //this.props.history.push('/maintainence');
    });
  }

  componentDidMount() {
    this.getPolicyList();
  }

  render() {
    const chooseCard = (polID, index, policyLIName, productName, policyStatus, polIssEffDate, faceAmount, agentName) => {
      var jsonState = this.state;
      jsonState.PolID = polID;
      jsonState.index = index;
      jsonState.PolicyLIName = policyLIName;
      jsonState.ProductName = productName;
      jsonState.PolicyStatus = policyStatus;
      jsonState.PolIssEffDate = polIssEffDate;
      jsonState.FaceAmount = faceAmount;
      jsonState.AgentName = agentName;
      this.setState(jsonState);
      if (document.getElementById(index).className === "card choosen") {
        document.getElementById(index).className = "card";
        document.getElementById('btn-get-policy-info').className = "btn btn-primary disabled";
      } else {
        //document.getElementById(index).className = "card choosen";
        this.state.clientProfile.forEach((polID, i) => {
          if (polID.PolicyStatusCode === '1') {
            if (i === index) {
              document.getElementById(i).className = "card choosen";
              document.getElementById('btn-get-policy-info').className = "btn btn-primary";
            } else {
              document.getElementById(i).className = "card";
            }
          }

        });

      }
    }

    if (!this.state.loaded) {
      return (
        <main className="logined">
          {this.state.renderMeta &&
            <Helmet>
              <title>Đóng phí/Hoàn trả tạm ứng – Dai-ichi Life Việt Nam</title>
              <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
              <meta name="robots" content="noindex, nofollow" />
              <meta property="og:type" content="website" />
              <meta property="og:url" content={FE_BASE_URL + "/category/3"} />
              <meta property="og:title" content="Đóng phí/Hoàn trả tạm ứng - Dai-ichi Life Việt Nam" />
              <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
              <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
              <link rel="canonical" href={FE_BASE_URL + "/category/3"} />
            </Helmet>
          }
          <section className="scfee-refundcontract">
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p><Link to="/point" className='breadcrums__link'>Điểm thưởng</Link></p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p><Link to="/point-exchange" className='breadcrums__link'>Đổi điểm thưởng</Link></p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Đóng phí/Hoàn trả tạm ứng</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="container">
              <LoadingIndicator area="policyList-by-cliID" />
              <LoadingIndicator area="point-product-info" />
              <LoadingIndicator area="policy-info" />

            </div>
          </section>
        </main>
      )
    }
    if (!this.state.submited && this.state.clientProfile !== null) {
      return (
        <main className="logined">
          <Helmet>
            <title>Đóng phí/Hoàn trả tạm ứng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/3"} />
            <meta property="og:title" content="Đóng phí/Hoàn trả tạm ứng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/3"} />
          </Helmet>
          <section className="scfee-refundcontract">
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Điểm thưởng</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Đổi điểm thưởng</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Đóng phí/Hoàn trả tạm ứng</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="container">
              <form onSubmit={this.handleGetPolicyInfoSubmit}>
                <div className="fee-refundcontract">
                  <div className="stepform">
                    <div className="stepform__body">
                      <h4 className="title basic-uppercase basic-bold">Chọn hợp đồng đóng phí/hoàn trả tạm ứng</h4>
                      <br /><br />
                      <div className="card-warpper">
                        {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => {
                          if ((item.PolicyStatusCode === '1')) {
                            return (
                              <div className="item">
                                <div className="card" onClick={() => chooseCard(item.PolicyID, index, item.PolicyLIName, item.ProductName, item.PolicyStatus, item.PolIssEffDate, item.FaceAmount, item.AgentName)} id={index}>
                                  <div className="card__header">
                                    <h4 className="basic-bold">{item.ProductName}</h4>
                                    <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                                    <p>Hợp đồng: {item.PolicyID}</p>

                                    <div className="dcstatus">
                                      <p className="active">{item.PolicyStatus}</p>
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
                                      <p className="basic-red basic-bold">{item.FaceAmount} VNĐ</p>
                                    </div>
                                    <div className="card__footer-item">
                                      <p>Đại lý bảo hiểm</p>
                                      <p>{formatFullName(item.AgentName)}</p>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            )
                          }
                        })}
                      </div>
                    </div>
                    <img className="decor-clip" src="../img/mock.svg" alt="" />
                    <img className="decor-person" src="../img/person.png" alt="" />
                  </div>
                  <div className="bottom-btn">
                    <button className="btn btn-primary disabled" id="btn-get-policy-info">Tiếp tục</button>
                  </div>
                </div>
              </form>
            </div>
          </section>
        </main>

      )
    } else if (this.state.submited) {
      let productSorted = null;
      if (this.state.productClientProfile) {
        productSorted = this.state.productClientProfile.slice().sort((a, b) => a.strDetail.length - b.strDetail.length);
      }
      var totalPoint = (parseFloat(this.state.jsonPoint.PolMPremAmtPoint) + parseFloat(this.state.jsonPoint.APLPoint) + parseFloat(this.state.jsonPoint.OPLPoint)).toFixed(1);
      var totalPointExchange = totalPoint * 1000;
      var remainPoint = (parseFloat(getSession(POINT)) - totalPoint - parseFloat(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0)).toFixed(1);
      return (
        <>
          <Helmet>
            <title>Đóng phí/Hoàn trả tạm ứng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/3"} />
            <meta property="og:title" content="Đóng phí/Hoàn trả tạm ứng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/3"} />
          </Helmet>
          <main className="logined">
            <section className="scfee-refundnextstep">
              <div className="breadcrums">
                <div className="breadcrums__item">
                  <p>Trang chủ</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>Điểm thưởng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>Đổi điểm thưởng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>Đóng phí/Hoàn trả tạm ứng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
              </div>
              <div className="container">
                <form onSubmit={this.handlePointFeeRefundSubmit}>
                  <div className="fee-refundstep">
                    <div className="personform">
                      <div className="personform__item">
                        <h4 className="title basic-uppercase basic-bold">Chọn hợp đồng đóng phí/hoàn trả tạm ứng</h4>
                        <div className="content">
                          <div className="content-item">
                            <div className="card choosen-noborder choosen">
                              <div className="card__header">
                                <h4 className="basic-bold">{this.state.ProductName}</h4>
                                <p>Dành cho: {this.state.PolicyLIName}</p>
                                <p>Hợp đồng: {this.state.PolID}</p>

                                <div className="dcstatus">
                                  {this.state.PolicyStatus === 'Đang hiệu lực' ? (
                                    <p className="inactive">{this.state.PolicyStatus}</p>
                                  ) : (
                                    <p className="active">{this.state.PolicyStatus}</p>
                                  )}
                                </div>
                                <div className="choose">
                                  <div className="dot"></div>
                                </div>
                              </div>
                              <div className="card__footer">
                                <div className="card__footer-item">
                                  <p>Ngày hiệu lực</p>
                                  <p>{this.state.PolIssEffDate}</p>
                                </div>
                                <div className="card__footer-item">
                                  <p>Số tiền bảo hiểm</p>
                                  <p className="basic-red basic-bold">{this.state.FaceAmount} VNĐ</p>
                                </div>
                                <div className="card__footer-item">
                                  <p>Đại lý bảo hiểm</p>
                                  <p className="basic-uppercase">{this.state.AgentName}</p>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div className="content-item">
                            <div className="yellow-tab">
                              <div className="wrapper">
                                <div className="yellow-tab__item">
                                  <p>Phí bảo hiểm định kỳ</p>
                                  <p>{parseFloat(this.state.PolMPremAmt).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                                </div>
                                <div className="yellow-tab__item">
                                  <p>Hoàn trả tạm ứng tiền mặt</p>
                                  <p>{parseFloat(this.state.OPL).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                                </div>
                                <div className="yellow-tab__item">
                                  <p>Hoàn trả tạm ứng đóng phí tự động</p>
                                  <p>{parseFloat(this.state.APL).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="personform__item">
                        <h4 className="title basic-semibold basic-text2">Số điểm sử dụng đóng cho:</h4>
                        <div className="content">
                          {productSorted !== null && productSorted.map((item, index) => (
                            <div className="content-item">
                              <div className="input special-input-extend">
                                <div className="input__content">
                                  <label htmlFor={item.ProductID}>{item.strDetail.replace('Đóng phí định kỳ', 'Phí bảo hiểm định kỳ')}</label>
                                  {item.ProductID === '18' &&
                                    <input type="search" id="PolMPremAmtPointID" maxLength="6" name="PolMPremAmtPoint" onChange={this.handleInputPointFeeRefundChange} />
                                  }
                                  {item.ProductID === '20' && (this.state.OPL > 0 ? (
                                    <input type="search" maxLength="6" name="OPLPoint" id="OPLPointID" onChange={this.handleInputPointFeeRefundChange} />
                                  ) : (
                                    <input type="search" disabled />
                                  ))
                                  }

                                  {item.ProductID === '19' && (this.state.APL > 0 ? (
                                    <input type="search" maxLength="6" name="APLPoint" id="APLPointID" onChange={this.handleInputPointFeeRefundChange} />
                                  ) : (
                                    <input type="search" disabled />
                                  ))
                                  }

                                </div>
                                {((item.ProductID === '18') || (item.ProductID === '20' && this.state.OPL > 0) || (item.ProductID === '19' && this.state.APL > 0)) &&
                                  <i><img src="../img/icon/edit.svg" alt="" /></i>
                                }

                              </div>
                            </div>

                          ))
                          }
                          <div className="content-item">
                            <div className="input disabled special-disabled">
                              <div className="input__content">
                                <label for="">Tổng điểm</label>
                                <input value={formatMoney(totalPoint, 1)} type="search" disabled />
                              </div>
                              <i><img src="../img/icon/edit.svg" alt="" /></i>
                            </div>
                          </div>
                        </div>
                        <p className="tag">Tương đương {totalPointExchange.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                      </div>

                      <img className="decor-clip" src="../img/mock.svg" alt="" />
                      <img className="decor-person" src="../img/person.png" alt="" />
                    </div>
                    <p className="error" id="error-not-enough-point-give">Số điểm hiện có của bạn không đủ đổi, vui lòng kiểm tra lại</p>
                    <div className="bottom-btn">
                      <button className="btn btn-primary disabled" id="btn-point-fee-refund">Tiếp tục</button>
                    </div>
                  </div>
                </form>
              </div>
            </section>
          </main>
          <PopupRefundConfirmSuccess totalPoint={this.state.jsonPoint.TotalPoint} polID={this.state.PolID} point={remainPoint} />
        </>
      )
    }
  }
}

export default ExchangePointFeeRefund;
