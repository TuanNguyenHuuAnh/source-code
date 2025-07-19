import React, {useCallback, useEffect, useState} from 'react';
import PORejectND13LINK from "../../PORejectND13/PORejectND13LINK";
import SuccessfulLIND13 from "../ND13Popup/SuccessfulLIND13";
import iconConfirmation from '../../../../../img/icon/iconConfirmation.svg';
import iconWarnning from "../../../../../img/icon/icon_Warnning.svg";
import './styles.css';
import {isEmpty} from "lodash";
import {getDeviceId,getSession} from "../../../../../util/common";
import {getOSVersion, getOperatingSystem, buildMicroRequest } from "../../../../../SDK/sdkCommon";
import {AUTHENTICATION, COMPANY_KEY, WEB_BROWSER_VERSION, RELATION_SHIP_MAPPING, SYSTEM_DCONNECT, ACCESS_TOKEN} from "../../../../../constants";
import {CPConsentConfirmation, iibGetMasterDataByType} from "../../../../../util/APIUtils";
import {requestClaimRecordInfor} from "../../../../../SDK/sdkAPI";
import ND13LILinkExpired from "../ND13LILinkExpired";
let answer = '';
const ND13LIConfirm = (props) => {
    const [clientProfile, setClientProfile] = useState([]);
    const [configClientProfile, setConfigClientProfile] = useState([]);
    const [relationShipList, setRelationShipList] = useState([]);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [purposeAnswer, setPurposeAnswer] = useState('');
    const [remainTime, setRemainTime] = useState(30);
    const [isExpired, setIsExpired] = useState(false);

    const [relateModalData, setRelateModalData] = useState({
        refPurpose: {
            value: '', error: '',
        }, refOtherPurpose: {
            value: '', error: '',
        },
    });

    const [isOpenRejectND13, setIsOpenRejectND13] = useState(false);
    const [isOpenSuccessfulND13, setIsOpenSuccessfulND13] = useState(false);

    const handlePurposeChange = (name, value) => {
        setRelateModalData({
            ...relateModalData, [name]: {
                ...relateModalData[name], value: value,
            },
        });
    };

    const checkIfNo = (value) => {
        return value.toLowerCase() !== 'no';
    };

    const checkIfYes = (value) => {
        return value.toLowerCase() === 'yes';
    };

    const getRelationShips = useCallback(() => {
        let request = {
            Action: "RelationShipConsent", Project: "mcp"
        };
        iibGetMasterDataByType(request)
            .then(Res => {
                let Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile) {
                    setRelationShipList(Response.ClientProfile);
                }
            })
            .catch(error => {
                // Xử lý lỗi ở đây nếu cần
            });
    }, []);

    const checkSubmit = () => {
        const isRefPurposeValid = checkIfYes(relateModalData.refPurpose.value);
        const isRefOtherPurposeValid = checkIfYes(relateModalData.refOtherPurpose.value);
        let RefPurposeStr = 'N';
        let RefOtherPurposeStr = 'N';
        if (isRefPurposeValid) {
            RefPurposeStr = 'Y';
        } else {
            RefPurposeStr = 'N';
        }
        if (isRefOtherPurposeValid) {
            RefOtherPurposeStr = 'Y'
        } else {
            RefOtherPurposeStr = 'N'
        }
        answer = RefPurposeStr + RefOtherPurposeStr;
        setPurposeAnswer(answer);
        if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
            setIsOpenRejectND13(true);
        } else {
            submitCPConsentConfirmation();
        }
    };

    const onClickCallBackND13 = () => {
        setRelateModalData({
            refPurpose: {
                value: '', error: '',
            }, refOtherPurpose: {
                value: '', error: '',
            },
        });
        setIsOpenRejectND13(false);
    };

    const onClickCallSuccessfulND13 = () => {
        const isRefPurposeValid = checkIfYes(relateModalData.refPurpose.value);
        const isRefOtherPurposeValid = checkIfYes(relateModalData.refOtherPurpose.value);
        let RefPurposeStr = 'N';
        let RefOtherPurposeStr = 'N';
        if (isRefPurposeValid) {
            RefPurposeStr = 'Y';
        } else {
            RefPurposeStr = 'N';
        }
        if (isRefOtherPurposeValid) {
            RefOtherPurposeStr = 'Y'
        } else {
            RefOtherPurposeStr = 'N'
        }
        answer = RefPurposeStr + RefOtherPurposeStr;
        cancelRequestConsentConfirmation();
        setPurposeAnswer(answer);
        submitCPConsentConfirmation();
        setIsOpenSuccessfulND13(true);
        setIsOpenRejectND13(false);
        onClickCallBackND13();
        
    };

    const cancelRequestConsentConfirmation=()=> {
        console.log('cancelRequestConsentConfirmation');
        let consentSubmitData = addAnswerPurpose(clientProfile);
        let UserID = '';
        let PType = '';
        let TrackingID = '';
        let requestId = '';
        if (!isEmpty(props.clientProfile)) {
            UserID = props.userLogin || "";
            PType = props.clientProfile[0].ProcessType;
            TrackingID = props.clientProfile[0].TrackingID;
            requestId = props.clientProfile[0]?.requestId;
        }

        let request = {
            jsonDataInput: {
                Action: "CancelRequest",
                APIToken: props.newAPIToken || "",
                Authentication: AUTHENTICATION,
                ClientID: UserID,
                Company: COMPANY_KEY,
                ProcessType: PType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: props.userLogin || "",
                TrackingID: TrackingID
            }

        };
        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                } 
            })
            .catch(error => {
                console.log(error);
            });
            updateClaimRequest('REJECT_DLCN', requestId, UserID, PType);
    }
    
    const startTimer = (expDate) => {
        let myInterval = setInterval(() => {
            let expiredDate = new Date(expDate);
            let currentDate = new Date();
            let remain = Math.floor((expiredDate - currentDate) / 60000);
            if (remain < 0) {
                remain = 0;
            }
            setRemainTime(remain);
            if (remain <= 0) {
                clearInterval(myInterval);
                setIsExpired(true);
            }
        }, 1000)
        return () => {
            clearInterval(myInterval);
        };

    }

    const fetchCPConsentConfirmation = (data) => {
        if (data && data.ExpiredDate) {
            let expiredDate = new Date(data.ExpiredDate);
            let currentDate = new Date();
            let remain = Math.floor((expiredDate - currentDate)/60000);
            if (remain <= 0) {
                remain = 0;
                setIsExpired(true);
            } else {
                startTimer(data.ExpiredDate);
            }
            setRemainTime(remain);
            
        }
        let request = {
            jsonDataInput: {
                Action: "CheckCustomerConsent",
                APIToken: props.newAPIToken || "",
                Authentication: AUTHENTICATION,
                ClientID: data && data.CustomerID ? data.CustomerID : "",
                Company: COMPANY_KEY,
                ClientList: data && data.CustomerID ? data.CustomerID : "",
                ProcessType: data && data.ProcessType ? data.ProcessType : "CLAIM",
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: data && data.CustomerID ? data.CustomerID : "",
                TrackingID: data && data.TrackingID ? data.TrackingID : "",
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                    const clientProfile = Response.ClientProfile;
                    const configClientProfile = Response.Config;
                    setClientProfile(clientProfile);
                    setConfigClientProfile(configClientProfile);
                } else {
                    console.log("System error!");
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    const submitCPConsentConfirmation = () => {
        setIsSubmitting(true);
        let consentSubmitData = addAnswerPurpose(clientProfile);
        let UserID = '';
        let PType = '';
        if (!isEmpty(props.clientProfile)) {
            UserID = props.clientProfile[0].RequesterID;
            PType = props.clientProfile[0].ProcessType;
        }
        const request = {
            jsonDataInput: {
                Action: "SubmitLinkConfirm",
                APIToken: props.newAPIToken || "",
                Authentication: AUTHENTICATION,
                ClientID: UserID,
                Company: COMPANY_KEY,
                ProcessType: PType,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                UserLogin: props.userLogin || "",
                ConsentSubmit: consentSubmitData,
            }
        };

        CPConsentConfirmation(request)
            .then(res => {
                const Response = res.Response;
                if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                    setIsSubmitting(false);
                    setIsOpenSuccessfulND13(true);
                } else {
                    setIsSubmitting(false);
                }
            })
            .catch(error => {
                setIsSubmitting(false);
            });
    }

    const addAnswerPurpose = (data) => {
        let UserID = '';
        if (!isEmpty(props.clientProfile)) {
            UserID = props.clientProfile[0].RequesterID;
        }
        
        return data.map(item => ({
            ...item, 
            AnswerPurpose: (item.ConsentRuleID === 'CLAIM_PAYMENT')?((answer.indexOf('Y') >= 0)?'Y':'N'): answer,
            RequesterID: UserID
        }));
    };

    const updateClaimRequest = (status, requestId, customerId, processType) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: SYSTEM_DCONNECT,
            signature: customerId,
            accessToken: props.newAPIToken || ""
        }
        let data = {
            requestId: requestId,
            customerId: customerId,
            // customerName: getSession(FULL_NAME),
            processType: processType,
            status: status,
            jsonState: ''
        }

        let request = buildMicroRequest(metadata, data);
        requestClaimRecordInfor(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ requestId: Res.data?.[0]?.requestId });
            } 
        }).catch(error => {
            console.log(error);
        });

    }

    const mapRelationCodeToText = (relationCode) => {
        const relation = relationShipList.find(rel => rel.RelationCode === relationCode);
        if (relation) {
            return relation.RelationName;
        } else {
            return "Khác";
        }
    }

    useEffect(() => {
        const {refPurpose, refOtherPurpose} = relateModalData;
        const isRefPurposeValid = checkIfNo(refPurpose.value);
        const isRefOtherPurposeValid = checkIfNo(refOtherPurpose.value);
        let RefPurposeStr = 'N';
        let RefOtherPurposeStr = 'N';
        if (refPurpose.value === 'yes') {
            RefPurposeStr = 'Y';
        } else if (refPurpose.value === 'no') {
            RefPurposeStr = 'N';
        }
        if (refOtherPurpose.value === 'yes') {
            RefOtherPurposeStr = 'Y'
        } else if (refOtherPurpose.value === 'no') {
            RefOtherPurposeStr = 'N'
        }
        answer = RefPurposeStr + RefOtherPurposeStr;
        if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
            setIsOpenRejectND13(true);
        }
    }, [checkIfNo, relateModalData, setIsOpenRejectND13]);

    useEffect(() => {
        getRelationShips();
    }, [getRelationShips]);

    const isButtonEnabled = relateModalData.refPurpose.value !== '' && relateModalData.refOtherPurpose.value !== '';
    const styles = {
        container: {
            background: '#9B3A32', padding: '16px', display: 'flex', alignItems: 'flex-start', marginBottom: '24px',
        }, icon: {
            marginRight: '10px',
        }, text: {
            fontSize: '13px', fontWeight: 500, lineHeight: '19px', textAlign: 'left', color: '#ffffff',
        }, flexLayout: {
            display: 'flex', alignItems: 'flex-start', flexDirection: 'column',
        }
    };

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

    const filterLI = (clientProfile) => {
        let clientProfileFiltered = clientProfile.filter(item => ((item.Role === 'LI') && ((item.ConsentRuleID === 'ND13') || (item.ConsentRuleID === ''))));
        return clientProfileFiltered;
    }

    useEffect(() => {
        if (!isEmpty(props.clientProfile)) {
            setClientProfile(props.clientProfile);
            fetchCPConsentConfirmation(!isEmpty(props.clientProfile) ? props.clientProfile[0] : null);
        }
    }, [props.clientProfile]);

    return (
    isExpired ? (<ND13LILinkExpired />) : (
    <section className="insured-per-li-wrapper">
        <div className="insurance-wrapper" style={{paddingBottom: 60}}>
            <div className="stepform" style={{marginTop: 68}}>
                <div className="info">
                    <div className="info__body">
                        <div className="optionalform-isActivewrapper">
                            <div className="optionalform" style={{paddingBottom: 36}}>
                                <div className="relate-modal" style={{paddingBottom: 0}}>
                                    <div style={styles.container}>
                                        <img style={styles.icon} src={iconWarnning} alt="iconWarnning"/>
                                        <div style={styles.flexLayout}>
                                            <p style={styles.text}>Yêu cầu xác nhận sẽ hết hiệu lực sau <span style={{fontWeight:700, color:'#ffffff'}}>{remainTime} </span>
                                                phút</p>
                                            <p style={styles.text}>
                                                Trường hợp Quý khách không đồng ý, Bên mua bảo hiểm sẽ không thể hoàn tất yêu cầu trực tuyến. Thay vào đó, Bên mua bảo hiểm sẽ lập yêu cầu bằng giấy với xác nhận đồng ý của Quý khách để nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất
                                            </p>
                                        </div>
                                    </div>
                                    <p className="modal-body" style={{fontSize: 16, fontWeight: 700, marginTop: 12}}>Xử
                                        lý
                                        Dữ liệu cá nhân</p>
                                    <p className="modal-body"
                                       style={{fontWeight: 500, fontSize: '15px', lineHeight: '23px',}}>Nhằm tuân thủ Nghị
                                        định
                                        13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ Dữ liệu cá nhân (DLCN), Chủ thể dữ liệu
                                        cần phải đồng ý cho phép Dai-ichi Life Việt Nam xử lý DLCN.</p>
                                    <div>
                                        <div className="body-frame">
                                            {isOlderThan18(props.clientProfile[0]?.CustomerDoB) ? <p className="body-frame-title">Tôi là <span
                                                className="bold-text" style={{fontSize: '12px'}}>{!isEmpty(props.clientProfile) && props.clientProfile[0].CustomerName}</span>,
                                                đồng ý rằng:</p> : <p className="body-frame-title">Tôi/Chúng tôi <span
                                                className="bold-text" style={{fontSize: '12px'}}>{!isEmpty(props.clientProfile) && props.clientProfile[0].ReceiverName}</span> là <span
                                                className="bold-text" style={{fontSize: '12px'}}>{!isEmpty(props.clientProfile) && !isEmpty(filterLI(props.clientProfile)) && filterLI(props.clientProfile)[0]?.RelationShip?RELATION_SHIP_MAPPING[filterLI(props.clientProfile)[0]?.RelationShip]: 'Khác'}</span> của Chủ thể dữ liệu (CTDL) <span
                                                className="bold-text" style={{fontSize: '12px'}}>{!isEmpty(props.clientProfile) && props.clientProfile[0].CustomerName}</span> đồng ý rằng:</p>}
                                                {isOlderThan18(props.clientProfile[0]?.CustomerDoB) ? (
                                                    <p className="body-frame-content">
                                                        (I) Chấp nhận <span
                                                            style={{ color: '#985801'}}><a href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e' target='_blank' className='simple-brown' style={{ display: 'inline', fontSize: '12px'  }}>Điều kiện và Điều khoản giao dịch điện tử (ĐK&ĐK GDĐT)</a></span> và
                                                        (II) Trong quá trình tư vấn, giao kết, thực hiện Hợp đồng bảo hiểm (HĐBH), giải quyết Quyền lợi bảo hiểm và các công việc khác liên quan, Dai-ichi Life Việt Nam (DLVN) có trách nhiệm xử lý Dữ liệu cá nhân (DLCN) của Tôi đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ DLCN. Tôi xác nhận đã được DLVN thông báo về việc xử lý DLCN, đã đọc và đồng ý cho phép DLVN được quyền xử lý DLCN bao gồm DLCN cơ bản và DLCN nhạy cảm phù hợp với các mục đích <span style={{fontSize: '12px', fontWeight: '700'}}>(Mục đích: 1- Định danh và nhận biết Khách hàng; 2- Giao kết HĐBH; 3- Thực hiện HĐBH; 4- Quản lý, đánh giá hoạt động kinh doanh và tuân thủ của DLVN) </span>cụ thể tại phần xác nhận bên dưới và toàn bộ nội dung của Quy định bảo vệ và xử lý DLCN được đăng tải trên trang chủ của DLVN: <span style={{ color: 'blue', cursor: 'pointer' }}
                                                        ><a href='https://www.dai-ichi-life.com.vn' target='_blank' style={{ display: 'inline' }}>https://www.dai-ichi-life.com.vn</a> <a style={{ display: 'inline' }} className='simple-brown' href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf' target='_blank'>(“Quy định BV&XLDLCN”)</a></span>.
                                                        <br/>Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 
                                                    </p>
                                                ) : (
                                                    <p className="body-frame-content">
                                                        (I) Chấp nhận <span
                                                            style={{ color: '#985801'}}><a href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e' target='_blank' className='simple-brown' style={{ display: 'inline', fontSize: '12px'  }}>Điều kiện và Điều khoản giao dịch điện tử (ĐK&ĐK GDĐT)</a></span> và
                                                        (II) Trong quá trình tư vấn, giao kết, thực hiện Hợp đồng bảo hiểm (HĐBH), giải quyết Quyền lợi bảo hiểm và các công việc khác liên quan, Dai-ichi Life Việt Nam (DLVN) có trách nhiệm xử lý Dữ liệu cá nhân (DLCN) của CTDL đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ DLCN. Trên cơ sở thay mặt CTDL, Tôi/Chúng tôi xác nhận CTDL đã được DLVN thông báo về việc xử lý DLCN, đã đọc và trực tiếp chọn đồng ý để DLVN được quyền xử lý DLCN bao gồm DLCN cơ bản và DLCN nhạy cảm phù hợp với các mục đích <span style={{fontSize: '12px', fontWeight: '700'}}>(Mục đích: 1- Định danh và nhận biết Khách hàng; 2- Giao kết HĐBH; 3- Thực hiện HĐBH; 4- Quản lý, đánh giá hoạt động kinh doanh và tuân thủ của DLVN)</span> cụ thể tại phần xác nhận bên dưới và toàn bộ nội dung của Quy định bảo vệ và xử lý DLCN được đăng tải trên trang chủ của DLVN: <span style={{ color: 'blue', cursor: 'pointer' }}
                                                        ><a href='https://www.dai-ichi-life.com.vn' target='_blank' style={{ display: 'inline', fontSize: '12px' }}>https://www.dai-ichi-life.com.vn</a> <a style={{ display: 'inline', fontSize: '12px'  }} className='simple-brown' href='https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf' target='_blank'>(“Quy định BV&XLDLCN”)</a></span>.
                                                        <br/>Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 
                                                    </p>
                                                )
                                                }
                                        </div>
                                        <br/>
                                        <div className="body-func">
                                            <h5 className="basic-semibold" style={{fontSize: 16}}>Mục đích
                                                1,2,3 (cùng thỏa thuận giao dịch điện tử)</h5>
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
                                            <h5 className="basic-semibold" style={{fontSize: 16}}>Mục đích 4</h5>
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
                                            <p style={{textAlign: 'justify'}}>
                                                <span
                                                    className="red-text basic-bold" style={{color: '#de181f'}}>Lưu ý: </span><span
                                                style={{color: '#727272'}}>
                                                    Đồng thời với việc lập Yêu cầu trực tuyến này của Bên mua bảo hiểm, sự đồng ý của Chủ thể dữ liệu cho phép DLVN xử lý DLCN sẽ có hiệu lực áp dụng ngay cả trường hợp Yêu cầu trực tuyến này bị hủy bỏ hoặc bị từ chối.
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="bottom-btn">
                <button
                    className={`btn btn-primary${isButtonEnabled && !isSubmitting ? '' : ' disabled'}`}
                    onClick={() => isButtonEnabled && checkSubmit()}
                    disabled={!isButtonEnabled && !isSubmitting}
                >Xác nhận
                </button>
            </div>
            {isOpenRejectND13 && <PORejectND13LINK
                onClickConfirmBtn={onClickCallBackND13}
                onClickCallBack={onClickCallSuccessfulND13}
                confirmLabel="Quay lại"
                callBackLabel="Xác nhận"
            />}

            {isOpenSuccessfulND13 && <SuccessfulLIND13/>}
        </div>
    </section>
    )

);
};

export default ND13LIConfirm;
