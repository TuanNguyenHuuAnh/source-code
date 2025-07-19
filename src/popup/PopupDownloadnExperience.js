import React, { Component } from 'react';
import {FE_BASE_URL} from '../constants';

class PopupDownloadnExperience extends Component {
  constructor(props) {
    super(props);
    this.closeDownloadnExperienceEsc = this.closeDownloadnExperienceEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closeDownloadnExperienceEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closeDownloadnExperienceEsc, false);
  }
  closeDownloadnExperienceEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closeDownloadnExperience();
      }

  }     
  closeDownloadnExperience = () => {
      document.getElementById('popup-DownloadnExperience').className = "popup special point-error-popup";
  }
    render() {
        const openDaiIchiConnectApp = () => {
          window.open('http://onelink.to/srb7p8', '_blank');
          this.closeDownloadnExperience();
        }
        return (
          <div className="popup special point-error-popup" id="popup-DownloadnExperience">
          <div className="popup__card">
            <div className="point-error-card">
              <div className="close-btn">
                <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={this.closeDownloadnExperience} style={{marginTop: '-16px'}}/>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src={FE_BASE_URL + "/img/popup/download-experience.svg"} alt="popup-hosobg" />
                </div>
              </div>
              <div className="content" style={{paddingTop: '0px', paddingBottom: '16px'}}>
                <p className='basic-bold'>Tải ứng dụng và trải nghiệm</p>
                <p>Tính năng chỉ khả dụng trên ứng dụng Dai-ichi Connect.Tải ứng dụng và trải nghiệm ngay.</p>
              </div>
              <div className="btn-wrapper">
                  <a className="btn btn-primary" href='#' onClick={()=>openDaiIchiConnectApp()}>Tải ứng dụng</a>
                  <button className="later" onClick={this.closeDownloadnExperience}>Tải sau</button>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupDownloadnExperience;