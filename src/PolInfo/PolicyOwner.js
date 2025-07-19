import 'antd/dist/antd.min.css';
import React, { Component } from 'react';
import {
  FE_BASE_URL,
  ACCESS_TOKEN,
  AUTHENTICATION,
  CLIENT_ID,
  USER_LOGIN,
  CLIENT_PROFILE,
  PageScreen
} from '../constants';
import LoadingIndicator from '../common/LoadingIndicator2';
import { format } from 'date-fns';
import {getDeviceId, getSession, trackingEvent} from '../util/common';
import { CPSaveLog } from '../util/APIUtils';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';

class PolicyOwner extends Component {
  constructor(props) {
    super(props);

    this.state = {
      cliID: this.props.match.params.clientId,
      Address: '',
      DOB: '',
      OtherAddress: '',
      FullName: '',
      Gender: '',
      POID: '',
      CellPhone: '',
      Email: '',
      clientProfile: JSON.parse(getSession(CLIENT_PROFILE)),
      renderMeta: false
    }
  }
  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.LIST_POLICY_OWNER}`);
    trackingEvent(
        "Thông tin hợp đồng",
        `Web_Open_${PageScreen.LIST_POLICY_OWNER}`,
        `Web_Open_${PageScreen.LIST_POLICY_OWNER}`,
    );

  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.LIST_POLICY_OWNER}`);
    trackingEvent(
        "Thông tin hợp đồng",
        `Web_Close_${PageScreen.LIST_POLICY_OWNER}`,
        `Web_Close_${PageScreen.LIST_POLICY_OWNER}`,
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

  render() {
    const toTitleCase = str => str.split(" ").map(
      e => e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase()).join(" ");
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    if (!getSession(CLIENT_ID)) {
      return <Redirect to={{
        pathname: '/home'
      }} />;
    }
    return (
      <div>
        {this.state.renderMeta &&
          <Helmet>
            <title>Bên mua bảo hiểm – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/policyowner"} />
            <meta property="og:title" content="Bên mua bảo hiểm - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/policyowner"} />
          </Helmet>
        }
        <main className="logined" id="main-id">
          <div className="main-warpper basic-mainflex">
            <section className="sccard-warpper">
              <div className="card-warpper">
                <LoadingIndicator area="claim-li-list" />
                {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                  <div className="item">
                    <div className="card choosen" id={index}>
                      <div className="card__header">
                        <h4 className="basic-bold">{toTitleCase(item.FullName.toLowerCase())}</h4>
                        <p>Mã khách hàng: {item.ClientID}</p>
                        <p>Ngày sinh: {format(new Date(item.DOB), 'dd/MM/yyyy')}</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
              <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                <p>Tiếp tục</p>
                <i><img src="img/icon/arrow-left.svg" alt="" /></i>
              </div>
            </section>

            <section className="sccontract-warpper">
              <div className="breadcrums">
                <div className="breadcrums__item">
                  <p>Thông tin hợp đồng</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
                <div className="breadcrums__item">
                  <p>Bên mua bảo hiểm</p>
                  <p className='breadcrums__item_arrow'>></p>
                </div>
              </div>
              <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                <p>Chọn thông tin</p>
                <i><img src="img/icon/return_option.svg" alt="" /></i>
              </div>
              {this.state.clientProfile === null && (
                <div className="insurance">
                  <div className="empty">
                    <div className="icon">
                      <img src="img/no-data(6).svg" alt="no-data" />
                    </div>
                    <p style={{ paddingTop: '20px' }}>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
                  </div>
                </div>
              )}

              <div className="menu-list">
                <button className="menu-list__item active">
                  <p>THÔNG TIN CÁ NHÂN</p>
                </button>
              </div>

              {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                <div className="qlbh-wrapper" id="TTCN">

                  <div className="personal-infomation consulting-service">
                    <div className="form">
                      <div className="form__item">
                        <p>Ngày sinh</p>
                        <p>{format(new Date(item.DOB), 'dd/MM/yyyy')}</p>
                      </div>
                      <div className="form__item">
                        <p>Giới tính</p>
                        {item.Gender === 'M' ?
                          <p>Nam</p> : <p>Nữ</p>}
                      </div>
                      <div className="form__item">
                        <p>Số giấy tờ tùy thân</p>
                        <p>{item.POID}</p>
                      </div>
                      <div className="form__item">
                        <p>Điện thoại</p>
                        <p>{item.CellPhone}</p>
                      </div>
                      <div className="form__item">
                        <p>Email</p>
                        <p>{item.Email}</p>
                      </div>
                      <div className="form__item">
                        <p>Địa chỉ liên hệ 1</p>
                        <p>{item.Address}</p>
                      </div>
                      <div className="form__item">
                        <p>Địa chỉ liên hệ 2</p>
                        <p>{item.OtherAddress}</p>
                      </div>
                    </div>
                  </div>

                </div>
              ))}
            </section>
          </div>
        </main>
      </div>
    );
  }
}


export default PolicyOwner;