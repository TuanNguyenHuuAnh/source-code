import React, { Component } from 'react';
import { Link, withRouter } from "react-router-dom";
import {
    ACCESS_TOKEN,
    AKTIVO_ACCESS_TOKEN,
    AKTIVO_MEMBER_ID,
    AUTHENTICATION,
    CLIENT_ID, FE_BASE_URL,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID,
    PageScreen,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    EDOCTOR_CODE,
    EDOCTOR_ID
} from '../constants';
import { CPSaveLog, getChallenges } from '../util/APIUtils';
import { getDeviceId, getSession, numOfDates, setSession, trackingEvent, getEnsc, isMobile, getDay, getMonth, getLinkPartner } from '../util/common';
import '../dest/swiper-bundle.min.css';
import SwiperCore, { Autoplay, Navigation, Pagination } from 'swiper';
import ChallengesSwiper from "../components/CustomSwiper/ChallengesSwiper";
import homeIcon from '../img/icon/homeIcon.svg';
import { isEmpty } from 'lodash';
import {Helmet} from "react-helmet";

SwiperCore.use([Autoplay, Pagination, Navigation]);

class CDYT extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            toggle: false,
            showRequireLogin: false,
            challenging: null,
            coming: null
        }
        this.carousel = React.createRef();

    }


    getChallenges() {
        let challengesRequest = {
            SubmitFrom: WEB_BROWSER_VERSION,
            APIToken: getSession(AKTIVO_ACCESS_TOKEN) ? getSession(AKTIVO_ACCESS_TOKEN) : '',
            ClientID: getSession(AKTIVO_MEMBER_ID) ? getSession(AKTIVO_MEMBER_ID) : ''
        }
        getChallenges(challengesRequest).then(response => {
            let challenge = response.data;
            if (!isEmpty(challenge)) {
                let challenging = challenge?.filter(item => {
                    return getDay(item.start_date) === 1;
                });
                challenging.sort((a, b) => {
                    let aIsFirst = getDay(a.start_date) === 1 ? 0 : 1;
                    let bIsFirst = getDay(b.start_date) === 1 ? 0 : 1;
                    if ((aIsFirst === 0) && (bIsFirst === 0)) {
                        if (getMonth(a.start_date) < getMonth(b.start_date)) {
                            aIsFirst = 0;
                            bIsFirst = 1;
                        } else if (getMonth(a.start_date) > getMonth(b.start_date)) {
                            aIsFirst = 1;
                            bIsFirst = 0;
                        }
                    }
                    return aIsFirst - bIsFirst;
                });
                let coming = challenge?.filter(item => {
                    return getDay(item.start_date) !== 1;
                });

                if (challenging && challenging.length > 0) {
                    this.setState({ challenging: challenging });
                }
                if (coming && coming.length > 0) {
                    this.setState({ coming: coming });
                }


            }
        }).catch(error => {
            console.log(error);
        });
    }

    componentDidMount() {
        this._isMounted = true;
        this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
            `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
        );
        //highlight the active menu
        setSession(LINK_MENU_NAME, 'Trang chủ');
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, 'Thử thách sống khỏe');
        setSession(LINK_SUB_MENU_NAME_ID, 'h3');
        this.getChallenges();
    }


    componentWillUnmount() {
        this._isMounted = false;
        this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
            `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
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
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }

    render() {

        let classNm = 'hw-page';
        if (getSession(ACCESS_TOKEN)) {
            classNm = "hw-page logined";
        }

        return (
            <>
                {(this.state.challenging || this.state.coming) &&
                    <Helmet>
                        <title>DAI-ICHI LIFE - CUNG ĐƯỜNG YÊU THƯƠNG</title>
                        <meta name="description"
                            content="Chương trình rèn luyện, chăm sóc sức khỏe và hoạt động thể dục thể thao trực tuyến vì cộng đồng Dai-ichi Life - Cung Đường Yêu Thương do Dai-ichi Life Việt Nam tổ chức nhằm khuyến khích rèn luyện sức khỏe và lan tỏa những giá trị tốt đẹp." />
                        <meta name="keywords"
                            content="Cung Đường Yêu Thương, giải chạy bộ, giải chạy trực tuyến, ứng dụng chăm sóc sức khỏe, sống vui khỏe" />
                        <meta name="robots" content="index, follow" />
                        <meta property="og:type" content="website" />
                        <meta property="og:url" content={FE_BASE_URL + "/song-vui-khoe/cung-duong-yeu-thuong"} />
                        <meta property="og:title" content="DAI-ICHI LIFE - CUNG ĐƯỜNG YÊU THƯƠNG" />
                        <meta property="og:description"
                            content="Cung Đường Yêu Thương, giải chạy bộ, giải chạy trực tuyến, ứng dụng chăm sóc sức khỏe, sống vui khỏe" />
                        <meta property="og:image"
                            content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40songvuikhoe2_1668399023175.png" />
                        <link rel="canonical" href={FE_BASE_URL + "/song-vui-khoe/cung-duong-yeu-thuong"} />                    </Helmet>
                }
                <main className={classNm} id="scrollableDiv">
                    {/*breadcums */}
                    <div className="container">
                        {/* <!-- Breadcrums --> */}
                        <div className="breadcrums challenge-flex-mobile" style={{ paddingLeft: 0 }}>
                            <div className="breadcrums__item">
                                <p className="challenge-home"><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
                                <img className="challenge-mobile-wrapper" src={homeIcon} alt="home-ico" />
                                <p className='breadcrums__item_arrow'>&gt;</p>
                            </div>
                            <div className="breadcrums__item">
                                <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
                                <p className='breadcrums__item_arrow'>&gt;</p>
                            </div>
                            <div className="breadcrums__item">
                                <p>Cung đường yêu thương</p>
                                <p className='breadcrums__item_arrow'>&gt;</p>
                            </div>
                        </div>
                        {/* <!-- End Breadcrums --> */}
                    </div>

                    {/* bắt đầu từ banner */}
                    <section className="sccdyt-banner" />
                    <section className="sccdyt-description" style={{ padding: '24px 16px', maxWidth: '1080px', margin: '0 auto', textAlign: 'center', lineHeight: '26px', fontSize: '25px' }}>
                        <p><b style={{ color: '#DE181F' }}>Dai-ichi Life - Cung Đường Yêu Thương</b> là chương trình rèn luyện, chăm sóc sức khỏe và hoạt động thể dục
                            thể thao trực tuyến vì cộng đồng do Dai-ichi Life Việt Nam (DLVN) tổ chức thường niên. Đây là chương trình
                            hoàn toàn miễn phí dành cho người dân Việt Nam, khuyến khích mỗi người, mỗi nhà xây dựng lối sống tích cực,
                            chủ động rèn luyện và chăm sóc sức khỏe, lan tỏa tinh thần: yêu thương bản thân và gia đình,
                            yêu thương cộng đồng, yêu thương Trái đất. Hành trình mùa thứ 5 chính thức trở lại với diện mạo mới,
                            được tích hợp với các dịch vụ Sống khỏe hữu ích ngay trên ứng dụng <b>Dai-ichi Connect </b>
                            giúp người dùng có những trải nghiệm gần gũi và thân thiện hơn.</p>
                    </section>
                    {/*--------------------------------------------------- cần cập nhật daily vì chưa fetch value về -------------------------------------------------------------------*/}
                    {/* <div className="highlight-section">
                    <div className="container highlight-grid-2x2x1">
                        <div className="highlight icon-donation">
                                <p>
                                    <span className="highlight_title">Số tiền đã quyên góp</span><br /><br />
                                    <span className="highlight_value">5 tỷ </span><span className="text-red large">đồng</span>
                                </p>
                        </div>

                        <div className="highlight icon-trees">
                            <p>
                                <span className="highlight_title">Số cây xanh đã quyên góp</span><br /><br />
                                <span className="highlight_value">15,000 </span><span className="text-red large">cây</span>
                            </p>
                        </div>

                        <div className="highlight icon-distance">
                            <p>
                                <span className="highlight_title">Quãng đường tích lũy</span><br /><br />
                                <span className="highlight_value">27,417,804 </span><span className="text-red large">km</span>
                            </p>
                        </div>

                        <div className="highlight icon-duration">
                            <p>
                                <span className="highlight_title">Số giờ tập luyện</span><br /><br />
                                <span className="highlight_value">56,902 </span><span className="text-red large">giờ</span>
                            </p>
                        </div>

                        <div className="highlight icon-participant participant-block">
                            <div className="participant-content">
                                <p>
                                    <span className="highlight_title">Số người tham gia</span><br /><br />
                                    <span className="highlight_value">26,902 </span><span className="text-red large">chiến binh</span>
                                </p>
                            </div>
                            <button className="btn btn-primary join-now" style={{justifyContent: 'center'}} onClick={showQRC}>Tham gia ngay</button>
                        </div>
                    </div>
                </div> */}

                    {/* mục 1 */}
                    <div style={{ background: 'white' }}>
                        <div className="container-diff" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                            <div style={{
                                width: '100%',
                                background: 'white',
                                borderRadius: '9px',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: '10px',
                                gap: '10px'
                            }}>
                                <div className="cdyt-difference-header">
                                    <h2 className="cdyt2025_dif">ĐIỂM KHÁC BIỆT MÙA</h2>
                                    <div className="icon-2025" />
                                </div>
                                <b style={{ textAlign: 'center', fontSize: '24px' }}>1. Thử thách Ngũ Đại đổi mới</b>

                                <div className="godai-image" />

                                <div style={{ maxWidth: '1080px' }}>
                                    <p style={{ textAlign: 'center', lineHeight: '26px', fontSize: '17px' }}>
                                        Lấy cảm hứng từ yếu tố hình thành nên sự sống:
                                        <span className="element earth"> Đất</span>,
                                        <span className="element water"> Nước</span>,
                                        <span className="element fire"> Lửa</span>,
                                        <span className="element wind"> Gió</span>,
                                        <span className="element void"> Vô</span> theo triết lý Ngũ Đại Nhật Bản,
                                        5 thử thách sẽ tương ứng với 5 hành trình tập luyện kéo dài 2 tuần với bộ môn đại diện cho tinh thần:
                                        <span className="element earth"> Bền bỉ</span>,
                                        <span className="element water"> Linh hoạt</span>,
                                        <span className="element fire"> Nhiệt huyết</span>,
                                        <span className="element wind"> Lan tỏa</span> và
                                        <span className="element void"> Yêu thương</span>.
                                        Bên cạnh đó, bạn còn có thể tham gia tập luyện với các thử thách phụ để vừa nâng cao sức khỏe
                                        vừa gia tăng cơ hội nhận quà tặng hấp dẫn từ chương trình (không giới hạn số lượng thử thách tham gia).
                                    </p>
                                </div>
                                {/*thử thách */}
                                <div className="container">

                                    {this.state.challenging &&
                                        <div className="cate-section mt-mobile-16">
                                            {/* <div className="cate-heading">
                                    <h3 className="cate-heading-title"></h3>
                                </div> */}
                                            <ChallengesSwiper data={this.state.challenging} />
                                        </div>
                                    }
                                    {this.state.coming &&
                                        <div className="cate-section">
                                            <div className="cate-heading" style={{ textAlign: 'center', display: 'flex', justifyContent: 'center', paddingTop: '12px' }}>
                                                <h3 className="cate-heading-title">Và nhiều thử thách hấp dẫn khác</h3>
                                            </div>
                                            <ChallengesSwiper data={this.state.coming} />
                                        </div>
                                    }

                                </div>
                            </div>
                        </div>
                    </div>

                    {/* mục 2 */}
                    <div style={{ background: 'white' }}>
                        <div className="container-muc2" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                            <div style={{
                                width: '100%',
                                background: 'white',
                                borderRadius: '9px',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: '60px 0 0',
                                maxWidth: '1080px',
                                gap: '10px'
                            }}>
                                {/* mục 2 */}
                                <p style={{ textAlign: 'center'}}>
                                    <b style={{ textAlign: 'center', fontSize: '24px', gap: '20px' }}>
                                        2. Ghi nhận các bộ môn không giới hạn
                                    </b>
                                </p>
                                <p>
                                    <span style={{ fontWeight: 'bold', fontSize: '24px', padding: '10px', color: '#DE181F' }}>
                                        thông qua Dai-ichi Connect
                                    </span>

                                </p>
                                <div className="container" style={{ padding: '0 16px', maxWidth: '1080px' }}>
                                    <p style={{ textAlign: 'center', lineHeight: '26px', fontSize: '17px' }}>
                                        Hỗ trợ ghi nhận tất cả bộ môn thể thao tập luyện trên ứng dụng Dai-ichi Connect hoặc đồng bộ
                                        kết quả từ các ứng dụng khác như: Garmin, Strava, Google Fit... Bạn có thể chọn lựa bộ môn
                                        yêu thích để rèn luyện sức khỏe mỗi ngày.
                                    </p>
                                </div>

                                {/* <div className="sport-icons" /> */}
                                <div className="app-connection" />

                            </div>
                        </div>
                    </div>

                    {/* mục 3 */}
                    <div className="container-prize" style={{ padding: '60px 0 0 0', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>

                        <div style={{
                            width: '100%',
                            borderRadius: '9px',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'space-around',
                            flexDirection: 'column',
                            gap: '20px'
                        }}>
                            <b style={{ textAlign: 'center', fontSize: '24px' }}>3. Hàng ngàn quà tặng hấp dẫn đón chờ</b>

                            <div className="container" style={{ padding: '0 16px', maxWidth: '1080px' }}>
                                <p style={{ textAlign: 'center', lineHeight: '26px', fontSize: '17px' }}>
                                    Bộ sưu tập quà tặng đẹp mắt cùng tấm huy chương độc đáo sẽ dành cho các chiến binh đồng
                                    hành cùng Dai-ichi Life – Cung Đường Yêu Thương trong các thử thách tập luyện. Duy trì
                                    sự bền bỉ, tham gia đầy đủ các thử thách, bứt phá giới hạn bản thân, nhiệt huyết chinh
                                    phục mục tiêu đề ra… tất cả đều được ghi nhận và tưởng thưởng xứng đáng.
                                </p>
                            </div>
                            <div className="prize-section" style={{ justifyContent: 'center' }}>
                                <div className="prize-image" />
                            </div>
                        </div>
                    </div>

                    {/* mục 4 */}
                    <div className="container-health" style={{ padding: '60px 0 0 0', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                        <div style={{
                            width: '100%',
                            borderRadius: '9px',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'space-around',
                            flexDirection: 'column',
                            gap: '20px'
                        }}>
                            <b style={{ textAlign: 'center', fontSize: '24px' }}>
                                4. Theo dõi hành trình tập luyện thông qua chỉ số sức khỏe
                            </b>

                            <div className="container" style={{ padding: '0 16px', maxWidth: '1080px' }}>
                                <p style={{ textAlign: 'center', lineHeight: '26px', fontSize: '17px' }}>
                                    Ngoài việc ghi nhận kết quả trực tiếp, Dai-ichi Connect còn giúp quá trình tập luyện, chinh
                                    phục Dai-ichi Life – Cung Đường Yêu Thương thêm thuận tiện hơn với tính năng Theo dõi vận
                                    động, giúp các chiến binh có thể ghi nhận tình trạng sức khỏe thể chất theo tuần/tháng để
                                    lên kế hoạch những buổi tập khoa học. Bên cạnh đó, các chiến binh còn có thể tham khảo ý
                                    kiến từ các chuyên gia y tế/Bác sĩ từ dịch vụ Tư vấn sức khỏe để có được chế độ tập luyện và
                                    dinh dưỡng phù hợp.
                                </p>
                            </div>

                            <div className="health-consultation-image" />
                            <a href="#" style={{ textAlign: 'center', width: '100%' }}>
                                <div className="health-consultation-button" onClick={() => clickEdoctor()} />
                            </a>
                        </div>
                    </div>

                    {/*mục 5 */}
                    <div className="container muc5" style={{ padding: '60px 0 0 0', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                        <div style={{
                            width: '100%',
                            borderRadius: '9px',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'space-around',
                            flexDirection: 'column',
                            gap: '10px'
                        }}>
                            <b style={{ textAlign: 'center', fontSize: '24px' }}>5. Đóng góp cho cộng đồng và Trái Đất</b>
                            <div className="container" style={{ padding: '0 16px', maxWidth: '1080px' }}>
                                <p style={{ textAlign: 'center', lineHeight: '26px', fontSize: '17px' }}>
                                    Với mỗi km người tham gia hoàn thành, DLVN sẽ đóng góp 1,000 đồng vào Quỹ "Vì cuộc sống tươi đẹp", hướng đến mục tiêu 5 tỷ đồng hỗ trợ những người dân có hoàn cảnh khó khăn trên khắp mọi miền đất nước. Đặc biệt, mỗi 30 hoạt động Xanh được ghi nhận từ người tham gia sẽ được quy đổi thành 1 cây xanh, hướng đến mục tiêu 5,555 cây xanh góp phần phủ xanh Trái Đất. Một chạm sống khỏe toàn diện cùng Dai-ichi Life - Cung Đường Yêu Thương, thỏa sức tập luyện theo từng bộ môn yêu thích.
                                </p>
                            </div>
                        </div>
                    </div>

                    {/* CSR */}

                    <div className="csr-container">
                        <div className="col2-csr">
                            <div className="csr-image-left" />
                            <p className="left_col_line" style={{ textAlign: 'center' }}>
                                <span style={{ fontWeight: 'bolder', fontSize: '22px' }}>Mỗi 1 km được ghi nhận</span><br /><br />
                                <span style={{ fontWeight: 'bold', color: '#45C2EE', fontSize: '39px' }}>= 1,000 </span>
                                <span style={{ color: '#45C2EE', fontSize: '25px' }}>VNĐ</span><br /><br />
                                <span style={{ lineHeight: '21px', fontSize: '15px' }}>HỖ TRỢ NGƯỜI DÂN<br />CÓ HOÀN CẢNH KHÓ KHĂN</span>
                            </p>
                        </div>

                        <div className="col2-csr">
                            <div className="csr-image-right" />
                            <p className="right_col_line" style={{ textAlign: 'center' }}>
                                <span style={{ fontWeight: 'bolder', fontSize: '22px' }}>Mỗi 30 hoạt động</span><br /><br />
                                <span style={{ fontWeight: 'bold', color: '#45C2EE', fontSize: '39px' }}>= 1 </span>
                                <span style={{ color: '#45C2EE', fontSize: '25px' }}>CÂY XANH</span><br /><br />
                                <span style={{ lineHeight: '21px', fontSize: '15px' }}>GÓP PHẦN<br />PHỦ XANH TRÁI ĐẤT</span>
                            </p>
                        </div>
                    </div>

                    {/* tài liệu */}

                    <div className="family">
                        <div className="family-run-section" style={{ background: 'linear-gradient(180deg, #FFFFFF 6.33%, #FFEFE7 100%)' }}>
                            <div className="grid-doc">

                                {/* Title */}
                                <div className="grid-title">
                                    CÁCH THỨC THAM GIA & TÀI LIỆU HƯỚNG DẪN
                                </div>

                                {/* Image */}
                                <div className="grid-image">
                                    <div className="family-ngu-dai">
                                        <div className="family-run-image" />
                                    </div>
                                </div>

                                {/* List */}
                                <div className="grid-list participate-list">
                                    {[
                                        {
                                            icon: 'doc_info_icon',
                                            text: 'THÔNG TIN CHƯƠNG TRÌNH',
                                            link: 'https://kh.dai-ichi-life.com.vn/song-vui-khoe/bi-quyet/tin-tuc-va-su-kien/tin-tuc/thong-tin-chuong-trinh-dai-ichi-life-cung-duong-yeu-thuong-2025'
                                        },
                                        {
                                            icon: 'doc_qna_icon',
                                            text: 'CÁC CÂU HỎI THƯỜNG GẶP',
                                            link: 'https://kh.dai-ichi-life.com.vn/song-vui-khoe/bi-quyet/tin-tuc-va-su-kien/tin-tuc/cac-cau-hoi-thuong-gap-dai-ichi-life-cung-duong-yeu-thuong-2025'
                                        },
                                        {
                                            icon: 'doc_fund_icon',
                                            text: 'QUỸ VÌ CUỘC SỐNG TƯƠI ĐẸP',
                                            link: 'https://dai-ichi-life.com.vn/quy-vi-cuoc-song-tuoi-dep'
                                        }
                                    ].map((item, index) => (
                                        <a href={item.link} key={index} className="participate-item" target="_blank" rel="noopener noreferrer">
                                            <div className={`doc-icon ${item.icon}`} />
                                            <p>{item.text}</p>
                                            <div className="learn-more-icon" />
                                        </a>
                                    ))}
                                </div>

                            </div>
                        </div>
                    </div>


                    {/* tải xuống */}
                    <div className="title-download" style={{ marginTop: '30px', marginLeft: '110px', padding: '0 80px 0' }}>
                        <strong>
                            <span style={{
                                fontWeight: 700,
                                lineHeight: '40px',
                                fontSize: '24px',
                                color: 'rgb(41, 41, 41)'
                            }}>TẢI </span>
                            <span style={{
                                fontWeight: 700,
                                lineHeight: '40px',
                                fontSize: '24px',
                                color: 'rgb(222, 24, 31)'
                            }}>DAI-ICHI CONNECT</span>
                        </strong>
                        <p>
                            <strong>
                                <span style={{
                                    fontWeight: 700,
                                    lineHeight: '32px',
                                    fontSize: '24px',
                                    color: 'rgb(41, 41, 41)'
                                }}>Sống vui sống khỏe toàn diện ngay hôm nay</span>
                            </strong>
                        </p>
                    </div >
                    <div className="container-download" style={{
                        padding: '60px 80px 0 0',
                        display: 'flex',
                        justifyContent: 'space-between',
                        flexWrap: 'wrap',
                        marginTop: '-80px',
                        marginLeft: '120px'
                    }}>
                        {/* Cột trái: nội dung */}
                        <div className="col2 content-column" style={{ flex: '1 1 50%', padding: '50px 70px 70px' }}>
                            <div style={{ margin: '30px 0' }} />

                            {/* Nút tải app */}
                            <div className="download_type" style={{ display: 'flex', justifyContent: 'left' }}>
                                <div>
                                    <div style={{ marginBottom: '6px' }}>
                                        <a href="https://play.google.com/store/apps/details?id=com.dlvn.mcustomerportal" target="_blank" rel="noopener noreferrer">
                                            <img alt="Google Play" src="https://kh.dai-ichi-life.com.vn/img/googleplay.png" />
                                        </a>
                                    </div>
                                    <div className="icon-wrapper">
                                        <a href="https://apps.apple.com/vn/app/dai-ichi-connect/id1435474783?l=vi" target="_blank" rel="noopener noreferrer">
                                            <img alt="App Store" src="https://kh.dai-ichi-life.com.vn/img/applestore.png" />
                                        </a>
                                    </div>
                                </div>
                                <div className="download_QR" style={{ overflow: 'hidden', position: 'relative', marginLeft: '20px' }}>
                                    <a
                                        href="http://onelink.to/srb7p8?utm_source=dai-ichi-connect&utm_medium=article&utm_campaign=health-content&utm_content=aktivolabs-tai-app-daiichi-connect"
                                        target="_blank" rel="noopener noreferrer"
                                    >
                                        <img alt="QR Code" src="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40image202308181346511_1692340642024.png" />
                                    </a>
                                </div>
                            </div>

                            {/* Các bước hướng dẫn */}
                            <ul className="download_steps" style={{
                                listStyleType: 'none',
                                paddingTop: '20px',
                                paddingLeft: '0'
                            }}>
                                <li style={{ fontSize: '1.6rem', lineHeight: '2.5rem', marginBottom: '0.5rem' }}>
                                    <b>Bước 1:</b> Tải ứng dụng bằng cách quét QR code hoặc tìm Dai-ichi Connect trên Apple store hoặc Play store.
                                </li>
                                <li style={{ fontSize: '1.6rem', lineHeight: '2.5rem', marginBottom: '0.5rem' }}>
                                    <b>Bước 2:</b> Đăng ký tài khoản bằng email hoặc đăng nhập bằng tài khoản Dai-ichi cung cấp.
                                </li>
                                <li style={{ fontSize: '1.6rem', lineHeight: '2.5rem', marginBottom: '0.5rem' }}>
                                    <b>Bước 3:</b> Trải nghiệm ứng dụng Dai-ichi Connect.
                                </li>
                            </ul>
                        </div>

                        {/* Cột phải: hình ảnh minh họa mobile */}
                        <div className="col2 image-column footer-download-image-mobile" style={{
                            flex: '1 1 50%',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            marginTop: '20px',
                            marginLeft: '0'
                        }}>
                            <div style={{ height: '384px' }} />
                        </div>
                    </div>
                    <div className="footer-background" style={{ marginTop: '-210px' }} />


                    <div className="footer_cdyt">
                        <div className="footer_cdyt_logo" />
                        <div className="footer_text">KỶ LỤC VIỆT NAM ĐÃ ĐƯỢC XÁC LẬP</div>
                        {[
                            {
                                year: '2021',
                                link: 'https://kyluc.vn/tin-tuc/ky-luc/dai-ichi-viet-nam-xac-lap-ky-luc-viet-nam-voi-hon-2-trieu-kilomet-di-bo-chay-bo-truc-tuyen-gay-quy-vi-cong-dong'
                            },
                            {
                                year: '2022',
                                link: 'https://kyluc.vn/tin-tuc/ky-luc/dai-ichi-life-viet-nam-don-nhan-ky-luc-viet-nam-cho-chuong-trinh-the-thao-truc-tuyen-gay-quy-vi-cong-dong-cung-duong-yeu-thuong-2022'
                            },
                            {
                                year: '2023',
                                link: 'https://kyluc.vn/tin-tuc/ky-luc-viet-nam/lien-tuc-pha-vo-ve-luong-va-chat-dai-ichi-life-cung-duong-yeu-thuong-2023-xac-lap-ky-luc-viet-nam-cho-hoat-dong-the-duc-the-thao-truc-tuyen-gay-quy-vi-cong-dong'
                            }
                        ].map((item, index) => (
                            <a
                                key={index}
                                href={item.link}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="record-link"
                            >
                                <div className="record-icon" />
                                <span className="record-year">{item.year}</span>
                            </a>
                        ))}
                    </div>
                </main>
            </>
        )
    }

}

const clickEdoctor = () => {
    let request = '';
    if (!getSession(ACCESS_TOKEN)) {
        request = {
            company: "DLVN",
            partner_code: EDOCTOR_CODE,
            deviceid: getDeviceId(),
            timeinit: new Date().getTime()
        }
        getEnsc(request, FE_BASE_URL + '/tu-van-suc-khoe');
    } else {
        getLinkPartner(EDOCTOR_ID, FE_BASE_URL + '/tu-van-suc-khoe');
    }
}

const showQRC = (event) => {
    event.preventDefault(); // Ngăn chặn hành động mặc định của nút

    if (isMobile()) {
        document.getElementById('popup-DownloadnExperience').className = "popup special point-error-popup show";
    } else {
        document.getElementById('popup-QRC').className = "popup special point-error-popup show";
    }
}
export default withRouter(CDYT);
