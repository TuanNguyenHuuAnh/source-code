import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, POINT, MOBIFONE, VINAPHONE, VIETTEL, CELL_PHONE, GIFT_CART_MOBILE_CARD, TOTAL_CART_POINT, EXPIRED_MESSAGE, AUTHENTICATION } from '../constants';
import { getProductByCategory } from '../util/APIUtils';
import PopupExchangeMobileCardConfirmSucess from '../popup/PopupExchangeMobileCardConfirmSucess';
import '../common/Common.css';
import './ExchangePointMobileCard.css';
import '../popup/PopupCloseButton.css'
import LoadingIndicator from '../common/LoadingIndicator2';
import { roundUp, showMessage, setSession, getSession, getDeviceId} from '../util/common';
import { Link } from 'react-router-dom';

class ExchangePointMobileCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      ProductClientProfile: null,
      SelectClientProfile: null,
      CellPhone: getSession(CELL_PHONE),
      Network: '',
      Price: '',
      Qty: '1',
      usePoint: 0
    };
    this.handleMobileCardSubmit = this.handleMobileCardSubmit.bind(this);
  }


  handleMobileCardSubmit(event) {
    event.preventDefault();
    if (document.getElementById('btn-exchange-point-mobile-card').className === 'btn btn-primary disabled') {
      return;
    }
    this.addMobileCards();
  }

  cpMobileCardGetProducts = (profile) => {
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
      //console.log(JSON.stringify(Res));
      let Response = Res.CPGetProductByCategoryResult;

      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        jsonState.loaded = true;
        jsonState.ProductClientProfile = Response.ClientProfile;
        this.setState(jsonState);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
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


  addMobileCards = () => {
    var totalCards = 0;
    if (getSession(TOTAL_CART_POINT)) {
      totalCards = parseFloat(getSession(TOTAL_CART_POINT));
    }
    if (!getSession(POINT) || (parseFloat(getSession(POINT)) < parseFloat(this.state.usePoint) + totalCards)) {
      document.getElementById('error-mobile-card').className = "error validate";
      return;
    } else {
      document.getElementById('error-mobile-card').className = "error";
    }
    var products = [];
    var strDt = '';
    var detail = '';
    var productClientProfile = this.state.SelectClientProfile;
    for (var i = 0; i < productClientProfile.length; i++) {
      if (productClientProfile[i].strDetail.indexOf(this.state.Network) >= 0 && productClientProfile[i].Price === this.state.Price) {
        detail = productClientProfile[i].strDetail;
        strDt = "{\"phone\":\"" + this.state.CellPhone + "\",\"orderid\":\"" + 'tracking_number' + "\"}" + "|" + detail + "|" + this.state.CellPhone + "|" + roundUp(parseInt(productClientProfile[i].MDiscount)/1000) + "|" + this.state.Qty + "|" + this.props.categorycd;
        var product = {
          category: this.props.categorycd,
          categoryName: 'Nạp thẻ điện thoại',
          fullDescription: '',
          shortDescription: productClientProfile[i].shortDescription,
          IMAGENAME: productClientProfile[i].IMAGENAME,
          IMAGEURL: productClientProfile[i].IMAGEURL,
          Shipping: parseInt(productClientProfile[i].Shipping),
          ProductCode: '',
          Quantity: parseInt(productClientProfile[i].Quantity),
          Price: parseInt(productClientProfile[i].MDiscount),
          Discount: parseFloat(productClientProfile[i].Discount),
          MDiscount: parseInt(productClientProfile[i].MDiscount),
          ProductID: productClientProfile[i].ProductID,
          strDetail: strDt
        };
        //console.log(JSON.stringify(product));
        products[0] = product;
      }
    }

    if (getSession(GIFT_CART_MOBILE_CARD)) {
      var giftCard = JSON.parse(getSession(GIFT_CART_MOBILE_CARD));

      if (giftCard && giftCard.length > 0) {
        var notMatch = false;
        for (var i = 0; i < giftCard.length; i++) {
          let giftProduct = giftCard[i];
          let strDetailArr = giftProduct.strDetail.split("|");
          let qty = strDetailArr[4];
          if (strDetailArr[1] === detail) {
            qty = parseInt(qty) + parseInt(this.state.Qty);
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
          giftCard.push(product);
        }
        setSession(GIFT_CART_MOBILE_CARD, JSON.stringify(giftCard));
      } else {
        setSession(GIFT_CART_MOBILE_CARD, JSON.stringify([product]));
      }
    } else {
      setSession(GIFT_CART_MOBILE_CARD, JSON.stringify([product]));
    }
    if (getSession(TOTAL_CART_POINT)) {
      setSession(TOTAL_CART_POINT, parseFloat(getSession(TOTAL_CART_POINT)) + roundUp(parseFloat(this.state.usePoint)));
      
    } else {
      setSession(TOTAL_CART_POINT, roundUp(parseFloat(this.state.usePoint)));
    }
    document.getElementById('exchange-mobile-card-popup-success').className = "popup refund-confirm-popup special show";
  }

  componentDidMount() {
    this.cpMobileCardGetProducts();
  }
  render() {
    let prefixMobile = ['089', '090', '093', '070', '079', '077', '076', '078'];
    let prefixVinaphone = ['091', '094', '083', '084', '085', '081', '082'];
    let prefixViettel = ['086', '096', '097', '098', '032', '033', '034', '035', '036', '037', '038', '039'];
    let prefixCellPhone = '';
    if (this.state.CellPhone.length > 3) {
      prefixCellPhone = this.state.CellPhone.substring(0, 3);
    }
    let networkType = '';
    if (this.state.Network === '') {
      if (prefixMobile.indexOf(prefixCellPhone) >= 0) {
        networkType = MOBIFONE;
      } else if (prefixVinaphone.indexOf(prefixCellPhone) >= 0) {
        networkType = VINAPHONE;
      } else if (prefixViettel.indexOf(prefixCellPhone) >= 0) {
        networkType = VIETTEL;
      }
    } else {
      networkType = this.state.Network;
    }
    let orignalProductClientProfile = null;
    let moibleProductClientProfile = null;
    let vinaphoneProductClientProfile = null;
    let viettelProductClientProfile = null;
    let selectClientProfile = null;
    let selectPriceProfile = null;
    if (this.state.ProductClientProfile) {
      orignalProductClientProfile = this.state.ProductClientProfile;
      selectClientProfile = orignalProductClientProfile.filter(product => {
        return product.strDetail.indexOf(networkType) >= 0;
      });
      moibleProductClientProfile = orignalProductClientProfile.filter(product => {
        return product.strDetail.indexOf(MOBIFONE) >= 0;
      });
      vinaphoneProductClientProfile = orignalProductClientProfile.filter(product => {
        return product.strDetail.indexOf(VINAPHONE) >= 0;
      });
      viettelProductClientProfile = orignalProductClientProfile.filter(product => {
        return product.strDetail.indexOf(VIETTEL) >= 0;
      });
      selectPriceProfile = selectClientProfile.filter(product => {
        return product.Price === this.state.Price;
      });
    }
    var usePoint = roundUp(parseFloat(this.state.usePoint));
    var remainPoint = (parseFloat(getSession(POINT)) - usePoint - roundUp(parseFloat(getSession(TOTAL_CART_POINT)?getSession(TOTAL_CART_POINT):0)));

    const ChoosePrice = (price, MDiscount, index) => {
      var jsonState = this.state;
      jsonState.Price = price;
      jsonState.usePoint = parseInt(MDiscount) / 1000;
      jsonState.SelectClientProfile = selectClientProfile;
      this.setState(jsonState);
      document.getElementById(index).className = "price-card active";

      selectClientProfile.forEach((product, i) => {
        if (i !== index) {
          document.getElementById(i).className = "price-card";
        }
      });
    }
    const chooseNetwork = (type) => {
      var jsonState = this.state;
      jsonState.Network = type;
      jsonState.Price = '';
      jsonState.usePoint = 0;
      let activePrices = document.getElementsByClassName('price-card active');
      if (activePrices && activePrices.length > 0) {
        for (let i = 0; i < activePrices.length; i++) {
          activePrices[i].className = "price-card";
        }
      }
      if (type === MOBIFONE) {
        jsonState.SelectClientProfile = moibleProductClientProfile;
      } else if (type === VINAPHONE) {
        jsonState.SelectClientProfile = vinaphoneProductClientProfile;
      } else if (type === VIETTEL) {
        jsonState.SelectClientProfile = viettelProductClientProfile;
      }
      this.setState(jsonState);
    }
    if (!this.state.loaded) {
      return (
        <LoadingIndicator area="point-product-info" />
      )
    }
    return (
      <div>
        <main className="logined">
          <section className="scexchangepoint-mobilecard">
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
                <p>Nạp thẻ điện thoại</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="container">
              <form onSubmit={this.handleMobileCardSubmit}>
                <div className="mobilecard">
                  <div className="personform">
                    <div className="personform__item">
                      <h4 className="title basic-bold basic-text2 basic-uppercase">Nạp thẻ điện thoại</h4>
                      <h5 className="sub-title">
                        Mã thẻ điện thoại sẽ được gửi đến số điện thoại Bên mua Bảo hiểm đăng ký trong Hợp đồng là:
                      </h5>
                      <div className="greytab">
                        <div className="greytab__item">
                          <span className="label">Số điện thoại</span>
                          <p>{this.state.CellPhone}</p>
                        </div>
                        <div className="icon-wrapper">
                          <img src="../img/icon/phone-grey.svg" alt="phone" />
                        </div>
                      </div>
                      <div className="choose-network">
                        <div className="title">Vui lòng chọn nhà mạng*</div>
                        <div className="icon-wrap">
                          {networkType === MOBIFONE ?
                            (
                              <div className="icon active" data-tab="mobi" id="mobi active" onClick={() => chooseNetwork(MOBIFONE)}>
                                <img src="../img/icon/mobi-icon.svg" alt="mobiphone" />
                              </div>
                            ) : (
                              <div className="icon" data-tab="mobi" id="mobi active" onClick={() => chooseNetwork(MOBIFONE)}>
                                <img src="../img/icon/mobi-inactive-icon.svg" alt="mobiphone" />
                              </div>
                            )
                          }
                          {networkType === VINAPHONE ?
                            (
                              <div className="icon" data-tab="vina" id="vina" onClick={() => chooseNetwork(VINAPHONE)}>
                                <img src="../img/icon/vina-active-icon.svg" alt="vinaphone" />
                              </div>
                            ) : (
                              <div className="icon" data-tab="vina" id="vina" onClick={() => chooseNetwork(VINAPHONE)}>
                                <img src="../img/icon/vina-icon.svg" alt="vinaphone" />
                              </div>
                            )
                          }
                          {networkType === VIETTEL ?
                            (
                              <div className="icon" data-tab="viettel" id="viettel" onClick={() => chooseNetwork(VIETTEL)}>
                                <img src="../img/icon/viettel-active-icon.svg" alt="viettelphone" />
                              </div>
                            ) : (
                              <div className="icon" data-tab="viettel" id="viettel" onClick={() => chooseNetwork(VIETTEL)}>
                                <img src="../img/icon/viettel-icon.svg" alt="viettelphone" />
                              </div>
                            )
                          }
                        </div>
                      </div>
                      <div className="choose-price">
                        <div className="title">Mệnh giá*</div>
                        <div className="price-wrap">
                          {networkType !== '' && selectClientProfile &&
                            selectClientProfile.map((item, index) => {
                              return (
                                <div className="price-card" id={index} onClick={() => ChoosePrice(item.Price, item.MDiscount, index)}>{item.Price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')}</div>
                              )
                            })
                          }
                        </div>
                      </div>
                      <div className="greytab">
                        <div className="greytab__item">
                          <span className="label">Số điểm tương ứng (đã chiết khấu)</span>
                          <p>{this.state.usePoint}</p>
                        </div>
                      </div>
                    </div>

                    <img className="decor-clip" src="../img/mock.svg" alt="" />
                    <img className="decor-person" src="../img/person.png" alt="" />
                  </div>
                  <p className="error" id="error-mobile-card">Số điểm hiện có của bạn không đủ đổi, vui lòng kiểm tra lại</p>
                  <div className="bottom-btn">
                    {this.state.usePoint > 0 && selectPriceProfile.length > 0 ?
                      (
                        <button className="btn btn-primary" id="btn-exchange-point-mobile-card">Tiếp tục</button>
                      ) : (
                        <button className="btn btn-primary disabled" id="btn-exchange-point-mobile-card">Tiếp tục</button>
                      )
                    }
                  </div>
                </div>
              </form>
            </div>
          </section>
        </main>
        <PopupExchangeMobileCardConfirmSucess usePoint={usePoint} network={this.state.Network} point={remainPoint} price={this.state.Price} categorycd={this.props.categorycd}/>
      </div>
    )
  }
}

export default ExchangePointMobileCard;
