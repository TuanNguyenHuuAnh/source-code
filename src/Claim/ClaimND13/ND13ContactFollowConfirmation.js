import React, {useCallback, useEffect, useState} from 'react';
import moment from 'moment';
import {getDeviceId, getSession, removeLocal, setSession} from "../../util/common";
import {CLAIM_STATE} from "../CreateClaim/CreateClaim";
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CELL_PHONE,
    CLAIM_SAVE_LOCAL,
    CLIENT_ID,
    COMPANY_KEY,
    ConsentStatus,
    EMAIL,
    FE_BASE_URL,
    POID,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    RELATION_SHIP_MAPPING,
    RELOAD_BELL,
    ONLY_PAYMENT
} from "../../constants";
import NumberFormat from 'react-number-format';
import arrowDown from "../../img/icon/dropdown-arrow.svg";
import iconArrownRight from '../../img/icon/iconArrowRight.svg';
import '../CreateClaim/ClaimProcess/ClaimDetail/styles.css';
import ND13InsuranceRequestCancelConfirm
    from "./ND13Modal/ND13InsuranceRequestCancelConfirm/ND13InsuranceRequestCancelConfirm";
import {isEmpty} from "lodash";
import {CPConsentConfirmation, iibGetMasterDataByType, postClaimInfo} from "../../util/APIUtils";
import iconStatusReject from "../../img/icon/iconStatusReject.svg";
import iconStatusPending from "../../img/icon/iconStatusPending.svg";
import iconStatusExpired from "../../img/icon/iconStatusExpired.svg";
import iconStatusSuccess from "../../img/icon/iconStatusSuccess.svg";
import iconWarnning from "../../img/icon/icon_Warnning.svg";
import ThanksGeneralPopup from '../../components/ThanksGeneralPopup';
import LoadingIndicator from '../../common/LoadingIndicator2';
import {db} from '../../util/db';

import './styles.css';

const ND13ContactFollowConfirmation = ({
                                           currentState,
                                           handlerBackToPrevStep,
                                           handleSaveLocalAndQuit,
                                           contactPersonInfo,
                                           selectedCliInfo,
                                           trackingId,
                                           claimId,
                                           handlerSubmitContact,
                                           handlerGoToStep,
                                           callBackConfirmation,
                                           closeToHome,
                                           claimSubmissionState,
                                           callBackUpdateND13State,
                                           callBackUpdateND13ClientProfile,
                                           callBackTrackingId,
                                           saveState,
                                           handlerUpdateMainState,
                                           //onlyPayment,
                                           liWating,
                                           callBackLIWating,
                                           cancelClaim
                                       }) => {
    const [isActive, setIsActive] = useState([true]);
    const [isInsuranceCancel, setIsInsuranceCancel] = useState(false);
    const [clientProfile, setClientProfile] = useState([]);
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [relationShipList, setRelationShipList] = useState([]);
    const [isDeclined, setIsDeclined] = useState(false);
    const [isExpired, setIsExpired] = useState(false);
    const [isWaitConfirm, setIsWaitConfirm] = useState(true);
    const [isAgreed, setIsAgreed] = useState(false);
    
    const [TimeExpMin, setTimeExpMin] = useState('30');
    const [showSuccess, setShowSuccess] = useState(false);
    const [isEnablePayment, setIsEnablePayment] = useState(false);
    const [paymentProfile, setPaymentProfile] = useState([]);
    const [haveShowPayment, setHaveShowPayment] = useState(getSession(ONLY_PAYMENT + selectedCliInfo.ClientID)?true:false);
    const [ignore, setIgnore] = useState(false);
    //alert(ONLY_PAYMENT + selectedCliInfo.ClientID);
    //alert(getSession(ONLY_PAYMENT + selectedCliInfo.ClientID));
    const onlyPayment = getSession(ONLY_PAYMENT + selectedCliInfo.ClientID)?true:false;
    // const [toggle, setToggle] = useState(false);
    const [TrackingID, setTrackingID] = useState('');
    
    let myTimeout = null;
    let isMounted = false;

    useEffect(() => {
        isMounted = true;
        fetchCPConsentConfirmation();
        return () => { 
            isMounted = false;
            if (myTimeout) {
                clearTimeout(myTimeout);
            }
         };
    }, [ignore, isAgreed,isExpired, isWaitConfirm, isDeclined]);

    const styles = {
        icon: {
            marginRight: '10px',
        }, text: {
            fontSize: '13px', fontWeight: 500, lineHeight: '19px', textAlign: 'left', color: '#ffffff',
        },
    };


    const toggleCollapsible = (index) => {
        setIsActive(prevState => {
            const updatedIsActive = [...prevState];
            updatedIsActive[index] = !updatedIsActive[index];
            return updatedIsActive;
        });
    };

    const checkPOAndLIEquality = (PO, LI) => {
        const clientIDLI = LI.ClientID;
        return clientIDLI === PO;
    };

    const generateSessionString = (CLIENT_ID, selectedCliInfo) => {
        if (checkPOAndLIEquality(CLIENT_ID, selectedCliInfo)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${selectedCliInfo?.ClientID}`;
        }
    };

    const submitCPConsentConfirmation = () => {
        //Work arround to fix issue claimId empty
        let claId = claimId;
        if (isEmpty(claimId)) {
            if (!isEmpty(trackingId)) {
                let arr = trackingId.split('-');
                if (arr && arr.length > 2) {
                    claId = arr[1];
                }
            }
        }
        const request = {
            jsonDataInput: {
                Action: "ConfirmClaim",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClaimID: claId,
                TrackingID: trackingId,
                Company: COMPANY_KEY,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
            }
        };

        postClaimInfo(request)
            .then(res => {
                const Response = res.Response;
                if (res.Response.Result === 'true' && res.Response.Message !== null) {
                    console.log('confirm claim success: ', res.Response.Message);
                } else {
                    postClaimInfo(request)
                    .then(res => {
                        const Response = res.Response;
                        if (res.Response.Result === 'true' && res.Response.Message !== null) {
                            console.log('retry confirm claim success: ', res.Response.Message);
                        } else {
                            console.log('retry confirm claim fail: ', res.Response.Message);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
                }
            })
            .catch(error => {
                console.log(error);
                postClaimInfo(request)
                .then(res => {
                    const Response = res.Response;
                    if (res.Response.Result === 'true' && res.Response.Message !== null) {
                        console.log('retry confirm claim success: ', res.Response.Message);
                    } else {
                        console.log('retry confirm claim fail: ', res.Response.Message);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
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

    const fetchCPConsentConfirmation = () => {
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ClientList: generateSessionString(getSession(CLIENT_ID), selectedCliInfo),
                ProcessType: "Claim",
                DeviceId: getDeviceId(), // Thêm hàm getDeviceId() tương ứng
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                TrackingID: TrackingID?TrackingID:trackingId,
            }
        };

        CPConsentConfirmation(request).then(res => {
            let Response = res.Response;
            if ((Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) || Response.Result === 'false') {
                if (isMounted) {
                    const isButtonDisabled = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.Role === 'LI') && (item.ConsentResult !== "Agreed")) || Response.Config[0]?.IsEnableND13 !== '1';
                    const isDeclined = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Declined") && (item.Role === 'LI'));
                    const isWaitConfirm = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "WaitConfirm") && (item.Role === 'LI'));
                    const isExpired = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Expired") && (item.Role === 'LI'));
                    const isAgreed = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Agreed") && (item.Role === 'LI'));
                    const config = Response.Config;
                    const IsEnablePayment = !isEmpty(config) && config[0]?.IsEnablePayment || '0';
                    const paymentProfile =  Response.ClientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT');
                    const consentResultPO = generateConsentResults(Response.ClientProfile)?.ConsentResultPO;
                    const consentResulLI = generateConsentResults(Response.ClientProfile)?.ConsentResultLI;
                    const isOpenPopupWarning = ( (consentResultPO !== ConsentStatus.AGREED) || (consentResulLI !== ConsentStatus.AGREED));
                    // let LIItem = null;
                    // let LIProfile = !isEmpty(Response.ClientProfile) && Response.ClientProfile?.filter(ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') );
                    // if (!isEmpty(LIProfile)) {
                    //     LIItem = LIProfile[0];
                    //     if (LIItem.ConsentResult !== ConsentStatus.AGREED) {
                    //         callBackLIWating(LIItem);
                    //     }
                    // }
                    // if (!ignore) {
                    //     if (!isOpenPopupWarning && (IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                    //         setHaveShowPayment(true);
                    //     } else {
                    //         setHaveShowPayment(false);
                    //     }
                    // }
                    if (!isEmpty(config)) {
                        const timeExp = config[0]?.TimeExpMin;
                        setTimeExpMin(timeExp);
                    }
                    if (isDeclined) {
                        setIsDeclined(true);
                    } else {
                        setIsDeclined(false);
                    }
                    if (isWaitConfirm) {
                        setIsWaitConfirm(true);
                    } else {
                        setIsWaitConfirm(false);
                    }
                    if (isExpired) {
                        setIsExpired(true);
                    } else {
                        setIsExpired(false);
                    }
                    if (isAgreed) {
                        setIsAgreed(true);
                    } else {
                        setIsAgreed(false);
                    }
                    
                    if (IsEnablePayment) {
                        setIsEnablePayment(true);
                    } else {
                        setIsEnablePayment(false);
                    }
                    if (paymentProfile) {
                        setPaymentProfile(paymentProfile);
                    } 
                    setIsButtonDisabled(isButtonDisabled);
                    setClientProfile(Response.ClientProfile);
                    if (!isEmpty(Response.ClientProfile)) {
                        // alert(Response.ClientProfile[0]?.TrackingID);
                        setTrackingID(Response.ClientProfile[0]?.TrackingID);
                    }
                    if (isButtonDisabled && !isDeclined && !haveShowPayment) {
                        setIgnore(true);
                        myTimeout = setTimeout(fetchCPConsentConfirmation, 30000);
                    }
                }

            }
        }).catch(error => {
            console.log(error);
        });
        
        setTimeout(() => {
            setSession(RELOAD_BELL, RELOAD_BELL);
        }, 5000);
    };

    const updateConsentResult = (records) => {
        records.forEach(record => {
            if (record.ConsentResult === ConsentStatus.EXPIRED) {
                record.ConsentResult = ConsentStatus.WAIT_CONFIRM;
            }
        });

        return records;
    }

    const extendPeriodConsent = (data) => {
        const request = {
            jsonDataInput: {
                Action: "ExtendPeriodConsent",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                Company: COMPANY_KEY,
                ProcessType: "Claim",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN),
                ConsentSubmit: updateConsentResult([data]),
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    fetchCPConsentConfirmation();
                    setShowSuccess(true);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    const handleAdjust = () => {
        if (myTimeout) {
            clearTimeout(myTimeout);
        }
        const isOver18 = isOlderThan18(clientProfile[0]?.CustomerDoB);
        const claimSubmissionState = isOver18
            ? CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_OVER_18
            : CLAIM_STATE.ND13_INFO_PO_CONTACT_INFO_UNDER_18;

        callBackUpdateND13State(claimSubmissionState);
        handlerGoToStep(CLAIM_STATE.SUBMIT);
        
    };

    const getStatusButton = (status) => {
        const BUTTON_STYLES = {
            [ConsentStatus.AGREED]: {
                backgroundColor: '#069000', color: '#ffffff', borderRadius: '15px', padding: '6px 12px',
            }, [ConsentStatus.DECLINED]: {
                backgroundColor: '#DE181F', color: '#ffffff', borderRadius: '15px', padding: '6px 12px',
            }, [ConsentStatus.WAIT_CONFIRM]: {
                backgroundColor: '#E79000', color: '#ffffff', borderRadius: '15px', padding: '6px 12px',
            }, [ConsentStatus.EXPIRED]: {
                backgroundColor: '#9C9C9C', color: '#ffffff', borderRadius: '15px', padding: '6px 12px',
            },
        };

        const BUTTON_TEXT = {
            [ConsentStatus.AGREED]: 'Đã đồng ý',
            [ConsentStatus.DECLINED]: 'Không đồng ý',
            [ConsentStatus.WAIT_CONFIRM]: 'Chờ xác nhận',
            [ConsentStatus.EXPIRED]: 'Hết hạn',
        };

        const buttonStyle = BUTTON_STYLES[status] || BUTTON_STYLES[ConsentStatus.WAIT_CONFIRM];
        const buttonText = BUTTON_TEXT[status] || BUTTON_TEXT[ConsentStatus.WAIT_CONFIRM];

        return (<button style={buttonStyle}>
            {buttonText}
        </button>);
    };

    const getIconByConsentStatus = (consentStatus) => {
        switch (consentStatus) {
            case ConsentStatus.AGREED:
                return iconStatusSuccess;
            case ConsentStatus.DECLINED:
                return iconStatusReject;
            case ConsentStatus.WAIT_CONFIRM:
                return iconStatusPending;
            case ConsentStatus.EXPIRED:
                return iconStatusExpired;
            default:
                return iconStatusPending;
        }
    }

    const checkSubmit = () => {
        submitCPConsentConfirmation();
        document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + selectedCliInfo.ClientID);
        // closeToHome();
    }

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const closePopupSucceededRedirect = () => {
        window.location.href = '/followup-claim-info';
        handlerGoToStep(CLAIM_STATE.DONE);
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + selectedCliInfo.ClientID);
    }

    const isOlderThan18 = (dob) => {
        const birthDate = new Date(dob);
        const currentDate = new Date();

        // Calculate the age
        let age = currentDate.getFullYear() - birthDate.getFullYear();
        const monthDiff = currentDate.getMonth() - birthDate.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < birthDate.getDate())) {
            age--;
        }
        // Check if the person is older than 18
        return age >= 18;
    }

    const closeSuccess = () => {
        setShowSuccess(false);
    }

    const getRelationShips = useCallback(() => {
        let request = {
            Action: "RelationShipConsent", Project: "mcp"
        };
        iibGetMasterDataByType(request)
            .then(Res => {
                let Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile) {
                    if (isMounted) {
                        setRelationShipList(Response.ClientProfile);
                    }
                }
            })
            .catch(error => {
                // Xử lý lỗi ở đây nếu cần
            });
    }, []);

    const mapRelationCodeToText = (relationCode) => {
        const relation = relationShipList.find(rel => rel.RelationCode === relationCode);
        if (relation) {
            return relation.RelationName;
        } else {
            return "Khác";
        }
    }

    const cancelClaimND13 = () => {
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + selectedCliInfo.ClientID);
        setTimeout(() => {
            window.location.href = '/';
        }, 200);
    }
    useEffect(() => {
        isMounted = true;
        getRelationShips();
        return () => { 
            isMounted = false;
            if (myTimeout) {
                clearTimeout(myTimeout);
            }
        };
    }, [getRelationShips]);
    let LIItem = null;
    let LIProfile = !isEmpty(clientProfile) && clientProfile?.filter(ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') );
    if (haveShowPayment) {
        LIProfile = !isEmpty(clientProfile) && clientProfile?.filter(ite => (ite.ConsentRuleID === "CLAIM_PAYMENT") && (ite.Role === 'LI') );
    }
    if (!isEmpty(LIProfile)) {
        LIItem = LIProfile[0];
    }
    return (
    <>
    <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance" style={{paddingTop: '140px'}}>
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
                </div>
                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                    <p>Chọn thông tin</p>
                    <i><img src="../../img/icon/return_option.svg" alt=""/></i>
                </div>
                <div className="heading__tab">
                    <div className="step-container">
                        <div className="step-wrapper">
                            
                            <div className="step-btn-wrapper">
                                {(isWaitConfirm || isExpired)&&
                                <div className="back-btn"
                                     onClick={() => handleAdjust()}>
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
                                }
                            </div>
                            
                            <div className="progress-bar">
                                <div
                                    className={(currentState >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                                    <div className="bullet">
                                        <span>1</span>
                                    </div>
                                    <p>Thông tin sự kiện</p>
                                </div>
                                <div
                                    className={(currentState >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                                    <div className="bullet">
                                        <span>2</span>
                                    </div>
                                    <p>Thanh toán và liên hệ</p>
                                </div>
                                <div
                                    className={(currentState >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                                    <div className="bullet">
                                        <span>3</span>
                                    </div>
                                    <p>Kèm <br/>chứng từ</p>
                                </div>
                                <div
                                    className={(currentState >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                                    <div className="bullet">
                                        <span>4</span>
                                    </div>
                                    <p>Hoàn tất yêu cầu</p>
                                </div>
                            </div>
                            
                            <div className="step-btn-save-quit">
                                {!isDeclined&&!isAgreed&&
                                <div>
                                    <button>
                                                <span className="simple-brown" style={{zIndex: '30'}}
                                                      onClick={handleSaveLocalAndQuit}>Lưu & thoát</span>
                                    </button>
                                </div>
                                }
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>
            <div className="follow-confirm-wrapper" style={styles.container}>
                <img style={styles.icon} src={!haveShowPayment && LIItem?getIconByConsentStatus(LIItem.ConsentResult):iconWarnning} alt="iconWarnning"/>
                {isDeclined?(
                    <div>
                        <p style={styles.text}>Chủ thể dữ liệu không đồng ý xử lý DLCN, Quý khách có thể thực hiện lại yêu cầu trực tuyến này hoặc lập phiếu yêu cầu bằng giấy tại văn phòng/Tổng Đại lý DLVN gần nhất.</p>
                        <p style={styles.text}>Yêu cầu trực tuyến này sẽ tự động bị hủy bỏ sau 24h.</p>
                    </div>
                ):(isExpired?(
                    <div>
                        <p style={styles.text}>Đã hết thời gian hiệu lực xác nhận DLCN. Quý khách có thể chọn Gia hạn lại để tiếp tục thực hiện yêu cầu trực tuyến này hoặc lập phiếu yêu cầu bằng giấy tại văn phòng/Tổng Đại lý DLVN gần nhất.</p>
                        <p style={styles.text}> Yêu cầu trực tuyến này sẽ tự động bị hủy bỏ sau 24h.</p>
                    </div>
                    ):(isWaitConfirm?(
                        (isEnablePayment === '1') && !isEmpty(paymentProfile)?(
                            <div>
                                <p style={styles.text}>Thông báo xác nhận cho phép DLVN xử lý DLCN đã gửi đến (những) Người sau đây theo số điện thoại/hộp thư điện tử được Quý khách khai báo.</p>
                                <p style={styles.text}>Quý khách vui lòng chờ xác nhận, thời gian chờ tối đa là {TimeExpMin} phút.</p>
                            </div>
                        ):(
                        <div>
                            <p style={styles.text}>Thông báo xác nhận cho phép DLVN xử lý DLCN đã gửi đến (những) Người sau đây theo số điện thoại/hộp thư điện tử được Quý khách khai báo</p>
                            <p style={styles.text}>Quý khách vui lòng chờ xác nhận, thời gian chờ tối đa là {TimeExpMin} phút.</p>
                        </div>
                        )
                        ):(haveShowPayment?(
                            isOlderThan18(LIItem?.CustomerDoB)?(
                            <div>
                                <p style={styles.text}>Thông tin về người nhận tiền của Yêu cầu này, nếu được chấp thuận, đã được gửi đến Người được bảo hiểm sau đây theo số điện thoại/hộp thư diện tử được Quý khách khai báo.<br/> Quý khách vui lòng bấm Hoàn thành để nộp Yêu cầu.</p>
                            </div>
                            ):(
                            <div>
                                <p style={styles.text}>Thông tin về người nhận tiền của Yêu cầu này, nếu được chấp thuận, đã được gửi đến Cha/Mẹ/Người giám hộ của Người được bảo hiểm theo số điện thoại/hộp thư điện tử được Quý khách khai báo.<br/> Quý khách vui lòng bấm Hoàn thành để nộp Yêu cầu.</p>
                            </div>  
                            )
                        ):(
                            <div>
                                <p style={styles.text}>Chủ thể dữ liệu đã xác nhận đồng ý xử lý DLCN. Quý khách vui lòng bấm Hoàn thành để nộp hồ sơ yêu cầu bảo hiểm.</p>
                            </div>
                        )
                        )
                        
                    )

                )}

            </div>
            <div style={{marginTop: 0}} className="stepform">
                <div className="info">
                    <div className="info__body">
                        <div className="optionalform-isActivewrapper">
                            <div className="optionalform">
                                <div className="optionalform__title">
                                    <h5 className="basic-bold"
                                        style={{marginTop: 24, marginBottom: 0, marginLeft: 16}}>{!haveShowPayment&& 'DANH SÁCH '}NGƯỜI ĐƯỢC BẢO HIỂM</h5>
                                        
                                </div>
                                {/*---------------------------------------------------------------*/}

                                {!isEmpty(LIProfile) && LIProfile?.map((item, ind) => (<div key={ind}
                                                                                                    className={`collapsible ${isActive[ind] ? 'active' : ''}`}
                                                                                                    style={{padding: '0px 16px 0px'}}>
                                    <div className="collapsible-header"
                                         onClick={() => toggleCollapsible(ind)}>
                                        <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                            {item?.CustomerName}
                                        </p>
                                        {!haveShowPayment && LIItem &&
                                            <img style={{transform: 'initial', marginRight: 6}}
                                            src={getIconByConsentStatus(LIItem.ConsentResult)} alt="icon-status"/>
                                        }
                                        {isActive[ind] ? (<img src={arrowDown} alt="arrow-down"/>) : (
                                            <img src={arrowDown} alt="arrow-left"/>)}
                                    </div>

                                    <div className="collapsible-content">
                                        <div className="optionalform__body">
                                            <div className="tab-wrapper" style={{marginTop: 16}}>
                                                <div className="collapsible-header">
                                                    <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                                        {haveShowPayment?'Thông tin liên hệ':'Thông tin người xác nhận'}
                                                    </p>
                                                    {(item.Role === 'LI') && (isWaitConfirm || isExpired)&&
                                                        <div className="dflex" onClick={() => handleAdjust()}>
                                                            <p className="edit-text">Điều chỉnh</p>
                                                            <img src={iconArrownRight} alt="arrow-right"/>
                                                        </div>}
                                                </div>
                                                {/* Họ và tên */}
                                                <div className="input disabled">
                                                    <div className="input__content">
                                                        {(item.CustomerName) && 
                                                        (<label style={{marginLeft: '2px'}}>{haveShowPayment?'Tên người liên hệ':'Họ và tên'}</label>)}
                                                            <input type="search" placeholder={haveShowPayment?'Tên người liên hệ':'Họ và tên'}
                                                                maxLength="100"
                                                                value={item.CustomerName}
                                                                disabled
                                                            />
                                                    </div>
                                                    <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                </div>
                                                {/* CMND/CCCD */}
                                                {!isOlderThan18(LIItem?.CustomerDoB) &&
                                                <div className="tab">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {(item.ReceiverIDNum || item.CustomerIDNum ) && <label
                                                                style={{marginLeft: '2px'}}>CMND/CCCD</label>}
                                                                <input type="search" placeholder="CMND/CCCD"
                                                                    maxLength="14"
                                                                    disabled
                                                                    value={item.ReceiverIDNum?item.ReceiverIDNum:item.CustomerIDNum}
                                                                    onChange={() => {
                                                                    }} />

                                                        </div>
                                                        <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                </div>
                                                }
                                                {!isOlderThan18(LIItem?.CustomerDoB) &&
                                                    <div className="tab">
                                                        <div className="input disabled">
                                                            <div className="input__content">
                                                                {item.RelationShip && <label
                                                                    style={{ marginLeft: '2px' }}>Mối quan hệ với Người được bảo hiểm</label>}
                                                                <input type="search" placeholder="Mối quan hệ với Người được bảo hiểm"
                                                                    maxLength="14"
                                                                    disabled
                                                                    value={RELATION_SHIP_MAPPING[item.RelationShip] ? RELATION_SHIP_MAPPING[item.RelationShip] : 'Khác'}
                                                                    onChange={() => {
                                                                    }} />
                                                            </div>
                                                            <i><img src="../../img/icon/edit.svg" alt="" /></i>
                                                        </div>
                                                    </div>
                                                }
                                                {/* Ngày sinh */}

                                                <div className="tab">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {(item.ReceiverDoB || item.CustomerDoB) && <label
                                                                style={{marginLeft: '2px'}}>Ngày tháng năm sinh</label>}
                                                                <input type="search" placeholder="Ngày tháng năm sinh"
                                                                maxLength="14"
                                                                disabled
                                                                value={item.ReceiverDoB ? moment(item.ReceiverDoB, "YYYY-MM-DD HH:mm:ss.S").format("DD/MM/YYYY") : moment(item.CustomerDoB, "YYYY-MM-DD HH:mm:ss.S").format("DD/MM/YYYY")}
                                                                onChange={() => {
                                                                }}/>
                                                        </div>
                                                        <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                </div>
                                                {/* Điện thoại */}
                                                <div className="tab">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {(item.PhoneReceiver) && <label style={{marginLeft: '2px'}}>Số điện
                                                                thoại</label>}
                                                                <NumberFormat displayType="input" type="search"
                                                                    maxLength="12"
                                                                    value={item.PhoneReceiver}
                                                                    prefix=""
                                                                    placeholder="Số điện thoại"
                                                                    allowNegative={false}
                                                                    allowLeadingZeros={true}
                                                                    disabled
                                                                    onChange={() => {
                                                                    }}
                                                                    onFocus={(e) => e.target.placeholder = ""}
                                                                    onBlur={(e) => e.target.placeholder = "Số điện thoại"}
                                                                />

                                                        </div>
                                                        <i><img src="../../img/icon/input_phone.svg"
                                                                alt=""/></i>
                                                    </div>
                                                </div>
                                                {/* Email */}
                                                <div className="tab">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {(item.EmailReceiver) && <label
                                                                style={{marginLeft: '2px'}}>Email</label>}
                                                                <input type="search" placeholder="Email"
                                                                    maxLength="100"
                                                                    value={item.EmailReceiver}
                                                                    onChange={() => {
                                                                    }}
                                                                    disabled
                                                                />

                                                        </div>
                                                        <i><img src="../../img/icon/email.svg" alt=""/></i>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className="collapsible-header"
                                                 style={{marginTop: 16, marginBottom: 0}}>
                                                <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                                    {!haveShowPayment&&'Xác nhận dữ liệu cá nhân'}</p>
                                                {!haveShowPayment && LIItem && getStatusButton(LIItem.ConsentResult)}
                                            </div>
                                            {item.ConsentResult === ConsentStatus.EXPIRED && <button
                                                style={{
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                    justifyContent: 'center',
                                                    float: 'right',
                                                    marginTop: 8
                                                }}
                                                onClick={() => extendPeriodConsent(item)}
                                            >
                                                <span className="simple-brown extend-text">Gia hạn lại</span>
                                                <div className="svg-wrapper">
                                                    <svg
                                                        width="11"
                                                        height="15"
                                                        viewBox="0 0 11 15"
                                                        fill="none"
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        style={{transform: 'scaleX(-1)', marginLeft: 6}}
                                                    >
                                                        <path
                                                            d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                                                            fill="#985801"
                                                            stroke="#985801"
                                                            strokeWidth="0.2"
                                                        />
                                                    </svg>
                                                </div>
                                            </button>}

                                        </div>
                                    </div>
                                </div>))}

                                {/*---------------------------------------------------------------*/}
                            </div>
                        </div>
                    </div>
                </div>
                <img className="decor-clip" src="../../img/mock.svg" alt=""/>
                <img className="decor-person" src="../../img/person.png" alt=""/>
            </div>
            <LoadingIndicator area="submit-init-claim"/>
            <div className="bottom-btn" style={{flexDirection: 'column', alignItems: 'center'}}>
                <button
                    className={isButtonDisabled ? "btn btn-primary disabled" : "btn btn-primary"}
                    id="submitContactDetail"
                    disabled={isButtonDisabled}
                    onClick={() => checkSubmit()}
                >
                    Hoàn thành
                </button>
                <button className="btn btn-callback"
                        onClick={() => setIsInsuranceCancel(true)}>Hủy
                </button>
            </div>
        </div>
        {isInsuranceCancel && <ND13InsuranceRequestCancelConfirm onClickCallBack={() => {
            setIsInsuranceCancel(false);
        }} onClickExtendBtn={() => cancelClaimND13()}/>}


        {/* Popup succeeded redirect */}
        <div className="popup special envelop-confirm-popup" id="popupSucceededRedirect">
            <div className="popup__card">
                <div className="envelop-confirm-card">
                    <div className="envelopcard">
                        <div className="envelop-content">
                            <div className="envelop-content__header"
                                 onClick={closePopupSucceededRedirect}
                            >
                                <i className="closebtn"><img src="../../img/icon/close.svg" alt=""/></i>
                            </div>
                            <div className="envelop-content__body">
                                <div>
                                    <h4 className="popup-claim-submission-h4">Gửi yêu cầu thành
                                        công</h4>
                                    <p>Vui lòng theo dõi tình trạng <br/> hồ sơ tại chức năng <br/> Theo
                                        dõi yêu
                                        cầu.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="envelopcard_bg">
                        <img src="../../img/envelop_nowhite.png" alt=""/>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    </section>
    {showSuccess && <ThanksGeneralPopup closePopup={() => closeSuccess()}
                                           msg={`<p>Quý khách vui lòng thông báo cho Chủ thể dữ liệu xác nhận DLCN. <br/>Hiệu lực xác nhận là ${TimeExpMin} phút</p>`}
                                           />}
    </>
    );
}

export default ND13ContactFollowConfirmation;
