import React, { Component } from 'react';
import { FE_BASE_URL, COMPANY_KEY, AUTHENTICATION, PURPOSE_OF_RESIDENCE, TIME_OF_RESIDENCE, IDDOCUMENTS_BC, OTHERS, OTHERS_NC, OTHERS_CV, DOCUMENTS_HINT_MAP, GENDER_MAP, CCCD_NAME, IS_MOBILE } from '../sdkConstant';
import { formatFullName, formatDate, isOlderThan18, disabledFutureDate, getSession, topFunction } from '../sdkCommon';
import { getDocType, iibGetMasterDataByType } from '../sdkAPI';
import LoadingIndicator from '../LoadingIndicator2';
import LoadingIndicatorBasic from '../LoadingIndicatorBasic';
import { DatePicker, Select, Form } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import PersonalInfoUpload from './PersonalInfoUpload';
import moment from 'moment';

class InputPersonalInfo extends Component {

  constructor(props) {
    super(props);
    this.state = {
      stepName: this.props.stepName,
      docTypeProfile: this.props.docTypeProfile,
      docTypeRebuild: this.props.docTypeRebuild,
      occupationList: this.props.occupationList,
      nationList: this.props.nationList,
      errorMessage: 'Vui lòng nhập thông tin',
      validInputText: this.props.validInputText
    }
    // this.handlerOnChangeDocType = this.OnChangeDocType.bind(this);
  }

  componentDidMount() {
    topFunction();
    // document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' });
    if (!this.props.docTypeProfile || !this.props.docTypeRebuild) {
      this.getDocTypeList();
    }
    if (!this.props.occupationList) {
      this.getOccupationList();
    }
    if (!this.props.nationList) {
      this.getCountries();
    }
  }

  getDocTypeList = () => {
    const apiRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Authentication: AUTHENTICATION,
        DeviceId: this.props.deviceId,
        APIToken: this.props.apiToken,
        Project: 'mcp',
        Action: 'PSProcess',
        ClientID: this.props.clientId,
        UserLogin: this.props.clientId,
        ProcessType: "CCI"
      }
    };
    console.log('apiRequest=', apiRequest);
    getDocType(apiRequest).then(Res => {

      let Response = Res.Response;
      console.log('Response=', Response);
      if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
        let jsonState = this.state;
        jsonState.docTypeProfile = Response.ClientProfile;
        this.props.updateDocTypeProfile(jsonState.docTypeProfile);
        jsonState.docTypeRebuild = this.buildDocTypeMap(Response.ClientProfile);
        this.props.updateDocTypeRebuild(jsonState.docTypeRebuild);
        console.log('Response.ClientProfile docTypeProfile list = ', Response.ClientProfile);
        console.log('jsonState.docTypeRebuild = ', jsonState.docTypeRebuild);
        this.setState(jsonState);
      }
    }).catch(error => {

    });


  }

  getOccupationList() {
    console.log('getOccupationList.....');
    let request = {
      Project: "mcp",
      Action: "Occupation"
    }
    iibGetMasterDataByType(request).then(Res => {
      let Response = Res.GetMasterDataByTypeResult;
      console.log('getOccupationList=', Response);
      if ((Response.Result === 'true') && (Response.Message === 'SUCCESSFUL')) {
        console.log('occupationList=', Response.ClientProfile);
        this.props.updateOccupationList(Response.ClientProfile);
        this.setState({ occupationList: Response.ClientProfile });
      }
    }).catch(error => {
    });
  }

  getCountries() {
    let request = {
      Action: "Nation", Project: "mcp"
    }
    iibGetMasterDataByType(request).then(Res => {
      let Response = Res.GetMasterDataByTypeResult;
      if (Response.Result === 'true' && Response.ClientProfile) {
        console.log('nation=', Response.ClientProfile);
        this.props.updateNationList(Response.ClientProfile);
        this.setState({ nationList: Response.ClientProfile });
      }
    }).catch(error => {
    });
  }

  buildDocTypeMap = (docTypeProfile) => {
    let docTypeMap = {};
    if (!docTypeProfile) {
      return null;
    }
    for (let i = 0; i < docTypeProfile.length; i++) {
      if (docTypeProfile[i]?.DocTypeName) {
        if (docTypeMap[docTypeProfile[i]?.DocTypeName]) {
          let docType = docTypeMap[docTypeProfile[i]?.DocTypeName];
          if (docType.SubDocList) {
            let list = docType.SubDocList;
            list.push(docTypeProfile[i]);
            docType.SubDocList = list;
            docTypeMap[docTypeProfile[i]?.DocTypeName] = docType;
          } else {
            let list = [docType];
            list.push(docTypeProfile[i]);
            docType.SubDocList = list;
            docTypeMap[docTypeProfile[i]?.DocTypeName] = docType;
          }
        } else {
          docTypeMap[docTypeProfile[i]?.DocTypeName] = docTypeProfile[i];
        }
      }
    }
    let valuesArray = Array.from(Object.entries(docTypeMap), ([key, value]) => value);
    return valuesArray;
  }

  // OnChangeDocType(value) {
  //   let personalInfo = this.state.personalInfo;
  //   personalInfo.DocTypeNameSelected = value;
  //   this.setState({personalInfo: personalInfo});
  // }

  render() {
    console.log('DocTypeSelected=' + this.props?.personalInfo?.DocTypeNameSelected);
    console.log('isQRCode=', this.props.personalInfo.isQRCode);
    console.log('nationList=', this.state.nationList);
    // const onChangeDocType=(DocTypeID)=> {
    //   let personalInfo = this.state.personalInfo;
    //   personalInfo.DocTypeNameSelected = DocTypeID;
    //   this.setState({personalInfo: personalInfo});
    // }
    let docTypeRebuild = this.state.docTypeRebuild;
    if (docTypeRebuild) {
      docTypeRebuild = docTypeRebuild.filter(item => (item?.SubDocId !== OTHERS) && (item?.SubDocId !== OTHERS_NC) && (item?.SubDocId !== OTHERS_CV));
    }
    if (docTypeRebuild && isOlderThan18(this.props.selectedLI.DOB)) {
      docTypeRebuild = docTypeRebuild.filter(item => item?.SubDocId !== IDDOCUMENTS_BC);
    }

    const getSubDoctypeListByDoctypeID = (DocTypeName) => {
      let DocTypeSelected = docTypeRebuild.find(item => item?.DocTypeName === DocTypeName);
      if (DocTypeSelected) {
        if (DocTypeSelected?.SubDocList) {
          DocTypeSelected = DocTypeSelected?.SubDocList;
        } else {
          DocTypeSelected = [DocTypeSelected];
        }

      }
      return DocTypeSelected;
    }

    console.log('this.state?.personalInfo?.DocTypeNameSelected=', this.state?.personalInfo?.DocTypeNameSelected);
    // const togglePersonalInfo = () => {
    //   this.setState({personalInfoChecked: !this.state.personalInfoChecked});
    // }

    // const toggleResidence = () => {
    //   this.setState({residenceCheck: !this.state.residenceCheck});
    // }

    // const toggleOccupation = () => {
    //   this.setState({occupationChecked: !this.state.occupationChecked});
    // }

    const onChangeOccupationDesc = (event) => {
      event.preventDefault();
      let value = event?.target?.value;
      this.setState({
        ...this.state, occupationInfo: {
          occupationDesc: value,
          error: value ? '' : 'Vui lòng nhập địa chỉ'
        }
      })
    }

    const getNationName = (code) => {
      let nation = this.state.nationList.find((nation) => nation.code === code);
      return nation.nameVN;
    }

    const getOccupation = (code) => {
      let occupation = this.state?.occupationList.find((occupation) => occupation?.code === code);
      return occupation;
    }


    let DocTypeSelected = null;
    if (this.props.personalInfo?.DocTypeNameSelected) {
      DocTypeSelected = getSubDoctypeListByDoctypeID(this.props.personalInfo?.DocTypeNameSelected);
      console.log('DocTypeSelected=', DocTypeSelected);
    }

    const nextStep = () => {
      let valid = this.props.validateInputText();
      if (valid) {
        if (this.props.residenceCheck && ((this.props.residenceInfo.NewCountryOfResidence.indexOf('Hoa Kỳ') >= 0) || (this.props.residenceInfo.NewCountryOfResidenceCode === 'US'))) {
          this.props.showFatca();
        } else {
          this.props.clearFatca();
          this.props.updateStepName(this.props.stepName + 1);
        }
      }
      this.setState({validInputText: valid});
      this.props.saveLocal();
    }

    return (
      <section className="sccontract-warpper change-policy-info" id="scrollAnchor">
        <div className="insurance">
          <div className={getSession(IS_MOBILE)?"stepform margin-top170":"stepform"}>
            <div className="stepform__body">
              <div className="info">
                <LoadingIndicator area="claim-li-benefit-list" />
                <div className="info__body">
                  <div className="checkbox-wrap basic-column">
                    <div className="tab-wrapper">
                      <div className="tab"> {/* Thêm key vào đây */}
                        <div className="checkbox-warpper">
                          <label className="checkbox" /*htmlFor={item.ClaimType}*/>
                            <input
                              type="checkbox"
                              name={'personal-info'}
                              id={'personal-info'}
                              checked={this.props.personalInfoChecked ? true : false}
                              onClick={() => this.props.togglePersonalInfo()}
                            />
                            <div className="checkmark">
                              <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                            </div>
                          </label>
                          <p className="basic-bold basic-text2">THAY ĐỔI THÔNG TIN NHÂN THÂN</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                {this.props.personalInfoChecked &&
                  <>
                    <div className="contractform__head2" style={{padding: '8px 0'}}>
                      <div className="contractform__head-content">
                        <div className="item">
                          <p className="item-label">Họ và tên</p>
                          <p className="item-content">{formatFullName(this.props.selectedLI.FullName)} </p>
                        </div>
                        <div className="contractform__head-content">
                          <div className="item">
                            <p className="item-label">Số giấy tờ tùy thân</p>
                            <p className="item-content">{this.props.selectedLI.LifeInsuredIDNum} </p>
                          </div>
                          <div className="item">
                            <p className="item-label">Số hộ chiếu</p>
                            <p className="item-content">{this.props.selectedLI.Passport} </p>
                          </div>
                          <div className="item">
                            <p className="item-label">Ngày tháng năm sinh</p>
                            <p className="item-content">{formatDate(this.props.selectedLI.DOB)} </p>
                          </div>
                          <div className="item">
                            <p className="item-label">Giới tính</p>
                            <p className="item-content">{this.props.selectedLI.Gender === 'M' ? 'Nam' : (this.props.selectedLI.Gender === 'F' ? 'Nữ' : '')} </p>
                          </div>
                        </div>

                      </div>
                    </div>
                    <img style={{ marginLeft: '-22px', minWidth: 'calc(100% + 44px)' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                    <div className="info__title" style={{ marginBottom: '16px' }}>
                      <h4>CHỌN LOẠI GIẤY TỜ TÙY THÂN</h4>
                    </div>
                    <div className="dropdown inputdropdown">
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>

                              <Select
                                size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder='Chọn loại giấy tờ tùy thân sử dụng'
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangeDocType(v)}
                                value={this.props?.personalInfo?.DocTypeNameSelected}
                                filterOption={(input, option) =>
                                  option.name.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                }
                              >
                                {docTypeRebuild && docTypeRebuild.map((doctype) => (
                                  doctype &&
                                  <Option
                                    key={doctype.DocTypeName}
                                    name={doctype.DocTypeName}>{doctype.DocTypeName}
                                  </Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                        <span
                          style={{
                            color: 'red',
                            'line-height': '22px'
                          }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {DocTypeSelected &&
                      <div className='nd13-padding-bottom16'></div>
                    }
                    {this.props?.personalInfo?.DocTypeNameSelected && (this.props?.personalInfo?.DocTypeNameSelected === CCCD_NAME) &&
                      <div class="cccdnotice">
                        <div class="container" style={{ paddingLeft: '0', paddingRight: '0' }}>
                          <div class="board">
                            <div class={getSession(IS_MOBILE)? "board_image_mobile": "board_image"}></div>
                            <div class="board_content">
                              <p>Để hình ảnh chứng từ của quý khách được nhận dạng dễ dàng, hình chụp nên:</p>
                              <ul style={{ paddingTop: '6px' }}>
                                <li>Chụp theo chiều ngang</li>
                                <li>Rõ nét, không nhoà chữ, mất chữ, mất góc</li>
                                <li>Không bật flash, không bị ám màu</li>
                                <li>Ảnh không vượt quá 5MB</li>
                              </ul>
                            </div>
                          </div>
                        </div>
                      </div>
                    }
                    {DocTypeSelected && (DocTypeSelected.length === 2) &&
                      <div className='dash-line-claim' style={{ marginBottom: '16px', marginTop: '12px' }}></div>
                    }
                    <div className={DocTypeSelected && (DocTypeSelected.length === 2) ? 'cccd-container' : ''}>
                      {DocTypeSelected && DocTypeSelected.map((item) => (
                        <PersonalInfoUpload
                          SubDocId={item?.SubDocId}
                          SubDocName={DOCUMENTS_HINT_MAP[item?.SubDocId]?DOCUMENTS_HINT_MAP[item?.SubDocId]:item?.SubDocName}
                          errorUpload={this.props.errorUpload}
                          attachmentState={this.props.personalInfo.attachmentState}
                          dragFileLeave={this.props.dragFileLeave}
                          dropFile={this.props.dropFile}
                          uploadAttachment={this.props.uploadAttachment}
                          updateAttachmentList={this.props.updateAttachmentList}
                          deleteAttachment={this.props.deleteAttachment}
                          dragFileOver={this.props.dragFileOver}
                          onLocalUploading={this.props.onLocalUploading}
                          isSubmitting={this.props.isSubmitting}
                        />
                      ))}
                      {this.props.errorUpload &&
                        <p className='inline-red' style={{
                          padding: '0 16px 16px'

                        }}>{this.props.errorUpload}</p>
                      }
                      {this.props.localUploading ?
                        <LoadingIndicatorBasic />
                        : <LoadingIndicator area="submit-loading" />
                      }
                      {this.props.localUploading &&
                        <p style={{
                          padding: '16px'

                        }}>Thông tin chứng từ đang được xử lý. Quý khách vui lòng đợi trong giây lát.</p>
                      }
                    </div>
                    {this.props?.personalInfo?.DocTypeNameSelected &&
                      <>
                        <img style={{ marginLeft: '-22px', minWidth: 'calc(100% + 44px)' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                        <div className="info__title" style={{ marginBottom: '16px' }}>
                          <h4>Chọn thông tin cần điều chỉnh</h4>
                        </div>
                        {(!this.props.personalInfo.isQRCode || this.props.personalInfo.personalInfoChange[0]?.Checked) &&
                          <div className="info">
                            <div className="info__body claim-type">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab"> {/* Thêm key vào đây */}
                                    <div className="checkbox-warpper">
                                      <label className={this.props.personalInfo.isQRCode ? "checkbox2" : "checkbox"} style={{ position: 'absolute', top: '0' }} htmlFor={'clientName'}>
                                        <input
                                          type="checkbox"
                                          name={'clientName'}
                                          id={'clientName'}
                                          checked={this.props.personalInfo.personalInfoChange[0]?.Checked}
                                          onClick={() => this.props.toggleClientProperties(0)}
                                          disabled={this.props.personalInfo.isQRCode}
                                        />
                                        <div className="checkmark">
                                          <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                      </label>
                                      <div style={{ width: '100%', marginLeft: '28px', borderRadius: '6px', background: '#E6E6E6' }}>
                                        <div className="tab__content" style={{ margin: '12px' }}>
                                          <div className={this.props.personalInfo.personalInfoChange[0]?.error ? "input validate" : this.props.personalInfo.isQRCode ? "input disabled" : "input"}>
                                            <div className="input__content">
                                              {this.props.personalInfo.personalInfoChange[0]?.NewFamilyName &&
                                                <label style={{ marginLeft: '2px' }}>Họ và tên đệm</label>
                                              }
                                              <input type="search" placeholder="Họ và tên đệm" maxLength="100"
                                                value={this.props.personalInfo.personalInfoChange[0]?.NewFamilyName}
                                                disabled={this.props.personalInfo.isQRCode}
                                                onChange={(e) => this.props.onChangeFamilyName(e.target.value)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                        <div className="tab__content" style={{ margin: '12px' }}>
                                          <div className={this.props.personalInfo.personalInfoChange[0]?.error2 ? "input validate" : this.props.personalInfo.isQRCode ? "input disabled" : "input"}>
                                            <div className="input__content">
                                              {this.props.personalInfo.personalInfoChange[0]?.NewGivenName &&
                                                <label style={{ marginLeft: '2px' }}>Tên</label>
                                              }
                                              <input type="search" placeholder="Tên" maxLength="20"
                                                value={this.props.personalInfo.personalInfoChange[0]?.NewGivenName}
                                                disabled={this.props.personalInfo.isQRCode}
                                                onChange={(e) => this.props.onChangeGivenName(e.target.value)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>

                                </div>
                              </div>
                            </div>
                          </div>
                        }

                        {!this.state.validInputText && this.props.personalInfo.personalInfoChange[0]?.Checked &&
                          (!this.props.personalInfo.personalInfoChange[0]?.NewFamilyName
                            || !this.props.personalInfo.personalInfoChange[0]?.NewGivenName) &&
                          <div style={{ paddingTop: '16px' }}>
                            <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                              {this.state.errorMessage}
                            </span>
                          </div>
                        }

                        {(!this.props.personalInfo.isQRCode || this.props.personalInfo.personalInfoChange[1]?.Checked) &&
                          <div className="info">
                            <div className="info__body">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab"> {/* Thêm key vào đây */}
                                    <div className="checkbox-warpper" style={{display: 'block'}}>
                                      <label className={this.props.personalInfo.isQRCode ? "checkbox2" : "checkbox"} style={{ position: 'absolute', top: '54px' }} htmlFor={'idNumber'}>
                                        <input
                                          type="checkbox"
                                          name={'idNumber'}
                                          id={'idNumber'}
                                          checked={this.props.personalInfo.personalInfoChange[1]?.Checked}
                                          onClick={() => this.props.toggleClientProperties(1)}
                                          disabled={this.props.personalInfo.isQRCode}
                                        />
                                        <div className="checkmark">
                                          <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                      </label>
                                      {/* {((this.props?.personalInfo?.DocTypeNameSelected === CCCD_NAME) || (this.props?.personalInfo?.DocTypeNameSelected === BIRTH_CERT)) && */}
                                      <div style={{ width: '100%', marginLeft: '-4px' }}>
                                        <div className="tab__content" style={{ margin: '34px', marginRight: '0' }}>
                                          <div className={this.props.personalInfo.personalInfoChange[1]?.error ? "input validate" : this.props.personalInfo.isQRCode ? "input disabled" : "input"}>
                                            <div className="input__content">
                                              {this.props.personalInfo.personalInfoChange[1]?.NewClientIDNumber &&
                                                <label style={{ marginLeft: '2px' }}>Số giấy tờ tùy thân</label>
                                              }
                                              <input type="search" placeholder="Số giấy tờ tùy thân" maxLength="20"
                                                value={this.props.personalInfo.personalInfoChange[1]?.NewClientIDNumber}
                                                disabled={this.props.personalInfo.isQRCode}
                                                onChange={(e) => this.props.onChangeClientIDNumber(e.target.value)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                      {/* } */}
                                    </div>
                                  </div>

                                </div>
                              </div>
                            </div>
                          </div>
                        }
                        {!this.state.validInputText && this.props.personalInfo.personalInfoChange[1]?.Checked &&
                          !this.props.personalInfo.personalInfoChange[1]?.NewClientIDNumber &&
                          <div style={{marginTop: '-24px', marginBottom: '8px'}}>
                            <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                              {this.state.errorMessage}
                            </span>
                          </div>
                        }

                        {(!this.props.personalInfo.isQRCode || this.props.personalInfo.personalInfoChange[1]?.ppChecked) &&
                          <div className="info" style={{marginTop: '-48px'}}>
                            <div className="info__body">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab"> {/* Thêm key vào đây */}
                                    <div className="checkbox-warpper" style={{display: 'block'}}>
                                      {this.props?.personalInfo?.DocTypeNameSelected === 'Hộ chiếu' &&
                                      <label className={this.props.personalInfo.isQRCode ? "checkbox2" : "checkbox"} style={{ position: 'absolute', top: '54px' }} htmlFor={'ppIdNumber'}>
                                        <input
                                          type="checkbox"
                                          name={'ppIdNumber'}
                                          id={'ppIdNumber'}
                                          checked={this.props.personalInfo.personalInfoChange[1]?.ppChecked}
                                          onClick={() => this.props.toggleClientPropertiesPassport(1)}
                                          disabled={this.props.personalInfo.isQRCode}
                                        />
                                        <div className="checkmark">
                                          <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                      </label>
                                      }
                                      {this.props?.personalInfo?.DocTypeNameSelected === 'Hộ chiếu' &&
                                      <div style={{ width: '100%', marginLeft: '-4px' }}>
                                        <div className="tab__content" style={{ margin: '34px', marginRight: '0' }}>
                                          <div className={this.props.personalInfo.personalInfoChange[1]?.error ? "input validate" : this.props.personalInfo.isQRCode ? "input disabled" : "input"}>
                                            <div className="input__content">
                                              {this.props.personalInfo.personalInfoChange[1]?.NewClientPassportNumber &&
                                                <label style={{ marginLeft: '2px' }}>Số hộ chiếu</label>
                                              }
                                              <input type="search" placeholder="Số hộ chiếu" maxLength="20"
                                                value={this.props.personalInfo.personalInfoChange[1]?.NewClientPassportNumber}
                                                disabled={this.props.personalInfo.isQRCode}
                                                onChange={(e) => this.props.onChangeClientPassportNumber(e.target.value)} />
                                            </div>
                                            <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                      }
                                    </div>
                                  </div>

                                </div>
                              </div>
                            </div>
                          </div>
                        }
                        {!this.state.validInputText && this.props.personalInfo.personalInfoChange[1]?.ppChecked &&
                          !this.props.personalInfo.personalInfoChange[1]?.NewClientPassportNumber &&
                          <div style={{marginTop: '-24px', marginBottom: '8px'}}>
                            <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                              {this.state.errorMessage}
                            </span>
                          </div>
                        }

                        {(!this.props.personalInfo.isQRCode || this.props.personalInfo.personalInfoChange[2]?.Checked) &&
                          <div className={this.props?.personalInfo?.DocTypeNameSelected === 'Hộ chiếu'? "info": "info padding-top32"}>
                            <div className="info__body claim-type">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab"> {/* Thêm key vào đây */}
                                    <div className="checkbox-warpper">
                                      <label className={this.props.personalInfo.isQRCode ? "checkbox2" : "checkbox"} style={{ position: 'absolute', top: '14px' }} htmlFor={'DOB'}>
                                        <input
                                          type="checkbox"
                                          name={'DOB'}
                                          id={'DOB'}
                                          checked={this.props.personalInfo.personalInfoChange[2]?.Checked}
                                          onClick={() => this.props.toggleClientProperties(2)}
                                          disabled={this.props.personalInfo.isQRCode}
                                        />
                                        <div className="checkmark">
                                          <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                      </label>
                                      <div style={{ width: '100%', marginLeft: '14px' }}>
                                        <div className="tab__content" style={{ margin: '14px', marginRight: '0' }}>
                                          <div className="datewrapper">
                                            <div className="datewrapper__item" style={{ width: '100%' }}>
                                              <div className="inputdate">
                                                <Form>
                                                  <Form.Item className="custom-date-picker" label="" style={{ width: '100%' }}>
                                                    <DatePicker placeholder="Ngày tháng năm sinh"
                                                      // id={'facility_startDate_' + this.props.facilityIndex} 
                                                      value={this.props.personalInfo.personalInfoChange[2]?.NewDOBSelected?moment(this.props.personalInfo.personalInfoChange[2]?.NewDOBSelected): ''}
                                                      disabled={this.props.personalInfo.isQRCode}
                                                      disabledDate={disabledFutureDate}
                                                      onChange={(value) => this.props.onChangeClientDOB(value)}
                                                      format="DD/MM/YYYY"
                                                      style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                                    {this.props.personalInfo.personalInfoChange[2]?.NewDOBSelected &&
                                                      <label className="custom-label">Ngày tháng năm sinh</label>
                                                    }
                                                  </Form.Item>
                                                </Form>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>

                                    </div>
                                  </div>

                                </div>
                              </div>
                            </div>
                          </div>
                        }
                        {!this.state.validInputText && this.props.personalInfo.personalInfoChange[2]?.Checked &&
                          !this.props.personalInfo.personalInfoChange[2]?.NewDOB &&
                          <div style={{marginTop: '-12px', marginBottom: '-8px'}}>
                            <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                              {this.state.errorMessage}
                            </span>
                          </div>
                        }
                        {(!this.props.personalInfo.isQRCode || this.props.personalInfo.personalInfoChange[3]?.Checked) &&
                          <div className="info cus-select" style={{marginTop: '8px'}}>
                            <div className="info__body claim-type">
                              <div className="checkbox-wrap basic-column">
                                <div className="tab-wrapper">
                                  <div className="tab"> {/* Thêm key vào đây */}
                                    <div className="checkbox-warpper">
                                      <label className={this.props.personalInfo.isQRCode ? "checkbox2" : "checkbox"} style={{ position: 'absolute', top: '14px' }} htmlFor={'gender'}>
                                        <input
                                          type="checkbox"
                                          name={'gender'}
                                          id={'gender'}
                                          checked={this.props.personalInfo.personalInfoChange[3]?.Checked}
                                          onClick={() => this.props.toggleClientProperties(3)}
                                          disabled={this.props.personalInfo.isQRCode}
                                        />
                                        <div className="checkmark">
                                          <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                                        </div>
                                      </label>
                                      <div style={{ width: '100%', marginLeft: '14px' }}>
                                        <div className="tab__content" style={{ margin: '14px', marginRight: '0' }}>
                                          <div className="dropdown inputdropdown">
                                            <div className="dropdown__content">
                                              <div
                                                className="input-wrapper-item">
                                                <div className={this.props.personalInfo.isQRCode ? "input disabled" : "input"} style={{ height: '59px', paddingTop: '10px' }}>
                                                  <div className="input__content" style={{ 'width': '100%', }}>

                                                  <Form>
                                                      <Form.Item label="" style={{ width: '100%'}}>
                                                        <Select
                                                          disabled={this.props.personalInfo.isQRCode}
                                                          size='large'
                                                          style={{
                                                          width: '100%',
                                                          margin: '0',
                                                          marginTop: '12px'
                                                          }}
                                                          placeholder='Giới tính'
                                                          width='100%'
                                                          bordered={false}
                                                          optionFilterProp="name"
                                                          onChange={(v) => this.props.onChangeClientGender(v)}
                                                          value={this.props.personalInfo.personalInfoChange[3]?.NewGenderCode}
                                                          filterOption={(input, option) =>
                                                          option.name.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                          }
                                                        >
                                                          {Object.entries(GENDER_MAP).map(([gender, genderName]) => (
                                                          gender &&
                                                          <Option
                                                            key={gender}
                                                            name={genderName}>{genderName}
                                                          </Option>
                                                          ))}
                                                        </Select>
                                                        {this.props.personalInfo.personalInfoChange[3]?.NewGenderCode &&
                                                          <label className="custom-label" style={{top: '12px', left: '0'}}>Giới tính</label>
                                                        }
                                                      </Form.Item>
                                                    </Form>
                                                  </div>
                                                </div>
                                              </div>
                                            </div>
                                            <div className="dropdown__items"></div>
                                          </div>
                                        </div>
                                      </div>

                                    </div>
                                  </div>

                                </div>
                              </div>
                            </div>
                          </div>
                        }
                        {!this.state.validInputText && this.props.personalInfo.personalInfoChange[3]?.Checked &&
                          !this.props.personalInfo.personalInfoChange[3]?.NewGender &&
                          <div style={{ paddingTop: '16px', marginBottom: '-24px'}}>
                            <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                              {this.state.errorMessage}
                            </span>
                          </div>
                        }
                        {this.props.errorInfoChange &&
                        <div style={{ paddingTop: '16px' }}>
                          <span style={{ color: 'red', lineHeight: '24px', marginLeft: '28px', verticalAlign: 'top' }}>
                            {this.state.errorInfoChange}
                          </span>
                        </div>
                        }
                      </>
                    }
                  </>
                }
              </div>
            </div>


            <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt="" />
            <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
          </div>
          <div className="stepform" style={{ marginTop: '20px' }}>
            <div className="stepform__body">
              <div className="info residence">
                <LoadingIndicator area="claim-li-benefit-list" />
                <div className="info__body claim-type">
                  <div className="checkbox-wrap basic-column">
                    <div className="tab-wrapper">
                      <div className="tab"> {/* Thêm key vào đây */}
                        <div className="checkbox-warpper">
                          <label className="checkbox" htmlFor={'residence'}>
                            <input
                              type="checkbox"
                              name={'residence'}
                              id={'residence'}
                              checked={this.props.residenceCheck ? true : false}
                              onClick={() => this.props.toggleResidence()}
                            />
                            <div className="checkmark">
                              <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                            </div>
                          </label>
                          <p className="basic-bold basic-text2">THAY ĐỔI QUỐC GIA CƯ TRÚ</p>
                        </div>
                      </div>

                    </div>
                  </div>
                  <div style={{ marginTop: '32px' }}>
                      <h4>Cư trú ở một Quốc gia khác liên tục từ 3 tháng trở lên</h4>
                    </div>
                </div>
                {this.props.residenceCheck &&
                  <>
                    <div className={this.props?.residenceInfo?.nationSelected?"dropdown inputdropdown":"dropdown inputdropdown cus-select-nation"} style={{paddingTop: '20px' }}>
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>
                              {this.props?.residenceInfo?.nationSelected &&
                                <label style={{ paddingTop: '20px' }}>Chọn Quốc gia đến</label>
                              }
                              <Select
                                suffixIcon={<SearchOutlined />}
                                showSearch
                                size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder="Chọn Quốc gia đến"
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangeNation(v, getNationName(v))}
                                value={this.props?.residenceInfo?.nationSelected}
                              >
                                {this.state.nationList && this.state.nationList.map((nation) => (
                                  nation &&
                                  <Option
                                    key={nation.code}
                                    name={nation.nameVN}>{nation.nameVN}</Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                          <span
                            style={{
                              color: 'red',
                              'line-height': '22px'
                            }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && !this.props.residenceInfo?.NewCountryOfResidence &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                    <div className="info" style={{marginTop: '20px'}}>
                      <div className="info__body claim-type">
                        <div className="checkbox-wrap basic-column">
                          <div className="tab-wrapper">
                            <div className="tab"> {/* Thêm key vào đây */}
                              <div className="checkbox-warpper">
                                <div style={{ width: '100%' }}>
                                  <div className="tab__content">
                                    <div className="datewrapper">
                                      <div className="datewrapper__item" style={{ width: '100%' }}>
                                        <div className="inputdate">
                                          <Form>
                                            <Form.Item className="custom-date-picker" label="" style={{ width: '100%' }}>
                                              <DatePicker placeholder="Ngày đi"
                                                value={this.props.residenceInfo.EntryDateSelected?moment(this.props.residenceInfo.EntryDateSelected):''}
                                                // disabled={this.props.residenceInfo.isQRCode}
                                                onChange={(value) => this.props.onChangeEntryDate(value)}
                                                format="DD/MM/YYYY"
                                                style={{ width: '100%', margin: '0px', padding: '0px', fontSize: '1.4rem' }} />
                                              {this.props.residenceInfo.EntryDateSelected &&
                                                <label className="custom-label">Ngày đi</label>
                                              }
                                            </Form.Item>
                                          </Form>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>

                              </div>
                            </div>

                          </div>
                        </div>
                      </div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && !this.props.residenceInfo?.EntryDate &&
                      <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                        {this.state.errorMessage}
                      </span>
                    }
                    <div className={this.props?.residenceInfo?.PurposeOfResidence? "dropdown inputdropdown": "dropdown inputdropdown cus-select-nation"} style={{ paddingTop: '20px' }}>
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>
                              {this.props?.residenceInfo?.PurposeOfResidence &&
                                <label style={{ paddingTop: '20px' }}>Mục đích chuyến đi</label>
                              }
                              <Select
                                size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder="Mục đích chuyến đi"
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangePurposeOfResidence(v)}
                                value={this.props?.residenceInfo?.PurposeOfResidence}
                              >
                                {PURPOSE_OF_RESIDENCE.map((purpose, index) => (
                                  purpose &&
                                  <Option
                                    key={purpose}
                                    name={purpose}>{purpose}</Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                          <span
                            style={{
                              color: 'red',
                              'line-height': '22px'
                            }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && !this.props.residenceInfo?.PurposeOfResidence &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                    {this.props.residenceInfo?.PurposeOfResidence && (this.props.residenceInfo?.PurposeOfResidence === 'Khác') &&
                      <div className="info" style={{marginTop: '24px', marginBottom: '40px'}}>
                        <div className="info__body claim-type">
                          <div className="checkbox-wrap basic-column">
                            <div className="tab-wrapper">
                              <div className="tab"> {/* Thêm key vào đây */}
                                <div className="checkbox-warpper">
                                  <div style={{ width: '100%' }}>
                                    <div className="tab__content">
                                      <div className="input">
                                        <div className="input__content">
                                          {this.props.residenceInfo.PurposeOfResidenceDescription &&
                                            <label style={{ marginLeft: '2px' }}>Mô tả chi tiết mục đích</label>
                                          }
                                          <input type="search" placeholder="Mô tả chi tiết mục đích" maxLength="300"
                                            value={this.props.residenceInfo.PurposeOfResidenceDescription}
                                            onChange={(e) => this.props.onChangePurposeOfResidenceDescription(e.target.value)}
                                          />
                                        </div>
                                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                      </div>
                                    </div>
                                  </div>

                                </div>
                              </div>

                            </div>
                          </div>
                        </div>
                        {!this.state.validInputText && !this.props.validResidence && this.props.residenceInfo?.PurposeOfResidence && (this.props.residenceInfo?.PurposeOfResidence === 'Khác') && 
                        <span style={{ color: 'red', lineHeight: '64px', verticalAlign: 'bottom' }}>
                          {'Vui lòng nhập thông tin'}
                        </span>
                        }
                      </div>

                    }

                    <div className={this.props?.residenceInfo?.Duration? "dropdown inputdropdown": "dropdown inputdropdown cus-select-nation"} style={{marginTop: '20px'}}>
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>
                              {this.props?.residenceInfo?.Duration &&
                                <label style={{ paddingTop: '20px' }}>Thời gian cư trú tại Quốc gia đến</label>
                              }
                              <Select
                                size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder="Thời gian cư trú tại Quốc gia đến"
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangeDuration(v)}
                                value={this.props?.residenceInfo?.Duration}
                              >
                                {TIME_OF_RESIDENCE.map((time, index) => (
                                  time &&
                                  <Option
                                    key={time.code}
                                    name={time.name}>{time.name}</Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                          <span
                            style={{
                              color: 'red',
                              'line-height': '22px'
                            }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && !this.props.residenceInfo?.Duration &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                    <div className={this.props?.residenceInfo?.occupationSelected? "dropdown inputdropdown": "dropdown inputdropdown cus-select-nation"} style={{ marginTop: '20px' }}>
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>
                              {this.props?.residenceInfo?.occupationSelected &&
                                <label style={{ paddingTop: '20px' }}>Nghề nghiệp/việc làm tại Quốc gia đến</label>
                              }
                              <Select
                                suffixIcon={<SearchOutlined />}
                                allowClear
                                showSearch
                                // size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder="Nghề nghiệp/việc làm tại Quốc gia đến"
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangeResidenceOccupation(v, getOccupation(v))}
                                value={this.props?.residenceInfo?.occupationSelected}
                              // filterOption={(input, option) =>
                              //   option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                              // }
                              >
                                {this.state.occupationList && this.state.occupationList.map((occupation) => (
                                  occupation &&
                                  <Option
                                    key={occupation.code}
                                    name={occupation.nameVN}>{occupation.nameVN}</Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                        <span
                          style={{
                            color: 'red',
                            'line-height': '22px'
                          }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && (!this.props.residenceInfo?.NewOccName && this.props.residenceInfo?.OccDescription || (['Du học', 'Công tác', 'Định cư', 'Xuất khẩu lao động'].indexOf(this.props.residenceInfo?.PurposeOfResidence) >= 0)) &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                    <div className="info" style={{marginTop: '20px'}}>
                      <div className="info__body claim-type">
                        <div className="checkbox-wrap basic-column">
                          <div className="tab-wrapper">
                            <div className="tab"> {/* Thêm key vào đây */}
                              <div className="checkbox-warpper">
                                <div style={{ width: '100%' }}>
                                  <div className="tab__content">
                                    <div className="input">
                                      <div className="input__content">
                                        {this.props.residenceInfo.OccDescription && 
                                          <label style={{ marginLeft: '2px' }}>Mô tả chi tiết công việc</label>
                                        }
                                        <input type="search" placeholder="Mô tả chi tiết công việc" maxLength="300"
                                          value={this.props.residenceInfo.OccDescription}
                                          onChange={(e) => this.props.onChangeResidenceOccDescription(e.target.value)}
                                        />
                                      </div>
                                      <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                                    </div>
                                  </div>
                                </div>

                              </div>
                            </div>

                          </div>
                        </div>
                      </div>
                    </div>
                    {!this.state.validInputText && !this.props.validResidence && (this.props.residenceInfo?.NewOccName && !this.props.residenceInfo?.OccDescription || (['Du học', 'Công tác', 'Định cư', 'Xuất khẩu lao động'].indexOf(this.props.residenceInfo?.PurposeOfResidence) >= 0)) &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '44px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                  </>

                }
              </div>
            </div>
          </div>
          <div className="stepform" style={{ marginTop: '20px' }}>
            <div className="stepform__body">
              <div className="info residence">
                <LoadingIndicator area="claim-li-benefit-list" />
                <div className="info__body claim-type">
                  <div className="checkbox-wrap basic-column">
                    <div className="tab-wrapper">
                      <div className="tab"> {/* Thêm key vào đây */}
                        <div className="checkbox-warpper">
                          <label className={this.props.residenceCheck ? "checkbox2" : "checkbox"} htmlFor={'occupation'}>
                            <input
                              type="checkbox"
                              name={'occupation'}
                              id={'occupation'}
                              disabled={this.props.residenceCheck}
                              checked={this.props.occupationChecked ? true : false}
                              onClick={() => this.props.toggleOccupation()}
                            />
                            <div className="checkmark">
                              <img src={FE_BASE_URL + '/img/icon/check.svg'} alt="" />
                            </div>
                          </label>
                          <p className={this.props.residenceCheck ? "basic-text2 basic-grey" : "basic-bold basic-text2"}>THAY ĐỔI NGHỀ NGHIỆP/VIỆC LÀM</p>
                        </div>
                      </div>

                    </div>
                  </div>
                </div>
                {this.props.occupationChecked &&
                  <>
                    <div className={this.props?.occupationInfo?.occupationSelected? "dropdown inputdropdown": "dropdown inputdropdown cus-select-nation"} style={{ marginTop: '20px' }}>
                      <div className="dropdown__content">
                        <div
                          className="input-wrapper-item">
                          <div className="input" style={{ height: '59px', paddingTop: '10px' }}>
                            <div className="input__content" style={{ 'width': '100%', }}>
                              {this.props?.occupationInfo?.occupationSelected &&
                                <label style={{ paddingTop: '20px' }}>Chọn nghề nghiệp/việc làm</label>
                              }
                              <Select
                                suffixIcon={<SearchOutlined />}
                                showSearch
                                size='large'
                                style={{
                                  width: '100%',
                                  margin: '0'
                                }}
                                placeholder="Chọn nghề nghiệp/việc làm"
                                width='100%'
                                bordered={false}
                                optionFilterProp="name"
                                onChange={(v) => this.props.onChangeOccupation(v, getOccupation(v))}
                                value={this.props?.occupationInfo?.occupationSelected}
                              // filterOption={(input, option) =>
                              //   option.cityname.toLowerCase().indexOf(input.toLowerCase()) >= 0
                              // }
                              >
                                {this.state.occupationList && this.state.occupationList.map((occupation) => (
                                  occupation &&
                                  <Option
                                    key={occupation.code}
                                    name={occupation.nameVN}>{occupation.nameVN}</Option>
                                ))}
                              </Select>
                            </div>
                          </div>
                          {/* {this.state.errorCity.length > 0 &&
                        <span
                          style={{
                            color: 'red',
                            'line-height': '22px'
                          }}>{this.state.errorCity}</span>} */}
                        </div>
                      </div>
                      <div className="dropdown__items"></div>
                    </div>
                    {!this.state.validInputText && !this.props.validOccupation && !this.props.occupationInfo?.NewOccName &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }
                    <div className="tab__content" style={{ marginTop: '12px' }}>
                      <div className={this.state.occupationInfo?.error ? "input validate" : "input"}>
                        <div className="input__content">
                          {this.props.occupationInfo.OccDescription && 
                            <label style={{ marginLeft: '2px' }}>Mô tả chi tiết công việc</label>
                          }
                          <input type="search" placeholder="Mô tả chi tiết công việc" maxLength="300"
                            value={this.props.occupationInfo.OccDescription}
                            onChange={(e) => this.props.onChangeOccDescription(e.target.value)}
                          />
                        </div>
                        <i><img src={FE_BASE_URL + "/img/icon/edit.svg"} alt="" /></i>
                      </div>
                    </div>
                    {!this.state.validInputText && !this.props.validOccupation && !this.props.occupationInfo?.OccDescription &&
                      <div style={{ paddingTop: '12px' }}>
                        <span style={{ color: 'red', lineHeight: '24px', verticalAlign: 'top' }}>
                          {this.state.errorMessage}
                        </span>
                      </div>
                    }

                  </>
                }
              </div>
            </div>
          </div>
          <div className="bottom-text"
            style={{ 'maxWidth': '594px', backgroundColor: '#f5f3f2' }}>
            <p style={{ textAlign: 'center', paddingLeft: '30px', paddingRight: '30px' }}>
              <span className="red-text basic-bold">Lưu ý: </span>
                Thông tin điều chỉnh/thay đổi sẽ được cập nhật cho (các) hợp đồng bảo hiểm của Khách hàng.
            </p>
          </div>
          {getSession(IS_MOBILE)&&
          <div className='nd13-padding-bottom64'></div>
          }
          <div className="bottom-btn">
            {!this.props.isSubmitting && this.props.validAttacthment && this.props.validateMinimum ? (
              <button className={"btn btn-primary"} onClick={() => nextStep()}
                >Tiếp tục</button>
            ) : (
              <button className={"btn btn-primary disabled"} disabled
                >Tiếp tục</button>
            )}

          </div>
        </div>
      </section>
    );
  }
}

export default InputPersonalInfo;
