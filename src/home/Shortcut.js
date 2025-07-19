import React from 'react';
import {Link, withRouter} from 'react-router-dom';
import {ACCESS_TOKEN, EDOCTOR_ID, FE_BASE_URL, FROM_HOME, PAGE_RUN, SCREENS, EDOCTOR_CODE, MY_HEALTH} from '../constants';
import {getDeviceId, getEnsc, getLinkId, getLinkPartner, getSession, setSession, isMobile} from '../util/common';
import GeneralPopup from '../components/GeneralPopup';
import {Swiper, SwiperSlide} from 'swiper/react';

class Shortcut extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showRequireLogin: false
    }
  }
  redirectPage(value) {
    var redirectLink = "";
    switch (value) {
      case 'PAGE_RUN':
        {
          redirectLink = PAGE_RUN + "' target='_blank'";
          break;
        }
      default:
        {
          redirectLink = "/" + value;
        }

    }
    return redirectLink;
  }

  render() {
    let itemList = [];
    itemList.push(
        <SwiperSlide key={'shortcus-4'}>
          <a className="swiper-slide nav-cate-item" href="/song-vui-khoe/cung-duong-yeu-thuong">
            <span className="nav-icon"><i className="ico ico-shoe ico__large"></i></span>
            <h2 className="nav-label">Cung Đường<br/>Yêu Thương</h2>
          </a>
        </SwiperSlide>);
    itemList.push(
        <SwiperSlide key={'shortcus-12'}>
          <Link className="swiper-slide nav-cate-item" to={MY_HEALTH + '/?display=qr'}>
            <span className="nav-icon"><i className="ico ico-tracking-movement ico__large"></i></span>
            <span className="nav-label">Theo dõi<br/>vận động</span>
          </Link>
        </SwiperSlide>);
    itemList.push(
      <SwiperSlide key={'shortcus-1'}>
          <Link className="swiper-slide nav-cate-item" to={MY_HEALTH + '/?display=qr'}>
              <span className="nav-icon"><i className="ico ico-heartbeat ico__large"></i></span>
              <span className="nav-label">Sức khỏe<br/>của tôi</span>
          </Link>
      </SwiperSlide>);
    // itemList.push(
    //     <SwiperSlide key={'shortcus-2'}>
    //         <Link className="swiper-slide nav-cate-item" to={"/song-vui-khoe/thu-thach-song-khoe"} onClick={() => setFromHome()}>
    //             <span className="nav-icon"><i className="ico ico-health-challenge ico__large"></i></span>
    //             <span className="nav-label">Thử thách<br/>sống khỏe</span>
    //         </Link>
    //     </SwiperSlide>);
    itemList.push(
        <SwiperSlide key={'shortcus-3'}>
          <a className="swiper-slide nav-cate-item" onClick={() => clickEdoctor()}>
            <span className="nav-icon"><i className="ico ico-consultant ico__large"></i></span>
            <span className="nav-label">Tư vấn<br/>sức khỏe</span>
          </a>
        </SwiperSlide>);
    itemList.push(
        <SwiperSlide key={'shortcus-5'}>
          <Link to={'/song-vui-khoe/bi-quyet'} className="swiper-slide nav-cate-item" onClick={() => setFromHome()}>
            <span className="nav-icon"><i className="ico ico-helth-insurance ico__large"></i></span>
            <h2 className="nav-label">Bí quyết<br/>Sống vui khỏe</h2>
          </Link>
        </SwiperSlide>);
    let props = {
      navigation: false,
      className: "nav-cate mySwiper z-index-under",
      slidesPerView: 5,
      scrollbar: {
        draggable: true,
        dragSize: 19.2
      },
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
        500: {
          slidesPerView: 4,
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
    const setFromHome = () => {
      setSession(FROM_HOME, FROM_HOME);
      callbackApp(true);
    }
    const callbackApp = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    const clickExternal = () => {
      if (!getSession(ACCESS_TOKEN)) {
        this.setState({ showRequireLogin: true });
      } else {
        getLinkId();
      }
    }
    const closeRequireLogin = () => {
      this.setState({ showRequireLogin: false });
    }

    const clickEdoctor = () => {
      let request = '';
      if (!getSession(ACCESS_TOKEN)) {
        request = {
          company: "DLVN",
          partner_code: EDOCTOR_CODE,
          deviceid: getDeviceId(),
          timeinit: new Date().getTime()
        }
        getEnsc(request, FE_BASE_URL + '/tu-van-suc-khoe');
      } else {
        getLinkPartner (EDOCTOR_ID, FE_BASE_URL + '/tu-van-suc-khoe');
      }
    }

    var html = "", cmsshortcut = null;
    if (!this.props.cmsshortcut) {
      return "";
    }
    else {
      return (
        <section className="scnews">
          <div className="container">
            <div className="scnews__head">
              <h2 className="basic-bold">Sống vui khỏe</h2>
              <p>
                Xây dựng cuộc sống khỏe mạnh & hạnh phúc với
                <span className="health-highlight-bold">{" Dai-ichi Life Việt Nam"}</span>
              </p>
              <div className='full_view_width'>
              <div className="swiper-wrapper" style={{ marginTop: 24, display: "flex", justifyContent: "center" }}>
                <Swiper {...props} >
                  {itemList}
                </Swiper>
                </div>
              </div>
            </div>
          </div>
          {this.state.showRequireLogin &&
            <GeneralPopup closePopup={closeRequireLogin} msg={'Quý khách vui lòng đăng nhập<br/>Dai-ichi Connect để tham gia Dai-ichi<br/>Life Cung Đường Yêu Thương'} imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'} buttonName={'Đăng nhập'} linkToGo={SCREENS.HOME} screenPath={SCREENS.HOME} />
          }
        </section>


      )
    }
  }
}

export default withRouter(Shortcut);