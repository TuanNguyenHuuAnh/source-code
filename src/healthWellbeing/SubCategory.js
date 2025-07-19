import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { CMS_TYPE, CMS_CATEGORY_LIST_DATA, CMS_CATEGORY, CMS_SUB_CATEGORY_MAP, ACCESS_TOKEN, CMS_PAGE_DEFAULT, CMS_HW_PAGE_TIP_SIZE, CMS_NEWS, CMS_LIST, CMS_SUB_CATEGORY_ID, CMS_IMAGE_URL, CMS_KEYWORK, MENU_NAME, SUB_MENU_NAME, CMS_CATEGORY_CODE, FROM_APP, API_NEWS_LIST_ALL_HOTS, LINK_MENU_NAME, LINK_MENU_NAME_ID, LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID, LINK_CATEGORY_ID, LINK_SUB_CATEGORY_ID, CMS_APP_ALIASLINK_SUB_CATEGORY_NAME_MAP, FE_BASE_URL,CMS_CATEGORY_LIST_DATA_JSON,
CMS_SUB_CATEGORY_MAP_JSON } from '../constants';
import { getListByPath } from '../util/APIUtils';
import { formatDate, StringToAliasLink, findCategoryNameBySubCatName, findItemInNewsList, shortFormatDate, findSubCategoryIdInSubCategoryList, findCategoryLinkAliasByCode, findAliasLinkInCategoryList, findCategoryCodeByLinkAlias, findCategoryNameByLinkAlias, findSubCategoryNameInSubCategoryList, pushHistory, getUrlParameter, findAliasLinkInSubCategoryList, setSession, getSession, postMessageSubNativeIOS, compareDate } from '../util/common';
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
class SubCategory extends Component {
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
      article: '',
      subCategoryId: '',
      subCategoryMap: JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)),
      noMore: false
    }
  }

  getCmsCategoryList = () => {
    if (!getSession(CMS_CATEGORY_LIST_DATA)) {
      setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
      setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
      // for (let i = 0; i < CMS_CATEGORY_LIST_DATA_JSON.length; i++) {
      //   setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
      //   this.setState({ subCategoryMap: CMS_SUB_CATEGORY_MAP_JSON });

      //   if (i === CMS_CATEGORY_LIST_DATA_JSON.length - 1) {
      //     //highlight the active menu
      //     let category = this.props.match.params.category;
      //     let article = this.props.match.params.article;
      //     let info = findAliasLinkInCategoryList(category, CMS_CATEGORY_LIST_DATA_JSON);
      //     if (info) {
      //       if (info !== null) {
      //         let subInfo = findAliasLinkInSubCategoryList(article, info[1], CMS_SUB_CATEGORY_MAP_JSON)
      //         if (subInfo !== null) {
      //           setSession(LINK_MENU_NAME, info[0]);
      //           setSession(LINK_MENU_NAME_ID, 'ah4');
      //           setSession(LINK_SUB_MENU_NAME, subInfo[0]);
      //           setSession(LINK_SUB_MENU_NAME_ID, 'h4');
      //           setSession(LINK_CATEGORY_ID, info[1]);
      //           setSession(LINK_SUB_CATEGORY_ID, 'sub' + subInfo[1]);
      //         } else {
      //           this.props.history.push('/404');
      //         }

      //       }
      //       setTimeout(this.getNewList, 100);
      //     } else {
      //       this.props.history.push('/404');
      //     }

      //   }
      // }



      // getListByPath(CMS_TYPE)
      //   .then(response => {
      //     if (response.statusCode === 200 && response.statusMessages === 'success') {
      //       setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data));
      //       let subCategoryMap = {};
      //       for (let i = 0; i < response.data.length; i++) {

      //         getListByPath(CMS_CATEGORY + response.data[i].code)
      //           .then(res => {
      //             if (res.statusCode === 200 && res.statusMessages === 'success') {
      //               subCategoryMap[response.data[i].code] = res.data;
      //               setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(subCategoryMap));
      //               this.setState({ subCategoryMap: subCategoryMap });

      //               if (i === response.data.length - 1) {
      //                 //highlight the active menu
      //                 let category = this.props.match.params.category;
      //                 let article = this.props.match.params.article;
      //                 let info = findAliasLinkInCategoryList(category, response.data);
      //                 if (info) {
      //                   if (info !== null) {
      //                     let subInfo = findAliasLinkInSubCategoryList(article, info[1], subCategoryMap)
      //                     if (subInfo !== null) {
      //                       setSession(LINK_MENU_NAME, info[0]);
      //                       setSession(LINK_MENU_NAME_ID, 'ah4');
      //                       setSession(LINK_SUB_MENU_NAME, subInfo[0]);
      //                       setSession(LINK_SUB_MENU_NAME_ID, 'h4');
      //                       setSession(LINK_CATEGORY_ID, info[1]);
      //                       setSession(LINK_SUB_CATEGORY_ID, 'sub' + subInfo[1]);
      //                     } else {
      //                       this.props.history.push('/404');
      //                     }

      //                   }
      //                   setTimeout(this.getNewList, 100);
      //                 } else {
      //                   this.props.history.push('/404');
      //                 }

      //               }
      //             }
      //           }).catch(error => {
      //           });
      //       }


      //     }
      //   }).catch(error => {
      //     //show 404
      //   });
    } else {
      //highlight the active menu
      let category = this.props.match.params.category;
      let article = this.props.match.params.article;
      let info = findAliasLinkInCategoryList(category);
      if (info) {
        let subInfo = findAliasLinkInSubCategoryList(article, info[1])
        if (subInfo !== null) {
          setSession(LINK_MENU_NAME, info[0]);
          setSession(LINK_MENU_NAME_ID, 'ah4');
          setSession(LINK_SUB_MENU_NAME, subInfo[0]);
          setSession(LINK_SUB_MENU_NAME_ID, 'h4');
          setSession(LINK_CATEGORY_ID, info[1]);
          setSession(LINK_SUB_CATEGORY_ID, 'sub' + subInfo[1]);
        } else {
          this.props.history.push('/404');
        }

      } else {
        this.props.history.push('/404');
      }
      let subCategoryId = null;
      if (getSession(CMS_SUB_CATEGORY_ID)) {
        subCategoryId = getSession(CMS_SUB_CATEGORY_ID);
      } else {
        let article = this.props.match.params.article;
        let category = this.props.match.params.category;

        let categoryCode = '';
        let result = findAliasLinkInCategoryList(category);
        if (result) {
          categoryCode = result[1];
        }
        subCategoryId = findSubCategoryIdInSubCategoryList(article, categoryCode, this.state.subCategoryMap);
      }

      this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE, subCategoryId);

    }

  }

  getNewList = () => {
    let article = this.props.match.params.article;
    let category = this.props.match.params.category;

    let categoryCode = '';
    let result = findAliasLinkInCategoryList(category);
    if (result) {
      categoryCode = result[1];
    }
    let subCategoryId = findSubCategoryIdInSubCategoryList(article, categoryCode, this.state.subCategoryMap);
    this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE, subCategoryId);
  }
  getNews = (page, pageSize, subCategoryId) => {

    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS + page + "/" + pageSize + "/" + subCategoryId)
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

          if (jsonState.hotNews && (jsonState.hotNews.length >= 2) && jsonState.selectedItem) {
            let copy = jsonState.hotNews;
            let n = 5;
            if (copy.length < n) {
              n = copy.length;
            }
            jsonState.hotNews = copy.slice(1, n);
          }
          if (res.data.datas && res.data.datas.length === 0) {
            jsonState.noMore = true;
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
        // alert(JSON.stringify(error));
        // console.log(error);
      });
  }

  getNewsRefresh = (page, pageSize, subCategoryId) => {
    let article = this.props.match.params.article;
    getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS + page + "/" + pageSize + "/" + subCategoryId)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          let jsonState = this.state;
          jsonState.totalData = res.data.totalData;
          jsonState.hotNews = res.data.hotNews;
          jsonState.selectedItem = null;
          if (jsonState.hotNews && (jsonState.hotNews.length >= 1)) {
            jsonState.selectedItem = res.data.hotNews[0];
            jsonState.categoryName = jsonState.selectedItem.newsType;
            jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
          }

          if (jsonState.hotNews && (jsonState.hotNews.length >= 2) && jsonState.selectedItem) {
            let copy = jsonState.hotNews;
            let n = 5;
            if (copy.length < n) {
              n = copy.length;
            }
            jsonState.hotNews = copy.slice(1, n);
          }
          jsonState.page = page;

          jsonState.datas = res.data.datas;
          if (!jsonState.selectedItem && res.data.datas > 0) {
            jsonState.selectedItem = res.data.datas[0];
            jsonState.categoryName = jsonState.selectedItem.newsType;
            jsonState.subCategoryName = jsonState.selectedItem.newsCategory;
          }
          jsonState.article = article;
          if (!jsonState.selectedItem) {
            jsonState.subCategoryName = CMS_APP_ALIASLINK_SUB_CATEGORY_NAME_MAP[article];
          }
          this.setState(jsonState);
        }
      }).catch(error => {
        // console.log(error);
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
    let article = this.props.match.params.article;
    if (this.state.subCategoryMap && (this.state.article !== article)) {
      this.setState({ article: article });
      let subCategoryId = getSession(CMS_SUB_CATEGORY_ID);
      this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE, subCategoryId);
    }

    const passSubCategory = (subCategoryId, imageUrl, keyWork) => {
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
      setSession(CMS_IMAGE_URL, imageUrl);
      setSession(CMS_KEYWORK, keyWork);
      this.getNewsRefresh(CMS_PAGE_DEFAULT, FIRST_GET_PAGE_SIZE, this.props.match.params.article);
    }
    const loadMore = () => {
      if (this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData) {
        this.getNews(this.state.page + 1, CMS_HW_PAGE_TIP_SIZE, getSession(CMS_SUB_CATEGORY_ID));
      } /*else {
          let total = this.state.datas.length - this.state.hotNews.length;
          if (total > this.state.totalData) {
            total =this.state.totalData - 1;
          }
          this.setState({lengthShow: total});
        }*/

    }
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    let x = 0;
    let categoryCode = getSession(CMS_CATEGORY_CODE);
    let selectedSubPath = '';
    let selectedPath = '';
    let basePath = '/song-vui-khoe/bi-quyet/';
    if (this.state.selectedItem) {
      selectedSubPath = basePath + findCategoryLinkAliasByCode(categoryCode) + '/' + article;
      selectedPath = selectedSubPath + "/" + this.state.selectedItem.linkAlias;
    }
    // let subCategoryName = '';
    // if (this.state.subCategoryMap) {
    //   subCategoryName = findSubCategoryNameInSubCategoryList(this.props.match.params.article, findCategoryCodeByLinkAlias(this.props.match.params.category), this.state.subCategoryMap);
    // }
    let ogTitle = "";
    let description = "";
    let ogDescription = "";
    let keywords = "";
    let ogImage = "";
    if (this.state.subCategoryName === 'Bệnh thường gặp') {
      ogTitle = "Bệnh thường gặp – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về về các bệnh thường gặp, nguyên nhân và các phương pháp điều trị, phòng ngừa hiệu quả.";
      ogDescription = description;
      keywords = "bệnh thường gặp, các loại bệnh, ung thư, tiểu đường, sốt xuất huyết";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL3_1667991005716.jpg";
    } else if (this.state.subCategoryName === 'Thuốc & Thực phẩm chức năng') {
      ogTitle = "Thuốc và Thực phẩm chức năng – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về thuốc và thực phẩm chức năng giúp hỗ trợ điều trị và cải thiện sức khỏe.";
      ogDescription = description;
      keywords = "thuốc, thực phẩm chức năng, uống thuốc, thuốc điều trị, uống thuốc đúng cách";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL4_1667991005853.jpg";
    } else if (this.state.subCategoryName === 'Thực phẩm') {
      ogTitle = "Thực phẩm – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về các loại thực phẩm lành mạnh và giàu dinh dưỡng, tốt cho sức khỏe.";
      ogDescription = description;
      keywords = "thực phẩm, thực phẩm lành mạnh, thực phẩm tốt cho sức khỏe";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL6_1667991006035.jpg";
    } else if (this.state.subCategoryName === 'Dinh dưỡng') {
      ogTitle = "Dinh dưỡng – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về chế độ ăn uống và dinh dưỡng lành mạnh giúp duy trì, tăng cường sức khỏe.";
      ogDescription = description;
      keywords = "dinh dưỡng, bữa ăn dinh dưỡng, đủ chất dinh dưỡng";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL5_1667991005931.jpg";
    } else if (this.state.subCategoryName === 'Sức khỏe thể chất') {
      ogTitle = "Sức khỏe thể chất – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức về thói quen, chế độ dinh dưỡng cùng các bài tập thể thao giúp xây dựng sức khỏe thể chất toàn diện.";
      ogDescription = description;
      keywords = "sức khỏe thể chất, phát triển thể chất, thể chất khỏe mạnh, sống khỏe";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL7_1668397821388.jpg";
    } else if (this.state.subCategoryName === 'Sức khỏe tinh thần') {
      ogTitle = "Sức khỏe tinh thần – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam giúp khách hàng hiểu hơn về cách xây dựng sức khỏe tinh thần, cải thiện tâm trạng và duy trì tinh thần lạc quan.";
      ogDescription = description;
      keywords = "sức khỏe tinh thần, phát triển tinh thần, tinh thần vui vẻ, tinh thần lạc quan, sống vui";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL8_1668397835077.jpg";
    } else if (this.state.subCategoryName === 'Bảo hiểm') {
      ogTitle = "Bảo hiểm – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam giúp khách hàng hiểu hơn về các sản phẩm bảo hiểm, lựa chọn giải pháp đầu tư và bảo vệ gia đình hiệu quả.";
      ogDescription = description;
      keywords = "bảo hiểm, bảo hiểm nhân thọ, bảo vệ sức khỏe, dai-ichi life việt nam";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL17_1667991009482.jpg";
    } else if (this.state.subCategoryName === 'Đầu tư & Tích lũy') {
      ogTitle = "Đầu tư tích lũy – Dai-ichi Life Việt Nam";
      description = "Trang thông tin của Dai-ichi Life Việt Nam cung cấp kiến thức đầu tư và tích lũy, giúp khách hàng hiểu rõ hơn về các phương pháp đầu tư và quản lý tài chính.";
      ogDescription = description;
      keywords = "đầu tư, tích lũy, đầu tư và tích lũy, tài chính, tài chính cá nhân";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL18_1667991009861.jpg";
    } else if (this.state.subCategoryName === 'Tin tức') {
      ogTitle = "Tin tức – Dai-ichi Life Việt Nam";
      description = "Trang thông tin cập nhật các tin tức mới, các sự kiện - hoạt động vì cộng đồng, các thành tựu đạt được của Dai-ichi Life Việt Nam.";
      ogDescription = description;
      keywords = "tin tức, tin tức mới, dai-ichi life việt nam";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL16_1667991009133.jpg";
    } else if (this.state.subCategoryName === 'Sự kiện') {
      ogTitle = "Sự kiện – Dai-ichi Life Việt Nam";
      description = "Trang thông tin cập nhật về các sự kiện - hoạt động vì cộng đồng, các thành tựu đạt được của Dai-ichi Life Việt Nam.";
      ogDescription = description;
      keywords = "sự kiện, sự kiện mới, dai-ichi life việt nam";
      ogImage = "https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40THUMBNAIL19_1667991005635.jpg";
    } else if (this.state.subCategoryName === 'Bản tin khách hàng') {
      ogTitle = "Bản tin khách hàng – Dai-ichi Life Việt Nam";
      description = "Trang thông tin hữu ích giúp khách hàng cập nhật những tin tức, sự kiện, chính sách mới của Dai-ichi Life Việt Nam.";
      ogDescription = description;
      keywords = "bản tin, trang thông tin, cập nhật tin tức, trang tin tức";
      ogImage = FE_BASE_URL + "/img/Ban-tin-khach-hang.jpg";
    }

    const adjustJustify = () => {
      if (document.getElementsByClassName('swiper-wrapper') && document.getElementsByClassName('swiper-wrapper')[0]) {
        document.getElementsByClassName('swiper-wrapper')[0].className = 'swiper-wrapper swiper-justify-left';
      }
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
        <p><Link to={"/song-vui-khoe/bi-quyet/" + this.props.match.params.category} className='breadcrums__link'>{findCategoryNameByLinkAlias(this.props.match.params.category)}</Link></p>
        <p className='breadcrums__item_arrow'>></p>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide className="breadcrums__item">
        <p>{this.state.subCategoryName}</p>
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
      var ow = window.outerWidth;
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
      setTimeout(adjustJustify(), 50);
    }
    return (
      <main className={logined ? "category-page logined" : "category-page"} id="scrollableDiv" style={getSession(FROM_APP) ? { paddingTop: '40px' } : {}}>
        {/* <main className={logined ? "logined" : ""} id="scrollableDiv" style={getSession(FROM_APP) ? { height: '800px', overflow: "auto", paddingTop: '250px' } : { height: '750px', overflow: "auto", paddingBottom:'50px' }}> */}
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
            // <div className="breadcrums">
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
            //     <p><Link to={"/song-vui-khoe/bi-quyet/" + this.props.match.params.category} className='breadcrums__link'>{findCategoryNameByLinkAlias(this.props.match.params.category)}</Link></p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            //   <div className="breadcrums__item">
            //     <p>{subCategoryName}</p>
            //     <p className='breadcrums__item_arrow'>></p>
            //   </div>
            // </div>
            <Swiper {...props} onSwiper={(swiper) => slideToEnd(swiper)} onReachEnd={(swiper) => slideToEnd(swiper)}>
              {itemList}
            </Swiper>
          }
          {/* <!-- End Breadcrums --> */}
          <div className="cate-section">
            {!getSession(FROM_APP) &&
              <div className="cate-heading">
                <h1 className="cate-heading-title">{this.state.subCategoryName}</h1>
                {/* <a href="#" className="cate-heading-view">Xem tất cả</a> */}
              </div>
            }
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
                    let subpath = '/song-vui-khoe/bi-quyet/' + StringToAliasLink(item.newsType) + '/' + StringToAliasLink(item.newsCategory);
                    let path = subpath + '/' + item.linkAlias;
                    return (
                      <div className="article-item article-item__inline article-item__border_mobile">
                        <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-thumb">
                          <img src={item.physicalImgUrl} alt="" />
                        </Link>
                        <div className="article-item-right">
                          <Link to={subpath} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-cate-link">{item.newsCategory}</Link>
                          <p><Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-title">{item.title}</Link></p>
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
                <h2 className="cate-heading-title">Bài viết mới</h2>
              </div>
            }

            {/* <InfiniteScroll
              dataLength={this.state.lengthShow}
              next={() => loadMore()}
              hasMore={this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData}
              loader={<LoadingIndicator area="request-loading-area" />}
              scrollableTarget="scrollableDiv"
              className="listing-gird"
            > */}
            <div className="listing-gird">
              {this.state.datas &&
                this.state.datas.map((item, index) => {
                  //if ((index < this.state.datas.length + x - this.state.hotNews.length) && (index < this.state.page * CMS_HW_PAGE_TIP_SIZE)) {
                  if (findItemInNewsList(item, this.state.hotNews) || (this.state.selectedItem.id === item.id)) {
                    x++;
                  } else {
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

                  //}

                })
              }
            </div>
            {/* </InfiniteScroll> */}


            <LoadingIndicator area="request-loading-area" />
            <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>
              {((this.state.page * CMS_HW_PAGE_TIP_SIZE < this.state.totalData) && !this.state.noMore) &&
                <div style={{ paddingLeft: '10px', paddingTop: '36px', display: 'flex' }}>
                  {/* <p className='simple-brown2 closebtn' onClick={() => loadMore()}>Xem thêm</p> */}
                  <p className="simple-brown2 closebtn" onClick={() => loadMore()}>Xem thêm&nbsp;</p><p className="feeDetailsArrowDown" style={{ padding: '8px 10px 20px 0px' }}><img src="../../../img/icon/arrow-down-bronw.svg" alt="arrow-down-icon" /></p>

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

export default SubCategory;