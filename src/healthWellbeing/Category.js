import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { CMS_TYPE, CMS_CATEGORY_LIST_DATA, CMS_CATEGORY, CMS_SUB_CATEGORY_MAP, ACCESS_TOKEN, CMS_PAGE_DEFAULT, CMS_HW_PAGE_TIP_SIZE, CMS_NEWS, CMS_LIST, CMS_SUB_CATEGORY_ID, CMS_IMAGE_URL, CMS_KEYWORK, MENU_NAME, CMS_CATEGORY_CODE, CMS_CATEGORY_CODE_SET, FROM_APP, LINK_MENU_NAME, LINK_MENU_NAME_ID, LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID, LINK_CATEGORY_ID, API_NEWS_LIST_ALL_HOTS_BY_CATEGORY,CMS_CATEGORY_LIST_DATA_JSON,
CMS_SUB_CATEGORY_MAP_JSON } from '../constants';
import { getListByPath } from '../util/APIUtils';
import { formatDate, StringToAliasLink, findCategoryNameBySubCatName, findItemInNewsList, shortFormatDate, findSubCategoryIdInSubCategoryList, findCategoryLinkAliasByCode, findCategoryCodeByLinkAlias, findCategoryByCode, findCategoryNameByLinkAlias, pushHistory, getUrlParameter, findAliasLinkInCategoryList, setSession, getSession, postMessageSubNativeIOS, compareDate } from '../util/common';
// import { showMessage } from '../util/common';
import { Swiper, SwiperSlide } from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {
  Autoplay, Pagination, Navigation
} from 'swiper';
import LoadingIndicator from '../common/LoadingIndicator3';
import { Helmet } from "react-helmet";

const FIRST_GET_PAGE_SIZE = 16;
SwiperCore.use([Autoplay, Pagination, Navigation]);
class Category extends Component {
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
      category: '',
      subCategoryMap: JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)),
      loading: false,
      noMore: false
    }
  }

  getCmsCategoryList = () => {
    if (!getSession(CMS_CATEGORY_LIST_DATA)) {
      setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
      setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
      this.setState({subCategoryMap: CMS_SUB_CATEGORY_MAP_JSON});
      //highlight the active menu
      let category = this.props.match.params.category;
      let info = findAliasLinkInCategoryList(category, CMS_CATEGORY_LIST_DATA_JSON);
      if (info) {
        setSession(LINK_MENU_NAME, info[0]);
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, info[0]);
        setSession(LINK_SUB_MENU_NAME_ID, 'h4');
        setSession(LINK_CATEGORY_ID, info[1]);
        this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE);
      } else {
        this.props.history.push('/404');
      }
      // getListByPath(CMS_TYPE)
      //   .then(response => {
      //     if (response.statusCode === 200 && response.statusMessages === 'success') {
      //       setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data));
      //       //highlight the active menu
      //       let category = this.props.match.params.category;
      //       let info = findAliasLinkInCategoryList(category, response.data);
      //       if (info) {
      //         setSession(LINK_MENU_NAME, info[0]);
      //         setSession(LINK_MENU_NAME_ID, 'ah4');
      //         setSession(LINK_SUB_MENU_NAME, info[0]);
      //         setSession(LINK_SUB_MENU_NAME_ID, 'h4');
      //         setSession(LINK_CATEGORY_ID, info[1]);
      //         let subCategoryMap = {};
      //         for (let i = 0; i < response.data.length; i++) {

      //           getListByPath(CMS_CATEGORY + response.data[i].code)
      //             .then(res => {
      //               if (res.statusCode === 200 && res.statusMessages === 'success') {
      //                 subCategoryMap[response.data[i].code] = res.data;
      //                 setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(subCategoryMap));
      //               }
      //             }).catch(error => {
      //             });
      //         }
      //         this.setState({ subCategoryMap: subCategoryMap });
      //         this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE);
      //       } else {
      //         this.props.history.push('/404');
      //       }

      //     }
      //   }).catch(error => {
      //     //show 404
      //   });
    } else {
      //highlight the active menu
      let category = this.props.match.params.category;
      let info = findAliasLinkInCategoryList(category);
      if (info) {
        setSession(LINK_MENU_NAME, info[0]);
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, info[0]);
        setSession(LINK_SUB_MENU_NAME_ID, 'h4');
        setSession(LINK_CATEGORY_ID, info[1]);
        this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE);
      } else {
        this.props.history.push('/404');
      }

    }

  }

  getNews = (page, pageSize) => {
    let category = this.props.match.params.category;
    let categoryCode = findCategoryCodeByLinkAlias(category);
    let jsonState = this.state;
    jsonState.category = category;
    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY_CATEGORY + page + "/" + pageSize + "/" + categoryCode)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          jsonState.totalData = res.data.totalData;

          if (!jsonState.selectedItem) {
            let hots = res.data.hotNews;
            if (hots && (hots.length >= 1)) {
              jsonState.selectedItem = hots[0];
              jsonState.categoryName = jsonState.selectedItem.newsType;
              jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
            }

            if (hots && (hots.length >= 2) && jsonState.selectedItem) {
              let copy = hots;
              // copy.splice(0, 1);
              // hots = copy;
              let n = 5;
              if (hots.length < n) {
                n = hots.length;
              }
              hots = copy.slice(1, n);
            }

            jsonState.hotNews = hots;

          }

          if (res.data.datas && res.data.datas.length === 0) {
            jsonState.noMore = true;
          }
          jsonState.page = page;
          if (!jsonState.datas) {
            jsonState.datas = res.data.datas;
          } else {
            if (res.data.datas.length > 0) {
              jsonState.datas = jsonState.datas.concat(res.data.datas);
            }

          }

          this.setState(jsonState);
        }
      }).catch(error => {
        // console.log(error);
      });
  }

  getNewsRefresh = (page, pageSize) => {
    let category = this.props.match.params.category;
    let categoryCode = findCategoryCodeByLinkAlias(category);
    let jsonState = this.state;
    jsonState.category = category;
    jsonState.loading = true;
    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY_CATEGORY + page + "/" + pageSize + "/" + categoryCode)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          jsonState.totalData = res.data.totalData;

          let hots = res.data.hotNews;

          if (hots && (hots.length >= 1)) {
            jsonState.selectedItem = hots[0];
            jsonState.categoryName = jsonState.selectedItem.newsType;
            jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
          }

          if (hots && (hots.length >= 2) && jsonState.selectedItem) {
            let copy = hots;
            // copy.splice(0, 1);
            // hots = copy;
            let n = 5;
            if (hots.length < n) {
              n = hots.length;
            }
            hots = copy.slice(1, n);
          }
          jsonState.hotNews = hots;

          this.state.page = page;
          jsonState.datas = res.data.datas;

          jsonState.loading = false;
          this.setState(jsonState);
        }
      }).catch(error => {
        jsonState.loading = false;
        this.setState(jsonState);
      });

  }


  componentDidMount() {
    this.getCmsCategoryList();
    this.topFunction();
    //Trace history path
    let fromNative = getUrlParameter("from");
    if (fromNative) {
      pushHistory(fromNative);
    } else {
      pushHistory();
    }
    postMessageSubNativeIOS(fromNative);
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }

  render() {
    let category = this.props.match.params.category;
    if ((this.state.category !== category) && !this.state.loading) {
      this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE);
    }
    const passSubCategory = (subCategoryId, imageUrl, keyWork) => {
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
      setSession(CMS_IMAGE_URL, imageUrl);
      setSession(CMS_KEYWORK, keyWork);
    }
    const loadMore = () => {
      if (this.state.page * FIRST_GET_PAGE_SIZE < this.state.totalData) {
        this.getNews(this.state.page + 1, CMS_HW_PAGE_TIP_SIZE);
      }

    }

    const cacheSubCategoryId = (linkAlias, code) => {
      let subCategoryId = findSubCategoryIdInSubCategoryList(linkAlias, code);
      setSession(CMS_CATEGORY_CODE, code);
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
    }
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    let x = 0;

    // let categoryCode = getSession(CMS_CATEGORY_CODE);
    // if (!categoryCode) {
    let categoryCode = findCategoryCodeByLinkAlias(this.props.match.params.category);
    // }
    let selectedSubPath = '';
    let selectedPath = '';
    let basePath = '/song-vui-khoe/bi-quyet/';
    if (this.state.selectedItem) {
      selectedSubPath = basePath + this.props.match.params.category + '/' + StringToAliasLink(this.state.selectedItem.newsCategory ? this.state.selectedItem.newsCategory : this.state.selectedItem.typeName);
      selectedPath = selectedSubPath + '/' + this.state.selectedItem.linkAlias;
    }

    let categoryName = findCategoryNameByLinkAlias(this.props.match.params.category);
    let clsName = logined ? "category-page logined" : "category-page";
    if (getSession(FROM_APP)) {
      clsName = clsName + ' ' + 'from-app-padding-top';
    }


    //icons swiper
    let itemList = [];
    let swipLength = 2;
    let ogTitle = "";
    let description = "";
    let ogDescription = "";
    let keywords = "";
    let ogImage = "";
    if (categoryCode === CMS_CATEGORY_CODE_SET.MEDICINE) {
      ogTitle = "Bệnh và thuốc – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về bệnh và thuốc, giúp khách hàng hiểu hơn về bệnh thường gặp và những loại thuốc hỗ trợ điều trị hiệu quả.";
      ogDescription = description;
      keywords = "bệnh, bệnh thường gặp, thuốc, thực phẩm chức năng";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL2_1667991005693.jpg";
      itemList.push(
        <SwiperSlide>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.MEDICINE) + "/" + StringToAliasLink('Bệnh thường gặp')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bệnh thường gặp'), CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-sick ico__large"></i></span>
            <h2 className="nav-label">Bệnh <br /> thường gặp</h2>
          </Link>
        </SwiperSlide>);
      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.MEDICINE) + "/" + StringToAliasLink('Thuốc & Thực phẩm chức năng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Thuốc & Thực phẩm chức năng'), CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-drug ico__large"></i></span>
            <h2 className="nav-label">Thuốc & <br /> Thực phẩm chức năng</h2>
          </Link>
        </SwiperSlide>);

    } else if (categoryCode === CMS_CATEGORY_CODE_SET.FOODS) {
      ogTitle = "Thực phẩm và dinh dưỡng – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về những loại thực phẩm và dinh dưỡng có lợi cho sức khỏe.";
      ogDescription = description;
      keywords = "thực phẩm, thực phẩm lành mạnh, dinh dưỡng, bữa ăn đủ chất dinh dưỡng";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL13_1667991007312.jpg";
      itemList.push(
        <SwiperSlide>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.FOODS) + "/" + StringToAliasLink('Thực phẩm')} onClick={() => cacheSubCategoryId(StringToAliasLink('Thực phẩm'), CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-food ico__large"></i></span>
            <h2 className="nav-label">Thực phẩm</h2>
          </Link>
        </SwiperSlide>);
      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.FOODS) + "/" + StringToAliasLink('Dinh dưỡng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Dinh dưỡng'), CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-nutrition ico__large"></i></span>
            <h2 className="nav-label">Dinh dưỡng</h2>
          </Link>
        </SwiperSlide>);
    } else if (categoryCode === CMS_CATEGORY_CODE_SET.HABIT) {
      ogTitle = "Thói quen sống khỏe – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về sức khỏe thể chất và tinh thần, giúp khách hàng xây dựng thói quen sống khỏe mỗi ngày.";
      ogDescription = description;
      keywords = "thói quen sống khỏe, thói quen lành mạnh, sức khỏe tinh thần, sức khỏe thể chất";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL14_1667991007608.jpg";
      itemList.push(
        <SwiperSlide>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.HABIT) + "/" + StringToAliasLink('Sức khỏe thể chất')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sức khỏe thể chất'), CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
            <span className="nav-icon" style={{ padding: '5px' }}><i className="ico ico-health ico__large" style={{ width: '50px', height: '50px' }}></i></span>
            <h2 className="nav-label">Sức khỏe <br /> thể chất</h2>
          </Link>
        </SwiperSlide>);
      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>

          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.HABIT) + "/" + StringToAliasLink('Sức khỏe tinh thần')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sức khỏe tinh thần'), CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-morale ico__large"></i></span>
            <h2 className="nav-label">Sức khỏe <br /> tinh thần</h2>
          </Link>
        </SwiperSlide>);
    } else if (categoryCode === CMS_CATEGORY_CODE_SET.INVESTMENT) {
      ogTitle = "Bảo hiểm và đầu tư tích lũy – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về bảo hiểm và đầu tư tích tích lũy, giúp khách hàng bảo vệ sức khỏe thể chất và tài chính an toàn.";
      ogDescription = description;
      keywords = "bảo hiểm, đầu tư, tích lũy, đầu tư và tích lũy.";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL15_1667991008332.jpg";
      itemList.push(
        <SwiperSlide>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.INVESTMENT) + "/" + StringToAliasLink('Bảo hiểm')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bảo hiểm'), CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-insurance ico__large"></i></span>
            <h2 className="nav-label">Bảo hiểm</h2>
          </Link>
        </SwiperSlide>);

      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.INVESTMENT) + "/" + StringToAliasLink('Đầu tư & Tích lũy')} onClick={() => cacheSubCategoryId(StringToAliasLink('Đầu tư & Tích lũy'), CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-waller ico__large"></i></span>
            <h2 className="nav-label">Đầu tư & <br /> Tích lũy</h2>
          </Link>
        </SwiperSlide>);
    } else if (categoryCode === CMS_CATEGORY_CODE_SET.NEWS) {
      ogTitle = "Tin tức và sự kiện – Dai-ichi Life Việt Nam";
      description = "Trang thông tin cập nhật các tin tức và sự kiện mới, các hoạt động vì cộng đồng, các thành tựu đạt được của Dai-ichi Life Việt Nam.";
      ogDescription = description;
      keywords = "tin tức và sự kiện, tin tức, sự kiện, tin tức mới, sự kiện mới, dai-ichi life việt nam";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL16_1667991009133.jpg";
      itemList.push(
        <SwiperSlide>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Tin tức')} onClick={() => cacheSubCategoryId(StringToAliasLink('Tin tức'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-news ico__large"></i></span>
            <h2 className="nav-label">Tin tức</h2>
          </Link>
        </SwiperSlide>);
      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Sự kiện')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sự kiện'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
            <span className="nav-icon"><i className="ico ico-event ico__large"></i></span>
            <h2 className="nav-label">Sự kiện</h2>
          </Link>
        </SwiperSlide>);
      itemList.push(
        <SwiperSlide className='margin-swiper-slice-category-two-right'>
          <Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Bản tin khách hàng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bản tin khách hàng'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
            <span className="nav-icon" style={{ padding: '10px' }}><i className="ico ico-customer-news ico__large" style={{ width: '35px', height: '35px' }}></i></span>
            <h2 className="nav-label">Bản tin <br /> khách hàng</h2>
          </Link>
        </SwiperSlide>);
      swipLength = 3;
    }

    let props = {
      // spaceBetween: 100,
      // navigation: false,
      className: "nav-cate mySwiper",
      slidesPerView: swipLength,
      breakpoints: {
        270: {
          slidesPerView: swipLength,
        },
        340: {
          slidesPerView: swipLength,
          // spaceBetween: 100
        },
        640: {
          slidesPerView: swipLength,
          // spaceBetween: 100
        },
        768: {
          slidesPerView: swipLength,
          // spaceBetween: 100
        },
        1024: {
          slidesPerView: swipLength,
          // spaceBetween: 100
        }
      }
    };

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
        <p><Link to="/song-vui-khoe/bi-quyet" className='breadcrums__link'>Bí quyết Sống vui khỏe</Link></p>
        <p className='breadcrums__item_arrow'>></p>
      </SwiperSlide>);
    itemListBreadcrums.push(
      <SwiperSlide className="breadcrums__item">
        <p>{categoryName}</p>
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
        },
        1024: {
          slidesPerView: 4,
          // spaceBetween: 40
        }
      }
    };
    const slideToEnd = (swiper) => {
      var ow = window.outerWidth;
      if (ow <= 280) {
        swiper.slideTo(3);
      } else if (ow < 512) {
        swiper.slideTo(1);
      } else {
        swiper.slideTo(0);
      }

    }
    if (!getSession(FROM_APP)) {
      setTimeout(adjustJustify(), 100);
    }

    return (

      // <main className={logined ? "logined" : ""} id="scrollableDiv" style={getSession(FROM_APP) ? { height: '800px', overflow: "auto", paddingTop: '240px' } : { height: '800px', overflow: "auto", paddingBottom:'50px' }}>
      <main className={clsName} id="scrollableDiv">
        {this.state.datas &&
          <Helmet>
            <title data-react-helmet="true">{ogTitle}</title>
            <meta name="description" content={description} />
            <meta name="keywords" content={keywords} />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={window.location.href} />
            <meta property="og:title" content={ogTitle} />
            <meta property="og:description" content={ogDescription} />
            <meta property="og:image" content={ogImage} />
            <link rel="canonical" href={window.location.href} />
          </Helmet>
        }
        <div className="container">
          {/* <!-- Breadcrums --> */}
          {!getSession(FROM_APP) &&
            // <div className="breadcrums breadcrums__scroll">
            //   <div className="breadcrums__item">
            //     <p><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            //   <div className="breadcrums__item">
            //     <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            //   <div className="breadcrums__item">
            //     <p><Link to="/song-vui-khoe/bi-quyet" className='breadcrums__link'>Bí quyết Sống vui khỏe</Link></p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            //   <div className="breadcrums__item">
            //     <p>{categoryName}</p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            // </div>
            <Swiper {...propsBreadcrums} onSwiper={(swiper) => slideToEnd(swiper)} onReachEnd={(swiper) => slideToEnd(swiper)}>
              {itemListBreadcrums}
            </Swiper>
          }
          {/* <!-- End Breadcrums --> */}
          {!getSession(FROM_APP) &&
            <div className="swiper-wrapper">
              <Swiper {...props} >
                {itemList}
              </Swiper>
            </div>
          }

          <div className="cate-section">
            <div className="cate-heading">
              <h1 className="cate-heading-title">{categoryName}</h1>
              {/* <a href="#" className="cate-heading-view">Xem tất cả</a> */}
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
                    let subPath = '/song-vui-khoe/bi-quyet/' + this.props.match.params.category + '/' + StringToAliasLink(item.newsCategory);
                    let path = subPath + '/' + item.linkAlias;
                    return (
                      <div className="article-item article-item__inline article-item__border_mobile" key={'hot-news-' + index}>
                        <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-thumb">
                          <img src={item.physicalImgUrl} alt="" />
                        </Link>
                        <div className="article-item-right">
                          <Link to={subPath} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-cate-link">{item.newsCategory}</Link>
                          <p><Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}>{item.title}</Link></p>
                          <div className="article-nav">
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
            <div className="listing-gird">
              {/* <InfiniteScroll
                dataLength={this.state.lengthShow}
                next={() => loadMore()}
                hasMore={this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData}
                loader={<LoadingIndicator area="request-loading-area" />}
                scrollableTarget="scrollableDiv"
                className="listing-gird"
              > */}
              {this.state.datas &&
                this.state.datas.map((item, index) => {
                  // if ((index < this.state.datas.length + x - this.state.hotNews.length) && (index < this.state.page * CMS_HW_PAGE_TIP_SIZE)) {
                  if (findItemInNewsList(item, this.state.hotNews) || (this.state.selectedItem.id === item.id)) {
                    x++;
                  } else {
                    let categoryName = findCategoryNameBySubCatName(item.typeName);
                    let subPath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(categoryName) + '/' + StringToAliasLink(item.typeName);
                    let path = subPath + '/' + item.linkAlias;
                    return (
                      <div className="article-item article-item__border" key={'news-artical-' + index}>
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

                  // }

                })
              }
              {/* </InfiniteScroll> */}

            </div>
            <LoadingIndicator area="request-loading-area" />
            <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>
              {((this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData) && !this.state.noMore) &&
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

    )
  }

}

export default Category;