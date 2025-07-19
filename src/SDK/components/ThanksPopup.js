import React, { Component } from 'react';
import { Link } from "react-router-dom";

class ThanksPopup extends Component {
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
    this.props.closeThanks();
  }
  render() {

    return (
      <div className="popup special envelop-confirm-popup show">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <Link to="/update-policy-info-change" onClick={this.closeThanks}><i className="closebtn"><img src="../img/icon/close.svg" alt=""/></i></Link>
                </div>
                <div className="envelop-content__body">
                  <h5 className="basic-bold basic-uppercase" style={{textAlign: 'center'}}>gửi yêu cầu thành công</h5>
                      <p>&nbsp;</p>
                  <p>
                    {this.props.msg}
                  </p>
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src="../img/envelop_nowhite.png" alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default ThanksPopup;