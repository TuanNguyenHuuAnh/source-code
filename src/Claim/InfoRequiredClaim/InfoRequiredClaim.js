// import 'antd/dist/antd.min.css';
// import '../claim.css';
import React, { Component } from 'react';
import {showMessage, getSession} from '../util/common';
import LoadingIndicator from '../../../src/common/LoadingIndicator-InfoRequiredClaim';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, COMPANY_KEY } from '../../constants';

import { getInfoRequiredClaimList, logoutSession } from '../../util/APIUtils';


import InfoRequiredClaimInput from './InfoRequiredClaimInput';
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


class InfoRequiredClaim extends Component {

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

      infoRequiredClaimList: null,

      selectedClaimCardIdx: '',
    }

    this.handlerClickOnClaimCard = this.clickOnClaimCard.bind(this);
  }

  componentDidMount() {
    const apiRequest = Object.assign({}, this.state.jsonInput);
    getInfoRequiredClaimList(apiRequest).then(Res => {
      let Response = Res.Response;
      var jsonState = this.state;
      if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
        jsonState.infoRequiredClaimList = Response.ClientProfile;
        this.setState(jsonState);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false}
  
        })
  
      }
      }).catch(error => {
        //this.props.history.push('/maintainence');
    });
  }

  updateMainState(subStateName, editedState) {
    var jsonState = this.state;
    jsonState[subStateName] = editedState;
    this.setState(jsonState);
    //console.log("CreateClaim - Updated main state:");
    //console.log(this.state);
  }

  clickOnClaimCard(index, item) {
    const jsonState = this.state;
    jsonState.selectedClaimCardIdx = index;
    jsonState.infoRequiredClaimList.forEach((client, i) => {
      if (i === index) {
        document.getElementById('inforReqClaimcard' + i).className = "card choosen";
      } else {
        document.getElementById('inforReqClaimcard' + i).className = "card";
      }
    });
    this.setState(jsonState);
  }

  render() {
    var jsonState = this.state;

    var infoRequiredClaimList = jsonState.infoRequiredClaimList;
    const goBack=()=> {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    return (
      <>
        <LoadingIndicator area="info-required-claim-initial" />
        {(infoRequiredClaimList !== null && infoRequiredClaimList !== undefined && infoRequiredClaimList.length > 0) &&
          // Initial
          <main className="logined additonal-information" id="main-id">
            <div className="main-warpper basic-mainflex">
              {/* Claim list */}
              <section className="qlbh-sccard-wrapper sccard-warpper hopdong-sccard-wrapper">
                <div className="title">
                  <h4 className="basic-bold">Vui lòng chọn yêu cầu cần bổ sung:</h4>
                </div>
                <div className="card-warpper">
                  {infoRequiredClaimList.map((claimData, index) => (
                    <div className="item" onClick={() => this.handlerClickOnClaimCard(index, claimData)}>
                      <div className="card" id={"inforReqClaimcard" + index}>
                        <div className="card__header">
                          <div className="card__header-item">
                            <div className="calendar-wrapper">
                              <img src="img/icon/Calendar.svg" alt="" />
                              <p>{claimData.lstClaimSubmission && claimData.lstClaimSubmission[0].DateSubmission}</p>
                            </div>
                          </div>
                          <div className="choose">
                            <div className="dot"></div>
                          </div>
                          <div className="status additional-status-custom">
                            <p className="active">{claimData.StatusVN}</p>
                          </div>
                        </div>
                        <div className="card__footer">
                          {claimData.lstClaimSubmission.map((claimSubmission, idx) => (
                            <>
                              <div className="card__footer-item">
                                <p>Mã yêu cầu</p>
                                <p>{claimSubmission.ClaimID}</p>
                              </div>
                              <div className="card__footer-item">
                                <p>Hợp đồng</p>
                                <p>{claimSubmission.PolicyNo}</p>
                              </div>
                              <div className="card__footer-item">
                                <p>Người được bảo hiểm</p>
                                <p>{claimSubmission.LIFullName}</p>
                              </div>
                              <div className="card__footer-item">
                                <p>Sự kiện bảo hiểm</p>
                                <p>{claimSubmission.ClaimType}</p>
                              </div>
                              <div className="card__footer-item">
                                <div className="dash"></div>
                              </div>
                              <div className="card__footer-item">
                                {/* <div className="dropdown"> */}
                                <div className="dropdown" id={'card' + index + '_claimSub' + idx} onClick={(e) => {
                                  document.getElementById('card' + index + '_claimSub' + idx).className === 'dropdown' ?
                                    document.getElementById('card' + index + '_claimSub' + idx).className = 'dropdown show' :
                                    document.getElementById('card' + index + '_claimSub' + idx).className = 'dropdown'
                                }}>
                                  <div className="dropdown__content">
                                    <p className="primary-text basic-semibold">Xem chi tiết cần bổ sung</p>
                                    <p className="alternative-text basic-semibold">Thu gọn</p>
                                    <div className="arrow">
                                      <img src="img/icon/arrow-down-bronw.svg" alt="" />
                                    </div>
                                  </div>
                                  <div className="dropdown__items">
                                    <div className="content-wrapper">
                                      {claimSubmission.lstDocTypeHold.map((doctypeHold, idx) => (
                                        <div className="item">
                                          <div className="list-style"></div>
                                          <div className="content">
                                            <p>{doctypeHold.DocTypeName}</p>
                                          </div>
                                        </div>
                                      ))}
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </>
                          ))}
                        </div>
                        <div className="card__bottom">
                          <p>
                            Vui lòng bổ sung chứng từ trước ngày
                            <span className="red-text">{claimData.lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate}</span>
                          </p>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </section>
              {/* Input area */}
              {(jsonState.selectedClaimCardIdx !== null &&
                jsonState.selectedClaimCardIdx !== undefined &&
                jsonState.selectedClaimCardIdx !== '') ?
                <InfoRequiredClaimInput
                  infoRequiredClaimData={infoRequiredClaimList[jsonState.selectedClaimCardIdx]} /> :
                <section className="sccontract-warpper additional-information-sccontract-warpper">
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
                  <div className="other_option" id="other-option-toggle" onClick={()=>goBack()}>
                    <p>Chọn thông tin</p>
                    <i><img src="img/icon/return_option.svg" alt="" /></i>
                  </div>
                  <div className="sccontract-container">
                  <div className="insurance">
                    <div className="empty">
                      <div className="icon">
                        <img src="img/icon/empty.svg" alt="" />
                      </div>
                      <h4 className="basic-semibold">Chưa có dữ liệu</h4>
                      <p>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
                    </div>
                  </div>
                </div>
                </section>
              }
            </div>
          </main>
        }
        {(infoRequiredClaimList !== null && infoRequiredClaimList !== undefined && infoRequiredClaimList.length === 0) &&
          // No data
          <main className="logined additonal-information-nodata">
            <div className="main-warpper basic-mainflex">
              <section className="sccontract-warpper additional-information-sccontract-warpper">
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
                <div className="insurance">
                  <div className="empty">
                    <div className="icon">
                      <img src="img/2.2-nodata-image.png" alt="" />
                    </div>
                    <h4>Hiện tại không có yêu cầu nào đang xử lý</h4>
                  </div>
                </div>
              </section>
            </div>
          </main>
        }
      </>
    );

  };

}

export default InfoRequiredClaim;
