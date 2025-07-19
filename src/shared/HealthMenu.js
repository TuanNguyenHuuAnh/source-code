import React, { Component } from 'react';
import {Link, withRouter } from 'react-router-dom';
import {
  PAGE_RUN_MENU,
  PAGE_ADVISORY,
  CMS_CATEGORY_LIST_DATA,
  CMS_SUB_CATEGORY_MAP,
  CMS_SUB_CATEGORY_ID,
  CMS_CATEGORY_CODE,
  ACCESS_TOKEN,
  FE_BASE_URL, 
  EDOCTOR_ID, 
  EDOCTOR_CODE, 
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
    const callbackAppHeader = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    const activeSelectedItemHealth = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
    }
    const activeSelectedItemHealthExternal = (hideMain, pid, id, menuName, subMenuName) => {
      callbackAppHeader(hideMain);
      this.props.callbackMenu(menuName, pid);
      this.props.callbackSubMenu(subMenuName, id);
      //irace
      // if (!getSession(ACCESS_TOKEN)) {
      //   this.props.showRequireLogin(true)
      // } else {
      //   getLinkId();
      // }
    }

    const hideMainAndCacheSubCategoryId=(hideMain, categoryCode, subCategoryId)=> {
      callbackAppHeader(hideMain);
      setSession(CMS_CATEGORY_CODE, categoryCode);
      setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
    }

    const hideMainAndCacheCategoryCode=(hideMain, categoryCode)=> {
      callbackAppHeader(hideMain);
      setSession(CMS_CATEGORY_CODE, categoryCode);
    }

    const healthConsultingList = HEALTH_CONSULTING;

    const clickEdoctor = (path) => {
      let request = '';
      if (!getSession(ACCESS_TOKEN)) {
        request = {
          company: "DLVN",
          partner_code: EDOCTOR_CODE,
          deviceid: getDeviceId(),
          timeinit: new Date().getTime()
        }
        getEnsc(request, FE_BASE_URL + path);
      } else {
        getLinkPartner (EDOCTOR_ID, FE_BASE_URL + path);
      }
    }

    const showEdoctorLogin = (path) => {
      let request = '';
      if (!getSession(ACCESS_TOKEN)) {
        this.props.showEdoctorLogin(path, true);
      } else {
        getLinkPartner (EDOCTOR_ID, FE_BASE_URL + path);
      }
    }

    return (
      <div className="nav-dropdown__items" >
        <div className="nav-dropdown-tab-wrapper">
          <Link className="nav-dropdown-tab" to={'/song-vui-khoe/cung-duong-yeu-thuong'} onClick={() => activeSelectedItemHealthExternal(true, 'ah4', 'h1', 'Sống khoẻ', 'Cung Đường Yêu Thương')}><span className={this.props.activeIndex === 'h1' ? 'bold' : ''} id='h1'>Cung Đường Yêu Thương</span></Link>

          <Link className="nav-dropdown-tab" to={MY_HEALTH + '/?display=qr'} onClick={() => activeSelectedItemHealth(true, 'ah4', 'h6', 'Sống khoẻ', 'Theo dõi vận động')}><span className={this.props.activeIndex === 'h6' ? 'bold' : ''} id='h6'>Theo dõi vận động</span></Link>

          <Link className="nav-dropdown-tab" to={MY_HEALTH + '/?display=qr'} onClick={() => activeSelectedItemHealth(true, 'ah4', 'h2', 'Sống khoẻ', 'Sức khỏe của tôi')}><span className={this.props.activeIndex === 'h2' ? 'bold' : ''} id='h2'>Sức khỏe của tôi</span></Link>
        
        
          {/* <Link className="nav-dropdown-tab" to={'/song-vui-khoe/thu-thach-song-khoe'} onClick={() => activeSelectedItemHealth(true, 'ah4', 'h3', 'Sống khoẻ', 'Thử thách sống khỏe')}><span className={this.props.activeIndex === 'h3' ? 'bold' : ''} id='h3'>Thử thách sống khỏe</span></Link> */}
          {/*Tư vấn sức khỏe*/}
          <a className="nav-dropdown-tab" onClick={(e) => {
            e.stopPropagation();
            clickEdoctor("/tu-van-suc-khoe");
          }}>
            <span className={this.props.activeIndex === 'h5' ? 'bold' : ''} id='h5' onClick={() => activeSelectedItemHealth(true, 'ah4', 'h5', 'Sống khoẻ', 'Tư vấn sức khỏe')}>Tư vấn sức khỏe</span>
            <div className="nav-dropdown__sub-items" >
              <div className="sub-nav-dropdown-tab-wrapper">
                {healthConsultingList &&
                    healthConsultingList.map((item, index) => {
                      return (
                          <a className="sub-nav-dropdown-tab" key={'edoctor-advice-' + index} onClick={(e) => {
                            e.stopPropagation();
                            if ((item.code === "QA_DOCTOR" || item.code === "CHAT_TO_DOCTOR" || item.code === "VIDEO_TO_DOCTOR")) {
                              clickEdoctor("/tu-van-suc-khoe/" + item.linkAlias);
                            } else {
                              showEdoctorLogin("/tu-van-suc-khoe/" + item.linkAlias);
                            }
                          }}>
                            <span className={this.props.categoryIndex === item.code ? 'bold' : ''} id={item.code} onClick={() => hideMainAndCacheCategoryCode(true, item.code)}>{item.label}</span>
                            {
                                item.childMenu && (
                                    <div className="nav-dropdown__super-sub-items" key={'edoctor-consulting-' + index}>
                                      <div className="super-sub-nav-dropdown-tab-wrapper">
                                        {item.childMenu.map((subcat, idx) => (
                                            <a className="super-sub-nav-dropdown-tab" key={'subcat-' + idx} onClick={(e) => {
                                              e.stopPropagation();
                                              showEdoctorLogin("/tu-van-suc-khoe/" + subcat.linkAlias);
                                            }}>
                                              <span className={this.props.subCategoryIndex === 'sub' + subcat.id ? 'bold' : ''} id={'sub' + subcat.id}>{subcat.label}</span>
                                            </a>
                                        ))}
                                      </div>
                                    </div>
                                )
                            }

                          </a>
                      )
                    })
                }
              </div>
            </div>
          </a>
          <Link className="nav-dropdown-tab" to={"/song-vui-khoe/bi-quyet"}>
            <span className={this.props.activeIndex === 'h4' ? 'bold' : ''} id='h4' onClick={() => activeSelectedItemHealth(true, 'ah4', 'h4', 'Sống khoẻ', 'Bí quyết Sống vui khỏe')}>Bí quyết Sống vui khỏe</span>
            <div className="nav-dropdown__sub-items" >
              <div className="sub-nav-dropdown-tab-wrapper">
                {categoryList &&
                  categoryList.map((item, index) => {
                    return (

                      <Link className="sub-nav-dropdown-tab" to={"/song-vui-khoe/bi-quyet/" + item.linkAlias} key={'link-health-content' + index}>
                        <span className={this.props.categoryIndex === item.code ? 'bold' : ''} id={item.code} onClick={() => hideMainAndCacheCategoryCode(true, item.code)}>{item.label}</span>
                        <div className="nav-dropdown__super-sub-items" key={'div-health-content' + index}>
                          <div className="super-sub-nav-dropdown-tab-wrapper">
                            {getMapSize(subCategoryMap) > 0 && subCategoryMap[item.code] &&
                              subCategoryMap[item.code].map((subcat, idx) => {
                                return (

                                  <Link className="super-sub-nav-dropdown-tab" to={"/song-vui-khoe/bi-quyet/" + item.linkAlias + "/" + StringToAliasLink(subcat.label)} onClick={() => hideMainAndCacheSubCategoryId(true, item.code, subcat.id)} key={'sub-health-content' + index + '-' + idx}>
                                    <span className={this.props.subCategoryIndex === 'sub' + subcat.id ? 'bold' : ''} id={'sub' + subcat.id}>{subcat.label}</span>
                                  </Link>

                                )
                              })


                            }

                          </div>

                        </div>
                      </Link>

                    )
                  })


                }
              </div>

            </div>
          </Link>
        </div>
      </div>
    )
  }
}

export default withRouter(HealthMenu);
