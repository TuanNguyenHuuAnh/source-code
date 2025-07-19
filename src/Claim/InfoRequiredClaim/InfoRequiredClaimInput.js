// import 'antd/dist/antd.min.css';
// import '../claim.css';
import React, { Component } from 'react';

import LoadingIndicator from '../../../src/common/LoadingIndicator-InfoRequiredClaim';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN } from '../../constants';
import {getSession} from '../util/common';
import { getInfoRequiredClaimList } from '../../util/APIUtils';
import { getDeviceId } from '../../util/common';


export const CLAIM_STATE = Object.freeze({
  INIT: 0,
  INIT_CLAIM: 1,
  CLAIM_TYPE: 2,
  CLAIM_DETAIL: 3,
  PAYMENT_METHOD: 4,
  ATTACHMENT: 5,
  SUBMIT: 6,
})


class InfoRequiredClaimInput extends Component {

  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Project: 'mcp',
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
        }
      },
      pinAttachment: [
        {
          imgData: "",
        },
      ],
      documentAttachment: [
        {
          imgData: "",
        },
      ],
      hospitalRecordAttachment: [
        {
          imgData: "",
        },
      ],
      billAttachment: [
        {
          imgData: "",
        },
      ],
    }

    this.handlerClickOnClaimCard = this.clickOnClaimCard.bind(this);
  }

  componentDidMount() {
    document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' })
  }

  updateMainState(subStateName, editedState) {
    var jsonState = this.state;
    jsonState[subStateName] = editedState;
    this.setState(jsonState);
    //console.log("CreateClaim - Updated main state:");
    //console.log(this.state);
  }

  clickOnClaimCard(index, item) {
  }

  render() {
    var data = this.props.infoRequiredClaimData;
    return (
      <section className="sccontract-warpper additional-information-sccontract-warpper" id="scrollAnchor">
        <div className="breadcrums">
          <div className="breadcrums__item">
            <p>Yêu cầu quyền lợi</p>
            <span>&gt;</span>
          </div>
          <div className="breadcrums__item">
            <p>Bổ sung thông tin</p>
            <span>&gt;</span>
          </div>
        </div>
        <div className="sccontract-container">
          <div className="warning-wrapper">
            <div className="icon-wrapper">
              <img src="img/icon/2.2/2.2-icon-signal.svg" alt="" />
            </div>
            <div className="content">
              <p>
                Vui lòng bổ sung Giấy tờ/Chứng từ còn thiếu/chưa hợp lệ trước ngày
                <span className="light-brown-text">{' ' + (data.lstClaimSubmission[0].lstDocTypeHold[0]?data.lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate:'') + ' '}</span>
                để chúng tôi thẩm định yêu cầu quyền lợi này và phản hồi kết quả đến Quý khách.
              </p>
            </div>
          </div>
          <div className="stepform form-wrapper local-form-wrapper">
            {/* Giấy tờ/chứng từ đính kèm */}
            <div className="stepform__body">
              {/* CMND/CCCD */}
              <div className="info">
                <div className="info__title">
                  <h4>Giấy tờ/chứng từ đính kèm</h4>
                  {/* <!-- <i className="invoke-hoso"><img src="img/icon/step/step_3_info.svg" alt=""/></i> --> */}
                </div>
                <div className="info__subtitle">
                  {/* <p className="basic-semibold">{data.}</p> */}
                </div>
                <div className="info__body">
                  <div className="item">
                    <div className="item__content">
                      <div className="img-upload-wrapper">
                        <div className="img-upload-item">
                          <div className="img-upload">
                            <button className="circle-plus">
                              <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                            </button>
                            <p className="basic-grey">
                              Kéo & thả tệp tin hoặc
                              <span className="basic-red basic-semibold">chọn tệp</span>
                            </p>

                            <input className="inputfile" type="file" multiple hidden />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  {/* CMND/CCCD - Phản hồi từ Dai-ichi Việt Nam */}
                  <div className="item">
                    <h5 className="item__title">* Phản hồi từ Dai-ichi Việt Nam</h5>
                    <h5 className="item__title">Quý khách có mắc bệnh tiểu đường hay không?</h5>
                    <div className="item__content">
                      <div className="tab">
                        <div className="tab__content">
                          <div className="input textarea">
                            <div className="input__content">
                              <label>Câu trả lời</label>
                              <textarea className='eclaim-text-area'
                                placeholder="Ví dụ: Mất chứng từ ghi nhận sự kiện bảo hiểm"
                                rows="4"
                              ></textarea>
                            </div>
                            <i><img src="img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              {/* Chứng từ bảo hiểm */}
              <div className="info">
                <div className="info__subtitle">
                  <p className="basic-semibold">Chứng từ bảo hiểm</p>
                </div>
                <div className="info__body">
                  <div className="item">
                    <div className="item__content">
                      <div className="img-upload-wrapper">
                        <div className="img-upload-item">
                          <div className="img-upload">
                            <button className="circle-plus">
                              <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                            </button>
                            <p className="basic-grey">
                              Kéo & thả tệp tin hoặc
                              <span className="basic-red basic-semibold">chọn tệp</span>
                            </p>

                            <input className="inputfile" type="file" multiple hidden />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              {/* Giấy tờ nằm viện/điều trị */}
              <div className="info">
                <div className="info__subtitle">
                  <p className="basic-semibold">Giấy tờ nằm viện/điều trị</p>
                </div>
                <div className="info__body">
                  <div className="item">
                    <div className="item__content">
                      <div className="img-upload-wrapper">
                        <div className="img-upload-item">
                          <div className="img-upload">
                            <button className="circle-plus">
                              <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                            </button>
                            <p className="basic-grey">
                              Kéo & thả tệp tin hoặc
                              <span className="basic-red basic-semibold">chọn tệp</span>
                            </p>

                            <input className="inputfile" type="file" multiple hidden />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              {/* Hoá đơn thanh toán */}
              <div className="info">
                <div className="info__subtitle">
                  <p className="basic-semibold">Hoá đơn thanh toán</p>
                </div>
                <div className="info__body">
                  <div className="item">
                    <div className="item__content">
                      <div className="img-upload-wrapper">
                        <div className="img-upload-item">
                          <div className="img-upload">
                            <button className="circle-plus">
                              <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                            </button>
                            <p className="basic-grey">
                              Kéo & thả tệp tin hoặc
                              <span className="basic-red basic-semibold">chọn tệp</span>
                            </p>

                            <input className="inputfile" type="file" multiple hidden />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  {/* Hoá đơn thanh toán - Phản hồi từ Dai-ichi Việt Nam */}
                  <div className="item">
                    <h5 className="item__title">* Phản hồi từ Dai-ichi Việt Nam</h5>
                    <h5 className="item__title">Chứng từ không hợp lệ, vui lòng gửi lại?</h5>
                    <div className="item__content">
                      <div className="tab">
                        <div className="tab__content">
                          <div className="input textarea">
                            <div className="input__content">
                              <label>Câu trả lời</label>
                              <textarea
                                placeholder="Ví dụ: Mất chứng từ ghi nhận sự kiện bảo hiểm"
                                rows="10"
                              ></textarea>
                            </div>
                            <i><img src="img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              {/* Phản hồi từ Dai-ichi Việt Nam */}
              <div className="info">
                <div className="info__body">
                  <div className="item">
                    <h5 className="item__title">* Phản hồi từ Dai-ichi Việt Nam</h5>
                    <h5 className="item__title">Quý khách có bị ho/ sốt trong vòng 14 ngày gần nhất không?</h5>
                    <div className="item__content">
                      <div className="tab">
                        <div className="tab__content">
                          <div className="input textarea">
                            <div className="input__content">
                              <label>Câu trả lời</label>
                              <textarea
                                placeholder="Ví dụ: Mất chứng từ ghi nhận sự kiện bảo hiểm"
                                rows="10"
                              ></textarea>
                            </div>
                            <i><img src="img/icon/edit.svg" alt="" /></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <img className="decor-clip" src="img/mock.svg" alt="" />
              <img className="decor-person" src="img/person.png" alt="" />
            </div>
          </div>

          <div className="bottom-text">
            <p>Bạn có thắc mắc? <span>Liên hệ tư vấn</span></p>
          </div>

          <div className="bottom-btn">
            <button className="btn btn-primary disabled">Gửi bổ sung</button>
          </div>
        </div>
      </section>
    );

  };

}

export default InfoRequiredClaimInput;
