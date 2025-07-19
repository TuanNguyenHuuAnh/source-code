import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  AUTHENTICATION,
  COMPANY_KEY,
  FE_BASE_URL,
  PageScreen,
  WEB_BROWSER_VERSION
} from '../constants';
import { logoutSession, CPGetPolicyListByCLIID, GetTaxInvoice, CPSaveLog, getDocumentList } from '../util/APIUtils';
import {formatFullName, showMessage, getSession, getDeviceId, trackingEvent} from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import LoadingIndicatorBasic from '../common/LoadingIndicatorBasic';
import AES256 from 'aes-everywhere';
import EDocumentInvoiceDetail from './EDocumentInvoiceDetail';
import EDocumentLetterDetail from './EDocumentLetterDetail';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';
import PdfViewer from '../SDK/PdfViewer';
import AlertPopup from '../SDK/AlertPopup';
import b64toBlob from 'b64-to-blob';

const ALL = 'Tất cả', RECEIVE = 'Tôi nhận', SENDER = 'Tôi gửi';
class EDocumentInvoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      clientProfile: null,
      pdfProfile: null,
      policyID: '',
      isEmpty: true,
      pdfUrl: '',
      DocTypeID: '',
      DocTypeName: '',
      taxInvoiceList: null,
      filterKeywork: ALL,
      filterList: [ALL, RECEIVE, SENDER],
      productName: '',
      renderMeta: false,
      letterList: null,
      viewingReadonlyUrlPdf: '',
      mimeType: '',
      loadingBlob: false

    };
    //this.handleMobileCardSubmit = this.handleMobileCardSubmit.bind(this);
  }
  sortListByStatus = (obj) => {
    obj.sort((a, b) => {
      if (a.PolicyStatus < b.PolicyStatus) return 1;
      else if (a.PolicyStatus > b.PolicyStatus) return -1;
      else return 0;
    });
    return obj;
  }

  sortListEffectDate = (obj) => {
    obj.sort((a, b) => {
      if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) < new Date(b.PolIssEffDate)) return 1;
      else if (a.PolicyStatus === b.PolicyStatus && new Date(a.PolIssEffDate) > new Date(b.PolIssEffDate)) return -1;
      else return 0;
    });
    return obj;
  }
  cpGetInvoices = () => {
    const submitRequest = {
      jsonDataInput: {
        Action: 'TaxInvoiceList',
        Company: COMPANY_KEY,
        OS: WEB_BROWSER_VERSION,//'Samsung_SM-A125F-Android-11',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN),
      }
    }

    CPGetPolicyListByCLIID(submitRequest).then(Res => {
      //console.log(JSON.stringify(Res));
      let Response = Res.Response;

      if (Response.Result === 'true' && Response.ClientProfile) {
        this.setState({ clientProfile: this.sortListEffectDate(this.sortListByStatus(Response.ClientProfile)) });
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }

        })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

  componentDidMount() {
    this.cpGetInvoices();
    this.cpSaveLog(`Web_Open_${PageScreen.E_POLICY_POL_MANAGEMENT}`);
    trackingEvent(
        "Thư viện tài liệu",
        `Web_Open_${PageScreen.E_POLICY_POL_MANAGEMENT}`,
        `Web_Open_${PageScreen.E_POLICY_POL_MANAGEMENT}`,
    );
  }

  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.E_POLICY_POL_MANAGEMENT}`);
    trackingEvent(
        "Thư viện tài liệu",
        `Web_Close_${PageScreen.E_POLICY_POL_MANAGEMENT}`,
        `Web_Close_${PageScreen.E_POLICY_POL_MANAGEMENT}`,
    );
  }

  cpSaveLog(functionName) {
    const masterRequest = {
      jsonDataInput: {
        OS: "Web",
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        DeviceToken: "",
        function: functionName,
        Project: "mcp",
        UserLogin: getSession(USER_LOGIN)
      }
    }
    CPSaveLog(masterRequest).then(res => {
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }
  render() {
    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }
    const getTaxInvoiceList = (policyID) => {
      const submitRequest = {
        jsonDataInput: {
          OS: WEB_BROWSER_VERSION,//'Samsung_SM-A125F-Android-11',
          Offset: '1',
          Action: 'TaxInvoiceList',
          Project: 'mcp',
          APIToken: getSession(ACCESS_TOKEN),
          ClientID: getSession(CLIENT_ID),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          PolicyNo: policyID,
          UserLogin: getSession(USER_LOGIN),
        }
      }
      //console.log(JSON.stringify(submitRequest));
      GetTaxInvoice(submitRequest).then(Res => {
        //console.log(JSON.stringify(Res));
        let Response = Res.Response;

        if (Response.Result === 'true' && Response.ClientProfile) {
          this.setState({
            policyID: policyID,
            isEmpty: false,
            taxInvoiceList: Response.ClientProfile
          });
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }

          })

        } else {
          this.setState({
            policyID: policyID,
            isEmpty: true,
            taxInvoiceList: null
          });
        }
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
    }

  const getLetterList = (policyID) => {
    const submitRequest = {
      sourceSystem: 'DConnect',
      policyNo: policyID,
      listDocumentType: [
        {
          documentName: "PremiumConfirmLetter",
          documentVersion: "All"
        }
      ]
    }

    getDocumentList(submitRequest).then(Response => {
      if (Response.responseCode === '00' && Response.DocumentList) {
        console.log(Response.DocumentList);
        this.setState({letterList: Response.DocumentList})
        // this.setState({ clientProfile: this.sortListEffectDate(this.sortListByStatus(Response.ClientProfile)) });
      } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }

        })

      }
    }).catch(error => {
      this.props.history.push('/maintainence');
    });
  }

    const showCardInfo = (polID, index, productName) => {
      polID = polID.trim();
      var jsonState = this.state;
      jsonState.policyID = polID;
      jsonState.productName = productName;
      jsonState.taxInvoiceList = null;
      jsonState.letterList = null;
      this.setState(jsonState);
      if (document.getElementById(index).className === "card choosen") {
        document.getElementById(index).className = "card";
        jsonState.policyID = '';
        jsonState.isEmpty = true;
        this.setState(jsonState);
      } else {
        jsonState.isEmpty = false;
        this.state.clientProfile.forEach((pol, i) => {
          if (i === index) {
            document.getElementById(i).className = "card choosen";
          } else {
            document.getElementById(i).className = "card";
          }
        });
        getTaxInvoiceList(polID);
        getLetterList(polID);

      }
    }

    const tickCategory = (filterKeywork, id) => {
      let popTicks = document.getElementsByClassName('filter-pop-tick').className;
      if (popTicks && popTicks.length > 0) {
        for (let i = 0; i < popTicks.length; i++) {
          popTicks[i].className = "filter-pop-tick";
        }
      }
      document.getElementById(id).className = 'filter-pop-tick ticked';

      this.setState({ filterKeywork: filterKeywork });
    }

    const toggleFilter = () => {
      if (document.getElementById('filter-dropdown-id').className === 'dropdown inputdropdown filter-dropdown') {
        document.getElementById('filter-dropdown-id').className = 'dropdown inputdropdown filter-dropdown show';
      } else {
        document.getElementById('filter-dropdown-id').className = 'dropdown inputdropdown filter-dropdown';
      }
    }

    const openLetter = (response, mimeType) => {
      this.setState({loadingBlob: true});
      const contentType = mimeType;//'application/pdf';
      const sliceSize = 512;
      const byteCharacters = atob(response);
      const byteArrays = [];
      for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize);
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }
      let blob = new Blob(byteArrays, { type: contentType });
      blob = b64toBlob(response, contentType);
      const blobUrl = URL.createObjectURL(blob);
      // window.open(blobUrl);
      this.setState({ viewingReadonlyUrlPdf: blobUrl, mimeType: mimeType, loadingBlob: false });
    }

    const closePdf = () => {
      this.setState({ viewingReadonlyUrlPdf: '' });
    }

    const closeNoDocument = () => {
      setState({ noDocument: false });
      closePdf();
    }

    let bgColor = '#f1f1f1';
    if (this.state.isEmpty) {
      bgColor = '#ffffff';
    }
    if (!getSession(CLIENT_ID)) {
      return <Redirect to={{
        pathname: '/home'
      }} />;
    }
    console.log('viewingReadonlyUrlPdf=', this.state.viewingReadonlyUrlPdf);
    return (
      <main className={logined ? "logined" : "logined nodata"} id="main-id">
        {this.state.renderMeta &&
          <Helmet>
            <title>Quản lý hợp đồng – Dai-ichi Life Việt Nam</title>
            <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta name="robots" content="noindex, nofollow" />
            <meta property="og:type" content="website" />
            <meta property="og:url" content={FE_BASE_URL + "/manage-epolicy"} />
            <meta property="og:title" content="Quản lý hợp đồng - Dai-ichi Life Việt Nam" />
            <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
            <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
            <link rel="canonical" href={FE_BASE_URL + "/manage-epolicy"} />
          </Helmet>
        }
        <div className={this.state.viewingReadonlyUrlPdf?"main-warpper insurancepage basic-mainflex hideDiv": "main-warpper insurancepage basic-mainflex"}>
          <section className="sccard-warpper">
            <h5 className="basic-bold">Vui lòng chọn hợp đồng:</h5>
            <div className="card-warpper">
              <LoadingIndicator area="policyList-by-cliID" />
              {this.state.clientProfile &&
                this.state.clientProfile.map((item, index) => {
                  return (
                    <div className="item" key={"item" + index}>
                      <div className="card" onClick={() => showCardInfo(item.PolicyID, index, item.ProductName)} id={index}>
                        <div className="card__header">
                          <h4 className="basic-bold">{item.ProductName}</h4>
                          <p>Dành cho: {formatFullName(item.PolicyLIName)}</p>
                          {item.PolicyStatus.length < 25 ?
                            <p>Hợp đồng: {item.PolicyID}</p> :
                            <p className="policy">Hợp đồng: {item.PolicyID}</p>}

                          {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                            <div className="dcstatus">
                              <p className="inactive">{item.PolicyStatus}</p>
                            </div>) : (
                            <div className="dcstatus">
                              {item.PolicyStatus.length < 25 ?
                                <p className="active" >{item.PolicyStatus}</p> :
                                <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                            </div>
                          )}
                          <div className="choose">
                            <div className="dot"></div>
                          </div>
                        </div>
                        <div className="card__footer">
                          <div className="card__footer-item">
                            <p>Ngày hiệu lực</p>
                            <p>{item.PolIssEffDate}</p>
                          </div>
                          <div className="card__footer-item">
                            <p>Số tiền bảo hiểm</p>
                            <p className="basic-red basic-bold">{item.FaceAmount} VNĐ</p>
                          </div>
                          <div className="card__footer-item">
                            <p>Đại lý bảo hiểm</p>
                            <p>{item.AgentName ? item.AgentName.toUpperCase() : ''}</p>
                          </div>
                        </div>
                      </div>
                    </div>




                  )
                })
              }

            </div>
            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
              <p>Tiếp tục</p>
              <i><img src="img/icon/arrow-left.svg" alt="" /></i>
            </div>
          </section>
          <section className={this.state.isEmpty ? "sccontract-warpper no-data" : "sccontract-warpper"}>

            <div className="breadcrums" style={{ backgroundColor: bgColor }}>
              <div className="breadcrums__item">
                <p>Trang chủ</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>Thư viện tài liệu</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>Quản lý hợp đồng</p>
                <span>&gt;</span>
              </div>
            </div>
            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
              <p>Chọn thông tin</p>
              <i><img src="img/icon/return_option.svg" alt="" /></i>
            </div>
            {this.state.loadingBlob?(
              <LoadingIndicatorBasic/>
            ):(
              <LoadingIndicator area="epolicy-pdf" />
            )}
            {this.state.isEmpty ? (
              <div className="insurance">
                <div className="empty">
                  <div className="icon">
                    <img src="img/no-data(6).svg" alt="no-data" />
                  </div>
                  <p style={{ paddingTop: '20px' }}>Thông tin chi tiết sẽ hiển thị khi bạn chọn một hợp đồng ở bên trái.</p>
                </div>
              </div>
            ) : (
              <div className="thu-vien-container">
                <div className="thu-vien-container_header">
                  <div className="thu-vien-container_header-title">{this.state.productName}</div>
                  <div className="dropdown inputdropdown filter-dropdown" id="filter-dropdown-id" onClick={() => toggleFilter()}>
                    <div className="dropdown__content">
                      <div className="filter_button">
                        <div className="icon-left">
                          <img src="img/icon/filter-icon.svg" alt="filter-icon" />
                        </div>
                        <div className="text">
                          <p>Bộ lọc</p>
                        </div>
                        <div className="icon-right">
                          <img src="img/icon/arrow-white-down.svg" alt="arrow-down-icon" />
                        </div>
                      </div>
                    </div>
                    <div className="dropdown__items">
                      <div className="filter-pop">
                        <div className="filter-pop-tick ticked" id="invoice-1" onClick={() => tickCategory(ALL, 'invoice-1')}>
                          <p className="content">{ALL}</p>
                          <div className="img-wrapper">
                            <img src="img/icon/green-ticked.svg" alt="ticked" />
                          </div>
                        </div>
                        <div className="filter-pop-tick" id="invoice-2" onClick={() => tickCategory(RECEIVE, 'invoice-2')}>
                          <p className="content">{RECEIVE}</p>
                          <div className="img-wrapper">
                            <img src="img/icon/green-ticked.svg" alt="ticked" />
                          </div>
                        </div>
                        <div className="filter-pop-tick" id="invoice-3" onClick={() => tickCategory(SENDER, 'invoice-3')}>
                          <p className="content">{SENDER}</p>
                          <div className="img-wrapper">
                            <img src="img/icon/green-ticked.svg" alt="ticked" />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="thu-vien-container_body">
                  {this.state.letterList && this.state.letterList.map((item, index) => {
                    return (
                      item.documentId && 
                        <EDocumentLetterDetail filterKeywork={this.state.filterKeywork} documentId={item.documentId} PolicyNo={this.state.policyID} createdDate={item.createdDate} docTypeCode={item.documentTitle} title={'Thư xác nhận đóng phí'} openLetter={(base64ArrayBuffer, mineType)=>openLetter(base64ArrayBuffer, mineType)}/>
                    )
                  })
                  }
                  {this.state.taxInvoiceList && this.state.taxInvoiceList.map((item, index) => {
                    return (
                      item.PDFFileName ? (
                        <EDocumentInvoiceDetail filterKeywork={this.state.filterKeywork} InvoiceID={item.TaxInvoiceID} InvoiceSign={item.InvoiceSign} PolicyNo={item.PolicyNo} ReceiptDate={item.PrintedDate} TaxID={item.TaxID} PDFFileName={item.PDFFileName} />
                      ) : (
                        <EDocumentInvoiceDetail filterKeywork={this.state.filterKeywork} InvoiceID={item.TaxInvoiceID} InvoiceSign={item.InvoiceSign} PolicyNo={item.PolicyNo} ReceiptDate={item.PrintedDate} TaxID={item.TaxID} />
                      )
                    )
                  })
                  }
                </div>
              </div>

            )}
          </section>
        </div>
        {this.state.viewingReadonlyUrlPdf &&
          <PdfViewer pdfUrl={this.state.viewingReadonlyUrlPdf} mimeType={this.state.mimeType} closePdf={() => closePdf()} title={'Thư xác nhận đóng phí'} openLetter={(base64, mineType) => openLetter(base64, mineType)} />
        }
        {this.state.noDocument &&
          <AlertPopup closePopup={() => closeNoDocument()} msg={'Không tìm thấy chứng từ'} imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'} />
        }
      </main>
    )
  }
}

export default EDocumentInvoice;
