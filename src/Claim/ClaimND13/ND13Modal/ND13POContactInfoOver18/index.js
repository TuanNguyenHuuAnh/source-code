import React, {useCallback, useEffect, useRef, useState} from 'react';
import arrowDown from '../../../../img/icon/dropdown-arrow.svg';
import {DatePicker} from 'antd';
import dayjs from "dayjs";
import moment from "moment";
import './styles.css';
import AlertPopupND13ConfirmPayment from "../../../../components/AlertPopupND13ConfirmPayment";
// import {CPConsentConfirmation} from "../../../../util/APIUtils";
import {getSession, VALID_EMAIL_REGEX, getDeviceId, setSession, removeSession} from "../../../../util/common";
import {isEmpty} from "lodash";
import {DOB, FULL_NAME, POID,FE_BASE_URL, CLIENT_ID, ACCESS_TOKEN, AUTHENTICATION, COMPANY_KEY, WEB_BROWSER_VERSION, USER_LOGIN, ConsentStatus, ONLY_PAYMENT} from "../../../../constants";// ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, COMPANY_KEY, WEB_BROWSER_VERSION, USER_LOGIN
import LoadingIndicator from '../../../../common/LoadingIndicator2';
import {CLAIM_STATE} from "../../../CreateClaim/CreateClaim";
import {CPConsentConfirmation} from "../../../../util/APIUtils";

const ND13POContactInfoOver18 = (props) => {
    const requiredFields = ['guardianName', 'relation', 'identityCard', 'dateOfBirth', 'cellPhone'];

    const guardianNameRef = useRef(null);
    const cellPhoneRef = useRef(null);
    const emailRef = useRef(null);
    const identityCardRef = useRef(null);
    const {
        onClickConfirmBtn = () => {
        }, onClickCallBack = () => {
        },
        paymentMethodState
    } = props;
    const [missingFields, setMissingFields] = useState([]);
    const [acceptPolicy, setAcceptPolicy] = useState(false);
    const [isPayment, setIsPayment] = useState(false);
    const [confirmPayment, setConfirmPayment] = useState(false);
    const [relateModalData, setRelateModalData] = useState(props.submissionState.relateModalData);
    const [isActive, setIsActive] = useState(true);
    const [haveShowPayment, setHaveShowPayment] = useState(false);
    const [isSubmiting, setIsSubmiting] = useState(false);
    const [isError, setIsError] = useState(false);

    const setRelateModalDataSync = (rlModalData) => {
        setRelateModalData(rlModalData);
        let submissionState = Object.assign({}, props.submissionState);
        submissionState.relateModalData = rlModalData;
        props.handlerUpdateMainState("submissionState", submissionState);
    }
    const validateInput = (event) => {
        const {name, value} = event.target;
        let inValid = false;
        switch (name) {
            case "guardianName":
                setRelateModalData({
                    ...relateModalData, guardianName: {
                        ...relateModalData.guardianName, error: !value ? "Vui lòng không được để trống" : "",
                    }
                });
                if (!value) {
                    guardianNameRef.current.scrollIntoView({
                        behavior: 'smooth', block: 'start',
                    });
                    inValid = true;
                }
                break;
            case "phone":
                setRelateModalData({
                    ...relateModalData, cellPhone: {
                        ...relateModalData.cellPhone,
                        error: !/^0\d{9}$/.test(value) ? "Số điện thoại phải bắt đầu bằng 0 có độ dài 10 chữ số" : "",
                    }
                });
                if (!/^0\d{9}$/.test(value)) {
                    cellPhoneRef.current.scrollIntoView({
                        behavior: 'smooth', block: 'start',
                    });
                    inValid = true;
                }
                break;
            case "email":
                setRelateModalData({
                    ...relateModalData, email: {
                        ...relateModalData.email,
                        error: value.length > 0 && !VALID_EMAIL_REGEX.test(value) ? "Email không hợp lệ" : "",
                    }
                });
                if (value.length > 0 && !VALID_EMAIL_REGEX.test(value)) {
                    emailRef.current.scrollIntoView({
                        behavior: 'smooth', block: 'start',
                    });
                    inValid = true;
                }
                break;
            case "identityCard":
                setRelateModalData({
                    ...relateModalData, identityCard: {
                        ...relateModalData.identityCard,
                        value: value,
                        error: value.length > 14 ? "CMND/CCCD không được quá 14 chữ số" : "",
                    },
                });
                if (value.length > 14) {
                    identityCardRef.current.scrollIntoView({
                        behavior: 'smooth', block: 'start',
                    });
                    inValid = true;
                }
                break;
            default:
                break;
        }
        if (inValid) {
            setIsError(true);
        } else {
            setIsError(false);
        }
    };

    const disabledDate = (current) => {
        return current && (current > dayjs().endOf('day'));
    }

    const toggleCollapsible = () => {
        setIsActive(!isActive);
    };

    const closeConfirmPayment = () => {
        setConfirmPayment(false);
    }
    const mapDataLIReceiverPO = (data) => {
        return {
            ...data,
            AnswerPurpose: (data.ConsentRuleID === 'CLAIM_PAYMENT')?'Y': '',
            RelationShip: data?.RelationShip?data.RelationShip:'LI',
            ReceiverName: relateModalData.guardianName.value ? relateModalData.guardianName.value : getSession(FULL_NAME),
            ReceiverDoB: relateModalData.dateOfBirth.value ? relateModalData.dateOfBirth.value : getSession(DOB),
            ReceiverIDNum: relateModalData.identityCard.value ? relateModalData.identityCard.value : getSession(POID),
            EmailReceiver: relateModalData.email.value ? relateModalData.email.value : "",
            PhoneReceiver: relateModalData.cellPhone.value ? relateModalData.cellPhone.value : "",
            ZaloIDReceiver: "",
            RequesterID: getSession(CLIENT_ID)
        };
    }

    const fetchCPConsentConfirmationRefresh=() => {
        setIsSubmiting(true);
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: generateSessionString(getSession(CLIENT_ID), props.selectedCliInfo),
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: props.TrackingID,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    const consentResultPO = generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = generateConsentResults(clientProfile)?.ConsentResultLI;
                    const isOpenPopupWarning = ( (consentResultPO !== ConsentStatus.AGREED) || (consentResulLI !== ConsentStatus.AGREED));
                    const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
                    const paymentProfile =  !isEmpty(clientProfile)?clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
                    if (!isOpenPopupWarning && (IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                        setHaveShowPayment(true);
                        // props.callBackOnlyPayment(true);
                        setSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID, ONLY_PAYMENT + props.selectedCliInfo.ClientID);
                    } else {
                        setHaveShowPayment(false);
                        // props.callBackOnlyPayment(false);
                        if (getSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID)) {
                            removeSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID);
                        }
                    }
                    setIsSubmiting(false);
                } else {
                    setIsSubmiting(false);
                    console.log("System error!");
                }
            })
            .catch(error => {
                setIsSubmiting(false);
                console.log(error);
            });
    }

    const generateConsentResults = (data) => {
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

    const generateSessionString = (CLIENT_ID, selectedCliInfo) => {
        if (checkPOAndLIEquality(CLIENT_ID, selectedCliInfo)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${selectedCliInfo?.ClientID}`;
        }
    };

    const checkPOAndLIEquality = (PO, LI) => {
        const clientIDLI = LI.ClientID;
        return clientIDLI === PO;
    };

    const handleClickContinue = () => {
        if (isError) {
            return;
        }
        const { clientProfile, totalInvoiceAmount, configClientProfile } = props;
        let liReceiverPO = [];
        const liPayment = !isEmpty(clientProfile)
            ? clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'CLAIM_PAYMENT'))
            : [];
        if (liPayment && !isEmpty(liPayment)) {
            liReceiverPO.push(mapDataLIReceiverPO(liPayment));
        }
        if (!haveShowPayment) {
            const liND13 = !isEmpty(clientProfile)
            ? clientProfile.find(item => (item.Role === 'LI')  && (item.ConsentRuleID === 'ND13'))
            : [];
            if (liND13 && !isEmpty(liND13)) {
                liReceiverPO.push(mapDataLIReceiverPO(liND13));
            }
        }
        // const liND13 = !isEmpty(clientProfile)
        // ? clientProfile.find(item => (item.Role === 'LI')  && (item.ConsentRuleID === 'ND13'))
        // : [];
        // if (liND13 && !isEmpty(liND13)) {
        //     liReceiverPO.push(mapDataLIReceiverPO(liND13));
        // }

        let type = '';
        if (isPayment || haveShowPayment) {
            onClickConfirmBtn(liReceiverPO, type);
        } /*else if (haveShowPayment) {
            type = 'NotND13';
            onClickConfirmBtn(liReceiverPO, type);
        } */else {
            const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
            const paymentProfile =  !isEmpty(clientProfile)?clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
            if ((IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                setIsPayment(true);
                setConfirmPayment(true);
            } else {
                setIsPayment(false);
                onClickConfirmBtn(liReceiverPO, type);
            }
        }
    };

    const handleDataUpdate = useCallback(() => {
        if (!isEmpty(props.clientProfile)) {
            const newData = {
                guardianName: {
                    value: !isEmpty(props.clientProfile) && props.clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'ND13' ))?.CustomerName || '', // Lấy họ và tên
                    error: '',
                }, dateOfBirth: {
                    value: !isEmpty(props.clientProfile) && props.clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'ND13' ))?.CustomerDoB, // Lấy ngày sinh
                    error: '',
                }, cellPhone: {
                    value: relateModalData.cellPhone.value || !isEmpty(props.clientProfile) && props.clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'ND13' ))?.PhoneReceiver || props.selectedCliInfo.CellPhone, // Lấy số điện thoại
                    error: '',
                }, email: {
                    value: relateModalData.email.value || !isEmpty(props.clientProfile) && props.clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'ND13' ))?.EmailReceiver || props.selectedCliInfo.Email, // Lấy email
                    error: '',
                }, identityCard: {
                    value: !isEmpty(props.clientProfile) && props.clientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'ND13' ))?.ReceiverIDNum || '', // Lấy số chứng minh nhân dân
                    error: '',
                },
            };
            setRelateModalData(prevData => ({
                ...prevData, ...newData
            }));
        }
    }, [props.clientProfile, props.contactInfo, setRelateModalData]);

    useEffect(() => {
        // const paymentProfile =  !isEmpty(props.clientProfile)?props.clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
        // let showPayment = !(props.poConfirmingND13 === '1') && !isEmpty(paymentProfile);
        // setHaveShowPayment(showPayment);
        handleDataUpdate();
        fetchCPConsentConfirmationRefresh();
    }, []);

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const backStep=() => {
        if (isPayment) {
            setIsPayment(false);
        } else {
            props.handlerBackToPrevSubmisionState(props.claimSubmissionState)
        }
    }

    return (!isSubmiting?(
    <>
        <section className="sccontract-warpper" id="scrollAnchor">
            <div className="insurance">
                <div className="heading">

                    <div className="breadcrums">
                        <div className="breadcrums__item">
                            <p>Yêu cầu quyền lợi</p>
                            <span>&gt;</span>
                        </div>
                        <div className="breadcrums__item">
                            <p>Tạo mới yêu cầu</p>
                            <span>&gt;</span>
                        </div>
                        {/* <div className="breadcrums__item">
                            <p>Đồng ý xử lý Dữ liệu cá nhân</p>
                            <span>&gt;</span>
                        </div> */}
                    </div>
                    <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                        <p>Chọn thông tin</p>
                        <i><img src="../../img/icon/return_option.svg" alt="" /></i>
                    </div>
                    <div className="heading__tab">
                        <div className="step-container">
                            <div className="step-wrapper">
                                <div className="step-btn-wrapper">
                                    <div className="back-btn"
                                        onClick={() => backStep()}>
                                        <button>
                                            <div className="svg-wrapper">
                                                <svg
                                                    width="11"
                                                    height="15"
                                                    viewBox="0 0 11 15"
                                                    fill="none"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                >
                                                    <path
                                                        d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                                                        fill="#985801"
                                                        stroke="#985801"
                                                        strokeWidth="0.2"
                                                    />
                                                </svg>
                                            </div>
                                            <span className={"simple-brown"}>Quay lại</span>
                                        </button>
                                    </div>
                                </div>
                                <div className="progress-bar">
                                    <div
                                        className={(props.currentState >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                                        <div className="bullet">
                                            <span>1</span>
                                        </div>
                                        <p>Thông tin sự kiện</p>
                                    </div>
                                    <div
                                        className={(props.currentState >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                                        <div className="bullet">
                                            <span>2</span>
                                        </div>
                                        <p>Thanh toán và liên hệ</p>
                                    </div>
                                    <div
                                        className={(props.currentState >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                                        <div className="bullet">
                                            <span>3</span>
                                        </div>
                                        <p>Kèm <br/>chứng từ</p>
                                    </div>
                                    <div
                                        className={(props.currentState >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                                        <div className="bullet">
                                            <span>4</span>
                                        </div>
                                        <p>Hoàn tất yêu cầu</p>
                                    </div>
                                </div>
                                <div className="step-btn-save-quit">
                                    <div>
                                        <button>
                                                    <span className="simple-brown" style={{zIndex: '30'}}
                                                        onClick={props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>     
                </div>
                <div className="stepform" /*style={{marginTop: 170}}*/>
                    <div className="info">
                        <div className="info__body">
                            <div className="optionalform-isActivewrapper">
                                <div className="optionalform">
                                    <div className="relate-modal-content">
                                        {(isPayment || haveShowPayment) ?
                                            <p className="modal-body-content">Quyền lợi Bảo hiểm Hỗ trợ viện phí/Chăm sóc sức khỏe, nếu được chấp thuận, sẽ được chi trả cho NĐBH từ 18 tuổi trở lên.
                                                </p> :
                                            <p className="modal-body-content">Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày
                                                17/04/2023 về
                                                bảo vệ DLCN, Chủ thể dữ liệu cần xác nhận đồng ý cho phép DLVN xử lý
                                                DLCN.</p>}
                                        <div className="card-border"></div>
                                        {/*----------------------------------------LI > 18T: PO khai báo thông tin liên hệ của LI------------------------------------------------------*/}
                                        {haveShowPayment &&
                                            <p className="modal-body-sub-content">Quý khách sẽ được thông báo đến Người được bảo hiểm, Quý khách vui lòng cung cấp thông tin liên hệ của NĐBH để nhận thông tin.</p> 
                                        }
                                        {isPayment &&
                                            <p className="modal-body-sub-content">Yêu cầu của Quý khách sẽ được thông báo đến Người được bảo hiểm, theo thông tin đã cung cấp bên dưới:</p> 
                                        }
                                        {!isPayment && !haveShowPayment &&
                                            <p className="modal-body-sub-content">Quý khách vui lòng cung cấp thông tin liên
                                                hệ của
                                                (những)
                                                Người sau đây để nhận tin và xác nhận cho phép DLVN xử lý DLCN.</p>}
                                        {(!isPayment || haveShowPayment)&&
                                        <p className="modal-body-sub-content-frame">
                                            Nếu chưa có số điện thoại/hộp thư điện tử liên hệ của Người được bảo hiểm,
                                            Quý khách vui lòng hủy Yêu cầu trực tuyến này để lập Phiếu yêu cầu bằng giấy
                                            và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.
                                        </p>
                                        }

                                        <div className={`collapsible ${isActive ? 'active' : ''}`}>
                                            <div className="collapsible-header" onClick={toggleCollapsible}>
                                                <p className={`modal-body-sub-content ${isActive ? 'bold-text' : ''}`}>{!isEmpty(props.clientProfile) && props.clientProfile.find(item => item.Role === 'LI')?.CustomerName}</p>
                                                {isActive ? <img src={arrowDown} alt="arrow-down"/> :
                                                    <img src={arrowDown} alt="arrow-left"/>}
                                            </div>
                                            <div className="collapsible-content">
                                                <div
                                                    className={`${relateModalData.dateOfBirth.error ? 'validate mb6' : ''} input mt12 disabled`}>
                                                    <div className="input__content" style={{width: '100%'}}>
                                                        <label>Ngày tháng năm sinh</label>
                                                        <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                    id="relateDateOfBirth" disabledDate={disabledDate}
                                                                    value={relateModalData.dateOfBirth.value ? moment(relateModalData.dateOfBirth.value) : ""}
                                                                    onChange={(value) => {
                                                                        if (requiredFields.includes('dateOfBirth')) {
                                                                            const updatedMissingFields = missingFields.filter(field => field !== 'dateOfBirth');
                                                                            setMissingFields(updatedMissingFields);
                                                                        }
                                                                        setRelateModalDataSync({
                                                                            ...relateModalData, dateOfBirth: {
                                                                                ...relateModalData.dateOfBirth, value,
                                                                            }
                                                                        })
                                                                    }}
                                                                    disabled
                                                                    format="DD/MM/YYYY"
                                                                    style={{
                                                                        width: '100%',
                                                                        margin: '0px',
                                                                        padding: '0px',
                                                                        fontSize: '1.4rem',
                                                                        border: '0'
                                                                    }}/>
                                                    </div>
                                                </div>
                                                <div
                                                    ref={cellPhoneRef}
                                                    className={`${relateModalData.cellPhone.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                    <div className="input__content">
                                                        <label>Số điện thoại người được bảo hiểm</label>
                                                        <input value={relateModalData.cellPhone.value?relateModalData.cellPhone.value.trim():''} name="phone"
                                                            id="relateCellPhone"
                                                            onChange={(e) => {
                                                                if (requiredFields.includes('cellPhone')) {
                                                                    const updatedMissingFields = missingFields.filter(field => field !== 'cellPhone');
                                                                    setMissingFields(updatedMissingFields);
                                                                }
                                                                setRelateModalDataSync({
                                                                    ...relateModalData, cellPhone: {
                                                                        error: '', value: e.target.value,
                                                                    }
                                                                })

                                                            }}
                                                            maxlength="10"
                                                            disabled={isPayment}
                                                            onBlur={(event) => validateInput(event)}
                                                            onTouchEnd={(event) => validateInput(event)}
                                                            type="search"/>
                                                    </div>
                                                    <i><img src="../../../img/icon/input_phone.svg"
                                                            alt=""/></i>
                                                </div>
                                                {relateModalData.cellPhone.error && <span
                                                    style={{color: 'red'}}>{relateModalData.cellPhone.error}</span>}
                                                <div
                                                    ref={emailRef}
                                                    className={`${relateModalData.email.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                    <div className="input__content">
                                                        <label>Email Người được bảo hiểm</label>
                                                        <input value={relateModalData.email.value?relateModalData.email.value.trim():''} name="email"
                                                            id="relateEmail"
                                                            disabled={isPayment}
                                                            onChange={(e) => {
                                                                setRelateModalDataSync({
                                                                    ...relateModalData, email: {
                                                                        error: '', value: e.target.value,
                                                                    }
                                                                });
                                                            }}
                                                            onBlur={(event) => validateInput(event)}
                                                            type="search"/>
                                                    </div>
                                                    <i><img src="../../../img/icon/input_mail.svg" alt=""
                                                    /></i>
                                                </div>
                                                {relateModalData.email.error &&
                                                    <span style={{color: 'red'}}>{relateModalData.email.error}</span>}
                                                {(isPayment || haveShowPayment) &&
                                                    <p style={{fontSize: '15px', fontWeight: '500', paddingTop: '16px'}}>Quý khách đã chọn người nhận tiền là Bên mua bảo hiểm, vì vậy, Quý khách vui lòng xác nhận và cam kết:</p>
                                                }
                                                {(isPayment || haveShowPayment) && 
                                                <div
                                                    className={acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                    style={{
                                                        'maxWidth': '600px', display: 'flex'
                                                    }}>
                                                    <div
                                                        className={acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                        style={{
                                                            flex: '0 0 auto', height: '20px', cursor: 'pointer', margin: 0
                                                        }}
                                                        onClick={() => setAcceptPolicy(!acceptPolicy)}>
                                                        <div className="checkmark">
                                                            <img src="img/icon/check.svg" alt=""/>
                                                        </div>
                                                    </div>
                                                    <div className="accept-policy-wrapper" style={{
                                                        paddingLeft: '12px'
                                                    }}>
                                                        <p>Chúng tôi/Tôi Bên mua bảo hiểm xác nhận:</p>
                                                        <p style={{marginTop: 8}}>- Người được bảo hiểm đã đồng ý để Bên mua bảo hiểm nhận khoản thanh toán của Yêu cầu này, nếu được chấp thuận chi trả. Bên mua bảo hiểm cam kết chịu trách nhiệm trước pháp luật về việc nhận tiền này.</p>
                                                        <p style={{marginTop: 8}}>- Bên mua bảo hiểm có trách nhiệm cung cấp Giấy ủy quyền nhận tiền khi nhận thanh toán số tiền lớn hơn hoặc bằng 20 triệu đồng hoặc cung cấp Giấy ủy quyền vào bất kỳ lúc nào khi có yêu cầu nếu số tiền nhận nhỏ hơn 20 triệu đồng.</p>
                                                    </div>
                                                </div>}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <img className="decor-clip" src="../../img/mock.svg" alt=""/>
                    <img className="decor-person" src="../../img/person.png" alt=""/>
                </div>
                <div className="confirm-policy-wrapper">
                    <p className="confirm-policy-content">Bằng cách bấm nút Tiếp tục, Quý khách đã cam kết các thông
                        tin cung cấp trong yêu cầu là chính xác và chịu trách nhiệm với các thông tin đã khai
                        báo.</p>
                </div>
                <div className="bottom-btn" style={{flexDirection: 'column', alignItems: 'center'}}>
                    <LoadingIndicator area="submit-init-claim"/>
                    <button
                        className={`btn btn-confirm ${(isPayment || haveShowPayment) && !acceptPolicy || !relateModalData.cellPhone.value || props.isSubmitting || isError? 'disabled' : ''}`}
                        disabled={(isPayment || haveShowPayment) && !acceptPolicy || !relateModalData.cellPhone.value || props.isSubmitting || isError}
                        onClick={handleClickContinue}>Tiếp tục
                    </button>
                    <button className="btn btn-callback" onClick={onClickCallBack}>Hủy
                    </button>
                </div>
            </div>
        </section>
        {confirmPayment &&
            <AlertPopupND13ConfirmPayment closePopup={closeConfirmPayment} msg={"<p className='basic-bold'>Yêu cầu xác nhận đồng ý Nghị định <br/> 13 đã gửi đến Người được bảo hiểm."} imgPath={FE_BASE_URL + "/img/popup/nd13_agreed_popup.png"} subMsg={"<p> Quý khách vui lòng thực hiện bước tiếp theo</p>"} />
        }
    </>):(<></>)
    );
};

export default ND13POContactInfoOver18;
