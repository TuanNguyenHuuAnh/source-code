import React, { Component } from 'react';

class PopupPointExChangeRule extends Component {
  constructor(props) {
    super(props);
    this.closePointExChangeRuleEsc = this.closePointExChangeRuleEsc.bind(this);
  }

  componentDidMount(){
      document.addEventListener("keydown", this.closePointExChangeRuleEsc, false);
  }
  componentWillUnmount(){
      document.removeEventListener("keydown", this.closePointExChangeRuleEsc, false);
  }
  closePointExChangeRuleEsc=(event)=> {
      if (event.keyCode === 27) {
          this.closePointExChangeRule();
      }

  }    
    closePointExChangeRule = () => {
        document.getElementById('point-exchange-rule-popup').className = "popup special point-exchange-rule-popup";
    }

    render() {
        return (
          <div>
          <div className="popup special point-exchange-rule-popup" id="point-exchange-rule-popup">
          <div className="popup__card">
            <div className="exchange-rule-card">
              <div>
              <div className="header">
                <p className="bigheight">Điểm thưởng có thể được đổi thành những phần quà/ dịch vụ hấp dẫn:</p>
                <i className="closebtn"><img src="../img/icon/close.svg" alt=""  onClick={this.closePointExChangeRule}/></i>
              </div>
              <div className="body">
                <div className="list">
                  <div className="list__item">
                    <div className="dot"></div>
                    <p>Điểm thưởng quy đổi: 1 điểm = 1.000 đồng </p>
                  </div>
                  <div className="list__item">
                    <div className="dot"></div>
                    <p>Bạn có thể chọn nhiều quà/ dịch vụ để đổi cùng một lượt.</p>
                  </div>
                  <div className="list__item">
                    <div className="dot"></div>
                    <p className="bigheight20">Với những phần quà cần giao tận nơi, bạn vui lòng cung cấp địa chỉ giao hàng cụ thể và chính xác (thông tin liên lạc này chỉ nhằm mục đích giao hàng, không có hiệu lực áp dụng đối với các liên hệ khác về Hợp đồng bảo hiểm).</p>
                  </div>
                  <div className="list__item">
                    <div className="dot"></div>
                    <p className="bigheight20">Nếu hợp đồng bảo hiểm đáo hạn hoặc chấm dứt hợp đồng trước thời hạn hoặc chấm dứt hợp đồng do giải quyết quyền lợi bảo hiểm hoặc chấm dứt hợp đồng theo quy định của pháp luật thì toàn bộ số điểm thưởng tích lũy sẽ được chuyển đổi thành tiền và thanh toán cho Bên mua bảo hiểm cùng với các khoản thanh toán quyền lợi liên quan (nếu có).</p>
                  </div>
                  <div className="list__item">
                    <div className="dot"></div>
                    <p className="bigheight20">Điểm thưởng tích lũy của Bên mua bảo hiểm sẽ được lưu giữ trong thời gian hai (02) năm. Nếu sau thời gian này, điểm thưởng chưa sử dụng sẽ được Dai-ichi Life Việt Nam tự động quy đổi thành số tiền tương ứng và chuyển đóng phí bảo hiểm định kỳ cho hợp đồng bảo hiểm.</p>
                  </div>
                </div>
              </div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>
        </div>
        )
    }
}
export default PopupPointExChangeRule;