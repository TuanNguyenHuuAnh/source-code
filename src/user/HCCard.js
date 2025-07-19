import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID, INSURED_IMAGE_LIST, EXPAND_ID_LIST, COMPANY_KEY, AUTHENTICATION } from '../constants';
import { logoutSession, CPGetClientProfileByCLIID, getHCCard } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import { Link } from "react-router-dom";
import '../common/Common.css';
import { formatFullName, showMessage, setSession, getSession, getDeviceId } from '../util/common';

class HCCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      clientProfile: null,
      imageCardBase64: '',
      insuredImageList: getSession(INSURED_IMAGE_LIST)?JSON.parse(getSession(INSURED_IMAGE_LIST)):{},
      expandIdList: getSession(EXPAND_ID_LIST)?JSON.parse(getSession(EXPAND_ID_LIST)):[]
    };
  }
  cpGetHCCards = () => {
    const submitRequest = {
      jsonDataInput: {
        OS: 'Samsung_SM-A125F-Android-11',
        Company: COMPANY_KEY,
        Authentication: AUTHENTICATION,
        Action: 'LifeInsuredHCList',
        DeviceId: getDeviceId(),
        APIToken: getSession(ACCESS_TOKEN),
        Project: 'mcp',
        ClientID: getSession(CLIENT_ID),
        UserLogin: getSession(USER_LOGIN)
      }
    }

    CPGetClientProfileByCLIID(submitRequest).then(Res => {
      //console.log(JSON.stringify(Res));
      let Response = Res.Response;

      if (Response.Result === 'true' && Response.ClientProfile) {
        this.setState({ clientProfile: Response.ClientProfile });
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }

        })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  componentDidMount() {
    this.cpGetHCCards();
  }

  render() {
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    const toggleCardInfo = (InsureID, id) => {
      //const index = this.state.expandIdList.indexOf(id);
      if (this.state.expandIdList.indexOf(id) >= 0) {
        let index = this.state.expandIdList.indexOf(id);
        let copyList = this.state.expandIdList;
        ///copyList[index] = '';
        copyList.splice(index, 1);
        setSession(EXPAND_ID_LIST, JSON.stringify(copyList));

        this.setState({expandIdList: copyList});
      } else {
        let copyList = this.state.expandIdList;
        copyList.push(id);
        setSession(EXPAND_ID_LIST, JSON.stringify(copyList));

        this.setState({expandIdList: copyList});
        if (!this.state.insuredImageList[InsureID]) {
          getHCCardImg(InsureID);
        }
      }

    }

    const getHCCardImg = (InsureID) => {
      const submitRequest = {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Action: '',
          InsureID: InsureID,
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          ClientID: getSession(CLIENT_ID),
          DeviceId: getDeviceId(),
          Project: 'mcp',
          UserLogin: getSession(USER_LOGIN),
        }

      }

      getHCCard(submitRequest)
        .then(response => {
          let insuredList = this.state.insuredImageList;
          if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
            insuredList[InsureID] = response.Response.Message;
            setSession(INSURED_IMAGE_LIST, JSON.stringify(insuredList));
            this.setState({ imageCardBase64: response.Response.Message, insuredImageList: insuredList });
          } else {
            if (InsureID in insuredList) {
              delete insuredList[InsureID];
            }
            setSession(INSURED_IMAGE_LIST, JSON.stringify(insuredList));
            this.setState({ imageCardBase64: '', insuredImageList: insuredList });
          }
          
        }).catch(error => {
          this.props.history.push('/maintainence');
        });
    }

    const openDownload = (base64, cardName) => {
      let a = document.createElement('a');
      a.href = "data:image/png;base64," + base64;
      a.download = cardName + ".png";
      a.click();
    }

    const selectedSubMenu=(id, subMenuName)=> {
      // setSession(LINK_SUB_MENU_NAME, subMenuName);
      // setSession(LINK_SUB_MENU_NAME_ID, id);
    }
   
    return (
        <main className="logined nodata">
          <div className="main-warpper insurancepage page-eleven">
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Thông tin tài khoản</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
              <div className="breadcrums__item">
                <p>Thẻ bảo hiểm sức khoẻ</p>
                <p className='breadcrums__item_arrow'>></p>
              </div>
            </div>
            <div className="profile-user-the-bao-hiem">
              <p className="head-text">Thẻ bảo hiểm sức khoẻ</p>
            </div>
            <div className="customer-informationthe-bh">
              <div className="customer-informationthe-bh consulting-service">
                <div className="customer-informationthe-bh__body">
                <LoadingIndicator area="claim-li-list" />
                <LoadingIndicator area="HCCard-loading" />
                  {this.state.clientProfile &&
                    this.state.clientProfile.map((item, index) => {
                      if ( (this.state.expandIdList.indexOf("hc-card-item-" + index) >= 0) && (item.ClientID in this.state.insuredImageList)) {
                        return (
                          <div className="information-card-item">
                            <div className="information-card-item-header" onClick={() => toggleCardInfo(item.ClientID, "hc-card-item-" + index)}>
                              <div className="information-card-item-header-text expand-text" id={"hc-card-item-" + index}>
                                {formatFullName(item.FullName)}
                              </div>
                              <div className="information-card-item-header-arrow">
                                <img
                                  src="img/icon/arrow-down.svg"
                                  alt="arrow-down"
                                  className="bottom-mar"
                                />
                              </div>
                            </div>
                            <div className="information-card-item-content">
                              <div className="information-card-item-content-image width80percent">
                                  <img src={'data:image/png;base64, ' + this.state.insuredImageList[item.ClientID]} alt="insurance-card" />
                              </div>
                              <div className="information-card-item-content-text"style={{width:'453px',paddingTop:'10px'}}>
                                <a className="left verybigheight"><div className="left verybigheight" onClick={()=>openDownload(this.state.insuredImageList[item.ClientID], item.FullName)}>Lưu thẻ</div></a>
                                <Link className="right verybigheight" to={"/lifeassured/" + item.ClientID} onClick={()=>selectedSubMenu('item-12-1', 'Người được bảo hiểm')}><div className="right verybigheight">Quyền lợi bảo hiểm</div></Link>
                              </div>
                            </div>

                          </div>
                        )
                      } else {
                        return (
                          <div className="information-card-item" onClick={() => toggleCardInfo(item.ClientID, "hc-card-item-" + index)}>
                            <div className="information-card-item-header">
                              <div className="information-card-item-header-text" id={"hc-card-item-" + index}>
                                {formatFullName(item.FullName)}
                              </div>
                              <div className="information-card-item-header-arrow">
                                <img src="img/icon/arrow-left-grey.svg" alt="arrow-left" />
                              </div>
                            </div>
                          </div>
                        )
                      }

                    })}
                  <Link to="/utilities/network/Hospital" onClick={()=>selectedSubMenu('u1', 'Mạng lưới')}>
                  <div className="location-container">
                    <div className="location-icon">
                      <img src="img/location-brown.svg" alt="location" />
                    </div>
                    <div className="location-text">Cơ sở y tế bảo lãnh viện phí</div>
                  </div>
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </main>
    )
  }
}

export default HCCard;
