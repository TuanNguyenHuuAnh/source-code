import React from 'react';
import { formatFullName } from '../util/common';

class PaymentList extends React.Component {
    render() {
        var showPaymentInfo = (policyNo, index) => {
            // if(policyNo=== this.props.PolIDSelected)
            //{
            //    this.props.parentCallback("");
            //    return false;               
            //}
            this.props.PolicyClientProfile.forEach((pol, i) => {
                if (i === index) {
                    document.getElementById("divPaymentItem" + i).className = "item choosen";
                } else {
                    document.getElementById("divPaymentItem" + i).className = "item";
                }

            });
            this.props.parentCallback(policyNo);
        }
        if (this.props.PolicyClientProfile) {
            return (
                <div className="card-warpper">
                    {this.props.PolicyClientProfile !== null && this.props.PolicyClientProfile.map((item, index) => (
                        <div className="item" onClick={() => showPaymentInfo(item.PolicyID, index)} id={"divPaymentItem" + index} >
                            <div className={this.props.PolIDSelected && this.props.PolIDSelected !== "" && this.props.PolIDSelected.trim() === item.PolicyID.trim() ? ("card choosen") : ("card")} >
                                <div className="card__header">
                                    <h4 className="basic-bold">{item.ProductName}</h4>
                                    <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                                    <p>Hợp đồng: {item.PolicyID}</p>
                                    {item.PolicyStatus.length < 25 ?
                                        <p>Ngày hiệu lực: {item.PolIssEffDate}</p> :
                                        <p className="policy">Ngày hiệu lực: {item.PolIssEffDate}</p>}
                                    {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                        <div className="dcstatus">
                                            <p className="inactive">{item.PolicyStatus}</p>
                                        </div>) : (
                                        <div className="dcstatus">
                                            {item.PolicyStatus.length < 25 ?
                                                <p className="active" >{item.PolicyStatus}</p> :
                                                <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                        </div>
                                    )}
                                    <div className="choose">
                                        <div className="dot"></div>
                                    </div>
                                </div>
                                <div className="card__footer">
                                    <div className="card__footer-item">
                                        <p>Kỳ đóng phí</p>
                                        <p>{item.PaidToDate}</p>
                                    </div>
                                    <div className="card__footer-item">
                                        <p>Số tiền</p>
                                        <p className="basic-red">{item.PolSndryAmt} VNĐ</p>
                                    </div>
                                </div>
                            </div>

                        </div>
                    ))}
                </div>

            )
        }
        else {
            return (""

            )
        }
    }
}


export default PaymentList;