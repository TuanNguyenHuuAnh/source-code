// import 'antd/dist/antd.css';

import React, { Component } from 'react';
import moment from 'moment';
import dayjs from 'dayjs';
import { Select, TimePicker, DatePicker } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { removeAccents, haveCheckedDeadth, isCheckedOnlyHC_HS, validAccidentDate } from '../../../../util/common';
import {CLAIM_TYPE} from '../../../../constants';

let deathDate = undefined;
class AccidentDetail extends Component {

  constructor(props) {
    super(props);


    this.state = {
    }

    this.handlerOnChangeHour = this.onChangeHour.bind(this);
    this.handlerOnChangeDate = this.onChangeDate.bind(this);
    this.handlerOnChangeCity = this.onChangeCity.bind(this);
    this.handlerOnChangeDistrict = this.onChangeDistrict.bind(this);
    this.handlerOnChangeAddress = this.onChangeAddress.bind(this);
    this.handlerOnChangeAccidentDescription = this.onChangeAccidentDescription.bind(this);
    this.handlerOnChangeHealthStatus = this.onChangeHealthStatus.bind(this);
    this.handlerOnChangeNation = this.onChangeNation.bind(this);
  }


  componentDidMount() {
  }

  onChangeHour(value) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.hour = value;
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  onChangeDate(value) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.date = value;
    const facilityList = this.props.facilityList;
    const deathInfo = this.props.deathInfo;
    pAccidentInfo = validAccidentDate(value, pAccidentInfo, facilityList, deathInfo);
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  onChangeCity(value) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    var cityObj = this.props.zipCodeList.find((city) => city.CityCode  === value );
    // console.log("tinh thanh: " + JSON.stringify(cityObj));
    if (cityObj) {
      pAccidentInfo.cityCode = cityObj.CityCode;
      pAccidentInfo.cityName = cityObj.CityName;
      pAccidentInfo.districtCode = undefined;
      pAccidentInfo.districtName = '';
      let districtZZZ = cityObj.lstDistrict.find(district => district.DistrictCode === 'ZZZ');
      if(districtZZZ){
        pAccidentInfo.districtCode = districtZZZ.DistrictCode;
        pAccidentInfo.districtName = districtZZZ.DistrictName;
      }
      this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
    }
  }

  onChangeDistrict(value) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    var cityObj = this.props.zipCodeList.find((city) => city.CityCode  === pAccidentInfo.cityCode );
    if (cityObj) {
      var districtObj = cityObj.lstDistrict.find((district) => district.DistrictCode === value);
      if (districtObj !== null) {
        pAccidentInfo.districtCode = value;
        pAccidentInfo.districtName = districtObj.DistrictName;
        this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
      }
    }
  };

  onChangeAddress(event) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.address = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  onChangeAccidentDescription(event) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.accidentDescription = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  onChangeHealthStatus(event) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.healthStatus = event.target.value;
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  onChangeNation(value) {
    var pAccidentInfo = Object.assign({}, this.props.accidentInfo);
    pAccidentInfo.selectedNation = value;
    this.props.handlerUpdateSubClaimDetailState("accidentInfo", pAccidentInfo);
  }

  render() {
    const disabledDate=(current) => {
      return current && ((current > dayjs().endOf('day')));
    }
    const { Option } = Select;
    const nationList = this.props.nationList;
    const zipCodeList = this.props.zipCodeList;
    const accidentInfo = this.props.accidentInfo;
    const facilityList = this.props.facilityList;
    deathDate = this.props.deathInfo.date;
    let todayDate = new Date();
    let maxAccidentDate = todayDate;
    let maxAccidentDateStr = new Date(maxAccidentDate.getTime() - (maxAccidentDate.getTimezoneOffset() * 60000))
      .toISOString().split("T")[0];
    facilityList.forEach(facility => {
      if (facility.startDate !== null && facility.startDate !== undefined && new Date(facility.startDate) < maxAccidentDate) {
        maxAccidentDateStr = facility.startDate;
      }
    })
    var cityObj = zipCodeList && zipCodeList.find((city) => city.CityCode === accidentInfo.cityCode);
    if(accidentInfo.districtCode === 'ZZZ'){
      accidentInfo.errors.errorList = accidentInfo.errors.errorList.filter(e => e !== "districtName");
    }
    return (
      <div className="info sectab">
        <div className="info__title">
          <h4>Tai nạn</h4>
        </div>
        <div className="info__body">
          {/* Thời gian */}
          <div className="item">
            <h5 className="item__title">Thời gian xảy ra tai nạn</h5>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content" style={{ paddingBottom: '0px', paddingTop: '8px' }}>
                  <div className="datetime-wrapper" style={{ paddingBottom: '8px' }}>
                    <div className="datetime-wrapper__item">
                      <div className={!accidentInfo.hour && (accidentInfo.errors.errorList.indexOf('hour') >= 0) ? "input validate" : "input"}>
                        {/* <input type={!!accidentInfo.hour ? 'time' : 'text'} placeholder="Giờ"
                          onFocus={(event) => {
                            event.target.type = 'time';
                          }}
                          onBlur={(event) => {
                            if (event.target.value === '') {
                              event.target.type = 'text';
                            }
                          }}
                          value={accidentInfo.hour} onChange={(event) => this.onChangeHour(event)} /> */}
                        <TimePicker placeholder="Giờ" format='HH:mm'
                          id='accident_hour'
                          bordered={false} showNow={false}
                          style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }}
                          value={accidentInfo.hour ? moment(accidentInfo.hour) : ""}
                          onChange={(value) => this.onChangeHour(value)}
                          onSelect={(value) => this.onChangeHour(value)} />
                      </div>
                    </div>
                    <div className="datetime-wrapper__item">
                      <div className={!accidentInfo.date && (accidentInfo.errors.errorList.indexOf('date') >= 0) ? "inputdate validate" : "inputdate"}>
                        {/* <input type='date' placeholder="Ngày"
                          max={maxAccidentDateStr}
                          // onFocus={(event) => {
                          //   event.target.type = 'date';
                          // }}
                          // onBlur={(event) => {
                          //   if (event.target.value === '') {
                          //     event.target.type = 'text';
                          //   }
                          // }}
                          value={accidentInfo.date} onChange={(event) => this.handlerOnChangeDate(event)} /> */}
                        <DatePicker placeholder="Ngày" id='accident_date' value={accidentInfo.date ? moment(accidentInfo.date) : ""} disabledDate={disabledDate} onChange={(value) => this.handlerOnChangeDate(value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                      </div>
                    </div>
                  </div>
                </div>
                {((accidentInfo.errors.dateValid.length > 0) || (!accidentInfo.hour && (accidentInfo.errors.errorList.indexOf('hour') >= 0)) || (!accidentInfo.date && (accidentInfo.errors.errorList.indexOf('date') >= 0))) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                    {accidentInfo.errors.dateValid}
                  </span>}
              </div>
            </div>
          </div>
          {/* Nơi xảy ra tai nạn */}
          <div className="item">
            <h5 className="item__title" style={{ marginBottom: '0', marginTop: '-20px' }}>Nơi xảy ra tai nạn</h5>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content"></div>
                <div className="tab__content" style={{padding:'0', marginBottom: '-4px'}}>
                  <div className="checkbox-warpper">
                    <div className="checkbox-item">
                      <div className="round-checkbox">
                        <label className="customradio" style={{ alignItems: 'center' }}>
                          <input type="checkbox" id='accident_isVietnam' checked={(this.props.isVietnam !== null) && this.props.isVietnam} onClick={() => this.props.inVietNam()} />
                          <div className="checkmark"></div>
                          <p claas="text">Việt nam</p>
                        </label>
                      </div>
                    </div>
                    <div className="checkbox-item margin-left-choose-nation">
                      <div className="round-checkbox">
                        <label className="customradio" style={{ alignItems: 'center' }}>
                          <input type="checkbox" checked={(this.props.isVietnam !== null) && !this.props.isVietnam} onClick={() => this.props.notInVietNam()} />
                          <div className="checkmark"></div>
                          <p claas="text">Nước ngoài</p>
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
                {/*Ngoài nước */}
                {(this.props.isVietnam !== null) && !this.props.isVietnam &&
                  <>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div className="tab__content">
                          <div className={!accidentInfo.selectedNation && (accidentInfo.errors.errorList.indexOf('selectedNation') >= 0) ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
                            <Select
                              suffixIcon={<SearchOutlined />}
                              showSearch
                              size='large'
                              style={{ width: '100%', margin: '0' }}
                              width='100%'
                              bordered={false}
                              id='accident_selectedNation'
                              placeholder="Chọn quốc gia"
                              optionFilterProp="name"
                              onChange={(value) => this.handlerOnChangeNation(value)}
                              // onFocus={onFocus}
                              // onBlur={onBlur}
                              // onSearch={onSearch}
                              value={accidentInfo.selectedNation ? accidentInfo.selectedNation : undefined}
                              filterOption={(input, option) =>
                                removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                              }
                            >
                              {nationList.map((nation) => (
                                <Option key={nation.name_vi} name={nation.name_vi}>{nation.name_vi}</Option>
                              ))}
                            </Select>
                          </div>
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!accidentInfo.selectedNation && (accidentInfo.errors.errorList.indexOf('selectedNation') >= 0) &&
                      <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                        Vui lòng chọn quốc gia
                      </span>}
                  </>
                }
                {/* Chọn tỉnh/ Thành phố */}
                {(this.props.isVietnam !== null) && this.props.isVietnam &&
                  <>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div className="tab__content">
                          <div className={!accidentInfo.cityCode && (accidentInfo.errors.errorList && accidentInfo.errors.errorList.indexOf('cityName') >= 0) ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
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
                              id='accident_cityCode'
                              value={accidentInfo.cityCode}
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
                    {!accidentInfo.cityCode && (accidentInfo.errors.errorList && accidentInfo.errors.errorList.indexOf('cityName') >= 0) &&
                      <span style={{ color: 'red', lineHeight: '24px', marginTop: '10px' }}>
                        Vui lòng chọn tỉnh/ Thành phố
                      </span>}
                  </>
                }
                {/* Chọn quận/ huyện */}
                {(this.props.isVietnam !== null) && this.props.isVietnam && accidentInfo.districtCode && accidentInfo.districtCode !== 'ZZZ' &&
                  <>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div className="tab__content">
                          <div className={!accidentInfo.districtCode && (accidentInfo.errors.errorList.indexOf('districtName') >= 0) && accidentInfo.districtCode === 'ZZZ' ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
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
                              id='accident_districtCode'
                              value={accidentInfo.districtCode}
                              filterOption={(input, option) =>
                                removeAccents(option.districtname.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                              }
                            >
                              {cityObj && cityObj.lstDistrict.map((district) => (
                                <Option key={district.DistrictCode} districtname={district.DistrictName}>{district.DistrictName}</Option>
                              ))}
                            </Select>
                          </div>
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>

                    {!accidentInfo.districtCode && (accidentInfo.errors.errorList.indexOf('districtName') >= 0) &&
                      <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                        Vui lòng chọn quận/huyện
                      </span>}
                  </>
                }
                {/* Địa chỉ cụ thể */}
                <div className="tab__content">
                  <div className={(!accidentInfo.address || (accidentInfo.address.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('address') >= 0) ? "input validate" : "input"}>
                    <div className="input__content">
                      <input type="search" placeholder="Địa chỉ cụ thể" maxLength="150" id='accident_address' defaultValue={accidentInfo.address}
                        // onFocus={(e) => e.target.placeholder = ""}
                        // onBlur={(e) => e.target.placeholder = "Địa chỉ cụ thể"}
                             onBlur={this.handlerOnChangeAddress} />
                    </div>
                    <i><img src="../../img/icon/edit.svg" alt="" /></i>
                  </div>
                </div>
                {(!accidentInfo.address || (accidentInfo.address.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('address') >= 0) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                    Vui lòng nhập địa chỉ
                  </span>}
              </div>
            </div>
          </div>
          {/* Diễn biến tai nạn */}
          <div className="item" style={{marginBottom: '0', marginTop: '-16px'}}>
            <h5 className="item__title" style={{marginBottom: '-16px', marginTop: '24px'}}>Diễn biến tai nạn</h5>
            <p className="basic-text-right" style={{ marginBottom: '8px' }}>{accidentInfo.accidentDescription.length}/300 kí tự</p>
            <div className="item__content" style={{ paddingBottom: '8px' }}>
              <div className="tab">
                <div className="tab__content">
                  <div className={(!accidentInfo.accidentDescription || (accidentInfo.accidentDescription.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('accidentDescription') >= 0) ? "input textarea validate" : "input textarea"}>
                    <div className="input__content">
                      <textarea className='eclaim-text-area' placeholder="Diễn biến tai nạn cụ thể" rows="4" maxLength="300"
                        id='accident_accidentDescription'
                        onBlur={this.handlerOnChangeAccidentDescription}
                        defaultValue={accidentInfo.accidentDescription} ></textarea>
                    </div>
                    <i><img src="../../img/icon/edit.svg" alt="" /></i>
                  </div>
                </div>
              </div>
            </div>
            {(!accidentInfo.accidentDescription || (accidentInfo.accidentDescription.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('accidentDescription') >= 0) &&
              <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                Vui lòng nhập diễn biến tai nạn
              </span>}
          </div>
          {/* <button className="circle-btn"></button> */}
        </div>
        {/* Tình trạng thương tật/sức khoẻ hiện tại của Người được bảo hiểm */}
        {!haveCheckedDeadth(this.props.claimCheckedMap) && !isCheckedOnlyHC_HS(this.props.claimCheckedMap) &&
          <div className="item" style={{ padding: '8px 0' }}>
            <h5 className="item__title" style={{ marginBottom: '8px' }}>Tình trạng thương tật/sức khoẻ hiện tại của Người được bảo hiểm</h5>
            <div className="item__content">
              <div className="tab">
                <div className="tab__content">
                  <div className={(!accidentInfo.healthStatus || (accidentInfo.healthStatus.trim().length === 0))  && (accidentInfo.errors.errorList.indexOf('healthStatus') >= 0) ? "input validate" : "input"}>
                    <div className="input__content">
                      <input type="search" placeholder="Tình trạng cụ thể" maxLength="300" defaultValue={accidentInfo.healthStatus}
                        id='accident_healthStatus'
                        onBlur={this.handlerOnChangeHealthStatus} />
                    </div>
                    <i><img src="../../img/icon/edit.svg" alt="" /></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        }
        {(!accidentInfo.healthStatus || (accidentInfo.healthStatus.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('healthStatus') >= 0) &&
          <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
            Vui lòng nhập tình trạng thương tật/sức khoẻ hiện tại
          </span>}
      </div>
    );
  }
}

export default AccidentDetail;
