import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession, trackingEvent, getUrlParameter, cpSaveLogSDK } from '../../util/common';
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING_REVIEW,
    PageScreen
  } from '../../constants';
import iconArrowLeftBrown from '../../img/icon/arrow-left-brown.svg';
import iconArrowDownBrown from '../../img/icon/arrow-down-bronw.svg';

class PaymentTypeReview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dropdownFundIL: false
        };
    }
    componentDidMount() {
        let from = getUrlParameter("fromApp");
        cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`,
            from
        );
    }

    render() {
        const dropdownFundILDetails=()=> {
            this.setState({dropdownFundIL: !this.state.dropdownFundIL});
        }
        return (
            <>
                <img style={{ minWidth: '100%', marginTop: '0', marginBottom: '0' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                <div className="contractform__body">
                    <div className="info">
                        <div className="info__title nd13-padding-bottom16">{(this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL')?'RÚT MỘT PHẦN GIÁ TRỊ QUỸ HỢP ĐỒNG': CATEGORY_NAME_MAPPING_REVIEW[this.props.proccessType]}</div>
                        <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                <div className="tab">
                                        <div className="info__content">
                                            <div className="info__content-item">
                                            {this.props.proccessType === 'Surrender'?(
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {this.props.reason &&
                                                                <input type="search" placeholder="Chấm dứt Hợp đồng bảo hiểm" maxLength="200"
                                                                value={'Chấm dứt Hợp đồng bảo hiểm'}
                                                                disabled
                                                                 />
                                                            }
                                                        </div>
                                                        <i><img src={FE_BASE_URL  + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                ):(
                                                    <>
                                                    <div className="input money disabled">
                                                        <div className="input__content">
                                                            <label>Số tiền yêu cầu</label>
                                                            <NumberFormat className="basic-uppercase"
                                                                id='txtInputAmount'
                                                                // inputmode="numeric"
                                                                type='seach'
                                                                value={this.props.amount ? this.props.amount : parseInt(this.props.selectedItem?.PrincIntAmt?this.props.selectedItem?.PrincIntAmt?.replaceAll('.', ''): 0)}
                                                                thousandSeparator={'.'} decimalSeparator={','}
                                                                suffix={' VNĐ'}
                                                                allowNegative={false}
                                                                disabled
                                                            />

                                                        </div>
                                                        <i onClick={() => this.editButton("txtInputAmount")}><img
                                                            src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                    </div>
                                                    {this.props.FundList && (this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL') && 
                                                    <div className={this.state.dropdownFundIL?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                                        <div className="dropdown__content"
                                                                onClick={() => dropdownFundILDetails()}>
                                                            <p className="card-dropdown-title" style={{
                                                                padding: '10px 0px',
                                                                fontSize: '1.6rem',
                                                                color: '#985801'
                                                            }}>{(this.state.dropdownFundIL === true)?'Thu gọn':'Chi tiết'}
                                                            <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.dropdownFundIL === true) ?
                                                                <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                                <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                                }
                                                            </span>
                                                            </p>
                                                        </div>
                                                        <div style={{borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                                        {this.state.dropdownFundIL && this.props.FundList.map((item, index) => (
                                                            item?.FundValue&&
                                                            <div>
                                                                <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0'}}>
                                                                    <p className='simple-brown-minwidth'>Số tiền rút {item?.FundName}</p>
                                                                    <p className='simple-brown-align-right'>{parseInt(item?.FundValue)?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                                </div>
                                                            </div>
                                                        ))}
                                                        </div>
                                                    </div>
                                                    }
                                                    {this.props.proccessType === 'Partial Withdrawal' &&
                                                        (this.props.selectedItem?.PolicyClassCD === 'IL'?(
                                                            <p style={{ marginTop: '12px', lineHeight: '17px', fontStyle: 'italic'}}>Số tiền thực nhận sẽ thay đổi nếu có phát sinh phí rút một phần giá trị quỹ.</p>
                                                        ):(
                                                            <p style={{ marginTop: '12px', lineHeight: '17px', fontStyle: 'italic'}}>Số tiền thực nhận sẽ thay đổi nếu có phát sinh phí rút một phần giá trị tài khoản.</p>
                                                        ))
                                                        
                                                    }
                                                    </>
                                                )}
                                            </div>
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

export default PaymentTypeReview;
