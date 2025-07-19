import React, { Component } from 'react';
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, PageScreen, USER_LOGIN, FE_BASE_URL} from '../constants';
import { Link } from 'react-router-dom';
import HealthMenuMobile from './HealthMenuMobile';
import PointMobileMenu from './PointMobileMenu';
import UtilitiesMobileMenu from './UtilitiesMobileMenu';
import {getDeviceId, getSession} from '../util/common';
import {CPSaveLog} from "../util/APIUtils";

class HeaderMobileMenu extends Component {
  constructor(props) {
    super(props);
    this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
    this.checkHideMobileMenu = this.checkHideMobileMenu.bind(this);
  }
  componentDidMount() {
    document.addEventListener("mousedown", this.checkHideMobileMenu);
  }

  componentWillUnmount() {
    document.removeEventListener("mousedown", this.checkHideMobileMenu);
  }
  setWrapperRef(node) {
    this.wrapperSucceededRef = node;
  }
  checkHideMobileMenu = (event) => {
    if (this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) {
      document.getElementById('header-id').className = getSession(ACCESS_TOKEN) ? 'logined' : '';
    }
  }
  collapseAllsideBarMenu = () => {
		let x = document.getElementsByClassName("sidebar-item");
		for (let i = 0; i < x.length; i++) {
			x[i].className = 'sidebar-item dropdown';
		}
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
  render() {
		const hideMobileHeader = () => {
			document.getElementById('header-id').className = getSession(ACCESS_TOKEN) ? 'logined' : '';
		}
		const toggleMenu = (id) => {
			if (document.getElementById(id).className === 'dropdown mobilenav-item' || document.getElementById(id).className === 'dropdown mobilenav-item active') {
				collapseAll();
        document.getElementById(id).className = 'dropdown mobilenav-item active show';
			} else {
        collapseAll();
				document.getElementById(id).className = 'dropdown mobilenav-item active';
			}

		}
    const collapseAll=() =>{
      let x = document.getElementsByClassName("dropdown mobilenav-item");
      for (let i = 0; i < x.length; i++) {
        x[i].className = 'dropdown mobilenav-item';
      }
    }

		const activeSelectedItemHeader = (hideMain, id, menuName, subMenuName) => {
			this.props.parentCallback(hideMain);
			this.props.callbackMenu(menuName, id);
			this.props.callbackSubMenu(subMenuName, id);
			this.collapseAllsideBarMenu();
      hideMobileHeader();
		}
		const activeSelectedItemHeaderHideMenu = (hideMain, id, menuName, subMenuName) => {
      activeSelectedItemHeader(hideMain, id, menuName, subMenuName);
      hideMobileHeader();
		}

    return (
      <div className="mobile-navigation" ref={this.handlerSetWrapperRef}>
        <div className="mobile-navigation__header" onClick={() => hideMobileHeader()}>
          <i className="closebtn"><img src={FE_BASE_URL+ "/img/icon/close.svg"} alt="" /></i>
        </div>
        <div className="mobile-navigation__body">
          <div className="mobilenav-items-wrapper">
            <Link to={"/"} onClick={() => activeSelectedItemHeaderHideMenu(false, 'ah1', 'Trang chủ', '')}>
              <div className={this.props.menuIndex === 'ah1' ?"mobilenav-item active":"mobilenav-item"}>
                <a className="mobilenav-item-tab">Trang chủ</a>
              </div>
            </Link>
            <a href="https://www.dai-ichi-life.com.vn/vi-VN/dai-ichi-life-viet-nam/303/"
              target='_blank' rel='noreferrer' onClick={() => {
                activeSelectedItemHeaderHideMenu(true, 'ah2', 'Về Dai-ichi Life Việt Nam', '');
                this.cpSaveLog(`Web_Open_${PageScreen.ABOUT_DLVN}`);
              }}>

              <div className={this.props.menuIndex === 'ah2' ?"mobilenav-item active":"mobilenav-item"}>
                <a className="mobilenav-item-tab">Về Dai-ichi Life Việt Nam</a>
              </div>
            </a>
            <div className={this.props.menuIndex === 'ah4' ?"dropdown mobilenav-item active show":"dropdown mobilenav-item"} id="health-moblie">
              <div className="dropdown__content">
                <div>
                  <div className="mobilenav-item-tab">
                  <Link to={"/song-vui-khoe"} onClick={() => activeSelectedItemHeader(true, 'ah4', 'Sống vui khoẻ', '')}><p>Sống vui khỏe</p></Link>
                    <div className="arrow" onClick={() => toggleMenu('health-moblie')}>&gt;</div>
                  </div>
                </div>
              </div>
              <HealthMenuMobile activeIndex={this.props.activeIndex} callbackMenu={this.props.callbackMenu} callbackSubMenu={this.props.callbackSubMenu} parentCallback={this.props.parentCallback} callbackCategory={this.props.callbackCategory} callbackSubCategory={this.props.callbackSubCategory} categoryIndex={this.props.categoryIndex} subCategoryIndex={this.props.subCategoryIndex}  showRequireLogin={this.props.showRequireLogin} showEdoctorLogin={this.props.showEdoctorLogin}/>
            </div>
            <div className="dropdown mobilenav-item" id="point-mobile">
              <div className="dropdown__content">
                <div>
                  <div className="mobilenav-item-tab">
                  <Link to={"/point"} onClick={() => activeSelectedItemHeader(true, 'ah3', 'Điểm thưởng', '')}><p>Điểm thưởng</p></Link>
                    <div className="arrow" onClick={() => toggleMenu('point-mobile')}>&gt;</div>
                  </div>
                </div>
              </div>
              <PointMobileMenu activeIndex={this.props.activeIndex} callbackMenu={this.props.callbackMenu} callbackSubMenu={this.props.callbackSubMenu} parentCallback={this.props.parentCallback} />
            </div>
            <div className="dropdown mobilenav-item" id="utilities-mobile">
              <div className="dropdown__content">
                <div>
                  <div className="mobilenav-item-tab">
                  <Link to={"/utilities"} onClick={() => activeSelectedItemHeader(true, 'ah5', 'Tiện ích', '')}><p>Tiện ích</p></Link>
                    <div className="arrow" onClick={() => toggleMenu('utilities-mobile')}>&gt;</div>
                  </div>
                </div>
              </div>
              <UtilitiesMobileMenu activeIndex={this.props.activeIndex} callbackMenu={this.props.callbackMenu} callbackSubMenu={this.props.callbackSubMenu} parentCallback={this.props.parentCallback} />
            </div>
          </div>

          <div className="contact">
            <i><img src={FE_BASE_URL + "/img/icon/phone_red.svg"} alt="" /></i>
            <p>(028) 3810 0888</p>
          </div>
        </div>
      </div>
    );
  }

}




export default HeaderMobileMenu;