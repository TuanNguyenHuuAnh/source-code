import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { logoutSession } from '../util/APIUtils';

class PopupDevelopingFuncForCompany extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const logoutAccount = () => {
      if (document.getElementById('error-popup-developing-for-company')) {
        document.getElementById('error-popup-developing-for-company').className = "popup error-popup special";
        logoutSession();
        this.props.parentCallback(false, null, 0);
      }
    }
    return (
      <div className="popup error-popup special show" id="error-popup-developing-for-company">
        <div className="popup__card">
          <div className="developing-popup-card">
            <div className="picture-wrapper">
              <div className="picture">
                <img src="../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
              </div>
            </div>
            <div className="content center-local">
              <br/>
              <h4 className="basic-semibold" style={{fontSize:'18px'}}>Tính năng đang được nâng cấp</h4>
              <br/>
              <p className="bigheight">Quý khách vui lòng liên hệ tổng đài <span style={{fontWeight:'600'}}>(028) 3810 0888</span> hoặc <span style={{fontWeight:'600'}}>Email:</span>
                  <a className="simple-brown inline-local" href={"mailto:customer.services@dai-ichi-life.com.vn"} style={{fontWeight:'600'}}>&nbsp;customer.services@dai-ichi-life.com.vn </a>
                  để biết thông tin chi tiết.
              </p>
              <br/>
              <div className="btn-wrapper">
                  <Link className="btn btn-primary" to={"/"} onClick={()=>logoutAccount()}><p className="padding-clock2">Thoát</p></Link>
              </div>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupDevelopingFuncForCompany;