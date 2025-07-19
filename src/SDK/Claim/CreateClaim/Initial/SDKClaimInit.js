import { Component } from 'react';
import { getSession, getUrlParameter } from '../../../sdkCommon';
import LoadingIndicator from '../../../LoadingIndicator2';
import { IS_MOBILE, FE_BASE_URL, SDK_ROLE_AGENT } from '../../../sdkConstant';
import AgreeCancelPopup from '../../../components/AgreeCancelPopup';

// import 'antd/dist/antd.min.css';

class SDKClaimInit extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);


    this.state = {
      showConfirm: false
    }
  }


  componentDidMount() {
    this._isMounted = true;
    if (!this.props.agentConfirmed && (this.props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT)) {
      if (!this.props.noValidPolicy) {
        this.setShowConfirm(true);
      }
      
    }
  }

  componentDidUpdate() {
    if (this.props.noValidPolicy) {
      this.setState({ showConfirm: false });
    }
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  setShowConfirm = (value) => {
    this.setState({ showConfirm: value });
    if (!value) {
      console.log('SDK Claim Init closeToHome.....')
      this.closeToHome();
    }
  }

  closeToHome = (notGo) => {
    if (notGo) {
      return;
    }
    let from = getUrlParameter("fromApp");
    if (from) {
      let obj = {
        Action: "END_ND13_" + this.props.processType,
        ClientID: this.props.clientId
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
    const closeShowConfirm = () => {
      this.setState({ showConfirm: false });
      console.log('SDK Claim Init2 closeToHome......')
      this.closeToHome();
    }
    console.log('SDKClaimInit this.state.showConfirm=', this.state.showConfirm);
    const agentConfirm=()=> {
      this.props.updateAgentConfirmed(true);
      // this.setShowConfirm(false);
      this.setState({ showConfirm: false });
    }

    return (
      <>
        <section className="sccontract-warpper">
          {!getUrlParameter("fromApp") &&
            <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
              <div className="breadcrums__item">
                <p>Yêu cầu quyền lợi</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>Tạo mới yêu cầu</p>
                <span>&gt;</span>
              </div>
            </div>
          }
          {getUrlParameter("fromApp") && (
            <div style={{ display: 'flex', background: 'linear-gradient(180deg, #d25540, #b53e3d 82.68%)' }}>
              <i><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
              <p className='viewer-back-title'>Tạo mới yêu cầu</p>
            </div>
          )}
          {!getUrlParameter("fromApp") &&
          <div className="other_option" id="other-option-toggle" onClick={this.goBack}>
            <p>Chọn thông tin</p>
            <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
          </div>
          }
          {!getUrlParameter("fromApp") &&
          <div className="sccontract-container">
            <div className="insurance">
              <div className="empty">
                <div className="icon">
                  <img src={FE_BASE_URL + "/img/icon/empty.svg"} alt="" />
                </div>
                <p style={{ paddingTop: '20px' }}>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
              </div>
            </div>
          </div>
          }
        </section>
        {this.state.showConfirm &&
          <AgreeCancelPopup closePopup={() => closeShowConfirm()} agreeFunc={() => agentConfirm()}
            title={'Cam kết của Đại lý bảo hiểm'}
            msg={'<p>Tôi hiểu rằng tôi đang thực hiện khai báo thông tin Yêu cầu giải quyết quyền lợi bảo hiểm thay cho Khách hàng của tôi.</p><p>Tôi xác nhận yêu cầu quyền lợi bảo hiểm này đã được thực hiện theo yêu cầu và đồng ý của BMBH/NĐBH, đồng thời tôi cam kết bảo mật tất cả thông tin của Khách hàng trong yêu cầu này.</p>'}
            imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} agreeText='Xác nhận' notAgreeText='Thoát' notAgreeFunc={() => closeShowConfirm()} />
        }
      </>
    );

  }

}

export default SDKClaimInit;
