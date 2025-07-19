// import 'antd/dist/antd.min.css';

import React, {Component} from 'react';

import FacilityDetail from './FacilityDetail';
import {AutoComplete, DatePicker, Select} from 'antd';
import dayjs from 'dayjs';
import moment from 'moment';
import {SearchOutlined} from '@ant-design/icons';
import CustomSelectContainer from '../../../../components/CustomSelectContainer';
import {OTHER_HOSPITAL} from '../../../../constants';
import {removeAccents, validSickDate} from '../../../../util/common';

// let accidentDate = undefined;
class TreatmentDetail extends Component {

    constructor(props) {
        super(props);


        this.state = {
            facilityDeleting: false,
            deleteFacilityIndex: null
        }

        this.handlerOnChangeSickInfoDate = this.onChangeSickInfoDate.bind(this);
        this.handlerOnChangeSickInfoPlace = this.onChangeSickInfoPlace.bind(this);
        this.handlerOnChangeSickInfoCityName = this.onChangeSickInfoCityName.bind(this);
        this.handlerOnChangeFacilityDetail = this.onChangeFacilityDetail.bind(this);
        this.handlerAddFacility = this.addFacility.bind(this);
        this.handlerRemoveFacility = this.removeFacility.bind(this);
        this.handleUpdateFacility = this.updateFacility.bind(this);
        this.handleDeleteFacilityCornfirm = this.deleteFacilityConfirm.bind(this);
        this.handleOnChangeSickFirstSympton = this.onChangeSickFirstSympton.bind(this);
    }


    componentDidMount() {
    }


    onChangeSickInfoDate(value) {
        let pSickInfo = Object.assign({}, this.props.sickInfo);
        pSickInfo.sickFoundTime = value;
        const facilityList = this.props.facilityList;
        const deathInfo = this.props.claimDetailState.deathInfo;
        pSickInfo = validSickDate(value, pSickInfo, facilityList, deathInfo);
        this.props.handlerUpdateSubClaimDetailState("sickInfo", pSickInfo);
    }

    onChangeSickInfoPlace(value) {
        const pSickInfo = Object.assign({}, this.props.sickInfo);
        pSickInfo.sickFoundFacility = value;
        this.props.handlerUpdateSubClaimDetailState("sickInfo", pSickInfo);
    }

    onChangeSickInfoCityName(value) {
        const pSickInfo = Object.assign({}, this.props.sickInfo);
        pSickInfo.cityName = value;
        this.props.handlerUpdateSubClaimDetailState("sickInfo", pSickInfo);
    }

    onChangeFacilityDetail(index, facilityDetail) {
        const pFacilityList = Object.assign([], this.props.facilityList);
        pFacilityList[index] = facilityDetail;
        this.props.handlerUpdateSubClaimDetailState("facilityList", pFacilityList);
    }

    onChangeSickFirstSympton(value) {
        const pSickInfo = Object.assign({}, this.props.sickInfo);
        pSickInfo.firstSympton = value;
        this.props.handlerUpdateSubClaimDetailState("sickInfo", pSickInfo);
    }

    addFacility() {
        const pFacilityList = Object.assign([], this.props.facilityList);
        pFacilityList.push({
            cityCode: undefined,
            cityName: '',
            name: '',
            treatmentType: '',
            startDate: '',
            endDate: '',
            diagnosis: '',
            otherDiagnostic: '', //Kết quả chuẩn đoán khác
            selectedHospital: '', //Chọn cơ sở y tế
            otherHospital: '',
            invoiceList: [{
                InvoiceAmount: '',
                InvoiceNumber: '',
                errorList: []
            }],
            isOtherCompanyRelated: '',
            otherCompanyInfo: {
                companyName: '',
                paidAmount: ''
            },
            errors: {
                dateValid: '',
                errorList: []
            }
        });
        this.props.handlerUpdateSubClaimDetailState("facilityList", pFacilityList);
    }

    removeFacility(facilityDetailIndex) {
        const pFacilityList = Object.assign([], this.props.facilityList);
        pFacilityList.splice(facilityDetailIndex, 1);
        this.props.handlerUpdateSubClaimDetailState("facilityList", pFacilityList);
    };

    updateFacility(index, fDetail) {
        const pFacilityList = Object.assign([], this.props.facilityList);
        pFacilityList[index] = fDetail;
        this.props.handlerUpdateSubClaimDetailState("facilityList", pFacilityList);
    }

    deleteFacilityConfirm = (facilityIndex) => {
        this.setState({deleteFacilityIndex: facilityIndex, facilityDeleting: true})
    }

    render() {
        const hospitalList = [...this.props.hospitalList];
        let other = {
            "ProviderCode": "",
            "HospitalName": "CSYT Khác",
            "HospitalNameSearch": "CSYT Khac",
            "StateName": "",
            "Address": ""
        };
        hospitalList.push(other);

        const disabledDate = (current) => {
            return current && (current > dayjs().endOf('day'));
        }
        const {Option} = Select;
        const cancelDeleteFacility = () => {
            this.setState({deleteFacilityIndex: null, facilityDeleting: false});
        }


        const removeFacility = (facilityDetailIndex) => {
            const pFacilityList = Object.assign([], this.props.facilityList);
            pFacilityList.splice(facilityDetailIndex, 1);
            this.props.handlerUpdateSubClaimDetailState("facilityList", pFacilityList);
            this.setState({deleteFacilityIndex: null, facilityDeleting: false});
        };

        const SelectOtherHospital = (value) => {
            this.props.SelectOtherSickInfoPlace(value);
            if (!value) {
                this.onChangeSickInfoPlace('');
            }
        }
        let sickInfo = this.props.sickInfo;
        return (
            <>
                {!this.props.claimDetailState.isTreatmentAt &&
                    <div className="channel-dash-no-margin-lg" style={{marginBottom: '0'}}>
                        <div className="list__item">
                            <p className='claim-questionaire'>Người được bảo hiểm có được khám/ điều trị tại cơ sở y tế
                                ?</p>
                        </div>
                        <div className="item__content">
                            <div className="tab">
                                <div className="tab__content" style={{marginLeft: '4px'}}>
                                    <div className="checkbox-warpper">

                                        <div className="checkbox-item">
                                            <div className="round-checkbox">
                                                <label className="customradio" style={{alignItems: 'center'}}>
                                                    <input type="checkbox"
                                                           checked={(this.props.claimDetailState.isTreatmentAt !== null) && this.props.claimDetailState.isTreatmentAt}
                                                           onClick={() => this.props.HaveTreatmentAt()}/>
                                                    <div className="checkmark"></div>
                                                    <p className="text basic-padding-right">Có</p>
                                                </label>
                                            </div>
                                        </div>

                                        <div className="checkbox-item restore-margin-left-answer-no">
                                            <div className="round-checkbox">
                                                <label className="customradio" style={{alignItems: 'center'}}>
                                                    <input type="checkbox"
                                                           checked={(this.props.claimDetailState.isTreatmentAt !== null) && !this.props.claimDetailState.isTreatmentAt}
                                                           onClick={() => this.props.NoHaveTreatmentAt()}/>
                                                    <div className="checkmark"></div>
                                                    <p className="text">Không</p>
                                                </label>
                                            </div>
                                        </div>

                                        <div className="checkbox-item"></div>
                                        <div className="checkbox-item"></div>
                                        <div className="checkbox-item"></div>
                                        <div className="checkbox-item"></div>

                                    </div>
                                </div>
                            </div>

                        </div>
                        {!this.props.claimDetailState.isTreatmentAt && this.props.claimDetailState.errorMsg &&
                            <span style={{
                                color: 'red',
                                lineHeight: '12px',
                                marginTop: '8px',
                                marginBottom: '10px',
                                verticalAlign: 'top'
                            }}>
                {this.props.claimDetailState.errorMsg}
              </span>
                        }
                    </div>
                }

                {this.props.claimDetailState.isTreatmentAt &&
                    <div className="info">
                        {/* {Thong tin benh} */}
                        {this.props.isSickClaim &&
                            <div className="info__title" style={{marginTop: '16px'}}>
                                <h4>Bệnh</h4>
                            </div>
                        }
                        {this.props.isSickClaim &&
                            <div className="info__body">
                                <div className="item">
                                    <h5 className="item__title">Thời điểm xuất hiện dấu hiệu bệnh/triệu chứng</h5>
                                    <div className="item__content">
                                        <div className="tab">
                                            <div className="tab__content">
                                                <div
                                                    className={(this.props.sickInfo.errors.dateValid.length > 0) || (!this.props.sickInfo.sickFoundTime && this.props.claimDetailState.sickInfo.errors.errorList && this.props.claimDetailState.sickInfo.errors.errorList.indexOf('sickFoundTime') >= 0) ? "input validate" : "input"}>
                                                    <div className="input__content" style={{width: '100%'}}>
                                                        <label>Thời điểm khởi phát bệnh</label>
                                                        <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                    id="sick_sickFoundTime" disabledDate={disabledDate}
                                                                    value={this.props.sickInfo.sickFoundTime ? moment(this.props.sickInfo.sickFoundTime) : ""}
                                                                    onChange={(value) => this.handlerOnChangeSickInfoDate(value)}
                                                                    format="DD/MM/YYYY" style={{
                                                            width: '100%',
                                                            margin: '0px',
                                                            padding: '0px',
                                                            fontSize: '1.4rem',
                                                            border: '0'
                                                        }}/>
                                                    </div>
                                                </div>
                                            </div>
                                            {this.props.sickInfo.errors.dateValid.length > 0 ? (
                                                <span style={{
                                                    color: 'red',
                                                    lineHeight: '20px',
                                                    marginTop: '4px',
                                                    marginBottom: '20px',
                                                    verticalAlign: 'top'
                                                }}>{this.props.sickInfo.errors.dateValid}</span>
                                            ) : (
                                                !this.props.sickInfo.sickFoundTime && this.props.claimDetailState.sickInfo.errors.errorList && this.props.claimDetailState.sickInfo.errors.errorList.indexOf('sickFoundTime') >= 0 &&
                                                <span style={{
                                                    color: 'red',
                                                    lineHeight: '20px',
                                                    marginTop: '4px',
                                                    marginBottom: '20px',
                                                    verticalAlign: 'top'
                                                }}>{'Vui lòng chọn thời điểm khởi phát bệnh'}</span>
                                            )}
                                        </div>
                                    </div>
                                </div>
                                <div className="item">
                                    <h5 className="item__title">Triệu chứng/ chẩn đoán đầu tiên</h5>
                                    <div className="item__content">
                                        <div className="tab">
                                            <div className="tab__content">
                                                <div
                                                    className={(!this.props.sickInfo.firstSympton || (this.props.sickInfo.firstSympton.trim().length === 0)) && this.props.claimDetailState.sickInfo.errors.errorList && this.props.claimDetailState.sickInfo.errors.errorList.indexOf('firstSympton') >= 0 ? "input validate" : "input"}>
                                                    <div className="input__content">
                                                        <label>Triệu chứng/chẩn đoán ban đầu</label>
                                                        <input placeholder="Ví dụ: Ho lao" type="search" maxLength="300"
                                                               id="sick_firstSympton"
                                                               defaultValue={this.props.sickInfo.firstSympton}
                                                               onBlur={(e) => this.handleOnChangeSickFirstSympton(e.target.value)}
                                                        />
                                                    </div>
                                                    <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                </div>
                                            </div>
                                            {(!this.props.sickInfo.firstSympton || (this.props.sickInfo.firstSympton.trim().length === 0)) && this.props.claimDetailState.sickInfo.errors.errorList && this.props.claimDetailState.sickInfo.errors.errorList.indexOf('firstSympton') >= 0 &&
                                                <span style={{
                                                    color: 'red',
                                                    lineHeight: '28px',
                                                    marginTop: '4px',
                                                    marginBottom: '20px',
                                                    verticalAlign: 'top'
                                                }}>{'Vui lòng nhập triệu chứng/chẩn đoán ban đầu'}</span>}

                                        </div>
                                    </div>
                                </div>
                                <div className="item">
                                    <h5 className="item__title">Cơ sở y tế thăm khám lần đầu tiên</h5>
                                    <div className="item__content">
                                        <div className="tab">
                                            {/* Chọn cơ sở y tế khám/điều trị */}
                                            {!sickInfo.sickFoundFacilityOther ? (
                                                <div className="dropdown inputdropdown">
                                                    <div className="dropdown__content">
                                                        <div className="tab__content">
                                                            <div
                                                                className={!sickInfo.sickFoundFacility && sickInfo.errors.errorList && ((sickInfo.errors.errorList.indexOf('cityName') >= 0) || (sickInfo.errors.errorList.indexOf('districtName') >= 0)) ? "input validate" : "input"}
                                                                style={{padding: '8px 0px 10px 0px'}}>
                                                                <AutoComplete
                                                                    suffixIcon={<SearchOutlined/>}
                                                                    showSearch
                                                                    size='large'
                                                                    style={{width: '100%', margin: '0'}}
                                                                    width='100%'
                                                                    bordered={false}
                                                                    placeholder="Cơ sở y tế thăm khám lần đầu tiên"
                                                                    optionFilterProp="name"
                                                                    onChange={(value) => this.handlerOnChangeSickInfoPlace(value)}
                                                                    onSelect={(value) => this.props.SelectedSickInfoPlaceChosen(value)}
                                                                    // onBlur={(e) => this.props.SelectedSickInfoPlaceChosen(e.target.value)}
                                                                    id="sick_sickFoundFacility"
                                                                    value={sickInfo.sickFoundFacility}
                                                                    notFoundContent={(
                                                                        <div className="nice-message">
                                                                            <div style={{whiteSpace: 'normal'}}><p>Chưa
                                                                                tìm thấy kết quả phù hợp.</p>
                                                                                <p>Vui lòng kiểm tra lại từ khoá hoặc
                                                                                    chọn <span className='basic-bold'>“CSYT khác”</span> để
                                                                                    cung cấp thông tin CSYT cho <span
                                                                                        className='basic-bold'>Những lần khám/điều trị.</span>
                                                                                </p>
                                                                                <p><a className='simple-brown'
                                                                                      onClick={() => SelectOtherHospital(OTHER_HOSPITAL)}> CSYT
                                                                                    khác</a></p></div>
                                                                        </div>
                                                                    )}
                                                                    filterOption={(input, option) =>
                                                                        (removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0) || (removeAccents(option.statename.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase()).replace(/  +/g, ' ')) >= 0)
                                                                    }
                                                                >
                                                                    {hospitalList.map((hospital) => (
                                                                        // <Option key={hospital.HospitalName} name={hospital.HospitalName}>{hospital.HospitalName}</Option>


                                                                        <Option key={hospital.HospitalName}
                                                                                name={hospital.HospitalName}
                                                                                statename={hospital.StateName}>
                                                                            <CustomSelectContainer data={hospital}
                                                                                                   keywork={sickInfo.sickFoundFacility}/>
                                                                        </Option>

                                                                    ))}
                                                                </AutoComplete>
                                                                <span className="ant-select-arrow" unselectable="on"
                                                                      aria-hidden="true"
                                                                      style={{userSelect: 'none'}}><span role="img"
                                                                                                         aria-label="search"
                                                                                                         className="anticon anticon-search"><svg
                                                                    viewBox="64 64 896 896" focusable="false"
                                                                    data-icon="search" width="1em" height="1em"
                                                                    fill="currentColor" aria-hidden="true"><path
                                                                    d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path></svg></span></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            ) : (
                                                <>
                                                    <div className="dropdown inputdropdown">
                                                        <div className="dropdown__content">
                                                            <div className="tab__content">
                                                                <div
                                                                    className={sickInfo.errors.errorList && ((sickInfo.errors.errorList.indexOf('cityName') >= 0) || (sickInfo.errors.errorList.indexOf('districtName') >= 0)) ? "input validate" : "input"}
                                                                    style={{padding: '8px 0px 10px 0px'}}>
                                                                    <AutoComplete
                                                                        suffixIcon={<SearchOutlined/>}
                                                                        showSearch
                                                                        size='large'
                                                                        style={{width: '100%', margin: '0'}}
                                                                        width='100%'
                                                                        bordered={false}
                                                                        placeholder="Cơ sở y tế thăm khám lần đầu tiên"
                                                                        optionFilterProp="name"
                                                                        onChange={(value) => SelectOtherHospital(value)}
                                                                        onSelect={(value) => this.props.SelectedSickInfoPlaceChosen(value)}
                                                                        value={sickInfo.sickFoundFacilityOther}
                                                                        id="sick_sickFoundFacilityOther"
                                                                        defaultValue={sickInfo.sickFoundFacilityOther}
                                                                        filterOption={(input, option) =>
                                                                            (removeAccents(option.name.toLowerCase()).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0) || (option.statename.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").indexOf(input.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/  +/g, ' ')) >= 0)
                                                                        }
                                                                    >
                                                                        {hospitalList.map((hospital) => (
                                                                            <Option key={hospital.HospitalName}
                                                                                    name={hospital.HospitalName}
                                                                                    statename={hospital.StateName}>
                                                                                <CustomSelectContainer data={hospital}
                                                                                                       keywork={sickInfo.sickFoundFacility}/>
                                                                            </Option>

                                                                        ))}
                                                                    </AutoComplete>
                                                                    <span className="ant-select-arrow" unselectable="on"
                                                                          aria-hidden="true"
                                                                          style={{userSelect: 'none'}}><span role="img"
                                                                                                             aria-label="search"
                                                                                                             className="anticon anticon-search"><svg
                                                                        viewBox="64 64 896 896" focusable="false"
                                                                        data-icon="search" width="1em" height="1em"
                                                                        fill="currentColor" aria-hidden="true"><path
                                                                        d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path></svg></span></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div className="dropdown inputdropdown">
                                                        <div className="dropdown__content">
                                                            <div className="tab__content">

                                                                <div
                                                                    className={sickInfo.errors.errorList.indexOf('cityName') >= 0 ? "input validate" : "input"}
                                                                    style={{padding: '10px 0px 10px 0px'}}>
                                                                    <Select
                                                                        suffixIcon={<SearchOutlined/>}
                                                                        showSearch
                                                                        size='large'
                                                                        style={{width: '100%', margin: '0'}}
                                                                        width='100%'
                                                                        bordered={false}
                                                                        placeholder="Chọn tỉnh/ Thành phố"
                                                                        optionFilterProp="cityname"
                                                                        onChange={this.handlerOnChangeSickInfoCityName}
                                                                        id="sick_cityName"
                                                                        value={sickInfo.cityName ? sickInfo.cityName : undefined}
                                                                        filterOption={(input, option) =>
                                                                            option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                                        }
                                                                    >
                                                                        {this.props.zipCodeList.map((city) => (
                                                                            <Option key={city.CityName}
                                                                                    cityname={city.CityName}>
                                                                                {city.CityName}
                                                                            </Option>
                                                                        ))}
                                                                    </Select>
                                                                </div>

                                                            </div>
                                                        </div>
                                                        <div className="dropdown__items"></div>
                                                    </div>
                                                    {!sickInfo.cityName && sickInfo.errors.errorList.indexOf('cityName') >= 0 &&
                                                        <span style={{
                                                            color: 'red',
                                                            lineHeight: '28px',
                                                            marginTop: '4px',
                                                            marginBottom: '20px',
                                                            verticalAlign: 'top'
                                                        }}>
                              Vui lòng chọn tỉnh/ Thành phố
                            </span>}

                                                    <div className="dropdown inputdropdown">
                                                        <div className="dropdown__content">
                                                            <div className="tab__content">
                                                                <div
                                                                    className={sickInfo.errors.errorList.indexOf('sickFoundFacility') >= 0 ? "input validate" : "input"}>
                                                                    <div className="input__content" style={{
                                                                        paddingLeft: '7px',
                                                                        paddingRight: '7px'
                                                                    }}>
                                                                        {sickInfo.sickFoundFacility &&
                                                                            <label style={{marginLeft: '2px'}}>Cơ sở y
                                                                                tế thăm khám lần đầu tiên</label>
                                                                        }
                                                                        <input type="search"
                                                                               placeholder="Nhập cơ sở y tế thăm khám lần đầu tiên"
                                                                               maxLength="300"
                                                                               id="sick_sickFoundFacility"
                                                                               value={sickInfo.sickFoundFacility}
                                                                               onChange={(e) => this.handlerOnChangeSickInfoPlace(e.target.value)}/>
                                                                    </div>
                                                                    <i><img src="../../img/icon/edit.svg" alt=""/></i>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {sickInfo.errors.errorList.indexOf('sickFoundFacility') >= 0 &&
                                                        <span style={{
                                                            color: 'red',
                                                            lineHeight: '28px',
                                                            marginTop: '4px',
                                                            marginBottom: '20px',
                                                            verticalAlign: 'top'
                                                        }}>
                              Vui lòng nhập cơ sở y tế thăm khám lần đầu tiên
                            </span>}


                                                </>
                                            )}

                                            {!sickInfo.sickFoundFacility && sickInfo.errors.errorList && ((sickInfo.errors.errorList.indexOf('cityName') >= 0) || (sickInfo.errors.errorList.indexOf('districtName') >= 0)) &&
                                                <span style={{
                                                    color: 'red',
                                                    lineHeight: '28px',
                                                    marginTop: '4px',
                                                    marginBottom: '20px',
                                                    verticalAlign: 'top'
                                                }}>
                          Vui lòng chọn cơ sở y tế thăm khám lần đầu tiên
                        </span>}
                                        </div>
                                    </div>
                                </div>
                                <div className='channel-dash-no-margin-md'></div>
                            </div>
                        }
                        <div className="info__body">
                            {this.props.facilityList != null && this.props.facilityList.map((facility, index) => (
                                <FacilityDetail key={index}
                                                zipCodeList={this.props.zipCodeList}
                                                hospitalList={this.props.hospitalList}
                                                hospitalResultList={this.props.hospitalResultList}
                                                facilityIndex={index}
                                                facilityDetail={facility}
                                                selectedHospital={this.props.selectedHospital}
                                                diagnosis={this.props.diagnosis}
                                                claimCheckedMap={this.props.claimCheckedMap}
                                                claimDetailState={this.props.claimDetailState}
                                                handlerValidateAccidentDate={this.props.handlerValidateAccidentDate}
                                                handlerOnChangeFacilityDetail={this.handlerOnChangeFacilityDetail}
                                                handleUpdateFacility={this.handleUpdateFacility}
                                                SelectedHospital={this.props.SelectedHospital}
                                                SelectedHospitalChosen={this.props.SelectedHospitalChosen}
                                                SelectedDiagnosticResult={this.props.SelectedDiagnosticResult}
                                                SelectOtherHospital={this.props.SelectOtherHospital}
                                                SelectOtherDiagnostic={this.props.SelectOtherDiagnostic}
                                                handleDeleteFacilityCornfirm={this.handleDeleteFacilityCornfirm}
                                                enterOtherCompanyName={this.props.enterOtherCompanyName}
                                                enterOtherCompanyPaid={this.props.enterOtherCompanyPaid}
                                                isOtherCompanyRelated={this.props.isOtherCompanyRelated}
                                                sickInfo={sickInfo}
                                                handlerRemoveFacility={this.handlerRemoveFacility}/>
                            ))}

                            <button className="circle-btn" onClick={this.handlerAddFacility}
                                    style={{marginTop: '12px', marginLeft: '-7px'}}>
                                <i className="circle-plus" style={{marginBottom: '16px'}}>
                                    <img src="../../img/icon/plus.svg" alt="circle-plus" className="plus-sign"/>
                                </i>
                                <label className="plus-information" style={{marginBottom: '16px'}}>Thêm lần khám/điều
                                    trị bệnh</label>
                            </button>
                        </div>

                    </div>
                }

                {this.state.facilityDeleting &&
                    <div className="popup option-popup  show">
                        <div className="popup__card">
                            <div className="optioncard" style={{lineHeight: '20px', paddingBottom: '0px'}}>
                                <p>Quý khách có muốn xóa thông tin <br/>lần khám/điều trị được chọn không?</p>
                                <div className="btn-wrapper">
                                    <button className="btn btn-primary"
                                            onClick={() => removeFacility(this.state.deleteFacilityIndex)}>Đồng ý
                                    </button>
                                    <button className="btn btn-nobg" onClick={() => cancelDeleteFacility()}>Không đồng
                                        ý
                                    </button>
                                </div>
                                <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt=""
                                                                             onClick={() => cancelDeleteFacility()}/></i>
                            </div>
                        </div>
                        <div className="popupbg"></div>
                    </div>
                }
                {/*<Index*/}
                {/*    title={DATA_DECREE.PROFILE_WARNING_INFO.title}*/}
                {/*    msg={DATA_DECREE.PROFILE_WARNING_INFO.msg}*/}
                {/*    imagePath={DATA_DECREE.PROFILE_WARNING_INFO.image}*/}
                {/*    isExtendFooter={true}*/}
                {/*    btnExtendName={'Tôi sẽ nộp hồ sơ giấy'}*/}
                {/*    onClickExtendBtn={() => {*/}
                {/*    }}*/}
                {/*/>*/}
                {/*<PaymentPOPersonalInfo />*/}
            </>

        );
    }
}

export default TreatmentDetail;
