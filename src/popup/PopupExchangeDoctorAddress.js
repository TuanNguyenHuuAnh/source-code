import React, { Component } from 'react';
import { Link } from "react-router-dom";

class PopupExchangeDoctorAddress extends Component {
  constructor(props) {
    super(props);
    this.closeExchangeMobileCardSuccessEsc = this.closeExchangeMobileCardSuccessEsc.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.closeExchangeMobileCardSuccessEsc, false);
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeExchangeMobileCardSuccessEsc, false);
  }
  closeExchangeMobileCardSuccessEsc = (event) => {
    if (event.keyCode === 27) {
      this.closeExchangeMobileCardSuccess();
    }

  }
  closeExchangeMobileCardSuccess = () => {
    document.getElementById('doctor-address-popup-id').className = "popup special doctor-address-popup";
  }
  render() {
    const selectTab = (tabName) => {
      var x, i, tablinks;
      x = document.getElementsByClassName("control-tab");
      for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "")
      }
      document.getElementById('HCM-tab-1').className = 'tab';
      document.getElementById('HN-tab-1').className = 'tab';
      document.getElementById('DN-tab-1').className = 'tab';
      document.getElementById(tabName).className = document.getElementById(tabName).className + ' active';
      document.getElementById(tabName + "-1").className = "tab active";
    }
    return (
      <div className="popup special doctor-address-popup" id="doctor-address-popup-id">
        <div className="popup__card">
          <div className="doctor-address-card">
            <div className="header">
              <p>Địa chỉ phòng khám</p>
              <i className="closebtn" onClick={this.closeExchangeMobileCardSuccess}><img src="../img/icon/close.svg" alt="" /></i>
            </div>
            <div className="control">
              <div className="control-tab active" id="HCM-tab" onClick={() => selectTab('HCM-tab')}>
                <p>TP.Hồ Chí Minh</p>
              </div>
              <div className="control-tab" id="HN-tab" onClick={() => selectTab('HN-tab')}><p>Hà Nội</p></div>
              <div className="control-tab" id="DN-tab" onClick={() => selectTab('DN-tab')}>
                <p>Đà Nẵng</p>
              </div>
            </div>

            <div className="body">
              <div className="tab-wrapper">
                <div className="tab active" id="HCM-tab-1">
                  <div className="item-wrapper">
                    <div className="item">
                      <div className="addresscard">
                        <div className="addresscard__head">
                          <p>Trung tâm Xét nghiệm Y khoa quốc tế - DIAG Center International (Cơ sở 1)</p>
                        </div>
                        <div className="addresscard__body">
                          <div className="addresscard-wrapper">
                            <div className="addresscard-item">
                              <i><img src="../img/popup/phone.svg" alt="phone-icon" /></i>
                              <p>(028) 3979 8181</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/location.svg" alt="location-icon" /></i>
                              <p>414-416-418-420 Cao Thắng nối dài, P12, Quận 10, Tp HCM</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/clock.svg" alt="clock-icon" /></i>
                              <div className="item-content">
                                <div className="item-content-text">
                                  <span>Thứ 2 đến Thứ 7</span>
                                  <span> 7h30 đến 16h00</span>
                                </div>
                                <div className="item-content-text">
                                  <span>Ngày Chủ nhật cuối cùng của mỗi tháng:</span>
                                  <span> 7h30 đến 11h00</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>


                    <div className="item">
                      <div className="addresscard">
                        <div className="addresscard__head">
                          <p>Trung tâm Xét nghiệm Y khoa quốc tế - DIAG Center International (Cơ sở 2)</p>
                        </div>
                        <div className="addresscard__body">
                          <div className="addresscard-wrapper">
                            <div className="addresscard-item">
                              <i><img src="../img/popup/phone.svg" alt="phone-icon" /></i>
                              <p>(028) 3925 5610</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/location.svg" alt="location-icon" /></i>
                              <p>75 Phạm Viết Chánh, P.Nguyễn Cư Trinh, Q.1, Tp.Hồ Chí Minh</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/clock.svg" alt="clock-icon" /></i>
                              <div className="item-content">
                                <div className="item-content-text">
                                  <span>Thứ 2 đến Thứ 7</span>
                                  <span> 7h30 đến 16h00</span>
                                </div>

                                <div className="item-content-text">
                                  <span>Ngày Chủ nhật cuối cùng của mỗi tháng:</span>
                                  <span> 7h30 đến 11h00</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div className="item">
                      <div className="addresscard">
                        <div className="addresscard__head">
                          <p>Phòng khám Đa khoa Hạnh Phúc</p>
                        </div>
                        <div className="addresscard__body">
                          <div className="addresscard-wrapper">
                            <div className="addresscard-item">
                              <i><img src="../img/popup/phone.svg" alt="phone-icon" /></i>
                              <p>(028) 3862 2699</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/location.svg" alt="location-icon" /></i>
                              <p>446A Đường 3 tháng 2, P.12, Q.10
                                Tp.Hồ Chí Minh
                              </p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/clock.svg" alt="clock-icon" /></i>
                              <div className="item-content">
                                <div className="item-content-text">
                                  <span>Thứ 2 đến Thứ 7</span>
                                  <span> 7h30 đến 20h00</span>
                                </div>

                                <div className="item-content-text">
                                  <span>Chủ nhật</span>
                                  <span> 7h30 đến 12h00</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>


                </div>
                <div className="tab" id="HN-tab-1">
                  <div className="item-wrapper">
                    <div className="item">
                      <div className="addresscard">
                        <div className="addresscard__head">
                          <p>Phòng khám Medelab</p>
                        </div>
                        <div className="addresscard__body">
                          <div className="addresscard-wrapper">
                            <div className="addresscard-item">
                              <i><img src="../img/popup/phone.svg" alt="phone-icon" /></i>
                              <p>(024) 3845 6868</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/location.svg" alt="location-icon" /></i>
                              <p>86-88 Nguyễn Lương Bằng
                                P.Ô Chợ Dừa, Q.Đống Đa, Hà Nội
                              </p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/clock.svg" alt="clock-icon" /></i>
                              <div className="item-content">
                                <div className="item-content-text">
                                  <span>Thứ 2 đến Thứ 6</span>
                                  <span> 7h30 đến 16h30</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>


                </div>
                <div className="tab" id="DN-tab-1">
                  <div className="item-wrapper">
                    <div className="item">
                      <div className="addresscard">
                        <div className="addresscard__head">
                          <p>Bệnh viện Đa khoa Vĩnh Toàn</p>
                        </div>
                        <div className="addresscard__body">
                          <div className="addresscard-wrapper">
                            <div className="addresscard-item">
                              <i><img src="../img/popup/phone.svg" alt="phone-icon" /></i>
                              <p>(0511) 3893 903</p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/location.svg" alt="location-icon" /></i>
                              <p>49 Lê Duẩn, Q.Hải Châu, Tp.Đà Nẵng
                              </p>
                            </div>
                            <div className="addresscard-item">
                              <i> <img src="../img/popup/clock.svg" alt="clock-icon" /></i>
                              <div className="item-content">
                                <div className="item-content-text">
                                  <span>Thứ 2 đến Thứ 6</span>
                                  <span>Buổi sáng: 7h30 đến 11h00</span>
                                  <span>Buổi chiều: 14h00 đến 16h30</span>
                                </div>
                                <div className="item-content-text">
                                  <span>Thứ 7</span>
                                  <span>Buổi sáng: 7h30 đến 11h00</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>

    )
  }
}
export default PopupExchangeDoctorAddress;