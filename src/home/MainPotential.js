import React from 'react';
import {Link} from "react-router-dom";
import {DOWNLOAD_APP_ANDROID, DOWNLOAD_APP_IOS} from '../constants/index';
import iconRunning from '../img/IconH&W.svg';

function MainPotential ({clientProfile}){
    return(
      <main className="logined">
      <section className="sccarousel">
        <div className="custom-carousel swiper">
          <div className="carousel swiper-wrapper">
            <div className="carousel__item swiper-slide">
              <div className="banner-content"></div>
              <div className="back-img">
                <img src="img/slider/banner 1.jpg" alt="" />
              </div>
            </div>
            <div className="carousel__item swiper-slide">
              <div className="banner-content"></div>
              <div className="back-img">
                <img src="img/slider/banner 1.jpg" alt="" />
              </div>
            </div>
            <div className="carousel__item swiper-slide">
              <div className="banner-content"></div>
              <div className="back-img">
                <img src="img/slider/banner 1.jpg" alt="" />
              </div>
            </div>
          </div>
          <div className="control">
            <button className="prev">
              <i>
                <img src="img/slider/control_left.svg" alt="" />
              </i>
            </button>
            <button className="next">
              <i>
                <img src="img/slider/control_right.svg" alt="" />
              </i>
            </button>
          </div>
          <div className="banner-paging"></div>
        </div>
      </section>

      <section className="scintro">
        <div className="container">
          <div className="title">
            <h2 className="basic-red">Dai-ichi Connect</h2>
            <p>Giao dịch từ xa - An tâm mọi nhà</p>
          </div>
          <div className="intro-warpper">
            <div className="intro">
              <div className="intro__item">
                <div className="icon-warpper">
                  <img src="img/icon/map_white.svg" alt="" />
                </div>
                <p className="basic-bold">Mạng lưới</p>
              </div>
              <div className="intro__item">
                <div className="icon-warpper">
                  <img src="img/icon/wallet_white.svg" alt="" />
                </div>
                <p className="basic-bold">Đóng phí bảo hiểm</p>
              </div>
              <div className="intro__item">
                <div className="icon-warpper">
                  <img src="img/icon/file_white.svg" alt="" />
                </div>
                <p className="basic-bold">Hợp đồng bảo hiểm</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="scfolder">
        <div className="container">
          <div className="plancarousel swiper">
            <div className="plancarousel__wrapper swiper-wrapper">
              <div className="plancarousel__slide swiper-slide">
                <div className="plancard">
                  <div className="plancard__body">
                    <div className="img-warpper">
                      <img src="img/newscard/new4.jpg" alt="" />
                    </div>
                    <div className="plancard__body-content">
                      <div className="head">
                        <div className="headtab">
                          <p className="head-title">Tuổi:</p>
                          <p className="head-content">33</p>
                        </div>
                        <div className="headtab">
                          <p className="head-title">Thu nhập:</p>
                          <p className="head-content">15.000.000 đ</p>
                        </div>
                      </div>
                      <div className="body">
                        <span>Số tiền bảo hiểm tối đa:</span>
                        <p className="basic-red basic-semibold">100.000.000 VNĐ</p>
                      </div>
                    </div>
                  </div>
                  <div className="plancard__footer">
                    <p className="basic-semibold">An Thịnh Đầu Tư</p>
                  </div>
                </div>
              </div>
              <div className="plancarousel__slide swiper-slide">
                <div className="plancard">
                  <div className="plancard__body">
                    <div className="img-warpper">
                      <img src="img/newscard/new4.jpg" alt="" />
                    </div>
                    <div className="plancard__body-content">
                      <div className="head">
                        <div className="headtab">
                          <p className="head-title">Tuổi:</p>
                          <p className="head-content">33</p>
                        </div>
                        <div className="headtab">
                          <p className="head-title">Thu nhập:</p>
                          <p className="head-content">15.000.000 đ</p>
                        </div>
                      </div>
                      <div className="body">
                        <span>Số tiền bảo hiểm tối đa:</span>
                        <p className="basic-red basic-semibold">100.000.000 VNĐ</p>
                      </div>
                    </div>
                  </div>
                  <div className="plancard__footer">
                    <p className="basic-semibold">An Thịnh Đầu Tư</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="scplan">
        <div className="container">
          <div className="present">
            <div className="present__header">
              <h4>Kế hoạch bảo hiểm cùng Dai-ichi</h4>
            </div>
            <div className="present__body">
              <div className="present__body-content">
                <p className="title">Chia sẻ cùng <span>Dai-ichi Life</span> để lựa chọn kế hoạch bảo vệ phù hợp nhất</p>
                <div className="tab-warpper">
                  <div className="tab">
                    <div className="special-dropdown">
                      <div className="dropdown__content">
                        <div className="tab-content">
                          <p>Tôi đang ở giai đoạn</p>
                          <i className="icon-down"><img src="img/icon/arrow-down.svg" alt="" /></i>
                        </div>
                      </div>
                      <div className="dropdown__items">
                        <div className="tab-item-warpper">
                          <div className="tab-head">
                            <p className="basic-semibold">Chọn giai đoạn cuộc sống của bạn</p>
                            <i className="close"><img src="img/icon/close-icon.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Độc thân">
                            <p>Độc thân</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Mới kết hôn">
                            <p>Mới kết hôn</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Gia đình có con nhỏ">
                            <p>Gia đình có con nhỏ</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Con trưởng thành">
                            <p>Con trưởng thành</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Chuẩn bị nghỉ hưu">
                            <p>Chuẩn bị nghỉ hưu</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                      <div className="bg-dropdown"></div>
                    </div>
                  </div>
                  <div className="tab">
                    <div className="special-dropdown">
                      <div className="dropdown__content">
                        <div className="tab-content">
                          <p>Tôi đang ở giai đoạn</p>
                          <i className="icon-down"><img src="img/icon/arrow-down.svg" alt="" /></i>
                        </div>
                      </div>
                      <div className="dropdown__items">
                        <div className="tab-item-warpper">
                          <div className="tab-head">
                            <p className="basic-semibold">Chọn giai đoạn cuộc sống của bạn</p>
                            <i className="close"><img src="img/icon/close-icon.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Độc thân">
                            <p>Độc thân</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Mới kết hôn">
                            <p>Mới kết hôn</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Gia đình có con nhỏ">
                            <p>Gia đình có con nhỏ</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Con trưởng thành">
                            <p>Con trưởng thành</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                          <div className="tab-item" data-tab="Chuẩn bị nghỉ hưu">
                            <p>Chuẩn bị nghỉ hưu</p>
                            <i><img src="img/icon/check_green.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                      <div className="bg-dropdown"></div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="present__body-decor">
                <img src="img/present_person.svg" alt="" />
              </div>
            </div>
            <i className="decor"><img src="img/icon/homepage/present_head_ic.svg" alt="" /></i>
          </div>
        </div>
      </section>

      <section className="scproduct">
        <div className="container">
          <h2>Sản phẩm được quan tâm</h2>
          <div className="products-warpper">
            <div className="products">
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product1.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Song Hành</p>
                    </div>
                  </div>
                </a>
              </div>
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product2.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Hưng Thịnh Toàn Diện</p>
                    </div>
                  </div>
                </a>
              </div>
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product3.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Song Hành</p>
                    </div>
                  </div>
                </a>
              </div>
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product4.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Song Hành</p>
                    </div>
                  </div>
                </a>
              </div>
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product5.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Song Hành</p>
                    </div>
                  </div>
                </a>
              </div>
              <div className="products__item">
                <a href="">
                  <div className="productcard">
                    <div className="img-warpper">
                      <img src="img/product6.jpg" alt="" />
                    </div>
                    <div className="content">
                      <p className="basic-semibold">An Tâm Song Hành</p>
                    </div>
                  </div>
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="scpromotion">
        <div className="container">
          <div className="promotion-warpper">
            <h2>Chương trình điểm thưởng</h2>
            <Link to="/point" className="promotionvideo">
            <button className="promotionvideo">
              <p>trải nghiệm ngay</p>
              <div className="icon-warpper">
                <i><img src="img/icon/playbtn.svg" alt="" /></i>
              </div>
            </button>
            </Link>
          </div>
        </div>
      </section>

      <section className="scnews">
        <div className="container">
          <div className="scnews__head">
            <h2 className="basic-bold">Sống khỏe</h2>
            <p>Xây dựng lối sống lành mạnh với <span className="basic-bold">Dai-ichi Life Việt Nam</span></p>
            <div className="icon-warpper">
              <div className="icon">
                <i><img src={iconRunning} alt="" /></i>
                <p>Cung Đường Yêu Thương</p>
              </div>
              <div className="icon">
                <i><img className="basic-small" src="img/icon/medical_man.svg" alt="" /></i>
                <p>Tư vấn sức khỏe</p>
              </div>
            </div>
          </div>

          <div className="scnews__body">
            <div className="scnews__body-title">
              <h3 className="basic-bold">Bản tin sống khỏe</h3>
              <a className="simple-brown basic-semibold" href="">Xem tất cả <span className="simple-brown">></span></a>
            </div>
            <div className="news-warpper">
              <div className="news">
                <div className="news__item">
                  <div className="newscard">
                    <a href="">
                      <div className="img-warpper">
                        <img src="img/newscard/new1.jpg" alt="" />
                      </div>
                    </a>
                    <div className="newscard__content">
                      <a href="">
                        <h4>Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai.</h4>
                      </a>
                      <div className="newscard__content-bottom">
                        <div className="tag"><p>mới</p></div>
                        <div className="icon-warpper">
                          <img src="img/icon/share.svg" alt="" />
                        </div>
                        <span>Chia sẻ</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="news__item">
                  <div className="newscard">
                    <a href="">
                      <div className="img-warpper">
                        <img src="img/newscard/new2.jpg" alt="" />
                      </div>
                    </a>
                    <div className="newscard__content">
                      <a href="">
                        <h4>Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai.</h4>
                      </a>
                      <div className="newscard__content-bottom">
                        <div className="tag"><p>mới</p></div>
                        <div className="icon-warpper">
                          <img src="img/icon/share.svg" alt="" />
                        </div>
                        <span>Chia sẻ</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="news__item">
                  <div className="newscard">
                    <a href="">
                      <div className="img-warpper">
                        <img src="img/newscard/new3.jpg" alt="" />
                      </div>
                    </a>
                    <div className="newscard__content">
                      <a href="">
                        <h4>Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai.</h4>
                      </a>
                      <div className="newscard__content-bottom">
                        <div className="tag"><p>mới</p></div>
                        <div className="icon-warpper">
                          <img src="img/icon/share.svg" alt="" />
                        </div>
                        <span>Chia sẻ</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="news__item">
                  <div className="newscard">
                    <a href="">
                      <div className="img-warpper">
                        <img src="img/newscard/new4.jpg" alt="" />
                      </div>
                    </a>
                    <div className="newscard__content">
                      <a href="">
                        <h4>Cùng Dai-ichi Life Việt Nam chủ động viết tiếp câu chuyện tương lai.</h4>
                      </a>
                      <div className="newscard__content-bottom">
                        <div className="tag"><p>mới</p></div>
                        <div className="icon-warpper">
                          <img src="img/icon/share.svg" alt="" />
                        </div>
                        <span>Chia sẻ</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="scdownloadapp">
        <div className="container">
          <div className="download-content">
            <h4 className="title">Tải ứng dụng Dai-ichi Connect về điện thoại của bạn</h4>
            <div className="app">
              <div className="qr">
                <img src="img/qr.png" alt="" />
              </div>
              <div className="app__item">
                <a href={DOWNLOAD_APP_ANDROID} target="_blank"
                  ><div className="icon-warpper top"><img src="img/googleplay.png" alt="" /></div
                ></a>
                <a href={DOWNLOAD_APP_IOS} target="_blank"
                  ><div className="icon-warpper"><img src="img/applestore.png" alt="" /></div
                ></a>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  
    )
}

export default MainPotential;