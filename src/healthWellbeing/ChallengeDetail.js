import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
  ACCESS_TOKEN,
  FROM_SCREEN_APP,
  CMS_IMAGE_URL,
  WEB_BROWSER_VERSION,
  FROM_APP,
  CMS_SUB_CATEGORY_MAP,
  IS_MOBILE,
  AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen, FE_BASE_URL
} from '../constants';
import { CPSaveLog, getChallengeDetail } from '../util/APIUtils';
import '../common/Common.css';
import {
  postMessageSubNativeIOS,
  formatDate,
  shortFormatDate,
  numOfDates,
  getUrlParameter,
  appendScript,
  removeScript,
  removeIframe,
  setSession,
  getSession,
  isMobile,
  getDeviceId, trackingEvent
} from '../util/common';
// import ReactHtmlParser from "react-html-parser";
import parse from 'html-react-parser';
import ShareZaloButton from './ShareZaloButton';
import { Swiper, SwiperSlide } from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {
  Autoplay, Pagination, Navigation
} from 'swiper';
import { Helmet } from "react-helmet";

SwiperCore.use([Autoplay, Pagination, Navigation]);
class ChallengeDetail extends Component {
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
      tagList: null,
      tagUsed: null
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
    this.getDetail();
    this.topFunction();
    document.addEventListener("scroll", this.scrollFunction, true);
    // setTimeout(this.appendScript, 150);
    appendScript("/js/zalo.sdk.js");
    let display = getUrlParameter("display") ? getUrlParameter("display") : '';
    if (display === 'qr') {
      // setTimeout(this.showQRC, 5000);
      this.showQRC();
    }
    this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`);
    trackingEvent(
      "Sống vui khỏe",
      `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`,
      `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`,
    );
  }

  componentWillUnmount() {
    document.removeEventListener("scroll", this.scrollFunction, true);
    removeScript("/js/zalo.sdk.js");
    removeIframe();

    this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`);
    trackingEvent(
      "Sống vui khỏe",
      `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`,
      `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES}`,
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
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }



  getDetail() {
    let challengesRequest = {
      SubmitFrom: WEB_BROWSER_VERSION,
      ChallengeID: this.props.match.params.id
    }
    getChallengeDetail(challengesRequest).then(response => {
      this.setState({ data: response.data });
    }).catch(error => {
      console.log(error);
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

    const adjustJustify = () => {
      if (document.getElementsByClassName('swiper-wrapper') && document.getElementsByClassName('swiper-wrapper')[0]) {
        document.getElementsByClassName('swiper-wrapper')[0].className = 'swiper-wrapper swiper-justify-left';
      }
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

    const showQRC = () => {
      if (isMobile()) {
        document.getElementById('popup-DownloadnExperience').className = "popup special point-error-popup show";
      } else {
        document.getElementById('popup-QRC').className = "popup special point-error-popup show";
      }
    }

    let itemList = [];
    itemList.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide className="breadcrums__item">
        <p><Link to="/song-vui-khoe/thu-thach-song-khoe" className='breadcrums__link'>Thử thách Sống khỏe</Link></p>
        <p className='breadcrums__item_arrow'>&gt;</p>
      </SwiperSlide>);
    itemList.push(
      <SwiperSlide className="breadcrums__item">
        <Link to={"/song-vui-khoe/thu-thach-song-khoe/" + this.props.match.params.id}><p>Chi tiết thử thách</p></Link>
        <p className='breadcrums__item_arrow'>&gt;</p>
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

    const shareZl = () => {
      if (document.getElementById('zalo-ref')) {
        document.getElementById('zalo-ref').click();
      }
    }

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
    let markerLoc = null;
    if (this.state.data && this.state.data.hasEvent) {
      markerLoc = { lat: this.state.data.lattitude, lng: this.state.data.longtitude };
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
            <title data-react-helmet="true">{this.state.data.title}</title>
            <meta name="description" content={this.state.data.description?(this.state.data.description.replaceAll('<p>','').replaceAll('</p>','').replaceAll('<strong>','').replaceAll('</strong>','').replaceAll('<i>','').replaceAll('</i>','').replaceAll('<em>','').replaceAll('</em>','')):''} />
            <meta name="keywords" content="thử thách sống khỏe, dai-ichi life việt nam, dai-ichi connect, sống vui sống khỏe" />
            <meta property="og:title" content={this.state.data.title} data-react-helmet="true" />
            <meta property="og:description" content={this.state.data.description?(this.state.data.description.replaceAll('<p>','').replaceAll('</p>','').replaceAll('<strong>','').replaceAll('</strong>','').replaceAll('<i>','').replaceAll('</i>','').replaceAll('<em>','').replaceAll('</em>','')):''} />
            <meta name="robots" content={indexFollow} />
            {/*<meta property="og:type" content="article" />*/}
            {/*<link rel="canonical" href={this.state.data.canonical} />*/}
            <meta property="og:url" content={window.location.href} data-react-helmet="true" />
            <meta property="og:image" content={this.state.data.imageUrl ? this.state.data.imageUrl : (FE_BASE_URL + "/img/meta-logo.jpg")} />
          </Helmet>
        }

        <main className={logined ? "article-page logined" + paddingTop : "article-page" + paddingTop}>
          <div className="container">
            {!getSession(FROM_APP) &&
              <ul className="list-social hide" id="list-social-id">
                <li className="tooltip">
                  <a href={`https://www.facebook.com/sharer/sharer.php?u=${window.location.href}`} onMouseOut={() => outFunc('myTooltipFacebook', 'Chia sẻ bài viết lên Facebook')} target={getSession(FROM_APP) ? '' : '_blank'}><i className="ico ico-fb-c ico__large"></i></a>
                  <span className="tooltiptext" id="myTooltipFacebook">Chia sẻ bài viết lên Facebook</span>
                </li>
                <li className="tooltip">
                  <ShareZaloButton clsName={'ico ico-zalo-c ico__large'} onMouseOut={() => outFunc('myTooltipZalo', 'Chia sẻ bài viết lên Zalo')} />
                  {/* <a onMouseOut={() => outFunc('myTooltipZalo', 'Chia sẻ bài viết lên Zalo')} ><i className="ico ico-zalo-c ico__large"></i></a> */}
                  <span className="tooltiptext" id="myTooltipZalo">Chia sẻ bài viết lên Zalo</span>
                </li>
                <li className="tooltip">
                  <a href={`mailto:?subject=${this.state.data ? this.state.data.title : ''}&body=${window.location.href}`} onMouseOut={() => outFunc('myTooltipEmail', 'Chia sẻ bài viết qua email')}><i className="ico ico-mail-c ico__large"></i></a>
                  <span className="tooltiptext" id="myTooltipEmail">Chia sẻ bài viết qua email</span>
                </li>
                <li className="tooltip">
                  <a onClick={() => copyToClipBoard('myTooltipCopy')} onMouseOut={() => outFunc('myTooltipCopy', 'Sao chép đường link bài viết')}><i className="ico ico-link-c ico__large"></i></a>
                  <span className="tooltiptext" id="myTooltipCopy">Sao chép đường link bài viết</span>
                </li>
                <li>
                  {/* <a href="#"><i className="ico ico-bookmark-c ico__large"></i></a> */}
                </li>
              </ul>
            }
            {!getSession(FROM_APP) ? (
              <Swiper {...props} onSwiper={(swiper) => slideToEnd(swiper)} onReachEnd={(swiper) => slideToEnd(swiper)}>
                {itemList}
              </Swiper>
            ) : (
              <div className="breadcrums"></div>
            )

            }
            {this.state.data &&
              <>
                <div className="article-section justify-content-center" style={{marginTop: '0'}}>

                  <span className="tooltiptext" id="tooltip-social"></span>
                  <div className="challenge-wrap">
                    {/* <p className="article-author">Bài viết được tư vấn chuyên môn bởi Bác sĩ chuyên khoa II <span >Nguyễn Thái Hưng</span></p> */}
                    <div className="article-content" style={{marginTop: '0'}}>

                      <img className='challenge-thumb' src={this.state.data.imageUrl} alt="" />
                      <ul className="challenge-socials">

                        <li className="tooltip">
                          <a href={`https://www.facebook.com/sharer/sharer.php?u=${window.location.href}`} onMouseOut={() => outFunc('myTooltipFacebook-socials', 'Chia sẻ bài viết lên Facebook')} target={getSession(FROM_APP) ? '' : '_blank'}><i className="ico ico-fb-c"></i></a>
                          <span className="tooltiptext" id="myTooltipFacebook-socials">Chia sẻ bài viết lên Facebook</span>
                        </li>
                        <li className="tooltip" id="share-zalo-id">
                          <ShareZaloButton clsName={'ico ico-zalo-c'} onMouseOut={() => outFunc('myTooltipZalo-socials', 'Chia sẻ bài viết lên Zalo')} />
                          {/* <a onMouseOut={() => outFunc('myTooltipZalo-socials', 'Chia sẻ bài viết lên Zalo')} ><i className="ico ico-zalo-c"></i></a> */}
                          <span className="tooltiptext" id="myTooltipZalo-socials">Chia sẻ bài viết lên Zalo</span>
                        </li>
                        <li className="tooltip">
                          <a href={`mailto:?subject=${this.state.data ? this.state.data.title : ''}&body=${window.location.href}`} onMouseOut={() => outFunc('myTooltipEmail-socials', 'Chia sẻ bài viết qua email')}><i className="ico ico-mail-c"></i></a>
                          <span className="tooltiptext" id="myTooltipEmail-socials">Chia sẻ bài viết qua email</span>
                        </li>
                        <li className="tooltip">

                          <a onClick={() => copyToClipBoard('myTooltip-socials')} onMouseOut={() => outFunc('myTooltip-socials', 'Sao chép đường link')}><i className="ico ico-link-c"></i></a>
                          <span className="tooltiptext" id="myTooltip-socials">Sao chép đường link</span>
                        </li>
                        <li>
                          {/* <a href="#"><i className="ico ico-bookmark-c"></i></a> */}
                        </li>
                      </ul>
                      <p className="challenges-heading">{this.state.data.title}</p>
                      {/* <h3>{this.state.data.shortContent}</h3> */}
                      {parse(this.state.data.description ? this.state.data.description.replaceAll('<table', '<table className=\"table\"') : this.state.data.description, { trim: true })}

                    </div>
                    <div class="challenge-event-block" id="countdown-block">
                          <div class="challenge-event-content">
                            <div class="participant-info">
                              <div class="participant-info-item">
                                <table>
                                  <tr>
                                    <td><i class="ico ico-challenge-date"></i></td>
                                    <td className='challenge-detail-box'>									
                                      <span className='font-size-event'>{shortFormatDate(this.state.data.start_date)}-{formatDate(this.state.data.end_date)}</span>
                                      <p className='font-size-challenge'>{'Còn lại ' + numOfDates(new Date(), this.state.data.end_date) + ' ngày'}</p>
                                    </td>
                                  </tr>
                                </table>
                                

                              </div>
                              <div class="participant-info-item">
                              <table>
                                  <tr>
                                    <td><i class="ico ico-challenge-joined"></i></td>
                                    <td className='challenge-detail-box'>									
                                    <span className='font-size-event'>{this.state.data.numberOfParticipants + ' người đã tham gia'}</span>                                    </td>
                                  </tr>
                                </table>
                              </div>
                            </div>

                          </div>
                        </div>
                    <div className="btn-wrapper" style={{justifyContent: 'center'}}>
                      <button className="btn btn-primary" onClick={()=>showQRC()} style={{width: '100%', height: 50, textAlign: 'center', justifyContent:'center', marginLeft: 'auto', marginRight: 'auto', marginBottom: '36px'}}>{this.state.data.isEnrolled?'Đã tham gia':'Tham gia'}</button>
                    </div>
                  </div>

                </div>


                {/* <!-- Quiz Section --> */}

                {/* <!-- End Quiz Section --> */}

              </>
            }


          </div>
          {getSession(FROM_APP) && getSession(FROM_APP) === "IOS" &&
            <div className='padding-bottom-60'></div>
          }
        </main >
      </>


    )
  }

}

export default ChallengeDetail;