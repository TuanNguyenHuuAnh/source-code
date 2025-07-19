import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession } from '../../util/common';
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING
  } from '../../constants';


class PaymentType extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proccessType: this.props.proccessType
        };
    }

    componentDidMount() {
        if ((this.props.proccessType === 'Maturity') && !this.props.amount) {
            this.props.handlerInputAmount(parseInt(this.props.selectedItem?.CashSurrender?this.props.selectedItem?.CashSurrender: 0) + '');
        } else if ((this.props.proccessType === 'Loan') && !this.props.amount) {
            this.props.handlerUpdateMaxLoanAmount(parseInt(this.props.selectedItem?.MaxLoanAmt?this.props.selectedItem?.MaxLoanAmt: 0));//MaxLoanAmt is number in state
        } else if ((this.props.proccessType === 'Dividend') && !this.props.amount) {
            this.props.handlerUpdateMaxDividendAmount(parseInt(this.props.selectedItem?.Dividend?this.props.selectedItem?.Dividend?.replaceAll('.', ''): 0));//Dividend is number in state
        } else if ((this.props.proccessType === 'Coupon') && !this.props.amount) {
            this.props.handlerUpdateMaxCouponAmount(parseInt(this.props.selectedItem?.Coupon?this.props.selectedItem?.Coupon?.replaceAll('.', ''): 0));//Coupon is number in state
        } else if ((this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD !== 'IL') && !this.props.amount) {
            this.props.handlerUpdateMaxPartialWithdrawalAmount(parseInt(this.props.selectedItem?.PolAccountValue?this.props.selectedItem?.PolAccountValue?.replaceAll('.', ''): 0));//PolAccountValue is number in state
        }
        
    }

    componentDidUpdate() {
        if (this.props.proccessType !== this.state.proccessType) {
            this.setState({proccessType: this.props.proccessType});
        }
    }

    render() {
        return (
            <>
                <img style={{ minWidth: '100%', marginTop: '0', marginBottom: '0' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                <div className="contractform__body">
                    <div className="info">
                        <div className="info__title nd13-padding-bottom16">{CATEGORY_NAME_MAPPING[this.props.proccessType]}</div>
                        <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                <div className="tab">
                                        <div className="info__content">
                                            
                                                {this.props.proccessType !== 'Surrender' &&
                                                    <div className="info__content-item">
                                                        <div className={(this.props.proccessType !== 'Loan') && (this.props.proccessType !== 'Dividend') && (this.props.proccessType !== 'Coupon') && (this.props.proccessType !== 'Partial Withdrawal')  && (this.props.proccessType !== 'Premium Refund')? "input money disabled": "input money"}>
                                                            {(this.props.proccessType !== 'Loan') && (this.props.proccessType !== 'Dividend') && (this.props.proccessType !== 'Coupon') && (this.props.proccessType !== 'Partial Withdrawal')  && (this.props.proccessType !== 'Premium Refund')?(
                                                                <>
                                                                    <div className="input__content">
                                                                        <label>Giá trị chi trả (tạm tính)</label>
                                                                        <NumberFormat className="basic-uppercase"
                                                                            id='txtInputAmount'
                                                                            // inputmode="numeric"
                                                                            type='seach'
                                                                            value={this.props.amount ? this.props.amount : ((this.props.proccessType === 'Maturity')? parseInt(this.props.selectedItem?.CashSurrender?this.props.selectedItem?.CashSurrender: 0): 0)}
                                                                            thousandSeparator={'.'} decimalSeparator={','}
                                                                            suffix={' VNĐ'}
                                                                            allowNegative={false}
                                                                            onValueChange={(values) => this.props.handlerInputAmount(values.value)}
                                                                            disabled
                                                                        />
                                                                    </div>
                                                                </>
                                                            ):(
                                                            <>
                                                                <div className="input__content">
                                                                    {this.props.amount && (this.props.proccessType === 'Maturity'?(
                                                                        <label>Giá trị chi trả (tạm tính)</label>
                                                                    ):(
                                                                        <label>Số tiền</label>
                                                                    ))}
                                                                   
                                                                    <NumberFormat
                                                                        id='txtInputAmount'
                                                                        // inputmode="numeric"
                                                                        type='seach'
                                                                        value={this.props.amount ? this.props.amount : ((this.props.proccessType === 'Maturity') ? parseInt(this.props.selectedItem?.CashSurrender ? this.props.selectedItem?.CashSurrender : 0) : this.props.amount)}
                                                                        thousandSeparator={'.'} decimalSeparator={','}
                                                                        suffix={' VNĐ'}
                                                                        placeholder='Số tiền'
                                                                        onValueChange={(values) => this.props.handlerInputAmount(values.value)}
                                                                        allowNegative={false}
                                                                    />
                                                                </div>
                                                                <i onClick={() => this.editButton("txtInputAmount")}><img
                                                                    src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                            </>
                                                            )}

                                                        </div>
                                                    </div>
                                                }
                                                {(this.props.proccessType === 'Surrender') &&(
                                                    <>
                                                    <div className="info__content-item">
                                                        <div className="input">
                                                            <div className="input__content">
                                                                {this.props.reason &&
                                                                    <label style={{ marginLeft: '2px' }}>Lý do chấm dứt hợp đồng trước hạn</label>
                                                                }
                                                                {/* <input type="search" placeholder="Lý do chấm dứt hợp đồng trước hạn" maxLength="200"
                                                                    value={this.props.reason}
                                                                    onChange={(e) => this.props.handlerInputReason(e.target.value)} /> */}
                                                                <textarea className='eclaim-text-area' rows="4" placeholder="Nhập lý do chấm dứt Hợp đồng trước hạn*" maxLength="1000" value={this.props.reason}
                                                                onChange={(e) => this.props.handlerInputReason(e.target.value)} />
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                        </div>
                                                        
                                                    </div>
                                                    <p style={{ marginBottom: '8px', paddingBottom: '8px' }}>*{this.props.reason.length}/1000 kí tự</p>
                                                    </>
                                                )
                                                }
                                                {(this.props.proccessType === 'Premium Refund') &&(
                                                    <>
                                                    <div className="info__content-item">
                                                        <div className="input">
                                                            <div className="input__content">
                                                                {this.props.reason &&
                                                                    <label style={{ marginLeft: '2px' }}>Lý do nhận phí dư</label>
                                                                }
                                                                {/* <input type="search" placeholder="Lý do chấm dứt hợp đồng trước hạn" maxLength="200"
                                                                    value={this.props.reason}
                                                                    onChange={(e) => this.props.handlerInputReason(e.target.value)} /> */}
                                                                <textarea className='eclaim-text-area' rows="4" placeholder="Lý do nhận phí dư" maxLength="1000" value={this.props.reason}
                                                                onChange={(e) => this.props.handlerInputReason(e.target.value)} />
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                        </div>
                                                        
                                                    </div>
                                                    <p style={{ marginBottom: '8px', paddingBottom: '8px' }}>*{this.props.reason.length}/1000 kí tự</p>
                                                    </>
                                                )
                                                }
                                            
                                            {this.props.errorMessage &&
                                                <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                    {this.props.errorMessage}
                                                </span>
                                            }

                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
                {!getSession(IS_MOBILE) &&
                <div className="other_option" id="other-option-toggle"
                    onClick={() => this.props.goBack()}>
                    <p>Tiếp tục</p>
                    <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                </div>
                }

            </>
        )
    }
}

export default PaymentType;
