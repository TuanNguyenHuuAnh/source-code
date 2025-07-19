import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class PopupSessionExpired extends Component {
    closeExpiredPopup() {
        document.getElementById('session-expired-popup').className = "popup option-popup";
    }    
    render() {
        return (
            <div className="popup option-popup" id="session-expired-popup">
                <div className="popup__card">
                    <div className="optioncard">
                    <p>Session đã expire hoặc account vừa đăng nhập trên một thiết bị khác.</p>
                    <Link to={'/home'} onClick={this.closeExpiredPopup}><i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt=""/></i></Link>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}
export default PopupSessionExpired;