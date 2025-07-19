import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID, CMS_BASE_URL_NO_LOAD, CMS_BASE_URL_REPLACE,
    CMS_CATEGORY,
    CMS_CATEGORY_LIST_DATA,
    CMS_DETAIL_BY_LINK,
    CMS_DETAIL_BY_LINK_ONLY,
    CMS_IMAGE_URL,
    CMS_LIST,
    CMS_LIST_ALL_TAGS,
    CMS_NEWS,
    CMS_PAGE_DEFAULT,
    CMS_RELATIVE,
    CMS_SUB_CATEGORY_MAP,
    CMS_TAG_LIST_DATA,
    CMS_TAGS,
    CMS_TYPE,
    FE_BASE_URL,
    FROM_APP,
    FROM_SCREEN_APP,
    IS_MOBILE,
    LINK_CATEGORY_ID,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    LINK_SUB_CATEGORY_ID,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID,
    PageScreen,
    USER_LOGIN,
    CMS_CATEGORY_LIST_DATA_JSON,
    CMS_SUB_CATEGORY_MAP_JSON
} from '../constants';
import {CPSaveLog, getListByPath} from '../util/APIUtils';
import '../common/Common.css';
import {
    appendScript,
    compareDate,
    findCategoryNameByLinkAlias, findCategoryNameBySubCatName,
    findLatestPublicInNewsList,
    formatDate,
    getDeviceId,
    getSession,
    getUrlParameter,
    isMobile,
    postMessageSubNativeIOS,
    pushHistory,
    removeIframe,
    removeScript,
    setSession,
    shortFormatDate, StringToAliasLink,
    trackingEvent
} from '../util/common';
// import ReactHtmlParser from "react-html-parser";
import parse from 'html-react-parser';
import ShareZaloButton from './ShareZaloButton';
import {Swiper, SwiperSlide} from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {Autoplay, Navigation, Pagination} from 'swiper';
import {Helmet} from "react-helmet";
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import {Skeleton} from "antd";

SwiperCore.use([Autoplay, Pagination, Navigation]);

class Article extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: null,
            imageUrl: getSession(CMS_IMAGE_URL),
            relativeData: null,
            latestPublicData: null,
            existIn: 0,
            subCategoryMap: getSession(CMS_SUB_CATEGORY_MAP) ? JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)) : null,
            link: '',
            intervalId: '',
            tagList: null,
            tagUsed: null,
            loading: false,
        };
        this.scrollFunction = this.scrollFunction.bind(this);
        // this.myRef = React.createRef();   // Create a ref object
    }

    showQRC = () => {
        if (isMobile()) {
            if (document.getElementById('popup-DownloadnExperience')) {
                document.getElementById('popup-DownloadnExperience').className = "popup special point-error-popup show";
            }
        } else {
            if (document.getElementById('popup-QRC')) {
                document.getElementById('popup-QRC').className = "popup special point-error-popup show";
            }
        }

    }

    componentDidMount() {
        this.getCmsCategoryList();
        this.topFunction();
        document.addEventListener("scroll", this.scrollFunction, true);
        // setTimeout(this.appendScript, 150);
        appendScript("/js/zalo.sdk.js");
        let display = getUrlParameter("display") ? getUrlParameter("display") : '';
        if (display === 'qr') {
            // setTimeout(this.showQRC, 5000);
            this.showQRC();
        }
        this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_WELLBEING_ARTICLE}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Open_${PageScreen.HEALTH_WELLBEING_ARTICLE}`,
            `Web_Open_${PageScreen.HEALTH_WELLBEING_ARTICLE}`,
        );
    }

    componentWillUnmount() {
        document.removeEventListener("scroll", this.scrollFunction, true);
        removeScript("/js/zalo.sdk.js");
        removeIframe();
        if (this.state.intervalId) {
            clearInterval(this.state.intervalId);
        }

        this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_WELLBEING_ARTICLE}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Close_${PageScreen.HEALTH_WELLBEING_ARTICLE}`,
            `Web_Close_${PageScreen.HEALTH_WELLBEING_ARTICLE}`,
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

    getCmsCategoryList = () => {
        if (!getSession(CMS_CATEGORY_LIST_DATA)) {
            setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
            setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
            this.setState({ subCategoryMap: CMS_SUB_CATEGORY_MAP_JSON });
            setTimeout(this.getArticalDetailByLink, 100);
            // getListByPath(CMS_TYPE)
            //     .then(response => {
            //         if (response.statusCode === 200 && response.statusMessages === 'success') {
            //             setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data))
            //             let subCategoryMap = {};
            //             for (let i = 0; i < response.data.length; i++) {

            //                 getListByPath(CMS_CATEGORY + response.data[i].code)
            //                     .then(res => {
            //                         if (res.statusCode === 200 && res.statusMessages === 'success') {
            //                             subCategoryMap[response.data[i].code] = res.data;
            //                             setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(subCategoryMap));

            //                             this.setState({subCategoryMap: subCategoryMap});

            //                         }
            //                     }).catch(error => {
            //                 });
            //             }
            //             setTimeout(this.getArticalDetailByLink, 100);
            //         }
            //     }).catch(error => {
            //     //show 404
            // });
        } else {
            if (!this.state.subCategoryMap && getSession(CMS_SUB_CATEGORY_MAP)) {
                this.setState({subCategoryMap: JSON.parse(getSession(CMS_SUB_CATEGORY_MAP))});
            }
            setTimeout(this.getArticalDetailByLink, 100);
        }

    }

    getArticalDetailByLink = () => {
        let link = this.props.match.params.link;
        this.setState({
            link: link,
            loading: true,
        });

        getListByPath(CMS_NEWS + CMS_DETAIL_BY_LINK_ONLY + "/" + link)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    this.setState({
                        data: res.data,
                        imageUrl: getSession(CMS_IMAGE_URL),
                        loading: false,
                    });
                    this.getRelativeNews(3, res.data.id, res.data.keyWord, res.data.newsCategoryId);
                    setSession(LINK_MENU_NAME, res.data.newsType);
                    setSession(LINK_MENU_NAME_ID, 'ah4');
                    setSession(LINK_SUB_MENU_NAME, res.data.subCategoryName);
                    setSession(LINK_SUB_MENU_NAME_ID, 'h4');
                    setSession(LINK_CATEGORY_ID, res.data.codeNewsType);
                    setSession(LINK_SUB_CATEGORY_ID, 'sub' + res.data.newsCategoryId);
                    if (res.data.hasEvent) {
                        this.startCountDown(res.data.startDate);
                    }
                    this.getTagList();
                } else {
                    this.setState({
                        loading: false,
                    });
                    this.props.history.push('/404');
                }
            }).catch(error => {
            this.setState({
                loading: false,
            });
            this.props.history.push('/404');
        });

    }

    getTagList = () => {
        if (getSession(CMS_TAG_LIST_DATA)) {
            let tList = JSON.parse(getSession(CMS_TAG_LIST_DATA));
            let tags = tList.filter(tag => {
                return this.state.data.keyWord && (this.state.data.keyWord.split(',').indexOf(tag.linkAlias) >= 0);
            });
            this.setState({tagList: tList, tagUsed: tags});
            return;
        }
        getListByPath(CMS_TAGS + CMS_LIST_ALL_TAGS)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    setSession(CMS_TAG_LIST_DATA, JSON.stringify(res.data));
                    let tags = res.data.filter(tag => {
                        return this.state.data.keyWord && (this.state.data.keyWord.split(',').indexOf(tag.linkAlias) >= 0);
                    });
                    this.setState({tagList: res.data, tagUsed: tags});
                }
            }).catch(error => {

        });

    }

    startCountDown = (date) => {
        if (this.state.intervalId) {
            clearInterval(this.state.intervalId);
        }
        const countDownEle = document.getElementById("countdown-block")
        if (countDownEle) {
            const countDownDate = new Date(date).getTime();
            let intervalId = setInterval(() => {
                // Get today's date and time
                const now = new Date().getTime()

                const distance = countDownDate - now

                const days = Math.floor(distance / (1000 * 60 * 60 * 24))
                const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
                const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60))
                const seconds = Math.floor((distance % (1000 * 60)) / 1000)

                if (distance > 0) {
                    const countWrap = countDownEle.getElementsByClassName('countdown-date-wrap')[0]
                    countWrap.innerHTML = '<div class="countdown-date-item"><span>' + days + '</span><label>Ngày</label></div><div class="countdown-date-item"><span>' + hours + '</span><label>Giờ</label></div><div class="countdown-date-item"><span>' + minutes + '</span><label>Phút</label></div><div class="countdown-date-item"><span>' + seconds + '</span><label>Giây</label></div>'
                }

                // If the count down is finished, write some text
                if (distance <= 0) {
                    clearInterval(intervalId)
                    //countWrap.innerHTML = '<div class="countdown-date-item"><span>00</span><label>Ngày</label></div><div class="countdown-date-item"><span>00</span><label>Giờ</label></div><div class="countdown-date-item"><span>00</span><label>Phút</label></div><div class="countdown-date-item"><span>00</span><label>Giây</label></div>'
                }
            }, 1000);
            this.setState({intervalId: intervalId});

        }
    }

    getArticalDetailByLinkParam = (link) => {
        getListByPath(CMS_NEWS + CMS_DETAIL_BY_LINK + "/" + link)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    this.setState({data: res.data, imageUrl: getSession(CMS_IMAGE_URL)});
                    if (res.data.hasEvent) {
                        this.startCountDown(res.data.startDate);
                    }
                }
            }).catch(error => {
            // console.log(error);
        });


    }

    getRelativeNews = (size, id, keywords, subCategoryId) => {
        if (keywords) {
            getListByPath(CMS_NEWS + CMS_RELATIVE + CMS_PAGE_DEFAULT + "/" + size + "/" + id + "/" + keywords)
                .then(res => {
                    if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                        let jsonState = this.state;
                        jsonState.relativeData = res.data.datas;
                        this.getPublicNews(size * 2 + 1, subCategoryId, jsonState.relativeData);
                        this.setState(jsonState);
                    } else {
                        this.getPublicNews(size * 2 + 1, subCategoryId, this.state.relativeData);
                    }
                }).catch(error => {
                // console.log(error);
            });
        } else {
            this.getPublicNews(size * 2 + 1, subCategoryId, this.state.relativeData);
        }

    }

    getPublicNews = (size, subCategoryId, relativeData) => {
        getListByPath(CMS_NEWS + CMS_LIST + CMS_PAGE_DEFAULT + "/" + size + "/" + subCategoryId)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    let jsonState = this.state;
                    jsonState.latestPublicData = findLatestPublicInNewsList(jsonState.data, res.data.datas, relativeData);
                    this.setState(jsonState);
                }
            }).catch(error => {
            // console.log(error);
        });
    }

    scrollFunction = () => {
        if (!getSession(FROM_APP)) {
            if (document.body.scrollTop > 150 || document.documentElement.scrollTop > 150) {
                document.getElementById("list-social-id").className = "list-social";
            } else {
                document.getElementById("list-social-id").className = "list-social hide";
            }
        }

    }


    topFunction() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }


    render() {
        //Trace history path
        let fromNative = getUrlParameter("from");
        let screen = getUrlParameter("screen");
        let isMobile = getUrlParameter("isMobile");
        if (isMobile && !getSession(IS_MOBILE)) {
            setSession(IS_MOBILE, isMobile);
        }
        if (screen) {
            setSession(FROM_SCREEN_APP, screen);
        }
        if (fromNative) {
            setSession(FROM_APP, fromNative);
            if (screen) {
                pushHistory(fromNative, true);
            } else {
                pushHistory(fromNative);
            }
        } else {
            pushHistory();
        }
        postMessageSubNativeIOS(fromNative);
        let link = this.props.match.params.link;
        if ((this.state.link !== link)) {
            this.getCmsCategoryList();
        }
        const adjustJustify = () => {
            if (document.getElementsByClassName('swiper-wrapper') && document.getElementsByClassName('swiper-wrapper')[0]) {
                document.getElementsByClassName('swiper-wrapper')[0].className = 'swiper-wrapper swiper-justify-left';
            }
        }
        const passSubCategory = (subCategoryId, imageUrl, keyWork, link, id) => {
            this.topFunction();
            this.getArticalDetailByLinkParam(link);
            this.getRelativeNews(3, id, keyWork, subCategoryId);
        }
        var logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }

        const copyToClipBoard = (id) => {
            navigator.clipboard.writeText(window.location.href)
            var tooltip = document.getElementById(id);
            tooltip.innerHTML = "Đã sao chép";
        }

        const outFunc = (id, text) => {
            var tooltip = document.getElementById(id);
            tooltip.innerHTML = text;
        }

        const markericon = () => {
            var iconurl = "../img/icon/10.1/mapOffice.svg";
            return iconurl;
        }

        let itemList = [];
        itemList.push(
            <SwiperSlide className="breadcrums__item">
                <p><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);
        itemList.push(
            <SwiperSlide className="breadcrums__item">
                <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);
        itemList.push(
            <SwiperSlide className="breadcrums__item">
                <p><Link to="/song-vui-khoe/bi-quyet" className='breadcrums__link'>Bí quyết Sống vui khỏe</Link></p>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);
        itemList.push(
            <SwiperSlide className="breadcrums__item">
                <p><Link to={"/song-vui-khoe/bi-quyet/" + this.props.match.params.category}
                         className='breadcrums__link'>{findCategoryNameByLinkAlias(this.props.match.params.category)}</Link>
                </p>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);
        itemList.push(
            <SwiperSlide className="breadcrums__item">
                <Link
                    to={"/song-vui-khoe/bi-quyet/" + this.props.match.params.category + "/" + this.props.match.params.article}>
                    <p>{this.state.data ? this.state.data.subCategoryName : ''}</p></Link>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);
        let props = {
            // spaceBetween: 35,
            // navigation: false,
            className: "breadcrums breadcrums__scroll",
            slidesPerView: 3,
            // style: {justifyContent: 'left'},
            breakpoints: {
                300: {
                    slidesPerView: 2,
                    // spaceBetween: 40
                },
                640: {
                    slidesPerView: 3,
                    // spaceBetween: 40
                },
                768: {
                    slidesPerView: 3,
                    // spaceBetween: 40
                },
                1024: {
                    slidesPerView: 5,
                    // spaceBetween: 40
                }
            }
        };
        const slideToEnd = (swiper) => {
            const ow = window.outerWidth;
            if (ow <= 280) {
                swiper.slideTo(4);
            } else if (ow < 390) {
                swiper.slideTo(3);
            } else if (ow < 512) {
                swiper.slideTo(2);
            } else if (ow < 991) {
                swiper.slideTo(1);
            }

        }
        if (!getSession(FROM_APP)) {
            setTimeout(adjustJustify(), 100);
        }
        let indexFollow = "index, follow";
        if (this.state.data) {
            let index = "noindex";
            if (this.state.data.robotIndexTag) {
                index = "index";
            }
            let follow = "nofollow";
            if (this.state.data.robotFollowTag) {
                follow = "follow";
            }
            indexFollow = index + ", " + follow;
        }
        let paddingTop = '';
        if (getSession(FROM_APP)) {
            paddingTop = ' padding-top-20';
        } else if (getSession(IS_MOBILE)) {
            paddingTop = ' basic-padding-top-0';
        }

        return (
            <>
                {this.state.data &&
                    <Helmet>
                        <title data-react-helmet="true">{this.state.data.titleSeo}</title>
                        <meta name="description" content={this.state.data.metaDescription}/>
                        <meta name="keywords" content={this.state.data.metaKeyword}/>
                        <meta property="og:title" content={this.state.data.titleSeo} data-react-helmet="true"/>
                        <meta property="og:description" content={this.state.data.metaDescription}/>
                        <meta name="robots" content={indexFollow}/>
                        <meta property="og:type" content="article"/>
                        <link rel="canonical" href={this.state.data.canonical}/>
                        <meta property="og:url" content={window.location.href} data-react-helmet="true"/>
                        <meta property="og:image"
                              content={this.state.data.physicalImgUrl ? this.state.data.physicalImgUrl : (FE_BASE_URL + "/img/meta-logo.jpg")}/>
                    </Helmet>
                }

                <main className={logined ? "article-page logined" + paddingTop : "article-page" + paddingTop}>
                    <div className="container">
                            {!getSession(FROM_APP) &&
                                <ul className="list-social hide" id="list-social-id">
                                    <li className="tooltip">
                                        <a href={`https://www.facebook.com/sharer/sharer.php?u=${window.location.href}`}
                                        onMouseOut={() => outFunc('myTooltipFacebook', 'Chia sẻ bài viết lên Facebook')}
                                        target={getSession(FROM_APP) ? '' : '_blank'}><i
                                            className="ico ico-fb-c ico__large"></i></a>
                                        <span className="tooltiptext"
                                            id="myTooltipFacebook">Chia sẻ bài viết lên Facebook</span>
                                    </li>
                                    <li className="tooltip">
                                        <ShareZaloButton clsName={'ico ico-zalo-c ico__large'}
                                                        onMouseOut={() => outFunc('myTooltipZalo', 'Chia sẻ bài viết lên Zalo')}/>
                                        <span className="tooltiptext" id="myTooltipZalo">Chia sẻ bài viết lên Zalo</span>
                                    </li>
                                    <li className="tooltip">
                                        <a href={`mailto:?subject=${this.state.data ? this.state.data.title : ''}&body=${window.location.href}`}
                                        onMouseOut={() => outFunc('myTooltipEmail', 'Chia sẻ bài viết qua email')}><i
                                            className="ico ico-mail-c ico__large"></i></a>
                                        <span className="tooltiptext" id="myTooltipEmail">Chia sẻ bài viết qua email</span>
                                    </li>
                                    <li className="tooltip">
                                        <a onClick={() => copyToClipBoard('myTooltipCopy')}
                                        onMouseOut={() => outFunc('myTooltipCopy', 'Sao chép đường link bài viết')}><i
                                            className="ico ico-link-c ico__large"></i></a>
                                        <span className="tooltiptext" id="myTooltipCopy">Sao chép đường link bài viết</span>
                                    </li>
                                    <li>
                                        {/* <a href="#"><i className="ico ico-bookmark-c ico__large"></i></a> */}
                                    </li>
                                </ul>
                            }
                            {!getSession(FROM_APP) ? (
                                <Swiper {...props} onSwiper={(swiper) => slideToEnd(swiper)}
                                        onReachEnd={(swiper) => slideToEnd(swiper)}>
                                    {itemList}
                                </Swiper>

                            ) : (
                                <div className="breadcrums"></div>
                            )

                            }
                            {/*{this.state.loading && <LoadingIndicatorBasic/>}*/}
                            {this.state.data &&
                                <>
                                    <div className="article-section">
                                        <span className="tooltiptext" id="tooltip-social"></span>
                                        <div className="article-wrap">
                                            <h1 className="article-heading">
                                                {this.state.loading ? (
                                                    <Skeleton loading={this.state.loading} active title={false}/>
                                                ) : (
                                                    this.state.data.title
                                                )}
                                            </h1>
                                            <div className="article-intro">
                                                {!this.state.loading && !isMobile && !getSession(IS_MOBILE) && (
                                                    <span className="article-date">
            <i className="ico ico-calendar"></i>{' '}
                                                        <span
                                                            style={{marginBottom: '5px'}}>{formatDate(this.state.data.postedDate)}</span>
            </span>
                                                )}
                                                {!getSession(FROM_APP) && (
                                                    <ul className="socials">
                                                        <li className="tooltip">
                                                            {this.state.loading ? (
                                                                <Skeleton.Button active={true} size="small" shape="circle"/>
                                                            ) : (
                                                                <a
                                                                    href={`https://www.facebook.com/sharer/sharer.php?u=${window.location.href}`}
                                                                    onMouseOut={() => outFunc('myTooltipFacebook-socials', 'Chia sẻ bài viết lên Facebook')}
                                                                    target={getSession(FROM_APP) ? '' : '_blank'}
                                                                >
                                                                    <i className="ico ico-fb-c"></i>
                                                                </a>
                                                            )}
                                                        </li>
                                                        <li className="tooltip" id="share-zalo-id">
                                                            <ShareZaloButton clsName={'ico ico-zalo-c'}
                                                                            onMouseOut={() => outFunc('myTooltipZalo-socials', 'Chia sẻ bài viết lên Zalo')}/>
                                                            <span className="tooltiptext" id="myTooltipZalo-socials">
                Chia sẻ bài viết lên Zalo
                </span>
                                                        </li>
                                                        <li className="tooltip">
                                                            <a
                                                                href={`mailto:?subject=${this.state.data ? this.state.data.title : ''}&body=${window.location.href}`}
                                                                onMouseOut={() => outFunc('myTooltipEmail-socials', 'Chia sẻ bài viết qua email')}
                                                            >
                                                                <i className="ico ico-mail-c"></i>
                                                            </a>
                                                            <span className="tooltiptext" id="myTooltipEmail-socials">
                Chia sẻ bài viết qua email
                </span>
                                                        </li>
                                                        <li className="tooltip">
                                                            <a onClick={() => copyToClipBoard('myTooltip-socials')}
                                                            onMouseOut={() => outFunc('myTooltip-socials', 'Sao chép đường link')}>
                                                                <i className="ico ico-link-c"></i>
                                                            </a>
                                                            <span className="tooltiptext" id="myTooltip-socials">
                Sao chép đường link
                </span>
                                                        </li>
                                                        <li></li>
                                                    </ul>
                                                )}
                                            </div>
                                            <div className="article-content">
                                                {this.state.loading ? (
                                                    <>
                                                        {/* Display skeleton for countdown block */}
                                                        {/*<Skeleton active/>*/}
                                                        {/* Display skeleton for content string */}
                                                        <Skeleton active paragraph={{rows: 8}}/>
                                                        {/* Display skeleton for other content */}
                                                        {/*<Skeleton active/>*/}
                                                    </>
                                                ) : (
                                                    <>
                                                        {this.state.data.hasEvent && (
                                                            <div className="countdown-block" id="countdown-block">
                                                                <div className="countdown-content">
                                                                    <div className="countdown-info">
                                                                        <div className="countdown-info-item">
                    <span>
                        <i className="ico ico-calendar-event"></i>
                    </span>
                                                                            {this.state.data.endDate && compareDate(this.state.data.startDate, this.state.data.endDate) > 0 ? (
                                                                                <p className="font-size-event">
                                                                                    Ngày sự kiện:{' '}
                                                                                    <span className="font-size-event">
                            {shortFormatDate(this.state.data.startDate)} - {formatDate(this.state.data.endDate)}
                        </span>
                                                                                </p>
                                                                            ) : (
                                                                                <p className="font-size-event">
                                                                                    Ngày sự
                                                                                    kiện: <span>{formatDate(this.state.data.startDate)}</span>
                                                                                </p>
                                                                            )}
                                                                        </div>
                                                                        <div className="countdown-info-item">
                    <span>
                        <i className="ico ico-location-event"></i>
                    </span>
                                                                            <p className="font-size-event">
                                                                                Địa điểm: <span
                                                                                className="font-size-event">{this.state.data.eventLocation}</span>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                    {new Date(this.state.data.startDate).getTime() - new Date().getTime() > 0 && (
                                                                        <div className="countdown-date-sec">
                                                                            <h4 className="countdown-heading">Sự kiện diễn
                                                                                ra sau:</h4>
                                                                            <div className="countdown-date-wrap">
                                                                                <div className="countdown-date-item">
                                                                                    <span>00</span>
                                                                                    <label>Ngày</label>
                                                                                </div>
                                                                                <div className="countdown-date-item">
                                                                                    <span>00</span>
                                                                                    <label>Giờ</label>
                                                                                </div>
                                                                                <div className="countdown-date-item">
                                                                                    <span>00</span>
                                                                                    <label>Phút</label>
                                                                                </div>
                                                                                <div className="countdown-date-item">
                                                                                    <span>00</span>
                                                                                    <label>Giây</label>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    )}
                                                                </div>
                                                            </div>
                                                        )}
                                                        {parse(this.state.data.contentString ? (this.state.data.contentString.replaceAll(CMS_BASE_URL_NO_LOAD, CMS_BASE_URL_REPLACE)).replaceAll('<table', '<table className=\"table\"') : this.state.data.contentString, { trim: true })}

                                                        {/* Display article content if not loading */}
                                                        {this.state.data && this.state.data.hasEvent && (
                                                            <>
                                                                <p className="title" style={{fontWeight: '700'}}>
                                                                    Địa điểm
                                                                </p>
                                                                {this.state.data.eventMap && parse(this.state.data.eventMap.replaceAll('width="600"', 'width="100%"'))}
                                                                <div className="padding-bottom-17"></div>
                                                            </>
                                                        )}
                                                    </>
                                                )}
                                            </div>

                                            {!this.state.loading && this.state.tagUsed && (this.state.tagUsed.length > 0) && !isMobile && !getSession(IS_MOBILE) &&
                                                <div className="article-tags">
                                                    <h5>Thẻ nội dung</h5>
                                                    <div style={{marginLeft: '-12px'}}>
                                                        {this.state.tagUsed.map((it, idx) => {
                                                            return (
                                                                <Link to={"/tags/" + it.linkAlias}
                                                                    className='tag'>{it.tagsName}</Link>
                                                            )
                                                        })}
                                                    </div>
                                                </div>
                                            }
                                            {/* Author Section */}
                                            <div class="article-author-block" style={{borderTop: '0', marginTop: '0'}}>
                                            </div>
                                        </div>

                                        {/* <!-- SideBar Section --> */}
                                        <div className="sidebar-article">
                                            <div className="article_temp_cont">
                                            {/* Skeleton for related articles */}
                                            <div className="sidebar-widget">
                                                {!this.state.loading && this.state.relativeData &&
                                                    this.state.relativeData.map((item, index) => {
                                                        let categoryName = findCategoryNameBySubCatName(item.typeName);
                                                        let subPath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(categoryName) + '/' + StringToAliasLink(item.typeName);
                                                        let path = subPath + '/' + item.linkAlias;
                                                        if (index < this.state.relativeData.length) {
                                                            return (
                                                                <div className="article-item"
                                                                    key={'listing-relative-' + index}>
                                                                    {index === 0 &&
                                                                        <Link to={path}
                                                                            onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                            className="article-thumb">
                                                                            <img src={item.physicalImgUrl} alt=""
                                                                                className=""/>
                                                                        </Link>
                                                                    }

                                                                    <div className="article-item-right">
                                                                        <Link to={subPath}
                                                                            onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                            className="article-cate-link">{item.typeName}</Link>
                                                                        <h4><Link to={path}
                                                                                onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                                className="article-title">{item.title}</Link>
                                                                        </h4>
                                                                        <div className="article-nav">
                                                                            {item.hasEvent ? (
                                                                                <span
                                                                                    className="article-nav-item margin-bottom-event">
                                            <div className='flex-event'><i
                                                className="ico ico-event-location"></i> <span>{item.eventLocation ? item.eventLocation : ''}</span></div>
                                            <div className='flex-event-second-right'>
                                                <i className="ico ico-event-date margin-left-event"></i>
                                                {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                                    <span>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>
                                                ) : (
                                                    <span>{item.startDate ? formatDate(item.startDate) : ''}</span>
                                                )}
                                            </div>
                                            </span>
                                                                            ) : (
                                                                                <span
                                                                                    className="article-nav-item margin-bottom-not-event">
                                            <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                            </span>
                                                                            )}
                                                                            <a href="#"
                                                                            className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            )

                                                        }
                                                    })
                                                }

                                                {this.state.loading && (
                                                    <>
                                                        <h3 className="sidebar-widget-heading">Bài viết liên quan</h3>
                                                        <div className="listing-v">
                                                            {/* Skeleton for each related article item */}
                                                            {[1, 2, 3].map((index) => (
                                                                <Skeleton key={`skeleton-related-${index}`} loading active
                                                                        avatar paragraph={{rows: 2}}>
                                                                    <div className="article-item">
                                                                        <Link to="/" className="article-thumb">
                                                                            <Skeleton.Avatar size={80} shape="square"
                                                                                            active/>
                                                                        </Link>
                                                                        <div className="article-item-right">
                                                                            <Link to="/" className="article-cate-link">
                                                                                <Skeleton active paragraph={{
                                                                                    rows: 1,
                                                                                    width: '50%'
                                                                                }}/>
                                                                            </Link>
                                                                            <h4>
                                                                                <Link to="/" className="article-title">
                                                                                    <Skeleton active paragraph={{
                                                                                        rows: 1,
                                                                                        width: '80%'
                                                                                    }}/>
                                                                                </Link>
                                                                            </h4>
                                                                            <div className="article-nav">
                                                                                <Skeleton active paragraph={{
                                                                                    rows: 1,
                                                                                    width: '50%'
                                                                                }}/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </Skeleton>
                                                            ))}
                                                        </div>
                                                    </>
                                                )}
                                            </div>

                                            {/* Skeleton for latest articles */}
                                            <div className="sidebar-widget">
                                                {!this.state.loading && this.state.latestPublicData &&
                                                    this.state.latestPublicData.map((item, index) => {
                                                        let categoryName = findCategoryNameBySubCatName(item.typeName);
                                                        let subPath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(categoryName) + '/' + StringToAliasLink(item.typeName);
                                                        let path = subPath + '/' + item.linkAlias;
                                                        if (index < this.state.latestPublicData.length) {
                                                            return (
                                                                <div className="article-item"
                                                                    key={'listing-last-public-' + index}>
                                                                    {index === 0 &&
                                                                        <Link to={path}
                                                                            onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                            className="article-thumb">
                                                                            <img src={item.physicalImgUrl} alt=""
                                                                                className=""/>
                                                                        </Link>
                                                                    }

                                                                    <div className="article-item-right">
                                                                        <Link to={subPath}
                                                                            onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                            className="article-cate-link">{item.typeName}</Link>
                                                                        <h4><Link to={path}
                                                                                onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord, item.linkAlias, item.id)}
                                                                                className="article-title">{item.title}</Link>
                                                                        </h4>
                                                                        <div className="article-nav">
                                                                            {item.hasEvent ? (
                                                                                <span
                                                                                    className="article-nav-item margin-bottom-event">
                                            <div className='flex-event'><i
                                                className="ico ico-event-location"></i> <span>{item.eventLocation ? item.eventLocation : ''}</span></div>
                                            <div className='flex-event-second'>
                                                <i className="ico ico-event-date margin-left-event"></i>
                                                {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                                    <span>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>
                                                ) : (
                                                    <span>{item.startDate ? formatDate(item.startDate) : ''}</span>
                                                )}
                                            </div>
                                            </span>
                                                                            ) : (
                                                                                <span
                                                                                    className="article-nav-item margin-bottom-not-event">
                                            <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                            </span>
                                                                            )}
                                                                            <a href="#"
                                                                            className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            )

                                                        }
                                                    })
                                                }
                                                {this.state.loading && (
                                                    <>
                                                        <h3 className="sidebar-widget-heading">Bài viết mới</h3>
                                                        <div className="listing-v">
                                                            {/* Skeleton for each latest article item */}
                                                            {[1, 2, 3].map((index) => (
                                                                <Skeleton key={`skeleton-latest-${index}`} loading active
                                                                        avatar paragraph={{rows: 2}}>
                                                                    <div className="article-item">
                                                                        <Link to="/" className="article-thumb">
                                                                            <Skeleton.Avatar size={80} shape="square"
                                                                                            active/>
                                                                        </Link>
                                                                        <div className="article-item-right">
                                                                            <Link to="/" className="article-cate-link">
                                                                                <Skeleton active paragraph={{
                                                                                    rows: 1,
                                                                                    width: '50%'
                                                                                }}/>
                                                                            </Link>
                                                                            <h4>
                                                                                <Link to="/" className="article-title">
                                                                                    <Skeleton active paragraph={{
                                                                                        rows: 1,
                                                                                        width: '80%'
                                                                                    }}/>
                                                                                </Link>
                                                                            </h4>
                                                                            <div className="article-nav">
                                                                                <Skeleton active paragraph={{
                                                                                    rows: 1,
                                                                                    width: '50%'
                                                                                }}/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </Skeleton>
                                                            ))}
                                                        </div>
                                                    </>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </>
                        }
                    </div>
                    {getSession(FROM_APP) && getSession(FROM_APP) === "IOS" &&
                        <div className='padding-bottom-60'></div>
                    }
                </main>
            </>


        )
    }

}

export default Article;