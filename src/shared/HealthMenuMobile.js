import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
  PAGE_RUN_MENU,
  PAGE_ADVISORY,
  ACCESS_TOKEN,
  CMS_CATEGORY_LIST_DATA,
  CMS_SUB_CATEGORY_MAP,
  CMS_SUB_CATEGORY_ID,
  CMS_CATEGORY_CODE,
  EDOCTOR_CODE, 
  EDOCTOR_ID,
  MY_HEALTH
} from '../constants';
import {
  StringToAliasLink,
  getMapSize,
  setSession,
  getSession,
  getLinkId,
  getDeviceId,
  getEnsc,
  getLinkPartner,
  isMobile
} from '../util/common';
import {HEALTH_CONSULTING} from "./dummyData";

class HealthMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeIndex: '0'

    };
  }

  render() {
    let categoryList = JSON.parse(getSession(CMS_CATEGORY_LIST_DATA));
    let subCategoryMap = JSON.parse(getSession(CMS_SUB_CATEGORY_MAP));
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    const callbackAppHeader = (hideMain) => {
      this.props.parentCallback(hideMain);
    }

    const activeSelectedItemHealthMobile = (hideMain, categoryCode, subCategoryId) => {
      callbackAppHeader(hideMain);
      setSession(CMS_CATEGORY_CODE, categoryCode);
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
      hideMobileHeader();
    }

    const activeSelectedItemHealthMobileNoClose = (hideMain, pid, id, menuName, subMenuName, subMId) => {
      callbackAppHeader(hideMain);
      // this.props.callbackMenu(menuName, pid);
      // this.props.callbackSubMenu(subMenuName, id);
      hideMobileHeader();

    }


    const activeSelectedItemHealthMobileSubNoClose = (hideMain, categoryCode, ssubMId) => {
      callbackAppHeader(hideMain);
      setSession(CMS_CATEGORY_CODE, categoryCode);
      hideMobileHeader();
    }

    const activeSelectedItemHealthMobileExternal = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      this.props.callbackMenu(menuName, pid);
      this.props.callbackSubMenu(subMenuName, id);
      hideMobileHeader();
      //irace
      if (!getSession(ACCESS_TOKEN)) {
        // this.setState({ showRequireLogin: true });
        this.props.showRequireLogin(true)
      } else {
        getLinkId();
      }
    }

    const hideMobileHeader = () => {
      document.getElementById('header-id').className = logined ? 'logined' : '';
    }
    const toggleSubMenu = (id) => {
      if (document.getElementById(id).className === 'mobile-dropdown-tab') {
        collapseAllSub();
        document.getElementById(id).className = 'mobile-dropdown-tab show';
      } else {
        collapseAllSub();
        // document.getElementById(id).className = 'mobilenav-sub-item active';
      }

    }

    const toggleSuperSubMenu = (id) => {
      if (document.getElementById(id).className === 'sub-mobile-dropdown-tab') {
        collapseAllSuperSub();
        document.getElementById(id).className = 'sub-mobile-dropdown-tab show';
      } else {
        collapseAllSuperSub();
        // document.getElementById(id).className = 'mobilenav-sub-item active';
      }

    }

    const collapseAllSub = () => {
      let x = document.getElementsByClassName("mobile-dropdown-tab");
      for (let i = 0; i < x.length; i++) {
        x[i].className = 'mobile-dropdown-tab';
      }
    }

    const collapseAllSuperSub = () => {
      let x = document.getElementsByClassName("sub-mobile-dropdown-tab");
      for (let i = 0; i < x.length; i++) {
        x[i].className = 'sub-mobile-dropdown-tab';
      }
    }

    const clickEdoctor = (path, hideMain, categoryCode, ssubMId) => {
      let request = '';
      if (!getSession(ACCESS_TOKEN)) {
        request = {
          company: "DLVN",
          partner_code: EDOCTOR_CODE,
          deviceid: getDeviceId(),
          timeinit: new Date().getTime()
        }
        getEnsc(request, path);
      } else {
        getLinkPartner (EDOCTOR_ID, path);
      }
      callbackAppHeader(hideMain);
      setSession(CMS_CATEGORY_CODE, categoryCode);
      hideMobileHeader();
    }

    const showEdoctorLogin = (path, hideMain, categoryCode, ssubMId) => {
      if (!getSession(ACCESS_TOKEN)) {
        this.props.showEdoctorLogin(path, true);
      } else {
        getLinkPartner (EDOCTOR_ID, path);
        callbackAppHeader(hideMain);
        setSession(CMS_CATEGORY_CODE, categoryCode);
        hideMobileHeader();
      }

    }

    const healthConsultingList = HEALTH_CONSULTING;

    return (
      <div className="dropdown__items" >
        <Link className="mobile-dropdown-tab" to={'/song-vui-khoe/cung-duong-yeu-thuong'} onClick={() => activeSelectedItemHealthMobile(true, 'ah4', 'h1', 'Sống khoẻ', 'Cung Đường Yêu Thương')}><span className={this.props.activeIndex === 'h1' ? 'bold' : ''} id='h1'>Cung Đường Yêu Thương</span></Link>
        <Link className="mobile-dropdown-tab" to={MY_HEALTH + '/?display=qr'} onClick={() => activeSelectedItemHealthMobile(true, 'ah4', 'h2', 'Sống khoẻ', 'Sức khỏe của tôi')}><span className={this.props.activeIndex === 'h2' ? 'bold' : ''} id='h2'>Sức khỏe của tôi</span></Link>
        <Link className="mobile-dropdown-tab" to={MY_HEALTH + '/?display=qr'} onClick={() => activeSelectedItemHealthMobile(true, 'ah4', 'h2', 'Sống khoẻ', 'Theo dõi vận động')}><span className={this.props.activeIndex === 'h6' ? 'bold' : ''} id='h6'>Theo dõi vận động</span></Link>
        {/* <Link className="mobile-dropdown-tab" to={'/song-vui-khoe/thu-thach-song-khoe'} onClick={() => activeSelectedItemHealthMobile(true, 'ah4', 'h3', 'Sống khoẻ', 'Thử thách sống khỏe')}><span className={this.props.activeIndex === 'h3' ? 'bold' : ''} id='h3'>Thử thách sống khỏe</span></Link> */}
        <div className="mobile-dropdown-tab" id="mobile-sub-menu-sec">
          <div className="dropdown__content" >
            <a onClick={(e) => {
              e.preventDefault();
              clickEdoctor("/tu-van-suc-khoe");
            }} id="mobile-sub-menu-sec"><span className={this.props.activeIndex === 'h5' ? 'bold' : ''} id='h5' onClick={() => activeSelectedItemHealthMobileNoClose(true, 'ah4', 'h5', 'Sống vui khoẻ', 'Tư vấn sức khỏe', 'mobile-sub-menu-sec')}>Tư vấn sức khỏe</span></a>
            <div className="arrow" onClick={() => toggleSubMenu('mobile-sub-menu-sec')}>&gt;</div>
          </div>
          <div className='mobilenav-sub-item'>
            {healthConsultingList &&
                healthConsultingList.map((item, index) => {
                  return (
                      <div key={`mobile-super-sub-menu-${item.code}`} className="sub-mobile-dropdown-tab" id={"mobile-super-sub-menu" + item.code}>
                        <div className="dropdown__content">
                          <div>
                            <a id={"mobile-super-sub-menu" + item.code}>
                            <span className={this.props.categoryIndex === item.code ? 'bold' : ''} id={"mobile-super-sub-menu" + item.code} onClick={(e) => {
                              e.preventDefault();
                              if (item.code === "QA_DOCTOR" || item.code === "CHAT_TO_DOCTOR" || item.code === "VIDEO_TO_DOCTOR") {
                                clickEdoctor("/tu-van-suc-khoe/" + item.linkAlias, true, item.code, "mobile-super-sub-menu" + item.code);
                              } else {
                                showEdoctorLogin("/tu-van-suc-khoe/" + item.linkAlias, true, item.code, "mobile-super-sub-menu" + item.code);
                              }
                            }}>{item.label}</span>
                            </a>
                            {item.childMenu &&
                                <div className="arrow" onClick={() => toggleSuperSubMenu("mobile-super-sub-menu" + item.code)}>&gt;</div>
                            }
                          </div>
                          {item.childMenu &&
                              <div className='mobilenav-super-sub-item'>
                                {item.childMenu.map((subcat, idx) => {
                                  const subcatKey = subcat.id !== undefined ? subcat.id : idx; // Sử dụng idx nếu subcat.id là undefined
                                  return (
                                      <a key={`subcat-${subcatKey}`} className="mobilenav-super-sub-item-tab" onClick={(e) => {
                                        e.stopPropagation();
                                        showEdoctorLogin("/tu-van-suc-khoe/" + subcat.linkAlias);
                                      }}>
                                        <span className={this.props.subCategoryIndex === 'sub' + subcat.id ? 'bold' : ''} id={'sub' + subcatKey}>{subcat.label}</span>
                                      </a>
                                  );
                                })}
                              </div>
                          }

                        </div>
                      </div>
                  );
                })
            }


          </div>

        </div>
        
        {/* <a className="mobile-dropdown-tab" href={PAGE_ADVISORY} target="_blank" onClick={() => activeSelectedItemHealthMobileExternal(true, 'ah4', 'h2', 'Sống khỏe', 'Tư vấn sức khỏe')}><span className={this.props.activeIndex === 'h2' ? 'bold' : ''} id='h2'>Tư vấn sức khỏe</span></a> */}
        {/* <Link className="mobile-dropdown-tab" to={"/healthnews"} onClick={() => activeSelectedItemHealthMobile(true, 'ah4', 'h3', 'Sống khỏe', 'Bản tin sống khỏe')}><span className={this.props.activeIndex === 'h3' ? 'bold' : ''} id='h3'>Bản tin sống khỏe</span></Link> */}
        <div className="mobile-dropdown-tab" id="mobile-sub-menu">
          <div className="dropdown__content" >
            <Link to={"/song-vui-khoe/bi-quyet"} id="mobile-sub-menu"><span className={this.props.activeIndex === 'h4' ? 'bold' : ''} id='h4' onClick={() => activeSelectedItemHealthMobileNoClose(true, 'ah4', 'h4', 'Sống vui khoẻ', 'Bí quyết Sống vui khỏe', 'mobile-sub-menu')}>Bí quyết Sống vui khỏe</span></Link>
            <div className="arrow" onClick={() => toggleSubMenu('mobile-sub-menu')}>&gt;</div>
          </div>
          <div className='mobilenav-sub-item'>
            {categoryList &&
              categoryList.map((item, index) => {
                return (
                  <div className="sub-mobile-dropdown-tab" id={"mobile-super-sub-menu" + item.code} key={'div-health-content-mobile' + index}>
                    <div className="dropdown__content">
                      <div>
                        <Link to={"/song-vui-khoe/bi-quyet/" + item.linkAlias} id={"mobile-super-sub-menu" + item.code}><span className={this.props.categoryIndex === item.code ? 'bold' : ''} id={"mobile-super-sub-menu" + item.code} onClick={() => activeSelectedItemHealthMobileSubNoClose(true, item.code, "mobile-super-sub-menu" + item.code)}>{item.label}</span></Link>
                        <div className="arrow" onClick={() => toggleSuperSubMenu("mobile-super-sub-menu" + item.code)}>&gt;</div>
                      </div>
                      <div className='mobilenav-super-sub-item'>
                        {getMapSize(subCategoryMap) > 0 && subCategoryMap[item.code] &&
                          subCategoryMap[item.code].map((subcat, idx) => {
                            return (
                              <Link className="mobilenav-super-sub-item-tab" to={"/song-vui-khoe/bi-quyet/" + item.linkAlias + "/" + StringToAliasLink(subcat.label)} key={'sub-health-content-mobile' + index + '-' + idx}>
                                <span className={this.props.subCategoryIndex === 'sub' + subcat.id ? 'bold' : ''} id={'sub' + subcat.id} onClick={() => activeSelectedItemHealthMobile(true, item.code, subcat.id)}>{subcat.label}</span>
                              </Link>
                            )
                          })


                        }

                      </div>
                    </div>

                  </div>
                )
              })
            }

          </div>

        </div>
      </div>
    )
  }
}

export default HealthMenu;
