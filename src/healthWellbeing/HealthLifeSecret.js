import React, { Component } from 'react';
import { Link } from "react-router-dom";
import {
  CMS_TYPE,
  CMS_CATEGORY_LIST_DATA,
  CMS_CATEGORY,
  CMS_SUB_CATEGORY_MAP,
  ACCESS_TOKEN,
  CMS_PAGE_DEFAULT,
  CMS_HW_PAGE_TIP_SIZE,
  CMS_NEWS,
  CMS_LIST,
  CMS_SUB_CATEGORY_ID,
  CMS_IMAGE_URL,
  CMS_KEYWORK,
  PAGE_RUN,
  CMS_CATEGORY_CODE_SET,
  CMS_CATEGORY_CODE,
  FROM_APP,
  LINK_MENU_NAME,
  LINK_MENU_NAME_ID,
  LINK_SUB_MENU_NAME,
  LINK_SUB_MENU_NAME_ID,
  API_NEWS_LIST_ALL_HOTS_BY,
  FROM_SCREEN_APP,
  FE_BASE_URL,
  AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen,
  CMS_CATEGORY_LIST_DATA_JSON,
  CMS_SUB_CATEGORY_MAP_JSON
} from '../constants';
import {CPSaveLog, getListByPath} from '../util/APIUtils';
import {
  formatDate,
  StringToAliasLink,
  findCategoryNameBySubCatName,
  findItemInNewsList,
  shortFormatDate,
  findCategoryLinkAliasByCode,
  findCategoryBySubCategoryId,
  pushHistory,
  getUrlParameter,
  setSession,
  getSession,
  postMessageSubNativeIOS,
  compareDate,
  getDeviceId, trackingEvent
} from '../util/common';
// import { showMessage } from '../util/common';
import { Swiper, SwiperSlide } from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {
  Autoplay, Pagination, Navigation
} from 'swiper';
import LoadingIndicator from '../common/LoadingIndicator3';
import { Helmet } from "react-helmet";

const FIRST_GET_PAGE_SIZE = 16;
const HOT_SIZE = 5;
SwiperCore.use([Autoplay, Pagination, Navigation]);
let lastHotIndex = 0;
class HealthLifeSecret extends Component {
  constructor(props) {
    super(props);
    this.state = {
      toggle: false,
      totalData: 0,
      hotNews: null,
      datas: null,
      selectedItem: null,
      categoryName: '',
      subCategoryName: '',
      page: CMS_PAGE_DEFAULT,
      lengthShow: 0,
      subCategoryMap: JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)),
      categoryListData: null
      // cmsCategoryListData: getSession(CMS_CATEGORY_LIST_DATA)? JSON.parse(getSession(CMS_CATEGORY_LIST_DATA)) : null
    }
  }

  getCmsCategoryList = () => {
    if (!getSession(CMS_CATEGORY_LIST_DATA)) {
      setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
      setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
      this.setState({ subCategoryMap: CMS_SUB_CATEGORY_MAP_JSON, categoryListData: CMS_CATEGORY_LIST_DATA_JSON });
      // getListByPath(CMS_TYPE)
      //   .then(response => {
      //     if (response.statusCode === 200 && response.statusMessages === 'success') {
      //       setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data))
      //       let subCategoryMap = {};
      //       for (let i = 0; i < response.data.length; i++) {

      //         getListByPath(CMS_CATEGORY + response.data[i].code)
      //           .then(res => {
      //             if (res.statusCode === 200 && res.statusMessages === 'success') {
      //               subCategoryMap[response.data[i].code] = res.data;
      //               setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(subCategoryMap));

      //               // this.setState({ toggle: !this.state.toggle });

      //             }
      //           }).catch(error => {
      //           });
      //       }
      //       this.setState({ subCategoryMap: subCategoryMap, categoryListData: response.data });
      //       console.log('subCategoryMap=', subCategoryMap);
      //     }
      //   }).catch(error => {
      //     //show 404
      //   });
    }

  }

  getNews = (page, pageSize, hotSize) => {
    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY + page + "/" + pageSize + "/" + hotSize)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          let jsonState = this.state;
          jsonState.totalData = res.data.totalData;
          jsonState.hotNews = res.data.hotNews;

          if (jsonState.hotNews && (jsonState.hotNews.length >= 1) && !jsonState.selectedItem) {
            jsonState.selectedItem = res.data.hotNews[0];
            jsonState.categoryName = jsonState.selectedItem.newsType;
            jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
          }

          this.state.page = page;
          if (!jsonState.datas) {
            jsonState.datas = res.data.datas;
          } else {
            jsonState.datas = jsonState.datas.concat(res.data.datas);
          }

          this.setState(jsonState);
        }
      }).catch(error => {
        // console.log(error);
      });
  }

  getNewsRefresh = (page, pageSize, hotSize) => {
    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY + page + "/" + pageSize + "/" + hotSize)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          let jsonState = this.state;
          jsonState.totalData = res.data.totalData;
          jsonState.hotNews = res.data.hotNews;

          if (jsonState.hotNews && (jsonState.hotNews.length >= 1) && !jsonState.selectedItem) {
            jsonState.selectedItem = res.data.hotNews[0];
            jsonState.categoryName = jsonState.selectedItem.newsType;
            jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
          }

          this.state.page = page;
          if (!jsonState.datas) {
            jsonState.datas = res.data.datas;
          } else if (res.data.datas) {
            jsonState.datas = jsonState.datas.concat(res.data.datas);
          }

          this.setState(jsonState);
          // this.prerenderReady();
        }
      }).catch(error => {
        // console.log(error);
      });
  }

  componentDidMount() {
    // window.prerenderReady = false;
    //highlight the active menu
    setSession(LINK_MENU_NAME, 'Sống vui khoẻ');
    setSession(LINK_MENU_NAME_ID, 'ah4');
    setSession(LINK_SUB_MENU_NAME, 'Bí quyết Sống vui khỏe');
    setSession(LINK_SUB_MENU_NAME_ID, 'h4');
    this.getCmsCategoryList();
    this.topFunction();
    this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE, HOT_SIZE);
    this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_CARD}`);
    trackingEvent(
        "Bí quyết Sống vui khỏe",
        `Web_Open_${PageScreen.HEALTH_CARD}`,
        `Web_Open_${PageScreen.HEALTH_CARD}`,
    );
    let fromNative = getUrlParameter("from");
    postMessageSubNativeIOS(fromNative);
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_CARD}`);
    trackingEvent(
        "Bí quyết Sống vui khỏe",
        `Web_Close_${PageScreen.HEALTH_CARD}`,
        `Web_Close_${PageScreen.HEALTH_CARD}`,
    );
  }

  prerenderReady() {
    window.prerenderReady = true;
  }
  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
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
    //Trace history path
    let fromNative = getUrlParameter("from");
    let screen = getUrlParameter("screen");
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
    const passSubCategory = (subCategoryId, imageUrl, keyWork) => {
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
      setSession(CMS_IMAGE_URL, imageUrl);
      setSession(CMS_KEYWORK, keyWork);
      let categoryName = findCategoryBySubCategoryId(subCategoryId);
      if (categoryName) {
        cacheCategoryCode(categoryName);
      }
    }
    const loadMore = () => {
      if (this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData) {
        this.getNews(this.state.page + 1, CMS_HW_PAGE_TIP_SIZE, HOT_SIZE);
      } /*else {
        let total = this.state.datas.length - this.state.hotNews.length;
        if (total > this.state.totalData) {
          total =this.state.totalData - 1;
        }
        this.setState({lengthShow: total});
      }*/

    }

    const cacheCategoryCode = (categoryCode) => {
      setSession(CMS_CATEGORY_CODE, categoryCode);
    }

    let x = 0;
    let selectedSubPath = '';
    let selectedPath = '';
    let basePath = '/song-vui-khoe/bi-quyet/';
    if (this.state.selectedItem) {
      selectedSubPath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(this.state.selectedItem.newsType) + '/' + StringToAliasLink(this.state.selectedItem.newsCategory);
      selectedPath = selectedSubPath + '/' + this.state.selectedItem.linkAlias;
    }

    let itemList = [];
    itemList.push(
      <SwiperSlide className='margin-swiper-slice-left'>
        <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.MEDICINE, this.state.categoryListData)} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
          <span className="nav-icon"><i className="ico ico-sick-and-drug ico__large"></i></span>
          <h2 className="nav-label">Bệnh và <br /> thuốc</h2>
        </Link>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide>
        <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.FOODS, this.state.categoryListData)} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
          <span className="nav-icon"><i className="ico ico-food-and-nutrition ico__large"></i></span>
          <h2 className="nav-label">Thực phẩm và <br /> dinh dưỡng</h2>
        </Link>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide>
        <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.HABIT, this.state.categoryListData)} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
          <span className="nav-icon"><i className="ico ico-habit ico__large"></i></span>
          <h2 className="nav-label">Thói quen <br /> sống khỏe</h2>
        </Link>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide>
        <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.INVESTMENT, this.state.categoryListData)} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
          <span className="nav-icon"><i className="ico ico-insurance-and-save ico__large"></i></span>
          <h2 className="nav-label">Bảo hiểm và <br /> Đầu tư tích&nbsp;lũy</h2>
        </Link>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide className='margin-swiper-slice-right'>
        <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS, this.state.categoryListData)} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
          <span className="nav-icon"><i className="ico ico-news-and-event ico__large"></i></span>
          <h2 className="nav-label">Tin tức và <br /> Sự kiện</h2>
        </Link>
      </SwiperSlide>);
    // let props = {
    //   // spaceBetween: 40,
    //   // navigation: false,
    //   className: "nav-cate mySwiper",
    //   slidesPerView: 3,
    //   breakpoints: {
    //     640: {
    //       slidesPerView: 3,
    //       // spaceBetween: 40
    //     },
    //     768: {
    //       slidesPerView: 3,
    //       // spaceBetween: 40
    //     },
    //     1024: {
    //       slidesPerView: 5,
    //       // spaceBetween: 40
    //     }
    //   }
    // };
    let props = {
      // spaceBetween: 40,
      // navigation: false,
      className: "nav-cate mySwiper",
      slidesPerView: 3,
      scrollbar: {
        draggable: true,
        dragSize: 19.2
      },
      // centeredSlides: true,
      breakpoints: {
        270: {
          slidesPerView: 2,
          // spaceBetween: 40
        },
        300: {
          slidesPerView: 2.7,
          // spaceBetween: 40
        },
        320: {
          slidesPerView: 2.8,
          // spaceBetween: 40
        },
        340: {
          slidesPerView: 3,
          spaceBetween: 10
        },
        360: {
          slidesPerView: 3,
          spaceBetween: 10
        },
        372: {
          slidesPerView: 3.2,
          spaceBetween: 10
        },
        388: {
          slidesPerView: 3.3,
          spaceBetween: 10
        },
        412: {
          slidesPerView: 3.5,
          spaceBetween: 10
        },
        500: {
          slidesPerView: 4,
          spaceBetween: 10
        },
        550: {
          slidesPerView: 4.5,
          spaceBetween: 10
        },
        600: {
          slidesPerView: 5,
          spaceBetween: 10
        },
        768: {
          slidesPerView: 5,
          // spaceBetween: 40
        },
        1024: {
          slidesPerView: 5,
          // spaceBetween: 40
        }
      }
    };

    let logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    let clsName = logined ? "category-page logined" : "category-page";
    if (getSession(FROM_APP)) {
      clsName = clsName + ' ' + 'from-app-padding-top';
    }

    const adjustJustify = () => {
      if (document.getElementsByClassName('swiper-wrapper') && document.getElementsByClassName('swiper-wrapper')[0]) {
        document.getElementsByClassName('swiper-wrapper')[0].className = 'swiper-wrapper swiper-justify-left';
      }
    }

    //breadcrums
    let itemListBreadcrums = [];
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p>Bí quyết Sống vui khỏe</p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);

    let propsBreadcrums = {
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
        }
      }
    };
    const slideToEnd = (swiper) => {
      var ow = window.outerWidth;
      if (ow <= 280) {
        swiper.slideTo(2);
      } else {
        swiper.slideTo(0);
      }

    }

    const slideReachEnd = (swiper) => {
      var ow = window.outerWidth;
      if (ow <= 280) {
        swiper.slideTo(2);
      } else if (ow < 412) {
        swiper.slideTo(2);
      } else {
        swiper.slideTo(0);
      }

    }
    if (!getSession(FROM_APP)) {
      setTimeout(adjustJustify(), 100);
    }

    return (
      <>
        {this.state.datas &&
          <Helmet>
            <title> Bí quyết Sống vui khỏe – Dai-ichi Life Việt Nam </title>
            <meta name="description" content="Trang thông tin về bí quyết sống vui khỏe của Dai-ichi Life Việt Nam, cung cấp kiến thức về sức khỏe thông qua các bài viết hữu ích." />
            <meta name="keywords" content="bí quyết sống vui, bí quyết sống khỏe, bí quyết sống vui khỏe, sống vui sống khỏe" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/song-vui-khoe/bi-quyet"} />
            <meta property="og:title" content="Bí quyết Sống vui khỏe – Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin về bí quyết sống vui khỏe của Dai-ichi Life Việt Nam, cung cấp kiến thức về sức khỏe thông qua các bài viết hữu ích." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40MicrosoftTeamsimage98_1667381529594.png" />
            {/* <meta property="og:image:width" content="1200"/>
            <meta property="og:image:height" content="628" /> */}
            <link rel="canonical" href={FE_BASE_URL + "/song-vui-khoe/bi-quyet"} />
          </Helmet>
        }
        <main className={clsName} id="scrollableDiv">
          <div className="container">
            {/* <!-- Breadcrums --> */}
            {!getSession(FROM_APP) &&
              // <div className="breadcrums breadcrums__scroll">
              //   <div className="breadcrums__item">
              //     <p>Trang chủ</p>
              //     <p className='breadcrums__item_arrow'>></p>
              //   </div>
              //   <div className="breadcrums__item">
              //     <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
              //     <p className='breadcrums__item_arrow'>></p>
              //   </div>
              //   <div className="breadcrums__item">
              //     <p>Bí quyết Sống vui khỏe</p>
              //     <p className='breadcrums__item_arrow'>></p>
              //   </div>
              // </div>
              <Swiper {...propsBreadcrums} onSwiper={(swiper) => slideToEnd(swiper)} onReachEnd={(swiper) => slideReachEnd(swiper)}>
                {itemListBreadcrums}
              </Swiper>
            }
            {/* <!-- End Breadcrums --> */}

            {!getSession(FROM_APP) &&
              <div className="hls_temp_cont">
                <div className="swiper-wrapper">
                  <Swiper {...props} >
                    {itemList}
                  </Swiper>
                </div>
              </div>
            }

            <div className="cate-section">
              <div className="cate-heading">
                <h1 className="cate-heading-title">Bí quyết Sống vui khỏe</h1>
              </div>
              {this.state.selectedItem &&
                <div className="cate-featured">
                  <div className="article-item">
                    <Link to={selectedPath} onClick={() => passSubCategory(this.state.selectedItem.newsCategoryId, this.state.selectedItem.physicalImgUrl, this.state.selectedItem.keyWord)} className="article-thumb">
                      <img src={this.state.selectedItem.physicalImgUrl} alt="" />
                    </Link>
                    <div className="article-item-right">
                      <Link to={selectedSubPath} onClick={() => passSubCategory(this.state.selectedItem.newsCategoryId, this.state.selectedItem.physicalImgUrl, this.state.selectedItem.keyWord)} className="article-cate-link">{this.state.subCategoryName}</Link>
                      <p><Link to={selectedPath} onClick={() => passSubCategory(this.state.selectedItem.newsCategoryId, this.state.selectedItem.physicalImgUrl, this.state.selectedItem.keyWord)} className="article-title-0" alt={this.state.selectedItem.title}>{this.state.selectedItem.title}</Link></p>
                      <div className="article-nav">
                        {this.state.selectedItem.hasEvent ? (
                          <span className="article-nav-item" style={{ marginBottom: '2px', marginTop: '-2px' }}>
                            <i className="ico ico-event-location"></i> <span>{this.state.selectedItem.eventLocation ? this.state.selectedItem.eventLocation : ''}</span>
                            <i className="ico ico-event-date ico-margin-left"></i>
                            {this.state.selectedItem.endDate && (compareDate(this.state.selectedItem.startDate, this.state.selectedItem.endDate) > 0) ? (
                              <span>{this.state.selectedItem.startDate ? shortFormatDate(this.state.selectedItem.startDate) : ''}{' - ' + formatDate(this.state.selectedItem.endDate)}</span>
                            ) : (
                              <span>{this.state.selectedItem.startDate ? formatDate(this.state.selectedItem.startDate) : ''}</span>
                            )}

                          </span>
                        ) : (
                          <span className="article-nav-item adjust-last-icon-event">
                            <i className="ico ico-calendar"></i> <span>{formatDate(this.state.selectedItem.postedDate)}</span>
                          </span>
                        )}
                        <a href="#" className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                      </div>
                    </div>
                  </div>
                </div>
              }

              <div className="cate-featured-right">
                <div className="listing-v">
                  {this.state.hotNews &&
                    this.state.hotNews.map((item, index) => {
                      if (this.state.selectedItem.id !== item.id) {
                      lastHotIndex = index;
                      let path = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(item.newsType) + '/' + StringToAliasLink(item.newsCategory) + '/' + item.linkAlias;
                      return (
                        <div className="article-item article-item__inline article-item__border_mobile">
                          <Link className="article-thumb" to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}>
                            <img src={item.physicalImgUrl} alt="" />
                          </Link>
                          <div className="article-item-right">
                            <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-cate-link">{item.newsCategory}</Link>
                            <p><Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}>{item.title}</Link></p>
                            <div className="article-nav" style={{height: "20px"}}>
                              {item.hasEvent ? (
                                <span className="article-nav-item margin-top-event">
                                  <div className='flex-event'><i className="ico ico-event-location"></i> <span>{item.eventLocation ? item.eventLocation : ''}</span></div>
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
                                <span className="article-nav-item margin-top-not-event">
                                  <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                </span>
                              )}
                              <a href="#" className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
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
            <div className="cate-section">
              {this.state.datas && (this.state.datas.length > 0) &&
                <div className="cate-heading">
                  <h3 className="cate-heading-title">Bài viết mới</h3>
                </div>
              }

              {/* <InfiniteScroll
              dataLength={this.state.lengthShow}
              next={() => loadMore()}
              hasMore={this.state.page * FIRST_GET_PAGE_SIZE < this.state.totalData}
              loader={<LoadingIndicator area="request-loading-area" />}
              scrollableTarget="scrollableDiv"
              className="listing-gird"
            > */}
              <div className="listing-gird">
                {this.state.datas &&
                  this.state.datas.map((item, idx) => {
                    if (idx < this.state.page * CMS_HW_PAGE_TIP_SIZE) {
                      let categoryName = findCategoryNameBySubCatName(item.typeName);
                      let subPath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(categoryName) + '/' + StringToAliasLink(item.typeName);
                      let path = subPath + '/' + item.linkAlias;
                      return (
                        <div className="article-item article-item__border">
                          <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-thumb">
                            <img src={item.physicalImgUrl} alt="" />
                          </Link>
                          <div className="article-item-right">
                            <Link to={subPath} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-cate-link">{item.typeName}</Link>
                            <p><Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className={item.hasEvent ? "article-title line-clamp-event" : "article-title"} alt={item.title}>{item.title}</Link></p>
                            <div className="article-nav">
                              {item.hasEvent ? (
                                <span className="article-nav-item grid-event" style={{ marginBottom: '2px', marginTop: '-2px' }}>
                                  <i className="ico ico-event-location"></i> <span className='margin-event'>{item.eventLocation ? item.eventLocation : ''}</span>
                                  <i className="ico ico-event-date ico-margin"></i>
                                  {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                    <span className='margin-event'>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>
                                  ) : (
                                    <span className='margin-event'>{item.startDate ? formatDate(item.startDate) : ''}</span>
                                  )}

                                </span>
                              ) : (
                                <span className="article-nav-item adjust-last-icon-event">
                                  <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                </span>
                              )}
                              <a href="#" className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                            </div>
                          </div>
                        </div>

                      )
                    }


                  })
                }
              </div>
              {/* </InfiniteScroll> */}


              <LoadingIndicator area="request-loading-area" />
              <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>
                {(this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData) &&
                  <div style={{ paddingLeft: '10px', paddingTop: '36px', display: 'flex' }}>
                    {/* <p className='simple-brown2 closebtn' onClick={() => loadMore()}>Xem thêm</p> */}
                    <p className="simple-brown2 closebtn" onClick={() => loadMore()}>Xem thêm&nbsp;</p><p className="feeDetailsArrowDown" style={{ padding: '8px 10px 20px 0px' }}><img src="../../img/icon/arrow-down-bronw.svg" alt="arrow-down-icon" /></p>

                  </div>
                }
              </div>
            </div>
          </div>
          {getSession(FROM_APP) && getSession(FROM_APP) === "IOS" &&
            <div className='padding-bottom-30'></div>
          }
        </main>
      </>


    )
  }

}

export default HealthLifeSecret;