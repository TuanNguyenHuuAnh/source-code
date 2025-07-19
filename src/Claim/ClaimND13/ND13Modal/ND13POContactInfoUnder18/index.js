import React, {memo, useCallback, useEffect, useRef, useState} from 'react';
import arrowDown from '../../../../img/icon/dropdown-arrow.svg';
import {DatePicker, Select} from 'antd';
import dayjs from "dayjs";
import moment from "moment";
import './styles.css';
import AlertPopupND13ConfirmPayment from "../../../../components/AlertPopupND13ConfirmPayment";
import {getSession, removeAccents, VALID_EMAIL_REGEX, isOlderThan18, removeLocal, getDeviceId, setSession, removeSession} from "../../../../util/common";
import {iibGetMasterDataByType, CPConsentConfirmation} from "../../../../util/APIUtils";
import {SearchOutlined} from "@ant-design/icons";
import PORejectND13 from "../PORejectND13/PORejectND13";
import {isEmpty} from "lodash";
import {CELL_PHONE, DOB, EMAIL, FULL_NAME, FE_BASE_URL, CLIENT_ID, CLAIM_SAVE_LOCAL, USER_LOGIN, ACCESS_TOKEN, AUTHENTICATION, COMPANY_KEY, WEB_BROWSER_VERSION, ConsentStatus, ONLY_PAYMENT} from "../../../../constants";
import ND13CancelRequestConfirm from "../ND13CancelRequestConfirm/ND13CancelRequestConfirm";
import {CLAIM_STATE} from "../../../CreateClaim/CreateClaim";
import LoadingIndicator from '../../../../common/LoadingIndicator2';

const ND13POContactInfoUnder18 = memo((props) => {
    let isMounted = false;
    const {Option} = Select;
    const guardianNameRef = useRef(null);
    const cellPhoneRef = useRef(null);
    const emailRef = useRef(null);
    const identityCardRef = useRef(null);
    const dateOfBirthRef = useRef(null);
    const {
        onClickConfirmBtn = () => {
        },
        paymentMethodState
    } = props;

    const [acceptPolicy, setAcceptPolicy] = useState(false);
    const [missingFields, setMissingFields] = useState([]);
    const [isPayment, setIsPayment] = useState(false);
    const [confirmPayment, setConfirmPayment] = useState(false);
    const [isOpenRejectND13, setIsOpenRejectND13] = useState(false);
    const [isOpenCallBack, setIsOpenCallBack] = useState(false);

    const [relationShipList, setRelationShipList] = useState([]);

    const [relateModalData, setRelateModalData] = useState(props.submissionState.relateModalData);
    const [isActiveRelate, setIsActiveRelate] = useState(true);
    const [haveShowPayment, setHaveShowPayment] = useState(false);
    const [isSubmiting, setIsSubmiting] = useState(false);
    const [isError, setIsError] = useState(false);

    const setRelateModalDataSync = (rlModalData) => {
        setRelateModalData(rlModalData);
        let submissionState = Object.assign({}, props.submissionState);
        submissionState.relateModalData = rlModalData;
        props.handlerUpdateMainState("submissionState", submissionState);
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
                        handleDataUpdate();
                    }
                }
            })
            .catch(error => {
                // Xử lý lỗi ở đây nếu cần
            });
    }, []);

    const handlePurposeChange = (name, value) => {
        setRelateModalData({
            ...relateModalData, [name]: {
                ...relateModalData[name], value: value,
            },
        });
        let submissionState = Object.assign({}, props.submissionState);
        submissionState.relateModalData = relateModalData;
        submissionState.relateModalData[name].value = value;
        props.handlerUpdateMainState("submissionState", submissionState);
    };

    const handleRadioChange = (event) => {
        setRelateModalData({
            ...relateModalData, relationship: {
                ...relateModalData.relationship, value: event.target.value,
            }
        });
        let submissionState = Object.assign({}, props.submissionState);
        submissionState.relateModalData = relateModalData;
        submissionState.relateModalData.relationship.value = event.target.value;
        props.handlerUpdateMainState("submissionState", submissionState);
    };

    const validateInput = (event) => {
        let inValid = false;
        const {name, value} = event.target;

        switch (name) {
            case "guardianName":
                setRelateModalData({
                    ...relateModalData, guardianName: {
                        ...relateModalData.guardianName, error: !value ? "Vui lòng không được để trống" : "",
                    }
                });
                if (!value) {
                    guardianNameRef.current?.scrollIntoView({
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
                    cellPhoneRef.current?.scrollIntoView({
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
                    emailRef.current?.scrollIntoView({
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
                    identityCardRef.current?.scrollIntoView({
                        behavior: 'smooth', block: 'start',
                    });
                    inValid = true;
                }
                break;
            case "dateOfBirth":
                setRelateModalData({
                    ...relateModalData, dateOfBirth: {
                        ...relateModalData.dateOfBirth,
                        error: !isValidDate(value) ? "Ngày tháng năm sinh không hợp lệ" : "",
                    }
                });
                if (!isValidDate(value)) {
                    dateOfBirthRef.current?.scrollIntoView({
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

    const isValidDate = (dateString) => {
        const pattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!pattern.test(dateString)) {
            return false;
        }

        const date = new Date(dateString);
        if (isNaN(date.getTime())) {
            return false;
        }

        const now = new Date();
        const minDate = new Date(now.getFullYear() - 150, now.getMonth(), now.getDate());
        const maxDate = new Date(now.getFullYear() - 18, now.getMonth(), now.getDate()); // Ví dụ: người dùng ít nhất 18 tuổi
        if (date < minDate || date > maxDate) {
            return false;
        }

        return true;
    };

    const disabledDate = (current) => {
        return current && (current > dayjs().endOf('day'));
    }

    const toggleCollapsibleRelate = () => {
        setIsActiveRelate(!isActiveRelate);
    };


    const onChangeRelation = (value) => {

        if (requiredFields.includes('relation')) {
            const updatedMissingFields = missingFields.filter(field => field !== 'relation');
            setMissingFields(updatedMissingFields);
        }

        setRelateModalData(prevState => ({
            ...prevState, relation: {
                error: '', value: value,
            },
        }));

        const relationCode = relationShipList.find(result => result.RelationName === value);

        if (relationCode) {
            setRelateModalData(prevState => ({
                ...prevState, relationCode: {
                    ...prevState.relationCode, value: relationCode.RelationCode,
                },
            }));
        }
    };

    const checkIfNo = useCallback((value) => {
        return value.toLowerCase() !== 'no';
    }, []);

    const onClickCallBackND13 = () => {
        setRelateModalData({
            guardianName: {
                value: relateModalData.guardianName.value, error: '',
            }, dateOfBirth: {
                value: relateModalData.dateOfBirth.value, error: '',
            }, cellPhone: {
                value: relateModalData.cellPhone.value, error: '',
            }, email: {
                value: relateModalData.email.value, error: '',
            }, relationship: {
                value: relateModalData.relationship.value, error: '',
            }, relation: {
                value: relateModalData.relation.value, error: '',
            }, relationCode: {
                value: relateModalData.relationCode.value, error: '',
            }, refPurpose: {
                value: '', error: '',
            }, refOtherPurpose: {
                value: '', error: '',
            }, identityCard: {
                value: relateModalData.identityCard.value, error: '',
            },
        });
        setIsOpenRejectND13(false);
        // setIsOpenCompletedND13(false);
    };

    const onClickCallRedirectHome = () => {
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + props.selectedCliInfo.ClientID);
        setTimeout(() => {
            window.location.href = '/';
        }, 100);
    };

    const mapDataLIReceiverOther = (data) => {
        return {
            ...data,
            AnswerPurpose: (data.ConsentRuleID === 'CLAIM_PAYMENT')?'Y': '',
            RelationShip: relateModalData.relationCode?.value,
            ReceiverName: relateModalData.guardianName?.value,
            ReceiverDoB: moment(relateModalData.dateOfBirth?.value).local().format("YYYY-MM-DD HH:mm:ss.S"),
            ReceiverIDNum: relateModalData.identityCard?.value.trim(),
            EmailReceiver: relateModalData.email?.value.trim(),
            PhoneReceiver: relateModalData.cellPhone?.value.trim(),
            ZaloIDReceiver: "",
            RequesterID: getSession(CLIENT_ID)
        };
    }

    const closeConfirmPayment = () => {
        setConfirmPayment(false);
    }

    const mapDataLIReceiverParent = (data) => {
        return {
            ...data,
            Role: 'LI',
            AnswerPurpose: (data.ConsentRuleID === 'CLAIM_PAYMENT')?'Y': 'YY',
            RelationShip: "FATHR/MOTHR/GRDCS",
            ReceiverName: getSession(FULL_NAME),
            ReceiverDoB: getSession(DOB),
            ReceiverIDNum: "",
            EmailReceiver: getSession(EMAIL),
            PhoneReceiver: getSession(CELL_PHONE),
            ZaloIDReceiver: ""
        };
    }

    const filterLIND13 = (clientProfile) => {
        let clientProfileFiltered = clientProfile.filter(item => ((item.Role === 'LI') && (item.ConsentRuleID === 'ND13') && (item.ConsentResult !== 'Agreed')));
        return clientProfileFiltered;
    }

    const filterLIPayment = (clientProfile) => {
        let clientProfileFiltered = clientProfile.filter(item => ((item.Role === 'LI') && (item.ConsentRuleID === 'CLAIM_PAYMENT')));
        return clientProfileFiltered;
    }

    const clearRequest = () => {
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + props.selectedCliInfo.ClientID);
        setTimeout(() => {
            // props.handlerGoToStep(CLAIM_STATE.DONE);
            window.location.href = '/';
        }, 100);
    }
    const handleDataUpdate = useCallback(() => {
        if (!isEmpty(props.contactInfo)) {
            const newData = {
                poGuardianName: {
                    value: props.contactInfo?.FullName, // Họ và tên
                    error: '',
                }, poDateOfBirth: {
                    value: props.contactInfo?.DOB, // Ngày sinh
                    error: '',
                }, poCellPhone: {
                    value: props.contactInfo?.CellPhone, // Số điện thoại
                    error: '',
                }, poEmail: {
                    value: props.contactInfo?.Email, // Email
                    error: '',
                }, poIdentityCard: {
                    value: props.contactInfo?.POID.trim(), // Số chứng minh nhân dân
                    error: '',
                },
            };
            if (isMounted) {
                setRelateModalData(prevData => ({
                    ...prevData, ...newData
                }));
            }
        }
    }, [props.contactInfo, setRelateModalData]);

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
                    // console.log('consentResultPO=', consentResultPO);
                    // console.log('consentResulLI=', consentResulLI);
                    const isOpenPopupWarning = ( (consentResultPO !== ConsentStatus.AGREED) || (consentResulLI !== ConsentStatus.AGREED));
                    const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
                    const paymentProfile =  !isEmpty(clientProfile)?clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT'):[];
                    if (!isOpenPopupWarning && (IsEnablePayment === '1') && !isEmpty(paymentProfile)) {
                        setHaveShowPayment(true);
                        //props.callBackOnlyPayment(true);
                        setSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID, ONLY_PAYMENT + props.selectedCliInfo.ClientID);
                    } else {
                        setHaveShowPayment(false);
                        //props.callBackOnlyPayment(false);
                        if (getSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID)) {
                            removeSession(ONLY_PAYMENT + props.selectedCliInfo.ClientID);
                        }
                    }
                    setIsSubmiting(false);
                } else {
                    console.log("System error!");
                    setIsSubmiting(false);
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

    // useEffect(() => {
    //     isMounted = true;
    //     getRelationShips();
    //     return () => { isMounted = false };
    // }, [getRelationShips]);

    
    // useEffect(() => {
    //     isMounted = true;
    //     const {refPurpose, refOtherPurpose} = relateModalData;
    //     const isRefPurposeValid = checkIfNo(refPurpose.value);
    //     const isRefOtherPurposeValid = checkIfNo(refOtherPurpose.value);
    //     if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
    //         if (isMounted) {
    //             setIsOpenRejectND13(true);
    //         }
    //     }
    //     return () => { isMounted = false };
    // }, [checkIfNo, relateModalData, setIsOpenRejectND13]);

    useEffect(() => {
        // isMounted = true;
        fetchCPConsentConfirmationRefresh();
        // return () => { isMounted = false };
    }, [haveShowPayment, isPayment]);

    useEffect(() => {
        isMounted = true;
        const {refPurpose, refOtherPurpose} = relateModalData;
        const isRefPurposeValid = checkIfNo(refPurpose.value);
        const isRefOtherPurposeValid = checkIfNo(refOtherPurpose.value);
        if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
            if (isMounted) {
                setIsOpenRejectND13(true);
            }
        }
        // fetchCPConsentConfirmationRefresh();
        if (isEmpty(relationShipList)) {
            getRelationShips();
        }

        return () => { isMounted = false };
    }, [checkIfNo, relateModalData, setIsOpenRejectND13, getRelationShips]);

    const requiredFields = ['guardianName', 'relation', 'identityCard', 'dateOfBirth', 'cellPhone'];

    const isRelateOther = relateModalData.relationship?.value === 'relateOther';

    const handleClickContinue = () => {
        if (isError) {
            return;
        }
        const { totalInvoiceAmount, configClientProfile, clientProfile } = props;

        const handleMissingFields = (newMissingFields) => {
            if (newMissingFields.length > 0) {
                setMissingFields(newMissingFields);
                const firstMissingField = newMissingFields[0];
                const fieldElement = document.querySelector(`input[name="${firstMissingField}"]`);
                if (fieldElement) {
                    fieldElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    fieldElement.focus();
                }
                return true;
            }
            return false;
        };

        const proceedWithAction = (profileData, type) => {
            // console.log('profileData=',profileData);
            // if (haveShowPayment) {
            //     type = 'NotND13';
            // }
            onClickConfirmBtn(!isEmpty(clientProfile) ? profileData : [], type);
        };

        const handleRelateOther = () => {
            const newMissingFields = requiredFields.filter(field => !relateModalData[field]?.value);
            if (!handleMissingFields(newMissingFields)) {
                let filterProfile = [];
                let paymentProfile = !isEmpty(filterLIPayment(clientProfile))?mapDataLIReceiverOther(filterLIPayment(clientProfile)[0]):[];
                if ((isPayment || haveShowPayment) && !isEmpty(paymentProfile)) {
                    filterProfile.push(paymentProfile);
                }
                let nd13Profile = !isEmpty(filterLIND13(clientProfile))?mapDataLIReceiverOther(filterLIND13(clientProfile)[0]):[];
                if (!isEmpty(nd13Profile)) {
                    filterProfile.push(nd13Profile);
                }
                proceedWithAction(filterProfile, "Other");
            }
        };
        const IsEnablePayment = !isEmpty(configClientProfile) && configClientProfile[0]?.IsEnablePayment || '0';
        const paymentProfile =  clientProfile.find(item => item.ConsentRuleID === 'CLAIM_PAYMENT');
        if (isPayment || haveShowPayment) {
            if (isRelateOther) {
                handleRelateOther();
            } else {
                let filterProfile = [];
                let paymentProfile = !isEmpty(filterLIPayment(clientProfile))?mapDataLIReceiverParent(filterLIPayment(clientProfile)[0]):[];
                console.log('paymentProfile=', paymentProfile);
                if ((isPayment || haveShowPayment) && !isEmpty(paymentProfile)) {
                    filterProfile.push(paymentProfile);
                }
                let nd13Profile = !isEmpty(filterLIND13(clientProfile))?mapDataLIReceiverParent(filterLIND13(clientProfile)[0]):[];
                if (!isEmpty(nd13Profile)) {
                    filterProfile.push(nd13Profile);
                }
                proceedWithAction(filterProfile, "Parent");
            }
            // setIsPayment(false);
        } else if ((IsEnablePayment === '1') && !isEmpty(paymentProfile) && isRelateOther ) {
            setIsPayment(true);
            setConfirmPayment(true);
        } else {
            // setIsPayment(false);
            if (isRelateOther) {
                handleRelateOther();
            } else {
                let filterProfile = [];
                let paymentProfile = !isEmpty(filterLIPayment(clientProfile))?mapDataLIReceiverParent(filterLIPayment(clientProfile)[0]):[];
                if ((isPayment || haveShowPayment) && !isEmpty(paymentProfile)) {
                    filterProfile.push(paymentProfile);
                }
                let nd13Profile = !isEmpty(filterLIND13(clientProfile))?mapDataLIReceiverParent(filterLIND13(clientProfile)[0]):[];
                if (!isEmpty(nd13Profile)) {
                    filterProfile.push(nd13Profile);
                }
                proceedWithAction(filterProfile, "Parent");
            }
        }
    };

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

    const isButtonFamilyDisabled = props.isSubmitting || (!checkIfNo(relateModalData.refPurpose.value) || !checkIfNo(relateModalData.refOtherPurpose.value) || !relateModalData.refPurpose.value || !relateModalData.refOtherPurpose.value);
    const isButtonOtherDisabled = (!relateModalData.guardianName.value || !relateModalData.relation.value || !relateModalData.identityCard.value || !relateModalData.dateOfBirth.value || !relateModalData.cellPhone.value);
    
    return (!isSubmiting?(<>
        <section className="sccontract-warpper" id="scrollAnchor">
            <div className="contact-under-modal-wrapper insurance">
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
                            <div className="optionalform-isActivewrapper" style={{padding: '16px 16px 0px 16px'}}>
                                <div className="optionalform" style={{paddingBottom: '0px'}}>
                                    <div className="contact-under-modal-content">
                                        {(isPayment || haveShowPayment)?
                                            <p className="modal-body-content">Quyền lợi Bảo hiểm Hỗ trợ viện phí/Chăm
                                                sóc sức khỏe của NĐBH dưới 18 tuổi, nếu được chấp thuận, sẽ được chi trả cho Cha/ Mẹ/ Người giám hộ của
                                                trẻ.</p> :
                                            <p className="modal-body-content">Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày
                                                17/04/2023 về
                                                bảo vệ DLCN, Chủ thể dữ liệu cần xác nhận đồng ý cho phép DLVN xử lý
                                                DLCN.</p>}
                                        <div className="card-border"></div>
                                        {/*----------------------------------------LI < 18T: PO khai báo mối quan hệ với LI------------------------------------------------------*/}

                                        {isPayment || haveShowPayment?
                                            <p className="modal-body-sub-content">
                                                {isPayment?('Thông tin về khoản thanh toán sẽ được thông báo đến Cha/Mẹ/Người giám hộ của NĐBH theo thông tin đã được cung cấp bên dưới:'):('Quý khách vui lòng xác nhận mối quan hệ của BMBH với NĐBH:')}
                                            </p> : <>
                                                <p className="modal-body-sub-content">Nếu trẻ dưới 18 tuổi, việc
                                                    xử
                                                    lý DLCN cần sự
                                                    đồng ý của trẻ (từ 7 tuổi) và Cha/Mẹ/Người giám hộ của
                                                    trẻ.</p>
                                                <p className="modal-body-sub-content">Quý khách vui lòng xác nhận
                                                    mối
                                                    quan hệ của
                                                    BMBH với những người sau:</p>
                                            </>}


                                        <div className={`collapsible ${isActiveRelate ? 'active' : ''}`}>
                                            {!isPayment&&
                                                <div className="collapsible-header" onClick={toggleCollapsibleRelate}>
                                                    <p className={`modal-body-sub-content ${isActiveRelate ? 'bold-text' : ''}`}>{props.contactInfo?.FullName}</p>
                                                    {isActiveRelate ? <img src={arrowDown} alt="arrow-down"/> :
                                                        <img src={arrowDown} alt="arrow-left"/>}
                                                </div>
                                            }
                                            <div className="collapsible-content">
                                                {!isPayment &&
                                                    <div className="checkbox-warpper">
                                                        <div className="checkbox-item">
                                                            <div className="round-checkbox">
                                                                <label className="customradio">
                                                                    <input
                                                                        type="radio"
                                                                        value="relateFamily"
                                                                        checked={relateModalData.relationship?.value === 'relateFamily'}
                                                                        onChange={handleRadioChange}
                                                                    />
                                                                    <div className="checkmark"></div>
                                                                    <p className="text" style={{fontSize:'15px'}}>Cha, mẹ/ Người giám hộ</p>
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div className="checkbox-item">
                                                            <div className="round-checkbox">
                                                                <label className="customradio">
                                                                    <input
                                                                        type="radio"
                                                                        value="relateOther"
                                                                        checked={relateModalData.relationship?.value === 'relateOther'}
                                                                        onChange={handleRadioChange}
                                                                    />
                                                                    <div className="checkmark"></div>
                                                                    <p className="text" style={{fontSize:'15px'}}>Khác</p>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                }
                                                {!haveShowPayment && (relateModalData.relationship?.value === 'relateFamily') &&
                                                    <div className="body-frame">
                                                        <p className="body-frame-title" style={{
                                                            fontSize: 14, lineHeight: '21px', color: '#292929'
                                                        }}>Tôi/Chúng tôi, <span style={{fontSize: 14}}
                                                                                className="bold-text">{getSession(FULL_NAME)}</span> là
                                                            Cha/Mẹ/Người giám hộ của Người được bảo hiểm
                                                            (NĐBH) <span style={{fontSize: 14}}
                                                                         className="bold-text">{props.contactInfo?.FullName}</span> đồng
                                                            ý rằng:</p>
                                                        <div className="body-frame-content"
                                                             style={{lineHeight: '21px'}}>
                                                            <p>(I) Chấp nhận <span onClick={() => {
                                                                window.open('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/', '_blank');
                                                            }}
                                                                                   style={{
                                                                                       fontSize: 14,
                                                                                       color: '#985801',
                                                                                       cursor: "pointer",
                                                                                       fontWeight: 700
                                                                                   }}> <a href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e' target='_blank' className='simple-brown' style={{display: 'inline'}}>Điều kiện và Điều khoản giao dịch điện tử</a></span>,
                                                                và</p>
                                                            <p>(II) Trong quá trình tư vấn, giao kết, thực hiện Hợp đồng
                                                                bảo hiểm (HĐBH), giải quyết Quyền lợi bảo hiểm và các
                                                                công việc khác liên quan, Dai-ichi Life Việt Nam (DLVN)
                                                                có trách nhiệm xử lý Dữ liệu cá nhân (DLCN) của NĐBH
                                                                đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP
                                                                ngày 17/04/2023 về bảo vệ DLCN. Trên cơ sở thay mặt
                                                                NĐBH, Tôi/Chúng tôi xác nhận NĐBH đã được DLVN thông báo
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
                                                                    window.open('https://www.dai-ichi-life.com.vn', '_blank');
                                                                }}> https://www.dai-ichi-life.com.vn</span><span
                                                                    onClick={() => {
                                                                        window.open('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf', '_blank');
                                                                    }}
                                                                    style={{
                                                                        fontSize: 14,
                                                                        color: '#985801',
                                                                        cursor: "pointer",
                                                                        fontWeight: 700
                                                                    }}> (Quy định BV&XLDLCN)</span>.
                                                                    <br/>Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 
                                                                    </p>
                                                        </div>
                                                    </div>}
                                                {!haveShowPayment && (relateModalData.relationship?.value === 'relateFamily') && 
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
                                                                            value="yes"
                                                                            name="refPurpose"
                                                                            checked={relateModalData.refPurpose.value === 'yes'}
                                                                            onChange={() => handlePurposeChange('refPurpose', 'yes')}
                                                                        />
                                                                        <div className="checkmark"></div>
                                                                        <p className="text">Đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input
                                                                            type="radio"
                                                                            value="no"
                                                                            name="refPurpose"
                                                                            checked={relateModalData.refPurpose.value === 'no'}
                                                                            onChange={() => handlePurposeChange('refPurpose', 'no')}
                                                                        />
                                                                        <div className="checkmark"></div>
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
                                                                            value="yes"
                                                                            name="refOtherPurpose"
                                                                            checked={relateModalData.refOtherPurpose.value === 'yes'}
                                                                            onChange={() => handlePurposeChange('refOtherPurpose', 'yes')}
                                                                        />
                                                                        <div className="checkmark"></div>
                                                                        <p className="text">Đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div className="checkbox-item">
                                                                <div className="round-checkbox">
                                                                    <label className="customradio">
                                                                        <input
                                                                            type="radio"
                                                                            value="no"
                                                                            name="refOtherPurpose"
                                                                            checked={relateModalData.refOtherPurpose.value === 'no'}
                                                                            onChange={() => handlePurposeChange('refOtherPurpose', 'no')}
                                                                        />
                                                                        <div className="checkmark"></div>
                                                                        <p className="text">Không đồng ý</p>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>}
                                                {relateModalData.relationship.value === 'relateOther' && <div>
                                                    {!(isPayment || haveShowPayment) &&
                                                        <p className="modal-body-sub-content">Quý khách vui lòng cung cấp
                                                            thông tin liên hệ của Cha/Mẹ/Người giám hộ của NĐBH để nhận
                                                            tin và xác nhận đồng ý xử lý DLCN của trẻ.
                                                        </p>
                                                    }
                                                    {haveShowPayment &&
                                                        <p className="modal-body-sub-content">Quý khách vui lòng cung cấp thông tin liên hệ của Cha/Mẹ/Người giám hộ của NĐBH để nhận tin về khoản thanh toán, nếu có:
                                                        </p>
                                                    }
                                                    {!isPayment&&
                                                        <p className="modal-body-sub-content-frame">
                                                            Nếu chưa có số điện thoại/hộp thư điện tử liên hệ của Người được
                                                            bảo hiểm,
                                                            Quý khách vui lòng hủy Yêu cầu trực tuyến này để lập Phiếu yêu
                                                            cầu bằng giấy
                                                            và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.
                                                        </p>
                                                    }

                                                    <div
                                                        ref={guardianNameRef}
                                                        className={`${relateModalData.guardianName.error ? 'validate' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                        <div className="input__content black">
                                                            <label>Họ và tên Cha, mẹ, người giám hộ</label>
                                                            <input
                                                                type="text"
                                                                // placeholder="Họ và tên Cha, mẹ, người giám hộ"
                                                                value={relateModalData.guardianName.value}
                                                                name="guardianName"
                                                                onChange={(e) => {
                                                                    if (requiredFields.includes('guardianName')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'guardianName');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({
                                                                        ...relateModalData, guardianName: {
                                                                            error: '', value: e.target.value,
                                                                        }
                                                                    })
                                                                }}
                                                                maxLength={50}
                                                                onBlur={(event) => validateInput(event)}
                                                                disabled={isPayment ?'disabled':''}
                                                            />
                                                        </div>
                                                        <i><img src="../../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                    <div
                                                        className={`${relateModalData.relation.error ? 'validate mb6' : ''} input mt12 relation-select-wrapper ${isPayment ? 'disabled' : ''}`}
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
                                                            onChange={(value) => onChangeRelation(value)}
                                                            onBlur={() => {
                                                                setRelateModalDataSync({
                                                                    ...relateModalData, relation: {
                                                                        ...relateModalData.relation,
                                                                        error: !relateModalData.relation.value ? "Vui lòng không được để trống" : "",
                                                                    }
                                                                });

                                                            }}
                                                            value={relateModalData.relation.value}
                                                            filterOption={(input, option) => removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0}
                                                            disabled={isPayment ?'disabled':''}
                                                        >
                                                            {relationShipList.map((relation) => (
                                                                <Option key={relation.RelationName}
                                                                        name={relation.RelationName}>{relation.RelationName}</Option>))}
                                                        </Select>
                                                    </div>
                                                    {relateModalData.relation.error && <span
                                                        style={{color: 'red'}}>{relateModalData.relation.error}</span>}
                                                    <div
                                                        ref={identityCardRef}
                                                        className={`${relateModalData.identityCard.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>CMND/CCCD</label>
                                                            <input
                                                                type="text"
                                                                // placeholder="CMND/CCCD"
                                                                maxLength="14"
                                                                value={relateModalData.identityCard.value}
                                                                name="identityCard"
                                                                onChange={(e) => {
                                                                    if (requiredFields.includes('identityCard')) {
                                                                        const updatedMissingFields = missingFields.filter(field => field !== 'identityCard');
                                                                        setMissingFields(updatedMissingFields);
                                                                    }
                                                                    setRelateModalDataSync({
                                                                        ...relateModalData, identityCard: {
                                                                            error: "", value: e.target.value,
                                                                        }
                                                                    })
                                                                }}
                                                                onBlur={(event) => validateInput(event)}
                                                                disabled={isPayment ?'disabled':''}
                                                            />
                                                        </div>
                                                        <i><img src="../../../img/icon/edit.svg" alt=""/></i>
                                                    </div>
                                                    {relateModalData.identityCard.error && <span
                                                        style={{color: 'red'}}>{relateModalData.identityCard.error}</span>}
                                                    <div
                                                        ref={dateOfBirthRef}
                                                        className={`${relateModalData.dateOfBirth.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                        <div className="input__content" style={{width: '100%'}}>
                                                            <label>Ngày tháng năm sinh</label>
                                                            <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                        id="relateDateOfBirth"
                                                                        disabledDate={disabledDate}
                                                                        value={relateModalData.dateOfBirth.value ? moment(relateModalData.dateOfBirth.value) : ""}
                                                                        onChange={(value) => {
                                                                            if (requiredFields.includes('dateOfBirth')) {
                                                                                const updatedMissingFields = missingFields.filter(field => field !== 'dateOfBirth');
                                                                                setMissingFields(updatedMissingFields);
                                                                            }
                                                                            setRelateModalDataSync({
                                                                                ...relateModalData, dateOfBirth: {
                                                                                    ...relateModalData.dateOfBirth,
                                                                                    value,
                                                                                }
                                                                            })
                                                                        }}
                                                                        onBlur={(event) => validateInput(event)}
                                                                        format="DD/MM/YYYY" style={{
                                                                width: '100%',
                                                                margin: '0px',
                                                                padding: '0px',
                                                                fontSize: '1.4rem',
                                                                border: '0'
                                                            }} disabled={isPayment ?'disabled':''}/>
                                                        </div>
                                                    </div>
                                                    <div
                                                        className={`${relateModalData.cellPhone.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>Số điện thoại</label>
                                                            <input value={relateModalData.cellPhone.value?relateModalData.cellPhone.value.trim():''}
                                                                   name="phone"
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
                                                                   onBlur={(event) => validateInput(event)}
                                                                   maxLength={10}
                                                                   type="text" disabled={isPayment ?'disabled':''}/>
                                                        </div>
                                                        <i><img src="../../../img/icon/input_phone.svg"
                                                                alt=""/></i>
                                                    </div>
                                                    {relateModalData.cellPhone.error && <span
                                                        style={{color: 'red'}}>{relateModalData.cellPhone.error}</span>}
                                                    <div
                                                        className={`${relateModalData.email.error ? 'validate mb6' : ''} input mt12 ${isPayment ? 'disabled' : ''}`}>
                                                        <div className="input__content">
                                                            <label>Email</label>
                                                            <input value={relateModalData.email.value} name="email"
                                                                   id="relateEmail"
                                                                   onChange={(e) => {
                                                                       if (requiredFields.includes('email')) {
                                                                           const updatedMissingFields = missingFields.filter(field => field !== 'email');
                                                                           setMissingFields(updatedMissingFields);
                                                                       }
                                                                       setRelateModalDataSync({
                                                                           ...relateModalData, email: {
                                                                               error: '', value: e.target.value,
                                                                           }
                                                                       })
                                                                   }}
                                                                   onBlur={(event) => validateInput(event)}
                                                                   type="text" disabled={isPayment ?'disabled':''}/>
                                                        </div>
                                                        <i><img src="../../../img/icon/input_mail.svg" alt=""
                                                        /></i>
                                                    </div>
                                                    {relateModalData.email.error && <span
                                                        style={{color: 'red'}}>{relateModalData.email.error}</span>}
                                                    <div style={{marginTop: '12px'}}>
                                                    {(isPayment || haveShowPayment) && 
                                                    <p style={{fontSize: '15px', fontWeight: '500'}}>Quý khách đã chọn người nhận tiền là Bên mua bảo hiểm, vì
                                                                vậy,
                                                                Quý khách vui lòng xác nhận và cam kết:</p>}
                                                    </div>

                                                    {(isPayment || haveShowPayment) && <div
                                                        className={acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                                        style={{
                                                            'maxWidth': '600px', display: 'flex'
                                                        }}>
                                                        <div
                                                            className={acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                                            style={{
                                                                flex: '0 0 auto',
                                                                height: '20px',
                                                                cursor: 'pointer',
                                                                margin: 0
                                                            }}
                                                            onClick={() => setAcceptPolicy(!acceptPolicy)}>
                                                            <div className="checkmark">
                                                                <img src="img/icon/check.svg" alt=""/>
                                                            </div>
                                                        </div>
                                                        <div className="accept-policy-wrapper" style={{
                                                            paddingLeft: '12px'
                                                        }}>
                                                            <p>Chúng tôi/Tôi, Bên mua bảo hiểm xác nhận:</p>
                                                            <p style={{marginTop: 8}}>- Cha/Mẹ/Người giám hộ của
                                                                Người được bảo hiểm đã đồng ý
                                                                để Bên mua bảo hiểm nhận khoản thanh toán của Yêu
                                                                cầu
                                                                này, nếu được chấp thuận chi trả. Bên mua bảo hiểm
                                                                cam
                                                                kết chịu trách nhiệm trước pháp luật về việc nhận
                                                                tiền
                                                                này. </p>
                                                            <p style={{marginTop: 8}}>- Bên mua bảo hiểm có trách
                                                                nhiệm cung cấp Giấy ủy quyền
                                                                nhận tiền khi nhận thanh toán số tiền lớn hơn hoặc
                                                                bằng
                                                                20 triệu đồng hoặc cung cấp Giấy ủy quyền vào bất kỳ
                                                                lúc
                                                                nào khi có yêu cầu nếu số tiền nhận nhỏ hơn 20 triệu
                                                                đồng.</p>
                                                        </div>
                                                    </div>}
                                                </div>}
                                                <br/>
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
                {/*---------------------------------------------------------Footer------------------------------------------------------------*/}
                <div className="btn-wrapper">
                    <LoadingIndicator area="submit-init-claim"/>
                    {(haveShowPayment && (relateModalData.relationship.value === 'relateFamily'))?(
                    <button
                        className='btn btn-confirm'
                        onClick={handleClickContinue}
                    >
                        Tiếp tục
                    </button>
                    ):(
                        <button
                            className={`${!relateModalData.relationship?.value || (relateModalData.relationship?.value === 'relateFamily' && isButtonFamilyDisabled) || (relateModalData.relationship?.value === 'relateOther' && isButtonOtherDisabled || ((isPayment || haveShowPayment) && !acceptPolicy)) || props.isSubmitting ? 'disabled' : ''} btn btn-confirm`}

                            disabled={!relateModalData.relationship?.value || (relateModalData.relationship?.value === 'relateFamily' && isButtonFamilyDisabled) || (relateModalData.relationship?.value === 'relateOther' && isButtonOtherDisabled || ((isPayment || haveShowPayment) && !acceptPolicy)) || props.isSubmitting}
                            onClick={handleClickContinue}
                        >
                            Tiếp tục
                        </button>
                    )}


                    <button className="btn btn-callback" onClick={() => setIsOpenCallBack(true)}>Hủy
                    </button>
                </div>
            </div>
            {isOpenRejectND13 &&
                <PORejectND13 onClickConfirmBtn={onClickCallBackND13} onClickCallBack={onClickCallRedirectHome}/>}
            {isOpenCallBack && <ND13CancelRequestConfirm
                onClickExtendBtn={() => clearRequest()}
                onClickCallBack={() => setIsOpenCallBack(false)}
            />}
        </section>
        {confirmPayment &&
            <AlertPopupND13ConfirmPayment closePopup={closeConfirmPayment} msg={"<p className='basic-bold'>Yêu cầu xác nhận đồng ý Nghị định <br/> 13 đã gửi đến Cha/Mẹ/Người giám <br/> hộ của Người được bảo hiểm. </p>"} imgPath={FE_BASE_URL + "/img/popup/nd13_agreed_popup.png"} subMsg={"<p>Quý khách vui lòng thực hiện bước tiếp theo</p>"} />
        }
    </>):(<></>));
});

export default ND13POContactInfoUnder18;
