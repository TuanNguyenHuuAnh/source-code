import React, { Component } from 'react';
import { format } from 'date-fns';
import {showMessage, getSession, getDeviceId, getOperatingSystem, buildMicroRequest, getUrlParameter, getOSVersion } from '../../../sdkCommon';
import LoadingIndicator from '../../../LoadingIndicator2';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN,  AUTHENTICATION, COMPANY_KEY, WEB_BROWSER_VERSION, CODE_SUCCESS, FE_BASE_URL, SYSTEM_DCONNECT, SIGNATURE  } from '../../../sdkConstant';
import { getSelectedLIPolList, getPolicyProductInfoByInduredId } from '../../../sdkAPI';
import AlertPopupHight from '../../../components/AlertPopupHight';

// import 'antd/dist/antd.min.css';

class PolList extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);


    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          Action: 'PolicyProductLifeInsure',
          ClientID: getSession(CLIENT_ID),
          UserID: getSession(USER_LOGIN),
          LifeInsureID: '',
          IsLIS: '',
          noValidPolicy: false
        }
      },
      // jsonResponse: null
      clientProfile: null
    }
  }


  componentDidMount() {
    this._isMounted = true;
    // var jsonState = this.state;
    // jsonState.jsonInput.jsonDataInput.LifeInsureID = this.props.selectedCliId;
    // jsonState.jsonInput.jsonDataInput.IsLIS = this.props.selectedCliInfo.isLIS;
    // jsonState.clientProfile = null;
    // this.setState(jsonState);
    // // this.props.handlerLoadedPolList(null);
    // const apiRequest = Object.assign({}, this.state.jsonInput);
    // getSelectedLIPolList(apiRequest).then(Res => {
    //   let Response = Res.Response;
    //   if (Response.Result === 'true' && Response.ClientProfile !== null) {
    //     if (this._isMounted) {
    //       const jsonState = this.state;
    //       jsonState.clientProfile = Response.ClientProfile;
    //       this.setState(jsonState);
    //       this.props.handlerLoadedPolList(Response.ClientProfile);
    //     }
    //   } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
    //   }
    // }).catch(error => {
    //   // this.props.history.push('/maintainence');
    // });
    this.getPolicyProductInfoByInduredId();
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  componentDidUpdate(prevProps) {
    this._isMounted = true;
    console.log('componentDidUpdate=', this.props.selectedCliId + '|' +  prevProps.selectedCliId)
    // Typical usage (don't forget to compare props):
    if (this.props.selectedCliId !== prevProps.selectedCliId) {
      // var jsonState = this.state;
      // jsonState.jsonInput.jsonDataInput.LifeInsureID = this.props.selectedCliId;
      // jsonState.clientProfile = null;
      // this.setState(jsonState);
      // // this.props.handlerLoadedPolList(null);
      // const apiRequest = Object.assign({}, this.state.jsonInput);
      // getSelectedLIPolList(apiRequest).then(Res => {
      //   let Response = Res.Response;
      //   if (Response.Result === 'true' && Response.ClientProfile !== null) {
      //     if (this._isMounted) {
      //       var jsonState = this.state;
      //       jsonState.clientProfile = Response.ClientProfile;
      //       this.setState(jsonState);
      //       this.props.handlerLoadedPolList(Response.ClientProfile);
      //     }
      //   } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
      //     showMessage(EXPIRED_MESSAGE);
      //   }
      // }).catch(error => {
      //   // TODO
      //   // Alert.warning(EXPIRED_MESSAGE);
      // });
      this.getPolicyProductInfoByInduredId();
    }
  }

  getPolicyProductInfoByInduredId = () => {
    let metadata = {
      clientKey: AUTHENTICATION,
      deviceId: getDeviceId(),
      operationSystem: getOperatingSystem(),
      operatingSystem: getOperatingSystem(),
      operatingSystemVersion: getOSVersion(),
      platform: WEB_BROWSER_VERSION,
      system: this.props.systemGroupPermission?.[0]?.SoureSystem || SYSTEM_DCONNECT,
      signature: getSession(SIGNATURE),
      accessToken: this.props.apiToken
    }
    let data = {
      customerId: this.props.clientId,
      insuredId: this.props.selectedCliId,
      agentCode: this.props.agentCode
    }

    let request = buildMicroRequest(metadata, data);
    getPolicyProductInfoByInduredId(request).then(Res => {
      console.log('getPolicyProductInfoByInduredId=', Res.data);
      if (Res.code === CODE_SUCCESS && Res.data) {
        this.setState({ clientProfile: Res.data });
        this.props.handlerLoadedPolList( Res.data);
        // this.props.setNoValidPolicy(false);
        this.setState({noValidPolicy: false});
      } else {
        // this.props.setNoValidPolicy(true);
         this.setState({noValidPolicy: true});
      }
    }).catch(error => {
      console.log(error);
    });

  }

  closeToHome = () => {
      let from = getUrlParameter("fromApp");
      if (from) {
          let obj = {
              Action: "END_ND13_" + this.state.processType,
              ClientID: this.state.clientId,
              PolicyNo: this.state.policyNo,
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
      } else {
          window.location.href = this.props.callBackCancel;
      }
  }

  render() {
    // const dataResponse = this.state.jsonResponse;
    let clientProfile = this.state.clientProfile;
    // if (dataResponse !== null) {
    //   clientProfile = dataResponse.ClientProfile;
    // }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }

    return (
      <>
      <div className="card-extend-wrapper">
        <LoadingIndicator area='claim-selectedli-pol-list' />
        {clientProfile && clientProfile.map((item, index) => (
          <div className="item" key={index}>
            <div className="card-extend">
              {item?.lisName === "1" ?
                <div className="label linear"><p>Song hành bảo vệ</p></div> :
                ((item?.isMainCvg === '1') ?
                  <div className="label"><p>Bảo hiểm chính</p></div> :
                  <div className="label brown"><p>Bảo hiểm bổ sung</p></div>
                )}
              <div className="cardtab">
                <div className="card__header">
                  <h4 className="basic-bold">{item.productName.split(';')[1]}</h4>
                  <p>Hợp đồng: {item.policyID}</p>
                </div>
                <div className="cardtab__footer">
                  <div className="cardtab__footer-item">
                    <p>Tình trạng</p>
                    <div className="dcstatus">
                      <p className="active">{item.policyStatus.split(';')[1]?item.policyStatus.split(';')[1]:(item.policyStatusCode === '3K'?'Đang hiệu lực (miễn đóng phí)':'')}</p>
                    </div>
                  </div>
                  <div className="cardtab__footer-item">
                    <p>Ngày hiệu lực</p>
                    <p>{format(new Date(item.polIssEffDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                  </div>
                  {/* <div className="cardtab__footer-item">
                    <p>Ngày hết hạn bảo hiểm</p>
                    <p>{format(new Date(item.PolExpiryDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                  </div> */}
                  <div className="cardtab__footer-item">
                    <p>Số tiền bảo hiểm</p>
                    <p style={{color: '#DE181F', fontWeight: '600'}}>{item.faceAmount.split(';')[1]}</p>
                  </div>
                  <div className="cardtab__footer-item">
                    <p>Đại lý bảo hiểm</p>
                    <p>{item.agentName}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
      {/* {this.state.noValidPolicy &&
            <AlertPopupHight closePopup={()=>this.closeToHome()}
          msg={'Quý Khách không còn hợp đồng nào đang hiệu lực. Vui lòng liên hệ tổng đài hoặc văn phòng Dai-ichi Life gần nhất.'}
          imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'} screen={SCREENS.HOME} />
      } */}
      {this.state.noValidPolicy &&
          <AlertPopupHight closePopup={()=>this.closeToHome()}
              msg={'Hợp đồng bảo hiểm của Quý Khách đã hết hiệu lực và không còn trong thời gian nộp YCQL'}
              imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'}
          />
      }
      </>
    );

  }

}

export default PolList;
