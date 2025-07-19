import React, {Component} from 'react';
import {formatFullName, getDeviceId, getSession, showMessage, trackingEvent, getUrlParameter, cpSaveLogSDK} from '../sdkCommon';
import {CPGetPolicyListByCLIID, logoutSession, onlineRequestSubmit} from '../sdkAPI';
import LoadingIndicator from '../../common/LoadingIndicator2';
import AlertPopupOriginal from '../../components/AlertPopupOriginal';
import {
    FUND_STATE,
    COMPANY_KEY,
    AUTHENTICATION,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    IS_MOBILE,
    PageScreen
  } from '../sdkConstant';

class ChangeSundryPolList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            polListPayMode: this.props.polListPayMode,
            submitIn24: false,
            PolicyID: '', 
            PolicyLIName: '', 
            Frequency: '', 
            PolMPremAmt: '', 
            IsChangeFrequency: '', 
            PolicyClassCD: '', 
            FrequencyCode: '', 
            PolSndryAmt: '', 
            IsDegrading: '',
            noValidPolicy: false
        };
    }

    componentDidMount() {
        // if (!this.state.polListPayMode) {
            this.getPolicyListPayMode();
        // }
        this.setState({stepName: FUND_STATE.CHOOSE_POLICY});
        let from = getUrlParameter("fromApp");
                cpSaveLogSDK(`${from?from:'Web'}_${this.props.proccessType}${PageScreen.CHOICEPOL}`, this.props.apiToken, this.props.deviceId, this.props.clientId);
        trackingEvent(
            "Giao dịch hợp đồng",
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.CHOICEPOL}`,
            `${from?from:'Web'}_${this.props.proccessType}${PageScreen.CHOICEPOL}`,
            from
        );
    }

    getPolicyListPayMode() {
        const apiRequest = {//Dummy lấy danh sách hợp đồng, khi có API sẽ update request
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'GetPolListSundryAmount',
                Authentication: AUTHENTICATION,
                DeviceId: this.props.deviceId,
                APIToken: this.props.apiToken,
                Project: 'mcp',
                ClientID: this.props.clientId,
                UserLogin: this.props.clientId
            }
        }
        CPGetPolicyListByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            console.log(Response);
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                const jsonState = this.state;
                jsonState.polListPayMode = Response.ClientProfile;
                jsonState.stepName = FUND_STATE.CHOOSE_POLICY;
                jsonState.noValidPolicy = false;
                this.setState(jsonState);
                this.props.handlerPolListPayMode(Response.ClientProfile);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                //showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: { authenticated: false, hideMain: false }

                })

            } else {
                // this.setState({noValidPolicy: true});
                if (getSession(IS_MOBILE)) {
                    this.setState({noValidPolicy: true});
                } else {
                    this.props.handlerUpdateNoValidPolicy(true);
                }
                
            }


        }).catch(error => {
        }).finally(() => {

        });
    }

    render() {

        const choosePolicy = (PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, PolSndryAmtMax) => {
            this.props.showCardInfo(PolicyID, PolicyLIName, Frequency, PolMPremAmt, IsChangeFrequency, PolicyClassCD, FrequencyCode, PolSndryAmt, IsDegrading, PolSndryAmtMax);
        }

        const closeNoValidPolicy=()=> {
            this.setState({noValidPolicy: false});
            if (this.props.appType !== 'CLOSE') {
                let from = this.props.from;
                let obj = {
                    Action: "BACK_NOPOLICY_" + this.props.proccessType,
                    ClientID: this.props.clientId
                };
                if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                } else if (from && (from === "IOS")) {//for IOS
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                }
            }
        }

        let msg = '';
        let imgPath = '';
        if (this.state.noValidPolicy) {
            msg = 'Chúng tôi không tìm thấy hợp đồng đủ điều kiện thực hiện yêu cầu Thay đổi phí dự tính định kỳ. Quý khách vui lòng kiểm tra lại.';
            imgPath = FE_BASE_URL + '/img/popup/no-policy.svg';
        }

        return (
            <>
                {this.state.noValidPolicy && (
                    <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
                        <div className="insurance">
                            <div className="empty">
                                <div className={this.state.noValidPolicy ? "picture" : "icon"}
                                        style={{marginTop: '-45px'}}>
                                    <img src={imgPath} alt=""/>
                                </div>
                                {this.state.noValidPolicy ? (
                                    <h5 className='msg-alert-fixed' style={{width: '290px'}}>{msg}</h5>
                                ) : (
                                    <p style={{paddingTop: '20px'}}>{msg}</p>
                                )}


                            </div>
                        </div>
                    </div>
                )}
                <section className={getSession(IS_MOBILE)?'section-container-mobile':'section-container'}>
                    <div className="personform" style={{height: 'auto', overflowY: 'visible', paddingBottom: '48px'}}>
                        <h5 className="basic-bold basic-text-upper">chọn hợp đồng áp dụng</h5>

                        <div className="card-warpper" style={{marginBottom: '-22px'}}>
                            {this.state.polListPayMode && this.state.polListPayMode.map((item, index) => (
                                <div className="item" style={{padding: '6px 0'}}
                                    key={"fund-policy" + index}>
                                    <div
                                        className={(this.props.polID === item.PolicyID) ? "card choosen" : "card"}
                                        onClick={() => choosePolicy(item.PolicyID, item.PolicyLIName, item.Frequency, item.PolMPremAmt, item.IsChangeFrequency, item.PolicyClassCD, item.FrequencyCode, item.PolSndryAmt, item.IsDegrading, item.PolSndryAmtMax)}
                                        id={index}>
                                        <div className="card__header">
                                            <h4 className="basic-bold">{item.ProductName}</h4>
                                            <p>Dành
                                                cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (formatFullName(item.PolicyLIName)) : ''}</p>
                                            <div className="d-flex" style={{justifyContent: 'space-between'}}>
                                            {item.PolicyStatus.length < 25 ?
                                                <p>Hợp đồng: {item.PolicyID}</p> :
                                                <p className="policy">Hợp
                                                    đồng: {item.PolicyID}</p>}
                                            
                                            {(item.PolicyStatus === 'Hết hiệu lực' || item.PolicyStatus === 'Mất hiệu lực') ? (
                                                <div className="dcstatus">
                                                    <p className="inactive">{item.PolicyStatus}</p>
                                                </div>) : (
                                                <div className="dcstatus">
                                                    {item.PolicyStatus.length < 25 ?
                                                        <p className="active">{item.PolicyStatus}</p> :
                                                        <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                </div>
                                            )}
                                            </div>
                                            <div className="choose">
                                                <div className="dot"></div>
                                            </div>
                                        </div>
                                        <div className="card__footer">
                                            <div className="card__footer-item">
                                                <p>Định kỳ đóng phí</p>
                                                <p>{item.Frequency}</p>
                                            </div>
                                            <div className="card__footer-item">
                                                <p>Phí định kỳ/cơ bản định kỳ</p>
                                                <p className="basic-red basic-semibold">{item.PolMPremAmt} VNĐ</p>
                                            </div>
                                            {(item.PolicyClassCD !== 'TL') && (item.PolicyClassCD !== 'VE') && (item.PolicyClassCD !== 'MTL') &&
                                                <div className="card__footer-item">
                                                    <p>Phí dự tính định kỳ</p>
                                                    <p className="basic-red basic-semibold">{item.PolSndryAmt} VNĐ</p>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                        <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt=""/>
                        <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt=""/>
                    </div>
                    {!getSession(IS_MOBILE) &&
                    <div className="other_option" id="other-option-toggle"
                        onClick={() => this.props.goBack()}>
                        <p>Tiếp tục</p>
                        <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt=""/></i>
                    </div>
                    }
                </section>
                {this.state.noValidPolicy &&
                    <AlertPopupOriginal closePopup={()=>closeNoValidPolicy()} msg={msg} imgPath={imgPath}/>
                         
                }
            </>
        )
    }
}

export default ChangeSundryPolList;
