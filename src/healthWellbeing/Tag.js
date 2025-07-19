import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { CMS_TAG_IMAGES, ACCESS_TOKEN, CMS_PAGE_DEFAULT, CMS_NEWS, CMS_SUB_CATEGORY_ID, CMS_IMAGE_URL, CMS_KEYWORK, CMS_CATEGORY_CODE, FROM_APP, LINK_MENU_NAME, LINK_MENU_NAME_ID, LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID, API_NEWS_LIST_ALL_HOTS, FROM_SCREEN_APP, FE_BASE_URL, API_NEWS_LIST_BY_KEYWORK, CMS_HW_SEARCH_SIZE, CMS_HW_PAGE_SIZE, API_NEWS_LIST_BY_TAG, CMS_TAG_LIST_DATA, CMS_TAGS, CMS_LIST_ALL_TAGS } from '../constants';
import { getListByPath } from '../util/APIUtils';
import { formatDate, StringToAliasLink, findCategoryNameBySubCatName, findItemInNewsList, shortFormatDate, findCategoryLinkAliasByCode, findCategoryBySubCategoryId, pushHistory, getUrlParameter, setSession, getSession, postMessageSubNativeIOS, compareDate, isMobile, iOS } from '../util/common';
// import { showMessage } from '../util/common';
import { Swiper, SwiperSlide } from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {
  Autoplay, Pagination, Navigation
} from 'swiper';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Helmet } from "react-helmet";

SwiperCore.use([Autoplay, Pagination, Navigation]);
let lastHotIndex = 0;
class Tag extends Component {
  constructor(props) {
    super(props);
    this.state = {
      datas: null,
      totalData: 0,
      subCategoryName: '',
      page: CMS_PAGE_DEFAULT,
      loading: false,
      tagFilter: null
    }
  }

  getNewsByTagRefresh = (page, pageSize) => {
    this.setState({ loading: true });
    let linkAlias = this.props.match.params.alias;
    getListByPath(CMS_NEWS + API_NEWS_LIST_BY_TAG + linkAlias + "/" + page + "/" + pageSize)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          this.setState({ datas: res.data.datas, totalData: res.data.totalData, loading: false, page: page });
          this.getTagList();
        }
      }).catch(error => {
        this.setState({ loading: false });
      });
  }

  getNewsByTag = (page, pageSize) => {
    this.setState({ loading: true });
    let linkAlias = this.props.match.params.alias;
    getListByPath(CMS_NEWS + API_NEWS_LIST_BY_TAG + linkAlias + "/" + page + "/" + pageSize)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          if (!this.state.datas) {
            this.setState({ datas: res.data.datas, totalData: res.data.totalData, loading: false, page: page });
          } else {
            this.setState({ datas: this.state.datas.concat(res.data.datas), totalData: res.data.totalData, loading: false, page: page });
          }

        }
      }).catch(error => {
        this.setState({ loading: false });
      });
  }

  componentDidMount() {
    // window.prerenderReady = false;
    //highlight the active menu
    setSession(LINK_MENU_NAME, 'Sống vui khoẻ');
    setSession(LINK_MENU_NAME_ID, 'ah4');
    setSession(LINK_SUB_MENU_NAME, 'Thẻ nội dung');
    setSession(LINK_SUB_MENU_NAME_ID, 'h5');
    this.getNewsByTagRefresh(CMS_PAGE_DEFAULT, CMS_HW_SEARCH_SIZE);
    this.topFunction();

    let fromNative = getUrlParameter("from");
    postMessageSubNativeIOS(fromNative);
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }

  getTagList = () => {
    if (getSession(CMS_TAG_LIST_DATA)) {
      let tList = JSON.parse(getSession(CMS_TAG_LIST_DATA));
      let linkAlias = this.props.match.params.alias;
      let tags = tList.filter(tag => {
        return linkAlias && (linkAlias === tag.linkAlias);
      });
      this.setState({ tagList: tList, tagFilter: tags[0] });
      return;
    }
    getListByPath(CMS_TAGS + CMS_LIST_ALL_TAGS)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          setSession(CMS_TAG_LIST_DATA, JSON.stringify(res.data));
          let linkAlias = this.props.match.params.alias;
          let tags = res.data.filter(tag => {
            return linkAlias && (linkAlias === tag.linkAlias);
          });
          this.setState({ tagList: res.data, tagFilter: tags[0] });
        }
      }).catch(error => {

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

    const cacheCategoryCode = (categoryCode) => {
      setSession(CMS_CATEGORY_CODE, categoryCode);
    }

    const loadMore = () => {
      if (this.state.page * CMS_HW_SEARCH_SIZE < this.state.totalData) {
        this.getNewsByTag(this.state.page + 1, CMS_HW_SEARCH_SIZE);
      }
    }

    let logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    let clsName = logined ? "category-page logined" : "category-page";
    if (getSession(FROM_APP)) {
      clsName = clsName + ' ' + 'from-app-search-padding-top';
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
        <p className='breadcrums__item_arrow'>></p>
      </SwiperSlide>);
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
        <p className='breadcrums__item_arrow'>></p>
      </SwiperSlide>);
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p>Bí quyết sống vui khỏe</p>
        <p className='breadcrums__item_arrow'>></p>
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
    let indexFollow = "index, follow";
    let imgUrl = FE_BASE_URL + "/img/meta-logo.jpg";
    if (this.state.tagFilter) {
      let index = "noindex";
      if (this.state.tagFilter.robotIndex) {
        index = "index";
      }
      let follow = "nofollow";
      if (this.state.tagFilter.robotFollow) {
        follow = "follow";
      }
      indexFollow = index + ", " + follow;

      if (CMS_TAG_IMAGES[this.state.tagFilter.linkAlias]) {
        if (this.state.tagFilter.linkAlias === 'cung-duong-yeu-thuong') {
          imgUrl = CMS_TAG_IMAGES[this.state.tagFilter.linkAlias];
        } else {
          imgUrl = 'https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=news//' + CMS_TAG_IMAGES[this.state.tagFilter.linkAlias];
        }
      }
    }
    
    return (
      <>
        {this.state.datas && this.state.tagFilter &&
          <Helmet>
            <title> {this.state.tagFilter.tagsName + " – Dai-ichi Life Việt Nam"} </title>
            <meta name="description" content={this.state.tagFilter.metaDescription} />
            <meta name="keywords" content={this.state.tagFilter.keyWord} />
            <meta name="robots" content={indexFollow} />
            <meta property="og:type" content="article" />
            <meta property="og:url" content={window.location.href} data-react-helmet="true" />
            <meta property="og:title" content={this.state.tagFilter.tagsName + " – Dai-ichi Life Việt Nam"} />
            <meta property="og:description" content={this.state.tagFilter.metaDescription} />
            <meta property="og:image" content={imgUrl} data-react-helmet="true"/>
            <link rel="canonical" href={this.state.tagFilter.canonical} data-react-helmet="true"/>
          </Helmet>
        }
        <main className={clsName} id="scrollableDiv">
          <div className="container">
            {/* <!-- Breadcrums --> */}
            {!getSession(FROM_APP) &&
              <Swiper {...propsBreadcrums} onSwiper={(swiper) => slideToEnd(swiper)} onReachEnd={(swiper) => slideReachEnd(swiper)}>
                {itemListBreadcrums}
              </Swiper>
            }
            {/* <!-- End Breadcrums --> */}
            <div className="tab-section">
              {!getSession(FROM_APP) ? (
                <h1 className="tag-super-heading">Thẻ nội dung</h1>
              ) : (
                <h1 className="tag-super-heading" style={{ marginTop: '43px' }}></h1>
              )
              }
              {this.state.tagFilter && (
                <h1 className="tag-heading">{this.state.tagFilter.tagsName}</h1>
              )}
              {this.state.totalData > 0 && (

                <div className="listing-v tag-listing">
                  {this.state.datas &&
                    this.state.datas.map((item, index) => {
                      return (







                        <div className="article-item article-item__inline">
                          <Link to={item.articlePathName} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-thumb">
                            <img src={item.physicalImgUrl} alt="" />
                          </Link>
                          <div className="article-item-right">
                            <h4><Link to={item.articlePathName} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-title" alt={item.title}>{item.title}</Link></h4>
                            {!getSession(FROM_APP) &&
                              <p className={isMobile() ? "article-item-excerpt hide" : "article-item-excerpt"}>{item.metaDescription}</p>
                            }
                            <div className="article-nav article-nav__start">
                              {item.hasEvent ? (
                                <span className="article-nav-item margin-bottom-event">
                                  <div className='flex-event'><i className="ico ico-event-location"></i> <span>{item.eventLocation ? item.eventLocation : ''}</span></div>
                                  <div className='flex-event-second-right-search'>
                                    <i className="ico ico-event-date margin-left-event"></i>
                                    {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                      <span>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>
                                    ) : (
                                      <span>{item.startDate ? formatDate(item.startDate) : ''}</span>
                                    )}
                                  </div>
                                </span>
                              ) : (
                                <span className="article-nav-item margin-bottom-not-event">
                                  <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                </span>
                              )}
                              <a href="#" className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>                          </div>
                          </div>
                        </div>





                      )
                    })}
                </div>


              )}
              <LoadingIndicator area="request-loading-area" />
              <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>
                {(this.state.page * CMS_HW_SEARCH_SIZE < this.state.totalData) &&
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

export default Tag;