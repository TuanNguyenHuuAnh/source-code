import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, AUTHENTICATION, WEB_BROWSER_VERSION } from '../constants';
import { GetTaxInvoice, getEPolicyPdf } from '../util/APIUtils';
import {base64ArrayBuffer, showMessage, getSession, getDeviceId} from '../util/common';
import b64toBlob from 'b64-to-blob';

const ALL = 'Tất cả', RECEIVE='Tôi nhận', SENDER='Tôi gửi', PREMIUM='Hoá đơn phí bảo hiểm', APL_OPL='Hóa đơn phí hoàn trả tạm ứng';
class EDocumentInvoiceDetail  extends Component {
  constructor(props) {
    super(props);
    this.state = {
        PDFFile: null,
        XMLFile: null,
        InvoiceID: this.props.InvoiceID,
        invoiceName: this.props.PDFFileName? 'Thư xác nhận' : PREMIUM
    };
  }

  getTaxInvoiceDetail = () => {
    const submitRequest = {
     jsonDataInput:{
      InvoiceID: this.props.InvoiceID,
      InvoiceSign: this.props.InvoiceSign,
      OS: WEB_BROWSER_VERSION,
      PolicyNo: this.props.PolicyNo,
      ReceiptDate: this.props.PrintedDate,
      APIToken:getSession(ACCESS_TOKEN),
      Action:'TaxInvoiceDetail',
      Authentication:AUTHENTICATION,
      ClientID: getSession(CLIENT_ID),
      DeviceId:getDeviceId(),
      Project: 'mcp',
      UserLogin: getSession(USER_LOGIN),
     }
   }
   //console.log(JSON.stringify(submitRequest));
   GetTaxInvoice(submitRequest).then(Res => {
     //console.log(JSON.stringify(Res));
     let Response = Res.Response;
 
     if(Response.Result === 'true' && Response.ClientProfile){
         this.setState({
           PDFFile: Response.ClientProfile[0].PDFFile?Response.ClientProfile[0].PDFFile: null,
           XMLFile: Response.ClientProfile[0].XMLFile?Response.ClientProfile[0].XMLFile: null
         });
     } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
      showMessage(EXPIRED_MESSAGE);
     } else {
       this.setState({
         PDFFile: null,
         XMLFile: null
       });
     }
     }).catch(error => {
       //this.props.history.push('/maintainence');
   });
 }

 sendDate = () => {
  const submitRequest = {
   jsonDataInput:{
    InvoiceID: this.props.InvoiceID,
    InvoiceSign: this.props.InvoiceSign,
    OS: WEB_BROWSER_VERSION,
    PolicyNo: this.props.PolicyNo,
    ReceiptDate: this.props.PrintedDate,
    APIToken:getSession(ACCESS_TOKEN),
    Action:'SendDate',
    Authentication:AUTHENTICATION,
    ClientID: getSession(CLIENT_ID),
    DeviceId:getDeviceId(),
    Project: 'mcp',
    UserLogin: getSession(USER_LOGIN),
   }
 }

 //console.log(JSON.stringify(submitRequest));
 GetTaxInvoice(submitRequest).then(Res => {
   //console.log(JSON.stringify(Res));
   let Response = Res.Response;

   if(Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL'){
    //alert('send date success!');
   } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
    showMessage(EXPIRED_MESSAGE);
   } 
   }).catch(error => {
     //this.props.history.push('/maintainence');
 });
}

getPDFDetail = () => {
  const submitRequest = {
   jsonDataInput:{
    OS: WEB_BROWSER_VERSION,
    APIToken:getSession(ACCESS_TOKEN),
    Action:'GetPDFFile',
    FileName: this.props.PDFFileName,
    Authentication:AUTHENTICATION,
    ClientID: getSession(CLIENT_ID),
    DeviceId:getDeviceId(),
    Project: 'mcp',
    UserLogin: getSession(USER_LOGIN),
   }
 }
 //console.log(JSON.stringify(submitRequest));
 getEPolicyPdf(submitRequest).then(Res => {
   //console.log(JSON.stringify(Res));
   let Response = Res.Response;
   if(Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message){
       this.setState({
         PDFFile: Response.Message,
         XMLFile: null
       });
   } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
    showMessage(EXPIRED_MESSAGE);
   } else {
     this.setState({
       PDFFile: null,
       XMLFile: null
     });
   }
   }).catch(error => {
     //this.props.history.push('/maintainence');
 });
}

  componentDidMount() {
    if (this.props.PDFFileName) {
      this.getPDFDetail();
    } else {
      this.getTaxInvoiceDetail();
    }
  }
  render() {
    if (this.props.InvoiceID && (this.props.InvoiceID !== this.state.InvoiceID)) {
      this.getTaxInvoiceDetail();
      this.setState({InvoiceID: this.props.InvoiceID});
    }
    var invoiceName = this.state.invoiceName;
    var sendType = RECEIVE;
    if (this.state.XMLFile) {
      if (this.state.XMLFile.indexOf('KIEU_DONG_PHI="PREMIUM"') >= 0) {
        invoiceName = PREMIUM;

      } else if(this.state.XMLFile.indexOf('KIEU_DONG_PHI="APL"') >= 0 || this.state.XMLFile.indexOf('KIEU_DONG_PHI="OPL"') >= 0) {
        invoiceName = APL_OPL;

      }
    }
      
      var logined = false;
      if (getSession(ACCESS_TOKEN)) {
        logined = true;
      }

      const handleConvert = (pdfArr) => {
        var byteArr = pdfArr + '';
        if (byteArr === null || byteArr === "") {
          alert("File content error !");
        }
        else {
          let data = byteArr.trim().replace(/ /g,'');
          data.replace(/\r?\n|\r/g, '');
          let arrData = JSON.parse("["+data+"]");
          const base64 = base64ArrayBuffer(arrData);
          openNewTab(base64);
        }
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
    
        var blob = new Blob(byteArrays, {type: contentType});
        blob = b64toBlob(response, contentType);
        var blobUrl = URL.createObjectURL(blob);
    
        window.open(blobUrl);
      }

      const handleDownoadConvert = (pdfArr, invoiceName) => {
        var byteArr = pdfArr + '';
        if (byteArr === null || byteArr === "") {
          alert("File content error !");
        }
        else {
          let data = byteArr.trim().replace(/ /g,'');
          data.replace(/\r?\n|\r/g, '');
          let arrData = JSON.parse("["+data+"]");
          const base64 = base64ArrayBuffer(arrData);
          openDownload(base64, invoiceName);
        }
      }

      const openDownload = (response, invoiceName) => {
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
    
        var blob = new Blob(byteArrays, {type: contentType});
        blob = b64toBlob(response, contentType);
        // var blobUrl = URL.createObjectURL(blob);
    
        // window.open(blobUrl);


        let url = window.URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.href = url;
        a.download = invoiceName;
        a.click();
      }

      const downloadXML=(invoiceName)=> {
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(this.state.XMLFile));
        element.setAttribute('download', invoiceName + '.xml');
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
        this.sendDate();
      }

      const viewPdf=()=> {
        handleConvert(this.state.PDFFile);
      }

      const viewLetterPdf=()=> {
        openNewTab(this.state.PDFFile);
      }

      const downloadPdf=(invoiceName)=> {
        handleDownoadConvert(this.state.PDFFile, invoiceName);
      }

      const downloadLetterPdf=(invoiceName)=> {
        openDownload(this.state.PDFFile, invoiceName);
      }

      const showNoXML = () => {
        document.getElementById('popup-no-xml').className = "popup special envelop-confirm-popup show";
      }
      return (
        <>
        {((this.props.filterKeywork === ALL) || (this.props.filterKeywork === sendType)) && (this.props.InvoiceID && (this.props.InvoiceID === this.state.InvoiceID)) && 
        <>
        <div className="thu-vien-container_body-item">
        {this.state.PDFFile?(
          <div className="item-left" onClick={this.props.PDFFileName?(()=>viewLetterPdf()):(()=>viewPdf())} style={{cursor: 'pointer'}}>
            <div className="item-left-icon">
              <img src="../img/icon/dai-ichi-icon.svg" alt="dai-ichi-icon" />
            </div>
            <div className="item-left-text">
              <div className="head-title">
                <p>{invoiceName}</p>
              </div>
              <div className="date">
                <div className="icon">
                  <img src="../img/icon/Calendar.svg" alt="Calendar" />
                </div>
                <div className="text">{this.props.ReceiptDate}</div>
              </div>
              <div className="text">{sendType}</div>
            </div>
          </div>
        ):(
          <div className="item-left">
            <div className="item-left-icon">
              <img src="../img/icon/dai-ichi-icon.svg" alt="dai-ichi-icon" />
            </div>
            <div className="item-left-text">
              <div className="head-title">
                <p>{invoiceName}</p>
              </div>
              <div className="date">
                <div className="icon">
                  <img src="../img/icon/Calendar.svg" alt="Calendar" />
                </div>
                <div className="text">{this.props.ReceiptDate}</div>
              </div>
              <div className="text">{sendType}</div>
            </div>
          </div>         
        )}

        <div className="item-right">
          { this.state.XMLFile&&(
            <button className="tai-xml" onClick={()=>downloadXML(invoiceName)}>Tải XML</button>
          )}
          
          {this.state.PDFFile?(
            <button className="tai-pdf" onClick={this.props.PDFFileName?(()=>downloadLetterPdf(invoiceName)):(()=>downloadPdf(invoiceName))}>Tải PDF</button>
          ):(
            <button className="tai-pdf" style={{background: '#d1c0b0', cursor: 'default'}}>Tải PDF</button>
          )}
          
        </div>
      </div>
      <div className="thu-vien-container_body-border"></div>  
      </>
      } 
      </>
      )
  }
}

export default EDocumentInvoiceDetail;
