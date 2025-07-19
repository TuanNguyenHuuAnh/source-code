import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import { getSession } from '../sdkCommon';
import {
    FE_BASE_URL,
    IS_MOBILE,
    CATEGORY_NAME_MAPPING
  } from '../sdkConstant';


class ChangeValue extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proccessType: this.props.proccessType
        };
    }

    componentDidMount() {

    }

    componentDidUpdate() {
        if (this.props.proccessType !== this.state.proccessType) {
            this.setState({proccessType: this.props.proccessType});
        }
    }

    render() {
        return (
            <>
                {/* <div className="contractform__body"> */}
                    <div className="info">
                        <div className="info__title">ĐIỀU CHỈNH PHÍ DỰ TÍNH ĐỊNH KỲ</div>
                        <div className="info__title nd13-padding-bottom16">{CATEGORY_NAME_MAPPING[this.props.proccessType]}</div>
                        <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                <div className="tab">
                                        <div className="info__content">
                                            <div className="info__content-item">
                                                <div className={this.props.errorMessage?"input money validate":"input money"}>

                                                    <div className="input__content">
                                                        {this.props.amount &&
                                                            <label>Phí dự tính định kỳ mới</label>
                                                        }
                                                        <NumberFormat
                                                            id='txtInputAmount'
                                                            // inputmode="numeric"
                                                            type='seach'
                                                            value={this.props.amount}
                                                            thousandSeparator={'.'} decimalSeparator={','}
                                                            suffix={' VNĐ'}
                                                            allowNegative={false}
                                                            onValueChange={(values) => this.props.handlerInputAmount(values.value)}
                                                            placeholder='Phí dự tính định kỳ mới'
                                                        />
                                                    </div>

                                                </div>
                                            </div>
                                            {this.props.errorMessage &&
                                                <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                                                    {this.props.errorMessage}
                                                </span>
                                            }

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

export default ChangeValue;
