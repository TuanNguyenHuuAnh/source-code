// import 'antd/dist/antd.min.css';

import React, { Component } from 'react';
import moment from 'moment';
import dayjs from 'dayjs';
import { Select, TimePicker, DatePicker } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { removeAccents, validDeathDate } from '../../../../sdkCommon';
import { FE_BASE_URL } from "../../../../sdkConstant.js";

let accidentDate = undefined;
class DeathDetail extends Component {

  constructor(props) {
    super(props);


    this.state = {
    }

    this.handlerOnChangeHour = this.onChangeHour.bind(this);
    this.handlerOnChangeDate = this.onChangeDate.bind(this);
    this.handlerOnChangeCity = this.onChangeCity.bind(this);
    this.handlerOnChangeDistrict = this.onChangeDistrict.bind(this);
    this.handlerOnChangeAddress = this.onChangeAddress.bind(this);
    this.handlerOnChangeDeathDescription = this.onChangeDeathDescription.bind(this);
  }


  componentDidMount() {
  }

  onChangeHour(value) {
    var pDeathInfo = Object.assign({}, this.props.deathInfo);
    pDeathInfo.hour = value;
    this.props.handlerUpdateSubClaimDetailState("deathInfo", pDeathInfo);
  }

  calculateMaxDeathDate() {
    // Calculate maximum death date
    let isInit = true;
    let todayDate = new Date();
    let maxDeathDate = todayDate;
    let minDeathDateStr = new Date(maxDeathDate.getTime() - (maxDeathDate.getTimezoneOffset() * 60000)).toISOString().split("T")[0];
    // check sickInfo
    const sickInfo = this.props.sickInfo;
    if (sickInfo.sickFoundTime !== null && sickInfo.sickFoundTime !== undefined && sickInfo.sickFoundTime !== ''
      && new Date(sickInfo.sickFoundTime) < maxDeathDate) {
      minDeathDateStr = sickInfo.sickFoundTime;
      isInit = false;
    }
    // check facilityList
    const facilityList = this.props.facilityList;
    let facilityMinStartDate = minDeathDateStr;
    facilityList.forEach(facility => {
      if (facility.startDate !== null && facility.startDate !== undefined && facility.startDate !== ''
        && new Date(facility.startDate) < maxDeathDate) {
        minDeathDateStr = facility.startDate;
        facilityMinStartDate = facility.startDate;
        isInit = false;
      }
    })
    // check accidentInfo
    const accidentInfo = this.props.accidentInfo;
    if (accidentInfo.date !== null && accidentInfo.date !== undefined && accidentInfo.date !== ''
      && new Date(accidentInfo.date) < maxDeathDate) {
      minDeathDateStr = accidentInfo.date;
      isInit = false;
    }
    return {
      'sickFoundTime': isInit ? "" : sickInfo.sickFoundTime,
      'facilityMinStartDate': isInit ? "" : facilityMinStartDate,
      'accidentInfoDate': isInit ? "" : accidentInfo.date,
      'minDeathDateStr': isInit ? "" : minDeathDateStr,
    };
  }


  onChangeDate(value) {
    if (value) {
      var pDeathInfo = Object.assign({}, this.props.deathInfo);
      pDeathInfo.date = value;
      const sickInfo = this.props.sickInfo;
      const accidentInfo = this.props.accidentInfo;
      const facilityList = this.props.facilityList;
      pDeathInfo = validDeathDate(value, pDeathInfo, sickInfo, accidentInfo, facilityList);
      this.props.handlerUpdateSubClaimDetailState("deathInfo", pDeathInfo);
    }
  }

  onChangeCity(value) {
    var pDeathInfo = Object.assign({}, this.props.deathInfo);
    var cityObj = this.props.zipCodeList?.find((city) => city?.CityCode === value);
    if (cityObj !== null) {
      pDeathInfo.cityCode = cityObj?.CityCode;
      pDeathInfo.cityName = cityObj?.CityName;
      pDeathInfo.districtCode = undefined;
      pDeathInfo.districtName = '';
      this.props.handlerUpdateSubClaimDetailState("deathInfo", pDeathInfo);
    }
  }

  onChangeDistrict(value) {
    var pDeathInfo = Object.assign({}, this.props.deathInfo);
    var cityObj = this.props.zipCodeList?.find((city) => city?.CityCode === pDeathInfo.cityCode);

    if (cityObj !== null) {
      var districtObj = cityObj.lstDistrict?.find((district) => district?.DistrictCode === value);
      if (districtObj !== null) {
        pDeathInfo.districtCode = value;
        pDeathInfo.districtName = districtObj?.DistrictName;
        this.props.handlerUpdateSubClaimDetailState("deathInfo", pDeathInfo);
      }
    }
  };

  onChangeAddress(event) {
    var pDeathInfo = Object.assign({}, this.props.deathInfo);
    pDeathInfo.address = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("deathInfo", pDeathInfo);
  }

  onChangeDeathDescription(event) {
    const updatedDeathInfo = { ...this.props.deathInfo };
    updatedDeathInfo.deathDescription = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("deathInfo", updatedDeathInfo);
    // setTimeout(() => {
    //   this.props.handlerUpdateSubClaimDetailState("deathInfo", updatedDeathInfo);
    // }, 500);
  }

  render() {
    function disabledDate(current) {
      return current && ((current > dayjs().endOf('day')));
    }
    const { Option } = Select;
    const zipCodeList = this.props.zipCodeList;
    const deathInfo = this.props.deathInfo;
    accidentDate = this.props.accidentInfo.date;
    var cityObj = zipCodeList.find((city) => city.CityCode === deathInfo.cityCode);

    // Calculate maximum death date
    let minDeathDateStr = this.calculateMaxDeathDate().minDeathDateStr;
    let todayDate = new Date();
    let todayDateStr = new Date(todayDate.getTime() - (todayDate.getTimezoneOffset() * 60000)).toISOString().split("T")[0];
    // let selectedCliInfo = this.state.selectedCliInfo;
    return (
      <div className="info">
        <div className="info__title">
          <h4>Tử vong</h4>
        </div>
        <div className="info__body">
          {/* Thời gian */}
          <div className="item">
            <h5 className="item__title">Thời gian</h5>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content">
                  <div className="datetime-wrapper">
                    <div className="datetime-wrapper__item">
                      <div className={!deathInfo.hour && (deathInfo.errors.errorList.indexOf('hour') >= 0) ? "input validate" : "input"}>
                        <TimePicker placeholder="Giờ" format='HH:mm'
                          id='death_hour'
                          bordered={false} showNow={false}
                          style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }}
                          value={deathInfo.hour ? moment(deathInfo.hour) : ""}
                          onChange={(value) => this.onChangeHour(value)}
                          onSelect={(value) => this.onChangeHour(value)} />
                      </div>
                    </div>
                    <div className="datetime-wrapper__item">
                      <div className={!deathInfo.date && (deathInfo.errors.errorList.indexOf('date') >= 0) ? "inputdate validate" : "inputdate"}>
                        <DatePicker placeholder="Ngày" id='death_date' value={deathInfo.date ? moment(deathInfo.date) : ""} disabledDate={disabledDate} onChange={(value) => this.handlerOnChangeDate(value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                      </div>
                    </div>
                  </div>
                </div>
                {((deathInfo.errors.dateValid.length > 0) || (!deathInfo.hour && (deathInfo.errors.errorList.indexOf('hour') >= 0)) || (!deathInfo.date && (deathInfo.errors.errorList.indexOf('date') >= 0))) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                    {deathInfo.errors.dateValid ? deathInfo.errors.dateValid : 'Quý khách cần nhập ngày giờ tử vong hợp lệ.'}
                  </span>}
              </div>
            </div>
          </div>
          {/* Nơi tử vong */}
          <div className="item">
            <h5 className="item__title" style={{ marginBottom: '0' }}>Nơi tử vong</h5>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content"></div>
                {/* <div className="tab__content">
                  <div className="checkbox-warpper">
                    <div className="checkbox-item">
                      <div className="round-checkbox">
                        <label className="customradio">
                          <input type="checkbox" />
                          <div className="checkmark"></div>
                          <p claas="text">Trong nước</p>
                        </label>
                      </div>
                    </div>
                    <div className="checkbox-item">
                      <div className="round-checkbox">
                        <label className="customradio">
                          <input type="checkbox" />
                          <div className="checkmark"></div>
                          <p claas="text">Nước ngoài</p>
                        </label>
                      </div>
                    </div>
                  </div>
                </div> */}
                {/* Chọn tỉnh/ Thành phố */}
                <div className="dropdown inputdropdown">
                  <div className="dropdown__content">
                    <div className="tab__content">
                      <div className={!deathInfo.cityName && (deathInfo.errors.errorList.indexOf('cityName') >= 0) ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
                        <Select
                          suffixIcon={<SearchOutlined />}
                          showSearch
                          size='large'
                          style={{ width: '100%', margin: '0' }}
                          width='100%'
                          bordered={false}
                          placeholder="Chọn tỉnh/ Thành phố"
                          optionFilterProp="cityname"
                          onChange={this.handlerOnChangeCity}
                          id='death_cityCode'
                          value={deathInfo.cityCode}
                          filterOption={(input, option) =>
                            removeAccents(option.cityname.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                          }
                        >
                          {zipCodeList.map((city) => (
                            <Option key={city.CityCode} cityname={city.CityName}>{city.CityName}</Option>
                          ))}
                        </Select>
                      </div>
                    </div>
                  </div>
                  <div className="dropdown__items"></div>
                </div>
                {!deathInfo.cityName && (deathInfo.errors.errorList.indexOf('cityName') >= 0) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                    Vui lòng chọn tỉnh/ Thành phố
                  </span>}
                {/* Chọn quận/ huyện */}
                <div className="dropdown inputdropdown">
                  <div className="dropdown__content">
                    <div className="tab__content">
                      <div className={!deathInfo.districtName && (deathInfo.errors.errorList.indexOf('districtName') >= 0) ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
                        <Select
                          suffixIcon={<SearchOutlined />}
                          showSearch
                          size='large'
                          style={{ width: '100%', margin: '0' }}
                          width='100%'
                          bordered={false}
                          placeholder="Chọn quận/ huyện"
                          optionFilterProp="districtname"
                          onChange={this.handlerOnChangeDistrict}
                          id='death_districtCode'
                          value={deathInfo.districtCode}
                          filterOption={(input, option) =>
                            removeAccents(option.districtname.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                          }
                        >
                          {(cityObj != null) && cityObj.lstDistrict.map((district) => (
                            <Option key={district.DistrictCode} districtname={district.DistrictName}>{district.DistrictName}</Option>
                          ))}
                        </Select>
                      </div>
                    </div>
                  </div>
                  <div className="dropdown__items"></div>
                </div>
                {!deathInfo.districtName && (deathInfo.errors.errorList.indexOf('districtName') >= 0) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                    Vui lòng chọn quận/ huyện
                  </span>}
                {/* Địa chỉ cụ thể */}
                <div className="tab__content">
                  <div className={!deathInfo.address && (deathInfo.errors.errorList.indexOf('address') >= 0) ? "input validate" : "input"}>
                    <div className="input__content">
                      <input type="search" placeholder="Địa điểm tử vong" maxLength="300" id='death_address' defaultValue={deathInfo.address}
                        // onFocus={(e) => e.target.placeholder = ""}
                        // onBlur={(e) => e.target.placeholder = "Địa chỉ cụ thể"}
                             onBlur={this.handlerOnChangeAddress} />
                    </div>
                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                  </div>
                </div>
                {!deathInfo.address && deathInfo.errors.errorList.indexOf('address') >= 0 &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                    Vui lòng nhập địa điểm tử vong
                  </span>}
              </div>
            </div>
          </div>
          {/* Nguyên nhân tử vong */}
          <div className="item">
            <p className="item__title" style={{ marginBottom: '-16px' }}>Nguyên nhân tử vong</p>
            <p className="basic-text-right" style={{ marginBottom: '8px' }}>{deathInfo.deathDescription.length}/300 kí tự</p>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content">
                  <div className={!deathInfo.deathDescription && (deathInfo.errors.errorList.indexOf('deathDescription') >= 0) ? "input textarea validate" : "input textarea"}>
                    <div className="input__content">
                      <textarea className='eclaim-text-area' placeholder="Nguyên nhân dẫn đến tử vong" rows="4" maxLength="300"
                        id='death_deathDescription'
                        onBlur={this.handlerOnChangeDeathDescription}
                        defaultValue={deathInfo.deathDescription}
                      ></textarea>
                    </div>
                    <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                  </div>
                </div>
                {!deathInfo.deathDescription && (deathInfo.errors.errorList.indexOf('deathDescription') >= 0) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top' }}>
                    Vui lòng nhập nguyên nhân dẫn đến tử vong
                  </span>}
              </div>
            </div>
          </div>
        </div>
        {((this.props.claimDetailState.isCheckedClaimTypeTreatment) || (this.props.claimTypeState.isAccidentClaim)) &&
          <div className='channel-dash-no-margin-md'></div>
        }
      </div>
    );
  }
}

export default DeathDetail;
