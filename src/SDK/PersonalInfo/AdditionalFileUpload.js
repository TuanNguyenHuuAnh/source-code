import React, { Component } from 'react';
import {IDDOCUMENTS_FRONT, IDDOCUMENTS_BACK, PERSONAL_INFO_STATE, FE_BASE_URL, OTHERS_CV } from '../sdkConstant';
import {openBase64} from '../sdkCommon';

class AdditionalFileUpload extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);
    this.state = {
    }
  }


  componentDidMount() {
    this._isMounted = true;
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  render() {
    console.log('SubDocName=' + this.props.SubDocName);
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }

    const chosenSubmit = () => {
      this.props.updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
    }

    const onLocalUploading=(id)=> {
      document.getElementById(id).click();
    }


    return (
      <div className="info__body cccd-item">
        <div className="title-no" style={{display: 'flex'}}>
          <h4 className='attach-title'>{this.props.SubDocName}</h4>
          {this.props.SubDocId === OTHERS_CV && 
          <i className="info-icon" onClick={() => this.props.showNotice()}
            ><img src={FE_BASE_URL + "/img/icon/Information-step.svg"}
                  alt="information-icon" className="info-icon"
            />
          </i>
          }
        </div>
        <div className="item">
          <div className="item__content"
            style={{ margin: '8px 16px 19px' }}>
            {(this.props.attachmentState.attachmentList
              && this.props.attachmentState.attachmentList.length > 0 && this.props.attachmentState.attachmentMap[this.props.SubDocId] && this.props.attachmentState.attachmentMap[this.props.SubDocId].length > 0) ?
              (
              
              <div
                className="img-upload-wrapper not-empty">
                {((this.props.SubDocId !== IDDOCUMENTS_FRONT) && (this.props.SubDocId !== IDDOCUMENTS_BACK) && this.props.attachmentState.attachmentList.length < 20) &&
                  <div className="img-upload-item">
                    <div className="img-upload"
                      id={"img-upload-claimAttachment"}
                      onClick={() => onLocalUploading("imgAttInput" + this.props.SubDocId)}
                      onDragOver={(event) => this.props.dragFileOver("img-upload-claimAttachment", event)}
                      onDragLeave={(event) => this.props.dragFileLeave("img-upload-claimAttachment", event)}
                      onDrop={(event) => this.props.dropFile(event, this.props.SubDocId)}>
                      <button
                        className="circle-plus"
                        style={{ padding: '0' }}>
                        <img style={{
                          width: '24px',
                          height: '24px'
                        }}
                          src="../../../img/icon/zl_plug.svg"
                          alt="circle-plus" />
                      </button>
                      <p className="basic-grey">
                        Kéo & thả tệp tin hoặc
                        <span
                          className="basic-red basic-semibold">&nbsp;chọn tệp</span>
                      </p>
                      <input id={"imgAttInput" + this.props.SubDocId}
                        className="inputfile"
                        type="file"
                        multiple={true}
                        hidden={true}
                        accept="image/*"
                        onChange={(e) => this.props.uploadAttachment(e, this.props.SubDocId)} />
                    </div>
                  </div>
                }

              {this.props.attachmentState.attachmentMap[this.props.SubDocId].map((att, attIdx) => (
                  <div className="img-upload-item"
                    key={attIdx}
                    style={{ padding: '6px', height: '140px'}}>
                    <div className="file">
                      <div
                        className="img-wrapper">
                        <img
                          src={att.imgData}
                          alt=""  onClick={() => openBase64(att.imgData)}/>
                      </div>
                      <div
                        className="deletebtn"
                        onClick={() => this.props.deleteAttachment(attIdx, this.props.SubDocId)}>X
                      </div>
                    </div>
                  </div>
                ))
              }
              </div>) :
              <div className="img-upload-wrapper">
                <div className="img-upload-item">
                  <div className="img-upload" style={{height: '140px'}}
                    id={"img-upload-empty"}
                    onClick={() => onLocalUploading("imgAttEmptyInput" + this.props.SubDocId)}
                    onDragOver={(event) => this.props.dragFileOver("img-upload-empty", event)}
                    onDragLeave={(event) => this.props.dragFileLeave("img-upload-empty", event)}
                    onDrop={(event) => this.props.dropFile(event, this.props.SubDocId)}>
                    <button
                      className="circle-plus"
                      style={{ padding: '0' }}>
                      <img style={{
                        width: '24px',
                        height: '24px'
                      }}
                        src="../../../img/icon/zl_plug.svg"
                        alt="circle-plus" />
                    </button>
                    <p className="basic-grey">
                      Kéo & thả tệp tin hoặc
                      <span
                        className="basic-red basic-semibold">&nbsp;chọn tệp</span>
                    </p>
                    <input
                      id={"imgAttEmptyInput" + this.props.SubDocId}
                      className="inputfile"
                      type="file"
                      multiple={true}
                      hidden={true}
                      accept="image/*"
                      onChange={(e) => this.props.uploadAttachment(e, this.props.SubDocId)} />
                  </div>
                </div>
              </div>
            }
          </div>
          {/* {this.props.errorUpload &&
            <p className='inline-red' style={{
              padding: '0 16px 16px'
              
            }}>{this.props.errorUpload}</p>
          } */}
        </div>
      </div>
    );
  }
}

export default AdditionalFileUpload;
