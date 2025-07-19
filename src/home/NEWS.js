import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { CMS_LIST_HOME, CMS_PAGE_DEFAULT, CMS_NEWS, CMS_SUB_CATEGORY_ID, CMS_IMAGE_URL, CMS_KEYWORK } from '../constants';
import { getListByPath } from '../util/APIUtils';
import { formatDate, setSession, shortFormatDate, compareDate } from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';

class NEWS extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);
    this.state = {
      toggle: false,
      totalData: 0,
      hotNews: null,
      datas: null,
      page: CMS_PAGE_DEFAULT
    }
  }

  getNews = () => {
    getListByPath(CMS_NEWS + CMS_LIST_HOME + "/" + 4)
      .then(res => {
        if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
          if (this._isMounted) {
            let jsonState = this.state;
            jsonState.totalData = res.data.totalData;
            jsonState.hotNews = res.data.hotNews;
            this.setState(jsonState);
          }
        }
      }).catch(error => {
        // console.log(error);
      });
  }

  componentDidMount() {
    this._isMounted = true;
    this.getNews();
  }
  componentWillUnmount() {
    this._isMounted = false;
  }


  render() {
    const passSubCategory = (subCategoryId, imageUrl, keyWork) => {
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
      setSession(CMS_IMAGE_URL, imageUrl);
      setSession(CMS_KEYWORK, keyWork);
      callbackApp(true);
    }
    const callbackApp = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    return (
      <section>
        <div className="container">
          {/* <!-- Category Section --> */}
          <div className="cate-section">
            <div className="cate-heading">
              <p className="cate-heading-title">Sống vui khỏe</p>
              <Link className="simple-brown basic-semibold" to={"/song-vui-khoe/bi-quyet"} onClick={() => callbackApp(true)} >Tất cả <span className="simple-brown basic-semibold">></span>
              </Link>
            </div>
            <div className="listing-gird">
              {this.state.hotNews &&
                this.state.hotNews.map((item, index) => {
                  let subPath = '/song-vui-khoe/bi-quyet/' + item.categoryLinkAlias + '/' + item.subCategoryLinkAlias;
                  let path = subPath + '/' + item.linkAlias;
                  return (
                    <div className="article-item article-item__border" key={"home-article-item" + index}>
                      <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-thumb">
                        <img src={item.physicalImgUrl} alt="" className="" />
                      </Link>
                      <div className="article-item-right">
                        <Link to={subPath} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className="article-cate-link">{item.newsCategory}</Link>
                        <Link to={path} onClick={() => passSubCategory(item.newsCategoryId, item.physicalImgUrl, item.keyWord)} className={item.hasEvent ? "article-title line-clamp-event" : "article-title"}>{item.title}</Link>
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


                })
              }
            </div>

            <LoadingIndicator area="request-loading-area" />
            <div className="paging-container basic-expand-footer" style={{ paddingTop: '0px' }}>
            </div>
          </div>
          {/* <!-- End Category Section --> */}

        </div>
      </section>
    )
  }

}

export default NEWS;