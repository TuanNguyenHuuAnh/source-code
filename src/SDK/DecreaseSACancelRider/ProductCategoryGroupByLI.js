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
import iconArrowLeftBrown from '../../img/icon/arrow-left-brown.svg';
import iconArrowDownBrown from '../../img/icon/arrow-down-bronw.svg';
import AgreeChangePopup from '../../components/AgreeChangePopup';
import AgreeCancelPopup from '../../components/AgreeCancelPopup';

class ProductCategoryGroupByLI extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showNotice: '',
            cancelRiderNotice: '',
            liIndex: '',
            liProfiles: null
        };
    }

    render() {
        console.log('showNotice=' + this.state.showNotice + ' hcType=' + this.state.hcType);
        console.log('this.props.tickBenefitMap=', this.props.tickBenefitMap);
        const closeNotice = () => {
            this.setState({showNotice: ''});
        }

        const agreeCancelRider = () => {
            this.setState({cancelRiderNotice: ''});
            this.props.handlerUntickDecreaseSA(this.state.cancelRiderNotice);
        }

        const notAgreeCancelRider = () => {
            if (this.props.tickCancelRiderMap[this.state.cancelRiderNotice]) {
                this.props.handlerUntickCancelRider(this.state.cancelRiderNotice);
                this.setState({cancelRiderNotice: ''});
            }
        }

        const toggleCancelRider = (index, product) => {
            if (!this.props.tickCancelRiderMap[index]) {
                // this.props.handlerUntickDecreaseSA(index);
                this.setState({cancelRiderNotice: index});
            }
            this.props.toggleCancelRider(index, product);
        }

        const notAgreeNotice = () => {
            let liHCMa = this.props.liHCMap;

            
            if (liHCMa[this.state.showNotice]) {
                let profiles = this.state.liProfiles;
                let index = this.state.liIndex;
                if (profiles && index) {
                    for (const idx in profiles) {
                        this.props.toggleBenefit(index + '-' + idx, '');
                    }
                }

                liHCMa[this.state.showNotice] = 0;
                this.props.handlerUpdateliHCMap(liHCMa);
                this.setState({liIndex: '', liProfiles: null});
            }
            liHCMa[this.state.showNotice] = 0;
            this.setState({showNotice: '', liHCMap: liHCMa});
        }

        
        const openNotice=(index, product, hcType, profiles)=> {
             
            let liHCMa = this.props.liHCMap;
            if (!liHCMa[product.LifeInsureID]) {
                if (hcType === 1) {
                    for (const idx in profiles) {
                        this.props.toggleBenefit(index + '-' + idx, profiles[idx]);
                    }
                } else if (hcType === 2) {
                    for (const idx in profiles) {
                        if (profiles[idx]?.PlanCode.indexOf("HI") >= 0) {
                            continue;
                        }
                        this.props.toggleBenefit(index + '-' + idx, profiles[idx]);
                    }
                } else if (hcType === 3) {
                    for (const idx in profiles) {
                        if ((profiles[idx]?.PlanCode.indexOf("HI") >= 0) || (profiles[idx]?.PlanCode.indexOf("HO") >= 0)) {
                            continue;
                        }
                        this.props.toggleBenefit(index + '-' + idx, profiles[idx]);
                    }
                }
                
                liHCMa[product.LifeInsureID] = hcType;
                this.props.handlerUpdateliHCMap(liHCMa);
                this.setState({showNotice: product.LifeInsureID, hcType: hcType, liIndex: index, liProfiles: profiles});
            } else {
                let keys = [];
                for (const idx in profiles) {
                    keys.push(index + '-' + idx);
                }
                this.props.handlerDeleteTickBenefitMapItem(keys);
                if (liHCMa[product.LifeInsureID] === hcType) {
                    // for (const idx in profiles) {
                    //     this.props.toggleBenefit(index + '-' + idx, '');
                    // }
                    liHCMa[product.LifeInsureID] = 0;
                    this.props.handlerUpdateliHCMap(liHCMa);
                    this.setState({liIndex: '', liProfiles: null});
                } else {
                    if (hcType === 1) {
                        for (const idx in profiles) {
                            console.log('handlerSetBenefit key=',index + '-' + idx);
                            this.props.handlerSetBenefit(index + '-' + idx, profiles[idx]);
                        }
                    } else if (hcType === 2) {
                        for (const idx in profiles) {
                            if (profiles[idx]?.PlanCode.indexOf("HI") >= 0) {
                                continue;
                            }
                            this.props.handlerSetBenefit(index + '-' + idx, profiles[idx]);
                        }
                    }
                    liHCMa[product.LifeInsureID] = hcType;
                    this.props.handlerUpdateliHCMap(liHCMa);
                    this.setState({showNotice: product.LifeInsureID, hcType: hcType, liIndex: index, liProfiles: profiles});
                }

            }
        }
        const choosePolicy = (PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading) => {
            this.props.showCardInfo(PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, false);
        }

        const closeNoValidPolicy=()=> {
            this.setState({noValidPolicy: false});
        }
       
        let msg = '';
        let imgPath = '';
        if (this.state.noValidPolicy) {
            msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi số tiền bảo hiểm/Chấm dứt sản phẩm bổ trợ. Quý khách vui lòng kiểm tra lại.';
            imgPath = FE_BASE_URL + '/img/popup/no-policy.svg';
        }

        return (
            <div className="tthd-main">
                <div className={getSession(IS_MOBILE)?"tthd-main__wrapper margin-left-right-16": "tthd-main__wrapper"} style={{minHeight: 'unset'}}>
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
                <div className="contract-products esubmission" style={{display: 'block', zIndex: 0, marginBottom: '16px'}}>
                    {(this.props.ClientProfileProducts !== null
                        && this.props.ClientProfileProducts !== ''
                        && this.props.ClientProfileProducts !== undefined) && this.props.ClientProfileProducts.map((item, index) => (
                        <div className="card-extend-container">
                            <div className="card-extend-wrapper">
                                {(item.IsMainCvg === '1') ? (

                                        <>
                                        <div className={getSession(IS_MOBILE)?"item card-mobile": "item"}>
                                            <div className="card__label">
                                                <p className="basic-bold">Bảo hiểm chính</p>
                                            </div>
                                            <div className="card">
                                                    {item?.Products?.map((product, t) => (
                                                    <>
                                                    {t === 0 && (
                                                        <div className="card__header">
                                                            <h4 className="basic-bold">{product?.Profiles[t]?.ProductName}</h4>
                                                        </div>
                                                    )}
                                                    <div className="card__footer">
                                                        <div className="card__footer-item dropdown show"
                                                                id={'dropdownContent' + index + '-' + t}
                                                                style={{
                                                                    borderBottom: '1px solid #e7e7e7',
                                                                    padding: '0px'
                                                                }}>
                                                            <div className="dropdown__content" onClick={() => this.props.dropdownContent(index + '-' + t)}>
                                                                <p className="card-dropdown-title"
                                                                    style={{
                                                                        color: '#292929',
                                                                        padding: '12px 12px',
                                                                        margin: '0px'
                                                                    }}>{formatFullName(product?.Profiles[0]?.PolicyLIName)}</p>
                                                                <p className="arrow">
                                                                    {this.props.showProductDetailMap[index + '-' + t]?(
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
                                                            {this.props.showProductDetailMap[index + '-' + t] &&
                                                                <div
                                                                className="card__footer dropdown__items2">
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Tình trạng</p>
                                                                    <div className="dcstatus">
                                                                        {(product?.Profiles[0]?.PolicyStatus === 'Hết hiệu lực'
                                                                            || product?.Profiles[0]?.PolicyStatus === 'Mất hiệu lực') ? (
                                                                            <p className="inactive">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                        ) : (
                                                                            <p className="active">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                        )}
                                                                    </div>
                                                                </div>
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Số tiền bảo hiểm</p>
                                                                    {product?.Profiles[0]?.FaceAmount === 'Chi tiết theo quy tắc điều khoản' ?
                                                                        <p>Chi tiết theo điều khoản Hợp
                                                                            đồng</p> :
                                                                        <p>{product?.Profiles[0]?.FaceAmount} {product?.Profiles[0]?.FaceAmount && (product?.Profiles[0]?.FaceAmount !== '-')? ' VNĐ':''}</p>
                                                                    }
                                                                </div>
                                                                <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                    <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                    <p>{product?.Profiles[0]?.PolMPremAmt} {product?.Profiles[0]?.PolMPremAmt && (product?.Profiles[0]?.PolMPremAmt !== '-')? ' VNĐ':''}</p>
                                                                </div>
                                                                <div className='dash-line-esubmission' style={{padding: '12px', margin: '12px'}}></div>
                                                                <div className="info__body"  style={{padding: '12px'}}>
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'decrease-sa' + index + '-' + t}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'decrease-sa' + index + '-' + t}
                                                                                        id={'decrease-sa' + index + '-' + t}
                                                                                        checked={this.props.tickDecreaseSAMap[index + '-' + t] ? true: false}
                                                                                        onClick={()=>this.props.toggleDecreaseSA(index + '-' + t, product?.Profiles[0])}
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
                                                                    {this.props.tickDecreaseSAMap[index + '-' + t] &&
                                                                    <>
                                                                    <div className="info__content-item">
                                                                        <div className={(this.props.faceAmountMap[index + '-' + t] && ((this.props.faceAmountMap[index + '-' + t] <= 0) || this.props.faceAmountMap[index + '-' + t] >= parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', ''))))?"input money validate":"input money"}>
                                                                            <div className="input__content">
                                                                                {this.props.faceAmountMap[index + '-' + t] &&
                                                                                    <label>Số tiền</label>
                                                                                }
                                                                                <NumberFormat
                                                                                    id='txtInputAmount'
                                                                                    inputmode="numeric"
                                                                                    value={this.props.faceAmountMap[index + '-' + t]?this.props.faceAmountMap[index + '-' + t]: ''}
                                                                                    thousandSeparator={'.'} decimalSeparator={','}
                                                                                    suffix={' VNĐ'}
                                                                                    onValueChange={(values)=>this.props.inputFaceAmount(index + '-' + t, values.value)}
                                                                                    allowNegative={false}
                                                                                    placeholder='Số tiền bảo hiểm mới'
                                                                                />
                                                                            </div>
                                                                            <img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" />
                                                                        </div>
                                                                    </div>
                                                                    {(this.props.faceAmountMap[index + '-' + t] && ((this.props.faceAmountMap[index + '-' + t] <= 0) || ( this.props.faceAmountMap[index + '-' + t] == parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', ''))))) &&
                                                                    <span>
                                                                        <p  style={{ color: 'red', lineHeight: '24px', marginTop: '8px', marginBottom: '12px', verticalAlign: 'top' }}>Vui lòng điều chỉnh số tiền bảo hiểm mới</p>
                                                                    </span>
                                                                    }
                                                                    {(this.props.faceAmountMap[index + '-' + t] && ( this.props.faceAmountMap[index + '-' + t] > parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', '')))) &&
                                                                    <span>
                                                                        <p  style={{ color: 'red', lineHeight: '24px', marginTop: '8px', marginBottom: '12px', verticalAlign: 'top' }}>Vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ tăng số tiền bảo hiểm</p>
                                                                    </span>
                                                                    }
                                                                    </>
                                                                    }
                                                                </div>

                                                            </div>
                                                            }

                                                        </div>
                                                    </div>
                                                    </>
                                                    ))}
                                            </div>
                                        </div>

                                        </>
                                    
                                ) : (
                                    item.PolicyGroup !== '3' ? (
                                        <div className={getSession(IS_MOBILE)?"item card-mobile": "item"}>
                                        <div className="card__label card__label-brown">
                                            <p className="basic-bold">Bảo hiểm BỔ SUNG</p>
                                        </div>
                                        <div className="card">
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item?.Products[0]?.Profiles[0]?.ProductName}</h4>
                                            </div>
                                            {item?.Products?.map((product, t) => (
                                                <div className="card__footer">
                                                    <div className="card__footer-item dropdown show"
                                                            id={'dropdownContent' + index + '-' + t}
                                                            style={{
                                                                borderBottom: '1px solid #e7e7e7',
                                                                padding: '0px'
                                                            }}>
                                                        <div className="dropdown__content" onClick={() => this.props.dropdownContent(index + '-' + t)}>
                                                            <p className="card-dropdown-title"
                                                                style={{
                                                                    color: '#292929',
                                                                    padding: '12px 12px',
                                                                    margin: '0px'
                                                                }}>{formatFullName(product?.Profiles[0]?.PolicyLIName)}
                                                                </p>
                                                                <p className="arrow">
                                                                    {this.props.showProductDetailMap[index + '-' + t]?(
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
                                                        {this.props.showProductDetailMap[index + '-' + t] &&
                                                        <div
                                                            className="card__footer dropdown__items2">
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Tình trạng</p>
                                                                <div className="dcstatus">
                                                                    {(product?.Profiles[0]?.PolicyStatus === 'Hết hiệu lực'
                                                                        || product?.Profiles[0]?.PolicyStatus === 'Mất hiệu lực') ? (
                                                                        <p className="inactive">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                    ) : (
                                                                        <p className="active">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                    )}
                                                                </div>
                                                            </div>
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Số tiền bảo hiểm</p>
                                                                {product?.Profiles[0]?.FaceAmount === 'Chi tiết theo quy tắc điều khoản' ?
                                                                    <p>Chi tiết theo điều khoản Hợp
                                                                        đồng</p> :
                                                                    <p>{product?.Profiles[0]?.FaceAmount} {product?.Profiles[0]?.FaceAmount && (product?.Profiles[0]?.FaceAmount !== '-')? ' VNĐ':''}</p>
                                                                }
                                                            </div>
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                <p>{product?.Profiles[0]?.PolMPremAmt} {product?.Profiles[0]?.PolMPremAmt && (product?.Profiles[0]?.PolMPremAmt !== '-')? ' VNĐ':''}</p>
                                                            </div>
                                                            <div className='dash-line-esubmission' style={{padding: '12px', margin: '12px'}}></div>
                                                                {item.PolicyGroup === '2'?(
                                                                <div className="info__body"  style={{padding: '12px'}}>
                                                                    <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                {this.props.tickCancelRiderMap[index + '-' + t] ? (
                                                                                    <label className="checkbox2" htmlFor={'decrease-sa' + index + '-' + t}>
                                                                                        <input
                                                                                            type="checkbox"
                                                                                            name={'decrease-sa' + index + '-' + t}
                                                                                            id={'decrease-sa' + index + '-' + t}
                                                                                            checked={false}
                                                                                        />
                                                                                        <div className="checkmark">
                                                                                            <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                        </div>
                                                                                    </label>
                                                                                ):(
                                                                                    <label className="checkbox" htmlFor={'decrease-sa' + index + '-' + t}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'decrease-sa' + index + '-' + t}
                                                                                        id={'decrease-sa' + index + '-' + t}
                                                                                        checked={this.props.tickDecreaseSAMap[index + '-' + t] ? true: false}
                                                                                        onClick={()=>this.props.toggleDecreaseSA(index + '-' + t, product?.Profiles[0])}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                )}
                                                                                {this.props.tickCancelRiderMap[index + '-' + t] ? (
                                                                                <p className="basic-text2" style={{color: '#727272'}}>Thay đổi số tiền bảo hiểm</p>
                                                                                ):(
                                                                                    <p className="basic-text2">Thay đổi số tiền bảo hiểm</p>
                                                                                )}
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                        </div>
                                                                        {this.props.tickDecreaseSAMap[index + '-' + t] &&
                                                                        <>
                                                                        <div className="info__content-item">
                                                                            <div className={(this.props.faceAmountMap[index + '-' + t] && ((this.props.faceAmountMap[index + '-' + t] <= 0) || (this.props.faceAmountMap[index + '-' + t] >= parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', '')))))?"input money validate":"input money"}>
                                                                                <div className="input__content">
                                                                                    {this.props.faceAmountMap[index + '-' + t] &&
                                                                                        <label>Số tiền</label>
                                                                                    }
                                                                                    <NumberFormat
                                                                                        id='txtInputAmount'
                                                                                        inputmode="numeric"
                                                                                        value={this.props.faceAmountMap[index + '-' + t]?this.props.faceAmountMap[index + '-' + t]: ''}
                                                                                        thousandSeparator={'.'} decimalSeparator={','}
                                                                                        suffix={' VNĐ'}
                                                                                        onValueChange={(values)=>this.props.inputFaceAmount(index + '-' + t, values.value)}
                                                                                        allowNegative={false}
                                                                                        placeholder='Số tiền bảo hiểm mới'
                                                                                    />
                                                                                </div>
                                                                                <img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" />
                                                                            </div>
                                                                        </div>
                                                                        {(this.props.faceAmountMap[index + '-' + t] && ((this.props.faceAmountMap[index + '-' + t] <= 0) || ( this.props.faceAmountMap[index + '-' + t] == parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', ''))))) &&
                                                                        <span>
                                                                            <p style={{ color: 'red', lineHeight: '24px', marginTop: '8px', marginBottom: '12px', verticalAlign: 'top' }}>Vui lòng điều chỉnh số tiền bảo hiểm mới</p>
                                                                        </span>
                                                                        }
                                                                        {(this.props.faceAmountMap[index + '-' + t] && ( this.props.faceAmountMap[index + '-' + t] > parseInt(product.Profiles[0]?.FaceAmount?.replaceAll('.', '')))) &&
                                                                        <span>
                                                                            <p style={{ color: 'red', lineHeight: '24px', marginTop: '8px', marginBottom: '12px', verticalAlign: 'top' }}>Vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ tăng số tiền bảo hiểm</p>
                                                                        </span>
                                                                        }
                                                                        </>
                                                                        }
                                                                        <div className="checkbox-wrap basic-column">
                                                                            <div className="tab-wrapper">
                                                                                <div className="tab"> {/* Thêm key vào đây */}
                                                                                    <div className="checkbox-warpper">
                                                                                    <label className="checkbox" htmlFor={'cancel-rider' + index + '-' + t}>
                                                                                        <input
                                                                                            type="checkbox"
                                                                                            name={'cancel-rider' + index + '-' + t}
                                                                                            id={'cancel-rider' + index + '-' + t}
                                                                                            checked={this.props.tickCancelRiderMap[index + '-' + t]? true: false}
                                                                                            onClick={()=>toggleCancelRider(index + '-' + t, product?.Profiles[0])}
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
                                                                ):(
                                                                    (item.PolicyGroup === '4') &&
                                                                        <div className="info__body"  style={{padding: '12px'}}>
                                                                            <div className="checkbox-wrap basic-column">
                                                                                <div className="tab-wrapper">
                                                                                    <div className="tab"> {/* Thêm key vào đây */}
                                                                                        <div className="checkbox-warpper">
                                                                                        <label className="checkbox" htmlFor={'cancel-rider' + index + '-' + t}>
                                                                                            <input
                                                                                                type="checkbox"
                                                                                                name={'cancel-rider' + index + '-' + t}
                                                                                                id={'cancel-rider' + index + '-' + t}
                                                                                                checked={this.props.tickCancelRiderMap[index + '-' + t] ? true: false}
                                                                                                onClick={()=>toggleCancelRider(index + '-' + t, product?.Profiles[0])}
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
                                                                )}

                                                        </div>
                                                        }
                                                    </div>
                                                </div>
                                            ))}
                                            
                                        </div>
                                    </div>
                                    ):(
                                        <div className={getSession(IS_MOBILE)?"item card-mobile": "item"}>
                                        <div className="card__label card__label-brown">
                                            <p className="basic-bold">Bảo hiểm BỔ SUNG</p>
                                        </div>
                                        <div className="card">
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item?.ProductName}</h4>
                                            </div> 
                                            {item?.Products?.map((product, t) => (
                                                <div className="card__footer">
                                                    <div className="card__footer-item dropdown show"
                                                            id={'dropdownContent' + index + '-' + t}
                                                            style={{
                                                                borderBottom: '1px solid #e7e7e7',
                                                                padding: '0px'
                                                            }}>
                                                        <div className="dropdown__content" onClick={() => this.props.dropdownContent(index + '-' + t)}>
                                                            <p className="card-dropdown-title"
                                                                style={{
                                                                    color: '#292929',
                                                                    padding: '12px 12px',
                                                                    margin: '0px'
                                                                }}>{formatFullName(product?.Profiles[0]?.PolicyLIName)}
                                                                </p>
                                                                <p className="arrow">
                                                                    {this.props.showProductDetailMap[index + '-' + t]?(
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
                                                        {this.props.showProductDetailMap[index + '-' + t] &&
                                                        <div
                                                            className="card__footer dropdown__items2">
                                                            <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                <p>Tình trạng</p>
                                                                <div className="dcstatus">
                                                                    {(product?.Profiles[0]?.PolicyStatus === 'Hết hiệu lực'
                                                                        || product?.Profiles[0]?.PolicyStatus === 'Mất hiệu lực') ? (
                                                                        <p className="inactive">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                    ) : (
                                                                        <p className="active">{product?.Profiles[0]?.PolicyStatus}</p>
                                                                    )}
                                                                </div>
                                                            </div>
                                                            {product?.Profiles?.map((profile, idx) => (
                                                                <>
                                                                {profile.PlanCode && (profile.PlanCode.indexOf("HI") >= 0) &&
                                                                    <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                        <p>Quyền lợi điều trị Nội trú</p>
                                                                        <p>{profile?.ProductName?.split('-')[1]?.replaceAll('Quyền lợi điều trị Nội trú ', '')}{profile?.ProductName?.split('-')[2]? ' - ' + profile?.ProductName?.split('-')[2]: ''}</p>
                                                                    </div>
                                                                }
                                                                {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                    <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                        <p>Quyền lợi điều trị Ngoại trú</p>
                                                                        <p>{profile?.ProductName?.split('-')[1]?.replaceAll('Quyền lợi điều trị Ngoại trú ', '')}{profile?.ProductName?.split('-')[2]? ' - ' + profile?.ProductName?.split('-')[2]: ''}</p>
                                                                    </div>
                                                                }
                                                                {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                    <div className="card__footer-item" style={{padding: '12px', borderBottom: '0'}}>
                                                                        <p>Quyền lợi Chăm sóc răng</p>
                                                                        <p>{profile?.ProductName?.split('-')[1]?.replaceAll('Quyền lợi điều trị Chăm sóc răng ', '').replaceAll('Quyền lợi Chăm sóc răng ', '')}{profile?.ProductName?.split('-')[2]? ' - ' + profile?.ProductName?.split('-')[2]: ''}</p>
                                                                    </div>
                                                                }
                                                                </>
                                                            ))}

                                                            <div className={this.props.dropdownFeeDetailsMap[product?.LifeInsureID]?"dropdown show":"dropdown"} 
                                                            style={{padding: '0px'}}>
                                                                <div className="dropdown__content"
                                                                        onClick={() => this.props.dropdownFeeDetails(product?.LifeInsureID)}>
                                                                    <p className="card-dropdown-title" style={{
                                                                        padding: '20px 12px 0px',
                                                                        fontSize: '1.6rem',
                                                                        color: '#985801'
                                                                    }}>Phí định kỳ/cơ bản định kỳ
                                                                    <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px'}}>{(this.props.dropdownFeeDetailsMap[product?.LifeInsureID] === true) ?
                                                                        <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                                                        <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                                                        }
                                                                    </span>
                                                                    </p>
                                                                </div>
                                                                <div style={{marginLeft:'12px', marginRight:'12px', borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                                                {this.props.dropdownFeeDetailsMap[product?.LifeInsureID] && product?.Profiles?.map((profile, idx) => (
                                                                    <>
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HI") >= 0) &&
                                                                        <div className="card__footer-item" style={{padding: '8px', borderBottom: '0'}}>
                                                                            <p style={{color: '#985801'}}>Quyền lợi điều trị Nội trú</p>
                                                                            <p style={{color: '#985801'}}>{profile?.PolMPremAmt} {profile?.PolMPremAmt && (profile?.PolMPremAmt !== '-')? ' VNĐ':''}</p>
                                                                        </div>
                                                                    }
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                        <div className="card__footer-item" style={{padding: '8px', borderBottom: '0'}}>
                                                                            <p style={{color: '#985801'}}>Quyền lợi điều trị Ngoại trú</p>
                                                                            <p style={{color: '#985801'}}>{profile?.PolMPremAmt} {profile?.PolMPremAmt && (profile?.PolMPremAmt !== '-')? ' VNĐ':''}</p>
                                                                        </div>
                                                                    }
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                        <div className="card__footer-item" style={{padding: '8px', borderBottom: '0'}}>
                                                                            <p style={{color: '#985801'}}>Quyền lợi Chăm sóc răng</p>
                                                                            <p style={{color: '#985801'}}>{profile?.PolMPremAmt} {profile?.PolMPremAmt && (profile?.PolMPremAmt !== '-')? ' VNĐ':''}</p>
                                                                        </div>
                                                                    }
                                                                   </>
                                                                ))}
                                                                </div>
                                                            </div>

                                                            <div className='dash-line-esubmission' style={{padding: '12px', margin: '12px'}}></div>
                                                            <div className="info__body"  style={{padding: '12px'}}>
                                                            <p className="basic-text2" style={{color: '#292929', fontWeight: '700'}}>Chấm dứt quyền lợi</p>
                                                            {product?.Profiles?.map((profile, idx) => (
                                                                <>
                                                                
                                                                {profile.PlanCode && (profile.PlanCode.indexOf("HI") >= 0) &&
                                                                    <div className="checkbox-wrap basic-column">
                                                                    <div className="tab-wrapper">
                                                                        <div className="tab"> {/* Thêm key vào đây */}
                                                                            <div className="checkbox-warpper">
                                                                            <label className="checkbox" htmlFor={'inpatient' + index + '-' + t + '-' + idx}>
                                                                                <input
                                                                                    type="checkbox"
                                                                                    name={'inpatient' + index + '-' + t + '-' + idx}
                                                                                    id={'inpatient' + index + '-' + t + '-' + idx}
                                                                                    checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                    onClick={()=>openNotice(index+ '-' + t, profile, 1, product.Profiles)}
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

                                                                {this.props.liHCMap[profile.LifeInsureID] ?(
                                                                (this.props.liHCMap[profile.LifeInsureID] === 1) ? (
                                                                    <>
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox2" htmlFor={'outpatient' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        id={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        checked={true}
                                                                                        disabled
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2" style={{color: '#727272'}}>Quyền lợi điều trị Ngoại trú</p>
                                                                                </div>
                                                                            </div>
    
                                                                        </div>
                                                                    </div>
                                                                    }
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox2" htmlFor={'dental-care' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        id={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        checked={true}
                                                                                    />
                                                                                    <div className="checkmark">
                                                                                    <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                    </div>
                                                                                </label>
                                                                                <p className="basic-text2" style={{color: '#727272'}}>Quyền lợi Chăm sóc răng</p>
                                                                                </div>
                                                                            </div>
    
                                                                        </div>
                                                                    </div>
                                                                    }
                                                                    </>
                                                                ): (
                                                                    (this.props.liHCMap[profile.LifeInsureID] === 2) ?(
                                                                        <>
                                                                        {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                            <div className="checkbox-wrap basic-column">
                                                                            <div className="tab-wrapper">
                                                                                <div className="tab"> {/* Thêm key vào đây */}
                                                                                    <div className="checkbox-warpper">
                                                                                    <label className="checkbox" htmlFor={'outpatient' + index + '-' + t + '-' + idx}>
                                                                                        <input
                                                                                            type="checkbox"
                                                                                            name={'outpatient' + index + '-' + t + '-' + idx}
                                                                                            id={'outpatient' + index + '-' + t + '-' + idx}
                                                                                            checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                            onClick={()=>openNotice(index + '-' + t, profile, 2, product.Profiles)}
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
                                                                        {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                            <div className="checkbox-wrap basic-column">
                                                                            <div className="tab-wrapper">
                                                                                <div className="tab"> {/* Thêm key vào đây */}
                                                                                    <div className="checkbox-warpper">
                                                                                    <label className="checkbox2" htmlFor={'dental-care' + index + '-' + t + '-' + idx}>
                                                                                        <input
                                                                                            type="checkbox"
                                                                                            name={'dental-care' + index + '-' + t + '-' + idx}
                                                                                            id={'dental-care' + index + '-' + t + '-' + idx}
                                                                                            checked={true}
                                                                                        />
                                                                                        <div className="checkmark">
                                                                                        <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                                                                        </div>
                                                                                    </label>
                                                                                    <p className="basic-text2" style={{color: '#727272'}}>Quyền lợi Chăm sóc răng</p>
                                                                                    </div>
                                                                                </div>
        
                                                                            </div>
                                                                        </div>
                                                                        }
                                                                        </>
    
                                                                    ): (
                                                                        <>
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'outpatient' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        id={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                        onClick={()=>openNotice(index + '-' + t, profile, 2, product.Profiles)}
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
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'dental-care' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        id={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                        onClick={()=>openNotice(index + '-' + t, profile, 3, product.Profiles)}
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
                                                                    </>
    
                                                                    )
                                                                )

                                                                ):(
                                                                    <>
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HO") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'outpatient' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        id={'outpatient' + index + '-' + t + '-' + idx}
                                                                                        checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                        onClick={()=>openNotice(index + '-' + t, profile, 2, product.Profiles)}
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
                                                                    {profile.PlanCode && (profile.PlanCode.indexOf("HD") >= 0) &&
                                                                        <div className="checkbox-wrap basic-column">
                                                                        <div className="tab-wrapper">
                                                                            <div className="tab"> {/* Thêm key vào đây */}
                                                                                <div className="checkbox-warpper">
                                                                                <label className="checkbox" htmlFor={'dental-care' + index + '-' + t + '-' + idx}>
                                                                                    <input
                                                                                        type="checkbox"
                                                                                        name={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        id={'dental-care' + index + '-' + t + '-' + idx}
                                                                                        checked={this.props.tickBenefitMap[index + '-' + t + '-' + idx]? true: false}
                                                                                        onClick={()=>openNotice(index + '-' + t, profile, 3, product.Profiles)}
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
                                                                    </>
                                                                )}

                                                                </>
                                                            ))}
                                                        </div>

                                                        </div>
                                                        }
                                                    </div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                    )

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
                {(this.state.showNotice) &&
                    <AgreeChangePopup closePopup={() => notAgreeNotice()}
                                        imgPath={FE_BASE_URL + '/img/popup/cancel-hc-notice.svg'}
                                        agreeText='Đồng ý' notAgreeText='Không đồng ý' agreeFunc={() => closeNotice()} notAgreeFunc={() => notAgreeNotice()}
				/>}
                {(this.state.cancelRiderNotice) &&
                    <AgreeCancelPopup closePopup={() => notAgreeCancelRider()}
                                        msg={'Quý khách có chắc chắn chấm dứt sản phẩm bổ trợ này không?'}
                                        imgPath={FE_BASE_URL + '/img/popup/cancel-hc-notice.svg'}
                                        agreeText='Đồng ý' notAgreeText='Không đồng ý' agreeFunc={() => agreeCancelRider()} notAgreeFunc={() => notAgreeCancelRider()}
				/>}
            </div>
        )
    }
}

export default ProductCategoryGroupByLI;
