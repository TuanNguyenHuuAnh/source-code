import React, { Component }  from 'react';
import { POINT, CLIENT_PROFILE, ACCESS_TOKEN, LINK_SUB_MENU_NAME_ID, LINK_SUB_MENU_NAME, USER_LOGIN, API_BASE_URL, AVATAR_USER, LINK_MENU_NAME, LINK_MENU_NAME_ID, UPDATE_POINT, CLIENT_ID, COMPANY_KEY, AUTHENTICATION, GENDER, QUANTRICS_SURVERY_CLS, LOGIN_TIME, LOG_OUTED, LOGINED, EMAIL, FULL_NAME, CELL_PHONE, DOB, ENCRYPTED_DATA, DEADTH_CLAIM_MSG, LINK_CATEGORY_ID, LINK_SUB_CATEGORY_ID, FROM_APP, IS_MOBILE, RELOAD_BELL, FROM_SDK} from '../constants';
import { Link } from "react-router-dom";
import './SideBar2.css';
import { formatMoney, roundDown, showQuantricsSurvey, formatDate3, setSession, getSession, removeSession, getDeviceId, setDeviceKey } from '../util/common';
import '../common/Common.css';
import { checkAvatar, getPointByClientID, enscryptData, CPSubmitForm } from '../util/APIUtils';

var showSurvey = false;
let count = 1;
class SideBar2 extends Component {//({clientProfile, parentCallback}){
  constructor(props) {
    super(props);
    this.state = {
        activeIndex: this.props.activeIndex,
        point: parseFloat(getSession(POINT))?parseFloat(getSession(POINT)):0,
        avatarPath: '',
        apiCallError: false,
        apiDeadClaimErr: false,
        checkAvatarError: false
    };
    this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
    this.closeMobileSidebar = this.closeMobileSidebar.bind(this);
  }

  getPoint=()=> {
    const pointRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        OS: '',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }
    }
    getPointByClientID(pointRequest).then(res => {
      let Response = res.CPGetPointByCLIIDResult;
      if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
        //setSession(CLASSPO, CLASSPOMAP[Response.ClassPO] ? CLASSPOMAP[Response.ClassPO] : Response.ClassPO);
        setSession(POINT, roundDown(parseFloat(Response.Point) / 1000));
        this.setState({point: roundDown(parseFloat(Response.Point) / 1000)});
      } 
    }).catch(error => {
      //this.props.history.push('/maintainence');
    });
  }

  updatePoint=()=> {
    if ((this.state.point !== parseFloat(getSession(POINT))) || getSession(UPDATE_POINT)) {
      // if (getSession(UPDATE_POINT)) {
      //   this.getPoint();
      //   sessionStorage.removeItem(UPDATE_POINT);
      // } else {
        this.setState({point: parseFloat(getSession(POINT))});
      // }
      
      if (!getSession(ACCESS_TOKEN)) {
        if (window.location.pathname === "/") {
          this.props.parentCallback(false);
        } else {
          this.props.parentCallback(true);
        }
        
      }
    }


    if (getSession(LINK_MENU_NAME_ID) && getSession(LINK_MENU_NAME)) {
      this.props.callbackMenu(getSession(LINK_MENU_NAME), getSession(LINK_MENU_NAME_ID));
      if (getSession(LINK_CATEGORY_ID)) {
        this.props.callbackCategory(getSession(LINK_MENU_NAME), getSession(LINK_CATEGORY_ID));
        sessionStorage.removeItem(LINK_CATEGORY_ID);
      }

      sessionStorage.removeItem(LINK_MENU_NAME_ID);
      sessionStorage.removeItem(LINK_MENU_NAME);
    }

    if (getSession(LINK_SUB_MENU_NAME_ID) && getSession(LINK_SUB_MENU_NAME)) {
      this.props.callbackSubMenu(getSession(LINK_SUB_MENU_NAME), getSession(LINK_SUB_MENU_NAME_ID));
      if (getSession(LINK_SUB_CATEGORY_ID)) {
        setTimeout(this.props.callbackSubCategory(getSession(LINK_SUB_MENU_NAME), getSession(LINK_SUB_CATEGORY_ID)), 100);
        sessionStorage.removeItem(LINK_SUB_CATEGORY_ID);
      }
      sessionStorage.removeItem(LINK_SUB_MENU_NAME_ID);
      sessionStorage.removeItem(LINK_SUB_MENU_NAME);
    }

    if (!this.state.checkAvatarError && !getSession(AVATAR_USER) && getSession(USER_LOGIN)) {
      this.checkAvatarExist();
    }
    let currentTimeSeconds = new Date().getTime()/1000;
    if (getSession(LOGIN_TIME) && (currentTimeSeconds >= parseInt(getSession(LOGIN_TIME)) + 300) && getSession(CLIENT_ID) && !showSurvey) {
      showSurvey = true;
      setTimeout(showQuantricsSurvey(QUANTRICS_SURVERY_CLS), 300000);
    }
    if (getSession(LOG_OUTED)) {
      sessionStorage.removeItem(LOG_OUTED);
      this.props.callbackShowLogin(true);
    }
    
    if (getSession(LOGINED)) {
      sessionStorage.removeItem(LOGINED);
      let clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
      if (getSession(POINT)) {
        this.props.callbackFunctionLogin(true, clientProfile, getSession(POINT));
      } else {
        this.props.callbackFunctionLogin(true, clientProfile, 0);
      }
      
    }
    // if (getSession(ZALO_LOGINED)) {
    //   sessionStorage.removeItem(ZALO_LOGINED);
    //   this.props.callbackFunctionAuthZalo(true);
    // }
    if (!this.state.apiCallError && !getSession(IS_MOBILE) && !getSession(ENCRYPTED_DATA) && getSession(ACCESS_TOKEN) && getSession(CLIENT_ID) && getSession(USER_LOGIN)) {
      this.enscriptData();
    }

    if (!this.state.apiDeadClaimErr && !getSession(IS_MOBILE) && !getSession(DEADTH_CLAIM_MSG) && getSession(ACCESS_TOKEN) && getSession(CLIENT_ID) && getSession(USER_LOGIN)) {
      this.checkDeathClaim();
    }

    //hide chatbot
    if (getSession(FROM_APP)||getSession(IS_MOBILE)) {
      if (document.getElementById('fpt_ai_livechat_button') && (document.getElementById('fpt_ai_livechat_button').className === 'fpt_ai_livechat_button_blink')) {
        document.getElementById('fpt_ai_livechat_button').className = 'fpt_ai_livechat_button_blink basic-none';
      }
    }
    // count++;
    // if ((count % 30) === 0) {
    //   count = 1;
    //   setSession(RELOAD_BELL, RELOAD_BELL);
    // }
    //Refresh bell
    if (getSession(RELOAD_BELL)) {
      this.props.refreshBell();
    }
  }

  enscriptData() {
    if (this.state.apiCallError) {
      return;
    }
    let enscriptRequest ={
      jsonDataInput: {
        APIToken: getSession(ACCESS_TOKEN),
        Action: "EncryptAES",
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        Company: COMPANY_KEY,
        Data: "{\"customer\":{\"fullName\":\"" + getSession(FULL_NAME) + "\",\"gender\":\"" + getSession(GENDER) + "\",\"phone\":\"" + getSession(CELL_PHONE) + "\",\"dob\":\"" + formatDate3(getSession(DOB)) + "\",\"email\":\"" + getSession(EMAIL) + "\",\"customerId\":\"" + getSession(USER_LOGIN) + "\",\"source\":\"CPortal\" }}",
        DeviceId: getDeviceId(),
        Project: "mcp",
        UserLogin: getSession(USER_LOGIN)
      }
    }
    enscryptData(enscriptRequest).then(Res => {
      let Response = Res.Response;
      if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
          setSession(ENCRYPTED_DATA, Response.Message);
          this.setState({enscrypted: true});
          this.props.updateEnscryptStr(Response.Message);
      } else {
        this.setState({apiCallError: true});
      }
  }).catch(error => {
      this.setState({apiCallError: true});
  });
  }

  checkDeathClaim() {
    if (this.state.apiDeadClaimErr) {
      return;
    }
    this.setState({apiDeadClaimErr: true});
    let enscriptRequest ={
      jsonDataInput: {
        OS: "Samsung_SM-A125F-Android-11",
        APIToken: getSession(ACCESS_TOKEN),
        Action: "CheckDeathClaim",
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Project: "mcp",
        UserLogin: getSession(USER_LOGIN)
      }
    }
    CPSubmitForm(enscriptRequest).then(Res => {
      let Response = Res.Response;
      if (Response.Result === 'true') {
          setSession(DEADTH_CLAIM_MSG, Response.ErrLog);
          this.setState({apiDeadClaimErr: true});
          this.props.updateEnscryptStr(Response.Message);
      } else {
        this.setState({apiDeadClaimErr: true});
      }
  }).catch(error => {
    this.setState({apiDeadClaimErr: true});
  });
  }

  componentDidMount() {
    //setTimeout(this.updatePoint, 4000);
    setInterval(this.updatePoint, 2000);
    document.addEventListener("mousedown", this.closeMobileSidebar);
  }

  componentWillUnmount() {
    document.removeEventListener("mousedown", this.closeMobileSidebar);
  }
  setWrapperRef(node) {
    this.wrapperSucceededRef = node;
  }
  closeMobileSidebar = (event) => {
    if (this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) {
      this.props.parentCallbackShowMobileSidebar(false);
    }
  }
  checkAvatarExist = () => {
    this.setState({checkAvatarError: true});
    const submitRequest = {
      imageName: getSession(USER_LOGIN),
      APIToken: getSession(ACCESS_TOKEN)
    }
    checkAvatar(submitRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ avatarPath: response.Response.avatarPath });
          setSession(AVATAR_USER, response.Response.avatarPath);
        } else {
          this.setState({ avatarPath: 'NOIMAGE' });
          setSession(AVATAR_USER, 'NOIMAGE');
        }
      }).catch(error => {
      });
  }
    render () {
      const createMenu = (menuArr, clientID, funcs) => {
        var requirePermission = [12, 13, 14];
        let menu = [];
        // Outer loop to create parent
        for (let i = 0; i < menuArr.length; i++) {
          let children = [];
          //Inner loop to create children
          for (let j = 0; j < menuArr[i][3].length; j++) {
            let link = [];
            link.push(<span className={this.props.activeIndex === 'item-' + menuArr[i][0] + '-' + j?'bold':''} id={'item-' + menuArr[i][0] + '-' + j} key={'item-' + menuArr[i][0] + '-' + j}>{menuArr[i][3][j][1]}</span>);
            children.push(<div className="tab">
                  <Link to={menuArr[i][3][j][0]} onClick={() => activeSelectedItem(true, 'item-' + menuArr[i][0] + '-' + j, menuArr[i][2], menuArr[i][3][j][1])} key={'link-' + menuArr[i][0] + '-' + j}>{link}</Link>
            </div>)
    
          }
          let content= [];
          let arrow = [];
          let img = [];
          let title = [];
          let tab = [];
          arrow.push(<div className="arrow">{'>'}</div>)
          img.push(<i><img src={menuArr[i][1]} alt="" /></i>)
          title.push(<p>{menuArr[i][2]}</p>)
          tab.push(<div className="tab">{img}{title}</div>)
    
          content.push(<div className="dropdown__content" id={'content-' + menuArr[i][0]} key={'content-' + menuArr[i][0]} onClick={()=>expandCollapseMenu('sidebar-item' + menuArr[i][0], menuArr[i][2])}>
                      {arrow}{tab}
                      </div>)
          //Create the parent and add the children
          
         // if (clientID !== '' && ((requirePermission.indexOf(menuArr[i][0]) >=0 && funcs.indexOf(menuArr[i][0]) >=0)) || requirePermission.indexOf(menuArr[i][0]) < 0) {
            menu.push(<div className="sidebar-item dropdown" id={'sidebar-item' + menuArr[i][0]} key={'sidebar-item' + menuArr[i][0]}>
            {content}
            <div className="dropdown__items" key={'dropdown__items-' + menuArr[i][0]}>
                  {children}
              </div>
            </div>)
         // } 

    
        }
        return menu
      }
      const expandCollapseMenu =(id, menuName) => {
        if(document.getElementById(id).className === 'sidebar-item dropdown') {
          collapseAll();
          document.getElementById(id).className = 'sidebar-item dropdown show';
        } else {
          collapseAll();
          document.getElementById(id).className = 'sidebar-item dropdown';
        }
        //this.props.callbackMenu(menuName, '0');
      }
      const collapseAll=() =>{
        let x = document.getElementsByClassName("sidebar-item");
        for (let i = 0; i < x.length; i++) {
          x[i].className = 'sidebar-item dropdown';
        }
      }
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
      } 
      const activeSelectedItem = (hideMain, id, menuName, subMenuName) => {
        callbackApp(hideMain);
        closeMobileSidebar();
      }
      const closeMobileSidebar=()=> {
        this.props.parentCallbackShowMobileSidebar(false);
      }
      const activeSelectedItemAcc = (hideMain, id, menuName, subMenuName) => {
        this.props.parentCallback(hideMain);
        this.props.callbackMenu(menuName, '0');
        this.props.callbackSubMenu(subMenuName, id);
      }
      var nameArr = '';
      var name = '';
      var userPermissions = [];
      var clientID = '';
      var point = 0;
      var fullName = '';
      //var clientProfile =
      let clientProfile = this.props.clientProfile;
      if (getSession(CLIENT_PROFILE)) {
        clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
      }
      if (clientProfile) {
        if (clientProfile[0].FullName.trim() !== '') {
          fullName = clientProfile[0].FullName;
          nameArr = fullName.split(' ');
          if (nameArr.length >= 2) {
            name = nameArr[nameArr.length-2].substring(0,1) + nameArr[nameArr.length-1].substring(0,1);
          } else if (nameArr.length === 1) {
            name = nameArr[nameArr.length-1].substring(0,1);
          }
        }
        userPermissions = clientProfile[0].UserPermission;
        var funcs = [];
        if (userPermissions) {
          for (let i = 0; i < userPermissions.length; i++) {
            funcs[i] = userPermissions[i].funcID;
          }
        }
        clientID = clientProfile[0].ClientID;
        point = this.props.point;
        if (getSession(POINT)) {
          point = parseFloat(getSession(POINT));
        }
      }
      var menuArrForExisted = [
        [12, '../img/icon/folder.svg', 'Thông tin hợp đồng', [
                                                            ['/mypolicyinfo', 'Danh sách hợp đồng'], 
                                                            ['/lifeassured', 'Người được bảo hiểm'],
                                                            ['/policyowner', 'Bên mua bảo hiểm'] ], ],
        [13, '../img/icon/wallet.svg', 'Đóng phí bảo hiểm', [
                                                            ['/mypayment', 'Hợp đồng của tôi'], 
                                                            ['/familypayment', 'Hợp đồng của người thân'] ], ],                                                            
        [14, '../img/icon/house.svg', 'Yêu cầu quyền lợi', [
                                                            ['/myclaim', 'Tạo mới yêu cầu'], 
                                                            ['/info-required-claim', 'Danh sách yêu cầu cần bổ sung'] ], ]

        ];

      var menuArrForAll = [
        [1, '../img/icon/track.svg', 'Theo dõi yêu cầu', [
                                                            ['/followup-claim-info', 'Giải quyết quyền lợi'] ], ],
        [2, '../img/icon/transaction.svg', 'Giao dịch hợp đồng', [
                                                            ['/update-contact-info', 'Điều chỉnh thông tin liên hệ'],
                                                            ['/update-personal-info', 'Điều chỉnh thông tin cá nhân'],
                                                            ['/update-policy-info', 'Điều chỉnh thông tin hợp đồng'],
                                                            ['/reinstatement', 'Khôi phục hiệu lực'],
                                                            ['/payment-contract', 'Thanh toán giá trị hợp đồng']  
                                                          ], ],                                                            
        [3, '../img/icon/clock.svg', 'Lịch sử hợp đồng', [
                                                            ['/payment-history', 'Đóng phí bảo hiểm'], 
                                                            ['/claim-history', 'Giải quyết quyền lợi'] ], ],
        [4, '../img/icon/edoc.svg', 'Thư viện tài liệu', [
                                                            ['/epolicy', 'Bộ hợp đồng'], 
                                                            ['/manage-epolicy', 'Quản lý hợp đồng'] ], ]                                                            

        ];
          
        var menuArrForCompany = [
          [12, '../img/icon/folder.svg', 'Thông tin hợp đồng', [
                                                              ['/companypolicyinfo', 'Danh sách hợp đồng theo công ty']]
                                                              , ],
          [4, '../img/icon/edoc.svg', 'Thư viện tài liệu', [
                                                            ['/epolicy', 'Bộ hợp đồng'], 
                                                            ['/companypolicyinvoiceinfo', 'Hóa đơn điện tử'] ], ] 
  
          ];   
        
    if (!getSession(ACCESS_TOKEN) || getSession(IS_MOBILE) || getSession(FROM_SDK)) {
      return(<></>)
    } else {
      const toTitleCase = str => str.split(" ").map(
        e => e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase()).join(" ");
      return(
        <div className={this.props.showMobile?"sidebar-warpper mobile":"sidebar-warpper"} ref={this.handlerSetWrapperRef}>
          <div className={this.props.showMobile?"sidebar mobile":"sidebar"}>
          <Link to={"/account"} onClick={()=>activeSelectedItemAcc(true, 'account-page-id', 'Thông tin tài khoản', '')} key="account-page-id">
          <div className="sidebar__head">
              <div className="avatar medium">
                {((this.state.avatarPath !== '') && (this.state.avatarPath !== 'NOIMAGE'))?(
                  <img src={API_BASE_URL + this.state.avatarPath} alt="" />
                ):(
                  <img src="" alt="" />
                )}
                
                <p className="text basic-bold">{name}</p>
              </div>
              <h3 className="basic-bold bigheight" style={{padding:'8px',textAlign:'center', fontSize: '18px', fontWeight: 700, lineHeight: '20px'}}>{toTitleCase(fullName.toLowerCase())}</h3>
              {clientID !== '' && 
              <p style={{marginTop: '-11px'}}>Điểm thưởng: <span className="basic-red">{formatMoney(point)}</span></p>
              }
          </div>
          </Link>
          <div className="sidebar__navigation">
            { clientID !== '' && getSession(GENDER) && getSession(GENDER) !== 'C' &&
              createMenu(menuArrForExisted, clientID, funcs)
              
            }
            { clientID !== '' && getSession(GENDER) && getSession(GENDER) === 'C' &&
              createMenu(menuArrForCompany, clientID, funcs)
              
            }
            {clientID !== '' && getSession(GENDER) && getSession(GENDER) !== 'C' &&
              createMenu(menuArrForAll, clientID, funcs)
            }
            { clientID === '' &&
            <Link to="/familypayment" onClick={()=>callbackApp(true)} key="link-potential-id">
              <div className="sidebar-item dropdown" id="sidebar-item-potential">
                <div className="dropdown__content" id="dropdown-content-potential">
                  <div className="tab">
                    <i><img src="img/icon/wallet.svg" alt="" /></i>
                    <p>Đóng phí bảo hiểm</p>
                  </div>
                </div>
              </div>
            </Link>
            }
          </div>
          <i className="sidebar-close" onClick={()=>closeMobileSidebar()}><img src="img/icon/close.svg" alt="" /></i>
          </div>
        </div>
      )
    }
  }
}

export default SideBar2;