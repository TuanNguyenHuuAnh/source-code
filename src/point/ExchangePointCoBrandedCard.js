import React, { Component } from 'react';
import { FE_BASE_URL, ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN } from '../constants';
import '../common/Common.css';
import { Link } from 'react-router-dom';
import { getDeviceId, getSession } from '../util/common';
import { CPSaveLog } from '../util/APIUtils';
import { Helmet } from "react-helmet";

class ExchangePointCoBrandedCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      renderMeta: false
    }
  };

  componentDidMount() {
    this.cpSaveLog("Thẻ đồng thương hiệu");
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
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    return (
      <main className={logined ? "logined" : ""}>
        {this.state.renderMeta &&
          <Helmet>
            <title>Thẻ đồng thương hiệu – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/category/6"} />
            <meta property="og:title" content="Thẻ đồng thương hiệu - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/category/6"} />
          </Helmet>
        }
        <div className="scpointchange">
          <div className="breadcrums">
            <div className="breadcrums__item">
              <p>Trang chủ</p>
              <p className='breadcrums__item_arrow'>></p>
            </div>
            <div className="breadcrums__item">
              <p><Link to="/point" className='breadcrums__link'>Điểm thưởng</Link></p>
              <p className='breadcrums__item_arrow'>></p>
            </div>
            <div className="breadcrums__item">
              <p><Link to="/point-exchange" className='breadcrums__link'>Đổi điểm thưởng</Link></p>
              <p className='breadcrums__item_arrow'>></p>
            </div>
            <div className="breadcrums__item">
              <p>Thẻ đồng thương hiệu</p>
              <p className='breadcrums__item_arrow'>></p>
            </div>
          </div>
          <section className="scquydautu">
            <div className="container">
              <div className="wrapper">
                <div className="quydautu">
                  <h4>Thẻ đồng thương hiệu</h4>
                  <div className="quydautu__form">
                    <div className="form-tab-wrapper">
                      <div className="form-tab">
                        <h5 className="basic-semibold">Thẻ quà tặng đồng thương hiệu DLVN & HDBank là Thẻ trả trước định danh nội địa với các tính năng nổi trội:</h5>
                      </div>

                      <div className="form-tab">
                        <div className="greytab-wrapper">
                          <div className="item">
                            <div className="greytab">
                              <i className="icon"><img src="../img/icon/9.1/9.1-coin-form.svg" alt="flower-icon" /></i>
                              <p className="minheight40">Giao dịch thanh toán</p>
                            </div>
                          </div>
                          <div className="item">
                            <div className="greytab">
                              <i className="icon"><img src="../img/icon/9.1/9.1-atm-form.svg" alt="atom-icon" /></i>
                              <p className="minheight40">Giao dịch rút tiền mặt</p>
                            </div>
                          </div>
                          <div className="item">
                            <div className="greytab">
                              <i className="icon"><img src="../img/icon/9.1/9.1-bank-form.svg" alt="hand-icon" /></i>
                              <p className="minheight60">Giao dịch nạp tiền vào Thẻ tại quầy giao dịch của HDBank.</p>
                            </div>
                          </div>
                          <div className="item">
                            <div className="greytab">
                              <i className="icon"><img src="../img/icon/9.1/9.1-discount-form.svg" alt="surface-icon" /></i>
                              <p className="minheight60">
                                Thỏa thích mua sắm được giảm giá đến 50% cùng Thẻ tại hơn 300 điểm ưu đãi liên kết với
                                HDBank
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="form-tab">
                        <p className="basic-light">Khi cần trao đổi thêm thông tin, Quý khách vui lòng liên hệ:</p>
                        <div className="list">
                          <p>Văn phòng và Trung tâm Dịch vụ khách hàng hoặc</p>
                          <p>
                            Tổng đài Dịch vụ Dai-ichi Life Việt Nam - (028) 3810 0888: bấm phím 1
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </div>
      </main>)
  }
}

export default ExchangePointCoBrandedCard;
