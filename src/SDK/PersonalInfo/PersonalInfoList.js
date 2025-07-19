import React, { Component } from 'react';
import { AUTHENTICATION, COMPANY_KEY, FE_BASE_URL, SUBMISSION_TYPE_MAPPING, PERSONAL_INFO_STATE, IS_MOBILE } from '../sdkConstant';
import { getDeviceId, getSession, setSession, formatFullName, getUrlParameter } from '../sdkCommon';
import { onlineRequestSubmit, logoutSession } from '../sdkAPI';
import LoadingIndicator from '../LoadingIndicator2';
import '../sdk.css';
import AlertPopupOriginal from '../components/AlertPopupOriginal';
import ErrorReInstamentSubmitted from '../components/ErrorReInstamentSubmitted';

let from = '';
class PersonalInfoList extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);
    this.state = {
      LIClientProfile: null,
      noValidPolicy: false,
      submitIn24: false
    }
  }


  componentDidMount() {
    from = getUrlParameter("fromApp");
    this._isMounted = true;
    this.callLifeAssuredListAPI();
  }

  // componentDidUpdate() {
  //   if (this.props.selectedLI !== this.state.selectedLI) {
  //     this.setState({selectedLI: this.props.selectedLI})
  //   }
  // }

  componentWillUnmount() {
    this._isMounted = false;
  }

  callLifeAssuredListAPI = () => {
    const apiRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Authentication: AUTHENTICATION,
        DeviceId: this.props.deviceId,
        APIToken: this.props.apiToken,
        Project: 'mcp',
        Action: 'GetLIInfoPSProcess',
        ClientID: this.props.clientId,
        UserLogin: this.props.clientId,
        RequestTypeID: "CPI",
        FromSystem: from ? "DCA" : "DCW",
      }
    };
    let jsonState;
    console.log('apiRequest=', apiRequest);
    onlineRequestSubmit(apiRequest).then(Res => {

      let Response = Res.Response;
      console.log('Response=', Response);
      if ((Response.ErrLog === 'Successfull.') && (Response.ClientProfile !== null) && this._isMounted) {
        jsonState = this.state;
        jsonState.LIClientProfile = Response.ClientProfile;
        console.log('Response.ClientProfile life insure list = ', Response.ClientProfile);
        this.setState(jsonState);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
              pathname: '/home',
              state: { authenticated: false, hideMain: false }

          })

      } else {
          this.setState({noValidPolicy: true});
          // if (!getSession(IS_MOBILE)) {
          //     this.props.handlerUpdateNoValidPolicy(true);
          // } 
      }
      
    }).catch(error => {

    });


  }

  closeNoValidPolicy=()=> {
      this.setState({noValidPolicy: false});
      if (this.props.appType === 'CLOSE') {
          window.location.href = '/update-personal-info';
      } else {
          let from = getUrlParameter("fromApp");
          let obj = {
              Action: "END_ND13_" + this.props.proccessType,
              ClientID: this.props.clientId,
              PolicyNo: this.props.polID,
              TrackingID: this.state.trackingId
          };
          if (from && (from === "Android")) {//for Android
              if (window.AndroidAppCallback) {
                  window.AndroidAppCallback.postMessage(JSON.stringify(obj));
              }
          } else if (from && (from === "IOS")) {//for IOS
              if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                  window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
              }
          }
      }
  }

  render() {
    // const selectLI=(li)=> {
    //   this.setState({selectedLI: li});
    //   this.props.updateSelectedLI(li);
    // }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }

    const ForceContinue = () => {
        closeSubmitIn24();
        this.props.updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
    }
    const closeSubmitIn24 = () => {
      this.setState({submitIn24: false});
    }
    const choosePersonalInfo = () => {
        const jsonRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: "CheckRequestSubmitND13",
                APIToken: this.props.apiToken,
                Authentication: AUTHENTICATION,
                DeviceId: this.props.deviceId,
                OS: "Web",
                Project: "mcp",
                ClientID: this.props.clientId,
                UserLogin: this.props.clientId,
                RequestTypeID: this.props.proccessType,
                PolicyNo: this.state.polID,
                FromSystem: from?"DCA":"DCW"
            }
        };
        onlineRequestSubmit(jsonRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === "EXIST") {
              this.setState({submitIn24: true});
                return;
            } else {
              this.props.updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
            }

        }).catch(error => {
            console.log(error);
        });

        
    }

    const closeNoValidPolicy=()=> {
        this.setState({noValidPolicy: false});
        if (this.props.appType === 'CLOSE') {
            // window.location.href = '/update-personal-info';
            this.setState({noValidPolicy: false});
        } else {
            let from = getUrlParameter("fromApp");
            let obj = {
                Action: "END_ND13_" + this.props.proccessType,
                ClientID: this.props.clientId,
                PolicyNo: this.props.polID,
                TrackingID: this.state.trackingId
            };
            if (from && (from === "Android")) {//for Android
                if (window.AndroidAppCallback) {
                    window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                }
            } else if (from && (from === "IOS")) {//for IOS
                if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                    window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                }
            }
        }
    }
    let msg = '';
    let imgPath = '';
    if (this.state.noValidPolicy) {
        msg = 'Chúng tôi không tìm thấy Khách hàng đủ điều kiện thực hiện yêu cầu ' + SUBMISSION_TYPE_MAPPING[this.props.proccessType] + ' trên Dai-ichi Connect. Quý khách vui lòng liên hệ văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.';
        imgPath = FE_BASE_URL + '/img/popup/no-policy.svg';
    }
    return (
      <>
            {this.state.noValidPolicy && !getSession(IS_MOBILE) && (
                <div className="sccontract-container" style={{backgroundColor: '#ffffff', paddingTop: '100px', width: '100%'}}>
                    <div className="insurance">
                        <div className="empty">
                            <div className="picture">
                                <img src={FE_BASE_URL + '/img/popup/no-policy.svg'} alt=""/>
                            </div>
                            <p style={{padding: '0 72px', textAlign: 'center', lineHeight: '21px'}}>{'Chúng tôi không tìm thấy Khách hàng đủ điều kiện thực hiện yêu cầu ' + SUBMISSION_TYPE_MAPPING[this.props.proccessType] + ' trên Dai-ichi Connect.'} <br/> Quý khách vui lòng liên hệ văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ. </p>
                           
                        </div>
                    </div>
                </div>
            )}
        {!this.state.noValidPolicy && 
        <section className="sccontract-warpper" style={{width: '100%'}} id="scrollAnchor">
          <div className="insurance claim-type">

            <div className={getSession(IS_MOBILE)?"stepform margin-top170":"stepform"}>
              <div className="stepform__body" style={{paddingBottom: '12px'}}>
                <div className="info nd13-padding-bottom32">
                  <LoadingIndicator area="claim-li-benefit-list" />
                  <div className="info__title" style={{marginBottom: '16px'}}>
                    <h4>Chọn khách hàng cần điều chỉnh thông tin</h4>
                  </div>
                  <div className="info__body claim-type">
                    <div className="checkbox-wrap basic-column">
                      <div className="tab-wrapper">
                        {this.state.LIClientProfile && this.state.LIClientProfile.map((item, index) => (
                            <div className={(index < this.state.LIClientProfile?.length -1) ?"tab bo-hop-dong-container-body-border": "tab"} key={index}> {/* Thêm key vào đây */}
                              <div className="checkbox-warpper">
                                <div className="checkbox-item" style={{width: 'auto'}}>
                                  <div className="round-checkbox">
                                    <label className="customradio" style={{ alignItems: 'center' }}>
                                      <input type="checkbox" checked={this.props.selectedLI?.LifeInsuredID === item?.LifeInsuredID} onClick={()=>this.props.updateSelectedLI(item)}/>
                                        <div className={"checkmark"}></div>
                                    </label>
                                  </div>
                                </div>
                                <p className="basic-text2">{formatFullName(item.FullName)}</p>
                              </div>
                            </div>
                        ))}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <img className="decor-clip" src={`${FE_BASE_URL}/img/mock.svg`} alt="" />
              <img className="decor-person" src={`${FE_BASE_URL}/img/person.png`} alt="" />
            </div>
            <div className="bottom-text">
            </div>
            <div className="bottom-btn">
              <button className={this.props.selectedLI?.LifeInsuredID?"btn btn-primary":"btn btn-primary disabled"}
                disabled={this.props.selectedLI?.LifeInsuredID?'':'disabled'} onClick={()=>this.props.chosenLISubmit()}>Tiếp tục</button>
            </div>
          </div>
        </section>
        }
        {this.state.noValidPolicy && getSession(IS_MOBILE) &&
            <AlertPopupOriginal closePopup={()=>closeNoValidPolicy()} msg={msg} imgPath={imgPath}/>
                  
        }
        {this.state.submitIn24 &&
          <ErrorReInstamentSubmitted closePopup={() => closeSubmitIn24()} msg={'Hợp đồng này đã có yêu cầu ' + SUBMISSION_TYPE_MAPPING[this.props.proccessType].toLowerCase() + '. Quý khách có muốn tiếp tục tạo yêu cầu không?'} imgPath={FE_BASE_URL + '/img/popup/cancel-hc-notice.svg'}
            screen={''}
            forceContinue={() => ForceContinue()} />
        }
      </>
    );
  }
}

export default PersonalInfoList;
