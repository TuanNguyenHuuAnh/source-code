import React, {Component} from 'react';
import {CPSaveLog, getProductByCategory} from '../util/APIUtils';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    GIFT_CART_OPEN_INVESTMENT,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    POINT,
    TOTAL_CART_POINT,
    UPDATE_POINT,
    USER_LOGIN
} from '../constants';
import '../common/Common.css';
import {Link} from 'react-router-dom';
import {checkIsNumber, formatMoney, getDeviceId, getSession, setSession, showMessage} from '../util/common';
import {Helmet} from "react-helmet";
import LoadingIndicator from "../common/LoadingIndicator2";
import {isEmpty} from "lodash";
import PopupExchangeOpenInvestmentConfirmSucess from "../popup/PopupExchangeOpenInvestmentConfirmSucess";
import iconChecked from '../img/icon/check.svg';
import iconsackDollar from '../img/icon/sack-dollar.svg';
import iconMoneyFlower from '../img/icon/money-flower.svg';

class ExchangePointOpenInvestment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            acceptPolicy: false,
            renderMeta: false,
            ProductClientProfile: null,
            showOpenInvestment: false,
            chooseInvestment: [],
            selectInvestment: null,
            investmentValue: 0,
            investmentValueFormat: "",
            investmentValueError: "",
            //--------------------------
            ProductID: '',
            remainPoint: 0,
            totalPoint: getSession(TOTAL_CART_POINT) ? parseFloat(getSession(TOTAL_CART_POINT)) : 0,
            isDisabledSubmitted: false,

        };
        this.handleExchangePointSubmit = this.handleExchangePointSubmit.bind(this);
    };

    componentDidMount() {
        this.cpSaveLog("Đầu tư quỹ mở");
        this.cpInvestmentGetProducts();

    }

    cpInvestmentGetProducts = () => {
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
                const jsonState = this.state;
                jsonState.ProductClientProfile = Response.ClientProfile;
                jsonState.chooseInvestment = Response.ClientProfile.map(item => ({...item, isChecked: false}));
                this.setState(jsonState);
                // if (getSession(TOTAL_CART_POINT)) {
                //     sessionStorage.removeItem(TOTAL_CART_POINT);
                // }
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
            }
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
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

    handleExchangePointSubmit(event) {
        event.preventDefault();
        this.addOpenInvestmentCards();
    }

    addOpenInvestmentCards = () => {
        // let totalPoint = 0;
        
        // if (getSession(TOTAL_CART_POINT)) {
        //     totalPoint = parseFloat(getSession(TOTAL_CART_POINT)).toFixed(1);
        // }

        if (parseFloat(this.state.investmentValue) < 100) {
            this.setState({
                investmentValueError: 'Số điểm cần đổi phải từ 100 điểm trở lên',
                isDisabledSubmitted: true,
            });
            document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
            return;
        }
        if (!getSession(POINT) || (parseFloat(getSession(POINT)) < parseFloat(this.state.investmentValue) + this.state.totalPoint)) {
            this.setState({
                investmentValueError: 'Số điểm hiện có không đủ để đổi quà',
                isDisabledSubmitted: true,
            });
            document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
            return;
        } else {
            this.setState({
                isDisabledSubmitted: false,
            });
        }
        const amount = parseFloat(this.state.investmentValue).toFixed(1);
        let strDt = '';
        let mdiscount = parseInt(amount * 1000);
        strDt = "{\"amount\":\"" + amount + "\",\"phone\":\"" + getSession(CELL_PHONE) + "\",\"orderid\":\"tracking_number\"}" + "|" + this.state.selectInvestment.strDetail + "|dummy|" + mdiscount / 1000 + "|1|" + this.props.categorycd;


        const product = {
            category: this.state.selectInvestment.CategoryID,
            categoryName: "Đầu tư quỹ mở",
            fullDescription: "",
            shortDescription: this.state.selectInvestment ? this.state.selectInvestment?.strDetail : "",
            IMAGENAME: this.state.selectInvestment ? this.state.selectInvestment?.IMAGENAME : "",
            IMAGEURL: this.state.selectInvestment ? this.state.selectInvestment?.IMAGEURL : "",
            Shipping: 0,
            ProductCode: "",
            Quantity: 0,
            Price: parseInt(amount * 1000),
            Discount: 0,
            MDiscount: mdiscount,
            ProductID: this.state.selectInvestment ? this.state.selectInvestment?.ProductID : "",
            strDetail: strDt,
        };

        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            const giftCard = JSON.parse(getSession(GIFT_CART_OPEN_INVESTMENT));
            if (giftCard && giftCard.length > 0) {
                let notMatch = false;
                for (let i = 0; i < giftCard.length; i++) {
                    let giftProduct = giftCard[i];
                    let strDetailArr = giftProduct.strDetail.split("|");
                    let qty = strDetailArr[4];
                    if (strDetailArr[1] === this.state.strDetail) {
                        qty = parseInt(qty) + parseInt(this.state.Quantity);
                        giftProduct.strDetail = strDetailArr[0] + "|" + strDetailArr[1] + "|" + strDetailArr[2] + "|" + strDetailArr[3] + "|" + qty + "|" + strDetailArr[5];
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
                setSession(GIFT_CART_OPEN_INVESTMENT, JSON.stringify(giftCard));
            } else {
                setSession(GIFT_CART_OPEN_INVESTMENT, JSON.stringify([product]));
            }
        } else {
            setSession(GIFT_CART_OPEN_INVESTMENT, JSON.stringify([product]));
        }


        if (getSession(TOTAL_CART_POINT)) {
            setSession(TOTAL_CART_POINT, (this.state.totalPoint + this.state.investmentValue));
        } else {
            setSession(TOTAL_CART_POINT, parseFloat(this.state.investmentValue).toFixed(1));
        }

        const remainPoint = parseFloat(getSession(POINT)) - parseFloat(this.state.investmentValue).toFixed(1);

        // console.log("POINT", parseFloat(getSession(POINT)));
        // console.log("TOTAL_CART_POINT", getSession(TOTAL_CART_POINT));
        // console.log("investmentValue", parseFloat(this.state.investmentValue));
        // console.log("remainPoint", parseFloat(getSession(POINT)) - parseFloat(this.state.investmentValue).toFixed(1));
        // setSession(POINT, remainPoint.toFixed(1));
        // setSession(UPDATE_POINT, UPDATE_POINT);

        this.setState({
            remainPoint: remainPoint.toFixed(1)
        });
        document.getElementById('exchange-investment-popup-success').className = "popup refund-confirm-popup special show";
    }

    render() {
        let logined = false;

        const acceptPolicy = (checked) => {
            if (!checked && !isEmpty(this.state.selectInvestment) && this.state.investmentValue > 0 && !this.state.investmentValueError) {
                document.getElementById('btn-investment-point').className = "btn btn-primary";
                this.setState({
                    isDisabledSubmitted: false,
                });
            } else {
                this.setState({
                    isDisabledSubmitted: true,
                });
                document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
            }
            this.setState({acceptPolicy: !checked});
        }

        const handleInputinvestmentValueChange = (event) => {
            const target = event.target;
            const inputName = target.name;
            let inputValue = target.value;

            if (!checkIsNumber(inputValue) && target.id === 'investmentPointId') {
                if (inputValue === '') {
                    document.getElementById('investmentPointId').value = '';
                    inputValue = 0;
                    this.setState({
                        [inputName]: inputValue,
                        isDisabledSubmitted: true,
                    });
                    document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
                } else {
                    let gPoint = this.state.investmentValue + '';
                    document.getElementById('investmentPointId').value = gPoint.replace('.', ',');
                    // if (gPoint.length < 5) {
                    //     document.getElementById('investmentPointId').value = gPoint.replace('.', ',');
                    // } else {
                    //     document.getElementById('investmentPointId').value = gPoint.replace('.', ',');
                    // }
                    this.setState({
                        isDisabledSubmitted: false,
                    });
                }
                return;
            } else if (target.id === 'investmentPointId') {
                let inputLength = inputValue.length;
                let copyInputValue = inputValue;
                inputValue = inputValue.replace('.', '').replace(',', '.');

                if ((inputLength === 4) && copyInputValue.indexOf(',') < 0) {
                    document.getElementById('investmentPointId').value = formatMoney(inputValue, 0);
                } else {
                    document.getElementById('investmentPointId').value = copyInputValue;
                }

                if (parseFloat(inputValue) > 0) {
                    if (parseFloat(inputValue) < 100) {
                        this.setState({
                            investmentValueError: 'Số điểm cần đổi phải từ 100 điểm trở lên',
                            isDisabledSubmitted: true,
                        });
                        document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
                    } else if (getSession(POINT) && (parseFloat(getSession(POINT)) < parseFloat(inputValue) + (getSession(TOTAL_CART_POINT)?parseFloat(getSession(TOTAL_CART_POINT)):0))) {
                        this.setState({
                            investmentValueError: 'Số điểm hiện có không đủ để đổi quà',
                            isDisabledSubmitted: true,
                        });
                        document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
                    } else {
                        if (this.state.acceptPolicy && !isEmpty(this.state.chooseInvestment)) {
                            document.getElementById('btn-investment-point').className = "btn btn-primary";
                        }
                        this.setState({
                            isDisabledSubmitted: false,
                            investmentValueError: '',
                        });
                    }
                } else {
                    this.setState({
                        isDisabledSubmitted: true,
                    });
                    document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
                }
            }

            if (isNaN(inputValue)) {
                inputValue = inputValue.trim();
            }

            if (inputValue.trim() === '') {
                this.setState({
                    isDisabledSubmitted: true,
                });
                document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
                return;
            }
        
            this.setState({
                [inputName]: parseFloat(inputValue),
                isDisabledSubmitted: false,
            });
        };


        const radioInvestRate = (record) => {
            const updatedData = this.state.chooseInvestment?.map(item => {
                if (item.ProductID === record?.ProductID) {
                    return {...item, isChecked: true};
                }
                return {...item, isChecked: false};
            });


            this.setState({
                chooseInvestment: updatedData,
                selectInvestment: updatedData?.find(item => item.isChecked),
                isDisabledSubmitted: false,
            });

            if (this.state.acceptPolicy && updatedData?.find(item => item.isChecked) && this.state.investmentValue > 0 && !this.state.investmentValueError) {
                document.getElementById('btn-investment-point').className = "btn btn-primary";
                this.setState({
                    isDisabledSubmitted: false,
                });
            } else {
                this.setState({
                    isDisabledSubmitted: true,
                });
                document.getElementById('btn-investment-point').className = "btn btn-primary disabled";
            }
        };

        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }

        const amount = this.state.investmentValue * 1000;

        return (
            <div>
                <main className={logined ? "logined" : ""}>
                    {this.state.renderMeta &&
                        <Helmet>
                            <title>Đầu tư quỹ mở – Dai-ichi Life Việt Nam</title>
                            <meta name="description"
                                  content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                            <meta name="robots" content="noindex, nofollow"/>
                            <meta property="og:type" content="website"/>
                            <meta property="og:url" content={FE_BASE_URL + "/category/9"}/>
                            <meta property="og:title" content="Đầu tư quỹ mở - Dai-ichi Life Việt Nam"/>
                            <meta property="og:description"
                                  content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                            <meta property="og:image"
                                  content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                            <link rel="canonical" href={FE_BASE_URL + "/category/9"}/>
                        </Helmet>
                    }
                    <div className="scpointchange">
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
                                <p>Đầu tư quỹ mở</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </div>
                        </div>
                        <section className="scquydautu">
                            {!this.state.showOpenInvestment ? <div className="container">
                                    <div className="wrapper">
                                        <div className="quydautu">
                                            <h4>Sử dụng điểm thưởng đầu tư Quỹ mở DFVN</h4>
                                            <div className="quydautu__form">
                                                <div className="form-tab-wrapper" style={{marginBottom: 0}}>

                                                    <div className="form-tab" style={{paddingTop: 0, paddingBottom: 16}}>
                                                        <p style={{
                                                            fontSize: 14,
                                                            fontWeight: 500,
                                                        }}>
                                                            “Quỹ mở DFVN” là các Quỹ đầu tư được quản lý và đầu tư bởi tổ
                                                            chức
                                                            uy tín và chuyên nghiệp là Công ty TNHH MTV Quản lý Quỹ Dai-ichi
                                                            Life Việt Nam (“DFVN”). “Quỹ mở DFVN” mang đến cho Khách hàng -
                                                            với
                                                            khả năng tài chính khác nhau - một công cụ tích lũy tài sản dài
                                                            hạn.
                                                            Đầu tư vào Quỹ mở DFVN giúp Khách hàng không phải lo lắng về
                                                            việc
                                                            làm sao tìm kiếm được cơ hội sinh lời tối ưu cho đồng vốn của
                                                            mình;
                                                            có thể tập trung thời gian công sức cho những công việc mình yêu
                                                            thích và làm tốt nhất. Khách hàng khi thực hiện đầu tư vào Quỹ
                                                            mở
                                                            DFVN có thể lựa chọn đầu tư vào hoặc Quỹ Đầu tư Tăng trưởng DFVN
                                                            (DFVN-CAF) hoặc Quỹ Đầu tư Trái phiếu DFVN (DFVN-FIX).
                                                        </p>
                                                        <p style={{
                                                            fontSize: 14,
                                                            fontWeight: 500,
                                                        }}>
                                                            Đầu tư vào Quỹ mở DFVN là lựa chọn tốt cho mục tiêu đầu tư và
                                                            tiết
                                                            kiệm trong dài hạn nhờ các ưu điểm:
                                                        </p>
                                                    </div>
                                                    <div className="form-tab">
                                                        <div className="greytab-wrapper">
                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img
                                                                        src={iconMoneyFlower}
                                                                        alt="flower-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Quản lý đầu tư chuyên nghiệp</p>
                                                                </div>
                                                            </div>

                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img
                                                                        src="../img/icon/surface-93.svg"
                                                                        alt="surface-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Thông tin minh bạch, bảo vệ quyền
                                                                        lợi Nhà đầu tư</p>
                                                                </div>
                                                            </div>
                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img
                                                                        src="../img/icon/hand-choose.svg"
                                                                        alt="hand-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Tối ưu hóa lợi nhuận và rủi ro
                                                                        nhờ danh mục đầu tư đa dạng hóa</p>
                                                                </div>
                                                            </div>
                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img
                                                                        src={iconsackDollar}
                                                                        alt="flower-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Phù hợp với mọi khả năng tài
                                                                        chính</p>
                                                                </div>
                                                            </div>

                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img src="../img/icon/atom.svg"
                                                                                             alt="atom-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Thanh khoản cao và linh hoạt</p>
                                                                </div>
                                                            </div>
                                                            <div className="item">
                                                                <div className="greytab">
                                                                    <i className="icon"><img src="../img/time.svg"
                                                                                             alt="clock-icon"/></i>
                                                                    <p className="minheight40" style={{
                                                                        marginLeft: 12,
                                                                        fontSize: 14,
                                                                        fontWeight: 500
                                                                    }}>Thủ tục tham gia đơn giản, tiết
                                                                        kiệm thời gian</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="form-tab" style={{paddingBottom: 0}}>
                                                        <p className="basic-light" style={{
                                                            fontSize: 14,
                                                            fontWeight: 500
                                                        }}>Điều kiện để yêu cầu quy đổi điểm thưởng Đầu tư vào Quỹ mở DFVN
                                                            quản
                                                            lý:</p>
                                                        <div className="list">
                                                            <p style={{
                                                                fontSize: 14,
                                                                fontWeight: 500
                                                            }}>Bên mua bảo hiểm (BMBH) có điểm thưởng tích lũy từ 100 điểm
                                                                trở
                                                                lên.</p>
                                                            <p style={{
                                                                fontSize: 14,
                                                                fontWeight: 500
                                                            }}>Hoàn tất mở tài khoản đầu tư Quỹ mở.</p>
                                                            <p style={{
                                                                fontSize: 14,
                                                                fontWeight: 500
                                                            }}>BMBH phải là Bên đứng tên tài khoản đầu tư Quỹ mở khi thực
                                                                hiện
                                                                yêu cầu đổi điểm.</p>
                                                        </div>
                                                        <p style={{
                                                            fontSize: 14,
                                                            fontWeight: 500,
                                                            display: 'block'
                                                        }}>
                                                            Quý khách có thể tìm hiểu thêm thông tin về Quỹ mở DFVN và
                                                            thực
                                                            hiện mở tài khoản thông qua nền tảng giao dịch <span
                                                            style={{
                                                                color: "#985801",
                                                                display: 'inline-flex',
                                                                fontWeight: 'bold', cursor: 'pointer'
                                                            }} onClick={() => {
                                                            window.open('https://itrust.dfvn.com.vn/home', '_blank');
                                                        }}>Quỹ mở trực tuyến iTRUST</span>.
                                                        </p>
                                                        <LoadingIndicator area="point-open-investment-info"/>
                                                        <LoadingIndicator area="point-product-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="bottom-btn">
                                            <button className="btn btn-primary" id="btn-exchange-point"
                                                    style={{maxWidth: '700px'}}
                                                    onClick={() => {
                                                        if ((!getSession(CLIENT_ID) || getSession(CLIENT_ID) === '')) {
                                                            document.getElementById('error-popup-only-for-existed').className = "popup error-popup special show";
                                                        } else {
                                                            this.setState({
                                                                showOpenInvestment: true
                                                            });
                                                        }
                                                    }}>Đổi
                                                điểm
                                                ngay
                                            </button>
                                        </div>
                                    </div>
                                </div> :
                                <div className="container">
                                    <form onSubmit={this.handleExchangePointSubmit}>
                                        <div className="openInvestment">
                                            <div className="openInvestmentForm">
                                                <div className="openInvestmentForm__body">
                                                    <h4 className="title basic-text2"
                                                        style={{fontWeight: 700, marginBottom: 12}}>ĐẦU TƯ QUỸ MỞ DO
                                                        DLVN
                                                        QUẢN LÝ</h4>
                                                    <p style={{
                                                        fontSize: 16,
                                                        fontWeight: 600,
                                                        lineHeight: '20px',
                                                        color: '#292929',
                                                        marginBottom: 4
                                                    }}>Chọn Quỹ mở muốn đầu tư</p>
                                                    <div className="checkbox-warpper" style={{flexDirection: 'column'}}>
                                                        {!isEmpty(this.state.chooseInvestment) && this.state.chooseInvestment.length > 0 ? this.state.chooseInvestment?.map((item, index) => (
                                                            <div className="checkbox-item" key={index}>
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input type="checkbox"
                                                                               checked={item.isChecked}
                                                                               onClick={() => radioInvestRate(item)}
                                                                               id='radioInvestRate'/>
                                                                        <div className="checkmark"></div>
                                                                        <p style={{
                                                                            marginLeft: '3px',
                                                                            fontSize: 16,
                                                                            fontWeight: 500,
                                                                            color: '#292929',
                                                                            lineHeight: '24px',
                                                                            padding: '10px 12px'
                                                                        }}>{item.strDetail}</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        )) : null}
                                                    </div>
                                                    <p style={{
                                                        fontSize: 14,
                                                        fontWeight: 600,
                                                        color: '#292929',
                                                        marginBottom: 12,
                                                        marginTop: 10,

                                                    }}>Nhập số điểm muốn đổi</p>
                                                    <div className="content">
                                                        <div className="content-item">
                                                            <div className="input special-input-extend"
                                                                 style={{border: this.state.investmentValueError && "1px solid #DE181F"}}>
                                                                <div className="input__content">
                                                                    <label htmlFor=""
                                                                           style={{color: this.state.investmentValueError && "#DE181F"}}>Số
                                                                        điểm muốn đổi</label>
                                                                    <input type="search" name="investmentValue"
                                                                           id="investmentPointId"
                                                                           onChange={handleInputinvestmentValueChange}
                                                                           required/>
                                                                </div>
                                                                <i><img src="../img/icon/edit.svg" alt=""/></i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br/>
                                                    <br/>
                                                    <div className="content-item">
                                                        <div className="input disabled special-disabled"
                                                             style={{background: "#E6E6E6"}}>
                                                            <div className="input__content">
                                                                <label htmlFor="">Số tiền đầu tư tương ứng</label>
                                                                <input value={amount.toLocaleString('it-IT', {
                                                                    style: 'currency',
                                                                    currency: 'VND'
                                                                }).replace('VND', 'VNĐ')}
                                                                       type="search"
                                                                       disabled
                                                                       style={{color: "#727272"}}
                                                                />
                                                            </div>
                                                            <i><img src="../img/icon/edit.svg" alt=""/></i>
                                                        </div>
                                                    </div>
                                                    {this.state.investmentValueError && <p style={{
                                                        fontSize: 14,
                                                        fontWeight: 500,
                                                        color: "#DE181F",
                                                        marginTop: 12,
                                                        marginBottom: 16
                                                    }}>{this.state.investmentValueError}</p>}
                                                    <p style={{
                                                        fontSize: 16,
                                                        fontWeight: 500,
                                                        color: '#292929',
                                                        lineHeight: '24px',
                                                        marginTop: 16,
                                                    }}>
                                                        Quý khách cần hoàn tất mở tài khoản đầu tư thông qua&nbsp;<p
                                                        style={{
                                                            color: "#985801", display: "inline", fontSize: 16,
                                                            fontWeight: 'bold', cursor: 'pointer'
                                                        }} onClick={() => {
                                                        window.open('https://itrust.dfvn.com.vn/home', '_blank');
                                                    }}>Quỹ mở trực tuyến iTRUST</p>&nbsp;trong vòng 5 ngày kể từ khi
                                                        lựa chọn đổi điểm. Nếu quá thời hạn này, lựa chọn đổi điểm sẽ
                                                        không còn hiệu lực và điểm thưởng sẽ được hoàn trả cho Quý
                                                        khách.
                                                    </p>
                                                </div>

                                                <img className="decor-clip" src="../img/mock.svg" alt=""/>
                                                <img className="decor-person" src="../img/person.png" alt=""/>
                                            </div>
                                            <div style={{
                                                display: 'flex',
                                                justifyContent: 'center',
                                                alignItems: 'center'
                                            }}>
                                                <div
                                                    className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                    style={{
                                                        'maxWidth': '600px',
                                                        backgroundColor: '#f5f3f2',
                                                        display: 'flex'
                                                    }}>
                                                    <div
                                                        className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                        style={{
                                                            flex: '0 0 auto',
                                                            height: '20px',
                                                            cursor: 'pointer',
                                                            margin: 0
                                                        }} onClick={() => acceptPolicy(this.state.acceptPolicy)}>
                                                        <div className="checkmark">
                                                            <img src={iconChecked} alt=""/>
                                                        </div>
                                                    </div>
                                                    <div className="contact-info-tac" style={{
                                                        textAlign: 'justify',
                                                        paddingLeft: '12px'
                                                    }}>
                                                        <p style={{
                                                            fontWeight: "500",
                                                            fontSize: 12,
                                                            lineHeight: '19px'
                                                        }}>Bằng việc tích chọn vào ô vuông này, Tôi đồng ý ủy quyền
                                                            cho Công ty Bảo hiểm Nhân thọ Dai-ichi Việt Nam (“DLVN”)
                                                            được quyền thay mặt Tôi thực hiện việc chuyển tiền đầu tư từ
                                                            việc quy đổi điểm thưởng của Tôi vào Quỹ mở do Công ty TNHH
                                                            MTV Quản lý Quỹ Dai-ichi Life Việt Nam (“DFVN”) đang quản
                                                            lý, bằng hình thức chuyển khoản từ bất kỳ tài khoản nào của
                                                            DLVN vào tài khoản của Quỹ phù hợp với các thông tin mà Tôi
                                                            đã đăng ký, và cho phép DLVN được cung cấp dữ liệu cá
                                                            nhân của Tôi (gồm: họ và tên, CMND/CCCD, số điện thoại) cho
                                                            DFVN nhằm mục đích xử lý dữ liệu theo yêu cầu đổi điểm
                                                            thưởng của Tôi để đầu tư vào Quỹ mở do DFVN đang quản lý
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                            <LoadingIndicator area="give-point-info"/>
                                            <div className="bottom-btn">
                                                {getSession(CLIENT_ID) && getSession(CLIENT_ID) !== '' &&
                                                    <button className="btn btn-primary disabled"
                                                            id="btn-investment-point"
                                                            disabled={this.state.isDisabledSubmitted || !this.state.acceptPolicy || !this.state.selectInvestment}>Thêm vào giỏ quà</button>
                                                }
                                            </div>
                                        </div>
                                    </form>
                                </div>}
                        </section>
                    </div>
                </main>
                <PopupExchangeOpenInvestmentConfirmSucess usePoint={this.state.investmentValue}
                                                          shortDescription={this.state.selectInvestment?.strDetail}
                                                          point={this.state.remainPoint}
                                                          categorycd={this.props.categorycd}
                />
            </div>
        )
    }
}

export default ExchangePointOpenInvestment;
