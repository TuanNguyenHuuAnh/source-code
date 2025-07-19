import React, {Component, createRef } from 'react';
import NumberFormat from 'react-number-format';
import {maskPhone, deleteND13DataTemp, getUrlParameter, getSession, formatFullName, getOSAndBrowserInfo, callbackAppOpenLink, isInteger, isFloat, trackingEvent, cpSaveLogSDK } from '../sdkCommon';
import {onlineRequestSubmitESubmission, genOTP, CPConsentConfirmation, onlineRequestSubmitConfirm} from '../sdkAPI';
import LoadingIndicator from '../../common/LoadingIndicator2';
import POWarningND13 from "../ModuleND13/ND13Modal/POWarningND13/POWarningND13";
import ND13 from "../ND13";
import DOTPInput from '../../components/DOTPInput';
import PayModeNoticePopup from '../../components/PayModeNoticePopup';
import parse from 'html-react-parser';
import SuccessChangePaymodePopup from '../../components/SuccessChangePaymodePopup';
import {
    FUND_STATE,
    COMPANY_KEY,
    AUTHENTICATION,
    FE_BASE_URL,
    ConsentStatus,
    CONFIRM_ACTION_MAPPING,
    NOTE_MAPPING,
    OTP_INCORRECT,
    OTP_EXPIRED,
    PAGE_POLICY_PAYMENT,
    SCREENS,
    IS_MOBILE,
    CLIENT_ID,
    FULL_NAME,
    DCID,
    PageScreen
  } from '../sdkConstant';
import ChangeValue from './ChangeValue';
import ChangeValueReview from './ChangeValueReview';

let from = '';
class SundryAmountDetail extends Component {
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
            errMessage: '',
            OTP: '',
            trackingId: '',
            openPopupWarningDecree13: false,
            clientProfile: null,
            configClientProfile: null,
            clientListStr: '',
            appType: '',
            proccessType: '',
            stepName: this.props.stepName,
            showNotice: false,
            acceptPolicy: false,
            position: { top: 260, left: 260 },
            isSubmitting: false,
            amount: '',
            isValidInput: false
        };
        // this.callBackUpdateND13State  = this.callBackUpdateND13State.bind(this);
        this.handlerClosePopupSucceededRedirect = this.closePopupSucceededRedirect.bind(this);
        this.handlerSetWrapperSucceededRef = this.setWrapperSucceededRef.bind(this);
        this.handlerInputAmount = this.inputAmount.bind(this);
    }

    componentDidMount() {
        from = getUrlParameter("fromApp");
        if ((this.props.from === 'Android') ) {
            this.checkPosition();
            this.intervalId = setInterval(this.checkPosition, 2000);
        }
        cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.KEYINDATA}`,
            from
        );
    }

    componentWillUnmount() {
        if ((this.props.from === 'Android') && this.intervalId) {
            clearInterval(this.intervalId);
        }
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

    // inputAmountChangedHandler = (values) => {
    //     console.log(this.state.newFrequency);

    //     if (!values.value || values.value === '') {
    //         values.value = 0;
    //     }
    //     console.log(values.value);
    //     let polPremAmtMax = this.state.polMPremAmtMax.replaceAll('.', '');
    //     let PolMPremAmt = this.state.polMPremAmt.replaceAll('.', '');
    //     console.log(parseInt(polPremAmtMax));
    //     console.log(parseInt(PolMPremAmt));
    //     console.log(parseInt(values.value) + parseInt(PolMPremAmt));
    //     let amt = parseInt(values.value) + parseInt(PolMPremAmt);
        
    //     console.log(amt > parseInt(polPremAmtMax));
    //     if ((amt > parseInt(polPremAmtMax)) && (this.props.PolicyClassCD !== 'PENSION')) {
    //         // alert('Tổng phí tạm tính và phí đóng thêm không được vượt quá ' + polPremAmtMax + ' VNĐ')
    //         this.setState({exceedMax: true, newPolMPremAmt: amt, polMPremAmtAddtional: values.value, errMessage: 'Tối đa bằng 5 lần Phí định kỳ/cơ bản định kỳ (tạm tính)'});
    //     } else {
    //         this.setState({exceedMax: false, newPolMPremAmt: amt, polMPremAmtAddtional: values.value, errMessage: ''});
    //     }
    // }

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
              Note: NOTE_MAPPING[this.props.proccessType],
              Action: CONFIRM_ACTION_MAPPING[this.props.proccessType]?CONFIRM_ACTION_MAPPING[this.props.proccessType]: 'SinglePSProcessConfirm',
              RequestTypeID: this.props.proccessType,
              APIToken: this.props.apiToken,
              Authentication: AUTHENTICATION,
              DeviceId: this.props.deviceId,
              OS: getOSAndBrowserInfo(),
              Project: "mcp",
              TransactionID: this.state.trackingId,
              UserLogin: this.props.clientId,
              ClientID: this.props.clientId,
              PolicyNo: this.props.polID,
              OtpVerified: OTP,
              TransactionVerified: this.state.transactionId
            }
          }
          console.log(submitRequest);
          onlineRequestSubmitConfirm(submitRequest)
          .then(res => {
            console.log(res.Response);
              console.log('Confirm ' + this.props.proccessType + ' is saved successfull.');
              if ((res.Response.Result === 'true') && (res.Response.ErrLog === ('Confirm ' + this.props.proccessType + ' is saved successfull.')) && res.Response.Message) {
                  // upload images
                  console.log('submitProccessConfirm success', this.state.trackingId);
                  deleteND13DataTemp(this.props.clientId, this.state.trackingId, this.props.apiToken, this.props.deviceId);
                //   document.getElementById("popupSucceededRedirectND13").className = "popup special envelop-confirm-popup show";
                if (this.props.appType === 'CLOSE') {
                    this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});
                } else {
                    let obj = {
                        Action: "END_ND13_" + this.props.proccessType,
                        ClientID: this.props.clientId,
                        PolicyNo: this.props.polID,
                        TrackingID: this.state.trackingId
                    };
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
                // this.setState({showOtp: false, minutes: 0, seconds: 0, showSuccess: true});

            } else if (res.Response.ErrLog === 'OTP Exceed') {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPLOCK' || res.Response.ErrLog === 'OTP Wrong 3 times') {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
            } else if (res.Response.ErrLog === 'OTPINCORRECT') {
                this.setState({errMessage: OTP_INCORRECT});
            } else if (res.Response.ErrLog === 'OTPEXPIRY') {
                this.setState({errMessage: OTP_EXPIRED});
            } else {
                this.setState({showOtp: false, minutes: 0, seconds: 0});
                document.getElementById("popup-exception").className = "popup special point-error-popup show";
            }

          }).catch(error => {
            console.log('error=', error);
        //   alert(error);
      });

    }

    closePopupSucceededRedirect(event) {
        this.setState({showSuccess: false});
        window.location.href = '/update-policy-info';
    }

    setWrapperSucceededRef(node) {
        this.wrapperSucceededRef = node;
    }

    closeOtp = () => {
        this.setState({showOtp: false, minutes: 0, seconds: 0, errMessage: ''});
    }
    
    closeThanks = () => {
        this.setState({showThanks: false});
    }

    inputAmount = (value) =>{
        let validateObj = this.validateInput(value);
        this.setState({amount: value, isValidInput: validateObj.isValidInput, errMessage: validateObj.errorMessage});
    }

    validateInput = (value) => {
        let polMPremAmt =  this.props.PolMPremAmt.replaceAll('.', '');
        polMPremAmt = isInteger(polMPremAmt)?parseInt(polMPremAmt): 0;
        let polSndryAmt =  this.props.PolSndryAmt?this.props.PolSndryAmt.replaceAll('.', ''): '0';//
        polSndryAmt = isInteger(polSndryAmt)?parseInt(polSndryAmt): 0;
        
        let polSndryAmtMax =  this.props.PolSndryAmtMax?this.props.PolSndryAmtMax.split('.')[0]: '0';
        polSndryAmtMax = isInteger(polSndryAmtMax)?parseInt(polSndryAmtMax): 0;
        if (value && (parseInt(value) === polSndryAmt)) {
            return {isValidInput: false, errorMessage: 'Vui lòng điều chỉnh phí dự tính định kỳ mới'};
        } else if (value && (parseInt(value) > 0) && (parseInt(value) >= polMPremAmt) && (parseInt(value) <= polSndryAmtMax) ) {
            return {isValidInput: true, errorMessage: ''};
        } else if (value && (parseInt(value) < polMPremAmt)) {
            return {isValidInput: false, errorMessage: 'Tối thiểu bằng Phí định kỳ/cơ bản định kỳ'};
        } else if (value && (parseInt(value) > polSndryAmtMax) && (this.props.PolicyClassCD !== 'PENSION')) {
            return {isValidInput: false, errorMessage: 'Phí dự tính định kỳ tối đa là ' + polSndryAmtMax?.toLocaleString('en-US').replace(/,/g, '.') + ' VNĐ'};
        } else if (value) {
            return {isValidInput: true};
        } else {
            return {isValidInput: false};
        }
    } 

    render() {
        const { top, left } = this.state.position;
        // const getInputCoordinates = () => {
        //     var inputElement = document.getElementById('txtInputAmount');
        //     if (inputElement) {
        //         var rect = inputElement.getBoundingClientRect();
        //         if (rect) {
        //             console.log('X: ' + rect.left + ', Y: ' + rect.top);
        //         }
        //     }


            
        // }
        // getInputCoordinates();
        console.log('X: ' + left + ', Y: ' + top);
        const acceptPolicy = () => {
            this.setState({acceptPolicy: !this.state.acceptPolicy});
        }
        const closeNotice = () => {
            this.setState({ showNotice: false });
        }
        const showNotice = () => {
            this.setState({ showNotice: true });
        }
        const radioFrequency = (Frequency, PolMPremAmtMax, PolMPremAmt, FrequencyCode) => {
            
            this.setState({newFrequency: Frequency, polMPremAmtMax: PolMPremAmtMax, polMPremAmt: PolMPremAmt, newFrequencyCode: FrequencyCode, polMPremAmtAddtional: '', newPolMPremAmt: PolMPremAmt});
        }

        const changePayModeSubmit = () => {
            // if (!this.state.newFrequencyCode || this.state.exceedMax) {
            //     return;
            // }
            if (this.state.isSubmitting || !this.state.amount) {
                return;
            }
            this.setState({isSubmitting: true});
            let submitRequest = {
                jsonDataInput:{
                   OtpVerified: this.state.OTP,
                   APIToken: this.props.apiToken,
                   Action: "SubmitChangeSundryAmount",
                   Authentication: AUTHENTICATION,
                   ClientClass: this.props.clientClass,
                   ClientID: this.props.clientId,
                   ClientName: formatFullName(this.props.clientName),
                   Company: COMPANY_KEY,
                   ContactEmail: this.props.email,
                   DeviceId: this.props.deviceId,
                   FromSystem: this.props.from?"DCA":"DCW",
                   NewValue:{
                      NewPremiumMode: this.props.FrequencyCode,
                      NewSundryAmount: parseInt(this.state.amount)?.toLocaleString('en-US').replace(/,/g, ','),
                   },
                   OldValue:null,
                   OS: getOSAndBrowserInfo(),
                   PolicyNo: this.props.polID,
                   Project: "mcp",
                   RequestTypeID: this.props.proccessType,
                   UserLogin: this.props.clientId
                }
             }
             console.log(submitRequest);
             onlineRequestSubmitESubmission(submitRequest)
                .then(res => {
                    if (res.Response.Result === 'true' && res.Response.ErrLog === 'Submit OS is saved successfull.') {
                        this.setState({trackingId: res.Response.Message});
                        this.props.handlerUpdateTrackingId(res.Response.Message);
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
                    OS: getOSAndBrowserInfo(),
                    Project: 'mcp',
                    UserLogin: this.props.clientId,
                    DCID : this.props.DCID?this.props.DCID: getSession(DCID),
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
                    ClientList: this.props.clientId?this.props.clientId: getSession(CLIENT_ID),
                    ProcessType: this.props.proccessType,
                    DeviceId: this.props.deviceId,
                    OS: getOSAndBrowserInfo(),
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
                            state.proccessType = 'SundryAmountS';
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
                    this.setState({isSubmitting: false});
                });
        }

        return (
            <>
            {this.state.stepName !== FUND_STATE.SDK &&
                <section style={{
                    width: '100%',
                    maxWidth: '600px',
                    borderRadius: '8px',
                    height: '100%'
                }} id="paymode-detail">
                    <div className="stepform">
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
                                        <p className="item-content basic-big">{this.props.PolicyLIName} </p>
                                    </div>
                                    {this.props.stepName === FUND_STATE.UPDATE_INFO &&
                                        <>
                                        <div className="item">
                                            <p className="item-label">Định kỳ đóng phí</p>
                                            <p className="item-content">{this.props.Frequency}</p>
                                        </div>
                                        <div className="item">
                                            <p className="item-label">Phí định kỳ/cơ bản định kỳ</p>
                                            <p className="item-content basic-red basic-semibold">{this.props.PolMPremAmt} VNĐ</p>
                                        </div>
                                        {(this.props.PolicyClassCD !== 'TL') && (this.props.PolicyClassCD !== 'VE') && (this.props.PolicyClassCD !== 'MTL') &&
                                            <div className="item">
                                                <p className="item-label">Phí dự tính định kỳ</p>
                                                <p className="item-content basic-red basic-semibold">{this.props.PolSndryAmt} VNĐ</p>
                                            </div>
                                        }
                                        </>
                                    }
                                </div>

                            </div>
                        </div>
                        <img style={{ minWidth: '100%' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                        <div className="contractform__body" style={{padding: '12px 22px'}}>
                            {this.props.stepName === FUND_STATE.UPDATE_INFO? (

                                    <ChangeValue 
                                        ClientProfile={this.props.ClientProfile}
                                        selectedItem={this.props.selectedItem}
                                        isConfirm={this.props.isConfirm}
                                        amount={this.state.amount}
                                        isValidInput={this.state.isValidInput}
                                        proccessType={this.props.proccessType}
                                        errorMessage={this.state.errMessage}
                                        handlerInputAmount={this.handlerInputAmount}
                                        />

                            ):(
                                <ChangeValueReview 
                                ClientProfile={this.props.ClientProfile}
                                selectedItem={this.props.selectedItem}
                                isConfirm={this.props.isConfirm}
                                amount={this.state.amount}
                                isValidInput={this.state.isValidInput}
                                proccessType={this.props.proccessType}
                                />
                            )}


                        </div>

                        <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt=""/>
                        <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                        
                    </div>
                    {/* {(this.props.stepName === FUND_STATE.UPDATE_INFO) && !this.props.IsDegrading &&
                        <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <div className="bottom-text"
                                    style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                <p style={{textAlign: 'justify'}}>
                                    <span className="red-text basic-bold">Lưu ý: </span><span
                                    style={{color: '#727272'}}>
                                    Ứng dụng chưa hỗ trợ yêu cầu thay đổi sang định kỳ đóng phí ngắn hơn. Quý khách vui lòng liên hệ Văn phòng Dai-ichi Life Việt Nam gần nhất để được hỗ trợ.
                                    </span>
                                </p>
                            </div>
                        </div>
                    } */}
                    {this.props.stepName === FUND_STATE.VERIFICATION &&
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
                                    <p style={{textAlign: 'left', fontWeight: "bold"}}>Tôi đồng ý và xác
                                        nhận:</p>
                                    <ul className="list-information">
                                        <li className="sub-list-li">
                                            - Tất cả thông tin trên đây là đầy đủ, đúng sự thật và hiểu rằng yêu cầu này chỉ có hiệu lực kể từ ngày được Dai-ichi Life Việt Nam chấp nhận.
                                        </li>
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
                    }
                    <div className={(left > 250)?"bottom-btn nd13-padding-bottom36": "bottom-btn"} ref={this.inputRef}>
                        { this.props.stepName === FUND_STATE.UPDATE_INFO ? (
                            
                            (this.state.isValidInput)? (
                                <button className="btn btn-primary" onClick={() => this.props.setStepName(FUND_STATE.VERIFICATION)} style={{zIndex: '197'}}>Tiếp tục</button>
                            ): (
                                <button className="btn btn-primary disabled" disabled>Tiếp tục</button>
                            )

                        ): (
                            this.state.acceptPolicy  && !this.state.isSubmitting? (
                                <button className="btn btn-primary" onClick={() => changePayModeSubmit()} style={{zIndex: '189'}}>Xác nhận</button>
                            ): (
                                <button className="btn btn-primary disabled" disabled>Xác nhận</button>
                            )
                            
                        )
                        }
                    </div>    
                    
                </section>
            }
            {/*START ND13*/}
                {/* {this.state.appType && this.state.trackingId && (this.state.stepName === FUND_STATE.SDK) && <ND13
                appType={this.state.appType}
                trackingId={this.state.trackingId}
                clientListStr={this.props.clientId}
                clientId={this.props.clientId}
                proccessType={this.props.proccessType}
                deviceId={this.props.deviceId}
                apiToken={this.props.apiToken}
                policyNo={this.props.polID}
                phone={this.props.phone}
                clientName={this.state.clientName?this.state.clientName: getSession(FULL_NAME)}
            />} */}

            {/*END ND13*/}
            {this.state.showOtp &&
                <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer}
                            closeOtp={this.closeOtp} errorMessage={this.state.errMessage}
                            popupOtpSubmit={this.popupOtpSubmit} reGenOtp={genOtpV2}
                            maskPhone={maskPhone(this.props.phone)}
                            />
            } 
            {this.state.openPopupWarningDecree13 && <POWarningND13 proccessType={this.props.proccessType} onClickExtendBtn={() => this.setState({
                openPopupWarningDecree13: false
            })}/>}
            {/* Popup succeeded redirect */}
            {this.state.showSuccess &&
            <SuccessChangePaymodePopup closePopup={this.handlerClosePopupSucceededRedirect} policyNo={this.props.polID}
            screen={SCREENS.UPDATE_POLICY_INFO} proccessType={this.props.proccessType}/>
            }
            {this.state.showNotice &&
            <PayModeNoticePopup closePopup={closeNotice} title='Phí dự tính định kỳ' msg={parse('Phí dự tính định kỳ bao gồm Phí định kỳ/ <br/>cơ bản định kỳ (tạm tính) và Phí đóng <br/> thêm định kỳ (nếu có).')} imgPath={FE_BASE_URL + '/img/popup/fee-time.svg'} />
            }
        </>
        )
    }
}

export default SundryAmountDetail;
