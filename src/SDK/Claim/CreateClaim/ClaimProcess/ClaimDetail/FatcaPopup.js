// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import { haveHC_HS, haveCheckedDeadth, isOlderThan18 } from '../../../../sdkCommon.js';
import LoadingIndicator from '../../../../LoadingIndicator2.js';
import { FE_BASE_URL } from "../../../../sdkConstant.js";

class FatcaPopup extends Component {

  constructor(props) {
    super(props);


    this.state = {
    }

  }


  componentDidMount() {
  }

  render() {
    const fatcaState = this.props.submissionState.fatcaState;
    return (
      <div className="popup confirm special" id="popupConfirmClaimSubmission">
        <div className="popup__card">
          <div className="confirm-card">
            <div className="confirm-card-header">
              <div className="confirm-card-close-btn"
                onClick={this.props.handleOnClickConfirmCloseButton}>
                <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn"
                  className="local-popup-btn" />
              </div>
            </div>
            <div className="confirm-card-body">
              <p>Theo luật thuế FATCA của Hoa Kỳ, nếu người nhận tiền có các thông tin dưới
                đây, vui
                lòng chọn:</p>
              <div className="checkbox-warpper">
                <label className="checkbox" htmlFor="isAmericanNationality">
                  <input type="checkbox" name="isAmericanNationality"
                    id="isAmericanNationality"
                    checked={!!fatcaState.isAmericanNationality}
                    onChange={(event) => this.props.handleOnChangeFatcaState(event)} />
                  <div className="checkmark">
                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                  </div>
                </label>
                <p className="text">Quốc tịch Hoa Kỳ</p>
              </div>
              <div className="checkbox-warpper">
                <label className="checkbox">
                  <input type="checkbox" name="isAmericanResidence"
                    id="isAmericanResidence"
                    checked={!!fatcaState.isAmericanResidence}
                    onChange={(event) => this.props.handleOnChangeFatcaState(event)} />
                  <div className="checkmark">
                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                  </div>
                </label>
                <p className="text">Địa chỉ thường trú tại Hoa Kỳ</p>
              </div>
              <div className="checkbox-warpper">
                <label className="checkbox">
                  <input type="checkbox" name="isAmericanTax" id="isAmericanTax"
                    checked={!!fatcaState.isAmericanTax}
                    onChange={(event) => this.props.handleOnChangeFatcaState(event)} />
                  <div className="checkmark">
                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                  </div>
                </label>
                <p className="text">Thực hiện khai báo thuế tại Hoa Kỳ</p>
              </div>
              <div className="dash-border"></div>
              <div className="title-head">
                <p>Xác nhận và cam kết của Người yêu cầu giải quyết quyền lợi bảo hiểm:</p>
              </div>
              <div className="content">
                <p>
                  Tôi cam kết các thông tin đã cung cấp trong yêu cầu quyền lợi bảo hiểm
                  này là
                  hoàn toàn đầy đủ và chính xác. <br />
                  Tôi cho phép bất kỳ nhân viên y tế, cơ sở y tế, doanh nghiệp bảo hiểm,
                  tổ chức
                  hay cá nhân nào khác được
                  cung cấp cho Dai-ichi Life Việt Nam bất kỳ thông tin, tài liệu nào liên
                  quan đến
                  Người được bảo hiểm
                  và/hoặc Bên mua bảo hiểm theo yêu cầu của Dai-ichi Life Việt Nam.
                </p>
              </div>
            </div>
            <div className="btn-footer" style={{ flexDirection: 'column' }}>
              {this.props.isSubmitting && <div style={{
                display: 'flex', justifyContent: 'center', alignItems: 'center'
              }}>
                <LoadingIndicator area="submit-init-claim" />
              </div>}
              <button
                className={`${this.props.isSubmitting ? 'disabled' : ''} btn btn-primary `}
                disabled={this.props.isSubmitting}
                style={{
                  display: 'flex',
                  justifyContent: 'center',
                  alignItems: 'center',
                  marginTop: 16
                }}
                id="fatca_id" onClick={this.props.handlerOpenPopupSucceeded}>
                {this.props.pinStep ?
                  'Lưu thông tin' : (
                    (this.props.poConfirmingND13 === '1') || (!haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList) && ((this.props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(this.props.selectedCliInfo?.dOB)) || (!isOlderThan18(this.props.selectedCliInfo?.dOB) && !haveCheckedDeadth(this.props.claimCheckedMap) && haveHC_HS(this.props.claimCheckedMap, this.props.claimTypeList))) ? 'Tiếp tục' : 'Hoàn Thành'
                )}
              </button>
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>
    );
  }
}

export default FatcaPopup;
