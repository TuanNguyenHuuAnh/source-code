import { Component } from 'react';
import parse from 'html-react-parser';
import { FE_BASE_URL } from '../sdkConstant';

class ThanksPopupSDK extends Component {
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
                  <i className="closebtn" onClick={()=>this.closeThanks()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></i>
                </div>
                <div className="envelop-content__body">
                  <h5 className="basic-bold basic-uppercase" style={{textAlign: 'center'}}>gửi yêu cầu thành công</h5>
                      <p>&nbsp;</p>
                    {this.props.msg ? parse(this.props.msg) : ''}
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
export default ThanksPopupSDK;