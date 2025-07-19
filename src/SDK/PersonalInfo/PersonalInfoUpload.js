import React, { Component } from 'react';
import {IDDOCUMENTS_FRONT, IDDOCUMENTS_BACK, PERSONAL_INFO_STATE } from '../sdkConstant';
import { getDeviceId, getSession, setSession, formatFullName } from '../sdkCommon';
import { onlineRequestSubmit } from '../sdkAPI';
import LoadingIndicator from '../LoadingIndicator2';
import '../sdk.css';

class PersonalInfoUpload extends Component {
  _isMounted = false;
  constructor(props) {
    super(props);
    this.state = {
      LIClientProfile: null,
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
        <div className="title-no">
          <h4 className='attach-title'>{this.props.SubDocName}</h4>
        </div>
        <div className="item">
          <div className="item__content"
            style={{ margin: '8px 4px 19px 0' }}>
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
                    ((this.props.SubDocId === IDDOCUMENTS_FRONT) || (this.props.SubDocId === IDDOCUMENTS_BACK))?(
                      <div className="img-upload"
                      key={attIdx}
                      style={{ padding: '6px', height: '140px'}}>
                      <div className="file">
                        <div
                          className="img-wrapper" style={{height: '140px', width: '250px' }}>
                          <img
                            src={att.imgData}
                            alt="" />
                        </div>
                        <div
                          className={this.props.isSubmitting? "deletebtn none-cursor": "deletebtn"}
                          onClick={() => this.props.deleteAttachment(attIdx, this.props.SubDocId)}>X
                        </div>
                      </div>
                    </div>
                    ):(
                      <div className="img-upload-item"
                      key={attIdx}>
                      <div className="file">
                        <div
                          className="img-wrapper">
                          <img
                            src={att.imgData}
                            alt="" />
                        </div>
                        <div
                          className={this.props.isSubmitting? "deletebtn none-cursor": "deletebtn"}
                          onClick={() => this.props.deleteAttachment(attIdx, this.props.SubDocId)}>X
                        </div>
                      </div>
                    </div>
                    )

                  ))
                }

              </div>) :
              <div className="img-upload-wrapper">
                <div className="img-upload-item" style={{paddingLeft: '0'}}>
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

export default PersonalInfoUpload;
