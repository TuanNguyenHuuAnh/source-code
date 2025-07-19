// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import moment from 'moment';
import { getSession, VALID_EMAIL_REGEX, removeAccents, onlyLettersAndSpaces, is18Plus } from "../../../../util/common";
import { CLAIM_STATE, PAYMENT_METHOD_CASE } from "../../CreateClaim";
import dayjs from 'dayjs';
import { ADDRESS, CELL_PHONE, CLAIM_TYPE, EMAIL, FULL_NAME, POID, DOB } from "../../../../constants";
import WarningPopupChange from "../../../../components/WarningPopupChange";
import { SearchOutlined } from '@ant-design/icons';
import { Select, DatePicker } from 'antd';
import NumberFormat from 'react-number-format';
import parse from 'html-react-parser';

class ContactPersonDetail extends Component {

  constructor(props) {
    super(props);


    this.state = {
      isPOInfoDiff: false,
      isDiffEmail: false,
      isDiffPhone: false,
      isDiffAddress: false,
      msg: '',
      selectReceiver: null
    }

    this.handlerOnChangeFullName = this.onChangeFullName.bind(this);
    this.handlerOnChangePin = this.onChangePin.bind(this);
    this.handlerOnChangeDob = this.onChangeDob.bind(this);
    this.handlerOnChangeEmail = this.onChangeEmail.bind(this);
    this.handlerOnChangePhone = this.onChangePhone.bind(this);
    this.handlerOnChangePoRelation = this.onChangePoRelation.bind(this);
    this.handlerOnChangeAddress = this.onChangeAddress.bind(this);
  }


  componentDidMount() {
    let contactState = Object.assign({}, this.props.contactState);
    let paymentMethodState = Object.assign({}, this.props.paymentMethodState);
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      if (paymentMethodState.lifeBenState.paymentMethodId === "P2") {
        contactState.contactPersonInfo.fullName = paymentMethodState.lifeBenState.transferTypeState.bankAccountName;
      } else {
        contactState.contactPersonInfo.fullName = paymentMethodState.lifeBenState.cashTypeState.receiverName;
        contactState.contactPersonInfo.pin = paymentMethodState.lifeBenState.cashTypeState.receiverPin;
      }
    } else if ((!is18Plus(this.props.selectedCliInfo.DOB) || this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] || this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] || this.props.claimCheckedMap[CLAIM_TYPE.TPD]) && (this.props.paymentMethodState.choseReceiver !== 'LI')) {
      contactState.contactPersonInfo.fullName = getSession(FULL_NAME) ? getSession(FULL_NAME) : '';
      if (!contactState.contactPersonInfo.pin) {
        contactState.contactPersonInfo.pin = getSession(POID) ? getSession(POID) : '';
      }
      if (!contactState.contactPersonInfo.dob) {
        contactState.contactPersonInfo.dob = getSession(DOB) ? getSession(DOB) : '';
      }
      if (!contactState.contactPersonInfo.phone) {
        contactState.contactPersonInfo.phone = getSession(CELL_PHONE) ? getSession(CELL_PHONE) : '';
      }
      if (!contactState.contactPersonInfo.email) {
        contactState.contactPersonInfo.email = getSession(EMAIL) ? getSession(EMAIL) : '';
      }
      if (!contactState.contactPersonInfo.address) {
        contactState.contactPersonInfo.address = getSession(ADDRESS) ? getSession(ADDRESS) : '';
      }
    } else if (this.props.paymentMethodState.choseReceiver === 'LI') {
      contactState.contactPersonInfo.fullName = this.props.selectedCliInfo.FullName;
      if (!contactState.contactPersonInfo.pin) {
        contactState.contactPersonInfo.pin = this.props.selectedCliInfo.POID;
      }
      if (!contactState.contactPersonInfo.dob) {
        contactState.contactPersonInfo.dob = this.props.selectedCliInfo.DOB;
      }
      if (!contactState.contactPersonInfo.phone) {
        contactState.contactPersonInfo.phone = this.props.selectedCliInfo.CellPhone;
      }
      if (!contactState.contactPersonInfo.email) {
        contactState.contactPersonInfo.email = this.props.selectedCliInfo.Email;
      }
      if (!contactState.contactPersonInfo.address) {
        contactState.contactPersonInfo.address = this.props.selectedCliInfo.Address;
      }
      if (this.props.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) {
        if (!contactState.contactPersonInfo.role || contactState.contactPersonInfo.role === 'PO') {
          contactState.contactPersonInfo.role = 'PO';
          contactState.contactPersonInfo.fullName = getSession(FULL_NAME);
          contactState.contactPersonInfo.pin = getSession(POID);
          contactState.contactPersonInfo.dob = getSession(DOB);
          contactState.contactPersonInfo.phone = getSession(CELL_PHONE);
          contactState.contactPersonInfo.email = getSession(EMAIL);
          contactState.contactPersonInfo.address = getSession(ADDRESS);
        }
      }
    } else if ((this.props.paymentMethodState.choseReceiver === 'PO') || this.props.claimTypeState.isSamePoLi) {
      contactState.contactPersonInfo.fullName = getSession(FULL_NAME) ? getSession(FULL_NAME) : '';
      if (!contactState.contactPersonInfo.pin) {
        contactState.contactPersonInfo.pin = getSession(POID) ? getSession(POID) : '';
      }
      if (!contactState.contactPersonInfo.dob) {
        contactState.contactPersonInfo.dob = getSession(DOB) ? getSession(DOB) : '';
      }
      if (!contactState.contactPersonInfo.phone) {
        contactState.contactPersonInfo.phone = getSession(CELL_PHONE) ? getSession(CELL_PHONE) : '';
      }
      if (!contactState.contactPersonInfo.email) {
        contactState.contactPersonInfo.email = getSession(EMAIL) ? getSession(EMAIL) : '';
      }
      if (!contactState.contactPersonInfo.address) {
        contactState.contactPersonInfo.address = getSession(ADDRESS) ? getSession(ADDRESS) : '';
      }
    } else {
      if (!contactState.contactPersonInfo.pin) {
        contactState.contactPersonInfo.pin = this.props.selectedCliInfo.POID;
      }
      if (!contactState.contactPersonInfo.dob) {
        contactState.contactPersonInfo.dob = this.props.selectedCliInfo.DOB;
      }
      if (!contactState.contactPersonInfo.phone) {
        contactState.contactPersonInfo.phone = this.props.selectedCliInfo.CellPhone;
      }
      if (!contactState.contactPersonInfo.email) {
        contactState.contactPersonInfo.email = this.props.selectedCliInfo.Email;
      }
      if (!contactState.contactPersonInfo.address) {
        contactState.contactPersonInfo.address = this.props.selectedCliInfo.Address;
      }
    }

    this.updateState(contactState);
  }

  onChangeFullName(event) {
    if (!onlyLettersAndSpaces(event.target.value)) {
      return;
    }
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.fullName = event.target.value;
    this.updateState(contactState);
  }

  onChangePin(event) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.pin = event.target.value;
    this.updateState(contactState);
  }

  onChangeDob(value) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.dob = value;

    let todayDate = new Date();
    if (contactState.contactPersonInfo.dob !== null && contactState.contactPersonInfo.dob !== undefined
      && moment(contactState.contactPersonInfo.dob, "yyyy-MM-DD") > moment(todayDate)) {
      contactState.contactPersonInfo.errors.dob = "Ngày sinh không được sau ngày hiện tại";
    } else {
      contactState.contactPersonInfo.errors.dob = "";
    }
    this.updateState(contactState);
  }

  onChangeEmail(event) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.email = event.target.value;

    if (contactState.contactPersonInfo.email === null || contactState.contactPersonInfo.email === undefined
      || (contactState.contactPersonInfo.email !== '' && !VALID_EMAIL_REGEX.test(contactState.contactPersonInfo.email))) {
      contactState.contactPersonInfo.errors.email = "Email không hợp lệ";
    } else {
      contactState.contactPersonInfo.errors.email = "";
    }
    this.updateState(contactState);
    if (!this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && ((this.props.paymentMethodState.choseReceiver === null) || (this.props.paymentMethodState.choseReceiver === 'PO') || this.props.claimTypeState.isSamePoLi || (contactState.contactPersonInfo.role === 'PO'))) {
      if (event.target.value !== (getSession(EMAIL) ? getSession(EMAIL) : '')) {
        this.setState({ isDiffEmail: true });
      } else {
        this.setState({ isDiffEmail: false });
      }
    }
  }

  onChangePhone(event) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.phone = event.target.value;
    this.updateState(contactState);
    if (!this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && ((this.props.paymentMethodState.choseReceiver === null) || (this.props.paymentMethodState.choseReceiver === 'PO') || this.props.claimTypeState.isSamePoLi || (contactState.contactPersonInfo.role === 'PO'))) {
      if (event.target.value !== (getSession(CELL_PHONE) ? getSession(CELL_PHONE) : '')) {
        this.setState({ isDiffPhone: true });
      } else {
        this.setState({ isDiffPhone: false });
      }
    }
  }

  onChangePoRelation(value) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.poRelation = value;
    const relationCode = this.props.relationShipList.filter(result => result.RelationName === value);
    if (relationCode && (relationCode.length > 0)) {
      contactState.contactPersonInfo.poRelationCode = relationCode[0].RelationCode;
    }

    this.updateState(contactState);
  }

  onChangeAddress(event) {
    let contactState = Object.assign({}, this.props.contactState);
    contactState.contactPersonInfo.address = event.target.value;
    this.updateState(contactState);
    if (!this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && ((this.props.paymentMethodState.choseReceiver === null) || (this.props.paymentMethodState.choseReceiver === 'PO') || this.props.claimTypeState.isSamePoLi || (contactState.contactPersonInfo.role === 'PO'))) {
      if (event.target.value !== (getSession(ADDRESS) ? getSession(ADDRESS) : '')) {
        this.setState({ isDiffAddress: true });
      } else {
        this.setState({ isDiffAddress: false });
      }
    }
  }

  updateState(contactState) {
    // Check condition for enabling/disabling
    let isInvalid = true;
    if (this.props.claimCheckedMap[CLAIM_TYPE.DEATH]) {
      isInvalid = Object.entries(contactState.contactPersonInfo)
        .filter(([k, v]) => (k != 'role') && (v === null || v === undefined || !v)).length > 0;
    } else {
      isInvalid = Object.entries(contactState.contactPersonInfo)
        .filter(([k, v]) => (k !== 'pin' && k !== 'dob' && k !== 'poRelation' && k != 'poRelationCode' && k != 'role') && (v === null || v === undefined || !v)).length > 0;

    }

    contactState.disabledButton = isInvalid;
    this.props.handlerUpdateMainState("contactState", contactState);
  }

  render() {
    const { Option } = Select;
    function disabledDate(current) {
      return current && (current > dayjs().endOf('day'));
    }
    const closePOInfoDiff = () => {
      this.setState({ isPOInfoDiff: false });
    }
    const checkSubmit = () => {
      let contactState = Object.assign({}, this.props.contactState);
      if (!this.props.claimCheckedMap[CLAIM_TYPE.DEATH] && ((this.props.paymentMethodState.choseReceiver === null) || (this.props.paymentMethodState.choseReceiver === 'PO') || this.props.claimTypeState.isSamePoLi || (contactState.contactPersonInfo.role === 'PO')) && (this.state.isDiffEmail || this.state.isDiffPhone || this.state.isDiffAddress)) {
        let text = '';
        if (this.state.isDiffAddress) {
          text = 'Địa chỉ';
        }
        if (this.state.isDiffEmail) {
          if (!text) {
            text = 'Email';
          } else {
            text = text + ', Email';
          }
        }
        if (this.state.isDiffPhone) {
          if (!text) {
            text = 'Số điện thoại';
          } else {
            text = text + ', Số điện thoại';
          }
        }

        this.setState({ isPOInfoDiff: true, msg: text });
        return;
      }
      this.props.handlerSubmitContact();
    }
    const changeSelectReceiver = (e) => {
      let contactState = Object.assign({}, this.props.contactState);
      if (e.target.value === 'LI') {
        contactState.contactPersonInfo.fullName = this.props.selectedCliInfo.FullName;
        contactState.contactPersonInfo.pin = this.props.selectedCliInfo.POID;
        contactState.contactPersonInfo.dob = this.props.selectedCliInfo.DOB;
        contactState.contactPersonInfo.phone = this.props.selectedCliInfo.CellPhone;
        contactState.contactPersonInfo.email = this.props.selectedCliInfo.Email;
        contactState.contactPersonInfo.address = this.props.selectedCliInfo.Address;
        contactState.contactPersonInfo.role = 'LI';
        // this.setState({ selectReceiver: 'LI' });
      } else {
        contactState.contactPersonInfo.fullName = getSession(FULL_NAME);
        contactState.contactPersonInfo.pin = getSession(POID);
        contactState.contactPersonInfo.dob = getSession(DOB);
        contactState.contactPersonInfo.phone = getSession(CELL_PHONE);
        contactState.contactPersonInfo.email = getSession(EMAIL);
        contactState.contactPersonInfo.address = getSession(ADDRESS);
        contactState.contactPersonInfo.role = 'PO';
        // this.setState({ selectReceiver: 'PO' });
      }
      this.updateState(contactState);
    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    var pContactPersonInfo = Object.assign({}, this.props.contactPersonInfo);
    let todayDate = new Date();
    let todayDateStr = new Date(todayDate.getTime() - (todayDate.getTimezoneOffset() * 60000)).toISOString().split("T")[0];
    return (
      <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance">
          <div className="heading">
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
            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
              <p>Chọn thông tin</p>
              <i><img src="../../img/icon/return_option.svg" alt="" /></i>
            </div>
            <div className="heading__tab">
              <div className="step-container">
                <div className="step-wrapper">
                  <div className="step-btn-wrapper">
                    <div className="back-btn" onClick={() => this.props.handlerBackToPrevStep(this.props.currentState)}>
                      <button>
                        <div className="svg-wrapper">
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
                        <span className="simple-brown">Quay lại</span>
                      </button>
                    </div>
                    {/* <div className="save-wrap">
                      <button className="back-text">Lưu</button>
                    </div> */}
                  </div>
                  <div className="progress-bar">
                    <div className={(this.props.currentState >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>1</span>
                      </div>
                      <p>Thông tin sự kiện</p>
                    </div>
                    <div className={(this.props.currentState >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>2</span>
                      </div>
                      <p>Thanh toán và liên hệ</p>
                    </div>
                    <div className={(this.props.currentState >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>3</span>
                      </div>
                      <p>Kèm <br />chứng từ</p>
                    </div>
                    <div className={(this.props.currentState >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>4</span>
                      </div>
                      <p>Hoàn tất yêu cầu</p>
                    </div>
                  </div>
                  <div className="step-btn-save-quit">
                    <div>
                      <button>
                        <span className="simple-brown" style={{ zIndex: '30' }} onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="stepform">
            <div className="info">
              <div className="info__body">
                <div className="optionalform-wrapper">
                  <div className="optionalform">
                    <div className="optionalform__title">
                      <h5 className="basic-bold">Thông tin liên hệ</h5>
                    </div>
                    {(this.props.paymentMethodState.paymentMethodCase === PAYMENT_METHOD_CASE.FULL_ABOVE_18) && ((pContactPersonInfo.role === 'LI') || (pContactPersonInfo.role === 'PO')) ? (
                      <>
                        <div className="optionalform__title"><p>Quý khách muốn chúng tôi liên hệ với ai cho yêu cầu bảo hiểm này?</p></div>
                        <div className="info__body">
                          <div className="checkbox-wrap basic-column">
                            <div className="tab-wrapper">
                              <div className="tab">
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="PO" valuename="Bên mua bảo hiểm"
                                      checked={pContactPersonInfo.role === "PO"}
                                      onChange={(e) => changeSelectReceiver(e)} />
                                    <div className="checkmark"></div>
                                    <p className="text">Bên mua bảo hiểm</p>
                                  </label>
                                </div>
                              </div>

                              <div className="tab" style={{ paddingTop: '12px', paddingBottom: '20px', borderTop: '1px solid #e6e6e6' }}>
                                {/* Checkbox */}
                                <div className="round-checkbox basic-bottom-margin">
                                  <label className="customradio">
                                    <input type="radio" value="LI" valuename="Người được bảo hiểm"
                                      checked={pContactPersonInfo.role === "LI"}
                                      onChange={(e) => changeSelectReceiver(e)} />
                                    <div className="checkmark"></div>
                                    <p className="text">Người được bảo hiểm</p>
                                  </label>
                                </div>
                              </div>

                            </div>
                          </div>
                        </div>
                      </>
                    ) : (
                      <div className="optionalform__title"><p>Vui lòng kiểm tra thông tin liên hệ cho yêu cầu bồi thường này.</p></div>
                    )}

                    <div className="optionalform__body">
                      <div className="tab-wrapper">
                        {/* Họ và tên */}
                        {this.props.claimCheckedMap[CLAIM_TYPE.DEATH] ? (
                          <div className="tab">
                            <div className="input">
                              <div className="input__content">
                                {this.props.contactPersonInfo.fullName &&
                                  <label style={{ marginLeft: '2px' }}>Họ và tên</label>
                                }
                                <input type="search" placeholder="Họ và tên" maxLength="100" value={this.props.contactPersonInfo.fullName}
                                  // onFocus={(e) => e.target.placeholder = ""}
                                  // onBlur={(e) => e.target.placeholder = "Họ và tên"}
                                  onChange={this.handlerOnChangeFullName} />
                              </div>
                              <i><img src="../../img/icon/edit.svg" alt="" /></i>
                            </div>
                          </div>
                        ) : (
                          <div className="tab">
                            <div className="input disabled">
                              <div className="input__content">
                                {this.props.contactPersonInfo.fullName &&
                                  <label style={{ marginLeft: '2px' }}>Họ và tên</label>
                                }
                                <input type="search" placeholder="Họ và tên" maxLength="100" value={this.props.contactPersonInfo.fullName}
                                  disabled
                                />
                              </div>
                              <i><img src="../../img/icon/edit.svg" alt="" /></i>
                            </div>
                          </div>
                        )}
                        {/* CMND/CCCD */}
                        {this.props.claimCheckedMap[CLAIM_TYPE.DEATH] &&
                          <div className="tab">
                            <div className="input">
                              <div className="input__content">
                                {this.props.contactPersonInfo.pin &&
                                  <label style={{ marginLeft: '2px' }}>CMND/CCCD</label>
                                }
                                <input type="search" placeholder="CMND/CCCD" maxLength="14" value={this.props.contactPersonInfo.pin}
                                  // onFocus={(e) => e.target.placeholder = ""}
                                  // onBlur={(e) => e.target.placeholder = "CMND/CCCD"}
                                  onChange={this.handlerOnChangePin} />
                              </div>
                              <i><img src="../../img/icon/edit.svg" alt="" /></i>
                            </div>
                          </div>
                        }
                        {/* Ngày sinh */}
                        {this.props.claimCheckedMap[CLAIM_TYPE.DEATH] &&
                          <>
                            <div className="tab">
                              <div className="datewrapper">
                                <div className="datewrapper__item" style={{ width: '100%' }}>
                                  <div className="inputdate">
                                    {/* <input type='date' placeholder="Ngày sinh" */}
                                    <DatePicker placeholder="Ngày sinh" disabledDate={disabledDate} value={this.props.contactPersonInfo.dob ? moment(this.props.contactPersonInfo.dob) : ""} onChange={(value) => this.handlerOnChangeDob(value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                  </div>
                                </div>
                              </div>
                            </div>

                            {pContactPersonInfo.errors.dob.length > 0 &&
                              <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                                {pContactPersonInfo.errors.dob}
                              </span>}
                          </>
                        }
                        {/* Điện thoại */}
                        <div className="tab">
                          <div className="input">
                            <div className="input__content">
                              {this.props.contactPersonInfo.phone &&
                                <label style={{ marginLeft: '2px' }}>Điện thoại</label>
                              }
                              <NumberFormat displayType="input" type="search" maxLength="12" value={this.props.contactPersonInfo.phone} prefix=""
                                placeholder="Điện thoại"
                                allowNegative={false}
                                allowLeadingZeros={true}
                                onChange={this.handlerOnChangePhone}
                                onFocus={(e) => e.target.placeholder = ""}
                                onBlur={(e) => e.target.placeholder = "Điện thoại"}
                              />
                            </div>
                            <i><img src="../../img/icon/input_phone.svg" alt="" /></i>
                          </div>
                        </div>
                        {/* Email */}
                        <div className="tab">
                          <div className="input">
                            <div className="input__content">
                              {this.props.contactPersonInfo.email &&
                                <label style={{ marginLeft: '2px' }}>Email</label>
                              }
                              <input type="search" placeholder="Email" maxLength="100" value={this.props.contactPersonInfo.email}
                                onChange={this.handlerOnChangeEmail} />
                            </div>
                            <i><img src="../../img/icon/email.svg" alt="" /></i>
                          </div>
                        </div>
                        {pContactPersonInfo.errors.email.length > 0 &&
                          <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                            {pContactPersonInfo.errors.email}
                          </span>}
                        {/* Quan hệ với bên mua bảo hiểm */}
                        {this.props.claimCheckedMap[CLAIM_TYPE.DEATH] &&
                          <div className="tab">
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
                                      placeholder="Quan hệ với bên mua bảo hiểm"
                                      optionFilterProp="name"
                                      onChange={(value) => this.handlerOnChangePoRelation(value)}
                                      value={this.props.contactPersonInfo.poRelation}
                                      filterOption={(input, option) =>
                                        removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                                      }
                                    >
                                      {this.props.relationShipList.map((relation) => (
                                        <Option key={relation.RelationName} name={relation.RelationName}>{relation.RelationName}</Option>
                                      ))}
                                    </Select>
                                  </div>
                                </div>
                              </div>
                              <div className="dropdown__items"></div>
                            </div>
                          </div>
                        }
                        {/* Địa chỉ liên hệ */}
                        <div className="tab">
                          <div className="input">
                            <div className="input__content">
                              {this.props.contactPersonInfo.address &&
                                <label style={{ marginLeft: '2px' }}>Địa chỉ</label>
                              }
                              <textarea className='eclaim-text-area' rows="4" placeholder="Địa chỉ" maxLength="250" value={this.props.contactPersonInfo.address}
                                onChange={this.handlerOnChangeAddress} />
                            </div>
                            <i><img src="../../img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <img className="decor-clip" src="../../img/mock.svg" alt="" />
            <img className="decor-person" src="../../img/person.png" alt="" />
          </div>
          <div className="bottom-btn">
            <button className={(this.props.contactState.disabledButton) ? "btn btn-primary disabled" : "btn btn-primary"}
              id="submitContactDetail" disabled={!!this.props.contactState.disabledButton}
              onClick={() => checkSubmit()}>Tiếp tục</button>
          </div>
        </div>
        {this.state.isPOInfoDiff &&
          <WarningPopupChange closePopup={closePOInfoDiff} parentSubmit={this.props.handlerSubmitContact} msg={parse("<p>Thông tin <span className='basic-bold'>" + this.state.msg + "</span> của <span className='basic-bold'>" + this.props.contactPersonInfo.fullName + "</span> đang khác với thông tin đã cung cấp trên hợp đồng bảo hiểm, nên thông tin liên hệ chỉ áp dụng cho yêu cầu này.</p><p>Quý khách vui lòng vào tính năng “Điều chỉnh thông tin hợp đồng” hoặc đến văn phòng Dai ichi Life Việt Nam gần nhất để cập nhật thông tin mới.</p>")} imgPath="../../../../img/popup/contact-info.svg" />
        }
      </section>
    );
  }
}

export default ContactPersonDetail;
