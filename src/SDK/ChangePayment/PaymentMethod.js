import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession, formatFullName } from '../../util/common';
import { Form, Select } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import {isEmpty} from "lodash";
import {
    FE_BASE_URL,
    IS_MOBILE
  } from '../../constants';
import DropConfirmPopup from '../../components/DropConfirmPopup';

class PaymentMethod extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showDropConfirm: false
        };
    }

    componentDidMount () {

    }

    render() {
        const { Option } = Select;
        let transfer = this.props.paymentMethod?.transfer;
        let payInsurance = this.props.paymentMethod?.payInsurance;
        let refundLoan = this.props.paymentMethod?.refundLoan;
        let APL = this.props.paymentMethod?.APL;
        let premiumRefund = this.props.paymentMethod?.premiumRefund;
        
        const closeShowDropConfirm = () => {
            this.setState({showDropConfirm: false});
        }

        const agreeDrop = () => {
            if (this.state.dropMethod === 'P6') {
                this.props.handlerRemovePayInsurancePolInfo(this.state.dropIndex);
            } else if (this.state.dropMethod === 'P7') {
                this.props.handlerRemoveRefundLoanPolInfo(this.state.dropIndex);
            } else if (this.state.dropMethod === 'P8') {
                this.props.handlerRemoveAPLPolInfo(this.state.dropIndex);
            } else if (this.state.dropMethod === 'P8') {
                //Todo
            }
        }

        const showDropConfirmPopup = (method, index) => {
            this.setState({showDropConfirm: true, dropMethod: method, dropIndex: index});
        }
        console.log('this.props.top=', this.props.top);
        return (
            <>
                <div className="contractform__body">
                    <div className="info">
                        <div className="info__title nd13-padding-bottom16">PHƯƠNG THỨC NHẬN TIỀN</div>
                        <div className="contractform__head-content">
                            {this.props.proccessType === 'Surrender'?(
                                <p className="item-label" style={{lineHeight: '20px', marginLeft: '8px'}}>Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài dịch vụ khách hàng Dai-ichi Life Việt Nam để được hỗ trợ thông tin về Giá trị chi trả (nếu có) khi chấm dứt hợp đồng bảo hiểm. Đồng thời cung cấp thông tin nhận tiền để Dai-ichi Life Việt Nam thực hiện yêu cầu thanh toán.</p>
                            ):(
                                (this.props.proccessType === 'Partial Withdrawal')?(
                                    <p className="item-label" style={{lineHeight: '20px'}}>Quý khách vui lòng cung cấp thông tin nhận tiền để Dai-ichi Life Việt Nam thanh toán số tiền thực nhận cho yêu cầu rút một phần giá trị tài khoản/giá trị quỹ này.</p>
                                ):(
                                    <div className="item">
                                        <p className="item-label">Số tiền yêu cầu</p>
                                        <p className="item-content basic-red basic-semibold">{parseInt(this.props.amount).toLocaleString('it-IT', { style: 'currency', currency: 'VND' }).replace('VND', 'VNĐ')}</p>
                                    </div>
                                )

                            )}

                        </div>
                        <div className="info__body esubmission" style={{ padding: '12px', paddingLeft: '0' }}>
                            <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                    <div className="tab"> {/* Thêm key vào đây */}
                                        <div className="checkbox-warpper">
                                            <label className="checkbox" htmlFor={'payment-method-1'}>
                                                <input
                                                    type="checkbox"
                                                    name={'payment-method-1'}
                                                    id={'payment-method-1'}
                                                    checked={this.props.checkedPaymentMethod?.includes('P6') ? true : false}
                                                    onClick={() => this.props.handlerToggleCheckedPaymentMethod('P6')}
                                                />
                                                <div className="checkmark">
                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                </div>
                                            </label>
                                            <p className="basic-text2">Đóng phí bảo hiểm</p>
                                        </div>
                                    </div>

                                </div>
                                {this.props.checkedPaymentMethod?.includes('P6') &&
                                    <>
                                    {payInsurance && payInsurance?.polInfoList.map((item, index) => (
                                        <div className="info">
                                            <div style={{display: 'flex'}}>
                                            <div className="info__title" style={{textTransform: 'unset', marginTop: '8px', width: '100%'}}>Thông tin Hồ sơ/Hợp đồng {index > 0? index + 1: ''}</div>
                                            {index > 0 &&
                                                <div style={{marginTop: '8px'}}>
                                                    <i className="circle-minus"
                                                        onClick={() => showDropConfirmPopup('P6', index)}>
                                                        <img src={FE_BASE_URL + "/img/icon/minus.svg"} alt="minus" />
                                                    </i>
                                                </div>
                                            }
                                            </div>
                                            <div className="info__content">
                                                <div className="info__content-item">
                                                    <div className={item?.warningMessage?"input money validate":"input money"}>
                                                        <div className="input__content">
                                                            {item?.PolicyNo &&
                                                                <label>Số Hồ sơ/Hợp đồng</label>
                                                            }
                                                            <input 
                                                            displayType="input" 
                                                            type="search" 
                                                            maxLength="10" 
                                                            value={item?.PolicyNo} 
                                                            prefix=""
                                                            placeholder="Số Hồ sơ/Hợp đồng"
                                                            allowNegative={false}
                                                            allowLeadingZeros={true}
                                                            onChange={(e) => this.props.handlerOnChangePayInsurancePolicyNo(index, e.target.value)}
                                                            />
                                                        </div>
                                                        <i><img
                                                            src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    {item?.warningMessage &&
                                                    <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                        {item?.warningMessage}
                                                    </span>
                                                    }
                                                </div>
                                                <div className="info__content-item">
                                                    <div className="input">
                                                        <div className="input__content">
                                                            {item?.POName &&
                                                                <label htmlFor='pay-insurance-name' style={{ marginLeft: '2px' }}>Họ và tên Bên mua bảo hiểm</label>
                                                            }
                                                            <input id='pay-insurance-name' 
                                                                type="search" placeholder="Họ và tên Bên mua bảo hiểm" 
                                                                maxLength="50" 
                                                                value={item?.POName}
                                                                onChange={(e) => this.props.handlerOnChangePayInsurancePOName(index, e.target.value)}
                                                                />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                </div>
                                                <div className="info__content-item">
                                                    <div className={(item?.Amount === '0')?"input money validate":"input money"}>
                                                        <div className="input__content">
                                                            {item?.Amount &&
                                                                <label>Số tiền</label>
                                                            }
                                                            
                                                            <NumberFormat 
                                                                id='txtInputAmount'
                                                                value={item?.Amount}
                                                                thousandSeparator={'.'} decimalSeparator={','}
                                                                suffix={' VNĐ'}
                                                                allowNegative={false}
                                                                placeholder='Số tiền'
                                                                onValueChange={(values) => this.props.handlerOnChangePayInsuranceAmount(index, values.value)}
                                                                />
                                                        </div>
                                                        <i onClick={() => this.editButton("txtInputAmount")}><img
                                                            src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    {item?.Amount === '0' &&
                                                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                            Vui lòng nhập thông tin
                                                        </span>
                                                    }
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                    {payInsurance?.polInfoList?.length < 3 &&
                                    <div className="fund-tab"
                                        style={{ alignItems: 'flex-start' }}
                                        onClick={() => this.props.handlerAddPayInsurancePolInfo()}>
                                        <button className="add-address"
                                            id="add-new-address">
                                            <i className="circle-plus">
                                                <img src={FE_BASE_URL + "/img/icon/plus.svg"}
                                                    alt="circle-plus"
                                                    className="plus-sign" />
                                            </i>
                                            <span className="basic-semibold">Thêm Hồ sơ/Hợp đồng</span>
                                        </button>
                                    </div>
                                    }
                                    </>
                                }
                            </div>
                            <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}> {/* Thêm key vào đây */}
                                        <div className="checkbox-warpper">
                                            <label className="checkbox" htmlFor={'payment-method-2'}>
                                                <input
                                                    type="checkbox"
                                                    name={'payment-method-2'}
                                                    id={'payment-method-2'}
                                                    checked={this.props.checkedPaymentMethod?.includes('P7') ? true : false}
                                                    onClick={() => this.props.handlerToggleCheckedPaymentMethod('P7')}
                                                />
                                                <div className="checkmark">
                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                </div>
                                            </label>
                                            <p className="basic-text2">Trả tạm ứng từ Giá trị hoàn lại</p>
                                        </div>
                                    </div>

                                </div>
                                {this.props.checkedPaymentMethod?.includes('P7') &&
									<>
                                    {refundLoan && refundLoan?.polInfoList.map((item, index) => (
                                        <div className="info">
                                            <div style={{display: 'flex'}}>
                                            <div className="info__title" style={{textTransform: 'unset', marginTop: '8px', width: '100%'}}>Thông tin Hợp đồng {index > 0? index + 1: ''}</div>
                                            {index > 0 &&
                                                <div style={{marginTop: '8px'}}>
                                                    <i className="circle-minus"
                                                        onClick={() => showDropConfirmPopup('P7', index)}>
                                                        <img src={FE_BASE_URL + "/img/icon/minus.svg"} alt="minus" />
                                                    </i>
                                                </div>
                                            }
                                            </div>
                                            <div className="info__content">
                                                <div className="info__content-item">
                                                    <div className="input money">
                                                        <div className="input__content">
                                                            {item?.PolicyNo &&
                                                                <label>Số Hợp đồng</label>
                                                            }
                                                            <NumberFormat 
                                                            displayType="input" 
                                                            type="search" 
                                                            maxLength="9" 
                                                            value={item?.PolicyNo} 
                                                            prefix=""
                                                            placeholder="Số Hợp đồng"
                                                            allowNegative={false}
                                                            allowLeadingZeros={true}
                                                            onChange={(e) => this.props.handlerOnChangeRefundLoanPolicyNo(index, e.target.value)}
                                                            />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                </div>
                                                <div className="info__content-item">
                                                    <div className="input">
                                                        <div className="input__content">
                                                            {item?.POName &&
                                                                <label htmlFor='pay-insurance-name' style={{ marginLeft: '2px' }}>Họ và tên Bên mua bảo hiểm</label>
                                                            }
                                                            <input id='pay-insurance-name' 
                                                                type="search" placeholder="Họ và tên Bên mua bảo hiểm" 
                                                                maxLength="50" 
                                                                value={item?.POName}
                                                                onChange={(e) => this.props.handlerOnChangeRefundLoanPOName(index, e.target.value)}
                                                                />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                </div>
                                                <div className="info__content-item">
                                                    <div className={(item?.Amount === '0')?"input money validate":"input money"}>
                                                        <div className="input__content">
                                                            {item?.Amount &&
                                                                <label>Số tiền</label>
                                                            }
                                                            
                                                            <NumberFormat 
                                                                        id='txtInputAmount'
                                                                        value={item?.Amount}
                                                                        thousandSeparator={'.'} decimalSeparator={','}
                                                                        suffix={' VNĐ'}
                                                                        allowNegative={false}
                                                                        placeholder='Số tiền'
                                                                        onValueChange={(values) => this.props.handlerOnChangeRefundLoanAmount(index, values.value)}
                                                                        />
                                                        </div>
                                                        <i onClick={() => this.editButton("txtInputAmount")}><img
                                                            src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    {item?.Amount === '0' &&
                                                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                            Vui lòng nhập thông tin
                                                        </span>
                                                    }
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                    {refundLoan?.polInfoList?.length < 3 &&
                                    <div className="fund-tab"
                                        style={{ alignItems: 'flex-start' }}
                                        onClick={() => this.props.handlerAddRefundLoanPolInfo()}>
                                        <button className="add-address"
                                            id="add-new-address">
                                            <i className="circle-plus">
                                                <img src={FE_BASE_URL + "/img/icon/plus.svg"}
                                                    alt="circle-plus"
                                                    className="plus-sign" />
                                            </i>
                                            <span className="basic-semibold">Thêm Hợp đồng</span>
                                        </button>
                                    </div>
                                    }
                                    </>
								}
                            </div>
                            <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}> {/* Thêm key vào đây */}
                                        <div className="checkbox-warpper">
                                            <label className="checkbox" htmlFor={'payment-method-3'}>
                                                <input
                                                    type="checkbox"
                                                    name={'payment-method-3'}
                                                    id={'payment-method-3'}
                                                    checked={this.props.checkedPaymentMethod?.includes('P8') ? true : false}
                                                    onClick={() => this.props.handlerToggleCheckedPaymentMethod('P8')}
                                                />
                                                <div className="checkmark">
                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                </div>
                                            </label>
                                            <p className="basic-text2">Trả tạm ứng đóng phí tự động</p>
                                        </div>
                                    </div>

                                </div>
                                {this.props.checkedPaymentMethod?.includes('P8') &&
									<>
                                    {APL && APL?.polInfoList.map((item, index) => (
                                        <div className="info">
                                            <div style={{display: 'flex'}}>
                                            <div className="info__title" style={{textTransform: 'unset', marginTop: '8px', width: '100%'}}>Thông tin Hợp đồng {index > 0? index + 1: ''}</div>
                                            {index > 0 &&
                                                <div style={{marginTop: '8px'}}>
                                                    <i className="circle-minus"
                                                        onClick={() => showDropConfirmPopup('P8', index)}>
                                                        <img src={FE_BASE_URL + "/img/icon/minus.svg"} alt="minus" />
                                                    </i>
                                                </div>
                                            }
                                            </div>
                                            <div className="info__content">
                                                <div className="info__content-item">
                                                    <div className="input money">
                                                        <div className="input__content">
                                                            {item?.PolicyNo &&
                                                                <label>Số Hợp đồng</label>
                                                            }
                                                            <NumberFormat 
                                                            displayType="input" 
                                                            type="search" 
                                                            maxLength="9" 
                                                            value={item?.PolicyNo} 
                                                            prefix=""
                                                            placeholder="Số Hợp đồng"
                                                            allowNegative={false}
                                                            allowLeadingZeros={true}
                                                            onChange={(e) => this.props.handlerOnChangeAPLPolicyNo(index, e.target.value)}
                                                            />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                </div>
                                                <div className="info__content-item">
                                                    <div className="input">
                                                        <div className="input__content">
                                                            {item?.POName &&
                                                                <label htmlFor='pay-insurance-name' style={{ marginLeft: '2px' }}>Họ và tên Bên mua bảo hiểm</label>
                                                            }
                                                            <input id='pay-insurance-name' 
                                                                type="search" placeholder="Họ và tên Bên mua bảo hiểm" 
                                                                maxLength="50" 
                                                                value={item?.POName}
                                                                onChange={(e) => this.props.handlerOnChangeAPLPOName(index, e.target.value)}
                                                                />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                </div>
                                                <div className="info__content-item">
                                                    <div className={(item?.Amount === '0')?"input money validate":"input money"}>
                                                        <div className="input__content">
                                                            {item?.Amount &&
                                                                <label>Số tiền</label>
                                                            }
                                                            
                                                            <NumberFormat 
                                                                        id='txtInputAmount'
                                                                        value={item?.Amount}
                                                                        thousandSeparator={'.'} decimalSeparator={','}
                                                                        suffix={' VNĐ'}
                                                                        allowNegative={false}
                                                                        placeholder='Số tiền'
                                                                        onValueChange={(values) => this.props.handlerOnChangeAPLAmount(index, values.value)}
                                                                        />
                                                        </div>
                                                        <i onClick={() => this.editButton("txtInputAmount")}><img
                                                            src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    {item?.Amount === '0' &&
                                                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                            Vui lòng nhập thông tin
                                                        </span>
                                                    }
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                    {APL?.polInfoList?.length < 3&&
                                    <div className="fund-tab"
                                        style={{ alignItems: 'flex-start' }}
                                        onClick={() => this.props.handlerAddAPLPolInfo()}>
                                        <button className="add-address"
                                            id="add-new-address">
                                            <i className="circle-plus">
                                                <img src={FE_BASE_URL +"/img/icon/plus.svg"}
                                                    alt="circle-plus"
                                                    className="plus-sign" />
                                            </i>
                                            <span className="basic-semibold">Thêm Hợp đồng</span>
                                        </button>
                                    </div>
                                    }
                                    </>
									}
                            </div>
                            <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}> {/* Thêm key vào đây */}
                                        <div className="checkbox-warpper">
                                            <label className="checkbox" htmlFor={'payment-method-4'}>
                                                <input
                                                    type="checkbox"
                                                    name={'payment-method-4'}
                                                    id={'payment-method-4'}
                                                    checked={this.props.checkedPaymentMethod?.includes('P3') ? true : false}
                                                    onClick={() => this.props.handlerToggleCheckedPaymentMethod('P3')}
                                                />
                                                <div className="checkmark">
                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                </div>
                                            </label>
                                            <p className="basic-text2">Chuyển khoản qua số tài khoản</p>
                                        </div>
                                        {this.props.checkedPaymentMethod?.includes('P3') &&
                                    <div >
                                        {/* Tên chủ tài khoản */}
                                        <div className="info__content-item">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {transfer?.AccountName &&
                                                                <label htmlFor='bank-po-name' style={{ marginLeft: '2px' }}>Họ và tên chủ tài khoản</label>
                                                            }
                                                            <input id='bank-po-name' 
                                                                type="search" placeholder="Họ và tên chủ tài khoản" 
                                                                maxLength="50" 
                                                                value={formatFullName(transfer?.AccountName?transfer?.AccountName: this.props.clientName)}
                                                                onChange={(e) => this.props.handlerOnChangeTransferAccountName(e.target.value)}
                                                                disabled
                                                                />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                </div>
                                        {/* Số tài khoản */}
                                        <div className="checkbox-dropdown__content-item">
                                            <div className="input">
                                                <div className="input__content">
                                                    {transfer?.AccountNumber &&
                                                        <label style={{ marginLeft: '2px' }}>Số tài khoản</label>
                                                    }
                                                    <NumberFormat displayType="input" type="search" value={transfer?.AccountNumber} prefix=""
                                                        placeholder="Số tài khoản"
                                                        allowNegative={false}
                                                        allowLeadingZeros={true}
                                                        maxLength="20"
                                                        onChange={(e) => this.props.handlerOnChangeAccountNumber(e.target.value)}
                                                        onFocus={(e) => e.target.placeholder = ""}
                                                        onBlur={(e) => e.target.placeholder = "Số tài khoản"}
                                                    />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                            </div>
                                        </div>
                                        {/* Chọn ngân hàng */}
                                        <div className="checkbox-dropdown__content-item">
                                            <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                    <div className="tab__content input" style={{ padding: '0 8px 0 8px' }}>
                                                        {/* <div className="input" style={{ padding: '10px 0px 10px 0px' }}> */}
                                                        {/* <label htmlFor='bankname' style={{ marginLeft: '16px', display: 'block', width: '100%' }}>Tỉnh/ Thành phố</label> */}
                                                        <Select
                                                            id='bankname'
                                                            suffixIcon={<SearchOutlined />}
                                                            showSearch
                                                            size='large'
                                                            style={{ width: '100%', margin: '0' }}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Ngân hàng"
                                                            optionFilterProp="bankname"
                                                            onChange={(v) => this.props.handlerOnChangeBank(v)}
                                                            value={transfer.BankCode}
                                                            filterOption={(input, option) =>
                                                                option.bankname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                            }
                                                        >
                                                            {this.props.bankList?.map((bank) => (
                                                                <Option key={bank.CityCode} bankname={bank.CityName}>{bank.CityName}</Option>
                                                            ))}
                                                        </Select>
                                                        {/* </div> */}
                                                    </div>
                                                </div>
                                                <div className="dropdown__items">
                                                    <div className="dropdown-tag"></div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Chọn tỉnh/thành phố */}
                                        <div className="checkbox-dropdown__content-item">
                                            <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                    <div className="tab__content input" style={{ padding: '0 8px 0 8px' }}>
                                                        
                                                        {/* <div className="input" style={{ padding: '10px 0px 10px 0px' }}> */}
                                                        {/* <label htmlFor='cityname' style={{ marginLeft: '16px', display: 'block', width: '100%' }}>Tỉnh/ Thành phố</label> */}
                                                            <Select
                                                                id='cityname'
                                                                suffixIcon={<SearchOutlined />}
                                                                showSearch
                                                                size='large'
                                                                style={{ width: '100%', margin: '0' }}
                                                                width='100%'
                                                                bordered={false}
                                                                placeholder="Tỉnh/ Thành phố"
                                                                optionFilterProp="cityname"
                                                                onChange={(v) => this.props.handlerOnChangeCity(v)}
                                                                value={transfer.CityCode}
                                                                filterOption={(input, option) =>
                                                                    option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                }
                                                            >
                                                                {this.props.zipCodeList?.map((city) => (
                                                                    <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                                                                ))}
                                                            </Select>
                                                        {/* </div> */}
                                                    </div>
                                                </div>
                                                <div className="dropdown__items">
                                                    <div className="dropdown-tag"></div>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Chi nhánh */}
                                        <div className="checkbox-dropdown__content-item">
                                            <div className="input">
                                                <div className="input__content">
                                                    {transfer?.BranchName &&
                                                        <label style={{ marginLeft: '2px' }}>Chi nhánh</label>
                                                    }
                                                    <input type="search" placeholder="Chi nhánh" maxLength="200"
                                                        value={transfer?.BranchName}
                                                        onFocus={(e) => e.target.placeholder = ""}
                                                        onBlur={(e) => e.target.placeholder = "Chi nhánh"}
                                                        onChange={(e) => this.props.handlerOnChangeBranchName(e.target.value)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                            </div>
                                        </div>
                                        {/* Phòng giao dịch */}
                                        <div className="checkbox-dropdown__content-item">
                                            <div className="input">
                                                <div className="input__content">
                                                    {transfer?.BankDealingRoom &&
                                                        <label style={{ marginLeft: '2px' }}>Phòng giao dịch</label>
                                                    }
                                                    <input type="search" placeholder="Phòng giao dịch" maxLength="200"
                                                        value={transfer?.BankDealingRoom}
                                                        onFocus={(e) => e.target.placeholder = ""}
                                                        onBlur={(e) => e.target.placeholder = "Phòng giao dịch"}
                                                        onChange={(e) => this.props.handlerOnChangeBankDealingRoom(e.target.value)} />
                                                </div>
                                                <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                            </div>
                                        </div>
                                        {/* Số tiền nhận */}
                                        {(this.props.proccessType !== 'Surrender') && (this.props.proccessType !== 'Partial Withdrawal') &&
                                        <div className="info__content-item">
                                            <div className={(transfer?.Amount === '0')?"input money validate":"input money"}>
                                                <div className="input__content">
                                                    {transfer?.Amount &&
                                                        <label>Số tiền nhận</label>
                                                    }
                                                    
                                                    <NumberFormat 
                                                                id='txtInputAmount'
                                                                value={transfer?.Amount}
                                                                thousandSeparator={'.'} decimalSeparator={','}
                                                                suffix={' VNĐ'}
                                                                allowNegative={false}
                                                                placeholder='Số tiền nhận'
                                                                onValueChange={(values) => this.props.handlerOnChangeBankOfficeAmount(values.value)}
                                                                />
                                                </div>
                                                <i onClick={() => this.editButton("txtInputAmount")}><img
                                                    src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                            </div>
                                            {transfer?.Amount === '0' &&
                                                <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                    Vui lòng nhập thông tin
                                                </span>
                                            }
                                        </div>
                                        }
                                    </div>
                                }
                                    </div>

                                </div>

                            </div>
                            {(this.props.proccessType === 'Premium Refund') &&
                            <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                    <div className="tab" style={{borderTop: '1px solid #E7E7E7'}}> {/* Thêm key vào đây */}
                                        <div className="checkbox-warpper">
                                            <label className="checkbox" htmlFor={'payment-method-5'}>
                                                <input
                                                    type="checkbox"
                                                    name={'payment-method-5'}
                                                    id={'payment-method-5'}
                                                    checked={this.props.checkedPaymentMethod?.includes('P9') ? true : false}
                                                    onClick={() => this.props.handlerToggleCheckedPaymentMethod('P9')}
                                                />
                                                <div className="checkmark">
                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                </div>
                                            </label>
                                            <p className="basic-text2">Hoàn trả chủ thẻ hoặc chủ tài khoản thanh toán
                                            </p>
                                        </div>
                                        <p style={{paddingLeft: '32px', maxWidth: '420px', lineHeight: '17px', fontStyle: 'italic'}}>Áp dụng cho khoản phí bảo hiểm đóng qua thẻ tín dụng hoặc cổng thanh toán VNPAY</p>
                                    </div>

                                </div>
                            </div>
                            }
                            {this.props.checkedPaymentMethod?.includes('P9') &&
                            <div className="info__content-item">
                                <div className={(premiumRefund?.Amount === '0')?"input money validate":"input money"}>
                                    <div className="input__content">
                                        {premiumRefund?.Amount &&
                                            <label>Số tiền nhận</label>
                                        }
                                        
                                        <NumberFormat 
                                                    id='txtInputAmount'
                                                    value={premiumRefund?.Amount}
                                                    thousandSeparator={'.'} decimalSeparator={','}
                                                    suffix={' VNĐ'}
                                                    allowNegative={false}
                                                    placeholder='Số tiền nhận'
                                                    onValueChange={(values) => this.props.handlerOnChangePremiumRefundAmount(values.value)}
                                                    />
                                    </div>
                                    <i onClick={() => this.editButton("txtInputAmount")}><img
                                        src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                </div>
                                {premiumRefund?.Amount === '0' &&
                                    <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                        Vui lòng nhập thông tin
                                    </span>
                                }
                            </div>
                            }
                        </div>
                        {!getSession(IS_MOBILE) && this.props.errorMessage &&
                            <p className='payment-main-err'>
                            <span style={{ color: 'red', lineHeight: '20px', marginTop: '0', marginBottom: '16px', verticalAlign: 'top', textAlign: 'center' }}>
                                {this.props.errorMessage}
                            </span>
                            </p>
                        }
                        {this.props.top > 150 &&
                            <div style={{paddingTop: '90px'}}></div>
                        }
                        {!getSession(IS_MOBILE) && (this.props.proccessType !== 'Surrender') && (this.props.proccessType !== 'Partial Withdrawal') && 
                            (this.props.errorMessage ? (
                                <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', border: '1px solid red' }}>
                                    <div className="item">
                                        <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '12px' }}>Tổng số tiền</p>
                                        <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '12px' }}>{this.props.sum?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                    </div>
                                </div>
                            ) : (
                                <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px' }}>
                                    <div className="item">
                                        <p className="item-label simple-brown" style={{ lineHeight: '24px' }}>Tổng số tiền</p>
                                        <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px' }}>{this.props.sum?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                    </div>
                                </div>
                            )

                        )
                        }
                    </div>
                </div>
                {!getSession(IS_MOBILE) &&
                <div className="other_option" id="other-option-toggle"
                    onClick={() => this.props.goBack()}>
                    <p>Tiếp tục</p>
                    <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                </div>
                }
                {this.state.showDropConfirm &&
                    <DropConfirmPopup closePopup={() => closeShowDropConfirm()} go={() => agreeDrop()} msg='Quý khách có muốn xóa thông tin được chọn không?' />
                }
            </>
        )
    }
}

export default PaymentMethod;
