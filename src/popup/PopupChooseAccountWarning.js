import React, { Component } from 'react';
class PopupChooseAccountWarning extends Component {
    constructor(props) {
        super(props);
        this.closeOptionPopupEsc = this.closeOptionPopupEsc.bind(this);
    }

    componentDidMount(){
        document.addEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    componentWillUnmount(){
        document.removeEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    closeOptionPopupEsc=(event)=> {
        if (event.keyCode === 27) {
            this.closeOptionPopup();
        }
    
    } 
    closeOptionPopup() {
        document.getElementById('option-popup-choose-account-warning').className = "popup option-popup";
    }
    render() {
        return (
            <div className="popup option-popup" id="option-popup-choose-account-warning">
                <div className="popup__card">
                    <div className="optioncard">
                    <p>Quý khách vui lòng nhập lại số Hợp đồng khác (Hợp đồng chọn để nhập là Hợp đồng đã được phát hành).</p>
                    <div className="btn-wrapper">
                        <button className="btn btn-primary" id="existed" onClick={this.closeOptionPopup}>Đóng</button>
                    </div>
                    <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeOptionPopup}/></i>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}
export default PopupChooseAccountWarning;