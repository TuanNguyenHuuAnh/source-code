import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession, formatFullName } from '../../util/common';
import AlertPopupOriginal from '../../components/AlertPopupOriginal';
import iconArrowLeftGrey from '../../img/icon/arrow-left-grey.svg';
import iconArrowDownGrey from '../../img/icon/arrow-down-grey.svg';
import {

    FE_BASE_URL,
    IS_MOBILE
  } from '../../constants';


class ProductCategory extends Component {
    constructor(props) {
        super(props);
        this.state = {
            noValidPolicy: false,
            showProductDetailMap: {},
            tickDecreaseSAMap: {},
            tickCancelRiderMap: {},
            faceAmountMap: {}
        };
    }

    render() {
        const dropdownContent = (index) => {
            let jsonState = this.state;
            if (jsonState.showProductDetailMap[index]) {
                jsonState.showProductDetailMap[index] = false;
            } else {
                jsonState.showProductDetailMap[index] = true;
            }
            this.setState(jsonState);
        }
        const choosePolicy = (PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading) => {
            this.props.showCardInfo(PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, false);
        }

        const closeNoValidPolicy=()=> {
            this.setState({noValidPolicy: false});
        }

        const inputFaceAmount = (index, value) =>{
            console.log('value=', value);
            let jsonState = this.state;
            jsonState.faceAmountMap[index] = value;
            this.setState(jsonState);
        }

        const toggleDecreaseSA = (index) => {
            let i = index + 1;
            let tickDecreaseSAMap = this.state.tickDecreaseSAMap;
            if (tickDecreaseSAMap[index] && (tickDecreaseSAMap[index] >= 0)) {
                tickDecreaseSAMap[index] = -1;
            } else {
                tickDecreaseSAMap[index] = i;
            }
            this.setState({tickDecreaseSAMap: tickDecreaseSAMap});
        }

        const toggleCancelRider = (index) => {
            let i = index + 1;
            let tickCancelRiderMap = this.state.tickCancelRiderMap;
            if (tickCancelRiderMap[index] && (tickCancelRiderMap[index] >= 0)) {
                tickCancelRiderMap[index] = -1;
            } else {
                tickCancelRiderMap[index] = i;
            }
            this.setState({tickCancelRiderMap: tickCancelRiderMap});
        }
        
        let msg = '';
        let imgPath = '';
        if (this.state.noValidPolicy) {
            msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi số tiền bảo hiểm/Chấm dứt sản phẩm bổ trợ. Quý khách vui lòng kiểm tra lại.';
            imgPath = FE_BASE_URL + '/img/popup/no-policy.svg';
        }

        return (
            <div className="tthd-main">
                <div className="tthd-main__wrapper">
                {this.state.noValidPolicy && (
                    <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
                        <div className="insurance">
                            <div className="empty">
                                <div className={this.state.noValidPolicy ? "picture" : "icon"}
                                        style={{marginTop: '-45px'}}>
                                    <img src={imgPath} alt=""/>
                                </div>
                                {this.state.noValidPolicy ? (
                                    <h5 className='msg-alert-fixed' style={{width: '290px'}}>{msg}</h5>
                                ) : (
                                    <p style={{paddingTop: '20px'}}>{msg}</p>
                                )}


                            </div>
                        </div>
                    </div>
                )}
                <div className="contract-products" style={{display: 'block', zIndex: 0}}>
                    {(this.props.ClientProfileProducts !== null
                        && this.props.ClientProfileProducts !== ''
                        && this.props.ClientProfileProducts !== undefined) && this.props.ClientProfileProducts.map((item, index) => (
                        <div className="card-extend-container">
                            <div className="card-extend-wrapper">
                                {(item.IsMainCvg === '1') ? (

                                        <>
                                        <div className="item">
                                            <div className="card__label">
                                                <p className="basic-bold">Bảo hiểm chính</p>
                                            </div>
                                            <div className="card">
                                                <div className="card__header">
                                                    <h4 className="basic-bold">{item.ProductName}</h4>
                                                </div>

                                                    <div className="card__footer">
                                                        <div className="card__footer-item dropdown show"
                                                                id={'dropdownContent' + index}
                                                                style={{
                                                                    borderBottom: '1px solid #e7e7e7',
                                                                    padding: '0px'
                                                                }}>
                                                            <div className="dropdown__content">
                                                                <p className="card-dropdown-title"
                                                                    style={{
                                                                        color: '#292929',
                                                                        padding: '12px 12px',
                                                                        margin: '0px'
                                                                    }}>{formatFullName(item.PolicyLIName)}</p>
                                                                <p className="arrow" onClick={() => dropdownContent(index)}>
                                                                    {this.state.showProductDetailMap[index]?(
                                                                        <img style={{width: '16px', height: 'auto'}}
                                                                        src={iconArrowDownGrey}
                                                                        alt="arrow-down-icon"/>
                                                                    ):(
                                                                        <img style={{width: '16px', height: 'auto'}}
                                                                        src={iconArrowLeftGrey}
                                                                        alt="arrow-left-icon"/>
                                                                    )}

                                                                </p>
                                                            </div>
                                                            {this.state.showProductDetailMap[index] &&
                                                                <div
                                                                className="card__footer dropdown__items2">
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Tình trạng</p>
                                                                    <div className="dcstatus">
                                                                        {(item.PolicyStatus === 'Hết hiệu lực'
                                                                            || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                                            <p className="inactive">{item.PolicyStatus}</p>
                                                                        ) : (
                                                                            <p className="active">{item.PolicyStatus}</p>
                                                                        )}
                                                                    </div>
                                                                </div>
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Số tiền bảo hiểm</p>
                                                                    {item.FaceAmount === 'Chi tiết theo quy tắc điều khoản' ?
                                                                        <p>Chi tiết theo điều khoản Hợp
                                                                            đồng</p> :
                                                                        <p>{item.FaceAmount}</p>
                                                                    }
                                                                </div>
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                    <p>{item.PolMPremAmt}</p>
                                                                </div>
                                                                <div className='dash-line-esubmission' style={{padding: '12px', margin: '12px'}}></div>
                                                                <div className="info__body"  style={{padding: '12px'}}>
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'decrease-sa' + index}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'decrease-sa' + index}
                                                                                        id={'decrease-sa' + index}
                                                                                        checked={this.state.tickDecreaseSAMap[index] > 0 ? true: false}
                                                                                        onClick={()=>toggleDecreaseSA(index)}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2">Thay đổi số tiền bảo hiểm</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                    {this.state.tickDecreaseSAMap[index] > 0 &&
                                                                    <div className="info__content-item">
                                                                        <div className="input money">
                                                                            <div className="input__content">
                                                                                <label>Số tiền</label>
                                                                                <NumberFormat className="basic-uppercase"
                                                                                    id='txtInputAmount'
                                                                                    inputmode="numeric"
                                                                                    value={this.state.faceAmountMap[index]?this.state.faceAmountMap[index]: 0}
                                                                                    thousandSeparator={'.'} decimalSeparator={','}
                                                                                    suffix={' VNĐ'}
                                                                                    onValueChange={(values)=>inputFaceAmount(index, values.value)}
                                                                                    allowNegative={false}
                                                                                />
                                                                            </div>
                                                                            <img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" />
                                                                        </div>
                                                                    </div>
                                                                    }
                                                                </div>

                                                            </div>
                                                            }

                                                        </div>
                                                    </div>
                                                
                                            </div>
                                        </div>

                                        </>
                                    
                                ) : (
                                    <div className="item">
                                        <div className="card__label card__label-brown">
                                            <p className="basic-bold">Bảo hiểm BỔ SUNG</p>
                                        </div>
                                        <div className="card">
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                            </div>

                                                <div className="card__footer">
                                                    <div className="card__footer-item dropdown show"
                                                            id={'dropdownContent' + index}
                                                            style={{
                                                                borderBottom: '1px solid #e7e7e7',
                                                                padding: '0px'
                                                            }}>
                                                        <div className="dropdown__content">
                                                            <p className="card-dropdown-title"
                                                                style={{
                                                                    color: '#292929',
                                                                    padding: '12px 12px',
                                                                    margin: '0px'
                                                                }}>{formatFullName(item.PolicyLIName)}
                                                                </p>
                                                                <p className="arrow" onClick={() => dropdownContent(index)}>
                                                                    {this.state.showProductDetailMap[index]?(
                                                                        <img style={{width: '16px', height: 'auto'}}
                                                                        src={iconArrowDownGrey}
                                                                        alt="arrow-down-icon"/>
                                                                    ):(
                                                                        <img style={{width: '16px', height: 'auto'}}
                                                                        src={iconArrowLeftGrey}
                                                                        alt="arrow-left-icon"/>
                                                                    )}

                                                                </p>
                                                        </div>
                                                        {this.state.showProductDetailMap[index] &&
                                                        <div
                                                            className="card__footer dropdown__items2">
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Tình trạng</p>
                                                                <div className="dcstatus">
                                                                    {(item.PolicyStatus === 'Hết hiệu lực'
                                                                        || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                                        <p className="inactive">{item.PolicyStatus}</p>
                                                                    ) : (
                                                                        <p className="active">{item.PolicyStatus}</p>
                                                                    )}
                                                                </div>
                                                            </div>
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Số tiền bảo hiểm</p>
                                                                {item.FaceAmount === 'Chi tiết theo quy tắc điều khoản' ?
                                                                    <p>Chi tiết theo điều khoản Hợp
                                                                        đồng</p> :
                                                                    <p>{item.FaceAmount}</p>
                                                                }
                                                            </div>
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                <p>{item.PolMPremAmt}</p>
                                                            </div>
                                                            <div className='dash-line-esubmission' style={{padding: '12px', margin: '12px'}}></div>
                                                                {item.PolicyGroup === '2'?(
                                                                <div className="info__body"  style={{padding: '12px'}}>
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                {this.state.tickCancelRiderMap[index] > 0 ? (
                                                                                    <label className="checkbox2" htmlFor={'decrease-sa' + index}>
                                                                                        <input
                                                                                            type="checkbox"
                                                                                            name={'decrease-sa' + index}
                                                                                            id={'decrease-sa' + index}
                                                                                            checked={false}
                                                                                        />
                                                                                        <div className="checkmark">
                                                                                            <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                        </div>
                                                                                    </label>
                                                                                ):(
                                                                                    <label className="checkbox" htmlFor={'decrease-sa' + index}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'decrease-sa' + index}
                                                                                        id={'decrease-sa' + index}
                                                                                        checked={this.state.tickDecreaseSAMap[index] > 0 ? true: false}
                                                                                        onClick={()=>toggleDecreaseSA(index)}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                )}

                                                                                <p className="basic-text2">Thay đổi số tiền bảo hiểm</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                        </div>
                                                                        {this.state.tickDecreaseSAMap[index] > 0 &&
                                                                        <div className="info__content-item">
                                                                            <div className="input money">
                                                                                <div className="input__content">
                                                                                    <label>Số tiền</label>
                                                                                    <NumberFormat className="basic-uppercase"
                                                                                        id='txtInputAmount'
                                                                                        inputmode="numeric"
                                                                                        value={0}
                                                                                        thousandSeparator={'.'} decimalSeparator={','}
                                                                                        suffix={' VNĐ'}
                                                                                        //onValueChange={this.inputAmountChangedHandler}
                                                                                        allowNegative={false}
                                                                                    />
                                                                                </div>
                                                                                <img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" />
                                                                            </div>
                                                                        </div>
                                                                        }
                                                                        {this.state.tickDecreaseSAMap[index] > 0? (
                                                                                <div className="checkbox-wrap basic-column">
                                                                                    <div className="tab-wrapper">
                                                                                        <div className="tab"> {/* Thêm key vào đây */}
                                                                                            <div className="checkbox-warpper">
                                                                                                <label className="checkbox2" htmlFor={'cancel-rider' + index}>
                                                                                                    <input
                                                                                                        type="checkbox"
                                                                                                        name={'cancel-rider' + index}
                                                                                                        id={'cancel-rider' + index}
                                                                                                        checked={false}
                                                                                                    />
                                                                                                    <div className="checkmark">
                                                                                                        <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                                    </div>
                                                                                                </label>
                                                                                                <p className="basic-text2">Chấm dứt sản phẩm</p>
                                                                                            </div>
                                                                                        </div>

                                                                                    </div>

                                                                                </div>
                                                                        ):(
                                                                            <div className="checkbox-wrap basic-column">
                                                                                <div className="tab-wrapper">
                                                                                    <div className="tab"> {/* Thêm key vào đây */}
                                                                                        <div className="checkbox-warpper">
                                                                                        <label className="checkbox" htmlFor={'cancel-rider' + index}>
                                                                                            <input
                                                                                                type="checkbox"
                                                                                                name={'cancel-rider' + index}
                                                                                                id={'cancel-rider' + index}
                                                                                                checked={this.state.tickCancelRiderMap[index] > 0 ? true: false}
                                                                                                onClick={()=>toggleCancelRider(index)}
                                                                                            />
                                                                                            <div className="checkmark">
                                                                                            <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                            </div>
                                                                                        </label>
                                                                                        <p className="basic-text2">Chấm dứt sản phẩm</p>
                                                                                        </div>
                                                                                    </div>

                                                                                </div>

                                                                            </div>
                                                                        )}

                                                                </div>
                                                                ):(
                                                                (item.PolicyGroup === '3') ?(
                                                                <div className="info__body"  style={{padding: '12px'}}>
                                                                    {item.PlanCode && (item.PlanCode.indexOf("HI") >= 0) &&
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'inpatient' + index}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'inpatient' + index}
                                                                                        id={'inpatient' + index}
                                                                                        checked={false}
                                                                                        // onClick={()=>toggleResidence()}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2">Quyền lợi điều trị Nội trú</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                    }
                                                                    {item.PlanCode && (item.PlanCode.indexOf("HO") >= 0) &&
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'outpatient' + index}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'outpatient' + index}
                                                                                        id={'outpatient' + index}
                                                                                        checked={false}
                                                                                        // onClick={()=>toggleResidence()}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2">Quyền lợi điều trị Ngoại trú</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                    }
                                                                    {item.PlanCode && (item.PlanCode.indexOf("HD") >= 0) &&
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'dental-care' + index}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'dental-care' + index}
                                                                                        id={'dental-care' + index}
                                                                                        checked={false}
                                                                                        // onClick={()=>toggleResidence()}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2">Quyền lợi Chăm sóc răng</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                    }
                                                                </div>
                                                                ):(
                                                                    (item.PolicyGroup === '4') &&
                                                                        <div className="info__body"  style={{padding: '12px'}}>
                                                                            <div className="checkbox-wrap basic-column">
                                                                                <div className="tab-wrapper">
                                                                                    <div className="tab"> {/* Thêm key vào đây */}
                                                                                        <div className="checkbox-warpper">
                                                                                        <label className="checkbox" htmlFor={'cancel-rider' + index}>
                                                                                            <input
                                                                                                type="checkbox"
                                                                                                name={'cancel-rider' + index}
                                                                                                id={'cancel-rider' + index}
                                                                                                checked={this.state.tickCancelRiderMap[index] > 0 ? true: false}
                                                                                                onClick={()=>toggleCancelRider(index)}
                                                                                            />
                                                                                            <div className="checkmark">
                                                                                            <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                            </div>
                                                                                        </label>
                                                                                        <p className="basic-text2">Chấm dứt sản phẩm</p>
                                                                                        </div>
                                                                                    </div>

                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                )
                                                                )}

                                                        </div>
                                                        }
                                                    </div>
                                                </div>
                                            
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
                    {!getSession(IS_MOBILE) &&
                    <div className="other_option" id="other-option-toggle"
                        onClick={() => this.props.goBack()}>
                        <p>Tiếp tục</p>
                        <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                    </div>
                    }
                {this.state.noValidPolicy &&
                    <AlertPopupOriginal closePopup={()=>closeNoValidPolicy()} msg={msg} imgPath={imgPath}/>
                         
                }
                </div>
            </div>
        )
    }
}

export default ProductCategory;
