import React, {useCallback, useEffect, useRef, useState} from 'react';
import arrowDown from '../../../img/icon/dropdown-arrow.svg';
import {DatePicker, Select} from 'antd';
import {SearchOutlined} from "@ant-design/icons";
import dayjs from "dayjs";
import moment from "moment";
import './styles.css';
import AlertPopupND13ConfirmPayment from "../../../components/AlertPopupND13ConfirmPayment.js";
// import {CPConsentConfirmation} from "../../../../util/APIUtils";
import {getSession, VALID_EMAIL_REGEX, getDeviceId, setSession, removeSession, removeAccents, removeLocal, deleteND13DataTemp, checkValue, callbackAppOpenLink, haveCheckedDeadth, haveHC_HS, convertDateToISO} from "../../../sdkCommon.js";
import {isEmpty, cloneDeep} from "lodash";
import {DOB, FULL_NAME, POID,FE_BASE_URL, CLIENT_ID, ACCESS_TOKEN, AUTHENTICATION, COMPANY_KEY, WEB_BROWSER_VERSION, USER_LOGIN, ConsentStatus, IS_MOBILE, CELL_PHONE, EMAIL, UPDATE_POLICY_INFO_SAVE_LOCAL, ONLY_PAYMENT, SDK_ROLE_AGENT , SDK_ROLE_PO, CONSENT_RULE_ID_ND13, CONSENT_RULE_ID_CLAIM_PAYMENT } from "../../../sdkConstant.js";// ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, COMPANY_KEY, WEB_BROWSER_VERSION, USER_LOGIN
import LoadingIndicator from '../../../LoadingIndicator2.js';
import {CPConsentConfirmation,iibGetMasterDataByType} from "../../../sdkAPI.js";
import PORejectND13 from "../PORejectND13/PORejectND13.js";

const ND13POContactInfoOver18 = (props) => {
    let isMounted = false;
    const {Option} = Select;
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
    const [acceptPolicy, setAcceptPolicy] = useState(props.acceptPolicy);
    const [isPayment, setIsPayment] = useState(false);
    const [confirmPayment, setConfirmPayment] = useState(false);
    const [relateModalData, setRelateModalData] = useState(props.relateModalData); // props.submissionState.relateModalData
    const [isActive, setIsActive] = useState(props.isActive?props.isActive: {});
    const [haveShowPayment, setHaveShowPayment] = useState(false);
    const [isSubmiting, setIsSubmiting] = useState(false);
    const [isError, setIsError] = useState(true);
    const [relationShipList, setRelationShipList] = useState([]);
    const [isOpenRejectND13, setIsOpenRejectND13] = useState(false);
    const [isOpenCallBack, setIsOpenCallBack] = useState(false);
    const [liOver18NeedAgree, setLiOver18NeedAgree] = useState([]);
    const [liUnder18NeedAgree, setLiUnder18NeedAgree] = useState([]);
    const [clientProfile, setClientProfile] = useState([]);
    const [haveDLCN, setHaveDLCN] = useState(false);

    const setRelateModalDataSync = (rlModalData) => {
        console.log('setRelateModalDataSync=',rlModalData);
        setRelateModalData(rlModalData);
        let submissionState = Object.assign({}, props.submissionState);
        console.log('submissionState..=', submissionState);
        submissionState.relateModalData = rlModalData;
        // props.handlerUpdateMainState("submissionState", submissionState);
    }

    const updateAcceptPolicy=(value)=> {
        setAcceptPolicy(value);
        props.updateAcceptPolicy(value);
    }
    const onClickCallBackND13 = () => {
        let liProfile = (props.clientProfile ?? []).filter(item=> (item.Role === ROLE_LI) && !isOlderThan18(item.CustomerDoB));
        if (liProfile) {
            for (let i = 0; i < liProfile.length; i++) {
                if (relateModalData[liProfile[i]?.CustomerID]?.relationship?.value === "relateFamily") {
                    setRelateModalDataSync({...relateModalData
                        ,[liProfile[i]?.CustomerID]:{

                        }
                    })

                }
            }
        }
        // setRelateModalData({});
        setIsOpenRejectND13(false);
    }
    console.log('xxx=', props.selectedCliInfo);
    const onClickCallRedirectHome = () => {
        if (props.appType === 'CLOSE') {
            removeLocal(UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + props.policyNo);
            setTimeout(() => {
                window.location.href = '/';
            }, 100);
        } else {
            let obj = {
                Action: "CANCEL_ND13_" + props.proccessType,
                ClientID: props.clientId,
                PolicyNo: props.policyNo,
                TrackingID: props.trackingId
            };
            if (props.from && (props.from === "Android")) {//for Android
                if (window.AndroidAppCallback) {
                    window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                }
            } else if (props.from && (props.from === "IOS")) {//for IOS
                if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                    window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                }
            }
        }
        deleteND13DataTemp(props.clientId, props.trackingId, props.apiToken, props.deviceId);
        setIsOpenRejectND13(false);
    }

    const getRelationShips = useCallback(() => {
        let request = {
            Action: "RelationShipConsent", Project: "mcp"
        };
        iibGetMasterDataByType(request)
            .then(Res => {
                let Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile) {
                    setRelationShipList(Response.ClientProfile);
                    if (isEmpty(relateModalData) && isEmpty(props.relateModalData)) {
                        handleDataUpdate();
                    }
                }
            })
            .catch(error => {
                // Xử lý lỗi ở đây nếu cần
            });
    }, []);

    const checkIfNo = useCallback((value) => {
        return value !== 'N';
    }, []);

    const ProcessType = "RIN";
    const ROLE_LI = "LI";

    const RELATIONSHIP = {
        PARENTS: "relateFamily",
        OTHERS: "relateOther"
    }

    const REF_PURPOSE = {
        YES: "Y",
        NO: "N"
    }

    const REF_OTHER_PURPOSE = {
        YES: "Y",
        NO: "N"
    }

    useEffect(()=>{
        let hasErr = !validateInputData();
        console.log('hasErr=', hasErr);
        // if (liUnder18NeedAgree && checkRelationshipParent(relateModalData) && checkRelationshipNotEmpty(relateModalData)) {
        //     hasErr = false;
        // }
        setIsError(hasErr);
        // if (!hasErr) {
        // alert('props=' + JSON.stringify(props.relateModalData));
        // alert('state=' + JSON.stringify(relateModalData));
        // if (!props.disableEdit || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && props.agentKeyInPOSelfEdit)) {
        if (!props.disableEdit) {
            props.updateRelateModalData(relateModalData);
        }
            
        // }
        console.log('aaxxx=', relateModalData);
    },[relateModalData, isError])

    const validateInput = (event, customerId) => {
        const {name, value} = event.target;
        let inValid = false;
        switch (name) {
            case "guardianName":
                setRelateModalData({
                    ...relateModalData,
                    [customerId]:{
                        ...relateModalData[customerId], guardianName: {
                            ...relateModalData[customerId]?.guardianName, error: !value ? "Vui lòng không được để trống" : "",
                        }
                    }
                    
                });
                if (!value) {
                    // if (guardianNameRef.current.scrollIntoView) {
                    //     guardianNameRef.current.scrollIntoView({
                    //         behavior: 'smooth', block: 'start',
                    //     });
                    // }
                    inValid = true;
                }
                break;
            case "phone":
                setRelateModalData({
                    ...relateModalData,
                    [customerId]:{
                        ...relateModalData[customerId], cellPhone: {
                            ...relateModalData[customerId]?.cellPhone,
                            error: !/^0\d{9}$/.test(value) ? "Số điện thoại phải bắt đầu bằng 0 có độ dài 10 chữ số" : "",
                        }

                    }
                    
                });
                if (!/^0\d{9}$/.test(value)) {
                    // if (cellPhoneRef.current.scrollIntoView) {
                    //     cellPhoneRef.current.scrollIntoView({
                    //         behavior: 'smooth', block: 'start',
                    //     });
                    // }
                    inValid = true;
                }
                break;
            case "email":
                setRelateModalData({
                    ...relateModalData,
                    [customerId]:{
                        ...relateModalData[customerId], email: {
                            ...relateModalData[customerId]?.email,
                            error: (value && !VALID_EMAIL_REGEX.test(value)) ? "Email không hợp lệ" : "",
                        }
                    }
                    
                });
                if (value && !VALID_EMAIL_REGEX.test(value)) {
                    // if (emailRef.current.scrollIntoView) {
                    //     emailRef.current.scrollIntoView({
                    //         behavior: 'smooth', block: 'start',
                    //     });
                    // }
                    inValid = true;
                }
                break;
            case "identityCard":
                setRelateModalData({
                    ...relateModalData,
                    [customerId]:{
                        ...relateModalData[customerId], identityCard: {
                            ...relateModalData[customerId]?.identityCard,
                            value: value,
                            error: (!value || (value.length > 14)) ? "Căn cước công dân/Thẻ căn cước không được quá 14 chữ số" : "",
                        }
                    }
                    ,
                });
                if (!value || (value.length > 14)) {
                    // if (identityCardRef.current.scrollIntoView) {
                    //     identityCardRef.current.scrollIntoView({
                    //         behavior: 'smooth', block: 'start',
                    //     });
                    // }
                    inValid = true;
                }
                break;
            default:
                break;
        }
        // if (inValid) {
        //     setIsError(true);
        // } else {
        //     setIsError(false);
        // }
    };

    const disabledDate = (current) => {
        return current && (current > dayjs().endOf('day'));
    }

    const toggleCollapsible = (customerId) => {
        setIsActive({...isActive, [customerId]: !isActive[customerId]});
        props.updateIsActive({[customerId]: !isActive[customerId]});
    };

    const closeConfirmPayment = () => {
        setConfirmPayment(false);
    }
    console.log(props.clientProfile)
    const mapDataLIReceiverPO = (data) => {
        const relateModal = relateModalData[data?.CustomerID]
        const needPOConfirm = relateModal?.relationship?.value === RELATIONSHIP.PARENTS && relateModal?.isUnderEighteen;
        const valueDepenOnPO = (defaultValue, value) => needPOConfirm ? defaultValue : value??defaultValue;

        return {
            ...data,
            RelationShip: relateModal?.relationCode?.value ?? (relateModal?.relationship?.value === RELATIONSHIP.PARENTS?'Parent':ROLE_LI),
            ReceiverName: valueDepenOnPO(getSession(FULL_NAME), relateModal?.guardianName?.value),
            ReceiverDoB: valueDepenOnPO(getSession(DOB), checkValue(relateModal?.dateOfBirth?.value ? moment(relateModal?.dateOfBirth?.value).format('YYYY-MM-DDT00:00:00.000[Z]') : "")),
            ReceiverIDNum: valueDepenOnPO(getSession(POID), relateModal?.identityCard?.value),
            EmailReceiver: valueDepenOnPO(getSession(EMAIL),relateModal?.email?.value),
            PhoneReceiver: valueDepenOnPO(getSession(CELL_PHONE), relateModal?.cellPhone?.value),
            ZaloIDReceiver: "",
            RequesterID: props.clientId,
            isUnderEighteen: relateModal?.isUnderEighteen,
            relationCode: relateModal?.relationCode?.value,
            relation: relateModal?.relation?.value,
            refPurpose: relateModal?.refPurpose?.value ?? '',
            refOtherPurpose: relateModal?.refOtherPurpose?.value ?? '',
            needPOConfirm,
            AnswerPurpose: ((relateModal?.refPurpose?.value ?? '' + relateModal?.refOtherPurpose?.value ?? ''), data?.AnswerPurpose)
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
                ClientList: props.clientIdString,
                ProcessType: props.proccessType,
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
                    setClientProfile(clientProfile);
                    console.log('aaJons=', JSON.stringify(clientProfile));
                    const configClientProfile = Response.Config;
                    const consentResultPO = generateConsentResults(clientProfile)?.ConsentResultPO;
                    const consentResulLI = generateConsentResults(clientProfile)?.ConsentResultLI;
                    const isOpenPopupWarning = ( (consentResultPO !== ConsentStatus.AGREED) || (haveLIStillNotAgree(clientProfile)));
                    const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
                    let copyClientProfile = cloneDeep(clientProfile);
                    let paymentProfile =  !isEmpty(copyClientProfile)?copyClientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
                    if ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && ((props.poConfirmingND13 === '1') || (!haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList) && ((props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(props.selectedCliInfo?.dOB)) || (!isOlderThan18(props.selectedCliInfo?.dOB) && !haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList))))) {
                        paymentProfile = !isEmpty(copyClientProfile)?copyClientProfile.find(item => item.ConsentRuleID === 'ND13'):[];
                        paymentProfile.ConsentRuleID = 'CLAIM_PAYMENT';
                    }
                    if (!isOpenPopupWarning && (IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                        setHaveShowPayment(true);
                        // props.callBackOnlyPayment(true);
                        if (props.proccessType === 'Claim') {
                            setSession(ONLY_PAYMENT + props.selectedCliInfo.clientId, ONLY_PAYMENT + props.selectedCliInfo.clientId);
                        }
                    } else {
                        setHaveShowPayment(false);
                        // props.callBackOnlyPayment(false);
                        if (props.proccessType === 'Claim') {
                            if (getSession(ONLY_PAYMENT + props.selectedCliInfo.clientId)) {
                                removeSession(ONLY_PAYMENT + props.selectedCliInfo.clientId);
                            }
                        }
                    }
                    // setIsSubmiting(true);
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
            } 
            else if (role === 'LI') {
                // key = `ConsentResultLI_${index + 1}`;
                key = 'ConsentResultLI';
            }
            if (item.ConsentRuleID === CONSENT_RULE_ID_ND13) {
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
            if ((role === 'LI') && (item.ConsentRuleID === CONSENT_RULE_ID_ND13) && (item.ConsentResult !== ConsentStatus.AGREED)) {
                console.log('haveLIStillNotAgree true');
                result = true;
                return result;
            }
            
        });
        console.log('haveLIStillNotAgree =', result);
        return result;
    }

    const handleClickContinue = () => {
        if (isError) {
            return;
        }
        if (props.proccessType === 'Claim') {
            handleClickContinueClaim();
        } else {
            const { clientProfile, totalInvoiceAmount, configClientProfile } = props;
            let liReceiverPO = (clientProfile ?? []).filter(item => item.Role === ROLE_LI && item.ConsentRuleID === CONSENT_RULE_ID_ND13).map(mapDataLIReceiverPO);
            console.log(liReceiverPO);
            onClickConfirmBtn(liReceiverPO);
        }
    };

    const mapDataLIReceiverParent = (data) => {
        return {
            ...data,
            Role: 'LI',
            AnswerPurpose: (data.ConsentRuleID === 'CLAIM_PAYMENT')?'Y': 'YY',
            RelationShip: "FATHR/MOTHR/GRDCS",
            ReceiverName: getSession(FULL_NAME),
            ReceiverDoB: convertDateToISO(getSession(DOB)),
            ReceiverIDNum: "",
            EmailReceiver: getSession(EMAIL),
            PhoneReceiver: getSession(CELL_PHONE),
            ZaloIDReceiver: ""
        };
    }

    const handleClickContinueClaim = () => {
        const {clientProfile, totalInvoiceAmount, configClientProfile } = props;
        console.log('handleClickContinueClaim clientProfile=', JSON.stringify(clientProfile));
        let copyClientProfile = cloneDeep(clientProfile);
        let copyClientProfile2 = cloneDeep(clientProfile);
        let liReceiverPO = [];
        const liND13 = !isEmpty(copyClientProfile2)
            ? copyClientProfile2.find(item => (item.Role === 'LI') && (item.ConsentRuleID === CONSENT_RULE_ID_ND13) && (item?.ConsentResult !== ConsentStatus.AGREED))
            : [];
        let type = '';
        if (checkRelationshipParent(relateModalData) && checkRelationshipNotEmpty(relateModalData)) {
            type = "Parent";
        }
        if (liND13 && !isEmpty(liND13)) {
            if (type === "Parent") {
                liReceiverPO.push(mapDataLIReceiverParent(liND13));
            } else {
                liReceiverPO.push(mapDataLIReceiverPO(liND13));
            }
            
        }


        let liPayment = !isEmpty(copyClientProfile)
            ? copyClientProfile.find(item => (item.Role === 'LI') && (item.ConsentRuleID === 'CLAIM_PAYMENT'))
            : [];
        if (((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && props.disableEdit) ) && ((!haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList) && ((props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(props.selectedCliInfo?.dOB)) || (!isOlderThan18(props.selectedCliInfo?.dOB) && !haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList))))) {
            liPayment = !isEmpty(copyClientProfile)
                ? copyClientProfile.find(item => (item.Role === 'LI') && ((item.ConsentRuleID === CONSENT_RULE_ID_ND13) || (item.ConsentRuleID === 'CLAIM_PAYMENT')))
                : [];
            if (!isEmpty(liPayment)) {
                liPayment.ConsentRuleID = 'CLAIM_PAYMENT';
            }
        }
        if (liPayment && !isEmpty(liPayment)) {
            if (type === "Parent") {
                liReceiverPO.push(mapDataLIReceiverParent(liPayment));
            } else {
                liReceiverPO.push(mapDataLIReceiverPO(liPayment));
            }
        }
        
        if (isPayment || haveShowPayment || (checkRelationshipParent(relateModalData) && checkRelationshipNotEmpty(relateModalData))) {
            onClickConfirmBtn(liReceiverPO, type);
        } else {
            const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
            let copyClientProfile3 = cloneDeep(clientProfile);
            let paymentProfile =  !isEmpty(copyClientProfile3)?copyClientProfile3.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
            if (((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && props.disableEdit)) && ((!haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList) && ((props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(props.selectedCliInfo?.dOB)) || (!isOlderThan18(props.selectedCliInfo?.dOB) && !haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList))))) {
                paymentProfile =  !isEmpty(copyClientProfile3)?copyClientProfile3.find(item => (item.ConsentRuleID === 'ND13') && item.Role === 'LI'):[];
                paymentProfile.ConsentRuleID = 'CLAIM_PAYMENT';
            }
            console.log('IsEnablePayment=' + IsEnablePayment);
            if ((IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                setIsPayment(true);
                if (props.systemGroupPermission?.[0]?.Role !== SDK_ROLE_AGENT) {
                    setConfirmPayment(true);
                }
            } else {
                setIsPayment(false);
                onClickConfirmBtn(liReceiverPO, type);
            }
        }
    };

    useEffect(()=>{
        console.log('useEffect 222');
        isMounted = true;
        let liProfile = (props.clientProfile ?? []).filter(item=> item.Role === ROLE_LI)
        console.log('----------------');
        console.log(relateModalData);
        if (liProfile) {
            for (let i = 0; i < liProfile.length; i++) {
                const refPurpose = relateModalData[liProfile[i]?.CustomerID]?.refPurpose;
                const refOtherPurpose = relateModalData[liProfile[i]?.CustomerID]?.refOtherPurpose;
                if (refPurpose || refOtherPurpose) {
                    const isRefPurposeValid = checkIfNo(refPurpose?.value);
                    const isRefOtherPurposeValid = checkIfNo(refOtherPurpose?.value);
                    if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
                        if (isMounted) {
                            setIsOpenRejectND13(true);
                        }
                        return;
                    }
                }

            }
        }

        if(isEmpty(relationShipList)){
            getRelationShips();
        }
        return () => { isMounted = false };
    },[getRelationShips, relateModalData])

    const handleDataUpdate = useCallback(() => {
        if (!isEmpty(props.clientProfile)) {
            props.clientProfile.filter(item => item && item.Role === ROLE_LI).forEach(item =>{
                if (item && isOlderThan18(item.CustomerDoB)) {
                    const newData = {
                        guardianName: {
                            value: item?.CustomerName || '', // Lấy họ và tên
                            error: '',
                        }, dateOfBirth: {
                            value: item?.CustomerDoB, // Lấy ngày sinh
                            error: '',
                        }, cellPhone: {
                            value: item?.PhoneReceiver || props.selectedCliInfo.CellPhone || props.selectedCliInfo.cellPhone || '', // Lấy số điện thoại
                            error: '',
                        }, email: {
                            value: item?.EmailReceiver || props.selectedCliInfo.Email || props.selectedCliInfo.email || '', // Lấy email
                            error: '',
                        }, identityCard: {
                            value: item?.ReceiverIDNum || '', // Lấy số chứng minh nhân dân
                            error: '',
                        }, isUnderEighteen: !isOlderThan18(item.CustomerDoB)
                    };
                    setRelateModalData(prevData => ({
                        ...prevData, [item?.CustomerID]: newData
                    }));
                } else {
                    const newData = {
                        guardianName: {
                            value: '', // Lấy họ và tên
                            error: '',
                        }, dateOfBirth: {
                            value: '', // Lấy ngày sinh
                            error: '',
                        }, cellPhone: {
                            value: '', // Lấy số điện thoại
                            error: '',
                        }, email: {
                            value: '', // Lấy email
                            error: '',
                        }, identityCard: {
                            value: '', // Lấy số chứng minh nhân dân
                            error: '',
                        }, isUnderEighteen: !isOlderThan18(item.CustomerDoB)
                    };
                    setRelateModalData(prevData => ({
                        ...prevData, [item?.CustomerID]: newData
                    }));
                }

            });



            
            
        }
    }, [props.clientProfile, props.contactInfo, setRelateModalData]);

    useEffect(() => {
        const paymentProfile =  !isEmpty(props.clientProfile)?props.clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
        let showPayment = !(props.poConfirmingND13 === '1') && !isEmpty(paymentProfile);
        //props.setHaveShowPayment(showPayment);
        if (!isEmpty(props.relateModalData)) {
            setRelateModalData(props.relateModalData);
        } else if (isEmpty(relateModalData)) {
            handleDataUpdate();
        }
        // fetchCPConsentConfirmationRefresh();
        let liOver18NeedAgree =(props.clientProfile ?? []).filter(item=> (item?.Role === ROLE_LI) && (item?.ConsentRuleID === CONSENT_RULE_ID_ND13) && (item?.ConsentResult !== ConsentStatus.AGREED) && isOlderThan18(item?.CustomerDoB));
        let liUnder18NeedAgree =(props.clientProfile ?? []).filter(item=> (item?.Role === ROLE_LI) && (item?.ConsentRuleID === CONSENT_RULE_ID_ND13) && (item?.ConsentResult !== ConsentStatus.AGREED) && !isOlderThan18(item?.CustomerDoB));
        console.log('props.clientProfile=', props.clientProfile);
        console.log('liOver18NeedAgree=', liOver18NeedAgree);
        console.log('liUnder18NeedAgree=', liUnder18NeedAgree);
        // let liOver18NeedAgree =(props.clientProfile ?? []).filter(item=> (item?.Role === ROLE_LI) && isOlderThan18(item?.CustomerDoB));
        // let liUnder18NeedAgree =(props.clientProfile ?? []).filter(item=> (item?.Role === ROLE_LI) && !isOlderThan18(item?.CustomerDoB));
        if (!isEmpty(liOver18NeedAgree) || !isEmpty(liUnder18NeedAgree)) {
            setHaveDLCN(true);
        } else {
            setHaveDLCN(false);
        }
        const { configClientProfile } = props;
        const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
        if (isEmpty(liOver18NeedAgree) && isEmpty(liUnder18NeedAgree) && (props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && !props.disableEdit) {
            liOver18NeedAgree = (props.clientProfile ?? []).filter(item => (item?.Role === ROLE_LI)  && (item?.ConsentRuleID === CONSENT_RULE_ID_CLAIM_PAYMENT) && (item?.ConsentResult !== ConsentStatus.AGREED) && isOlderThan18(item?.CustomerDoB));
            liUnder18NeedAgree = (props.clientProfile ?? []).filter(item => (item?.Role === ROLE_LI)  && (item?.ConsentRuleID === CONSENT_RULE_ID_CLAIM_PAYMENT) && (item?.ConsentResult !== ConsentStatus.AGREED) && !isOlderThan18(item?.CustomerDoB));
            if (!isEmpty(liOver18NeedAgree) || !isEmpty(liUnder18NeedAgree)) {
                setIsPayment(true);
                // setConfirmPayment(true);
                setHaveShowPayment(true);
                showPayment = true;
            }
        } else if ((IsEnablePayment === '1') && isEmpty(liOver18NeedAgree) && isEmpty(liUnder18NeedAgree) && ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) || (props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && props.disableEdit) && ((!haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList) && ((props.paymentMethodState.choseReceiver === 'PO') && isOlderThan18(props.selectedCliInfo?.dOB)) || (!isOlderThan18(props.selectedCliInfo?.dOB) && !haveCheckedDeadth(props.claimCheckedMap) && haveHC_HS(props.claimCheckedMap, props.claimTypeList))))) {
            // const clientProfile = cloneDeep(props.clientProfile);
            liOver18NeedAgree = (props.clientProfile ?? []).filter(item => (item?.Role === ROLE_LI)  && (item?.ConsentRuleID === CONSENT_RULE_ID_ND13) && isOlderThan18(item?.CustomerDoB));
            liUnder18NeedAgree = (props.clientProfile ?? []).filter(item => (item?.Role === ROLE_LI)  && (item?.ConsentRuleID === CONSENT_RULE_ID_ND13) && !isOlderThan18(item?.CustomerDoB));
            if (!isEmpty(liOver18NeedAgree) || !isEmpty(liUnder18NeedAgree)) {
                setIsPayment(true);
                // setConfirmPayment(true);
                setHaveShowPayment(true);
                showPayment = true;
            }
        }
        console.log('liOver18NeedAgree2=', liOver18NeedAgree);
        console.log('liUnder18NeedAgree2=', liUnder18NeedAgree);
        console.log('haveDLCN=', haveDLCN);
        setLiOver18NeedAgree(liOver18NeedAgree);
        setLiUnder18NeedAgree(liUnder18NeedAgree);
        if (!isEmpty(liOver18NeedAgree)) {
            setIsActive({ ...isActive, [liOver18NeedAgree?.[0]?.CustomerID]: true });
            props.updateIsActive({ [liOver18NeedAgree?.[0]?.CustomerID]: true });
        } else if (!isEmpty(liUnder18NeedAgree)) {
            setIsActive({ ...isActive, [liUnder18NeedAgree?.[0]?.CustomerID]: true });
            props.updateIsActive({ [liUnder18NeedAgree?.[0]?.CustomerID]: true });
        }

        if (document.getElementById('scrollAnchor')) {
            document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' });
        }
    }, [props.clientProfile]);

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

    const isOlderThan18 = (dob) => {
        const birthDate = new Date(dob);
        const currentDate = new Date();

        // Calculate the age
        let age = currentDate.getFullYear() - birthDate.getFullYear();
        const monthDiff = currentDate.getMonth() - birthDate.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < birthDate.getDate())) {
            age--;
        }
        return age >= 18;
    }

    const handleChangeRelationshipValue = (e, customerID) => {
        const value = e?.target?.defaultValue ?? '';
        setRelateModalData({...relateModalData,
            [customerID] : {
                ...relateModalData[customerID],
                relationship: {
                    value, error: ''
                }
            }
        })
    }

    const handleRadioPurpose = (e, customerID) =>{
        const value = e?.target.defaultValue ?? '';
        const tempName = e?.target?.name;
        const name = tempName.split("_")[0];
        setRelateModalData({...relateModalData,
            [customerID] : {
                ...relateModalData[customerID],
                [name]: {
                    value, error: ''
                }
            }
        })

    }

    const onChangeRelation = (value, CustomerID) => {

        if (requiredFields.includes('relation')) {
            const updatedMissingFields = missingFields.filter(field => field !== 'relation');
            setMissingFields(updatedMissingFields);
        }

        setRelateModalData(prevState => ({
            ...prevState, 
            [CustomerID]:{
                ...prevState[CustomerID],
                relation: {
                error: '', value: value,
                },
            }
            
        }));

        const relationCode = relationShipList.find(result => result.RelationName === value);

        if (relationCode) {
            setRelateModalData(prevState => ({
                ...prevState, 
                [CustomerID]:{
                    ...prevState[CustomerID],
                    relationCode: {
                        ...prevState.relationCode, value: relationCode.RelationCode,
                    },
                }
                
            }));
        }
    };

    const checkRadioChecked = (customerID, fieldName, value) =>{
        const customerData = relateModalData[customerID];
        const fieldData = (customerData ?? {})[fieldName];
        const currentValue = fieldData?.value;

        console.log(customerID, fieldName, value, currentValue);
        return currentValue === value;
    }

    const validateInputData = () => {
        let liOver18Valid = true;
        let liUnder18Valid = true;
        if (liOver18NeedAgree && liOver18NeedAgree.length > 0) {
            liOver18Valid = false;
            for (let i = 0; i < liOver18NeedAgree.length; i++) {
                let anyError = false;
                anyError = validateAnyError('phone', liOver18NeedAgree[i]?.CustomerID);
                if (anyError) {
                    return false;
                }
                anyError = validateAnyError('email', liOver18NeedAgree[i]?.CustomerID);
                if (anyError) {
                    return false;
                }
                liOver18Valid = true;
            }
        }
        if (!liOver18Valid) {
            return false;
        }
        if (liUnder18NeedAgree && (liUnder18NeedAgree.length > 0)) {
            // if (checkRelationshipParent(relateModalData)) {
            //     return true;
            // }
            for (let i = 0; i < liUnder18NeedAgree.length; i++) {
                liUnder18Valid = false;
                let anyError = false;
                if (!relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.relationship?.value) {
                    return false;
                }
                if ((relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.relationship?.value === RELATIONSHIP.PARENTS) && !haveDLCN) {
                    return true;
                }
                if (relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.relationship?.value === RELATIONSHIP.PARENTS) {
                    if (!relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refOtherPurpose?.value || (relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refOtherPurpose?.value === undefined) || !relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refPurpose?.value || (relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refPurpose?.value === undefined)) {
                        return false;
                    }
                    if ((relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refOtherPurpose?.value !== 'Y') || (relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.refPurpose?.value !== 'Y')) {
                        return false;
                    }
                }
                if (relateModalData[liUnder18NeedAgree[i]?.CustomerID]?.relationship?.value === RELATIONSHIP.OTHERS) {
                    anyError = validateAnyError('guardianName', liUnder18NeedAgree[i]?.CustomerID);
                    if (anyError) {
                        return false;
                    }
                    anyError = validateAnyError('relation', liUnder18NeedAgree[i]?.CustomerID);
                    if (anyError) {
                        return false;
                    }
                    anyError = validateAnyError('identityCard', liUnder18NeedAgree[i]?.CustomerID);
                    console.log('anyError=', anyError);
                    if (anyError) {
                        return false;
                    }
                    anyError = validateAnyError('dateOfBirth', liUnder18NeedAgree[i]?.CustomerID);
                    if (anyError) {
                        return false;
                    }
                    anyError = validateAnyError('phone', liUnder18NeedAgree[i]?.CustomerID);
                    if (anyError) {
                        return false;
                    }
                    anyError = validateAnyError('email', liUnder18NeedAgree[i]?.CustomerID);
                    if (anyError) {
                        return false;
                    }
                }

                liUnder18Valid = true;
            }
        }
        if (!liUnder18Valid) {
            return false;
        }
        return true;
    }

    const validateAnyError = (name, customerId) => {
        let inValid = false;
        switch (name) {
            case "guardianName":
                if (!relateModalData[customerId]?.guardianName?.value) {
                    inValid = true;
                }
                break;
            case "phone":
                if (!/^0\d{9}$/.test(relateModalData[customerId]?.cellPhone?.value)) {
                    inValid = true;
                }
                break;
            case "email":
                if (relateModalData[customerId]?.email?.value && !VALID_EMAIL_REGEX.test(relateModalData[customerId]?.email?.value)) {
                    inValid = true;
                }
                break;
            case "identityCard":
                if (!relateModalData[customerId]?.identityCard?.value || (relateModalData[customerId]?.identityCard?.value.length > 14)) {
                    console.log('!relateModalData[customerId]?.identityCard?.value=', !relateModalData[customerId]?.identityCard?.value);
                    console.log('(relateModalData[customerId]?.identityCard?.value.length > 14)=', (relateModalData[customerId]?.identityCard?.value.length > 14));
                    inValid = true;
                }
                break;
            case "relation":
                if (!relateModalData[customerId]?.relation?.value) {
                    console.log('relation=', relateModalData[customerId]?.relation?.value);
                    inValid = true;
                }
                break;
            case "dateOfBirth":
                if (!relateModalData[customerId]?.dateOfBirth?.value) {
                    inValid = true;
                }
                break;
            default:
                break;
        }
        return inValid;
    };

    const openLink=(link) => {
        if (getSession(IS_MOBILE)) {
            callbackAppOpenLink(link, props.from)
        } else {
            window.open(link, '_blank');
        }
    }

    const checkRelationshipParent=(obj) => {
        for (const key in obj) {
            if (obj[key].relationship && obj[key].relationship.value === "relateFamily") {
                return true;
            }
        }
        return false;
    }

    const checkRelationshipNotEmpty=(obj) => {
        for (const key in obj) {
            if (obj[key].relationship && obj[key].relationship.value) {
                return true;
            }
        }
        return false;
    }

    return (!isSubmiting?(
    <>
        {/* <section className="sccontract-warpper-sdk nd13" style={{overflowX: 'none', marginTop: '16px'}} id="scrollAnchor"> */}
            {/* <div className="insurance"> */}
                <div className={getSession(IS_MOBILE)?"stepform margin-top170":"stepform"}>
                    <div className="info">
                        <div className="info__body">
                            <LoadingIndicator area="submit-init-claim"/>
                            <div className="optionalform-isActivewrapper">
                                <div className="optionalform">
                                    <div className="relate-modal-content">
                                        {(isPayment || haveShowPayment) ?
                                        (!isEmpty(liOver18NeedAgree)?(
                                            <p className="modal-body-content">Quyền lợi Bảo hiểm Hỗ trợ viện phí/Chăm sóc sức khỏe, nếu được chấp thuận, sẽ được chi trả cho NĐBH từ 18 tuổi trở lên.
                                                </p>
                                        ):(
                                            <p className="modal-body-content">Quyền lợi Bảo hiểm Hỗ trợ viện phí/Chăm
                                                sóc sức khỏe của NĐBH dưới 18 tuổi, nếu được chấp thuận, sẽ được chi trả cho Cha/Mẹ/Người giám hộ của
                                                trẻ.</p>
                                        )):(
                                            <p className="modal-body-content">Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày
                                                17/04/2023 về
                                                bảo vệ DLCN, Chủ thể dữ liệu cần xác nhận đồng ý cho phép DLVN xử lý
                                                DLCN.</p>
                                        )
                                        }
                                        <div className="card-border"></div>
                                        {/*----------------------------------------LI > 18T: PO khai báo thông tin liên hệ của LI------------------------------------------------------*/}
                                        {/* {!isEmpty(liOver18NeedAgree) && haveShowPayment &&
                                            <p className="modal-body-sub-content">Quý khách sẽ được thông báo đến Chủ thể dữ liệu, Quý khách vui lòng cung cấp thông tin liên hệ của CTDL để nhận thông tin.</p> 
                                        } */}
                                        {!isEmpty(liOver18NeedAgree) && isPayment &&
                                            <p className="modal-body-sub-content">{haveDLCN?'Yêu cầu của Quý khách sẽ được thông báo đến Người được bảo hiểm, theo thông tin đã cung cấp bên dưới:':'Yêu cầu của Quý khách sẽ được thông báo đến Người được bảo hiểm, Quý khách vui lòng cung cấp thông tin liên hệ của NĐBH để nhận thông tin.'}</p> 
                                        }



                                         
                                        {!isEmpty(liOver18NeedAgree) && !isPayment && !haveShowPayment &&
                                            <p className="modal-body-sub-content">Quý khách vui lòng cung cấp thông tin liên
                                                hệ của
                                                (những)
                                                Người sau đây để nhận tin và xác nhận cho phép DLVN xử lý DLCN.</p>}
                                        {!isEmpty(liOver18NeedAgree) && (((!isPayment || haveShowPayment) && !haveDLCN) || (haveDLCN && !isPayment)) &&
                                        <p className="modal-body-sub-content-frame">
                                            Nếu chưa có số điện thoại/hộp thư điện tử liên hệ của {!haveDLCN?'Người được bảo hiểm':'Chủ thể dữ liệu'},
                                            Quý khách vui lòng hủy Yêu cầu trực tuyến này để lập Phiếu yêu cầu bằng giấy
                                            và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.
                                        </p>
                                        }
                                        {
                                            (liOver18NeedAgree ?? []).map((li, index)=>(
                                                <div className={`collapsible ${isActive[li?.CustomerID] || (isPayment || !haveDLCN) ? 'active' : ''}`}>
                                                <div className="collapsible-header" onClick={()=> toggleCollapsible(li?.CustomerID)}>
                                                    <p className={`modal-body-sub-content ${isActive[li?.CustomerID]  || (isPayment || !haveDLCN)? 'bold-text' : ''}`}>{li?.CustomerName}</p>
                                                    {isActive[li?.CustomerID] ? <img src={arrowDown} alt="arrow-down"/> :
                                                        <img src={arrowDown} alt="arrow-left"/>}
                                                </div>
                                                <div className="collapsible-content">
                                                {/* start older than 18 */}

                                                    <>
                                                    <div
                                                        className={`${relateModalData[li?.CustomerID]?.dateOfBirth?.error ? 'validate mb6' : ''} input mt12 disabled`}>
                                                        <div className="input__content" style={{width: '100%'}}>
                                                            <label>Ngày tháng năm sinh</label>
                                                            <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                        id="relateDateOfBirth" disabledDate={disabledDate}
                                                                        value={relateModalData[li?.CustomerID]?.dateOfBirth?.value ? moment(relateModalData[li?.CustomerID]?.dateOfBirth?.value) : ""}
                                                                        onChange={(value) => {
                                                                            if (requiredFields.includes('dateOfBirth')) {
                                                                                const updatedMissingFields = missingFields.filter(field => field !== 'dateOfBirth');
                                                                                setMissingFields(updatedMissingFields);
                                                                            }
                                                                            setRelateModalDataSync({
                                                                                ...relateModalData
                                                                                , [li?.CustomerID]: {...relateModalData[li?.CustomerID]
                                                                                    ,dateOfBirth: {
                                                                                        ...relateModalData[li?.CustomerID].dateOfBirth, value,
                                                                                    }
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
                                                        className={`${relateModalData[li?.CustomerID]?.cellPhone?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>{isPayment? 'Số điện thoại Người được bảo hiểm': 'Số điện thoại Chủ thể dữ liệu'}</label>
                                                            <input value={relateModalData[li?.CustomerID]?.cellPhone?.value ? relateModalData[li?.CustomerID]?.cellPhone?.value.trim():''} name="phone"
                                                                id="relateCellPhone"
                                                                onChange={(e) => {
                                                                    if (requiredFields.includes('cellPhone')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'cellPhone');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({...relateModalData
                                                                        ,[li?.CustomerID]:{
                                                                            ...relateModalData[li?.CustomerID], cellPhone: {
                                                                                error: '', value: e.target.value,
                                                                            }
                                                                        }
                                                                        
                                                                    })
    
                                                                }}
                                                                maxlength="10"
                                                                disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                onTouchEnd={(event) => validateInput(event, li?.CustomerID)}
                                                                inputMode='numeric'
                                                                pattern="[0-9]*"
                                                                type="tel"/>
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/input_phone.svg"}
                                                                alt=""/></i>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.cellPhone?.error && <span
                                                        style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.cellPhone?.error}</span>}
                                                    <div
                                                        ref={emailRef}
                                                        className={`${relateModalData[li?.CustomerID]?.email?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>{isPayment? 'Email Người được bảo hiểm':'Email Chủ thể dữ liệu'}</label>
                                                            <input value={relateModalData[li?.CustomerID]?.email?.value?relateModalData[li?.CustomerID]?.email?.value.trim():''} name="email"
                                                                id="relateEmail"
                                                                disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                onChange={(e) => {
                                                                    setRelateModalDataSync({...relateModalData,
                                                                        [li?.CustomerID]:{
                                                                            ...relateModalData[li?.CustomerID], email: {
                                                                                error: '', value: e.target.value,
                                                                            }
                                                                        }
                                                                        
                                                                    });
                                                                }}
                                                                onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                type="search"/>
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/input_mail.svg"} alt=""
                                                        /></i>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.email?.error &&
                                                        <span style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.email?.error}</span>}
                                                    </>

                                                {/* end older than 18 */}
                                                {/* start under 18 */}


                                                {/* end under 18 */}

                                                    
                                                    {(isPayment || haveShowPayment) && 
                                                        <p style={{fontSize: '15px', fontWeight: '500', paddingTop: '16px'}}>Quý khách đã chọn người nhận tiền là Bên mua bảo hiểm, vì vậy, Quý khách vui lòng xác nhận và cam kết:</p>
                                                    }
                                                    {(isPayment || haveShowPayment) && 
                                                    <div
                                                        className={props.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                        style={{
                                                            'maxWidth': '600px', display: 'flex'
                                                        }}>
                                                        {(props.disableEdit && !props.agentKeyInPOSelfEdit) ? (
                                                            <div
                                                                className={props.acceptPolicy ? "square-choose-readonly fill-grey" : "square-choose"}
                                                                style={{
                                                                    flex: '0 0 auto', height: '20px', cursor: 'pointer', margin: 0
                                                                }}
                                                                >
                                                                <div className="checkmark">
                                                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                                                </div>
                                                            </div>
                                                        ) : (
                                                            <div
                                                                className={props.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                                style={{
                                                                    flex: '0 0 auto', height: '20px', cursor: 'pointer', margin: 0
                                                                }}
                                                                onClick={() => updateAcceptPolicy(!acceptPolicy)}>
                                                                <div className="checkmark">
                                                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                                                </div>
                                                            </div>
                                                        )
                                                        }
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
                                            ))

                                        }
                                        {!isEmpty(liUnder18NeedAgree) &&
                                            <>
                                                {!isEmpty(liOver18NeedAgree) ? (
                                                    <img style={{ marginLeft: '-16px', minWidth: 'calc(100% + 32px)' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                                                ):(
                                                    <div></div>
                                                )
                                                }
                                                {
                                                (isPayment || haveShowPayment) ?
                                                <p className="modal-body-sub-content">
                                                    {haveDLCN?'Thông tin về khoản thanh toán sẽ được thông báo đến Cha/Mẹ/Người giám hộ của NĐBH theo thông tin đã được cung cấp bên dưới:':'Quý khách vui lòng xác nhận mối quan hệ của BMBH với NĐBH:'}
                                                </p>
                                                : <>
                                                    <p className="modal-body-sub-content">Nếu trẻ dưới 18 tuổi, việc
                                                        xử
                                                        lý DLCN cần sự
                                                        đồng ý của trẻ (từ 7 tuổi) và Cha/Mẹ/Người giám hộ của
                                                        trẻ.</p>
                                                    <p className="modal-body-sub-content">Quý khách vui lòng xác nhận
                                                        mối
                                                        quan hệ của
                                                        BMBH với những người sau:</p>
                                                </>
                                                }
                                            </>
                                        }
                                        {
                                            (liUnder18NeedAgree ?? []).map((li, index)=>(
                                                <div className={`collapsible ${(isActive[li?.CustomerID]) || (isPayment || !haveDLCN)? 'active' : ''}`}>
                                                {((isPayment && !haveDLCN) || haveDLCN) &&
                                                <div className="collapsible-header" onClick={()=> toggleCollapsible(li?.CustomerID)}>
                                                    <p className={`modal-body-sub-content ${isActive[li?.CustomerID] || (isPayment || !haveDLCN) ? 'bold-text' : ''}`}>{li?.CustomerName}</p>
                                                    {isActive[li?.CustomerID] ? <img src={arrowDown} alt="arrow-down"/> :
                                                        <img src={arrowDown} alt="arrow-left"/>}
                                                </div>
                                                }
                                                <div className="collapsible-content">
                                                {/* start under 18 */}

                                                    <>
                                                     {((isPayment && !haveDLCN) || haveDLCN) &&
                                                    <div className="checkbox-warpper">
                                                        <div className="checkbox-item">
                                                            <div className="round-checkbox">
                                                                <label className="customradio">
                                                                    <input
                                                                        type="radio"
                                                                        value={RELATIONSHIP.PARENTS}
                                                                        checked={relateModalData[li?.CustomerID]?.relationship?.value === RELATIONSHIP.PARENTS}
                                                                        onChange={(e)=> handleChangeRelationshipValue(e, li?.CustomerID)}
                                                                        disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))}
                                                                    />
                                                                    <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                    <p className="text" style={{fontSize:'15px'}}>Cha/Mẹ/Người giám hộ</p>
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div className="checkbox-item">
                                                            <div className="round-checkbox">
                                                                <label className="customradio">
                                                                    <input
                                                                        type="radio"
                                                                        value={RELATIONSHIP.OTHERS}
                                                                        checked={relateModalData[li?.CustomerID]?.relationship?.value === RELATIONSHIP.OTHERS}
                                                                        onChange={(e)=> handleChangeRelationshipValue(e, li?.CustomerID)}
                                                                        disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))}
                                                                    />
                                                                    <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                    <p className="text" style={{fontSize:'15px'}}>Khác</p>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    }

                                                    {((!isPayment && !haveShowPayment) &&  relateModalData[li?.CustomerID]?.relationship?.value === RELATIONSHIP.PARENTS) &&
                                                    <div className="body-frame">
                                                        <p className="body-frame-title" style={{
                                                            fontSize: 14, lineHeight: '21px', color: '#292929'
                                                        }}>Tôi/Chúng tôi, <span style={{fontSize: 14}}
                                                                                className="bold-text">{getSession(FULL_NAME)}</span> là
                                                            Cha/Mẹ/Người giám hộ của Chủ thể dữ liệu
                                                            (CTDL) <span style={{fontSize: 14}}
                                                                            className="bold-text">{li?.CustomerName}</span> đồng
                                                            ý rằng:</p>
                                                        <div className="body-frame-content"
                                                                style={{lineHeight: '21px'}}>
                                                            <p>(I) Chấp nhận <span 
                                                                                    style={{
                                                                                        fontSize: 14,
                                                                                        color: '#985801',
                                                                                        cursor: "pointer",
                                                                                        fontWeight: 700
                                                                                    }}>{getSession(IS_MOBILE)?(
                                                                                        <a 
                                                                                        href='#'
                                                                                        onClick={()=>callbackAppOpenLink('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e', props.from)}
                                                                                        target='_blank' className='simple-brown' style={{display: 'inline'}}>Điều kiện và Điều khoản giao dịch điện tử</a>
                                                                                    ):(
                                                                                        <a href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e' target='_blank' className='simple-brown' style={{display: 'inline'}}>Điều kiện và Điều khoản giao dịch điện tử</a>
                                                                                    )}</span>,
                                                                và</p>
                                                            <p>(II) Trong quá trình tư vấn, giao kết, thực hiện Hợp đồng
                                                                bảo hiểm (HĐBH), giải quyết Quyền lợi bảo hiểm và các
                                                                công việc khác liên quan, Dai-ichi Life Việt Nam (DLVN)
                                                                có trách nhiệm xử lý Dữ liệu cá nhân (DLCN) của CTDL
                                                                đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP
                                                                ngày 17/04/2023 về bảo vệ DLCN. Trên cơ sở thay mặt
                                                                CTDL, Tôi/Chúng tôi xác nhận CTDL đã được DLVN thông báo
                                                                về việc xử lý DLCN, đã đọc và trực tiếp chọn đồng ý để
                                                                DLVN được quyền xử lý DLCN bao gồm DLCN cơ bản và DLCN
                                                                nhạy cảm phù hợp với các mục đích <span className='basic-bold'>(Mục đích: 1- Định
                                                                danh và nhận biết Khách hàng; 2- Giao kết HĐBH; 3- Thực
                                                                hiện HĐBH; 4 - Quản lý, đánh giá hoạt động kinh doanh
                                                                và tuân thủ của DLVN)</span> cụ thể tại phần xác nhận
                                                                bên dưới
                                                                và toàn bộ nội dung của Quy định bảo vệ và xử lý DLCN
                                                                được đăng tải trên trang chủ của DLVN
                                                                <span style={{
                                                                    fontSize: 14, color: 'blue', cursor: "pointer"
                                                                }} onClick={() => {
                                                                    openLink('https://www.dai-ichi-life.com.vn');
                                                                }}> https://www.dai-ichi-life.com.vn</span><span
                                                                    onClick={() => {
                                                                        openLink('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf');
                                                                    }}
                                                                    style={{
                                                                        fontSize: 14,
                                                                        color: '#985801',
                                                                        cursor: "pointer",
                                                                        fontWeight: 700
                                                                    }}> (Quy định BV&XLDLCN)</span>.</p>
                                                                    <p>
                                                                        Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 
                                                                    </p>
                                                        </div>
                                                    </div>}
                                                        {/* start relationship parent*/}
                                                    {!haveShowPayment &&  relateModalData[li?.CustomerID]?.relationship?.value === RELATIONSHIP.PARENTS && 
                                                    <div className="body-func" style={{marginTop: 26}}>
                                                        <h5 className="basic-semibold" style={{fontSize: 16}}>Mục
                                                            đích 1,2,3 <span style={{fontSize: 16, color: '#292929'}}>(cùng  thỏa thuận giao dịch điện tử)</span>
                                                        </h5>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input
                                                                            type="radio"
                                                                            value={REF_PURPOSE.YES}
                                                                            name={"refPurpose_" + index}
                                                                            checked={
                                                                                    relateModalData[li?.CustomerID]?.refPurpose?.value === REF_PURPOSE.YES
                                                                                //  checkRadioChecked(li?.CustomerID, 'refPurpose', REF_PURPOSE.YES)
                                                                            }
                                                                            onChange={(e)=>handleRadioPurpose(e, li?.CustomerID)}
                                                                            disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))}
                                                                        />
                                                                        <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                        <p className="text">Đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input
                                                                            type="radio"
                                                                            value={REF_PURPOSE.NO}
                                                                            name={"refPurpose_" + index}
                                                                            checked={
                                                                                relateModalData[li?.CustomerID]?.refPurpose?.value === REF_PURPOSE.NO
                                                                                //  checkRadioChecked(li?.CustomerID, 'refPurpose', REF_PURPOSE.NO)
                                                                            }
                                                                            onChange={(e)=>handleRadioPurpose(e, li?.CustomerID)}
                                                                            disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))}
                                                                        />
                                                                        <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                        <p className="text">Không đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <h5 className="basic-semibold" style={{fontSize: 16}}>Mục
                                                            đích 4</h5>
                                                        <div className="checkbox-warpper">
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input
                                                                            type="radio"
                                                                            value={REF_OTHER_PURPOSE.YES}
                                                                            name={"refOtherPurpose_" + index}
                                                                            checked={
                                                                                    relateModalData[li?.CustomerID]?.refOtherPurpose?.value === REF_OTHER_PURPOSE.YES
                                                                                //  checkRadioChecked(li?.CustomerID, 'refOtherPurpose', REF_OTHER_PURPOSE.YES)
                                                                            }
                                                                            onChange={(e)=>handleRadioPurpose(e, li?.CustomerID)}
                                                                            disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                        />
                                                                        <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                        <p className="text">Đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                    <input type="radio"
                                                                            value={REF_OTHER_PURPOSE.NO}
                                                                            name={"refOtherPurpose_" + index}
                                                                            checked={
                                                                                    relateModalData[li?.CustomerID]?.refOtherPurpose?.value === REF_OTHER_PURPOSE.NO
                                                                                // checkRadioChecked(li?.CustomerID, 'refOtherPurpose', REF_OTHER_PURPOSE.NO)
                                                                            }
                                                                            onChange={(e)=>handleRadioPurpose(e, li?.CustomerID)}
                                                                            disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                        />
                                                                        <div className={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED)))? "checkmark-readonly":"checkmark"}></div>
                                                                        <p className="text">Không đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>}
                                                    {/* end relationship parents */}

                                                    {/* start relationship OTHERS */}
                                                    {(!haveShowPayment  || !haveDLCN)&&  relateModalData[li?.CustomerID]?.relationship?.value === RELATIONSHIP.OTHERS &&(
                                                        <>
                                                        {!isPayment &&
                                                        <p className="modal-body-sub-content">Quý khách vui lòng cung cấp
                                                            thông tin liên hệ của Cha/Mẹ/Người giám hộ của Chủ thể dữ liệu để nhận
                                                            tin và xác nhận đồng ý xử lý DLCN của trẻ.
                                                        </p>
                                                        }
                                                        {(((!isPayment || haveShowPayment) && !haveDLCN) || (haveDLCN && !isPayment)) &&
                                                            <>
                                                                {isPayment &&
                                                                <p className="modal-body-sub-content">
                                                                    Quý khách vui lòng cung cấp thông tin liên hệ của Cha/Mẹ/Người giám hộ của NĐBH để nhận tin về khoản thanh toán, nếu có:
                                                                </p>
                                                                }
                                                                <p className="modal-body-sub-content-frame">
                                                                    Nếu chưa có số điện thoại/hộp thư điện tử liên hệ của Cha/Mẹ/Người giám hộ của {!haveDLCN?'NĐBH':'Chủ thể dữ liệu'}, Quý khách vui lòng hủy Yêu cầu trực tuyến này để lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất. 
                                                                </p>
                                                            </>
                                                        }
                                                        <div
                                                        className={`${relateModalData[li?.CustomerID]?.guardianName?.error ? 'validate' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content black">
                                                            <label>Họ và tên Cha/Mẹ/Người giám hộ</label>
                                                            <input
                                                                type="text"
                                                                // placeholder="Họ và tên Cha, mẹ, người giám hộ"
                                                                value={relateModalData[li?.CustomerID]?.guardianName?.value}
                                                                name="guardianName"
                                                                onChange={(e) => {
                                                                    if (requiredFields.includes('guardianName')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'guardianName');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({
                                                                        ...relateModalData,
                                                                        [li?.CustomerID]:{
                                                                            ...relateModalData[li?.CustomerID], guardianName: {
                                                                                error: '', value: e.target.value,
                                                                            }
                                                                        }
                                                                    })
                                                                }}
                                                                maxLength={50}
                                                                onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                            />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    <div
                                                        className={`${relateModalData[li?.CustomerID].relation?.error ? 'validate mb6' : ''} input mt12 relation-select-wrapper ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}
                                                        style={{
                                                            padding: '8px 12px 8px 16px',
                                                            height: '56px',
                                                            flexDirection: 'column'
                                                        }}>
                                                        <label style={{width: '100%',}}>Mối quan hệ Cha/Mẹ/Người
                                                            giám hộ</label>
                                                        <Select
                                                            suffixIcon={<SearchOutlined/>}
                                                            showSearch
                                                            size='large'
                                                            style={{
                                                                width: '100%',
                                                                height: '26px',
                                                                display: 'flex',
                                                                alignItems: 'center',
                                                            }}
                                                            bordered={false}
                                                            // placeholder="Mối quan hệ Cha/Mẹ/Người giám hộ"
                                                            optionFilterProp="name"
                                                            onChange={(value) => onChangeRelation(value, li?.CustomerID)}
                                                            onBlur={() => {
                                                                setRelateModalDataSync({
                                                                    ...relateModalData,
                                                                    [li?.CustomerID]:{
                                                                        ...relateModalData[li?.CustomerID], relation: {
                                                                            ...relateModalData[li?.CustomerID]?.relation,
                                                                            error: !relateModalData[li?.CustomerID]?.relation?.value ? "Vui lòng không được để trống" : "",
                                                                        }
                                                                    }
                                                                });

                                                            }}
                                                            value={relateModalData[li?.CustomerID]?.relation?.value}
                                                            filterOption={(input, option) => removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0}
                                                            disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                        >
                                                            {relationShipList.map((relation) => (
                                                                <Option key={relation?.RelationName}
                                                                        name={relation?.RelationName}>{relation?.RelationName}</Option>
                                                                        ))}
                                                        </Select>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.relation?.error && <span
                                                        style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.relation?.error}</span>}
                                                    
                                                    {/* start cmnd/cccd  */}
                                                    <div
                                                        
                                                        className={`${relateModalData[li?.CustomerID]?.identityCard?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>Căn cước công dân/Thẻ căn cước</label>
                                                            <input
                                                                type="text"
                                                                // placeholder="Căn cước công dân/Thẻ căn cước"
                                                                maxLength="14"
                                                                value={relateModalData[li?.CustomerID]?.identityCard?.value}
                                                                name="identityCard"
                                                                onChange={(e) => {
                                                                    if (requiredFields.includes('identityCard')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'identityCard');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({
                                                                        ...relateModalData,
                                                                        [li?.CustomerID]:{
                                                                            ...relateModalData[li?.CustomerID], identityCard: {
                                                                                error: "", value: e.target.value,
                                                                            }
                                                                        }
                                                                        
                                                                    })
                                                                }}
                                                                onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                            />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt=""/></i>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.identityCard?.error && <span
                                                        style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.identityCard?.error}</span>}


                                                    {/* end cmnd/cccd */}

                                                    {/* start date of birth  */}
                                                    <div
                                                        className={`${relateModalData[li?.CustomerID]?.dateOfBirth?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content" style={{ width: '100%' }}>
                                                            <label>Ngày tháng năm sinh</label>
                                                            <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                id="relateDateOfBirth"
                                                                disabledDate={disabledDate}
                                                                value={relateModalData[li?.CustomerID]?.dateOfBirth?.value ? moment(relateModalData[li?.CustomerID]?.dateOfBirth?.value) : ""}
                                                                onChange={(value) => {
                                                                    if (requiredFields.includes('dateOfBirth')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'dateOfBirth');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({
                                                                        ...relateModalData,
                                                                        [li?.CustomerID]: {
                                                                            ...relateModalData[li?.CustomerID], dateOfBirth: {
                                                                                ...relateModalData[li?.CustomerID]?.dateOfBirth,
                                                                                value,
                                                                            }
                                                                        }

                                                                    })
                                                                }}
                                                                onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                format="DD/MM/YYYY" style={{
                                                                    width: '100%',
                                                                    margin: '0px',
                                                                    padding: '0px',
                                                                    fontSize: '1.4rem',
                                                                    border: '0'
                                                                }} 
                                                                disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''} 
                                                                />
                                                        </div>
                                                    </div>
                                                    {/* end date of birth */}
                                                    
                                                    {/* start cellphone  */}
                                                    <div
                                                        className={`${relateModalData[li?.CustomerID]?.cellPhone?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>Số điện thoại</label>
                                                            <input value={relateModalData[li?.CustomerID]?.cellPhone?.value ? relateModalData[li?.CustomerID]?.cellPhone?.value.trim():''}
                                                                    name="phone"
                                                                    id="relateCellPhone"
                                                                    onChange={(e) => {

                                                                        if (requiredFields.includes('cellPhone')) {
                                                                            const updatedMissingFields = missingFields.filter(field => field !== 'cellPhone');
                                                                            setMissingFields(updatedMissingFields);
                                                                        }
                                                                        setRelateModalDataSync({
                                                                            ...relateModalData,
                                                                            [li?.CustomerID]:{
                                                                                ...relateModalData[li?.CustomerID], cellPhone: {
                                                                                    error: '', value: e.target.value,
                                                                                }
                                                                            }
                                                                            
                                                                        });
                                                                    }}
                                                                    onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                    maxLength={10}
                                                                    type="tel" 
                                                                    inputMode='numeric'
                                                                    disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                    />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/input_phone.svg"}
                                                                alt=""/></i>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.cellPhone?.error && <span
                                                        style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.cellPhone?.error}</span>}
                                                    {/* end cellphone  */}

                                                    {/* start email  */}
                                                    <div
                                                        className={`${relateModalData[li?.CustomerID]?.email?.error ? 'validate mb6' : ''} input mt12 ${(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>Email</label>
                                                            <input value={relateModalData[li?.CustomerID]?.email?.value} name="email"
                                                                    id="relateEmail"
                                                                    onChange={(e) => {
                                                                        if (requiredFields.includes('email')) {
                                                                            const updatedMissingFields = missingFields.filter(field => field !== 'email');
                                                                            setMissingFields(updatedMissingFields);
                                                                        }
                                                                        setRelateModalDataSync({
                                                                        ...relateModalData,
                                                                        [li?.CustomerID]:{
                                                                            ...relateModalData[li?.CustomerID], email: {
                                                                                error: '', value: e.target.value,
                                                                            }
                                                                        }
                                                                            
                                                                        });
                                                                    }}
                                                                    onBlur={(event) => validateInput(event, li?.CustomerID)}
                                                                    type="text" 
                                                                    disabled={(isPayment  && haveDLCN) || (props.disableEdit && !props.agentKeyInPOSelfEdit) || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && ( (li.ConsentResult === ConsentStatus.AGREED) || (li.ConsentResult === ConsentStatus.DECLINED))) ?'disabled':''}
                                                                    />
                                                        </div>
                                                        <i><img src={FE_BASE_URL + "/img/icon/input_mail.svg"} alt=""
                                                        /></i>
                                                    </div>
                                                    {relateModalData[li?.CustomerID]?.email?.error && <span
                                                        style={{color: 'red'}}>{relateModalData[li?.CustomerID]?.email?.error}</span>}
                                                    {/* end email  */}

                                                        </>
                                                    )}
                                                    {/* end relationship OTHERS */}

                                                    </>


                                                {/* end under 18 */}

                                                    
                                                    {(isPayment || haveShowPayment) && !checkRelationshipParent(relateModalData) && checkRelationshipNotEmpty(relateModalData) &&
                                                        <p style={{fontSize: '15px', fontWeight: '500', paddingTop: '16px'}}>Quý khách đã chọn người nhận tiền là Bên mua bảo hiểm, vì vậy, Quý khách vui lòng xác nhận và cam kết:</p>
                                                    }
                                                    {(isPayment || haveShowPayment) && !checkRelationshipParent(relateModalData) && checkRelationshipNotEmpty(relateModalData) &&
                                                    <div
                                                        className={props.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                        style={{
                                                            'maxWidth': '600px', display: 'flex'
                                                        }}>
                                                        {(props.disableEdit && !props.agentKeyInPOSelfEdit) ? (
                                                            <div
                                                                className={props.acceptPolicy ? "square-choose-readonly fill-grey" : "square-choose"}
                                                                style={{
                                                                    flex: '0 0 auto', height: '20px', cursor: 'pointer', margin: 0
                                                                }}
                                                                >
                                                                <div className="checkmark">
                                                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                                                </div>
                                                            </div>
                                                        ) : (
                                                            <div
                                                                className={props.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                                style={{
                                                                    flex: '0 0 auto', height: '20px', cursor: 'pointer', margin: 0
                                                                }}
                                                                onClick={() => updateAcceptPolicy(!acceptPolicy)}>
                                                                <div className="checkmark">
                                                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                                                </div>
                                                            </div>
                                                        )
                                                        }
                                                        <div className="accept-policy-wrapper" style={{
                                                            paddingLeft: '12px'
                                                        }}>
                                                            <p>Chúng tôi/Tôi Bên mua bảo hiểm xác nhận:</p>
                                                            <p style={{marginTop: 8}}>- Cha/Mẹ/Người giám hộ của Người được bảo hiểm đã đồng ý để Bên mua bảo hiểm nhận khoản thanh toán của Yêu cầu này, nếu được chấp thuận chi trả. Bên mua bảo hiểm cam kết chịu trách nhiệm trước pháp luật về việc nhận tiền này.</p>
                                                            <p style={{marginTop: 8}}>- Bên mua bảo hiểm có trách nhiệm cung cấp Giấy ủy quyền nhận tiền khi nhận thanh toán số tiền lớn hơn hoặc bằng 20 triệu đồng hoặc cung cấp Giấy ủy quyền vào bất kỳ lúc nào khi có yêu cầu nếu số tiền nhận nhỏ hơn 20 triệu đồng.</p>
                                                        </div>
                                                    </div>}
                                                </div>
                                            </div>
                                            ))
                                        }
                                      
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt=""/>
                    <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                </div>
                <div className="confirm-policy-wrapper">
                    <p className="confirm-policy-content">Bằng cách bấm nút Tiếp tục, Quý khách đã cam kết các thông
                        tin cung cấp trong yêu cầu là chính xác và chịu trách nhiệm với các thông tin đã khai
                        báo.</p>
                </div>
                {getSession(IS_MOBILE)&&
                <div className='nd13-padding-bottom120'></div>
                }
                <div className="bottom-btn" style={{flexDirection: 'column', alignItems: 'center'}}>
                    <LoadingIndicator area="submit-init-claim"/>
                    { (liUnder18NeedAgree && (liUnder18NeedAgree.length > 0) && !haveDLCN && checkRelationshipParent(relateModalData)) ?
                    (
                        <button
                            className={`btn btn-confirm`}
                            onClick={handleClickContinue}>Tiếp tục
                        </button> 
                    ):(
                        <button
                            className={`btn btn-confirm ${((((isPayment || haveShowPayment) && !props.acceptPolicy ) || props.isSubmitting || isError || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && props.disableEdit))) ? 'disabled' : ''}`}
                            disabled={((((isPayment || haveShowPayment) && !props.acceptPolicy ) || props.isSubmitting || isError || ((props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && props.disableEdit)))}
                            onClick={handleClickContinue}>Tiếp tục
                        </button>
                    )
                    }

                    {(props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && (props.disableEdit && !props.agentKeyInPOSelfEdit) ? (
                        <button className="btn btn-callback" onClick={() => props.notAgree()}>Hủy/Điều chỉnh thông tin</button>
                    ):(
                        (props.systemGroupPermission?.[0]?.Role === SDK_ROLE_AGENT) && props.disableEdit || (!props.disableEdit && props.consultingViewRequest) ?
                            (
                                <></>
                            ) : (
                                <button className="btn btn-callback" onClick={onClickCallBack}>Hủy</button>
                            )
                    )
                    }

                </div>
            {/* </div> */}
            {isOpenRejectND13 &&
                <PORejectND13 onClickConfirmBtn={onClickCallBackND13} onClickCallBack={onClickCallRedirectHome}/>}
            {/* {isOpenCallBack && <ND13CancelRequestConfirm
                onClickExtendBtn={() => clearRequest()}
                onClickCallBack={() => setIsOpenCallBack(false)}
            />} */}
        {/* </section> */}
        {confirmPayment &&
            <AlertPopupND13ConfirmPayment closePopup={closeConfirmPayment} msg={"<p className='basic-bold'>Yêu cầu xác nhận đồng ý Nghị định <br/> 13 đã gửi đến Chủ thể dữ liệu."} imgPath={FE_BASE_URL + "/img/popup/nd13_agreed_popup.png"} subMsg={"<p> Quý khách vui lòng thực hiện bước tiếp theo</p>"} />
        }
    </>):(<></>)
    );
};

export default ND13POContactInfoOver18;
