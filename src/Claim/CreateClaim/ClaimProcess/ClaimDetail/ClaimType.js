import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION, COMPANY_KEY, FE_BASE_URL } from '../../../../constants';
import { getDeviceId, getSession, isCheckedOnlyAccident, isCheckedOnlyIllness } from '../../../../util/common';
import { getLiBenefit } from '../../../../util/APIUtils';
import { CLAIM_STATE } from '../../CreateClaim';
import LoadingIndicator from '../../../../common/LoadingIndicator2';

class ClaimType extends Component {

  constructor(props) {
    super(props);
    this.state = {
      stepName: CLAIM_STATE.CLAIM_TYPE,
      claimTypeList: null
    }
  }


  componentDidMount() {
    document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' });
    this.loadClientBenifit();
  }

  loadClientBenifit() {
    // Check benefits
    const apiRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        APIToken: getSession(ACCESS_TOKEN),
        Project: 'mcp',
        Action: 'LifeInsuredBenefit',
        LifeInsuredID: this.props.selectedCliID,
        UserLogin: getSession(USER_LOGIN),
        ClientID: getSession(CLIENT_ID),
      }
    }
    getLiBenefit(apiRequest).then(Res => {
      let Response = Res.Response;
      if (Response.Result === 'true' && Response.ClientProfile !== null && Response.ClientProfile !== undefined) {
        this.props.loadClaimTypeList(Response.ClientProfile);
        this.setState({ claimTypeList: Response.ClientProfile });
      } else {
        document.getElementById('step-start-popup').className = "popup step-start-popup show";
        return;
      }
    }).catch(error => {
    });
  }

  render() {
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    const claimTypeState = this.props.claimTypeState;
    const claimCheckedMap = this.props.claimCheckedMap;

    return (
      <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance claim-type">
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
            <div className="other_option" id="other-option-toggle" onClick={()=>goBack()}>
              <p>Chọn thông tin</p>
              <i><img src="../../img/icon/return_option.svg" alt="" /></i>
            </div>
            <div className="heading__tab">
              <div className="step-container">
                <div className="step-wrapper">
                  <div className="step-btn-wrapper">
                    <div className="back-btn" onClick={() => this.props.handlerBackToPrevStep(this.state.stepName)}>
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
                    <div className={(this.state.stepName === CLAIM_STATE.CLAIM_TYPE || this.state.stepName === CLAIM_STATE.CLAIM_DETAIL) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>1</span>
                      </div>
                      <p>Thông tin sự kiện</p>
                    </div>
                    <div className={(this.state.stepName === CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>2</span>
                      </div>
                      <p>Thanh toán và liên hệ</p>
                    </div>
                    <div className={(this.state.stepName === CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>3</span>
                      </div>
                      <p>Kèm <br />chứng từ</p>
                    </div>
                    <div className={(this.state.stepName === CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>4</span>
                      </div>
                      <p>Hoàn tất yêu cầu</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="stepform">
            <div className="stepform__body" style={{paddingBottom: '12px'}}>
              <div className="info">
                <LoadingIndicator area="claim-li-benefit-list" />
                <div className="info__title" style={{marginBottom: '-4px'}}>
                  <h4>Thông tin sự kiện</h4>
                </div>
                <div className="info__title">
                  <p>Chọn 1 hoặc nhiều quyền lợi cho sự kiện bảo hiểm:</p>
                </div>
                <div className="info__body claim-type">
                  <div className="checkbox-wrap basic-column">
                    <div className="tab-wrapper">
                      {this.state.claimTypeList && this.state.claimTypeList.map((item, index) => (
                          <div className="tab" key={index}> {/* Thêm key vào đây */}
                            <div className="checkbox-warpper">
                              <label className="checkbox" htmlFor={item.ClaimType}>
                                <input
                                    type="checkbox"
                                    name={item.ClaimType}
                                    id={item.ClaimType}
                                    checked={claimCheckedMap && claimCheckedMap[item.ClaimType] ? true : false}
                                    onChange={(event) => this.props.handlerChangeClaimTypeOption(event, item.ClaimName)}
                                />
                                <div className="checkmark">
                                  <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                </div>
                              </label>
                              <p className="basic-text2">{item.ClaimName}</p>
                            </div>
                          </div>
                      ))}
                    </div>
                  </div>
                </div>
                <div className="info__body claim-type">

                <div className='top-dash-line-margin'></div>
                  <div className="list__item">
                    <p className='claim-questionaire' style={{fontSize: '16px'}}>Sự kiện bảo hiểm này có liên quan đến tai nạn ?</p>
                  </div>
                  <div className="item__content">
                    <div className="tab">
                      <div className="tab__content">
                        <div className="checkbox-warpper">

                          <div className="checkbox-item">
                            <div className="round-checkbox">
                              <label className="customradio" style={{ alignItems: 'center' }}>
                                <input type="checkbox" checked={(claimTypeState.isAccidentClaim !== null) && claimTypeState.isAccidentClaim} onClick={()=>this.props.answerYes()} disabled={isCheckedOnlyAccident(claimCheckedMap) || isCheckedOnlyIllness(claimCheckedMap)}/>
                                <div className={(isCheckedOnlyIllness(claimCheckedMap))?"checkmark-readonly":"checkmark"}></div>
                                <p className="text basic-padding-right" style={{fontSize: '16px'}}>Có</p>
                              </label>
                            </div>
                          </div>

                          <div className="checkbox-item margin-left-answer-no">
                            <div className="round-checkbox">
                              <label className="customradio" style={{ alignItems: 'center' }}>
                                <input type="checkbox" checked={(claimTypeState.isAccidentClaim !== null) && !claimTypeState.isAccidentClaim} onClick={()=>this.props.answerNo()} disabled={isCheckedOnlyAccident(claimCheckedMap) || isCheckedOnlyIllness(claimCheckedMap)}/>
                                <div className={(isCheckedOnlyAccident(claimCheckedMap))?"checkmark-readonly":"checkmark"}></div>
                                <p className="text" style={{fontSize: '16px'}}>Không</p>
                              </label>
                            </div>
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
          <div className="bottom-text">
          </div>
          <div className="bottom-btn">
            <button className={(claimTypeState.disabledButton) ? "btn btn-primary disabled" : "btn btn-primary"}
              id="submitClaimType" disabled={claimTypeState.disabledButton} onClick={this.props.handlerSubmitClaimType}>Tiếp tục</button>
          </div>
        </div>
      </section>
    );
  }
}

export default ClaimType;
