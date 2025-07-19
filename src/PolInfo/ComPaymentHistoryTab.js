import React, { Component } from 'react';
import Pagination from '../History/Paging';
import { logoutSession, Pay_HistoryPaymentDetail } from '../util/APIUtils';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION, FULL_NAME, CLIENT_PROFILE } from '../constants';
import {showMessage, toDate, getSession, getDeviceId} from '../util/common';

var profile=[];
class ComPaymentHistoryTab extends Component {
    constructor(props) {
        super(props)
        this.state = {
            clientProfile: [],
            pageOfItemsCom: [],
            clientProfileSearch: []
        };
        this.onChangePageCom = this.onChangePageCom.bind(this);
    }

    onChangePageCom(pageOfItemsCom) {
        // update state with new page of items
        this.setState({ pageOfItemsCom: pageOfItemsCom });
    }

    renderTableData() {
        return this.state.pageOfItemsCom.map((compol, index) => {
            const { ReceiptDate, TransactionType, ReceiptType, CollectionAmount, FullName, StatusVN } = compol //destructuring
            if (TransactionType === 'Đóng phí') {
                return (
                    <tr key={index}>
                        <td>{ReceiptDate.split(' ')[0]}</td>
                        <td>{TransactionType}</td>
                        <td>{ReceiptType}</td>
                        <td style={{textAlign: 'right'}}>{CollectionAmount}</td>
                    </tr>
                )
            } else if (TransactionType === 'Giải quyết quyền lợi') {
                return (
                    <tr key={index}>
                        <td>{ReceiptDate.split(' ')[0]}</td>
                        <td>{TransactionType}</td>
                        <td>
                            {FullName}<br/>
                            {StatusVN}<br/>
                            {ReceiptType}
                        </td>
                        <td style={{textAlign: 'right'}}>{CollectionAmount}</td>
                    </tr>
                )
            } else {
                return (<></>)
            }

        })
    }

    callHistoryPaymentAPI = () => {
        profile=[];
        let apiRequest = {
            jsonDataInput: {
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                Company: COMPANY_KEY,
                OS: '',
                Project: 'mcp',
                ClientID: getSession(CLIENT_ID),
                UserLogin: getSession(USER_LOGIN),
                PolicyNo: this.props.polID,
                Action: 'PaymentHistory',
                Offset: '1',
                Filter: 'All'
              }
        }
        Pay_HistoryPaymentDetail(apiRequest).then(Res => {
          if (Res.Response.ErrLog === 'successfull' && Res.Response.dtProposal !== null) {
            Res.Response.dtProposal.forEach(this.rebuildProfileFee);
            let sortProfile = this.sortListByDate(profile);
            this.setState({clientProfile: sortProfile, clientProfileSearch: sortProfile, pageOfItemsCom: sortProfile});
          } else if (Res.Response.NewAPIToken === 'invalidtoken' || Res.Response.ErrLog === 'APIToken is invalid') {
            // showMessage(EXPIRED_MESSAGE);
   
          } 
        }).catch(error => {
          
        });
        apiRequest.jsonDataInput.Action = 'ClaimHistory';
        Pay_HistoryPaymentDetail(apiRequest).then(Res => {
            if (Res.Response.ErrLog === 'successfull' && Res.Response.dtProposal !== null) {
              Res.Response.dtProposal.forEach(this.rebuildProfileClaim);
              let sortProfile = this.sortListByDate(profile);
               this.setState({clientProfile: sortProfile, clientProfileSearch: sortProfile, pageOfItemsCom: sortProfile});
            } else if (Res.Response.NewAPIToken === 'invalidtoken' || Res.Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
     
            } 
          }).catch(error => {
            
          });
          
      }

      rebuildProfileFee(item) {
        item['TransactionType'] = 'Đóng phí'; 
        profile.push(item);
      }
      rebuildProfileClaim(item) {
        item['TransactionType'] = 'Giải quyết quyền lợi'; 
        var reitem = [];
        reitem['TransactionType'] = 'Giải quyết quyền lợi'; 
        reitem['ReceiptDate'] = item['CreatedDate'];

        reitem['ReceiptType'] = item['ClaimType'];
        reitem['FullName'] = item['FullName'];
        reitem['StatusVN'] = item['StatusVN'];
        reitem['CollectionAmount'] = item['ClaimAmt']?item['ClaimAmt']: '-';
        profile.push(reitem);
      }
      sortListByDate(obj) {
        obj.sort((a, b) => {
          if (toDate(a.ReceiptDate) < toDate(b.ReceiptDate)) return 1;
          else if (toDate(a.ReceiptDate) > toDate(b.ReceiptDate)) return -1;
          else return 0;
        });
        return obj;
      }

      componentDidMount() {
          this.callHistoryPaymentAPI();
      }

    render() {
        return (
            <>
                <div>
                    <table className='comhislist'>
                        <thead>
                            <tr>
                                <th className='basic-bold' rowSpan="2" key="c11" style={{'background': 'RGB(242, 222, 202)', 'color': '#985801'}} >Ngày giao dịch</th>
                                <th className='basic-bold' rowSpan="2" key="c12" style={{'background': 'RGB(242, 222, 202)', 'color': '#985801'}}>Loại giao dịch</th>
                                <th className='basic-bold' colSpan="2" key="c134" style={{'background': 'RGB(242, 222, 202)', 'color': '#985801'}} >Các giao dịch phát sinh</th>
                            </tr>
                            <tr>
                                <th className='basic-bold' key="c23">Chi tiết</th>
                                <th className='basic-bold' key="c24">Số tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.renderTableData()}
                        </tbody>
                    </table>

                </div>
                <div className="paging-container" id="paging-container-com-id">
                    {this.state.clientProfileSearch !== null ? (
                        <Pagination items={this.state.clientProfileSearch} onChangePage={this.onChangePageCom} pageSize={20} />
                    ): (
                        <div className="insurance">
                          <div className="empty">
                            <div className="icon">
                              <img src="../../../img/no-data(6).svg" alt="no-data" />
                            </div>
                            <p style={{paddingTop:'20px'}}>Chưa có thông tin về giao dịch của hợp đồng</p>
                          </div>
                        </div>
                    )}
                </div>
            </>

        )
    }
}

export default ComPaymentHistoryTab;