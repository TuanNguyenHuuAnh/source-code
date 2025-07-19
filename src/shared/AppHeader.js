import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import PointMenu from './PointMenu';
import UtilitiesMenu from './UtilitiesMenu';
import Notification from '../Notification/Notification';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    CLIENT_PROFILE,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FULL_NAME, PageScreen,
    RECAPTCHA_KEY,
    SCREENS,
    USER_LOGIN,
    COMPANY_KEY,
    POL_LI_LISTCLAIM_ND13_CLIENT,
    RELOAD_BELL
} from '../constants';
import {CPSaveLog, getNotification, logoutSession, getLIListForClaim} from '../util/APIUtils';
import HealthMenu from './HealthMenu';
import HeaderMobileMenu from './HeaderMobileMenu';
import {GoogleReCaptchaProvider} from "react-google-recaptcha-v3";
import Login from './Login';
import {getDeviceId, getSession, removeSession, setSession, trackingEvent} from '../util/common';
import GeneralPopup from '../components/GeneralPopup';

let menuIndex = '0';
let activeIndex = '0';
let categoryIndex = '0';
let subCategoryIndex = '0';

class AppHeader extends Component {
    constructor(props) {
        const today = new Date(),
            date = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
        super(props);
        this.state = {
            isShowNoti: false,
            jsonResponse: null,
            ClientProfile: null,
            sessionClientProfile: this.props.clientProfile,
            UserLogin: '',
            activeIndex: activeIndex,
            countNoti: 0,
            currentDate: date,
            LIClientProfile: null
        }
    }

    collapseAllsideBarMenu = () => {
        let x = document.getElementsByClassName("sidebar-item");
        for (let i = 0; i < x.length; i++) {
            x[i].className = 'sidebar-item dropdown';
        }
    }
    callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
        this.collapseAllsideBarMenu();
        setTimeout(() => {
            this.callNotificationAPI();
        }, 500);

    }

    reloadBell = () => {
        setInterval(() => {
            console.log('check reloadBell=',getSession(RELOAD_BELL));
            if (getSession(RELOAD_BELL)) {
                removeSession(RELOAD_BELL);
                this.callNotificationAPI();
            }
        }, 15000);
    }

    componentDidMount() {
        if (getSession(USER_LOGIN) && (this.state.UserLogin !== getSession(USER_LOGIN))) {
            this.callNotificationAPI();
            this.setState({UserLogin: getSession(USER_LOGIN)});
        }
        this.callLifeAssuredListAPI();
        // this.reloadBell();
    }

    componentDidUpdate() {
        if (getSession(USER_LOGIN) && (this.state.UserLogin !== getSession(USER_LOGIN))) {
            this.callNotificationAPI();
            this.setState({UserLogin: getSession(USER_LOGIN)});
            if (!getSession(POL_LI_LISTCLAIM_ND13_CLIENT)) {
                this.callLifeAssuredListAPI();
            }
        }
        if (!getSession(USER_LOGIN) && this.state.UserLogin && (this.state.UserLogin !== getSession(USER_LOGIN))) {
            this.checkAndResetState();
        }
        if (getSession(RELOAD_BELL)) {
            removeSession(RELOAD_BELL);
            this.callNotificationAPI();
        }
    }

    checkAndResetState() {
            const today = new Date(),
                date = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
            this.setState({
                isShowNoti: false,
                jsonResponse: null,
                ClientProfile: null,
                sessionClientProfile: this.props.clientProfile,
                UserLogin: '',
                activeIndex: activeIndex,
                countNoti: 0,
                currentDate: date
            });
    }

    callNotificationAPI = () => {
        const submitRequest = {
            jsonDataInput: {
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'Notification',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID) ? getSession(CLIENT_ID) : '',
                CreatedDate: '',
                DeviceId: getDeviceId(),
                Company: '',
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }
        }
        const apiRequest = Object.assign({}, submitRequest);
        //console.log(apiRequest);
        getNotification(apiRequest).then(Res => {
            let jsonState;

            let Response = Res.Response;
            console.log('response noti=', Response);
            if (Response.ErrLog === 'SUCCESSFUL') {
                jsonState = this.state;
                jsonState.jsonResponse = Response;
                jsonState.ClientProfile = Response.ClientProfile;

                jsonState.countNoti = 0;
                for (let i = 0; i < Response.ClientProfile.length; i++) {
                    if (Response.ClientProfile[i].IsView === '0') {
                        jsonState.countNoti++;
                    }
                }
                this.setState(jsonState);
            }
            if (Response.ErrLog === 'EMPTY') {
                jsonState = this.state;
                jsonState.ClientProfile = null;
                jsonState.countNoti = 0;
                this.setState(jsonState);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();
                // this.props.history.push({
                //     pathname: '/home',
                //     state: {authenticated: false, hideMain: false}
                // })
            }
        }).catch(error => {

        });
    }
    callLifeAssuredListAPI = () => {
        const apiRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                APIToken: getSession(ACCESS_TOKEN),
                Project: 'mcp',
                Action: 'LifeInsuredList',
                ClientID: getSession(CLIENT_ID),
                UserLogin: getSession(USER_LOGIN)
            }
        };
        let jsonState;
        if (!getSession(POL_LI_LISTCLAIM_ND13_CLIENT)) {
            getLIListForClaim(apiRequest).then(Res => {
            
                let Response = Res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                    jsonState = this.state;
                    jsonState.LIClientProfile = Response.ClientProfile;
                    setSession(POL_LI_LISTCLAIM_ND13_CLIENT, JSON.stringify(Response.ClientProfile));
                    console.log('Response.ClientProfile 4 claim = ', Response.ClientProfile);
                    this.setState(jsonState);
                }
            }).catch(error => {

            });
        } else {
            jsonState = this.state;
            jsonState.LIClientProfile = JSON.parse(getSession(POL_LI_LISTCLAIM_ND13_CLIENT));
            this.setState(jsonState);
        }
        
    }
    hideNotification = () => {
        let jsonState = this.state;
        if (this.state.isShowNoti === true) {
            jsonState.isShowNoti = false;
            this.setState(jsonState);
        }
    }

    showRequireLogin = () => {
        this.setState({showRequireLogin: true});
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

        if (this.props.menuIndex !== '0') {
            menuIndex = this.props.menuIndex;
        }
        if (this.props.activeIndex !== '0') {
            activeIndex = this.props.activeIndex;
        }
        if (this.props.categoryIndex !== '0') {
            categoryIndex = this.props.categoryIndex;
        }
        if (this.props.subCategoryIndex !== '0') {
            subCategoryIndex = this.props.subCategoryIndex;
        }
        const showLogin = () => {
            this.props.callbackShowLogin(true);
        }
        let logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }
        let nameArr = '';
        let name = '';
        let fullName = '';

        if (getSession(FULL_NAME)) {
            fullName = getSession(FULL_NAME);
            nameArr = fullName.split(' ');
            if (nameArr.length >= 2) {
                name = nameArr[nameArr.length - 2].substring(0, 1) + nameArr[nameArr.length - 1].substring(0, 1);
            } else if (nameArr.length === 1) {
                name = nameArr[nameArr.length - 1].substring(0, 1);
            }

        }

        const activeSelectedItemHeader = (hideMain, id, menuName, subMenuName) => {
            this.props.parentCallback(hideMain);
            this.collapseAllsideBarMenu();
        }
        const activeSelectedItemHeaderExternal = (hideMain, id, menuName, subMenuName) => {
            this.props.parentCallback(hideMain);
            this.props.callbackMenu(menuName, id);
            this.props.callbackSubMenu(subMenuName, id);
            this.collapseAllsideBarMenu();
        }
        const callbackApp2 = (hideMain) => {
            this.props.parentCallback(hideMain);
        }
        const hasPrivilege = true;

        const callNotification = () => {
            const jsonState = this.state;
            if (this.state.isShowNoti === false) {
                jsonState.isShowNoti = true;
                this.setState(jsonState);
            } else {
                jsonState.isShowNoti = false;
                this.setState(jsonState);
                this.callNotificationAPI();
            }
        };


        const showMobileHeader = () => {
            document.getElementById('header-id').className = 'mobile' + (logined ? ' logined' : '');
        }
        const showMobileSidebar = () => {
            this.props.parentCallbackShowMobileSidebar(true);
        }

        const closeRequireLogin = () => {
            this.setState({showRequireLogin: false});
        }

        return (
            <>
                <header className={logined ? "logined" : ""} id="header-id">
                    <div className="container">
                        <div className="header-warpper">
                            <div className="header__item">

                                <div className="logo"><Link to={"/"} id="logo-home-id"><img
                                    src={FE_BASE_URL + '/img/logo_white.svg'} alt=""
                                    onClick={() => activeSelectedItemHeader(false, 'ah1', 'Trang chủ', '')}/></Link>
                                </div>

                                <div className="profile" onClick={() => showMobileSidebar()}>
                                    <div id="profilebtn" className="avatar">
                                        <img src="" alt=""/>
                                        <p className="text basic-bold">{name}</p>
                                    </div>
                                    <div className="profile__item">
                                        <p>{this.props.menuName}</p>
                                        <p className="big">{this.props.subMenuName}</p>
                                    </div>
                                </div>
                                <div className="navigation">
                                    <nav>
                                        <Link to={"/"}
                                              onClick={() => activeSelectedItemHeader(false, 'ah1', 'Trang chủ', '')}>
                                            <div className={menuIndex === 'ah1' ? 'nav-item active' : 'nav-item'}
                                                 id='ah1'>
                                                Trang chủ
                                            </div>
                                        </Link>
                                        <a className={menuIndex === 'ah2' ? 'nav-item active' : 'nav-item'}
                                           style={{padding: '0px'}}
                                           href="https://www.dai-ichi-life.com.vn/vi-VN/dai-ichi-life-viet-nam/303/"
                                           target='_blank' rel='noreferrer'
                                           onClick={() => {
                                               activeSelectedItemHeaderExternal(true, 'ah2', 'Về Dai-ichi Life Việt Nam', '');
                                               this.cpSaveLog(`Web_Open_${PageScreen.ABOUT_DLVN}`);
                                               trackingEvent(
                                                   "Home",
                                                   `${PageScreen.ABOUT_DLVN}`,
                                                   `${PageScreen.ABOUT_DLVN}`,
                                               );
                                           }}>
                                            <div className={menuIndex === 'ah2' ? 'nav-item active' : 'nav-item'}
                                                 id='ah2'>
                                                Về Dai-ichi Life Việt Nam
                                            </div>
                                        </a>


                                        <div className="nav-dropdown">
                                            <div className="nav-dropdown__content"
                                                 onClick={() => activeSelectedItemHeader(true, 'ah4', 'Sống vui khoẻ', '')}>
                                                <Link to={"/song-vui-khoe"}
                                                      className={menuIndex === 'ah4' ? 'nav-item active' : 'nav-item'}
                                                      id='ah4'>Sống vui khỏe</Link>
                                            </div>
                                            {hasPrivilege && <HealthMenu activeIndex={activeIndex}
                                                                         callbackMenu={this.props.callbackMenu}
                                                                         callbackSubMenu={this.props.callbackSubMenu}
                                                                         parentCallback={this.callbackApp}
                                                                         callbackCategory={this.props.callbackCategory}
                                                                         callbackSubCategory={this.props.callbackSubCategory}
                                                                         categoryIndex={categoryIndex}
                                                                         subCategoryIndex={subCategoryIndex}
                                                                         showEdoctorLogin={this.props.showEdoctorLogin}
                                                                         showRequireLogin={this.showRequireLogin}/>}
                                        </div>
                                        <div className="nav-dropdown">
                                            <div className="nav-dropdown__content"
                                                 onClick={() => activeSelectedItemHeader(true, 'ah3', 'Điểm thưởng', '')}>
                                                <Link to={"/point"}
                                                      className={menuIndex === 'ah3' ? 'nav-item active' : 'nav-item'}
                                                      id='ah3'>Điểm thưởng</Link>
                                            </div>
                                            {hasPrivilege && <PointMenu activeIndex={activeIndex}
                                                                        callbackMenu={this.props.callbackMenu}
                                                                        callbackSubMenu={this.props.callbackSubMenu}
                                                                        parentCallback={this.callbackApp}/>}
                                        </div>

                                        <div className="nav-dropdown">
                                            <div className="nav-dropdown__content"
                                                 onClick={() => activeSelectedItemHeader(true, 'ah5', 'Tiện ích', '')}>
                                                <Link to={"/utilities"}
                                                      className={menuIndex === 'ah5' ? 'nav-item active' : 'nav-item'}
                                                      id='ah5'>Tiện ích</Link>
                                            </div>
                                            {hasPrivilege && <UtilitiesMenu activeIndex={activeIndex}
                                                                            callbackMenu={this.props.callbackMenu}
                                                                            callbackSubMenu={this.props.callbackSubMenu}
                                                                            parentCallback={this.callbackApp}/>}
                                        </div>
                                    </nav>
                                    <div className="phone">
                                        <i><img src="../../../../img/icon/phone_red.svg" alt=""/></i>
                                        <p>(028) 3810 0888</p>
                                    </div>
                                </div>
                            </div>
                            <div className="header__item right">
                                <button className="login-btn" onClick={() => showLogin()}>Đăng nhập</button>
                                {((window.location.pathname.indexOf('/song-vui-khoe/bi-quyet') < 0) && (window.location.pathname.indexOf('/timkiem') < 0) && (window.location.pathname.indexOf('/tags') < 0)) &&
                                    <div className="phone">
                                        <i><img src="../../../../img/icon/phone.svg" alt=""/></i>
                                        <p>(028) 3810 0888</p>
                                    </div>


                                }
                                {((window.location.pathname.indexOf('/song-vui-khoe/bi-quyet') >= 0) || (window.location.pathname.indexOf('/timkiem') >= 0) || (window.location.pathname.indexOf('/tags') >= 0)) &&
                                    <div className="search" onClick={() => this.props.toggleSearchBox()}>
                                        <i><img src={FE_BASE_URL + "/img/icon/search-look.svg"} alt=""/></i>
                                    </div>
                                }
                                <div className="notification"
                                     onClick={() => {
                                         callNotification();
                                     }}
                                >
                                    <i><img src={FE_BASE_URL + "/img/icon/bell.svg"} alt=""/></i>
                                    {(this.state.countNoti > 0) &&
                                        <span>{this.state.countNoti}</span>}
                                </div>
                                <div className="burger" onClick={() => showMobileHeader()}><span></span><span
                                    className="middle"></span><span></span></div>
                            </div>

                            <HeaderMobileMenu activeIndex={activeIndex} menuIndex={this.props.menuIndex}
                                              callbackMenu={this.props.callbackMenu}
                                              callbackSubMenu={this.props.callbackSubMenu}
                                              parentCallback={this.callbackApp}
                                              callbackCategory={this.props.callbackCategory}
                                              callbackSubCategory={this.props.callbackSubCategory}
                                              categoryIndex={categoryIndex} subCategoryIndex={subCategoryIndex}
                                              showEdoctorLogin={this.props.showEdoctorLogin}
                                              showRequireLogin={this.showRequireLogin}/>

                            <div className="headerbg"></div>
                        </div>

                        {this.state.isShowNoti === true && (
                            <div>
                                <Notification clientProfile={this.state.ClientProfile} parentCallback={this.callbackApp}
                                              hideNotification={this.hideNotification} LIClientProfile={this.state.LIClientProfile}/>
                            </div>
                        )}

                    </div>
                </header>
                {((!this.props.authenticated || !getSession(CLIENT_PROFILE)) && (this.props.showLogin)) && RECAPTCHA_KEY &&

                    <section>
                        <GoogleReCaptchaProvider reCaptchaKey={RECAPTCHA_KEY}>
                            <Login parentCallback={this.props.callbackFunction} hideMain={this.props.hideMain} isShowPopupApp={this.props.isShowPopupApp}
                                   callbackShowLogin={this.props.callbackShowLogin} showMoving={this.props.showMoving}
                                   showIraceQ={this.props.showIraceQ} path={this.props.path}/>
                        </GoogleReCaptchaProvider>
                    </section>

                }
                {this.state.showRequireLogin &&
                    <GeneralPopup closePopup={closeRequireLogin}
                                  msg={'Quý khách vui lòng đăng nhập<br/>Dai-ichi Connect để tham gia Dai-ichi<br/>Life Cung Đường Yêu Thương'}
                                  imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'} buttonName={'Đăng nhập'}
                                  linkToGo={SCREENS.HOME} screenPath={SCREENS.HEALTH}/>
                }
            </>

        )
    }
}

export default AppHeader;