// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import { Select, DatePicker } from 'antd';
import dayjs from 'dayjs';
import moment from 'moment';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, FULL_NAME, POID, AUTHENTICATION, COMPANY_KEY, CLAIM_TYPE, DOB, CELL_PHONE, EMAIL, ADDRESS, CLAIM_STEPCODE, FE_BASE_URL, IS_MOBILE } from '../../../../sdkConstant';
import { getSession, is18Plus, formatDate, onlyLettersAndSpaces, getDeviceId, getBenifits, haveHC_HS, isHC3, getUrlParameter } from '../../../../sdkCommon';
import { INITIAL_STATE, CLAIM_STATE, PAYMENT_METHOD_CASE, PAYMENT_METHOD_STEP } from '../../CreateClaimSDK';
import { SearchOutlined } from '@ant-design/icons';
import NumberFormat from 'react-number-format';
import {isEmpty} from "lodash";
import LoadingIndicator from '../../../../LoadingIndicator2';

let backCase = false;
class PaymentMethod extends Component {

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
          UserID: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
        }
      },

      stepName: CLAIM_STATE.PAYMENT_METHOD,
      zipCodeList: [],
      bankList: [],

      // paymentMethodState: INITIAL_STATE().paymentMethodState
      paymentMethodState: this.props.paymentMethodState
    }

    this.handlerOnChangeDate = this.onChangeDate.bind(this);
    this.handlerOnChangePaymentMethod = this.onChangePaymentMethod.bind(this);
    this.handlerOnChangeCity = this.onChangeCity.bind(this);
    this.handlerOnChangeBank = this.onChangeBank.bind(this);
    this.handlerOnChangeBankBranchName = this.onChangeBankBranchName.bind(this);
    this.handlerOnChangeBankOfficeName = this.onChangeBankOfficeName.bind(this);
    // Transfer Type handlers
    this.handlerOnChangeBankAccountNo = this.onChangeBankAccountNo.bind(this);
    this.handlerOnChangeBankAccountName = this.onChangeBankAccountName.bind(this);
    // Cash Type handlers
    this.handlerOnChangeReceiverName = this.onChangeReceiverName.bind(this);
    this.handlerOnChangeReceiverPin = this.onChangeReceiverPin.bind(this);
    // this.handlerOnChangeReceiverPinDate = this.onChangeReceiverPinDate.bind(this);
    this.handlerOnChangeReceiverPinLocation = this.onChangeReceiverPinLocation.bind(this);
    this.handlerOnChangeChooseReceiver = this.onChangeChooseReceiver.bind(this);
  }


  componentDidMount() {
    this.initPaymentMethod(this.state);
  }

  componentDidUpdate() {
    if ((this.state.bankList !== this.props.bankList) || (this.state.zipCodeList !== this.props.zipCodeList)) {
      this.initPaymentMethod(this.state);
    }
  }

  initPaymentMethod = (jsonState) => {
    jsonState.zipCodeList = this.props.zipCodeList;
    jsonState.bankList = this.props.bankList;
    jsonState.paymentMethodState = this.props.paymentMethodState;
    if (!jsonState.paymentMethodState.goneInPaymentMethod) {
      if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
        // if (!jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName) {
        //   jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = "";
        //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = "";
        //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = "";
        // }
        jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.DEAD;
      } else if (this.props.paymentMethodState.choseReceiver === 'LI') {
        if ((this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum ? this.props.selectedCliInfo.idNum.trim() : '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.FULL_ABOVE_18;
        } else if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum ? this.props.selectedCliInfo.idNum.trim() : '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.HC_HS_ABOVE_18;
        } else if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum ? this.props.selectedCliInfo.idNum.trim() : '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18;
        }

      } else if (!this.props.paymentMethodState.choseReceiver) {
        if ((this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.FULL_ABOVE_18;

          if (this.props.claimTypeState.isSamePoLi) {
            if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P2') && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName) {
              jsonState.paymentMethodState.healthCareBenState.paymentMethodName = jsonState.paymentMethodState.lifeBenState.paymentMethodName;
              jsonState.paymentMethodState.healthCareBenState.paymentMethodId = jsonState.paymentMethodState.lifeBenState.paymentMethodId

              jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode;
              jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName;

              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo;
              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName;
              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId;

              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName;
              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName;
              jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankOfficeName;
            } else if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P1') && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName) {
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation;

              jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankOfficeName;

              jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode;
              jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName;
            }
          }
        } else if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          if (this.props.claimTypeState.isSamePoLi) {
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';
          } else {
            jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
            jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
            jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum ? this.props.selectedCliInfo.idNum.trim() : '';
          }
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.HC_HS_ABOVE_18;
        } else if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';

          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18;
        }
      } else {
        if ((this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.FULL_ABOVE_18;
        } else if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.HC_HS_ABOVE_18;
        } else if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = getSession(FULL_NAME) || this.props.fullName || '';
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = getSession(POID) || this.props.idNum || '';
          jsonState.paymentMethodState.paymentMethodCase = PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18;
        }

      }
    } 

    if (jsonState.paymentMethodState.paymentMethodStep < PAYMENT_METHOD_STEP.INIT) {
      jsonState.paymentMethodState.paymentMethodStep = PAYMENT_METHOD_STEP.INIT;
      backCase = true;
    } 
    this.setState(jsonState);
    this.updateState(jsonState);
    document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' })

  }

  updateState(jsonState) {
    // Check condition for enabling/disabling
    let isInvalidHCPayment = false;
    if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && !this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && is18Plus(this.props.selectedCliInfo.dOB)) {
      if (this.props.paymentMethodState.choseReceiver || this.props.claimTypeState.isSamePoLi) {
        if (jsonState.paymentMethodState.healthCareBenState.paymentMethodId === "P2") {
          isInvalidHCPayment =
            Object.entries(jsonState.paymentMethodState.healthCareBenState.transferTypeState)
              .filter(([k, v]) => (
                (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
              )).length > 0;
        }
        if (jsonState.paymentMethodState.healthCareBenState.paymentMethodId === "P1") {
          isInvalidHCPayment =
            Object.entries(jsonState.paymentMethodState.healthCareBenState.cashTypeState)
              .filter(([k, v]) => (
                (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
              )).length > 0;
        }
      }

    } else {
      isInvalidHCPayment = false;
    }

    let isInvalidLifePayment = false;
    if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
      if (jsonState.paymentMethodState.lifeBenState.paymentMethodId === "P2") {
        isInvalidLifePayment =
          Object.entries(jsonState.paymentMethodState.lifeBenState.transferTypeState)
            .filter(([k, v]) => (
              (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
            )).length > 0;
      }
      if (jsonState.paymentMethodState.lifeBenState.paymentMethodId === "P1") {
        isInvalidLifePayment =
          Object.entries(jsonState.paymentMethodState.lifeBenState.cashTypeState)
            .filter(([k, v]) => (
              (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
            )).length > 0;
      }
    } else {
      isInvalidLifePayment = false;
    }
    jsonState.paymentMethodState.disabledButton = (isInvalidHCPayment || isInvalidLifePayment);
    this.setState(jsonState);
    this.props.handlerUpdateMainState("paymentMethodState", jsonState.paymentMethodState);
  }

  validateInput(jsonState) {
    // Check condition for enabling/disabling
    let isInvalidHCPayment = false;
    if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && !this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && is18Plus(this.props.selectedCliInfo.dOB)) {
      if (this.props.paymentMethodState.choseReceiver || this.props.claimTypeState.isSamePoLi) {
        if (jsonState.paymentMethodState.healthCareBenState.paymentMethodId === "P2") {
          isInvalidHCPayment =
            Object.entries(jsonState.paymentMethodState.healthCareBenState.transferTypeState)
              .filter(([k, v]) => (
                (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
              )).length > 0;
        }
        if (jsonState.paymentMethodState.healthCareBenState.paymentMethodId === "P1") {
          isInvalidHCPayment =
            Object.entries(jsonState.paymentMethodState.healthCareBenState.cashTypeState)
              .filter(([k, v]) => (
                (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
              )).length > 0;
        }
      }

    } else {
      isInvalidHCPayment = false;
    }

    let isInvalidLifePayment = false;
    if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
      if (jsonState.paymentMethodState.lifeBenState.paymentMethodId === "P2") {
        isInvalidLifePayment =
          Object.entries(jsonState.paymentMethodState.lifeBenState.transferTypeState)
            .filter(([k, v]) => (
              (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
            )).length > 0;
      }
      if (jsonState.paymentMethodState.lifeBenState.paymentMethodId === "P1") {
        isInvalidLifePayment =
          Object.entries(jsonState.paymentMethodState.lifeBenState.cashTypeState)
            .filter(([k, v]) => (
              (k !== 'bankOfficeName') && (v === null || v === undefined || !v)
            )).length > 0;
      }
    } else {
      isInvalidLifePayment = false;
    }
    console.log('isInvalidHCPayment=' + isInvalidHCPayment + '|isInvalidLifePayment=' + isInvalidLifePayment)
    jsonState.paymentMethodState.disabledButton = (isInvalidHCPayment || isInvalidLifePayment);
    this.setState(jsonState);
  }

  updateContactState(contactState) {
    this.props.handlerUpdateMainState("contactState", contactState);
  }

  onChangeDate(benefitType, paymentMethodCase, transferMethod, value) {
    if (value) {
      var jsonState = this.state;
      if (benefitType !== null) {
        jsonState.paymentMethodState[benefitType][transferMethod].receiverPinDate = value;
      } else {
        if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
          if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPinDate = value;
          } else {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPinDate = value;
          }
        } else {
          if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPinDate = value;
          }
          if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPinDate = value;
          }
        }
      }
      jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
      this.updateState(jsonState);
    }

  };

  onChangePaymentMethod(benefitType, paymentMethodCase, event) {
    if (event.target.checked) {
      var jsonState = this.state;
      // if (is18Plus(this.props.selectedCliInfo.dOB) && !this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      //   jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
      //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
      //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum;
      // }
      if (benefitType !== null) {
        jsonState.paymentMethodState[benefitType].paymentMethodId = event.target.value;
        jsonState.paymentMethodState[benefitType].paymentMethodName = event.target.getAttribute('valuename');
      } else {
        if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
          if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
            jsonState.paymentMethodState.lifeBenState.paymentMethodId = event.target.value;
            jsonState.paymentMethodState.lifeBenState.paymentMethodName = event.target.getAttribute('valuename');
          } else {
            jsonState.paymentMethodState.healthCareBenState.paymentMethodId = event.target.value;
            jsonState.paymentMethodState.healthCareBenState.paymentMethodName = event.target.getAttribute('valuename');
          }
        } else {
          if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.lifeBenState.paymentMethodId = event.target.value;
            jsonState.paymentMethodState.lifeBenState.paymentMethodName = event.target.getAttribute('valuename');
          }
          if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.healthCareBenState.paymentMethodId = event.target.value;
            jsonState.paymentMethodState.healthCareBenState.paymentMethodName = event.target.getAttribute('valuename');
          }
        }
      }
      jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
      this.updateState(jsonState);
    }
  }

  onChangeCity(benefitType, paymentMethodCase, transferMethod, value) {
    var jsonState = this.state;
    var cityObj = jsonState.zipCodeList.find((city) => city.CityCode === value);
    if (cityObj !== null) {
      if (benefitType !== null) {
        jsonState.paymentMethodState[benefitType][transferMethod].cityCode = cityObj.CityCode;
        jsonState.paymentMethodState[benefitType][transferMethod].cityName = cityObj.CityName;
      } else {
        if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
          if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].cityCode = cityObj.CityCode;
            jsonState.paymentMethodState.lifeBenState[transferMethod].cityName = cityObj.CityName;
          } else {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].cityCode = cityObj.CityCode;
            jsonState.paymentMethodState.healthCareBenState[transferMethod].cityName = cityObj.CityName;
          }
        } else {
          if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].cityCode = cityObj.CityCode;
            jsonState.paymentMethodState.lifeBenState[transferMethod].cityName = cityObj.CityName;
          }
          if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].cityCode = cityObj.CityCode;
            jsonState.paymentMethodState.healthCareBenState[transferMethod].cityName = cityObj.CityName;
          }
        }

      }
      jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
      this.updateState(jsonState);
    }
  }

  onChangeBank(benefitType, paymentMethodCase, transferMethod, value) {
    var jsonState = this.state;
    var bankObj = jsonState.bankList.find((bank) => bank.CityCode === value);
    if (bankObj !== null) {
      if (benefitType !== null) {
        jsonState.paymentMethodState[benefitType][transferMethod].bankId = bankObj.CityCode;
        jsonState.paymentMethodState[benefitType][transferMethod].bankName = bankObj.CityName;
      } else {
        if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
          if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].bankId = bankObj.CityCode;
            jsonState.paymentMethodState.lifeBenState[transferMethod].bankName = bankObj.CityName;
          } else {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].bankId = bankObj.CityCode;
            jsonState.paymentMethodState.healthCareBenState[transferMethod].bankName = bankObj.CityName;
          }
        } else {
          if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.lifeBenState[transferMethod].bankId = bankObj.CityCode;
            jsonState.paymentMethodState.lifeBenState[transferMethod].bankName = bankObj.CityName;
          }
          if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
            jsonState.paymentMethodState.healthCareBenState[transferMethod].bankId = bankObj.CityCode;
            jsonState.paymentMethodState.healthCareBenState[transferMethod].bankName = bankObj.CityName;
          }
        }
      }

      jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
      this.updateState(jsonState);
    }
  }

  onChangeBankBranchName(benefitType, paymentMethodCase, transferMethod, event) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].bankBranchName = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankBranchName = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankBranchName = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankBranchName = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankBranchName = event.target.value;
        }
      }

    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  onChangeBankOfficeName(benefitType, paymentMethodCase, transferMethod, event) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].bankOfficeName = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankOfficeName = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankOfficeName = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankOfficeName = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankOfficeName = event.target.value;
        }
      }
    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  // Transfer Type handlers
  onChangeBankAccountNo(benefitType, paymentMethodCase, transferMethod, e) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].bankAccountNo = e.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankAccountNo = e.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankAccountNo = e.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankAccountNo = e.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankAccountNo = e.target.value;
        }
      }

    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  onChangeBankAccountName(benefitType, paymentMethodCase, transferMethod, event) {
    if (!onlyLettersAndSpaces(event.target.value)) {
      return;
    }
    var jsonState = this.state;
    jsonState.paymentMethodState.goneInPaymentMethod = true;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].bankAccountName = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankAccountName = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankAccountName = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].bankAccountName = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].bankAccountName = event.target.value;
        }
      }
    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  // Cash Type handlers
  onChangeReceiverName(benefitType, paymentMethodCase, transferMethod, event) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].receiverName = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverName = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverName = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverName = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverName = event.target.value;
        }
      }

    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);

  }

  onChangeReceiverPin(benefitType, paymentMethodCase, transferMethod, event) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].receiverPin = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPin = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPin = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPin = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPin = event.target.value;
        }
      }
    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  // onChangeReceiverPinDate(benefitType, paymentMethodCase, event) {
  //   var jsonState = this.state;
  //   if (benefitType !== null) {
  //     jsonState.paymentMethodState[benefitType]["cashTypeState"].receiverPinDate = event.target.value;
  //   } else {
  //     if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
  //       jsonState.paymentMethodState.lifeBenState["cashTypeState"].receiverPinDate = event.target.value;
  //     }
  //     if (this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] || this.props.claimCheckedMap[CLAIM_TYPE.HS]) {
  //       jsonState.paymentMethodState.healthCareBenState["cashTypeState"].receiverPinDate = event.target.value;
  //     }
  //   }
  //   jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
  //   this.setState(jsonState);
  // }

  onChangeReceiverPinLocation(benefitType, paymentMethodCase, transferMethod, event) {
    var jsonState = this.state;
    if (benefitType !== null) {
      jsonState.paymentMethodState[benefitType][transferMethod].receiverPinLocation = event.target.value;
    } else {
      if (paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPinLocation = event.target.value;
        } else {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPinLocation = event.target.value;
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || (paymentMethodCase === PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.lifeBenState[transferMethod].receiverPinLocation = event.target.value;
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && (paymentMethodCase !== PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18)) {
          jsonState.paymentMethodState.healthCareBenState[transferMethod].receiverPinLocation = event.target.value;
        }
      }
    }
    jsonState.paymentMethodState.paymentMethodCase = paymentMethodCase;
    this.updateState(jsonState);
  }

  onChangeChooseReceiver(event) {
    let jsonState = this.state;
    let value = event.target.value;
    
    jsonState.paymentMethodState.goneInPaymentMethod = false;
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = "";
      jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = "";
      jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = "";
    } else if (value === 'LI') {
      // if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.DEATH] || !is18Plus(this.props.selectedCliInfo.dOB)) {
      //   jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
      //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
      //   jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum;
      // }
      if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
        jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = this.props.selectedCliInfo.fullName;
        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = this.props.selectedCliInfo.fullName;
        jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = this.props.selectedCliInfo.idNum;
        jsonState.paymentMethodState.healthCareBenState.paymentMethodId = '';
        jsonState.paymentMethodState.healthCareBenState.paymentMethodName = '';
        if (this.props.paymentMethodState.choseReceiver !== value) {
          //Reset ck
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = undefined;
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = '';

            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = undefined;

            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = '';

          //Reset tien mat
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = '';

            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = undefined;
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = '';

            jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = undefined;
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = '';
        }
      }


    } else {
      if ((value === 'PO') && (jsonState.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18)) {
        if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P2') && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName && jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName) {
          jsonState.paymentMethodState.healthCareBenState.paymentMethodName = jsonState.paymentMethodState.lifeBenState.paymentMethodName;
          jsonState.paymentMethodState.healthCareBenState.paymentMethodId = jsonState.paymentMethodState.lifeBenState.paymentMethodId
  
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityCode;
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = jsonState.paymentMethodState.lifeBenState.transferTypeState.cityName;
  
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountNo;
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName;
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankId;
  
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankName;
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankBranchName;
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.transferTypeState.bankOfficeName;
        } else if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P1') && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate && jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName && jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode && jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName) {
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinDate;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPinLocation;
  
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankId;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankBranchName;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = jsonState.paymentMethodState.lifeBenState.cashTypeState.bankOfficeName;
  
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityCode;
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = jsonState.paymentMethodState.lifeBenState.cashTypeState.cityName;
        } else if (this.props.paymentMethodState.choseReceiver !== value) {
          // jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          // jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          // jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = getSession(POID) ? getSession(POID).trim() : "";
          
          if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P2')) {
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityCode = undefined;
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.cityName = '';

            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountNo = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankId = undefined;

            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankName = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankBranchName = '';
            jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankOfficeName = '';
          } else if ((jsonState.paymentMethodState.lifeBenState.paymentMethodId === 'P1')) {
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinDate = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPinLocation = '';

            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankId = undefined;
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankName = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankBranchName = '';
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.bankOfficeName = '';

            jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityCode = undefined;
            jsonState.paymentMethodState.healthCareBenState.cashTypeState.cityName = '';
          }
        }
      } else {
        if (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList) || !is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.lifeBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          jsonState.paymentMethodState.lifeBenState.cashTypeState.receiverPin = getSession(POID) ? getSession(POID).trim() : "";
        }
        if (haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && is18Plus(this.props.selectedCliInfo.dOB)) {
          jsonState.paymentMethodState.healthCareBenState.transferTypeState.bankAccountName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverName = getSession(FULL_NAME) ? getSession(FULL_NAME).trim() : "";
          jsonState.paymentMethodState.healthCareBenState.cashTypeState.receiverPin = getSession(POID) ? getSession(POID).trim() : "";
        }
      }

    }
    jsonState.paymentMethodState.choseReceiver = value;
    jsonState.paymentMethodState.currentChoseReceiver = value;
    let contactState = this.props.contactState;
    if (value === 'PO') {
      if (getSession(FULL_NAME)) {
        contactState.contactPersonInfo.fullName = getSession(FULL_NAME);
      }
      if (getSession(POID)) {
        contactState.contactPersonInfo.pin = getSession(POID);
      }
      if (getSession(DOB)) {
        contactState.contactPersonInfo.dob = getSession(DOB);
      }
      if (getSession(CELL_PHONE)) {
        contactState.contactPersonInfo.phone = getSession(CELL_PHONE);
      }
      if (getSession(EMAIL)) {
        contactState.contactPersonInfo.email = getSession(EMAIL); 
      }
      if (getSession(ADDRESS)) {
        contactState.contactPersonInfo.address = getSession(ADDRESS);
      }
    } else if (value === 'LI') {
      if (this.props.selectedCliInfo.fullName) {
        contactState.contactPersonInfo.fullName = this.props.selectedCliInfo.fullName;
      }
      if (this.props.selectedCliInfo.idNum) {
        contactState.contactPersonInfo.pin = this.props.selectedCliInfo.idNum;
      }
      if (this.props.selectedCliInfo.dOB) {
        contactState.contactPersonInfo.dob = this.props.selectedCliInfo.dOB;
      }
      if (this.props.selectedCliInfo.cellPhone) {
        contactState.contactPersonInfo.phone = this.props.selectedCliInfo.cellPhone;
      }
      if (this.props.selectedCliInfo.email) {
        contactState.contactPersonInfo.email = this.props.selectedCliInfo.email;
      }
      if (this.props.selectedCliInfo.address) {
        contactState.contactPersonInfo.address = this.props.selectedCliInfo.address;
      }
    } 
    this.updateState(jsonState);
    this.updateContactState(contactState);
  }

  render() {

    console.log('choseReceiver=' + this.state.paymentMethodState.choseReceiver);
    console.log('props choseReceiver=' + this.props.paymentMethodState.choseReceiver);

    function disabledDate(current) {
      return current && (current > dayjs().endOf('day'));
    }
    const { Option } = Select;

    var jsonState = this.state;
    const zipCodeList = jsonState.zipCodeList;
    const bankList = jsonState.bankList;

    const lifeBenState = jsonState.paymentMethodState.lifeBenState;
    const lifeBenTransferState = lifeBenState.transferTypeState;
    const lifeBenCashState = lifeBenState.cashTypeState;

    const healthCareBenState = jsonState.paymentMethodState.healthCareBenState;
    const healthCareBenTransferState = healthCareBenState.transferTypeState;
    const healthCareBenCashState = healthCareBenState.cashTypeState;

    let disable = false;
    if ((this.state.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.HC_HS_ABOVE_18) && (this.state.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.STEP1) && ((!this.state.paymentMethodState.choseReceiver && !this.props.claimTypeState.isSamePoLi) || (this.state.paymentMethodState.choseReceiver && !this.state.paymentMethodState.healthCareBenState.paymentMethodId))) {
      disable = true;
    }

    if ((this.state.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) && (this.state.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.STEP1) && ((!this.state.paymentMethodState.choseReceiver && !this.props.claimTypeState.isSamePoLi) || (this.state.paymentMethodState.choseReceiver && !this.state.paymentMethodState.healthCareBenState.paymentMethodId && this.state.paymentMethodState.choseReceiver !== 'PO'))) {
      disable = true;
    }

    if (((this.state.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) || (this.state.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.HC_HS_ABOVE_18)) && (this.state.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.STEP2) && (this.state.paymentMethodState.choseReceiver === 'LI' && !this.state.paymentMethodState.healthCareBenState.paymentMethodId)) {
      disable = true;
    }

    if (this.state.paymentMethodState.paymentMethodStep > PAYMENT_METHOD_STEP.INIT) {
      console.log('gan backcase false');
      backCase = false;
    } 
    const back = () => {
      // let jsonState = this.state;
      // if ((jsonState.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.INIT) && jsonState.paymentMethodState.choseReceiver) {
      //   jsonState.paymentMethodState.choseReceiver = null;
      //   jsonState.paymentMethodState.disabledButton = true;
      //   this.setState(jsonState);
      // } else {
      //   this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD))
      // }
      this.props.handlerBackToPrevStep(this.props.handlerGetStep(CLAIM_STEPCODE.PAYMENT_METHOD));
    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }

    const validateAndSubmit=()=> {
      this.validateInput(this.state);
      this.props.handlerSubmitPaymentMethod();
    }

    return (
      <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance">
          <div className={getSession(IS_MOBILE)?"heading padding-heading-mobile": "heading"}>
            {(this.props.sourceSystem === 'DConnect') &&
            !getSession(IS_MOBILE) &&
            <div className="breadcrums">
            <div className="breadcrums__item">
                <p>Yêu cầu quyền lợi</p>
                <span>&gt;</span>
            </div>
            <div className="breadcrums__item">
                <p>Tạo mới yêu cầu</p>
                <span>&gt;</span>
            </div>
            </div>
            }
            {!getSession(IS_MOBILE) &&
            <div className="other_option" id="other-option-toggle" onClick={()=>goBack()}>
              <p>Chọn thông tin</p>
              <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
            </div>
            }
            <div className={getSession(IS_MOBILE) ? "heading__tab mobile" : "heading__tab"}>
              <div className="step-container">
                <div className="step-wrapper">
                  <div className="step-btn-wrapper">
                    <div className="back-btn">
                      <button>
                        <div className="svg-wrapper" onClick={() => back()}>
                          <svg
                            width="11"
                            height="15"
                            viewBox="0 0 11 15"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                              fill="#985801"
                              stroke="#985801"
                              strokeWidth="0.2"
                            />
                          </svg>
                        </div>
                        {!getSession(IS_MOBILE) ? (
                            <span className="simple-brown" onClick={() => back()}>Quay lại</span>
                        ):(
                          <p style={{ textAlign: 'center', paddingLeft: '16px', minWidth: '250%', fontWeight: '700' }}>{'Tạo mới yêu cầu'}</p>
                        )}
                      </button>
                    </div>
                    {getSession(IS_MOBILE) &&
                    <div className="step-btn-save-quit">
                      <div className='save-quit-wrapper'>
                        <button>
                          <span className="simple-brown" onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                        </button>
                      </div>
                    </div>
                    }
                    {/* <div className="save-wrap">
                      <button className="back-text">Lưu</button>
                    </div> */}
                  </div>
                  <div className="progress-bar">
                    <div className={(this.state.stepName >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>1</span>
                      </div>
                      <p>Thông tin sự kiện</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>2</span>
                      </div>
                      <p>Thanh toán và liên hệ</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>3</span>
                      </div>
                      <p>Kèm <br />chứng từ</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>4</span>
                      </div>
                      <p>Hoàn tất yêu cầu</p>
                    </div>
                  </div>
                  {!getSession(IS_MOBILE) &&
                    <div className="step-btn-save-quit">
                      <div className='save-quit-wrapper'>
                        <button>
                          <span className="simple-brown" onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                        </button>
                      </div>
                    </div>
                  }
                </div>
                
              </div>
            </div>
          </div>
          {getSession(IS_MOBILE) &&
            <div className='padding-top64 margin-top56'></div>
          }
          {this.props.systemGroupPermission?.[0]?.Role === 'AGENT' &&
            <div className='ndbh-info'>
              <div style={{ display: 'flex' }}>
                <p>Tên NĐBH:</p>
                <p className='bold-text'>{this.props.selectedCliInfo?.fullName}</p>
              </div>
              <div style={{ display: 'flex' }}>
                <p>Quyền lợi:</p>
                <p className='bold-text'>{getBenifits(this.props.claimCheckedMap)}</p>
              </div>
            </div>
          }
          <div className="stepform">
            <div className="stepform__body">
              <LoadingIndicator area="submit-init-claim"/>
              {/* Phương thức thanh toán tử vong */}
              {(this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) ? (
                <div className="info">
                  <div className="info__title">
                    <h4>Phương thức thanh toán</h4>
                  </div>
                  <div className="info__body">
                    <div className="checkbox-wrap basic-column">
                      <div className="tab-wrapper">
                        <div className="tab">
                          {/* Checkbox */}
                          <div className="round-checkbox basic-bottom-margin">
                            <label className="customradio">
                              <input type="radio" value="P2" valuename="Chuyển khoản"
                                checked={lifeBenState.paymentMethodId === "P2"}
                                onChange={(e) => this.handlerOnChangePaymentMethod("lifeBenState", PAYMENT_METHOD_CASE.DEAD, e)} />
                              <div className="checkmark"></div>
                              <p claas="text">Chuyển khoản</p>
                            </label>
                          </div>
                          {/* Detail */}
                          <div className={lifeBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                            <div className="checkbox-dropdown__content">
                              {/* Tên chủ tài khoản */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenTransferState.bankAccountName &&
                                      <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                    }
                                    <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={lifeBenTransferState.bankAccountName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Tên chủ tài khoản"}
                                      onChange={(e) => this.handlerOnChangeBankAccountName("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Số tài khoản */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenTransferState.bankAccountNo &&
                                      <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                    }
                                    <NumberFormat displayType="input" type="tel" maxLength="20" value={lifeBenTransferState.bankAccountNo} prefix=""
                                      placeholder="Số tài khoản"
                                      allowNegative={false}
                                      allowLeadingZeros={true}
                                      onChange={(e) => this.handlerOnChangeBankAccountNo("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", e)}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                    />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Chọn ngân hàng */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="dropdown inputdropdown">
                                  <div className="dropdown__content">
                                    <div className="tab__content">
                                      <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                        <Select
                                          suffixIcon={<SearchOutlined />}
                                          showSearch
                                          size='large'
                                          style={{ width: '100%', margin: '0' }}
                                          width='100%'
                                          bordered={false}
                                          placeholder="Chọn ngân hàng"
                                          optionFilterProp="bankname"
                                          onChange={(e) => this.handlerOnChangeBank("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", e)}
                                          // onFocus={onFocus}
                                          // onBlur={onBlur}
                                          // onSearch={onSearch}
                                          value={lifeBenTransferState.bankId}
                                          filterOption={(input, option) =>
                                            option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                          }
                                        >
                                          {!isEmpty(bankList) && bankList.map((bank) => (
                                            <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                          ))}
                                        </Select>
                                      </div>
                                    </div>
                                  </div>
                                  <div className="dropdown__items">
                                    <div className="dropdown-tag"></div>
                                  </div>
                                </div>
                              </div>
                              {/* Chọn tỉnh/thành phố */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="dropdown inputdropdown">
                                  <div className="dropdown__content">
                                    <div className="tab__content">
                                      <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                        <Select
                                          suffixIcon={<SearchOutlined />}
                                          showSearch
                                          size='large'
                                          style={{ width: '100%', margin: '0' }}
                                          width='100%'
                                          bordered={false}
                                          placeholder="Chọn tỉnh/ Thành phố"
                                          optionFilterProp="cityname"
                                          onChange={(v) => this.handlerOnChangeCity("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", v)}
                                          // onFocus={onFocus}
                                          // onBlur={onBlur}
                                          // onSearch={onSearch}
                                          value={lifeBenTransferState.cityCode}
                                          filterOption={(input, option) =>
                                            option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                          }
                                        >
                                          {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                            <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                          ))}
                                        </Select>
                                      </div>
                                    </div>
                                  </div>
                                  <div className="dropdown__items">
                                    <div className="dropdown-tag"></div>
                                  </div>
                                </div>
                              </div>
                              {/* Chi nhánh */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenTransferState.bankBranchName &&
                                      <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                    }
                                    <input type="search" placeholder="Chi nhánh" maxLength="200"
                                      value={lifeBenTransferState.bankBranchName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                      onChange={(e) => this.handlerOnChangeBankBranchName("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Phòng giao dịch */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenTransferState.bankOfficeName &&
                                      <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                    }
                                    <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                      value={lifeBenTransferState.bankOfficeName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                      onChange={(e) => this.handlerOnChangeBankOfficeName("lifeBenState", PAYMENT_METHOD_CASE.DEAD, "transferTypeState", e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>


                            </div>
                          </div>
                        </div>
                        <div className="tab" style={{ borderTop: '1px solid #E7E7E7' }}>
                          {/* Checkbox */}
                          <div className="round-checkbox basic-bottom-margin">
                            <label className="customradio">
                              <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                checked={lifeBenState.paymentMethodId === "P1"}
                                onChange={(event) => this.handlerOnChangePaymentMethod(null, PAYMENT_METHOD_CASE.DEAD, event)} />
                              <div className="checkmark"></div>
                              <p claas="text">Nhận tiền mặt tại ngân hàng</p>
                            </label>
                          </div>
                          {/* Detail */}
                          <div className={lifeBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                            <div className="checkbox-dropdown__content">
                              {/* Họ tên người nhận */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenCashState.receiverName &&
                                      <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                    }
                                    <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={lifeBenCashState.receiverName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Họ tên người nhận"}
                                      onChange={(e) => this.handlerOnChangeReceiverName(null, PAYMENT_METHOD_CASE.DEAD, 'cashTypeState', e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* CMND/CCCD */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenCashState.receiverPin &&
                                      <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                    }
                                    <input type="search" placeholder="CMND/CCCD" maxLength="14" value={lifeBenCashState.receiverPin}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                      onChange={(e) => this.handlerOnChangeReceiverPin(null, PAYMENT_METHOD_CASE.DEAD, 'cashTypeState', e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Ngày cấp */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="datewrapper">
                                  <div className="datewrapper__item" style={{ width: '100%' }}>
                                    <div className="inputdate">
                                      {/* <input type={benCashState.currDateInputType ? 'date' : 'text'} placeholder="Ngày cấp"
                                        onFocus={(event) => {
                                          var jsonState = this.state;
                                          jsonState.paymentMethodState.benState.cashTypeState.currDateInputType = true;
                                          this.setState(jsonState);
                                        }}
                                        onBlur={(event) => {
                                          if (event.target.value === '') {
                                            var jsonState = this.state;
                                            jsonState.paymentMethodState.benState.cashTypeState.currDateInputType = false;
                                            this.setState(jsonState);
                                          }
                                        }}
                                        value={benCashState.receiverPinDate} onChange={(event) => this.handlerOnChangeDate("lifeBenState", PAYMENT_METHOD_CASE.DEAD, event)} /> */}
                                      <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={lifeBenCashState.receiverPinDate ? moment(lifeBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                    </div>
                                  </div>
                                </div>
                              </div>
                              {/* Nơi cấp */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    <label>Nơi cấp</label>
                                    <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                      value={lifeBenCashState.receiverPinLocation}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                      onChange={(event) => this.handlerOnChangeReceiverPinLocation(null, PAYMENT_METHOD_CASE.DEAD, 'cashTypeState', event)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Chọn ngân hàng */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="dropdown inputdropdown">
                                  <div className="dropdown__content">
                                    <div className="tab__content">
                                      <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                        <Select
                                          suffixIcon={<SearchOutlined />}
                                          showSearch
                                          size='large'
                                          style={{ width: '100%', margin: '0' }}
                                          width='100%'
                                          bordered={false}
                                          placeholder="Chọn ngân hàng"
                                          optionFilterProp="bankname"
                                          onChange={(e) => this.handlerOnChangeBank(null, PAYMENT_METHOD_CASE.DEAD, "cashTypeState", e)}
                                          // onFocus={onFocus}
                                          // onBlur={onBlur}
                                          // onSearch={onSearch}
                                          value={lifeBenCashState.bankId}
                                          filterOption={(input, option) =>
                                            option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                          }
                                        >
                                          {!isEmpty(bankList) && bankList.map((bank) => (
                                            <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                          ))}
                                        </Select>
                                      </div>
                                    </div>
                                  </div>
                                  <div className="dropdown__items">
                                    <div className="dropdown-tag"></div>
                                  </div>
                                </div>
                              </div>
                              {/* Chọn tỉnh/thành phố */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="dropdown inputdropdown">
                                  <div className="dropdown__content">
                                    <div className="tab__content">
                                      <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                        <Select
                                          suffixIcon={<SearchOutlined />}
                                          showSearch
                                          size='large'
                                          style={{ width: '100%', margin: '0' }}
                                          width='100%'
                                          bordered={false}
                                          placeholder="Chọn tỉnh/ Thành phố"
                                          optionFilterProp="cityname"
                                          onChange={(v) => this.handlerOnChangeCity(null, PAYMENT_METHOD_CASE.DEAD, "cashTypeState", v)}
                                          // onFocus={onFocus}
                                          // onBlur={onBlur}
                                          // onSearch={onSearch}
                                          value={lifeBenCashState.cityCode}
                                          filterOption={(input, option) =>
                                            option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                          }
                                        >
                                          {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                            <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                          ))}
                                        </Select>
                                      </div>
                                    </div>
                                  </div>
                                  <div className="dropdown__items">
                                    <div className="dropdown-tag"></div>
                                  </div>
                                </div>
                              </div>
                              {/* Chi nhánh */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenCashState.bankBranchName &&
                                      <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                    }
                                    <input type="search" placeholder="Chi nhánh" maxLength="200"
                                      value={lifeBenCashState.bankBranchName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                      onChange={(e) => this.handlerOnChangeBankBranchName(null, PAYMENT_METHOD_CASE.DEAD, "cashTypeState", e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                              {/* Phòng giao dịch */}
                              <div className="checkbox-dropdown__content-item">
                                <div className="input">
                                  <div className="input__content">
                                    {lifeBenCashState.bankOfficeName &&
                                      <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                    }
                                    <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                      value={lifeBenCashState.bankOfficeName}
                                      onFocus={(e) => e.target.placeholder = ""}
                                      onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                      onChange={(e) => this.handlerOnChangeBankOfficeName(null, PAYMENT_METHOD_CASE.DEAD, "cashTypeState", e)} />
                                  </div>
                                  <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                is18Plus(this.props.selectedCliInfo.dOB) ? (
                  (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) ? (
                    // {/*Phương thức thanh toán claim life && HS/HC */}
                    (this.props.paymentMethodState.paymentMethodStep === PAYMENT_METHOD_STEP.INIT) ? (
                      <div className="info">
                        <div className="info__title">
                          <h4>Phương thức thanh toán</h4>
                        </div>
                        {/* <div className="info__title" style={{ marginTop: '-20px' }}>
                          <p>Đối với Quyền lợi bảo hiểm nhân thọ, hoặc Người được bảo hiểm dưới 18 tuổi, Dai ichi Life Việt Nam sẽ thanh toán cho Bên mua bảo hiểm theo quy định của Hợp đồng. Quý khách vui lòng chọn hình thức thanh toán:</p>
                        </div> */}
                        <div className="info__body">
                          <div className="checkbox-wrap basic-column">
                            <div className="tab-wrapper">
                              <div className="tab">
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="P2" valuename="Chuyển khoản"
                                      checked={lifeBenState.paymentMethodId === "P2"}
                                      onChange={(e) => this.handlerOnChangePaymentMethod(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, e)} />
                                    <div className="checkmark"></div>
                                    <p claas="text">Chuyển khoản</p>
                                  </label>
                                </div>
                                {/* Detail */}
                                <div className={lifeBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                  <div className="checkbox-dropdown__content">
                                    {/* Tên chủ tài khoản */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input disabled">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankAccountName &&
                                            <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                          }
                                          <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={lifeBenTransferState.bankAccountName || getSession(FULL_NAME)}
                                            // onFocus={(e) => e.target.placeholder = ""}
                                            // onBlur={(e) => e.target.placeholder = "Tên chủ tài khoản"}
                                            // onChange={(e) => this.handlerOnChangeBankAccountName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)} 
                                            disabled
                                          />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Số tài khoản */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankAccountNo &&
                                            <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                          }
                                          <NumberFormat displayType="input" type="tel" maxLength="20" value={lifeBenTransferState.bankAccountNo} prefix=""
                                            placeholder="Số tài khoản"
                                            allowNegative={false}
                                            allowLeadingZeros={true}
                                            onChange={(e) => this.handlerOnChangeBankAccountNo(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                          />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Chọn ngân hàng */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn ngân hàng"
                                                optionFilterProp="bankname"
                                                onChange={(e) => this.handlerOnChangeBank(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenTransferState.bankId}
                                                filterOption={(input, option) =>
                                                  option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(bankList) && bankList.map((bank) => (
                                                  <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chọn tỉnh/thành phố */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn tỉnh/ Thành phố"
                                                optionFilterProp="cityname"
                                                onChange={(v) => this.handlerOnChangeCity(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", v)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenTransferState.cityCode}
                                                filterOption={(input, option) =>
                                                  option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                  <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chi nhánh */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankBranchName &&
                                            <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                          }
                                          <input type="search" placeholder="Chi nhánh" maxLength="200"
                                            value={lifeBenTransferState.bankBranchName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                            onChange={(e) => this.handlerOnChangeBankBranchName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Phòng giao dịch */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankOfficeName &&
                                            <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                          }
                                          <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                            value={lifeBenTransferState.bankOfficeName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                            onChange={(e) => this.handlerOnChangeBankOfficeName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div className="tab">
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                      checked={lifeBenState.paymentMethodId === "P1"}
                                      onChange={(event) => this.handlerOnChangePaymentMethod(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, event)} />
                                    <div className="checkmark"></div>
                                    <p claas="text">Nhận tiền mặt tại ngân hàng</p>
                                  </label>
                                </div>
                                {/* Detail */}
                                <div className={lifeBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                  <div className="checkbox-dropdown__content">
                                    {/* Họ tên người nhận */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input disabled">
                                        <div className="input__content">
                                          {lifeBenCashState.receiverName &&
                                            <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                          }
                                          <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={lifeBenCashState.receiverName}
                                            // onFocus={(e) => e.target.placeholder = ""}
                                            // onBlur={(e) => e.target.placeholder = "Họ tên người nhận"}
                                            // onChange={(e) => this.handlerOnChangeReceiverName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18,'cashTypeState', e)} 
                                            disabled
                                          />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* CMND/CCCD */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.receiverPin &&
                                            <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                          }
                                          <input type="search" placeholder="CMND/CCCD" maxLength="14" value={lifeBenCashState.receiverPin}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                            onChange={(e) => this.handlerOnChangeReceiverPin(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Ngày cấp */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="datewrapper">
                                        <div className="datewrapper__item" style={{ width: '100%' }}>
                                          <div className="inputdate">
                                            <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={lifeBenCashState.receiverPinDate ? moment(lifeBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Nơi cấp */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          <label>Nơi cấp</label>
                                          <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                            value={lifeBenCashState.receiverPinLocation}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                            onChange={(event) => this.handlerOnChangeReceiverPinLocation(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', event)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Chọn ngân hàng */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn ngân hàng"
                                                optionFilterProp="bankname"
                                                onChange={(e) => this.handlerOnChangeBank(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenCashState.bankId}
                                                filterOption={(input, option) =>
                                                  option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(bankList) && bankList.map((bank) => (
                                                  <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chọn tỉnh/thành phố */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn tỉnh/ Thành phố"
                                                optionFilterProp="cityname"
                                                onChange={(v) => this.handlerOnChangeCity(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", v)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenCashState.cityCode}
                                                filterOption={(input, option) =>
                                                  option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                  <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chi nhánh */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.bankBranchName &&
                                            <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                          }
                                          <input type="search" placeholder="Chi nhánh" maxLength="200"
                                            value={lifeBenCashState.bankBranchName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                            onChange={(e) => this.handlerOnChangeBankBranchName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Phòng giao dịch */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.bankOfficeName &&
                                            <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                          }
                                          <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                            value={lifeBenCashState.bankOfficeName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                            onChange={(e) => this.handlerOnChangeBankOfficeName(null, PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ) : (
                      (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) && !this.props.claimTypeState.isSamePoLi ? (
                        <div className="info">
                          <div className="info__title">
                            <h4>Phương thức thanh toán</h4>
                          </div>
                          <div className="info__title" style={{ marginTop: '-20px' }}>
                            <p>Quyền lợi Bảo hiểm Chăm sóc sức khỏe/ Hỗ trợ viện phí sẽ được chi trả cho Người được bảo hiểm (NĐBH). Nếu  yêu cầu chi trả cho Bên mua bảo hiểm, cần cung cấp thông tin liên hệ của NĐBH  ở bước sau để  nhận thông báo  của Dai-ichi Life Việt Nam.</p>
                          </div>
                          <div className="info__body">
                            <div className="checkbox-wrap basic-column">
                              <div className="tab-wrapper">
                                <div className="tab">
                                  {/* Checkbox */}
                                  <div className="round-checkbox basic-bottom-margin">
                                    <label className="customradio">
                                      <input type="radio" value="PO" valuename="Bên mua bảo hiểm"
                                        checked={this.props.paymentMethodState.choseReceiver === "PO"}
                                        onChange={(e) => this.handlerOnChangeChooseReceiver(e)} />
                                      <div className="checkmark"></div>
                                      <p className="text">Bên mua bảo hiểm</p>
                                    </label>
                                  </div>
                                </div>

                                <div className="tab">
                                  {/* Checkbox */}
                                  <div className="round-checkbox basic-bottom-margin">
                                    <label className="customradio">
                                      <input type="radio" value="LI" valuename="Người được bảo hiểm"
                                        checked={this.props.paymentMethodState.choseReceiver === "LI"}
                                        onChange={(e) => this.handlerOnChangeChooseReceiver(e)} />
                                      <div className="checkmark"></div>
                                      <p className="text">Người được bảo hiểm</p>
                                    </label>
                                  </div>
                                </div>

                              </div>
                            </div>
                          </div>
                        </div>
                      ) : (
                        <div className="info">
                          <div className="info__title">
                            <h4>Phương thức thanh toán</h4>
                          </div>
                          <div className="info__body">
                            <div className="checkbox-wrap basic-column">
                              <div className="tab-wrapper">
                                {(this.props.paymentMethodState.choseReceiver === 'PO') &&
                                  <div className="tab">
                                    {/* Checkbox */}
                                    <div className="round-checkbox basic-bottom-margin">
                                      <label className="customradio">
                                        <input type="radio" value="PO" valuename="Bên mua bảo hiểm"
                                          checked={this.props.paymentMethodState.choseReceiver === "PO"}
                                          onChange={(e) => this.handlerOnChangeChooseReceiver(e)} />
                                        <div className="checkmark"></div>
                                        <p className="text">Bên mua bảo hiểm</p>
                                      </label>
                                    </div>
                                    {(this.props.paymentMethodState.choseReceiver === 'PO') &&
                                      <div className="tab-wrapper">
                                        {lifeBenState.paymentMethodId === "P2" &&
                                          <div className="tab">
                                            {/* Detail */}
                                            <div className={lifeBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                              <div className="checkbox-dropdown__content">
                                                {/* Tên chủ tài khoản */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {(lifeBenTransferState.bankAccountName) &&
                                                        <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                                      }
                                                      <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={lifeBenTransferState.bankAccountName}
                                                        disabled />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Số tài khoản */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenTransferState.bankAccountNo &&
                                                        <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                                      }
                                                      <input type="tel" placeholder="Số tài khoản" maxLength="20" value={lifeBenTransferState.bankAccountNo}
                                                        disabled />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Chọn ngân hàng */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="dropdown inputdropdown">
                                                    <div className="dropdown__content">
                                                      <div className="tab__content">
                                                        <div className="input" style={{ padding: '10px 0px 10px 0px', background: '#EDEBEB' }}>
                                                          <Select
                                                            showSearch
                                                            size='large'
                                                            style={{ width: '100%', margin: '0' }}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Chọn ngân hàng"
                                                            optionFilterProp="bankname"
                                                            disabled
                                                            // onChange={(e) => this.handlerOnChangeBank(PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)}
                                                            // onFocus={onFocus}
                                                            // onBlur={onBlur}
                                                            // onSearch={onSearch}
                                                            value={lifeBenTransferState.bankId}
                                                            filterOption={(input, option) =>
                                                              option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                            }
                                                          >
                                                            {!isEmpty(bankList) && bankList.map((bank) => (
                                                              <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                            ))}
                                                          </Select>
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                      <div className="dropdown-tag"></div>
                                                    </div>
                                                  </div>
                                                </div>
                                                {/* Chọn tỉnh/thành phố */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="dropdown inputdropdown">
                                                    <div className="dropdown__content">
                                                      <div className="tab__content">
                                                        <div className="input" style={{ padding: '10px 0px 10px 0px', background: '#EDEBEB' }}>
                                                          <Select
                                                            showSearch
                                                            size='large'
                                                            style={{ width: '100%', margin: '0' }}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Chọn tỉnh/ Thành phố"
                                                            optionFilterProp="cityname"
                                                            disabled
                                                            // onChange={(v) => this.handlerOnChangeCity(PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", v)}
                                                            // onFocus={onFocus}
                                                            // onBlur={onBlur}
                                                            // onSearch={onSearch}
                                                            value={lifeBenTransferState.cityCode}
                                                            filterOption={(input, option) =>
                                                              option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                            }
                                                          >
                                                            {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                              <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                            ))}
                                                          </Select>
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                      <div className="dropdown-tag"></div>
                                                    </div>
                                                  </div>
                                                </div>
                                                {/* Chi nhánh */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenTransferState.bankBranchName &&
                                                        <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                                      }
                                                      <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                        value={lifeBenTransferState.bankBranchName}
                                                        disabled />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Phòng giao dịch */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenTransferState.bankOfficeName &&
                                                        <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                                      }
                                                      <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                        value={lifeBenTransferState.bankOfficeName}
                                                        disabled />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                              </div>
                                            </div>
                                          </div>
                                        }
                                        {lifeBenState.paymentMethodId === "P1" &&
                                          <div className="tab">
                                            {/* Detail */}
                                            <div className={lifeBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                              <div className="checkbox-dropdown__content">
                                                {/* Họ tên người nhận */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenCashState.receiverName &&
                                                        <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                                      }
                                                      <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={lifeBenCashState.receiverName}
                                                        disabled
                                                      />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* CMND/CCCD */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenCashState.receiverPin &&
                                                        <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                                      }
                                                      <input type="search" placeholder="CMND/CCCD" maxLength="14" value={lifeBenCashState.receiverPin}
                                                        disabled
                                                      />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Ngày cấp */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="datewrapper">
                                                    <div className="datewrapper__item" style={{ width: '100%' }}>
                                                      <div className="inputdate">
                                                        <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={lifeBenCashState.receiverPinDate ? moment(lifeBenCashState.receiverPinDate) : ""} disabled format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                                      </div>
                                                    </div>
                                                  </div>
                                                </div>
                                                {/* Nơi cấp */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      <label>Nơi cấp</label>
                                                      <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                                        value={lifeBenCashState.receiverPinLocation}
                                                        disabled
                                                      />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Chọn ngân hàng */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="dropdown inputdropdown">
                                                    <div className="dropdown__content">
                                                      <div className="tab__content">
                                                        <div className="input" style={{ padding: '10px 0px 10px 0px', background: '#EDEBEB' }}>
                                                          <Select
                                                            showSearch
                                                            size='large'
                                                            style={{ width: '100%', margin: '0' }}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Chọn ngân hàng"
                                                            optionFilterProp="bankname"
                                                            disabled
                                                            //onChange={(e) => this.handlerOnChangeBank(PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)}
                                                            // onFocus={onFocus}
                                                            // onBlur={onBlur}
                                                            // onSearch={onSearch}
                                                            value={lifeBenCashState.bankId}
                                                            filterOption={(input, option) =>
                                                              option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                            }
                                                          >
                                                            {!isEmpty(bankList) && bankList.map((bank) => (
                                                              <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                            ))}
                                                          </Select>
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                      <div className="dropdown-tag"></div>
                                                    </div>
                                                  </div>
                                                </div>
                                                {/* Chọn tỉnh/thành phố */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="dropdown inputdropdown">
                                                    <div className="dropdown__content">
                                                      <div className="tab__content">
                                                        <div className="input" style={{ padding: '10px 0px 10px 0px', background: '#EDEBEB' }}>
                                                          <Select
                                                            showSearch
                                                            size='large'
                                                            style={{ width: '100%', margin: '0' }}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Chọn tỉnh/ Thành phố"
                                                            optionFilterProp="cityname"
                                                            disabled
                                                            //onChange={(v) => this.handlerOnChangeCity(PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", v)}
                                                            // onFocus={onFocus}
                                                            // onBlur={onBlur}
                                                            // onSearch={onSearch}
                                                            value={lifeBenCashState.cityCode}
                                                            filterOption={(input, option) =>
                                                              option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                            }
                                                          >
                                                            {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                              <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                            ))}
                                                          </Select>
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div className="dropdown__items">
                                                      <div className="dropdown-tag"></div>
                                                    </div>
                                                  </div>
                                                </div>
                                                {/* Chi nhánh */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenCashState.bankBranchName &&
                                                        <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                                      }
                                                      <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                        value={lifeBenCashState.bankBranchName}
                                                        disabled
                                                      />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                                {/* Phòng giao dịch */}
                                                <div className="checkbox-dropdown__content-item">
                                                  <div className="input disabled">
                                                    <div className="input__content">
                                                      {lifeBenCashState.bankOfficeName &&
                                                        <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                                      }
                                                      <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                        value={lifeBenCashState.bankOfficeName}
                                                        disabled
                                                      />
                                                    </div>
                                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                  </div>
                                                </div>
                                              </div>
                                            </div>
                                          </div>
                                        }
                                      </div>
                                    }
                                  </div>
                                }
                                {(this.props.paymentMethodState.choseReceiver === 'LI') &&
                                  <div className="tab">
                                    {/* Checkbox */}

                                    <div className="tab-wrapper">
                                      <div className="tab">
                                        {/* Checkbox */}

                                        <div className="round-checkbox basic-bottom-margin">
                                          <label className="customradio">
                                            <input type="radio" value="P2" valuename="Chuyển khoản"
                                              checked={healthCareBenState.paymentMethodId === "P2"}
                                              onChange={(e) => this.handlerOnChangePaymentMethod("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, e)}
                                            />
                                            <div className="checkmark"></div>
                                            <p className="text">Chuyển khoản</p>
                                          </label>
                                        </div>

                                        {/* Detail */}
                                        <div className={healthCareBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                          <div className="checkbox-dropdown__content">
                                            {/* Tên chủ tài khoản */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input disabled">
                                                <div className="input__content">
                                                  {healthCareBenTransferState.bankAccountName &&
                                                    <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                                  }
                                                  <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={healthCareBenTransferState.bankAccountName}
                                                    disabled />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Số tài khoản */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenTransferState.bankAccountNo &&
                                                    <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                                  }
                                                  <NumberFormat displayType="input" type="tel" maxLength="20" value={healthCareBenTransferState.bankAccountNo} prefix=""
                                                    placeholder="Số tài khoản"
                                                    allowNegative={false}
                                                    allowLeadingZeros={true}
                                                    onChange={(e) => this.handlerOnChangeBankAccountNo("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                                  />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Chọn ngân hàng */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                  <div className="tab__content">
                                                    <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                      <Select
                                                        showSearch
                                                        size='large'
                                                        style={{ width: '100%', margin: '0' }}
                                                        width='100%'
                                                        bordered={false}
                                                        placeholder="Chọn ngân hàng"
                                                        optionFilterProp="bankname"
                                                        onChange={(e) => this.handlerOnChangeBank("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)}
                                                        // onFocus={onFocus}
                                                        // onBlur={onBlur}
                                                        // onSearch={onSearch}
                                                        value={healthCareBenTransferState.bankId}
                                                        filterOption={(input, option) =>
                                                          option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                        }
                                                      >
                                                        {!isEmpty(bankList) && bankList.map((bank) => (
                                                          <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                        ))}
                                                      </Select>
                                                    </div>
                                                  </div>
                                                </div>
                                                <div className="dropdown__items">
                                                  <div className="dropdown-tag"></div>
                                                </div>
                                              </div>
                                            </div>
                                            {/* Chọn tỉnh/thành phố */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                  <div className="tab__content">
                                                    <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                      <Select
                                                        showSearch
                                                        size='large'
                                                        style={{ width: '100%', margin: '0' }}
                                                        width='100%'
                                                        bordered={false}
                                                        placeholder="Chọn tỉnh/ Thành phố"
                                                        optionFilterProp="cityname"
                                                        onChange={(v) => this.handlerOnChangeCity("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", v)}
                                                        // onFocus={onFocus}
                                                        // onBlur={onBlur}
                                                        // onSearch={onSearch}
                                                        value={healthCareBenTransferState.cityCode}
                                                        filterOption={(input, option) =>
                                                          option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                        }
                                                      >
                                                        {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                          <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                        ))}
                                                      </Select>
                                                    </div>
                                                  </div>
                                                </div>
                                                <div className="dropdown__items">
                                                  <div className="dropdown-tag"></div>
                                                </div>
                                              </div>
                                            </div>
                                            {/* Chi nhánh */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenTransferState.bankBranchName &&
                                                    <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                                  }
                                                  <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                    value={healthCareBenTransferState.bankBranchName}
                                                    // onFocus={(e) => e.target.placeholder = ""}
                                                    // onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                                    onChange={(e) => this.handlerOnChangeBankBranchName("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Phòng giao dịch */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenTransferState.bankOfficeName &&
                                                    <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                                  }
                                                  <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                    value={healthCareBenTransferState.bankOfficeName}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                                    onChange={(e) => this.handlerOnChangeBankOfficeName("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "transferTypeState", e)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                      <div className="tab">
                                        {/* Checkbox */}
                                        <div className="round-checkbox basic-bottom-margin">
                                          <label className="customradio">
                                            <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                              checked={healthCareBenState.paymentMethodId === "P1"}
                                              onChange={(event) => this.handlerOnChangePaymentMethod("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, event)} />
                                            <div className="checkmark"></div>
                                            <p className="text">Nhận tiền mặt tại ngân hàng</p>
                                          </label>
                                        </div>
                                        {/* Detail */}
                                        <div className={healthCareBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                          <div className="checkbox-dropdown__content">
                                            {/* Họ tên người nhận */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input disabled">
                                                <div className="input__content">
                                                  {healthCareBenCashState.receiverName &&
                                                    <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                                  }
                                                  <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={healthCareBenCashState.receiverName}
                                                    disabled />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* CMND/CCCD */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenCashState.receiverPin &&
                                                    <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                                  }
                                                  <input type="search" placeholder="CMND/CCCD" maxLength="14" value={healthCareBenCashState.receiverPin}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                                    onChange={(e) => this.handlerOnChangeReceiverPin("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', e)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Ngày cấp */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="datewrapper">
                                                <div className="datewrapper__item" style={{ width: '100%' }}>
                                                  <div className="inputdate">
                                                    <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={healthCareBenCashState.receiverPinDate ? moment(healthCareBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                                  </div>
                                                </div>
                                              </div>
                                            </div>
                                            {/* Nơi cấp */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  <label>Nơi cấp</label>
                                                  <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                                    value={healthCareBenCashState.receiverPinLocation}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                                    onChange={(event) => this.handlerOnChangeReceiverPinLocation("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', event)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Chọn ngân hàng */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                  <div className="tab__content">
                                                    <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                      <Select
                                                        showSearch
                                                        size='large'
                                                        style={{ width: '100%', margin: '0' }}
                                                        width='100%'
                                                        bordered={false}
                                                        placeholder="Chọn ngân hàng"
                                                        optionFilterProp="bankname"
                                                        onChange={(e) => this.handlerOnChangeBank("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)}
                                                        // onFocus={onFocus}
                                                        // onBlur={onBlur}
                                                        // onSearch={onSearch}
                                                        value={healthCareBenCashState.bankId}
                                                        filterOption={(input, option) =>
                                                          option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                        }
                                                      >
                                                        {!isEmpty(bankList) && bankList.map((bank) => (
                                                          <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                        ))}
                                                      </Select>
                                                    </div>
                                                  </div>
                                                </div>
                                                <div className="dropdown__items">
                                                  <div className="dropdown-tag"></div>
                                                </div>
                                              </div>
                                            </div>
                                            {/* Chọn tỉnh/thành phố */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                  <div className="tab__content">
                                                    <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                      <Select
                                                        showSearch
                                                        size='large'
                                                        style={{ width: '100%', margin: '0' }}
                                                        width='100%'
                                                        bordered={false}
                                                        placeholder="Chọn tỉnh/ Thành phố"
                                                        optionFilterProp="cityname"
                                                        onChange={(v) => this.handlerOnChangeCity("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", v)}
                                                        // onFocus={onFocus}
                                                        // onBlur={onBlur}
                                                        // onSearch={onSearch}
                                                        value={healthCareBenCashState.cityCode}
                                                        filterOption={(input, option) =>
                                                          option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                        }
                                                      >
                                                        {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                          <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                        ))}
                                                      </Select>
                                                    </div>
                                                  </div>
                                                </div>
                                                <div className="dropdown__items">
                                                  <div className="dropdown-tag"></div>
                                                </div>
                                              </div>
                                            </div>
                                            {/* Chi nhánh */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenCashState.bankBranchName &&
                                                    <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                                  }
                                                  <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                    value={healthCareBenCashState.bankBranchName}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                                    onChange={(e) => this.handlerOnChangeBankBranchName("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                            {/* Phòng giao dịch */}
                                            <div className="checkbox-dropdown__content-item">
                                              <div className="input">
                                                <div className="input__content">
                                                  {healthCareBenCashState.bankOfficeName &&
                                                    <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                                  }
                                                  <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                    value={healthCareBenCashState.bankOfficeName}
                                                    onFocus={(e) => e.target.placeholder = ""}
                                                    onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                                    onChange={(e) => this.handlerOnChangeBankOfficeName("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, "cashTypeState", e)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>

                                  </div>
                                }
                              </div>
                            </div>
                          </div>
                        </div>
                      )

                    )
                  ) : (
                    (this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT]) || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)? (
                      // {/*Phương thức thanh toán claim life */}
                      <div className="info">
                        <div className="info__title">
                          <h4>Phương thức thanh toán</h4>
                        </div>
                        <div className="info__body">
                          <div className="checkbox-wrap basic-column">
                            <div className="tab-wrapper">
                              <div className="tab">
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="P2" valuename="Chuyển khoản"
                                      checked={lifeBenState.paymentMethodId === "P2"}
                                      onChange={(e) => this.handlerOnChangePaymentMethod("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, e)} />
                                    <div className="checkmark"></div>
                                    <p claas="text">Chuyển khoản</p>
                                  </label>
                                </div>
                                {/* Detail */}
                                <div className={lifeBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                  <div className="checkbox-dropdown__content">
                                    {/* Tên chủ tài khoản */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input disabled">
                                        <div className="input__content">
                                          {(lifeBenTransferState.bankAccountName || getSession(FULL_NAME)) &&
                                            <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                          }
                                          <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={lifeBenTransferState.bankAccountName || getSession(FULL_NAME)}
                                            disabled />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Số tài khoản */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankAccountNo &&
                                            <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                          }
                                          <NumberFormat displayType="input" type="tel" maxLength="20" value={lifeBenTransferState.bankAccountNo} prefix=""
                                            placeholder="Số tài khoản"
                                            allowNegative={false}
                                            allowLeadingZeros={true}
                                            onChange={(e) => this.handlerOnChangeBankAccountNo("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                          />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Chọn ngân hàng */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn ngân hàng"
                                                optionFilterProp="bankname"
                                                onChange={(e) => this.handlerOnChangeBank("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenTransferState.bankId}
                                                filterOption={(input, option) =>
                                                  option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(bankList) && bankList.map((bank) => (
                                                  <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chọn tỉnh/thành phố */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn tỉnh/ Thành phố"
                                                optionFilterProp="cityname"
                                                onChange={(v) => this.handlerOnChangeCity("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", v)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenTransferState.cityCode}
                                                filterOption={(input, option) =>
                                                  option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                  <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chi nhánh */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankBranchName &&
                                            <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                          }
                                          <input type="search" placeholder="Chi nhánh" maxLength="200"
                                            value={lifeBenTransferState.bankBranchName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                            onChange={(e) => this.handlerOnChangeBankBranchName("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Phòng giao dịch */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenTransferState.bankOfficeName &&
                                            <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                          }
                                          <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                            value={lifeBenTransferState.bankOfficeName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                            onChange={(e) => this.handlerOnChangeBankOfficeName("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div className="tab">
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                      checked={lifeBenState.paymentMethodId === "P1"}
                                      onChange={(event) => this.handlerOnChangePaymentMethod("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, event)} />
                                    <div className="checkmark"></div>
                                    <p claas="text">Nhận tiền mặt tại ngân hàng</p>
                                  </label>
                                </div>
                                {/* Detail */}
                                <div className={lifeBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                  <div className="checkbox-dropdown__content">
                                    {/* Họ tên người nhận */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input disabled">
                                        <div className="input__content">
                                          {lifeBenCashState.receiverName &&
                                            <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                          }
                                          <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={lifeBenCashState.receiverName}
                                            disabled />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* CMND/CCCD */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.receiverPin &&
                                            <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                          }
                                          <input type="search" placeholder="CMND/CCCD" maxLength="14" value={lifeBenCashState.receiverPin}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                            onChange={(e) => this.handlerOnChangeReceiverPin("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Ngày cấp */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="datewrapper">
                                        <div className="datewrapper__item" style={{ width: '100%' }}>
                                          <div className="inputdate">
                                            <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={lifeBenCashState.receiverPinDate ? moment(lifeBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Nơi cấp */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          <label>Nơi cấp</label>
                                          <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                            value={lifeBenCashState.receiverPinLocation}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                            onChange={(event) => this.handlerOnChangeReceiverPinLocation("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', event)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Chọn ngân hàng */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn ngân hàng"
                                                optionFilterProp="bankname"
                                                onChange={(e) => this.handlerOnChangeBank("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenCashState.bankId}
                                                filterOption={(input, option) =>
                                                  option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(bankList) && bankList.map((bank) => (
                                                  <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chọn tỉnh/thành phố */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="dropdown inputdropdown">
                                        <div className="dropdown__content">
                                          <div className="tab__content">
                                            <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                              <Select
                                                showSearch
                                                size='large'
                                                style={{ width: '100%', margin: '0' }}
                                                width='100%'
                                                bordered={false}
                                                placeholder="Chọn tỉnh/ Thành phố"
                                                optionFilterProp="cityname"
                                                onChange={(v) => this.handlerOnChangeCity("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", v)}
                                                // onFocus={onFocus}
                                                // onBlur={onBlur}
                                                // onSearch={onSearch}
                                                value={lifeBenCashState.cityCode}
                                                filterOption={(input, option) =>
                                                  option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                }
                                              >
                                                {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                  <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                ))}
                                              </Select>
                                            </div>
                                          </div>
                                        </div>
                                        <div className="dropdown__items">
                                          <div className="dropdown-tag"></div>
                                        </div>
                                      </div>
                                    </div>
                                    {/* Chi nhánh */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.bankBranchName &&
                                            <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                          }
                                          <input type="search" placeholder="Chi nhánh" maxLength="200"
                                            value={lifeBenCashState.bankBranchName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                            onChange={(e) => this.handlerOnChangeBankBranchName("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                    {/* Phòng giao dịch */}
                                    <div className="checkbox-dropdown__content-item">
                                      <div className="input">
                                        <div className="input__content">
                                          {lifeBenCashState.bankOfficeName &&
                                            <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                          }
                                          <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                            value={lifeBenCashState.bankOfficeName}
                                            onFocus={(e) => e.target.placeholder = ""}
                                            onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                            onChange={(e) => this.handlerOnChangeBankOfficeName("lifeBenState", PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)} />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ) : (
                      (!this.props.paymentMethodState.choseReceiver || !this.props.paymentMethodState.currentChoseReceiver) && !this.props.claimTypeState.isSamePoLi?
                        // {/*Phương thức thanh toán QL Health */}
                        (
                          <div className="info">
                            <div className="info__title">
                              <h4>Phương thức thanh toán</h4>
                            </div>
                            <div className="info__title" style={{ marginTop: '-20px' }}>
                              <p>Quyền lợi Bảo hiểm Chăm sóc sức khỏe/ Hỗ trợ viện phí sẽ được chi trả cho Người được bảo hiểm (NĐBH). Nếu  yêu cầu chi trả cho Bên mua bảo hiểm, cần cung cấp thông tin liên hệ của NĐBH  ở bước sau để  nhận thông báo  của Dai-ichi Life Việt Nam.</p>
                            </div>
                            <div className="info__body">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab">
                                    {/* Checkbox */}
                                    <div className="round-checkbox basic-bottom-margin">
                                      <label className="customradio">
                                        <input type="radio" value="PO" valuename="Bên mua bảo hiểm"
                                          checked={this.props.paymentMethodState.choseReceiver === "PO"}
                                          onChange={(e) => this.handlerOnChangeChooseReceiver(e)} />
                                        <div className="checkmark"></div>
                                        <p className="text">Bên mua bảo hiểm</p>
                                      </label>
                                    </div>

                                  </div>
                                  <div className="tab">
                                    {/* Checkbox */}
                                    <div className="round-checkbox basic-bottom-margin">
                                      <label className="customradio">
                                        <input type="radio" value="LI" valuename="Người được bảo hiểm"
                                          checked={this.props.paymentMethodState.choseReceiver === "LI"}
                                          onChange={(e) => this.handlerOnChangeChooseReceiver(e)} />
                                        <div className="checkmark"></div>
                                        <p className="text">Người được bảo hiểm</p>
                                      </label>
                                    </div>

                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        ) : (
                          <div className="info">
                            <div className="info__title">
                              <h4>Phương thức thanh toán</h4>
                            </div>
                            <div className="info__body">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab">
                                    {/* Checkbox */}
                                    <div className="round-checkbox basic-bottom-margin">
                                      <label className="customradio">
                                        <input type="radio" value="P2" valuename="Chuyển khoản"
                                          checked={healthCareBenState.paymentMethodId === "P2"}
                                          onChange={(e) => this.handlerOnChangePaymentMethod("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, e)} />
                                        <div className="checkmark"></div>
                                        <p claas="text">Chuyển khoản</p>
                                      </label>
                                    </div>
                                    {/* Detail */}
                                    <div className={healthCareBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                      <div className="checkbox-dropdown__content">
                                        {/* Tên chủ tài khoản */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input disabled">
                                            <div className="input__content">
                                              {healthCareBenTransferState.bankAccountName &&
                                                <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                              }
                                              <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={healthCareBenTransferState.bankAccountName}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "Tên chủ tài khoản"}
                                                // onChange={(e) => this.handlerOnChangeBankAccountName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", e)} 
                                                disabled
                                              />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Số tài khoản */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenTransferState.bankAccountNo &&
                                                <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                              }
                                              <NumberFormat displayType="input" type="tel" maxLength="20" value={healthCareBenTransferState.bankAccountNo} prefix=""
                                                placeholder="Số tài khoản"
                                                allowNegative={false}
                                                allowLeadingZeros={true}
                                                onChange={(e) => this.handlerOnChangeBankAccountNo("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", e)}
                                                onFocus={(e) => e.target.placeholder = ""}
                                                onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                              />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Chọn ngân hàng */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="dropdown inputdropdown">
                                            <div className="dropdown__content">
                                              <div className="tab__content">
                                                <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                  <Select
                                                    showSearch
                                                    size='large'
                                                    style={{ width: '100%', margin: '0' }}
                                                    width='100%'
                                                    bordered={false}
                                                    placeholder="Chọn ngân hàng"
                                                    optionFilterProp="bankname"
                                                    onChange={(e) => this.handlerOnChangeBank("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", e)}
                                                    // onFocus={onFocus}
                                                    // onBlur={onBlur}
                                                    // onSearch={onSearch}
                                                    value={healthCareBenTransferState.bankId}
                                                    filterOption={(input, option) =>
                                                      option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                    }
                                                  >
                                                    {!isEmpty(bankList) && bankList.map((bank) => (
                                                      <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                    ))}
                                                  </Select>
                                                </div>
                                              </div>
                                            </div>
                                            <div className="dropdown__items">
                                              <div className="dropdown-tag"></div>
                                            </div>
                                          </div>
                                        </div>
                                        {/* Chọn tỉnh/thành phố */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="dropdown inputdropdown">
                                            <div className="dropdown__content">
                                              <div className="tab__content">
                                                <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                  <Select
                                                    showSearch
                                                    size='large'
                                                    style={{ width: '100%', margin: '0' }}
                                                    width='100%'
                                                    bordered={false}
                                                    placeholder="Chọn tỉnh/ Thành phố"
                                                    optionFilterProp="cityname"
                                                    onChange={(v) => this.handlerOnChangeCity("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", v)}
                                                    // onFocus={onFocus}
                                                    // onBlur={onBlur}
                                                    // onSearch={onSearch}
                                                    value={healthCareBenTransferState.cityCode}
                                                    filterOption={(input, option) =>
                                                      option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                    }
                                                  >
                                                    {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                      <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                    ))}
                                                  </Select>
                                                </div>
                                              </div>
                                            </div>
                                            <div className="dropdown__items">
                                              <div className="dropdown-tag"></div>
                                            </div>
                                          </div>
                                        </div>
                                        {/* Chi nhánh */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenTransferState.bankBranchName &&
                                                <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                              }
                                              <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                value={healthCareBenTransferState.bankBranchName}
                                                onFocus={(e) => e.target.placeholder = ""}
                                                onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                                onChange={(e) => this.handlerOnChangeBankBranchName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", e)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Phòng giao dịch */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenTransferState.bankOfficeName &&
                                                <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                              }
                                              <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                value={healthCareBenTransferState.bankOfficeName}
                                                onFocus={(e) => e.target.placeholder = ""}
                                                onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                                onChange={(e) => this.handlerOnChangeBankOfficeName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, "transferTypeState", e)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div className="tab">
                                    {/* Checkbox */}
                                    <div className="round-checkbox basic-bottom-margin">
                                      <label className="customradio">
                                        <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                          checked={healthCareBenState.paymentMethodId === "P1"}
                                          onChange={(event) => this.handlerOnChangePaymentMethod("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, event)} />
                                        <div className="checkmark"></div>
                                        <p claas="text">Nhận tiền mặt tại ngân hàng</p>
                                      </label>
                                    </div>
                                    {/* Detail */}
                                    <div className={healthCareBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                                      <div className="checkbox-dropdown__content">
                                        {/* Họ tên người nhận */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input disabled">
                                            <div className="input__content">
                                              {healthCareBenCashState.receiverName &&
                                                <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                              }
                                              <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={healthCareBenCashState.receiverName}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "Họ tên người nhận"}
                                                // onChange={(e) => this.handlerOnChangeReceiverName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', e)} 
                                                disabled
                                              />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* CMND/CCCD */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenCashState.receiverPin &&
                                                <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                              }
                                              <input type="search" placeholder="CMND/CCCD" maxLength="14" value={healthCareBenCashState.receiverPin}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                                onChange={(e) => this.handlerOnChangeReceiverPin("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', e)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Ngày cấp */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="datewrapper">
                                            <div className="datewrapper__item" style={{ width: '100%' }}>
                                              <div className="inputdate">
                                                <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={healthCareBenCashState.receiverPinDate ? moment(healthCareBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate("healthCareBenState", PAYMENT_METHOD_CASE.FULL_ABOVE_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                        {/* Nơi cấp */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              <label>Nơi cấp</label>
                                              <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                                value={healthCareBenCashState.receiverPinLocation}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                                onChange={(event) => this.handlerOnChangeReceiverPinLocation("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', event)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Chọn ngân hàng */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="dropdown inputdropdown">
                                            <div className="dropdown__content">
                                              <div className="tab__content">
                                                <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                  <Select
                                                    showSearch
                                                    size='large'
                                                    style={{ width: '100%', margin: '0' }}
                                                    width='100%'
                                                    bordered={false}
                                                    placeholder="Chọn ngân hàng"
                                                    optionFilterProp="bankname"
                                                    onChange={(e) => this.handlerOnChangeBank("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', e)}
                                                    // onFocus={onFocus}
                                                    // onBlur={onBlur}
                                                    // onSearch={onSearch}
                                                    value={healthCareBenCashState.bankId}
                                                    filterOption={(input, option) =>
                                                      option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                    }
                                                  >
                                                    {!isEmpty(bankList) && bankList.map((bank) => (
                                                      <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                    ))}
                                                  </Select>
                                                </div>
                                              </div>
                                            </div>
                                            <div className="dropdown__items">
                                              <div className="dropdown-tag"></div>
                                            </div>
                                          </div>
                                        </div>
                                        {/* Chọn tỉnh/thành phố */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="dropdown inputdropdown">
                                            <div className="dropdown__content">
                                              <div className="tab__content">
                                                <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                                  <Select
                                                    showSearch
                                                    size='large'
                                                    style={{ width: '100%', margin: '0' }}
                                                    width='100%'
                                                    bordered={false}
                                                    placeholder="Chọn tỉnh/ Thành phố"
                                                    optionFilterProp="cityname"
                                                    onChange={(v) => this.handlerOnChangeCity("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', v)}
                                                    // onFocus={onFocus}
                                                    // onBlur={onBlur}
                                                    // onSearch={onSearch}
                                                    value={healthCareBenCashState.cityCode}
                                                    filterOption={(input, option) =>
                                                      option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                    }
                                                  >
                                                    {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                                      <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                    ))}
                                                  </Select>
                                                </div>
                                              </div>
                                            </div>
                                            <div className="dropdown__items">
                                              <div className="dropdown-tag"></div>
                                            </div>
                                          </div>
                                        </div>
                                        {/* Chi nhánh */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenCashState.bankBranchName &&
                                                <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                              }
                                              <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                value={healthCareBenCashState.bankBranchName}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                                onChange={(e) => this.handlerOnChangeBankBranchName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', e)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        {/* Phòng giao dịch */}
                                        <div className="checkbox-dropdown__content-item">
                                          <div className="input">
                                            <div className="input__content">
                                              {healthCareBenCashState.bankOfficeName &&
                                                <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                              }
                                              <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                value={healthCareBenCashState.bankOfficeName}
                                                // onFocus={(e) => e.target.placeholder = ""}
                                                // onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                                onChange={(e) => this.handlerOnChangeBankOfficeName("healthCareBenState", PAYMENT_METHOD_CASE.HC_HS_ABOVE_18, 'cashTypeState', e)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        )
                    ))
                ) : (
                  // {/*Phương thức thanh toán life hoặc HS/HC < 18 */}
                  <div className="info">
                    <div className="info__title">
                      <h4>Phương thức thanh toán</h4>
                    </div>
                    {(this.props.claimCheckedMap[CLAIM_TYPE.TPD] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || isHC3(this.props.claimCheckedMap, this.props.claimTypeList)) && !haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) ?(
                      <></>
                    ):(
                      <div className="info__title" style={{ marginTop: '-20px' }}>
                        {/* <p>Tôi hiểu rằng khi chi trả quyền lợi Bảo hiểm Chăm sóc sức khỏe/ Hỗ trợ viện phí, Dai-ichi Life sẽ chi trả cho Người được bảo hiểm. Do Người được bảo hiểm dưới 18 tuổi, bằng việc chọn Người nhận tiền là Bên mua bảo hiểm, tôi sẽ chịu trách nhiệm nếu có bất kỳ tranh chấp đối với số tiền chi trả.</p> */}
                      </div>
                    )}
                    <div className="info__body">
                      <div className="checkbox-wrap basic-column">
                        <div className="tab-wrapper">
                          <div className="tab">
                            {/* Checkbox */}
                            <div className="round-checkbox basic-bottom-margin">
                              <label className="customradio">
                                <input type="radio" value="P2" valuename="Chuyển khoản"
                                  checked={lifeBenState.paymentMethodId === "P2"}
                                  onChange={(e) => this.handlerOnChangePaymentMethod(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, e)} />
                                <div className="checkmark"></div>
                                <p claas="text">Chuyển khoản</p>
                              </label>
                            </div>
                            {/* Detail */}
                            <div className={lifeBenState.paymentMethodId === "P2" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                              <div className="checkbox-dropdown__content">
                                {/* Tên chủ tài khoản */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input disabled">
                                    <div className="input__content">
                                      {lifeBenTransferState.bankAccountName &&
                                        <label style={{ marginLeft: '2px' }}>Tên chủ tài khoản</label>
                                      }
                                      <input type="search" placeholder="Tên chủ tài khoản" maxLength="50" value={lifeBenTransferState.bankAccountName || getSession(FULL_NAME)}
                                        // onFocus={(e) => e.target.placeholder = ""}
                                        // onBlur={(e) => e.target.placeholder = "Tên chủ tài khoản"}
                                        // onChange={(e) => this.handlerOnChangeBankAccountName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)} 
                                        disabled
                                      />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Số tài khoản */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenTransferState.bankAccountNo &&
                                        <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                      }
                                      <NumberFormat displayType="input" type="tel" maxLength="20" value={lifeBenTransferState.bankAccountNo} prefix=""
                                        placeholder="Số tài khoản"
                                        allowNegative={false}
                                        allowLeadingZeros={true}
                                        onChange={(e) => this.handlerOnChangeBankAccountNo(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                      />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Chọn ngân hàng */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="dropdown inputdropdown">
                                    <div className="dropdown__content">
                                      <div className="tab__content">
                                        <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                          <Select
                                            showSearch
                                            size='large'
                                            style={{ width: '100%', margin: '0' }}
                                            width='100%'
                                            bordered={false}
                                            placeholder="Chọn ngân hàng"
                                            optionFilterProp="bankname"
                                            onChange={(e) => this.handlerOnChangeBank(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)}
                                            // onFocus={onFocus}
                                            // onBlur={onBlur}
                                            // onSearch={onSearch}
                                            value={lifeBenTransferState.bankId}
                                            filterOption={(input, option) =>
                                              option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                            }
                                          >
                                            {!isEmpty(bankList) && bankList.map((bank) => (
                                              <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                            ))}
                                          </Select>
                                        </div>
                                      </div>
                                    </div>
                                    <div className="dropdown__items">
                                      <div className="dropdown-tag"></div>
                                    </div>
                                  </div>
                                </div>
                                {/* Chọn tỉnh/thành phố */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="dropdown inputdropdown">
                                    <div className="dropdown__content">
                                      <div className="tab__content">
                                        <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                          <Select
                                            showSearch
                                            size='large'
                                            style={{ width: '100%', margin: '0' }}
                                            width='100%'
                                            bordered={false}
                                            placeholder="Chọn tỉnh/ Thành phố"
                                            optionFilterProp="cityname"
                                            onChange={(v) => this.handlerOnChangeCity(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", v)}
                                            // onFocus={onFocus}
                                            // onBlur={onBlur}
                                            // onSearch={onSearch}
                                            value={lifeBenTransferState.cityCode}
                                            filterOption={(input, option) =>
                                              option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                            }
                                          >
                                            {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                              <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                            ))}
                                          </Select>
                                        </div>
                                      </div>
                                    </div>
                                    <div className="dropdown__items">
                                      <div className="dropdown-tag"></div>
                                    </div>
                                  </div>
                                </div>
                                {/* Chi nhánh */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenTransferState.bankBranchName &&
                                        <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                      }
                                      <input type="search" placeholder="Chi nhánh" maxLength="200"
                                        value={lifeBenTransferState.bankBranchName}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                        onChange={(e) => this.handlerOnChangeBankBranchName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Phòng giao dịch */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenTransferState.bankOfficeName &&
                                        <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                      }
                                      <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                        value={lifeBenTransferState.bankOfficeName}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                        onChange={(e) => this.handlerOnChangeBankOfficeName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "transferTypeState", e)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div className="tab">
                            {/* Checkbox */}
                            <div className="round-checkbox basic-bottom-margin">
                              <label className="customradio">
                                <input type="radio" value="P1" valuename="Nhận tiền mặt tại ngân hàng"
                                  checked={lifeBenState.paymentMethodId === "P1"}
                                  onChange={(event) => this.handlerOnChangePaymentMethod(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, event)} />
                                <div className="checkmark"></div>
                                <p claas="text">Nhận tiền mặt tại ngân hàng</p>
                              </label>
                            </div>
                            {/* Detail */}
                            <div className={lifeBenState.paymentMethodId === "P1" ? "checkbox-dropdown show" : "checkbox-dropdown"}>
                              <div className="checkbox-dropdown__content">
                                {/* Họ tên người nhận */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input disabled">
                                    <div className="input__content">
                                      {lifeBenCashState.receiverName &&
                                        <label style={{ marginLeft: '2px' }}>Họ tên người nhận</label>
                                      }
                                      <input type="search" placeholder="Họ tên người nhận" maxLength="50" value={lifeBenCashState.receiverName}
                                        // onFocus={(e) => e.target.placeholder = ""}
                                        // onBlur={(e) => e.target.placeholder = "Họ tên người nhận"}
                                        // onChange={(e) => this.handlerOnChangeReceiverName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18,'cashTypeState', e)} 
                                        disabled
                                      />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* CMND/CCCD */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenCashState.receiverPin &&
                                        <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                      }
                                      <input type="search" placeholder="CMND/CCCD" maxLength="14" value={lifeBenCashState.receiverPin}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                        onChange={(e) => this.handlerOnChangeReceiverPin(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', e)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Ngày cấp */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="datewrapper">
                                    <div className="datewrapper__item" style={{ width: '100%' }}>
                                      <div className="inputdate">
                                        <DatePicker placeholder="Ngày cấp" disabledDate={disabledDate} value={lifeBenCashState.receiverPinDate ? moment(lifeBenCashState.receiverPinDate) : ""} onChange={(value) => this.handlerOnChangeDate(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                {/* Nơi cấp */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      <label>Nơi cấp</label>
                                      <input type="search" placeholder="Ví dụ: Cục Quản lý XNK Hà Nội" maxLength="100"
                                        value={lifeBenCashState.receiverPinLocation}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Ví dụ: Cục Quản lý XNK Hà Nội"}
                                        onChange={(event) => this.handlerOnChangeReceiverPinLocation(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, 'cashTypeState', event)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Chọn ngân hàng */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="dropdown inputdropdown">
                                    <div className="dropdown__content">
                                      <div className="tab__content">
                                        <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                          <Select
                                            showSearch
                                            size='large'
                                            style={{ width: '100%', margin: '0' }}
                                            width='100%'
                                            bordered={false}
                                            placeholder="Chọn ngân hàng"
                                            optionFilterProp="bankname"
                                            onChange={(e) => this.handlerOnChangeBank(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)}
                                            // onFocus={onFocus}
                                            // onBlur={onBlur}
                                            // onSearch={onSearch}
                                            value={lifeBenCashState.bankId}
                                            filterOption={(input, option) =>
                                              option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                            }
                                          >
                                            {!isEmpty(bankList) && bankList.map((bank) => (
                                              <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                            ))}
                                          </Select>
                                        </div>
                                      </div>
                                    </div>
                                    <div className="dropdown__items">
                                      <div className="dropdown-tag"></div>
                                    </div>
                                  </div>
                                </div>
                                {/* Chọn tỉnh/thành phố */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="dropdown inputdropdown">
                                    <div className="dropdown__content">
                                      <div className="tab__content">
                                        <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                                          <Select
                                            showSearch
                                            size='large'
                                            style={{ width: '100%', margin: '0' }}
                                            width='100%'
                                            bordered={false}
                                            placeholder="Chọn tỉnh/ Thành phố"
                                            optionFilterProp="cityname"
                                            onChange={(v) => this.handlerOnChangeCity(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", v)}
                                            // onFocus={onFocus}
                                            // onBlur={onBlur}
                                            // onSearch={onSearch}
                                            value={lifeBenCashState.cityCode}
                                            filterOption={(input, option) =>
                                              option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                            }
                                          >
                                            {!isEmpty(zipCodeList) && zipCodeList.map((city) => (
                                              <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                            ))}
                                          </Select>
                                        </div>
                                      </div>
                                    </div>
                                    <div className="dropdown__items">
                                      <div className="dropdown-tag"></div>
                                    </div>
                                  </div>
                                </div>
                                {/* Chi nhánh */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenCashState.bankBranchName &&
                                        <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                      }
                                      <input type="search" placeholder="Chi nhánh" maxLength="200"
                                        value={lifeBenCashState.bankBranchName}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                        onChange={(e) => this.handlerOnChangeBankBranchName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                                {/* Phòng giao dịch */}
                                <div className="checkbox-dropdown__content-item">
                                  <div className="input">
                                    <div className="input__content">
                                      {lifeBenCashState.bankOfficeName &&
                                        <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                      }
                                      <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                        value={lifeBenCashState.bankOfficeName}
                                        onFocus={(e) => e.target.placeholder = ""}
                                        onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                        onChange={(e) => this.handlerOnChangeBankOfficeName(null, PAYMENT_METHOD_CASE.CLAIM_LIFE_OR_HC_HS_UNDER_18, "cashTypeState", e)} />
                                    </div>
                                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                )
              )}
              {/* Phương thức thanh toán cho quyền lợi sức khỏe */}
              {/* {this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] && (this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] === true) && (

              )} */}
            </div>
            <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt="" />
            <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
          </div>
          {getSession(IS_MOBILE) &&
            <div className='nd13-padding-bottom64'></div>
          }
          <div className="bottom-btn">
            <button className={(jsonState.paymentMethodState.disabledButton || disable || this.props.isSubmitting || (!lifeBenState.paymentMethodId && !healthCareBenState.paymentMethodId)) && !backCase? "btn btn-primary disabled" : "btn btn-primary"}
              id="submitClaimDetail" disabled={!!((jsonState.paymentMethodState.disabledButton || disable || this.props.isSubmitting || (!lifeBenState.paymentMethodId && !healthCareBenState.paymentMethodId)) && !backCase)}
              onClick={()=>validateAndSubmit()}>{this.props.pinStep?'Lưu thông tin': 'Tiếp tục'}</button>
          </div>
        </div>
      </section>
    );
  }

}

export default PaymentMethod;
