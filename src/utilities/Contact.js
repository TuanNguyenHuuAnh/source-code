// import 'antd/dist/antd.css';
// import '../claim.css';
import './utilities.css';
import React, { Component } from "react";
import { Link } from 'react-router-dom';

import LoadingIndicator from '../../src/common/LoadingIndicator2';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EMAIL,
  CELL_PHONE,
  AUTHENTICATION,
  COMPANY_KEY,
  FE_BASE_URL,
  PageScreen
} from "../constants";
import PopupException from "../popup/PopupException"
import PopupContactFormSucceeded from "../popup/PopupContactFormSucceeded"

import { postContactForm, CPSaveLog } from '../util/APIUtils';

import { loadCaptchaEnginge, LoadCanvasTemplate, validateCaptcha } from '../util/captcha';
import {VALID_EMAIL_REGEX, isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";
import { Helmet } from "react-helmet";

export const TABS = Object.freeze({
  THU_TUC: 0,
  BAO_LANH_VIEN_PHI: 1,
});

class Contact extends Component {
  constructor(props) {
    super(props);

    this.state = {
      // TODO: Error in API
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Project: "mcp",
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
          Action: "ContactForm",
        },
      },
      contactForm: {
        phone: '',
        email: '',
        residence: '',
        contactDetail: '',
        captcha: '',
        errors: {
          phone: '',
          email: '',
          residence: '',
          contactDetail: '',
          captcha: '',
        }
      },
      isValid: false,
      isSubmitting: false,
      isSucceeded: false,
      hasError: false,
      renderMeta: false
    };

    this.handlerChangeInput = this.onChangeInput.bind(this);
    this.handlerValidateInput = this.validateInput.bind(this);
    this.handlerSubmitForm = this.submitForm.bind(this);
    this.handlerClosePopup = this.closePopup.bind(this);
  }

  componentDidMount() {
    this.initializeState();
    this.cpSaveLog(`Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`);
    trackingEvent(
        "Tiện ích",
        `Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`,
        `Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`);
    trackingEvent(
        "Tiện ích",
        `Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`,
        `Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`,
    );
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

  initializeState() {
    var jsonState = this.state;
    jsonState.contactForm = {
      phone: isLoggedIn() ? getSession(CELL_PHONE) : "",
      email: isLoggedIn() ? getSession(EMAIL) : "",
      residence: '',
      contactDetail: '',
      captcha: '',
      errors: {
        phone: '',
        email: '',
        residence: '',
        contactDetail: '',
        captcha: '',
      }
    };
    jsonState.isValid = false;
    jsonState.isSubmitting = false;
    jsonState.isSucceeded = false;
    jsonState.hasError = false;
    this.setState(jsonState);
    loadCaptchaEnginge(6, 'grey', 'white');
  };

  onChangeInput(event) {
    const { name, value } = event.target;
    var jsonState = this.state;
    switch (name) {
      case "phone":
        jsonState.contactForm.phone = value;
        break;
      case "email":
        jsonState.contactForm.email = value;
        break;
      case "residence":
        jsonState.contactForm.residence = value;
        break;
      case "contactDetail":
        jsonState.contactForm.contactDetail = value;
        break;
      case "captcha":
        jsonState.contactForm.captcha = value;
        // if (jsonState.contactForm.captcha.length === 6) {
        //   this.validateInput(event);
        // }
        break;
      default:
        break;
    }
    this.setState(jsonState);
  }

  validateInput(event) {
    const { name, value } = event.target;
    var jsonState = this.state;
    let errors = jsonState.contactForm.errors;
    switch (name) {
      case "phone":
        errors.phone = value.length < 10 || !/^\d+$/.test(value) ? "Số điện thoại phải có độ dài 10 chữ số" : "";
        break;
      case "email":
        errors.email = value.length === 0 || !VALID_EMAIL_REGEX.test(value) ? "Email không hợp lệ" : "";
        break;
      case "residence":
        errors.residence = value.length === 0 ? "Nơi cư trú không được để trống" : "";
        break;
      case "contactDetail":
        errors.contactDetail = value.length === 0 ? "Nhu cầu liên hệ không được để trống" : "";
        break;
      case "captcha":
        errors.captcha = !validateCaptcha(value, false) ? "Mã bảo vệ không đúng" : "";
        break;
      default:
        break;
    }
    jsonState.isValid = this.validateSubmission(jsonState);
    this.setState(jsonState);
    //console.log(jsonState);
  }

  validateSubmission(jsonState) {
    if (jsonState === null || jsonState === undefined) {
      return false;
    }

    let { errors, ...inputDataDetail } = jsonState.contactForm;
    for (let inputName in inputDataDetail) {
      if (inputDataDetail[inputName] === '') {
        return false;
      }
    }

    for (let errorName in errors) {
      if (errors[errorName].length !== 0) {
        return false;
      }
      let inputValue = inputDataDetail[errorName];
      switch (errorName) {
        case "phone":
          errors.phone = inputValue.length < 10 || !/^\d+$/.test(inputValue) ? "Số điện thoại phải có độ dài 10 chữ số" : "";
          break;
        case "email":
          errors.email = inputValue.length === 0 || !VALID_EMAIL_REGEX.test(inputValue) ? "Email không hợp lệ" : "";
          break;
        case "residence":
          errors.residence = inputValue.length === 0 ? "Nơi cư trú không được để trống" : "";
          break;
        case "contactDetail":
          errors.contactDetail = inputValue.length === 0 ? "Nhu cầu liên hệ không được để trống" : "";
          break;
        case "captcha":
          errors.captcha = !validateCaptcha(inputValue) ? "Mã bảo vệ không đúng" : "";
          break;
        default:
          break;
      }
    }

    return true;
  }

  submitForm() {
    if (!this.state.isValid || this.state.isSubmitting) {
      return;
    }
    var jsonState = this.state;
    jsonState.isSubmitting = true;
    this.setState(jsonState);

    const apiRequest = Object.assign({}, this.state.jsonInput);
    apiRequest.jsonDataInput['ContactPhone'] = jsonState.contactForm.phone;
    apiRequest.jsonDataInput['Email'] = jsonState.contactForm.email;
    apiRequest.jsonDataInput['City'] = jsonState.contactForm.residence;
    apiRequest.jsonDataInput['ContactContent'] = jsonState.contactForm.contactDetail;
    if (apiRequest.jsonDataInput['UserLogin'] === null
      || apiRequest.jsonDataInput['UserLogin'] === undefined
      || apiRequest.jsonDataInput['UserLogin'] === '') {
      apiRequest.jsonDataInput['UserLogin'] = jsonState.contactForm.email;
    }
    postContactForm(apiRequest).then(Res => {
      let Response = Res.Response;
      var jsonState = this.state;
      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        jsonState.isSucceeded = true;
        this.setState(jsonState);
      } else {
        jsonState.hasError = true;
        this.setState(jsonState);
      }
    }).catch(error => {
      jsonState.hasError = true;
      this.setState(jsonState);
    });
  }

  closePopup() {
    this.initializeState();
  }

  render() {
    var jsonState = this.state;
    var errors = jsonState.contactForm.errors;
    return (
      <main className={isLoggedIn() ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Liên hệ – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Trang thông tin hỗ trợ liên quan sản phẩm, dịch vụ chăm sóc khách hàng hoặc quyền lợi khi tham gia bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Liên hệ" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities/contact"} />
            <meta property="og:title" content="Liên hệ - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin hỗ trợ liên quan sản phẩm, dịch vụ chăm sóc khách hàng hoặc quyền lợi khi tham gia bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities/contact"} />
          </Helmet>
        }
        <div className="main-warpper customer-feedback-page">
          {/* Route display */}
          <section className="scbreadcrums basic-white">
            <div className="container">
              <div className="breadcrums">
                <Link to="/" className="breadcrums__item">
                    <p>Trang chủ</p>
                    <p className='breadcrums__item_arrow'>></p>
                </Link>
                <Link to="/utilities" className="breadcrums__item">
                    <p>Tiện ích</p>
                    <p className='breadcrums__item_arrow'>></p>
                </Link>

                <div className="breadcrums__item">
                  <p>Liên hệ</p>
                  <span>&gt;</span>
                </div>
              </div>
            </div>
          </section>
          {/* Banner */}
          <div className="scdieukhoan">
            <div className="container">
              <h1>Liên hệ</h1>
            </div>
          </div>
          {/* Main page */}
          <section className="sc-contact">
            <div className="container">
              <div className="contact-page">
                <div className="lien-he-form">
                  <div className="lien-he-form__body">
                    <div className="lien-he-form-wrapper">
                      <div className="lien-heform">
                        {/* Liên hệ trực tiếp */}
                        <div className="lien-heform-left">
                          <div className="lien-heform-left-content">
                            <h2>Liên hệ trực tiếp</h2>
                            <div className="hot-line">
                              <div className="hot-line-tit">Đường dây nóng: </div>
                              <div className="hot-line-content">
                                <img src="/img/icon/phone-nobg.svg" alt="phone" />
                                <p>(028) 3810 0888 - Bấm phím 1</p>
                              </div>
                              <div className="hot-line-content">
                                <img src="/img/icon/phone-nobg.svg" alt="phone" />
																<p>(028) 7308 8880 - Bấm phím 1</p>
															</div>
                              <div className="hot-line-content">
                                <p>Thứ Hai đến Chủ Nhật: từ 08:00 - 17:30</p>
                              </div>
                              <div className="hot-line-content">
                                <p style={{ fontSize: 16 }}>(trừ các ngày nghỉ lễ, Tết)</p>
                              </div>
                            </div>
                            <div className="email-list" style={{ 'margin-bottom': '41px' }}>
                              <div className="email-list-tit">
                                <img src="/img/icon/email-nobg.svg" alt="email" />
                                <p>Email</p>
                              </div>
                              <div className="address-list-content">
                                <ul>
                                  <li>
                                    Thông tin liên quan sản phẩm và dịch vụ chăm sóc khách hàng hoặc quyền lợi khi tham
                                    gia bảo hiểm:
                                    <a href="mailto: customer.services@dai-ichi-life.com.vn" className="simple-brown2">
                                      customer.services@dai-ichi-life.com.vn
                                    </a>
                                    {/* <Link to='#' className="simple-brown2" onClick={(e) => {
                                      window.location = 'mailto: customer.services@dai-ichi-life.com.vn';
                                      e.preventDefault();
                                    }}
                                    >customer.services@dai-ichi-life.com.vn</Link> */}
                                  </li>
                                  <li>
                                    Các vấn đề khác: <a href="mailto: customer.services@dai-ichi-life.com.vn" className="simple-brown2">info@dai-ichi-life.com.vn</a>
                                  </li>
                                </ul>
                              </div>
                            </div>
                            <div className="address-list">
                              <div className="address-list-tit">
                                <img src="/img/icon/location-nobg.svg" alt="address" />
                                <p>Địa chỉ</p>
                              </div>
                              <div className="address-list-content">
                                <ul>
                                  {/* <li>
                                    Trụ sở chính: Tòa nhà DAI-ICHI LIFE, 149-151 Nguyễn Văn Trỗi, Phường 11, Quận Phú
                                    Nhuận, TP.Hồ Chí Minh
                                  </li> */}
                                  <li>
                                    Trụ sở chính: Tòa nhà DAI-ICHI LIFE, 149-151 Nguyễn Văn Trỗi, Phường Phú Nhuận, TP.Hồ Chí Minh
                                  </li>
                                  <li>
                                    Hoặc <Link to='/utilities/network' className="simple-brown2">Tìm kiếm Văn phòng Tổng đại lý, Trung tâm dịch vụ khách hàng gần nhất</Link>
                                  </li>
                                  <li>
                                    Thời gian làm việc: <br />
                                    + Thứ Hai đến Thứ Sáu: từ 08:00 - 17:30<br />
                                    + Thứ Bảy: từ 08:00 - 12:00<br />
                                    <p style={{ fontSize: 16 }}>(trừ các ngày nghỉ lễ, Tết)</p>
                                  </li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div className="border-column"></div>
                        {/* Yêu cầu hỗ trợ */}
                        <div className="lien-heform-right">
                          <div className="lien-heform-right-content">
                            <h2>Yêu cầu hỗ trợ</h2>
                            <div className="text">
                              Quý khách vui lòng điền đầy đủ thông tin sau. Chúng tôi sẽ liên lạc hỗ trợ Quý khách trong
                              thời gian sớm nhất.
                            </div>
                            <div className="lien-heform-right-content-input">
                              <div className="input special-input-extend expand-input">
                                <div className="input__content">
                                  <label for="">Số điện thoại*</label>
                                  <input type="search" name="phone" value={jsonState.contactForm.phone} maxLength="10"
                                    onChange={(event) => this.handlerChangeInput(event)}
                                    onBlur={(event) => this.handlerValidateInput(event)} />
                                </div>
                                <i><img src="/img/icon/input_phone.svg" alt="" /></i>
                              </div>
                              {errors.phone.length > 0 && <span style={{ color: 'red' }}>{errors.phone}</span>}
                              <div className="input special-input-extend expand-input">
                                <div className="input__content">
                                  <label for="">Email*</label>
                                  <input type="search" name="email" value={jsonState.contactForm.email} maxLength="50"
                                    onChange={(event) => this.handlerChangeInput(event)}
                                    onBlur={(event) => this.handlerValidateInput(event)} />
                                </div>
                                <i><img src="/img/icon/input_mail.svg" alt="" /></i>
                              </div>
                              {errors.email.length > 0 && <span style={{ color: 'red' }}>{errors.email}</span>}
                              <div className="input special-input-extend expand-input">
                                <div className="input__content">
                                  <label for="">Tỉnh/ Thành nơi bạn cư trú *</label>
                                  <input type="search" name="residence" value={jsonState.contactForm.residence} maxLength="150"
                                    onChange={(event) => this.handlerChangeInput(event)}
                                    onBlur={(event) => this.handlerValidateInput(event)} />
                                </div>
                                <i><img src="/img/icon/edit.svg" alt="" /></i>
                              </div>
                              {errors.residence.length > 0 && <span style={{ color: 'red' }}>{errors.residence}</span>}
                              <div className="input special-input-extend expand-input">
                                <div className="input__content">
                                  <label for="">Nhu cầu liên hệ *</label>
                                  <input type="search" name="contactDetail" value={jsonState.contactForm.contactDetail} maxLength="250"
                                    onChange={(event) => this.handlerChangeInput(event)}
                                    onBlur={(event) => this.handlerValidateInput(event)} />
                                </div>
                                <i><img src="/img/icon/edit.svg" alt="" /></i>
                              </div>
                              {errors.contactDetail.length > 0 && <span style={{ color: 'red' }}>{errors.contactDetail}</span>}
                            </div>
                            <div className="captcha-container" style={{ margin: '12px 0 12px 0' }}>
                              <div className="captcha-container-input">
                                <div className="input special-input-extend expand-input">
                                  <div className="input__content">
                                    <label for="" className="text-code">Mã bảo vệ *</label>
                                    <input type="search" name="captcha" value={jsonState.contactForm.captcha} maxLength="6"
                                      onChange={(event) => this.handlerChangeInput(event)}
                                      onBlur={(event) => this.handlerValidateInput(event)}
                                    />
                                  </div>
                                </div>
                              </div>
                              <div className="captcha-container-captcha">
                                <div className="capt-img">
                                  <LoadCanvasTemplate />
                                </div>
                                <div className="reload-icon" id="reload_href">
                                  <img src="/img/icon/reload-icon.svg" alt="reload" />
                                </div>
                              </div>
                            </div>
                            {errors.captcha.length > 0 && <span style={{ color: 'red', margin: '12px 0 0 0' }}>{errors.captcha}</span>}
                            <div className="force-text">* Thông tin bắt buộc</div>
                          </div>
                          <div className="bottom-btn">
                            {jsonState.isSubmitting ?
                              <LoadingIndicator area='contact-form-submit' /> :
                              <button className={jsonState.isValid ? "btn btn-primary" : "btn btn-primary disabled"}
                                disabled={!jsonState.isValid}
                                onClick={(event) => {
                                  if (event.target.className === "btn btn-primary") {
                                    this.handlerSubmitForm();
                                  }
                                }}>Gửi</button>

                            }
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  {(jsonState.hasError) && <PopupException hasError={jsonState.hasError} handlerClosePopup={this.handlerClosePopup} />}
                  {(jsonState.isSucceeded) && <PopupContactFormSucceeded isSucceeded={jsonState.isSucceeded} handlerClosePopup={this.handlerClosePopup} />}
                  <img className="decor-clip1" src="/img/icon/clip-small-single.svg" alt="" />
                  <img className="decor-clip2" src="/img/icon/clip-small-single.svg" alt="" />
                  <img className="decor-person" src="/img/person.png" alt="" />
                </div>
              </div>
            </div>
          </section>
        </div >
      </main >
    );
  }
}

export default Contact;
