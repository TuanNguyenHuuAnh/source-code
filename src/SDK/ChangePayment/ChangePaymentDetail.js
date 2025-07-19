import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import {maskPhone, deleteND13DataTemp, getUrlParameter, getSession, onlyLettersAndSpaces, isOnlyNormalText, formatFullName, trackingEvent, cpSaveLogSDK } from '../../util/common';
import {callbackAppOpenLink} from '../sdkCommon';
import { genOTP, CPConsentConfirmation, onlineRequestSubmitConfirm, getZipCodeMasterData, getBankMasterData, CPServicesPSPaymentProcess} from '../../util/APIUtils';
import POWarningND13 from "../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ND13 from "../ND13";
import DOTPInput from '../../components/DOTPInput';
import PayModeNoticePopup from '../../components/PayModeNoticePopup';
import parse from 'html-react-parser';
import iconArrowLeftBrown from '../../img/icon/arrow-left-brown.svg';
import iconArrowDownBrown from '../../img/icon/arrow-down-bronw.svg';
import {
    FUND_STATE,
    COMPANY_KEY,
    AUTHENTICATION,
    WEB_BROWSER_VERSION,
    FE_BASE_URL,
    ConsentStatus,
    CONFIRM_ACTION_MAPPING,
    OTP_INCORRECT,
    OTP_EXPIRED,
    PAGE_POLICY_PAYMENT,
    IS_MOBILE,
    PAYMENT_SUB_STATE,
    FULL_NAME,
    PageScreen
  } from '../../constants';
  import PaymentType from './PaymentType';
  import PaymentMethod from './PaymentMethod';
  import PaymentTypeReview from './PaymentTypeReview';
  import {isEmpty} from "lodash";
  import PaymentMethodReview from './PaymentMethodReview';
  import PaymentTypePartialWithdrawIL from './PaymentTypePartialWithdrawIL';

let from = '';
class ChangePaymentDetail extends Component {
    constructor(props) {
        super(props);
        this.inputRef =  React.createRef();
        this.state = {
            radioPayModeYear: false,
            radioPayModeHaflYear: false,
            newFrequency: '',
            polMPremAmt: '',
            polMPremAmtMax: '',
            polMPremAmtAddtional: '',
            newFrequencyCode: '',
            newPolMPremAmt: 0,
            exceedMax: false,
            //errorMessage: '',
            OTP: '',
            trackingId: this.props.trackingId,
            openPopupWarningDecree13: false,
            clientProfile: null,
            configClientProfile: null,
            clientListStr: '',
            appType: '',
            proccessType: this.props.proccessType,
            stepName: this.props.stepName,
            subStepName: this.props.subStepName,
            showNotice: false,
            acceptPolicy: false,
            isValidInput: false,
            isValidStep1Input: false,
            agree: false,
            amount: '', //Gia tri input ban dau hoac default load
            sum: 0, //Gia tri sum tu cac phuong thuc thanh toan input
            reason: '',
            maxLoanAmount: -1, //max Loan Amount
            minLoanAmount: 2000000, //min Loan Amount
            errorMessage: '',
            errorMessageStep1: '',
            errorMessageIL: '',
            // warningMessage: '',
            zipCodeList: null,
            bankList: null,
            checkedPaymentMethod: [],
            paymentMethod: {
                transfer: {
                    PaymentMethodID:'P3',
                    PaymentMethodName:'Chuyển khoản qua số tài khoản',
                    AccountName: formatFullName(this.props.clientName),
                    AccountNumber: '',
                    BankName: '',
                    BankCode: undefined,
                    CityCode: undefined,
                    BranchName: '',
                    BranchCode: '',
                    BankDealingRoom: '',
                    Province: '',
                    Amount: ''
                },
                payInsurance: {
                    PaymentMethodID:'P6',
                    PaymentMethodName:'Đóng phí bảo hiểm',
                    polInfoList: [
                        {
                            PolicyNo: '',
                            POName: '',
                            Amount: '',
                            warningMessage: ''
                        }
                    ]
                },
                refundLoan: {
                    PaymentMethodID:'P7',
                    PaymentMethodName:'Tạm ứng từ Giá trị hoàn lại',
                    polInfoList: [
                        {
                            PolicyNo: '',
                            POName: '',
                            Amount: ''
                        }
                    ]
                },
                APL: {
                    PaymentMethodID:'P8',
                    PaymentMethodName:'Trả tạm ứng đóng phí tự động',
                    polInfoList: [
                        {
                            PolicyNo: '',
                            POName: '',
                            Amount: ''
                        }
                    ]
                },
                premiumRefund: {
                    PaymentMethodID:'P9',
                    PaymentMethodName:'Hoàn trả chủ thẻ hoặc chủ tài khoản thanh toán',
                    Amount: ''
                },
                disabledButton: true
            },
            FundList: [],
            attachmentState: {
                previewVisible: false,
                previewImage: "",
                previewTitle: "",
                attachmentList: [],
                disabledButton: true,
            },
            showDetailFundIL: false,
            position: { top: 0, left: 0 },
            maxDividendAmount: -1, //max Dividend Amount
            minDividendAmount: 100000, //min Dividend Amount
            maxCouponAmount: -1, //max Coupon Amount
            minCouponAmount: 100000, //min Coupon Amount
            maxPartialWithdrawalAmount: -1, //max PartialWithdrawal Amount
            minPartialWithdrawalAmount: 2000000, //min PartialWithdrawal Amount
            minPartialWithdrawalILAmount: 100000, //min PartialWithdrawal IL Amount
            minPartialWithdrawalILSum: 2000000, //min PartialWithdrawal IL Sum
            isSubmitting: false
        };

        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerInputAmount = this.inputAmount.bind(this);
        this.handlerInputReason = this.inputReason.bind(this);
        this.handlerToggleCheckedPaymentMethod = this.toggleCheckedPaymentMethod.bind(this);
        this.handlerOnChangeTransferAccountName = this.onChangeTransferAccountName.bind(this);
        this.handlerOnChangeAccountNumber = this.onChangeAccountNumber.bind(this);
        this.handlerOnChangeBank = this.onChangeBank.bind(this);
        this.handlerOnChangeCity = this.onChangeCity.bind(this);
        this.handlerOnChangeBranchName = this.onChangeBranchName.bind(this);
        this.handlerOnChangeBankDealingRoom = this.onChangeBankDealingRoom.bind(this);
        this.handlerOnChangeBankOfficeAmount = this.onChangeBankOfficeAmount.bind(this);
        this.handlerValidateInputPaymentMethod = this.validateInputPaymentMethod.bind(this);
        this.handlerUpdatePaymentMethod = this.updatePaymentMethod.bind(this);
        this.handlerAddPayInsurancePolInfo = this.addPayInsurancePolInfo.bind(this);
        this.handlerOnChangePayInsurancePolicyNo = this.onChangePayInsurancePolicyNo.bind(this);
        this.handlerOnChangePayInsurancePOName = this.onChangePayInsurancePOName.bind(this);
        this.handlerOnChangePayInsuranceAmount = this.onChangePayInsuranceAmount.bind(this);
        this.handlerRemovePayInsurancePolInfo = this.removePayInsurancePolInfo.bind(this);
        this.handlerAddRefundLoanPolInfo = this.addRefundLoanPolInfo.bind(this);
        this.handlerOnChangeRefundLoanPolicyNo = this.onChangeRefundLoanPolicyNo.bind(this);
        this.handlerOnChangeRefundLoanPOName = this.onChangeRefundLoanPOName.bind(this);
        this.handlerOnChangeRefundLoanAmount = this.onChangeRefundLoanAmount.bind(this);
        this.handlerRemoveRefundLoanPolInfo = this.removeRefundLoanPolInfo.bind(this);
        this.handlerAddAPLPolInfo = this.addAPLPolInfo.bind(this);
        this.handlerOnChangeAPLPolicyNo = this.onChangeAPLPolicyNo.bind(this);
        this.handlerOnChangeAPLPOName = this.onChangeAPLPOName.bind(this);
        this.handlerOnChangeAPLAmount = this.onChangeAPLAmount.bind(this);
        this.handlerRemoveAPLPolInfo = this.removeAPLPolInfo.bind(this);
        this.handlerOnChangePremiumRefundAmount = this.onChangePremiumRefundAmount.bind(this);
        //FundIL
        this.handlerUpdateFundList = this.updateFundList.bind(this);
        this.handlerOnChangeFundLValue = this.onChangeFundLValue.bind(this);
        //MaxLoan
        this.handlerUpdateMaxLoanAmount = this.updateMaxLoanAmount.bind(this);
        //MaxDividend
        this.handlerUpdateMaxDividendAmount = this.updateMaxDividendAmount.bind(this);
        //MaxCoupon
        this.handlerUpdateMaxCouponAmount = this.updateMaxCouponAmount.bind(this);
        //MaxPartialWithdrawal
        this.handlerUpdateMaxPartialWithdrawalAmount = this.updateMaxPartialWithdrawalAmount.bind(this);
    }

    toggleCheckedPaymentMethod(value) {
        let checkedList = this.state.checkedPaymentMethod;
        if (checkedList.includes(value)) {
            // Remove the element
            checkedList.splice(checkedList.indexOf(value), 1);
        } else {
            if((this.props.proccessType === 'Partial Withdrawal') && this.state.checkedPaymentMethod[0] && (this.state.checkedPaymentMethod[0] === 'P3')) {
                //Add the element at index 0
                checkedList.unshift(value);
            } else {
                // Add the element
                checkedList.push(value);
            }
        }
        if (this.props.proccessType !== 'Partial Withdrawal') {
            if (this.state.checkedPaymentMethod[0]) {
                let paymentMethod = this.state.paymentMethod;
                if ((this.state.checkedPaymentMethod[0] === 'P3') && !paymentMethod.transfer.Amount) {
                    if (this.props.proccessType !== 'Partial Withdrawal') {
                        paymentMethod.transfer.Amount = this.state.amount;
                    }
                } else if ((this.state.checkedPaymentMethod[0] === 'P6') && !paymentMethod.payInsurance.polInfoList[0].Amount) {
                    paymentMethod.payInsurance.polInfoList[0].Amount = this.state.amount;
                } else if ((this.state.checkedPaymentMethod[0] === 'P7') && !paymentMethod.refundLoan.polInfoList[0].Amount) {
                    paymentMethod.refundLoan.polInfoList[0].Amount = this.state.amount;
                } else if ((this.state.checkedPaymentMethod[0] === 'P8') && !paymentMethod.APL.polInfoList[0].Amount) {
                    paymentMethod.APL.polInfoList[0].Amount = this.state.amount;
                } else if ((this.state.checkedPaymentMethod[0] === 'P9') && !paymentMethod.premiumRefund.Amount) {
                    paymentMethod.premiumRefund.Amount = this.state.amount;
                }
                this.setState({checkedPaymentMethod: checkedList, paymentMethod: paymentMethod});
            } else {
                this.setState({checkedPaymentMethod: checkedList});
            }
        } else {
            this.setState({checkedPaymentMethod: checkedList});
        }
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        
    }
    //Partial Withdrawal IL
    updateFundList(list) {
        this.setState({FundList: list});
    }

    onChangeFundLValue(index, value) {
        let fundList = this.state.FundList;
        let fund = fundList[index];
        if (fund) {
            fund.FundValue = value;
			fundList[index] = fund;
            this.setState({FundList: fundList});
            this.validateInputFundIL();
        }
    }

    //Dong phi bao hiem
    addPayInsurancePolInfo() {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.payInsurance?.polInfoList;
        if (polInfoList) {
            let polInfo = {
                PolicyNo: '',
                POName: '',
                Amount: ''
            }
            polInfoList.push(polInfo);
            paymentMethod.payInsurance.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    removePayInsurancePolInfo(index) {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.payInsurance?.polInfoList;
        if (polInfoList && polInfoList[index]) {
            polInfoList.splice(index, 1);
            paymentMethod.payInsurance.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangePayInsurancePolicyNo(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let warning = '';
        let polInfo = paymentMethod.payInsurance?.polInfoList[index];
        if (polInfo) {
            if (!isOnlyNormalText(value)) {
                warning = 'Không được bao gồm khoảng trống hoặc ký tự đặc biệt';
            } 
            polInfo.PolicyNo = value;
            polInfo.warningMessage = warning;
            paymentMethod.payInsurance.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangePayInsurancePOName(index, value) {
        // if (!onlyLettersAndSpaces(value)) {
        //     return;
        // }
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.payInsurance?.polInfoList[index];
        if (polInfo) {
            polInfo.POName = value;
            paymentMethod.payInsurance.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangePayInsuranceAmount(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.payInsurance?.polInfoList[index];
        if (polInfo) {
            polInfo.Amount = value;
            paymentMethod.payInsurance.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    //Tra tam ung tu gia tri hoan lai
    addRefundLoanPolInfo() {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.refundLoan?.polInfoList;
        if (polInfoList) {
            let polInfo = {
                PolicyNo: '',
                POName: '',
                Amount: ''
            }
            polInfoList.push(polInfo);
            paymentMethod.refundLoan.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    removeRefundLoanPolInfo(index) {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.refundLoan?.polInfoList;
        if (polInfoList && polInfoList[index]) {
            polInfoList.splice(index, 1);
            paymentMethod.refundLoan.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeRefundLoanPolicyNo(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.refundLoan?.polInfoList[index];
        if (polInfo) {
            polInfo.PolicyNo = value;
            paymentMethod.refundLoan.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeRefundLoanPOName(index, value) {
        // if (!onlyLettersAndSpaces(value)) {
        //     return;
        // }
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.refundLoan?.polInfoList[index];
        if (polInfo) {
            polInfo.POName = value;
            paymentMethod.refundLoan.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeRefundLoanAmount(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.refundLoan?.polInfoList[index];
        if (polInfo) {
            polInfo.Amount = value;
            paymentMethod.refundLoan.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    //Tra tam ung phi tu dong
    addAPLPolInfo() {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.APL?.polInfoList;
        if (polInfoList) {
            let polInfo = {
                PolicyNo: '',
                POName: '',
                Amount: ''
            }
            polInfoList.push(polInfo);
            paymentMethod.APL.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    removeAPLPolInfo(index) {
        let paymentMethod = this.state.paymentMethod;
        let polInfoList = paymentMethod.APL?.polInfoList;
        if (polInfoList && polInfoList[index]) {
            polInfoList.splice(index, 1);
            paymentMethod.APL.polInfoList = polInfoList;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeAPLPolicyNo(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.APL?.polInfoList[index];
        if (polInfo) {
            polInfo.PolicyNo = value;
            paymentMethod.APL.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeAPLPOName(index, value) {
        // if (!onlyLettersAndSpaces(value)) {
        //     return;
        // }
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.APL?.polInfoList[index];
        if (polInfo) {
            polInfo.POName = value;
            paymentMethod.APL.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeAPLAmount(index, value) {
        let paymentMethod = this.state.paymentMethod;
        let polInfo = paymentMethod.APL?.polInfoList[index];
        if (polInfo) {
            polInfo.Amount = value;
            paymentMethod.APL.polInfoList[index] = polInfo;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }
    
    //Chuyen khoan
    onChangeTransferAccountName = (value) => {
        // if (!onlyLettersAndSpaces(value)) {
        //     return;
        // }
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.transfer.AccountName = value;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    }

    onChangeAccountNumber = (value) => {
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.transfer.AccountNumber = value;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    }

    onChangeBank = (value) => {
        let paymentMethod = this.state.paymentMethod;
        var bankObj = this.state.bankList.find((bank) => bank.CityCode === value);
        if (bankObj !== null) {
            paymentMethod.transfer.BankCode = value;
            paymentMethod.transfer.BankName = bankObj.CityName;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }
    
    onChangeCity = (value) => {
        let paymentMethod = this.state.paymentMethod;
        var cityObj = this.state.zipCodeList.find((city) => city.CityCode === value);
        if (cityObj !== null) {
            paymentMethod.transfer.CityCode = value;
            paymentMethod.transfer.Province = cityObj.CityName;
            paymentMethod.disabledButton = !this.validateInputPaymentMethod();
            let isValidInput = !paymentMethod.disabledButton;
            this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
        }
    }

    onChangeBranchName = (value) => {
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.transfer.BranchName = value;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    }

    onChangeBankDealingRoom = (value) => {
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.transfer.BankDealingRoom = value;
        // paymentMethod.disabledButton = !validateInputPaymentMethod();
        this.setState({paymentMethod: paymentMethod});
    }

    onChangeBankOfficeAmount = (value) => {
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.transfer.Amount = value;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    }

    //Hoàn trả chủ thẻ chủ tài khoản thanh toán

    onChangePremiumRefundAmount(value) {
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.premiumRefund.Amount = value;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});

    }
    
    validateInputPaymentMethod = (amount) => {
        if (!amount) {
            amount = this.state.amount;
        }
        let sum = 0;
        let valid = true;
        if (isEmpty(this.state.checkedPaymentMethod)) {
            this.setState({sum: sum});
            valid = false;
            this.setState({sum: sum, errorMessage: ''});
            return valid;
        }
        let isInvalid = true;
        let existOne = false;
        //validate Chuyen khoan
        if (this.state.checkedPaymentMethod.includes('P3')) {
            isInvalid = 
            Object.entries(this.state.paymentMethod?.transfer)
                .filter(([k, v]) => (
                (k !== 'BankDealingRoom') && (k !== 'BranchCode') && (k !== 'Amount') && (v === null || v === undefined || !v)
                )).length > 0;
            if (this.state.paymentMethod?.transfer.Amount) {
                sum = sum + parseInt(this.state.paymentMethod?.transfer?.Amount);
            }
            if ((this.props.proccessType !== 'Surrender') && (this.props.proccessType !== 'Partial Withdrawal') && !this.state.paymentMethod?.transfer.Amount ) {
                valid = false;
            }
            if (isInvalid || (this.state.paymentMethod?.transfer.Amount && (parseInt(this.state.paymentMethod?.transfer.Amount) === 0))) {
                valid = false;
            }
            existOne = true;
        }
        //Validate dong phi bao hiem
        if (this.state.checkedPaymentMethod.includes('P6')) {
            for (const polInfo of this.state.paymentMethod.payInsurance?.polInfoList) {
                isInvalid = 
                Object.entries(polInfo)
                .filter(([k, v]) => (
                    (k !== 'warningMessage') && (v === null || v === undefined || !v)
                )).length > 0;
                if (polInfo.Amount) {
                    sum = sum + parseInt(polInfo.Amount);
                }
                if (isInvalid || (polInfo.Amount && (parseInt(polInfo.Amount) === 0)) || !isOnlyNormalText(polInfo?.PolicyNo)) {
                    valid = false;
                }
               
                existOne = true;
            }
        }
        //Hoan tra gia tri tam ung
        if (this.state.checkedPaymentMethod.includes('P7')) {
            for (const polInfo of this.state.paymentMethod.refundLoan?.polInfoList) {
                isInvalid = 
                Object.entries(polInfo)
                .filter(([k, v]) => (
                (v === null || v === undefined || !v)
                )).length > 0;
                if (polInfo.Amount) {
                    sum = sum + parseInt(polInfo.Amount);
                }
                if (isInvalid || (polInfo.Amount && (parseInt(polInfo.Amount) === 0))) {
                    valid = false;
                }
                existOne = true;
            }
        }
        //Hoan tra tra phi tu dong
        if (this.state.checkedPaymentMethod.includes('P8')) {
            for (const polInfo of this.state.paymentMethod.APL?.polInfoList) {
                isInvalid = 
                Object.entries(polInfo)
                .filter(([k, v]) => (
                    (v === null || v === undefined || !v)
                )).length > 0;
                if (polInfo.Amount) {
                    sum = sum + parseInt(polInfo.Amount);
                }
                if (isInvalid || (polInfo.Amount && (parseInt(polInfo.Amount) === 0))) {
                    valid = false;
                }
                existOne = true;
            }
        }
        //Nhận phí bảo hiểm đóng dư (Hoàn trả chủ thẻ tài khoản)
        if (this.state.checkedPaymentMethod.includes('P9')) {
            isInvalid = !this.state.paymentMethod?.premiumRefund?.Amount;
            if (this.state.paymentMethod?.premiumRefund?.Amount) {
                sum = sum + parseInt(this.state.paymentMethod?.premiumRefund?.Amount);
            }
            if ((this.props.proccessType !== 'Surrender') && (this.props.proccessType !== 'Partial Withdrawal') && !this.state.paymentMethod?.premiumRefund.Amount ) {
                valid = false;
            }
            if (isInvalid || (this.state.paymentMethod?.premiumRefund?.Amount && (parseInt(this.state.paymentMethod?.premiumRefund?.Amount) === 0))) {
                valid = false;
            }
            existOne = true;
        }
        

        let result = this.validateAmount(sum, amount);
        if (!valid) {
            return valid;
        }

        if (!this.state.amount && existOne) {
            this.setState({sum: sum});
            if (this.props.proccessType === 'Premium Refund') {
                if (amount <=0) {
                    return false;
                }
            }
            return true;//Case Surrender,... no have amount
        }


        return result;
        
    }

    validateAmount = (sum, amount) => {
        let valid = false;

        if (sum < amount) {
            if ((this.state.proccessType !== 'Surrender') && (this.state.proccessType !== 'Partial Withdrawal')) {
                this.setState({sum: sum, errorMessage: 'Chưa đủ số tiền yêu cầu'});
                valid = false;
            } else {
                this.setState({sum: sum, errorMessage: ''});
                valid = true;
            }
            
        } else if (sum > amount) {
            if ((this.state.proccessType !== 'Surrender')) {
                this.setState({sum: sum, errorMessage: 'Vượt quá số tiền yêu cầu'});
                valid = false;
            } else {
                this.setState({sum: sum, errorMessage: ''});
                valid = true;
            }
            
        } else {
            if ((this.state.proccessType === 'Partial Withdrawal') && this.state.checkedPaymentMethod.includes('P3')) {

                this.setState({sum: sum, errorMessage: 'Vượt quá số tiền yêu cầu'});
                valid = false;
            } else {

                this.setState({sum: sum, errorMessage: ''});
                valid = true;
            }

        }
        return valid;
    }
    updatePaymentMethod = (pmMethod) => {
        this.setState({paymentMethod: pmMethod});
    }

    componentDidMount() {
        from = this.props.from;//For use at open link app
        // console.log('from=', this.props.from);
        // if ((this.props.from === 'Android') ) {
        //     this.checkPosition();
        //     this.intervalId = setInterval(this.checkPosition, 2000);
        // }
        this.fetchZipCodeMaster();
        this.fetchBankMaster();
        cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            from
        );
    }

    componentDidUpdate() {
        if (this.props.proccessType != this.state.proccessType) {
            this.props.setSubStepName(PAYMENT_SUB_STATE.INIT);
            this.setState({proccessType: this.props.proccessType})
        }
    }

    componentWillUnmount() {
        // if ((this.props.from === 'Android') && this.intervalId) {
        //     clearInterval(this.intervalId);
        // }
    }

    checkPosition = () => {
        if (this?.inputRef?.current) {
            const rect = this?.inputRef?.current.getBoundingClientRect();
            this.setState({ position: { top: rect.top, left: rect.left } });
            if (rect.top >= 430) {
                console.log('khong hide............');
                this.props.handlerHideForKeyBoardAndroid(false);
            } else {
                this.props.handlerHideForKeyBoardAndroid(true);
                console.log('hide............ rect.left=', rect.top);
            } 
        } else {
            console.error("inputRef.current is null or undefined");
        }
    }

    fetchZipCodeMaster() {
        // Get ZipCode master data
        const zipCodeRequest = {
            jsonDataInput: {
                Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
            }
        };
        getZipCodeMasterData(zipCodeRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ zipCodeList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });
    }

    fetchBankMaster() {
        // Get bank master data
        const bankRequest = {
            jsonDataInput: {
                Project: 'mcp', Action: 'Bank',
            }
        };
        getBankMasterData(bankRequest)
            .then(Res => {
                const Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile !== null) {
                    this.setState({ bankList: Response.ClientProfile });
                }
            })
            .catch(error => {
                this.props.history.push('/maintainence');
            });
    }

    generateConsentResults(data) {
        const result = {};
        data.forEach((item, index) => {
            const role = item.Role;
            let key;
            if (role === 'PO') {
                key = 'ConsentResultPO';
            } else {
                // key = `ConsentResultLI_${index + 1}`;
                key = 'ConsentResultLI';
            }
            if (item.ConsentRuleID === 'ND13') {
                result[key] = item.ConsentResult;
            }
            
        });
        return result;
    }

    startTimer = () => {
        let myInterval = setInterval(() => {
            if (this.state.seconds > 0) {
                this.setState({seconds: this.state.seconds - 1});
            }
            if (this.state.seconds === 0) {
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

    popupOtpSubmit = (OTP) => {
        this.submitProccessConfirm(OTP);
    }

    submitProccessConfirm = (OTP) => {
        let submitRequest = {
            jsonDataInput: {
              Company: COMPANY_KEY,
              Note: this.props.proccessType + 'ProcessConfirm',
              Action: CONFIRM_ACTION_MAPPING[this.props.proccessType]?CONFIRM_ACTION_MAPPING[this.props.proccessType]: 'SinglePSProcessConfirm',
              RequestTypeID: this.props.proccessType,
              APIToken: this.props.apiToken,
              Authentication: AUTHENTICATION,
              DeviceId: this.props.deviceId,
              OS: WEB_BROWSER_VERSION,
              Project: "mcp",
              TransactionID: this.state.trackingId,
              UserLogin: this.props.clientId,
              ClientID: this.props.clientId,
              PolicyNo: this.props.polID,
              OtpVerified: OTP,
              TransactionVerified: this.state.transactionId
            }
          }
          onlineRequestSubmitConfirm(submitRequest)
          .then(res => {
              if ((res.Response.Result === 'true') && (res.Response.ErrLog === ('Confirm ' + this.props.proccessType + ' is saved successfull.')) && res.Response.Message) {
                  // upload images
                  console.log('submitProccessConfirm success', this.state.trackingId);
                  deleteND13DataTemp(this.props.clientId, this.state.trackingId, this.props.apiToken, this.props.deviceId);
                //   document.getElementById("popupSucceededRedirectND13").className = "popup special envelop-confirm-popup show";
               
                // if (this.props.appType === 'CLOSE') {
                //     this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});
                // } else {
                //     let obj = {
                //         Action: "END_ND13_" + this.props.proccessType,
                //         ClientID: this.props.clientId,
                //         PolicyNo: this.props.polID,
                //         TrackingID: this.state.trackingId
                //     };
                //     if (from && (from === "Android")) {//for Android
                //         if (window.AndroidAppCallback) {
                //             window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                //         }
                //     } else if (from && (from === "IOS")) {//for IOS
                //         if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                //             window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                //         }
                //     }
                // }
                // this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});
                this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});

            } else if (res.Response.ErrLog === 'OTP Exceed') {
                this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
                document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
                document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                this.setState({errorMessage: OTP_INCORRECT});
            } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                this.setState({errorMessage: OTP_EXPIRED});
            } else {
                this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }

          }).catch(error => {
        //   alert(error);
      });

    }

    closePopupSucceededRedirect(event) {
        this.setState({showSuccess: false});

        if (this.props.appType === 'CLOSE') {
            window.location.href = '/payment-contract';
        } else {
            let obj = {
                Action: "END_ND13_" + this.props.proccessType,
                ClientID: this.props.clientId,
                PolicyNo: this.props.polID,
                TrackingID: this.state.trackingId
            };
            from = this.props.from;
            if (from && (from === "Android")) {//for Android
                if (window.AndroidAppCallback) {
                    window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                }
            } else if (from && (from === "IOS")) {//for IOS
                if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                    window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                }
            }
        }
        
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0, errorMessage: ''});
    }
    
    closeThanks = () => {
        this.setState({showThanks: false});
    }

    inputAmount = (value) =>{
        let validateObj = this.validateInput(value);
        this.setState({amount: value, isValidStep1Input: validateObj.isValidStep1Input, errorMessageStep1: validateObj.errorMessage});

        let paymentMethod = this.state.paymentMethod;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod();
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    }

    updateMaxLoanAmount = (value) => {
        this.setState({maxLoanAmount: value});
    }

    updateMaxDividendAmount = (value) => {
        this.setState({maxDividendAmount: value});
    }

    updateMaxCouponAmount = (value) => {
        this.setState({maxCouponAmount: value});
    }

    updateMaxPartialWithdrawalAmount = (value) => {
        this.setState({maxPartialWithdrawalAmount: value});
    }

    inputReason = (value) => {
        let errMsg = '';
        let isValid = true;
        if (!value) {
            errMsg = 'Vui lòng nhập thông tin';
            isValid = false;
        }
        if (this.props.proccessType === 'Premium Refund') {
            if ((parseInt(this.state.amount) > 0) && value) {
                errMsg = '';
                isValid = true;
            } else {
                errMsg = 'Vui lòng nhập thông tin';
                isValid = false;
            }
        }
        this.setState({reason: value, isValidStep1Input: isValid, errorMessageStep1: errMsg});
    }
    

    validateInput = (value) => {
        if (this.props.proccessType === 'Maturity') {
            if (parseInt(value) > 0) {
                return {isValidStep1Input: true, errorMessage: ''};
            } else {
                return {isValidStep1Input: false};
            }
        } else if (this.props.proccessType === 'Premium Refund') {
            if ((parseInt(value) > 0) && this.state.reason) {
                return {isValidStep1Input: true, errorMessage: ''};
            } else {
                return {isValidStep1Input: false, errorMessage:'Vui lòng nhập thông tin'};
            }
        }
        let min = -1;
        let max = -1;
        if (this.props.proccessType === 'Loan') {
            min = this.state.minLoanAmount;
            max = this.state.maxLoanAmount;
        } else if (this.props.proccessType === 'Dividend') {
            min = this.state.minDividendAmount;
            max = this.state.maxDividendAmount;
        } else if (this.props.proccessType === 'Coupon') {
            min = this.state.minCouponAmount;
            max = this.state.maxCouponAmount;
        } else if ((this.props.proccessType === 'Partial Withdrawal') && (this.props?.selectedItem?.PolicyClassCD !== 'IL')) {
            min = this.state.minPartialWithdrawalAmount;
            max = this.state.maxPartialWithdrawalAmount;
        } 

        let minVND = min?.toLocaleString('en-US').replace(/,/g, '.') + ' VNĐ'; 
        if (value && (value < min)) {
            return {isValidStep1Input: false, errorMessage: 'Số tiền tối thiểu phải từ ' + minVND};
        }
        if (max > 0) {
            if (value && (value > 0) && (value <= max)) {
                return {isValidStep1Input: true, errorMessage: ''};
            } else if (value && (value > 0)){
                return {isValidStep1Input: false, errorMessage: 'Vượt quá số tiền tối đa'};
            } else {
                return {isValidStep1Input: false, errorMessage: 'Vui lòng nhập số tiền'};
            }

        } else {
            if (value && (value > 0)) {
                return {isValidStep1Input: true, errorMessage: ''};
            } else {
                return {isValidStep1Input: false, errorMessage: 'Vui lòng nhập số tiền'};
            }
        }
    } 

    validateInputFundIL = () => {
        let invalid = false;
        let FundList = this.state.FundList;
        // let haveValue = 0;
        let min = this.state.minPartialWithdrawalILAmount;
        let sum = 0;
        for (let i= 0; i < FundList?.length; i++) {
            if (FundList[i]?.FundValue && (FundList[i]?.FundValue >= 0)) {
                if (parseInt(FundList[i]?.FundValue) <= parseInt(FundList[i]?.OldFundValue.replaceAll('.', ''))) {
                    FundList[i].errorMessage = '';
                    // haveValue = FundList[i]?.FundValue;
                    if (FundList[i]?.FundValue < min) {
                        let minVND = min?.toLocaleString('en-US').replace(/,/g, '.') + ' VNĐ'; 
                        FundList[i].errorMessage = 'Số tiền tối thiểu phải từ ' + minVND;
                        invalid = true;
                    }
                } else {
                    FundList[i].errorMessage = 'Vượt quá số tiền tối đa.';
                    invalid = true;
                }
                sum = sum + parseInt(FundList[i]?.FundValue);
            } else {
                FundList[i].errorMessage = '';
            } 
        }
        let errMsg = '';
        let sumFundIL = 0;
        for (let i = 0; i < this.props?.ClientProfile.length; i++) {
            if (this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1] && (this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1] !== '-')) {
                sumFundIL = sumFundIL + parseInt(this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1].replaceAll('.', ''));
            }
        }
        if ((sum === 0) && !invalid) {
            errMsg = '';
            invalid = true;
        }
        else if (sum < this.state.minPartialWithdrawalILSum) {
            let minSumVND = this.state.minPartialWithdrawalILSum?.toLocaleString('en-US').replace(/,/g, '.') + ' VNĐ';
            errMsg = 'Số tiền tối thiểu phải từ ' + minSumVND;
            invalid = true;
        } else if (sum > sumFundIL) {
            // errMsg = 'Vượt quá số tiền yêu cầu';
        }
        this.setState({FundList: FundList, isValidStep1Input: !invalid, errorMessageIL: errMsg, amount: sum});
        let paymentMethod = this.state.paymentMethod;
        paymentMethod.disabledButton = !this.validateInputPaymentMethod(sum);
        let isValidInput = !paymentMethod.disabledButton;
        this.setState({paymentMethod: paymentMethod, isValidInput: isValidInput});
    } 

    render() {
        const { top, left } = this.state.position;
        let sumFundIL = 0;
        if ((this.props.stepName === FUND_STATE.UPDATE_INFO) && this.props?.ClientProfile && (this.props.proccessType === 'Partial Withdrawal') && (this.props?.selectedItem?.PolicyClassCD === 'IL')) {
            {this.state.showDetailFundIL && this.props?.ClientProfile.map((item, index) => (
                <div>
                    <div className="card__footer-item" style={{padding: '8px 8px 0px 8px', borderBottom: '0', backgroundColor: '#F2DECA'}}>
                        <p className='simple-brown'>{item?.FundNameCode?.split(';')[1]}</p>
                        <p className='simple-brown'>{item?.fund_val_each_funf?.split(';')[1]}</p>
                    </div>
                </div>
            ))}
            for (let i = 0; i < this.props?.ClientProfile.length; i++) {
                if (this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1] && (this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1] !== '-')) {
                    sumFundIL = sumFundIL + parseInt(this.props?.ClientProfile[i]?.fund_val_each_funf?.split(';')[1].replaceAll('.', ''));
                }
            }
        }
        const setSubStepName = (paymentSubStep) => {
            // this.setState({isValidInput: false});
            this.props.setSubStepName(paymentSubStep);
        }
        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }
        const closeNotice = () => {
            this.setState({ showNotice: false });
        }

        const changePaymentSubmit = () => {
            if (!this.state.isValidInput) {
                return;
            }
            if (this.state.isSubmitting) {
                return;
            }
            console.log('Payment sumbit proccessType=', this.props.proccessType);
            this.setState({isSubmitting: true});
            let paymentList = [];
            if (this.state.checkedPaymentMethod?.includes('P3')) {
                console.log('P3 transfer=', this.state.paymentMethod.transfer);
                let copyTransfer = JSON.parse(JSON.stringify(this.state.paymentMethod.transfer));
                // copyTransfer.Amount = copyTransfer?.Amount?.replaceAll('.', ',');
                copyTransfer.Amount = copyTransfer?.Amount? parseInt(copyTransfer?.Amount)?.toLocaleString('en-US').replace(/,/g, ','): 0;
                paymentList.push(copyTransfer);
            }
            if (this.state.checkedPaymentMethod?.includes('P6')) {
                let payInsurance = JSON.parse(JSON.stringify(this.state.paymentMethod.payInsurance));
                console.log('payInsurance=', payInsurance);
                let copyPolInfoList = [...payInsurance?.polInfoList];
                 
                for (let i = 0; i < copyPolInfoList.length; i++) {
                    copyPolInfoList[i].Amount = copyPolInfoList[i].Amount? parseInt(copyPolInfoList[i].Amount)?.toLocaleString('en-US').replace(/,/g, ','): 0;
                }
                let paymentItem = {
                    PaymentMethodID: payInsurance.PaymentMethodID,
                    PaymentMethodName: payInsurance.PaymentMethodName,
                    PolicyList: [...copyPolInfoList]
                }
                paymentList = [...paymentList, paymentItem];
            }

            if (this.state.checkedPaymentMethod?.includes('P7')) {
                let refundLoan = JSON.parse(JSON.stringify(this.state.paymentMethod.refundLoan));
                console.log('refundLoan=', refundLoan);
                let copyPolInfoList = [...refundLoan?.polInfoList];
                for (let i = 0; i < copyPolInfoList.length; i++) {
                    // copyPolInfoList[i].Amount = copyPolInfoList[i].Amount?.replaceAll('.', ',');
                    copyPolInfoList[i].Amount = copyPolInfoList[i].Amount? parseInt(copyPolInfoList[i].Amount)?.toLocaleString('en-US').replace(/,/g, ','): 0;
                }
                let paymentItem = {
                    PaymentMethodID: refundLoan.PaymentMethodID,
                    PaymentMethodName: refundLoan.PaymentMethodName,
                    PolicyList: [...copyPolInfoList]
                }
                paymentList = [...paymentList, paymentItem];
            }

            if (this.state.checkedPaymentMethod?.includes('P8')) {
                let APL = JSON.parse(JSON.stringify(this.state.paymentMethod.APL));
                console.log('APL=', APL);
                let copyPolInfoList = [...APL?.polInfoList];
                for (let i = 0; i < copyPolInfoList.length; i++) {
                    copyPolInfoList[i].Amount = copyPolInfoList[i].Amount? parseInt(copyPolInfoList[i].Amount)?.toLocaleString('en-US').replace(/,/g, ','): 0;
                }
                let paymentItem = {
                    PaymentMethodID: APL.PaymentMethodID,
                    PaymentMethodName: APL.PaymentMethodName,
                    PolicyList: [...copyPolInfoList]
                }
                paymentList = [...paymentList, paymentItem];
            }

            if (this.state.checkedPaymentMethod?.includes('P9')) {
                let premiumRefund = JSON.parse(JSON.stringify(this.state.paymentMethod.premiumRefund));
                console.log('premiumRefund=', premiumRefund);
				premiumRefund.Amount = premiumRefund.Amount? parseInt(premiumRefund.Amount)?.toLocaleString('en-US').replace(/,/g, ','): 0;
  				paymentList.push(premiumRefund);
            }
            let copyFundList = [];
            if (!isEmpty(this.state.FundList)) {
                copyFundList = JSON.parse(JSON.stringify(this.state.FundList));
                for (let i= 0; i < copyFundList?.length; i++) {
                    if (copyFundList[i].FundValue) {
                        copyFundList[i].FundValue = copyFundList[i].FundValue? parseInt(copyFundList[i].FundValue)?.toLocaleString('en-US').replace(/,/g, ','): 0;
                    }
                }
            }

            console.log('paymentList=', paymentList);


            let submitRequest = {
                jsonDataInput: {
                    OtpVerified: this.state.OTP,
                    APIToken: this.props.apiToken,
                    Action: "SubmitPSPayment",
                    Authentication: AUTHENTICATION,
                    ClientClass: this.props.clientClass,
                    ClientID: this.props.clientId,
                    ConsentList: [
                        {
                            ConsentRuleID: this.props.proccessType + '_Agreement',
                            ConsentResult: 'Y'
                        }
                    ],
                    ClientName: formatFullName(this.props.clientName),
                    Company: COMPANY_KEY,
                    ContactEmail: this.props.email,
                    DeviceId: this.props.deviceId,
                    FromSystem: this.props.from ? "DCA" : "DCW",
                    PaymentAmount:this.state.amount?parseInt(this.state.amount)?.toLocaleString('en-US').replace(/,/g, ','): 0,
                    PaymentReason:this.state.reason?this.state.reason: '',
                    NewValue: paymentList,
                    OldValue: null,
                    FundList: copyFundList,
                    OS: WEB_BROWSER_VERSION,
                    PolicyNo: this.props.polID,
                    Project: "mcp",
                    RequestTypeID: this.props.proccessType,
                    UserLogin: this.props.clientId
                }
            }
             console.log(submitRequest);
             CPServicesPSPaymentProcess(submitRequest)
                .then(res => {
                    if (res.Response.Result === 'true' && res.Response.ErrLog === 'Submit OS is saved successfull.') {
                        this.setState({trackingId: res.Response.Message});
                        fetchCPConsentConfirmation(res.Response.Message);
                    } else {
                        this.setState({isSubmitting: false});
                    }
                }).catch(error => {
                    this.setState({isSubmitting: false});
                });
            
            cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
            trackingEvent(
                "Giao dịch hợp đồng",
                `${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`,
                `${from?from:'Web'}_${this.props.proccessType}${PageScreen.SUBMITCLICK}`,
                from
            );
         
        }

        const startTimer = () => {
            let myInterval = setInterval(() => {
                if (this.state.seconds > 0) {
                    this.setState({seconds: this.state.seconds - 1});
                }
                if (this.state.seconds === 0) {
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

        const genOtpV2 = () => {
            //gen otp, email/phone get at backend
            const genOTPRequest = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'GenOTPV2',
                    APIToken: this.props.apiToken,
                    Authentication: AUTHENTICATION,
                    ClientID: this.props.clientId,
                    DeviceId: this.props.deviceId,
                    Note: this.props.proccessType + 'ProcessConfirm',
                    OS: WEB_BROWSER_VERSION,
                    Project: 'mcp',
                    UserLogin: this.props.clientId,
                    DCID : this.props.DCID,
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
                    this.setState({isSubmitting: false});
                }).catch(error => {
                    this.setState({isSubmitting: false});
                    document.getElementById("popup-exception").className = "popup special point-error-popup show";
                });
    
        }

        const closeOtp = () => {
            this.setState({showOtp: false, minutes: 0, seconds: 0});
        }

        const fetchCPConsentConfirmation = (TrackingID) => {
            console.log('fetchCPConsentConfirmation', TrackingID);
            let request = {
                jsonDataInput: {
                    Action: "CheckCustomerConsent",
                    APIToken: this.props.apiToken,
                    Authentication: AUTHENTICATION,
                    ClientID: this.props.clientId,
                    Company: COMPANY_KEY,
                    ClientList: this.props.clientId,
                    ProcessType: this.props.proccessType,
                    DeviceId: this.props.deviceId,
                    OS: WEB_BROWSER_VERSION,
                    Project: "mcp",
                    UserLogin: this.props.clientId,
                    TrackingID: TrackingID,
                }
            };
            console.log('fetchCPConsentConfirmation request', request)
   
            CPConsentConfirmation(request)
                .then(res => {
                    console.log('res=', res);
                    const Response = res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                        // const { DOB } = this.state.clientProfile;
                        const clientProfile = Response.ClientProfile;
                        const configClientProfile = Response.Config;
                        const consentResultPO = this.generateConsentResults(clientProfile)?.ConsentResultPO;
    
                        const trackingID = TrackingID;
                        if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                            let state = this.state;
                            state.clientProfile = clientProfile;
                            state.configClientProfile = configClientProfile;
                            state.trackingId = trackingID;
                            state.clientListStr = this.props.clientId;

                            // state.appType = 'CLOSE';
                            state.appType = this.props.appType;
                            // state.proccessType = 'CSA';
                            state.stepName = FUND_STATE.SDK
                            this.setState(state);
                            this.props.setStepName(FUND_STATE.SDK);
                            this.setState({isSubmitting: false});
                        } else {
                            genOtpV2();
                        }
                    } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                        this.setState({
                            openPopupWarningDecree13: false,
                        });
                        genOtpV2();
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.setState({isSubmitting: false});
                });
        }

        const dropdownFundDetails=()=> {
            this.setState({showDetailFundIL: !this.state.showDetailFundIL})
        }

        return (
            <>
            {this.state.stepName !== FUND_STATE.SDK &&
                <section className={getSession(IS_MOBILE)?'section-container-mobile':'section-container'} >
                    <div className="stepform" style={{marginTop: '60px'}}>
                        {(((this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName === PAYMENT_SUB_STATE.INIT)) || (this.props.stepName === FUND_STATE.VERIFICATION)) &&
                        <div className="contractform__head2">
                            <h3 className="contractform__head-title">THÔNG TIN HỢP ĐỒNG</h3>
                            <div className="contractform__head-content">
                                <div className="item">
                                    <p className="item-label">Hợp đồng</p>
                                    <p className="item-content basic-big">{this.props.polID} </p>
                                </div>
                                <div className="contractform__head-content">
                                    <div className="item">
                                        <p className="item-label">Dành cho</p>
                                        <p className="item-content basic-big">{this.props.selectedItem?.PolicyLIName} </p>
                                    </div>
                                    {(this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName === PAYMENT_SUB_STATE.INIT) &&
                                    <>
                                        {this.props.proccessType === 'Loan' &&
                                        <div className="item">
                                            <p className="item-label">Số tiền có thể tạm ứng (tạm tính)</p>
                                            <p className="item-content basic-big">{(this.props.selectedItem?.MaxLoanAmt?parseInt(this.props.selectedItem?.MaxLoanAmt): 0)?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                        </div>
                                        }
                                        {this.props.proccessType === 'Dividend' &&
                                        <div className="item">
                                            <p className="item-label">Lãi chia tích lũy (tạm tính)</p>
                                            <p className="item-content basic-big">{(this.props.selectedItem?.Dividend?parseInt(this.props.selectedItem?.Dividend?.replaceAll('.', '')): 0)?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                        </div>
                                        }
                                        {this.props.proccessType === 'Coupon' &&
                                        <div className="item">
                                            <p className="item-label">Quyền lợi tiền mặt (tạm tính)</p>
                                            <p className="item-content basic-big">{(this.props.selectedItem?.Coupon?parseInt(this.props.selectedItem?.Coupon?.replaceAll('.', '')): 0)?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                        </div>
                                        }
                                        {(this.props.proccessType === 'Partial Withdrawal') && (this.props?.selectedItem?.PolicyClassCD !== 'IL') &&
                                        <div className="item">
                                            <p className="item-label">Giá trị tài khoản đầu ngày</p>
                                            <p className="item-content basic-big">{((this.props.selectedItem?.PolAccountValue && (this.props.selectedItem?.PolAccountValue !== '-'))?parseInt(this.props.selectedItem?.PolAccountValue?.replaceAll('.', ''))?.toLocaleString('en-US').replace(/,/g, '.'): this.props.selectedItem?.PolAccountValue)} VNĐ</p>
                                        </div>
                                        }
                                    </>
                                    }
                                    {((this.props.stepName === FUND_STATE.UPDATE_INFO) && this.props?.ClientProfile && (this.props.proccessType === 'Partial Withdrawal') && (this.props?.selectedItem?.PolicyClassCD === 'IL')) &&
                                    <div className={this.state.showDetailFundIL?"dropdown show":"dropdown"} style={{padding: '0px'}}>
                                    <div className="dropdown__content item"
                                            onClick={() => dropdownFundDetails()}>
                                        <p className="card-dropdown-title item-label" style={{
                                            padding: '10px 0px',
                                            color: '#985801'
                                        }}>Giá trị quỹ
                                        </p>
                                        <p className="item-content" style={{
                                            padding: '10px 0px',
                                            color: '#985801'
                                        }}>{sumFundIL.toLocaleString('en-US').replace(/,/g, '.')} VNĐ
                                        <span style={{display: 'inline-flex', verticalAlign: 'middle', paddingLeft: '2px', marginBottom: '2px'}}>{(this.state.showDetailFundIL === true) ?
                                            <img src={iconArrowDownBrown} alt="arrow-down-icon"/> :
                                            <img src={iconArrowLeftBrown} alt="arrow-left-icon"/>
                                            }
                                        </span>
                                        </p>
                                    </div>
                                    {this.state.showDetailFundIL && 
                                    <div style={{padding: '8px 8px 0px 8px', borderBottom: '0', borderRadius: '6px', backgroundColor: '#F2DECA'}}>
                                    {this.props?.ClientProfile.map((item, index) => (
                                        <>
                                            {item?.fund_val_each_funf?.split(';')[1] && (item?.fund_val_each_funf?.split(';')[1] !== '-') &&
                                            <div className="card__footer-item">
                                                <p className='simple-brown'>{item?.FundNameCode?.split(';')[1]}</p>
                                                <p className='simple-brown'>{item?.fund_val_each_funf?.split(';')[1]} VNĐ</p>
                                            </div>
                                            }
                                        </>
                                    ))}
                                    </div>
                                    }
                                </div>
                                    }
                                </div>

                            </div>
                        </div>
                        }
                        {/* <div className="contractform__body"> */}

                        {(this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName === PAYMENT_SUB_STATE.INIT) &&
                            <>
                            {!((this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL')) &&
                            <PaymentType 
                            ClientProfile={this.props.ClientProfile}
                            selectedItem={this.props.selectedItem}
                            isConfirm={this.props.isConfirm}
                            amount={this.state.amount}
                            reason={this.state.reason}
                            isValidInput={this.state.isValidInput}
                            proccessType={this.props.proccessType}
                            errorMessage={this.state.errorMessageStep1}
                            handlerInputAmount={this.handlerInputAmount}
                            handlerInputReason={this.handlerInputReason}
                            handlerUpdateMaxLoanAmount={this.handlerUpdateMaxLoanAmount}
                            handlerUpdateMaxDividendAmount={this.handlerUpdateMaxDividendAmount}
                            handlerUpdateMaxCouponAmount={this.handlerUpdateMaxCouponAmount}
                            handlerUpdateMaxPartialWithdrawalAmount={this.handlerUpdateMaxPartialWithdrawalAmount}
                            />
                            }
                            {(sumFundIL > 0) && this.props.ClientProfile && (this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL') &&
                                <PaymentTypePartialWithdrawIL 
                                ClientProfile={this.props.ClientProfile}
                                selectedItem={this.props.selectedItem}
                                isConfirm={this.props.isConfirm}
                                isValidInput={this.state.isValidInput}
                                proccessType={this.props.proccessType}
                                errorMessage={this.state.errorMessageIL}
                                FundList={this.state.FundList}
                                amount={this.state.amount}
                                handlerUpdateFundList={this.handlerUpdateFundList}
                                handlerOnChangeFundLValue={this.handlerOnChangeFundLValue}
                                />
                            }
                            </>
                        }
                        {(this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName === PAYMENT_SUB_STATE.CHOOSE_METHOD) &&
                            <PaymentMethod 
                            ClientProfile={this.props.ClientProfile}
                            selectedItem={this.props.selectedItem}
                            isConfirm={this.props.isConfirm}
                            amount={this.state.amount}
                            isValidInput={this.state.isValidInput}
                            proccessType={this.props.proccessType}
                            errorMessage={this.state.errorMessage}
                            checkedPaymentMethod={this.state.checkedPaymentMethod}
                            paymentMethod={this.state.paymentMethod}
                            attachmentState={this.state.attachmentState}
                            zipCodeList={this.state.zipCodeList}
                            bankList={this.state.bankList}
                            clientName={this.props.clientName}
                            warningMessage={this.state.warningMessage}
                            sum={this.state.sum}
                            top={top}
                            handlerInputAmount={this.handlerInputAmount}
                            handlerToggleCheckedPaymentMethod={this.handlerToggleCheckedPaymentMethod}
                            handlerOnChangeTransferAccountName={this.handlerOnChangeTransferAccountName}
                            handlerOnChangeAccountNumber={this.handlerOnChangeAccountNumber}
                            handlerOnChangeBank={this.handlerOnChangeBank}
                            handlerOnChangeCity={this.handlerOnChangeCity}
                            handlerOnChangeBranchName={this.handlerOnChangeBranchName}
                            handlerOnChangeBankDealingRoom={this.handlerOnChangeBankDealingRoom}
                            handlerOnChangeBankOfficeAmount={this.handlerOnChangeBankOfficeAmount}
                            handlerValidateInputPaymentMethod={this.handlerValidateInputPaymentMethod}
                            handlerUpdatePaymentMethod={this.handlerUpdatePaymentMethod}
                            //Dong phi bao hiem
                            handlerAddPayInsurancePolInfo = {this.handlerAddPayInsurancePolInfo}
                            handlerRemovePayInsurancePolInfo = {this.handlerRemovePayInsurancePolInfo}
                            handlerOnChangePayInsurancePolicyNo = {this.handlerOnChangePayInsurancePolicyNo}
                            handlerOnChangePayInsurancePOName = {this.handlerOnChangePayInsurancePOName}
                            handlerOnChangePayInsuranceAmount = {this.handlerOnChangePayInsuranceAmount}
                            //Tra tam ung tu gia tri hoan lai
                            handlerAddRefundLoanPolInfo={this.handlerAddRefundLoanPolInfo}
                            handlerRemoveRefundLoanPolInfo={this.handlerRemoveRefundLoanPolInfo}
                            handlerOnChangeRefundLoanPolicyNo={this.handlerOnChangeRefundLoanPolicyNo}
                            handlerOnChangeRefundLoanPOName={this.handlerOnChangeRefundLoanPOName}
                            handlerOnChangeRefundLoanAmount={this.handlerOnChangeRefundLoanAmount}
                            //Tra tam ung phi tu dong
                            handlerAddAPLPolInfo = {this.handlerAddAPLPolInfo}
                            handlerRemoveAPLPolInfo = {this.handlerRemoveAPLPolInfo}
                            handlerOnChangeAPLPolicyNo = {this.handlerOnChangeAPLPolicyNo}
                            handlerOnChangeAPLPOName = {this.handlerOnChangeAPLPOName}
                            handlerOnChangeAPLAmount = {this.handlerOnChangeAPLAmount}
                            //Nhận phí bảo hiểm đóng dư
                            handlerOnChangePremiumRefundAmount = {this.handlerOnChangePremiumRefundAmount}
                            />
                        }
                        {(this.props.stepName === FUND_STATE.VERIFICATION) && 
                            <>
                                <PaymentTypeReview 
                                ClientProfile={this.props.ClientProfile}
                                selectedItem={this.props.selectedItem}
                                isConfirm={this.props.isConfirm}
                                amount={this.state.amount}
                                reason={this.state.reason}
                                isValidInput={this.state.isValidInput}
                                proccessType={this.props.proccessType}
                                errorMessage={this.state.errorMessage}
                                FundList={this.state.FundList}
                                handlerInputAmount={this.handlerInputAmount}
                                />
                                <PaymentMethodReview 
                                ClientProfile={this.props.ClientProfile}
                                selectedItem={this.props.selectedItem}
                                isConfirm={this.props.isConfirm}
                                amount={this.state.amount}
                                isValidInput={this.state.isValidInput}
                                proccessType={this.props.proccessType}
                                errorMessage={this.state.errorMessage}
                                checkedPaymentMethod={this.state.checkedPaymentMethod}
                                paymentMethod={this.state.paymentMethod}
                                />

                            </>

                        }
                        {/* {this.props.ProfileGroupBy && (this.props.stepName === FUND_STATE.VERIFICATION) && 
                            <ProductCategoryGroupByLIReview 
                            ClientProfileProducts={this.props.ProfileGroupBy}
                            tickDecreaseSAMap={this.state.tickDecreaseSAMap}
                            tickCancelRiderMap={this.state.tickCancelRiderMap}
                            faceAmountMap={this.state.faceAmountMap}
                            tickBenefitMap={this.state.tickBenefitMap}
                            />
                        } */}

                        {/* </div> */}

                        <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt=""/>
                        <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                       </div>
                    {(this.props.stepName === FUND_STATE.UPDATE_INFO) && !this.props.IsDegrading &&(
                        (this.props.subStepName > PAYMENT_SUB_STATE.INIT)?(
                            <>

                            </>
                        ):(
                            <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                <div className="bottom-text"
                                        style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                    <p style={{textAlign: 'justify'}}>
                                        {(this.props.proccessType !== 'Maturity') && (this.props.proccessType !== 'Premium Refund') && (this.props.proccessType !== 'Dividend') && (this.props.proccessType !== 'Coupon') &&
                                        <span className="red-text basic-bold">Lưu ý: </span>
                                        }
                                        {this.props.proccessType === 'Surrender'? (
                                            <ul className="list-information" style={{color: '#727272'}}>
                                                - Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài dịch vụ khách hàng Dai-ichi Life Việt Nam để được tư vấn thêm trước khi quyết định chấm dứt Hợp đồng bảo hiểm trước hạn.
                                                <br/>
                                                - (Những) Người được bảo hiểm sẽ không được Dai-ichi Life Việt Nam bảo hiểm kể từ khi Quý khách nộp yêu cầu chấm dứt Hợp đồng bảo hiểm trước hạn.
                                            </ul>
                                        ):(
                                            (this.props.proccessType === 'Partial Withdrawal')?(
                                                (this.props?.selectedItem?.PolicyClassCD === 'IL')? (
                                                    <ul className="list-information" style={{color: '#727272'}}>
                                                        - Số tiền thực nhận sẽ thay đổi nếu có phát sinh phí rút một phần giá trị quỹ.
                                                        <br/>
                                                        - Giá trị quỹ đang hiển thị là giá trị tạm tính dựa trên giá đơn vị quỹ gần nhất và giá trị này có thể thay đổi tại ngày định giá tiếp theo.
                                                        <br/>
                                                        - Giá trị quỹ còn lại sau khi rút không được thấp hơn số dư tối thiểu theo quy định của Dai-ichi Life Việt Nam tại từng thời điểm. Trong trường hợp số tiền yêu cầu rút không thỏa, chúng tôi sẽ thông báo đến Quý khách.
                                                    </ul>
                                                ):(
                                                    <ul className="list-information" style={{color: '#727272'}}>
                                                        - Số tiền thực nhận sẽ thay đổi nếu có phát sinh phí rút một phần giá trị tài khoản.
                                                        <br/>
                                                        - Giá trị tài khoản còn lại sau khi rút không được thấp hơn số dư tối thiểu theo quy định của Dai-ichi Life Việt Nam tại từng thời điểm. Trong trường hợp số tiền yêu cầu rút không thỏa, chúng tôi sẽ thông báo đến Quý khách.
                                                        <br/>
                                                        - Với quyền lợi bảo hiểm cơ bản, số tiền bảo hiểm sẽ được điều chỉnh giảm tương ứng nếu giá trị tài khoản Hợp đồng sau khi rút nhỏ hơn số tiền bảo hiểm.
                                                    </ul>
                                                )
                                            ):(
                                                (this.props.proccessType !== 'Maturity') && (this.props.proccessType !== 'Premium Refund') && (this.props.proccessType !== 'Dividend') && (this.props.proccessType !== 'Coupon') &&
                                                <ul className="list-information" style={{color: '#727272'}}>
                                                    - Số tiền có thể tạm ứng đang hiển thị là giá trị tạm tính và có thể thay đổi vào ngày chúng tôi thực hiện yêu cầu của Quý khách. Trường hợp số tiền yêu cầu tạm ứng không thỏa, chúng tôi sẽ thông báo đến Quý khách.
                                                    <br/>
                                                    - Yêu cầu sẽ phát sinh Khoản giảm thu nhập đầu tư/Phí tạm ứng.
                                                </ul>
                                            )

                                        )}

                                    </p>
                                </div>
                            </div>
                        )
                    )

                    }
                    {this.props.stepName === FUND_STATE.VERIFICATION && (
                        <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <div className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                    style={{
                                        'maxWidth': '594px',
                                        backgroundColor: '#f5f3f2',
                                        paddingLeft: '6px'
                                    }}>
                                <div
                                    className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                    style={{flex: '0 0 auto', height: '20px', cursor: 'pointer'}}
                                    onClick={() => acceptPolicy()}>
                                    <div className="checkmark">
                                        <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                    </div>
                                </div>
                                <div className="policy-info-tac" style={{
                                    textAlign: 'justify',
                                    paddingLeft: '12px'
                                }}>
                                    <p style={{textAlign: 'left', fontWeight: "bold"}}>Tôi đồng ý và xác nhận:</p>
                                    <ul className="list-information">
                                        <li className="sub-list-li">
                                            - Tất cả thông tin trên đây là đầy đủ, đúng sự thật và hiểu rằng yêu cầu này chỉ có hiệu lực kể từ ngày được Dai-ichi Life Việt Nam (DLVN) chấp nhận.
                                        </li>
                                        <li className="sub-list-li">
                                            - Yêu cầu này chỉ được thực hiện khi thỏa các giá trị, điều kiện và quy định của Hợp đồng bảo hiểm.
                                        </li>
                                        {/* <li className="sub-list-li">
                                            - Thông tin điều chỉnh/thay đổi CCCD (nếu có) sẽ được cập nhật cho (các) Hợp đồng bảo hiểm của Bên mua bảo hiểm.
                                        </li> */}
                                        <li className="sub-list-li">
                                            - Với xác nhận hoàn tất giao dịch, đồng ý với
                                            {getSession(IS_MOBILE)?(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href='#'
                                                onClick={()=>callbackAppOpenLink(PAGE_POLICY_PAYMENT, from)}
                                                >{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            ):(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href={PAGE_POLICY_PAYMENT}
                                                target='_blank'>{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            )}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    )

                    }
                    {getSession(IS_MOBILE)&& (this.props.stepName === FUND_STATE.UPDATE_INFO) && (this.props.subStepName === PAYMENT_SUB_STATE.CHOOSE_METHOD) &&
                        <div className='nd13-padding-bottom160'></div>
                    }
                    <div className={(top > 250)?"bottom-btn nd13-padding-bottom36": "bottom-btn"} style={{display: 'block'}} ref={this.inputRef}>
                        { this.props.stepName === FUND_STATE.UPDATE_INFO ? (
                                <>
                                {getSession(IS_MOBILE) && (this.props.subStepName === PAYMENT_SUB_STATE.INIT) && (sumFundIL > 0) && this.props.ClientProfile && (this.props.proccessType === 'Partial Withdrawal') && (this.props.selectedItem?.PolicyClassCD === 'IL') &&
                                    <>
                                        {this.state.errorMessageIL &&
                                            <p className='payment-main-err' style={{position: 'revert-layer'}}>
                                            <span style={{ color: 'red', lineHeight: '24px', marginTop: '0', marginBottom: '16px', marginTop: '16px', verticalAlign: 'top', textAlign: 'center' }}>
                                                {this.state.errorMessageIL}
                                            </span>
                                            </p>
                                        }
                                        {this.state.errorMessageIL ? (
                                            <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', border: '1px solid red', marginBottom: '12px', marginLeft: '0', marginRight: '0' }}>
                                                <div className="item">
                                                    <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '15px' }}>Số tiền</p>
                                                    <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '15px' }}>{this.state.amount && (this.state.amount > 0)?this.state.amount?.toLocaleString('en-US').replace(/,/g, '.'): '-'} VNĐ</p>
                                                </div>
                                            </div>
                                        ) : (
                                            <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', marginLeft: '0', marginRight: '0' }}>
                                                <div className="item">
                                                    <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '15px' }}>Số tiền</p>
                                                    <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '15px' }}>{this.state.amount && (this.state.amount > 0)?this.state.amount?.toLocaleString('en-US').replace(/,/g, '.'): '-'} VNĐ</p>
                                                </div>
                                            </div>
                                        )}
                                    </>
                                }
                                
                                {this.props.subStepName === PAYMENT_SUB_STATE.INIT?(
                                    this.state.isValidStep1Input?(
                                        <button className="btn btn-primary" onClick={() => setSubStepName(PAYMENT_SUB_STATE.CHOOSE_METHOD)} style={{zIndex: '197'}}>Tiếp tục</button>
                                    ):(
                                        <button className="btn disabled" disabled>Tiếp tục</button>
                                    )
                                    
                                ):(
                                    <>
                                        {getSession(IS_MOBILE) && (this.props.subStepName === PAYMENT_SUB_STATE.CHOOSE_METHOD) && (this.props.proccessType !== 'Surrender') && (this.props.proccessType !== 'Partial Withdrawal') ?(
                                        <>
                                            <p className='payment-main-err' style={{position: 'revert-layer'}}>
                                            <span style={{ color: 'red', lineHeight: '24px', marginTop: '0', marginBottom: '16px', marginTop: '16px', verticalAlign: 'top', textAlign: 'center' }}>
                                                {this.state.errorMessage}
                                            </span>
                                            </p>
                                            {this.state.errorMessage ? (
                                                <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', border: '1px solid red', marginBottom: '12px', marginLeft: '0', marginRight: '0' }}>
                                                    <div className="item">
                                                        <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '15px' }}>Tổng số tiền</p>
                                                        <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '15px' }}>{this.state.sum?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                    </div>
                                                </div>
                                            ) : (
                                                <div className="contractform__head-content payment-sum" style={{ borderRadius: '6px', backgroundColor: '#F2DECA', marginTop: '12px', marginLeft: '0', marginRight: '0' }}>
                                                    <div className="item">
                                                        <p className="item-label simple-brown" style={{ lineHeight: '24px', fontSize: '15px' }}>Tổng số tiền</p>
                                                        <p className="item-content basic-red basic-semibold" style={{ lineHeight: '24px', fontSize: '15px' }}>{this.state.sum?.toLocaleString('en-US').replace(/,/g, '.')} VNĐ</p>
                                                    </div>
                                                </div>
                                            )}
                                        </>
                                        ):(
                                            getSession(IS_MOBILE) && this.state.errorMessage && (this.props.subStepName === PAYMENT_SUB_STATE.CHOOSE_METHOD) &&
                                            (
                                                <>
                                                <p className='payment-main-err' style={{position: 'revert-layer', bottom: '70px'}}>
                                                <span style={{ color: 'red', lineHeight: '17px', marginTop: '0', marginBottom: '16px', marginTop: '16px', verticalAlign: 'top', textAlign: 'center' }}>
                                                    {this.state.errorMessage}
                                                </span>
                                                </p>
                                                <div className="contractform__head-content" style={{ marginTop: '12px', marginBottom: '12px', marginLeft: '0', marginRight: '0' }}>
                                                    <div className="item">
                                                        <p style={{ lineHeight: '17px', fontSize: '15px' }}></p>
                                                        <p style={{ lineHeight: '17px', fontSize: '15px' }}></p>
                                                    </div>
                                                </div>
                                                </>
                                            )
                                            
                                        )
                                        }
                                        {this.state.isValidInput ? (
                                            this.props.subStepName === PAYMENT_SUB_STATE.CHOOSE_METHOD&&(
                                                <button className="btn btn-primary" onClick={() => this.props.setStepName(FUND_STATE.VERIFICATION)} style={{zIndex: '197'}}>Tiếp tục</button>
                                                
                                            )
                                        ): (
                                            <button className="btn disabled" disabled>Tiếp tục</button>
                                        )}
                                    </>

                                    )
                                }
                                </>

                            
                        ): (
                            this.state.acceptPolicy && !this.state.isSubmitting ? (
                                <button className="btn btn-primary" onClick={() => changePaymentSubmit()} style={{zIndex: '189'}}>Xác nhận</button>
                            ): (
                                <button className="btn disabled" disabled>Xác nhận</button>
                            )

                        )
                        }
                        
                    </div>
                </section>
            }
            {/*START ND13*/}
                {this.state.appType && this.state.trackingId && (this.state.stepName === FUND_STATE.SDK) && <ND13
                appType={this.state.appType}
                trackingId={this.state.trackingId}
                clientListStr={this.props.clientId}
                clientId={this.props.clientId}
                proccessType={this.state.proccessType}
                deviceId={this.props.deviceId}
                apiToken={this.props.apiToken}
                PolicyNo={this.props.polID}
                phone={this.props.phone}
                clientName={this.props.clientName?this.props.clientName: getSession(FULL_NAME)}
            />}

            {/*END ND13*/}
            {this.state.showOtp &&
                <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                            closeOtp={this.closeOtp} errorMessage={this.state.errorMessage}
                            popupOtpSubmit={this.popupOtpSubmit} reGenOtp={genOtpV2}
                            maskPhone={maskPhone(this.props.phone)}
                            />
            } 
            {this.state.openPopupWarningDecree13 && <POWarningND13 proccessType={this.state.proccessType} onClickExtendBtn={() => this.setState({
                openPopupWarningDecree13: false
            })}/>}
            {/* Popup succeeded redirect */}
            {this.state.showSuccess &&
                <div className="popup special envelop-confirm-popup show" id="popupSucceeded">
                    <div className="popup__card">
                        <div className="envelop-confirm-card">
                            <div className="envelopcard">
                                <div className="envelop-content">
                                    <div className="envelop-content__header"
                                        
                                    >
                                        <i className="closebtn" onClick={()=>this.handlerClosePopupSucceededRedirect()}><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></i>
                                    </div>
                                    <div className="envelop-content__body">
                                        <div>
                                            <h4 className="popup-claim-submission-h4" style={{textAlign: 'center', marginBottom: '8px'}}>Gửi yêu cầu thành công</h4>
                                            <p>Cảm ơn Quý khách đã 
                                            đồng hành cùng Dai-ichi Life Việt Nam. Chúng tôi sẽ xử lý yêu cầu và thông báo kết quả trong thời gian sớm nhất</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="envelopcard_bg">
                                <img src={FE_BASE_URL + "/img/envelop_nowhite.png"} alt=""/>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>
            }
            {this.state.showNotice &&
            <PayModeNoticePopup closePopup={closeNotice} title='Phí dự tính định kỳ' msg={parse('Phí dự tính định kỳ bao gồm Phí định kỳ/ <br/>cơ bản định kỳ (tạm tính) và Phí đóng <br/> thêm định kỳ (nếu có).')} imgPath={FE_BASE_URL + '/img/popup/fee-time.svg'} />
            }
        </>
        )
    }
}

export default ChangePaymentDetail;
