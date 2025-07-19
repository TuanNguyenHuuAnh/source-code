import React, { Component } from 'react';

import LoadingIndicator from '../../../../src/common/LoadingIndicator2';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION } from '../../../constants';
import { format } from 'date-fns';
import {getSession, formatFullName, getDeviceId} from '../../../util/common';

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
      selectedCliID: '',
      isInit: true,
      polID: '',
      PolicyStatus: '',
      jsonResponse: null
    }
  }


  componentDidMount() {
    // const apiRequest = Object.assign({}, this.state.jsonInput);
    // getLIListForClaim(apiRequest).then(Res => {
    //   let Response = Res.Response;
    //   if (Response.Result === 'true' && Response.ClientProfile !== null) {
    //     var jsonState = this.state;
    //     jsonState.jsonResponse = Response;
    //     this.setState(jsonState);
    //     this.props.handlerLIList(this.state.jsonResponse.ClientProfile);
    //   } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
    //     Alert.warning(EXPIRED_MESSAGE);
    //     // logoutSession();
    //     // this.props.history.push({
    //     //   pathname: '/home',
    //     //   state: { authenticated: false, hideMain: false}

    //     // })

    //   }
    // }).catch(error => {
    //   //this.props.history.push('/maintainence');
    // });
  }


  render() {
    const ClickOnLI = (index, item)=> {
      this.props.handlerClickOnLICard(index, item);
    }
    let clientProfile = this.props.responseLIList;
    //console.log(clientProfile);
    return (
      <div className="card-warpper">
        <LoadingIndicator area="claim-li-list" />
        {clientProfile != null && clientProfile.map((item, index) => (
          <div className="item" key={index}>
            <div className={item.ClientID === this.props.selectedCliID ? "card choosen" : "card"}
              onClick={() => ClickOnLI(index, item)} id={"card" + index}>
              <div className="card__header">
                <h4 className="basic-bold">{formatFullName(item.FullName)}</h4>
                <p>Mã khách hàng: {item.ClientID}</p>
                <p>Ngày sinh: {format(new Date(item.DOB), 'dd/MM/yyyy')}</p>
                <div className="choose">
                  <div className="dot"></div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }

}

export default LifeInsureList;
