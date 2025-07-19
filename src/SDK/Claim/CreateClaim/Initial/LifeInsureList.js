import React, { Component } from 'react';

import LoadingIndicator from '../../../LoadingIndicator2';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION, WEB_BROWSER_VERSION, CODE_SUCCESS, FULL_NAME, IS_MOBILE, SYSTEM_DCONNECT, SDK_REQUEST_STATUS, FE_BASE_URL, SIGNATURE } from '../../../sdkConstant';
import { format } from 'date-fns';
import {getSession, formatFullName, getDeviceId, buildMicroRequest, getOperatingSystem, getOSVersion} from '../../../sdkCommon';
import {requestClaimRecordInfor, getLifeInsuredListByClientId} from '../../../sdkAPI';
import { isEmpty } from 'lodash';
import AES256 from 'aes-everywhere';

// import 'antd/dist/antd.min.css';

class LifeInsureList extends Component {

  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: '',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          Action: 'LifeInsuredList',
          ClientID: getSession(CLIENT_ID),
          UserLogin: getSession(USER_LOGIN)
        }
      },
      selectedCliID: this.props.selectedCliID,
      isInit: true,
      polID: '',
      PolicyStatus: '',
      clientProfile: this.props.responseLIList,
      clientId: this.props.clientId
    }
  }


  componentDidMount() {
    this.setState({clientId: this.props.clientId, clientProfile: [], selectedCliID: ''});
    this.getLifeInsuredListByClientId(this.props.clientId || getSession(CLIENT_ID));
  }

  componentDidUpdate() {
    if (this.props.clientId !== this.props.clientId) {
      this.setState({clientId: this.props.clientId, clientProfile: [], selectedCliID: ''});
      this.getLifeInsuredListByClientId(this.props.clientId || getSession(CLIENT_ID));
    }
  }

  getLifeInsuredListByClientId = (clientId) => {
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
      customerId: clientId,
      agentCode: this.props.agentCode?this.props.agentCode: ''
    }

    let request = buildMicroRequest(metadata, data);
    getLifeInsuredListByClientId(request).then(Res => {
      console.log('life insure res=', Res.data);
      if (Res.code === CODE_SUCCESS && Res.data) {
        this.setState({ clientProfile: Res.data });
        this.props.handleSetResponseLIList(Res.data);


      } /*else if (Res.code === CODE_ERROR) {
                        } else if (Res.code === CODE_EXPIRED_TOKEN) {
                            showMessage(EXPIRED_MESSAGE);
                            logoutSession();
                            this.props.history.push({
                                pathname: '/home', state: {authenticated: false, hideMain: false}
                
                            })
                
                        }*/
    }).catch(error => {
      console.log(error);
    });

  }

  render() {
    const ClickOnLI = (index, item)=> {
      // if (!this.props.requestIdMap[this.props.clientId + '_' + item?.clientId]) {
      //     createClaimRequest(item);
      // }
      this.props.handlerClickOnLICard(index, item);
    }

  const createClaimRequest = (liItem) => {
    let metadata = {
      clientKey: AUTHENTICATION,
      deviceId: getDeviceId(),
      operationSystem: getOperatingSystem(),
      operatingSystem: getOperatingSystem(),
      operatingSystemVersion: getOSVersion(),        
      platform: WEB_BROWSER_VERSION,
      system: this.props.systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
      signature: getSession(SIGNATURE),
      accessToken: this.props.apiToken
    }
    console.log('aaaxxxxxxxxxx=' + this.props.clientId);
    let link = '';
    let data = {
      sourceSystem: this.props.systemGroupPermission?.[0]?.SourceSystem,
      requestId: '',
      customerId: this.props.clientId,
      customerName: getSession(FULL_NAME),
      agentCode: this.props.agentCode,
      agentName: this.props.agentName,
      agentPhone: this.props.agentPhone,
      lifeInsuredName: liItem?.fullName ? liItem?.fullName : '',
      lifeInsuredId: liItem?.clientId ? liItem?.clientId : '',
      processType: this.props.processType,
      status: SDK_REQUEST_STATUS.CREATE_REQUEST,
      link: link,
      lockingBy: this.props.systemGroupPermission?.[0]?.Role,
      jsonState: ''
    }

    let request = buildMicroRequest(metadata, data);
    requestClaimRecordInfor(request).then(Res => {
      if (Res.code === CODE_SUCCESS && Res.data) {
        this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
        this.props.updateRequestIdMap(this.props.clientId + '_' + liItem?.clientId, Res.data?.[0]?.requestId);
        // this.props.handleGoNextStep();
      } /*else if (Res.code === CODE_ERROR) {
                      } else if (Res.code === CODE_EXPIRED_TOKEN) {
                          showMessage(EXPIRED_MESSAGE);
                          logoutSession();
                          this.props.history.push({
                              pathname: '/home', state: {authenticated: false, hideMain: false}
              
                          })
              
                      }*/
    }).catch(error => {
      console.log(error);
    });

  }

    let clientProfile = this.state.clientProfile;//this.props.responseLIList;
    //console.log(clientProfile);
    return (
      <div className="card-warpper">
        <LoadingIndicator area="claim-li-list" />
        {clientProfile && clientProfile.map((item, index) => (
          (this.props.disableEdit || this.props.consultingViewRequest) ? (
          <div className="item" key={index}>
            <div className={item.clientId === this.props.selectedCliID ? "card choosen" : "card"}
              id={"card" + index}>
              <div className="card__header">
                <h4 className="basic-bold">{formatFullName(item.fullName)}</h4>
                <p>Mã khách hàng: {item.clientId}</p>
                <p>Ngày sinh: {format(new Date(item.dOB), 'dd/MM/yyyy')}</p>
                <div className="choose disabled">
                  <div className="dot disabled"></div>
                </div>
              </div>
            </div>
          </div>
          ): (
          <div className="item" key={index}>
            <div className={item.clientId === this.props.selectedCliID ? "card choosen" : "card"}
              onClick={() => ClickOnLI(index, item)} id={"card" + index}>
              <div className="card__header">
                <h4 className="basic-bold">{formatFullName(item.fullName)}</h4>
                <p>Mã khách hàng: {item.clientId}</p>
                <p>Ngày sinh: {format(new Date(item.dOB), 'dd/MM/yyyy')}</p>
                <div className="choose">
                  <div className="dot"></div>
                </div>
              </div>
            </div>
          </div>
          )

        ))}
      </div>
    );
  }

}

export default LifeInsureList;
