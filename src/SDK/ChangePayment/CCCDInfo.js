import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession } from '../../util/common';
import AgreeChangePopup from '../../components/AgreeChangePopup';
import ImageViewerBase64 from "../../Followup/ImageViewerBase64";
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING
  } from '../../constants';


class CCCDInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <>
                <div className="info__body" style={{width: '100%'}}>
                    <h3 className="contractform__head-title">THÔNG TIN CCCD</h3>
                    <p>
                        Quý khách vui lòng cung cấp những thông tin sau đây theo CCCD mới nhất để xác minh người nhận tiền tại bưu cục thuộc Tổng Công ty Bưu Điện Việt Nam hoặc tại Ngân hàng:
                    </p>
                    <div className="checkbox-wrap basic-column">
                        <div className="tab-wrapper">
                            <div className="tab"> {/* Thêm key vào đây */}
                                <div className="checkbox-warpper">
                                    <label className="checkbox" htmlFor={'change-cccd'}>
                                        <input
                                            type="checkbox"
                                            name={'change-cccd'}
                                            id={'change-cccd'}
                                            //checked={this.props.checkedPaymentMethod?.includes('P5') ? true : false}
                                            //onClick={() => this.props.handlerToggleCheckedPaymentMethod('P5')}
                                        />
                                        <div className="checkmark">
                                            <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                    </label>
                                    <p className="basic-text2">Điều chỉnh thông tin CCCD</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <div className="bottom-text"
                                    style={{'maxWidth': '594px', backgroundColor: '#f5f3f2'}}>
                                <p style={{textAlign: 'justify'}}>
                                    <span className="red-text basic-bold">Lưu ý: </span><span
                                    style={{color: '#727272'}}>
                                        Thông tin điều chỉnh/thay đổi sẽ được cập nhật cho (các) hợp đồng bảo hiểm của Bên mua bảo hiểm.
                                    </span>
                                </p>
                            </div>
                        </div>
                        <div className="item">
                        <div className="item__content" style={{margin: '14px 16px 24px'}}>
                                {(this.state.attachmentState.attachmentList
                                        && this.state.attachmentState.attachmentList.length > 0) &&
                                    <div
                                        className="img-upload-wrapper not-empty"
                                        style={{
                                            background: '#EDEBEB',
                                            padding: '6px'
                                        }}>
                                        {!isEmpty(this.state.attachmentState.attachmentList) && <ImageViewerBase64 images={this.state.attachmentState.attachmentList}/>}
                                    </div>
                                }
                            </div>
                        </div>
                </div>
                {!getSession(IS_MOBILE) &&
                <div className="other_option" id="other-option-toggle"
                    onClick={() => this.props.goBack()}>
                    <p>Tiếp tục</p>
                    <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                </div>
                }

            </>
        )
    }
}

export default CCCDInfo;
