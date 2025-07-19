import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, POINT, CATEGORY_IMG_FOLDER_URL, CELL_PHONE, GIFT_CART_DOCTOR_CARD, TOTAL_CART_POINT, LINK_MENU_NAME, LINK_MENU_NAME_ID, AUTHENTICATION, FE_BASE_URL } from '../constants';
import { getProductByCategory } from '../util/APIUtils';
import { setSession, getSession, getDeviceId } from '../util/common';
import '../common/Common.css';
//import '../popup/PopupCloseButton.css'
import PopupExchangeCommerceConfirmSucess from '../popup/PopupExchangeCommerceConfirmSucess';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Link } from "react-router-dom";
import { Helmet } from "react-helmet";

class ExchangePointDoctorPaper extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      selected: false,
      ProductClientProfile: null,
      Price: 0,
      Quantity: 0,
      MDiscount: 0,
      Discount: 0,
      ProductID: '',
      Shipping: 0,
      strDetail: '',
      shortDescription: '',
      imageUrl: '',
      imageName: '',
      CellPhone: getSession(CELL_PHONE),
      usePoint: 0,
      renderMeta: false
    };
    this.handleEcommerceSubmit = this.handleEcommerceSubmit.bind(this);
    this.resetSelected = this.resetSelected.bind(this);
  }


  handleEcommerceSubmit(event) {
    event.preventDefault();
    this.addDoctorCards();
    //this.cpEcommerceSubmitForm();

  }

  cpEcommerceGetProducts = (profile) => {
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
        jsonState.ProductClientProfile = Response.ClientProfile;
        jsonState.loaded = true;
        this.setState(jsonState);
        //this.cpEcommerceSubmitForm(profile, Response.ClientProfile);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }


  addDoctorCards = () => {
    var totalCards = 0;
    if (getSession(TOTAL_CART_POINT)) {
      totalCards = parseFloat(getSession(TOTAL_CART_POINT));
    }
    if (!getSession(POINT) || ((parseFloat(getSession(POINT)) < parseFloat(this.state.usePoint) + totalCards))) {
      document.getElementById('error-ecommerce').className = "error validate";
      return;
    } else {
      document.getElementById('error-ecommerce').className = "error";
    }
    let detail = this.state.strDetail;
    let indexStart = this.state.strDetail.indexOf('[');
    let indexEnd = this.state.strDetail.indexOf(']');
    let typeSup = 'Không có';
    if (indexStart >= 0 && indexEnd > 0) {
      typeSup = this.state.strDetail.substring(indexStart + 1, indexEnd);
      var position = detail.indexOf('Phiếu khám sức khỏe');
      detail = [detail.slice(position, position + 20), typeSup, detail.slice(position + 19)].join('');
    }
    var strDt = "{\"orderid\":\"" + 'tracking_number' + "\",\"phone\":\"" + this.state.CellPhone + "\",\"shipping\":\"shipping_fee" + "\"}" + "|" + detail + "|" + typeSup + "|" + parseInt(this.state.MDiscount) / 1000 + "|" + this.state.Quantity + "|" + this.props.categorycd;
    var product = {
      category: this.props.categorycd,
      categoryName: 'Phiếu khám sức khỏe',
      fullDescription: '',
      shortDescription: this.state.shortDescription,
      IMAGENAME: this.state.imageName,
      IMAGEURL: this.state.imageUrl,
      Shipping: this.state.Shipping,
      ProductCode: '',
      Quantity: 0,
      Price: this.state.Price,
      Discount: this.state.Discount,
      MDiscount: this.state.MDiscount,
      ProductID: this.state.ProductID,
      strDetail: strDt
    };

    if (getSession(GIFT_CART_DOCTOR_CARD)) {
      var giftCard = JSON.parse(getSession(GIFT_CART_DOCTOR_CARD));

      if (giftCard && giftCard.length > 0) {
        var notMatch = false;
        for (var i = 0; i < giftCard.length; i++) {
          let giftProduct = giftCard[i];
          let strDetailArr = giftProduct.strDetail.split("|");
          let qty = strDetailArr[4];
          if (strDetailArr[1] === detail) {
            qty = parseInt(qty) + parseInt(this.state.Quantity);
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
        setSession(GIFT_CART_DOCTOR_CARD, JSON.stringify(giftCard));
      } else {
        setSession(GIFT_CART_DOCTOR_CARD, JSON.stringify([product]));
      }
    } else {
      setSession(GIFT_CART_DOCTOR_CARD, JSON.stringify([product]));
    }
    if (getSession(TOTAL_CART_POINT)) {
      setSession(TOTAL_CART_POINT, (parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat(this.state.usePoint)).toFixed(1));
    } else {
      setSession(TOTAL_CART_POINT, parseFloat(this.state.usePoint).toFixed(1));
    }


    document.getElementById('exchange-ecommerce-popup-success').className = "popup refund-confirm-popup special show";


  }

  resetSelected(value) {
    this.setState({ selected: value, ProductID: '' });
  }

  componentDidMount() {
    if (!this.state.selected) {
      this.cpEcommerceGetProducts();
    }
  }
  render() {
    //console.log("scmarketpaper");


    var remainPoint = (parseFloat(getSession(POINT)) - parseFloat(this.state.usePoint) - parseFloat(getSession(TOTAL_CART_POINT))).toFixed(1);
    const ChooseCard = (Price, Discount, MDiscount, Quantity, Shipping, strDetail, shortDescription, ProductID, imageUrl, index, imageName) => {
      var jsonState = this.state;
      jsonState.Price = Price;
      jsonState.Discount = Discount;
      jsonState.MDiscount = MDiscount;
      jsonState.Quantity = Quantity;
      jsonState.ProductID = ProductID;
      jsonState.Shipping = Shipping;
      jsonState.strDetail = strDetail;
      jsonState.shortDescription = shortDescription;
      jsonState.imageUrl = imageUrl;
      jsonState.usePoint = parseInt(MDiscount) / 1000;
      jsonState.imageName = imageName;
      jsonState.selected = true;
      this.setState(jsonState);


    }

    const changeQuatity = (event) => {
      let value = event.target.value;
      var jsonState = this.state;
      if (!isNaN(value) && value.length <= 3 && value.trim().length > 0 && parseInt(value) > 0) {
        jsonState.Quantity = parseInt(value);
        jsonState.usePoint = jsonState.Price / 1000 * jsonState.Quantity
      }
      this.setState(jsonState);
    }
    const plugQuatity = () => {
      var jsonState = this.state;
      jsonState.Quantity = parseInt(jsonState.Quantity) + 1;
      if (jsonState.Quantity > 999) {
        return;
      }
      jsonState.usePoint = jsonState.Price / 1000 * jsonState.Quantity
      this.setState(jsonState);
    }
    const minusQuatity = () => {
      var jsonState = this.state;
      var quatity = parseInt(jsonState.Quantity);
      if (quatity === 1) {
        return;
      }
      jsonState.Quantity = parseInt(jsonState.Quantity) - 1;
      jsonState.usePoint = jsonState.Price / 1000 * jsonState.Quantity
      this.setState(jsonState);
    }
    const showClinicAddress = () => {
      document.getElementById('doctor-address-popup-id').className = 'popup special doctor-address-popup show';
    }

    const showError = () => {
      document.getElementById('error-popup-only-for-existed').className = "popup error-popup special show";
    }
    const selectedMenu = (id, menuName) => {
      setSession(LINK_MENU_NAME, menuName);
      setSession(LINK_MENU_NAME_ID, id);
    }
    return (
      <div>
        {this.state.renderMeta &&
          <Helmet>
            <title>Phiếu khám sức khỏe – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/5"} />
            <meta property="og:title" content="Phiếu khám sức khỏe - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/5"} />
          </Helmet>
        }
        <main className="logined" style={{ padding: !getSession(USER_LOGIN) && 0 }}>
          <section className="scmarketpaper" style={{ minHeight: !getSession(USER_LOGIN) && 'calc(100vh - 56px)' }}>
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
                <p>Phiếu khám sức khỏe</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="container">
              {this.state.selected &&
                <div className="useroption-card">

                  <div className="personform">
                    <div className="content">
                      <div className="content__item">
                        <div className="info">
                          <h4 className="info__title">Phiếu mua hàng bạn chọn</h4>
                          <h4 className="info__title">Số lượng *</h4>
                          <div className="btn-group">
                            <div className="counter">
                              {this.state.Quantity <= 1 ? (
                                <button className="counter__btn minus disabled" onClick={() => minusQuatity()}><span>-</span></button>
                              ) : (
                                <button className="counter__btn minus" onClick={() => minusQuatity()}><span>-</span></button>
                              )}
                              <div className="counter__num">
                                <input maxLength="3" onChange={(event) => changeQuatity(event)} value={this.state.Quantity}></input>
                              </div>
                              {this.state.Quantity >= 999 ? (
                                <button className="counter__btn plus disabled" onClick={() => plugQuatity()}><span>+</span></button>
                              ) : (
                                <button className="counter__btn plus" onClick={() => plugQuatity()}><span>+</span></button>
                              )}
                            </div>
                          </div>
                          <p className="tag">Tương đương {(parseInt(this.state.MDiscount) * parseInt(this.state.Quantity)).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')} </p>
                        </div>
                      </div>
                      <div className="content__item">
                        <div className="market-card">
                          <div className="market-card__header">
                            <div className="icon-wrapper">
                              <img src={CATEGORY_IMG_FOLDER_URL + this.state.imageUrl} alt="" />
                            </div>
                            <p>{this.state.shortDescription}</p>
                          </div>
                          <div className="market-card__body">
                            <div className="point">
                              <div className="coin">
                                <img src="../img/icon/coint-icon.svg" alt="coin" />
                              </div>
                              <p className="number">{this.state.usePoint.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', '')}</p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <img className="decor-clip" src="../img/mock.svg" alt="" />
                    <img className="decor-person" src="../img/person.png" alt="" />
                  </div>
                  <div className="link-wrapper">
                    <p className="simple-brown basic-semibold" onClick={() => showClinicAddress()}>Địa chỉ phòng khám ></p>
                  </div>
                  <p className="error" id="error-ecommerce">Số điểm hiện có của bạn không đủ đổi, vui lòng kiểm tra lại</p>
                  <div className="bottom-btn">
                    {getSession(CLIENT_ID) && getSession(CLIENT_ID) !== '' &&
                      <button className="btn btn-primary" onClick={this.handleEcommerceSubmit}>Tiếp tục</button>
                    }
                    {getSession(CLIENT_ID) === '' &&
                      <button className="btn btn-primary" onClick={() => showError()}>Tiếp tục</button>
                    }
                    {getSession(CLIENT_ID) === null &&
                      <Link to="/home" className="btn" onClick={() => selectedMenu('ah1', 'Trang chủ')}><button className="btn btn-primary">Tiếp tục</button></Link>
                    }
                  </div>

                </div>

              }
              <div className="marketcards">
                <div className="marketcards__header">
                  <h4>Phiếu khám sức khỏe</h4>
                </div>
                <div className="marketcards__body">
                  <div className="title">
                    <h5 className="basic-semibold">Chọn phiếu khám sức khỏe</h5>
                  </div>
                  {!this.state.loaded &&
                    <LoadingIndicator area="point-product-info" />

                  }
                  <div className="marketcard-wrapper">
                    {this.state.ProductClientProfile &&
                      this.state.ProductClientProfile.map((item, index) => {
                        if (item.ProductID !== this.state.ProductID) {
                          return (
                            <div className="marketcard-item" key={index} onClick={() => ChooseCard(item.Price, item.Discount, item.MDiscount, 1, item.Shipping, item.strDetail, item.shortDescription, item.ProductID, item.IMAGEURL, index)}>
                              <div className="market-card">
                                <div className="market-card__header">
                                  <div className="icon-wrapper">
                                    <img src={CATEGORY_IMG_FOLDER_URL + item.IMAGEURL} alt="" />
                                  </div>
                                  <p>{item.shortDescription}</p>
                                </div>
                                <div className="market-card__body">
                                  <div className="point">
                                    <div className="coin">
                                      <img src="../img/icon/coint-icon.svg" alt="coin" />
                                    </div>
                                    <p className="number">{(parseInt(item.Price / 1000)).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', '')}</p>
                                  </div>
                                </div>
                              </div>
                            </div>

                          )
                        }
                      })
                    }

                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
        <PopupExchangeCommerceConfirmSucess usePoint={this.state.usePoint} shortDescription={this.state.shortDescription} point={remainPoint} price={this.state.Price} qty={this.state.Quantity} categorycd={this.props.categorycd} resetSelected={this.resetSelected} />
      </div>
    )
  }
}

export default ExchangePointDoctorPaper;
