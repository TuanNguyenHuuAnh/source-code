import 'antd/dist/antd.min.css';

import React, {Component} from 'react';
import NumberFormat from 'react-number-format';
import OtherCompany from './OtherCompany';
import {AutoComplete, DatePicker, Select} from 'antd';
import {SearchOutlined} from '@ant-design/icons';
import dayjs from 'dayjs';
import moment from 'moment';
import CustomSelectContainer from '../../../../components/CustomSelectContainer';
import CustomSelectICDContainer from '../../../../components/CustomSelectICDContainer';
import {removeAccents, validFacilityDate} from '../../../../util/common';
import {CLAIM_TYPE, FE_BASE_URL, OTHER_HOSPITAL} from '../../../../constants';
import NoticePopup from '../../../../components/NoticePopup';
import parse from 'html-react-parser';

let totalAmount = 0;
let inputICD = '';
let accidentDate = undefined;
let deathDate = undefined;
class FacilityDetail extends Component {

  constructor(props) {
    super(props);


    this.state = {
      deleteFacilityIndex: null,
      deleteInvoiceIndex: null,
      invoiceDeleting: false,
      typingICD: '',
      toggle: false,
      hospitalResultList: this.props.hospitalResultList,
      showNotice: false
    }

    this.handlerOnChangeTreatmentType = this.onChangeTreatmentType.bind(this);
    this.handlerOnChangeFacilityName = this.onChangeFacilityName.bind(this);
    this.handlerOnChangeCity = this.onChangeCity.bind(this);
    this.handlerOnChangeDate = this.onChangeDate.bind(this);

  }


  componentDidMount() {
  }



  onChangeTreatmentType(event) {
    if (event.target.checked) {
      var pFacilityDetail = Object.assign({}, this.props.facilityDetail);
      pFacilityDetail.treatmentType = event.target.value;
      pFacilityDetail.errors.dateValid = '';
      this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
    }
  }

  onChangeFacilityName(event) {
    var pFacilityDetail = Object.assign({}, this.props.facilityDetail);
    pFacilityDetail.name = event.target.value;
    this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
  }

  onChangeCity(value) {
    var pFacilityDetail = Object.assign({}, this.props.facilityDetail);
    var cityObj = this.props.zipCodeList.find((city) => city.CityName === value);
    if (cityObj !== null) {
      pFacilityDetail.cityCode = cityObj.CityCode;
      pFacilityDetail.cityName = cityObj.CityName;
      pFacilityDetail.districtCode = undefined;
      pFacilityDetail.districtName = '';
      this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
    }
  }

  onChangeDate(dateType, value) {
    var pFacilityDetail = Object.assign({}, this.props.facilityDetail);
    pFacilityDetail[dateType] = value;
    const sickInfo = this.props.sickInfo;
    const accidentInfo = this.props.claimDetailState.accidentInfo;
    const deathInfo = this.props.claimDetailState.deathInfo;
    pFacilityDetail = validFacilityDate(pFacilityDetail, value, sickInfo, accidentInfo, deathInfo);
    this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
  };



  render() {
    const closeNotice = () => {
      this.setState({ showNotice: false });
    }
    const showNotice = () => {
      this.setState({ showNotice: true });
    }
    const { Option } = Select;
    const zipCodeList = this.props.zipCodeList;
    const hospitalList = [...this.props.hospitalList];
    let other = {
      "ProviderCode": "",
      "HospitalName": "CSYT Khác",
      "HospitalNameSearch": "CSYT Khac",
      "StateName": "",
      "DistrictName": "",
      "Address": ""
    };
    hospitalList.push(other);
    const hospitalResultList = this.props.hospitalResultList;
    const fDetail = this.props.facilityDetail;
    totalAmount = 0;
    // var cityObj = zipCodeList.find((city) => city.CityCode === fDetail.cityCode);
    let todayDate = new Date();
    let todayDateStr = new Date(todayDate.getTime() - (todayDate.getTimezoneOffset() * 60000))
      .toISOString()
      .split("T")[0];
    let endDate = fDetail.endDate ? new Date(fDetail.endDate) : new Date();
    accidentDate = this.props.claimDetailState.accidentInfo.date;
    deathDate = this.props.claimDetailState.deathInfo.date;
    function disabledEndDate(current) {
      // let max = dayjs().endOf('day');
      // if (deathDate) {
      //   let max = (dayjs(new Date(deathDate)).endOf('day'));
      //   if (accidentDate) {
      //     let minT = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     if (fDetail.startDate) {
      //       let min = ((dayjs(new Date(fDetail.startDate)).add(-1, 'day')).endOf('day'));
      //       return current && ((current > dayjs().endOf('day')) || (current < min) || (current < minT) || (current > max));
      //     }
      //     return current && ((current > dayjs().endOf('day') || (current < minT) || (current > max)));
      //   } else {
      //     if (fDetail.startDate) {
      //       let min = ((dayjs(new Date(fDetail.startDate)).add(-1, 'day')).endOf('day'));
      //       return current && ((current > dayjs().endOf('day')) || (current < min) || (current > max));
      //     }
      //     return current && ( (current > dayjs().endOf('day') || (current > max)));
      //   }
      // } else {
      //   if (accidentDate) {
      //     let minT = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     if (fDetail.startDate) {
      //       let min = ((dayjs(new Date(fDetail.startDate)).add(-1, 'day')).endOf('day'));
      //       return current && ((current > dayjs().endOf('day')) || (current < min) || (current < minT) || (current > max));
      //     }
      //     return current && ((current > dayjs().endOf('day') || (current < minT) || (current > max)));
      //   } else {
      //     if (fDetail.startDate) {
      //       let min = ((dayjs(new Date(fDetail.startDate)).add(-1, 'day')).endOf('day'));
      //       return current && ((current > dayjs().endOf('day')) || (current < min) || (current > max));
      //     }
      //     return current && ((current > dayjs().endOf('day') || (current > max) ));
      //   }
      // }


      if (fDetail.startDate) {
        let min = ((dayjs(new Date(fDetail.startDate)).add(-1, 'day')).endOf('day'));
        return current && ((current > dayjs().endOf('day')) || (current < min));
      }
      return current && (current > dayjs().endOf('day'));

    }

    function disabledStartDate(current) {
      // let deathDate = this.props.claimDetailState.deathInfo.date;
      let max = dayjs().endOf('day');
      if (fDetail.endDate) {
        max = (dayjs(new Date(fDetail.endDate)).endOf('day'));
      }
      // if (deathDate) {
      //   let death = (dayjs(new Date(deathDate)).endOf('day'));
      //   if (death < max) {
      //     max = death;
      //   }
      //   if (accidentDate) {
      //     let min = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     return current && ((current > max) || (current < min));
      //   }
      // } else {
      //   if (accidentDate) {
      //     let min = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     return current && ((current > max) || (current < min));
      //   }
      // }

      return current && (current > max);
    }

    // function disabledDate(current) {
    //   return current && (current > dayjs().endOf('day'));
    // }

    const disabledDate = (current) => {
      // let deathDate = this.props.claimDetailState.deathInfo.date;
      // if (deathDate) {
      //   let max = ((dayjs(new Date(deathDate))).endOf('day'));
      //   if (accidentDate) {
      //     let min = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     return current && ((current > dayjs().endOf('day')) || (current < min) || (current > max));
      //   }
      // } else {
      //   if (accidentDate) {
      //     let min = ((dayjs(new Date(accidentDate)).add(-1, 'day')).endOf('day'));
      //     return current && ((current > dayjs().endOf('day')) || (current < min));
      //   }
      // }
      return current && (current > dayjs().endOf('day'));
    }

    const addInvoice = (facilityIndex) => {
      var fDetail = Object.assign([], this.props.facilityDetail);
      var fInvoiceList = fDetail.invoiceList;
      fInvoiceList.push({
        InvoiceNumber: '',
        InvoiceAmount: ''
      });
      this.props.handleUpdateFacility(facilityIndex, fDetail);
    }

    const deleteInvoiceCornfirm = (facilityIndex, invoiceIndex) => {
      this.setState({ deleteFacilityIndex: facilityIndex, deleteInvoiceIndex: invoiceIndex, invoiceDeleting: true })
    }

    const cancelDeleteInvoice = () => {
      this.setState({ deleteFacilityIndex: null, deleteInvoiceIndex: null, invoiceDeleting: false })
    }

    const removeInvoice = (facilityIndex, invoiceIndex) => {
      var fDetail = Object.assign([], this.props.facilityDetail);
      var fInvoiceList = fDetail.invoiceList;
      fInvoiceList.splice(invoiceIndex, 1);
      this.props.handleUpdateFacility(facilityIndex, fDetail);
      this.setState({ facilityIndex: null, invoiceIndex: null, invoiceDeleting: false })
    }

    const enterInvoiceCode = (event, index) => {
      var pFacilityDetail = Object.assign({}, this.props.facilityDetail);
      pFacilityDetail.invoiceList[index].InvoiceNumber = event.target.value;
      this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
    }

    const filterICD = (option, input) => {
      inputICD = input;
      if (input.length === 1) {
        inputICD = '';
      }
      return (removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0)
    }

    const setInputICD = () => {
      setTimeout(setTypingICD, 5);
    }

    const resetInputICD = () => {
      inputICD = '';
      this.setState({ typingICD: '' });
    }

    const setTypingICD = () => {
      this.setState({ typingICD: inputICD });
    }

    const SelectDiagnostic = (facilityIndex, name) => {
      this.props.SelectedDiagnosticResult(facilityIndex, name);
    }

    const SelectOther = (facilityIndex, inputICD, id) => {
      this.props.SelectOtherDiagnostic(facilityIndex, inputICD);
      // if (document.getElementById(id)) {
      //   document.getElementById(id).blur();
      // }
      resetInputICD();
    }

    const SelectOtherHospital = (index, value) => {
      this.props.SelectOtherHospital(index, value);
      if (!value) {
        this.props.SelectedHospital(index, '');
      }
    }


    return (
      <>
        <div className="info__title" style={{ paddingTop: '16px' }}>
          {this.props.facilityIndex > 0 ? (
            <div className="optionalform__title" style={{ display: 'contents' }}>
              <div><h4>{'NHỮNG LẦN KHÁM/ĐIỀU TRỊ ' + (this.props.facilityIndex > 0 ? this.props.facilityIndex + 1 : '')}</h4></div>
              <div className="dot" style={{ cursor: 'pointer' }}
                onClick={() => this.props.handleDeleteFacilityCornfirm(this.props.facilityIndex)}><span className="line"></span></div>
            </div>
          ) : (
            <h4>NHỮNG LẦN KHÁM/ĐIỀU TRỊ</h4>
          )
          }

        </div>
        {/* Thông tin khám/điều trị tại Cơ sở y tế */}
        <div className="item">
          <div className="optionalform__title">
            <p className="--item__title">Cơ sở y tế khám/điều trị</p>
            {/* {(this.props.facilityIndex !== 0) &&
              <div className="dot" style={{ cursor: 'pointer' }}
                onClick={() => this.props.handlerRemoveFacility(this.props.facilityIndex)}><span className="line"></span></div>
            } */}
          </div>
          <div className="item__content">
            <div className="tab">
              <div className="tab__content"></div>
              {/* Chọn cơ sở y tế khám/điều trị */}
              {!fDetail.otherHospital ? (
                <div className="dropdown inputdropdown">
                  <div className="dropdown__content">
                    <div className="tab__content">
                      <div className={!fDetail.selectedHospital && fDetail.errors.errorList && ((fDetail.errors.errorList.indexOf('cityName') >= 0) || (fDetail.errors.errorList.indexOf('districtName') >= 0)) ? "input validate" : "input"} style={{ padding: '8px 0px 10px 0px' }}>
                        <AutoComplete
                          suffixIcon={<SearchOutlined />}
                          showSearch
                          size='large'
                          style={{ width: '100%', margin: '0' }}
                          width='100%'
                          bordered={false}
                          placeholder="Chọn cơ sở y tế"
                          optionFilterProp="name"
                          onChange={(value) => this.props.SelectedHospital(this.props.facilityIndex, value)}
                          onSelect={(value) => this.props.SelectedHospitalChosen(this.props.facilityIndex, value)}
                          onBlur={(e) => this.props.SelectedHospitalChosen(this.props.facilityIndex, e.target.value)}
                          id={'facility_selectedHospital_' + this.props.facilityIndex}
                          value={fDetail.selectedHospital}
                          notFoundContent={(
                            <div className="nice-message">
                              <div style={{ whiteSpace: 'normal' }}><p>Chưa tìm thấy kết quả phù hợp.</p>
                                <p>Vui lòng kiểm tra lại từ khoá hoặc chọn <span className='basic-bold'>“CSYT khác”</span> để cung cấp thông tin CSYT cho <span className='basic-bold'>Những lần khám/điều trị.</span> </p>
                                <p><a className='simple-brown' onClick={() => SelectOtherHospital(this.props.facilityIndex, OTHER_HOSPITAL)}> CSYT khác</a></p></div>
                            </div>
                          )}
                          filterOption={(input, option) =>
                            (removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0) || (removeAccents(option.statename.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase()).replace(/  +/g, ' ')) >= 0)
                          }
                        >
                          {hospitalList.map((hospital) => (
                            <Option key={hospital.HospitalName} name={hospital.HospitalName} statename={hospital.StateName}>
                              <CustomSelectContainer data={hospital} keywork={this.props.facilityDetail.selectedHospital} />
                            </Option>

                          ))}
                        </AutoComplete>
                        <span className="ant-select-arrow" unselectable="on" aria-hidden="true" style={{ userSelect: 'none' }}><span role="img" aria-label="search" className="anticon anticon-search"><svg viewBox="64 64 896 896" focusable="false" data-icon="search" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path></svg></span></span>
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                <>
                  <div className="dropdown inputdropdown">
                    <div className="dropdown__content">
                      <div className="tab__content">
                        <div className="input" style={{ padding: '8px 0px 10px 0px' }}>
                          <AutoComplete
                            suffixIcon={<SearchOutlined />}
                            showSearch
                            size='large'
                            style={{ width: '100%', margin: '0' }}
                            width='100%'
                            bordered={false}
                            placeholder="Chọn cơ sở y tế"
                            optionFilterProp="name"
                            onChange={(value) => SelectOtherHospital(this.props.facilityIndex, value)}
                            onSelect={(value) => this.props.SelectedHospitalChosen(this.props.facilityIndex, value)}
                            id={'facility_otherHospital_' + this.props.facilityIndex}
                            value={this.props.facilityDetail.otherHospital}
                            defaultValue={this.props.facilityDetail.otherHospital}
                            filterOption={(input, option) =>
                              (removeAccents(option.name.toLowerCase()).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0) || (option.statename.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").indexOf(input.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/  +/g, ' ')) >= 0)
                            }
                          >
                            {hospitalList.map((hospital) => (
                              <Option key={hospital.HospitalName} name={hospital.HospitalName} statename={hospital.StateName}>
                                <CustomSelectContainer data={hospital} keywork={this.props.facilityDetail.selectedHospital} />
                              </Option>

                            ))}
                          </AutoComplete>
                          <span className="ant-select-arrow" unselectable="on" aria-hidden="true" style={{ userSelect: 'none' }}><span role="img" aria-label="search" className="anticon anticon-search"><svg viewBox="64 64 896 896" focusable="false" data-icon="search" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path></svg></span></span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div className="dropdown inputdropdown">
                    <div className="dropdown__content">
                      <div className="tab__content">

                        <div className={!fDetail.cityName && (fDetail.errors.errorList.indexOf('cityName') >= 0) ? "input validate" : "input"} style={{ padding: '10px 0px 10px 0px' }}>
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
                            id={'facility_cityName_' + this.props.facilityIndex}
                            value={fDetail.cityName ? fDetail.cityName : undefined}
                            filterOption={(input, option) =>
                              option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                            }
                          >
                            {zipCodeList.map((city) => (
                              <Option key={city.CityName} cityname={city.CityName}>
                                {city.CityName}
                              </Option>
                            ))}
                          </Select>
                        </div>

                      </div>
                    </div>
                    <div className="dropdown__items"></div>
                  </div>
                  {!fDetail.cityName && fDetail.errors.errorList.indexOf('cityName') >= 0 &&
                    <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                      Vui lòng chọn tỉnh/ Thành phố
                    </span>}

                  <div className="dropdown inputdropdown">
                    <div className="dropdown__content">
                      <div className="tab__content">
                        <div className={!fDetail.selectedHospital && (fDetail.errors.errorList.indexOf('selectedHospital') >= 0) ? "input validate" : "input"}>
                          <div className="input__content" style={{ paddingLeft: '7px', paddingRight: '7px' }}>
                            {fDetail.selectedHospital &&
                              <label style={{ marginLeft: '2px' }}>Cơ sở y tế</label>
                            }
                            <input type="search" placeholder="Nhập cơ sở y tế" maxLength="300" id={'facility_selectedHospital_' + this.props.facilityIndex} value={fDetail.selectedHospital}
                              onChange={(e) => this.props.SelectedHospital(this.props.facilityIndex, e.target.value)} />
                          </div>
                          <i><img src="../../img/icon/edit.svg" alt="" /></i>
                        </div>
                      </div>
                    </div>
                  </div>
                  {!fDetail.selectedHospital && (fDetail.errors.errorList.indexOf('selectedHospital') >= 0) &&
                    <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                      Vui lòng nhập cơ sở y tế
                    </span>}


                </>
              )}

              {!fDetail.selectedHospital && !fDetail.selectedHospital && fDetail.errors.errorList && ((fDetail.errors.errorList.indexOf('cityName') >= 0) || (fDetail.errors.errorList.indexOf('districtName') >= 0)) &&
                <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                  Vui lòng chọn cơ sở y tế
                </span>}
            </div>
          </div>
        </div >
        {/* Hình thức điều trị */}
        <div className="item">
          <h5 className="item__title">Hình thức điều trị</h5>
          <div className="item__content" style={{ display: 'flex' }}>
            {/* Nội trú */}
            <div className="tab">
              <div className="tab__content">
                <div className="checkbox-warpper">
                  <div className="checkbox-item">
                    <div className="round-checkbox">
                      <label className="customradio">
                        <input type="radio" value="IN"
                          checked={fDetail.treatmentType === "IN"}
                          onChange={this.handlerOnChangeTreatmentType}
                          id={'facility_treatmentType_' + this.props.facilityIndex}
                        />
                        <div className="checkmark"></div>
                        <p className="text">Nội trú</p>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            {/* Ngoại trú */}
            <div className="tab">
              <div className="tab__content">
                <div className="checkbox-warpper">
                  <div className="checkbox-item">
                    <div className="round-checkbox">
                      <label className="customradio">
                        <input type="radio" value="OUT"
                          checked={fDetail.treatmentType === "OUT"}
                          onChange={this.handlerOnChangeTreatmentType}
                        />
                        <div className="checkmark"></div>
                        <p className="text">Ngoại trú</p>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            {/* Nha khoa */}
            <div className="tab">
              <div className="tab__content">
                <div className="checkbox-warpper">
                  <div className="checkbox-item">
                    <div className="round-checkbox">
                      <label className="customradio">
                        <input type="radio" value="DENT"
                          checked={fDetail.treatmentType === "DENT"}
                          onChange={this.handlerOnChangeTreatmentType}
                        />
                        <div className="checkmark"></div>
                        <p className="text">Nha khoa</p>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          {!fDetail.treatmentType && fDetail.errors.errorList && (fDetail.errors.errorList.indexOf('treatmentType') >= 0) &&
            <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
              Vui lòng chọn hình thức điều trị
            </span>}
        </div >
        {/* Ngày khám/ điều trị */}
        <div className="item">
          <h5 className="item__title">Thời gian khám/ điều trị</h5>
          <div className="item__content">
            {/* Từ ngày-Đến ngày OR Ngày khám/ điều trị */}
            <div className="tab__content">
              {(fDetail.treatmentType === 'IN') &&
                <div className="datewrapper">
                  <div className="datewrapper__item">
                    <div className={!fDetail.startDate && (fDetail.errors.errorList.indexOf('startDate') >= 0)?"inputdate validate":"inputdate"}>
                      {/* <input type={!!fDetail.startDate ? 'date' : 'text'} placeholder="Từ ngày" */}
                      {/* <input type='date' placeholder="Từ ngày"
                        max={endDate < todayDate ? fDetail.endDate : todayDateStr}
                        value={fDetail.startDate} onChange={(event) => this.handlerOnChangeDate("startDate", event)} /> */}
                      <DatePicker placeholder="Từ ngày" disabledDate={disabledStartDate} id={'facility_startDate_' + this.props.facilityIndex} value={fDetail.startDate ? moment(fDetail.startDate) : ""} onChange={(value) => this.handlerOnChangeDate("startDate", value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                    </div>
                  </div>
                  <div className="line"></div>
                  <div className="datewrapper__item">
                    <div className={!fDetail.endDate && (fDetail.errors.errorList.indexOf('endDate') >= 0)?"inputdate validate":"inputdate"}>
                      {/* <input type={!!fDetail.endDate ? 'date' : 'text'} placeholder="Đến ngày" */}
                      {/* <input type='date' placeholder="Đến ngày"
                        min={fDetail.startDate} max={todayDateStr}
                        value={fDetail.endDate} onChange={(event) => this.handlerOnChangeDate("endDate", event)} /> */}
                      <DatePicker placeholder="Đến ngày" disabledDate={disabledEndDate} id={'facility_endDate_' + this.props.facilityIndex} value={fDetail.endDate ? moment(fDetail.endDate) : ""} onChange={(value) => this.handlerOnChangeDate("endDate", value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                    </div>
                  </div>
                </div>
              }
              {(fDetail.treatmentType !== 'IN') &&
                <div className="datewrapper">
                  <div className="datewrapper__item" style={{ width: '100%' }}>
                    <div className={!fDetail.startDate && (fDetail.errors.errorList.indexOf('startDate') >= 0)?"inputdate validate":"inputdate"}>
                      {/* <input type={!!fDetail.startDate ? 'date' : 'text'} */}
                      {/* <input type='date'
                        max={todayDateStr}
                        placeholder="Ngày khám/ điều trị"
                        value={fDetail.startDate} onChange={(event) => this.handlerOnChangeDate("startDate", event)} /> */}
                      <DatePicker placeholder="Ngày khám/ điều trị" disabledDate={disabledDate} id={'facility_startDate_' + this.props.facilityIndex} value={fDetail.startDate ? moment(fDetail.startDate) : ""} onChange={(value) => this.handlerOnChangeDate("startDate", value)} format="DD/MM/YYYY" style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                    </div>
                  </div>
                </div>
              }
            </div>
            {fDetail.errors.dateValid &&
              <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>{fDetail.errors.dateValid}</span>
            }
          </div>
        </div>
        {/* Kết quả chẩn đoán */}
        <div className="item">
          <div className="optionalform__title">
            <p className="item__title" style={{marginBottom: '0'}}>Kết quả chẩn đoán</p>
          </div>
          <div className="item__content">
            <div className="tab">
              <div className="tab__content"></div>
              {/* Chọn kết quả chuẩn đoán */}
              <div className="dropdown inputdropdown">
                <div className="dropdown__content">
                  <div className="tab__content">
                    <div className={(!this.props.facilityDetail.diagnosis || ((typeof this.props.facilityDetail.diagnosis === "string") && (this.props.facilityDetail.diagnosis.trim().length === 0)) || (this.props.facilityDetail.diagnosis.length === 0)) && fDetail.errors.errorList && (fDetail.errors.errorList.indexOf('diagnosis') >= 0) ? "input validate" : "input"} style={{ padding: '8px 0px 8px 0px' }}>
                      {this.props.facilityDetail.diagnosis ? (
                        <Select
                          // suffixIcon={<SearchOutlined />}
                          id={"facility_diagnosis_" + this.props.facilityIndex}
                          mode="multiple"
                          showSearch
                          size='large'
                          style={{ width: '100%', margin: '0' }}
                          width='100%'
                          bordered={false}
                          placeholder="Chọn kết quả chẩn đoán"
                          optionFilterProp="name"
                          onChange={(name) => SelectDiagnostic(this.props.facilityIndex, name)}
                          onKeyUp={() => setInputICD()}
                          onDeselect={() => resetInputICD()}
                          onSelect={() => resetInputICD()}
                          value={this.props.facilityDetail.diagnosis}
                          notFoundContent={(
                            <div className="nice-message">
                              <div style={{ whiteSpace: 'normal' }}><p>Chưa tìm thấy kết quả phù hợp.</p>
                                <p>Vui lòng nhập đầy đủ kết quả chuẩn đoán và chọn để sử dụng từ khóa đã nhập làm kết quả chuẩn đoán </p>
                                <p><a className='simple-brown' onClick={() => SelectOther(this.props.facilityIndex, inputICD, "facility" + this.props.facilityIndex)}>{inputICD}</a></p></div>
                            </div>
                          )}
                          filterOption={(input, option) =>
                            filterICD(option, input)
                          }
                        >
                          {hospitalResultList.map((result) => (
                            <Option key={result.ICDName} name={result.ICDName} code={result.ICDCode}>
                              <CustomSelectICDContainer data={result} keywork={inputICD} />
                            </Option>

                          ))}
                        </Select>

                      ) : (
                        <Select
                          // suffixIcon={<SearchOutlined />}
                          id={"facility_diagnosis_" + this.props.facilityIndex}
                          mode="multiple"
                          showSearch
                          size='large'
                          style={{ width: '100%', margin: '0' }}
                          width='100%'
                          bordered={false}
                          placeholder="Chọn kết quả chẩn đoán"
                          optionFilterProp="name"
                          onChange={(name) => SelectDiagnostic(this.props.facilityIndex, name)}
                          onKeyUp={() => setInputICD()}
                          onDeselect={() => resetInputICD()}
                          onSelect={() => resetInputICD()}
                          notFoundContent={(
                            <div className="nice-message">
                              <div style={{ whiteSpace: 'normal' }}><p>Chưa tìm thấy kết quả phù hợp.</p>
                                <p>Vui lòng nhập đầy đủ kết quả chuẩn đoán và chọn để sử dụng từ khóa đã nhập làm kết quả chuẩn đoán </p>
                                <p><a className='simple-brown' onClick={() => SelectOther(this.props.facilityIndex, inputICD)}>{inputICD}</a></p></div>
                            </div>
                          )}
                          filterOption={(input, option) =>
                            filterICD(option, input)
                          }
                        >
                          {hospitalResultList.map((result) => (
                            <Option key={result.ICDName} name={result.ICDName} code={result.ICDCode}>
                              <CustomSelectICDContainer data={result} keywork={inputICD} />
                            </Option>

                          ))}
                        </Select>
                      )}
                      <span className="ant-select-arrow" unselectable="on" aria-hidden="true" style={{ userSelect: 'none' }}><span role="img" aria-label="search" className="anticon anticon-search"><svg viewBox="64 64 896 896" focusable="false" data-icon="search" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"></path></svg></span></span>
                    </div>
                  </div>
                </div>
              </div>
              {(!this.props.facilityDetail.diagnosis || ((typeof this.props.facilityDetail.diagnosis === "string") && (this.props.facilityDetail.diagnosis.trim().length === 0)) || (this.props.facilityDetail.diagnosis.length === 0)) && fDetail.errors.errorList && (fDetail.errors.errorList.indexOf('diagnosis') >= 0) &&
                <span style={{ color: 'red', lineHeight: '28px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                  Vui lòng chọn kết quả chẩn đoán
                </span>}
                <input type='text' id='hiddenDiagnosticId' style={{display: 'none'}} />
            </div>
          </div>
        </div >
        {/* Thông tin hóa đơn */}
        {(this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE]) &&
          <>
            {fDetail.invoiceList != null && fDetail.invoiceList.map((invoice, index) => {
              if (invoice.InvoiceAmount > 0) {
                totalAmount = totalAmount + parseInt(invoice.InvoiceAmount);
              }
              return (
                <div className="item">
                  <div className="optionalform__title">
                    <p className="--item__title">Thông tin hóa đơn {index > 0 ? index + 1 : ''}</p>
                    {fDetail.invoiceList.length > 1 &&
                      <div className="dot" style={{ cursor: 'pointer' }}
                        onClick={() => deleteInvoiceCornfirm(this.props.facilityIndex, index)}><span className="line"></span>
                      </div>
                    }
                  </div>
                  <div className="item__content">
                    <div className="tab">
                      <div className="tab__content"></div>
                      {/* Số hóa đơn */}
                      <div className="tab__content">
                        <div className={((!fDetail.invoiceList[index].InvoiceNumber || (fDetail.invoiceList[index].InvoiceNumber.trim().length === 0)) && invoice.errorList && (invoice.errorList.indexOf('InvoiceNumber') >= 0)) ? "input validate" : "input"}>
                          <div className="input__content">
                            {fDetail.invoiceList[index].InvoiceNumber &&
                              <label>Số hóa đơn</label>
                            }
                            <input type="search" placeholder="Số hóa đơn" maxLength="300" defaultValue={invoice.InvoiceNumber}
                              id={"facility_"+ this.props.facilityIndex + "_InvoiceNumber_" + index}
                              onBlur={(e) => enterInvoiceCode(e, index)} />
                          </div>
                          <i><img src="../../img/icon/edit.svg" alt="" /></i>
                        </div>
                      </div>
                      {((!fDetail.invoiceList[index].InvoiceNumber || (fDetail.invoiceList[index].InvoiceNumber.trim().length === 0)) && invoice.errorList && (invoice.errorList.indexOf('InvoiceNumber') >= 0)) &&
                        <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                          Vui lòng nhập số hóa đơn
                        </span>}
                      {/* Số tiền */}
                      <div className="tab__content">
                        <div className={!fDetail.invoiceList[index].InvoiceAmount && invoice.errorList && (invoice.errorList.indexOf('InvoiceAmount') >= 0) ? "input validate" : "input"}>
                          <div className="input__content">
                            {fDetail.invoiceList[index].InvoiceAmount &&
                              <label>Số tiền</label>
                            }
                            <NumberFormat displayType="input" type="search" value={fDetail.invoiceList[index].InvoiceAmount} prefix=""
                              placeholder="20.000.000 VNĐ"
                              thousandsGroupStyle="thousand" thousandSeparator={'.'}
                              decimalSeparator="," decimalScale="0" suffix={' VNĐ'} allowNegative={false}
                              id={"facility_"+ this.props.facilityIndex + "_InvoiceAmount_" + index}
                              onValueChange={(values) => {
                                const { value } = values;
                                const pFacilityDetail = Object.assign({}, this.props.facilityDetail);
                                pFacilityDetail.invoiceList[index].InvoiceAmount = value;
                                setTimeout(() => {
                                  this.props.handlerOnChangeFacilityDetail(this.props.facilityIndex, pFacilityDetail);
                                }, 300);
                              }}
                              // onFocus={(e) => e.target.placeholder = ""}
                              // onBlur={(e) => e.target.placeholder = "20.000.000 VNĐ"}
                            />
                          </div>
                          <i><img src="../../img/icon/edit.svg" alt="" /></i>
                        </div>
                      </div>
                      {(!fDetail.invoiceList[index].InvoiceAmount && invoice.errorList && (invoice.errorList.indexOf('InvoiceAmount') >= 0)) &&
                        <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
                          Vui lòng nhập số tiền
                        </span>}
                    </div>
                  </div>
                </div >
              )
            })}
            <button className="circle-btn" onClick={() => addInvoice(this.props.facilityIndex)}>
              <i className="circle-plus">
                <img src="../../img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
              </i>
              <label className="plus-information">Thêm hóa đơn</label>
            </button>
            {(fDetail.invoiceList != null) && (fDetail.invoiceList.length > 0) &&
              <div className="item">
                <div className="optionalform__title">
                  <p className="--item__title">Tổng chi phí cho lần điều trị này</p>
                  <i className="info-icon" onClick={() => showNotice()}
                  ><img src="../../img/icon/Information-step.svg" alt="information-icon" className="info-icon"
                    /></i>
                </div>
                <div className="item__content">
                  <div className="tab">

                    <div className="tab__content">
                      <div className="input disabled">
                        <div className="input__content">
                          <label>Số tiền</label>
                          <NumberFormat displayType="input" type="search" prefix=""
                            value={totalAmount}
                            thousandsGroupStyle="thousand" thousandSeparator={'.'}
                            decimalSeparator="," decimalScale="0" suffix={' VNĐ'} allowNegative={false}
                            disabled
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div >
            }
          </>
        }

        {/* {component Sự kiện bảo hiểm cũng được yêu cầu quyền lợi tại Công ty bảo hiểm khác} */}
        <div className="optional-checkbox-wrapper">
          <h5 className="basic-semibold">Đã được chi trả tại Công ty Bảo hiểm khác ?  </h5>
          <div className="checkbox-warpper">
            <div className="checkbox-item" style={{ display: 'flex' }}>
              <div className="round-checkbox">
                <label className="customradio">
                  <input type="radio" value="yes"
                    id={"facility_isOtherCompanyRelated" + this.props.facilityIndex}
                    checked={fDetail.isOtherCompanyRelated === "yes"}
                    onChange={() => this.props.isOtherCompanyRelated(this.props.facilityIndex, "yes")}
                  />
                  <div className="checkmark"></div>
                  <p className="text">Có</p>
                </label>
              </div>
              <div className="round-checkbox">
                <label className="customradio">
                  <input type="radio" value="no"
                         checked={fDetail.isOtherCompanyRelated === "no"}
                         onChange={() => this.props.isOtherCompanyRelated(this.props.facilityIndex, "no")}
                  />
                  <div className="checkmark"></div>
                  <p className="text">Không</p>
                </label>
              </div>
            </div>
          </div>
        </div>
        {(!fDetail.isOtherCompanyRelated && (fDetail.errors.errorList.indexOf('isOtherCompanyRelated') >= 0)) && (fDetail.isOtherCompanyRelated === '') &&
          <span style={{ color: 'red', lineHeight: '24px', marginTop: '4px', marginBottom: '20px', verticalAlign: 'top'}}>
            Vui lòng chọn đã được chi trả tại Công ty Bảo hiểm khác?
          </span>}
        {/* {component Thông tin công ty bảo hiểm} */}
        {(fDetail.isOtherCompanyRelated === 'yes') &&
          <OtherCompany
            claimDetailState={this.props.claimDetailState}
            facilityIndex={this.props.facilityIndex}
            facilityDetail={fDetail}
            enterOtherCompanyName={this.props.enterOtherCompanyName}
            enterOtherCompanyPaid={this.props.enterOtherCompanyPaid}
            handlerUpdateSubClaimDetailState={this.props.handlerUpdateSubClaimDetailState} />
        }

        {this.state.invoiceDeleting &&
          <div className="popup option-popup  show">
            <div className="popup__card">
              <div className="optioncard" style={{ lineHeight: '20px', paddingBottom: '0px' }}>
                <p>Quý khách có muốn xóa thông tin <br />hóa đơn được chọn không?</p>
                <div className="btn-wrapper">
                  <button className="btn btn-primary" onClick={() => removeInvoice(this.state.deleteFacilityIndex, this.state.deleteInvoiceIndex)}>Đồng ý</button>
                  <button className="btn btn-nobg" onClick={() => cancelDeleteInvoice()}>Không đồng ý</button>
                </div>
                <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt="" onClick={() => cancelDeleteInvoice()} /></i>
              </div>
            </div>
            <div className="popupbg"></div>
          </div>
        }
        {this.state.showNotice &&
          <NoticePopup closePopup={closeNotice} title='Tổng chi phí cho lần điều trị này' msg={parse('Tổng chi phí được tính dựa vào số tiền của <br/> các hóa đơn cho một lần khám và điều trị')} imgPath={FE_BASE_URL + '/img/popup/total-claim-fee.png'} />
        }
      </>
    );
  }
}

export default FacilityDetail;
