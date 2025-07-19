import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {
    ACCESS_TOKEN,
    API_NEWS_LIST_ALL_HOTS_BY,
    API_NEWS_LIST_BY_KEYWORK,
    CMS_CATEGORY_CODE,
    CMS_HW_PAGE_SIZE,
    CMS_HW_SEARCH_SIZE,
    CMS_IMAGE_URL,
    CMS_KEYWORK,
    CMS_LIST_DIST_META_KEYWORD,
    CMS_LIST_DIST_META_KEYWORD_DATA,
    CMS_NEWS,
    CMS_PAGE_DEFAULT,
    CMS_SUB_CATEGORY_ID,
    CMS_SUB_CATEGORY_MAP,
    FE_BASE_URL,
    FROM_APP,
    FROM_SCREEN_APP,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID
} from '../constants';
import {getListByPath} from '../util/APIUtils';
import {
    compareDate,
    findCategoryBySubCategoryId,
    formatDate,
    getSession,
    getUrlParameter,
    isMobile,
    postMessageSubNativeIOS,
    pushHistory, removeAccents,
    setSession,
    shortFormatDate
} from '../util/common';
import {Swiper, SwiperSlide} from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {Autoplay, Navigation, Pagination} from 'swiper';
import LoadingIndicator from '../common/LoadingIndicator2';
import {Helmet} from "react-helmet";
import {deburr} from "lodash/string";

SwiperCore.use([Autoplay, Pagination, Navigation]);

class SearchArticle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            datas: null,
            totalData: 0,
            searchDatas: null,
            categoryName: '',
            subCategoryName: '',
            page: CMS_PAGE_DEFAULT,
            subCategoryMap: JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)),
            categoryListData: null,
            keyword: getUrlParameter("q"),
            keyedit: getUrlParameter("q"),
            loading: false,
            searchInPage: false,
            listDistMetaKeyword: null,
            isShowPopup: false,
            selectedFromList: false,
        }
    }

    getNewsRefresh = (page, pageSize) => {
        this.setState({loading: true});
        getListByPath(CMS_NEWS + API_NEWS_LIST_ALL_HOTS_BY + page + "/" + pageSize + "/" + pageSize)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    this.setState({datas: res.data.datas, totalData: 0, loading: false});
                }
            }).catch(error => {
            this.setState({loading: false});
        });
    }

    searchByKeyRefresh = (keyword, page, pageSize, searchInPage) => {
        this.setState({loading: true, keyword: keyword, keyedit: keyword, searchInPage: searchInPage});
        let copyKey = keyword.slice().replace(/\s+/g, ' ').trim();
        getListByPath(CMS_NEWS + API_NEWS_LIST_BY_KEYWORK + copyKey + "/" + page + "/" + pageSize)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    if (res.data.totalData === 0) {
                        if (!getSession(FROM_APP)) {
                            this.getNewsRefresh(CMS_PAGE_DEFAULT, CMS_HW_PAGE_SIZE);
                        } else {
                            this.setState({totalData: 0, loading: false});
                        }

                    } else {
                        this.setState({
                            searchDatas: res.data.datas,
                            totalData: res.data.totalData,
                            page: page,
                            loading: false,
                            keyword: keyword
                        });
                    }
                }
            }).catch(error => {
            this.setState({loading: false});
        });
    }

    searchByKey = (keyword, page, pageSize) => {
        this.setState({loading: true});
        let copyKey = keyword.slice().replace(/\s+/g, ' ').trim();
        getListByPath(CMS_NEWS + API_NEWS_LIST_BY_KEYWORK + copyKey + "/" + page + "/" + pageSize)
            .then(res => {
                if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                    if (res.data.totalData === 0) {
                        this.getNewsRefresh(CMS_PAGE_DEFAULT, CMS_HW_PAGE_SIZE);
                    } else {
                        if (!this.state.searchDatas) {
                            this.setState({
                                searchDatas: res.data.datas,
                                totalData: res.data.totalData,
                                page: page,
                                loading: false
                            });
                        } else {
                            this.setState({
                                searchDatas: this.state.searchDatas.concat(res.data.datas),
                                totalData: res.data.totalData,
                                page: page,
                                loading: false
                            });
                        }

                    }
                }
            }).catch(error => {
            this.setState({loading: false});
        });
    }

    getMetaKeywordSuggestion() {
        if (getSession(CMS_LIST_DIST_META_KEYWORD_DATA)) {
            let listDistMetaKeyword = JSON.parse(getSession(CMS_LIST_DIST_META_KEYWORD_DATA));
            this.setState({listDistMetaKeyword: listDistMetaKeyword});
        } else {
            getListByPath(CMS_NEWS + CMS_LIST_DIST_META_KEYWORD)
                .then(res => {
                    if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                        setSession(CMS_LIST_DIST_META_KEYWORD_DATA, JSON.stringify(res.data));
                        this.setState({listDistMetaKeyword: res.data});
                    }
                }).catch(error => {
            });
        }

    }

    componentDidMount() {
        // window.prerenderReady = false;
        //highlight the active menu
        setSession(LINK_MENU_NAME, 'Sống vui khoẻ');
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, 'Bí quyết Sống vui khỏe');
        setSession(LINK_SUB_MENU_NAME_ID, 'h4');
        this.topFunction();

        let fromNative = getUrlParameter("from");
        postMessageSubNativeIOS(fromNative);
        let query = getUrlParameter("q");
        if (query) {
            // console.log('query', query);
            this.searchByKeyRefresh(query, CMS_PAGE_DEFAULT, CMS_HW_SEARCH_SIZE, false);
        }
        this.getMetaKeywordSuggestion();
    }

    componentDidUpdate() {
        if (this.state.searchInPage) {
            return;
        }
        let query = getUrlParameter("q");
        if (query !== this.state.keyword) {
            this.topFunction();
            if (query) {
                this.searchByKeyRefresh(query, CMS_PAGE_DEFAULT, CMS_HW_SEARCH_SIZE, false);
            } else {
                this.getNewsRefresh(CMS_PAGE_DEFAULT, CMS_HW_PAGE_SIZE);
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
                this.searchByKey(this.state.keyword, this.state.page + 1, CMS_HW_SEARCH_SIZE);
            }
        }

        let logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }

        let clsName = logined ? "category-page logined" : "category-page";
        if (getSession(FROM_APP)) {
            if (window.location.pathname === '/timkiem/') {
                clsName = clsName + ' ' + 'from-app-search-padding-top';
            } else {
                clsName = clsName + ' ' + 'from-app-padding-top';
            }
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
                <p>Bí quyết Sống vui khỏe</p>
                <p className='breadcrums__item_arrow'>></p>
            </SwiperSlide>);

        let propsBreadcrums = {
            className: "breadcrums breadcrums__scroll",
            slidesPerView: 3,
            breakpoints: {
                300: {
                    slidesPerView: 2,
                },
                640: {
                    slidesPerView: 3,
                },
                768: {
                    slidesPerView: 3,
                }
            }
        };
        const slideToEnd = (swiper) => {
            const ow = window.outerWidth;
            if (ow <= 280) {
                swiper.slideTo(2);
            } else {
                swiper.slideTo(0);
            }

        }

        const slideReachEnd = (swiper) => {
            const ow = window.outerWidth;
            if (ow <= 280) {
                swiper.slideTo(2);
            } else if (ow < 412) {
                swiper.slideTo(2);
            } else {
                swiper.slideTo(0);
            }

        }

        const setKeyEdit = (value) => {
            this.setState({keyedit: value});
        }

        const submitSearch = (event) => {
            event.preventDefault();
            if (!this.state.keyedit) {
                return;
            }
            if (document.getElementById('search-box')) {
                document.getElementById('search-box').blur();
            }
            this.props.history.push('/timkiem/?q=' + this.state.keyedit);
            this.searchByKeyRefresh(this.state.keyedit, CMS_PAGE_DEFAULT, CMS_HW_SEARCH_SIZE, true);
        }

        if (!getSession(FROM_APP)) {
            setTimeout(adjustJustify(), 100);
        }

        const filteredAndSortedKeywords = (keywords) => {
            const { keyedit } = this.state;

            const sortedKeywords = keywords.sort((a, b) => a.metaKeyword.localeCompare(b.metaKeyword));

            const normalizedKeyEdit = removeAccents(keyedit.toLowerCase()).trim();

            const filteredKeywords = sortedKeywords.filter(item => {
                const normalizedMetaKeyword = removeAccents(item.metaKeyword.toLowerCase()).trim();
                return normalizedMetaKeyword.startsWith(normalizedKeyEdit);
            });

            return filteredKeywords.slice(0, 7);
        };


        const {keyedit, isShowPopup, listDistMetaKeyword} = this.state;
        const filteredKeywords = listDistMetaKeyword && filteredAndSortedKeywords(listDistMetaKeyword);

        return (
            <>
                {this.state.datas &&
                    <Helmet>
                        <title> Bí quyết Sống vui khỏe – Dai-ichi Life Việt Nam </title>
                        <meta name="description"
                              content="Trang thông tin hữu ích giúp bạn sống vui khỏe mỗi ngày, hiểu hơn về sức khỏe thể chất cũng như tinh thần của mình."/>
                        <meta name="keywords"
                              content="bí quyết sống vui, bí quyết sống khỏe, bí quyết sống vui khỏe, sống vui sống khỏe"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/song-vui-khoe/bi-quyet"}/>
                        <meta property="og:title" content="Bí quyết Sống vui khỏe – Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Trang thông tin hữu ích giúp bạn sống vui khỏe mỗi ngày, hiểu hơn về sức khỏe thể chất cũng như tinh thần của mình."/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40MicrosoftTeamsimage98_1667381529594.png"/>
                        <link rel="canonical" href={FE_BASE_URL + "/song-vui-khoe/bi-quyet"}/>
                    </Helmet>
                }
                <main className={clsName} id="scrollableDiv">
                    <div className="container">
                        {/* <!-- Breadcrums --> */}
                        {!getSession(FROM_APP) &&
                            <Swiper {...propsBreadcrums} onSwiper={(swiper) => slideToEnd(swiper)}
                                    onReachEnd={(swiper) => slideReachEnd(swiper)}>
                                {itemListBreadcrums}
                            </Swiper>
                        }
                        {/* <!-- End Breadcrums --> */}
                        <form className={getSession(FROM_APP) ? "formsearch padding-top-28" : "formsearch"}
                              onSubmit={(e) => submitSearch(e)} autocomplete="off" style={{paddingLeft: '12px'}}>
                            <div className="form-group">
                                <div className="formsearch-inline">
                                    <div className="col-l">
                                        <div className='input' style={{minHeight: '48px', height: '48px'}}>
                                            <div
                                                className={this.state.keyedit ? "input__content_expand" : "input__content"}>
                                                <input
                                                    type="search"
                                                    inputMode="search"
                                                    className="form-control"
                                                    placeholder="Nhập từ khóa để tìm kiếm bài viết"
                                                    name="search-box-popup"
                                                    value={this.state.keyedit}
                                                    onChange={(e) => setKeyEdit(e.target.value)}
                                                    onFocus={() => {
                                                        this.setState({
                                                            isShowPopup: true,
                                                        });
                                                    }}
                                                    onBlur={(e) => {
                                                        if (!e.relatedTarget || e.relatedTarget.className !== "keyword-suggestions") {
                                                            setTimeout(() => {
                                                                if (!this.state.selectedFromList) {
                                                                    this.setState({
                                                                        isShowPopup: false,
                                                                    });
                                                                }
                                                                this.setState({
                                                                    selectedFromList: false,
                                                                });
                                                            }, 100);
                                                        }
                                                    }}
                                                    onKeyPress={(e) => {
                                                        if (e.key === 'Enter') {
                                                            submitSearch(e);
                                                        }
                                                    }}
                                                />
                                                {keyedit && isShowPopup && filteredKeywords && (
                                                    <ul className="keyword-suggestions">
                                                        {filteredKeywords?.length === 0 ? (
                                                            <li>Không có từ khóa gợi ý tương ứng</li>
                                                        ) : (
                                                            filteredKeywords?.map((item, index) => (
                                                                <li
                                                                    key={index}
                                                                    onMouseDown ={() => {
                                                                        setKeyEdit(item?.metaKeyword?.trim());
                                                                        this.setState({
                                                                            selectedFromList: true,
                                                                            isShowPopup: false,
                                                                        });
                                                                        this.props.history.push('/timkiem/?q=' + item?.metaKeyword?.trim());
                                                                    }}
                                                                >
                                                                    {item.metaKeyword}
                                                                </li>
                                                            ))
                                                        )}
                                                    </ul>
                                                )}
                                            </div>
                                            {this.state.keyedit ? (
                                                <></>
                                            ) : (
                                                <i className="icon" style={{cursor: 'pointer'}}
                                                   onClick={(e) => setKeyEdit('')}><img
                                                    src={FE_BASE_URL + "/img/icon/Search.svg"} alt="search"/></i>
                                            )}
                                        </div>
                                    </div>
                                    {!getSession(FROM_APP) &&
                                        <div className="col-r">
                                            <button
                                                className={this.state.keyedit ? "btn btn-primary" : "btn btn-primary disabled"}
                                                style={{width: '175px'}} id="search-box-id"
                                                disabled={this.state.keyword ? false : true}>Tìm kiếm
                                            </button>
                                        </div>
                                    }
                                    <div className="col-l">
                                        <LoadingIndicator area="request-loading-area"/>
                                    </div>
                                </div>

                            </div>

                        </form>
                        {this.state.totalData > 0 ? (
                            <div className="searchresult">
                                {!this.state.loading &&
                                    <p className="formsearch_noti">Kết quả tìm kiếm
                                        cho <span>“{getUrlParameter("q") ? getUrlParameter("q") : ''}"</span></p>
                                }
                                <div className="searchresult-content list-v">
                                    {this.state.searchDatas &&
                                        this.state.searchDatas.map((item, index) => {
                                            return (
                                                <div className="article-item article-item__inline">
                                                    <Link to={item.articlePathName}
                                                          onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                          className="article-thumb">
                                                        <img src={item.physicalImgUrl} alt=""/>
                                                    </Link>
                                                    <div className="article-item-right">
                                                        <h4><Link to={item.articlePathName}
                                                                  onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                  className="article-search-title"
                                                                  alt={item.title}>{item.title}</Link></h4>
                                                        {!getSession(FROM_APP) &&
                                                            <p className={isMobile() ? "article-item-excerpt hide" : "article-item-excerpt"}>{item.metaDescription}</p>
                                                        }
                                                        <div className="article-nav article-nav__start">
                                                            {item.hasEvent ? (
                                                                <span className="article-nav-item margin-bottom-event">
                                  <div className='flex-event'><i
                                      className="ico ico-event-location"></i> <span>{item.eventLocation ? item.eventLocation : ''}</span></div>
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
                                        })
                                    }


                                </div>
                                <LoadingIndicator area="request-loading-area"/>
                                <div className="paging-container basic-expand-footer" style={{paddingTop: '0px'}}>
                                    {((this.state.page * CMS_HW_SEARCH_SIZE < this.state.totalData) && !this.state.noMore) &&
                                        <div style={{paddingLeft: '10px', paddingTop: '36px', display: 'flex'}}>
                                            {/* <p className='simple-brown2 closebtn' onClick={() => loadMore()}>Xem thêm</p> */}
                                            <p className="simple-brown2 closebtn" onClick={() => loadMore()}>Xem
                                                thêm&nbsp;</p><p
                                            className="feeDetailsArrowDown" style={{padding: '8px 10px 20px 0px'}}><img
                                            src="../../img/icon/arrow-down-bronw.svg" alt="arrow-down-icon"/></p>

                                        </div>
                                    }
                                </div>
                            </div>
                        ) : (
                            <>
                                <div className="searchresult searchresult__empty">
                                    {!this.state.loading &&
                                        <>
                                            <div className="searchresult-content">
                                                <img src="../../../img/new/emptysate2.png" alt=""/>
                                            </div>
                                            {getUrlParameter("q") &&
                                                <div className="searchresult-content">
                                                    <p>Xin lỗi! Chúng tôi không tìm thấy bài viết theo <br/> từ khóa bạn
                                                        vừa nhập.
                                                        Hãy thử lại nhé! <br/> <br/> Xem thêm <Link
                                                            to="/song-vui-khoe/bi-quyet">Bí
                                                            quyết Sống vui khỏe</Link></p>
                                                </div>
                                            }
                                        </>
                                    }
                                </div>
                                {!getSession(FROM_APP) &&
                                    <div className="cate-section">
                                        {this.state.datas && (this.state.datas.length > 0) &&
                                            <h3 className="cate-heading">
                                                <p className="cate-heading-title">Có thể bạn quan tâm</p>
                                            </h3>
                                        }

                                        <div className="listing-gird">
                                            {this.state.datas &&
                                                this.state.datas.map((item, idx) => {
                                                    // let categoryName = findCategoryNameBySubCatName(item.typeName);
                                                    return (
                                                        <div className="article-item article-item__border">
                                                            <Link to={item.articlePathName}
                                                                  onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                  className="article-thumb">
                                                                <img src={item.physicalImgUrl} alt=""/>
                                                            </Link>
                                                            <div className="article-item-right">
                                                                <Link to={item.subCategoryPathName}
                                                                      onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                      className="article-cate-link">{item.typeName}</Link>
                                                                <h4><Link to={item.articlePathName}
                                                                          onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)}
                                                                          className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}
                                                                          alt={item.title}>{item.title}</Link></h4>
                                                                <div className="article-nav">
                                                                    {item.hasEvent ? (
                                                                        <span className="article-nav-item grid-event"
                                                                              style={{
                                                                                  marginBottom: '2px',
                                                                                  marginTop: '-2px'
                                                                              }}>
                                      <i className="ico ico-event-location"></i> <span
                                                                            className='margin-event'>{item.eventLocation ? item.eventLocation : ''}</span>
                                      <i className="ico ico-event-date ico-margin"></i>
                                                                            {item.endDate && (compareDate(item.startDate, item.endDate) > 0) ? (
                                                                                <span
                                                                                    className='margin-event'>{item.startDate ? shortFormatDate(item.startDate) : ''}{' - ' + formatDate(item.endDate)}</span>
                                                                            ) : (
                                                                                <span
                                                                                    className='margin-event'>{item.startDate ? formatDate(item.startDate) : ''}</span>
                                                                            )}

                                    </span>
                                                                    ) : (
                                                                        <span
                                                                            className="article-nav-item adjust-last-icon-event">
                                      <i className="ico ico-calendar"></i> <span>{formatDate(item.postedDate)}</span>
                                    </span>
                                                                    )}
                                                                    <a href="#"
                                                                       className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                })
                                            }
                                        </div>

                                    </div>
                                }
                            </>
                        )}

                    </div>
                    {
                        getSession(FROM_APP) && getSession(FROM_APP) === "IOS" &&
                        <div className='padding-bottom-30'></div>
                    }
                </main>
            </>


        )
    }

}

export default SearchArticle;