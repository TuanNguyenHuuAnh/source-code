import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession, getUrlParameter, cpSaveLogSDK, trackingEvent } from '../sdkCommon';
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING,
    PageScreen
  } from '../sdkConstant';


class ChangeValueReview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proccessType: this.props.proccessType
        };
    }

    componentDidMount() {
        let from = getUrlParameter("fromApp");
        cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`, this.state.apiToken, this.state.deviceId, this.state.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.REVIEW}`,
            from
        );
    }

    render() {
        return (
            <>
                {/* <div className="contractform__body"> */}
                    <div className="info">
                        <div className="info__title">XÁC NHẬN THÔNG TIN ĐIỀU CHỈNH</div>
                        <div className="info__title nd13-padding-bottom16">{CATEGORY_NAME_MAPPING[this.props.proccessType]}</div>
                        <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                <div className="tab">
                                        <div className="info__content">
                                            <div className="info__content-item">
                                                <div className="input money disabled">

                                                    <div className="input__content">
                                                        <label>Phí dự tính định kỳ</label>
                                                        <NumberFormat className="basic-uppercase"
                                                            id='txtInputAmount'
                                                            type='seach'
                                                            value={this.props.amount}
                                                            thousandSeparator={'.'} decimalSeparator={','}
                                                            suffix={' VNĐ'}
                                                            allowNegative={false}
                                                            disabled
                                                        />
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                {/* </div> */}
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

export default ChangeValueReview;
