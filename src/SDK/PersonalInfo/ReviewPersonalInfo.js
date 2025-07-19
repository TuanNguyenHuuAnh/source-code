import React, { Component } from 'react';
import { FE_BASE_URL, PAGE_POLICY_PAYMENT, PERSONAL_INFO_STATE, IS_MOBILE, TIME_OF_RESIDENCE, IDDOCUMENTS_PP, OTHERS_NC, OTHERS_CV } from '../sdkConstant';
import { formatFullName, formatDate, callbackAppOpenLink, getSession, getMapSize, getUrlParameter, convertObjectToArray, removeEmptyEntries, topFunction  } from '../sdkCommon';
import PersonalInfoUploadReview from './PersonalInfoUploadReview';
import iconArrowLeftGrey from '../../img/icon/arrow-left-grey.svg';
import iconArrowDownGrey from '../../img/icon/arrow-down-grey.svg';
import ImageViewerMap from '../ImageViewerMap';
import { isEmpty } from 'lodash';

let from = '';
class ReviewPersonalInfo extends Component {

  constructor(props) {
    super(props);
    this.state = {
      stepName: this.props.stepName,
      showProfile: true,      
      docTypeProfile: this.props.docTypeProfile,
      docTypeRebuild: this.props.docTypeRebuild,
      occupationList: this.props.occupationList,
      acceptPolicy: false

    }
    // this.handlerOnChangeDocType = this.OnChangeDocType.bind(this);
  }

  componentDidMount() {
    topFunction();
    from = getUrlParameter("fromApp");
    this.props.saveLocal();
  }


  render() {
    const acceptPolicy = () => {
      this.setState({acceptPolicy: !this.state.acceptPolicy});
    }
    const getSubDocName=(subDocId)=> {
      let DocTypeItem = this.props.docTypeProfile?.find(item => item?.SubDocId === subDocId);
      if (DocTypeItem) {
        return DocTypeItem.SubDocName;
      }
      return null;
    }
    let attachmentMap  = this.props.personalInfo.attachmentState.attachmentMap;
    let copiedAttachmentMap = new Map(Object.entries(attachmentMap));

    let additionalAttachmentMap = this.props.attachmentState.attachmentMap;
    let copiedMap = new Map(Object.entries(additionalAttachmentMap));

    if (!this.props.residenceCheck) {
      copiedMap.delete(IDDOCUMENTS_PP);
      copiedMap.delete(OTHERS_NC);
    }
    
    if (!this.props.personalInfoChecked || !this.props.personalInfo?.personalInfoChange[0]?.Checked && (!this.props.personalInfo?.personalInfoChange[2]?.Checked || !this.props.personalInfo?.personalInfoChange[1]?.Checked)) {
      copiedMap.delete(OTHERS_CV);
    }
    copiedAttachmentMap = removeEmptyEntries(copiedAttachmentMap); 
    copiedMap = removeEmptyEntries(copiedMap); 
    let updatedAdditionalObj = Object.fromEntries(copiedMap);
    let updatedAttachmentObj = Object.fromEntries(copiedAttachmentMap);

    let mergeMap = {...updatedAttachmentObj, ...updatedAdditionalObj};

    console.log('mergeMap=', mergeMap);
    let mergeArray = convertObjectToArray(mergeMap);
    console.log('xxx=', mergeArray);
    const changePolInfoSubmit=()=> {
      // alert('changePolInfoSubmit');
      this.props.changePolicyInfoSubmit();
    }

    const dropdownContent=()=> {
      this.setState({showProfile: !this.state.showProfile});
    }

    const getDurationName=(code)=> {
      let duration = TIME_OF_RESIDENCE.find(item=>item.code === code);
      if (duration) {
        return duration.name;
      } else {
        return code;
      }
    }
    return (
      <section className="sccontract-warpper change-policy-info" id="scrollAnchor">
        <div className="insurance claim-type">
          <div className={getSession(IS_MOBILE)?"stepform margin-top170":"stepform"}>
            <div className="stepform__body">
              <div className="info">
                
                <div className="contractform__head2" style={{padding: '12px 0'}}>
                  <div className="dropdown__content" onClick={() => dropdownContent()}>
                      <p className="card-dropdown-title basic-bold"
                          style={{
                              color: '#292929',
                              padding: '12px 0',
                              margin: '0px'
                          }}>{formatFullName(this.props.selectedLI.FullName)}</p>
                      <p className="arrow">
                          {this.state.showProfile?(
                              <img style={{width: '16px', height: 'auto'}}
                              src={iconArrowDownGrey}
                              alt="arrow-down-icon"/>
                          ):(
                              <img style={{width: '16px', height: 'auto'}}
                              src={iconArrowLeftGrey}
                              alt="arrow-left-icon"/>
                          )}

                      </p>
                  </div>
                  {this.state.showProfile &&
                  <div className="contractform__head-content">
                    <div className="contractform__head-content">
                      <div className="item" style={{padding: '7px 0'}}>
                        <p className="item-label">Số giấy tờ tùy thân</p>
                        <p className="item-content">{this.props.selectedLI.LifeInsuredIDNum} </p>
                      </div>
                      <div className="item" style={{padding: '7px 0'}}>
                        <p className="item-label">Số hộ chiếu</p>
                        <p className="item-content">{this.props.selectedLI.Passport} </p>
                      </div>
                      <div className="item" style={{padding: '7px 0'}}>
                        <p className="item-label">Ngày tháng năm sinh</p>
                        <p className="item-content">{formatDate(this.props.selectedLI.DOB)} </p>
                      </div>
                      <div className="item" style={{padding: '7px 0'}}>
                        <p className="item-label">Giới tính</p>
                        <p className="item-content">{this.props.selectedLI.Gender === 'M'? 'Nam': (this.props.selectedLI.Gender === 'F'? 'Nữ': '')} </p>
                      </div>
                    </div>

                  </div>
                  }
                </div>
                <img style={{ marginLeft: '-22px', minWidth: 'calc(100% + 44px)' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                <div className="info__title">
                    <h4>XÁC NHẬN THÔNG TIN ĐIỀU CHỈNH</h4>
                    <span className="update-btn simple-brown4" style={{lineHeight: '14px'}}
                          onClick={() => this.props.updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO)}>Cập nhật</span>
                </div>
                {this.props.personalInfoChecked &&
                <div className="info__body">
                  <h5 className='basic-bold'>Thay đổi thông tin nhân thân</h5>
                  <div className="item" style={{paddingTop: '12px'}}>
                    <div className="item__content">
                        <div className="tab"> {/* Thêm key vào đây */}
                        {this.props.personalInfo.personalInfoChange[0]?.Checked && this.props.personalInfo.personalInfoChange[0]?.NewFamilyName  &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Họ và tên đệm</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[0]?.NewFamilyName } 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                          {this.props.personalInfo.personalInfoChange[0]?.Checked && this.props.personalInfo.personalInfoChange[0]?.NewGivenName &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Tên</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[0]?.NewGivenName} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                          {this.props.personalInfo.personalInfoChange[1]?.Checked && this.props.personalInfo.personalInfoChange[1]?.NewClientIDNumber &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Số giấy tờ tùy thân</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[1]?.NewClientIDNumber} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                           }
                          {this.props.personalInfo.personalInfoChange[1]?.ppChecked && this.props.personalInfo.personalInfoChange[1]?.NewClientPassportNumber &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Số hộ chiếu</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[1]?.NewClientPassportNumber} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                           }
                           {this.props.personalInfo.personalInfoChange[2]?.Checked && this.props.personalInfo.personalInfoChange[2]?.NewDOB &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Ngày tháng năm sinh</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[2]?.NewDOB} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                          {this.props.personalInfo.personalInfoChange[3]?.Checked && this.props.personalInfo.personalInfoChange[3]?.NewGender &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Giới tính</label>
                                  <input 
                                  value={this.props.personalInfo.personalInfoChange[3]?.NewGender} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                        </div>
                      </div>
                  </div>


                </div>
                }
                {this.props.residenceCheck &&
                <div className="info__body" style={{paddingTop: '12px'}}>
                  <h5 className='basic-bold'>Thay đổi Quốc gia cư trú</h5>
                  <div className="item" style={{paddingTop: '12px'}}>
                    <div className="item__content">
                        <div className="tab"> {/* Thêm key vào đây */}
                          {this.props?.residenceInfo?.NewCountryOfResidence  &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Quốc gia đến</label>
                                  <input 
                                  value={this.props?.residenceInfo?.NewCountryOfResidence} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                          {this.props?.residenceInfo?.EntryDate  &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Ngày đi</label>
                                  <input 
                                  value={this.props?.residenceInfo?.EntryDate} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                          
                          {this.props?.residenceInfo?.PurposeOfResidenceDescription ? (
                            <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Mục đích chuyến đi</label>
                                  <input 
                                  value={this.props?.residenceInfo?.PurposeOfResidenceDescription} 
                                  disabled
                                  type="search"/>
                              </div>
                            </div>
                        </div>
                          ) : (
                            this.props?.residenceInfo?.PurposeOfResidence &&
                            <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Mục đích chuyến đi</label>
                                  <input
                                    value={this.props?.residenceInfo?.PurposeOfResidence}
                                    disabled
                                    type="search" />
                                </div>
                              </div>
                            </div>
                          )
                        }
                        {this.props?.residenceInfo?.Duration &&
                        <div className="tab__content">
                            <div className="input disabled">
                              <div className="input__content">
                                <label>Thời gian cư trú tại Quốc gia đến</label>
                                <input 
                                value={getDurationName(this.props?.residenceInfo?.Duration)} 
                                disabled
                                type="search"/>
                              </div>
                            </div>
                        </div>
                        }
                        {this.props?.residenceInfo?.NewOccName &&
                        <div className="tab__content">
                           <div className="input disabled">
                              <div className="input__content">
                                <label>Nghề nghiệp/việc làm</label>
                                <input 
                                value={this.props?.residenceInfo?.NewOccName} 
                                disabled
                                type="search"/>
                              </div>
                            </div>
                        </div>
                        }
                        {this.props?.residenceInfo?.OccDescription &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Mô tả chi tiết công việc</label>
                                  <input 
                                  value={this.props?.residenceInfo?.OccDescription} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                        }
                        </div>
                      </div>
                  </div>


                </div>
                }
                {this.props.occupationChecked &&
                <div className="info__body" style={{paddingTop: '12px'}}>
                  <h5 className='basic-bold'>Thay đổi nghề nghiệp/việc làm</h5>
                  <div className="item" style={{paddingTop: '12px'}}>
                    <div className="item__content">
                        <div className="tab"> {/* Thêm key vào đây */}
                          {this.props?.occupationInfo?.NewOccName &&
                            <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Nghề nghiệp/việc làm</label>
                                  <input
                                    value={this.props?.occupationInfo?.NewOccName}
                                    disabled
                                    type="search" />
                                </div>
                              </div>
                            </div>
                          }
                          {this.props?.occupationInfo?.OccDescription &&
                          <div className="tab__content">
                              <div className="input disabled">
                                <div className="input__content">
                                  <label>Mô tả chi tiết công việc</label>
                                  <input 
                                  value={this.props?.occupationInfo?.OccDescription} 
                                  disabled
                                  type="search"/>
                                </div>
                              </div>
                          </div>
                          }
                        </div>
                      </div>
                  </div>


                </div>
                }
                {this.props.residenceCheck && ((this.props.residenceInfo.NewCountryOfResidence.indexOf('Hoa Kỳ') >= 0) || (this.props.residenceInfo.NewCountryOfResidenceCode === 'US')) &&
                <>
                  <div className="dash-border-pd"></div>
                  <div className="info__title"  style={{paddingTop: '24px'}}>
                      <h4>XÁC NHẬN THÔNG TIN</h4>
                  </div>
                  <div className="info__body">
                    <div className="checkbox-warpper">
                      <label className="checkbox2" htmlFor="isAmericanNationality">
                        <input type="checkbox" name="isAmericanNationality"
                          id="isAmericanNationality"
                          checked={this.props.USNationality === 'Yes'}
                          disabled
                        />
                        <div className="checkmark">
                          <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                        </div>
                      </label>
                      <p className="text">Quốc tịch Hoa Kỳ</p>
                    </div>
                    <div className="checkbox-warpper">
                      <label className="checkbox2">
                        <input type="checkbox" name="isAmericanResidence"
                          id="isAmericanResidence"
                          checked={this.props.USPermanent === 'Yes'}
                          disabled
                        />
                        <div className="checkmark">
                          <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                        </div>
                      </label>
                      <p className="text">Địa chỉ thường trú tại Hoa Kỳ</p>
                    </div>
                    <div className="checkbox-warpper">
                      <label className="checkbox2">
                        <input type="checkbox" name="isAmericanTax" id="isAmericanTax"
                          checked={this.props.USTaxDeclared === 'Yes'}
                          disabled
                        />
                        <div className="checkmark">
                          <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                        </div>
                      </label>
                      <p className="text">Thực hiện khai báo thuế tại Hoa Kỳ</p>
                    </div>
                    {/* <div className="title-head">
                          <p>Xác nhận và cam kết của Người yêu cầu giải quyết quyền lợi bảo hiểm:</p>
                      </div> */}
                    {((this.props.USNationality === 'Yes') || (this.props.USPermanent === 'Yes') || (this.props.USTaxDeclared === 'Yes')) &&
                      <>
                        <div className="dash-border-pd"></div>
                        <div className="content" style={{ paddingTop: '12px' }}>
                          <p className='basic-red' style={{lineHeight: '20px'}}>
                            Quý khách vui lòng liên hệ Đại lý bảo hiểm hoặc Tổng đài Dịch vụ Khách hàng để được hướng dẫn điền Tờ khai theo mẫu {(this.props.USNationality !== 'Yes') && (this.props.USPermanent !== 'Yes')? 'W-8BEN': 'W-9'} và nộp về Văn phòng Dai-ichi Life Việt Nam.
                          </p>
                        </div>
                      </>
                    }
                  </div>
                </>
                }
                {getMapSize(mergeMap) > 0 && !isEmpty(mergeArray) &&
                  <>
                    <div className="dash-border-pd"></div>
                    <div className="info__title"  style={{paddingTop: '12px'}}>
                        <h4>Giấy tờ/ chứng từ đính kèm</h4>
                        <span className="update-btn simple-brown4"  style={{lineHeight: '14px'}}
                              onClick={() => this.props.updateStepName(PERSONAL_INFO_STATE.ATTACHMENT)}>Cập nhật</span>
                    </div>
                  </>
                }

                {/* {Object.entries(mergeMap).map(([SubDocId, value]) => (
                  <PersonalInfoUploadReview
                    SubDocId={SubDocId}
                    SubDocName={getSubDocName(SubDocId)}
                    attachmentMap={mergeMap}
                  />
                ))} */}
                  {/* <div className="optionalform__body">
                      {!isEmpty(mergeMap) && Object.entries(mergeMap).map(([SubDocId, value]) =>  {
                          return (<div className="imglist" key={'sub-att-' + SubDocId}>
                              <div className="imglist__title">
                                  <h5 className="basic-semibold">{value}</h5>
                              </div>
                              <div className="imglist__content">
                                  <div className="imgtab-wrapper">
                                      {!isEmpty(mergeMap) && <ImageViewerBase64 images={convertObjectToArray(mergeMap)}/>}
                                  </div>
                              </div>
                          </div>);
                      })}
                  </div> */}
                  {!isEmpty(mergeMap) && !isEmpty(mergeArray) && Object.entries(mergeMap).map(([SubDocId, value]) =>  {
                      return (<div className="imglist" key={'sub-att-' + SubDocId}>
                          <div className="imglist__title">
                              <h5 className="basic-semibold">{getSubDocName(SubDocId)}</h5>
                          </div>
                          <div className="imglist__content">
                              <div className="imgtab-wrapper">
                                  {!isEmpty(value) && <ImageViewerMap images={value}/>}
                              </div>
                          </div>
                      </div>);
                  })}
              </div>
            </div>


            <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt="" />
            <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
          </div>

                        <div className='paymode-margin-bottom' style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <div className={this.state.acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                    style={{
                                        'maxWidth': '594px',
                                        backgroundColor: '#f5f3f2',
                                        paddingLeft: '6px'
                                    }}>
                                <div
                                    className={this.state.acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                    style={{flex: '0 0 auto', height: '20px', cursor: 'pointer'}}
                                    onClick={() => acceptPolicy()}>
                                    <div className="checkmark">
                                        <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                    </div>
                                </div>
                                <div className="policy-info-tac" style={{
                                    textAlign: 'justify',
                                    paddingLeft: '12px'
                                }}>
                                    <p style={{textAlign: 'left', fontWeight: "bold"}}>Tôi đồng ý và xác nhận:</p>
                                    <ul className="list-information">
                                        <li className="sub-list-li">
                                          - Tất cả thông tin trên đây là đầy đủ, đúng sự thật và hiểu rằng yêu cầu này chỉ có hiệu lực kể từ ngày được Dai-ichi Life Việt Nam chấp nhận.
                                        </li>
                                        <li className="sub-list-li">
                                          - Thông tin điều chỉnh/thay đổi sẽ được cập nhật cho (các) Hợp đồng bảo hiểm của Khách hàng.
                                        </li>
                                        {/* <li className="sub-list-li">
                                            - Thông tin điều chỉnh/thay đổi CCCD (nếu có) sẽ được cập nhật cho (các) Hợp đồng bảo hiểm của Bên mua bảo hiểm.
                                        </li> */}
                                        <li className="sub-list-li">
                                          - Với xác nhận hoàn tất giao dịch, đồng ý với
                                            {getSession(IS_MOBILE)?(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href='#'
                                                onClick={()=>callbackAppOpenLink(PAGE_POLICY_PAYMENT, from)}
                                                >{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            ):(
                                                <a
                                                style={{display: 'inline'}}
                                                className="red-text basic-bold"
                                                href={PAGE_POLICY_PAYMENT}
                                                target='_blank'>{' Điều kiện và Điều khoản Giao dịch điện tử.'}
                                                </a> 
                                            )}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
          <div className="bottom-btn">
            {this.state.acceptPolicy && !this.props.isSubmitting?(
              <button className={"btn btn-primary"} onClick={()=>changePolInfoSubmit()}>
                Xác nhận
              </button>
            ):(
              <button className={"btn btn-primary disabled"} disabled>
                Xác nhận
              </button>
            )}
          </div>
        </div>
      </section>
    );
  }
}

export default ReviewPersonalInfo;
