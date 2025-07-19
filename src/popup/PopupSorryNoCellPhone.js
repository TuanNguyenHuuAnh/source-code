import React, { Component } from 'react';

class PopupSorryNoCellPhone extends Component {
  constructor(props) {
    super(props);
    this.closeSorryEsc = this.closeSorryEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closeSorryEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closeSorryEsc, false);
  }
  closeSorryEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closeSorry();
      }

  }     
  closeSorry = () => {
      document.getElementById('point-error-popup-sorry-no-cell-phone').className = "popup special point-error-popup";
  }
    render() {

        return (
          <div className="popup special point-error-popup" id="point-error-popup-sorry-no-cell-phone">
          <div className="popup__card">
            <div className="point-error-card">
              <div className="close-btn">
                <img src="img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeSorry}/>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src="img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                </div>
              </div>
              <div className="content">
                <h4>Rất tiếc!</h4>
                <p>Không có thông tin số điện thoại, vui lòng cập nhật tài khoản</p>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupSorryNoCellPhone;