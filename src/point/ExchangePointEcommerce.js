import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, POINT, CATEGORY_IMG_FOLDER_URL, CELL_PHONE, GIFT_CART_ECOMMERCE, TOTAL_CART_POINT, EXPIRED_MESSAGE, LINK_MENU_NAME, LINK_MENU_NAME_ID, AUTHENTICATION, FE_BASE_URL } from '../constants';
import { getProductByCategory } from '../util/APIUtils';
import '../common/Common.css';
import PopupExchangeCommerceConfirmSucess from '../popup/PopupExchangeCommerceConfirmSucess';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Link } from "react-router-dom";
import { showMessage, setSession, getSession, getDeviceId } from '../util/common';
// import ReactHtmlParser from "react-html-parser";
import parse from 'html-react-parser';
import { Helmet } from "react-helmet";

const ALL = 'Tất cả';
class ExchangePointEcommerce extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      selected: false,
      ProductClientProfile: null,
      SelectClientProfile: null,
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
      CategoryID: 0,
      filterKeywork: ALL,
      filterList: [ALL],
      renderMeta: false
    };
    this.handleEcommerceSubmit = this.handleEcommerceSubmit.bind(this);
    this.resetSelected = this.resetSelected.bind(this);
  }


  handleEcommerceSubmit(event) {
    event.preventDefault();
    this.addEcommerCards();
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
      let Response = Res.CPGetProductByCategoryResult;
      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        var jsonState = this.state;
        jsonState.ProductClientProfile = Response.ClientProfile;
        jsonState.loaded = true;
        let filterList = [ALL];
        if (jsonState.ProductClientProfile) {
          jsonState.ProductClientProfile.map((item, index) => {
            if (item.strDetail.indexOf(']') > 0 && item.strDetail.indexOf('[') >= 0) {
              let filterKey = item.strDetail.substring(item.strDetail.indexOf('[') + 1, item.strDetail.indexOf(']'));
              if (filterList.indexOf(filterKey) < 0) {
                filterList.push(filterKey);
              }

            }
          });
        }
        jsonState.filterList = filterList;
        this.setState(jsonState);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }



  addEcommerCards = () => {
    var totalCards = 0;
    if (getSession(TOTAL_CART_POINT)) {
      totalCards = parseFloat(getSession(TOTAL_CART_POINT));
    }
    if (!getSession(POINT) || (parseFloat(getSession(POINT)) < parseFloat(this.state.usePoint) + totalCards)) {
      document.getElementById('error-ecommerce').className = "error validate";
      return;
    } else {
      document.getElementById('error-ecommerce').className = "error";
    }
    var strDt = "{\"orderid\":\"" + 'tracking_number' + "\",\"phone\":\"" + this.state.CellPhone + "\"}" + "|" + this.state.shortDescription + "|" + this.state.CellPhone + "|" + (parseInt(this.state.MDiscount) / 1000) + "|" + this.state.Quantity + "|" + this.state.CategoryID;

    var product = {
      category: this.state.CategoryID,
      categoryName: 'Phiếu quà tặng điện tử (Voucher điện tử)',
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

    if (getSession(GIFT_CART_ECOMMERCE)) {
      var giftCard = JSON.parse(getSession(GIFT_CART_ECOMMERCE));

      if (giftCard && giftCard.length > 0) {
        var notMatch = false;
        for (var i = 0; i < giftCard.length; i++) {
          let giftProduct = giftCard[i];
          let strDetailArr = giftProduct.strDetail.split("|");
          let qty = strDetailArr[4];
          if (strDetailArr[1] === this.state.strDetail) {
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
        setSession(GIFT_CART_ECOMMERCE, JSON.stringify(giftCard));
      } else {
        setSession(GIFT_CART_ECOMMERCE, JSON.stringify([product]));
      }
    } else {
      setSession(GIFT_CART_ECOMMERCE, JSON.stringify([product]));
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
    let filterKeywork = this.state.filterKeywork;
    let selectClientProfile = null;
    if (this.state.ProductClientProfile) {
      if (filterKeywork === ALL) {
        selectClientProfile = this.state.ProductClientProfile;
      } else {
        selectClientProfile = this.state.SelectClientProfile;
      }

    }
    var remainPoint = (parseFloat(getSession(POINT)) - parseFloat(this.state.usePoint) - parseFloat(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0)).toFixed(1);
    const ChooseCard = (Price, Discount, MDiscount, Quantity, Shipping, strDetail, shortDescription, ProductID, imageUrl, index, imageName, CategoryID) => {
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
      jsonState.CategoryID = CategoryID;
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
    const goHomeLogin = () => {
      this.props.history.push({
        pathname: '/home',
        state: { hideMain: false }
      });
    }
    const toggleFilter = () => {
      if (document.getElementById('ecom-specialfilter-id').className === 'specialfilter') {
        document.getElementById('ecom-specialfilter-id').className = 'specialfilter show';
      } else {
        document.getElementById('ecom-specialfilter-id').className = 'specialfilter';
      }
    }
    const tickCategory = (filterKeywork, id) => {
      document.getElementById(id).className = 'filter-pop-tick ticked';

      this.state.filterList.map((item, index) => {
        if (item !== filterKeywork) {
          document.getElementById(index).className = 'filter-pop-tick';
        }
      });

      let orignalProductClientProfile = this.state.ProductClientProfile;
      let selectClientProfile = null;
      if (filterKeywork === ALL) {
        selectClientProfile = orignalProductClientProfile;
      } else {
        selectClientProfile = orignalProductClientProfile.filter(product => {
          return product.strDetail.indexOf(filterKeywork) >= 0;
        });
      }
      let jsonState = this.state;
      jsonState.filterKeywork = filterKeywork;
      jsonState.SelectClientProfile = selectClientProfile;
      this.setState(jsonState);
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
            <title>Phiếu quà tặng điện tử(Voucher điện tử) – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/12"} />
            <meta property="og:title" content="Phiếu quà tặng điện tử(Voucher điện tử) - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/12"} />
          </Helmet>
        }
        <main className="logined">
          <section className="scmarketpaper">
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
                <p>Phiếu quà tặng điện tử (Voucher điện tử)</p>
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
                          <h4 className="info__title">Phiếu quà tặng bạn chọn</h4>
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

                    <div className="bordertab">
                      <h5 className='line-20'>Mã mua hàng {this.state.strDetail.substring(this.state.strDetail.indexOf('[') + 1, this.state.strDetail.indexOf(']'))} sẽ được gửi đến số điện thoại Bên mua bảo hiểm đăng ký trong Hợp đồng là:</h5>
                      <div className="phone-wrapper">
                        <div className="greytab">
                          <div className="greytab__item">
                            <span className="label">Số điện thoại</span>
                            <p>{this.state.CellPhone}</p>
                          </div>

                          <div className="icon-wrapper">
                            <img src="../img/icon/phone-grey.svg" alt="phone" />
                          </div>
                        </div>
                      </div>
                    </div>

                    <img className="decor-clip" src="../img/mock.svg" alt="" />
                    <img className="decor-person" src="../img/person.png" alt="" />
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
                  <h4>Phiếu quà tặng điện tử (Voucher điện tử)</h4>
                  <div className="specialfilter" id="ecom-specialfilter-id" onClick={() => toggleFilter()}>
                    <div className="filter_button">
                      <div className="icon-left">
                        <img src="../img/icon/filter-icon.svg" alt="filter-icon" />
                      </div>
                      <div className="text">
                        <p>Bộ lọc</p>
                      </div>
                      <div className="icon-right">
                        <img src="../img/icon/arrow-white-down.svg" alt="arrow-down-icon" />
                      </div>
                    </div>
                    <div className="filter_content">
                      <div className="filter-pop">
                        <div className="head">
                          <h4>Chọn sản phẩm</h4>
                          <i className="closebtn"><img src="../img/icon/close.svg" alt="" /></i>
                        </div>

                        {this.state.filterList &&
                          this.state.filterList.map((item, index) => {
                            return (
                              <div className="filter-pop-tick" id={index} onClick={() => tickCategory(item, index)}>
                                <p className="content">{item}</p>
                                <div className="img-wrapper">
                                  <img src="../img/icon/green-ticked.svg" alt="ticked" />
                                </div>
                              </div>
                            )
                          })
                        }

                      </div>
                    </div>
                    <div className="bg"></div>
                  </div>
                </div>
                <div className="marketcards__body">
                  <div className="title">
                    <h5 className="basic-semibold">Chọn phiếu quà tặng điện tử (Voucher điện tử)</h5>
                  </div>
                  {!this.state.loaded &&
                    <LoadingIndicator area="point-product-info" />

                  }
                  <div className="marketcard-wrapper">
                    {selectClientProfile &&
                      selectClientProfile.map((item, index) => {
                        if (item.ProductID !== this.state.ProductID) {
                          return (
                            <div className="marketcard-item" key={index} onClick={() => ChooseCard(item.Price, item.Discount, item.MDiscount, 1, item.Shipping, item.strDetail, item.shortDescription, item.ProductID, item.IMAGEURL, index, item.IMAGENAME, item.CategoryID)}>
                              <div className="market-card">
                                <div className="market-card__header">
                                  <div className="icon-wrapper">
                                    <img src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : CATEGORY_IMG_FOLDER_URL + '0000228_phieu-mua-hang_got-it.jpg'} alt="" />
                                  </div>
                                  <p>{item.shortDescription}</p>
                                </div>
                                <div style={{ marginTop: '-15px', height: '44px', backgroundColor: '#f5f3f2' }}>
                                  {item.CategoryID === 11 &&
                                    <p style={{ fontWeight: '500', fontSize: '12px', lineHeight: '15px', color: '#727272', paddingLeft: '72px', backgroundColor: '#f5f3f2' }}>{parse(item.fullDescription)}</p>
                                  }
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

export default ExchangePointEcommerce;
