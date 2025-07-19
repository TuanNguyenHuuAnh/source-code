import React, { Component } from 'react';
import {FE_BASE_URL} from '../constants';

class PopupQRCnButton extends Component {
  constructor(props) {
    super(props);
    this.closeQRCEsc = this.closeQRCEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closeQRCEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closeQRCEsc, false);
  }
  closeQRCEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closeQRC();
      }

  }     
  closeQRC = () => {
      document.getElementById('popup-QRC').className = "popup special point-error-popup";
  }
    render() {

        return (
          <div className="popup special point-error-popup" id="popup-QRC">
          <div className="popup__card">
            <div className="point-error-card" style={{maxWidth: '460px'}}>
              <div className="close-btn">
                <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={this.closeQRC} style={{marginTop: '-16px'}}/>
              </div>
              <div className="content" style={{paddingTop: '0px', paddingBottom: '16px'}}>
                <p className='basic-bold'>Tải ứng dụng và trải nghiệm</p>
                <p>Tính năng chỉ khả dụng trên ứng dụng Dai-ichi Connect.<br/>Tải ứng dụng và trải nghiệm ngay.</p>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src={FE_BASE_URL + "/img/popup/QRC-Button.svg"} alt="popup-hosobg" />
                </div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupQRCnButton;