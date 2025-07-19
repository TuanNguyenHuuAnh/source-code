import React, {useCallback, useEffect, useState} from 'react';
import moment from 'moment';
import {getDeviceId, getSession, removeLocal, setSession, deleteND13DataTemp, haveCheckedDeadth, haveHC_HS} from "../sdkCommon";
// import { ProcessType} from "../UpdatePolicyInfo";
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
    ONLY_PAYMENT,
    DCID,
    UPDATE_POLICY_INFO_SAVE_LOCAL,
    ND_13,
    IS_MOBILE,
    SDK_ROLE_PO,
    CONSENT_RULE_ID_ND13,
    SDK_ROLE_AGENT
} from "../sdkConstant";
import NumberFormat from 'react-number-format';
import arrowDown from "../img/icon/dropdown-arrow.svg";
import iconArrownRight from '../img/icon/iconArrowRight.svg';
import './update.css';
import ND13InsuranceRequestCancelConfirm
    from "./ND13Modal/ND13InsuranceRequestCancelConfirm/ND13InsuranceRequestCancelConfirm";
import {cloneDeep, isEmpty} from "lodash";
import {CPConsentConfirmation, iibGetMasterDataByType, postClaimInfo} from "../sdkAPI";
import iconStatusReject from "../img/icon/iconStatusReject.svg";
import iconStatusPending from "../img/icon/iconStatusPending.svg";
import iconStatusExpired from "../img/icon/iconStatusExpired.svg";
import iconStatusSuccess from "../img/icon/iconStatusSuccess.svg";
import iconWarnning from "../img/icon/icon_Warnning.svg";
import ThanksGeneralPopup from '../components/ThanksGeneralPopup';
import LoadingIndicator from '../LoadingIndicator2';
// import {db} from '../db';

import './styles.css';

const ND13ContactFollowConfirmation = ({
                                           currentState,
                                           handlerAdjust,
                                           handleSaveLocalAndQuit,
                                           contactPersonInfo,
                                           poID = '',
                                           trackingId ='',
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
                                           cancelClaim,
                                           clientListStr,
                                           clientId,
                                           deviceId,
                                           apiToken,
                                           genOtp,
                                           appType,
                                           from,
                                           policyNo,
                                           proccessType,
                                           updateClientProfile,
                                           callBackUpdateND13StateRefresh,
                                           updateExistLINotAgree,
                                           updateAllLIAgree,
                                           updateIsWaitConfirm,
                                           updateIsExpired,
                                           deleteLocal,
                                           handlerConfirmCPConsent,
                                           disableEdit,
                                           agentKeyInPOSelfEdit,
                                           notAgree,
                                           systemGroupPermission,
                                           cancelRequest,
                                           claimCheckedMap,
                                           claimTypeList,
                                           selectedCliInfo
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
    const [haveShowPayment, setHaveShowPayment] = useState(false);
    const [ignore, setIgnore] = useState(false);
    const [isPayment, setIsPayment] = useState(false);
    const [haveDLCN, setHaveDLCN] = useState(false);

    const [clientList, setClientList] = useState([]);
    const ROLE_LI = "LI";
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
         console.log(ignore, isAgreed,isExpired, isWaitConfirm, isDeclined)
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
        const clientIDLI = LI;
        return clientIDLI === PO;
    };

    const generateSessionString = (CLIENT_ID, poID) => {
        if (checkPOAndLIEquality(CLIENT_ID, poID)) {
            return CLIENT_ID;
        } else {
            return `${CLIENT_ID},${poID}`;
        }
    };

    const submitCPConsentConfirmation = () => {
        const request = {
            jsonDataInput: {
                Action: "ReInstatementConfirm",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClaimID: claimId,
                TrackingID: trackingId,
                Company: COMPANY_KEY,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientId,
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

    const haveLIStillNotAgree = (data) => {
        let result = false;
        console.log('haveLIStillNotAgree=', data);
        data.forEach((item, index) => {
            const role = item.Role;
            if ((role === 'LI') && (item.ConsentRuleID === 'ND13') && (item.ConsentResult !== ConsentStatus.AGREED)) {
                console.log('haveLIStillNotAgree true');
                result = true;
                return result;
            }
            
        });
        console.log('haveLIStillNotAgree =', result);
        return result;
    }
    
    const fetchCPConsentConfirmation = () => {
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClientID: clientId,
                Company: COMPANY_KEY,
                ClientList: clientListStr,
                ProcessType: proccessType,
                DeviceId: deviceId, 
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientId,
                TrackingID: trackingId,
                ConsumerTracking: 'Update#FollowConfirmation'
            }
        };

        CPConsentConfirmation(request).then(res => {
            let Response = res.Response;
            console.log('Response:', Response, isMounted);
            if ((Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) || Response.Result === 'false') {
                if (isMounted) {
                    const isButtonDisabled = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.Role === 'LI') && (item.ConsentResult !== "Agreed")) || Response.Config[0]?.IsEnableND13 !== '1';
                    const isDeclined = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Declined") && (item.Role === 'LI'));
                    const isWaitConfirm = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "WaitConfirm") && (item.Role === 'LI'));
                    const isExpired = Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Expired") && (item.Role === 'LI'));
                    let isAgreed = false;//Response.ClientProfile?.some(item => (item.ConsentRuleID === 'ND13') && (item.ConsentResult === "Agreed") && (item.Role === 'LI'));
                    const config = Response.Config;
                    const IsEnablePayment = !isEmpty(config) && config[0]?.IsEnablePayment || '0';
                    const paymentProfile =  Response.ClientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT');
                    if (proccessType === 'Claim' && !isEmpty(paymentProfile)) {
                        setHaveShowPayment(true);
                    }
                    
                    // const consentResultPO = generateConsentResults(Response.ClientProfile)?.ConsentResultPO;
                    // const consentResulLI = generateConsentResults(Response.ClientProfile)?.ConsentResultLI;
                    // const isOpenPopupWarning = ( (consentResultPO !== ConsentStatus.AGREED) || (consentResulLI !== ConsentStatus.AGREED));
                    const predicateStillNotAgree =  item => (item.ConsentRuleID === "ND13") && (item.Role === 'LI') && (item.ConsentResult !== ConsentStatus.AGREED);
                    const stillNotAgree = (Response.ClientProfile ?? [])?.filter(predicateStillNotAgree);
                    if (isEmpty(stillNotAgree)) {
                        isAgreed = true;
                    } 
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
                        updateIsWaitConfirm(true);
                    } else {
                        setIsWaitConfirm(false);
                        updateIsWaitConfirm(false);
                    }
                    if (isExpired) {
                        setIsExpired(true);
                        updateIsExpired(true);
                    } else {
                        setIsExpired(false);
                        updateIsExpired(false);
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

                    updateClientProfile(Response.ClientProfile);
                    console.log('isButtonDisabled=' + isButtonDisabled + ' ,!isDeclined=' +  !isDeclined + ',!haveShowPayment || (haveShowPayment && !isWaitConfirm)=' + (!haveShowPayment || (haveShowPayment && !isWaitConfirm)));
                    if (isButtonDisabled && !isDeclined) {
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
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClientID: clientId,
                Company: COMPANY_KEY,
                ProcessType: proccessType,
                DeviceId: deviceId,
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: clientId,
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
        callBackUpdateND13StateRefresh(ND_13.ND13_INFO_PO_CONTACT_INFO_OVER_18);
       
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

    const getGeneralIconStatus = () => {
        if (isAgreed) {
            return iconStatusSuccess;
        } else if (isDeclined) {
            return iconStatusReject;
        } else if (isExpired) {
            return iconStatusExpired;
        } else {
            return iconStatusPending;
        }
    }

     const checkSubmit = () => {
        if (proccessType === 'Claim') {
            handlerConfirmCPConsent();
            return;
        }
        if ((appType === 'CLOSE') || (proccessType !== 'RIN')) {
            genOtp();
        } else {
            let obj = {
                Action: "END_ND13_" + proccessType,
                ClientID: clientId,
                PolicyNo: policyNo,
                TrackingID: trackingId
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
        
        // submitCPConsentConfirmation();
        // document.getElementById("popupSucceededRedirect").className = "popup special envelop-confirm-popup show";
        // removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + poID);
        // closeToHome();
    }

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const closePopupSucceededRedirect = () => {
        window.location.href = '/update-policy-info';
        // handlerGoToStep(ND_13.DONE);
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + poID);
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
        // closeToHome();
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

    const cancelND13 = () => {
        if (deleteLocal) {
            deleteLocal();
        }
        if (cancelRequest) {
            cancelRequest();
        }
        if (myTimeout) {
            clearTimeout(myTimeout);
        }
        if (appType === 'CLOSE') {
            removeLocal(UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + policyNo);
            setTimeout(() => {
                window.location.href = '/';
            }, 200);
        } else {
            let obj = {
                Action: "CANCEL_ND13_" + proccessType,
                ClientID: clientId,
                PolicyNo: policyNo,
                TrackingID: trackingId
            };
            if (from && (from === "Android")) {//for Android
                if (window.AndroidAppCallback) {
                    window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                }
            } else if (from && (from === "IOS")) {//for IOS
                console.log('IOS callbackNavigateToPage...........');
                if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                    window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                }
            }
        }
        deleteND13DataTemp(clientId, trackingId, apiToken, deviceId);
        setIsInsuranceCancel(false);
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
    if (isEmpty(LIProfile) && haveShowPayment) {
        LIProfile = !isEmpty(clientProfile) && clientProfile?.filter(ite => (ite.ConsentRuleID === "CLAIM_PAYMENT") && (ite.Role === 'LI') );
    }
    if (!isEmpty(LIProfile)) {
        LIItem = LIProfile[0];
    }


    useEffect(()=>{
        let predicate = ite => (ite.ConsentRuleID === "CLAIM_PAYMENT") && (ite.Role === 'LI');
        let filter = (clientProfile ?? [])?.filter(predicate);
        if (!isEmpty(filter)) {
            setIsPayment(true);
        } /*else {
            predicate = ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') && (ite.ConsentResult !== 'Agreed') && (ite.ReceiverName);
            filter = (clientProfile ?? [])?.filter(predicate);
        }*/
        predicate = ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') && (ite.ConsentResult !== 'Agreed') && (ite.ReceiverName);
        let nd13Filter = (clientProfile ?? [])?.filter(predicate);
        if (!isEmpty(nd13Filter)) {
            setHaveDLCN(true);
            filter = nd13Filter;
        } else {
            predicate = ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') && (ite.ConsentResult === 'Agreed') && (ite.ReceiverName);
            nd13Filter = (clientProfile ?? [])?.filter(predicate);
            if (!isEmpty(nd13Filter) && isEmpty(filter)) {
                filter = nd13Filter;
                //Case đã đồng ý
                setHaveDLCN(true);
            }
        }
        console.log('clientProfile', clientList, haveShowPayment)
        setClientList(filter);
        console.log('filter=', filter);
        if (!isEmpty(filter)) {
        LIItem = filter[0];
        const predicateExistDecline =  ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') && (ite.ConsentResult === ConsentStatus.DECLINED);
        const existDeclined = (clientProfile ?? [])?.filter(predicateExistDecline);
        if (!isEmpty(existDeclined)) {
            if (updateExistLINotAgree) {
                updateExistLINotAgree(true);
            }
        } else {
            const predicateStillNotAgree =  ite => (ite.ConsentRuleID === "ND13") && (ite.Role === 'LI') && (ite.ConsentResult !== ConsentStatus.AGREED);
            const stillNotAgree = (clientProfile ?? [])?.filter(predicateStillNotAgree);
            if (isEmpty(stillNotAgree) && updateAllLIAgree) {
                updateAllLIAgree(true);
            } 
        }
    }

    }, [clientProfile, isPayment, haveDLCN]);

    return (
    <>
    {/* <section className="sccontract-warpper" id="scrollAnchor"> */}
        <div className="insurance" style={{paddingTop: '10px'}}>
            <div className={(proccessType === 'Claim')?"follow-confirm-wrapper margin-top119": "follow-confirm-wrapper margin-top16"}>
                <img style={styles.icon} src={isPayment && !haveDLCN?iconWarnning: getGeneralIconStatus()} alt="iconWarnning"/>
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
                            <p style={styles.text}>Thông báo đề nghị xác nhận đồng ý cho phép DLVN xử lý DLCN đã được gửi đến (những) Người sau đây theo số điện thoại/hộp thư điện tử được Quý khách khai báo.</p>
                            <p style={styles.text}>Quý khách vui lòng chờ xác nhận, thời gian chờ tối đa là {TimeExpMin} phút.</p>
                        </div>
                        )
                        ):(isPayment && !haveDLCN?(
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
                                <p style={styles.text}>Chủ thể dữ liệu đã xác nhận đồng ý cho phép DLVN xử lý DLCN. Quý khách vui lòng bấm Hoàn thành để nộp yêu cầu trực tuyến này.</p>
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
                                        style={{marginTop: 24, marginBottom: 0, marginLeft: 16}}>{!(isPayment && !haveDLCN)? 'DANH SÁCH CHỦ THỂ DỮ LIỆU': 'NGƯỜI ĐƯỢC BẢO HIỂM'}</h5>
                                        
                                </div>
                                {/*---------------------------------------------------------------*/}

                                {clientList.map((item, ind) => (
                                    <div key={ind} className={`collapsible ${isActive[ind] ? ((item.ConsentResult === "Expired")?'active nd13-padding-bottom16':'active') : ''}`} style={{padding: '0px 16px 0px'}}>
                                    <div className="collapsible-header"
                                         onClick={() => toggleCollapsible(ind)}>
                                        <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                            {item?.CustomerName}
                                        </p>
                                        {item && !(isPayment && !haveDLCN) &&
                                            <img style={{transform: 'initial', marginRight: 6}}
                                            src={getIconByConsentStatus(item.ConsentResult)} alt="icon-status"/>
                                        }
                                        {isActive[ind] ? (<img src={arrowDown} alt="arrow-down"/>) : (
                                            <img src={arrowDown} alt="arrow-left"/>)}
                                    </div>

                                    <div className="collapsible-content">
                                        <div className="optionalform__body">
                                            <div className="tab-wrapper" style={{marginTop: 16}}>
                                                <div className="collapsible-header">
                                                    <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                                        {isPayment && !haveDLCN?'Thông tin liên hệ':'Thông tin người xác nhận'}
                                                    </p>
                                                    {(item.Role === 'LI') && (((item.ConsentResult === "WaitConfirm") || (item.ConsentResult === "Expired")) && !isAgreed && !isDeclined && !(disableEdit && !agentKeyInPOSelfEdit))&&
                                                        <div className="dflex" onClick={() => handlerAdjust()}>
                                                            <p className="edit-text">Điều chỉnh</p>
                                                            <img src={iconArrownRight} alt="arrow-right"/>
                                                        </div>}
                                                </div>
                                                {/* Họ và tên */}
                                                <div className="input disabled">
                                                    <div className="input__content">
                                                        {(item.ReceiverName) && 
                                                        (<label style={{marginLeft: '2px'}}>{isPayment && !haveDLCN?'Tên người liên hệ':'Họ và tên'}</label>)}
                                                            <input type="search" placeholder={isPayment && !haveDLCN?'Tên người liên hệ':'Họ và tên'}
                                                                maxLength="100"
                                                                value={item.ReceiverName}
                                                                disabled
                                                            />
                                                    </div>
                                                    <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                </div>
                                                {/* Căn cước công dân/Thẻ căn cước */}
                                                {!isOlderThan18(item?.CustomerDoB) &&
                                                <div className="tab">
                                                    <div className="input disabled">
                                                        <div className="input__content">
                                                            {(item.ReceiverIDNum || item.CustomerIDNum ) && <label
                                                                style={{marginLeft: '2px'}}>Căn cước công dân/Thẻ căn cước</label>}
                                                                <input type="search" placeholder="Căn cước công dân/Thẻ căn cước"
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
                                                {!isOlderThan18(item?.CustomerDoB) &&
                                                    <div className="tab">
                                                        <div className="input disabled">
                                                            <div className="input__content">
                                                                {item.RelationShip && <label
                                                                    style={{ marginLeft: '2px' }}>{isPayment && !haveDLCN?'Mối quan hệ với Người được bảo hiểm':'Mối quan hệ với Chủ thể dữ liệu'}</label>}
                                                                <input type="search" placeholder={isPayment && !haveDLCN?'Mối quan hệ với Người được bảo hiểm':'Mối quan hệ với Chủ thể dữ liệu'}
                                                                    maxLength="14"
                                                                    disabled
                                                                    value={RELATION_SHIP_MAPPING[item.RelationShip] ? RELATION_SHIP_MAPPING[item.RelationShip] : 'Khác'}
                                                                    onChange={() => {
                                                                    }} />
                                                            </div>
                                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
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
                                                {item.EmailReceiver &&
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
                                                }
                                            </div>

                                            <div className="collapsible-header"
                                                 style={{marginTop: 16, marginBottom: 0}}>
                                                <p className={`collapsible-header-title ${isActive[ind] ? 'bold-text' : ''}`}>
                                                    {(!(isPayment && !haveDLCN) || isWaitConfirm)&&'Xác nhận dữ liệu cá nhân'}</p>
                                                {item && !(isPayment && !haveDLCN) && getStatusButton(item.ConsentResult)}
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
                <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt=""/>
                <img className="decor-person" src={FE_BASE_URL  + "/img/person.png"} alt=""/>
            </div>
            <LoadingIndicator area="submit-init-claim"/>
            {getSession(IS_MOBILE) &&
                    <div className='nd13-padding-bottom160'></div>
            }
            <div className="bottom-btn" style={{flexDirection: 'column', alignItems: 'center'}}>
                <button
                    className={isButtonDisabled ? "btn btn-primary disabled" : "btn btn-primary"}
                    id="submitContactDetail"
                    disabled={isButtonDisabled}
                    onClick={() => checkSubmit()}
                >
                    Hoàn thành
                </button>
                {(systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && (disableEdit && !agentKeyInPOSelfEdit) && !(isPayment && !haveDLCN) && !isAgreed && !isDeclined ?
                        <button className="btn btn-callback" onClick={() => notAgree()}>Hủy/Điều chỉnh thông tin</button>
                        :
                        <button className="btn btn-callback"
                            onClick={() => setIsInsuranceCancel(true)}>Hủy
                        </button>
                }

            </div>
        </div>
        {isInsuranceCancel && <ND13InsuranceRequestCancelConfirm onClickCallBack={() => {
            setIsInsuranceCancel(false);
        }} onClickExtendBtn={() => cancelND13()}/>}


        {/* Popup succeeded redirect */}
        <div className="popup special envelop-confirm-popup" id="popupSucceededRedirect">
            <div className="popup__card">
                <div className="envelop-confirm-card">
                    <div className="envelopcard">
                        <div className="envelop-content">
                            <div className="envelop-content__header"
                                 onClick={closePopupSucceededRedirect}
                            >
                                <i className="closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt=""/></i>
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
    {/* </section> */}
    {showSuccess && <ThanksGeneralPopup closePopup={() => closeSuccess()}
                                           msg={`<p>Quý khách vui lòng thông báo cho Chủ thể dữ liệu xác nhận DLCN. <br/>Hiệu lực xác nhận là ${TimeExpMin} phút</p>`}
                                           />}
    </>
    );
}

export default ND13ContactFollowConfirmation;
