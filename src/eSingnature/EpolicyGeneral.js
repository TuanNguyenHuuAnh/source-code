import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import AES256 from 'aes-everywhere';
import { AUTHENTICATION, ACCESS_TOKEN, E_SINGNATURE_MAP, E_SINGNATURE_CLIENT_ID, E_SINGNATURE_POLICY_NO, CLIENT_ID, RECAPTCHA_KEY, COMPANY_KEY } from '../constants';
import '../common/Common.css';
import { getUrlParameter, setSession, getSession, getDeviceId } from '../util/common';
import { getUserInfo, logoutSession } from '../util/APIUtils';
import EAuthenticationLogin from './EAuthenticationLogin';
import { GoogleReCaptchaProvider } from "react-google-recaptcha-v3";

let profile = [];
class EpolicyGeneral extends Component {
  constructor(props) {
    super(props);
    this.state = {
      eSingnatureClientID: '',
      eSingnaturePolicyNO: '',
      showAuthentication: false,
      loginSuccess: false,
      clientProfile: null
    };
    this.setShowAuthentication = this.setShowAuthentication.bind(this);
    this.setLoginSuccess = this.setLoginSuccess.bind(this);
  }

  componentDidMount() {
    let jsonState = this.state;
    let token = getUrlParameter("pid");

    if (token) {
      const userInfoRequest = {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Action: 'GetQRCodeInfo',
          APIToken: '41cfe98839d949918f6a33e4ad319e00',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Project: 'mcp',
          UserLogin: '0000000000',
          QRID: token
        }
      }
      getUserInfo(userInfoRequest).then(res => {
        let Response = res.Response;
        if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
          setSession(E_SINGNATURE_CLIENT_ID, Response.ClientProfile.ClientID ? Response.ClientProfile.ClientID : '');
          setSession(E_SINGNATURE_POLICY_NO, Response.ClientProfile.PolicyNo ? Response.ClientProfile.PolicyNo : '');
          jsonState.eSingnatureClientID = Response.ClientProfile.ClientID ? Response.ClientProfile.ClientID : '';
          jsonState.eSingnaturePolicyNO = Response.ClientProfile.PolicyNo ? Response.ClientProfile.PolicyNo : '';
          jsonState.clientProfile = Response.ClientProfile[0];
          this.setState(jsonState);
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });
    }

    


  }

  setShowAuthentication(value) {
    this.setState({ showAuthentication: value });
  }

  setLoginSuccess(value) {
    this.setState({ loginSuccess: value });
  }

  render() {
    const checkAuthentication = () => {
      let jsonState = this.state;
      const accessToken = getSession(ACCESS_TOKEN);
      const clientID = getSession(CLIENT_ID);

      if (accessToken && clientID && jsonState.eSingnatureClientID === clientID) {
        jsonState.showAuthentication = false;
      } else {
        jsonState.showAuthentication = true;
      }
      this.setState(jsonState);
    }
    
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    if (this.state.loginSuccess) {
      let polID = ((this.state.eSingnaturePolicyNO.length <= 7) ? ("00" + this.state.eSingnaturePolicyNO) : this.state.eSingnaturePolicyNO);
      return <Redirect to={{
        pathname: "/mypolicyinfo/" + polID
      }} />;
    } else {
      return (
        <>
          <main className={logined ? "logined" : ""}>
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Điểm thưởng</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>

            <section>
              <div className="e-singnature">
                <div className='logo'>
                  <img src="../img/logo-esignature.png" alt="logo" />
                </div>
                {this.state.clientProfile &&
                  <div className="form">
                    {this.state.clientProfile.ClientID &&
                      <div className="form__item">
                        <p>{E_SINGNATURE_MAP['ClientID']}</p>
                        <p>{this.state.clientProfile.ClientID}</p>
                      </div>
                    }
                    {this.state.clientProfile.PolicyNo &&
                      <div className="form__item">
                        <p>{E_SINGNATURE_MAP['PolicyNo']}</p>
                        <p>{this.state.clientProfile.PolicyNo}</p>
                      </div>
                    }
                    {this.state.clientProfile.ClientName &&
                      <div className="form__item">
                        <p>{E_SINGNATURE_MAP['ClientName']}</p>
                        <p>{this.state.clientProfile.ClientName}</p>
                      </div>
                    }
                    {this.state.clientProfile.IssueBy &&
                      <div className="form__item">
                        <p>{E_SINGNATURE_MAP['IssueBy']}</p>
                        <p>{this.state.clientProfile.IssueBy}</p>
                      </div>
                    }
                    {this.state.clientProfile.SignedDate &&
                      <div className="form__item">
                        <p>{E_SINGNATURE_MAP['SignedDate']}</p>
                        <p>{this.state.clientProfile.SignedDate}</p>
                      </div>
                    }
                    {this.state.clientProfile.ShowLink && (this.state.clientProfile.ShowLink === 'Y') &&
                      <div className="form__item">
                        <p className="e-singnature-link" onClick={() => checkAuthentication()}>Bấm để xem chi tiết hợp đồng</p>
                        <p></p>
                      </div>
                    }
                  </div>
                }

              </div>
            </section>


          </main>
          {this.state.showAuthentication &&
            <GoogleReCaptchaProvider reCaptchaKey={RECAPTCHA_KEY}>
              <EAuthenticationLogin eSingnatureClientID={this.state.eSingnatureClientID} setShowAuthentication={this.setShowAuthentication} setLoginSuccess={this.setLoginSuccess} />
            </GoogleReCaptchaProvider>
          }
        </>


      )
    }

  }

}

export default EpolicyGeneral;