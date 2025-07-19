import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    COMPANY_KEY,
    EMAIL,
    EXPIRED_MESSAGE,
    FULL_NAME,
    GIFT_CART_DOCTOR_CARD,
    GIFT_CART_ECOMMERCE,
    GIFT_CART_FEE_REFUND,
    GIFT_CART_GIVE_POINT,
    GIFT_CART_MOBILE_CARD,
    GIFT_CART_OPEN_INVESTMENT,
    GIFT_CART_SUPER_MARKET,
    GIFT_CART_WELLNESS_CARD,
    OS,
    OTP_EXPIRED,
    OTP_INCORRECT,
    POINT,
    TOTAL_CART_POINT,
    UPDATE_POINT,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    DCID
} from '../constants';
import {
    CPCalculateShippingFee,
    CPLoyaltyPointConfirmation,
    CPSubmitForm,
    genOTP,
    iibGetMasterDataByType,
    iibGetShippingAddress,
    verifyOTP
} from '../util/APIUtils';
import 'antd/dist/antd.min.css';
import {Select} from 'antd';
import LoadingIndicator from '../common/LoadingIndicator2';
//import '../popup/PopupCloseButton.css'
import {
    formatFullName, formatMoney, getDeviceId, getSession, isInteger, maskPhone, setSession, showMessage, validateEmail
} from '../util/common';
import './ExchangePointAddress.css';
import DOTPInput from '../components/DOTPInput';

class ExchangePointAddress extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: true,
            cityProfile: [],
            districts: [],
            cityId: '',
            districtId: '',
            Phone: '',
            Email: '',
            District: '',
            Province: '',
            Ward: '',
            Address: '',
            Id: null,
            shippingAddress: null,
            showAddAddress: false,
            showVerifyPhone: false,
            Action: '',
            shippingFeeInPoint: 0,
            shippingFeeInPointGotIt: 0,
            showConfirm: false,
            remainPoint: (parseFloat(getSession(POINT)) - parseFloat(getSession(TOTAL_CART_POINT))).toFixed(1),
            totalPoint: parseFloat(getSession(TOTAL_CART_POINT)),
            defaultProvineId: '',
            defaultDistrictId: '',
            defaultAddress: '',
            activeId: -1,
            trackingNum: '',
            fullAddress: '',
            shippingPhone: '',
            submitting: false,
            showOtp: false,
            OTP: '',
            errorMessage: '',
            loadedAddress: false,
            totalShipping: 0,
            trackingNumber: ''
        };
        this.handleInputAddressChange = this.handleInputAddressChange.bind(this);
        this.addUpdateShippingAddress = this.addUpdateShippingAddress.bind(this);
        this.handleGiftCartShippingSubmit = this.handleGiftCartShippingSubmit.bind(this);
        this.closePopupEsc = this.closePopupEsc.bind(this);
        this.popupOtpSubmit = this.popupOtpSubmit.bind(this);
        this.handleInputOtpChange = this.handleInputOtpChange.bind(this);
    }

    handleInputAddressChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        if (target.id === 'shipping-phone-id' && !isInteger(inputValue)) {
            return;
        }
        this.setState({
            [inputName]: inputValue
        });
        let check = true;
        const allStateArr = Object.keys(this.state).map((key) => [key, this.state[key]]);
        const stateArr = allStateArr.filter(item => {
            return ['Phone', 'Email', 'District', 'Province', 'Ward', 'Address'].indexOf(item[0]) >= 0;
        });

        for (let i = 0; i < stateArr.length; i++) {
            if (stateArr[i][0] !== inputName) {
                if (typeof stateArr[i][1] === "string" && stateArr[i][1].trim() === '') {
                    check = false;
                    break;
                } else if (stateArr[i][1] === '') {
                    check = false;
                    break;
                }
            } else if (inputValue.trim() === '') {
                check = false;
                break;
            }
        }


        if (check) {
            document.getElementById('btn-gift-card-add-address').className = "btn btn-primary";
        } else {
            document.getElementById('btn-gift-card-add-address').className = "btn btn-primary disabled";
        }
    }

    handleGiftCartShippingSubmit(event) {
        event.preventDefault();
        if (document.getElementById("shippingBtn") && document.getElementById("shippingBtn").className) {
            document.getElementById("shippingBtn").className = "btn btn-primary disabled";
        }
        this.setState({submitting: true});
        this.cpCartShippingSubmitForm();
    }

    cpCartShippingSubmitForm = () => {
        if (getSession(POINT) && (parseFloat(getSession(POINT)) < parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat((this.state.totalShipping / 1000).toFixed(1)))) {
            document.getElementById('point-error-popup-sorry').className = "popup special point-error-popup show";
            return;
        } else {
            document.getElementById('point-error-popup-sorry').className = "popup special point-error-popup";
        }
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetTrackingNumber',
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: OS,
                UserLogin: getSession(USER_LOGIN),
                ClientID: getSession(CLIENT_ID),
                APIToken: getSession(ACCESS_TOKEN)
            }
        }
        CPSubmitForm(submitRequest).then(Res => {
            let Response = Res.Response;
            if ((Response.Result === 'true') && (Response.ErrLog === 'Submit Tracking Number is saved successfull.')) {
                let msgArr = Response.Message.split("|");
                if (!msgArr) {
                    document.getElementById('popup-exception').className = "popup special point-error-popup show";
                    return;
                }
                this.setState({
                    showOtp: true, trackingNumber: msgArr[0], transactionId: msgArr[1], minutes: 5, seconds: 0
                });
                this.startTimer();
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
            } else if (Response.ErrLog === 'OTP Exceed') {
                document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (Response.ErrLog === 'OTPLOCK' || Response.ErrLog === 'OTP Wrong 3 times') {
                document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else {
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }
        }).catch(error => {
        });
    }

    submitCartOrderShipping = (trackingNum) => {
        let promiseArr = [];
        let chargedShippingFee = false;
        let chargedShippingFeeGotIt = false;

        if (getSession(GIFT_CART_FEE_REFUND)) {
            let products = [];
            let feeRefundProducts = JSON.parse(getSession(GIFT_CART_FEE_REFUND));
            if (feeRefundProducts) {
                let categorycd = '';
                for (let i = 0; i < feeRefundProducts.length; i++) {
                    let product = feeRefundProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum);
                    product.strDetail = strDt;
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);

                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd));
            }
        }

        if (getSession(GIFT_CART_GIVE_POINT)) {
            let products = [];
            let givePointProducts = JSON.parse(getSession(GIFT_CART_GIVE_POINT));
            if (givePointProducts) {
                let categorycd = '';
                for (let i = 0; i < givePointProducts.length; i++) {
                    let product = givePointProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum);
                    product.strDetail = strDt;
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);

                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd));
            }
        }

        if (getSession(GIFT_CART_MOBILE_CARD)) {
            let products = [];
            let moibleCardProducts = JSON.parse(getSession(GIFT_CART_MOBILE_CARD));
            if (moibleCardProducts) {
                let categorycd = '';
                for (let i = 0; i < moibleCardProducts.length; i++) {
                    let product = moibleCardProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum);
                    product.strDetail = strDt;
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);

                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd));
            }
        }

        if (getSession(GIFT_CART_ECOMMERCE)) {
            let mapProducts = {};
            let ecommerceProducts = JSON.parse(getSession(GIFT_CART_ECOMMERCE));
            if (ecommerceProducts) {
                let categorycd = '';
                for (let i = 0; i < ecommerceProducts.length; i++) {
                    let product = ecommerceProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum);
                    product.strDetail = strDt;
                    product.Shipping = 0;
                    product.Quantity = qty;
                    if (mapProducts[categorycd]) {
                        let products = mapProducts[categorycd];
                        products.push(product);
                        mapProducts[categorycd] = products;
                    } else {
                        let products = [];
                        products.push(product);
                        mapProducts[categorycd] = products;
                    }
                }
                for (const key in mapProducts) {
                    if (mapProducts.hasOwnProperty(key)) {
                        promiseArr.push(this.cpLoyaltyOrderShippingConfirm(mapProducts[key], key));
                    }
                }
            }
        }

        if (getSession(GIFT_CART_SUPER_MARKET)) {
            let superMarketProducts = [];
            let gotItProducts = [];
            let feeSupermaket = 0;
            let feeGotIt = 0;
            let supermarketProducts = JSON.parse(getSession(GIFT_CART_SUPER_MARKET));
            if (supermarketProducts) {
                let categorycd = '';
                for (let i = 0; i < supermarketProducts.length; i++) {
                    let product = supermarketProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = '';
                    if (categorycd === '10') {
                        strDt = detail[0].replace('tracking_number', trackingNum).replace('shipping_fee', !chargedShippingFeeGotIt ? parseFloat(this.state.shippingFeeInPointGotIt) * 1000 : 0);
                        product.Shipping = !chargedShippingFeeGotIt ? parseFloat(this.state.shippingFeeInPointGotIt) * 1000 : 0;
                        if (!chargedShippingFeeGotIt) {
                            feeGotIt = product.Shipping;
                        }
                        chargedShippingFeeGotIt = true;
                        product.strDetail = strDt;
                        product.Quantity = qty;
                        gotItProducts.push(product);
                    } else {
                        strDt = detail[0].replace('tracking_number', trackingNum).replace('shipping_fee', !chargedShippingFee ? parseFloat(this.state.shippingFeeInPoint) * 1000 : 0);
                        product.Shipping = !chargedShippingFee ? parseFloat(this.state.shippingFeeInPoint) * 1000 : 0;
                        if (!chargedShippingFee) {
                            feeSupermaket = product.Shipping;
                        }
                        chargedShippingFee = true;
                        product.strDetail = strDt;
                        product.Quantity = qty;
                        superMarketProducts.push(product);
                    }

                }
                if (superMarketProducts.length > 0) {
                    promiseArr.push(this.cpLoyaltyOrderShippingConfirm(superMarketProducts, '1', feeSupermaket));
                }
                if (gotItProducts.length > 0) {
                    promiseArr.push(this.cpLoyaltyOrderShippingConfirm(gotItProducts, '10', feeGotIt));
                }

            }
        }

        if (getSession(GIFT_CART_WELLNESS_CARD)) {
            let products = [];
            let fee = 0;
            let wellNessCardProducts = JSON.parse(getSession(GIFT_CART_WELLNESS_CARD));
            if (wellNessCardProducts) {
                let categorycd = '';
                for (let i = 0; i < wellNessCardProducts.length; i++) {
                    let product = wellNessCardProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum).replace('shipping_fee', 0);
                    product.strDetail = strDt;
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);
                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd, fee));
            }
        }

        if (getSession(GIFT_CART_DOCTOR_CARD)) {
            let products = [];
            let fee = 0;
            let doctorcardProducts = JSON.parse(getSession(GIFT_CART_DOCTOR_CARD));
            if (doctorcardProducts) {
                let categorycd = '';
                for (let i = 0; i < doctorcardProducts.length; i++) {
                    let product = doctorcardProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    let strDt = detail[0].replace('tracking_number', trackingNum).replace('shipping_fee', !chargedShippingFee ? parseFloat(this.state.shippingFeeInPoint) * 1000 : 0);
                    product.strDetail = strDt;
                    product.Shipping = !chargedShippingFee ? parseFloat(this.state.shippingFeeInPoint) * 1000 : 0;
                    if (!chargedShippingFee) {
                        fee = product.Shipping;
                    }
                    product.Quantity = qty;
                    products.push(product);
                    chargedShippingFee = true;
                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd, fee));
            }
        }

        //GIFT_CART_OPEN_INVESTMENT
        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            let products = [];
            let openInvestmentProducts = JSON.parse(getSession(GIFT_CART_OPEN_INVESTMENT));
            if (openInvestmentProducts) {
                let categorycd = '';
                for (let i = 0; i < openInvestmentProducts.length; i++) {
                    let product = openInvestmentProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    product.strDetail = detail[0].replace('tracking_number', trackingNum);
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);
                }
                promiseArr.push(this.cpLoyaltyOrderShippingConfirm(products, categorycd));
            }
        }

        // After submitting all loyalty orders, close the Tracking number assigned to the last order session.
        const SubmitShippingCloseTracking = this.cpFinalSubmitShippingCloseTracking.bind(this);
        const buildMessage = this.buildMessage.bind(this);
        let msg = '';
        const promiseList = [];
        for (let i = 0; i < promiseArr.length; i++) {
            promiseList.push(CPLoyaltyPointConfirmation(promiseArr[i]));
        }

        Promise.allSettled(promiseList).then(function (results) {
            msg = buildMessage(results);
            if (msg !== null) {
                SubmitShippingCloseTracking(trackingNum, msg);
            } else {
                document.getElementById('popup-exception').className = 'popup special point-error-popup show';
            }

        }).catch(function (error) {
            // alert("error:" + error);
        });
    }

    buildMessage = (results) => {
        let msg = '';
        if (results === null || results.length <= 0) {
            return msg;
        }

        for (let i = 0; i < results.length; i++) {
            if (results[i].value.CPLoyaltyPointConfirmationResult.ErrLog.indexOf('Loyalty Point successful!') < 0) {
                return null;
            }
            if (results[i].value.CPLoyaltyPointConfirmationResult.Message) {
                let msgArr = results[i].value.CPLoyaltyPointConfirmationResult.Message.split('#');
                if (msgArr.length > 0) {
                    if (i === 0) {
                        msg = msgArr[msgArr.length - 1];
                    } else {
                        msg = msg + "#" + msgArr[msgArr.length - 1];
                    }
                }
            }
        }
        return msg;
    }

    cpLoyaltyOrderShippingConfirm = (products, categorycd, fee) => {
        if (!fee) {
            fee = 0;
        }
        let polID = '';
        let arrDetail = products[0].strDetail.split('|')[0].split(',');
        if ((categorycd === '3' || categorycd === '4') && products.length > 0) {
            polID = arrDetail[0].split(':')[1].replaceAll('"', '').trim();
        }
        let shipFirstName = getSession(FULL_NAME);
        let shipClientId = '';
        if (categorycd === '4' && products.length > 0) {
            shipFirstName = arrDetail[2].split(':')[1].replaceAll('"', '').trim();
            shipClientId = arrDetail[1].split(':')[1].replaceAll('"', '').trim();
        }
        let submitRequest = '';
        if (categorycd === '3' || categorycd === '4') {
            submitRequest = {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    smsconfirm: getSession(CELL_PHONE),
                    category: categorycd,
                    productItems: products,
                    UserLogin: getSession(USER_LOGIN),
                    DeviceId: getDeviceId(),
                    Authentication: AUTHENTICATION,
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    DeviceToken: 'Web',
                    emailconfirm: getSession(EMAIL),
                    deliveryPhone: getSession(CELL_PHONE),
                    policyno: polID,
                    shippingLastName: shipClientId,
                    shippingFirstName: shipFirstName
                }
            }
        } else {
            if (categorycd === '1' || categorycd === '10' || categorycd === '5' || categorycd === '14') {
                submitRequest = {
                    jsonDataInput: {
                        APIToken: getSession(ACCESS_TOKEN),
                        shippingDistrict: this.state.districtId,
                        shippingPhone: this.state.shippingPhone,
                        smsconfirm: getSession(CELL_PHONE),
                        category: categorycd,
                        productItems: products,
                        UserLogin: getSession(USER_LOGIN),
                        shippingCity: this.state.cityId,
                        DeviceId: getDeviceId(),
                        Authentication: AUTHENTICATION,
                        Project: 'mcp',
                        ClientID: getSession(CLIENT_ID),
                        DeviceToken: 'Web',
                        shippingWard: this.state.Ward,
                        emailconfirm: getSession(EMAIL),
                        shippingAddress: this.state.Address,
                        deliveryFeeGros: fee,
                        deliveryPhone: getSession(CELL_PHONE),
                        shippingLastName: shipClientId,
                        shippingFirstName: shipFirstName
                    }
                }
            } else {
                submitRequest = {
                    jsonDataInput: {
                        APIToken: getSession(ACCESS_TOKEN),
                        shippingPhone: this.state.shippingPhone,
                        smsconfirm: getSession(CELL_PHONE),
                        category: categorycd,
                        productItems: products,
                        UserLogin: getSession(USER_LOGIN),
                        DeviceId: getDeviceId(),
                        Authentication: AUTHENTICATION,
                        Project: 'mcp',
                        ClientID: getSession(CLIENT_ID),
                        DeviceToken: 'Web',
                        emailconfirm: getSession(EMAIL),
                        shippingAddress: this.state.Address,
                        deliveryPhone: getSession(CELL_PHONE),
                        shippingLastName: shipClientId,
                        shippingFirstName: shipFirstName
                    }
                }
            }
        }

        console.log("submitRequest", submitRequest);
        return submitRequest;
    }

    cpFinalSubmitShippingCloseTracking = (trackingNum, msg) => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'ConfirmLP',
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: OS,
                UserLogin: getSession(USER_LOGIN),
                ClientID: getSession(CLIENT_ID),
                APIToken: getSession(ACCESS_TOKEN),
                Email: getSession(EMAIL),
                CellPhone: getSession(CELL_PHONE),
                TrackingNumber: trackingNum,
                Message: msg

            }
        }

        CPSubmitForm(submitRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true') {
                const remainPoint = parseFloat(getSession(POINT)) - parseFloat(getSession(TOTAL_CART_POINT)) - parseFloat((this.state.totalShipping / 1000).toFixed(1));
                setSession(POINT, remainPoint.toFixed(1));
                setSession(UPDATE_POINT, UPDATE_POINT);
                this.cleanGiftCardAddress();
                this.setState({
                    remainPoint: remainPoint.toFixed(1), // totalPoint: (parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat(this.state.shippingFeeInPoint) + parseFloat(this.state.shippingFeeInPointGotIt)).toFixed(1),
                    totalPoint: parseFloat(getSession(TOTAL_CART_POINT)) + parseFloat((this.state.totalShipping / 1000).toFixed(1)),
                    showConfirm: true
                });
                if (getSession(TOTAL_CART_POINT)) {
                    sessionStorage.removeItem(TOTAL_CART_POINT);
                }
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    cleanGiftCardAddress = () => {
        if (getSession(GIFT_CART_FEE_REFUND)) {
            sessionStorage.removeItem(GIFT_CART_FEE_REFUND);
        }
        if (getSession(GIFT_CART_GIVE_POINT)) {
            sessionStorage.removeItem(GIFT_CART_GIVE_POINT);
        }
        if (getSession(GIFT_CART_MOBILE_CARD)) {
            sessionStorage.removeItem(GIFT_CART_MOBILE_CARD);
        }
        if (getSession(GIFT_CART_ECOMMERCE)) {
            sessionStorage.removeItem(GIFT_CART_ECOMMERCE);
        }
        if (getSession(GIFT_CART_SUPER_MARKET)) {
            sessionStorage.removeItem(GIFT_CART_SUPER_MARKET);
        }
        if (getSession(GIFT_CART_DOCTOR_CARD)) {
            sessionStorage.removeItem(GIFT_CART_DOCTOR_CARD);
        }
        if (getSession(GIFT_CART_WELLNESS_CARD)) {
            sessionStorage.removeItem(GIFT_CART_WELLNESS_CARD);
        }
        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            sessionStorage.removeItem(GIFT_CART_OPEN_INVESTMENT);
        }
    }

    getMasterDataCityDistrict = () => {
        const masterRequest = {
            Project: 'mcp', Type: "city_district"
        }
        iibGetMasterDataByType(masterRequest).then(res => {
            let Response = res.GetMasterDataByTypeResult;
            if (Response.Message === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                this.setState({
                    cityProfile: Response.ClientProfile
                });
            } else {
                //Alert
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    // getDefaultAddress = () => {
    //   let provineId = '';
    //   let districtId = '';
    //   let address = '';
    //   if (getSession(CLIENT_PROFILE)) {
    //     let clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
    //     if (clientProfile) {
    //       address = clientProfile[0].Address;
    //       let addressCode = clientProfile[0].AddressCode;
    //       if (addressCode) {
    //         let adrressCodeArr = addressCode.split('-');
    //         if (adrressCodeArr.length >= 2) {
    //           provineId = adrressCodeArr[0];
    //           districtId = adrressCodeArr[1];
    //           this.setState({ defaultProvineId: provineId, defaultDistrictId: districtId, defaultAddress: address, activeId: 'def-addr' });
    //           this.chooseAndCalculateShippingFee(districtId, provineId, 'def-addr', clientProfile[0].CellPhone, clientProfile[0].Address);
    //         }
    //       }
    //     }
    //   }
    // }

    // --------------------------------------------------------------------------------------------------------------------------------------------------------
    // Hàm tính tổng phí ship cho các sản phẩm
    calculateShippingFee = (products) => {
        let totalShippingFee = 0;
        let chargedShippingFee = false;
        let chargedShippingFeeGotIt = false;

        if (products && products.length > 0) {
            for (let i = 0; i < products.length; i++) {
                const product = products[i];
                const detail = product.strDetail.split('|');
                const qty = parseInt(detail[4]);
                let fee = 0;
                if (parseInt(product.category, 10) === 10) {
                    fee = !chargedShippingFeeGotIt ? parseFloat(this.state.shippingFeeInPointGotIt) * 1000 : 0;
                } else {
                    fee = !chargedShippingFee ? parseFloat(this.state.shippingFeeInPoint) * 1000 : 0;
                }
                totalShippingFee += fee;

                // Thực hiện các thay đổi vào sản phẩm nếu cần
                const strDt = detail[0].replace('tracking_number', this.state.trackingNum).replace('shipping_fee', fee);
                product.strDetail = strDt;
                product.Shipping = fee;
                product.Quantity = qty;

                if (parseInt(product.category, 10) === 10) {
                    chargedShippingFeeGotIt = true;
                } else {
                    chargedShippingFee = true;
                }
            }
        }

        return totalShippingFee;
    };

// Hàm xử lý GIFT_CART_SUPER_MARKET
    processGiftCartgotIt = () => {
        const superMarketProducts = JSON.parse(getSession(GIFT_CART_SUPER_MARKET));
        if (superMarketProducts) {
            const gotItFee = this.calculateShippingFee(superMarketProducts, 10)

            this.setState({
                totalShipping: this.state.totalShipping + gotItFee,
            });
        }
    };

// Hàm xử lý GIFT_CART_DOCTOR_CARD
    processGiftCartDoctorCard = () => {
        const doctorCardProducts = JSON.parse(getSession(GIFT_CART_DOCTOR_CARD));
        if (doctorCardProducts) {
            const doctorCardFee = this.calculateShippingFee(doctorCardProducts, 5);
            this.setState({
                totalShipping: this.state.totalShipping + doctorCardFee,
            });
        }
    };

    processGiftCartWellnessCard = () => {
        const wellnessCardProducts = JSON.parse(getSession(GIFT_CART_WELLNESS_CARD));
        if (wellnessCardProducts) {
            const wellnessCardFee = this.calculateShippingFee(wellnessCardFee, 14);
            this.setState({
                totalShipping: this.state.totalShipping + wellnessCardFee,
            });
        }
    };
    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    chooseAndCalculateShippingFee = (districtId, provinceId, index, phone, address) => {
        if (getSession(VERIFY_CELL_PHONE) && getSession(VERIFY_CELL_PHONE) === '0') {
            //document.getElementById('phone-confirm-popup-id').className = "popup special phone-confirm-popup show";
            this.showPopupVerifyPhone();
        }

        let totalOrder = 0;
        if (getSession(TOTAL_CART_POINT)) {
            totalOrder = parseFloat(getSession(TOTAL_CART_POINT)) * 1000;
        }
        this.setState({activeId: index});

        let products = this.buildShippingProducts();
        const calculateRequest = {
            jsonDataInput: {
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                Action: 'CalcOrderShippingFee',
                maQuanNhan: districtId,
                maTinhNhan: provinceId,
                tienCOD: '0',
                products: products
            }
        }

        CPCalculateShippingFee(calculateRequest).then(Response => {
            console.log("CPCalculateShippingFee", Response);

            if (Response && Response.Response[0]) {
                for (let i = 0; i < Response.Response.length; i++) {
                    if (Response.Response[i].Code === 'HCMPOST' && Response.Response[i].ShippingFeeAfterTax) {
                        this.setState({
                            shippingFeeInPoint: parseFloat(Response.Response[i].ShippingFeeAfterTax) / 1000,
                            cityId: provinceId,
                            districtId: districtId,
                            shippingPhone: phone,
                            fullAddress: address,
                            loadedAddress: true
                        });//Never jumb this becase HCMPOST removed now
                    } else if (Response.Response[i].Code === 'GOTIT' && Response.Response[i].ShippingFeeAfterTax) {
                        this.setState({
                            shippingFeeInPointGotIt: parseFloat(Response.Response[i].ShippingFeeAfterTax) / 1000,
                            cityId: provinceId,
                            districtId: districtId,
                            shippingPhone: phone,
                            fullAddress: address,
                            loadedAddress: true,
                            shippingFeeInPoint: parseFloat(Response.Response[i].ShippingFeeAfterTax) / 1000 //other product will use shipping fee of GOTIT now
                        });
                    }
                }
                if (this.state.totalShipping === 0) {
                    this.processGiftCartgotIt();
                    this.processGiftCartDoctorCard();
                    this.processGiftCartWellnessCard();
                }
                // this.processGiftCartOpenInvestment();
            } else {
                //Alert
                this.setState({
                    loadedAddress: true
                });//Never jumb this becase HCMPOST removed now
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }


    getUserAddress = () => {
        const addressRequest = {
            jsonDataInput: {
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'GETLIST',
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                CustomerId: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                OS: OS,
                UserLogin: getSession(USER_LOGIN),
            }
        }
        iibGetShippingAddress(addressRequest).then(res => {
            let Response = res.CPProcessShippingAddress;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ShippingAddress) {
                if (document.getElementById('update-shipping-address-form-id')) {
                    document.getElementById('update-shipping-address-form-id').reset();
                }
                if (Response.ShippingAddress.length > 0) {
                    let item = Response.ShippingAddress[0];
                    this.chooseAndCalculateShippingFee(item.IDDISTRICT, item.IDPROVINCE, 0, item.PHONE, (item.ADDRESS ? item.ADDRESS + ', ' : '' + item.WARD ? item.WARD + ', ' : '' + item.DISTRICT ? item.DISTRICT + ', ' : '' + item.PROVINCE))
                }
                this.setState({
                    shippingAddress: Response.ShippingAddress,
                    showAddAddress: false,
                    Address: Response.ShippingAddress[0]?.ADDRESS,
                    totalShipping: 0,
                });
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }


    addUpdateShippingAddress(event) {
        event.preventDefault();
        if (this.state.Phone.length !== 10) {
            this.setState({errorMessage: 'Số điện thoại không đúng format, vui lòng kiểm tra lại'});
            // document.getElementById('add-address-error-id').className = 'address-error expoint-validate';
            // document.getElementById('add-address-error-id').value = 'Số điện thoại không đúng format, vui lòng kiểm tra lại';
            return;
        }
        if (!validateEmail(this.state.Email)) {
            this.setState({errorMessage: 'Email không đúng format, vui lòng kiểm tra lại'});
            // document.getElementById('add-address-error-id').className = 'address-error expoint-validate';
            // document.getElementById('add-address-error-id').value = 'Email không đúng format, vui lòng kiểm tra lại';
            return;
        }
        this.setState({errorMessage: ''});
        const addressRequest = {
            jsonDataInput: {
                Id: this.state.Action === 'UPDATE' ? this.state.Id : null,
                APIToken: getSession(ACCESS_TOKEN),
                Action: this.state.Action,
                Address: this.state.Address,
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                CustomerId: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                District: this.state.District,
                IdDistrict: this.state.districtId,
                Email: this.state.Email,
                FirstName: getSession(FULL_NAME) ? getSession(FULL_NAME) : '',
                LastName: '',
                OS: OS,
                Phone: this.state.Phone,
                Project: 'mcp',
                Province: this.state.Province,
                IdProvince: this.state.cityId,
                UserLogin: getSession(USER_LOGIN),
                Ward: this.state.Ward

            }
        }
        iibGetShippingAddress(addressRequest).then(res => {
            let Response = res.CPProcessShippingAddress;
            if (Response.ErrLog === 'SUCCESSFUL') {
                this.getUserAddress();
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    buildShippingProducts() {
        let products = [];
        if (getSession(GIFT_CART_SUPER_MARKET)) {
            let supermarketProducts = JSON.parse(getSession(GIFT_CART_SUPER_MARKET));
            if (supermarketProducts) {
                let prod = '';
                for (let i = 0; i < supermarketProducts.length; i++) {
                    let product = supermarketProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    prod = {
                        ProductID: product.ProductID, Quantity: qty, Price: product.Price
                    }
                    products.push(prod);
                }
            }
        }

        if (getSession(GIFT_CART_DOCTOR_CARD)) {
            let doctorcardProducts = JSON.parse(getSession(GIFT_CART_DOCTOR_CARD));
            if (doctorcardProducts) {
                let prod = '';
                for (let i = 0; i < doctorcardProducts.length; i++) {
                    let product = doctorcardProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    prod = {
                        ProductID: product.ProductID, Quantity: qty, Price: product.Price
                    }
                    products.push(prod);
                }
            }
        }

        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            let openInvestmentProducts = JSON.parse(getSession(GIFT_CART_OPEN_INVESTMENT));
            if (openInvestmentProducts) {
                let prod = '';
                for (let i = 0; i < openInvestmentProducts.length; i++) {
                    let product = openInvestmentProducts[i];
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    prod = {
                        ProductID: product.ProductID, Quantity: qty, Price: product.Price
                    }
                    products.push(prod);
                }
            }
        }
        return products;
    }

    componentDidMount() {
        this.getMasterDataCityDistrict();
        // this.getDefaultAddress();
        this.getUserAddress();
        document.addEventListener("keydown", this.closePopupEsc, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.closePopupEsc, false);
    }

    onSelectCity = (cityId) => {
        const selCity = this.state.cityProfile.filter(c => (c.STT_Tinh_TP + '') === cityId);
        const selDistricts = selCity[0].lsQuanHuyen;
        this.setState({
            cityId: cityId, Province: selCity[0].Ten_Tinh_TP, districts: selDistricts
        });
    }

    onSelectDistrict = (districtId) => {
        const district = this.state.districts.find((district) => (district.Ma_Quan_Huyen + '') === districtId);
        this.setState({
            districtId: districtId, District: district.Ten_Quan_Huyen
        });
        this.checkEnableButton();
    }

    checkEnableButton = () => {
        let check = true;
        const allStateArr = Object.keys(this.state).map((key) => [key, this.state[key]]);
        const stateArr = allStateArr.filter(item => {
            return ['Phone', 'Email', 'District', 'Province', 'Ward', 'Address'].indexOf(item[0]) >= 0;
        });

        for (let i = 0; i < stateArr.length; i++) {

            if (stateArr[i][1] === '') {
                check = false;
                break;
            }

        }


        if (check) {
            document.getElementById('btn-gift-card-add-address').className = "btn btn-primary";
        } else {
            document.getElementById('btn-gift-card-add-address').className = "btn btn-primary disabled";
        }
    }

    closeAddShippingAddressPopup = () => {
        document.getElementById('update-shipping-address-form-id').reset();

        this.setState({showAddAddress: false});

    }

    closePopupVerifyPhone = () => {
        this.setState({showVerifyPhone: false});
        this.props.parentCallBack();
    }
    showPopupVerifyPhone = () => {
        this.setState({showVerifyPhone: true});
    }

    closePopupShippingConfirm = () => {
        this.setState({showConfirm: false});
        this.props.parentCallBack();
    }

    showPopupShippingConfirm = () => {
        this.setState({showConfirm: true});
    }

    genOtpGiftCard = () => {
        //gen otp
        const genOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GenOTPV2',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                // CellPhone: getSession(CELL_PHONE),
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Note: 'VALID_OTP_LOYALTYPOINT',
                OS: OS,
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
                DCID : getSession(DCID)
            }

        }

        genOTP(genOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({showOtp: true, transactionId: response.Response.Message, minutes: 5, seconds: 0});
                    this.startTimer();
                } else if (response.Response.ErrLog === 'OTP Exceed') {
                    document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else {
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                }
            }).catch(error => {
            document.getElementById("popup-exception").className = "popup special point-error-popup show";
        });

    }

    closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            if (this.state.showOtp) {
                this.closeOtpPopup();
            } else if (this.state.showConfirm) {
                this.closeConfirmSuccess();
            }
        }

    }
    closeOtpPopup = () => {
        if (this.state.showOtp) {
            document.getElementById('error-message-giftcard-expire-id').className = 'error-message';
            document.getElementById('error-message-giftcard-id').className = 'error-message';
            document.getElementById('otp-giftcard-id').value = '';
            this.setState({showOtp: false, minutes: 0, seconds: 0, submitting: false});
        }
        if (this.state.submitting) {
            this.setState({submitting: false});
        }

    }

    handleInputOtpChange(event) {

        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        this.setState({
            [inputName]: inputValue
        });
        if (inputValue !== '') {
            document.getElementById('btn-verify-otp-giftcard').className = "btn btn-primary";
        } else {
            document.getElementById('btn-verify-otp-giftcard').className = "btn btn-primary disabled";
        }
    }

    popupOtpSubmit(OTP) {
        const verifyOTPRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckOTP',
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),//this.state.POID
                DeviceId: getDeviceId(),
                Note: 'VALID_OTP_LOYALTYPOINT',
                OS: '',
                OTP: OTP,
                Project: 'mcp',
                TransactionID: this.state.transactionId,
                TrackingID: this.state.trackingNumber,
                UserLogin: getSession(USER_LOGIN)
            }

        }

        verifyOTP(verifyOTPRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                    this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
                    //this.cpCartShippingSubmitForm();
                    this.submitCartOrderShipping(this.state.trackingNumber);

                } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                } else if (response.Response.ErrLog === 'OTPEXPIRY') {
                    this.setState({errorMessage: OTP_EXPIRED});
                } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
                    this.setState({showOtp: false, minutes: 0, seconds: 0});
                    document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
                } else if (response.Response.ErrLog === 'OTPINCORRECT') {
                    this.setState({errorMessage: OTP_INCORRECT});
                } else {
                    this.setState({errorMessage: OTP_INCORRECT});
                }
            }).catch(error => {
            //this.props.history.push('/maintainence');
        });
    }

    startTimer = () => {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            else if (this.state.seconds === 0) {
                if (this.state.minutes === 0) {
                    clearInterval(myInterval)
                } else {
                    this.setState({minutes: this.state.minutes - 1, seconds: 59});
                }
            }
        }, 1000)
        return () => {
            clearInterval(myInterval);
        };

    }

    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0, submitting: false});

    }

    render() {
        const showShippingAddress = () => {
            this.setState({showAddAddress: true, Action: 'ADDNEW'});
        }
        //alert(this.state.districtId + ":" + this.state.District + "|" + this.state.districts)
        const showUpdateShippingAddress = (Address, District, IdDistrict, Email, FirstName, LastName, Phone, Province, IdProvince, Ward, Id) => {
            //alert(District + ":" + IdDistrict + ":" + IdProvince) ;
            this.setState({
                showAddAddress: true,
                Action: 'UPDATE',
                Address: Address,
                District: District,
                districtId: IdDistrict,
                Email: Email,
                FirstName: FirstName,
                LastName: LastName,
                Phone: Phone,
                Province: Province,
                cityId: IdProvince,
                Ward: Ward,
                Id: Id
            });

        }

        return (<>
                <main className="logined">
                    <div className="delivery-address-page">
                        <div className="breadcrums">
                            <div className="breadcrums__item">
                                <p>Trang chủ</p>
                                <span>&gt;</span>
                            </div>
                            <div className="breadcrums__item">
                                <p>Điểm thưởng</p>
                                <span>&gt;</span>
                            </div>
                            <div className="breadcrums__item">
                                <p>Giỏ quà của tôi</p>
                                <span>&gt;</span>
                            </div>
                        </div>
                        <section className="scdelivery-address">
                            <div className="container">
                                <div className="delivery-address">
                                    <div className="personform">
                                        <h4 className="basic-uppercase basic-bold">Chọn địa chỉ giao hàng</h4>
                                        {this.state.shippingAddress && this.state.shippingAddress.map((item, index) => (
                                            <div className="address-tab" key={'tab-' + index}>
                                                <label className="customradio">
                                                    {this.state.activeId === index ? (
                                                        <input type="radio" name="AddressChosen" defaultChecked
                                                               id={index}
                                                               onClick={() => this.chooseAndCalculateShippingFee(item.IDDISTRICT, item.IDPROVINCE, index, item.PHONE, (item.ADDRESS ? item.ADDRESS + ', ' : '' + item.WARD ? item.WARD + ', ' : '' + item.DISTRICT ? item.DISTRICT + ', ' : '' + item.PROVINCE))}/>) : (
                                                        <input type="radio" name="AddressChosen" id={index}
                                                               onClick={() => this.chooseAndCalculateShippingFee(item.IDDISTRICT, item.IDPROVINCE, index, item.PHONE, (item.ADDRESS ? item.ADDRESS + ', ' : '' + item.WARD ? item.WARD + ', ' : '' + item.DISTRICT ? item.DISTRICT + ', ' : '' + item.PROVINCE))}/>)}

                                                    <div className="checkmark"></div>
                                                    <div className="checkbox-content">
                                                        <h5 className="basic-bold basic-text2">{formatFullName(item.FISTNAME)} {' '} {formatFullName(item.LASTNAME)}</h5>
                                                        <div className="list-tab">
                                                            <i><img src="img/popup/phone.svg" alt=""/></i>
                                                            <p>{item.PHONE}</p>
                                                        </div>
                                                        <div className="list-tab">
                                                            <i><img src="img/popup/location.svg" alt=""/></i>
                                                            <p>{item.ADDRESS ? item.ADDRESS + ', ' : ''}<br/>{item.WARD ? item.WARD + ', ' : ''}{item.DISTRICT ? item.DISTRICT + ', ' : ''} {item.PROVINCE}
                                                            </p>
                                                        </div>
                                                    </div>
                                                </label>
                                                <span className="update-btn simple-brown"
                                                      onClick={() => showUpdateShippingAddress(item.ADDRESS, item.DISTRICT, item.IDDISTRICT, item.EMAIL, item.FISTNAME, item.LASTNAME, item.PHONE, item.PROVINCE, item.IDPROVINCE, item.WARD, item.ID)}> Cập nhật </span>
                                            </div>))}
                                        {(!this.state.shippingAddress || (this.state.shippingAddress.length < 1)) && <>
                                            <h5 className="basic-bold basic-text2" style={{
                                                lineHeight: '22px',
                                                paddingTop: '16px',
                                                paddingBottom: '16px',
                                                borderBottom: '1px solid #e0dedc'
                                            }}>{formatFullName(getSession(FULL_NAME))}</h5>
                                            <div className="address-tab">
                                                <button className="add-address" id="add-new-address">
                                                    <i className="circle-plus"
                                                       onClick={() => showShippingAddress()}>
                                                        <img src="img/icon/plus.svg" alt="circle-plus"
                                                             className="plus-sign"/>
                                                    </i>
                                                    <span className="basic-semibold">Thêm địa chỉ giao hàng</span>
                                                </button>
                                            </div>
                                        </>}
                                        <div className="note-wrapper">
                                            <div className="input textarea">
                                                <div className="input__content">
                                                    <label>Ghi chú</label>
                                                    <textarea rows="10" style={{fontSize: 14}}></textarea>
                                                </div>
                                                <i><img src="img/icon/edit.svg" alt=""/></i>
                                            </div>
                                        </div>

                                        <img className="decor-clip" src="img/mock.svg" alt=""/>
                                        <img className="decor-person" src="img/person.png" alt=""/>
                                    </div>
                                    <div className="important-note-list">
                                        <p><span className="basic-bold basic-red">Lưu ý: </span><span className="basic-bold">Địa chỉ này chỉ áp dụng
                                            cho việc giao hàng</span></p>

                                        <ul>
                                            <li>Phiếu quà tặng giấy (voucher giấy)</li>
                                            <li>Phiếu khám sức khỏe</li>
                                            <li>Quà tặng sức khỏe</li>
                                        </ul>
                                    </div>
                                    {/*<LoadingIndicator area="submit-loyalty-point"/>*/}
                                    <LoadingIndicator area="claim-li-list"/>
                                    <div className="point-tag">
                                        <p>Phí giao hàng:</p>
                                        <div className="point">
                                            <i><img src="img/icon/coint-icon.svg" alt="coin"/></i>
                                            <span
                                                className="basic-bold basic-red">{formatMoney((this.state.totalShipping / 1000).toFixed(3).toString())}</span>
                                        </div>
                                    </div>
                                    <div className="btn-wrapper">
                                        {this.state.submitting || !this.state.loadedAddress ? (
                                            <button className="btn btn-primary disabled" id="shippingBtn">Hoàn
                                                thành</button>) : (<button className="btn btn-primary" id="shippingBtn"
                                                                           onClick={this.handleGiftCartShippingSubmit}>Hoàn
                                                thành</button>)}

                                    </div>

                                </div>
                            </div>
                        </section>
                    </div>
                </main>
                <div
                    className={this.state.showAddAddress ? "popup new-deliveryaddress-popup special show" : "popup new-deliveryaddress-popup special"}
                    id="new-deliveryaddress-popup">
                    <div className="popup__card">
                        <div className="new-deliveryaddress-card">
                            <div className="header">
                                <h4>Địa chỉ mới</h4>
                                <i className="closebtn"><img src="img/icon/close.svg" alt=""
                                                             onClick={this.closeAddShippingAddressPopup}/></i>
                            </div>
                            <div className="body">
                                <form onSubmit={this.addUpdateShippingAddress} id="update-shipping-address-form-id">
                                    <div className="input-wrapper">
                                        <div className="input-wrapper-item">
                                            <div className="input special-input-extend">
                                                <div className="input__content">
                                                    <label htmlFor="">Số điện thoại*</label>
                                                    <input type="search" name="Phone" id="shipping-phone-id"
                                                           maxLength='10' value={this.state.Phone}
                                                           onChange={this.handleInputAddressChange} required/>
                                                </div>
                                                <i><img src="img/icon/input_phone.svg" alt=""/></i>
                                            </div>
                                        </div>
                                        <div className="input-wrapper-item">
                                            <div className="input special-input-extend">
                                                <div className="input__content">
                                                    <label htmlFor="">Email*</label>
                                                    <input type="search" name="Email" maxLength='50'
                                                           value={this.state.Email}
                                                           onChange={this.handleInputAddressChange} required/>
                                                </div>
                                                <i><img src="img/icon/input_mail.svg" alt=""/></i>
                                            </div>
                                        </div>
                                        <div className="input-wrapper-item">
                                            <City
                                                data={this.state.cityProfile}
                                                selectedId={this.state.cityId}
                                                city={this.state.Province}
                                                onSelect={this.onSelectCity}/>
                                        </div>
                                        <div className="input-wrapper-item">
                                            <District
                                                data={this.state.districts}
                                                selectedId={this.state.districtId}
                                                district={this.state.District}
                                                onSelect={this.onSelectDistrict}/>
                                        </div>

                                        <div className="input-wrapper-item">
                                            <div className="dropdown inputdropdown">
                                                <div className="dropdown__content">
                                                    <div className="tab__content">
                                                        <div className="input special-input-extend">
                                                            <div className="input__content">
                                                                <label htmlFor="">Phường/ Xã*</label>
                                                                <input type="search" name="Ward" value={this.state.Ward}
                                                                       onChange={this.handleInputAddressChange}
                                                                       required/>
                                                            </div>
                                                            <i><img src="img/icon/edit.svg" alt=""/></i>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="dropdown__items"></div>
                                            </div>
                                        </div>
                                        <div className="input-wrapper-item">
                                            <div className="input special-input-extend">
                                                <div className="input__content">
                                                    <label htmlFor="">Địa chỉ*</label>
                                                    <input type="search" name="Address" value={this.state.Address}
                                                           onChange={this.handleInputAddressChange} required/>
                                                </div>
                                                <i><img src="img/icon/edit.svg" alt=""/></i>
                                            </div>
                                        </div>
                                    </div>
                                    <p className={this.state.errorMessage ? "address-error expoint-validate" : "address-error"}
                                       id="add-address-error-id">{this.state.errorMessage}</p>
                                    <div className="btn-wrapper">
                                        <button className="btn btn-primary disabled" id="btn-gift-card-add-address">Lưu
                                            địa chỉ
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>

                <div
                    className={this.state.showVerifyPhone ? "popup special phone-confirm-popup show" : "popup special phone-confirm-popup"}
                    id="phone-confirm-popup-id">
                    <div className="popup__card">
                        <div className="phone-confirm-card">
                            <div className="close-btn">
                                <img src="img/icon/close-icon.svg" alt="closebtn" className="btn"
                                     onClick={this.closePopupVerifyPhone}/>
                            </div>
                            <div className="picture-wrapper">
                                <div className="picture">
                                    <img src="img/icon/9.2/9.2-shield.svg" alt="popup-hosobg"/>
                                </div>
                            </div>
                            <div className="content">
                                <p>Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch
                                    trực tuyến</p>
                                <div className="btn-wrapper">
                                    <Link className="btn btn-primary" to={"/account"}
                                          onClick={this.closePopupVerifyPhone}>Xác thực</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>
                <div
                    className={this.state.showConfirm ? "popup refund-confirm-popup special show" : "popup refund-confirm-popup special"}
                    id="exchange-gift-cart-popup-success">
                    <div className="popup__card">
                        <div className="refund-confirm-card">
                            <div className="close-btn">
                                <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn"
                                     onClick={this.closePopupShippingConfirm}/>
                            </div>
                            <div className="picture-wrapper">
                                <div className="picture">
                                    <img src="../img/popup/doi-diem-thuong.svg" alt="popup-hosobg"/>
                                </div>
                            </div>
                            <div className="content">
                                <p style={{paddingBottom: '1px'}}>
                                    Cảm ơn Quý khách đã chọn đổi <span
                                    className="basic-red basic-bold special">{formatMoney(this.state.totalPoint)} điểm thưởng</span>
                                </p>
                                <div className="yellow-tab" style={{paddingTop: '12px', paddingBottom: '12px'}}>
                                    <div className="yellow-tab__item">
                                        <p>Số điểm còn lại</p>
                                        <p>{formatMoney(this.state.remainPoint)} điểm</p>
                                    </div>
                                </div>
                                <div className="btn-wrapper">
                                    <Link className="btn btn-primary" to={"/point-exchange"}
                                          onClick={this.closePopupShippingConfirm}>Tiếp tục đổi điểm</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>
                {this.state.showOtp &&
                    <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                               closeOtp={this.closeOtp} errorMessage={this.state.errorMessage}
                               popupOtpSubmit={this.popupOtpSubmit} reGenOtp={this.genOtpGiftCard}
                               maskPhone={maskPhone(getSession(CELL_PHONE))}/>}
            </>)
    }
}


class City extends React.Component {
    onSelect = (value) => {
        this.props.onSelect(value);
    }

    render() {
        const {Option} = Select;
        return (

            <div className="dropdown inputdropdown">
                <div className="dropdown__content">
                    <div className="tab__content">
                        <div className="input" style={{padding: '10px 0px 10px 0px'}}>
                            {this.props.city ? (<Select showSearch
                                                        size='large'
                                                        style={{width: '100%', margin: '0'}}
                                                        width='100%'
                                                        bordered={false}
                                                        placeholder="Chọn tỉnh/ Thành phố*"
                                                        optionFilterProp="province"
                                                        value={this.props.city}
                                                        filterOption={(input, option) => option.province.toLowerCase().indexOf(input.toLowerCase()) >= 0}

                                                        name="Province"
                                                        onChange={this.onSelect}>
                                    {this.props.data.map(city => (<Option
                                            key={city.STT_Tinh_TP}
                                            province={city.Ten_Tinh_TP}
                                            selected={this.props.selectedId === city.STT_Tinh_TP}>
                                            {city.Ten_Tinh_TP}
                                        </Option>))}
                                </Select>) : (<Select showSearch
                                                      size='large'
                                                      style={{width: '100%', margin: '0'}}
                                                      width='100%'
                                                      bordered={false}
                                                      placeholder="Chọn tỉnh/ Thành phố*"
                                                      optionFilterProp="province"
                                                      filterOption={(input, option) => option.province.toLowerCase().indexOf(input.toLowerCase()) >= 0}

                                                      name="Province"
                                                      onChange={this.onSelect}>
                                    {this.props.data.map(city => (<Option
                                            key={city.STT_Tinh_TP}
                                            province={city.Ten_Tinh_TP}
                                            selected={this.props.selectedId === city.STT_Tinh_TP}>
                                            {city.Ten_Tinh_TP}
                                        </Option>))}
                                </Select>)}

                        </div>
                    </div>
                </div>
            </div>


        );
    }
}


class District extends React.Component {
    onSelect = (value) => {
        this.props.onSelect(value);
    }

    render() {
        const {Option} = Select;
        return (

            <div className="dropdown inputdropdown">
                <div className="dropdown__content">
                    <div className="tab__content">
                        <div className="input" style={{padding: '10px 0px 10px 0px'}}>
                            {this.props.district ? (<Select showSearch
                                                            size='large'
                                                            style={{width: '100%', margin: '0'}}
                                                            width='100%'
                                                            bordered={false}
                                                            placeholder="Chọn Quận/ huyện*"
                                                            optionFilterProp="district"
                                                            value={this.props.district}
                                                            filterOption={(input, option) => option.district.toLowerCase().indexOf(input.toLowerCase()) >= 0}

                                                            name="District"
                                                            onChange={this.onSelect}>
                                    {this.props.data.map(district => (<Option
                                            key={district.Ma_Quan_Huyen}
                                            district={district.Ten_Quan_Huyen}
                                            selected={this.props.selectedId === district.Ma_Quan_Huyen}>
                                            {district.Ten_Quan_Huyen}
                                        </Option>))}
                                </Select>) : (<Select showSearch
                                                      size='large'
                                                      style={{width: '100%', margin: '0'}}
                                                      width='100%'
                                                      bordered={false}
                                                      placeholder="Chọn Quận/ huyện*"
                                                      optionFilterProp="district"
                                                      filterOption={(input, option) => option.district.toLowerCase().indexOf(input.toLowerCase()) >= 0}

                                                      name="District"
                                                      onChange={this.onSelect}>
                                    {this.props.data.map(district => (<Option
                                            key={district.Ma_Quan_Huyen}
                                            district={district.Ten_Quan_Huyen}
                                            selected={this.props.selectedId === district.Ma_Quan_Huyen}>
                                            {district.Ten_Quan_Huyen}
                                        </Option>))}
                                </Select>)}

                        </div>
                    </div>
                </div>
            </div>


        );
    }
}

export default ExchangePointAddress;
