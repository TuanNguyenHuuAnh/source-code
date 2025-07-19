import React, {useEffect, useState} from 'react';
import './styles.css';
import PORejectND13 from "../PORejectND13/PORejectND13";
import LoadingIndicatorBasic from "../../../LoadingIndicatorBasic";
import LoadingIndicator from '../../../LoadingIndicator2';
import {getSession, removeLocal, deleteND13DataTemp, callbackAppOpenLink} from "../../../sdkCommon";
import {SDK_ROLE_PO, UPDATE_POLICY_INFO_SAVE_LOCAL, FE_BASE_URL, USER_LOGIN, SUBMISSION_TYPE_MAPPING, IS_MOBILE} from "../../../sdkConstant";

const ND13POConfirm = (props) => {
    const [relateModalData, setRelateModalData] = useState(props.relateModalDataPO);

    const [isOpenRejectND13, setIsOpenRejectND13] = useState(false);
    const [purposeAnswer, setPurposeAnswer] = useState('');
    

    const handlePurposeChange = (name, value) => {
        setRelateModalData({
            ...relateModalData, [name]: {
                ...relateModalData[name], value: value,
            },
        });
    };

    const checkIfNo = (value) => {
        return value?.toLowerCase() !== 'no';
    };

    const checkSubmit = () => {
        props.onCompletedND13();

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

    const onClickCallRedirectHome = () => {
        if (props.appType === 'CLOSE') {
            if (props.proccessType === 'RIN') {
                removeLocal(UPDATE_POLICY_INFO_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + props.policyNo);
            }
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
    };

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const backStep=() => {
        props.handlerBackToPrevSubmisionState(props.claimSubmissionState)
    }

    const checkPOAndLIEquality = (PO, LI) => {
        const clientIDLI = LI.ClientID;
        return clientIDLI === PO;
    };

    useEffect(() => {
        const isRefPurposeValid = checkIfNo(relateModalData?.refPurpose?.value);
        const isRefOtherPurposeValid = checkIfNo(relateModalData?.refOtherPurpose?.value);
        if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
            setIsOpenRejectND13(true);
        }
        if (props.updateRelateModalDataPO) {
            props.updateRelateModalDataPO(relateModalData);
        }
        return () => setIsOpenRejectND13(false);
    }, [relateModalData, props.clientName]);

    return (
    // <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance nd13-padding-bottom96 nd13" style={{backgroundColor: '#f5f3f2'}}>
            <div className={((props?.proccessType === 'FAP') || (props?.proccessType === 'FPM') || (props?.proccessType === 'EML') || (props?.proccessType === 'ADR') || (props?.proccessType === 'CCI'))?"stepform nd13-margin-top185":"stepform"} /*style={{marginTop: 170}}*/>
                <div className="info">
                    <div className="info__body">
                        <LoadingIndicator area="submit-init-claim"/>
                        <div className="optionalform-isActivewrapper">
                            <div className="optionalform" style={{paddingBottom: '0'}}>
                                <div className="relate-modal-content">
                                    <p className="modal-body-content">Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày
                                        17/04/2023 về
                                        bảo vệ DLCN, Chủ thể dữ liệu cần xác nhận đồng ý cho phép DLVN xử lý
                                        DLCN.</p>
                                    <div>
                                        <div className="body-frame">
                                            <p className="body-frame-title">Bên mua bảo hiểm (BMBH) <span
                                                className="bold-text" style={{fontSize: '12px'}}>{props.clientName}</span> đồng ý và
                                                cam kết:</p>
                                            <p className="body-frame-content">
                                                Trong quá trình tư vấn, giao kết, thực hiện Hợp đồng bảo hiểm (HĐBH), giải quyết Quyền
                                                lợi
                                                bảo
                                                hiểm và
                                                các công việc khác liên quan, Dai-ichi Life Việt Nam (DLVN) có trách nhiệm xử lý Dữ liệu cá
                                                nhân
                                                (DLCN) của
                                                Tôi/Chúng tôi đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP
                                                ngày
                                                17/04/2023
                                                về bảo vệ DLCN. Tôi/Chúng tôi xác nhận đã được DLVN thông báo về
                                                việc
                                                xử lý
                                                DLCN,
                                                đã đọc và đồng ý cho phép DLVN được quyền xử lý DLCN bao gồm DLCN
                                                cơ
                                                bản và
                                                DLCN
                                                nhạy cảm phù hợp với các mục đích <span className='basic-bold' style={{fontSize: '12px', fontWeight:700}}>(Mục đích: 1- Định danh và nhận
                                                biết
                                                Khách
                                                hàng;
                                                2- Giao kết HĐBH; 3- Thực hiện HĐBH; 4 - Quản lý, đánh giá hoạt động
                                                kinh
                                                doanh và
                                                tuân thủ của DLVN)</span> cụ thể tại phần xác nhận bên dưới và toàn bộ nội
                                                dung
                                                của
                                                Quy
                                                định bảo vệ và xử lý DLCN được đăng tải trên trang chủ của DLVN:
                                                {getSession(IS_MOBILE)?(
                                                    <>
                                                    <a style={{fontSize: 12, color: 'blue', cursor: "pointer", display: 'inline'}} onClick={()=>callbackAppOpenLink('https://dai-ichi-life.com.vn', props.from)} href='#'
                                                     > https://www.dai-ichi-life.com.vn
                                                    </a>
                                                    <a onClick={()=>callbackAppOpenLink('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf', props.from)} href='#' 
                                                    style={{fontSize: 12, color: '#985801', cursor: "pointer", fontWeight: 700, display: 'inline'}}> (“Quy định BV&XLDLCN”)
                                                    </a>.
                                                    </>
                                                ):(
                                                    <>
                                                    <span style={{fontSize: 12, color: 'blue', cursor: "pointer"}} onClick={() => {
                                                        window.open('https://www.dai-ichi-life.com.vn', '_blank');
                                                    }}> https://www.dai-ichi-life.com.vn
                                                    </span>
                                                    <span onClick={() => {
                                                    window.open('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf', `${getSession(IS_MOBILE)?'_self':'_blank'}`);
                                                }}
                                                    style={{fontSize: 12, color: '#985801', cursor: "pointer", fontWeight: 700}}> (“Quy định BV&XLDLCN”)
                                                    </span>.
                                                    </>
                                                )}

                                                <br/>Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 

                                            </p>
                                        </div>
                                        <br/>
                                        <div className="body-func">
                                            <h5 className="basic-semibold" style={{fontSize: 16}}>Mục đích
                                                1,2,3</h5>
                                            <div className="checkbox-warpper">
                                                <div className="checkbox-item">
                                                    <div className="round-checkbox">
                                                        <label className="customradio">
                                                            <input
                                                                type="radio"
                                                                value="yes"
                                                                name="refPurpose"
                                                                checked={(relateModalData.refPurpose.value === 'yes') || (props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                                onChange={() => handlePurposeChange('refPurpose', 'yes')}
                                                                disabled={(props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                            />
                                                            <div className={(props.disableEdit && !props.agentKeyInPOSelfEdit)?"checkmark-readonly":"checkmark"}></div>
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
                                                                disabled={(props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                            />
                                                            <div className={(props.disableEdit && !props.agentKeyInPOSelfEdit)?"checkmark-readonly":"checkmark"}></div>
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
                                                                checked={(relateModalData.refOtherPurpose.value === 'yes') || (props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                                onChange={() => handlePurposeChange('refOtherPurpose', 'yes')}
                                                                disabled={(props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                            />
                                                            <div className={(props.disableEdit && !props.agentKeyInPOSelfEdit)?"checkmark-readonly":"checkmark"}></div>
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
                                                                disabled={(props.disableEdit && !props.agentKeyInPOSelfEdit)}
                                                            />
                                                            <div className={(props.disableEdit && !props.agentKeyInPOSelfEdit)?"checkmark-readonly":"checkmark"}></div>
                                                            <p className="text">Không đồng ý</p>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt=""/>
                <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
            </div>

            {props.isSubmitting && <LoadingIndicatorBasic/>}
            <div style={{maxWidth: '600px', textAlign: 'center', justifyContent: 'center'}}>
                <p style={{ color: 'red', margin: '12px 0 0 0', display: 'flex', fontSize: '12px', fontWeight: '700', lineHeight: '18px' }}>Lưu ý rằng, đồng thời với việc lập Yêu cầu trực tuyến này, sự đồng ý của Chủ thể dữ liệu cho phép DLVN xử lý DLCN sẽ có hiệu lực áp dụng ngay cả trường hợp Yêu cầu trực tuyến này bị hủy bỏ hoặc bị từ chối.</p>
            </div>
            {getSession(IS_MOBILE)&&
            <div className='nd13-padding-bottom64'></div>
            }
            <div className="bottom-btn">
                <button
                    className={`${props.isSubmitting || ((!checkIfNo(relateModalData.refPurpose.value) || !checkIfNo(relateModalData.refOtherPurpose.value) || !relateModalData.refPurpose.value || !relateModalData.refOtherPurpose.value) && !(props.disableEdit && !props.agentKeyInPOSelfEdit)) ? 'disabled' : ''} btn btn-primary`}
                    onClick={() => checkSubmit()}
                    disabled={props.isSubmitting || ((!checkIfNo(relateModalData.refPurpose.value) || !checkIfNo(relateModalData.refOtherPurpose.value) || !relateModalData.refPurpose.value || !relateModalData.refOtherPurpose.value) && !(props.disableEdit && !props.agentKeyInPOSelfEdit))}>Xác
                    nhận
                </button>
            </div>
            {(props.systemGroupPermission?.[0]?.Role === SDK_ROLE_PO) && (props.disableEdit && !props.agentKeyInPOSelfEdit) &&
                <button className="btn btn-callback" onClick={() => props.notAgree()}>Hủy/Điều chỉnh thông tin</button>
            }
            {isOpenRejectND13 &&
                <PORejectND13 onClickConfirmBtn={()=>onClickCallBackND13()} onClickCallBack={()=>onClickCallRedirectHome()}/>}
        </div>
    // </section>
);
};

export default ND13POConfirm;
