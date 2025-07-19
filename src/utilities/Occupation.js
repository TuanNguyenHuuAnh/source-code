// import 'antd/dist/antd.css';
// import '../claim.css';
import React, { Component } from "react";

import LoadingIndicator from '../../src/common/LoadingIndicator2';

import {isLoggedIn, getSession, getDeviceId, trackingEvent} from "../util/common";

import { getOccupationList, CPSaveLog } from '../util/APIUtils';
import {FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen} from '../constants';
import { Helmet } from "react-helmet";
import { Link } from "react-router-dom";


class Occupation extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          OccupationSearch: '',
          LangID: 'VN'
        }
      },
      occupList: [],
      hasOccup: "",
      searchInput: "",
      searching: false,
      renderMeta: false
    };

    this.handlerSearch = this.search.bind(this);
    this.handlerOnChangeSearchInput = this.onChangeSearchInput.bind(this);
  }

  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.OCCUPATION}`);
    trackingEvent(
        "Tiện ích",
        `Web_Open_${PageScreen.OCCUPATION}`,
        `Web_Open_${PageScreen.OCCUPATION}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.OCCUPATION}`);
    trackingEvent(
        "Tiện ích",
        `Web_Close_${PageScreen.OCCUPATION}`,
        `Web_Close_${PageScreen.OCCUPATION}`,
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
    jsonState.hasOccup = "";
    this.setState(jsonState);

    if (!jsonState.searching) {
      jsonState.searching = true;
      this.setState(jsonState);

      var apiRequest = JSON.parse(JSON.stringify(this.state.jsonInput));
      apiRequest.jsonDataInput.OccupationSearch = jsonState.searchInput;
      getOccupationList(apiRequest).then(Res => {
        jsonState.searching = false;
        if (Res.Response.Result === 'true' && Res.Response.ErrLog === 'SUCCESSFUL'
          && Res.Response.OccupationDTO !== null && Res.Response.OccupationDTO !== undefined && Res.Response.OccupationDTO.length > 0) {
          jsonState.occupList = Res.Response.OccupationDTO;
          jsonState.hasOccup = "Y";
        } else {
          jsonState.hasOccup = "N";
        }
        this.setState(jsonState);
      }).catch(error => {
        jsonState.hasOccup = "N";
        this.setState(jsonState);
      });
    }
  }

  render() {
    var jsonState = this.state;
    var occupList = jsonState.occupList;
    var hasOccup = jsonState.hasOccup;
    return (
      <main className={isLoggedIn() ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Phân nhóm nghề – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Trang thông tin về phân nhóm nghề của khách hàng nhằm hỗ trợ cho việc chọn lựa Hợp đồng bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Phân nhóm nghề" />
            <meta name="robots" content="index, follow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/utilities/occupation"} />
            <meta property="og:title" content="Phân nhóm nghề - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Trang thông tin về phân nhóm nghề của khách hàng nhằm hỗ trợ cho việc chọn lựa Hợp đồng bảo hiểm từ Dai-ichi Life Việt Nam." />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/utilities/occupation"} />
          </Helmet>
        }
        <div className="main-warpper">
          {/* Header */}
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
                  <p>Phân nhóm nghề</p>
                  <span>&gt;</span>
                </div>
              </div>
            </div>
          </section>
          <section className="scdieukhoan">
            <div className="container">
              <h1>Phân nhóm nghề</h1>
            </div>
          </section>

          {/* Search bar */}
          <form onSubmit={this.handlerSearch}>
            <section className="searching-component nghenghiep-searching">
              <div className="common-searchbar">
                <div className="search-bar">
                  <div className="input">
                    <div className="input__content">
                      <input placeholder="Nhập nghề nghiệp của bạn" type="search" maxLength="200"
                        value={jsonState.searchInput} onChange={(event) => this.handlerOnChangeSearchInput(event)} />
                    </div>
                    {jsonState.searchInput === null || jsonState.searchInput === undefined || jsonState.searchInput.length === 0 ?
                      <i className="icon"><img src="/img/icon/Search.svg" alt="search" /></i> : ""}
                    {/* <i className="close-autocomplete"><img src="img/icon/close-icon.svg" alt="close" className="close-i" /></i> */}
                  </div>
                  {/* <div className="searchbar-autocomplete">
                    <div className="searchbar-autocomplete-tab">
                      <p>Làm sao để nhận các <span className="simple-brown">phần quà</span> từ chương trình Gắn bó dài lâu?</p>
                      <div className="icon-arrow-right"><img src="img/icon/arrow-down.svg" alt="" /></div>
                    </div>
                    <div className="searchbar-autocomplete-tab">
                      <p>Làm sao để nhận các <span className="simple-brown">phần quà</span> từ chương trình Gắn bó dài lâu?</p>
                      <div className="icon-arrow-right"><img src="img/icon/arrow-down.svg" alt="" /></div>
                    </div>
                  </div> */}
                </div>
                <button
                  className={jsonState.searchInput === null || jsonState.searchInput === undefined
                    || jsonState.searchInput.length === 0 || jsonState.searching ?
                    "searchbtn btn btn-primary disabled" : "searchbtn btn btn-primary"}
                  disabled={jsonState.searchInput === null || jsonState.searchInput === undefined
                    || jsonState.searchInput.length === 0 || jsonState.searching}>
                  Tìm kiếm
                </button>
              </div>
            </section>
          </form>

          {/* Result */}
          <LoadingIndicator area='occup-loading-area' />
          <section className="searching-result">
            {hasOccup === 'Y' &&
              <>
                <h3>Kết quả tìm kiếm</h3>
                <h3 style={{ 'margin-top': '6px' }}>Thông tin tìm kiếm: {jsonState.searchInput}</h3>
                <div className="searching-result-wrapper">
                  <div className="result-table">
                    <div className="result-table__item table-header">
                      <div className="col2" style={{ 'border-right': '1px solid #e5e5e5' }}>
                        <p>Nhóm nghề</p>
                      </div>
                      <div className="col1" style={{ 'border-right': 'none' }}>
                        <p>Nhóm</p>
                      </div>
                    </div>
                    {occupList.map((occup, index) => (
                      <div key={index} className="result-table__item">
                        <div className="col2" style={{ 'border-right': '1px solid #e5e5e5' }}>
                          <p>{occup && occup.length > 2 && occup[1]}</p>
                        </div>
                        <div className="col1" style={{ 'border-right': 'none' }}>
                          <p>{occup && occup.length > 2 && occup[2]}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </>
            }
            {hasOccup === 'N' &&
              // <div className="no-result" style={{ 'text-align': 'center' }}>
              <div className="no-result">
                <p>Rất tiếc! Chúng tôi không tìm thấy kết quả. Bạn vui lòng nhập lại!</p>
              </div>
            }
          </section>
        </div>
      </main >
    );
  }
}

export default Occupation;
