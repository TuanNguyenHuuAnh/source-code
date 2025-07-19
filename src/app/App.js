import React, {Component} from 'react';
import AppHeader from '../shared/AppHeader';
import AppMain from '../shared/AppMain';
import AppFooter from '../shared/AppFooter';
import AppPopup from '../shared/AppPopup';
import {cms, getListByPath, loadLogin, logoutSession, verifyTokenOTP, getPointByClientID, enscryptData} from '../util/APIUtils';
import './App.css';
import '../dest/style.min.css';
import '../dest/goolgeapifont.css';
import SideBar2 from '../shared/SideBar2';
import ReactGA from 'react-ga4';
import RouteChangeTracker from '../shared/RouteChangeTracker';
import LiveNotification from '../Notification/LiveNotification';
import logoOpenApp from '../img/icon/logoOpenApp.svg';
import CloseIcon from '../img/ico-close.svg';
import {
    ACCESS_TOKEN,
    ADDRESS,
    CELL_PHONE,
    CLIENT_ID,
    CLIENT_PROFILE,
    CMS_CATEGORY,
    CMS_CATEGORY_LIST_DATA,
    CMS_HOMEPAGE,
    CMS_SUB_CATEGORY_MAP,
    CMS_TYPE,
    COMPANY_KEY2,
    DCID,
    DOB,
    EMAIL,
    FE_BASE_URL,
    FROM_APP,
    FROM_SCREEN_APP,
    FULL_NAME,
    GENDER,
    GOOGLE_ANALYTICS_PROPERTY_ID,
    GOOGLE_TAG_MANAGER_PROPERTY_ID,
    IRACE_SECRET_KEY,
    LINK_FB,
    LINK_GMAIL,
    LOGINED,
    MENU_NAME,
    OTP_EXPIRED,
    OTP_INCORRECT,
    PAGE_IRACE_BASE,
    POID,
    POINT,
    SUB_MENU_NAME,
    TWOFA,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    IS_MOBILE, 
    OTHER_ADDRESS, COMPANY_KEY, AUTHENTICATION, CLASSPO,
    CLASSPOMAP, LOGIN_TIME, FORGOT_PASSWORD,
    TRANSACTION_ID, TYPE_PWA, BACK_PATH, DEVICE_ID, ICALLBACK, 
    LOGOUT_TIMEOUT, ACCOUNT_STATUS, ACCOUNT_REGISTER,
    CMS_CATEGORY_LIST_DATA_JSON,
    CMS_SUB_CATEGORY_MAP_JSON
} from '../constants';
import {Redirect, withRouter} from 'react-router-dom';
import AES256 from 'aes-everywhere';
import DOTPInput from '../components/DOTPInput';
import ExceptionPopup from '../components/ExceptionPopup';
import {getDeviceId, getSession, setSession, roundDown, getUrlParameter, buildParamsAndRedirect, removeSession, getLinkId, setDeviceKey, getDeviceKey} from '../util/common';
import AppHeaderNative from '../shared/AppHeaderNative';
import AppHeaderNativePWA from '../shared/AppHeaderNativePWA';
import {Helmet} from "react-helmet";
import SearchPopup from '../components/SearchPopup';
import md5 from 'md5';
import TagManager from 'react-gtm-module';
import AlertPopupClaim from "../components/AlertPopupClaim";
import GeneralPopupPartner from '../components/GeneralPopupPartner';
import AppHeaderWrapper from "../shared/AppHeader";
import ErrorBoundary from "../SDK/ErrorBoundary";

let fromNative = null;
let isMobile = null;
let keyword = '';
let movingPath = '';
let type = '';
ReactGA.initialize(GOOGLE_ANALYTICS_PROPERTY_ID);

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isIdle: false,
            authenticated: false,
            currentUser: null,
            loading: false,
            hideMain: false,
            clientProfile: null,
            point: parseFloat(getSession(POINT)) ? parseFloat(getSession(POINT)) : 0,
            reload: false,
            cmshomepage: null,
            showMobileSidebar: false,
            menuName: '',
            subMenuName: '',
            menuIndex: '0',
            activeIndex: '0',
            categoryIndex: '0',
            subCategoryIndex: '0',
            haveError: false,
            showOtp: false,
            showTempPass: false,
            beLoggedIn: false,
            errorMessage: '',
            accessToken: '',
            minutes: 0,
            seconds: 0,
            isShowPopupApp: true,
            toHome: false,
            uiidToken: '',
            enscryptStr: '',
            categoryTypeData: null,
            subCategoryMap: {},
            showLogin: (window.location.pathname === '/') ? true : false,
            showSearchBox: false,
            showMoving: false,
            showIraceQ: false,
            path: '',
            popup: '',
            // listDistMetaKeyword: null,
            isAndroid: /Android/.test(navigator.userAgent),
            isIOS: /iPhone|iPad|iPod/.test(navigator.userAgent),
            toggleBell: false,
            myIP: ''
        }
        this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
        this.popupOtpSubmit = this.popupOtpSubmit.bind(this);
        this.popupOtpLoginSubmit = this.popupOtpLoginSubmit.bind(this);
        this.closeExceptionPopup = this.closeExceptionPopup.bind(this);

        this.closeNotAvailable = this.closeNotAvailable.bind(this);
        this.idleTimer = null;
        this.idleTimeout = LOGOUT_TIMEOUT || 180000;
        this.handleIdle = this.handleIdle.bind(this);
        this.refreshBell = this.onRefreshBell.bind(this);
    }


    updateEnscryptStr = (str) => {
        this.setState({enscryptStr: str});
    }

    callbackFunction = (isLogined, profile, po) => {
        this.setState({authenticated: isLogined, clientProfile: profile, point: po})
    }

    callbackFunctionZalo = (isLogined) => {
        this.setState({authenticated: isLogined})
    }

    callbackHideMain = (isHidden) => {
        if (this.state.hideMain !== isHidden) {
            this.setState({hideMain: isHidden})
        }
    }

    callBackReloadSidebar = (isReload) => {
        this.setState({reload: isReload});
    }

    callBackShowMobileSidebar = (isShow) => {
        this.setState({showMobileSidebar: isShow});
    }

    callbackMenu = (name, index) => {
        this.setState({menuName: name, menuIndex: index});
        setSession(MENU_NAME, name);
    }
    callbackSubMenu = (name, index) => {
        this.setState({subMenuName: name, activeIndex: index});
        setSession(SUB_MENU_NAME, name);
    }

    callbackCategory = (name, index) => {
        this.setState({menuName: name, categoryIndex: index});
        setSession(MENU_NAME, name);
    }
    callbackSubCategory = (name, index) => {
        this.setState({subMenuName: name, subCategoryIndex: index});
        setSession(SUB_MENU_NAME, name);
    }

    callbackShowLogin = (show) => {
        this.setState({showLogin: show});
    }

    loadCurrentlyLoggedInUser() {
        if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {

            if (window.location.pathname === '/home' || window.location.pathname === '/') {
                this.setState({
                    clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
                    authenticated: true,
                    loading: true,
                    hideMain: false,
                });

            } else if (window.location.pathname !== '/') {
                this.setState({
                    clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
                    authenticated: true,
                    loading: false,
                    hideMain: true,
                });
            } else {
                this.setState({
                    clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
                    authenticated: true,
                    loading: false
                });
            }

        } else {
            if (window.location.pathname !== '/' && window.location.pathname !== '/home') {
                this.setState({
                    loading: false,
                    hideMain: true,
                });
            } else if (window.location.pathname === '/' || window.location.pathname === '/home') {
                this.setState({
                    authenticated: false,
                    loading: false,
                    hideMain: false,
                    clientProfile: null
                });
            }
        }

    }

    GetHomePage() {
        var jsonState = this.state;

        if (!getSession(CMS_HOMEPAGE)) {
            const homepageRequest = {"Action": "GetHomePage"};
            cms(homepageRequest)
                .then(response => {
                    if (response && response.error === 0 && response.data) {
                        //jsonState.homepage = response;
                        setSession(CMS_HOMEPAGE, JSON.stringify(response));
                        this.setState(jsonState);
                    } else {
                        setSession(CMS_HOMEPAGE, null);
                    }


                }).catch(error => {

            });
        }
    }

    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(window.location.search);
        return results === null ? '' : decodeURIComponent(results[1]);
    };

    popupOtpSubmit(OTP) {
        this.submitOtp(OTP, 'CheckOTP');
    }

    popupOtpLoginSubmit(OTP) {
        this.submitOtp(OTP, 'CPLOGIN');
    }

    registerAccount = () => {
		if (getSession(FORGOT_PASSWORD)) {
			sessionStorage.removeItem(FORGOT_PASSWORD);
		}
		if (getSession(TRANSACTION_ID)) {
            sessionStorage.removeItem(TRANSACTION_ID);
		}
		setSession(ACCOUNT_STATUS, ACCOUNT_REGISTER);
		document.getElementById('option-popup').className = "popup option-popup show";
	}

    submitOtp(OTP, action) {
        const verifyOTPRequest = {
            jsonDataInput: {
                Action: action,
                APIToken: this.state.accessToken,
                OTP: OTP,
                RecaptchaToken: this.state.uiidToken,
                DCID : getSession(DCID)
            }

        }

        verifyTokenOTP(verifyOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && ((response.Response.ErrLog === 'Login successfull') || (response.Response.ErrLog === 'CHANGEPASS'))) {
                    setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                    setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                    setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);
                    setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                    setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                    setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                    setSession(GENDER, response.Response.ClientProfile[0].Gender);
                    setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                    setSession(EMAIL, response.Response.ClientProfile[0].Email);
                    setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                    setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                    setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                    setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                    setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);

                    if (response.Response.ClientProfile[0].POID) {
                        setSession(POID, response.Response.ClientProfile[0].POID);
                    }
                    if (response.Response.ClientProfile[0].DOB) {
                        setSession(DOB, response.Response.ClientProfile[0].DOB);
                    }
                    if (response.Response.ClientProfile[0].DCID) {
                        setSession(DCID, response.Response.ClientProfile[0].DCID);
                    }
                    setSession(LOGINED, LOGINED);
                    this.setState({beLoggedIn: true, showOtp: false, showTempPass: false});
                    const p = getSession(BACK_PATH);
                    if (p) {
                        removeSession(BACK_PATH);
                        buildParamsAndRedirect(p);
                    }
                } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({errorMessage: OTP_EXPIRED});
                } else if (response.Response.Result === 'true' && (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times')) {
                    this.setState({showOtp: false, minutes: 0, seconds: 0});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({errorMessage: OTP_INCORRECT});
                } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({minutes: 5, seconds: 0});
                    this.startTimer();
                } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'FORGOTPASSSUCC') {
                    this.setState({minutes: 5, seconds: 0});
                    this.startTimer();
                } else {
                    this.setState({errorMessage: OTP_INCORRECT});
                }
            }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    startTimer = () => {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            else if (this.state.seconds === 0) {
                if (this.state.minutes === 0) {
                    clearInterval(myInterval)
                } else {
                    this.setState({minutes: this.state.minutes - 1, seconds: 59});
                }
            }
        }, 1000)
        return () => {
            clearInterval(myInterval);
        };

    }

    closeOtp = () => {
        this.setState({showOtp: false, showTempPass: false, minutes: 0, seconds: 0});
    }

    closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            this.closeOtpPopup();
        }

    }
    closeOtpPopup = () => {
        if (this.state.showOtp || this.state.showTempPass) {
            //   document.getElementById('error-message-giftcard-expire-id').className = 'error-message';
            //   document.getElementById('error-message-giftcard-id').className = 'error-message';
            //   document.getElementById('otp-giftcard-id').value = '';
            this.setState({showOtp: false, showTempPass: false, minutes: 0, seconds: 0});
        }

    }

    closeExceptionPopup = () => {
        this.setState({haveError: false, toHome: true})
    }

    genOtp = () => {
        this.submitOtp('', 'GenOTPV2');
    }

    forGotPassword = () => {
        this.submitOtp('', 'ForgotPassword');
    }

    closeSearchBox = () => {
        this.setState({showSearchBox: false});
    }

    searchSubmit = (value) => {
        keyword = value;
        this.setState({showSearchBox: false});
    }

    toggleSearchBox = () => {
        this.setState({showSearchBox: !this.state.showSearchBox});
    }

    showMoving = (value) => {
        this.setState({showMoving: value});
    }

    showIraceQ = (value) => {
        this.setState({showIraceQ: value});
    }

    iraceNotYet = () => {
        this.showIraceQ(false);
        this.showMoving(true);
        this.turnOffMovingNotYet();
    }

    iraceExisted = () => {
        this.showIraceQ(false);
        this.turnOffMovingExisted();
    }

    turnOffMovingNotYet = () => {
        let dlvn_id = getSession(DCID) ? getSession(DCID) : "";
        let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id);
        let href = PAGE_IRACE_BASE + '/register?dlvn_id=' + dlvn_id + '&checksum=' + checksum;
        if (getSession(ICALLBACK)) {
            removeSession(ICALLBACK);
            window.location.href = href;
        } else {
            window.open(href);
        }
    }

    turnOffMovingExisted = () => {
        let dlvn_id = getSession(DCID) ? getSession(DCID) : "";
        let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id);
        let href = PAGE_IRACE_BASE + '/?dlvn_id=' + dlvn_id + '&checksum=' + checksum;
        if (getSession(ICALLBACK)) {
            removeSession(ICALLBACK);
            window.location.href = href;
        } else {
            window.open(href);
        }
    }

    trackingEvent = (category, action, label) => {
        ReactGA.send({hitType: 'pageview', page: window.location.pathname});
        ReactGA.event({
            category: category,
            action: action + (getSession(DCID)?('_' + getSession(DCID)):''),
            label: label + (getSession(DCID)?('_' + getSession(DCID)):''),
            DCID:  (getSession(DCID)?getSession(DCID):''),
            Device_ID: (getSession(DEVICE_ID)?getSession(DEVICE_ID):''),
            Section_ID: `Web_${(getSession(DEVICE_ID)?getSession(DEVICE_ID):'')}`
        });
    }

    closeNotAvailable = () => {
        this.setState({isIdle: false});
        this.resetIdleTimer();
        window.location.href = '/';
    }

    startIdleTimer() {
        this.resetIdleTimer();
        this.idleTimer = setTimeout(() => {
            this.setState({isIdle: true});
            logoutSession();
        }, this.idleTimeout);
    }

    resetIdleTimer() {
        if (this.idleTimer) {
            clearTimeout(this.idleTimer);
        }
    }

    handleIdle() {
        this.resetIdleTimer();
        this.startIdleTimer();
    }

    onRefreshBell() {
        this.setState({toggleBell:!this.state.toggleBell});
    }
    // getMetaKeywordSuggestion() {
    //   if (getSession(CMS_LIST_DIST_META_KEYWORD_DATA)) {
    //     let listDistMetaKeyword = JSON.parse(getSession(CMS_LIST_DIST_META_KEYWORD_DATA));
    //     this.setState({listDistMetaKeyword: listDistMetaKeyword});
    //   } else {
    //     getListByPath(CMS_NEWS + CMS_LIST_DIST_META_KEYWORD )
    //     .then(res => {
    //       if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
    //         setSession(CMS_LIST_DIST_META_KEYWORD_DATA, JSON.stringify(res.data));
    //         this.setState({listDistMetaKeyword: res.data});
    //       }
    //     }).catch(error => {
    //     });
    //   }
    //
    // }

    componentDidMount() {
        window.onbeforeunload = null;
        let path = getUrlParameter("path");
        let popup = getUrlParameter("popup")?getUrlParameter("popup"):'';
        if (path) {
            this.setState({path: path, popup: popup});
        }
        getDeviceId();
        // this.synLogin(deviceId);
        const tagManagerArgs = {
            gtmId: GOOGLE_TAG_MANAGER_PROPERTY_ID
        };
        TagManager.initialize(tagManagerArgs);
        this.getCmsCategoryList();
        this.loadCurrentlyLoggedInUser();
        this.GetHomePage();
        // ReactGA.pageview(window.location.pathname);
        // this.getMetaKeywordSuggestion();
        this.trackingEvent(document.title, document.title, document.title);
        let forgotPass = this.getUrlParameter("forgotpass");
        let register = this.getUrlParameter("register");
        if (forgotPass) {
            setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
            document.getElementById('option-popup').className = "popup option-popup show";
        } else if (register) {
            if (getSession(FORGOT_PASSWORD)) {
                sessionStorage.removeItem(FORGOT_PASSWORD);
            }
            if (getSession(TRANSACTION_ID)) {
                sessionStorage.removeItem(TRANSACTION_ID);
            }
            document.getElementById('option-popup').className = "popup option-popup show";
        }
        let token = this.getUrlParameter("token");

        if (token) {
            const tokenArr = token.split('||');
            if (tokenArr && tokenArr.length >= 1) {
                token = tokenArr[0];
                if (tokenArr[1]) {
                    this.setState({uiidToken: tokenArr[1]});
                }
            }
            if (tokenArr && tokenArr.length >= 2) {
                if (tokenArr[2]) {
                    setSession(DEVICE_ID, tokenArr[2]);
                }
            }
            let res = AES256.decrypt(token, COMPANY_KEY2);
            let response = JSON.parse(res);
            if (response.Response.Result === 'true' && response.Response.ErrLog === 'Login successfull') {
                setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);
                setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                setSession(GENDER, response.Response.ClientProfile[0].Gender);
                setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                setSession(EMAIL, response.Response.ClientProfile[0].Email);
                setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);

                if (response.Response.ClientProfile[0].POID) {
                    setSession(POID, response.Response.ClientProfile[0].POID);
                }
                if (response.Response.ClientProfile[0].DOB) {
                    setSession(DOB, response.Response.ClientProfile[0].DOB);
                }
                if (response.Response.ClientProfile[0].DCID) {
                    setSession(DCID, response.Response.ClientProfile[0].DCID);
                }
                setSession(LOGINED, LOGINED);
                this.setState({beLoggedIn: true});
                const p = getSession(BACK_PATH);
                if (p) {
                    removeSession(BACK_PATH);
                    buildParamsAndRedirect(p);
                }
            } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'CLIREGSUCC') {
                this.setState({showOtp: true, accessToken: response.Response.NewAPIToken, minutes: 5, seconds: 0});
                this.startTimer();
            } else if (response.Response.Result === 'true' && response.Response.ErrLog === 'FORGOTPASSSUCC') {
                this.setState({showTempPass: true, minutes: 5, seconds: 0});
                this.startTimer();
            } else {
                this.setState({haveError: true});
                // document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }
        }

        fromNative = this.getUrlParameter("from");
        if (fromNative) {
            setSession(FROM_APP, fromNative);
        }

        let screen = this.getUrlParameter("screen");
        if (screen) {
            setSession(FROM_SCREEN_APP, screen);
        }

        isMobile = this.getUrlParameter("isMobile");
        if (isMobile) {
            setSession(IS_MOBILE, isMobile);
        }
        type = this.getUrlParameter("type");
        if (type) {
            setSession(TYPE_PWA, type);
        }

        if (!fromNative && !isMobile) {
            setTimeout(() => {
                    if (document.getElementById('fpt_ai_livechat_button')) {
                        document.getElementById('fpt_ai_livechat_button').className = 'fpt_ai_livechat_button_blink basic-show';
                    }
            }, 5000);
        }

        this.unlisten = this.props.history.listen((location, action) => {
            if (!this.state.isShowPopupApp) {

                setTimeout(() => {
                    const headerContainer = document.getElementById('header-id');
                    const mainContainer = document.querySelector('main');

                    headerContainer.style.marginTop = '0px';
                    mainContainer.style.marginTop = '0px';

                }, 200);
            }
        });
        //for ask login from irace
        let icallback = getUrlParameter("icallback");
        let iType = getUrlParameter("itype");
        if (icallback) {
            if (!getSession(ACCESS_TOKEN)) {
                setSession(ICALLBACK, "irace");
            } else {
                getLinkId();
            }
        }
        if(iType && iType === 'register') {
        //show signup popup 
        this.registerAccount();
        }
        if (!this.state.myIP && !getDeviceKey()) {
            this.getPublicIPAddress();
        }
        // getLocal(DEVICE_KEY)
        // .then(Res => {
            
        //     if (!Res?.v && !this.state.myIP) {
        //         this.getPublicIPAddress();
        //     } 
        // })
        // .catch(error => {
        // });
    }

    getPublicIPAddress() {
        const setComponentState = this.setState.bind(this);
        //Code to get the user's public IP address using an external API
        fetch('https://api.ipify.org?format=json')
            .then(response => response.json())
            .then(data => {
                //console.log('Your Public IP Address:', data.ip);
                this.enscriptDeviceKey(data.ip);
                setComponentState({ myIP: data.ip });
            })
            .catch(error => {
                this.enscriptDeviceKey(getDeviceId());
                setComponentState({ myIP: getDeviceId() });
            });
        //this.enscriptDeviceKey(getDeviceId());
        //this.setState({myIP: getDeviceId()});
    }

    enscriptDeviceKey(keyStr) {
        let enscriptRequest ={
          jsonDataInput: {
            APIToken: getSession(ACCESS_TOKEN),
            Action: "EncryptAES",
            Authentication: AUTHENTICATION,
            ClientID: getSession(CLIENT_ID),
            Company: COMPANY_KEY,
            Data: keyStr,
            DeviceId: getDeviceId(),
            Project: "mcp",
            UserLogin: getSession(USER_LOGIN)
          }
        }
        enscryptData(enscriptRequest).then(Res => {
          let Response = Res.Response;
          if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
              setDeviceKey(Response.Message);
              console.log('DeviceKey=', Response.Message);
          } 
      }).catch(error => {
      });
      }
    
    componentDidUpdate(prevProps) {
        const currentAccessToken = getSession(ACCESS_TOKEN);

        if (currentAccessToken !== null && !this.state.isIdle && !this.getUrlParameter("from") && !getSession(IS_MOBILE)) {
            this.startIdleTimer();
            window.addEventListener('mousemove', this.handleIdle);
            window.addEventListener('keydown', this.handleIdle);
        }
    }

    componentWillUnmount() {
        window.removeEventListener('mousemove', this.handleIdle);
        window.removeEventListener('keydown', this.handleIdle);

        window.onbeforeunload = null;

        clearTimeout(this.idleTimer);
        this.setState({isIdle: false});
        this.unlisten();
    }

    synLogin(deviceId) {
        loadLogin(deviceId)
        .then(response => {
            if (response.Response.Result === 'true' && response.Response.ErrLog.toLowerCase() === 'Login successfull'.toLowerCase() && response.Response.ClientProfile) {
                setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);
                setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                if (response.Response.ClientProfile[0].Gender) {
                    setSession(GENDER, response.Response.ClientProfile[0].Gender);
                }
                setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                if (response.Response.ClientProfile[0].OtherAddress) {
                    setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
                }
                if (response.Response.ClientProfile[0].Email) {
                    setSession(EMAIL, response.Response.ClientProfile[0].Email);
                }
                if (response.Response.ClientProfile[0].VerifyCellPhone) {
                    setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                }
                if (response.Response.ClientProfile[0].VerifyEmail) {
                    setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                }
                if (response.Response.ClientProfile[0].TwoFA) {
                    setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                }
                if (response.Response.ClientProfile[0].LinkFaceBook) {
                    setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                }
                if (response.Response.ClientProfile[0].LinkGmail) {
                    setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
                }
                if (response.Response.ClientProfile[0].POID) {
                    setSession(POID, response.Response.ClientProfile[0].POID);
                }
                if (response.Response.ClientProfile[0].DOB) {
                    setSession(DOB, response.Response.ClientProfile[0].DOB);
                }
                if (response.Response.ClientProfile[0].DCID) {
                    setSession(DCID, response.Response.ClientProfile[0].DCID);
                    ReactGA.set({ userId: response.Response.ClientProfile[0].DCID });
                }

                const pointRequest = {
                    jsonDataInput: {
                        Company: COMPANY_KEY,
                        OS: '',
                        APIToken: response.Response.NewAPIToken,
                        Authentication: AUTHENTICATION,
                        ClientID: response.Response.ClientProfile[0].ClientID,
                        DeviceId: getDeviceId(),
                        Project: 'mcp',
                        UserLogin: response.Response.ClientProfile[0].LoginName
                    }
                }
                getPointByClientID(pointRequest).then(res => {
                    let Response = res.CPGetPointByCLIIDResult;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                        setSession(CLASSPO, CLASSPOMAP[Response.ClassPO] ? CLASSPOMAP[Response.ClassPO] : Response.ClassPO);
                        setSession(POINT, roundDown(parseFloat(Response.Point) / 1000));
                        this.callbackFunction(true, response.Response.ClientProfile, Response.Point);
                    } else {
                        this.callbackFunction(true, response.Response.ClientProfile, 0);
                    }
                }).catch(error => {
                    //this.props.history.push('/maintainence');
                });

                setSession(LOGIN_TIME, new Date().getTime() / 1000);
                // if (getSession(PREVIOUS_SCREENS) && ((getSession(PREVIOUS_SCREENS) === '/song-vui-khoe')) || (getSession(PREVIOUS_SCREENS) === '/home') || (getSession(PREVIOUS_SCREENS) === '/')) {
                //     // document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
                //     getLinkId();
                // }
            } 

        }).catch(error => {

        });
    }
    getCmsCategoryList = () => {
        if (!getSession(CMS_CATEGORY_LIST_DATA) && !this.state.categoryTypeData) {
            setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(CMS_CATEGORY_LIST_DATA_JSON));
            setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(CMS_SUB_CATEGORY_MAP_JSON));
            // getListByPath(CMS_TYPE)
            //     .then(response => {
            //         if (response.statusCode === 200 && response.statusMessages === 'success') {
            //             let jsonState = this.state;
            //             setSession(CMS_CATEGORY_LIST_DATA, JSON.stringify(response.data))
            //             console.log('CMS_CATEGORY_LIST_DATA=', response.data);
            //             jsonState.categoryTypeData = response.data;
            //             for (let i = 0; i < response.data.length; i++) {

            //                 getListByPath(CMS_CATEGORY + response.data[i].code)
            //                     .then(res => {
            //                         if (res.statusCode === 200 && res.statusMessages === 'success') {

            //                             jsonState.subCategoryMap[response.data[i].code] = res.data;
            //                             setSession(CMS_SUB_CATEGORY_MAP, JSON.stringify(jsonState.subCategoryMap));
            //                             console.log('jsonState.subCategoryMap=', jsonState.subCategoryMap);
            //                             this.setState(jsonState);


            //                         }
            //                     }).catch(error => {
            //                     //this.props.history.push('/maintainence');
            //                 });
            //             }
            //             this.setState(jsonState);
            //         }
            //     }).catch(error => {
            //     //this.props.history.push('/maintainence');
            // });
        }

    }
    showEdoctorLogin=(path, popup)=> {
        this.setState({path: path, popup: popup});
        this.props.history.push('/');
    }

    render() {
        const showEdoctorLogin=(path, popup)=> {
            this.setState({path: path, popup: popup});
            return <Redirect to={{
                pathname: '/'
            }}/>;
        }

        const closeLoginPopup = () => {
            this.setState({popup: ''});
        }
        fromNative = this.getUrlParameter("from");
        if (!getSession(FROM_APP) && fromNative) {
            setSession(FROM_APP, fromNative);
        }
        isMobile = this.getUrlParameter("isMobile");
        if (!getSession(IS_MOBILE) && isMobile) {
            setSession(IS_MOBILE, isMobile);
        }
        let point = 0;
        if (getSession(POINT)) {
            point = parseFloat(getSession(POINT));
        }

        let clientProfile = null;
        if (getSession(CLIENT_PROFILE)) {
            clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
        }
        if (this.state.beLoggedIn || this.state.toHome) {
            this.setState({beLoggedIn: false, toHome: false});
            return <Redirect to={{
                pathname: "/"
            }}/>;
        }
        if (keyword) {
            let path = "/timkiem/?q=" + keyword;
            if (window.location.pathname === '/tim/') {
                path = "/tim/?q=" + keyword;
            }
            keyword = '';
            return <Redirect to={{
                pathname: path
            }}/>;
        }
        if (movingPath) {
            let path = '' + movingPath;
            movingPath = '';
            return <Redirect to={{
                pathname: path
            }}/>;
        }

        const openDaiIchiConnectApp = () => {
            if (this.state.isAndroid) {
                window.open('https://daiichi.page.link/openDCapp', '_blank');
            } else if (this.state.isIOS) {
                window.open('https://daiichi.page.link/openDCapp', '_blank');
            } else {
                alert('Ứng dụng này chỉ hỗ trợ Android và iOS.');
            }
        };


        return (
            <>
            <ErrorBoundary>
                <Helmet>
                    <title>Cổng thông tin Khách hàng – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam, cung cấp thông tin về hợp đồng bảo hiểm, đổi điểm thưởng, ứng dụng và bí quyết sống vui khỏe."/>
                    <meta name="keywords"
                          content="bảo hiểm, bảo hiểm nhân thọ, dai-ichi life, bí quyết sống vui khỏe, chạy bộ, tập luyện thể thao, hợp đồng bảo hiểm, yêu cầu hỗ trợ, giải quyết quyền lợi, thay đổi thông tin, người được bảo hiểm, chủ hợp đồng"/>
                    <meta name="robots" content="index, follow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL}/>
                    <meta property="og:title" content="Cổng thông tin Khách hàng – Dai-ichi Life Việt Nam"
                          data-react-helmet="true"/>
                    <meta property="og:description"
                          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam, cung cấp thông tin về hợp đồng bảo hiểm, đổi điểm thưởng, ứng dụng và bí quyết sống vui khỏe."/>
                    <meta property="og:image" content={FE_BASE_URL + "/img/meta-logo.png"}/>
                    <link rel="canonical" href={FE_BASE_URL}/>

                    <script>
                        {`
            !function(f,b,e,v,n,t,s)
            {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
            n.callMethod.apply(n,arguments):n.queue.push(arguments)};
            if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
            n.queue=[];t=b.createElement(e);t.async=!0;
            t.src=v;s=b.getElementsByTagName(e)[0];
            s.parentNode.insertBefore(t,s)}(window, document,'script',
            'https://connect.facebook.net/en_US/fbevents.js');
            fbq('init', '1799776023873184');
            fbq('track', 'PageView');
        `}
                    </script>
                </Helmet>

                <div>
                    {!isMobile && !getSession(IS_MOBILE) && !getSession(FROM_APP) && this.state.isShowPopupApp && <div className="popupOpenApp">
                        <div className="popupOpenAppContent">
                            <img src={logoOpenApp} alt="Logo" style={{width: 38, height: 38}}/>
                            <div className="center">
                                <p>
                                    Mở bằng ứng dụng <span style={{fontWeight: 700}}>Dai-ichi Connect</span> để có trải
                                    nghiệm đầy đủ nhất.
                                </p>
                            </div>
                            <div className="right">
                                <a href="#" className="open-app-link" onClick={() => openDaiIchiConnectApp()}>
                                    Mở
                                </a>
                                <div className="close-btn" onClick={() => {
                                    const headerContainer = document.getElementById('header-id');
                                    const mainContainer = document.querySelector('main');
                                    if (headerContainer) {
                                        headerContainer.style.marginTop = '0px';
                                    }
                                    if (mainContainer) {
                                        mainContainer.style.marginTop = '0px';
                                    }
                                    this.setState({isShowPopupApp: false});
                                }}>
                                    <img src={CloseIcon} alt="closebtn" className="close-btn-img"/>
                                </div>
                            </div>
                        </div>
                    </div>}
                    {fromNative || getSession(FROM_APP) ? (
                        type || getSession(TYPE_PWA)?(
                            <AppHeaderNativePWA subMenuName={this.state.subMenuName} toggleSearchBox={this.toggleSearchBox}/>
                        ):(
                            <AppHeaderNative subMenuName={this.state.subMenuName} toggleSearchBox={this.toggleSearchBox}/>
                        )
                        
                    ) : (
                        !isMobile && !getSession(IS_MOBILE) &&
                        <AppHeaderWrapper authenticated={this.state.authenticated} menuIndex={this.state.menuIndex} isShowPopupApp={this.state.isShowPopupApp}
                                   activeIndex={this.state.activeIndex} menuName={this.state.menuName}
                                   subMenuName={this.state.subMenuName} parentCallback={this.callbackHideMain}
                                   parentCallbackShowMobileSidebar={this.callBackShowMobileSidebar}
                                   callbackMenu={this.callbackMenu} callbackSubMenu={this.callbackSubMenu}
                                   clientProfile={this.state.clientProfile} callbackFunction={this.callbackFunction}
                                   hideMain={this.state.hideMain} categoryTypeData={this.state.categoryTypeData}
                                   subCategoryMap={this.state.subCategoryMap} callbackCategory={this.callbackCategory}
                                   callbackSubCategory={this.callbackSubCategory}
                                   categoryIndex={this.state.categoryIndex}
                                   subCategoryIndex={this.state.subCategoryIndex}
                                   toggleBell={this.state.toggleBell}
                                   callbackShowLogin={this.callbackShowLogin} showLogin={this.state.showLogin}
                                   toggleSearchBox={this.toggleSearchBox} showMoving={this.showMoving}
                                   showIraceQ={this.showIraceQ} path={this.state.path} showEdoctorLogin={this.showEdoctorLogin}/>
                    )}
                    <SideBar2 activeIndex={this.state.activeIndex} showMobile={this.state.showMobileSidebar}
                              clientProfile={clientProfile} point={point} parentCallback={this.callbackHideMain}
                              parentCallbackShowMobileSidebar={this.callBackShowMobileSidebar}
                              callbackMenu={this.callbackMenu} callbackSubMenu={this.callbackSubMenu}
                              callbackFunctionLogin={this.callbackFunction}
                              callbackFunctionAuthZalo={this.callbackFunctionZalo}
                              updateEnscryptStr={this.updateEnscryptStr} callbackShowLogin={this.callbackShowLogin}
                              callbackCategory={this.callbackCategory} callbackSubCategory={this.callbackSubCategory}
                              showIraceQ={this.showIraceQ} refreshBell={this.refreshBell}/>
                    {!this.state.hideMain &&
                        <AppMain clientProfile={clientProfile} authenticated={this.state.authenticated}
                                 parentCallback={this.callbackFunction} parentCallbackHiden={this.callbackHideMain}
                                 enscryptStr={this.state.enscryptStr}/>}
                    {window.location.pathname !== '/home' && window.location.pathname !== '/claim-la' && !(fromNative || getSession(FROM_APP)) && !isMobile && !getSession(IS_MOBILE) &&
                        <AppFooter/>}
                    <AppPopup parentCallback={this.callbackFunction}
                              parentCallbackReloadSidebar={this.callBackReloadSidebar} iraceNotYet={this.iraceNotYet}
                              iraceExisted={this.iraceExisted} path={this.state.path} callbackShowLogin={this.callbackShowLogin}/>
                    <RouteChangeTracker callbackMenu={this.callbackMenu} callbackSubMenu={this.callbackSubMenu}
                                        callbackHideMain={this.callbackHideMain}
                                        callbackCategory={this.callbackCategory}
                                        callbackSubCategory={this.callbackSubCategory}
                                        callbackShowLogin={this.callbackShowLogin}/>
                    <LiveNotification/>
                    {this.state.showOtp &&
                        <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds}
                                   startTimer={this.startTimer} closeOtp={this.closeOtp}
                                   errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupOtpSubmit}
                                   reGenOtp={this.genOtp}/>
                    }
                    {this.state.showTempPass &&
                        <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds}
                                   startTimer={this.startTimer} closeOtp={this.closeOtp}
                                   errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupOtpLoginSubmit}
                                   reGenOtp={this.forGotPassword} length={8} width={'36px'}/>
                    }
                    {this.state.haveError &&
                        <ExceptionPopup closeOtp={this.closeExceptionPopup}/>
                    }
                    {this.state.showSearchBox &&
                        <SearchPopup closeBox={this.closeSearchBox} searchSubmit={this.searchSubmit}
                        />
                    }
                    {this.state.isIdle && <AlertPopupClaim closePopup={this.closeNotAvailable.bind(this)}
                                                           subMsg={'<p style="margin-bottom: 0; font-size: 14px; font-weight: 500; color: #414141; line-height: 21px;">Đã hết thời gian truy cập ứng dụng. Vì lý do bảo mật, Quý khách vui lòng đăng nhập lại</p>'}
                                                           imgPath={FE_BASE_URL + "/img/popup/key-expired.svg"}/>}
                    {this.state.popup && <GeneralPopupPartner closePopup={closeLoginPopup}
                                  msg={'Quý khách cần đăng nhập Dai-ichi Connect để sử dụng tính năng này.'}
                                  imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'} buttonName={'Đăng nhập'}
                                  linkToGo={'/'} screenPath={'/'} showEdoctorLogin={showEdoctorLogin}/>}                                                           
                </div>
            </ErrorBoundary>
            </>

        );
    }
}

export default withRouter(App);
