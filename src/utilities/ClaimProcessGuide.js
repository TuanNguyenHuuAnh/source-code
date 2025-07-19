// import 'antd/dist/antd.css';
// import '../claim.css';
import './utilities.css';
import React, {Component} from "react";
import {Link} from 'react-router-dom';
import {CPSaveLog} from '../util/APIUtils';
import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import {FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen} from '../constants';
import {Helmet} from "react-helmet";

export const TABS = Object.freeze({
    THU_TUC: 0,
    BAO_LANH_VIEN_PHI: 1,
});

export const PROCESS_TAB_OPTS = Object.freeze({
    GENERAL: 0,
    STEPS: 1,
    BENEFIT_PROCESS: 2,
});

export const PROCESS_PER_BENEFIT_OPTS = Object.freeze({
    BH_CSSK: 0,
    BH_HO_TRO_PHI: 1,
    // BH_HAU_SU: 2,
    BH_TN: 2,
    BH_BENH_HIEM_NGHEO: 3,
    QL_TU_VONG: 4,
    QL_HO_TRO_VIEN_PHI_TN: 5,
    QL_THUONG_TAT_TN: 6,
    QL_BENH_HIEM_NGHEO: 7,
    QL_THUONG_TAT_VV_BENH_TN: 8,
    CT_QL_BH_VA_NGUOI_NHAN_TIEN: 9,
    LUUY_KHI_BH_VA_NGUOI_NHAN_TIEN: 10,
    YC_GQ_QLBH: 11,
    TD_HS_BS_TT: 12,
    BH_TTC: 13
});

export const BILLING_TAB_OPTS = Object.freeze({
    WHITELIST: 0,
    FACILITIES: 1,
    BILLING_STEPS: 2,
});

class ClaimProcessGuide extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentTab: TABS.THU_TUC,
            currentOption: '',
            currentBenInProcessTab: '',
            isOpenDeadlyDiseaseDetail: false,
            renderMeta: false
        };

        this.handlerChangeOption = this.onChangeOption.bind(this);
        this.handlerChangeProcessPerBen = this.onChangeProcessPerBen.bind(this);
        this.handlerShowDeadlyDiseaseDetail = this.onClickShowDeadlyDiseaseDetail.bind(this);
        this.handlerCreateNewRequest = this.onClickCreateNewRequest.bind(this);
    }

    componentDidMount() {
        this.cpSaveLog(`Web_Open_${PageScreen.GUIDE_CLAIM}`);
        trackingEvent(
            "Tiện ích",
            `Web_Open_${PageScreen.GUIDE_CLAIM}`,
            `Web_Open_${PageScreen.GUIDE_CLAIM}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.GUIDE_CLAIM}`);
        trackingEvent(
            "Tiện ích",
            `Web_Close_${PageScreen.GUIDE_CLAIM}`,
            `Web_Close_${PageScreen.GUIDE_CLAIM}`,
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

    onChangeOption(tabId, optionId) {
        var jsonState = this.state;
        jsonState.currentTab = tabId;
        jsonState.currentOption = (jsonState.currentOption === optionId) ? '' : optionId;
        jsonState.currentBenInProcessTab = '';
        jsonState.isOpenDeadlyDiseaseDetail = false;
        this.setState(jsonState);
    }

    onChangeProcessPerBen(benId) {
        var jsonState = this.state;
        if (jsonState.currentTab === TABS.THU_TUC) {
            jsonState.currentBenInProcessTab = (jsonState.currentBenInProcessTab === benId) ? '' : benId;
            jsonState.isOpenDeadlyDiseaseDetail = false;
        }
        this.setState(jsonState);
    }

    onClickShowDeadlyDiseaseDetail() {
        var jsonState = this.state;
        if (jsonState.currentTab === TABS.THU_TUC
            && jsonState.currentOption === PROCESS_TAB_OPTS.BENEFIT_PROCESS
            && jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_BENH_HIEM_NGHEO) {
            jsonState.isOpenDeadlyDiseaseDetail = !jsonState.isOpenDeadlyDiseaseDetail;
        }
        this.setState(jsonState);
    }

    onClickCreateNewRequest() {
        if (isLoggedIn()) {
            if (getSession(CLIENT_ID) === null
                || getSession(CLIENT_ID) === undefined
                || getSession(CLIENT_ID) === '') {
                document.getElementById("error-popup-only-for-existed").className = "popup error-popup special show";
            } else {
                this.props.history.push("/myclaim");
            }
        } else {
            this.props.history.push("/");
        }
    }

    render() {
        var jsonState = this.state;
        return (
            <main className={isLoggedIn() ? "logined" : ""}>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Giải quyết quyền lợi bảo hiểm – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Trang thông tin yêu cầu giải quyết quyền lợi bảo hiểm, quy trình và thủ tục bảo lãnh viện phí của Dai-ichi Life Việt Nam."/>
                        <meta name="keywords"
                              content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Giải quyết quyền lợi bảo hiểm"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/utilities/claim-guide"}/>
                        <meta property="og:title" content="Giải quyết quyền lợi bảo hiểm - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Trang thông tin yêu cầu giải quyết quyền lợi bảo hiểm, quy trình và thủ tục bảo lãnh viện phí của Dai-ichi Life Việt Nam."/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/utilities/claim-guide"}/>
                    </Helmet>

                }
                <div className="main-warpper">
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
                                    <p>Giải quyết quyền lợi bảo hiểm</p>
                                    <span>&gt;</span>
                                </div>
                            </div>
                        </div>
                    </section>
                    {/* Banner */}
                    <section className="scdieukhoan scquyenloibaohiem">
                        <div className="container">
                            <h1>Giải quyết quyền lợi bảo hiểm</h1>
                        </div>
                    </section>
                    {/* Main page */}
                    <div className="policy-container bieumau-container">
                        {/* Tabs */}
                        <section className="policy-menu">
                            <button
                                className={jsonState.currentTab === TABS.THU_TUC ? "policy-menu__item active" : "policy-menu__item"}
                                onClick={() => this.handlerChangeOption(TABS.THU_TUC, '')}>
                                <h2>THỦ TỤC, TIẾN TRÌNH</h2>
                            </button>

                            <button
                                className={jsonState.currentTab === TABS.BAO_LANH_VIEN_PHI ? "policy-menu__item active" : "policy-menu__item"}
                                onClick={() => this.handlerChangeOption(TABS.BAO_LANH_VIEN_PHI, '')}>
                                <h2>BẢO LÃNH VIỆN PHÍ</h2>
                            </button>

                        </section>
                        <section className="policy-content bieumau-content quyenloi-content">
                            {/* Thủ tục */}
                            <div className={jsonState.currentTab === TABS.THU_TUC ?
                                "policy-content-wrapper policy-letter bieumau-page show" :
                                "policy-content-wrapper policy-letter bieumau-page"}>
                                <div className="bieumau-page__item">
                                    <div className="content-wrapper">
                                        {/* Giới thiệu */}
                                        <div className={jsonState.currentOption === PROCESS_TAB_OPTS.GENERAL ?
                                            "content-item dropdown general-requirements show" :
                                            "content-item dropdown general-requirements"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.THU_TUC, PROCESS_TAB_OPTS.GENERAL)}>
                                                <p>Giới thiệu</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                <div className="item-wrapper">
                                                    <div className='head-title' style={{paddingTop: '13px'}}><p
                                                        style={{display: 'inline', 'padding-left': '0px'}}>Để việc xử
                                                        lý hồ sơ yêu cầu giải quyết Quyền lợi bảo hiểm (QLBH)
                                                        được nhanh chóng, Quý Khách vui lòng lưu ý một số nội dung sau
                                                        đây khi nộp yêu cầu giải quyết Quyền lợi bảo hiểm:</p>
                                                    </div>
                                                    <div className='head-title'><p></p></div>
                                                    <p style={{'padding-left': '12px', display: 'inline'}}>Xem quy định
                                                        về Quyền lợi bảo hiểm và giải quyết Quyền lợi bảo
                                                        hiểm tại Quy tắc và Điều khoản Hợp đồng bảo hiểm (trong
                                                        bộ Hợp đồng bảo hiểm bảng giấy đang lưu giữ hoặc xem
                                                        trực tuyến bộ Hợp đồng trên Dai-ichi Connect này tại mục:
                                                        Hợp đồng\Thư viện tài liệu).</p>
                                                    <div className='head-title'><p></p></div>
                                                    <p style={{'padding-left': '12px', display: 'inline'}}>Kiểm tra chi
                                                        tiết thông tin Hợp đồng bảo hiểm trên Dai-ichi Connect và
                                                        đọc Hướng dẫn thủ tục yêu cầu giải quyết quyền lợi bảo
                                                        hiểm tại mục tiện ích này.</p>
                                                    <div className='head-title'><p></p></div>
                                                    <div className='head-title'><p
                                                        style={{display: 'inline', 'padding-left': '0px'}}>Những nội
                                                        dung tại mục này nhằm cung cấp các hướng dẫn tiêu chuẩn
                                                        để Quý Khách tham khảo khi lập yêu cầu giải quyết Quyết
                                                        lợi bảo hiểm. Một số trường hợp cụ thể có thể có
                                                        thêm một số thủ tục đặc thù khác và/ hoặc giấy tờ
                                                        bản gốc, khi đó, Dai-ichi Life Việt Nam (DLVN) sẽ có hướng
                                                        dẫn cụ thể đến từng Khách hàng.</p></div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Thủ tục Yêu cầu giải quyết Quyền lợi bảo hiểm */}
                                        <div className={jsonState.currentOption === PROCESS_TAB_OPTS.BENEFIT_PROCESS ?
                                            "content-item dropdown rights-process show" :
                                            "content-item dropdown rights-process"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.THU_TUC, PROCESS_TAB_OPTS.BENEFIT_PROCESS)}>
                                                <p>Thủ tục Yêu cầu giải quyết Quyền lợi bảo hiểm</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                {/* Quyền lợi Bảo hiểm Chăm sóc sức khoẻ */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_TTC ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_TTC)}>
                                                        <p>1. Thủ tục chung</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.BH_TTC &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items show">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot' style={{padding: 0}}>Yêu cầu giải
                                                                quyết QLBH bao gồm các chứng từ cần thiết sau
                                                                đây:</p>
                                                            <p className='no-dot' style={{display: "inline"}}>- Yêu cầu
                                                                trực tuyến trên Dai-ichi Connect hoặc Yêu cầu giải
                                                                quyết quyền lợi bảo hiểm theo mẫu&nbsp;<a
                                                                    style={{display: 'inline'}}
                                                                    href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                    className="simple-brown2"
                                                                    target='_blank'>(CL01)</a></p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot' style={{display: "inline"}}>- Văn bản
                                                                đồng ý cho DLVN thu thập thông tin tại Cơ sở y tế nơi
                                                                Người được bảo hiểm khám/điều trị (theo mẫu&nbsp;<a
                                                                    style={{display: 'inline'}}
                                                                    href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                    className="simple-brown2" target='_blank'>CL03</a>).
                                                                Một số Cơ sở y tế yêu cầu có văn bản của Khách
                                                                hàng đồng ý cho DLVN thu thập thông tin, chứng từ
                                                                khám/điều trị của bệnh nhân để phục vụ cho việc xem
                                                                xét yêu cầu giải quyết QLBH. Để việc xử lý hồ sơ
                                                                được nhanh chóng, Khách hàng cần hoàn tất văn
                                                                bản này khi DLVN có thông báo hoặc chủ động hoàn
                                                                tất khi nộp Yêu cầu giải quyết QLBH.</p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot'>- Chứng từ liên quan đến sự kiện
                                                                bảo hiểm (theo chi tiết tại từng Quyền lợi bảo hiểm
                                                                yêu cầu)</p>
                                                            <p className='no-dot' style={{marginBottom: '-3px'}}>- Chứng
                                                                từ về quyền hưởng Quyền lợi bảo hiểm và người nhận
                                                                tiền.</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_TTC &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/* Quyền lợi Bảo hiểm Hỗ trợ viện phí */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_CSSK ?
                                                        "rights-process__item rights-process__sub-item dropdown show" : "rights-process__item rights-process__sub-item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_CSSK)}>
                                                        <p>Quyền lợi bảo hiểm Chăm sóc sức khỏe <br/>(điều trị Nội trú/
                                                            Ngoại trú/ Chăm sóc răng)</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.BH_CSSK &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='basic-semibold'
                                                               style={{paddingLeft: 12}}>Chứng từ y tế: </p>
                                                            <p className='no-dot'>- Giấy ra viện (nếu điều trị nội
                                                                trú) </p>
                                                            <p className='no-dot'>- Hồ sơ điều trị trong ngày (nếu điều
                                                                trị trong ngày)</p>
                                                            <p className='no-dot'>- Sổ khám bệnh, toa thuốc (nếu điều
                                                                trị ngoại trú)</p>
                                                            <p className='no-dot'>- Phiếu điều trị nha khoa (nếu có điều
                                                                trị nha khoa)</p>
                                                            <p className='no-dot'>- Giấy chứng nhận phẫu thuật (nếu có
                                                                phẫu thuật)</p>
                                                            <p className='no-dot'>- Giấy chuyển viện (nếu có chuyển
                                                                viện)</p>
                                                            <p className='no-dot'>- Phiếu cung cấp thông tin sự kiện tai nạn (theo mẫu&nbsp;
                                                                <a style={{display: 'contents'}}
                                                                   href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                   className="simple-brown2"
                                                                   target='_blank'>CL04</a>, nếu sự kiện liên
                                                                quan đến tai nạn)</p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Trích
                                                                sao bệnh án, Báo cáo y tế, Kết quả xét nghiệm, Chẩn đoán
                                                                hình ảnh (nộp cùng yêu cầu, nếu có, hoặc theo
                                                                thông báo của DLVN tùy trường hợp)</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='basic-semibold'
                                                               style={{paddingLeft: 12}}>Chứng từ chi phí </p>
                                                            <p className='no-dot'>- Hóa đơn Giá trị gia tăng</p>
                                                            <p className='no-dot'>- Bảng kê chi tiết chi phí khám chữa
                                                                bệnh</p>
                                                            <div className="text-wrapper"
                                                                 style={{'margin-bottom': '-4px'}}>
                                                                <p className='no-dot'
                                                                   style={{'display': 'inline', paddingLeft: '0px'}}>
                                                                    <span className="basic-text2">Nếu nộp Hóa đơn điện tử đã có thông tin tra cứu, tùy trường hợp, Khách hàng có thể được yêu cầu cung cấp bản gốc của Giấy ra viện/ Toa thuốc/ Hồ sơ điều trị</span>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_CSSK &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/* Quyền lợi Bảo hiểm tai nạn */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_HO_TRO_PHI ?
                                                        "rights-process__item rights-process__sub-item dropdown show" : "rights-process__item rights-process__sub-item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_HO_TRO_PHI)}>
                                                        <p>Quyền lợi bảo hiểm Hỗ trợ viện phí</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.BH_HO_TRO_PHI &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot'>- Giấy ra viện (nếu điều trị nội
                                                                trú)</p>
                                                            <p className='no-dot'>- Hồ sơ điều trị trong ngày (nếu điều
                                                                trị trong ngày)</p>
                                                            <p className='no-dot'>- Sổ khám bệnh, toa thuốc (nếu
                                                                có)</p>
                                                            <p className='no-dot'>- Giấy chứng nhận phẫu thuật (nếu có
                                                                phẫu thuật)</p>
                                                            <p className='no-dot'>- Giấy chuyển viện (nếu có chuyển
                                                                viện)</p>
                                                            <p className='no-dot'>- Phiếu cung cấp thông tin sự kiện tai nạn (theo mẫu&nbsp;
                                                                <a style={{display: 'contents'}}
                                                                   href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                   className="simple-brown2"
                                                                   target='_blank'>CL04</a>, nếu sự kiện liên
                                                                quan đến tai nạn)</p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Trích
                                                                sao bệnh án, Báo cáo y tế, Kết quả xét nghiệm, Chẩn đoán
                                                                hình ảnh (nộp cùng yêu cầu, nếu có, hoặc theo
                                                                thông báo của DLVN tùy trường hợp)</p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Bảng kê
                                                                chi tiết chi phí khám chữa bệnh (nếu có điều trị phẫu
                                                                thuật hoặc chăm sóc đặc biệt)</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_HO_TRO_PHI &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/* Quyền lợi bảo hiểm Tai nạn */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_TN ?
                                                        "rights-process__item rights-process__sub-item dropdown show" : "rights-process__item rights-process__sub-item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_TN)}>
                                                        <p>Quyền lợi bảo hiểm Tai nạn</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.BH_TN &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='basic-semibold'
                                                               style={{paddingLeft: 12}}>Chứng từ y tế </p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot'>- Giấy ra viện (nếu điều trị nội
                                                                trú)</p>
                                                            <p className='no-dot'>- Toa thuốc (nếu điều trị ngoại trú/
                                                                cấp cứu)</p>
                                                            <p className='no-dot'>- Tóm tắt bệnh án</p>
                                                            <p className='no-dot'>- Giấy chứng nhận thương tích</p>
                                                            <p className='no-dot'>- Hình ảnh và kết quả chụp XQuang, CT
                                                                scan, MRI, Siêu âm,…</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-4px'}}>
                                                            <p className='basic-semibold'
                                                               style={{paddingLeft: 12}}>Phiếu cung cấp thông tin sự kiện tai nạn (theo
                                                                mẫu&nbsp;<a style={{display: 'contents'}}
                                                                             href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                             className="simple-brown2 basic-bold"
                                                                             target='_blank'>CL04</a>)</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_TN &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/* Quyền lợi Bệnh hiểm nghèo/ Ung thư */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_BENH_HIEM_NGHEO ?
                                                        "rights-process__item rights-process__sub-item dropdown show" : "rights-process__item rights-process__sub-item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_BENH_HIEM_NGHEO)}>
                                                        <p>Quyền lợi Bệnh hiểm nghèo/ Ung thư</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.BH_BENH_HIEM_NGHEO &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot'>- Giấy ra viện và Tóm tắt bệnh án (bản
                                                                gốc hoặc bản sao có chứng thực)</p>
                                                            <p className='no-dot'>- Giấy chứng nhận phẫu thuật (nếu có
                                                                phẫu thuật)</p>
                                                            <p className='no-dot'>- Phiếu và kết quả chụp Xquang, CT
                                                                scan, MRI, Siêu âm…</p>
                                                            <p className='no-dot'>- Kết quả Giải phẫu mô bệnh học/ Kết
                                                                quả sinh thiết (bản sao có chứng thực)</p>
                                                            <p className='no-dot' style={{marginBottom: '-2px'}}>- Các
                                                                thông tin/ chứng từ thể hiện tình trạng bệnh thỏa điều
                                                                kiện của từng Chẩn đoán bệnh cụ thể được quy định trong
                                                                Quy tắc và Điều khoản sản phẩm bảo hiểm.</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_BENH_HIEM_NGHEO &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/* Quyền lợi Thương tật toàn bộ và vĩnh viễn (do Bệnh hoặc Tai nạn) */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.QL_THUONG_TAT_VV_BENH_TN ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.QL_THUONG_TAT_VV_BENH_TN)}>
                                                        <p>Quyền lợi Thương tật toàn bộ và vĩnh viễn (do Bệnh hoặc Tai
                                                            nạn)</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.QL_THUONG_TAT_VV_BENH_TN &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Chứng từ y tế (nếu có điều
                                                                trị/ nằm viện)</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot'>- Giấy tờ liên quan đến việc khám và
                                                                điều trị bệnh/ tai nạn</p>
                                                            <p className='no-dot'>- Phiếu và kết quả chụp Xquang, CT
                                                                scan, MRI, Siêu âm,…</p>
                                                            <p className='no-dot'>- Giấy ra viện và Tóm tắt bệnh án </p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Phiếu cung cấp thông tin sự kiện tai nạn (theo
                                                                mẫu&nbsp;<a style={{display: 'contents'}}
                                                                             href={`${FE_BASE_URL}/utilities/document/ResolveInsuranceContract`}
                                                                             className="simple-brown2"
                                                                             target='_blank'>CL04</a>, nếu sự
                                                                kiện liên quan đến tai nạn)</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-2px'}}>
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Kết luận
                                                                giám định tỷ lệ tổn thương cơ thể của Hội đồng Giám định
                                                                y khoa cấp tỉnh/ Thành phố trực thuộc Trung ương (bản
                                                                gốc, khi có yêu cầu)</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.QL_THUONG_TAT_VV_BENH_TN &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>

                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.QL_TU_VONG ?
                                                        "rights-process__item rights-process__sub-item dropdown show" : "rights-process__item rights-process__sub-item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.QL_TU_VONG)}>
                                                        <p>Quyền lợi Tử vong</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.QL_TU_VONG &&
                                                        <div className="rights-process__subItem"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Trích lục
                                                                khai tử có thể hiện số CMND/ CCCD của người tử vong (bản
                                                                sao có chứng thực)</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Chứng từ y
                                                                tế (nếu có điều trị/ nằm viện):</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p className='no-dot'>- Giấy ra viện, Toa thuốc và Tóm tắt
                                                                bệnh án</p>
                                                            <p className='no-dot'>- Phiếu và kết quả chụp Xquang, CT
                                                                scan, MRI, Siêu âm…</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Kết quả Giải
                                                                phẫu mô bệnh học / Kết quả sinh thiết (bản sao có chứng
                                                                thực, nếu Quyền lợi Tử vong do bệnh ung thư)</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-2px'}}>
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Hồ sơ tai
                                                                nạn do Cơ quan Cảnh sát điều tra lập: Biên bản khám
                                                                nghiệm hiện trường, Biên bản khám nghiệm tử thi, Sơ đồ
                                                                hiện trường vụ tai nạn, Kết luận điều tra vụ tai nạn/
                                                                Biên bản giải quyết vụ tai nạn (bản sao có chứng thực,
                                                                nếu Quyền lợi Tử vong do tai nạn)</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.QL_TU_VONG &&
                                                        <div className="rights-process__subItem"/>}
                                                </div>
                                                {/*Chứng từ về quyền hưởng Quyền lợi bảo hiểm và Người nhận tiền */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.CT_QL_BH_VA_NGUOI_NHAN_TIEN ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.CT_QL_BH_VA_NGUOI_NHAN_TIEN)}>
                                                        <p>2. Chứng từ về quyền hưởng Quyền lợi bảo hiểm và Người
                                                            nhận tiền</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.CT_QL_BH_VA_NGUOI_NHAN_TIEN &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Quy tắc và
                                                                Điều khoản Hợp đồng có quy định cụ thể người
                                                                được hưởng QLBH và thứ tự ưu tiên xem xét đối
                                                                với từng QLBH cụ thể. DLVN sẽ thực hiện thanh
                                                                toán theo quy định này hoặc theo yêu cầu của Bên
                                                                mua bảo hiểm (BMBH) phù hợp với quy định.</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Đối với QLBH tử vong của
                                                                NĐBH, nếu Hợp đồng không có chỉ định Người thụ
                                                                hưởng và BMBH đã tử vong, QLBH sẽ được chi trả cho
                                                                người thừa kế của BMBH theo quy định, vì vậy,
                                                                Khách hàng cần hoàn tất thủ tục thừa kế theo
                                                                quy định pháp luật.</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Người nhận tiền và thông tin
                                                                thanh toán:</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-6px'}}>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Nếu
                                                                người nhận tiền là Bên mua bảo hiểm/Người được bảo
                                                                hiểm/Người thụ hưởng: Cần cung cấp CMND/ CCCD (bản sao)
                                                                nếu đã được cấp đổi với thông tin khác với thông tin
                                                                trên CMND/CCCD đã cung cấp cho DLVN trước đó.</p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Nếu
                                                                người nhận tiền không phải là người có quyền hưởng quyền
                                                                lợi bảo hiểm: CMND/ CCCD (bản sao) của người nhận tiền
                                                                và Giấy ủy quyền theo quy định pháp luật (bản gốc).</p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot'>- Khách hàng cần ghi rõ thông tin
                                                                thanh toán trong Yêu cầu giải quyết QLBH hoặc các bổ
                                                                sung, nếu có, nhằm đảm bảo tiền được thanh toán
                                                                một cách nhanh chóng ngay khi có quyết định chi
                                                                trả QLBH.</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.CT_QL_BH_VA_NGUOI_NHAN_TIEN &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                </div>

                                                {/*Những lưu ý khi nộp hồ sơ yêu cầu giải quyết Quyền lợi bảo hiểm*/}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.LUUY_KHI_BH_VA_NGUOI_NHAN_TIEN ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.LUUY_KHI_BH_VA_NGUOI_NHAN_TIEN)}>
                                                        <p>3. Những lưu ý khi nộp hồ sơ yêu cầu giải quyết Quyền lợi bảo
                                                            hiểm</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Ngoài các chứng từ như đã nêu,
                                                                DLVN có thể yêu cầu bổ sung thêm thông tin, chứng từ
                                                                khác để đảm bảo yêu cầu của Khách hàng được xem xét một
                                                                cách đầy đủ, chính xác và phù hợp với các quy định của
                                                                Quy tắc và Điều khoản Hợp đồng, các quy định
                                                                của pháp luật.</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{paddingLeft: 12}}>Với các giấy tờ bản sao, trong
                                                                một số trường hợp, DLVN có thể sẽ yêu cầu Khách hàng
                                                                cung cấp bản gốc để đối chiếu.</p>
                                                        </div>
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Quy tắc và
                                                                Điều khoản Hợp đồng có quy định thời hạn nộp hồ sơ yêu
                                                                cầu giải quyết QLBH kể từ ngày xảy ra Sự kiện bảo
                                                                hiểm (tai nạn/ bệnh tật/ tử vong), tuy nhiên, Khách
                                                                hàng nên gửi yêu cầu đến DLVN càng sớm càng tốt
                                                                để yêu cầu được xem xét nhanh chóng, đảm bảo
                                                                quyền lợi của Khách hàng.</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Quy trình đối với từng quyền lợi */}
                                        <div className={jsonState.currentOption === PROCESS_TAB_OPTS.STEPS ?
                                            "content-item dropdown rights-process show" :
                                            "content-item dropdown rights-process"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.THU_TUC, PROCESS_TAB_OPTS.STEPS)}>
                                                <p>Tiến trình xử lý yêu cầu giải quyết Quyền lợi bảo hiểm</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                {/* Lập yêu cầu giải quyết Quyền lợi bảo hiểm */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.YC_GQ_QLBH ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.YC_GQ_QLBH)}>
                                                        <p>1. Lập yêu cầu giải quyết Quyền lợi bảo hiểm</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.YC_GQ_QLBH &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                    <div className="dropdown__items show">
                                                        <div className="rights-process__item__content-wrapper">
                                                            <p style={{display: 'inline', paddingLeft: 12}}>Khách hàng
                                                                cần lập yêu cầu giải quyết Quyền lợi bảo hiểm và
                                                                đính kèm các giấy tờ nêu tại mục Thủ tục Yêu
                                                                cầu giải quyết Quyền lợi bảo hiểm tương ứng với
                                                                loại Quyền lợi bảo hiểm yêu cầu để gửi cho DLVN, bằng
                                                                cách:</p>
                                                            <p className='no-dot'></p>
                                                            <p className='no-dot'>- Lập yêu cầu giải quyết Quyền lợi
                                                                bảo hiểm trực tuyến và đính kèm hình ảnh chứng
                                                                từ trên Dai-ichi Connect, hoặc; </p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Lập
                                                                Phiếu yêu cầu giải quyết Quyền lợi bảo hiểm theo
                                                                mẫu&nbsp;<a style={{display: 'inline'}}
                                                                             href='https://kh.dai-ichi-life.com.vn/utilities/document/ResolveInsuranceContract'
                                                                             className="simple-brown2"
                                                                             target='_blank'>(CL01)</a> và nộp
                                                                kèm chứng từ tại các Văn phòng Tổng Đại lý DLVN
                                                                trên toàn quốc, hoặc gửi bưu điện bằng hình thức đảm
                                                                bảo về Phòng Giải quyết Quyền lợi Bảo hiểm, địa chỉ: Tòa
                                                                nhà Dai-ichi Life Việt Nam, 149 - 151 Nguyễn Văn Trỗi,
                                                                Phường 11, Quận Phú Nhuận, TP. Hồ Chí Minh.</p>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.YC_GQ_QLBH &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                </div>
                                                {/* 2.	Theo dõi hồ sơ và bổ sung thông tin */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.TD_HS_BS_TT ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"} style={{ borderBottom: 0, paddingBottom: 0 }}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.TD_HS_BS_TT)}>
                                                        <p>2. Theo dõi hồ sơ và bổ sung thông tin</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab !== PROCESS_PER_BENEFIT_OPTS.TD_HS_BS_TT &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-4px'}}>
                                                            <div className='head-title'><p
                                                                style={{paddingLeft: 12}}>Ngay sau khi tiếp nhận Yêu
                                                                cầu giải quyết QLBH từ Khách hàng, DLVN sẽ xử
                                                                lý hồ sơ. Khách hàng nên thường xuyên theo dõi thông
                                                                tin, tình trạng xử lý hồ sơ qua: </p></div>
                                                            <p className='no-dot'>- Dai-ichi Connect;</p>
                                                            <p className='no-dot'>- Tin nhắn Zalo;</p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Thư điện
                                                                tử (đối với yêu cầu giải quyết Quyền lợi bảo hiểm chăm
                                                                sóc sức khỏe/ Hỗ trợ viện phí); thư báo qua bưu điện đối
                                                                với các quyền lợi khác. </p>
                                                            <div className='head-title'><p className='no-dot'></p></div>
                                                            <div className='head-title'><p
                                                                style={{paddingLeft: 12}}>Đồng thời, Khách hàng có thể
                                                                nộp bổ sung thông tin chứng từ trực tiếp trên Dai-ichi
                                                                Connect/ Zalo, ngoại trừ một số trường hợp DLVN
                                                                có yêu cầu nộp bản gốc chứng từ, khi đó, Khách
                                                                hàng sẽ nộp bản gốc chứng từ tại các Văn phòng
                                                                Tổng Đại lý DLVN trên toàn quốc, hoặc gửi bưu điện
                                                                bằng hình thức đảm bảo về Phòng Giải quyết Quyền lợi
                                                                Bảo hiểm, địa chỉ: Tòa nhà Dai-ichi Life Việt Nam, 149 -
                                                                151 Nguyễn Văn Trỗi, Phường 11, Quận Phú Nhuận, TP. Hồ
                                                                Chí Minh.</p></div>
                                                        </div>
                                                    </div>
                                                    {jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.TD_HS_BS_TT &&
                                                        <div className="rights-process__subItemEnd"/>}
                                                </div>
                                                {/* Quyết định giải quyết Quyền lợi bảo hiểm và thanh toán */}
                                                <div
                                                    className={jsonState.currentBenInProcessTab === PROCESS_PER_BENEFIT_OPTS.BH_TN ?
                                                        "rights-process__item dropdown show" : "rights-process__item dropdown"}>
                                                    <div className="dropdown__content"
                                                         onClick={() => this.handlerChangeProcessPerBen(PROCESS_PER_BENEFIT_OPTS.BH_TN)}>
                                                        <p>3. Quyết định giải quyết Quyền lợi bảo hiểm và thanh toán</p>
                                                        <div className="arrow">
                                                            <p className="primary-text">Xem chi tiết</p>
                                                            <p className="alternative-text">Thu gọn</p>
                                                            <img src="/img/icon/arrow-down-bronw.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                        <div className="rights-process__item__content-wrapper"
                                                             style={{marginBottom: '-4px'}}>
                                                            <div className='head-title'><p style={{paddingLeft: 12}}>Từ
                                                                khi nhận được thông tin, chứng từ đầy đủ và hợp
                                                                lệ từ Khách hàng (kể cả các chứng từ được yêu
                                                                cầu bổ sung sau khi nộp Phiếu yêu cầu), DLVN sẽ xử
                                                                lý và thông báo kết quả đến Khách hàng theo quy
                                                                định của Quy tắc và Điều khoản Hợp đồng.</p></div>
                                                            <div className='head-title'><p
                                                                style={{paddingLeft: 12}}>Thông báo kết quả giải
                                                                quyết QLBH cũng sẽ được gửi đến Khách hàng qua
                                                                các kênh: </p></div>
                                                            <p className='no-dot'>- Dai-ichi Connect;</p>
                                                            <p className='no-dot'>- Tin nhắn Zalo;</p>
                                                            <p className='no-dot' style={{display: 'inline'}}>- Thư điện
                                                                tử (đối với yêu cầu giải quyết Quyền lợi bảo hiểm chăm
                                                                sóc sức khỏe/ Hỗ trợ viện phí); thư báo qua bưu điện đối
                                                                với các quyền lợi khác. </p>
                                                            <div className='head-title'><p className='no-dot'></p></div>
                                                            <div className='head-title'><p
                                                                style={{paddingLeft: 12}}>Đồng thời với việc thông
                                                                báo kết quả giải quyết QLBH, số tiền QLBH chi
                                                                trả sẽ được thanh toán cho Khách hàng nếu thông
                                                                tin nhận tiền đã được cung cấp đầy đủ và hợp
                                                                lệ. </p></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="bottom-btn">
                                            <button className="btn btn-primary basic-semibold"
                                                    style={{'maxWidth': '780px'}}
                                                    onClick={this.handlerCreateNewRequest}>Tạo yêu cầu ngay
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            {/* Bảo lãnh viện phí */}
                            <div className={jsonState.currentTab === TABS.BAO_LANH_VIEN_PHI ?
                                "policy-content-wrapper commitment-letter dichvu-page show" :
                                "policy-content-wrapper commitment-letter dichvu-page"}>
                                <div className="bieumau-page__item">
                                    <div className="content-wrapper">
                                        {/* Dịch vụ bảo lãnh viện phí */}
                                        <div className={jsonState.currentOption === BILLING_TAB_OPTS.WHITELIST ?
                                            "content-item dropdown show" :
                                            "content-item dropdown"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.BAO_LANH_VIEN_PHI, BILLING_TAB_OPTS.WHITELIST)}>
                                                <p>Dịch vụ bảo lãnh viện phí</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                <div className="content-wrapper" style={{paddingLeft: '7px'}}>
                                                    <div className="list-information"
                                                         style={{paddingBottom: '0px', marginLeft: '9px'}}>
                                                        <p>Nhằm tạo điều kiện thuận lợi cho Khách hàng về giải
                                                            quyết quyền lợi bảo hiểm khi khám/ điều trị tại Cơ
                                                            sở y tế, DLVN có hợp tác với các Cơ sở y tế về dịch
                                                            vụ Bảo lãnh viện phí Nội trú, Ngoại trú và Chăm sóc
                                                            răng của sản phẩm Bảo Hiểm Chăm Sóc Sức Khỏe và Bảo Hiểm
                                                            Chăm Sóc Sức Khỏe Toàn Cầu</p>
                                                        <p>Khi Khách hàng (Người được bảo hiểm) đến khám/ điều
                                                            trị tại Cơ sở y tế trong hệ thống các Cơ sở y tế hợp tác
                                                            dịch vụ bảo lãnh viện phí với DLVN, Cơ sở y tế sẽ hướng
                                                            dẫn Khách hàng làm thủ tục bảo lãnh viện phí với
                                                            DLVN. Nếu yêu cầu bảo lãnh viện phí được chấp nhận,
                                                            DLVN sẽ thanh toán số tiền bảo lãnh viện phí trực
                                                            tiếp cho Cơ sở y tế. Trường hợp yêu cầu bảo lãnh viện phí
                                                            không được chấp nhận, Khách hàng sẽ thanh toán chi phí
                                                            khám/ điều trị cho Cơ sở y tế và sau đó nộp yêu cầu giải
                                                            quyết Quyền lợi bảo hiểm cho DLVN.</p>
                                                    </div>
                                                    <div className="text-wrapper">
                                                        <p className="basic-text2 basic-semibold pd-16">Không xem xét
                                                            bảo lãnh viện phí trong trường hợp sau:</p>
                                                    </div>
                                                    <div className="list-information" style={{marginBottom: '-18px'}}>
                                                        <ul className="sub-list">
                                                            <li className="sub-list-li">Hợp đồng bảo hiểm trong thời
                                                                gian xem xét lại 21 ngày, chưa được đóng phí bảo hiểm
                                                                đến hạn hoặc đã mất hiệu lực.
                                                            </li>
                                                            <li className="sub-list-li">Bệnh tật/ Thương tật thuộc điều
                                                                kiện loại trừ trách nhiệm bảo hiểm hoặc trong thời gian
                                                                loại trừ trách nhiệm bảo hiểm.
                                                            </li>
                                                            <li className="sub-list-li">Người được bảo hiểm có tình
                                                                trạng Bệnh tật/ Thương tật tồn tại trước hoặc
                                                                khám/điều trị Bệnh lý mạn tính.
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Cơ sở y tế bảo lãnh viện phí */}
                                        <div className={jsonState.currentOption === BILLING_TAB_OPTS.FACILITIES ?
                                            "content-item dropdown show" :
                                            "content-item dropdown"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.BAO_LANH_VIEN_PHI, BILLING_TAB_OPTS.FACILITIES)}>
                                                <p>Cơ sở y tế bảo lãnh viện phí</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                <div className="content-wrapper">
                                                    <div className="list-information" style={{marginBottom: '-18px'}}>
                                                        <ul className="sub-list">
                                                            <li className="sub-list-li pd-bottom-14">
                                                                Hệ thống Cơ sở y tế bảo lãnh viện phí <a
                                                                style={{display: 'inline'}}
                                                                href='https://www.dai-ichi-life.com.vn/vi-VN/danh-sach-benh-vien-bao-lanh-vien-phi/496/'
                                                                className="simple-brown2" target='_blank'>tại đây</a>
                                                            </li>
                                                            <li className="sub-list-li">
                                                                Hoặc liên hệ số (028) 3823 0108/ 3810 0888 để được hướng
                                                                dẫn chọn Cơ sở y tế phù hợp.
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Thủ tục, quy trình bảo lãnh viện phí */}
                                        <div className={jsonState.currentOption === BILLING_TAB_OPTS.BILLING_STEPS ?
                                            "content-item dropdown show" :
                                            "content-item dropdown"}>
                                            <div className="dropdown__content"
                                                 onClick={() => this.handlerChangeOption(TABS.BAO_LANH_VIEN_PHI, BILLING_TAB_OPTS.BILLING_STEPS)}>
                                                <p>Thủ tục, quy trình bảo lãnh viện phí</p>
                                                <div className="arrow">
                                                    <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                </div>
                                            </div>
                                            <div className="dropdown__items">
                                                <div className="content-wrapper">
                                                    <div className="vertical-step-list">
                                                        <div className="vertical-step-item">
                                                            <div className="bullet"><span>1</span></div>
                                                            <div className="vertical-step-content">
                                                                <p>Xuất trình giấy tờ tại Cơ sở y tế:</p>

                                                                <p className="basic-grey">- Thẻ Chăm sóc sức khỏe
                                                                    Dai-ichi Life (thẻ vật lý hoặc thẻ điện tử trên
                                                                    Dai-ichi Connect).</p>
                                                                <p className="basic-grey pd-bottom-34">
                                                                    - Giấy tờ tùy thân của Người được bảo hiểm (CMND/
                                                                    CCCD/ Hộ chiếu/ Giấy khai sinh).
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div className="vertical-step-item">
                                                            <div className="bullet"><span>2</span></div>
                                                            <div className="vertical-step-content">
                                                                <p className="pd-bottom-34">
                                                                    Xác nhận (ký tên) vào Yêu cầu giải quyết Quyền lợi
                                                                    bảo hiểm (bảo lãnh viện phí)
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div className="vertical-step-item">
                                                            <div className="bullet"><span>3</span></div>
                                                            <div className="vertical-step-content">
                                                                <p className="pd-bottom-34">
                                                                    Cơ sở y tế sẽ liên hệ DLVN về yêu cầu bảo lãnh và
                                                                    thông báo kết quả bảo lãnh viện phí đến Khách hàng
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div className="vertical-step-item">
                                                            <div className="bullet"><span>4</span></div>
                                                            <div className="vertical-step-content">
                                                                <p>
                                                                    Khi ra viện: kiểm tra, xác nhận, hoàn tất hồ sơ và
                                                                    thủ tục ra viện theo quy định của Cơ sở y tế.
                                                                </p>
                                                                <p className='basic-red pd-bottom-8'
                                                                   style={{fontWeight: '700', fontSize: '14px'}}>Lưu
                                                                    ý: </p>
                                                                <p className="basic-grey pd-bottom-8"
                                                                   style={{fontSize: '13px'}}>
                                                                    - Tùy quy định riêng của Cơ sở y tế, Quý Khách vui
                                                                    lòng đóng tạm ứng viện phí khi thực hiện thủ tục vào
                                                                    viện.</p>
                                                                <p className="basic-grey"
                                                                   style={{fontSize: '13px', marginBottom: '-18px'}}>
                                                                    - Thủ tục, quy trình bảo lãnh viện phí này không áp
                                                                    dụng cho bảo lãnh viện phí ở nước ngoài. Khi có yêu
                                                                    cầu bảo lãnh viện phí ở nước ngoài, Quý
                                                                    Khách vui lòng liên hệ DLVN, điện thoại số (028)
                                                                    3823 0108/ 3810 0888 để được hướng dẫn cụ thể.
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </main>
        );
    }
}

export default ClaimProcessGuide;
