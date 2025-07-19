// import 'antd/dist/antd.css';
// import '../claim.css';


import React, {Component} from "react";

import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import {CLIENT_ID, FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, USER_LOGIN, PageScreen} from "../constants";
import {Helmet} from "react-helmet";
import {CPSaveLog} from '../util/APIUtils';
import { Link } from "react-router-dom";


export const TABS = Object.freeze({
    NOTICE: 0,
    PAYMENT_METHOD: 1,
});

class PolicyPayment extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentTab: window.location.pathname === '/utilities/policy-payment-la' ? TABS.PAYMENT_METHOD : TABS.NOTICE,
            renderMeta: false
        };

        this.handlerChangeTab = this.onChangeTab.bind(this);
        this.handlerCreateNewRequest = this.onClickCreateNewRequest.bind(this);
    }

    componentDidMount() {
        this.cpSaveLog(`Web_Open_${PageScreen.GUIDE_PAY_FEE}`);
        trackingEvent(
            "Tiện ích",
            `Web_Open_${PageScreen.GUIDE_PAY_FEE}`,
            `Web_Open_${PageScreen.GUIDE_PAY_FEE}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.GUIDE_PAY_FEE}`);
        trackingEvent(
            "Tiện ích",
            `Web_Close_${PageScreen.GUIDE_PAY_FEE}`,
            `Web_Close_${PageScreen.GUIDE_PAY_FEE}`,
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
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    onChangeTab(tabId) {
        var jsonState = this.state;
        jsonState.currentTab = tabId;
        this.setState(jsonState);
    }

    onClickCreateNewRequest() {
        if (isLoggedIn()) {
            if (getSession(CLIENT_ID) === null
                || getSession(CLIENT_ID) === undefined
                || getSession(CLIENT_ID) === '') {
                this.props.history.push("/familypayment");
            } else {
                this.props.history.push("/mypayment");
            }
        } else {
            this.props.history.push("/");
        }
    }

    render() {
        const copyToClipBoard = (id, value) => {
            navigator.clipboard.writeText(value)
            var tooltip = document.getElementById(id);
            // tooltip.innerHTML = "Đã sao chép";
            tooltip.style.visibility = 'visible';
            setTimeout(() => {
                tooltip.style.visibility = 'hidden';
            }, 3000);
        }
        var jsonState = this.state;
        return (
            <main className={isLoggedIn() ? "logined" : ""}>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Đóng phí bảo hiểm – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Trang thông tin về phương thức đóng phí và những lưu ý quan trọng khi đóng phí bảo hiểm các sản phẩm của Dai-ichi Life Việt Nam"/>
                        <meta name="keywords"
                              content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Đóng phí bảo hiểm"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/utilities/policy-payment"}/>
                        <meta property="og:title" content="Đóng phí bảo hiểm - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Trang thông tin về phương thức đóng phí và những lưu ý quan trọng khi đóng phí bảo hiểm các sản phẩm của Dai-ichi Life Việt Nam"/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/utilities/policy-payment"}/>
                    </Helmet>
                }
                {/* <main className="basic-expand-footer"> */}
                <div className="main-warpper page-ten">
                    {/* Route display */}
                    <section className="scbreadcrums">
                        <div className="container">
                            <div className="breadcrums basic-white">
                                <Link to="/" className="breadcrums__item">
                                    <p>Trang chủ</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </Link>
                                <Link to="/utilities" className="breadcrums__item">
                                    <p>Tiện ích</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </Link>
                                <div className="breadcrums__item">
                                    <p>Đóng phí bảo hiểm</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                            </div>
                        </div>
                    </section>
                    {/* Banner */}
                    <div className="scdongphi">
                        <h1>Đóng phí bảo hiểm</h1>
                    </div>
                    {/* Main page */}
                    <div className="dpbh-page10-page">
                        <div className="tab-pane-container">
                            {/* Tabs */}
                            <section className="policy-menu">
                                <button className={jsonState.currentTab === TABS.NOTICE ? "policy-menu__item active" : "policy-menu__item"}
                                        onClick={() => this.handlerChangeTab(TABS.NOTICE, '')}>
                                    <h2>LƯU Ý QUAN TRỌNG</h2>
                                </button>

                                <button className={jsonState.currentTab === TABS.PAYMENT_METHOD ? "policy-menu__item active" : "policy-menu__item"}
                                        onClick={() => this.handlerChangeTab(TABS.PAYMENT_METHOD, '')}>
                                    <h2>PHƯƠNG THỨC ĐÓNG PHÍ</h2>
                                </button>

                            </section>
                            {/*<div className="tab-pane-container-item">*/}
                            {/*    <div className="tab-pane-container-item-tab1"*/}
                            {/*         onClick={() => this.handlerChangeTab(TABS.NOTICE)}>*/}
                            {/*        <button className={jsonState.currentTab === TABS.NOTICE ?*/}
                            {/*            "tab-pane-container-item-tab1-in basic-text-upper active" : "tab-pane-container-item-tab1-in basic-text-upper"}*/}
                            {/*                data-tab="luuy">*/}
                            {/*            <h2>LƯU Ý QUAN TRỌNG</h2>*/}
                            {/*        </button>*/}
                            {/*    </div>*/}
                            {/*    <div className="tab-pane-container-item-tab2"*/}
                            {/*         onClick={() => this.handlerChangeTab(TABS.PAYMENT_METHOD)}>*/}
                            {/*        <button className={jsonState.currentTab === TABS.PAYMENT_METHOD ?*/}
                            {/*            "tab-pane-container-item-tab2-in basic-text-upper active" : "tab-pane-container-item-tab2-in basic-text-upper"}*/}
                            {/*                data-tab="phuongthuc">*/}
                            {/*            <h2>PHƯƠNG THỨC ĐÓNG PHÍ</h2>*/}
                            {/*        </button>*/}
                            {/*    </div>*/}
                            {/*</div>*/}
                            {/* Contents */}
                            <div className="tab-pane-container-content">
                                <div id="luuy"
                                     className={jsonState.currentTab === TABS.NOTICE ? "tabpane-tab active" : "tabpane-tab"}>
                                    <div className="tab-pane-container-content-one" style={{padding: '24px 24px'}}>
                                        <ul className="list-information">
                                            <li className="main-menu-li-none" style={{marginBottom: '4px', marginLeft: '-10px', fontWeight: '700'}}>
                                                <span className="brown-text">1.</span> Đóng phí bảo hiểm:
                                            </li>
                                            <li className="main-menu-li">
                                                Việc đóng phí bảo hiểm đầy đủ và đúng hạn là điều kiện quan trọng để duy trì hiệu lực Hợp đồng bảo hiểm nhằm đảm bảo các quyền lợi bảo hiểm. Quý khách có thể xem <a className="simple-brown2" style={{'display': 'inline-block'}} onClick={() => this.handlerCreateNewRequest()}>chi tiết thông tin về phí bảo hiểm và định kỳ đóng phí</a>  trên Dai-ichi Connect. Dai-ichi Life Việt Nam hỗ trợ thông báo nhắc phí bảo hiểm bằng các hình thức như Zalo hoặc tin nhắn SMS, Email, ứng dụng Dai-ichi Connect, … Tuy nhiên, trong mọi trường hợp, Quý khách cần đóng phí bảo hiểm đầy đủ và đúng hạn ngay cả khi không nhận được các thông báo này. Khi đóng phí bảo hiểm, Quý khách cần lưu giữ chứng từ đóng phí cho tới khi nhận được thông báo đã nhận được phí bảo hiểm từ Dai-ichi Life Việt Nam.
                                            </li>
                                            <li className="main-menu-li">
                                                Quý khách vui lòng đóng phí trực tiếp qua các kênh thanh toán của Dai-ichi Life Việt Nam được giới thiệu tại mục Phương thức đóng phí, không đóng phí bằng cách chuyển tiền vào tài khoản ngân hàng hoặc ví điện tử của cá nhân Đại lý bảo hiểm. Dai-ichi Life Việt Nam không chịu bất kỳ trách nhiệm nào về các khoản thanh toán mà Quý khách chuyển vào tài khoản ngân hàng hoặc ví điện tử của cá nhân Đại lý bảo hiểm.
                                            </li>
                                            <li className="main-menu-li">
                                                Nếu đóng phí bằng tiền mặt tại Văn phòng/Tổng đại lý của Dai-ichi Life Việt Nam hoặc cho Đại lý bảo hiểm đang phục vụ hợp đồng (kiểm tra thông tin Đại lý bảo hiểm phục vụ tại mục Đại lý bảo hiểm, trang chi tiết Hợp đồng bảo hiểm trên Dai-ichi Connect), Quý khách vui lòng yêu cầu Nhân viên/Đại lý bảo hiểm giao <span className="basic-bold">Phiếu thu tiền</span> ngay khi hoàn tất thanh toán phí bảo hiểm. Các Nhân viên/Đại lý bảo hiểm thu phí bảo hiểm mà không phát hành Phiếu thu tiền hợp lệ là vi phạm quy định thu phí của Dai-ichi Life Việt Nam, Quý khách cần từ chối thanh toán.
                                            </li>
                                            <li className="main-menu-li">
                                                Quý khách có thể kiểm tra tình trạng Phiếu thu tiền ngay trên Dai-ichi Connect (phiên bản ứng dụng điện thoại di động) bằng cách đăng nhập và quét mã QR trên Phiếu thu tại biểu tượng QR góc trên bên phải của màn hình Trang chủ của Dai-ichi Connect.
                                            </li>
                                            <li className="main-menu-li">
                                                Nếu sau thời gian 03 ngày làm việc, Quý khách không nhận được tin nhắn/email xác nhận đã đóng phí của Dai-ichi Life Việt Nam, vui lòng liên hệ ngay với chúng tôi.
                                            </li>

                                            <li className="main-menu-li-none" style={{marginBottom: '4px', marginLeft: '-10px', fontWeight: '700'}}>
                                                <span className="brown-text">2.</span> Thời hạn đóng phí của Hợp đồng bảo hiểm tùy thuộc sản phẩm bảo hiểm tham gia:
                                            </li>
                                            <li className="main-menu-li-none" style={{marginBottom: '4px', marginLeft: '16px'}}>
                                                <span className="brown-text" style={{display: 'inline'}}>Đối với sản phẩm bảo hiểm chính thuộc dòng Bảo hiểm Liên kết đầu tư</span> (Liên kết chung/Liên kết đơn vị):
                                            </li>
                                            <li className="main-menu-li">
                                                Thời hạn đóng phí sẽ bằng Thời hạn bảo hiểm (hay còn gọi là Thời hạn hợp đồng). Đây là khoảng thời gian tối đa mà Quý khách có thể đóng phí bảo hiểm. Cần hiểu rằng, phí bảo hiểm đóng vào sẽ được phân bổ vào các Quỹ liên kết chung/Quỹ Liên kết đơn vị theo Quyền lợi đầu tư được quy định trong Hợp đồng bảo hiểm.
                                            </li>
                                            <li className="main-menu-li">
                                                Trong thời gian 3 hoặc 4 năm đầu, tùy từng sản phẩm có quy định trong Hợp đồng bảo hiểm (gọi là Thời hạn đóng phí tối thiểu), Quý Khách cần đóng phí đầy đủ và đúng hạn. Việc dừng đóng phí trong khoảng thời gian này sẽ làm mất hiệu lực Hợp đồng.
                                            </li>
                                            <li className="main-menu-li-nonee">
                                                Sau thời gian này, Quý khách có thể tiếp tục đóng phí/ dừng đóng phí hoặc chủ động thay đổi kế hoạch đóng phí phù hợp với tình hình tài chính và nhu cầu bảo hiểm của mình. Khi dừng đóng phí, Tài khoản hợp đồng vẫn tiếp tục được hưởng kết quả đầu tư từ Quỹ Liên kết chung/ Quỹ Liên kết đơn vị, đồng thời vẫn bị khấu trừ Phí bảo hiểm rủi ro và Phí quản lý hợp đồng hàng tháng để duy trì các Quyền lợi bảo vệ. Hợp đồng bảo hiểm sẽ mất hiệu lực nếu giá trị Tài khoản hợp đồng bị khấu trừ hết.
                                            </li>
                                            <li className="main-menu-li-nonee">
                                                Để đảm bảo các kế hoạch tài chính lâu dài, Quý khách nên lựa chọn kế hoạch đóng phí dài hơn Thời hạn đóng phí tối thiểu để Tài khoản hợp đồng có giá trị tích lũy đủ thanh toán các khoản khấu trừ hàng tháng nhằm duy trì Quyền lợi bảo vệ.
                                            </li>

                                            <li className="main-menu-li-none" style={{marginBottom: '4px', marginLeft: '16px'}}>
                                                <span className="brown-text" style={{display: 'inline'}}>Đối với sản phẩm bảo hiểm chính thuộc dòng Bảo hiểm khác:</span> 
                                            </li>
                                            <li className="main-menu-li">
                                                Thời hạn đóng phí được quy định cụ thể trong Hợp đồng bảo hiểm và ghi rõ tại Giấy chứng nhận bảo hiểm. Thời hạn này có thể bằng hoặc ngắn hơn Thời hạn bảo hiểm. Khách hàng cần duy trì việc đóng phí đầy đủ và đúng hạn trong suốt thời gian này.
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div id="phuongthuc"
                                     className={jsonState.currentTab === TABS.PAYMENT_METHOD ? "tabpane-tab active" : "tabpane-tab"}>
                                    <div className="tab-pane-container-content-two">
                                        <p>
                                        Nhằm tạo thuận lợi cho Khách hàng khi đóng phí bảo hiểm để duy trì hiệu lực Hợp đồng, 
                                        Dai-ichi Life Việt Nam đã phát triển nhiều kênh đóng phí với các phương thức đóng phí đa dạng,
                                         đặc biệt là đóng phí trực tuyến.
                                        </p>
                                        
                                        <ul className="list-information">
                                            <li className="sub-list-li">
                                                Ứng dụng <span className="text-bold dai-ichi"> Dai-ichi Connect </span> hoặc website <a className="simple-brown2"
                                                                                          style={{'display': 'inline-block'}}
                                                                                          onClick={() => {
                                                                                              window.open("http://kh.dai-ichi-life.com.vn");
                                                                                          }}>
                                                http://kh.dai-ichi-life.com.vn
                                            </a>
                                            </li>
                                            {/* <li className="sub-list-li-second">- Thanh toán bằng chuyển khoản/quét mã QR từ các ứng dụng ngân hàng/ví điện tử qua cổng thanh toán VNPAY.</li>
                                            <li className="sub-list-li-second">- Thanh toán bằng Thẻ nội địa (Napas) hoặc Thẻ quốc tế (VISA, MasterCard, JCB, American Express) của hơn 40 ngân hàng Việt Nam.</li> */}
                                            {/* <li className="sub-list-li">
                                                Internet banking/ Mobile banking của các ngân hàng sau:
                                            </li> */}
                                        </ul>
                                        {/* <div className="img-bank-four">
                                            // <img src="/img/bank-icon/vnpay-qr.svg" alt="bank"/>
                                        </div> */}
                                        <div className="grid-paid-channel">
                                            <div className="row-paid-channel">
                                                <div className="img-bank-vnpay-qr"></div>
                                                <div className="text-field">
                                                    <p>Chuyển khoản từ 54 ứng dụng ngân hàng/ví điện tử qua cổng thanh toán <div className="vnpayqr-text"></div></p> 
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-napas"></div>
                                                <div className="text-field">
                                                    <p>Thẻ ATM/thẻ nội địa (Napas)</p>
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-visa"></div>
                                                <div className="text-field">
                                                    <p>Thẻ quốc tế (VISA, MasterCard, JCB, American Express)</p>
                                                </div>
                                            </div>
                                        </div>
                                        <ul className="list-information">
                                            <li className="sub-list-li">
                                                Internet banking/Mobile banking (sử dụng chức năng Thanh toán hóa đơn/Phí bảo hiểm của Ngân hàng):
                                            </li>
                                        </ul>
                                        <div className="grid-paid-channel two">
                                            <div className="row-paid-channel">
                                                <div className="img-bank-sacombank"></div>
                                                <div className="text-field">
                                                    <p>Sacombank Pay, mBanking, <nobr>e-Banking</nobr></p>
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-bacabank"></div>
                                                <div className="text-field vnpay-qr">
                                                    <p>BAC A BANK Mobile Banking</p>
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-shb"></div>
                                                <div className="text-field vnpay-qr">
                                                    <p>SHB SAHA, SHB Mobile</p>
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-vietcombank"></div>
                                                <div className="text-field vnpay-qr">
                                                    <p>VCB Digibank</p>
                                                </div>
                                            </div>
                                            <div className="row-paid-channel">
                                                <div className="img-bank-lpbank"></div>
                                                <div className="text-field vnpay-qr">
                                                    <p>LPBank</p>
                                                </div>
                                            </div>
                                        </div>
                                        <ul className="list-information">
                                            <li className="sub-list-li">
                                                Ủy thác thanh toán (Auto Debit) dành cho khách hàng có tài
                                                khoản tại Sacombank, SHB, LPBank
                                            </li>
                                            <li className="sub-list-li">
                                                Ví điện tử bằng ứng dụng
                                                <span className="text-bold momo"> Ví Momo </span>,
                                                <span className="text-bold papoo">Ví Payoo </span>
                                                (hoặc website <a className="simple-brown2"
                                                                style={{'display': 'inline-block'}}
                                                                onClick={() => {
                                                                            window.open("https://bill.payoo.vn");
                                                                }}> https://bill.payoo.vn/
                                                </a>) 
                                            </li>
                                            <li className="sub-list-li">
                                                Nộp tiền mặt tại 13.000 cửa hàng liên kết với <span className="text-bold papoo">Payoo </span> trên toàn quốc
                                            </li>
                                            <li className="sub-list-li">
                                                Mạng lưới 
                                                <span className="text-bold"> Bưu điện </span>
                                                của Tổng Công ty Bưu điện Việt Nam 
                                                <span className="text-bold"> (VNPOST) </span>
                                                trên toàn quốc
                                            </li>
                                            <li className="sub-list-li">
                                                Mạng lưới giao dịch của
                                                <span className="text-bold viettle"> Viettel </span>
                                                và siêu thị Viettel (Viettel Store) trên toàn quốc
                                            </li>
                                            {/* <li className="sub-list-li">
                                                Ví điện tử bằng ứng dụng Payoo hoặc website <a className="simple-brown2"
                                                                                               style={{'display': 'inline-block'}}
                                                                                               onClick={() => {
                                                                                                   window.open("https://bill.payoo.vn");
                                                                                               }}> https://bill.payoo.vn
                                            </a> và đóng phí bằng tiền mặt tại 2.400 cửa hàng liên kết với Payoo trên
                                                toàn quốc.
                                            </li> */}
                                            <li className="sub-list-li">
                                                Chuyển khoản hoặc nộp tiền mặt vào tài khoản của Dai-ichi Life Việt Nam
                                                (miễn phí khi giao dịch trong cùng hệ thống ngân hàng):
                                            </li>
                                        </ul>
                                        
                                        <div className="account-list">
                                            <div className="account-list_table">
                                                <table>
                                                    <tr>
                                                        <th colspan="2">Chủ tài khoản: Công ty TNHH Bảo hiểm Nhân thọ
                                                            Dai-ichi Việt Nam
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/Logo-Sacombank.svg"
                                                                 alt="Sacombank" />
                                                        </td>
                                                        <td>010 0000 33818 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-1', '010000033818')}>
                                                            <span className="copy-row" id="copy-row-1">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/SHB.svg" alt="SHB" style={{ marginBottom: '-8px', marginTop: '-8px', height: '30px', width :'60%', height: '60%', minWidth: '40px', minHeight: '21px' }}/>
                                                        </td>
                                                        <td>101 0502 953 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-2', '1010502953')}>
                                                        <span className="copy-row" id="copy-row-2">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/lienviet-bank.svg"
                                                                 alt="lienviet-bank" style={{ marginTop: '-2px' }}/>
                                                        </td>
                                                        <td>006 6115 90001 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-3', '006611590001')}>
                                                        <span className="copy-row" id="copy-row-3">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/bacabank.svg" alt="bacabank" style={{ marginBottom: '-8px', marginTop: '-8px', width :'75%', height: 'auto' }}/>
                                                        </td>
                                                        <td>200 0010 6000 3720 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-4', '200001060003720')}>
                                                        <span className="copy-row" id="copy-row-5">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/vietcombank.svg"
                                                                 alt="vietcombank" style={{ marginBottom: '-8px', marginTop: '-8px' }}/>
                                                        </td>
                                                        <td>007 1000 329053 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-5', '0071000329053')}>
                                                        <span className="copy-row" id="copy-row-4">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/BIDV.svg" alt="BIDV"/>
                                                        </td>
                                                        <td>310 0119 400 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-6', '3100119400')}>
                                                        <span className="copy-row" id="copy-row-6">Đã sao chép</span></div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/Agrinbank.svg" alt="Agrinbank"/>
                                                        </td>
                                                        <td>170 020 1180 905 <div className="icon-copy" onClick={()=>copyToClipBoard('copy-row-7', '1700201180905')}>
                                                        <span className="copy-row" id="copy-row-7">Đã sao chép</span></div></td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div className="policy-payment-method-noted" style={{textAlign: 'center'}}>
                                            {/* <p className="noted-text">Lưu ý:</p>
                                            <p><span style={{ fontWeight: 'bold', fontSize: 16, fontStyle: 'initial' }}>Nội dung chuyển khoản:</span>  [Số HĐBH/YCBH] [Tên Bên mua bảo hiểm] [Số điện thoại]</p>
                                            <p><span style={{ fontWeight: 'bold', fontSize: 16, fontStyle: 'initial' }}>Ví dụ:</span> HĐ 1234567 Nguyen Van A 0917888000</p> */}
                                            <p>Nội dung đóng tiền:<span style={{ fontWeight: 'bold', fontSize: 16, fontStyle: 'initial' }}> Số hợp đồng/Số hồ sơ YCBH-Tên Bên mua bảo hiểm-Số điện thoại</span></p>
                                            <p>Ví dụ:<span style={{ fontWeight: 'bold', fontSize: 16, fontStyle: 'initial' }}> 1122334-Nguyen Van A-0918080808</span></p>
                                        </div>
                                        <ul className="list-information">
                                            <li className="sub-list-li">
                                                Trung tâm Dịch vụ khách hàng tại các <Link to="/utilities/network" className="simple-brown2"
                                                                                        style={{'display': 'inline-block'}}
                                                                                        > Văn phòng/Tổng Đại lý Dai-ichi Life Việt Nam
                                                </Link> 
                                            </li>
                                        </ul>
                                        {/* <ul className="list-information" style={{ width: '100%'}}>
                                            <li className="sub-list-li">
                                                Chuyển khoản vào tài khoản của Dai-ichi Life Việt Nam (phí chuyển do
                                                người chuyển tiền chịu):
                                            </li>
                                        </ul>
                                        <div className="account-list">
                                            <div className="account-list_table">
                                                <table>
                                                    <tr>
                                                        <th colspan="2">Tài khoản: Công ty TNHH Bảo hiểm Nhân thọ
                                                            Dai-ichi Việt Nam
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <img src="/img/bank-icon/citibank.svg" alt="citibank" style={{ marginTop: '-2px' }}/>
                                                        </td>
                                                        <td>0300178006</td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <ul className="list-information" style={{ width: '100%'}}>
                                            <li className="sub-list-li">
                                                Mạng lưới Bưu cục của Tổng công ty Bưu điện Việt Nam trên toàn quốc
                                                (VNPost).
                                            </li>
                                            <li className="sub-list-li">
                                                Mạng lưới giao dịch của Viettel và siêu thị Viettel (Viettel Store) trên
                                                toàn quốc.
                                            </li>
                                            <li className="sub-list-li">
                                                Trung tâm Dịch vụ khách hàng tại các Văn phòng/Tổng Đại lý Dai-ichi Life
                                                Việt Nam.
                                            </li>
                                            <li className="sub-list-li">
                                               Trung tâm Dịch vụ khách hàng tại các Văn phòng/Tổng Đại lý Dai-ichi Life
                                               Việt Nam (bằng tiền mặt hoặc Thẻ nội địa/ Thẻ quốc tế).
                                            </li>
                                            <li className="sub-list-li">
                                               Qua các hệ thống ATM của ngân hàng Vietcombank.
                                            </li>
                                        </ul> */}
                                        <div className="bottom-btn" onClick={() => this.handlerCreateNewRequest()}>
                                            <button className={isLoggedIn() ? "logined_btn btn-primary" : "btn btn-primary"}>Đóng phí ngay</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        );
    }
}

export default PolicyPayment;
