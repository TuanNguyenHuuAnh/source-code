// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import NumberFormat from 'react-number-format';
import {FE_BASE_URL} from '../../../../sdkConstant';

class OtherCompany extends Component {

  constructor(props) {
    super(props);


    this.state = {
      paidAmount: undefined,
    }

  }

  componentDidMount() {
    if (this.props.facilityDetail.otherCompanyInfo.paidAmount) {
      this.setState({
        paidAmount: this.props.facilityDetail.otherCompanyInfo.paidAmount
      });
    }
  }

  render() {
    return (

      <div className="optionalform" style={{ paddingTop: '0' }}>
        <div className="optionalform__title">
          <h4 className="item__title" style={{ marginBottom: '16px' }}>Tên công ty bảo hiểm đã chi trả</h4>
        </div>
        <div className="optionalform__body">
          <div className="tab-wrapper">
            <div className="tab">
              <div className={(!this.props.facilityDetail.otherCompanyInfo.companyName || (this.props.facilityDetail.otherCompanyInfo.companyName.trim().length === 0)) && (this.props.facilityDetail.errors.errorList.indexOf('companyName') >= 0) ? "input validate" : "input"}>
                <div className="input__content">
                  <input type="search" placeholder="Tên công ty bảo hiểm" maxLength="100" id={"facility_companyName" + this.props.facilityIndex} defaultValue={this.props.facilityDetail.otherCompanyInfo.companyName}
                    onBlur={(event) => this.props.enterOtherCompanyName(this.props.facilityIndex, event.target.value)} />
                </div>
                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
              </div>
              {((!this.props.facilityDetail.otherCompanyInfo.companyName || (this.props.facilityDetail.otherCompanyInfo.companyName.trim().length === 0)) && (this.props.facilityDetail.errors.errorList.indexOf('companyName') >= 0)) &&
                <span style={{ color: 'red', lineHeight: '32px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                  Vui lòng nhập tên công ty bảo hiểm đã chi trả
                </span>}
            </div>
          </div>
        </div>
        <div className="optionalform__title">
          <h4 className="item__title" style={{ marginBottom: '16px', marginTop: '12px' }}>Số tiền được chi trả</h4>
        </div>
        <div className="optionalform__body">
          <div className="tab-wrapper">
            <div className="tab">
              <div className={!this.props.facilityDetail.otherCompanyInfo.paidAmount && (this.props.facilityDetail.errors.errorList.indexOf('paidAmount') >= 0) ? "input validate" : "input"}>
                <div className="input__content">
                  {/* <input type="search" placeholder="Số tiền" maxLength="20" value={this.props.otherCompanyInfo.paidAmount}
                    onChange={(event) => this.onChangePaidAmount(event)} /> */}
                  <NumberFormat displayType="input" placeholder="Số tiền" prefix="" type='tel'
                    id={"facility_paidAmount" + this.props.facilityIndex}
                                value={this.state.paidAmount}
                                // value={this.props.facilityDetail.otherCompanyInfo.paidAmount}
                    thousandsGroupStyle="thousand" thousandSeparator={'.'}
                    decimalSeparator="," decimalScale="0" suffix={' VNĐ'} allowNegative={false}
                    // onChange={(event) => this.props.enterOtherCompanyPaid(this.props.facilityIndex, event.target.value)}
                    onValueChange={(value) => {
                      this.setState({paidAmount: value.floatValue});
                      setTimeout(() => {
                        this.props.enterOtherCompanyPaid(this.props.facilityIndex, value.floatValue);
                      }, 300);
                    }}
                    // onBlur={(value) => this.props.enterOtherCompanyPaid(this.props.facilityIndex, value.floatValue)}
                  />
                </div>
                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
              </div>
              {(!this.props.facilityDetail.otherCompanyInfo.paidAmount && (this.props.facilityDetail.errors.errorList.indexOf('paidAmount') >= 0)) &&
                <span style={{ color: 'red', lineHeight: '32px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                  Vui lòng nhập số tiền được chi trả
                </span>}
            </div>
          </div>
        </div>

      </div>




    );
  }
}

export default OtherCompany;
