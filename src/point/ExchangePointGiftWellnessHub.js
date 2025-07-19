import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CATEGORY_IMG_FOLDER_URL,
    CELL_PHONE,
    CLIENT_ID,
    FE_BASE_URL,
    GIFT_CART_WELLNESS_CARD,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    POINT,
    TOTAL_CART_POINT,
    USER_LOGIN
} from '../constants';
import {getProductByCategory} from '../util/APIUtils';
import {getDeviceId, getSession, setSession} from '../util/common';
import '../common/Common.css';
import PopupExchangeCommerceConfirmSucess from '../popup/PopupExchangeCommerceConfirmSucess';
import LoadingIndicator from '../common/LoadingIndicator2';
import {Link} from "react-router-dom";
import {Helmet} from "react-helmet";
import AlertPopupInvalidUser from "../components/AlertPopupInvalidUser";
import PopupGiftWellnessConfirmSuccess from "../popup/PopupGiftWellnessConfirmSuccess";
import iconNoPolicy from '../img/popup/no-policy-new.svg';
import './ExchangePointGiftWellnessHub.css';

class ExchangePointGiftWellnessHub extends Component {
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
            fullDescription: '',
            imageUrl: '',
            imageName: '',
            CellPhone: getSession(CELL_PHONE),
            usePoint: 0,
            renderMeta: false,
            invalidTypeUser: false,
            msgPopup: '',
            popupImgPath: '',
            remainPoint: 0,

        };
        this.handleEcommerceSubmit = this.handleEcommerceSubmit.bind(this);
        this.resetSelected = this.resetSelected.bind(this);
    }


    handleEcommerceSubmit(event) {
        event.preventDefault();
        this.addWellnessCards();
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
        };

        getProductByCategory(submitRequest)
            .then(Res => {
                const Response = Res.CPGetProductByCategoryResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState(prevState => ({
                        ...prevState, ProductClientProfile: Response.ClientProfile, loaded: true
                    }));
                }
                this.setState({renderMeta: true});
            })
            .catch(error => {
                this.setState({renderMeta: true});
            });
    }

    addWellnessCards = () => {
        let totalCards = 0;
        const sessionTotalCartPoint = getSession(TOTAL_CART_POINT) || 0;
        if (sessionTotalCartPoint) {
            totalCards = parseFloat(sessionTotalCartPoint);
        }

        if (sessionTotalCartPoint) {
            setSession(TOTAL_CART_POINT, (parseFloat(sessionTotalCartPoint) + parseFloat(this.state.usePoint)).toFixed(1));
        } else {
            setSession(TOTAL_CART_POINT, parseFloat(this.state.usePoint).toFixed(1));
        }

        const sessionPoint = getSession(POINT);
        if (!sessionPoint || parseFloat(sessionPoint) < parseFloat(this.state.usePoint) + totalCards) {
            document.getElementById('error-ecommerce').className = "error validate";
            return;
        } else {
            document.getElementById('error-ecommerce').className = "error";
        }

        let detail = this.state.strDetail;
        const indexStart = detail.indexOf('[');
        const indexEnd = detail.indexOf(']');
        let typeSup = 'Không có';
        if (indexStart >= 0 && indexEnd > 0) {
            typeSup = detail.substring(indexStart + 1, indexEnd);
            //Fix issue 1 số version ios <= 11 trắng trang khi truy cập link d-connect web replace regex js bằng basic js
            const startDelimiterIndex = detail.indexOf('|', indexStart + 1);
            const endDelimiterIndex = detail.indexOf('|', indexEnd);
            if (startDelimiterIndex !== -1 && endDelimiterIndex !== -1) {
                detail = detail.substring(0, startDelimiterIndex) + typeSup + detail.substring(endDelimiterIndex + 1);
            }
        }

        const strDt = `{"orderid":"tracking_number","phone":"${this.state.CellPhone}","shipping":"shipping_fee"}|${detail}|${typeSup}|${parseInt(this.state.MDiscount > 0 ? this.state.MDiscount : this.state.Price) / 1000}|${this.state.Quantity}|${this.props.categorycd}`;

        const product = {
            category: this.props.categorycd,
            categoryName: 'Quà tặng sức khỏe',
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

        const sessionGiftCartWellnessCard = getSession(GIFT_CART_WELLNESS_CARD);
        if (sessionGiftCartWellnessCard) {
            const giftCard = JSON.parse(sessionGiftCartWellnessCard);
            let notMatch = true;
            for (let i = 0; i < giftCard.length; i++) {
                const giftProduct = giftCard[i];
                const strDetailArr = giftProduct.strDetail.split("|");
                let qty = parseInt(strDetailArr[4]);
                if (strDetailArr[1] === detail) {
                    qty += parseInt(this.state.Quantity);
                    giftProduct.strDetail = `${strDetailArr[0]}|${strDetailArr[1]}|${strDetailArr[2]}|${strDetailArr[3]}|${qty}|${strDetailArr[5]}`;
                    giftCard[i] = giftProduct;
                    notMatch = false;
                    break;
                }
            }
            if (notMatch) {
                giftCard.push(product);
            }
            console.log("giftCard", giftCard);
            setSession(GIFT_CART_WELLNESS_CARD, JSON.stringify(giftCard));
        } else {
            setSession(GIFT_CART_WELLNESS_CARD, JSON.stringify([product]));
        }

        document.getElementById('gift-wellness-confirm-popup-success').className = "popup gift-wellness-confirm-popup special show";

        this.setState({
            remainPoint: (parseFloat(getSession(POINT)) - parseFloat(getSession(TOTAL_CART_POINT))).toFixed(1),
        });

    }

    resetSelected(value) {
        this.setState({selected: value, ProductID: ''});
    }

    componentDidMount() {
        if (!this.state.selected) {
            this.cpEcommerceGetProducts();
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (this.state.invalidTypeUser !== prevState.invalidTypeUser) {
            if (this.state.invalidTypeUser) {
                this.setState({
                    msgPopup: 'Chức năng chỉ dành cho Khách hàng có hợp đồng với Dai-chi-Life Việt Nam',
                    popupImgPath: iconNoPolicy,
                })
            }
        }
    }

    render() {
        console.log("remainPoint", this.state.remainPoint);

        const ChooseCard = (Price, Discount, MDiscount, Quantity, Shipping, strDetail, shortDescription, fullDescription, ProductID, imageUrl, index, imageName) => {
            if (!getSession(CLIENT_ID)) {
                this.setState({
                    invalidTypeUser: true,
                });
            } else {
                this.setState(prevState => ({
                    ...prevState,
                    Price,
                    Discount,
                    MDiscount,
                    Quantity,
                    ProductID,
                    Shipping,
                    strDetail,
                    shortDescription,
                    fullDescription,
                    imageUrl,
                    usePoint: parseInt(MDiscount > 0 ? MDiscount : Price) / 1000,
                    imageName,
                    selected: true
                }));
            }
        }

        const changeQuantity = (event) => {
            let value = event.target.value;
            if (!isNaN(value) && value.length <= 3 && value.trim().length > 0 && parseInt(value) > 0) {
                this.setState(prevState => ({
                    ...prevState, Quantity: parseInt(value), usePoint: prevState.Price / 1000 * parseInt(value)
                }));
            }
        }

        const plusQuantity = () => {
            this.setState(prevState => {
                const newQuantity = parseInt(prevState.Quantity) + 1;
                if (newQuantity > 999) return prevState;
                return {
                    ...prevState, Quantity: newQuantity, usePoint: prevState.Price / 1000 * newQuantity
                };
            });
        }

        const minusQuantity = () => {
            this.setState(prevState => {
                const newQuantity = parseInt(prevState.Quantity) - 1;
                if (newQuantity === 0) return prevState;
                return {
                    ...prevState, Quantity: newQuantity, usePoint: prevState.Price / 1000 * newQuantity
                };
            });
        }

        const showError = () => {
            document.getElementById('error-popup-only-for-existed').className = "popup error-popup special show";
        }

        const selectedMenu = (id, menuName) => {
            setSession(LINK_MENU_NAME, menuName);
            setSession(LINK_MENU_NAME_ID, id);
        }

        const renderMetaTags = () => {
            if (!this.state.renderMeta) return null;
            return (<Helmet>
                <title>Quà tặng sức khỏe – Dai-ichi Life Việt Nam</title>
                <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                <meta name="robots" content="noindex, nofollow"/>
                <meta property="og:type" content="website"/>
                <meta property="og:url" content={FE_BASE_URL + "/category/5"}/>
                <meta property="og:title" content="Quà tặng sức khỏe - Dai-ichi Life Việt Nam"/>
                <meta property="og:description"
                      content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                <meta property="og:image"
                      content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                <link rel="canonical" href={FE_BASE_URL + "/category/5"}/>
            </Helmet>);
        }

        const closeInvalidUserType = () => {
            this.setState({invalidTypeUser: false});
        }

        return (<div>
            {renderMetaTags()}
            <main className="logined" style={{padding: !getSession(USER_LOGIN) && 0}}>
                <section className="scmarketpaper"
                         style={{minHeight: !getSession(USER_LOGIN) && 'calc(100vh - 56px)'}}>
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
                            <p>Quà tặng sức khỏe</p>
                            <p className='breadcrums__item_arrow'>></p>
                        </div>
                    </div>
                    <div className="container">
                        {this.state.selected ? <div className="useroption-card">
                            <div className="personform">
                                <div className="content">
                                    <div className="content__item">
                                        <div className="info">
                                            <h4 className="info__title">Quà tặng bạn chọn</h4>
                                            <h4 className="info__title">Số lượng *</h4>
                                            <div className="btn-group">
                                                <div className="counter">
                                                    {this.state.Quantity <= 1 ? (
                                                        <button className="counter__btn minus disabled"
                                                                onClick={() => minusQuantity()}><span>-</span>
                                                        </button>) : (<button className="counter__btn minus"
                                                                              onClick={() => minusQuantity()}>
                                                        <span>-</span>
                                                    </button>)}
                                                    <div className="counter__num">
                                                        <input maxLength="3"
                                                               onChange={(event) => changeQuantity(event)}
                                                               value={this.state.Quantity}></input>
                                                    </div>
                                                    {this.state.Quantity >= 999 ? (
                                                        <button className="counter__btn plus disabled"
                                                                onClick={() => plusQuantity()}><span>+</span>
                                                        </button>) : (<button className="counter__btn plus"
                                                                              onClick={() => plusQuantity()}>
                                                        <span>+</span>
                                                    </button>)}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="content__item">
                                        <div className="market-card" style={{ height: 177, width: 276 }}>
                                            <div className="market-card__header">
                                                <div style={{
                                                    background: '#ffffff',
                                                    borderRadius: '50%',
                                                    width: 44,
                                                    height: 44,
                                                    flexShrink: '0',
                                                    display: 'flex',
                                                    justifyContent: 'center',
                                                    alignItems: 'center'
                                                }}>
                                                    <img src={CATEGORY_IMG_FOLDER_URL + this.state.imageUrl}
                                                         alt=""/>
                                                </div>
                                                <div style={{paddingLeft: '16px'}}>
                                                    <p style={{
                                                        paddingLeft: '0px',
                                                        lineHeight: '21px',
                                                        display: 'initial',
                                                    }}>{this.state.shortDescription}</p>
                                                    <h5 style={{
                                                        fontSize: '12px',
                                                        fontWeight: 500,
                                                        lineHeight: '14.52px',
                                                        color: '#727272',
                                                        marginTop: 8,
                                                    }}>{this.state.fullDescription}</h5>
                                                </div>
                                            </div>
                                            <div className="market-card__body">
                                                <div className="point">
                                                    <div className="coin">
                                                        <img src="../img/icon/coint-icon.svg" alt="coin"/>
                                                    </div>
                                                    <p className="number">{this.state.usePoint.toLocaleString('it-IT', {
                                                        style: 'currency', currency: 'VND'
                                                    }).replace('VND', '')}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                <img className="decor-person" src="../img/person.png" alt=""/>
                            </div>
                            <div className="reward-message-container">
                                <p className="reward-message">
                                    <span>*Nhằm tri ân các khách hàng thân thiết trong chương trình Gắn bó dài lâu. </span><br/>
                                    Với <span className="font-bold">3999 điểm</span>, quý khách được đổi 1 <span className="font-bold">Đồng hồ Garmin Forerunner 55</span>.
                                </p>
                                <p className="reward-message">
                                    Đây là một cách để bày tỏ sự trân trọng của Dai-ichi Life Việt Nam đối với sự ủng hộ của quý khách trong thời gian qua.
                                </p>
                            </div>

                            <p className="error" id="error-ecommerce">Số điểm hiện có của bạn không đủ đổi, vui
                                lòng kiểm tra lại</p>
                            <div className="bottom-btn">
                                {getSession(CLIENT_ID) && getSession(CLIENT_ID) !== '' &&
                                    <button className="btn btn-primary"
                                            onClick={this.handleEcommerceSubmit}>Tiếp tục</button>}
                                {getSession(CLIENT_ID) === '' &&
                                    <button className="btn btn-primary" onClick={() => showError()}>Tiếp
                                        tục</button>}
                                {getSession(CLIENT_ID) === null && <Link to="/home" className="btn"
                                                                         onClick={() => selectedMenu('ah1', 'Trang chủ')}>
                                    <button className="btn btn-primary">Tiếp tục</button>
                                </Link>}
                            </div>
                        </div> : <div className="marketcards">
                            <div className="marketcards__header">
                                <h4>Quà tặng sức khỏe</h4>
                            </div>
                            <div className="marketcards__body">
                                <div className="title">
                                    <h5 className="basic-semibold">Chọn quà tặng sức khỏe</h5>
                                </div>
                                {!this.state.loaded && <LoadingIndicator area="point-product-info"/>}
                                <div className="marketcard-wrapper">
                                    {this.state.ProductClientProfile && this.state.ProductClientProfile.map((item, index) => {
                                        if (item.ProductID !== this.state.ProductID) {
                                            return (<div className="marketcard-item" key={index}
                                                         onClick={() => ChooseCard(item.Price, item.Discount, item.MDiscount, 1, item.Shipping, item.strDetail, item.shortDescription, item.fullDescription, item.ProductID, item.IMAGEURL, index)}>
                                                <div className="market-card" style={{ height: 177, width: 276 }}>
                                                    <div className="market-card__header">
                                                        <div style={{
                                                            background: '#ffffff',
                                                            borderRadius: '50%',
                                                            width: 44,
                                                            height: 44,
                                                            flexShrink: '0',
                                                            display: 'flex',
                                                            justifyContent: 'center',
                                                            alignItems: 'center'
                                                        }}>
                                                            <img src={CATEGORY_IMG_FOLDER_URL + item.IMAGEURL} alt=""/>
                                                        </div>
                                                        <div style={{paddingLeft: '16px'}}>
                                                            <p style={{
                                                                paddingLeft: '0px',
                                                                lineHeight: '21px',
                                                                display: 'initial',
                                                            }}>{item.shortDescription}</p>
                                                            <h5 style={{
                                                                fontSize: '12px',
                                                                fontWeight: 500,
                                                                lineHeight: '14.52px',
                                                                color: '#727272',
                                                                marginTop: 8,
                                                            }}>{item.fullDescription}</h5>
                                                        </div>
                                                    </div>
                                                    <div className="market-card__body">
                                                        <div className="point">
                                                            <div className="coin">
                                                                <img src="../img/icon/coint-icon.svg"
                                                                     alt="coin"/>
                                                            </div>
                                                            <p className="number">{(parseInt(item.Price / 1000)).toLocaleString('it-IT', {
                                                                style: 'currency', currency: 'VND'
                                                            }).replace('VND', '')}</p>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>)
                                        }
                                    })}
                                </div>
                            </div>
                        </div>}
                    </div>
                </section>
            </main>
            <PopupGiftWellnessConfirmSuccess usePoint={this.state.usePoint}
                                             shortDescription={this.state.shortDescription} point={this.state.remainPoint}
                                             price={this.state.Price} qty={this.state.Quantity}
                                             categorycd={this.props.categorycd}
                                             resetSelected={this.resetSelected}/>

            {this.state.invalidTypeUser &&
                <AlertPopupInvalidUser closePopup={() => closeInvalidUserType()} msg={this.state.msgPopup}
                                       imgPath={this.state.popupImgPath}/>}
        </div>)
    }
}

export default ExchangePointGiftWellnessHub;
