import React, { useEffect, useState } from 'react';
import './styles.css';
import PORejectND13 from "../PORejectND13/PORejectND13";
import LoadingIndicatorBasic from "../../../../common/LoadingIndicatorBasic";
import { getSession, removeLocal } from "../../../../util/common";
import { CLIENT_ID, FULL_NAME, CLAIM_SAVE_LOCAL, FE_BASE_URL, USER_LOGIN } from "../../../../constants";
import { CLAIM_STATE } from "../../../CreateClaim/CreateClaim";

const ND13POConfirm = (props) => {
    const [relateModalData, setRelateModalData] = useState({
        refPurpose: {
            value: '', error: '',
        }, refOtherPurpose: {
            value: '', error: '',
        },
    });

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
        return value.toLowerCase() !== 'no';
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
        removeLocal(CLAIM_SAVE_LOCAL + FE_BASE_URL + getSession(USER_LOGIN) + props.selectedCliInfo.ClientID);
        setTimeout(() => {
            window.location.href = '/';
        }, 100);
        setIsOpenRejectND13(false);
    };

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const backStep = () => {
        props.handlerBackToPrevSubmisionState(props.claimSubmissionState)
    }

    const checkPOAndLIEquality = (PO, LI) => {
        const clientIDLI = LI.ClientID;
        return clientIDLI === PO;
    };

    useEffect(() => {
        const isRefPurposeValid = checkIfNo(relateModalData.refPurpose.value);
        const isRefOtherPurposeValid = checkIfNo(relateModalData.refOtherPurpose.value);
        if (!(isRefPurposeValid && isRefOtherPurposeValid)) {
            setIsOpenRejectND13(true);
        }

        return () => setIsOpenRejectND13(false);

    }, [relateModalData]);

    return (<section className="sccontract-warpper" id="scrollAnchor">
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
                                    <p>Kèm <br />chứng từ</p>
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
                                        <span className="simple-brown" style={{ zIndex: '30' }}
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
                            <div className="optionalform" style={{paddingBottom: '0'}}>
                                <div className="relate-modal-content">
                                    <p className="modal-body-content">Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày
                                        17/04/2023 về
                                        bảo vệ DLCN, Chủ thể dữ liệu cần xác nhận đồng ý cho phép DLVN xử lý
                                        DLCN.</p>
                                    <div>
                                        <div className="body-frame">
                                            <p className="body-frame-title">Bên mua bảo hiểm (BMBH) <span
                                                className="bold-text" style={{ fontSize: '12px' }}>{getSession(FULL_NAME)}</span> đồng ý và
                                                cam kết:</p>
                                            <p className="body-frame-content">
                                                Trong quá trình tư vấn, giao kết, thực hiện HĐBH, giải quyết Quyền
                                                lợi
                                                bảo
                                                hiểm và
                                                các công việc khác liên quan, DLVN có trách nhiệm xử lý Dữ liệu cá
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
                                                nhạy cảm phù hợp với các mục đích <span className='basic-bold' style={{ fontSize: '12px', fontWeight: 700 }}>(Mục đích: 1- Định danh và nhận
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
                                                <span style={{ fontSize: 12, color: 'blue', cursor: "pointer" }} onClick={() => {
                                                    window.open('https://www.dai-ichi-life.com.vn', '_blank');
                                                }}> https://www.dai-ichi-life.com.vn</span><span onClick={() => {
                                                    window.open('https://kh.dai-ichi-life.com.vn/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf', '_blank');
                                                }}
                                                    style={{ fontSize: 12, color: '#985801', cursor: "pointer", fontWeight: 700 }}> (“Quy định BV&XLDLCN”)</span>.
                                                <br/>Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 

                                            </p>
                                        </div>
                                        <br />
                                        <div className="body-func">
                                            <h5 className="basic-semibold" style={{ fontSize: 16 }}>Mục đích
                                                1,2,3</h5>
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
                                            <h5 className="basic-semibold" style={{ fontSize: 16 }}>Mục đích 4</h5>
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
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <img className="decor-clip" src="../../img/mock.svg" alt="" />
                <img className="decor-person" src="../../img/person.png" alt="" />
            </div>
            

            {props.isSubmitting && <LoadingIndicatorBasic />}
            <div style={{maxWidth: '600px', textAlign: 'center', justifyContent: 'center'}}>
                <p style={{ color: 'red', margin: '12px 0 0 0', display: 'flex', fontSize: '12px', fontWeight: '700' }}>Lưu ý rằng, đồng thời với việc lập Yêu cầu Giải quyết quyền lợi bảo hiểm, sự đồng ý của Chủ thể dữ liệu (Bên mua bảo hiểm, Người được bảo hiểm, Cha/Mẹ/Người giám hộ của Người được bảo hiểm dưới 18 tuổi) cho phép DLVN xử lý DLCN sẽ có hiệu lực áp dụng ngay cả trường hợp Yêu cầu Giải quyết quyền lợi bảo hiểm này bị hủy bỏ hoặc bị từ chối.</p>
            </div>                                    
            <div className="bottom-btn">
                <button
                    className={`${props.isSubmitting || (!checkIfNo(relateModalData.refPurpose.value) || !checkIfNo(relateModalData.refOtherPurpose.value) || !relateModalData.refPurpose.value || !relateModalData.refOtherPurpose.value) ? 'disabled' : ''} btn btn-primary`}
                    onClick={() => checkSubmit()}
                    disabled={props.isSubmitting || (!checkIfNo(relateModalData.refPurpose.value) || !checkIfNo(relateModalData.refOtherPurpose.value) || !relateModalData.refPurpose.value || !relateModalData.refOtherPurpose.value)}>Xác
                    nhận
                </button>
            </div>
            {isOpenRejectND13 &&
                <PORejectND13 onClickConfirmBtn={() => onClickCallBackND13()} onClickCallBack={() => onClickCallRedirectHome()} />}
        </div>
    </section>);
};

export default ND13POConfirm;
