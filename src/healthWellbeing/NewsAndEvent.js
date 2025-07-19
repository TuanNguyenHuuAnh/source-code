import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { MAGP_FILE_FOLDER_URL,ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, CLIENT_PROFILE, EXPIRED_MESSAGE, CMS_HEALTH_NEWS, AUTHENTICATION, COMPANY_KEY} from '../constants';
import { CPGetClientProfileByCLIID,CPGetPolicyInfoByPOLID,cms, logoutSession } from '../util/APIUtils';
import '../common/Common.css';
import {showMessage, getSession} from '../util/common';
class NewsAndEvent extends Component
{    
  constructor(props) {
    super(props);   
  
  }
 

    render(){  
      var logined = false;
      if (getSession(ACCESS_TOKEN)) {
        logined = true;
      }
        return (
        
          <main className={logined ? "logined" : ""}>
          <div className="container">
            {/* <!-- Breadcrums --> */}
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Sống khỏe</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Bên mua bảo hiểm</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            {/* <!-- End Breadcrums --> */}
            <div className="nav-cate">
              <a href="#" className="nav-cate-item">
                <span className="nav-icon"><i className="ico ico-news ico__large"></i></span>
                <span className="nav-label">Tin tức</span>
              </a>
              <a href="#" className="nav-cate-item">
                <span className="nav-icon"><i className="ico ico-event ico__large"></i></span>
                <span className="nav-label">Sự kiện</span>
              </a>
            </div>
            <div className="cate-section">
              <h3 className="cate-heading">
                <a href="#" className="cate-heading-title">Cẩm nang dùng thuốc</a>
                <a href="#" className="cate-heading-view">Xem tất cả</a>
              </h3>
              <div className="cate-featured">
                <div className="article-item">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cẩm nang dùng thuốc</a></h4>
                    <p>Lorem ipsum dolor sit amet, consectetur sed do eiusmod tempor incididunt ut labore et</p>
                    <div className="article-nav article-nav__start">
                      <span className="article-nav-item">
                        <i className="ico ico-place"></i> <span>Hồ Chí Minh</span>
                      </span>
                      <span className="article-nav-item">
                        <i className="ico ico-dater"></i> <span>30/08/2022</span>
                      </span>
                      
                    </div>
                  </div>
                </div>
              </div>
              <div className="cate-featured-right">
                <div className="listing-v">
                  <div className="article-item article-item__inline article-item__border_mobile">
                    <a href="#" className="article-thumb">
                      <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                    </a>
                    <div className="article-item-right">
                      <a href="#" className="article-cate-link">Sự kiện</a>
                      <h4><a href="#" className="article-title">5 bài tập buổi sáng giúp bạn luôn tỉnh táo và minh mẫn suốt cả ngày</a></h4>
                      <div className="article-nav">
                        <span className="article-nav-item">
                          <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                        </span>
                        <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                      </div>
                    </div>
                  </div>
                  <div className="article-item article-item__inline article-item__border_mobile">
                    <a href="#" className="article-thumb">
                      <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                    </a>
                    <div className="article-item-right">
                      <a href="#" className="article-cate-link">Sự kiện</a>
                      <h4><a href="#" className="article-title">5 bài tập buổi sáng giúp bạn luôn tỉnh táo và minh mẫn suốt cả ngày</a></h4>
                      <div className="article-nav">
                        <span className="article-nav-item">
                          <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                        </span>
                        <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                      </div>
                    </div>
                  </div>
                  <div className="article-item article-item__inline article-item__border_mobile">
                    <a href="#" className="article-thumb">
                      <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                    </a>
                    <div className="article-item-right">
                      <a href="#" className="article-cate-link">Sự kiện</a>
                      <h4><a href="#" className="article-title">5 bài tập buổi sáng giúp bạn luôn tỉnh táo và minh mẫn suốt cả ngày</a></h4>
                      <div className="article-nav">
                        <span className="article-nav-item">
                          <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                        </span>
                        <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                      </div>
                    </div>
                  </div>
                  <div className="article-item article-item__inline article-item__border_mobile">
                    <a href="#" className="article-thumb">
                      <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                    </a>
                    <div className="article-item-right">
                      <a href="#" className="article-cate-link">Sự kiện</a>
                      <h4><a href="#" className="article-title">5 bài tập buổi sáng giúp bạn luôn tỉnh táo và minh mẫn suốt cả ngày</a></h4>
                      <div className="article-nav">
                        <span className="article-nav-item">
                          <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                        </span>
                        <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="cate-section">
              <h3 className="cate-heading">
                <a href="#" className="cate-heading-title">Bài viết mới</a>
              </h3>
              <div className="listing-gird">
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
                <div className="article-item article-item__border">
                  <a href="#" className="article-thumb">
                    <img src="../../img/new/rectangle-372.jpg" alt="" className=""/>
                  </a>
                  <div className="article-item-right">
                    <a href="#" className="article-cate-link">Sự kiện</a>
                    <h4><a href="#" className="article-title">Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai</a></h4>
                    <div className="article-nav">
                      <span className="article-nav-item">
                        <i className="ico ico-calendar"></i> <span>30/08/2022</span>
                      </span>
                      <a href="#" className="article-nav-item"><i className="ico ico-bookmark-g"></i> <span>Lưu</span></a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      
        )         
    }

}

export default NewsAndEvent;