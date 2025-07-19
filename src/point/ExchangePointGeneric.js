import React, { Component } from 'react';
import ExchangePointFeeRefund from './ExchangePointFeeRefund';
import ExchangePointGivePoint from './ExchangePointGivePoint';
import ExchangePointMobileCard from './ExchangePointMobileCard';
import ExchangePointSuperMarketVoucher from './ExchangePointSuperMarketVoucher';
import ExchangePointEcommerce from './ExchangePointEcommerce';
import ExchangePointDoctorPaper from './ExchangePointDoctorPaper';
import ExchangePointCoBrandedCard from './ExchangePointCoBrandedCard';
import ExchangePointOpenInvestment from './ExchangePointOpenInvestment';
import {getDeviceId, getSession} from "../util/common";
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN} from "../constants";
import {CPSaveLog} from "../util/APIUtils";
import ExchangePointGiftWellnessHub from "./ExchangePointGiftWellnessHub";

class ExchangePointGeneric extends Component {
  constructor(props) {
    super(props);
  };

  componentDidMount() {
    switch (this.props.match.params.id) {
      case '1':
        this.cpSaveLog("Web_Open_ExchangePointSuperMarketVoucher");
        break;
      case '2':
        this.cpSaveLog("Web_Open_ExchangePointMobileCard");
        break;
      case '3':
        this.cpSaveLog("Web_Open_ExchangePointFeeRefund");
        break;
      case '4':
        this.cpSaveLog("Web_Open_ExchangePointGivePoint");
        break;
      case '5':
        this.cpSaveLog("Web_Open_ExchangePointDoctorPaper");
        break;
      case '6':
        this.cpSaveLog("Web_Open_ExchangePointCoBrandedCard");
        break;
      case '8':
        this.cpSaveLog("Web_Open_ExchangePointEcommerce");
        break;
      case '9':
        this.cpSaveLog("Web_Open_ExchangePointOpenInvestment");
        break;
      case '12':
        this.cpSaveLog("Web_Open_ExchangePointEcommerce");
        break;
      case '13':
        this.cpSaveLog("Web_Open_ExchangePointSuperMarketVoucher");
        break;
      default:
        this.cpSaveLog("Web_Open_ExchangePointGeneric");
        break;
    }
  }

  componentWillUnmount() {
    switch (this.props.match.params.id) {
      case '1':
        this.cpSaveLog("Web_Close_ExchangePointSuperMarketVoucher");
        break;
      case '2':
        this.cpSaveLog("Web_Close_ExchangePointMobileCard");
        break;
      case '3':
        this.cpSaveLog("Web_Close_ExchangePointFeeRefund");
        break;
      case '4':
        this.cpSaveLog("Web_Close_ExchangePointGivePoint");
        break;
      case '5':
        this.cpSaveLog("Web_Close_ExchangePointDoctorPaper");
        break;
      case '6':
        this.cpSaveLog("Web_Close_ExchangePointCoBrandedCard");
        break;
      case '8':
        this.cpSaveLog("Web_Close_ExchangePointEcommerce");
        break;
      case '9':
        this.cpSaveLog("Web_Close_ExchangePointOpenInvestment");
        break;
      case '12':
        this.cpSaveLog("Web_Close_ExchangePointEcommerce");
        break;
      case '13':
        this.cpSaveLog("Web_Close_ExchangePointSuperMarketVoucher");
        break;
      default:
        this.cpSaveLog("Web_Close_ExchangePointGeneric");
        break;
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
      this.setState({renderMeta: true});
    }).catch(error => {
      this.setState({renderMeta: true});
    });
  }

  render() {
    return (
      <div>
        {this.props.match.params.id === '3' && <ExchangePointFeeRefund categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '4' && <ExchangePointGivePoint categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '2' && <ExchangePointMobileCard categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '1' && <ExchangePointSuperMarketVoucher categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '8' && <ExchangePointEcommerce categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '5' && <ExchangePointDoctorPaper categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '6' && <ExchangePointCoBrandedCard categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '9' && <ExchangePointOpenInvestment categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '12' && <ExchangePointEcommerce categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '13' && <ExchangePointSuperMarketVoucher categorycd={this.props.match.params.id} />}
        {this.props.match.params.id === '14' && <ExchangePointGiftWellnessHub categorycd={this.props.match.params.id} />}


      </div>

    )
  }
}

export default ExchangePointGeneric;
