// import 'antd/dist/antd.css';
import './faq.css';

import React, { Component } from "react";

import LoadingIndicator from '../../src/common/LoadingIndicator2';

import {getDeviceId, getSession, isLoggedIn, trackingEvent} from "../util/common";

import {CPSaveLog, getFaq} from '../util/APIUtils';
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, FE_BASE_URL, PageScreen, USER_LOGIN} from '../constants';
import { Helmet } from "react-helmet";
import { Link } from "react-router-dom";


class Faq extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        Action: "GetFAQ",
        Data: {
          isOnlyItem: false,
          keyword: ""
        }
      },
      faqList: [],
      hasFaq: "",
      searchInput: "",
      currentTab: "",
      currentQuestion: "",
      renderMeta: false
    };

    this.handlerSearch = this.search.bind(this);
    this.handlerOnChangeSearchInput = this.onChangeSearchInput.bind(this);
    this.handlerOnChangeCategory = this.onChangeCategory.bind(this);
    this.handlerOnChangeFaq = this.onChangeFaq.bind(this);
  }

  componentDidMount() {
    const apiRequest = Object.assign({}, this.state.jsonInput);
    getFaq(apiRequest).then(Res => {
      if (Res.data !== null) {
        var jsonState = this.state;
        jsonState.faqList = Res.data;
        jsonState.currentTab = "";
        jsonState.currentQuestion = "";
        if (jsonState.faqList !== null && jsonState.faqList !== undefined && jsonState.faqList.length > 0 &&
          jsonState.faqList.findIndex(elem =>
            Object.entries(elem).filter(
              ([k, v]) => k === 'children' && v !== null && v !== undefined && v.length > 0).length > 0
          ) >= 0) {
          jsonState.hasFaq = "Y";
        }
        this.setState(jsonState);
      }
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
    this.cpSaveLog(`Web_Open_${PageScreen.FAQ}`);
    trackingEvent(
        "Tiện ích",
        `Web_Open_${PageScreen.FAQ}`,
        `Web_Open_${PageScreen.FAQ}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.FAQ}`);
    trackingEvent(
        "Tiện ích",
        `Web_Close_${PageScreen.FAQ}`,
        `Web_Close_${PageScreen.FAQ}`,
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

  onChangeSearchInput(event) {
    var jsonState = this.state;
    jsonState.searchInput = event.target.value;
    this.setState(jsonState);
  }

  search(event) {
    event.preventDefault();

    var jsonState = this.state;
    jsonState.hasFaq = "";
    this.setState(jsonState);

    var apiRequest = JSON.parse(JSON.stringify(this.state.jsonInput));
    apiRequest.Data.keyword = jsonState.searchInput;
    getFaq(apiRequest).then(Res => {
      if (Res.error === 0 && Res.message === 'Success' && Res.data !== null) {
        jsonState.faqList = Res.data;
        if (jsonState.faqList !== null && jsonState.faqList !== undefined && jsonState.faqList.length > 0 &&
          jsonState.faqList.findIndex(elem =>
            Object.entries(elem).filter(
              ([k, v]) => k === 'children' && v !== null && v !== undefined && v.length > 0).length > 0
          ) >= 0) {
          jsonState.hasFaq = "Y";
        } else {
          jsonState.hasFaq = "N";
        }
        if (jsonState.searchInput === null || jsonState.searchInput === undefined || jsonState.searchInput === '') {
          jsonState.currentTab = "";
          jsonState.currentQuestion = "";
        } else {
          var firstFaqFound = jsonState.faqList !== null && jsonState.faqList !== undefined && jsonState.faqList.length > 0 &&
            jsonState.faqList.findIndex(elem =>
              Object.entries(elem).filter(
                ([k, v]) => k === 'children' && v !== null && v !== undefined && v.length > 0).length > 0
            );
          jsonState.currentTab = firstFaqFound >= 0 ? jsonState.faqList[firstFaqFound].id : '';
          jsonState.currentQuestion = "";
        }
        this.setState(jsonState);
      }
    }).catch(error => {
      jsonState.hasFaq = "N";
    });
  }

  onChangeCategory(categoryId) {
    var jsonState = this.state;
    jsonState.currentTab = jsonState.currentTab === categoryId ? '' : categoryId;
    jsonState.currentQuestion = "";
    this.setState(jsonState);
    //console.log(this.state);
  }

  onChangeFaq(faqId) {
    var jsonState = this.state;
    jsonState.currentQuestion = jsonState.currentQuestion === faqId ? '' : faqId;
    this.setState(jsonState);
    //console.log(this.state);
  }


  render() {
    var jsonState = this.state;
    var faqList = jsonState.faqList;
    var hasFaq = jsonState.hasFaq;
    return (
      <main className={isLoggedIn() ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Câu hỏi thường gặp – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Các câu hỏi thường gặp liên quan đến các sản phẩm bảo hiểm, giải quyết quyền lợi của khách hàng tại Dai-ichi Life Việt Nam." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Câu hỏi thường gặp" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities/faq"} />
            <meta property="og:title" content="Câu hỏi thường gặp - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Các câu hỏi thường gặp liên quan đến các sản phẩm bảo hiểm, giải quyết quyền lợi của khách hàng tại Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities/faq"} />
          </Helmet>
        }
        <div className="main-warpper">
          <section className="scbreadcrums">
            <div className="container">
              <div className="breadcrums basic-white">
                <Link to="/" className="breadcrums__item">
                    <p>Trang chủ</p>
                    <p className='breadcrums__item_arrow'>></p>
                </Link>
                <Link to="/utilities" className="breadcrums__item">
                    <p>Tiện ích</p>
                    <p className='breadcrums__item_arrow'>></p>
                </Link>
                <div className="breadcrums__item">
                  <p>Câu hỏi thường gặp</p>
                  <span>&gt;</span>
                </div>
              </div>
            </div>
          </section>
          <section className="scfaq-title">
            <div className="container">
              <h1>Câu hỏi thường gặp</h1>
            </div>
          </section>

          <form onSubmit={this.handlerSearch}>
            <section className="scsearching">
              <div className="container">
                <div className="faq-search">
                  <div className="common-searchbar">
                    <div className="search-bar">
                      <div className="input">
                        <div className="input__content">
                          <input placeholder="Nhập thông tin tìm kiếm" type="search" maxLength="200"
                            value={jsonState.searchInput} onChange={(event) => this.handlerOnChangeSearchInput(event)} />
                        </div>
                        {jsonState.searchInput === null || jsonState.searchInput === undefined || jsonState.searchInput.length === 0 ?
                          <i className="icon"><img src="/img/icon/Search.svg" alt="search" /></i> : ""}
                        <i className="close-autocomplete"><img src="/img/icon/close-icon.svg" alt="close" className="close-i" /></i>
                      </div>
                      {/* <div className="searchbar-autocomplete">
                      <div className="searchbar-autocomplete-tab">
                        <p>Làm sao để nhận các <span className="simple-brown">phần quà</span> từ chương trình Gắn bó dài lâu?</p>
                        <div className="icon-arrow-right"><img src="/img/icon/arrow-down.svg" alt="" /></div>
                      </div>
                      <div className="searchbar-autocomplete-tab">
                        <p>Làm sao để nhận các <span className="simple-brown">phần quà</span> từ chương trình Gắn bó dài lâu?</p>
                        <div className="icon-arrow-right"><img src="/img/icon/arrow-down.svg" alt="" /></div>
                      </div>
                    </div> */}
                    </div>
                    <button
                      className={jsonState.searchInput === null || jsonState.searchInput === undefined || jsonState.searchInput.length === 0 ?
                        "searchbtn btn btn-primary disabled" : "searchbtn btn btn-primary"}
                      disabled={jsonState.searchInput === null || jsonState.searchInput === undefined || jsonState.searchInput.length === 0}>
                      Tìm kiếm
                    </button>
                  </div>
                </div>
              </div>
            </section>
          </form>

          <LoadingIndicator area='faq-loading-area' />
          {hasFaq === 'Y' &&
            <section className="scfaq">
              <div className="container">
                <div className="faq-wrapper">
                  <div className="faq-header">
                    <div className="question-section">
                      {faqList.map((category, index) => (
                        <div key={index} className="question-section-item" id={"category-" + category.id}
                          onClick={() => this.handlerOnChangeCategory(category.id)}>
                          <div className={category.id === jsonState.currentTab ? "question-item-card active" : "question-item-card"}>
                            <button><h2>{category.name}</h2></button>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>

                  <div className="faq-body">
                    {/* Categories */}
                    {faqList !== null && faqList !== undefined && faqList.map((category, index) => (
                      <div className={category.id === jsonState.currentTab ? "faq-body-tab active" : "faq-body-tab"} >
                        {/* FAQs */}
                        <div className="faq-tab-wrapper">
                          {category.children !== null && category.children !== undefined && category.children.map((faq, idx) => (
                            <div key={idx} className={faq.id === jsonState.currentQuestion ? "dropdown faq-tab show" : "dropdown faq-tab"}
                              id={"category-" + category.id + "-faq-" + faq.id}>
                              {/* Question */}
                              <div className="dropdown__content" onClick={() => this.handlerOnChangeFaq(faq.id)}>
                                <div className="faq-content">
                                  <p>
                                    {faq.title}
                                  </p>
                                  <i><img src="/img/icon/arrow-down.svg" alt="" /></i>
                                </div>
                              </div>
                              {/* Answer */}
                              <div className="dropdown__items">
                                <div className="faq-dropdown-content" dangerouslySetInnerHTML={{ __html: faq.content }}>
                                </div>
                              </div>
                            </div>
                          ))}
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </section>
          }
          {hasFaq === 'N' &&
            <section className="scfaq-search-nodata">
              <div className="container">
                <div className="faq-search-nodata">
                  <div className="/img-wrapper">
                    <img src="/img/icon/10/search-nodata.svg" alt="" />
                  </div>
                  <p>Không tìm thấy kết quả bạn cần. Hãy thử lại từ khoá khác nhé!</p>
                </div>
              </div>
            </section>
          }
        </div>
      </main >
    );
  }
}

export default Faq;
