import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession } from '../../util/common';
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING
  } from '../../constants';
  import {isEmpty} from "lodash";

class PaymentTypePartialWithdrawIL extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proccessType: this.props.proccessType
        };
    }
    componentDidMount() {
        if (isEmpty(this.props.FundList) && !isEmpty(this.props.ClientProfile)) {
            let fundList = [];
            for (let i= 0; i < this.props.ClientProfile.length; i++) {
                if (this.props.ClientProfile[i]?.fund_val_each_funf.split(';')[1] && (this.props.ClientProfile[i]?.fund_val_each_funf.split(';')[1] !== '-')) {
                    let fund = {
                        FundId: this.props.ClientProfile[i]?.fund_id.split(';')[1],							//Mã quỹ
                        FundName: this.props.ClientProfile[i]?.FundNameCode.split(';')[1],						//Tên quỹ
                        FundValue: '',
                        OldFundValue: this.props.ClientProfile[i]?.fund_val_each_funf.split(';')[1],
                        errorMessage: ''
                    }
                    fundList.push(fund);
                }
            }
            this.props.handlerUpdateFundList(fundList);
        }
    }
    render() {
        return (
            <>
                <img style={{ minWidth: '100%', marginTop: '0', marginBottom: '0' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                <div className="contractform__body">
                    <div className="info">
                        <div className="info__title nd13-padding-bottom16">RÚT MỘT PHẦN GIÁ TRỊ QUỸ HỢP ĐỒNG</div>
                        <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                <div className="tab">
                                        <div className="info__content">
                                            
                                                {this.props.FundList && (this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL') && this.props.FundList.map((item, index) => (
                                                    <>
                                                    <div className="info__content-item">
                                                        <div className={item.errorMessage?"input money validate":"input money"}>
                                                            <div className="input__content">
                                                                {item?.FundValue&&
                                                                    <label>Số tiền rút {item?.FundName}</label>
                                                                }
                                                                <NumberFormat 
                                                                    id='txtInputAmount'
                                                                    placeholder={'Số tiền rút ' + item?.FundName}
                                                                    type='seach'
                                                                    value={item?.FundValue}
                                                                    thousandSeparator={'.'} decimalSeparator={','}
                                                                    suffix={' VNĐ'}
                                                                    onValueChange={(values) => this.props.handlerOnChangeFundLValue(index, values.value)}
                                                                    allowNegative={false}
                                                                />

                                                            </div>
                                                            <i onClick={() => this.editButton("txtInputAmount")}><img
                                                                src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                                        </div>
                                                    </div>
                                                    {item.errorMessage && (this.props.FundList.length > 0) &&
                                                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                            {item.errorMessage}
                                                        </span>
                                                    }
                                                    </>
                                                ))}
                                                {!getSession(IS_MOBILE) &&
                                                    <>
                                                        {this.props.errorMessage &&
                                                            <p className='payment-main-err'>
                                                                <span style={{ color: 'red', lineHeight: '20px', marginTop: '0', marginBottom: '16px', marginTop: '16px', verticalAlign: 'top', textAlign: 'center' }}>
                                                                    {this.props.errorMessage}
                                                                </span>
                                                            </p>
                                                        }
                                                        {this.props.errorMessage ? (
                                                            <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', border: '1px solid red', marginBottom: '12px' }}>
                                                                <div className="item">
                                                                    <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '12px' }}>Số tiền</p>
                                                                    <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '12px' }}>{this.props.amount && (this.props.amount > 0) ? this.props.amount?.toLocaleString('en-US').replace(/,/g, '.') : '-'} VNĐ</p>
                                                                </div>
                                                            </div>
                                                        ) : (
                                                            <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px' }}>
                                                                <div className="item">
                                                                    <p className="item-label simple-brown" style={{ lineHeight: '24px' }}>Số tiền</p>
                                                                    <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px' }}>{this.props.amount && (this.props.amount > 0) ? this.props.amount?.toLocaleString('en-US').replace(/,/g, '.') : '-'} VNĐ</p>
                                                                </div>
                                                            </div>
                                                        )}
                                                    </>
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

export default PaymentTypePartialWithdrawIL;
