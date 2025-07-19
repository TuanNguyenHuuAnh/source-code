import React, { Component } from 'react';
import { FE_BASE_URL, OTHERS_CV, IDDOCUMENTS_PP, OTHERS, OTHERS_NC, IS_MOBILE } from '../sdkConstant';
import { getMapSize, getSession, topFunction } from '../sdkCommon';
import LoadingIndicator from '../LoadingIndicator2';
import LoadingIndicatorBasic from '../LoadingIndicatorBasic';
import AdditionalFileUpload from './AdditionalFileUpload';


class AdditionalAttachment extends Component {

  constructor(props) {
    super(props);
    this.state = {
      stepName: this.props.stepName,
    }
  }

  componentDidMount() {
    topFunction();
  }

  render() {
    const validateAddtionalAtt=(additionalDocRequire)=> {
      let personalAttachmentMap = this.props.personalInfo.attachmentState.attachmentMap;
      let attachmentMap = this.props.attachmentState.attachmentMap;
      let mergeMap = {...personalAttachmentMap, ...attachmentMap};
      let notUploaded = additionalDocRequire && additionalDocRequire.find(SubDocId => getMapSize(mergeMap[SubDocId]) <= 0);
      if (notUploaded) {
        return false;
      }
      return true;
    }

    let additionalDocRequire = [];
    let remainList = [];
    if (this.props.docTypeProfile) {
      remainList = this.props.docTypeProfile.filter(item => 
        (!this.props.personalInfo.attachmentState?.attachmentMap[item.SubDocId] || (this.props.personalInfo.attachmentState?.attachmentMap[item.SubDocId]?.length <= 0)));
      console.log('remainList=', remainList);
    }
    let docTypeList = [];
    if (this.props.residenceCheck) {
      let passport = remainList.find(item => item.SubDocId === IDDOCUMENTS_PP);
      if (passport) {
        docTypeList.push(passport);
        additionalDocRequire.push(IDDOCUMENTS_PP);
      }
      let visa = remainList.find(item => item.SubDocId === OTHERS_NC);
      if (visa) {
        docTypeList.push(visa);
        additionalDocRequire.push(OTHERS_NC);
      }
      
    }

    if (this.props.personalInfoChecked && (this.props.personalInfo?.personalInfoChange[0]?.Checked === true) || ((this.props.personalInfo?.personalInfoChange[2]?.Checked === true) && (this.props.personalInfo?.personalInfoChange[1]?.Checked === true))) {
      let others = this.props.docTypeProfile.find(item => item.SubDocId === OTHERS_CV);
      if (others) {
        docTypeList.push(others);
      }
    }

    // if (this.props.personalInfoChecked || this.props.occupationChecked) {
      let others = this.props.docTypeProfile.find(item => item.SubDocId === OTHERS);
      if (others) {
        docTypeList.push(others);
      }
    // }
    
    let disableButton = !validateAddtionalAtt(additionalDocRequire);

    const nextStep=()=> {
      this.props.updateStepName(this.props.stepName + 1);
      // this.props.saveLocal();
    }
    
    return (
      <section className="sccontract-warpper change-policy-info" id="scrollAnchor">
        <div className="insurance claim-type">
          <div className={getSession(IS_MOBILE)?"stepform margin-top170":"stepform"}>
            <div className="stepform__body">
              <div className="info">
                <LoadingIndicator area="claim-li-benefit-list" />
                <>
                {/* <img style={{ marginLeft: '-22px', minWidth: 'calc(100% + 44px)' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} /> */}
                <div className="info__title" style={{marginBottom: '16px'}}>
                  <h4>ĐÍNH KÈM CHỨNG TỪ</h4>
                </div>

                {/* {DocTypeSelected && (DocTypeSelected.length === 2) &&
                  <div className='dash-line-claim' style={{marginBottom: '16px', marginTop: '12px'}}></div>
                } */}
                <div>
                {docTypeList && docTypeList.map((item) => (
                    <AdditionalFileUpload
                      SubDocId={item?.SubDocId}
                      SubDocName={additionalDocRequire.includes(item?.SubDocId) ? item?.SubDocName + ' (bắt buộc)': item?.SubDocName}
                      attachmentState={this.props.attachmentState}
                      dragFileLeave={this.props.dragFileLeave}
                      dropFile={this.props.dropFile}
                      uploadAttachment={this.props.uploadAttachment}
                      // updateAttachmentList={this.props.updateAttachmentList}
                      deleteAttachment={this.props.deleteAttachment}
                      dragFileOver={this.props.dragFileOver}
                      showNotice={this.props.showNotice}
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
                </>
                
              </div>
            </div>


            <img className="decor-clip" src={FE_BASE_URL  + "/img/mock.svg"} alt="" />
            <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
          </div>


          <div className="bottom-text"
            style={{ 'maxWidth': '594px', backgroundColor: '#f5f3f2' }}>
            <p style={{ textAlign: 'justify' }}>
              <span className="red-text basic-bold">Lưu ý: </span>
              <ul className="list-information" style={{ color: '#727272', marginLeft: '0' }}>
                1. Hỗ trợ hình ảnh định dạng JPG, JPEG, PNG và có dung lượng dưới 5MB.
                <br />
                2. Các chứng từ yêu cầu ở trên cần thiết để xử lý yêu cầu thay đổi/điều chỉnh thông tin. Quý khách có thể được yêu cầu bổ sung chứng từ, chứng từ gốc để hoàn tất xử lý yêu cầu.
              </ul>
            </p>
          </div>
          {getSession(IS_MOBILE)&&
          <div className='nd13-padding-bottom36'></div>
          }
          <div className="bottom-btn">
          {disableButton?(
            <button className={"btn btn-primary disabled"} disabled
            >Tiếp tục</button>
          ):(
            <button className={"btn btn-primary"} onClick={()=>nextStep()}
            >Tiếp tục</button>
          )}

          </div>
        </div>
      </section>
    );
  }
}

export default AdditionalAttachment;
