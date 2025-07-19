// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import {FE_BASE_URL} from '../../../../sdkConstant';

class OtherCompanyDetail extends Component {

  constructor(props) {
    super(props);


    this.state = {
    }

    this.handlerAddOtherCompany = this.addOtherCompany.bind(this);
    this.handlerRemoveOtherCompany = this.removeOtherCompany.bind(this);
    this.handlerOnChangeCompanyName = this.onChangeCompanyName.bind(this);
    this.handlerOnChangeProductName = this.onChangeProductName.bind(this);
    this.handlerOnChangePolicyNo = this.onChangePolicyNo.bind(this);
  }


  componentDidMount() {
  }

  addOtherCompany() {
    var pOtherCompanyInfoList = Object.assign([], this.props.otherCompanyInfoList);
    pOtherCompanyInfoList.push(
      {
        companyName: '',
        productName: '',
        policyNo: '',
      }
    );
    this.props.handlerUpdateSubClaimDetailState("otherCompanyInfoList", pOtherCompanyInfoList);
  }

  removeOtherCompany(otherCompanyIndex) {
    var pOtherCompanyInfoList = Object.assign([], this.props.otherCompanyInfoList);
    pOtherCompanyInfoList.splice(otherCompanyIndex, 1);
    this.props.handlerUpdateSubClaimDetailState("otherCompanyInfoList", pOtherCompanyInfoList);
  };

  onChangeCompanyName(idx, event) {
    var pOtherCompanyInfoList = Object.assign([], this.props.otherCompanyInfoList);
    pOtherCompanyInfoList[idx].companyName = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("otherCompanyInfoList", pOtherCompanyInfoList);
  }

  onChangeProductName(idx, event) {
    var pOtherCompanyInfoList = Object.assign([], this.props.otherCompanyInfoList);
    pOtherCompanyInfoList[idx].productName = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("otherCompanyInfoList", pOtherCompanyInfoList);
  }

  onChangePolicyNo(idx, event) {
    var pOtherCompanyInfoList = Object.assign([], this.props.otherCompanyInfoList);
    pOtherCompanyInfoList[idx].policyNo = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("otherCompanyInfoList", pOtherCompanyInfoList);
  }


  render() {
    return (
      <div className="optionalform-wrapper">
        {this.props.otherCompanyInfoList !== null && this.props.otherCompanyInfoList.map((otherCompany, index) => (
          <div className="optionalform">
            <div className="optionalform__title">
              <h5 className="basic-bold">Công ty bảo hiểm khác {(index !== 0) && (index + 1)}</h5>
              {(index !== 0) &&
                <div className="dot" style={{ cursor: 'pointer' }}
                  onClick={() => this.handlerRemoveOtherCompany(index)}><span className="line"></span></div>
              }
            </div>
            <div className="optionalform__body">
              <div className="tab-wrapper">
                <div className="tab">
                  <div className="input">
                    <div className="input__content">
                      <input type="search" placeholder="Tên công ty bảo hiểm" maxLength="100" value={otherCompany.companyName}
                        // onFocus={(e) => e.target.placeholder = ""}
                        // onBlur={(e) => e.target.placeholder = "Tên công ty bảo hiểm"}
                        onChange={(event) => this.handlerOnChangeCompanyName(index, event)} />
                    </div>
                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                  </div>
                </div>
                <div className="tab">
                  <div className="input">
                    <div className="input__content">
                      <input type="search" placeholder="Tên sản phẩm" maxLength="100" value={otherCompany.productName}
                        // onFocus={(e) => e.target.placeholder = ""}
                        // onBlur={(e) => e.target.placeholder = "Tên sản phẩm"}
                        onChange={(event) => this.handlerOnChangeProductName(index, event)} />
                    </div>
                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                  </div>
                </div>
                <div className="tab">
                  <div className="input">
                    <div className="input__content">
                      <input type="search" placeholder="Số hợp đồng" maxLength="20" value={otherCompany.policyNo}
                        // onFocus={(e) => e.target.placeholder = ""}
                        // onBlur={(e) => e.target.placeholder = "Số hợp đồng"}
                        onChange={(event) => this.handlerOnChangePolicyNo(index, event)} />
                    </div>
                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}

        {/* <button className="circle-btn" onClick={this.handlerAddOtherCompany}>
          <i className="circle-plus"> <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" /></i>
          <label className="plus-information">Thêm công ty bảo hiểm</label>
        </button> */}
      </div>
    );
  }
}

export default OtherCompanyDetail;
