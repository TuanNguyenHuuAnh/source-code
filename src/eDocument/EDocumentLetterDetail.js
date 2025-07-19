import React, { Component } from 'react';
import { ACCESS_TOKEN, EXPIRED_MESSAGE, FE_BASE_URL, PageScreen } from '../constants';
import { logoutSession, getDocumentDetail } from '../util/APIUtils';
import { showMessage, getSession, cpSaveLog, trackingEvent, formatDateFull } from '../util/common';
import b64toBlob from 'b64-to-blob';

const ALL = 'Tất cả', RECEIVE = 'Tôi nhận';
class EDocumentLetterDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  componentDidMount() {
    cpSaveLog(`Web_Open_${PageScreen.PREMIUM_LETTER_LIST_SDK}`);
    trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.PREMIUM_LETTER_LIST_SDK}`, `Web_Open_${PageScreen.PREMIUM_LETTER_LIST_SDK}`);
  }

  render() {
    const getLetterDetail = () => {
      const submitRequest = {
        sourceSystem: 'DConnect',
        policyNo: this.props.policyID,
        docTypeCode: this.props.docTypeCode,
        documentIndex: this.props.documentId
      }

      getDocumentDetail(submitRequest).then(Response => {
        if (Response.responseCode === '00' && Response.DocumentList) {
          // openNewTab(Response.DocumentList?.[0]?.content);
          this.props.openLetter(Response.DocumentList?.[0]?.content, Response.DocumentList?.[0]?.mimeType);
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();

        }
      }).catch(error => {
        // this.props.history.push('/maintainence');
      });
    }

    var sendType = RECEIVE;
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }

    const openNewTab = (response) => {
      var contentType = 'application/pdf';
      var sliceSize = 512;
      var byteCharacters = atob(response);
      var byteArrays = [];
      for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        var slice = byteCharacters.slice(offset, offset + sliceSize);
        var byteNumbers = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
        var byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }

      var blob = new Blob(byteArrays, { type: contentType });
      blob = b64toBlob(response, contentType);
      var blobUrl = URL.createObjectURL(blob);

      window.open(blobUrl);
    }

    const closePdf = () => {
      this.setState({viewingReadonlyUrlPdf: '' });
    }

    const closeNoDocument = () => {
      setState({noDocument: false });
      closePdf();
    }

    return (
      <>
        {((this.props.filterKeywork === ALL) || (this.props.filterKeywork === sendType)) && this.props.documentId &&
          <>
            <div className="thu-vien-container_body-item">
              <div className="item-left" onClick={(() => getLetterDetail())} style={{ cursor: 'pointer' }}>
                <div className="item-left-icon">
                  <img src={FE_BASE_URL + "/img/icon/dai-ichi-icon.svg"} alt="dai-ichi-icon" />
                </div>
                <div className="item-left-text">
                  <div className="head-title">
                    <p>{this.props.title}</p>
                  </div>
                  <div className="date">
                    <div className="icon">
                      <img src={FE_BASE_URL + "/img/icon/Calendar.svg"} alt="Calendar" />
                    </div>
                    <div className="text">{this.props.createdDate?formatDateFull(this.props.createdDate): ''}</div>
                  </div>
                  <div className="text">{sendType}</div>
                </div>
              </div>

            </div>
            <div className="thu-vien-container_body-border"></div>
          </>
        }
      </>
    )
  }
}

export default EDocumentLetterDetail;
