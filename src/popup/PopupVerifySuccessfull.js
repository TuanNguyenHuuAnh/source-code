import React, { Component } from 'react';
import { PREVIOUS_SCREENS, FE_BASE_URL } from '../constants';
import { getSession, removeSession } from '../util/common';
import { Link } from 'react-router-dom';

class PopupVerifySuccessfull extends Component {
  constructor(props) {
    super(props);
    this.closeThanksEsc = this.closeThanksEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeThanksEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeThanksEsc, false);
  }
  closeThanksEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeThanks();
    }

  }
  closeThanks = () => {
    document.getElementById('popup-verify-successful').className = "popup special envelop-confirm-popup";
    if (getSession(PREVIOUS_SCREENS)) {
      removeSession(PREVIOUS_SCREENS);
    }
  }
  render() {

    return (
      <div className="popup special envelop-confirm-popup" id="popup-verify-successful">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  {getSession(PREVIOUS_SCREENS) ? (
                    <Link to={getSession(PREVIOUS_SCREENS)} onClick={this.closeThanks}><i className="closebtn"><img src="../img/icon/close.svg" alt="" /></i></Link>
                  ) : (
                    <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={this.closeThanks} /></i>
                  )}

                </div>
                <div className="envelop-content__body" style={{ paddingTop: '20px', paddingBottom: '32px' }}>
                  <p>
                    Xác thực <br /> thành công!
                  </p>
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src={FE_BASE_URL + "/img/envelop_nowhite.png"} alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupVerifySuccessfull;