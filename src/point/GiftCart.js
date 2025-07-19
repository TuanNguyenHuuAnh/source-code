import React, {Component} from 'react';
import {Link, Redirect} from "react-router-dom";
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLIENT_ID,
    COMPANY_KEY,
    DOCTOR_CARD_LOCAL_IMG,
    ECOMMERCE_LOCAL_IMG,
    EMAIL,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    FEE_REFUND_LOCAL_IMG,
    FULL_NAME,
    GIFT_CART_DOCTOR_CARD,
    GIFT_CART_ECOMMERCE,
    GIFT_CART_FEE_REFUND,
    GIFT_CART_GIVE_POINT,
    GIFT_CART_MOBILE_CARD,
    GIFT_CART_OPEN_INVESTMENT,
    GIFT_CART_SUPER_MARKET,
    GIFT_CART_WELLNESS_CARD,
    GIVE_POINT_LOCAL_IMG,
    MOBILE_CARD_LOCAL_IMG,
    OS,
    OTP_EXPIRED,
    OTP_INCORRECT,
    PageScreen,
    POINT,
    PRICE_TAG_LOCAL_IMG,
    QUANTRICS_SURVERY_CLS,
    SUPER_MARKET_LOCAL_IMG,
    TOTAL_CART_POINT,
    TWOFA,
    UPDATE_POINT,
    USER_LOGIN,
    WEB_BROWSER_VERSION, 
    WELLNESS_GIFT_IMG,
    DCID
} from '../constants';
import iconInvest from '../img/icon/9.1/iconInvest.svg';

import {CPLoyaltyPointConfirmation, CPSaveLog, CPSubmitForm, genOTP, logoutSession, verifyOTP} from '../util/APIUtils';
import ExchangePointAddress from './ExchangePointAddress';
import {
    formatMoney,
    getDeviceId,
    getSession,
    maskPhone,
    roundUp,
    setSession,
    showMessage,
    showQuantricsSurvey,
    trackingEvent
} from '../util/common';
import './GiftCart.css';
import '../common/Common.css';
import LoadingIndicator from '../common/LoadingIndicator2';
import DOTPInput from '../components/DOTPInput';
import AuthenticationPopup from '../components/AuthenticationPopup';

import {Helmet} from "react-helmet";

class GiftCart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reload: false,
            totalPoint: getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0,
            GiftConstantDeleting: '',
            productsDeleting: null,
            indexDeleting: -1,
            showAddress: false,
            shippingFee: 0,
            showConfirm: false,
            showOtp: false,
            transactionId: '-1',
            OTP: '',
            minutes: 0,
            seconds: 0,
            remainPoint: parseFloat(getSession(POINT)) - parseFloat(getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0),
            submitting: false,
            errorMessage: '',
            renderMeta: false,
            noTwofa: false,
            trackingNumber: ''
        }
        this.handleGiftCartSubmit = this.handleGiftCartSubmit.bind(this);
        this.handleInputOtpChange = this.handleInputOtpChange.bind(this);
        this.popupOtpSubmit = this.popupOtpSubmit.bind(this);
    };

    componentDidMount() {
        this.cpSaveLog(`Web_Open_${PageScreen.LOYALTY_GIFTS_CARD}`);
        trackingEvent(
            "Điểm thưởng",
            `Web_Open_${PageScreen.LOYALTY_GIFTS_CARD}`,
            `Web_Open_${PageScreen.LOYALTY_GIFTS_CARD}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.LOYALTY_GIFTS_CARD}`);
        trackingEvent(
            "Điểm thưởng",
            `Web_Close_${PageScreen.LOYALTY_GIFTS_CARD}`,
            `Web_Close_${PageScreen.LOYALTY_GIFTS_CARD}`,
        );
    }

    cpSaveLog(functionName) {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN)
            }
        }
        CPSaveLog(masterRequest).then(res => {
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    hideAddress = () => {
        this.setState({
            showAddress: false,
            totalPoint: getSession(TOTAL_CART_POINT) ? getSession(TOTAL_CART_POINT) : 0
        });
    }

    handleGiftCartSubmit(event) {
        event.preventDefault();
        if (parseFloat(this.state.totalPoint) <= 0) {
            document.getElementById('point-error-popup-no-order').className = "popup special point-error-popup show";
            return;
        }
        if (getSession(POINT) && (parseFloat(getSession(POINT)) < parseFloat(this.state.totalPoint))) {
            document.getElementById('point-error-popup-sorry').className = "popup special point-error-popup show";
            return;
        } else {
            document.getElementById('point-error-popup-sorry').className = "popup special point-error-popup";
        }
        if (!getSession(TWOFA) || (getSession(TWOFA) === '0') || getSession(TWOFA) === 'undefined') {
            //yêu cầu bật 2fa
            this.setState({noTwofa: true});
            return;
        }
        if (getSession(GIFT_CART_SUPER_MARKET) || getSession(GIFT_CART_DOCTOR_CARD) || getSession(GIFT_CART_WELLNESS_CARD)) {
            this.setState({showAddress: true});
        } else {
            this.setState({submitting: true});
            this.cpCartSubmitForm();
        }

    }

    cpCartSubmitForm = () => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetTrackingNumber',
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
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
                this.setState({showOtp: true, trackingNumber: msgArr[0], transactionId: msgArr[1], minutes: 5, seconds: 0});
                this.startTimer();
                //this.submitCartOrder(Response.Message);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}

                })

            } else if (Response.ErrLog === 'OTP Exceed') {
                document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (Response.ErrLog === 'OTPLOCK' || Response.ErrLog === 'OTP Wrong 3 times') {
                document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else {
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }

        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }

    submitCartOrder = (trackingNum) => {
        let promiseArr = [];

        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            let products = [];
            let openInvestmentProducts = JSON.parse(getSession(GIFT_CART_OPEN_INVESTMENT));
            if (openInvestmentProducts) {
                let categorycd = '';
                for (let i = 0; i < openInvestmentProducts.length; i++) {
                    let product = openInvestmentProducts[i];
                    //console.log("product", product);
                    let detail = product.strDetail.split('|');
                    let qty = parseInt(detail[4]);
                    categorycd = detail[5];
                    product.strDetail = detail[0].replace('tracking_number', trackingNum);
                    product.Shipping = 0;
                    product.Quantity = qty;
                    products.push(product);

                }
                promiseArr.push(this.cpLoyaltyGiftCardOrderConfirm(products, categorycd));
            }
        }

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
                promiseArr.push(this.cpLoyaltyGiftCardOrderConfirm(products, categorycd));
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
                promiseArr.push(this.cpLoyaltyGiftCardOrderConfirm(products, categorycd));
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
                promiseArr.push(this.cpLoyaltyGiftCardOrderConfirm(products, categorycd));
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

                for (var key in mapProducts) {
                    if (mapProducts.hasOwnProperty(key)) {
                        promiseArr.push(this.cpLoyaltyGiftCardOrderConfirm(mapProducts[key], key));
                    }
                }

            }
        }


        //After submitting all loyalty orders, close the Tracking number assigned to the last order session.
        var SubmitCloseTracking = this.cpFinalSubmitSuperMarketceCloseTracking.bind(this);
        var buildMessage = this.buildMessage.bind(this);
        var msg = '';
        const promiseList = [];
        for (let i = 0; i < promiseArr.length; i++) {
            promiseList.push(CPLoyaltyPointConfirmation(promiseArr[i]));
        }
        Promise.allSettled(promiseList).then(function (results) {
            //console.log("results", results);
            msg = buildMessage(results);
            if (msg !== null) {
                SubmitCloseTracking(trackingNum, msg);
            } else {
                document.getElementById('popup-exception').className = 'popup special point-error-popup show';
            }


        }).catch(function (error) {
            //alert("error:" + error);
        });

    }

    buildMessage = (results) => {
        let msg = '';
        if (results === null || results.length <= 0) {
            return msg;
        }

        for (let i = 0; i < results.length; i++) {
            if (results[i].value.CPLoyaltyPointConfirmationResult.ErrLog.indexOf('Loyalty Point successful!') < 0 ) {
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

    cpLoyaltyGiftCardOrderConfirm = (products, categorycd) => {
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
                    shippingLastName: shipClientId,
                    shippingFirstName: shipFirstName
                }
            }
        }
        return submitRequest;
    }

    cpFinalSubmitSuperMarketceCloseTracking = (trackingNum, msg) => {
        {
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
                    const remainPo = parseFloat(getSession(POINT)) - parseFloat(this.state.totalPoint);
                    setSession(POINT, remainPo.toFixed(1));
                    if (getSession(TOTAL_CART_POINT)) {
                        sessionStorage.removeItem(TOTAL_CART_POINT);
                    }
                    setSession(UPDATE_POINT, UPDATE_POINT);
                    this.cleanGiftCard();
                    this.setState({remainPoint: remainPo, showConfirm: true, submitting: false});
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}

                    })

                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        }
    }

    cleanGiftCard = () => {
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

    closeConfirmSuccess = () => {
        this.setState({showConfirm: false, totalPoint: 0});
        showQuantricsSurvey(QUANTRICS_SURVERY_CLS);
    }
    showConfirmSuccess = () => {
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
                OS: '',
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
                    //setSession(TRANSACTION_ID, response.Response.Message);
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

    closeOtpPopup = () => {
        if (this.state.showOtp) {
            document.getElementById('error-message-giftcard-expire-id').className = 'error-message';
            document.getElementById('error-message-giftcard-id').className = 'error-message';
            document.getElementById('otp-giftcard-id').value = '';
            this.setState({showOtp: false, minutes: 0, seconds: 0});
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
                    //Alert.success("Verify OTP success!");
                    //document.getElementById('otp-popup-giftcard').className = "popup special otp-popup";

                    if (getSession(GIFT_CART_SUPER_MARKET) || getSession(GIFT_CART_DOCTOR_CARD) || getSession(GIFT_CART_WELLNESS_CARD)) {
                        this.setState({
                            showAddress: true,
                            showOtp: false,
                            submitting: false,
                            minutes: 0,
                            seconds: 0,
                            errorMessage: ''
                        });
                    } else {
                        this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
                        //this.cpCartSubmitForm();
                        this.submitCartOrder(this.state.trackingNumber);
                    }
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
        this.setState({showOtp: false, minutes: 0, seconds: 0});
    }

    render() {
        let feeRefundProducts = null;
        if (getSession(GIFT_CART_FEE_REFUND)) {
            feeRefundProducts = JSON.parse(getSession(GIFT_CART_FEE_REFUND));
        }
        let givePointProducts = null;
        if (getSession(GIFT_CART_GIVE_POINT)) {
            givePointProducts = JSON.parse(getSession(GIFT_CART_GIVE_POINT));
        }
        let moibleCardProducts = null;
        if (getSession(GIFT_CART_MOBILE_CARD)) {
            moibleCardProducts = JSON.parse(getSession(GIFT_CART_MOBILE_CARD));
        }
        let ecommerceProducts = null;
        if (getSession(GIFT_CART_ECOMMERCE)) {
            ecommerceProducts = JSON.parse(getSession(GIFT_CART_ECOMMERCE));
        }
        let openInvestmentProducts = null;

        if (getSession(GIFT_CART_OPEN_INVESTMENT)) {
            openInvestmentProducts = JSON.parse(getSession(GIFT_CART_OPEN_INVESTMENT)).reduce((acc, current) => {
                const existingProduct = acc.find((product) => product.ProductID === current.ProductID);

                if (existingProduct) {
                    existingProduct.Price = existingProduct.Price + current.Price;
                    existingProduct.MDiscount = existingProduct.MDiscount + current.MDiscount;

                    // const strDetail = existingProduct.strDetail;

                    existingProduct.strDetail = existingProduct.strDetail.replace(
                        /"amount":"(\d+(\.\d+)?)"/,
                        `"amount":"${(parseFloat(existingProduct.MDiscount) / 1000).toFixed(1)}"`
                    ).replace(/dummy\|(\d+(\.\d+)?)\|/, `dummy|${(parseFloat(existingProduct.MDiscount) / 1000).toFixed(1)}|`);
                    // console.log("existingProduct", existingProduct);
                    // console.log("Total", this.state.totalPoint);
                    // console.log("TOTAL_CART_POINT", getSession(TOTAL_CART_POINT));
                } else {
                    acc.push(current);
                }

                return acc;
            }, []);
        }


        let superMarketProducts = null;
        if (getSession(GIFT_CART_SUPER_MARKET)) {
            superMarketProducts = JSON.parse(getSession(GIFT_CART_SUPER_MARKET));
        }
        let doctorCardProducts = null;
        if (getSession(GIFT_CART_DOCTOR_CARD)) {
            doctorCardProducts = JSON.parse(getSession(GIFT_CART_DOCTOR_CARD));
        }

        let wellnessCardProducts = null;
        if (getSession(GIFT_CART_WELLNESS_CARD)) {
            wellnessCardProducts = JSON.parse(getSession(GIFT_CART_WELLNESS_CARD));

            console.log("wellnessCardProducts", wellnessCardProducts);
        }

        let feeRefundBottom = true;
        if (givePointProducts || moibleCardProducts || ecommerceProducts || superMarketProducts || doctorCardProducts || wellnessCardProducts  || openInvestmentProducts) {
            feeRefundBottom = false;
        }

        let givePointTop = true;
        if (feeRefundProducts) {
            givePointTop = false;
        }
        let givePointBottom = true;
        if (moibleCardProducts || ecommerceProducts || superMarketProducts || doctorCardProducts || wellnessCardProducts || openInvestmentProducts) {
            givePointBottom = false;
        }

        let moibleCardTop = true;
        if (feeRefundProducts || givePointProducts || openInvestmentProducts) {
            moibleCardTop = false;
        }
        let moibleCardBottom = true;
        if (ecommerceProducts || superMarketProducts || doctorCardProducts || wellnessCardProducts || openInvestmentProducts) {
            moibleCardBottom = false;
        }

        let ecommerceTop = true;
        if (feeRefundProducts || givePointProducts || moibleCardProducts || openInvestmentProducts) {
            ecommerceTop = false;
        }
        let ecommerceBottom = true;
        if (superMarketProducts || doctorCardProducts || wellnessCardProducts) {
            ecommerceBottom = false;
        }

        let superMarketTop = true;
        if (feeRefundProducts || givePointProducts || moibleCardProducts || ecommerceProducts || openInvestmentProducts) {
            superMarketTop = false;
        }
        let superMarketBottom = true;
        if (doctorCardProducts || wellnessCardProducts) {
            superMarketBottom = false;
        }

        let doctorCardTop = true;
        if (feeRefundProducts || givePointProducts || moibleCardProducts || ecommerceProducts || superMarketProducts || openInvestmentProducts) {
            doctorCardTop = false;
        }

        let wellnessCardTop = true;
        if (feeRefundProducts || givePointProducts || moibleCardProducts || ecommerceProducts || superMarketProducts || openInvestmentProducts || wellnessCardProducts) {
            wellnessCardTop = false;
        }

        let openInvestmentCardTop = true;
        if (feeRefundProducts || givePointProducts || moibleCardProducts || ecommerceProducts || superMarketProducts || openInvestmentProducts) {
            openInvestmentCardTop = false;
        }


        const deleteProductItem = (GiftConstant, products, index) => {
            this.setState({
                reload: true,
                GiftConstantDeleting: GiftConstant,
                productsDeleting: products,
                indexDeleting: index
            });
        }

        const cancelDeletingProductItem = () => {
            this.setState({
                reload: true,
                GiftConstantDeleting: '',
                productsDeleting: null,
                indexDeleting: -1
            });
        }

        const deleteProductItemConfirmed = (GiftConstant, products, index) => {

            let product = products[index];
            let detail = product.strDetail.split('|');
            //console.log("detail", detail);

            let qty = parseInt(detail[4]);
            products.splice(index, 1);
            if (products && products.length > 0) {

                setSession(GiftConstant, JSON.stringify(products));
            } else {
                sessionStorage.removeItem(GiftConstant);
            }
            let point = parseFloat(this.state.totalPoint) - roundUp(parseFloat(detail[3]) * qty);

            setSession(TOTAL_CART_POINT, point.toFixed(1));

            this.setState({
                reload: true,
                totalPoint: point.toFixed(1),
                GiftConstantDeleting: '',
                productsDeleting: null,
                indexDeleting: -1
            });
        }

        const changeQuatity = (event, GiftConstant, products, index) => {
            let value = event.target.value;
            var jsonState = this.state;

            if (!isNaN(value) && value.length <= 3 && value.trim().length > 0 && parseInt(value) > 0) {
                let product = products[index];
                let detail = product.strDetail.split('|');
                let qty = parseInt(value);
                let oldQty = parseInt(detail[4]);
                product.strDetail = detail[0] + "|" + detail[1] + "|" + detail[2] + "|" + detail[3] + "|" + qty + "|" + detail[5];
                products[index] = product;
                setSession(GiftConstant, JSON.stringify(products));
                let point = parseFloat(this.state.totalPoint) - roundUp(parseFloat(detail[3]) * oldQty) + roundUp(parseFloat(detail[3]) * qty);
                setSession(TOTAL_CART_POINT, point);
                jsonState.totalPoint = point;
            }
            jsonState.reload = true;
            this.setState(jsonState);
        }

        const plugQty = (GiftConstant, products, index) => {
            let product = products[index];
            let detail = product.strDetail.split('|');
            let qty = parseInt(detail[4]) + 1;
            if (qty > 999) {
                return;
            }
            product.strDetail = detail[0] + "|" + detail[1] + "|" + detail[2] + "|" + detail[3] + "|" + qty + "|" + detail[5];
            products[index] = product;
            setSession(GiftConstant, JSON.stringify(products));
            let point = (parseFloat(this.state.totalPoint) + roundUp(parseFloat(detail[3])));
            setSession(TOTAL_CART_POINT, point);
            this.setState({reload: true, totalPoint: point});
        }
        const minusQty = (GiftConstant, products, index) => {
            let product = products[index];
            let detail = product.strDetail.split('|');
            let qty = parseInt(detail[4]);
            if (qty <= 1) {
                this.setState({
                    reload: true,
                    GiftConstantDeleting: GiftConstant,
                    productsDeleting: products,
                    indexDeleting: index
                });
                return;
            }
            qty = qty - 1;
            product.strDetail = detail[0] + "|" + detail[1] + "|" + detail[2] + "|" + detail[3] + "|" + qty + "|" + detail[5];
            products[index] = product;
            setSession(GiftConstant, JSON.stringify(products));
            let point = parseFloat(this.state.totalPoint) - roundUp(parseFloat(detail[3]));
            setSession(TOTAL_CART_POINT, point.toFixed(1));
            this.setState({reload: true, totalPoint: point});
        }

        const closeNoTwofa = () => {
            this.setState({noTwofa: false});
        }
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }}/>;
        }
        if (!this.state.showAddress) {
            return (
                <>
                    {this.state.renderMeta &&
                        <Helmet>
                            <title>Giỏ quà của tôi – Dai-ichi Life Việt Nam</title>
                            <meta name="description"
                                  content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                            <meta name="robots" content="noindex, nofollow"/>
                            <meta property="og:type" content="website"/>
                            <meta property="og:url" content={FE_BASE_URL + "/gift-cart"}/>
                            <meta property="og:title" content="Giỏ quà của tôi - Dai-ichi Life Việt Nam"/>
                            <meta property="og:description"
                                  content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                            <meta property="og:image"
                                  content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                            <link rel="canonical" href={FE_BASE_URL + "/gift-cart"}/>
                        </Helmet>
                    }
                    <main className="logined">

                        <div className={parseFloat(this.state.totalPoint) > 0 ? "giftcard-page" : ""}>
                            <div className="breadcrums">
                                <Link to="/" className="breadcrums__item">
                                    <p>Trang chủ</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </Link>
                                <div className="breadcrums__item">
                                    <p style={{zIndex: '999'}}><Link to="/point" className='breadcrums__link'>Điểm
                                        thưởng</Link></p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                                <div className="breadcrums__item">
                                    <p>Giỏ quà của tôi</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                            </div>
                            {(parseFloat(this.state.totalPoint) > 0) &&
                                <section className="scgiftcart">
                                    <div className="container">
                                        <LoadingIndicator area="submit-loyalty-point"/>
                                        {feeRefundProducts &&
                                            <div
                                                className={feeRefundBottom ? "giftcart-border" : "giftcart-border-top"}>
                                                <div className="cart-category-wrapper">

                                                    {feeRefundProducts.map((item, index) => (
                                                        <div className="cart-category">
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Đóng phí/Hoàn trả tạm ứng</h4>
                                                                </div>
                                                            }
                                                            {item &&
                                                                <div className="cart-category__body">
                                                                    <div className="cart-tab">
                                                                        <div className="cart-tab__info">
                                                                            <div className="info-content">
                                                                                <i><img src={FEE_REFUND_LOCAL_IMG}
                                                                                        alt=""/></i>
                                                                                <div className="content">
                                                                                    <h5>{item.strDetail.split('|')[1]}</h5>
                                                                                    <p>Số hợp
                                                                                        đồng: {item.strDetail.split('|')[2]}</p>
                                                                                </div>
                                                                            </div>

                                                                        </div>
                                                                        <div className="cart-tab__delete">
                                                                            <span className="remove-btn simple-brown"
                                                                                  onClick={() => deleteProductItem(GIFT_CART_FEE_REFUND, feeRefundProducts, index)}>Xóa</span>
                                                                        </div>
                                                                        <div className="cart-tab__point">
                                                                            <div className="icon-wrapper"><img
                                                                                src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                            <span>{formatMoney(parseFloat(item.strDetail.split('|')[3]))}</span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {!givePointTop && givePointProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {givePointProducts &&
                                            <div
                                                className={givePointTop ? (givePointBottom ? "giftcart-border" : "giftcart-border-top") : (givePointBottom ? "giftcart-border-bottom" : "giftcart")}>
                                                <div className="cart-category-wrapper">
                                                    {givePointProducts.map((item, index) => (
                                                        <div
                                                            className={index === givePointProducts.length - 1 ? (givePointProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : givePointTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Tặng điểm cho người thân</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={GIVE_POINT_LOCAL_IMG} alt=""/></i>
                                                                            <div className="content">
                                                                                <h5>Tặng điểm cho người thân</h5>
                                                                                <p>Số hợp
                                                                                    đồng: {item.strDetail.split('|')[1]}</p>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_GIVE_POINT, givePointProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {!moibleCardTop && moibleCardProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {moibleCardProducts &&
                                            <div
                                                className={moibleCardTop ? (moibleCardBottom ? "giftcart-border" : "giftcart-border-top") : (moibleCardBottom ? "giftcart-border-bottom" : "giftcart")}>
                                                <div className="cart-category-wrapper">
                                                    {moibleCardProducts.map((item, index) => (
                                                        <div
                                                            className={index === moibleCardProducts.length - 1 ? (moibleCardProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : moibleCardTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Nạp thẻ điện thoại</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={MOBILE_CARD_LOCAL_IMG} alt=""/></i>
                                                                            <div className="content">
                                                                                <h5>{item.strDetail.split('|')[1]}</h5>
                                                                                <p>Số điện
                                                                                    thoại: {item.strDetail.split('|')[2]}</p>
                                                                            </div>
                                                                        </div>
                                                                        <div className="info-counter">
                                                                            <div className="counter">
                                                                                <button className="counter__btn minus">
                                                                                    <span
                                                                                        onClick={() => minusQty(GIFT_CART_MOBILE_CARD, moibleCardProducts, index)}>-</span>
                                                                                </button>
                                                                                <div className="counter__num">
                                                                                    <input maxLength="3"
                                                                                           onChange={(event) => changeQuatity(event, GIFT_CART_MOBILE_CARD, moibleCardProducts, index)}
                                                                                           value={item.strDetail.split('|')[4]}></input>
                                                                                </div>
                                                                                {parseInt(item.strDetail.split('|')[4]) >= 999 ? (
                                                                                    <button
                                                                                        className="counter__btn plus disabled"
                                                                                        onClick={() => plugQty(GIFT_CART_MOBILE_CARD, moibleCardProducts, index)}>
                                                                                        <span>+</span></button>
                                                                                ) : (
                                                                                    <button
                                                                                        className="counter__btn plus">
                                                                                        <span
                                                                                            onClick={() => plugQty(GIFT_CART_MOBILE_CARD, moibleCardProducts, index)}>+</span>
                                                                                    </button>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_MOBILE_CARD, moibleCardProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== moibleCardProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {!ecommerceTop && ecommerceProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {ecommerceProducts &&
                                            <div
                                                className={ecommerceTop ? (ecommerceBottom ? "giftcart-border" : "giftcart-border-top") : (ecommerceBottom ? "giftcart-border-bottom" : "giftcart")}>
                                                <div className="cart-category-wrapper">
                                                    {ecommerceProducts.map((item, index) => (
                                                        <div
                                                            className={index === ecommerceProducts.length - 1 ? (ecommerceProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : ecommerceTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Phiếu quà tặng điện tử (Voucher điện tử)</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={ECOMMERCE_LOCAL_IMG}
                                                                                    alt=""/></i>
                                                                            <div className="content">
                                                                                <h5>{item.strDetail.split('|')[1]}</h5>
                                                                                <p>Số điện
                                                                                    thoại: {item.strDetail.split('|')[2]}</p>
                                                                            </div>
                                                                        </div>
                                                                        <div className="info-counter">
                                                                            <div className="counter">
                                                                                <button className="counter__btn minus">
                                                                                    <span
                                                                                        onClick={() => minusQty(GIFT_CART_ECOMMERCE, ecommerceProducts, index)}>-</span>
                                                                                </button>
                                                                                <div className="counter__num">
                                                                                    <input maxLength="3"
                                                                                           onChange={(event) => changeQuatity(event, GIFT_CART_ECOMMERCE, ecommerceProducts, index)}
                                                                                           value={item.strDetail.split('|')[4]}></input>
                                                                                </div>
                                                                                {parseInt(item.strDetail.split('|')[4]) >= 999 ? (
                                                                                    <button
                                                                                        className="counter__btn plus disabled"
                                                                                        onClick={() => plugQty(GIFT_CART_ECOMMERCE, ecommerceProducts, index)}>
                                                                                        <span>+</span></button>
                                                                                ) : (
                                                                                    <button
                                                                                        className="counter__btn plus">
                                                                                        <span
                                                                                            onClick={() => plugQty(GIFT_CART_ECOMMERCE, ecommerceProducts, index)}>+</span>
                                                                                    </button>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_ECOMMERCE, ecommerceProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== ecommerceProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {!superMarketTop && superMarketProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {superMarketProducts &&
                                            <div
                                                className={superMarketTop ? (superMarketBottom ? "giftcart-border" : "giftcart-border-top") : (superMarketBottom ? "giftcart-border-bottom" : "giftcart")}>
                                                <div className="cart-category-wrapper">
                                                    {superMarketProducts.map((item, index) => (
                                                        <div
                                                            className={index === superMarketProducts.length - 1 ? (superMarketProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : superMarketTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Phiếu quà tặng giấy (Voucher giấy)</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={SUPER_MARKET_LOCAL_IMG}
                                                                                    alt=""/></i>
                                                                            <div className="content">
                                                                                <h5>{item.strDetail.split('|')[1]}</h5>
                                                                            </div>
                                                                        </div>
                                                                        <div className="info-counter">
                                                                            <div className="counter">
                                                                                <button className="counter__btn minus">
                                                                                    <span
                                                                                        onClick={() => minusQty(GIFT_CART_SUPER_MARKET, superMarketProducts, index)}>-</span>
                                                                                </button>
                                                                                <div className="counter__num">
                                                                                    <input maxLength="3"
                                                                                           onChange={(event) => changeQuatity(event, GIFT_CART_SUPER_MARKET, superMarketProducts, index)}
                                                                                           value={item.strDetail.split('|')[4]}></input>
                                                                                </div>
                                                                                {parseInt(item.strDetail.split('|')[4]) >= 999 ? (
                                                                                    <button
                                                                                        className="counter__btn plus disabled"
                                                                                        onClick={() => plugQty(GIFT_CART_SUPER_MARKET, superMarketProducts, index)}>
                                                                                        <span>+</span></button>
                                                                                ) : (
                                                                                    <button
                                                                                        className="counter__btn plus">
                                                                                        <span
                                                                                            onClick={() => plugQty(GIFT_CART_SUPER_MARKET, superMarketProducts, index)}>+</span>
                                                                                    </button>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_SUPER_MARKET, superMarketProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== superMarketProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {!doctorCardTop && doctorCardProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {doctorCardProducts &&
                                            <div
                                                className={doctorCardTop ? "giftcart-border" : "giftcart-border-bottom"}>
                                                <div className="cart-category-wrapper">
                                                    {doctorCardProducts.map((item, index) => (
                                                        <div
                                                            className={index === doctorCardProducts.length - 1 ? (doctorCardProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : doctorCardTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Phiếu khám sức khoẻ</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={DOCTOR_CARD_LOCAL_IMG} alt=""/></i>
                                                                            <div className="content">
                                                                                <h5>{item.strDetail.split('|')[1]}</h5>
                                                                            </div>
                                                                        </div>
                                                                        <div className="info-counter">
                                                                            <div className="counter">
                                                                                <button className="counter__btn minus">
                                                                                    <span
                                                                                        onClick={() => minusQty(GIFT_CART_DOCTOR_CARD, doctorCardProducts, index)}>-</span>
                                                                                </button>
                                                                                <div className="counter__num">
                                                                                    <input maxLength="3"
                                                                                           onChange={(event) => changeQuatity(event, GIFT_CART_DOCTOR_CARD, doctorCardProducts, index)}
                                                                                           value={item.strDetail.split('|')[4]}></input>
                                                                                </div>
                                                                                {parseInt(item.strDetail.split('|')[4]) >= 999 ? (
                                                                                    <button
                                                                                        className="counter__btn plus disabled"
                                                                                        onClick={() => plugQty(GIFT_CART_DOCTOR_CARD, doctorCardProducts, index)}>
                                                                                        <span>+</span></button>
                                                                                ) : (
                                                                                    <button
                                                                                        className="counter__btn plus">
                                                                                        <span
                                                                                            onClick={() => plugQty(GIFT_CART_DOCTOR_CARD, doctorCardProducts, index)}>+</span>
                                                                                    </button>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_DOCTOR_CARD, doctorCardProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== doctorCardProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }

                                        {/*//------------------------------------*/}
                                        {!wellnessCardTop && wellnessCardProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {wellnessCardProducts &&
                                            <div
                                                className={wellnessCardTop ? "giftcart-border" : "giftcart-border-bottom"}>
                                                <div className="cart-category-wrapper">
                                                    {wellnessCardProducts.map((item, index) => (
                                                        <div
                                                            className={index === wellnessCardProducts.length - 1 ? (wellnessCardProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : wellnessCardTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Quà tặng sức khoẻ</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <img style ={{ marginRight: 12 }} src={WELLNESS_GIFT_IMG} alt=""/>
                                                                            <div className="content">
                                                                                <h5>{item.strDetail.split('|')[1]}</h5>
                                                                            </div>
                                                                        </div>
                                                                        <div className="info-counter">
                                                                            <div className="counter">
                                                                                <button className="counter__btn minus">
                                                                                    <span
                                                                                        onClick={() => minusQty(GIFT_CART_WELLNESS_CARD, wellnessCardProducts, index)}>-</span>
                                                                                </button>
                                                                                <div className="counter__num">
                                                                                    <input maxLength="3"
                                                                                           onChange={(event) => changeQuatity(event, GIFT_CART_WELLNESS_CARD, wellnessCardProducts, index)}
                                                                                           value={item.strDetail.split('|')[4]}></input>
                                                                                </div>
                                                                                {parseInt(item.strDetail.split('|')[4]) >= 999 ? (
                                                                                    <button
                                                                                        className="counter__btn plus disabled"
                                                                                        onClick={() => plugQty(GIFT_CART_WELLNESS_CARD, wellnessCardProducts, index)}>
                                                                                        <span>+</span></button>
                                                                                ) : (
                                                                                    <button
                                                                                        className="counter__btn plus">
                                                                                        <span
                                                                                            onClick={() => plugQty(GIFT_CART_WELLNESS_CARD, wellnessCardProducts, index)}>+</span>
                                                                                    </button>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_WELLNESS_CARD, wellnessCardProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point" style={{ width: wellnessCardProducts && '64px' }}>
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== wellnessCardProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                        {/*//------------------------------------*/}
                                        {!openInvestmentCardTop && openInvestmentProducts &&
                                            <div className='cart-category-spacing'></div>
                                        }
                                        {openInvestmentProducts &&
                                            <div
                                                className={openInvestmentCardTop ? "giftcart-border" : "giftcart-border-bottom"}>
                                                <div className="cart-category-wrapper">
                                                    {openInvestmentProducts.map((item, index) => (
                                                        <div
                                                            className={index === openInvestmentProducts.length - 1 ? (openInvestmentProducts.length > 1 ? "cart-category-no-padding-top" : "cart-category") : openInvestmentCardTop ? "cart-category-no-bottom-padding-top" : (index === 0 ? "cart-category-no-bottom-padding-top" : "cart-category-no-bottom")}>
                                                            {index === 0 &&
                                                                <div className="cart-category__header">
                                                                    <h4>Đầu tư quỹ mở</h4>
                                                                </div>
                                                            }
                                                            <div className="cart-category__body">
                                                                <div className="cart-tab">
                                                                    <div className="cart-tab__info">
                                                                        <div className="info-content">
                                                                            <i><img src={iconInvest} alt=""/></i>
                                                                            <div className="content">
                                                                                <h5 style={{
                                                                                    fontSize: 16,
                                                                                    fontWeight: 500,
                                                                                    color: '#292929'
                                                                                }}>{item.strDetail.split('|')[1]}</h5>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="cart-tab__delete">
                                                                        <span className="remove-btn simple-brown"
                                                                              onClick={() => deleteProductItem(GIFT_CART_OPEN_INVESTMENT, openInvestmentProducts, index)}>Xóa</span>
                                                                    </div>
                                                                    <div className="cart-tab__point">
                                                                        <div className="icon-wrapper"><img
                                                                            src={PRICE_TAG_LOCAL_IMG} alt=""/></div>
                                                                        <span>{formatMoney(item.strDetail.split('|')[3])}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            {index !== openInvestmentProducts.length - 1 &&
                                                                <div className="cart-category__line">
                                                                </div>
                                                            }
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        }
                                    </div>
                                </section>
                            }
                            {(parseFloat(this.state.totalPoint) > 0) &&
                                <div className="gift-btn-wrapper">
                                    <div className="btn-wrapper">
                                        <div className="total-point">
                                            <h5>Tổng điểm:</h5>
                                            <div className="point">
                                                <i><img src="img/icon/9.2/9.2-icon-pricetag-red.svg" alt=""/></i>
                                                <p>{formatMoney(this.state.totalPoint)}</p>
                                            </div>
                                        </div>
                                        {(getSession(CLIENT_ID) && !this.state.submitting && !this.state.showOtp) ? (
                                            <button className="btn btn-primary" onClick={this.handleGiftCartSubmit}>Đổi
                                                thưởng</button>
                                        ) : (
                                            <button className="btn btn-primary disabled" disabled>Đổi thưởng</button>
                                        )}

                                    </div>
                                </div>
                            }
                            {(parseFloat(this.state.totalPoint) <= 0) &&

                                <div className="insurance">
                                    <div className="empty">
                                        <div className="icon">
                                            <img src="../../../img/no-gift.svg" alt="no-data"/>
                                        </div>
                                        <p style={{paddingTop: '20px'}}>Hiện tại chưa có sản phẩm nào trong giỏ quà</p>
                                    </div>
                                </div>

                            }
                        </div>


                    </main>
                    <div
                        className={this.state.productsDeleting ? "popup remove-cart-popup special show" : "popup remove-cart-popup special"}>
                        <div className="popup__card">
                            <div className="remove-cart-card">
                                <div className="header">
                                    <h4>Xóa quà/dịch vụ</h4>
                                    <i className="closebtn"><img src="img/icon/close.svg" alt=""
                                                                 onClick={() => cancelDeletingProductItem()}/></i>
                                </div>
                                <div className="body">
                                    <p className="bigheight">Bạn có chắc chắn muốn xoá món quà/dịch vụ này khỏi giỏ quà
                                        không?</p>
                                    <div className="btn-wrapper">
                                        <Link to="/gift-cart" className="btn btn-primary"
                                              onClick={() => deleteProductItemConfirmed(this.state.GiftConstantDeleting, this.state.productsDeleting, this.state.indexDeleting)}
                                              style={{justifyContent: 'center'}}>Tiếp
                                            tục</Link>
                                        <span className="no-btn simple-brown"
                                              onClick={() => cancelDeletingProductItem()}>Hủy</span>
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
                                <Link to={"/point"}>
                                    <div className="close-btn" style={{top: 0}}>
                                        <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn"
                                             onClick={this.closeConfirmSuccess}/>
                                    </div>
                                </Link>
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
                                              onClick={this.closeConfirmSuccess}>Tiếp tục đổi điểm</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="popupbg"></div>
                    </div>
                    {this.state.showOtp &&
                        <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds}
                                   startTimer={this.startTimer} closeOtp={this.closeOtp}
                                   errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupOtpSubmit}
                                   reGenOtp={this.genOtpGiftCard} maskPhone={maskPhone(getSession(CELL_PHONE))}/>
                    }
                    {this.state.noTwofa &&
                        <AuthenticationPopup closePopup={closeNoTwofa}
                                             msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}/>
                    }
                    {/* <div className={this.state.showOtp ? "popup special otp-popup show" : "popup special otp-popup"} id="otp-popup-giftcard">
            <form onSubmit={this.popupOtpSubmit}>
              <div className="popup__card">
                <div className="popup-otp-card">
                  <div className="header">
                    <h4>Nhập mã OTP</h4>
                    <i className="closebtn"><img src="img/icon/close.svg" alt="" onClick={this.closeOtpPopup} /></i>
                  </div>
                  <div className="body">
                    <div className="error-message" id="error-message-giftcard-id">
                      <i className="icon">
                        <img src="img/icon/warning_sign.svg" alt="" />
                      </i>
                      <p>Mã OTP không đúng, Quý khách vui lòng nhập lại.</p>
                    </div>
                    <div className="error-message" id="error-message-giftcard-expire-id">
                      <i className="icon">
                        <img src="img/icon/warning_sign.svg" alt="" />
                      </i>
                      <p>Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.</p>
                    </div>
                    <h5 className="basic-text2">
                      Mã <span className="basic-text2 basic-bold">OTP</span> đã được gửi đến
                      <span className="basic-text2 basic-bold">&nbsp;Số điện thoại</span> của Quý khách. Vui lòng nhập
                      <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác nhận.
                    </h5>
                    <div className="input-wrapper">
                      <div className="input-wrapper-item">
                        <div className="input special-input-extend">
                          <div className="input__content">
                            <label className="--" htmlFor="">Mã OTP</label>
                            <input type="search" name="OTP" id="otp-giftcard-id" onChange={this.handleInputOtpChange} required />
                          </div>
                          <i><img src="img/icon/edit.svg" alt="" /></i>
                        </div>
                      </div>
                    </div>
                    {this.state.minutes === 0 && this.state.seconds === 0
                      ? (
                        <p className="tag">Bạn chưa nhận được mã OTP? <span className="simple-brown" onClick={this.genOtpGiftCard}>Gửi lại mã mới</span></p>
                      ) : (<p className="tag padding-clock"><i className="sand-clock"><img src="../img/icon/sand_clock.svg"/>{this.state.minutes}:{this.state.seconds < 10 ? `0${this.state.seconds}` : this.state.seconds}</i></p>)
                    }

                    <div className="btn-wrapper">
                      <button className="btn btn-primary disabled" id="btn-verify-otp-giftcard">Xác nhận</button>
                    </div>
                  </div>
                </div>
              </div>
              <div className="popupbg"></div>
            </form>
          </div> */}

                </>
            )
        } else {
            return (
                <ExchangePointAddress parentCallBack={this.hideAddress}/>
            )
        }
    }
}

export default GiftCart;
