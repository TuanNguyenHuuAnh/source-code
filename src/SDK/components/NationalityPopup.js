import React from 'react';
// import '../common/Common.css';
import {FE_BASE_URL} from '../sdkConstant';

function NationalityPopup({ closePopup, ConfirmGo, isSubmitting, USNationality, USPermanent, USTaxDeclared, onChangeUSNationality, onChangeUSPermanent, onChangeUSTaxDeclared }) {

    return (

        <div className="popup confirm special show">
            <div className="popup__card">
                <div className="confirm-card">
                    <div className="confirm-card-header">
                        <div className="confirm-card-close-btn"
                                onClick={closePopup}>
                            <img src={FE_BASE_URL  + "/img/icon/close-icon.svg"} alt="closebtn"
                                    className="local-popup-btn"/>
                        </div>
                    </div>
                    <div className="confirm-card-body">
                        <p>Để tuân thủ luật thuế FATCA của Hoa Kỳ, Quý khách vui lòng đánh dấu vào ô thích hợp dưới đây (nếu có):</p>
                        <div className="checkbox-warpper">
                            <label className="checkbox" htmlFor="isAmericanNationality">
                                <input type="checkbox" name="isAmericanNationality"
                                        id="isAmericanNationality"
                                        checked={USNationality === 'Yes'}
                                        onChange={(event) => onChangeUSNationality(event.target.checked)}/>
                                <div className="checkmark">
                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                </div>
                            </label>
                            <p className="text">Quốc tịch Hoa Kỳ</p>
                        </div>
                        <div className="checkbox-warpper">
                            <label className="checkbox">
                                <input type="checkbox" name="isAmericanResidence"
                                        id="isAmericanResidence"
                                        checked={USPermanent === 'Yes'}
                                        onChange={(event) => onChangeUSPermanent(event.target.checked)}/>
                                <div className="checkmark">
                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                </div>
                            </label>
                            <p className="text">Địa chỉ thường trú tại Hoa Kỳ</p>
                        </div>
                        <div className="checkbox-warpper">
                            <label className="checkbox">
                                <input type="checkbox" name="isAmericanTax" id="isAmericanTax"
                                        checked={USTaxDeclared === 'Yes'}
                                        onChange={(event) => onChangeUSTaxDeclared(event.target.checked)}/>
                                <div className="checkmark">
                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                </div>
                            </label>
                            <p className="text">Thực hiện khai báo thuế tại Hoa Kỳ</p>
                        </div>
                        
                        {((USNationality === 'Yes') || (USPermanent === 'Yes') || (USTaxDeclared === 'Yes')) &&
                        <>
                            <div className="dash-border"></div>
                            <div className="content" style={{paddingTop: '16px'}}>
                                <p style={{lineHeight: '17px', color: '#de181f', fontSize: '14px'}}>
                                Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài Dịch vụ Khách hàng để được hướng dẫn điền Tờ khai theo mẫu {(USNationality !== 'Yes') && (USPermanent !== 'Yes')? 'W-8BEN': 'W-9'}  và nộp về Văn phòng Dai-ichi Life Việt Nam.
                                </p>
                            </div>
                        </>
                        }
                    </div>
                    <div className="btn-footer" style={{flexDirection: 'column'}}>
                        {isSubmitting && <div style={{
                            display: 'flex', justifyContent: 'center', alignItems: 'center'
                        }}>
                            <LoadingIndicator area="submit-init-claim"/>
                        </div>}
                        <button
                            className={`${isSubmitting ? 'disabled' : ''} btn btn-primary `}
                            disabled={isSubmitting}
                            style={{
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                                marginTop: 16
                            }}
                            id="fatca_id" onClick={ConfirmGo}>
                            Tiếp tục
                        </button>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default NationalityPopup;