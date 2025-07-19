// import 'antd/dist/antd.css';

import React, { Component } from 'react';
import { Select} from 'antd';
import {getZipCodeMasterData} from '../util/APIUtils';
import {PageScreen} from '../constants';
import {trackingEvent, cpSaveLog} from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';

class Address extends Component {

  constructor(props) {
    super(props);


    this.state = {
      zipCodeList: null,
      cityCode: undefined,
      cityName: '',
      districtCode: undefined,
      districtName: '',
      specifficAddress: '',
      isEnableSubmit: false,
      isShowDistrict: false
    }

    this.handlerOnChangeCity = this.onChangeCity.bind(this);
    this.handlerOnChangeDistrict = this.onChangeDistrict.bind(this);
    this.handlerOnChangeSpecifficAddress = this.onChangeSpecifficAddress.bind(this);
  }


  componentDidMount() {
    // Get ZipCode master data
    const zipCodeRequest = {
      jsonDataInput: {
        Project: 'mcp', Type: 'city_district', Action: 'ZipCode',
      }
    };
    getZipCodeMasterData(zipCodeRequest)
      .then(Res => {
        const Response = Res.GetMasterDataByTypeResult;
        if (Response.Result === 'true' && Response.ClientProfile !== null) {
          this.setState({ zipCodeList: Response.ClientProfile });
        }
      })
      .catch(error => {
        this.props.history.push('/maintainence');
      });
  }

  onChangeCity(value) {
    let jsonState = this.state;
    let cityObj = this.state.zipCodeList.find((city) => city.CityCode  === value );
    if (cityObj) {
      jsonState.cityCode = cityObj.CityCode;
      jsonState.cityName = cityObj.CityName;
      jsonState.districtCode = undefined;
      jsonState.districtName = '';
      jsonState.isEnableSubmit = this.isEnableSubmitBtn();
      let districtZZZ = cityObj.lstDistrict.find(district => district.DistrictCode === 'ZZZ');
      if(districtZZZ){
        jsonState.isShowDistrict = false;
        jsonState.districtCode = districtZZZ.DistrictCode;
        jsonState.districtName = districtZZZ.DistrictName;
      }
      this.setState(jsonState);
    }
  }

  onChangeDistrict(value) {
    let jsonState = this.state;
    let cityObj = this.state.zipCodeList.find((city) => city.CityCode  === jsonState.cityCode );
    if (cityObj) {
      var districtObj = cityObj.lstDistrict.find((district) => district.DistrictCode === value);
      if (districtObj !== null) {
        jsonState.districtCode = value;
        jsonState.districtName = districtObj.DistrictName;
        jsonState.isEnableSubmit = this.isEnableSubmitBtn();
        this.setState(jsonState);
      }
    }
  };

  onChangeSpecifficAddress(event) {
    const isEnableSubmit = this.isEnableSubmitBtn();
    console.log('onChangeSpecifficAddress=' + isEnableSubmit);
    this.setState({specifficAddress: event.target.value, isEnableSubmit: isEnableSubmit})
  }

  isEnableSubmitBtn() {
    return this.state.cityCode && this.state.cityName && this.state.districtCode && this.state.districtName && this.state.specifficAddress
  }

  submitUpdateInfo=()=> {
    let districtName = this.state.districtCode === 'ZZZ' ? '' : ', ' + this.state.districtName;
    this.props.handlerOnChangeAddress(this.state.specifficAddress + districtName + ',' + this.state.cityName)
  }

  render() {
    const zipCodeList = this.state.zipCodeList;
    var cityObj = zipCodeList && zipCodeList.find((city) => city.CityCode === this.state.cityCode);
    return (
      <div className="info sectab">
        <div className="info__title" style={{padding: '12px 0'}}>
          <h4>Cập nhật địa chỉ liên hệ</h4>
        </div>
        <div className="info__body">
          {/* Địa chỉ */}
          <div className="item">
            {/* <h5 className="item__title" style={{ marginBottom: '0', marginTop: '-20px' }}></h5> */}
            <div className="item__content">
              <div className="tab">
                <div className="tab__content"></div>
                {/* Chọn tỉnh/ Thành phố */}
                {zipCodeList &&
                  <>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div className="tab__content">
                          <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                            <Select
                              // suffixIcon={<SearchOutlined />}
                              // showSearch
                              size='large'
                              style={{ width: '100%', margin: '0' }}
                              width='100%'
                              bordered={false}
                              placeholder="Chọn tỉnh/ Thành phố"
                              optionFilterProp="cityname"
                              onChange={this.handlerOnChangeCity}
                              id='accident_cityCode'
                              value={this.state.cityCode}
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
                    {/* {!accidentInfo.cityCode && (accidentInfo.errors.errorList && accidentInfo.errors.errorList.indexOf('cityName') >= 0) &&
                      <span style={{ color: 'red', lineHeight: '24px', marginTop: '10px' }}>
                        Vui lòng chọn tỉnh/ Thành phố
                      </span>} */}
                  </>
                }
                {/* Chọn quận/ huyện */}
                { (zipCodeList && this.state.isShowDistrict) &&
                  <>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div className="tab__content">
                          <div className="input" style={{ padding: '10px 0px 10px 0px' }}>
                            <Select
                              // suffixIcon={<SearchOutlined />}
                              // showSearch
                              size='large'
                              style={{ width: '100%', margin: '0' }}
                              width='100%'
                              bordered={false}
                              placeholder="Chọn quận/ huyện"
                              optionFilterProp="districtname"
                              onChange={this.handlerOnChangeDistrict}
                              id='accident_districtCode'
                              value={this.state.districtCode}
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

                    {/* {!accidentInfo.districtCode && (accidentInfo.errors.errorList.indexOf('districtName') >= 0) &&
                      <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                        Vui lòng chọn quận/huyện
                      </span>} */}
                  </>
                }
                {/* Địa chỉ cụ thể */}
                <div className="tab__content">
                  <div className="input">
                    <div className="input__content">
                      <input type="search" placeholder="Địa chỉ cụ thể" maxLength="150" id='accident_address' defaultValue={this.state.specifficAddress}
                             onChange={this.handlerOnChangeSpecifficAddress} 
                             onSelect={this.handlerOnChangeSpecifficAddress}
                             />
                    </div>
                    <i><img src="../../img/icon/edit.svg" alt="" /></i>
                  </div>
                </div>
                {/* {(!accidentInfo.address || (accidentInfo.address.trim().length === 0)) && (accidentInfo.errors.errorList.indexOf('address') >= 0) &&
                  <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                    Vui lòng nhập địa chỉ
                  </span>} */}
              </div>
            </div>
          </div>
          <LoadingIndicator area="submit-loading"/>
          <div className="bottom-btn">
              <button
                  className={this.state.isEnableSubmit ? "btn btn-primary" : "btn btn-primary disabled"}
                  disabled={!this.state.isEnableSubmit}
                  onClick={(event) => {
                      if (event.target.className === "btn btn-primary") {
                          trackingEvent(
                              "Thông tin khách hàng",
                              `Web_Open_${PageScreen.SETTINGACCOUNTPAGE}`,
                              `Web_Open_${PageScreen.SETTINGACCOUNTPAGE}`,
                          );
                          cpSaveLog(`Web_Open_${PageScreen.SETTINGACCOUNTPAGE}`);
                          this.submitUpdateInfo();
                      }
                  }}>Xác nhận
              </button>
          </div>
        </div>
      </div>
    );
  }
}

export default Address;
