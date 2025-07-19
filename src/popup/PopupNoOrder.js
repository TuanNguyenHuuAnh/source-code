import React, { Component } from 'react';

class PopupNoOrder extends Component {
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
      document.getElementById('point-error-popup-no-order').className = "popup special point-error-popup";
  }
    render() {

        return (
          <div className="popup special point-error-popup" id="point-error-popup-no-order">
          <div className="popup__card">
            <div className="point-error-card">
              <div className="close-btn">
                <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeSorry}/>
              </div>
              <div className="picture-wrapper">
                <div className="picture">
                  <img src="../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                </div>
              </div>
              <div className="content">
                <p>Rất tiếc!</p>
                <p>Quý khách không có đơn hàng nào để tiến hành đặt hàng.</p>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupNoOrder;