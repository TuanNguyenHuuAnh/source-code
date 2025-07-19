import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, POINT, GIFT_CART_GIVE_POINT, TOTAL_CART_POINT, EXPIRED_MESSAGE, AUTHENTICATION, FE_BASE_URL } from '../constants';
import { searchPolicyHolder, getProductByCategory, logoutSession, CPSaveLog } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import '../popup/PopupCloseButton.css'
import { checkIsNumber, formatMoney, showMessage, formatFullName, setSession, getSession, getDeviceId } from '../util/common';
import '../common/Common.css';
import './ExchangePointGivePoint.css';
import { Link } from 'react-router-dom';
import { Helmet } from "react-helmet";
import PopupGivePointConfirmSuccess from '../popup/PopupGivePointConfirmSuccess';

class ExchangePointGivePoint extends Component {
  constructor(props) {
    super(props);
    this.state = {
      existPolicyHolder: false,
      FullName: '',
      loaded: true,
      PolicyID: '',
      POID: '',
      PODOB: '',
      givePoint: 0,
      renderMeta: false
    };
    this.handleInputGivePointChange = this.handleInputGivePointChange.bind(this);
    this.handleGivePointSubmit = this.handleGivePointSubmit.bind(this);
  }

  componentDidMount() {
    this.cpSaveLog("Tặng điểm cho người thân");
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

  handleInputGivePointChange(event) {
    const target = event.target;
    const inputName = target.name;
    let inputValue = target.value;

    if (target.id && target.id === 'PODOBID') {
      let date = inputValue.split('-');
      inputValue = date[2] + '/' + date[1] + '/' + date[0];
    }
    if (!checkIsNumber(inputValue) && target.id === 'givePointId') {
      if (inputValue === '') {
        document.getElementById('givePointId').value = '';
        inputValue = 0;
        this.setState({
          [inputName]: inputValue
        });
        document.getElementById('btn-give-point').className = "btn btn-primary disabled";
      } else {
        let gPoint = this.state.givePoint + '';
        if (gPoint.length < 5) {
          document.getElementById('givePointId').value = gPoint.replace('.', ',');
        } else {
          document.getElementById('givePointId').value = this.state.givePoint;
        }

      }
      return;
    } else if (target.id === 'givePointId') {
      let inputLength = inputValue.length;
      let copyInputValue = inputValue;
      inputValue = inputValue.replace('.', '').replace(',', '.');
      if ((inputLength === 4) && copyInputValue.indexOf(',') < 0) {
        document.getElementById('givePointId').value = formatMoney(inputValue, 0);
      } else {
        document.getElementById('givePointId').value = copyInputValue;
      }
      if (parseFloat(inputValue) > 0) {
        document.getElementById('btn-give-point').className = "btn btn-primary";
      } else {
        document.getElementById('btn-give-point').className = "btn btn-primary disabled";
      }

    }
    if (isNaN(inputValue) && target.id === 'policyId') {
      document.getElementById('policyId').value = this.state.PolicyID;
      document.getElementById('btn-give-point').className = "btn btn-primary disabled";
      return;
    }
    if (isNaN(inputValue)) {
      inputValue = inputValue.trim();
    }
    this.setState({
      [inputName]: inputValue
    });
    if (inputValue.trim() === '') {
      document.getElementById('btn-give-point').className = "btn btn-primary disabled";
    }
  }

  checkPolicyHolder = () => {
    if (document.getElementById('btn-give-point').className === "btn btn-primary disabled") {
      return;
    }
    let totalCards = 0;
    if (getSession(TOTAL_CART_POINT)) {
      totalCards = parseFloat(getSession(TOTAL_CART_POINT));
    }
    if ((!getSession(POINT)) || (parseInt(getSession(POINT)) < parseInt(this.state.givePoint) + totalCards)) {
      document.getElementById('error-not-enough-point').className = "error validate";
      return;
    } else {
      document.getElementById('error-not-enough-point').className = "error";
    }
    const policyInfoRequest = {
      jsonDataInput: {
        Project: 'mcp',
        APIToken: getSession(ACCESS_TOKEN),
        POID: this.state.POID,
        ClientID: getSession(CLIENT_ID),
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        PODOB: this.state.PODOB,
        DeviceToken: 'ios_simulator',
        UserLogin: getSession(USER_LOGIN),
        PolicyID: this.state.PolicyID

      }
    }
    searchPolicyHolder(policyInfoRequest)
      .then(response => {
        const Response = response.CPSearchPolicyHolderByPolicyIDResult;
        if (Response.Result === 'true' && Response.ClientProfile) {
          // const desiredPolicyID = `00${this.state.PolicyID}`;
          const desiredPolicyID = String(this.state.PolicyID).padStart(9, '0');
          const foundClientProfile = Response.ClientProfile?.find(profile => profile.PolicyID.trim() === desiredPolicyID);
          if (foundClientProfile) {
            this.setState({
              PolicyID: foundClientProfile.PolicyID
            });
            this.cpGivePointGetProducts(foundClientProfile);
          }

        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        } else {
          document.getElementById('error-popup-not-found-policy-holder').className = "popup error-popup special show";
        }

      }).catch(error => {

        //this.props.history.push('/maintainence');
      });

  }


  cpGivePointGetProducts = (profile) => {
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
      let Response = Res.CPGetProductByCategoryResult;
      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        this.addGivePoints(profile, Response.ClientProfile);
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

  addGivePoints = (profile, productClientProfile) => {
    let i;
    if (!getSession(POINT) || (getSession(POINT) && (parseFloat(getSession(POINT)) < parseFloat(this.state.givePoint) + parseFloat(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0)))) {
      document.getElementById('error-not-enough-point').className = "error validate";
      return;
    } else {
      document.getElementById('error-not-enough-point').className = "error";
    }
    const amount = parseFloat(this.state.givePoint); //* 1000;
    const products = [];
    let strDt = '';
    for (i = 0; i < productClientProfile.length; i++) {
      let mdiscount = parseInt(amount * 1000) - parseInt(amount * 1000) * parseInt(productClientProfile[i].Discount);
      strDt = "{\"policyno\":\"" + profile.PolicyID + "\",\"clientno\":\"" + profile.ClientID + "\",\"clientname\":\"" + profile.FullName + "\",\"" + "amount" + "\":\"" + amount + "\",\"orderid\":\"" + 'tracking_number' + "\"}" + "|" + profile.PolicyID + "|dummy|" + mdiscount / 1000 + "|1|" + this.props.categorycd;
      const product = {
        Discount: productClientProfile[i].Discount,
        MDiscount: mdiscount,
        category: this.props.categorycd,
        categoryName: 'Tặng điểm người thân',
        fullDescription: '',
        shortDescription: productClientProfile[i].shortDescription,
        IMAGENAME: productClientProfile[i].IMAGENAME,
        IMAGEURL: productClientProfile[i].IMAGEURL,
        Shipping: productClientProfile[i].Shipping,
        ProductCode: '',
        Quantity: productClientProfile[i].Quantity,
        Price: parseInt(amount * 1000),
        ProductID: productClientProfile[i].ProductID,
        strDetail: strDt
      };
      products[i] = product;
    }

    if (getSession(GIFT_CART_GIVE_POINT)) {
      const giftCard = JSON.parse(getSession(GIFT_CART_GIVE_POINT));

      if (giftCard && giftCard.length > 0 && products.length > 0) {
        let notMatch = false;
        for (i = 0; i < giftCard.length; i++) {
          let giftProduct = giftCard[i];
          let strDetail = giftProduct.strDetail;
          let strDetailArr = strDetail.split("|");
          let qty = strDetailArr[4];

          if (strDetailArr[0] === strDt.split("|")[0]) {
            qty = parseInt(qty) + 1;
            let strDt = strDetailArr[0] + "|" + strDetailArr[1] + "|" + strDetailArr[2] + "|" + strDetailArr[3] + "|" + qty + "|" + strDetailArr[5];
            giftProduct.strDetail = strDt;
            giftCard[i] = giftProduct;
            notMatch = false;
            break;
          } else {
            notMatch = true;
          }
        }
        if (notMatch) {
          giftCard.push(products[0]);
        }
        setSession(GIFT_CART_GIVE_POINT, JSON.stringify(giftCard));
      } else if (products.length > 0) {
        setSession(GIFT_CART_GIVE_POINT, JSON.stringify(products));
      }
    } else if (products.length > 0) {
      setSession(GIFT_CART_GIVE_POINT, JSON.stringify(products));
    }



    if (getSession(TOTAL_CART_POINT)) {
      setSession(TOTAL_CART_POINT, (parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat(this.state.givePoint)).toFixed(1));
    } else {
      setSession(TOTAL_CART_POINT, parseFloat(this.state.givePoint).toFixed(1));
    }
    document.getElementById('give-point-confirm-popup-success').className = "popup refund-confirm-popup special show";
  }


  handleGivePointSubmit(event) {
    event.preventDefault();
    this.checkPolicyHolder();

  }

  updateDate(value) {
    const jsonState = this.state;
    jsonState.PODOB = value;
    this.setState(jsonState);
    document.getElementById('PODOBID').value = value;
  }
  render() {
    const amount = this.state.givePoint * 1000;
    const totalPoint = parseFloat(this.state.givePoint);
    const remainPoint = (parseFloat(getSession(POINT)) - totalPoint - parseFloat(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0)).toFixed(1);

    const findPolicyHolder = () => {
      const policyInfoRequest = {
        jsonDataInput: {
          Project: 'mcp',
          APIToken: getSession(ACCESS_TOKEN),
          POID: this.state.POID,
          ClientID: getSession(CLIENT_ID),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          PODOB: this.state.PODOB,
          DeviceToken: 'ios_simulator',
          UserLogin: getSession(USER_LOGIN),
          PolicyID: this.state.PolicyID

        }

      }
      searchPolicyHolder(policyInfoRequest)
        .then(response => {
          const Response = response.CPSearchPolicyHolderByPolicyIDResult;
          if (Response.Result === 'true' && Response.ClientProfile) {
            this.setState({ FullName: Response.ClientProfile[0].FullName, existPolicyHolder: true });
            document.getElementById('receive-person-id').className = 'content-item';
          } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
            showMessage(EXPIRED_MESSAGE);

          } else {
            this.setState({ FullName: '', existPolicyHolder: false });
            document.getElementById('error-popup-not-found-policy-holder').className = "popup error-popup special show";
            document.getElementById('receive-person-id').className = 'content-item disabled';
          }

        }).catch(error => {

          //this.props.history.push('/maintainence');
        });

    }
    return (
      <>
        {this.state.renderMeta &&
          <Helmet>
            <title>Tặng điểm cho người thân – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/4"} />
            <meta property="og:title" content="Tặng điểm cho người thân - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/4"} />
          </Helmet>
        }
        <main className="logined">
          <section className="scgivepoint">
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
                <p>Tặng điểm cho người thân</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="container">
              <form onSubmit={this.handleGivePointSubmit}>
                <div className="givepoint">
                  <div className="personform">
                    <div className="personform__item">
                      <h4 className="title basic-semibold basic-text2">TẶNG ĐIỂM CHO NGƯỜI THÂN:</h4>
                      <div className="content">
                        <div className="content-item">
                          <div className="input special-input-extend">
                            <div className="input__content">
                              <label htmlFor="">Số hợp đồng</label>
                              <input type="search" name="PolicyID" id="policyId" maxLength="9" onChange={this.handleInputGivePointChange} required />
                            </div>
                            <i><img src="../img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                        <div className="content-item">
                          <div className="input special-input-extend">
                            <div className="input__content">
                              <label htmlFor="">CMND/CCCD của Bên mua bảo hiểm</label>
                              <input type="search" name="POID" minLength="3" maxLength="25" onChange={this.handleInputGivePointChange} required />
                            </div>
                            <i><img src="../img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                        <div className="content-item">
                          <div className="input special-input-extend">
                            <div className="input__content extend">
                              <label htmlFor="">Ngày sinh của Bên mua bảo hiểm</label>
                              <input type="date" name="PODOB" id="PODOBID" onChange={this.handleInputGivePointChange} required />
                            </div>

                          </div>
                        </div>
                        <div className="content-item disabled" id="receive-person-id">
                          <div className="yellow-tab">
                            <div className="wrapper">
                              <div className="yellow-tab__item">
                                <p>Người nhận điểm</p>
                                <p>{formatFullName(this.state.FullName)}</p>
                              </div>
                            </div>
                          </div>
                        </div>
                        {(this.state.PolicyID !== '') && (this.state.POID !== '') && (this.state.PODOB !== '') ? (
                          <div className="content-item" onClick={() => findPolicyHolder()}>
                            <div className="search-icon-enable">
                              <img src="../img/icon/look-enable.svg" alt="" />
                            </div>
                            <div className="search-text-enable">
                              <label>Kiểm tra thông tin</label>
                            </div>
                          </div>
                        ) : (
                          <div className="content-item">
                            <div className="search-icon">
                              <img src="../img/icon/look.svg" alt="" />
                            </div>
                            <div className="search-text">
                              <label>Kiểm tra thông tin</label>
                            </div>
                          </div>
                        )
                        }


                        <div className="content-item">
                          <div className="input special-input-extend">
                            <div className="input__content">
                              <label htmlFor="">Nhập số điểm tặng</label>
                              {(this.state.PolicyID !== '') && (this.state.POID !== '') && (this.state.PODOB !== '') && this.state.existPolicyHolder ? (
                                <input type="search" name="givePoint" id="givePointId" maxLength="5" onChange={this.handleInputGivePointChange} required />
                              ) : (
                                <input type="search" name="givePoint" id="givePointId" maxLength="5" disabled required />
                              )}

                            </div>
                            {(this.state.PolicyID !== '') && (this.state.POID !== '') && (this.state.PODOB !== '') && this.state.existPolicyHolder &&
                              <i><img src="../img/icon/edit.svg" alt="" /></i>
                            }
                          </div>
                        </div>
                      </div>
                      <p className="tag">Tương đương {amount.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                    </div>

                    <img className="decor-clip" src="../img/mock.svg" alt="" />
                    <img className="decor-person" src="../img/person.png" alt="" />
                  </div>
                  <LoadingIndicator area="give-point-info" />
                  <p className="error" id="error-not-enough-point">Số điểm hiện có của bạn không đủ đổi, vui lòng kiểm tra lại</p>
                  <div className="bottom-btn">
                    <button className="btn btn-primary disabled" id="btn-give-point">Tiếp tục</button>
                  </div>
                </div>
              </form>
            </div>
          </section>
        </main>
        <PopupGivePointConfirmSuccess totalPoint={this.state.givePoint} polID={this.state.PolicyID} point={remainPoint} />
      </>
    )
  }
}

export default ExchangePointGivePoint;
