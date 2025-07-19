import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession, formatFullName, getMapSize } from '../../util/common';

import {
    FE_BASE_URL,
    IS_MOBILE
  } from '../../constants';

class ProductCategoryGroupByLIReview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            // noValidPolicy: false,
        };
    }

    render() {
        const choosePolicy = (PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading) => {
            this.props.showCardInfo(PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, false);
        }

        const closeNoValidPolicy=()=> {
            this.setState({noValidPolicy: false});
        }
       
        let t = 0;
        let msg = '';
        let imgPath = '';
        if (this.state.noValidPolicy) {
            msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi số tiền bảo hiểm/Chấm dứt sản phẩm bổ trợ. Quý khách vui lòng kiểm tra lại.';
            imgPath = FE_BASE_URL + '/img/popup/no-policy.svg';
        }

        return (
            <div className="tthd-main">
                <div className="tthd-main__wrapper" style={{minHeight: '100%'}}>
                <div className="contract-products" style={{display: 'block', width: '100%'}}>
                    {Object.entries(this.props.tickDecreaseSAMap).map(([key, value]) => (
                        <div className="contractform__head2" style={{padding: '8px'}} key={key + '-sa'}>
                            <h3 className="contractform__head-title" style={{textTransform: 'unset'}}>{value?.ProductName} - {formatFullName(value?.PolicyLIName)}</h3>
                            {this.props.tickDecreaseSAMap[key] &&
                                <div className="info__content-item">
                                    <div className="input money disabled">
                                        <div className="input__content">
                                            {this.props.faceAmountMap[key] &&
                                                <label>Số tiền bảo hiểm mới</label>
                                            }
                                            <NumberFormat
                                                id='txtInputAmount'
                                                inputmode="numeric"
                                                value={this.props.faceAmountMap[key]?this.props.faceAmountMap[key]: ''}
                                                thousandSeparator={'.'} decimalSeparator={','}
                                                suffix={' VNĐ'}
                                                allowNegative={false}
                                                placeholder='Số tiền bảo hiểm mới'
                                            />
                                        </div>
                                    </div>
                                </div>
                            }

                        </div>
                    ))}
                    {Object.entries(this.props.tickCancelRiderMap).map(([key, value]) => (
                        <div className="contractform__head2" style={{padding: '8px'}} key={key + '-rider'}>
                            <h3 className="contractform__head-title" style={{textTransform: 'unset'}}>{value?.ProductName} - {formatFullName(value?.PolicyLIName)}</h3>
								 <div className="input-wrapper-item">
									<div className="input"
										style={{
											height: '59px',
											paddingTop: '10px',
											background: '#EDEBEB'
										}}>
										<div className="input__content">
											<input className="basic-light-black"
												value='Chấm dứt sản phẩm'
												type="search"
												style={{padding: '0px'}}
												disabled/>
										</div>
									</div>
								</div>

                        </div>
                    ))}
                    {Object.entries(this.props.tickBenefitMap).map(([key, value]) => {
                        t++;
                        return (
                        <div className="contractform__head2" style={{padding: '8px'}} key={key + '-rider'}>
                            {t === 1 &&
                                <h3 className="contractform__head-title" style={{textTransform: 'unset'}}>{value?.ProductName?.split('-')[0]} - {formatFullName(value?.PolicyLIName)}</h3>
                            }
								 <div className="input-wrapper-item">
									<div className="input"
										style={{
											height: '59px',
											paddingTop: '10px',
											background: '#EDEBEB'
										}}>
										<div className="input__content">
											<input className="basic-light-black"
												value={(value?.PlanCode?.indexOf("HI") >= 0)?'Chấm dứt Quyền lợi điều trị Nội trú': ((value?.PlanCode?.indexOf("HO") >= 0)?'Chấm dứt Quyền lợi điều trị Ngoại trú':'Chấm dứt Quyền lợi Chăm sóc răng')}
												type="search"
												style={{padding: '0px'}}
												disabled/>
										</div>
									</div>
								</div>

                        </div>
                    )
                    
                    })}
                </div>
                    {!getSession(IS_MOBILE) &&
                    <div className="other_option" id="other-option-toggle"
                        onClick={() => this.props.goBack()}>
                        <p>Tiếp tục</p>
                        <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                    </div>
                    }
                </div>
            </div>
        )
    }
}

export default ProductCategoryGroupByLIReview;
