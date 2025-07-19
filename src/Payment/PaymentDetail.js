import React from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    PAGE_POLICY_PAYMENT,
    PAYMENT_GATEWAY_URL,
    USER_LOGIN,
    PageScreen
} from '../constants';
import NumberFormat from 'react-number-format';
import {getDeviceId, getSession, validateEmail, trackingEvent} from '../util/common';
import {enscryptData} from '../util/APIUtils';

let modalWindow = null;

class PaymentDetail extends React.Component {
    formatter = new Intl.NumberFormat('vi-VN', {

        thousandSeparator: '.',
        decimalSeparator: ',',
        suffix: 'VNĐ',
    })

    constructor(props) {
        super(props);
        this.state = {
            PolicyNo: null,
            ClientID: null,
            POID: null,
            Amount: 0,
            PolMPremAmt: 0,
            PolSndryAmt: 0,
            AllocateMPrem: 0,
            OPL: 0,
            APL: 0,
            OPLInput: 0,
            APLInput: 0,
            isEditOPL: 0,
            isEditAPL: 0,
            PayerAccountName: '',
            ClientName: '',
            PayerName: '',
            CellPhone: null,
            Email: null,
            isEditCellPhone: 0,
            isEditEmail: 0,
            PaymentType: 'UVQ=',
            DOB: '',
            Checkbox: false,
            TotalAmount: '',
            errorEmail: '',
            errorPhone: '',
            errorTotal: '',
            forceIgnore: false,
            showOPLWarning: false

        };
        this.setWrapperRef = this.setWrapperRef.bind(this);
        this.closeErrorPayment = this.closeErrorPayment.bind(this);
        this.closeErrorESC = this.closeErrorESC.bind(this);
        this.closeError = this.closeError.bind(this);
    }

    ConvertToLatin(str) {
        str = str.toLowerCase();
        str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, 'a');
        str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, 'e');
        str = str.replace(/ì|í|ị|ỉ|ĩ/g, 'i');
        str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, 'o');
        str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, 'u');
        str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, 'y');
        str = str.replace(/đ/g, 'd');
        return str.toUpperCase();
    }

    convertDateToString = (value) => {
        const date = new Date(value); // M-D-YYYY
        const d = date.getDate();
        const m = date.getMonth() + 1;
        const y = date.getFullYear();
        const dateString = (d <= 9 ? '0' + d : d) + '/' + (m <= 9 ? '0' + m : m) + '/' + y;
        return dateString;
    }

    encodeBase64(str) {
        //alert(str+" "+ base64_encode(str));
        window.btoa(unescape(encodeURIComponent(str)));
        return window.btoa(unescape(encodeURIComponent(str)));
    }

    //decodeBase64(str){
    // alert(str+" "+ base64_decode(str));
    //  return base64_decode(str);
    //}
    setWrapperRef(node) {
        this.wrapperSucceededRef = node;
    }

    inputAmountChangedHandler = values => {
        const jsonState = this.state;
        if (!values.value || values.value === '') {
            values.value = 0;
        }
        jsonState.Amount = values.value;
        this.setState(jsonState);
        const txtPaymentAmount = document.getElementById("txtPaymentAmount");
        const TotalAmount = this.state.Checkbox ? (parseFloat(values.value) + parseFloat(this.state.OPLInput) + parseFloat(this.state.APLInput)) : parseFloat(values.value);
        jsonState.TotalAmount = TotalAmount;
        this.setState(jsonState);
        txtPaymentAmount.innerText = this.formatter.format(TotalAmount) + " VNĐ";
        if (TotalAmount > 0) {
            document.getElementById("btnPayment").className = "btn btn-primary";
            document.getElementById("btnPayment").disabled = false;
        } else {
            document.getElementById("btnPayment").className = "btn btn-primary disabled";
            document.getElementById("btnPayment").disabled = true;

        }
    };
    inputOPLChangedHandler = values => {
        const jsonState = this.state;
        if (!values.value || values.value === '') {
            values.value = 0;
        }
        jsonState.OPLInput = values.value;
        this.setState(jsonState);
        const txtPaymentAmount = document.getElementById("txtPaymentAmount");
        const TotalAmount = this.state.Checkbox ? (parseFloat(values.value) + parseFloat(this.state.Amount) + parseFloat(this.state.APLInput)) : parseFloat(this.state.Amount);
        txtPaymentAmount.innerText = this.formatter.format(TotalAmount) + " VNĐ";
        jsonState.TotalAmount = TotalAmount;
        this.setState(jsonState);
        if (TotalAmount > 0) {
            document.getElementById("btnPayment").className = "btn btn-primary";
            document.getElementById("btnPayment").disabled = false;
        } else {
            document.getElementById("btnPayment").className = "btn btn-primary disabled";
            document.getElementById("btnPayment").disabled = true;

        }
    };
    inputAPLChangedHandler = values => {
        const jsonState = this.state;
        if (!values.value || values.value === '') {
            values.value = 0;
        }
        jsonState.APLInput = values.value;
        this.setState(jsonState);
        const txtPaymentAmount = document.getElementById("txtPaymentAmount");
        const TotalAmount = this.state.Checkbox ? (parseFloat(values.value) + parseFloat(this.state.Amount) + parseFloat(this.state.OPLInput)) : parseFloat(this.state.Amount);
        txtPaymentAmount.innerText = this.formatter.format(TotalAmount) + " VNĐ";
        jsonState.TotalAmount = TotalAmount;
        this.setState(jsonState);
        if (TotalAmount > 0) {
            document.getElementById("btnPayment").className = "btn btn-primary";
            document.getElementById("btnPayment").disabled = false;
        } else {
            document.getElementById("btnPayment").className = "btn btn-primary disabled";
            document.getElementById("btnPayment").disabled = true;

        }
    };


    editButton(obj) {
        if (obj === "txtInputAPL") {
            if (this.state.isEditAPL === 0 || this.state.isEditAPL === '0') {
                document.getElementById("txtInputAPL").disabled = true;
            }
        } else if (obj === "txtInputOPL") {
            if (this.state.isEditOPL === 0 || this.state.isEditOPL === '0') {
                document.getElementById("txtInputOPL").disabled = true;
            }
        } else if (obj === "txtInputEmail") {
            if (this.state.isEditEmail === 0 || this.state.isEditEmail === '0') {
                document.getElementById("txtInputEmail").disabled = true;
            }
        } else if (obj === "txtInputCellPhone") {
            if (this.state.isEditCellPhone === 0 || this.state.isEditCellPhone === '0') {
                document.getElementById("txtInputCellPhone").disabled = true;
            }
        }
        const txtInput = document.getElementById(obj);
        if (txtInput)
            txtInput.focus();

    };

    componentDidMount() {
        let jsonState;
        if (!this.state.PolicyNo && this.props.PolicyClientProfile && this.props.PolicyInfo && this.props.UserProfile) {
            jsonState = this.state;
            jsonState.PolicyNo = this.props.PolicyInfo[0].PolicyID;
            jsonState.ClientID = this.props.PolicyClientProfile[0].ClientID;
            jsonState.POID = this.props.PolicyClientProfile[0].POID;
            jsonState.Amount = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.AllocateMPrem = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.PolMPremAmt = Number(this.props.PolicyInfo[0].PolMPremAmt);
            jsonState.PolSndryAmt = Number(this.props.PolicyInfo[0].PolSndryAmt);
            jsonState.OPL = Number(this.props.PolicyInfo[0].OPL);
            jsonState.APL = Number(this.props.PolicyInfo[0].APL);
            jsonState.OPLInput = Number(this.props.PolicyInfo[0].OPL);
            jsonState.APLInput = Number(this.props.PolicyInfo[0].APL);
            jsonState.isEditOPL = Number(this.props.PolicyInfo[0].OPL);
            jsonState.isEditAPL = Number(this.props.PolicyInfo[0].APL);
            jsonState.TotalAmount = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.PayerAccountName = this.props.UserProfile[0].FullName ? this.props.UserProfile[0].FullName : "";
            jsonState.PayerName = this.props.UserProfile[0].FullName;
            jsonState.ClientName = this.props.PolicyClientProfile[0].FullName;
            jsonState.Email = this.props.UserProfile[0].Email ? this.props.UserProfile[0].Email.trim() : "";
            jsonState.CellPhone = this.props.UserProfile[0].CellPhone ? this.props.UserProfile[0].CellPhone.trim() : "";
            jsonState.isEditEmail = this.props.UserProfile[0].Email && this.props.UserProfile[0].Email != "" ? (0) : (1);
            jsonState.isEditCellPhone = this.props.UserProfile[0].CellPhone && this.props.UserProfile[0].CellPhone != "" ? (0) : (1);

            jsonState.PaymentType = 'UVQ=';
            jsonState.DOB = this.props.PolicyClientProfile[0].DOB ? this.props.PolicyClientProfile[0].DOB.substr(0, 10) : '';
            this.setState(jsonState);
        } else {
            jsonState = this.state;
            jsonState.PolicyNo = null;
            jsonState.ClientID = null;
            jsonState.Amount = null;
            jsonState.OPL = null;
            jsonState.APL = null;
            jsonState.OPLInput = 0;
            jsonState.APLInput = 0;
            jsonState.PayerAccountName = "";
            jsonState.CellPhone = "";
            jsonState.Email = "";
            jsonState.PaymentType = 'UVQ=';
            this.setState(jsonState);

        }
        document.addEventListener("mousedown", this.closeError);
        document.addEventListener("keydown", this.closeErrorESC);
    }

    componentDidUpdate() {
        let jsonState;
        if (this.props.PolicyClientProfile && this.props.PolicyInfo && this.props.PolicyInfo[0] && (this.state.PolicyNo != this.props.PolicyInfo[0].PolicyID) && this.props.UserProfile) {
            jsonState = this.state;
            jsonState.PolicyNo = this.props.PolicyInfo[0].PolicyID;
            jsonState.ClientID = this.props.PolicyClientProfile[0].ClientID;
            jsonState.POID = this.props.PolicyClientProfile[0].POID;
            jsonState.Amount = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.AllocateMPrem = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.PolMPremAmt = Number(this.props.PolicyInfo[0].PolMPremAmt);
            jsonState.PolSndryAmt = Number(this.props.PolicyInfo[0].PolSndryAmt);
            jsonState.OPL = Number(this.props.PolicyInfo[0].OPL);
            jsonState.APL = Number(this.props.PolicyInfo[0].APL);
            jsonState.OPLInput = Number(this.props.PolicyInfo[0].OPL);
            jsonState.APLInput = Number(this.props.PolicyInfo[0].APL);
            jsonState.isEditOPL = Number(this.props.PolicyInfo[0].OPL);
            jsonState.isEditAPL = Number(this.props.PolicyInfo[0].APL);
            jsonState.TotalAmount = Number(this.props.PolicyInfo[0].AllocateMPrem);
            jsonState.PayerAccountName = this.props.UserProfile[0].FullName ? this.props.UserProfile[0].FullName : "";
            jsonState.PayerName = this.props.UserProfile[0].FullName;
            jsonState.ClientName = this.props.PolicyClientProfile[0].FullName;
            jsonState.Email = this.props.UserProfile[0].Email ? this.props.UserProfile[0].Email.trim() : "";
            jsonState.CellPhone = this.props.UserProfile[0].CellPhone ? this.props.UserProfile[0].CellPhone.trim() : "";
            jsonState.isEditEmail = this.props.UserProfile[0].Email && this.props.UserProfile[0].Email != "" ? (0) : (1);
            jsonState.isEditCellPhone = this.props.UserProfile[0].CellPhone && this.props.UserProfile[0].CellPhone != "" ? (0) : (1);

            jsonState.PaymentType = 'UVQ=';
            jsonState.DOB = this.props.PolicyClientProfile[0].DOB ? this.props.PolicyClientProfile[0].DOB.substr(0, 10) : '';
            this.setState(jsonState);
        } 
    }

    componentWillUnmount() {
        document.removeEventListener("mousedown", this.closeError);
        document.removeEventListener("keydown", this.closeErrorESC);
    }

    closeErrorPayment() {
        if (!this.state.forceIgnore) {
            this.setState({forceIgnore: true});
        }
    }

    closeError = (event) => {
        if (this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) {
            this.closeErrorPayment();
        }
    }

    closeErrorESC(event) {
        if (event.keyCode === 27) {
            this.closeErrorPayment();
            this.closeOPLWaringPopup();
        }
    }

    closeOPLWaringPopup() {
        if (this.state.showOPLWarning) {
            this.setState({showOPLWarning: false});
        }
    }

    render() {
        const openModalAndUponCloseReturnValueTo = () => {
            if (this.state.PolicyNo && handleValidateInput()) {
                let enscriptRequest = {
                    jsonDataInput: {
                        APIToken: getSession(ACCESS_TOKEN),
                        Action: "EncryptAES",
                        Authentication: AUTHENTICATION,
                        ClientID: getSession(CLIENT_ID),
                        Company: COMPANY_KEY,
                        Data: "{\"customer\":{\"PayerName\":\"" + this.state.PayerName + "\", \"POName\":\"" + this.state.ClientName + "\", \"TotalAmount\":\"" + this.state.TotalAmount + "\", \"PolicyNo\":\"" + this.state.PolicyNo + "\",\"Phone\":\"" + this.state.CellPhone + "\",\"PaymentType\":\"" + this.state.PaymentType + "\",\"Email\":\"" + this.state.Email + "\",\"ClientID\":\"" + this.state.ClientID + "\",\"Amount\":\"" + this.state.Amount + "\",\"APL\":\"" + (this.state.Checkbox ? this.state.APLInput : 0) + "\",\"OPL\":\"" + (this.state.Checkbox ? this.state.OPLInput : 0) + "\",\"FromApp\":\"WCP\"}}",
                        DeviceId: getDeviceId(),
                        Project: "mcp",
                        UserLogin: getSession(USER_LOGIN),
                        // POName: this.state.ClientName,
                        // TotalAmount: this.state.Amount,
                    }
                }
                enscryptData(enscriptRequest).then(Res => {

                    let Response = Res.Response;

                    if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                        const url = PAYMENT_GATEWAY_URL + "?Data=" + Response.Message;
                        modalWindow = window.open(url, "Popup", "width=800,height=1000,top=500,left=50");

                        modalWindow.focus();

                        this.setState({errorEmail: '', errorPhone: '', errorTotal: ''});
                    } else {

                    }
                }).catch(error => {
                    console.log("error", error);
                });
            }

        };
        const closeOPLWaringPopup = () => {
            if (this.state.showOPLWarning) {
                this.setState({showOPLWarning: false});
            }
        }
        const continuePayment = () => {
            this.closeOPLWaringPopup();
            openModalAndUponCloseReturnValueTo();
            trackingEvent(
                `Web_${PageScreen.CLICK_PAYMENT_INS_FEE}`,
                `Web_${PageScreen.CLICK_PAYMENT_INS_FEE}`,
                `Web_${PageScreen.CLICK_PAYMENT_INS_FEE}`,
            );
        }
        const handleValidateInput = () => {
            let valid = true;
            let jsonState = this.state;
            let errEmail = !validateEmail(this.state.Email) ? "Địa chỉ email không hợp lệ, vui lòng kiểm tra lại" : "";
            if (errEmail !== this.state.errorEmail) {
                jsonState.errorEmail = errEmail;
            }
            let errPhone = ((this.state.CellPhone === "") || (!/^\d+$/.test(this.state.CellPhone)) || (this.state.CellPhone.length < 10) || (this.state.CellPhone.length > 11)) ? "Số phone không hợp lệ, vui lòng kiểm tra lại" : "";
            if (errPhone !== this.state.errorPhone) {
                jsonState.errorPhone = errPhone;
            }
            const TotalAmount = this.state.Checkbox ? (parseFloat(this.state.APLInput) + parseFloat(this.state.Amount) + parseFloat(this.state.OPLInput)) : parseFloat(this.state.Amount);
            let errTotal = TotalAmount < 1000 ? "Số tiền thanh toán tối thiểu là 1.000 VNĐ" : "";
            if (errTotal !== this.state.errorTotal) {
                jsonState.errorTotal = errTotal;
            }
            if ((errEmail.length > 0) || (errPhone.length > 0) || (errTotal.length > 0)) {
                valid = false;
                jsonState.forceIgnore = false;
                this.setState(jsonState);
            }
            return valid;
        }

        const submitModelAndUpon = () => {
            if (handleValidateInput()) {
                if (this.state.Checkbox && this.state.OPLInput > 0) {
                    this.setState({showOPLWarning: true});
                } else {
                    openModalAndUponCloseReturnValueTo();
                }
            }
        }
        const policyPayment_Click = () => {
            document.getElementById("divPaymentPolicy").className = "popup bill-policy-popup special show";

        };
        const policyPaymentClose_Click = () => {
            document.getElementById("divPaymentPolicy").className = "popup bill-policy-popup special";

        };
        const disableParentWindow = () => {
            if (modalWindow && !modalWindow.closed) {
                modalWindow.focus();
            }
        }
        const collapse = (obj) => {
            const ctr = document.getElementById(obj);
            if (ctr.classList.contains('show') === false) {
                ctr.classList.add('show');
            } else {
                ctr.classList.remove('show');
            }
        }
        const PayerChange = () => {
            const jsonState = this.state;
            jsonState.PayerAccountName = document.getElementById("txtInputPayerAccountName").value;
            jsonState.CellPhone = document.getElementById("txtInputCellPhone").value.trim();
            jsonState.Email = document.getElementById("txtInputEmail").value.trim();
            this.setState(jsonState);
        }

        const checkboxClick = () => {
            const txtPaymentAmount = document.getElementById("txtPaymentAmount");
            const TotalAmount = !this.state.Checkbox ? (parseFloat(this.state.OPLInput) + parseFloat(this.state.Amount) + parseFloat(this.state.APLInput)) : parseFloat(this.state.Amount);
            txtPaymentAmount.innerText = this.formatter.format(TotalAmount) + " VNĐ";

            const jsonState = this.state;
            jsonState.Checkbox = !jsonState.Checkbox;
            jsonState.TotalAmount = TotalAmount;
            this.setState(jsonState);
        }
        let isfamilypayment = false;
        if (window.location.pathname === '/familypayment') {
            isfamilypayment = true;
        }

        if (this.props.SearchResult === true) {
            if (this.props.PolicyClientProfile && this.props.PolicyInfo) {
                const PolicyClientProfile = this.props.PolicyClientProfile;
                const PolicyInfo = this.props.PolicyInfo;
                //this.UpdateState();
                return (
                    <>
                        <div className="insurance" onFocus={() => disableParentWindow()}
                             onClick={() => disableParentWindow()}>
                            <div className="contractform">
                                <div className="contractform__head">
                                    <h3 className="contractform__head-title">CHI TIẾT HỢP ĐỒNG</h3>
                                    <div className="contractform__head-content">
                                        <div className="item">
                                            <p className="item-label">Hợp đồng</p>
                                            <p className="item-content basic-big">{this.state.PolicyNo} </p>
                                        </div>
                                        <div className="contractform__head-content">
                                            <div className="item">
                                                <p className="item-label">Bên mua bảo hiểm</p>
                                                <p className="item-content basic-big">{this.state.ClientName} </p>
                                            </div>
                                            <div className="item">
                                                <p className="item-label">CMND/CCCD</p>
                                                <p className="item-content">{this.state.POID}</p>
                                            </div>
                                            <div className="item">
                                                <p className="item-label">Ngày sinh</p>
                                                <p className="item-content">{this.state.DOB}</p>
                                            </div>

                                        </div>

                                    </div>
                                </div>

                                <div className="contractform__body">
                                    <div className="info">
                                        <div className="info__title">Số TIỀN THANH TOÁN</div>
                                        <div className="info__content">
                                            <div className="info__content-item">
                                                <div className="input money">
                                                    <div className="input__content">
                                                        <label>Số tiền</label>
                                                        <NumberFormat className="basic-uppercase"
                                                                      id='txtInputAmount'
                                                                      value={Number(this.state.Amount)}
                                                                      thousandSeparator={'.'} decimalSeparator={','}
                                                                      suffix={' VNĐ'}
                                                                      onValueChange={this.inputAmountChangedHandler}
                                                                      allowNegative={false}/>
                                                    </div>
                                                    <i onClick={() => this.editButton("txtInputAmount")}><img
                                                        src="img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="detail dropdown detail-insurance-custom" id="btnPremium">
                                        <div className="dropdown__content" onClick={() => collapse('btnPremium')}>
                                            <p className="simple-brown basic-semibold">Xem chi tiết</p>
                                            <span className="icon simple-brown basic-semibold">></span>
                                        </div>
                                        <div className="dropdown__items">
                                            <div className="yellowtab">
                                                <div className="tab">
                                                    <p className="simple-brown">Phí dự tính định kỳ</p>
                                                    <p className="simple-brown">{Number(this.state.PolSndryAmt) === 0 ? "-" : this.formatter.format(Number(this.state.PolSndryAmt)) + " VNĐ"}</p>
                                                </div>
                                                <div className="tab">
                                                    <p className="simple-brown">Phí cơ bản định kỳ</p>
                                                    <p className="simple-brown">{PolicyInfo != null && this.formatter.format(Number(this.state.PolMPremAmt)) + " VNĐ"}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="detail dropdown detail-insurance-custom" id='btnOPL'>
                                        <div className="dropdown__content" onClick={() => collapse('btnOPL')}>
                                            <p className="simple-brown basic-semibold">
                                                {"Bạn có khoản tạm ứng "}
                                                <span
                                                    className="basic-red basic-semibold">{PolicyInfo != null && (this.formatter.format(Number(this.state.OPL) + Number(this.state.APL)) + " VNĐ")}</span> cần
                                                hoàn trả
                                                <span
                                                    className="icon simple-brown basic-semibold basic-semibold">></span>
                                            </p>
                                        </div>
                                        <div className="dropdown__items">
                                            <div className="tab">
                                                <div
                                                    className={this.state.isEditOPL === 0 ? ('input money disabled') : ('input money')}>
                                                    <div className="input__content">
                                                        <label>Hoàn trả tạm ứng tiền mặt</label>
                                                        <NumberFormat className="basic-uppercase" id="txtInputOPL"
                                                                      onClick={() => this.editButton("txtInputOPL")}  {...this.state.isEditOPL === 0 ? ('disabled') : ('')}
                                                                      value={this.state.OPLInput}
                                                                      thousandSeparator={'.'} decimalSeparator={','}
                                                                      suffix={' VNĐ'}
                                                                      onValueChange={this.inputOPLChangedHandler}
                                                                      allowNegative={false}/>
                                                    </div>
                                                    <i onClick={() => this.editButton("txtInputOPL")}><img
                                                        src="img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                            <div className="tab">
                                                <p className="tag basic-grey">
                                                    *Chúng tôi xin lưu ý không áp dụng hình thức thanh toán bằng thẻ tín
                                                    dụng (Credit Card) để hoàn
                                                    trả khoản tạm ứng tiền mặt
                                                </p>
                                            </div>
                                            <div className="tab">
                                                <div
                                                    className={this.state.isEditAPL === 0 ? ('input money disabled') : ('input money')}>
                                                    <div className="input__content">
                                                        <label>Hoàn trả tạm ứng đóng phí tự động</label>
                                                        <NumberFormat className="basic-uppercase" id="txtInputAPL"
                                                                      value={this.state.APLInput}
                                                                      onClick={() => this.editButton("txtInputAPL")}
                                                                      thousandSeparator={'.'} decimalSeparator={','}
                                                                      suffix={' VNĐ'}
                                                                      onValueChange={this.inputAPLChangedHandler}
                                                                      allowNegative={false}/>
                                                    </div>
                                                    <i onClick={() => this.editButton("txtInputAPL")}><img
                                                        src="img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                            <div className="tab">
                                                <div className="checkbox-warpper">
                                                    <label className="checkbox">
                                                        <input type="checkbox" checked={this.state.Checkbox} id='ckOPL'
                                                               onClick={() => checkboxClick()}/>
                                                        <div className="checkmark">
                                                            <img src="../img/icon/check.svg" alt=""/>
                                                        </div>
                                                    </label>
                                                    <p>Xác nhận số tiền thanh toán hoàn trả tạm ứng</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="info">
                                        <div className="info__title">NGƯỜI THANH TOÁN</div>
                                        <div className="info__content">
                                            <div className="info__content-item">
                                                <div className="input disabled">
                                                    <div className="input__content">
                                                        <label>Họ và tên</label>
                                                        <input value={this.state.PayerName} disabled type="search"/>
                                                    </div>
                                                    <i><img src="../img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                            <div className="info__content-item">
                                                <div className="input">
                                                    <div className="input__content">
                                                        <label>Họ và tên chủ thẻ thanh toán</label>
                                                        <input className="basic-uppercase" id="txtInputPayerAccountName"
                                                               value={this.state.PayerAccountName} type="search"
                                                               onChange={() => PayerChange()}
                                                               onValueChange={() => PayerChange()}/>
                                                    </div>
                                                    <i onClick={() => this.editButton("txtInputPayerAccountName")}><img
                                                        src="img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                            <div className="info__content-item">
                                                <div
                                                    className={this.state.isEditEmail === 0 ? ('input disabled') : ('input')}>
                                                    <div className="input__content">
                                                        <label>Email</label>
                                                        <input value={this.state.Email} id="txtInputEmail"
                                                               onClick={() => this.editButton("txtInputEmail")}
                                                               onChange={() => PayerChange()}
                                                               onValueChange={() => PayerChange()} type="search"/>
                                                    </div>
                                                    <i><img src="../img/icon/input_mail.svg" alt=""
                                                            onClick={() => this.editButton("txtInputEmail")}/></i>
                                                </div>
                                            </div>
                                            {(this.state.errorEmail.length > 0) && <span style={{
                                                color: 'red',
                                                margin: '0 0 8px 0'
                                            }}>{this.state.errorEmail}</span>}
                                            <div className="info__content-item">
                                                <div
                                                    className={this.state.isEditCellPhone === 0 ? ('input disabled') : ('input')}>
                                                    <div className="input__content">
                                                        <label>Số điện thoại</label>
                                                        <input value={this.state.CellPhone} id="txtInputCellPhone"
                                                               onClick={() => this.editButton("txtInputCellPhone")}
                                                               onChange={() => PayerChange()}
                                                               onValueChange={() => PayerChange()} type="search"/>
                                                    </div>
                                                    <i><img src="../img/icon/input_phone.svg"
                                                            onClick={() => this.editButton("txtInputCellPhone")}
                                                            alt=""/></i>
                                                </div>
                                            </div>
                                            {(this.state.errorPhone.length > 0) && <span style={{
                                                color: 'red',
                                                margin: '0 0 8px 0'
                                            }}>{this.state.errorPhone}</span>}
                                        </div>
                                    </div>
                                </div>

                                <img className="decor-clip" src="img/mock.svg" alt=""/>
                                <img className="decor-person" src="img/person.png" alt=""/>
                            </div>
                            <p className="rule">
                                Bằng cách bấm nút thanh toán, Quý khách hàng đã đồng ý với <br/>
                                <a><span className="basic-red" onClick={() => policyPayment_Click()}>Điều khoản sử dụng dịch vụ thanh toán trực tuyến</span></a>
                            </p>

                            <div className="bottom-btn" style={{zIndex: 250}}>
                                <button
                                    className={this.state.TotalAmount && this.state.TotalAmount > 0 ? ("btn btn-primary") : ("btn btn-primary disabled")}
                                    id='btnPayment' disabled={this.state.TotalAmount === 0}
                                    onClick={() => submitModelAndUpon()}>
                                    <div className="btn__content">
                                        <p className="btn__content-item basic-semibold">Thanh Toán</p>
                                        <p className="btn__content-item"
                                           id='txtPaymentAmount'>{this.state.TotalAmount && this.state.TotalAmount > 0 ? this.formatter.format(this.state.TotalAmount) : ("0")}VNĐ</p>
                                    </div>
                                </button>
                            </div>


                            <div className="popup bill-policy-popup special" id="divPaymentPolicy">
                                <div className="popup__card">
                                    <div className="bill-policy">
                                        <button className="bill-policy__close-button"
                                                onClick={() => policyPaymentClose_Click()}>
                                            <img src="../img/icon/close-icon.svg" alt=""/>
                                        </button>
                                        <div className="bill-policy__content">
                                            <div className="icon-wrapper">
                                                <img src="../img/icon/dieukhoan_icon.svg" alt=""/>
                                            </div>
                                            <h4 className="title">Điều khoản sử dụng dịch vụ thanh toán trực tuyến</h4>
                                            <p className="subtitle">Khách hàng cam kết:</p>
                                            <div className="content">
                                                <p>
                                                    1. Việc sử dụng dịch vụ thanh toán trực tuyến là hoàn toàn tự nguyện
                                                    và khách hàng không thể hủy, thay đổi bất kỳ giao dịch thanh toán
                                                    nào đã được thực hiện thành công.
                                                </p>
                                                <p>
                                                    2. Đảm bảo tính chính xác của các thông tin thẻ (Tên chủ thẻ, số
                                                    thẻ, thời hạn thẻ, mã số xác minh thẻ gồm 03 số phía sau thẻ) và
                                                    thông tin thanh toán khi thực hiện giao dịch thanh toán trực tuyến.
                                                </p>
                                                <p>
                                                    3. Chịu trách nhiệm bảo mật thông tin đăng nhập tài khoản thanh toán
                                                    trực tuyến, các thông tin thẻ, mã OTP (được cung cấp qua điện thoại)
                                                    và chịu toàn bộ thiệt hại xảy ra do vô tình hay cố ý để bất kỳ cá
                                                    nhân, tổ chức nào sử dụng thông tin đăng nhập tài khoản thanh toán
                                                    trực tuyến, các thông tin thẻ, mã OTP nhằm thực hiện các hành vi
                                                    gian lận trong dịch vụ thanh toán trực tuyến.
                                                </p>
                                                <p>
                                                    4. Tuân thủ đúng, đầy đủ Thỏa thuận sử dụng dịch vụ giao dịch điện
                                                    tử mà khách hàng và Dai-ichi Life đã ký kết.<a
                                                    href={PAGE_POLICY_PAYMENT} target="_blank">(Xem chi tiết)</a>
                                                </p>
                                                <p>
                                                    Mọi chi tiết liên quan đến các giao dịch thanh toán trực tuyến sẽ
                                                    được bảo mật theo quy định của pháp luật.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="popupbg"></div>
                            </div>

                        </div>
                        {((this.state.errorTotal.length > 0) && !this.state.forceIgnore) &&
                            <div className="popup special point-error-popup show" ref={this.setWrapperRef}>
                                <div className="popup__card">
                                    <div className="point-error-card">
                                        <div className="close-btn">
                                            <img src="../img/icon/close-icon.svg" alt="closebtn" className="btn"
                                                 onClick={this.closeErrorPayment}/>
                                        </div>
                                        <div className="picture-wrapper">
                                            <div className="picture">
                                                <img src="../img/popup/quyenloi-popup.svg" alt="popup-hosobg"/>
                                            </div>
                                        </div>
                                        <div className="content">
                                            <p>{this.state.errorTotal}</p>
                                        </div>
                                    </div>
                                </div>
                                <div className="popupbg"></div>
                            </div>
                        }
                        {this.state.showOPLWarning &&
                            <div className="popup option-popup show">
                                <div className="popup__card">
                                    <div className="optioncard">
                                        <p>Chúng tôi xin lưu ý không áp dụng hình thức thanh toán bằng thẻ tín dụng
                                            (Credit Card) để hoàn trả khoản tạm ứng tiền mặt</p>
                                        <div className="btn-wrapper">
                                            <button className="btn btn-primary" id="existed"
                                                    onClick={() => continuePayment()}>Tiếp tục
                                            </button>
                                        </div>
                                        <i className="closebtn option-closebtn"><img src="img/icon/close.svg" alt=""
                                                                                     onClick={() => closeOPLWaringPopup()}/></i>
                                    </div>
                                </div>
                                <div className="popupbg"></div>
                            </div>
                        }
                    </>
                )
            } else if (this.props.policyInquiryExceed) {
                return (
                    <div className="empty searchingfail">
                        <div className="icon">
                            <img src="../img/popup/quyenloi-popup.svg" alt=""/>
                        </div>
                        <h4 className="basic-semibold">Rất tiếc</h4>
                        <p>{this.props.policyInquiryExceed}</p>
                    </div>
                )

            } else {
                return (

                    {
                        ...this.props.PolicyClientProfile ?
                            (
                                <div className="empty searchingfail">
                                    <div className="icon">
                                        <img src="../img/popup/quyenloi-popup.svg" alt=""/>
                                    </div>
                                    <h4 className="basic-semibold">Rất tiếc</h4>
                                    <p>{"Hợp đồng không thỏa điều kiện thanh toán. Quý khách vui lòng kiểm tra lại!"}</p>
                                </div>
                            )
                            : (

                                <div className="empty searchingfail">
                                    <div className="icon">
                                        <img src="../img/popup/quyenloi-popup.svg" alt=""/>
                                    </div>
                                    <h4 className="basic-semibold">Rất tiếc</h4>
                                    <p>Hợp đồng không thỏa điều kiện thanh toán. Quý khách vui lòng kiểm tra lại!</p>
                                </div>

                            )
                    }

                )
            }
        } else if (this.props.policyInquiryExceed) {
            return (
                <div className="empty searchingfail">
                    <div className="icon">
                        <img src="../img/popup/quyenloi-popup.svg" alt=""/>
                    </div>
                    <h4 className="basic-semibold">Rất tiếc</h4>
                    <p>{this.props.policyInquiryExceed}</p>
                </div>
            )

        } else {
            return (
                <div className="sccontract-container">
                    <div className="insurance">
                        <div className="empty">
                            <div className="icon">
                                <img src="img/icon/empty.svg" alt=""/>
                            </div>

                            <p style={{paddingTop: '20px'}}>{isfamilypayment ? 'Bạn hãy chọn thông tin ở phía bên trái nhé!' : 'Thông tin chi tiết sẽ hiển thị khi bạn chọn một hợp đồng ở bên trái.'}</p>
                        </div>
                    </div>
                </div>
            )
        }
    }
}


export default PaymentDetail;