import React, {Component} from 'react';
import { formatFullName } from '../../util/common';
import iconArrowLeftBrown from '../../img/icon/arrow-left-brown.svg';
import iconArrowDownBrown from '../../img/icon/arrow-down-bronw.svg';
import { getSession } from '../sdkCommon';
import { IS_MOBILE } from '../../constants';

class PaymentMethodReview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dropdownFeeDetailsMap: {}
        };
    }

    componentDidMount () {

    }



    render() {
        let transfer = this.props.paymentMethod?.transfer;
        let payInsurance = this.props.paymentMethod?.payInsurance;
        let refundLoan = this.props.paymentMethod?.refundLoan;
        let APL = this.props.paymentMethod?.APL;
        let premiumRefund = this.props.paymentMethod?.premiumRefund;
        console.log('this.props.checkedPaymentMethod=',this.props.checkedPaymentMethod);
        const dropdownFeeDetails = (key) => {
            let jsonState = this.state;
            if (jsonState.dropdownFeeDetailsMap[key]) {
                jsonState.dropdownFeeDetailsMap[key] = false;
            } else {
                jsonState.dropdownFeeDetailsMap[key] = true;
            }
            this.setState(jsonState);
        }
        return (
            <>
                <div className="contractform__body">
                    <div className="info">
                        <div className="info__title nd13-padding-bottom16">PHƯƠNG THỨC NHẬN TIỀN</div>
                        <div className="contractform__head-content">
                            <div className="item">
                            </div>
                        </div>
                        <div className="info__body">
                            {this.props.checkedPaymentMethod?.includes('P6') &&
                                <div className="checkbox-wrap basic-column" style={{marginBottom: '16px'}}>
                                    <div className="input disabled">
                                        <div className="input__content">
                                            <input type="search" maxLength="200"
                                                value='Đóng phí bảo hiểm'
                                                disabled
                                                />
                                        </div>
                                    </div>

                                    <div className={this.state.dropdownFeeDetailsMap['P6']?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                        <div className="dropdown__content"
                                                onClick={() => dropdownFeeDetails('P6')}>
                                            <p className="card-dropdown-title" style={{
                                                padding: '10px 0px',
                                                fontSize: '1.6rem',
                                                color: '#985801'
                                            }}>{(this.state.dropdownFeeDetailsMap['P6'] === true)?'Thu gọn':'Chi tiết'}
                                            <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.dropdownFeeDetailsMap['P6'] === true) ?
                                                <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                }
                                            </span>
                                            </p>
                                        </div>
                                        <div style={{borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                        {this.state.dropdownFeeDetailsMap['P6'] && payInsurance && payInsurance?.polInfoList.map((item, index) => (
                                            <div>

                                                <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Số hợp đồng</p>
                                                    <p className='simple-brown-align-right'>{item?.PolicyNo}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>- Họ và tên BMBH</p>
                                                    <p className='simple-brown-align-right'>{item?.POName}</p>
                                                </div>
                                                <div className="card__footer-item" style={{ padding: '0px 8px', borderBottom: '0' }}>
                                                    <p className='simple-brown-minwidth'>- Số tiền</p>
                                                    <p className='simple-brown-align-right'>{parseInt((item?.Amount + '')?.replaceAll(',', ''))?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                </div>

                                            </div>
                                        ))}
                                        </div>
                                    </div>



                                </div>
                            }
                            {this.props.checkedPaymentMethod?.includes('P7') &&
                                <div className="checkbox-wrap basic-column" style={{marginBottom: '16px'}}>
                                    <div className="input disabled">
                                        <div className="input__content">
                                            <input type="search" maxLength="200"
                                                value='Trả tạm ứng từ Giá trị hoàn lại'
                                                disabled
                                                />
                                        </div>
                                    </div>

                                    <div className={this.state.dropdownFeeDetailsMap['P7']?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                        <div className="dropdown__content"
                                                onClick={() => dropdownFeeDetails('P7')}>
                                            <p className="card-dropdown-title" style={{
                                                padding: '10px 0px',
                                                fontSize: '1.6rem',
                                                color: '#985801'
                                            }}>{(this.state.dropdownFeeDetailsMap['P7'] === true)?'Thu gọn':'Chi tiết'}
                                            <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.dropdownFeeDetailsMap['P7'] === true) ?
                                                <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                }
                                            </span>
                                            </p>
                                        </div>
                                        <div style={{borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                        {this.state.dropdownFeeDetailsMap['P7'] && refundLoan && refundLoan?.polInfoList.map((item, index) => (
                                            <div>
                                                <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Số hợp đồng</p>
                                                    <p className='simple-brown-align-right'>{item?.PolicyNo}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>- Họ và tên BMBH</p>
                                                    <p className='simple-brown-align-right'>{item?.POName}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>- Số tiền</p>
                                                    <p className='simple-brown-align-right'>{parseInt((item?.Amount + '')?.replaceAll(',', ''))?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                </div>
                                            </div>
                                        ))}
                                        </div>
                                    </div>



                                </div>
                            }
                            {this.props.checkedPaymentMethod?.includes('P8') &&
                                <div className="checkbox-wrap basic-column" style={{marginBottom: '16px'}}>
                                    <div className="input disabled">
                                        <div className="input__content">
                                            <input type="search" maxLength="200"
                                                value='Trả tạm ứng đóng phí tự động'
                                                disabled
                                                />
                                        </div>
                                    </div>

                                    <div className={this.state.dropdownFeeDetailsMap['P8']?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                        <div className="dropdown__content"
                                                onClick={() => dropdownFeeDetails('P8')}>
                                            <p className="card-dropdown-title" style={{
                                                padding: '10px 0px',
                                                fontSize: '1.6rem',
                                                color: '#985801'
                                            }}>{(this.state.dropdownFeeDetailsMap['P8'] === true)?'Thu gọn':'Chi tiết'}
                                            <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.dropdownFeeDetailsMap['P8'] === true) ?
                                                <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                }
                                            </span>
                                            </p>
                                        </div>
                                        <div style={{borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                        {this.state.dropdownFeeDetailsMap['P8'] && APL && APL?.polInfoList.map((item, index) => (
                                            <div>
                                                <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Số hợp đồng</p>
                                                    <p className='simple-brown-align-right'>{item?.PolicyNo}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>- Họ và tên BMBH</p>
                                                    <p className='simple-brown-align-right'>{item?.POName}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>- Số tiền</p>
                                                    <p className='simple-brown-align-right'>{parseInt((item?.Amount + '')?.replaceAll(',', ''))?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                </div>
                                            </div>
                                        ))}
                                        </div>
                                    </div>



                                </div>
                            }

                            {this.props.checkedPaymentMethod?.includes('P3') &&
                                <div className="checkbox-wrap basic-column">
                                    <div className="input disabled">
                                        <div className="input__content">
                                            <input type="search" maxLength="200"
                                                value='Chuyển khoản qua số tài khoản'
                                                disabled
                                                />
                                        </div>
                                    </div>

                                    <div className={this.state.dropdownFeeDetailsMap['P3']?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                        <div className="dropdown__content"
                                                onClick={() => dropdownFeeDetails('P3')}>
                                            <p className="card-dropdown-title" style={{
                                                padding: '10px 0px',
                                                fontSize: '1.6rem',
                                                color: '#985801'
                                            }}>{(this.state.dropdownFeeDetailsMap['P3'] === true)?'Thu gọn':'Chi tiết'}
                                            <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.dropdownFeeDetailsMap['P3'] === true) ?
                                                <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                }
                                            </span>
                                            </p>
                                        </div>
                                        {this.state.dropdownFeeDetailsMap['P3'] && transfer &&
                                            <div style={{borderRadius: '6px', backgroundColor: '#F2DECA'}}>

                                                <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Họ và tên người nhận tiền</p>
                                                    <p className='simple-brown-align-right'>{formatFullName(transfer?.AccountName)}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Số tài khoản</p>
                                                    <p className='simple-brown-align-right'>{transfer?.AccountNumber}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Ngân hàng</p>
                                                    <p className='simple-brown-align-right'>{transfer?.BankName}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Tỉnh/ Thành phố</p>
                                                    <p className='simple-brown-align-right'>{transfer?.Province}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Chi nhánh</p>
                                                    <p className='simple-brown-align-right'>{transfer?.BranchName}</p>
                                                </div>
                                                <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                    <p className='simple-brown-minwidth'>Phòng giao dịch</p>
                                                    <p className='simple-brown-align-right'>{transfer?.BankDealingRoom}</p>
                                                </div>
                                                {transfer?.Amount &&
                                                    <div className="card__footer-item" style={{padding: '0px 8px', borderBottom: '0'}}>
                                                        <p className='simple-brown-minwidth'>Số tiền nhận</p>
                                                        <p className='simple-brown-align-right'>{parseInt(transfer?.Amount?.replaceAll(',', ''))?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                    </div>
                                                }
                                            </div>
                                        }
                                    </div>



                                </div>
                            }
                            {this.props.checkedPaymentMethod?.includes('P9') &&
                                <div className="checkbox-wrap basic-column">
                                    <div className="input disabled">
                                        <div className="input__content">
                                            {getSession(IS_MOBILE)?(
                                                <textarea className='eclaim-text-area' rows="2" maxLength="200"
                                                    value='Hoàn trả chủ thẻ hoặc chủ tài khoản thanh toán'
                                                    disabled
                                                    style={{height: 'auto', background: 'none'}}
                                                />

                                            ):(
                                                <input type="search" maxLength="200"
                                                    value='Hoàn trả chủ thẻ hoặc chủ tài khoản thanh toán'
                                                    disabled
                                                />
                                            )
                                            }

                                            

                                        </div>
                                    </div>

                                    <div className={this.state.dropdownFeeDetailsMap['P9'] ? "dropdown show" : "dropdown"} style={{ padding: '0px' }}>
                                        <div className="dropdown__content"
                                            onClick={() => dropdownFeeDetails('P9')}>
                                            <p className="card-dropdown-title" style={{
                                                padding: '10px 0px',
                                                fontSize: '1.6rem',
                                                color: '#985801'
                                            }}>{(this.state.dropdownFeeDetailsMap['P9'] === true) ? 'Thu gọn' : 'Chi tiết'}
                                                <span style={{ display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px' }}>{(this.state.dropdownFeeDetailsMap['P9'] === true) ?
                                                    <img src={iconArrowDownBrown} alt="arrow-down-icon" /> :
                                                    <img src={iconArrowLeftBrown} alt="arrow-left-icon" />
                                                }
                                                </span>
                                            </p>
                                        </div>
                                        {this.state.dropdownFeeDetailsMap['P9'] && premiumRefund &&
                                            <div style={{ borderRadius: '6px', backgroundColor: '#F2DECA' }}>

                                                {premiumRefund?.Amount &&
                                                    <div className="card__footer-item" style={{ padding: '0px 8px', borderBottom: '0' }}>
                                                        <p className='simple-brown-minwidth' style={{paddingTop: '12px'}}>Số tiền nhận</p>
                                                        <p className='simple-brown-align-right' style={{paddingTop: '12px'}}>{parseInt((premiumRefund?.Amount + '')?.replaceAll(',', ''))?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                    </div>
                                                }
                                            </div>
                                        }
                                    </div>

                                </div>
                            }
                        </div>
                    </div>
                </div>

            </>
        )
    }
}

export default PaymentMethodReview;
