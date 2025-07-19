import { Component } from 'react';
import {
    SDK_REQUEST_STATUS, FULL_NAME, SDK_ROLE_AGENT, CLAIM_STEPCODE, SDK_ROLE_PO, FE_BASE_URL, IS_MOBILE,
    AUTHORZIE_STATUS_CODE_NOT_CREATE, AUTHORZIE_STATUS_CODE_APPROVE, WEB_BROWSER_VERSION, AUTHENTICATION, CODE_SUCCESS, SIGNATURE,
    ACCESS_TOKEN
} from '../../../sdkConstant';
import { getSession, getBenifits, getDeviceId, getOperatingSystem, buildMicroRequest, getOSVersion, formatDateString, getUrlParameter } from '../../../sdkCommon';
import OkPopupTitle from '../../../components/OkPopupTitle';
import AgreeCancelAuthorizePopup from '../../../components/AgreeCancelAuthorizePopup';
import { authorizationUpdateInfor, authorizationRecordInfor } from '../../../sdkAPI';

class ConfirmAuthorization extends Component {
    constructor(props) {
        super(props)
        this.state = {
            showRejectConfirm: false,
            showThank: false
        };

    }

    componentDidMount() {

    }



    render() {
        const agree = () => {
            if (this.props.authorzieStatus === AUTHORZIE_STATUS_CODE_NOT_CREATE) {
                cornfirmCreateAuthorization(this.props.agentCode, this.props.requestId);
            } else {
                cornfirmUpdateAuthorization(this.props.authorizationId);
            }

            this.props.setShowConfirmAuthorization(false);
            this.props.setCurrentState(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
        }

        const notAgree = () => {
            this.props.setCurrentState(this.props.handlerGetStep(CLAIM_STEPCODE.REVIEW));
            setShowRejectConfirm(true);
        }

        const setShowRejectConfirm = (value) => {
            this.setState({ showRejectConfirm: value });
        }

        const rejectClaim = () => {
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REJECT_AUTHORIZED, SDK_ROLE_PO, "", true);//Truyền status empty sẽ keep current status
            setShowRejectConfirm(false);
            setShowThank(true);

        }

        const requestEdit = (event) => {
            // event.preventDefault();
            setShowRejectConfirm(false);
            this.props.handlerUpdateClaimRequest(SDK_REQUEST_STATUS.REEDIT, SDK_ROLE_AGENT, "");//Truyền status empty sẽ keep current status
            setShowThank(true);
        }

        const setShowThank = (value) => {
            this.setState({ showThank: value });
            if (!value) {
                this.props.closeToHome();
            }
        }

        const cornfirmCreateAuthorization = (agentCode, requestId) => {
            // event.preventDefault();
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),
                platform: WEB_BROWSER_VERSION,
                system: this.props.systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),
                accessToken: this.props.apiToken || getSession(ACCESS_TOKEN)
            }
            this.setState({ requestId: requestId });
            let link = '';
            let data = {
                requestId: requestId,
                customerId: this.props.clientId,
                authorizadCode: agentCode,
                processType: this.props.processType,
                link: link,
                status: AUTHORZIE_STATUS_CODE_APPROVE,
                role: this.props.systemGroupPermission?.[0]?.Role,
                authorizadName: this.props.agentName
            }

            let request = buildMicroRequest(metadata, data);
            console.log(JSON.stringify(request));

            authorizationRecordInfor(request).then(Res => {
                if (Res.code === CODE_SUCCESS) {
                    // this.setState({authorizedId: Res.data?.[0]?.authorizedId, showAuthorizeSuccess: true});
                    this.setState({ showAuthorizeSuccess: true });
                    // this.props.handleSetAuthorizedId(Res.data?.[0]?.authorizedId);
                } /*else if (Res.code === CODE_ERROR) {
                } else if (Res.code === CODE_EXPIRED_TOKEN) {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home', state: {authenticated: false, hideMain: false}
        
                    })
        
                }*/
            }).catch(error => {
                console.log(error);
            });

        }

        const cornfirmUpdateAuthorization = (authorizationId) => {
            // event.preventDefault();
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),
                platform: WEB_BROWSER_VERSION,
                system: this.props.systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),
                accessToken: this.props.apiToken || getSession(ACCESS_TOKEN)
            }
            let link = '';
            let data = {
                authorizationId: authorizationId,
                link: link,
                status: AUTHORZIE_STATUS_CODE_APPROVE,
                role: this.props.systemGroupPermission?.[0]?.Role
            }

            let request = buildMicroRequest(metadata, data);
            console.log(JSON.stringify(request));

            authorizationUpdateInfor(request).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    // this.setState({authorizedId: Res.data?.[0]?.authorizedId, showAuthorizeSuccess: true});
                    this.setState({ showAuthorizeSuccess: true });
                    this.props.handleSetAuthorizedId(Res.data?.[0]?.authorizedId);
                } /*else if (Res.code === CODE_ERROR) {
                } else if (Res.code === CODE_EXPIRED_TOKEN) {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home', state: {authenticated: false, hideMain: false}
        
                    })
        
                }*/
            }).catch(error => {
                console.log(error);
            });

        }
        return (
            <>
                <section className="sccontract-warpper" style={{height:'100%'}}>
                {!getSession(IS_MOBILE) &&
                    <div className="heading" style={{height: '40px', background:'#F5F5F2', paddingBottom:'50px !important'}}>
                        <div className="breadcrums"  style={{background:'#F5F5F2'}}>
                            <div className="breadcrums__item">
                                <p>Yêu cầu quyền lợi</p>
                                <span>&gt;</span>
                            </div>
                            <div className="breadcrums__item">
                                <p>Yêu cầu quyền lợi bảo hiểm</p>
                                <span>&gt;</span>
                            </div>
                        </div>
                    </div>
                } 
                {getSession(IS_MOBILE) &&
                <div className={getUrlParameter("fromApp") ? "heading__tab mobile" : "heading__tab"}>
                    <div className="step-container">
                        <div className="step-wrapper">
                            <div className="step-btn-wrapper">
                                <div className="back-btn" onClick={() => this.props.closeToHome()}
                                        >
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
                                        <p style={{textAlign: 'center', minWidth: '200%', fontWeight: '700'}}>Yêu cầu Quyền lợi bảo hiểm</p>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                }
                    <div className="eclaim-confirm" style={{ paddingBottom: 60 }}>
                        <div className="stepform" style={{ marginTop: '180px'}}>
                            {/* <div className='stepform__body'> */}
                            <div className="info">
                                <div className="info__body" style={{ paddingTop: '20px', fontFamily: 'Inter', fontSize: '16px', fontWeight: '500', lineHeight: '24px', textAlign: 'left' }}>
                                    <p style={{ marginLeft: '16px', marginRight: '16px', marginTop: '16px' }}>Kính chào Quý khách <span className='basic-bold'>{this.props.fullName || getSession(FULL_NAME)}</span>,</p>
                                    <p style={{ marginLeft: '16px', marginRight: '16px', marginTop: '8px' }}>Quý khách vui lòng xác nhận thông tin về việc cho phép Đại lý bảo hiểm hỗ trợ khai báo <span className='basic-bold'>Yêu cầu Giải quyết quyền lợi bảo hiểm</span>:<br /><br /></p>
                                    <img style={{ minWidth: '100%', marginTop: '0', marginBottom: '0', marginTop:'-16px' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                                    <div className="optionalform-wrapper" style={{marginTop: '-16px'}}>
                                        <div className="optionalform">
                                            <div className="optionalform__title">
                                                <h5 className="basic-bold">THÔNG TIN YÊU CẦU</h5>
                                            </div>
                                            <div className="optionalform__body" style={{marginTop: '-20px'}}>
                                                <div className="tabflex-wrapper">
                                                    <div className="tabflex">
                                                        <h5>Mã yêu cầu</h5>
                                                        <p className="special basic-uppercase" style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{this.props.requestId}</p>
                                                    </div>
                                                    <div className="tabflex">
                                                        <h5>Ngày nộp</h5>
                                                        <p style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{formatDateString(this.props.effectiveDate)}</p>
                                                    </div>
                                                    <div className="tabflex">
                                                        <h5>Người được bảo hiểm</h5>
                                                        <p style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{this.props.selectedCliInfo?.fullName}</p>
                                                    </div>
                                                    <div className="tabflex">
                                                        <h5>Quyền lợi được chọn</h5>
                                                        <p style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{getBenifits(this.props.claimCheckedMap)}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <img style={{minWidth: '100%', marginLeft: '0px', marginBottom: '0', marginTop:'-16px' }} className='punch-line-margin' src={FE_BASE_URL + '/img/punch-line.svg'} />
                                    <div className="optionalform-wrapper" style={{marginTop: '-16px'}}>    
                                        {/* bug19 <div className={getUrlParameter("fromApp")?"optionalform nd13-padding-bottom120":"optionalform"} style={{marginTop: '-24px'}}> */}
                                        <div className={getUrlParameter("fromApp")?"optionalform":"optionalform"} style={{marginTop: '-24px'}}> 
                                            <div className="optionalform__title">
                                                <h5 className="basic-bold">THÔNG TIN ĐẠI LÝ BẢO HIỂM</h5>
                                            </div>
                                            <div className="optionalform__body" style={{marginTop: '-20px'}}>
                                                <div className="tabflex-wrapper">
                                                    <div className="tabflex">
                                                        <h5>Họ và tên</h5>
                                                        <p className="special basic-uppercase" style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{this.props.agentName}</p>
                                                    </div>
                                                    <div className="tabflex">
                                                        <h5>Mã số Đại lý bảo hiểm</h5>
                                                        <p style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{this.props.agentCode}</p>
                                                    </div>
                                                    <div className="tabflex">
                                                        <h5>Số điện thoại</h5>
                                                        <p style={{
                                                            maxWidth: "300px", textAlign: "right"
                                                        }}>{this.props.agentPhone}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <p className='modal-body-sub-content-frame'>Quý khách vui lòng bấm <span className='bold-brown'>Tiếp tục</span> để thực hiện  xác nhận thông tin và hoàn tất Yêu cầu, hoặc <span className='bold-brown'>Từ chối</span> để hủy Yêu cầu quyền lợi này.
                                                <br />
                                                Yêu cầu trực tuyến này sẽ tự đồng hủy sau 24h nếu không được hoàn thành.
                                            </p>
                                            {/* <p style={{ textAlign: 'center', marginTop: '12px' }}>
                                                <span className="au-red-text">Bằng cách bấm nút Tiếp tục, Quý khách đã đồng ý cho phép Đại lý bảo hiểm hỗ trợ Quý khách khai báo Yêu cầu quyền lợi này.</span>
                                            </p> */}
                                        </div>

                                    </div>
                                    
                                    {/* <div className="bottom-text"
                                        style={{ 'maxWidth': '594px', marginBottom: '20px', marginLeft: '16px', marginRight: '16px' }}>
                                        <p style={{ textAlign: 'center' }}>
                                            <span className="red-text basic-bold">Bằng cách bấm nút Tiếp tục, Quý khách đã đồng ý cho phép Đại lý bảo hiểm hỗ trợ Quý khách khai báo Yêu cầu quyền lợi này.</span>
                                        </p>
                                    </div> */}
                                    {/* <div style={{justifyContent:'center', display: 'flex'}}>
                                <div className="btn-wrapper eclaim-btn">
                                    <button className="btn btn-primary eclaim-btn" onClick={() => agree()}>Tiếp tục</button>
                                    <button className="btn btn-nobg eclaim-btn" onClick={() => notAgree()}>Từ chối</button>

                                </div>
                            </div> */}
                                    {/* <div className="bottom-btn eclaim-confirm-bottom">
                                        {this.props.isSubmitting ? (
                                            <button className="btn btn-primary disabled"
                                            >Tiếp tục
                                            </button>
                                        ) : (
                                            <button className="btn btn-primary"
                                                onClick={() => agree()}>Tiếp tục
                                            </button>
                                        )}
                                    </div>
                                    <div className="bottom-btn eclaim-confirm-bottom2">
                                        <button className="no-btn bold-brown" onClick={() => notAgree()}>Từ chối</button>
                                    </div> */}
                                </div>
                            </div>
                            {/* </div> */}
                            <img className="decor-clip" src={FE_BASE_URL + "/img/mock.svg"} alt="" />
                            <img className="decor-person" src={FE_BASE_URL + "/img/person.png"} alt="" />
                        </div>
                        <div className="confirm-policy-wrapper" style={{paddingBottom:'20px'}}>
                            <p className='confirm-policy-content'>Bằng cách bấm nút Tiếp tục, Quý khách đã đồng ý cho phép Đại lý bảo hiểm hỗ trợ Quý khách khai báo Yêu cầu quyền lợi này.
                            </p>
                        </div>
                        
                        {/* {getUrlParameter("fromApp") &&
                            <div className='nd13-padding-bottom120'></div>
                        }
                        {getUrlParameter("fromApp") &&
                            <div className='nd13-padding-bottom120'></div>
                        } */}

                        <div className={getUrlParameter("fromApp")?"bottom-btn eclaim-confirm-bottom filter-none":"bottom-btn eclaim-confirm-bottom"} >
                            {this.props.isSubmitting ? (
                                <button className="btn btn-primary disabled"
                                >Tiếp tục
                                </button>
                            ) : (
                                <button className="btn btn-primary"
                                    onClick={() => agree()}>Tiếp tục
                                </button>
                            )}
                        </div>
                        <div className={getUrlParameter("fromApp")? "bottom-btn eclaim-confirm-bottom2 filter-none": "bottom-btn eclaim-confirm-bottom2"} style={{ 'marginBottom': '20px' }}>
                            <button className="no-btn bold-brown" onClick={() => notAgree()}>Từ chối</button>
                        </div>
                    </div>
                </section>
                {/* {this.state.showRejectConfirm &&
                    <AgreeCancelPopup closePopup={() => setShowRejectConfirm(false)} agreeFunc={(event) => requestEdit(event)}
                        msg={'<p>Yêu cầu điều chỉnh thông tin của Quý khách sẽ được gửi đến Đại lý bảo hiểm thực hiện, hoặc Quý khách có thể hủy yêu cầu trực tuyến này và lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý gần nhất.</p>'}
                        imgPath={FE_BASE_URL + '/img/popup/reject-confirm.svg'} agreeText='Yêu cầu Đại lý BH điều chỉnh' notAgreeText='Hủy yêu cầu QLBH' notAgreeFunc={() => rejectClaim()} />} */}
                {this.state.showRejectConfirm &&
                <AgreeCancelAuthorizePopup closePopup={() => setShowRejectConfirm(false)} agreeFunc={() => setShowRejectConfirm(false)}
                    imgPath={FE_BASE_URL + '/img/popup/reject-confirm.svg'} agreeText='Thực hiện lại' notAgreeText='Hủy yêu cầu' notAgreeFunc={() => rejectClaim()} />}
                {this.state.showThank &&
                    <OkPopupTitle closePopup={() => setShowThank(false)}
                        msg={'Cảm ơn Quý khách đã xác nhận thông tin'}
                        imgPath={FE_BASE_URL + '/img/popup/ok.svg'}
                        agreeFunc={() => setShowThank(false)}
                        agreeText='Đóng'
                    />
                }
            </>
        )
    }
}

export default ConfirmAuthorization;
