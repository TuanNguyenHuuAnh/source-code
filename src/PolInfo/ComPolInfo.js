import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  COMPANY_KEY,
  AUTHENTICATION,
  FULL_NAME,
  CLIENT_PROFILE,
  POL_EXPIRE_DATE,
  POL_LI_NAME,
  POL_POLICY_STATUS,
  POL_CLASS_CD,
  FE_BASE_URL
} from '../constants';
import 'antd/dist/antd.min.css';
import { logoutSession, CPGetProductListByPOLID, CPGetClientProfileByCLIID } from '../util/APIUtils';
import { CPGetPolicyInfoByPOLID } from '../util/APIUtils';
import {formatDate, formatFullName, getDeviceId} from '../util/common';
import lichsugiaodich from '../img/icon/lichsugiaodich.svg';
import sanphambaohiem from '../img/icon/sanpham.svg';
import giatrihopdong from '../img/icon/giatrihopdong.svg';
import nguoithuhuong from '../img/icon/nguoithuhuong.svg';
import benmuabaohiem from '../img/icon/10.1/10.1-icon-person.svg';
import chitiethopdong from '../img/icon/10.1/10.1-icon-thamgiahd.svg';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Link } from 'react-router-dom';
import '../common/Common.css';
import ComPaymentHistoryTab from './ComPaymentHistoryTab';
import {showMessage, getSession} from '../util/common';
import NoticePopup from "../components/NoticePopup";

class ComPolInfo extends Component {

  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN)
        }
      },
      jsonInput2: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          OS: '',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN),
          UserID: getSession(USER_LOGIN),
          PolID: this.props.match.params.id,
          Action: ''
        }
      },
      ClientProfile: null,
      ClientProfileFee: null,
      ClientProfileProducts: null,
      ClientProfileValue: null,
      ClientProfileBene: null,
      ClientProfileAgent: null,
      ClientProfileHistory: null,
      ClientProfileContractMain: null,
      polID: '',
      AgentName: '',
      jsonResponse: null,
      FeeResponse: null,
      ValueResponse: null,
      AgentResponse: null,
      BeneResponse: null,
      ProductsResponse: null,
      isEmpty: true,
      ProvinceList: [],
      PolicyClassCD: '',
      PolicyInfo: null,
      isDropdownFeeDetails: false,
      isDropdownTotalFee: false,
      isHideBorder: false,
      isShowSPBSDetails: false,
      showContractDetail: true,
      showContractInfo: true,
      showAgentInfo: true,
      loadingProducts: false,
      showNoticeEffectiveDate: false,
    }
  }


  componentDidMount() {
    this.callAPIcontractFee();
    this.callAPIcontractProducts();
    this.callAPIcontractValue();
    this.callAPIcontractBene();
    this.callAPIcontractAgent();
    this.callAPIContractMain();
    document.getElementById('contractPO').className = "com-contract-value show-component";
    document.getElementById('contractPOButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
    document.getElementById('contractProducts').className = "contract-products";
    document.getElementById('contractValue').className = "contract-value";
    document.getElementById('contractBene').className = "contract-beneficiary";
    document.getElementById('contractPolDetail').className = "com-contract-value";
    document.getElementById('contractMain').className = "contract-beneficiary";
    document.getElementById('contractPaymentHistory').className = "contract-value";
  }
  callAPIContractMain = () => {
    const request = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'LifeInsuredList',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN),
        ClientID: getSession(CLIENT_ID)
      }
    };
    CPGetClientProfileByCLIID(request).then(Res=> {
      let Response1 = Res.Response;
      if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
          let main = Response1.ClientProfile.filter(item => {
          return item.FullName === getSession(POL_LI_NAME);
        });
        this.setState({ClientProfileContractMain: main});
      }
    }).catch(error => {
      //console.log('error:' + error);
    });
  }
  callAPIcontractFee = () => {
    var jsonState = this.state;
    jsonState.jsonInput2.jsonDataInput.Action = 'PolicyPayment';
    this.setState(jsonState);
    const apiRequest = Object.assign({}, this.state.jsonInput2);
    CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
      let Response1 = Res.Response;
      if (Response1.ErrLog === 'SUCCESSFUL') {
        jsonState.FeeResponse = Response1;
        jsonState.ClientProfileFee = Response1.ClientProfile;
        this.setState({
          ProvinceList: this.sortListByName(jsonState.ClientProfileFee),
        });
        this.setState(jsonState);
      } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }

        })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  callAPIcontractProducts = () => {
    var jsonState = this.state;
    jsonState.jsonInput2.jsonDataInput.Action = 'PolicyProduct';
    jsonState.loadingProducts = true;
    this.setState(jsonState);
    const apiRequest = Object.assign({}, this.state.jsonInput2);
    CPGetProductListByPOLID(apiRequest).then(Res => {
      let Response2 = Res.Response;
      if (Response2.ErrLog === 'SUCCESSFUL') {
        jsonState.ProductsResponse = Response2;
        jsonState.ClientProfileProducts = Response2.Products;
        jsonState.loadingProducts = false;
        this.setState(jsonState);
        // document.getElementById('contractProducts').className = "contract-products";
        // document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      } else if (Response2.NewAPIToken === 'invalidtoken' || Response2.ErrLog === 'APIToken is invalid') {
        // showMessage(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false }

        // })
        this.setState({loadingProducts: false});
      }
    }).catch(error => {
      this.setState({loadingProducts: false});
      this.props.history.push('/maintainence');
    });
  }

  callAPIcontractValue = () => {
    var jsonState = this.state;
    jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAnn';
    this.setState(jsonState);
    const apiRequest = Object.assign({}, this.state.jsonInput2);
    CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
      let Response3 = Res.Response;
      if (Response3.ErrLog === 'SUCCESSFUL') {
        jsonState.ValueResponse = Response3;
        jsonState.ClientProfileValue = Response3.ClientProfile;
        jsonState.PolicyInfo = Response3.PolicyInfo;
        this.setState(jsonState);
        // document.getElementById('contractValue').className = "com-contract-value";
        // document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      } else if (Response3.NewAPIToken === 'invalidtoken' || Response3.ErrLog === 'APIToken is invalid') {
        // showMessage(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false }

        // })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  callAPIcontractBene = () => {
    var jsonState = this.state;
    jsonState.jsonInput2.jsonDataInput.Action = 'PolicyBene';
    this.setState(jsonState);
    const apiRequest = Object.assign({}, this.state.jsonInput2);
    //console.log(apiRequest);
    CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
      //console.log(Res);
      let Response4 = Res.Response;
      if (Response4.ErrLog === 'SUCCESSFUL') {
        jsonState.AgentResponse = Response4;
        jsonState.ClientProfileBene = Response4.ClientProfile;
        this.setState(jsonState);
        // document.getElementById('contractBene').className = "contract-2beneficiary";
        // document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
        //Alert.success("Successfully Call Bene");
      } else if (Response4.NewAPIToken === 'invalidtoken' || Response4.ErrLog === 'APIToken is invalid') {
        // showMessage(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false }

        // })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  callAPIcontractAgent = () => {
    var jsonState = this.state;
    jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAgent';
    this.setState(jsonState);
    const apiRequest = Object.assign({}, this.state.jsonInput2);
    //console.log(apiRequest);
    CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
      //console.log(Res);
      let Response5 = Res.Response;
      if (Response5.ErrLog === 'SUCCESSFUL') {
        jsonState.AgentResponse = Response5;
        jsonState.ClientProfileAgent = Response5.ClientProfile;

        this.setState(jsonState);
        // document.getElementById('contractAgent').className = "contract-advise";
        // document.getElementById('contracPaymenttHistoryButton').className = "com-contract-list-menu__item";
        //Alert.success("Successfully Call Agent");

      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        // showMessage(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false }

        // })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }


  sortListByName(obj) {
    obj.sort((a, b) => {
      if (a.ProvinceCode < b.ProvinceCode) return -1;
      else if (a.ProvinceCode > b.ProvinceCode) return 1;
      else return 0;
    });
    return obj;
  }
  render() {
    const showContractDetail = () => {
      this.setState({ showContractDetail: !this.state.showContractDetail });
    }
    const showContractInfo = () => {
      this.setState({ showContractInfo: !this.state.showContractInfo });
    }
    const showAgentInfo = () => {
      this.setState({ showAgentInfo: !this.state.showAgentInfo });
    }
    const buttonClickcontractProducts = () => {
      if (this.state.loadingProducts) {
        document.getElementById('contractValue').className = "com-contract-value";
        document.getElementById('contractBene').className = "contract-beneficiary";
        // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
        document.getElementById('contractProductsButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
        document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
        document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
        document.getElementById('contractPO').className = "com-contract-value";
        document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
        document.getElementById('contractPolDetail').className = "com-contract-value";
        document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
        document.getElementById('contractMain').className = "contract-beneficiary";
        document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
        document.getElementById('contractPaymentHistory').className = "com-contract-value";
        document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";
        setTimeout(buttonClickcontractProducts, 100);
        return;
      }
      let contractcss = "contract-products show-component";
      if (this.state.ClientProfileProducts && this.state.ClientProfileProducts.length > 1) {
        contractcss = "contract-2products show-component";
      }
      document.getElementById('contractProducts').className = contractcss;
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractBene').className = "contract-beneficiary";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";
    }

    const buttonClickcontractValue = () => {
      // document.getElementById('contractFee').className = "contract-fee";
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractValue').className = "com-contract-value show-component";
      document.getElementById('contractBene').className = "contract-beneficiary";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";

    }

    const buttonClickcontractMain = () => {
      let contractcss = "contract-beneficiary show-component";
      if (this.state.ClientProfileContractMain && this.state.ClientProfileContractMain.length > 1) {
        contractcss = "contract-2beneficiary show-component";
      }
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractBene').className = "contract-beneficiary";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = contractcss;
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";

    }

    const buttonClickcontractBene = () => {
      let contractcss = "contract-beneficiary show-component";
      if (this.state.ClientProfileBene && this.state.ClientProfileBene.length > 1) {
        contractcss = "contract-2beneficiary show-component";
      }
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractBene').className = contractcss;
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";

    }

    const buttonClickcontractPaymentHistory = () => {
      // document.getElementById('contractFee').className = "contract-fee";
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractBene').className = "contract-beneficiary";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";

      document.getElementById('contractPaymentHistory').className = "com-contract-value show-component";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";

    }

    const buttonClickcontractPO = () => {
      // document.getElementById('contractFee').className = "contract-fee";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBene').className = "contract-beneficiary";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value show-component";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractPolDetail').className = "com-contract-value";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";

    }
    const buttonClickcontractPolDetail = () => {
      // document.getElementById('contractFee').className = "contract-fee";
      // document.getElementById('contractFeeButton').className = "com-contract-list-menu__item";
      document.getElementById('contractProducts').className = "contract-products";
      document.getElementById('contractProductsButton').className = "com-contract-list-menu__item";
      document.getElementById('contractValue').className = "com-contract-value";
      document.getElementById('contractValueButton').className = "com-contract-list-menu__item";
      document.getElementById('contractBene').className = "contract-beneficiary";
      document.getElementById('contractBeneButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPO').className = "com-contract-value";
      document.getElementById('contractPOButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPolDetail').className = "com-contract-value show-component";
      document.getElementById('contractPolDetailButton').className = "com-contract-list-menu__item com-contract-list-menu__item__active";
      document.getElementById('contractMain').className = "contract-beneficiary";
      document.getElementById('contractMainButton').className = "com-contract-list-menu__item";
      document.getElementById('contractPaymentHistory').className = "com-contract-value";
      document.getElementById('contractPaymentHistoryButton').className = "com-contract-list-menu__item";

    }

    const showNoticeEffectiveDate = (m) => {
      if (m) {
        this.setState({ showNoticeEffectiveDate: true, msg: m });
      }
    }
    const closeNoticeEffectiveDate = () => {
      this.setState({ showNoticeEffectiveDate: false });
    }

    const toTitleCase = str => str.split(" ").map(
      e => e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase()).join(" ");

    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    var showFundValue1 = () => {
      var jsonState = this.state;
      if (document.getElementById("fundvalue1").className === "header2 dropdown show") {
        document.getElementById("fundvalue1").className = "header2 dropdown";
      } else {
        document.getElementById("fundvalue1").className = "header2 dropdown show";
      }
    }
    var showFundValue2 = () => {
      var jsonState = this.state;
      if (document.getElementById("fundvalue2").className === "contract-fee__form__item dropdown show") {
        document.getElementById("fundvalue2").className = "contract-fee__form__item dropdown";
      } else {
        document.getElementById("fundvalue2").className = "contract-fee__form__item dropdown show";
      }
    }
    var showFundValue3 = () => {
      var jsonState = this.state;
      if (document.getElementById("fundvalue3").className === "contract-fee__form__item dropdown show") {
        document.getElementById("fundvalue3").className = "contract-fee__form__item dropdown";
      } else {
        document.getElementById("fundvalue3").className = "contract-fee__form__item dropdown show";
      }
    }
    var dropdownContent = (index, prdIndex) => {
      const jsonState = this.state;
      if (document.getElementById('dropdownContent' + index + prdIndex).className === "card__footer-item dropdown show") {
        document.getElementById('dropdownContent' + index + prdIndex).className = "card__footer-item dropdown";
      } else {
        document.getElementById('dropdownContent' + index + prdIndex).className = "card__footer-item dropdown show";
      }
    }
    var dropdownTotalFee = () => {
      const jsonState = this.state;
      if (document.getElementById('TotalFee').className === "dropdown show") {
        document.getElementById('TotalFee').className = "dropdown";
        jsonState.isDropdownTotalFee = false;
        this.setState(jsonState);
      } else {
        document.getElementById('TotalFee').className = "dropdown show";
        jsonState.isDropdownTotalFee = true;
        this.setState(jsonState);
      }
    }
    var dropdownFeeDetails = () => {
      const jsonState = this.state;
      if (document.getElementById('FeeDetails').className === "dropdown show") {
        document.getElementById('FeeDetails').className = "dropdown";
        jsonState.isDropdownFeeDetails = false;
        this.setState(jsonState);
      } else {
        document.getElementById('FeeDetails').className = "dropdown show";
        jsonState.isDropdownFeeDetails = true;
        this.setState(jsonState);
      }
    }

    let profile = null;
    if (getSession(CLIENT_PROFILE)) {
      profile = JSON.parse(getSession(CLIENT_PROFILE));
    }
    return (
      <div>

        <main className="logined" id="main-id">
          <div className="main-warpper basic-mainflex">


            <section className="sccontract-warpper2">

              <div className="breadcrums">
                <div className="breadcrums__item">
                  <p>Thông tin hợp đồng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p><Link to="/companypolicyinfo" className='breadcrums__link'>Danh sách hợp đồng theo công ty</Link></p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>{this.props.match.params.id}</p>
                  <span></span>
                </div>
              </div>


              <div className="com-contract-list-menu" style={{paddingTop: '0px'}}>
                <div className="com-contract-list-menu__item" id="contractPOButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractPO()}>
                    <div className="item__icon">
                      <img src={benmuabaohiem} alt="" />
                    </div>
                    <div className="item__title"><p>Bên mua bảo hiểm</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>
                <div className="com-contract-list-menu__item" id="contractPolDetailButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractPolDetail()}>
                    <div className="item__icon">
                      <img src={chitiethopdong} alt="" />
                    </div>
                    <div className="item__title"><p>Chi tiết hợp đồng</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>

                <div className="com-contract-list-menu__item" id="contractProductsButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractProducts()}>
                    <div className="item__icon">
                      <img src={sanphambaohiem} alt="" />
                    </div>
                    <div className="item__title"><p>Sản phẩm</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>

                <div className="com-contract-list-menu__item" id="contractMainButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractMain()}>
                    <div className="item__icon">
                      <img src={nguoithuhuong} alt="" />
                    </div>
                    <div className="item__title"><p>Người được bảo hiểm chính</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>

                <div className="com-contract-list-menu__item" id="contractValueButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractValue()}>
                    <div className="item__icon">
                      <img src={giatrihopdong} alt="" />
                    </div>
                    <div className="item__title"><p>Các giá trị hợp đồng</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>


                <div className="com-contract-list-menu__item" id="contractBeneButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractBene()}>
                    <div className="item__icon">
                      <img src={nguoithuhuong} alt="" />
                    </div>
                    <div className="item__title"><p>Người thụ hưởng</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>





                <div className="com-contract-list-menu__item" id="contractPaymentHistoryButton">
                  <div className="contract-menu-content" onClick={() => buttonClickcontractPaymentHistory()}>
                    <div className="item__icon">
                      <img src={lichsugiaodich} alt="" />
                    </div>
                    <div className="item__title"><p>Lịch sử giao dịch</p></div>
                    <div className="item__polygon-active" style={{ margin: '25px' }}></div>
                  </div>
                </div>
              </div>



              <div className="tthd-main" style={{ marginLeft: '-15px', marginRight: '-15px'}}>
                <div className="tthd-main__wrapper contract-margin">
                  <LoadingIndicator area="policy-info" />

                  <div className="com-contract-value" id="contractPO">


                    <div className="card-extend-container">

                      <div className="card-extend-wrapper">
                        {profile !== null &&
                          <div className="contract-fee__form">
                            <div className="contract-fee__form__item contract-fee__form__item-flexRow">
                              <p>Tên khách hàng</p>
                              <p>{profile[0].FullName}</p>
                            </div>
                            <div className="contract-fee__form__item contract-fee__form__item-flexRow">
                              <p>Giấy phép thành lập</p>
                              <p>{profile[0].POID}</p>
                            </div>
                            <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6'}}>
                              <h5 style={{fontSize: '16px', color: '#727272', marginBottom: '5px'}}>Địa chỉ</h5>
                              <div className="contract-fee__form__item-footer__item">

                                <p className="add-dot">Liên lạc thư tín</p>
                                <p>{profile[0].Address?profile[0].Address:'-'}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p className="add-dot">Liên lạc khác</p>
                                <p>{(profile[0].OtherAddress && profile[0].OtherAddress.replaceAll(' ', '') !== ',')?profile[0].OtherAddress: '-'}</p>
                              </div>
                            </div>
                            <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6'}}>
                              <h5 style={{fontSize: '16px', color: '#727272', marginBottom: '5px'}}>Điện thoại</h5>
                              <div className="contract-fee__form__item-footer__item">
                                <p className="add-dot">Nhà</p>
                                <p>{profile[0].HomePhone?profile[0].HomePhone: '-'}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p className="add-dot">Văn phòng</p>
                                <p>{profile[0].BusinessPhone?profile[0].BusinessPhone:'-'}</p>
                              </div>
							                <div className="contract-fee__form__item-footer__item">
                                <p className="add-dot">Di động</p>
                                <p>{profile[0].CellPhone?profile[0].CellPhone:'-'}</p>
                              </div>
                            </div>
                            <div className="contract-fee__form__item-footer">
                              <div className="contract-fee__form__item-footer__item">
                                <p style={{fontSize: '16px'}}>Email</p>
                                <p style={{fontSize: '16px'}}>{profile[0].Email?profile[0].Email:'-'}</p>
                              </div>
                            </div>
                          </div>
                        }

                      </div>


                    </div>

                  </div>
                  <div className="com-contract-value" id="contractPolDetail">


                    <div className="card-extend-container">

                      <div className="card-extend-wrapper">

                        <div className="contract-fee__form">
                          {this.state.showContractDetail ? (
                            ((this.state.ClientProfileProducts !== null) && (this.state.ClientProfileProducts !== undefined)
                              && this.state.ClientProfileProducts.map((item, index) => (
                                ((item[0].CVGNum === '01') && (this.state.PolicyClassCD === 'TL')) || ((item[0].CVGNum === '02') && (this.state.PolicyClassCD !== 'TL')) &&
                                <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>

                                  <div className="contract-fee__form__item-footer__item__head">
                                    <p className='basic-semibold' style={{fontSize: '16px'}}>Chi tiết hợp đồng</p>
                                    <p>
                                      <img style={{height: '9px'}} onClick={() => showContractDetail()}
                                        src="../../../img/icon/arrow-down.svg"
                                      /></p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                  </div>

                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Số hợp đồng</p>
                                    <p>{this.props.match.params.id}</p>
                                  </div>

                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Bên mua bảo hiểm</p>
                                    <p>{getSession(FULL_NAME)}</p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Người được bảo hiểm</p>
                                    <p>{formatFullName(item[0].PolicyLIName.split(';')[1])}</p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Tình trạng</p>
                                    <p>{getSession(POL_POLICY_STATUS)}</p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Ngày hiệu lực</p>
                                    <p>{item[0].PolIssEffDate.split(';')[1]}</p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Ngày đáo hạn</p>
                                    <p>{getSession(POL_POLICY_STATUS) !== 'Hết hiệu lực'?getSession(POL_EXPIRE_DATE): '-'}</p>
                                  </div>
                                  {(this.state.ClientProfileFee !== null) && (this.state.ClientProfileFee[0] != null) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p>Định kỳ đóng phí</p>
                                      <p>{this.state.ClientProfileFee[0].Frequency.split(';')[1]}</p>
                                    </div>
                                  }

                                </div>
                              )))
                          ) : (
                            <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>

                              <div className="contract-fee__form__item-footer__item__head">
                                <p style={{fontSize: '16px', fontWeight: '500', color: '#727272'}}>Chi tiết hợp đồng</p>
                                <p><img onClick={() => showContractDetail()} src="../../../img/icon/arrow-left-grey.svg" alt="arrow-left" /></p>
                              </div>
                            </div>
                          )
                          }
                          {this.state.showContractInfo ? (
                            (this.state.ClientProfileFee !== null) && this.state.ClientProfileFee.map((item, index) => (
                              !item.TotalDeposit ? (
                                <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>
                                  <div className="contract-fee__form__item-footer__item__head">
                                    <p className='basic-semibold' style={{ fontSize: '16px' }}>Thông tin phí bảo hiểm</p>
                                    <p>
                                      <img style={{ height: '9px' }} onClick={() => showContractInfo()}
                                        src="../../../img/icon/arrow-down.svg"
                                        alt="arrow-down"
                                        className="bottom-mar"
                                      /></p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>{item.PolMPremAmt.split(';')[0]}</p>
                                    <p>{item.PolMPremAmt.split(';')[1]}</p>
                                  </div>

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p>Phí dự tính định kỳ</p>
                                      <p>{item.PolNextMPremAmt.split(';')[1]}</p>
                                    </div>
                                  }
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Tổng phí bảo hiểm đã đóng</p>
                                    <p>{item.PolSndryAmt.split(';')[1]}</p>
                                  </div>

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <h6>Xem theo loại phí</h6>
                                    </div>
                                  }

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>Phí cơ bản</p>
                                      <p>{item.PolMPremAmt.split(';')[1]}</p>
                                    </div>
                                  }

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&

                                    item.ExcessPrem != null &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>{item.ExcessPrem.split(';')[0]}</p>
                                      <p>{item.ExcessPrem.split(';')[1] && item.ExcessPrem.split(';')[1] !== 'null' ? item.ExcessPrem.split(';')[1] : '-'}</p>
                                    </div>

                                  }


                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) ? (
                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>Phí đóng trước cho kỳ tới</p>
                                      <p>{item.PremiumSuspense ? item.PremiumSuspense.split(';')[1] : '-'}</p>
                                    </div>

                                  ) : (
                                    <div className="contract-fee__form__item-footer__item">
                                      <p>Phí đóng trước cho kỳ tới</p>
                                      <p>{item.PremiumSuspense ? item.PremiumSuspense.split(';')[1] : '-'}</p>
                                    </div>
                                  )
                                  }
                                </div>
                              ) : (
                                <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>
                                  <div className="contract-fee__form__item-footer__item__head">
                                    <p className='basic-semibold' style={{ fontSize: '16px' }}>Thông tin phí bảo hiểm</p>
                                    <p>
                                      <img style={{ height: '9px' }} onClick={() => showContractInfo()}
                                        src="../../../img/icon/arrow-down.svg"
                                        alt="arrow-down"
                                        className="bottom-mar"
                                      /></p>
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                  </div>
                                  <div className="contract-fee__form__item-footer__item">
                                    <p>{item.PolMPremAmt.split(';')[0]}</p>
                                    <p>{item.PolMPremAmt.split(';')[1]}</p>
                                  </div>

                                  <div className="contract-fee__form__item-footer__item">
                                    <p>{item.PolSndryAmt.split(';')[0]}</p>
                                    <p>{item.PolSndryAmt.split(';')[1]}</p>
                                  </div>

                                  <div className="contract-fee__form__item-footer__item">
                                    <p>Tổng phí bảo hiểm đã đóng</p>
                                    <p>{item.TotalDeposit.split(';')[1]}</p>
                                  </div>

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item" style={{ paddingTop: '7px' }}>
                                      <h6>Xem theo loại phí</h6>
                                    </div>
                                  }

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>{item.BasicPrem.split(';')[0]}</p>
                                      <p>{item.BasicPrem.split(';')[1] ? item.BasicPrem.split(';')[1] : '-'}</p>
                                    </div>
                                  }

                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) &&
                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>{item.ExcessPrem.split(';')[0]}</p>
                                      <p>{item.ExcessPrem.split(';')[1] && item.ExcessPrem.split(';')[1] !== 'null' ? item.ExcessPrem.split(';')[1] : '-'}</p>
                                    </div>
                                  }
                                  {((getSession(POL_CLASS_CD) === 'UL') || (getSession(POL_CLASS_CD) === 'IL')) ? (

                                    <div className="contract-fee__form__item-footer__item">
                                      <p className='add-dot'>Phí đóng trước cho kỳ tới</p>
                                      <p>{item.PremiumSuspense ? item.PremiumSuspense.split(';')[1] : '-'}</p>
                                    </div>
                                  ) : (
                                    <div className="contract-fee__form__item-footer__item">
                                      <p>Phí đóng trước cho kỳ tới</p>
                                      <p>{item.PremiumSuspense ? item.PremiumSuspense.split(';')[1] : '-'}</p>
                                    </div>
                                  )
                                  }

                                </div>
                              )
                            ))
                          ) : (
                            <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>
                              <div className="contract-fee__form__item-footer__item">
                                <p className='basic-bold' style={{fontSize: '16px', fontWeight: '500'}}>Thông tin phí bảo hiểm</p>
                                <p><img onClick={() => showContractInfo()} src="../../../img/icon/arrow-left-grey.svg" alt="arrow-left" /></p>
                              </div>
                            </div>
                          )}

                          {this.state.showAgentInfo ? (
                            (this.state.ClientProfileAgent !== null) && this.state.ClientProfileAgent.map((item, index) => (
                              <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>

                                <div className="contract-fee__form__item-footer__item__head">
                                  <p className='basic-semibold' style={{fontSize: '16px'}}>Thông tin đại lý bảo hiểm và nhân viên thu phí</p>
                                  <p>
                                    <img style={{height: '9px'}} onClick={() => showAgentInfo()}
                                      src="../../../img/icon/arrow-down.svg"
                                      alt="arrow-down"
                                      className="bottom-mar"
                                    /></p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>Đại lý bảo hiểm</p>
                                  <p>{item.PrimaryAgent.split(';')[1]}</p>
                                </div>

                                <div className="contract-fee__form__item-footer__item">
                                  <p>Nhân viên thu phí</p>
                                  <p>{item.ServAgent.split(';')[1]}</p>
                                </div>
                              </div>
                            ))
                          ) : (
                            <div className="contract-fee__form__item-footer" style={{ 'border-bottom': '1px solid #e6e6e6' }}>
                              <div className="contract-fee__form__item-footer__item">
                                <p style={{fontSize: '16px', fontWeight: '500', color: '#727272'}}>Thông tin đại lý bảo hiểm và nhân viên thu phí</p>
                                <p><img onClick={() => showAgentInfo()} src="../../../img/icon/arrow-left-grey.svg" alt="arrow-left" /></p>
                              </div>
                            </div>
                          )}
                        </div>



                      </div>


                    </div>

                  </div>

                  <div className="contract-2products" id="contractProducts">
                    {((this.state.ClientProfileProducts !== null)
                      && (this.state.ClientProfileProducts !== '')
                      && (this.state.ClientProfileProducts !== undefined)) && this.state.ClientProfileProducts.map((item, index) => (

                            ((index === 0) ? (
                              item.map((mainproductsList, mainprdIndex) => (
                                <div className="card-extend-container basic-padding-left-com" style={{alignItems: 'start'}}>
                                <div className="card-extend-wrapper2">
                                <div className="item">
                                  <div className="card__label">
                                    <p className="basic-bold">Bảo hiểm chính</p>
                                  </div>
                                  <div className="card" style={{ cursor: 'default' }}>
                                    <div className="card__header">
                                      <h4 className="basic-bold">{mainproductsList.ProductName.split(';')[1]}</h4>
                                      <p>Dành cho {toTitleCase(mainproductsList.PolicyLIName.split(';')[1].toLowerCase())}</p>
                                    </div>
                                    <div className="card__footer">

                                      <div className="card__footer-item2">
                                        <p>Số tiền bảo hiểm</p>
                                        {mainproductsList.FaceAmount.split(';')[1] === 'Chi tiết theo quy tắc điều khoản' ?
                                          <p >Chi tiết theo điều khoản Hợp đồng</p> :
                                          <p>{mainproductsList.FaceAmount.split(';')[1]}</p>
                                        }
                                      </div>
                                      <div className="card__footer-item2">
                                        <p>Ngày hiệu lực</p>
                                        <p>{mainproductsList.PolIssEffDate.split(';')[1]}</p>
                                      </div>
                                      <div className="card__footer-item2">
                                        <p>Ngày đáo hạn</p>
                                        <p>{getSession(POL_POLICY_STATUS) !== 'Hết hiệu lực'?mainproductsList.PolExpiryDate.split(';')[1]: '-'}</p>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                </div>
                        </div>
                              ))
                            ) : (


                                  item.map((productsList, prdIndex) => (
                                    <div className="card-extend-container basic-padding-left-com" style={{alignItems: 'start'}}>
                                    <div className="card-extend-wrapper2">
                                    <div className="item">
                                    <div className="card__label card__label-brown">
                                      <p className="basic-bold">Bảo hiểm BỔ SUNG</p>
                                    </div>
                                  <div className="card">
                                    <div className="card__header">
                                      <h4 className="basic-bold">{productsList.ProductName.split(';')[1]}</h4>
                                      <p>Dành cho {toTitleCase(productsList.PolicyLIName.split(';')[1].toLowerCase())}</p>
                                    </div>
                                    <div className="card__footer">

                                      <div className="card__footer-item2" style={{ borderBottom: '1px solid #e7e7e7', padding: '20px 12px', margin: '0px' }}>
                                        <p>Số tiền bảo hiểm</p>
                                        {productsList.FaceAmount.split(';')[1] === 'Chi tiết theo quy tắc điều khoản' ?
                                          <p>Chi tiết theo điều khoản Hợp đồng</p> :
                                          <p>{productsList.FaceAmount.split(';')[1]}</p>
                                        }
                                      </div>
                                      <div className="card__footer-item2" style={{ borderBottom: '1px solid #e7e7e7', padding: '20px 12px', margin: '0px' }}>
                                        <p>Ngày hiệu lực</p>
                                        {productsList.CvgNote &&
                                            <i className="info-icon-light" style={{ marginTop: '-6.5px', width: '245px'
                                            }} onClick={(event) => {
                                              event.stopPropagation();
                                              showNoticeEffectiveDate(productsList.CvgNote);
                                            }}
                                            ><img src="../../img/icon/info.svg" alt="information-icon" className="info-icon-light"
                                            /></i>
                                        }
                                        <p>{productsList.PolIssEffDate.split(';')[1]}</p>
                                      </div>
                                      <div className="card__footer-item2">
                                        <p>Ngày đáo hạn</p>
                                        <p>{getSession(POL_POLICY_STATUS) !== 'Hết hiệu lực'?productsList.PolExpiryDate.split(';')[1]:'-'}</p>
                                      </div>


                                    </div>
                                  </div>
                                  </div>
                                  </div>
                        </div>
                                  ))
                                
                             
                            ))

                      ))}
                  </div>

                  <div className="contract-2beneficiary" id="contractMain">
                    {(this.state.ClientProfileContractMain) && this.state.ClientProfileContractMain.map((item, index) => (
                      <div className='card-extend-container basic-padding-left-com' style={{ alignItems: 'start' }}>
                        <div className="card-extend-wrapper3">
                          <div className="item">
                            <div className="card">
                              <div className="card__header">
                                <h4 className="basic-bold">{formatFullName(getSession(POL_LI_NAME))}</h4>
                              </div>
                              <div className="card__footer" style={{ paddingLeft: '12px', paddingRight: '12px' }}>
                                <div className="card__footer-item">
                                  <p>Ngày sinh</p>
                                  <p style={{textAlign: 'right'}}>{formatDate(item.DOB)}</p>
                                </div>
                                <div className="card__footer-item">
                                  <p>Giới tính</p>
                                  <p style={{textAlign: 'right'}}>{item.Gender === 'M'? 'Nam': 'Nữ'}</p>
                                </div>
                                <div className="card__footer-item">
                                  <p>Số giấy tờ tùy thân</p>
                                  <p style={{textAlign: 'right'}}>{item.POID}</p>
                                </div>
                                <div className="card__footer-item" style={{marginTop:'9px'}}>
                                  <p style={{minWidth: '150px', lineHeight:'20px', marginBottom: '-5px'}}>Địa chỉ</p>
                                  <p style={{lineHeight:'20px', textAlign: 'right', marginBottom: '-5px'}}>{item.Address?item.Address: '-'}</p>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}

                  </div>

                  <div className="com-contract-value" id="contractValue">
                    {(this.state.ClientProfileValue != null) && this.state.ClientProfileValue.map((item, index) => (
                      item.PolicyClassCD === 'UL'?(
                      <div className="card-extend-container">
                        <div className="card-extend-wrapper">
                          <div className="contract-fee__form contract-fee__form2">
                            <div className="contract-fee__form__item contract-fee__form__item-flexRow" style={{borderBottom: '0px solid #e6e6e6', paddingBottom: '0px'}}>
                              <p className="basic-semibold" style={{color: '#292929'}}>Thông tin giá trị hợp đồng</p>
                              <p className="basic-semibold"></p>
                            </div>
                            <div className="contract-fee__form__item-footer" style={{paddingTop: '17px'}}>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Giá trị tài khoản đầu ngày</p>
                                <p>{item.PolAccountValue.split(';')[1]}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Tổng phí bảo hiểm đã được phân bổ</p>
                                <p>{item.AllocateMPrem.split(';')[1]}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Lãi đầu tư</p>
                                <p>{item.ACFIP.split(';')[1]}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Thưởng duy trì hợp đồng</p>
                                <p>{item.LoyaltyBonus?item.LoyaltyBonus.split(';')[1]:'-'}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Chi phí rủi ro và quản lý hợp đồng</p>
                                <p>{item.TotalDeduct.split(';')[1]}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Tiền rút giá trị tài khoản (Đã bao gồm phí rút)</p>
                                <p>{item.NetWithDrawal.split(';')[1]}</p>
                              </div>
                              <div className="contract-fee__form__item-footer__item">
                                <p>Tạm ứng từ giá trị hoàn lại</p>
                                <p>{item.PrincIntAmt?item.PrincIntAmt.split(';')[1]:'-'}</p>
                              </div>
                              {/* <div className="contract-fee__form__item-footer__item">
                                <p>Tổng quyền lợi bảo hiểm hưu trí tích lũy</p>
                                <p>-</p>
                              </div> */}
                            </div>
                          </div>
                        </div>
                      </div>
                      ):(
                        item.PolicyClassCD === 'IL'?(
                          <div className="card-extend-container">
                          <div className="card-extend-wrapper">
                            <div className="contract-fee__form contract-fee__form2">
                              <div className="contract-fee__form__item contract-fee__form__item-flexRow" style={{borderBottom: '0px solid #e6e6e6', paddingBottom: '0px'}}>
                                <p className="basic-semibold" style={{color: '#292929'}}>Thông tin giá trị hợp đồng</p>
                                <p className="basic-semibold"></p>
                              </div>
                              <div className="contract-fee__form__item-footer" style={{paddingTop: '17px'}}>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>Giá trị tài khoản đầu ngày</p>
                                  <p>{item.PolAccountValue.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{item.AllocateMPrem.split(';')[0]}</p>
                                  <p>{item.AllocateMPrem.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{item.LoyaltyBonus.split(';')[0]}</p>
                                  <p>{item.LoyaltyBonus.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{item.CiaShrtAmt.split(';')[0]}</p>
                                  <p>{item.CiaShrtAmt.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{item.NetWithDrawal.split(';')[0]}</p>
                                  <p>{item.NetWithDrawal.split(';')[1]}</p>
                                </div>

                              </div>
                            </div>
                          </div>
                          </div> 

                        ):(
                          <div className="card-extend-container">
                          <div className="card-extend-wrapper">
                            <div className="contract-fee__form contract-fee__form2">
                              <div className="contract-fee__form__item contract-fee__form__item-flexRow" style={{borderBottom: '0px solid #e6e6e6', paddingBottom: '0px'}}>
                                <p className="basic-semibold" style={{color: '#292929'}}>Thông tin giá trị hợp đồng</p>
                                <p className="basic-semibold"></p>
                              </div>
                              <div className="contract-fee__form__item-footer" style={{paddingTop: '17px'}}>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>Giá trị hợp đồng</p>
                                  <p>{item.LastAnnDate.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                    <p className="add-dot">Lãi chia tích lũy</p>
                                    <p>{item.Dividend.split(';')[1]}</p>
                                </div>
                                {item.showDividendNote === true &&
                                <div className="contract-fee__form__item-footer__item">
                                    <p className="add-dot_remove-dot">(đã bao gồm trong Giá trị hợp đồng)</p>
                                    <p></p>
                                </div>  
                                }
                                <div className="contract-fee__form__item-footer__item">
                                  <p>Tiền mặt định kỳ</p>
                                  <p>{item.Coupon.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{'Tạm ứng tiền mặt (OPL)'}</p>
                                  <p>{item.Loan.split(';')[1]}</p>
                                </div>
                                <div className="contract-fee__form__item-footer__item">
                                  <p>{'Đóng phí bảo hiểm tự động (APL)'}</p>
                                  <p>{item.APL.split(';')[1]}</p>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        )
                      )
                    ))}
                  </div>
                  {this.state.ClientProfileBene?(
                  <div className="contract-2beneficiary" id="contractBene">
                  {this.state.ClientProfileBene.map((item, index) => (
                    <div className='card-extend-container basic-padding-left-com' style={{ alignItems: 'start' }}>
                      <div className="card-extend-wrapper3">
                        <div className="item">
                          <div className="card">
                            <div className="card__header">
                              <h4 className="basic-bold">{formatFullName(item.FullName.split(';')[1])}</h4>
                              <p>Tỉ lệ thụ hưởng: {item.Note.split(';')[1]}</p>
                            </div>
                            <div className="card__footer" style={{paddingLeft: '12px', paddingRight: '12px'}}>
                              <div className="card__footer-item">
                                <p>Ngày sinh</p>
                                <p style={{textAlign: 'right'}}>{formatDate(item.DOB.split(';')[1])}</p>
                              </div>
                              <div className="card__footer-item">
                                <p>Giới tính</p>
                                <p style={{textAlign: 'right'}}>{item.Gender.split(';')[1] === 'M'? 'Nam': 'Nữ'}</p>
                              </div>
                              <div className="card__footer-item">
                                <p>Số giấy tờ tùy thân</p>
                                <p style={{textAlign: 'right'}}>{item.POID.split(';')[1]}</p>
                              </div>
                              <div className="card__footer-item" style={{marginTop:'9px'}}>
                                <p style={{minWidth: '150px', lineHeight:'20px', marginBottom: '-5px'}}>Địa chỉ</p>
                                <p style={{lineHeight:'20px', textAlign: 'right', marginBottom: '-5px'}}>{item.Address.split(';')[1]}</p>
                              </div>
                            </div>
                          </div>
                        </div>

                      </div>
                    </div>
                  ))}
                  </div>
                  ):(
                    <div className="contract-2beneficiary" id="contractBene">
                      <div className="insurance">
                        <div className="empty">
                          <div className="icon">
                            <img src="../../../img/no-data(6).svg" alt="no-data" />
                          </div>
                          <p style={{paddingTop:'20px'}}>Chưa có thông tin người thụ hưởng</p>
                        </div>
                      </div>
                    </div>

                  )}
                  <div className="contract-advise" id="contractAgent">
                    {(this.state.ClientProfileAgent !== null) && this.state.ClientProfileAgent.map((item, index) => (
                      <div className="consulting-service">
                        <div className="avatar-field">
                          <div className="avatar">
                            <img src="../img/ava_tvtc.png" alt="" />
                          </div>
                          <h3 className="basic-bold">{this.state.AgentName}</h3>
                          <p>{item.AgentLevel}</p>
                        </div>
                        <div className="form">
                          <div className="form__item">
                            <p>Tình trạng</p>
                            <div className="dcstatus" style={{ alignItems: 'center', display: 'flex' }}>
                              {item.AgentStatus === 'Term' ? (
                                <p className="inactiveContent">Ngưng hoạt động</p>
                              ) : (
                                <p className="activeContent">Đang hoạt động</p>
                              )}
                            </div>
                          </div>
                          <div className="form__item">
                            <p>Mã đại lý</p>
                            <p>{item.ServAgent.split(';')[1].split('-')[0].trim()}</p>
                          </div>
                          <div className="form__item">
                            <p>Văn phòng</p>
                            <p>{item.CSSaleOffice}</p>
                          </div>
                          {item.AgentStatus !== 'Term' && (
                            <div className="form__item">
                              <p>Điện thoại</p>
                              <p>{item.CellPhone.split(';')[1]}</p>
                            </div>
                          )}
                          {item.AgentStatus !== 'Term' && (
                            <div className="form__item">
                              <p>Email</p>
                              <p>{item.ContactEmail.split(';')[1]}</p>
                            </div>
                          )}
                        </div>
                        {item.AgentStatus !== 'Term' ? (
                          <div className="contact">
                            <div className="mail-icon">
                              {(item.ContactEmail.split(';')[1] != null
                                && item.ContactEmail.split(';')[1] != ''
                                && item.ContactEmail.split(';')[1] != undefined) ?
                                <a href={"mailto:" + item.ContactEmail.split(';')[1]}><img src="../img/icon/icon_email.svg" alt="" /></a> :
                                <a href="mailto: customer.services@dai-ichi-life.com.vn"><img src="../img/icon/icon_email.svg" alt="" /></a>}
                            </div>
                            <div className="phone-icon">
                              <a href={"tel:" + item.CellPhone.split(';')[1]}><img src="../img/icon/icon_phone.svg" alt="" /></a>
                            </div>
                          </div>
                        ) : (
                          <><p><br /></p><p className="basic-bold">Liên hệ với Dai-ichi Life Việt Nam</p><div className="contact">
                            <div className="mail-icon">
                              <a href="mailto: customer.services@dai-ichi-life.com.vn"><img src="../img/icon/icon_email.svg" alt="" /></a>
                            </div>
                            <div className="phone-icon">
                              <a href="tel:02838100888"><img src="../img/icon/icon_phone.svg" alt="" /></a>
                            </div>
                          </div></>
                        )}
                      </div>
                    ))}
                  </div>

                  <div className="com-contract-value" id="contractPaymentHistory">

                    <ComPaymentHistoryTab polID={this.props.match.params.id} />

                  </div>
                </div>
              </div>
            </section>
          </div>
        </main>
        {this.state.showNoticeEffectiveDate &&
            <NoticePopup closePopup={closeNoticeEffectiveDate} title={this.state.title} msg={this.state.msg} imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} />
        }
      </div>

    );

  }

}


export default ComPolInfo;