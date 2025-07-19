import React, { Component } from "react";
import {Helmet} from "react-helmet";
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  AUTHENTICATION,
  PageScreen,
  FE_BASE_URL
} from "../constants";

import { CPSaveLog } from '../util/APIUtils';
import { getSession, getDeviceId, trackingEvent } from "../util/common";
// import { Helmet } from "react-helmet";

class CustomerNeedKnow extends Component {
  constructor(props) {
    super(props);

    this.state = {
      renderMeta: false
    };

  }

  componentDidMount() {
    window.location.href = FE_BASE_URL + '/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/khach-hang-can-biet.pdf';
    this.cpSaveLog(`Web_Open_${PageScreen.CUSTOMER_NEED_KNOW}`);
    trackingEvent(
      "Tiện ích",
      `Web_Open_${PageScreen.CUSTOMER_NEED_KNOW}`,
      `Web_Open_${PageScreen.CUSTOMER_NEED_KNOW}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.CUSTOMER_NEED_KNOW}`);
    trackingEvent(
      "Tiện ích",
      `Web_Close_${PageScreen.CUSTOMER_NEED_KNOW}`,
      `Web_Close_${PageScreen.CUSTOMER_NEED_KNOW}`,
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
    return (
      <>
      {this.state.renderMeta &&
        <Helmet>
          <title>Những điều khách hàng cần biết – Dai-ichi Life Việt Nam</title>
          <meta name="description"
                content="Tài liệu được cung cấp với mục đích mang đến một số thông tin hỗ trợ và hướng dẫn cần thiết để Quý khách tham khảo, tra cứu trong suốt quá trình tham gia bảo hiểm tại Dai-ichi Life Việt Nam."/>
          <meta name="keywords"
                content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Mạng lưới"/>
          <meta name="robots" content="noindex, nofollow"/>
          <meta property="og:type" content="website"/>
          <meta property="og:url" content={FE_BASE_URL + "/network"}/>
          <meta property="og:title" content="Những điều khách hàng cần biết – Dai-ichi Life Việt Nam"/>
          <meta property="og:description"
                content="Tài liệu được cung cấp với mục đích mang đến một số thông tin hỗ trợ và hướng dẫn cần thiết để Quý khách tham khảo, tra cứu trong suốt quá trình tham gia bảo hiểm tại Dai-ichi Life Việt Nam."/>
          <meta property="og:image"
                content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
          <link rel="canonical" href={FE_BASE_URL + "/khach-hang-can-biet"}/>
      </Helmet>
      }
      </>
    );
  }
}

export default CustomerNeedKnow;