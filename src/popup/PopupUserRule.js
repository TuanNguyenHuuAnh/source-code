import React, { Component } from 'react';
import { USER_RULE_AGREE } from '../constants';
import {setSession} from '../util/common';

class PopupUserRule extends Component {
  constructor(props) {
    super(props);
    this.closeUserRuleEsc = this.closeUserRuleEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closeUserRuleEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closeUserRuleEsc, false);
  }
  closeUserRuleEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closeUserRule();
      }

  }  
    closeUserRule = () => {
        document.getElementById('user-rule-popup').className = "popup special user-rule-popup";
    }



    render() {
      const popupUserRuleSubmit=() =>{
        //event.preventDefault(); 
        if (document.getElementById('btn-user-rule').className === "btn btn-primary disabled") {
          return;
        }
        setSession(USER_RULE_AGREE, true);
        //close user rule popup
        document.getElementById('user-rule-popup').className = "popup special user-rule-popup";
        //show none existed popup
        document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted show";
        this.props.parentCallback(true);
       
      }
      //alert(this.props.parentCallback);
      const toggleAgree = () => {
        if (document.getElementById('checkbox-agree').checked === false) {
          document.getElementById('checkbox-agree').checked = true;
          document.getElementById('btn-user-rule').className = "btn btn-primary";
        } else {
          document.getElementById('checkbox-agree').checked = false;
          document.getElementById('btn-user-rule').className = "btn btn-primary disabled";
        }
        
      }
        return (
          <div className="popup special user-rule-popup" id="user-rule-popup">
          <div className="popup__card">
            <div className="user-rule-card">
              <div className="header">
                <p>Điều khoản sử dụng</p>
                <i className="closebtn"><img src="img/icon/close.svg" alt="" onClick={this.closeUserRule}/></i>
              </div>
              <div className="body">
                <p>Cam kết sử dụng giao dịch điện tử</p>
                <div className="content-wrapper">
                  <p>Tôi, bên mua bảo hiểm, xác nhận và đồng ý các nội dung sau:</p>
                  <p>
                    1. Sử dụng Dịch Vụ Giao Dịch Điện Tử liên quan đến tất cả Hợp đồng bảo hiểm với Công ty TNHH Bảo hiểm
                    nhân thọ Dai-ichi Việt Nam (Dai-ichi Life Việt Nam” hay “DLVN”).
                  </p>
                  <p>
                    2. Đã đọc hiểu, đồng ý và cam kết tuân thủ quy định của
                    <span className="basic-bold">ĐIều kiện và Điều khoản Giao Dịch Điện Tử</span> cũng như tất cả quy định, hướng
                    dẫn của Dai-ichi Life Việt Nam và pháp luật liên quan đế Giao Dịch Điện Tử.
                  </p>
                  <p>
                    3. Dai-ichi Việt Nam được quyền gửi thông tin Mã Truy Cập và Mật Khẩu Truy Cập để sử dụng hình thức Giao
                    Dịch Điện Tử và thực hiện Giao Dịch Điện Tử thông qua địa chỉ thư điện tử (email) và/hoặc số điện thoại
                    mà tôi đã cung cấp cho Dai-ichi Life Việt Nam.
                  </p>
                </div>
    
                <div className="checkbox-wrapper">
                  <div className="round-checkbox">
                    <label className="customradio"  onClick={() => toggleAgree()}>
                      {this.props.ruleChecked?(
                        <input type="checkbox" id="checkbox-agree" checked/>
                      ):(
                        <input type="checkbox" id="checkbox-agree"/>
                      )
                      }
                      <div className="checkmark"></div>
                      <p claas="text">Tôi đồng ý</p>
                    </label>
                  </div>
                </div>
                <div className="btn-wrapper">
                  {this.props.ruleChecked?(
                    <button className="btn btn-primary" id="btn-user-rule" onClick={()=>popupUserRuleSubmit()}>Xác nhận</button>
                  ):(
                    <button className="btn btn-primary disabled" id="btn-user-rule" onClick={()=>popupUserRuleSubmit()}>Xác nhận</button>
                  )
                  }
                  
                </div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>

        )
    }
}
export default PopupUserRule;