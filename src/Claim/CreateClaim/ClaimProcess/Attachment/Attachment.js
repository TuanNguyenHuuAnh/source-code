// import 'antd/dist/antd.css';

import React, { Component } from 'react';
import imageCompression from 'browser-image-compression';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, AUTHENTICATION, COMPANY_KEY, CLAIM_TYPE, FE_BASE_URL } from '../../../../constants';
import { getDocType } from '../../../../util/APIUtils';
import { getSession, checkListRequireContain, getDeviceId } from '../../../../util/common';
import { CLAIM_STATE } from '../../CreateClaim';
import LoadingIndicator from '../../../../common/LoadingIndicator2';
import LoadingIndicatorBasic from '../../../../common/LoadingIndicatorBasic';

import AlertPopupError from '../../../../components/AlertPopupError';
let fileDialogOpen = false;
class Attachment extends Component {

  constructor(props) {
    super(props);


    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
          Action: 'DocTypeGroup',
        }
      },

      stepName: CLAIM_STATE.ATTACHMENT,
      zipCodeList: [],
      bankList: [],
      popupCalled: false,
      popupData: null,

      jsonResponse: null,
      requireObj: [],
      showError: false,
      showMaxImageOnSectionError: false,
      uploading: false,
      docTypeUploading: ''
    }

    this.handlerUploadAttachment = this.uploadAttachment.bind(this);
    this.handlerDragOver = this.dragFileOver.bind(this);
    this.handlerDragLeave = this.dragFileLeave.bind(this);
    this.handlerDrop = this.dropFile.bind(this);
    this.handlerDeleteAttachment = this.deleteAttachment.bind(this);
    this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
    this.handlerSetCloseButtonRef = this.setCloseButtonRef.bind(this);
    this.handlerOpenPopup = this.openPopup.bind(this);
    this.handlerClosePopup = this.closePopup.bind(this);
  }


  componentDidMount() {
    // window.addEventListener('dragover', function(event) {
    //   event.preventDefault();
    // });
  

    var jsonState = this.props.attachmentState;
    // const claimTypeState = this.props.claimTypeState;
    const apiRequest = Object.assign({}, this.state.jsonInput);
    apiRequest.jsonDataInput['ClaimType'] = (this.props.claimCheckedMap[CLAIM_TYPE.HEALTH_CARE] ? 'Healthcare;' : '')
      // + (claimTypeState.isDeathClaim ? 'Death;' : '')
      // + (claimTypeState.isAccidentClaim ? 'Accident;' : '')
      // + (claimTypeState.isSickClaim && claimTypeState.isIllness ? 'Illness;' : '');
      + (this.props.claimCheckedMap[CLAIM_TYPE.HS] ? 'HS;' : '')
      + (this.props.claimCheckedMap[CLAIM_TYPE.ACCIDENT] ? 'Accident;' : '')
      + (this.props.claimCheckedMap[CLAIM_TYPE.ILLNESS] ? 'Illness;' : '')
      + (this.props.claimCheckedMap[CLAIM_TYPE.TPD] ? 'TPD;' : '')
      + (!this.props.claimCheckedMap[CLAIM_TYPE.DEATH] ? '' : 'Death');
    getDocType(apiRequest).then(Res => {
      let Response = Res.Response;
      if (Response.Result === 'true' && Response.ClientProfile !== null) {
        let profile = Response.ClientProfile;
        profile.forEach(elem => {
          let attachmentData = jsonState.attachmentData;
          let attachmentList = [];
          if (attachmentData !== null && attachmentData !== undefined) {
            let docTypeInfo = attachmentData.find(docType => docType.DocID === elem.DocID);
            if (docTypeInfo !== null && docTypeInfo !== undefined) {
              attachmentList = docTypeInfo.attachmentList;
            }
          }
          elem['attachmentList'] = attachmentList !== null && attachmentList !== undefined && attachmentList.length >= 0 ?
            attachmentList : [];
        });
        jsonState.attachmentData = profile;
        // this.updateState(jsonState);
        this.props.updateAttachmentData(jsonState)
        // 
        // this.setState({attachmentData: Response.ClientProfile});
        if (document.getElementById('scrollAnchor')) {
          document.getElementById('scrollAnchor').scrollIntoView({ behavior: 'smooth' })
        }
        // this.props.handlerLoadedAttachmentData(this.state.jsonResponse.ClientProfile);
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        // Alert.warning(EXPIRED_MESSAGE);
        // logoutSession();
        // this.props.history.push({
        //   pathname: '/home',
        //   state: { authenticated: false, hideMain: false }

        // })
      }
    }).catch(error => {
      // this.props.history.push('/maintainence');
    });
  }

  // componentWillUnmount() {
  //   window.addEventListener('drop', function(event) {
  //     event.preventDefault();
  //   });
  // }

  updateState(jsonState) {
    var c1Attachments = jsonState.attachmentData.find(attachment => attachment.attachmentList.length > 0);
    jsonState.disabledButton = !(c1Attachments);
    this.props.handlerUpdateMainState("attachmentState", jsonState);
  }

  uploadAttachment(docId, event) {
    event.preventDefault();
    this.setState({uploading: true});
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    const files = Array.from(event.target.files);
    const attachments = this.props.attachmentState.attachmentData.find(attachment => attachment.DocID === docId);
    if (files && files.length > 20) {
      event.target.value = null;
      this.setState({showMaxImageOnSectionError: true, uploading: false});
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)  ) {
      event.target.value = null;
      this.setState({showMaxImageOnSectionError: true, uploading: false});
      return;
    }
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        let fileSize = ((file.size / 1024) / 1024).toFixed(4);
        if (validateImage.includes(file.type)) {
          try {
            let options = {
              maxSizeMB: 3, // Giới hạn kích thước file sau khi nén
              maxWidthOrHeight: 1920, // Giới hạn kích thước chiều rộng hoặc chiều cao
              useWebWorker: true, // Sử dụng Web Workers để cải thiện hiệu suất
              initialQuality: 1
            };
            if (fileSize > 2) {
              options = {
                maxSizeMB: 3, // Giới hạn kích thước file sau khi nén
                maxWidthOrHeight: 1920, // Giới hạn kích thước chiều rộng hoặc chiều cao
                useWebWorker: true, // Sử dụng Web Workers để cải thiện hiệu suất
                initialQuality: 0.2
              };
            }
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error(error);
            // this.setState({showError: true});
            // reject(error);
          }
        } else {
          this.setState({showError: true});
          // reject(new Error('Invalid file type or size'));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, docId, event) => {
      let index = 0;
      const processNextBatch = () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        Promise.all(promises).then(results => {
          var jsonState = this.props.attachmentState;
          var arrAttData = results.map(base64 => {
            return { imgData: base64 }
          });
          var attachments = jsonState.attachmentData.find(attachment => attachment.DocID === docId);
          if (attachments && attachments.attachmentList) {
            attachments.attachmentList.push(...arrAttData);
            this.updateState(jsonState);
          }
          index += batchSize;
          if (index < files.length) {
            processNextBatch();
          } else {
            event.target.value = null;
            this.setState({uploading: false});
          }
        }).catch(err => {
          console.error(err);
          this.setState({showError: true});
        });
      };
      processNextBatch();
    };
  
    processFilesInBatches.call(this, files, 5, processFile, docId, event);
  }

  dragFileOver(htmlId, event) {
    event.preventDefault();
    document.getElementById(htmlId).className = "img-upload active";
  }

  dragFileLeave(htmlId, event) {
    event.preventDefault();
    document.getElementById(htmlId).className = "img-upload";
  }

  dropFile(docId, event) {
    event.preventDefault();
    this.setState({uploading: true});
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    const files = Object.values(event.dataTransfer.files);
    const attachments = this.props.attachmentState.attachmentData.find(attachment => attachment.DocID === docId);
    if (files && files.length > 20) {
      event.dataTransfer.clearData();
      this.setState({showMaxImageOnSectionError: true, uploading: false});
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)  ) {
      event.dataTransfer.clearData();
      this.setState({showMaxImageOnSectionError: true, uploading: false});
      return;
    }
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        let fileSize = ((file.size / 1024) / 1024).toFixed(4);
        if (validateImage.includes(file.type)) {
          try {
            let options = {
              maxSizeMB: 3, // Giới hạn kích thước file sau khi nén
              maxWidthOrHeight: 1920, // Giới hạn kích thước chiều rộng hoặc chiều cao
              useWebWorker: true, // Sử dụng Web Workers để cải thiện hiệu suất
              initialQuality: 1
            };
            if (fileSize > 2) {
              options = {
                maxSizeMB: 3, // Giới hạn kích thước file sau khi nén
                maxWidthOrHeight: 1920, // Giới hạn kích thước chiều rộng hoặc chiều cao
                useWebWorker: true, // Sử dụng Web Workers để cải thiện hiệu suất
                initialQuality: 0.2
              };
            }
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error(error);
            // this.setState({showError: true});
            // reject(error);
          }
        } else {
          this.setState({showError: true});
          // reject(new Error('Invalid file type or size'));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, docId, event) => {
      let index = 0;
      const processNextBatch = () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        Promise.all(promises).then(results => {
          var jsonState = this.props.attachmentState;
          var arrAttData = results.map(base64 => {
            return { imgData: base64 }
          });
          var attachments = jsonState.attachmentData.find(attachment => attachment.DocID === docId);
          if (attachments && attachments.attachmentList) {
            attachments.attachmentList.push(...arrAttData);
            this.updateState(jsonState);
          }
          index += batchSize;
          if (index < files.length) {
            processNextBatch();
          } else {
            event.dataTransfer.clearData();
            this.setState({uploading: false});
          }
        }).catch(err => {
          console.error(err);
          // this.setState({showError: true});
        });
      };
      processNextBatch();
    };
    processFilesInBatches.call(this, files, 5, processFile, docId, event);
  }

  deleteAttachment(docId, attachmentIndex) {
    var jsonState = this.props.attachmentState;
    var attachments = jsonState.attachmentData.find(attachment => attachment.DocID === docId);
    if (attachments !== null && attachments !== undefined) {
      attachments.attachmentList.splice(attachmentIndex, 1)
      this.updateState(jsonState);
    }
  }

  setWrapperRef(node) {
    this.wrapperRef = node;
  }

  setCloseButtonRef(node) {
    this.closeButtonRef = node;
  }

  openPopup(docTypeData) {
    var compJsonState = this.state;
    compJsonState.popupCalled = true;
    compJsonState.popupData = docTypeData;
    this.setState(compJsonState);
    document.getElementById("popupAttachmentClaim").className = "popup special hoso-popup show";
    document.addEventListener('mousedown', this.handlerClosePopup);
  }

  closePopup(event) {
    if ((this.wrapperRef && !this.wrapperRef.contains(event.target))
      || (this.closeButtonRef && this.closeButtonRef.contains(event.target))) {
      document.getElementById("popupAttachmentClaim").className = "popup special hoso-popup";
      var compJsonState = this.state;
      compJsonState.popupCalled = false;
      this.setState(compJsonState);
      document.removeEventListener('mousedown', this.handlerClosePopup);
    }
  }

  render() {
    const jsonState = this.props.attachmentState;
    const compJsonState = this.state;

    const attachmentData = jsonState.attachmentData;
    // if (!attachmentData && this.state.attachmentData) {
    //   attachmentData = this.state.attachmentData;
    // }

    const openBase64 = (data) => {
      var image = new Image();
      image.src = data;
      var w = window.open("");
      w.document.write(image.outerHTML);
    }

    const validAndSubmit = () => {
      const attachmentData = jsonState.attachmentData;
      let requireObj = attachmentData && attachmentData.filter((attach => (attach.Used === "1" && (!attach.attachmentList || (attach.attachmentList.length === 0)))));
      if (requireObj && requireObj.length > 0) {
        this.setState({ requireObj: requireObj });
        return;
      }
      this.props.handlerSubmitAttachment();
    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    const closeError = () => {
      this.setState({showError: false});
    }

    const closeMaxImageOnSectionError = () => {
      this.setState({showMaxImageOnSectionError: false});
    }
    
    const openUpload = (id, docId) => {
      const attachments = this.props.attachmentState.attachmentData.find(attachment => attachment.DocID === docId);
      if (attachments && (attachments.attachmentList.length >= 20)) {
        this.setState({showMaxImageOnSectionError: true, uploading: false});
        return;
      }
      this.setState({docTypeUploading: docId, uploading: true});
      document.getElementById(id).click();
    }

    const openDialog= () => {
      fileDialogOpen = true;
    }

    const cancelDialog= (event, id) => {
      const fileInput = document.getElementById(id);
      if (fileDialogOpen && !fileInput?.files?.length) {
        fileDialogOpen = false;
        this.setState({uploading: false});
      }
    }

    return (
      <>
      <section className="sccontract-warpper" id="scrollAnchor">
        <div className="insurance">
          <div className="heading">
            <div className="breadcrums">
              <div className="breadcrums__item">
                <p>Yêu cầu quyền lợi</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>Tạo mới yêu cầu</p>
                <span>&gt;</span>
              </div>
            </div>
            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
              <p>Chọn thông tin</p>
              <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
            </div>
            <div className="heading__tab">
              <div className="step-container">
                <div className="step-wrapper">
                  <div className="step-btn-wrapper">
                    <div className="back-btn" onClick={() => this.props.handlerBackToPrevStep(this.state.stepName)}>
                      <button>
                        <div className="svg-wrapper">
                          <svg
                            width="11"
                            height="15"
                            viewBox="0 0 11 15"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                              fill="#985801"
                              stroke="#985801"
                              strokeWidth="0.2"
                            />
                          </svg>
                        </div>
                        <span className="simple-brown">Quay lại</span>
                      </button>
                    </div>
                    {/* <div className="save-wrap">
                      <button className="back-text">Lưu</button>
                    </div> */}
                  </div>
                  <div className="progress-bar">
                    <div className={(this.state.stepName >= CLAIM_STATE.CLAIM_TYPE) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>1</span>
                      </div>
                      <p>Thông tin sự kiện</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.PAYMENT_METHOD) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>2</span>
                      </div>
                      <p>Phương thức thanh toán</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.ATTACHMENT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>3</span>
                      </div>
                      <p>Kèm <br />chứng từ</p>
                    </div>
                    <div className={(this.state.stepName >= CLAIM_STATE.SUBMIT) ? "step active" : "step"}>
                      <div className="bullet">
                        <span>4</span>
                      </div>
                      <p>Hoàn tất yêu cầu</p>
                    </div>
                  </div>
                  <div className="step-btn-save-quit">
                    <div>
                      <button>
                        <span className="simple-brown" style={{ zIndex: '30' }} onClick={this.props.handleSaveLocalAndQuit}>Lưu & thoát</span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="stepform">
            <div className="stepform__body" style={{boxShadow: 'none'}}>
              <LoadingIndicator area="info-required-claim-initial" />
              <div className="info__title" style={{marginBottom: '0'}}>
                <h4>ĐÍNH KÈM CHỨNG TỪ</h4>
              </div>
              {/* Document list */}
              {attachmentData.map((docTypeData, index) => (
                <div className={index !== 0 ? "info padding-top32" : "info  padding-top12"} key={index}>

                  <div className="info__title margin-bottom0">
                    <h4 style={{ textTransform: 'unset' }}>{docTypeData.DocTypeName}{docTypeData.Used === "1" ? " (*)" : ""}</h4>
                    <i className="invoke-hoso" onClick={() => this.handlerOpenPopup(docTypeData)} ><img src="../../img/icon/step/step_3_info.svg" alt="" /></i>
                  </div>
                  {this.state.uploading && (this.state.docTypeUploading === docTypeData.DocID) &&
                    <LoadingIndicatorBasic/>
                  }
                  <div className="info__body">
                    <div className="item">
                      <div className="item__content">
                        {(docTypeData.attachmentList !== null && docTypeData.attachmentList !== undefined
                          && !!docTypeData.attachmentList && docTypeData.attachmentList.length > 0) ?

                          <div className="img-upload-wrapper not-empty">
                            <div className="img-upload-item">
                              <div className="img-upload" id={"img-upload-" + docTypeData.DocID}
                                onClick={() => openUpload("attInput-" + docTypeData.DocID, docTypeData.DocID)}
                                onBlur={(event)=> cancelDialog(event, "attInput-" + docTypeData.DocID)}
                                onFocus={()=> openDialog()}
                                onDragOver={(event) => this.handlerDragOver("img-upload-" + docTypeData.DocID, event)}
                                onDragLeave={(event) => this.handlerDragLeave("img-upload-" + docTypeData.DocID, event)}
                                onDrop={(event) => this.handlerDrop(docTypeData.DocID, event)}>

                                <button className="circle-plus">
                                  <img src={FE_BASE_URL + "/img/icon/plus.svg"} alt="circle-plus" className="plus-sign" />
                                </button>
                                <p className="basic-grey">
                                  Kéo &amp; thả tệp tin hoặc
                                  <span className="basic-red basic-semibold">chọn tệp</span>
                                </p>
                                <input id={"attInput-" + docTypeData.DocID} className="inputfile"
                                  type="file" multiple="multiple" hidden={true} accept="image/*"
                                  onChange={(event) => this.handlerUploadAttachment(docTypeData.DocID, event)} />
                              </div>
                            </div>
                            {docTypeData.attachmentList.slice().reverse().map((attachment, attIdx) => (
                              <div className="img-upload-item" key={attIdx}>
                                <div className="file">
                                  <div className="img-wrapper">
                                    <img src={attachment.imgData} alt="" onClick={() => openBase64(attachment.imgData)} />
                                  </div>
                                  <div className="deletebtn" onClick={() => this.handlerDeleteAttachment(docTypeData.DocID, docTypeData.attachmentList.length - 1 - attIdx)}>X</div>
                                </div>
                              </div>
                            ))}
                          </div> :
                          <div className="img-upload-wrapper">
                            <div className="img-upload-item">
                              <div className="img-upload" id={"img-upload-empty-" + docTypeData.DocID}
                                onClick={() => openUpload("attEmptyInput" + docTypeData.DocID, docTypeData.DocID) }
                                onBlur={(event)=> cancelDialog(event, "attEmptyInput-" + docTypeData.DocID)}
                                onFocus={()=> openDialog()}
                                onDragOver={(event) => this.handlerDragOver("img-upload-empty-" + docTypeData.DocID, event)}
                                onDragLeave={(event) => this.handlerDragLeave("img-upload-empty-" + docTypeData.DocID, event)}
                                onDrop={(event) => this.handlerDrop(docTypeData.DocID, event, this.handlerUpdateAttachmentList)}>
                                <button className="circle-plus">
                                  <img src={FE_BASE_URL + "/img/icon/plus.svg"} alt="circle-plus" className="plus-sign" />
                                </button>
                                <p className="basic-grey">
                                  Kéo & thả tệp tin hoặc&nbsp;
                                  <span className="basic-red basic-semibold">chọn tệp</span>
                                </p>
                                <input id={"attEmptyInput" + docTypeData.DocID} className="inputfile"
                                  type="file" multiple="multiple" hidden={true} accept="image/*"
                                  onChange={(e) => this.handlerUploadAttachment(docTypeData.DocID, e, this.handlerUpdateAttachmentList)} />
                              </div>
                            </div>
                          </div>
                        }
                      </div>
                    </div>
                  </div>
                  {checkListRequireContain(this.state.requireObj, docTypeData) &&
                    <span style={{ color: 'red', lineheight: '12px', marginTop: '10px' }}>
                      Vui lòng cung cấp {docTypeData.DocTypeName}
                    </span>
                  }
                  <div className="dash-line-claim"></div>
                </div>
              ))}

              <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt="" />
              <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
            </div>
            <div className="bottom-text" style={{ 'maxWidth': '594px', padding: '0 16px 16px 16px' }}>
              <p style={{ textAlign: 'justify' }}>
                <span className="red-text basic-bold">Lưu ý: </span>
                <span style={{ color: '#727272', paddingTop: '8px' }}>
                  <br />
                  1. Hỗ trợ hình ảnh định dạng JPG, JPEG, PNG và có dung lượng dưới 5MB.
                </span>
                <span style={{ color: '#727272', paddingTop: '16px' }}>
                  <br />
                  <div style={{ paddingTop: '8px' }}>
                    2. Các chứng từ yêu cầu ở trên cần thiết để giải quyết quyền lợi bảo hiểm. Quý khách có thể được yêu cầu bổ sung chứng từ, chứng từ gốc để hoàn tất hồ sơ giải quyết quyền lợi bảo hiểm.
                  </div>
                </span>
              </p>
            </div>
          </div>
          <div className="bottom-btn">
            <button className={(jsonState.disabledButton) ? "btn btn-primary disabled" : "btn btn-primary"}
              id="submitClaimDetail" disabled={!!jsonState.disabledButton}
              onClick={() => validAndSubmit()}>Tiếp tục</button>
          </div>
        </div>

        {/* <div className="popup special hoso-popup" id="popupAttachmentClaim">
          <div className="popup__card">
            <div ref={this.handlerSetWrapperRef}>
              <div className="hoso-information-card">
                <div className="close-btn" ref={this.handlerSetCloseButtonRef}>
                  <img src="../../img/icon/close-icon.svg" alt="closebtn" className="local-popup-btn" />
                </div>
                <div className="picture-wrapper">
                  <div className="picture">
                    <img src="../../img/popup/popup_hoso.svg" alt="popup-hosobg" />
                  </div>
                </div>
                <div className="hoso-information-content">
                  <div className="hoso-information-content-header">{compJsonState.popupData && compJsonState.popupData.DocTypeName}</div>
                  <div className="hoso-information-content-full">
                    {compJsonState.popupData && compJsonState.popupData.lstDocTypeName.map((item, index) => (
                      <p dangerouslySetInnerHTML={{ __html: (index + 1) + '. ' + item.replace(/(?:\r\n|\r|\n)/g, '<br>') }} />
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div> */}
        {/* TODO: Modal edit image */}
        <div className="popup special hoso-popup" id="popupAttachmentClaim">
          <div className="popup__card">
            <div ref={this.handlerSetWrapperRef}>
              <div className="hoso-information-card">
                <div className="close-btn" ref={this.handlerSetCloseButtonRef}>
                  <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="local-popup-btn" />
                </div>
                <div className="picture-wrapper">
                  <div className="picture">
                    <img src={FE_BASE_URL + "/img/popup/popup_hoso.svg"} alt="popup-hosobg" />
                  </div>
                </div>
                <div className="hoso-information-content">
                  <div className="hoso-information-content-header">{compJsonState.popupData && compJsonState.popupData.DocTypeName}</div>
                  <div className="hoso-information-content-full">
                    {compJsonState.popupData && compJsonState.popupData.lstDocTypeName.map((item, index) => (
                      <p dangerouslySetInnerHTML={{ __html: (index + 1) + '. ' + item.replace(/(?:\r\n|\r|\n)/g, '<br>') }} />
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="popupbg"></div>
        </div>
      </section>
      {this.state.showError&& 
        <AlertPopupError closePopup={() => closeError()} msg={'Vui lòng chọn hình ảnh định dạng JPG, JPEG, PNG'} imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'} />
      }
      {this.state.showMaxImageOnSectionError&& 
        <AlertPopupError closePopup={() => closeMaxImageOnSectionError()} msg={'Quý khách có thể đính kèm tối đa 20 chứng từ với các định dạng JPG, JPEG, PNG.'} imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'} />
      }
      </>
    );
  }

}

export default Attachment;
