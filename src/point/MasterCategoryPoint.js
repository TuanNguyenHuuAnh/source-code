import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {getSession} from '../util/common';
import {
    CATEGORY_IMG_FOLDER_URL,
    CELL_PHONE,
    CLIENT_ID,
    DEADTH_CLAIM_MSG,
    GIFT_CART_FEE_REFUND,
    GIFT_CART_GIVE_POINT, GIFT_CART_WELLNESS_CARD
} from '../constants';
import AlertPopupDeadthClaim from '../components/AlertPopupDeadthClaim';

let msgPopup = '';
let popupImgPath = '';

class MasterCategoryPoint extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showDeadClaimErr: false,
        };
    };

    render() {
        let permissions = ['2', '3', '4'];

        let orginalMasterProfile = this.props.masterProfile;
        if (!orginalMasterProfile) {
            return null;
        }
        let masterProfile = orginalMasterProfile.filter(product => {
            return product.PRODUCTCATEGORYCD !== '7';
        });

        const customSortOrder = ["14", "4", "12", "13", "3", "5", "9", "6"];

        const sortedData = masterProfile.sort((a, b) => {
            return customSortOrder.indexOf(a.PRODUCTCATEGORYCD) - customSortOrder.indexOf(b.PRODUCTCATEGORYCD);
        });

        let size = sortedData.length;
        let leftSize = Math.floor(size / 2) + size % 2;
        let rightSize = Math.floor(size / 2);


        const leftCategory = sortedData.slice(0, leftSize);
        const rightCategory = sortedData.slice(leftSize);

        const showError = () => {
            document.getElementById('error-popup-only-for-existed').className = "popup error-popup special show";
        }
        const checkDeadthClaimError = (categorycd) => {
            // alert(getSession(DEADTH_CLAIM_MSG));
            if (getSession(DEADTH_CLAIM_MSG) && getSession(DEADTH_CLAIM_MSG) === 'DeathClaim') {
                return true;
            }
            return false;
        }

        const showDeadthClaimError = () => {
            msgPopup = 'Chức năng không thực hiện được do Quý khách đang có Yêu cầu giải quyết quyền lợi bảo hiểm';
            popupImgPath = 'img/popup/dead-claim.png';
            this.setState({showDeadClaimErr: true});

        }

        const showFeeRefundOnlyForOneATime = () => {
            document.getElementById('point-error-popup-only-one-fee-refund-a-time').className = "popup special point-error-popup show";
        }
        const showGivePointOnlyForOneATime = () => {
            document.getElementById('point-error-popup-only-one-give-point-a-time').className = "popup special point-error-popup show";
        }
        const checkHaveAPhone = () => {
            if (getSession(DEADTH_CLAIM_MSG) && getSession(DEADTH_CLAIM_MSG) !== 'Permit Use Loyalty Point') {
                msgPopup = 'Chức năng không thực hiện được do Quý khách đang có Yêu cầu giải quyết quyền lợi bảo hiểm';
                popupImgPath = 'img/popup/dead-claim.png';
                this.setState({showDeadClaimErr: true});
                return;
            }
            document.getElementById('error-popup-no-have-phone-to-exchange').className = "popup error-popup special show";
        }
        const closePopupError = () => {
            this.setState({showDeadClaimErr: false});
        }

        return (
            <>
                <section className="scexchange-point-history">
                    <div className="container">
                        <div className="exchange-point-history-wrapper">
                            <h2> Kho quà tặng </h2>
                            <div className="exchange-point-history">
                                <div className="left-side">
                                    {
                                        leftCategory.map((item, index) => {
                                            if ((!getSession(CLIENT_ID) || getSession(CLIENT_ID) === '') && permissions.indexOf(item.PRODUCTCATEGORYCD) >= 0) {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => showError()} key={index}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            }  else if (item.PRODUCTCATEGORYCD === '3' && getSession(GIFT_CART_FEE_REFUND)) {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => showFeeRefundOnlyForOneATime()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if (item.PRODUCTCATEGORYCD === '4' && getSession(GIFT_CART_GIVE_POINT)) {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => showGivePointOnlyForOneATime()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if ((getSession(CLIENT_ID) && !getSession(CELL_PHONE)) && item.PRODUCTCATEGORYCD !== '6' && item.PRODUCTCATEGORYCD !== '9') {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => checkHaveAPhone()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if (checkDeadthClaimError(item.PRODUCTCATEGORYCD) === true) {
                                                return (
                                                    <Link className="item2" to={"/point-exchange"}
                                                          key={index + leftSize} onClick={() => showDeadthClaimError()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else {
                                                return (
                                                    <Link className="item2"
                                                          to={"/category/:id".replace(":id", item.PRODUCTCATEGORYCD)}
                                                          key={index + leftSize}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            }
                                        })
                                    }
                                </div>
                                <div className="right-side">
                                    {
                                        rightCategory.map((item, index) => {
                                            if ((!getSession(CLIENT_ID) || getSession(CLIENT_ID) === '') && permissions.indexOf(item.PRODUCTCATEGORYCD) >= 0) {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => showError()} key={index + leftSize}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if (item.PRODUCTCATEGORYCD === '3' && getSession(GIFT_CART_FEE_REFUND)) {
                                                return (
                                                    <Link className={rightCategory.length - index !== 1 ? "item" : ""}
                                                          to="/point-exchange"
                                                          onClick={() => showFeeRefundOnlyForOneATime()}>
                                                        <div className="item2">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if (item.PRODUCTCATEGORYCD === '4' && getSession(GIFT_CART_GIVE_POINT)) {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => showGivePointOnlyForOneATime()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if ((getSession(CLIENT_ID) && !getSession(CELL_PHONE)) && item.PRODUCTCATEGORYCD !== '6' && item.PRODUCTCATEGORYCD !== '9') {
                                                return (
                                                    <Link className="item2" to="/point-exchange"
                                                          onClick={() => checkHaveAPhone()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else if (checkDeadthClaimError(item.PRODUCTCATEGORYCD)) {
                                                return (
                                                    <Link className="item2"
                                                          to={"/point-exchange".replace(":id", item.PRODUCTCATEGORYCD)}
                                                          key={index + leftSize} onClick={() => showDeadthClaimError()}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            } else {
                                                return (
                                                    <Link className="item2"
                                                          to={"/category/:id".replace(":id", item.PRODUCTCATEGORYCD)}
                                                          key={index + leftSize}>
                                                        <div className="item">
                                                            <div className="icon">
                                                                <img
                                                                    src={item.IMAGEURL ? CATEGORY_IMG_FOLDER_URL + item.IMAGEURL : (item.PCP_Code === '002' ? CATEGORY_IMG_FOLDER_URL + '0000152_nhan-ma-mua-hang-tiki_450.jpeg' : CATEGORY_IMG_FOLDER_URL + '0000217_nhan-phieu-mua-hang_450.jpeg')}
                                                                    alt=""/>
                                                            </div>
                                                            <div className="content">
                                                                <h3 className="basic-bold">{item.IMAGENAME ? item.IMAGENAME.replace('Đầu tư Quỹ mở', 'Đầu tư quỹ mở') : (item.PCP_Code === '002' ? 'Phiếu quà tặng điện tử (voucher điện tử)' : 'Phiếu quà tặng giấy (voucher giấy)')}</h3>
                                                                <p>{item.PRODUCTCATEGORYNAME}</p>
                                                            </div>
                                                        </div>
                                                    </Link>
                                                )
                                            }
                                        })
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                {this.state.showDeadClaimErr &&
                    <AlertPopupDeadthClaim closePopup={closePopupError} msg={msgPopup} imgPath={popupImgPath}/>
                }
            </>
        )
    }
}

export default MasterCategoryPoint;
