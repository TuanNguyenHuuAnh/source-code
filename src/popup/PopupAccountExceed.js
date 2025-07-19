import React, { Component } from 'react';
class PopupAccountExceed extends Component {
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
        document.getElementById('option-popup-account-exceed').className = "popup option-popup";
    }
    render() {
        return (
            <div className="popup option-popup" id="option-popup-account-exceed">
                <div className="popup__card">
                    <div className="optioncard">
                    <p>Thông tin Quý khách nhập chưa đúng, Quý khách có thể nhập lại sau 24h hoặc liên hệ Tổng đài dịch vụ Khách hàng, điện thoại: (028) 38 100 888, bấm phím số 1 để được hỗ trợ.</p>
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
export default PopupAccountExceed;