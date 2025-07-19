import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, AUTHENTICATION, POL_PRODUCT_NAME } from '../constants';
import { logoutSession, CPGetPolicyListByCLIID, GetTaxInvoice } from '../util/APIUtils';
import { formatFullName, showMessage, getSession, getDeviceId } from '../util/common';
import LoadingIndicator from '../common/LoadingIndicator2';
import AES256 from 'aes-everywhere';
import EDocumentInvoiceDetail from './EDocumentInvoiceDetail';
import { Link, Redirect } from 'react-router-dom';
import Pagination from '../History/Paging';

const ALL = 'Tất cả', RECEIVE='Tôi nhận', SENDER='Tôi gửi';
class ComEDocumentInvoice  extends Component {
  constructor(props) {
    super(props);
    this.state = {
        clientProfile: null,
        pdfProfile: null,
        policyID: this.props.match.params.id,
        isEmpty: null,
        pdfUrl: '',
        DocTypeID: '',
        DocTypeName: '',
        taxInvoiceList: null,
        filterKeywork: ALL,
        filterList: [ALL, RECEIVE, SENDER],
        pageOfItemsCom: []

    };
    this.onChangePageCom = this.onChangePageCom.bind(this);
  }

  componentDidMount() {
    this.getTaxInvoiceList();
  }
  getTaxInvoiceList = () => {
    const submitRequest = {
     jsonDataInput:{
       OS: 'Samsung_SM-A125F-Android-11',
       Offset: '1',
       Action:'TaxInvoiceList',
       Project: 'mcp',
       APIToken:getSession(ACCESS_TOKEN),
       ClientID: getSession(CLIENT_ID),
       Authentication:AUTHENTICATION,
       DeviceId:getDeviceId(),
       PolicyNo: this.props.match.params.id?this.props.match.params.id.trim():'',
       UserLogin: getSession(USER_LOGIN),
     }
   }
   //console.log(JSON.stringify(submitRequest));
   GetTaxInvoice(submitRequest).then(Res => {
     //console.log(JSON.stringify(Res));
     let Response = Res.Response;
 
     if(Response.Result === 'true' && Response.ClientProfile){
         this.setState({
           isEmpty: false,
           taxInvoiceList: Response.ClientProfile
         });
     } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
       showMessage(EXPIRED_MESSAGE);
       logoutSession();
       this.props.history.push({
         pathname: '/home',
         state: { authenticated: false, hideMain: false}
 
       })
 
     } else {
       this.setState({
         isEmpty: true,
         taxInvoiceList: null
       });
     }
     }).catch(error => {
       this.props.history.push('/maintainence');
   });
 }
  onChangePageCom(pageOfItemsCom) {
    // update state with new page of items
    this.setState({ pageOfItemsCom: pageOfItemsCom });
  }
  render() {
      var logined = false;
      if (getSession(ACCESS_TOKEN)) {
        logined = true;
      }


      const tickCategory=(filterKeywork, id)=> {
        let popTicks = document.getElementsByClassName('filter-pop-tick').className;
        if (popTicks && popTicks.length > 0) {
          for (let i = 0; i < popTicks.length; i++) {
            popTicks[i].className = "filter-pop-tick";
          }
        }
        document.getElementById(id).className = 'filter-pop-tick ticked';

        this.setState({filterKeywork: filterKeywork});
      }

      const toggleFilter=()=> {
        if (document.getElementById('filter-dropdown-id').className === 'dropdown inputdropdown filter-dropdown') {
          document.getElementById('filter-dropdown-id').className = 'dropdown inputdropdown filter-dropdown show';
        } else {
          document.getElementById('filter-dropdown-id').className = 'dropdown inputdropdown filter-dropdown';
        }
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
      return (
        <main className={logined?"logined":"logined nodata"} id="main-id">
        <div className="main-warpper insurancepage basic-mainflex">

          <section className={this.state.isEmpty?"sccontract-warpper no-data":"sccontract-warpper"} style={{width:'100%'}}>
           
            <div className="breadcrums"style={{backgroundColor: bgColor}}>
              <div className="breadcrums__item">
                <p>Thư viện tài liệu</p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p style={{zIndex: '999'}}><Link to="/companypolicyinvoiceinfo" style={{color: '#727272', fontFamily: 'Inter, sans-serif'}}>Hóa đơn điện tử</Link></p>
                <span>&gt;</span>
              </div>
              <div className="breadcrums__item">
                <p>{this.props.match.params.id}</p>
                <span>&gt;</span>
              </div>
            </div>
            <LoadingIndicator area="epolicy-pdf" />
            {this.state.isEmpty ? (  
            <div className="insurance">
              <div className="empty">
                <div className="icon">
                  <img src="../img/no-data(6).svg" alt="no-data" />
                </div>
                <p style={{paddingTop:'20px'}}>Không tìm thấy hóa đơn.</p>
              </div>
            </div>
            ):(
              <div className="thu-vien-container">
              <div className="thu-vien-container_header">
                <div className="thu-vien-container_header-title">{getSession(POL_PRODUCT_NAME)}</div>
              </div>
              <div className="thu-vien-container_body">
              <LoadingIndicator area="policyList-by-cliID" />
              {this.state.pageOfItemsCom && this.state.pageOfItemsCom.map((item, index) => {
                    return (
                      item.PDFFileName?(
                        <EDocumentInvoiceDetail filterKeywork={this.state.filterKeywork} InvoiceID= {item.TaxInvoiceID} InvoiceSign={item.InvoiceSign} PolicyNo={item.PolicyNo} ReceiptDate={item.PrintedDate} TaxID={item.TaxID} PDFFileName={item.PDFFileName}/>
                      ):(
                        <EDocumentInvoiceDetail filterKeywork={this.state.filterKeywork} InvoiceID= {item.TaxInvoiceID} InvoiceSign={item.InvoiceSign} PolicyNo={item.PolicyNo} ReceiptDate={item.PrintedDate} TaxID={item.TaxID}/>
                      )
                    )
                  })
                }

              </div>
              <div className="paging-container" id="paging-container-com-invoice-id">
                  {this.state.taxInvoiceList !== null && (
                      <Pagination items={this.state.taxInvoiceList} onChangePage={this.onChangePageCom} pageSize={5} />
                  )}
              </div>
            </div>

            )}
          </section>
        </div>
      </main>     
      )
  }
}

export default ComEDocumentInvoice;
