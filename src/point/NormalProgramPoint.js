import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    CLIENT_ID,
    AUTHENTICATION,
    CLIENT_PROFILE,
    TOTAL_CART_POINT,
    CLASSPO,
    POINT,
    FE_BASE_URL,
    USER_LOGIN, PageScreen
} from '../constants';
import './NormalProgramPoint.css';
import {Link} from 'react-router-dom';
import {formatMoney, getDeviceId, getSession, trackingEvent} from '../util/common';
import {Helmet} from "react-helmet";
import {CPSaveLog} from '../util/APIUtils';

export const CLASSPOMAPSWAP = {'Thân thiết': 'NoVip', 'Vàng': 'Gold', 'Bạc': 'Silver', 'Kim Cương': 'Diamond'};

class NormalProgramPoint extends Component {
    constructor(props) {
        super(props);
        this.state = {
            classPO: getSession(CLASSPO),
            hdpoint: '',
            point: getSession(POINT),
            renderMeta: false
        }
    };

    componentDidMount() {
        this.cpSaveLog(`Web_Open_${PageScreen.LOYALTY_PAGE}`);
        trackingEvent(
            "Điểm thưởng",
            `Web_Open_${PageScreen.LOYALTY_PAGE}`,
            `Web_Open_${PageScreen.LOYALTY_PAGE}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.LOYALTY_PAGE}`);
        trackingEvent(
            "Điểm thưởng",
            `Web_Close_${PageScreen.LOYALTY_PAGE}`,
            `Web_Close_${PageScreen.LOYALTY_PAGE}`,
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

    render() {
        const selectTab = (tabName) => {
            var x, i, tablinks;
            x = document.getElementsByClassName("main-component");
            for (i = 0; i < x.length; i++) {
                x[i].className = x[i].className.replace(" show-component", "")
            }
            tablinks = document.getElementsByClassName("accumulate-points__menu-list__item");
            for (i = 0; i < x.length; i++) {
                tablinks[i].className = "accumulate-points__menu-list__item";
            }
            document.getElementById(tabName).className = document.getElementById(tabName).className + ' show-component';
            document.getElementById(tabName + "-1").className = "accumulate-points__menu-list__item accumulate-points__menu-list__item-active";
        }
        const selectedSubMenu = (id, subMenuName) => {
            // setSession(LINK_SUB_MENU_NAME, subMenuName);
            // setSession(LINK_SUB_MENU_NAME_ID, id);
        }

        let clientProfile = null;
        if (getSession(CLIENT_PROFILE)) {
            clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
        }
        let fullName = '';
        if (clientProfile !== null) {
            fullName = clientProfile[0].FullName;
        }

        var usePoint = '0';
        if (getSession(TOTAL_CART_POINT)) {
            usePoint = getSession(TOTAL_CART_POINT);
        }

        var logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }
        let activeTabId = 'NoVip-1';
        let activeTabContentId = 'NoVip';
        if (getSession(CLASSPO)) {
            activeTabId = CLASSPOMAPSWAP[getSession(CLASSPO)] + '-1';
            activeTabContentId = CLASSPOMAPSWAP[getSession(CLASSPO)];
        }
        return (
            <main className={logined ? "logined" : ""}>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Điểm thưởng – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Trang thông tin về Chương trình tích lũy điểm thưởng &#8243;Gắn bó dài lâu&#8243; với mong muốn gửi lời tri ân đến tất cả khách hàng đã lựa chọn Dai-ichi Life Việt Nam."/>
                        <meta name="keywords"
                              content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Điểm thưởng"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/point"}/>
                        <meta property="og:title" content="Điểm thưởng - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Trang thông tin về Chương trình tích lũy điểm thưởng &#8243;Gắn bó dài lâu&#8243; với mong muốn gửi lời tri ân đến tất cả khách hàng đã lựa chọn Dai-ichi Life Việt Nam."/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/point"}/>
                    </Helmet>
                }
                <section className="scbreadcrums">
                    <div className="container">
                        <div className="breadcrums basic-white">
                            <Link to="/" className="breadcrums__item">
                                <p>Trang chủ</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </Link>
                            <div className="breadcrums__item">
                                <p>Điểm thưởng</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="scpromotionpointcard" style={{ padding: 0 }}>
                    <div className="container">
                        <div className="promotionpointcard-wrapper">
                            <div className="promotionpointcard">
                                {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
                                    <div className="card">
                                        <div className="card__header">
                                            <h4 className="basic-bold">{fullName}</h4>
                                            <div className="ranktag"><p
                                                className="basic-semibold">{this.state.classPO}</p></div>
                                        </div>
                                        <div className="card__footer">
                                            <div className="card__footer-item">
                                                <div className="cardfootertab">
                                                    <p>Điểm thưởng: <span
                                                        className="basic-red">{formatMoney(this.state.point)}</span></p>
                                                    <Link to="/point-history"
                                                          onClick={() => selectedSubMenu('p3', 'Lịch sử điểm thưởng')}><a
                                                        className="basic-semibold simple-brown">Lịch sử
                                                        điểm &gt;</a></Link>
                                                </div>
                                                <span className="line"></span>
                                                <div className="cardfootertab">
                                                    <p>Giỏ quà của tôi: <span
                                                        className="basic-red">{formatMoney(usePoint)}</span></p>
                                                    <Link to="/gift-cart"
                                                          onClick={() => selectedSubMenu('p2', 'Giỏ quà của tôi')}><a
                                                        className="basic-semibold simple-brown">Xem chi
                                                        tiết &gt;</a></Link>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                </section>
                <section className="scnormalprogram" style={{ padding: 0 }}>
                    <div className="container">
                        <div className="program-field special-program reverse">
                            <div className="program-field__image"></div>
                            <div className="program-field__content">
                                <h1 className="basic-red" style={{ fontWeight: 'bold' }}>GẮN BÓ DÀI
                                    LÂU</h1>
                                <p className="marginboth paddingright30">
                                    Với phương châm hoạt động “Khách hàng là trên hết”, Dai-ichi Life Việt Nam đã
                                    triển khai <span
                                    className="basic-bold">Chương trình điểm thưởng “Gắn bó dài lâu”</span> từ năm 2013
                                    nhằm tri ân
                                    Khách hàng đã tín nhiệm lựa chọn tham gia bảo hiểm với Dai-ichi Life Việt Nam, qua
                                    đó, mang đến cho Khách hàng những quyền lợi cộng thêm bên cạnh các quyền lợi bảo
                                    hiểm đã quy định trong Hợp đồng bảo hiểm. Chương trình đã nhận được rất nhiều
                                    sự quan tâm và đón nhận từ Khách hàng.
                                </p>
                                <p className="marginboth paddingright30">
                                    <span className="basic-bold">Chương trình điểm thưởng “Gắn bó dài lâu” </span>
                                    dành cho Khách hàng có hợp đồng bảo hiểm đang hiệu lực và đáp ứng một số
                                    điều kiện/tiêu chí của Chương trình. Khách hàng dễ dàng tích lũy điểm thưởng
                                    liên quan đến các dịch vụ của hợp đồng bảo hiểm, chương trình ưu đãi liên quan đến
                                    sản phẩm và nhiều hoạt động tri ân Khách hàng của Dai-ichi Life Việt Nam.
                                </p>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="scaccumulatepoint">
                    <div className="container">
                        <div className="accumulate-points">
                            <h2 className="basic-bold">Điều kiện tham gia và cơ chế tích điểm</h2>
                            <div className="sub-title">
                                <p className="bigheight">
                                    Đối với Khách hàng có Hợp đồng bảo hiểm đang có hiệu lực, phân hạng Chương trình dựa
                                    trên Tổng số phí bảo hiểm cơ bản quy năm của tất các Hợp đồng đang có hiệu lực của
                                    cùng một Bên mua bảo hiểm (cho Chương trình này, gọi tắt là Tổng phí bảo hiểm).
                                </p>
                            </div>
                            <div className="accumulate-points__menu-list">
                                <div className="item">
                                    <div
                                        className={activeTabId === 'NoVip-1' ? "accumulate-points__menu-list__item accumulate-points__menu-list__item-active" : "accumulate-points__menu-list__item"}
                                        id="NoVip-1" onClick={() => selectTab('NoVip')}>
                                        <div className="icon">
                                            <img src="../img/icon/9.1/9.1-menulist-icon-heart.svg" alt=""/>
                                        </div>
                                        <div className="name"><p>Thân thiết</p></div>
                                    </div>
                                </div>
                                <div className="item">
                                    <div
                                        className={activeTabId === 'Silver-1' ? "accumulate-points__menu-list__item accumulate-points__menu-list__item-active" : "accumulate-points__menu-list__item"}
                                        id="Silver-1" onClick={() => selectTab('Silver')}>
                                        <div className="icon">
                                            <img src="../img/icon/9.1/9.1-menulist-icon-silver.svg" alt=""/>
                                        </div>
                                        <div className="name"><p>Bạc</p></div>
                                    </div>
                                </div>
                                <div className="item">
                                    <div
                                        className={activeTabId === 'Gold-1' ? "accumulate-points__menu-list__item accumulate-points__menu-list__item-active" : "accumulate-points__menu-list__item"}
                                        id="Gold-1" onClick={() => selectTab('Gold')}>
                                        <div className="icon">
                                            <img src="../img/icon/9.1/9.1-menulist-icon-gold.svg" alt=""/>
                                        </div>
                                        <div className="name"><p>Vàng</p></div>
                                    </div>
                                </div>
                                <div className="item">
                                    <div
                                        className={activeTabId === 'Diamond-1' ? "accumulate-points__menu-list__item accumulate-points__menu-list__item-active" : "accumulate-points__menu-list__item"}
                                        id="Diamond-1" onClick={() => selectTab('Diamond')}>
                                        <div className="icon">
                                            <img src="../img/icon/9.1/9.1-menulist-icon-diamond.svg" alt=""/>
                                        </div>
                                        <div className="name"><p>Kim cương</p></div>
                                    </div>
                                </div>
                            </div>

                            <div className="accumulate-points__main-component">
                                <div className="main-component-wrapper">
                                    <div
                                        className={activeTabContentId === "NoVip" ? "main-component heart show-component" : "main-component heart"}
                                        id="NoVip">
                                        <div className="title">
                                            <p className="basic-semibold">(Tổng phí bảo hiểm/năm dưới 30 triệu)</p>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-thamgiahd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Hợp đồng cũ đáo hạn và tham gia hợp đồng mới, có phí bảo hiểm
                                                        quy năm:</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Dưới 30 triệu đồng</p>
                                                        <p className="right-aligned">500 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Từ 30 triệu đến 50 triệu đồng</p>
                                                        <p>800 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Trên 50 triệu đồng</p>
                                                        <p className="right-aligned">1.500 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-knhd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Duy trì hợp đồng</h3>
                                                    <p>(Ngày kỷ niệm hợp đồng, khi đã nộp đầy đủ phí bảo hiểm)</p>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Vào ngày kỷ niệm 5 năm</p>
                                                        <p className="right-aligned">50 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p style={{lineHeight: '22px'}}>Vào ngày kỷ niệm 10
                                                            năm, <br/> và mỗi 10 năm tiếp theo</p>
                                                        <p className="right-aligned">50 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/*<div className="main-component__row">*/}
                                        {/*    <div className="left">*/}
                                        {/*        <div className="icon">*/}
                                        {/*            <img src="../img/icon/9.1/9.1-menulist-icon-nopphibh.svg" alt=""/>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*    <div className="right">*/}
                                        {/*        <div className="right__subtitle">*/}
                                        {/*            <h3>Thanh toán phí bảo hiểm định kỳ</h3>*/}
                                        {/*            <p className="color-grey marginboth">*/}
                                        {/*                - Dịch vụ này áp dụng đối với Hợp đồng bảo hiểm đang*/}
                                        {/*                được phục vụ bởi Tư vấn tài chính Dai-ichi Life Việt Nam thuộc*/}
                                        {/*                kênh Đại lý truyền thống;*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Phí bảo hiểm định kỳ được thanh toán qua Dai-ichi Connect,*/}
                                        {/*                ngân hàng (chuyển khoản hoặc tiền mặt), ví điện tử (MoMo, Payoo,*/}
                                        {/*                Ví Việt), máy POS, Bưu điện (VNPost, Viettel), hệ thống cửa hàng*/}
                                        {/*                liên kết Payoo.*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Điểm thưởng tương đương với số tiền phí đóng như sau:*/}
                                        {/*            </p>*/}
                                        {/*        </div>*/}
                                        {/*        <div className="right__content">*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 2 đến năm thứ 4</p>*/}
                                        {/*                <p className="right-aligned">1,0% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 5 trở đi</p>*/}
                                        {/*                <p className="right-aligned">0,5% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="bottom-text" style={{textAlign: 'left'}}><p><span*/}
                                        {/*                className="red-text basic-bold">Lưu ý: </span></p></div>*/}
                                        {/*            <div className='content b-gray'>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Điểm thưởng được tính vào thời điểm phí bảo hiểm được ghi nhận vào năm hợp đồng,</p>*/}
                                        {/*                </div>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Tổng điểm thưởng tích lũy tối đa là 1.000 điểm/01 năm hợp đồng.</p>*/}
                                        {/*                </div>*/}
                                        {/*            </div>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*</div>*/}
                                    </div>
                                    <div
                                        className={activeTabContentId === "Silver" ? "main-component silver show-component" : "main-component silver"}
                                        id="Silver">
                                        <div className="title">
                                            <p className="basic-semibold">(Tổng phí bảo hiểm/năm từ 30 triệu đến dưới
                                                100 triệu)</p>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-thamgiahd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Hợp đồng cũ đáo hạn và tham gia hợp đồng mới, có phí bảo hiểm
                                                        quy năm:</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Dưới 30 triệu đồng</p>
                                                        <p className="right-aligned">500 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Từ 30 triệu đến 50 triệu đồng</p>
                                                        <p>800 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Trên 50 triệu đồng</p>
                                                        <p className="right-aligned">1.500 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-knhd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Duy trì hợp đồng</h3>
                                                    <p>(Ngày kỷ niệm hợp đồng, khi đã nộp đầy đủ phí bảo hiểm)</p>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Vào ngày kỷ niệm 5 năm</p>
                                                        <p className="right-aligned">100 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p style={{lineHeight: '22px'}}>Vào ngày kỷ niệm 10
                                                            năm, <br/> và mỗi 10 năm tiếp theo</p>
                                                        <p className="right-aligned">80 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/*<div className="main-component__row">*/}
                                        {/*    <div className="left">*/}
                                        {/*        <div className="icon">*/}
                                        {/*            <img src="../img/icon/9.1/9.1-menulist-icon-nopphibh.svg" alt=""/>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*    <div className="right">*/}
                                        {/*        <div className="right__subtitle">*/}
                                        {/*            <h3>Thanh toán phí bảo hiểm định kỳ</h3>*/}
                                        {/*            <p className="color-grey marginboth">*/}
                                        {/*                - Dịch vụ này áp dụng đối với Hợp đồng bảo hiểm đang*/}
                                        {/*                được phục vụ bởi Tư vấn tài chính Dai-ichi Life Việt Nam thuộc*/}
                                        {/*                kênh Đại lý truyền thống;*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Phí bảo hiểm định kỳ được thanh toán qua Dai-ichi Connect,*/}
                                        {/*                ngân hàng (chuyển khoản hoặc tiền mặt), ví điện tử (MoMo, Payoo,*/}
                                        {/*                Ví Việt), máy POS, Bưu điện (VNPost, Viettel), hệ thống cửa hàng*/}
                                        {/*                liên kết Payoo.*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Điểm thưởng tương đương với số tiền phí đóng như sau:*/}
                                        {/*            </p>*/}
                                        {/*        </div>*/}
                                        {/*        <div className="right__content">*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 2 đến năm thứ 4</p>*/}
                                        {/*                <p className="right-aligned">1,0% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 5 trở đi</p>*/}
                                        {/*                <p className="right-aligned">0,5% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="bottom-text" style={{textAlign: 'left'}}><p><span*/}
                                        {/*                className="red-text basic-bold">Lưu ý: </span></p></div>*/}
                                        {/*            <div className='content b-gray'>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Điểm thưởng được tính vào thời điểm phí bảo hiểm được ghi nhận vào năm hợp đồng,</p>*/}
                                        {/*                </div>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Tổng điểm thưởng tích lũy tối đa là 1.000 điểm/01 năm hợp đồng.</p>*/}
                                        {/*                </div>*/}
                                        {/*            </div>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*</div>*/}
                                    </div>
                                    <div
                                        className={activeTabContentId === "Gold" ? "main-component gold show-component" : "main-component gold"}
                                        id="Gold">
                                        <div className="title">
                                            <p className="basic-semibold">(Tổng phí bảo hiểm/năm từ 100 triệu đến dưới
                                                200 triệu)</p>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-sinhnhat.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Sự kiện Bên Mua Bảo Hiểm</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Sinh nhật Bên Mua Bảo Hiểm</p>
                                                        <p className="right-aligned">500 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-thamgiahd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Hợp đồng cũ đáo hạn và tham gia hợp đồng mới, có phí bảo hiểm
                                                        quy năm:</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Dưới 30 triệu đồng</p>
                                                        <p className="right-aligned">500 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Từ 30 triệu đến 50 triệu đồng</p>
                                                        <p className="right-aligned">800 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Trên 50 triệu đồng</p>
                                                        <p className="right-aligned">1.500 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-knhd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Duy trì hợp đồng</h3>
                                                    <p>(Ngày kỷ niệm hợp đồng, khi đã nộp đầy đủ phí bảo hiểm)</p>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Vào ngày kỷ niệm 5 năm</p>
                                                        <p className="right-aligned">150 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p style={{lineHeight: '22px'}}>Vào ngày kỷ niệm 10
                                                            năm, <br/> và mỗi 10 năm tiếp theo</p>
                                                        <p className="right-aligned">100 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/*<div className="main-component__row">*/}
                                        {/*    <div className="left">*/}
                                        {/*        <div className="icon">*/}
                                        {/*            <img src="../img/icon/9.1/9.1-menulist-icon-nopphibh.svg" alt=""/>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*    <div className="right">*/}
                                        {/*        <div className="right__subtitle">*/}
                                        {/*            <h3>Thanh toán phí bảo hiểm định kỳ</h3>*/}
                                        {/*            <p className="color-grey marginboth">*/}
                                        {/*                - Dịch vụ này áp dụng đối với Hợp đồng bảo hiểm đang*/}
                                        {/*                được phục vụ bởi Tư vấn tài chính Dai-ichi Life Việt Nam thuộc*/}
                                        {/*                kênh Đại lý truyền thống;*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Phí bảo hiểm định kỳ được thanh toán qua Dai-ichi Connect,*/}
                                        {/*                ngân hàng (chuyển khoản hoặc tiền mặt), ví điện tử (MoMo, Payoo,*/}
                                        {/*                Ví Việt), máy POS, Bưu điện (VNPost, Viettel), hệ thống cửa hàng*/}
                                        {/*                liên kết Payoo.*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Điểm thưởng tương đương với số tiền phí đóng như sau:*/}
                                        {/*            </p>*/}
                                        {/*        </div>*/}
                                        {/*        <div className="right__content">*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 2 đến năm thứ 4</p>*/}
                                        {/*                <p className="right-aligned">1,0% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 5 trở đi</p>*/}
                                        {/*                <p className="right-aligned">0,5% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="bottom-text" style={{textAlign: 'left'}}><p><span*/}
                                        {/*                className="red-text basic-bold">Lưu ý: </span></p></div>*/}
                                        {/*            <div className='content b-gray'>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Điểm thưởng được tính vào thời điểm phí bảo hiểm được ghi nhận vào năm hợp đồng,</p>*/}
                                        {/*                </div>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Tổng điểm thưởng tích lũy tối đa là 1.000 điểm/01 năm hợp đồng.</p>*/}
                                        {/*                </div>*/}
                                        {/*            </div>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*</div>*/}
                                    </div>
                                    <div
                                        className={activeTabContentId === "Diamond" ? "main-component diamond show-component" : "main-component diamond"}
                                        id="Diamond">
                                        <div className="title">
                                            <p className="basic-semibold">
                                                (Tổng phí bảo hiểm/năm từ 200 triệu trở lên)
                                            </p>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-sinhnhat.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Sự kiện Bên Mua Bảo Hiểm</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Sinh nhật Bên Mua Bảo Hiểm</p>
                                                        <p className="right-aligned">800 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-thamgiahd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Hợp đồng cũ đáo hạn và tham gia hợp đồng mới, có phí bảo hiểm
                                                        quy năm:</h3>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Dưới 30 triệu đồng</p>
                                                        <p className="right-aligned">500 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Từ 30 triệu đến 50 triệu đồng</p>
                                                        <p className="right-aligned">800 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p>Trên 50 triệu đồng</p>
                                                        <p className="right-aligned">1.500 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="main-component__row">
                                            <div className="left">
                                                <div className="icon">
                                                    <img src="../img/icon/9.1/9.1-menulist-icon-knhd.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="right">
                                                <div className="right__subtitle">
                                                    <h3>Duy trì hợp đồng</h3>
                                                    <p>(Ngày kỷ niệm hợp đồng, khi đã nộp đầy đủ phí bảo hiểm)</p>
                                                </div>
                                                <div className="right__content">
                                                    <div className="right__content__item">
                                                        <p>Vào ngày kỷ niệm 5 năm</p>
                                                        <p className="right-aligned">200 điểm</p>
                                                    </div>
                                                    <div className="right__content__item">
                                                        <p style={{lineHeight: '22px'}}>Vào ngày kỷ niệm 10
                                                            năm, <br/> và mỗi 10 năm tiếp theo</p>
                                                        <p className="right-aligned">150 điểm</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/*<div className="main-component__row">*/}
                                        {/*    <div className="left">*/}
                                        {/*        <div className="icon">*/}
                                        {/*            <img src="../img/icon/9.1/9.1-menulist-icon-nopphibh.svg" alt=""/>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*    <div className="right">*/}
                                        {/*        <div className="right__subtitle">*/}
                                        {/*            <h3>Thanh toán phí bảo hiểm định kỳ</h3>*/}
                                        {/*            <p className="color-grey marginboth">*/}
                                        {/*                - Dịch vụ này áp dụng đối với Hợp đồng bảo hiểm đang*/}
                                        {/*                được phục vụ bởi Tư vấn tài chính Dai-ichi Life Việt Nam thuộc*/}
                                        {/*                kênh Đại lý truyền thống;*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Phí bảo hiểm định kỳ được thanh toán qua Dai-ichi Connect,*/}
                                        {/*                ngân hàng (chuyển khoản hoặc tiền mặt), ví điện tử (MoMo, Payoo,*/}
                                        {/*                Ví Việt), máy POS, Bưu điện (VNPost, Viettel), hệ thống cửa hàng*/}
                                        {/*                liên kết Payoo.*/}
                                        {/*            </p>*/}
                                        {/*            <p className="color-grey marginboth" style={{ marginTop: '2px' }}>*/}
                                        {/*                - Điểm thưởng tương đương với số tiền phí đóng như sau:*/}
                                        {/*            </p>*/}
                                        {/*        </div>*/}
                                        {/*        <div className="right__content">*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 2 đến năm thứ 4</p>*/}
                                        {/*                <p className="right-aligned">1,0% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="right__content__item">*/}
                                        {/*                <p>Từ năm hợp đồng thứ 5 trở đi</p>*/}
                                        {/*                <p className="right-aligned">0,5% trên số tiền phí đóng</p>*/}
                                        {/*            </div>*/}
                                        {/*            <div className="bottom-text" style={{textAlign: 'left'}}><p><span*/}
                                        {/*                className="red-text basic-bold">Lưu ý: </span></p></div>*/}
                                        {/*            <div className='content b-gray'>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Điểm thưởng được tính vào thời điểm phí bảo hiểm được ghi nhận vào năm hợp đồng,</p>*/}
                                        {/*                </div>*/}
                                        {/*                <div className='list__item'>*/}
                                        {/*                    <div className="dot-grey"></div>*/}
                                        {/*                    <p className="marginboth">Tổng điểm thưởng tích lũy tối đa là 1.000 điểm/01 năm hợp đồng.</p>*/}
                                        {/*                </div>*/}
                                        {/*            </div>*/}
                                        {/*        </div>*/}
                                        {/*    </div>*/}
                                        {/*</div>*/}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="scpromotion-point">
                    <div className="container">
                        <div className="promotion-warpper">
                            <Link to={"/point-exchange"} onClick={() => selectedSubMenu('p1', 'Đổi điểm thưởng')}>
                                {getSession(CLIENT_ID) ? (
                                    <button className="promotionvideo2">
                                        <p>Đổi điểm</p>
                                        <div className="icon-warpper">
                                            <i><img src="img/icon/playbtn.svg" alt=""/></i>
                                        </div>
                                    </button>
                                ) : (
                                    <button className="promotionvideo">
                                        <p>Xem kho quà tặng</p>
                                        <div className="icon-warpper">
                                            <i><img src="img/icon/playbtn.svg" alt=""/></i>
                                        </div>
                                    </button>
                                )}
                            </Link>
                        </div>
                    </div>
                </section>
            </main>


        )
    }
}

export default NormalProgramPoint;
