import React, { Component } from 'react';
import {IDDOCUMENTS_FRONT, IDDOCUMENTS_BACK, PERSONAL_INFO_STATE } from '../sdkConstant';
import { getDeviceId, getSession, setSession, formatFullName } from '../sdkCommon';
import { onlineRequestSubmit } from '../sdkAPI';
import LoadingIndicator from '../LoadingIndicator2';
import '../sdk.css';

class PersonalInfoUploadReview extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
    return (
      <div className="info__body">
        <div className="title-no">
          <h4 className='attach-title'>{this.props.SubDocName?this.props.SubDocName: ''}</h4>
        </div>
        <div className="item">
          <div className="item__content">
            {this.props.attachmentMap[this.props.SubDocId].map((att, attIdx) => (
              <div className="img-upload-item"
                key={attIdx}
                style={{ padding: '6px 0'}}>
                <div className="file" style={{background:'#EDEBEB', border: '0'}}>
                  <div
                    className="img-wrapper">
                    <img
                      src={att?.imgData}
                      alt="" />
                  </div>
                </div>
              </div>
            ))
            }
          </div>
        </div>
      </div>
    );
  }
}

export default PersonalInfoUploadReview;
