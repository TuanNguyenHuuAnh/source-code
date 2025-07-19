import React from 'react';
import './styles.css';
import {DATA_DECREE, SUBMISSION_TYPE_MAPPING, IS_MOBILE } from "../../../sdkConstant";
import {getSession, callbackAppOpenLink, getUrlParameter} from "../../../sdkCommon";
import iconClose from "../../../img/icon/close-icon.svg";

const POWarningND13 = (props) => {
    const {
        onClickExtendBtn = () => {},
    } = props;
    let from = getUrlParameter("fromApp");
    return (
        <>
            <div className="popup special show" id="modal-common">
                <div className="popup__card po-warning-nd13">
                    <div className="modal-common-card" style={{ padding: '32px 20px 40px' }}>
                        <div className="picture-wrapper">
                            <div className="modal-header" style={{ background: '#ffffff' }}>
                                <i className="close-btn"> <img src={iconClose} alt="closebtn" className="btn"
                                                               onClick={onClickExtendBtn}/></i>
                            </div>
                            <div className="picture">
                                <img src={DATA_DECREE.COMPLY_WITH_DECREE.image} alt="popup-hosobg" />
                            </div>
                        </div>
                        <div className="content">
                            <p style={{ textAlign: 'center', fontSize: 14, fontWeight: 500, lineHeight: '21px', color: '#292929', marginTop: 24 }}>
                                Nhằm tuân thủ Nghị định số 13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ dữ liệu cá nhân (DLCN), Quý khách có thể được yêu cầu cung cấp số điện thoại/hộp thư điện tử của Chủ thể dữ liệu (NĐBH và/hoặc những người khác liên quan đến giao dịch) để (những) người này xác nhận trực tuyến về việc đồng ý cung cấp dữ liệu cá nhân. Chi tiết sẽ được hướng dẫn tại các màn hình tương ứng tiếp theo. <br/> Lưu ý rằng, đồng thời với việc lập Yêu cầu trực tuyến này, sự đồng ý của Chủ thể dữ liệu cho phép DLVN xử lý DLCN sẽ có hiệu lực áp dụng ngay cả trường hợp Yêu cầu trực tuyến này bị hủy bỏ hoặc bị từ chối.
                            </p>
                            <p style={{ textAlign: 'center', fontSize: 14, fontWeight: 500, lineHeight: '21px', color: '#292929', marginTop: 12 }}>
                                {DATA_DECREE.COMPLY_WITH_DECREE.msg}
                                {getSession(IS_MOBILE)?(
                                    <a className='simple-brown' href='#' onClick={()=>callbackAppOpenLink('https://dai-ichi-life.com.vn/dich-vu-19/thong-bao-ve-viec-xu-ly-du-lieu-ca-nhan-2221', from)} style={{display: 'inline', textAlign: 'center'}}> Tại đây</a>
                                ):(
                                    <a className='simple-brown' href='https://dai-ichi-life.com.vn/dich-vu-19/thong-bao-ve-viec-xu-ly-du-lieu-ca-nhan-2221' target='_blank' style={{display: 'inline', textAlign: 'center'}}> Tại đây</a>
                                )}
                            </p>
                            <div className="btn-wrapper">
                                <button className="btn btn-confirm" onClick={onClickExtendBtn}>Tiếp tục</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>
        </>
    );
};

export default POWarningND13;
