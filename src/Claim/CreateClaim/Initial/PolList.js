import React, { Component } from 'react';
import { format } from 'date-fns';
import {showMessage, getSession, getDeviceId} from '../../../util/common';
import LoadingIndicator from '../../../../src/common/LoadingIndicator2';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, AUTHENTICATION, COMPANY_KEY } from '../../../constants';
import { getSelectedLIPolList } from '../../../util/APIUtils';

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
        }
      },
      // jsonResponse: null
      clientProfile: null
    }
  }


  componentDidMount() {
    this._isMounted = true;
    var jsonState = this.state;
    jsonState.jsonInput.jsonDataInput.LifeInsureID = this.props.selectedCliId;
    jsonState.jsonInput.jsonDataInput.IsLIS = this.props.selectedCliInfo.IsLIS;
    jsonState.clientProfile = null;
    this.setState(jsonState);
    // this.props.handlerLoadedPolList(null);
    const apiRequest = Object.assign({}, this.state.jsonInput);
    getSelectedLIPolList(apiRequest).then(Res => {
      let Response = Res.Response;
      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        if (this._isMounted) {
          const jsonState = this.state;
          jsonState.clientProfile = Response.ClientProfile;
          this.setState(jsonState);
          this.props.handlerLoadedPolList(Response.ClientProfile);
        }
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
      }
    }).catch(error => {
      // this.props.history.push('/maintainence');
    });
    // TODO: Order
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  componentDidUpdate(prevProps) {
    this._isMounted = true;
    // Typical usage (don't forget to compare props):
    if (this.props.selectedCliId !== prevProps.selectedCliId) {
      var jsonState = this.state;
      jsonState.jsonInput.jsonDataInput.LifeInsureID = this.props.selectedCliId;
      jsonState.clientProfile = null;
      this.setState(jsonState);
      // this.props.handlerLoadedPolList(null);
      const apiRequest = Object.assign({}, this.state.jsonInput);
      getSelectedLIPolList(apiRequest).then(Res => {
        let Response = Res.Response;
        if (Response.Result === 'true' && Response.ClientProfile !== null) {
          if (this._isMounted) {
            var jsonState = this.state;
            jsonState.clientProfile = Response.ClientProfile;
            this.setState(jsonState);
            this.props.handlerLoadedPolList(Response.ClientProfile);
          }
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        }
      }).catch(error => {
        // TODO
        // Alert.warning(EXPIRED_MESSAGE);
      });
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
      <div className="card-extend-wrapper">
        <LoadingIndicator area='claim-selectedli-pol-list' />
        {clientProfile && clientProfile.map((item, index) => (
          <div className="item" key={index}>
            <div className="card-extend">
              {item.LISName === "1" ?
                <div className="label linear"><p>Song hành bảo vệ</p></div> :
                ((item.CVGNum === '01' && item.PolicyClassCD === 'TL') || (item.CVGNum === '02' && item.PolicyClassCD !== 'TL') ?
                  <div className="label"><p>Bảo hiểm chính</p></div> :
                  <div className="label brown"><p>Bảo hiểm bổ sung</p></div>
                )}
              <div className="cardtab">
                <div className="card__header">
                  <h4 className="basic-bold">{item.ProductName.split(';')[1]}</h4>
                  <p>Hợp đồng: {item.PolicyID}</p>
                </div>
                <div className="cardtab__footer">
                  <div className="cardtab__footer-item">
                    <p>Tình trạng</p>
                    <div className="dcstatus">
                      <p className="active">{item.PolicyStatus.split(';')[1]?item.PolicyStatus.split(';')[1]:(item.PolicyStatusCode === '3K'?'Đang hiệu lực (miễn đóng phí)':'')}</p>
                    </div>
                  </div>
                  <div className="cardtab__footer-item">
                    <p>Ngày hiệu lực</p>
                    <p>{format(new Date(item.PolIssEffDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                  </div>
                  {/* <div className="cardtab__footer-item">
                    <p>Ngày hết hạn bảo hiểm</p>
                    <p>{format(new Date(item.PolExpiryDate.split(';')[1]), 'dd/MM/yyyy')}</p>
                  </div> */}
                  <div className="cardtab__footer-item">
                    <p>Số tiền bảo hiểm</p>
                    <p style={{color: '#DE181F', fontWeight: '600'}}>{item.FaceAmount.split(';')[1]}</p>
                  </div>
                  <div className="cardtab__footer-item">
                    <p>Đại lý bảo hiểm</p>
                    <p>{item.AgentName}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );

  }

}

export default PolList;
