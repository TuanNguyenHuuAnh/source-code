import React, {Component} from 'react';
import {Link, withRouter} from "react-router-dom";
import {
    ACCESS_TOKEN,
    AKTIVO_ACCESS_TOKEN,
    AKTIVO_MEMBER_ID,
    API_NEWS_LIST_ALL_HOTS_BY,
    AUTHENTICATION,
    CLIENT_ID,
    CMS_CATEGORY,
    CMS_CATEGORY_LIST_DATA,
    CMS_HW_PAGE_SIZE,
    CMS_IMAGE_URL,
    CMS_KEYWORK,
    CMS_NEWS,
    CMS_PAGE_DEFAULT,
    CMS_SUB_CATEGORY_ID,
    CMS_SUB_CATEGORY_MAP,
    CMS_TYPE,
    DCID,
    EDOCTOR_CODE,
    EDOCTOR_ID,
    FE_BASE_URL,
    FROM_APP,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID,
    MY_HEALTH,
    PageScreen,
    SCREENS,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    CMS_CATEGORY_LIST_DATA_JSON,
    CMS_SUB_CATEGORY_MAP_JSON
} from '../constants';
import {CPSaveLog, fetchTagsData, getChallenges, getListByPath, fetchDoctorsData} from '../util/APIUtils';
import {
    compareDate,
    findItemInNewsList,
    formatDate,
    getDeviceId,
    getEnsc,
    getLinkId,
    getLinkPartner,
    getSession,
    getUrlParameter,
    postMessageMainNativeIOS,
    pushHistory,
    setSession,
    shortFormatDate,
    trackingEvent
} from '../util/common';
// import { showMessage } from '../util/common';
import {Swiper, SwiperSlide} from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {Autoplay, Navigation, Pagination} from 'swiper';
import LoadingIndicator from '../common/LoadingIndicator2';
import {Helmet} from "react-helmet";
import GeneralPopup from '../components/GeneralPopup';
import ChallengesSwiper from "../components/CustomSwiper/ChallengesSwiper";
import DoctorList2 from "../components/CustomSwiper/DoctorList2";
import {isEmpty} from "lodash";
import iconArrownRight from "../img/icon/iconArrowRight.svg";
import SpecialistPage from "../pages/Specialist/SpecialistPage";

let lastHotIndex = 0;
SwiperCore.use([Autoplay, Pagination, Navigation]);

class HealthLife extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            toggle: false,
            totalData: 0,
            hotNews: null,
            hotTags: null,
            doctors: null,
            datas: null,
            page: CMS_PAGE_DEFAULT,
            showRequireLogin: false,
            challenges: null,
            hideTags: false,
            hideDoctors: false,
            hideChallenges: false
        }
    }

    getCmsCategoryList = () => {
        if (!getSession(CMS_CATEGORY_LIST_DATA)) {
            setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
            setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
            // getListByPath(CMS_TYPE)
            //     .then(response => {
            //         if (response.statusCode === 200 && response.statusMessages === 'success') {
            //             if (this._isMounted) {
            //                 setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data))
            //                 let subCategoryMap = {};
            //                 for (let i = 0; i < response.data.length; i++) {

            //                     getListByPath(CMS_CATEGORY + response.data[i].code)
            //                         .then(res => {
            //                             if (res.statusCode === 200 && res.statusMessages === 'success') {
            //                                 subCategoryMap[response.data[i].code] = res.data;
            //                                 setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(subCategoryMap));
            //                                 console.log('HealthLife subCategoryMap=', subCategoryMap);
            //                                 this.setState({toggle: !this.state.toggle});


            //                             }
            //                         }).catch(error => {
            //                     });
            //                 }
            //             }
            //         }
            //     }).catch(error => {
            //     //show 404
            // });
        }
    }

    getNews = (page) => {
        getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY + page + "/" + CMS_HW_PAGE_SIZE + "/" + CMS_HW_PAGE_SIZE)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    if (this._isMounted) {
                        let jsonState = this.state;
                        jsonState.totalData = res.data.totalData;
                        jsonState.hotNews = res.data.hotNews;
                        this.state.page = page;
                        if (!jsonState.datas) {
                            jsonState.datas = res.data.datas;
                        } else {
                            jsonState.datas = jsonState.datas.concat(res.data.datas);
                        }
                        this.setState(jsonState);
                    }
                }
            }).catch(error => {
            // console.log(error);
        });
    }

    getNewsRefresh = (page) => {
        getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY + page + "/" + CMS_HW_PAGE_SIZE + "/" + CMS_HW_PAGE_SIZE)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    if (this._isMounted) {
                        let jsonState = this.state;
                        jsonState.totalData = res.data.totalData;
                        jsonState.hotNews = res.data.hotNews;
                        this.state.page = page;
                        jsonState.datas = res.data.datas;
                        this.setState(jsonState);
                    }
                }
            }).catch(error => {
            // console.log(error);
        });
    }

    fetchTags() {
        let requestData = {
            limit: 1000, start: 0,
        };
        fetchTagsData(requestData).then(Res => {
            if (!isEmpty(Res.data)) {
                this.setState({hotTags: this.sortListByOrder(Res.data)});

                // this.setState({hotTags: temp});
            } else {
                this.setState({hideTags: true});
            }
        }).catch(error => {
            this.setState({hideTags: true});
            console.log(error);
        });
    }


    fetchDoctors() {
        let requestData = {
            limit: 20, start: 0,
        };
        fetchDoctorsData(requestData).then(Res => {
            console.log("fetchDoctors", Res.data);
            if (!isEmpty(Res.data)) {
                let data = [...Res.data]
                let listSorted = this.sortListByOnboardDate(data);
                this.setState({doctors: listSorted});
            } else {
                this.setState({hideDoctors: true});
            }
        }).catch(error => {
            this.setState({hideDoctors: true});
            console.log(error);
        });
    }

    getChallenges() {
        let challengesRequest = {
            SubmitFrom: WEB_BROWSER_VERSION,
            APIToken: getSession(AKTIVO_ACCESS_TOKEN) ? getSession(AKTIVO_ACCESS_TOKEN) : '',
            ClientID: getSession(AKTIVO_MEMBER_ID) ? getSession(AKTIVO_MEMBER_ID) : ''
        }
        getChallenges(challengesRequest).then(response => {
            // let d = response.data;
            // d.push(response.data[1]);
            if (!isEmpty(response.data)) {
                this.setState({challenges: response.data});
            } else {
                this.setState({hideChallenges: true});
            }
            
        }).catch(error => {
            this.setState({hideChallenges: true});
            console.log(error);
        });
    }

    sortListByOnboardDate(obj) {
        obj.sort((a, b) => {
            if (this.strToDate(a.onboardDate) < this.strToDate(b.onboardDate)) return 1;
            else if (this.strToDate(a.onboardDate) > this.strToDate(b.onboardDate)) return -1;
            else return 0;
        });
        return obj;
    }

    strToDate(dtStr) {
        if (!dtStr) return null;
        let dateParts = dtStr.split("/");
        let timeParts = dateParts[2].split(" ")[1].split(":");
        dateParts[2] = dateParts[2].split(" ")[0];
        // month is 0-based, that's why we need dataParts[1] - 1
        return new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0], timeParts[0], timeParts[1], timeParts[2]);
    }

    sortListByOrder(obj) {
        obj.sort((a, b) => {
            if (!a.order) {
                a.order = 0;
            }
            if (!b.order) {
                b.order = 0;
            }
            if (a.order > b.order) return 1;
            else if (a.order < b.order) return -1;
            else return 0;
        });
        return obj;
    }

    componentDidMount() {
        this._isMounted = true;
        this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_WELLBEING}`);
        trackingEvent("Sống vui khỏe", `Web_Open_${PageScreen.HEALTH_WELLBEING}`, `Web_Open_${PageScreen.HEALTH_WELLBEING}`,);
        //highlight the active menu
        setSession(LINK_MENU_NAME, 'Trang chủ');
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, 'Sống vui khoẻ');
        setSession(LINK_SUB_MENU_NAME_ID, 'ah4');
        // this.getCmsCategoryList();
        this.getNewsRefresh(CMS_PAGE_DEFAULT);
        //Trace history path
        let ignore = getUrlParameter("ignore");
        let fromNative = getUrlParameter("from");
        if (!ignore) {
            if (fromNative) {
                pushHistory(fromNative);
            } else {
                pushHistory();
            }
        }
        this.fetchTags();
        this.fetchDoctors();
        postMessageMainNativeIOS(fromNative);
        let cdyt = getUrlParameter("cdyt");
        if (cdyt) {
            getLinkId();
        }
        this.getChallenges();
    }

    componentWillUnmount() {
        this._isMounted = false;
        this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_WELLBEING}`);
        trackingEvent("Sống vui khỏe", `Web_Close_${PageScreen.HEALTH_WELLBEING}`, `Web_Close_${PageScreen.HEALTH_WELLBEING}`,);
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
        const styles = {
            container: {
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                flexDirection: 'column',
                marginTop: '12px'
            }
        };
        const passSubCategory = (subCategoryId, imageUrl, keyWork) => {
            setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
            setSession(CMS_IMAGE_URL, imageUrl);
            setSession(CMS_KEYWORK, keyWork);
        }
        const loadMore = () => {
            this.getNews(this.state.page + 1);
        }
        const clickExternal = () => {
            // postMessageExternalNativeIOS();
            if (!getSession(ACCESS_TOKEN)) {
                this.setState({showRequireLogin: true});
            } else {
                getLinkId();
            }
        }
        const closeRequireLogin = () => {
            this.setState({showRequireLogin: false});
        }


        const clickEdoctor = () => {
            let request = '';
            if (!getSession(ACCESS_TOKEN)) {
                request = {
                    company: "DLVN", partner_code: EDOCTOR_CODE, deviceid: getDeviceId(), timeinit: new Date().getTime()
                }
                getEnsc(request, FE_BASE_URL + '/tu-van-suc-khoe');
            } else {
                request = {
                    company: "DLVN",
                    partner_code: EDOCTOR_CODE,
                    partnerid: "",
                    deviceid: getDeviceId(),
                    dcid: getSession(DCID),
                    token: getSession(ACCESS_TOKEN),
                    having_partnerid: "NO",
                    timeinit: new Date().getTime()
                }
                getLinkPartner(EDOCTOR_ID, FE_BASE_URL + '/tu-van-suc-khoe');

            }
        }

        let itemList = [];
        itemList.push(<SwiperSlide>
            <Link className="swiper-slide nav-cate-item" to={'/song-vui-khoe/cung-duong-yeu-thuong'}>
                <span className="nav-icon"><i className="ico ico-shoe ico__large"></i></span>
                <h2 className="nav-label">Cung Đường<br/>Yêu Thương</h2>
            </Link>
        </SwiperSlide>);
        itemList.push(<SwiperSlide>
            <Link className="swiper-slide nav-cate-item" to={MY_HEALTH + '/?display=qr'}>
                <span className="nav-icon"><i className="ico ico-tracking-movement ico__large"></i></span>
                <span className="nav-label">Theo dõi<br/>vận động</span>
            </Link>
        </SwiperSlide>);
        itemList.push(<SwiperSlide>
            <Link className="swiper-slide nav-cate-item" to={MY_HEALTH + '/?display=qr'}>
                <span className="nav-icon"><i className="ico ico-heartbeat ico__large"></i></span>
                <span className="nav-label">Sức khỏe<br/>của tôi</span>
            </Link>
        </SwiperSlide>);
        // itemList.push(<SwiperSlide>
        //     <Link className="swiper-slide nav-cate-item" to={"/song-vui-khoe/thu-thach-song-khoe"}>
        //         <span className="nav-icon"><i className="ico ico-health-challenge ico__large"></i></span>
        //         <span className="nav-label">Thử thách<br/>sống khỏe</span>
        //     </Link>
        // </SwiperSlide>);
        itemList.push(<SwiperSlide>
            <a className="swiper-slide nav-cate-item" onClick={() => clickEdoctor()}>
                <span className="nav-icon"><i className="ico ico-consultant ico__large"></i></span>
                <span className="nav-label">Tư vấn<br/>sức khỏe</span>
            </a>
        </SwiperSlide>);
        itemList.push(<SwiperSlide>
            <Link to={'/song-vui-khoe/bi-quyet'} className="swiper-slide nav-cate-item">
                <span className="nav-icon"><i className="ico ico-helth-insurance ico__large"></i></span>
                <h2 className="nav-label">Bí quyết<br/>Sống vui khỏe</h2>
            </Link>
        </SwiperSlide>);

        let props = {
            navigation: false, className: "nav-cate mySwiper z-index-under", slidesPerView: 6, scrollbar: {
                draggable: true, dragSize: 19.2
            }, breakpoints: {
                270: {
                    slidesPerView: 2, // spaceBetween: 40
                }, 300: {
                    slidesPerView: 2.7, // spaceBetween: 40
                }, 320: {
                    slidesPerView: 2.8, // spaceBetween: 40
                }, 340: {
                    slidesPerView: 3, spaceBetween: 10
                }, 360: {
                    slidesPerView: 3, spaceBetween: 10
                }, 500: {
                    slidesPerView: 4, spaceBetween: 10
                }, 600: {
                    slidesPerView: 5, spaceBetween: 10
                }, 768: {
                    slidesPerView: 5, // spaceBetween: 40
                }, 1024: {
                    slidesPerView: 5, // spaceBetween: 40
                }
            }
        };

        let classNm = 'hw-page';
        if (getSession(ACCESS_TOKEN)) {
            if (getSession(FROM_APP)) {
                classNm = "hw-page logined basic-padding-top-0";
            } else {
                classNm = "hw-page logined";
            }
        } else if (getSession(FROM_APP)) {
            classNm = 'hw-page basic-padding-top-0';
        }
        let x = 0;
        let total = 0;
        if (this.state.datas) {
            total = this.state.datas.length;
            if (total > this.state.page * CMS_HW_PAGE_SIZE) {
                total = this.state.page * CMS_HW_PAGE_SIZE;
            }
        }

        return (<main className={classNm} id="scrollableDiv">
            {this.state.datas && <Helmet>
                <title>Sống vui khỏe – Dai-ichi Life Việt Nam </title>
                <meta name="description"
                      content="Trang thông tin về sống vui khỏe của Dai-ichi Life Việt Nam, giúp khách hàng sống vui sống khỏe thông qua các bài viết, chương trình thể thao trực tuyến và các ứng dụng hữu ích."/>
                <meta name="keywords"
                      content="sống vui khỏe, sống vui sống khỏe, cẩm nang sống vui khỏe, sức khỏe"/>
                <meta name="robots" content="index, follow"/>
                <meta property="og:type" content="website"/>
                <meta property="og:url" content={FE_BASE_URL + "/song-vui-khoe"}/>
                <meta property="og:title" content="Sống vui khỏe – Dai-ichi Life Việt Nam"/>
                <meta property="og:description"
                      content="Trang thông tin về sống vui khỏe của Dai-ichi Life Việt Nam, giúp khách hàng sống vui sống khỏe thông qua các bài viết, chương trình thể thao trực tuyến và các ứng dụng hữu ích."/>
                <meta property="og:image"
                      content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40songvuikhoe2_1668399023175.png"/>
                <link rel="canonical" href={FE_BASE_URL + "/song-vui-khoe"}/>
            </Helmet>}
            <div className="hl_breadcrums_wrap">
                    <section className="scbreadcrums">
                        <div className="container">
                            <div className="breadcrums basic-white">
                                <Link to="/" className="breadcrums__item">
                                    <p>Trang chủ</p>
                                    <p className='breadcrums__item_arrow'>&gt;</p>
                                </Link>
                                <div className="breadcrums__item">
                                    <p>Sống vui khỏe</p>
                                    <p className='breadcrums__item_arrow'>&gt;</p>
                                </div>
                            </div>
                        </div>
                        <div className="hl_heading">Sống vui khỏe</div>
                    </section>
                </div>
                <div className='HealthLife_temp_cont' style={{marginBottom : '32px'}}>
                    <div className="container">
                    {/* <!-- NavCate --> */}
                        <div className="swiper-wrapper">
                            <Swiper {...props} >
                                {itemList}
                            </Swiper>
                        </div>
                    </div>
                </div>
                <div className="container">
                        {/* <!-- Health challenges Section --> */}
                        {!this.state.hideChallenges && 
                        <div className="cate-section" style={{marginTop : '62px'}}>
                            <div className="cate-heading">
                                <h3 className="cate-heading-title">Thử thách sống khỏe</h3>
                                <Link className="simple-brown basic-semibold" to={"/song-vui-khoe/thu-thach-song-khoe"}
                                    style={{fontSize: 16, display: 'flex', alignItems: 'center', marginRight: 4}}>Tất
                                    cả <span
                                        className="simple-brown basic-semibold"> <img src={iconArrownRight}
                                                                                    alt="arrow-right"/></span>
                                </Link>
                            </div>
                            <ChallengesSwiper data={this.state.challenges ? this.state.challenges : []}/>
                        </div>
                        }
                        {/* <!-- End Health challenges Section Section --> */}
                        {!this.state.hideDoctors && 
                        <div className='HealthLife_temp_cont'>
                            <div className="container">
                                {/* <!-- Doctor Section --> */}
                                <div className="cate-section" style={{marginTop : '52px'}}>
                                    <div className="cate-heading">
                                        <h3 className="cate-heading-title" style={{marginLeft: '-15px'}}>Bác sĩ/ Chuyên gia y tế</h3>
                                        <a className="simple-brown basic-semibold" href={FE_BASE_URL + "/tu-van-suc-khoe/bac-si"}
                                            style={{fontSize: 16, display: 'flex', alignItems: 'center', marginRight: 4}}>Tất
                                            cả <span
                                                className="simple-brown basic-semibold"> <img src={iconArrownRight}
                                                                                            alt="arrow-right"/></span>
                                        </a>
                                    </div>
                                    <DoctorList2 data={this.state.doctors ? (this.state.doctors.length > 4?this.state.doctors.slice(0,4): this.state.doctors) : []}/>
                                </div>
                                {/* <!-- End Doctor Section Section --> */}
                            </div>
                        </div>
                        }
                        {!this.state.hideTags && 
                        <div className='HealthLife_temp_cont' style={{paddingTop: '40px'}}>
                            <div className="container">
                                <div className="cate-section" style={ styles.container}>
                                    <div className="cate-heading heading-height">
                                        <h3 className="cate-heading-title" style={{marginLeft: '-15px'}}>Chuyên khoa được quan tâm</h3>
                                    </div>
                                    <SpecialistPage specialist={this.state.hotTags ? this.state.hotTags : []}/>
                                </div>
                                {/* <!-- End Special list Section Section --> */}
                            </div>
                        </div>
                        }

                        {/* <!-- Category Section --> */}
                        <div className="cate-section" style={{marginTop: '62px'}}>
                            <div className="cate-heading">
                                <h3 className="cate-heading-title">Bí quyết Sống khỏe</h3>
                                <Link className="simple-brown basic-semibold" to={"/song-vui-khoe/bi-quyet"}>Tất cả <span
                                    className="simple-brown basic-semibold">&gt;</span>
                                </Link>
                            </div>
                            <div className="listing-gird">
                                {this.state.hotNews && this.state.hotNews.map((item, index) => {
                                    if (index < this.state.page * CMS_HW_PAGE_SIZE) {
                                        lastHotIndex = index;
                                        let subPath = item.subCategoryPathName;
                                        let path = item.articlePathName;
                                        return (<div className="article-item article-item__border">
                                            <Link to={path}
                                                onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                className="article-thumb">
                                                <img src={item.physicalImgUrl} alt="" className=""/>
                                            </Link>
                                            <div className="article-item-right">
                                                <Link to={subPath}
                                                    onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                    className="article-cate-link">{item.newsCategory}</Link>
                                                <p><Link to={path}
                                                        onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                        className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}>{item.title}</Link>
                                                </p>
                                                <div className="article-nav">
                                                    {item.hasEvent ? (<span className="article-nav-item grid-event"
                                                                            style={{
                                                                                marginBottom: '2px', marginTop: '-2px'
                                                                            }}>
                                        <i className="ico ico-event-location"></i> <span
                                                        className='margin-event'>{item.eventLocation ? item.eventLocation : ''}</span>
                                        <i className="ico ico-event-date ico-margin"></i>
                                                        {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                                            <span
                                                                className='margin-event'>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>) : (
                                                            <span
                                                                className='margin-event'>{item.startDate ? formatDate(item.startDate) : ''}</span>)}

                                    </span>) : (<span className="article-nav-item adjust-last-icon-event">
                                        <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                    </span>)}
                                                    <a href="#"
                                                    className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                                </div>
                                            </div>
                                        </div>)
                                    }
                                })}
                                {this.state.datas && this.state.datas.map((item, idx) => {
                                    if (lastHotIndex + idx < this.state.page * CMS_HW_PAGE_SIZE) {
                                        if (idx < this.state.datas.length + x - this.state.hotNews.length) {
                                            if (findItemInNewsList(item, this.state.hotNews)) {
                                                x++;
                                            } else {
                                                let subPath = item.subCategoryPathName;
                                                let path = item.articlePathName;
                                                return (<div className="article-item article-item__border">
                                                        <Link to={path}
                                                            onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                            className="article-thumb">
                                                            <img src={item.physicalImgUrl} alt=""/>
                                                        </Link>
                                                        <div className="article-item-right">
                                                            <Link to={subPath}
                                                                onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                className="article-cate-link">{item.typeName}</Link>
                                                            <p><Link to={path}
                                                                    onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                    className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}
                                                                    alt={item.title}>{item.title}</Link></p>
                                                            <div className="article-nav">
                                                                {item.hasEvent ? (<span className="article-nav-item grid-event"
                                                                                        style={{
                                                                                            marginBottom: '2px',
                                                                                            marginTop: '-2px'
                                                                                        }}>
                                            <i className="ico ico-event-location"></i> <span
                                                                    className='margin-event'>{item.eventLocation ? item.eventLocation : ''}</span>
                                            <i className="ico ico-event-date ico-margin"></i>
                                                                    {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                                                        <span
                                                                            className='margin-event'>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>) : (
                                                                        <span
                                                                            className='margin-event'>{item.startDate ? formatDate(item.startDate) : ''}</span>)}

                                        </span>) : (<span
                                                                    className="article-nav-item adjust-last-icon-event">
                                            <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                        </span>)}
                                                                <a href="#"
                                                                className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                                            </div>
                                                        </div>
                                                    </div>

                                                )
                                            }
                                        }
                                    }
                                })}
                            </div>
                            <LoadingIndicator area="request-loading-area"/>
                            <div className="paging-container basic-expand-footer" style={{paddingTop: '0px'}}>
                            </div>
                        </div>
                        {/* <!-- End Category Section --> */}

                    </div>
            {getSession(FROM_APP) && getSession(FROM_APP) === "IOS" && <div className='padding-bottom-30'></div>}
            {this.state.showRequireLogin && <GeneralPopup closePopup={closeRequireLogin}
                                                          msg={'Quý khách vui lòng đăng nhập<br/>Dai-ichi Connect để tham gia Dai-ichi<br/>Life Cung Đường Yêu Thương'}
                                                          imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'}
                                                          buttonName={'Đăng nhập'}
                                                          linkToGo={SCREENS.HOME} screenPath={SCREENS.HEALTH}/>}
        </main>)
    }

}

export default withRouter(HealthLife);
