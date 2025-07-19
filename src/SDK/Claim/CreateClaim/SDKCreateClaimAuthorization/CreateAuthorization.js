import { Component } from 'react';
import { AUTHENTICATION, CODE_SUCCESS, WEB_BROWSER_VERSION, IS_MOBILE, FE_BASE_URL, FULL_NAME, ACCESS_TOKEN, COMPANY_KEY3, SYSTEM_DCONNECT, SIGNATURE } from '../../../sdkConstant';
import { authorizationRecordInfor, getAgentServiceListByClientId, getPolicyListByAgentServiceId } from '../../../sdkAPI';
import LoadingIndicator from '../../../LoadingIndicator2';
import { buildMicroRequest, getOperatingSystem, getDeviceId, formatFullName, getSession, getOSVersion, getUrlParameter } from '../../../sdkCommon';
import arrowDown from "../../../img/icon/dropdown-arrow.svg";
import ThanksPopupSDK from '../../../components/ThanksPopupSDK';
import AES256 from 'aes-everywhere';

class CreateAuthorization extends Component {
    constructor(props) {
        super(props)
        this.state = {
            agentServiceList: null,
            showAuthorizeSuccess: false,
            isActive: [],
            agree: false,
            policyList: null,
            isEmpty: true
        };

    }

    componentDidMount() {
        this.getAgentServiceList();
    }

    getAgentServiceList() {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.props.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE),
            accessToken: this.props.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            customerId: this.props.clientId
        };
        let request = buildMicroRequest(metadata, data);
        getAgentServiceListByClientId(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ agentServiceList: Res.data });
                console.log('AgentServiceList=', Res.data);
            } else {
                console.log('not success=', Res);
            }
        }).catch(error => {
            console.log(error);
        });
    }

    getPolicyListByAgentServiceId(agentCode) {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: this.props.sourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE),
            accessToken: this.props.apiToken || getSession(ACCESS_TOKEN)
        }
        let data = {
            customerId: this.props.clientId,
            agentId: agentCode
        };
        let request = buildMicroRequest(metadata, data);
        getPolicyListByAgentServiceId(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                this.setState({ policyList: Res.data });
                console.log('PolicyListByAgent=', Res.data);
            } else {
                console.log('PolicyListByAgent not success=', Res);
            }
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        let item = this.props.selectedAgent;
        const createAuthorization = (event, agentCode, sourceSystem, requestId, agentName) => {
            event.preventDefault();
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),
                platform: WEB_BROWSER_VERSION,
                system: this.props.sourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),
                accessToken: this.props.apiToken || getSession(ACCESS_TOKEN)
            }
            this.setState({ requestId: requestId });
            let link = '';//buildLinkAuthorization(agentCode, agentName);
            let data = {
                sourceSystem: sourceSystem,
                requestId: requestId,
                customerId: this.props.clientId,
                authorizadCode: agentCode,
                processType: this.props.processType,
                link: link,
                status: 'Approval',
                role: this.props.systemGroupPermission?.[0]?.Role,
                authorizadName: agentName
            }

            let request = buildMicroRequest(metadata, data);

            authorizationRecordInfor(request).then(Res => {
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

        const closeAuthorizeSuccess = () => {
            this.setState({ showAuthorizeSuccess: false });
            this.props.closeToHome();
        }

        const toggleActive = (index) => {
            let isActive = this.state?.isActive;
            isActive[index] = !isActive[index];
            this.setState({ isActive: isActive });
        }

        const toggleAgree = () => {
            this.setState({ agree: !this.state.agree });
        }

        const chooseAgent=(item) => {
            this.setState({isEmpty: false});
            this.props.handleChooseAgent(item);
            this.getPolicyListByAgentServiceId(item?.agentCode);
        }

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (document.getElementById("main-id")?.className?.indexOf('nodata') >= 0) {
                this.props.closeToHome();
            } else if (main) {
                main.classList.toggle("nodata");
            }
        }

        const continueAgent = () => {
            const main = document.getElementById("main-id");
             if (main) {
                main.classList.toggle("nodata");
            }
        }

        let nodata = getUrlParameter("fromApp") ? ' nodata dc-mobile' : '';
        return (
            (this.props.systemGroupPermission?.[0]?.Role === 'PO') && (
                <main className={getSession(ACCESS_TOKEN) ? `logined ${this.props.cssSystem + nodata}` : (this.props.cssSystem + nodata)} id="main-id">
                    {getUrlParameter("fromApp") &&
                    <div className='app-header-back'>
                        <i className='margin-left8' onClick={() => goBack()}><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                        <p className='viewer-back-title'>Yêu cầu ĐLBH hỗ trợ</p>
                    </div>
                    }
                    <div className="main-warpper basic-mainflex">
                        <section className="sccard-warpper">
                            <h5 className={getUrlParameter("fromApp") && (getUrlParameter("fromApp") === 'IOS')? "basic-bold padding-top56": "basic-bold"}>Chọn Đại lý bảo hiểm thực hiện thay</h5>
                            <div className="card-warpper padding-top16">
                                <LoadingIndicator area="policyList-by-cliID" />
                                {this.state.agentServiceList && this.state.agentServiceList.map((item, index) => (
                                    <div className="item">
                                        <div className={(this.props.state?.selectedAgent?.agentCode === item?.agentCode) ? "card choosen" : "card"}
                                            onClick={() => chooseAgent(item)}
                                            id={index}>
                                            <div className="card__header">
                                                <h4 className="basic-bold">{formatFullName(item?.agentName)}</h4>
                                                <p>Mã tư vấn: {item?.agentCode}</p>
                                                <p>Tình trạng:
                                                    {item?.agentStatus === 'Term' ? (
                                                        <span className="inactiveContent"> Ngưng hoạt động</span>
                                                    ) : (
                                                        <span className="activeContent"> Đang hoạt động</span>
                                                    )}
                                                </p>
                                                <div className="choose">
                                                    <div className="dot"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                            {getUrlParameter("fromApp") && this.props.state.selectedAgent?.agentCode &&
                                <div className="bottom-btn">
                                    <button className="btn btn-primary" onClick={() => continueAgent()}
                                    >Tiếp tục
                                    </button>
                                </div>
                            }
                            {!getUrlParameter("fromApp") &&
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Tiếp tục</p>
                                <i><img src={FE_BASE_URL + "/img/icon/arrow-left.svg"} alt="" /></i>
                            </div>
                            }
                        </section>
                        <section className="sccontract-warpper">
                            {!getUrlParameter("fromApp") &&
                                <div className="breadcrums">
                                    <div className="breadcrums__item">
                                        <p>Yêu cầu quyền lợi</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Tạo mới yêu cầu</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Tạo ủy quyền</p>
                                        <span></span>
                                    </div>
                                </div>
                            }
                            {!getSession(IS_MOBILE) &&
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src={FE_BASE_URL + "/img/icon/return_option.svg"} alt="" /></i>
                            </div>
                            }
                            {this.state.isEmpty ? (
                                <div className="sccontract-container" style={{ backgroundColor: '#ffffff' }}>
                                    <div className="insurance">
                                        <div className="empty">
                                            <p style={{ paddingTop: '20px' }}>Thông tin chi tiết sẽ hiển thị khi bạn
                                                chọn một đại lý ở bên trái.</p>

                                        </div>
                                    </div>
                                </div>
                            ) : (
                                <div className={getUrlParameter("fromApp")?((getUrlParameter("fromApp") === 'Android')?"tthd-main margin-top0": "tthd-main nd13-margin-top68"): "tthd-main"}>
                                    <div className={getSession(IS_MOBILE) ? "tthd-main__wrapper margin-left-right-16" : "tthd-main__wrapper"} style={{ minHeight: 'unset' }}>
                                        {item && (this.props.systemGroupPermission?.[0]?.Role === 'PO') && (
                                            <div className="info__body claim-type">
                                                <div className="consulting-service" style={{ alignItems: 'flex-start', maxWidth: '600px' }}>
                                                    <h5 className='basic-bold' style={{ alignSelf: 'flex-start' }}>Thông tin Đại lý bảo hiểm</h5>
                                                    <div className="avatar-field" style={{ alignSelf: 'center' }}>
                                                        <div className="avatar">
                                                            <img src="../img/ava_tvtc.png" alt="" />
                                                        </div>
                                                        <h3 className="basic-bold">{item?.agentName}</h3>
                                                        <p>{item?.agentLevel}</p>
                                                    </div>
                                                    <div className="form">
                                                        <div className="form__item">
                                                            <p>Tình trạng</p>
                                                            <div className="dcstatus"
                                                                style={{ alignItems: 'center', display: 'flex' }}>
                                                                {item.agentStatus === 'Term' ? (
                                                                    <p className="inactiveContent">Ngưng hoạt động</p>
                                                                ) : (
                                                                    <p className="activeContent">Đang hoạt động</p>
                                                                )}
                                                            </div>
                                                        </div>
                                                        <div className="form__item">
                                                            <p>Mã đại lý</p>
                                                            <p>{item?.agentCode}</p>
                                                        </div>
                                                        <div className="form__item">
                                                            <p>Văn phòng</p>
                                                            <p>{item?.officeName}</p>
                                                        </div>
                                                        {item.agentStatus !== 'Term' && (
                                                            <div className="form__item">
                                                                <p>Điện thoại</p>
                                                                <p>{item?.phone}</p>
                                                            </div>
                                                        )}
                                                        {item.agentStatus !== 'Term' && (
                                                            <div className="form__item"
                                                                style={{ borderBottom: '0px solid #e6e6e6' }}>
                                                                <p>Email</p>
                                                                <p>{item?.email}</p>
                                                            </div>
                                                        )}
                                                    </div>
                                                    {item?.agentStatus !== 'Term' ? (
                                                        <div className="contact" style={{ alignSelf: 'center' }}>
                                                            <div className="mail-icon">
                                                                {item?.email ?
                                                                    <a href={"mailto:" + item?.email}><img
                                                                        src={FE_BASE_URL + "/img/icon/icon_email.svg"} alt="" /></a> :
                                                                    <a href="mailto: customer.services@dai-ichi-life.com.vn"><img
                                                                        src={FE_BASE_URL + "/img/icon/icon_email.svg"} alt="" /></a>}
                                                            </div>
                                                            <div className="phone-icon">
                                                                <a href={"tel:" + item?.phone}><img
                                                                    src={FE_BASE_URL + "/img/icon/icon_phone.svg"} alt="" /></a>
                                                            </div>
                                                        </div>
                                                    ) : (
                                                        <><p><br /></p><p className="basic-bold">Liên hệ với Dai-ichi Life
                                                            Việt Nam</p>
                                                            <div className="contact" style={{ alignSelf: 'center' }}>
                                                                <div className="mail-icon">
                                                                    <a href="mailto: customer.services@dai-ichi-life.com.vn"><img
                                                                        src={FE_BASE_URL + "/img/icon/icon_email.svg"} alt="" /></a>
                                                                </div>
                                                                <div className="phone-icon">
                                                                    <a href="tel:02838100888"><img
                                                                        src={FE_BASE_URL + "/img/icon/icon_phone.svg"} alt="" /></a>
                                                                </div>
                                                            </div>
                                                        </>
                                                    )}
                                                </div>
                                                <div className='padding-top32'>
                                                    <p className='basic-bold'>Danh sách hợp đồng ĐLBH đang được phục vụ</p>
                                                    <LoadingIndicator area="submit-init-claim"/>
                                                </div>
                                                <div className="esubmission contract-products show-component" style={{ zIndex: 0, marginBottom: '16px' }}>

                                                    <div className="card-extend-container">
                                                        <div className="card-extend-wrapper">

                                                            {this.state.policyList && this.state.policyList.map((item, index) => (
                                                            <div className={getSession(IS_MOBILE) ? "item card-mobile" : "item"} style={{ maxWidth: '600px', width: 'unset' }}>
                                                                {/* <div className="card__label">
                                                                    <p className="basic-bold">Bảo hiểm chính</p>
                                                                </div> */}
                                                                <div className="card">

                                                                    <>


                                                                        <div className="card__header">
                                                                            <h4 className="basic-bold">{item?.productName}</h4>
                                                                            <div style={{ display: 'flex', justifyContent: 'space-between'}}>
                                                                                {item?.policyStatus?.length < 25 ? <p>Hợp đồng: {item?.policyId }</p> :
                                                                                    <p className="policy">Hợp đồng: {item?.policyId}</p>}
                                                                                <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'end' }}>
                                                                                {(item?.policyStatus === 'Hết hiệu lực' || item?.policyStatus === 'Mất hiệu lực') ? (
                                                                                    <div className="dcstatus">
                                                                                        <p className="inactive">{item?.policyStatus}</p>
                                                                                    </div>) : (<div className="dcstatus">
                                                                                        {item?.policyStatus?.length < 25 ?
                                                                                            <p className="active">{item?.policyStatus}</p> :
                                                                                            <p className="activeLong">{item?.policyStatus?.replaceAll('(', ' (') ? item?.policyStatus?.replaceAll('(', ' (') : 'Đang hiệu lực'}</p>}
                                                                                    </div>)}
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div className="card__footer">
                                                                            <div className="card__footer-item">
                                                                                <p>Ngày hiệu lực</p>
                                                                                <p>{item?.polIssEffDate ? item?.polIssEffDate : ''}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p>Số tiền bảo hiểm</p>
                                                                                <p className="basic-red basic-bold">{item?.faceAmount} VNĐ</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p>Đại lý bảo hiểm</p>
                                                                                <p>{item.agentName ? item?.agentName.toUpperCase() : ''}</p>
                                                                            </div>
                                                                        </div>

                                                                    </>

                                                                </div>
                                                            </div>
                                                            ))}



                                                        </div>
                                                    </div>
                                                    <div className='paymode-margin-bottom' style={{ display: 'flex', justifyContent: 'left', alignItems: 'left', marginTop:'24px', verticalAlign: 'middle' }}>
                                                        <div className={this.state.agree ? "bottom-text choosen" : "bottom-text"}
                                                            style={{ 'maxWidth': '594px', paddingLeft: '3px', marginTop: '10px', marginBottom:'26px' }}
                                                            onClick={() => toggleAgree()}>
                                                            <div className={this.state.agree ? "square-choose fill-red" : "square-choose"}
                                                                style={{ cursor: 'pointer', minWidth: '17.14px' }}>
                                                                <div className="checkmark">
                                                                    <img src={FE_BASE_URL + "/img/icon/check.svg"} alt="" />
                                                                </div>
                                                            </div>
                                                            <p className="popup-security__title" style={{
                                                                textAlign: 'justify',
                                                                paddingLeft: '12px',
                                                                paddingRight: '12px',
                                                                paddingTop: '0'
                                                            }}>
                                                                
                                                                <span style={{ color: '#727272', fontSize: '12px', fontWeight: '600' }}>
                                                                    Tôi xác nhận đồng ý cho phép Đại lý bảo hiểm trong vòng 24 giờ, hỗ trợ tôi khai báo Yêu cầu giải quyết quyền lợi và các thông tin liên quan đến Yêu cầu này.
                                                                </span>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div className="btn-wrapper" style={{ display: 'flex', justifyContent: 'center', maxWidth: '600px', marginTop: '20px' }}>
                                                        {this.props.selectedAgent && this.state.agree ? (
                                                            <div className="bottom-btn">
                                                                <button className="btn btn-primary" id="btn-user-agree"
                                                                    onClick={(event) => createAuthorization(event, this.props.selectedAgent?.agentCode, (this.props.selectedAgent?.channel === 'AD') ? 'ADPortal' : 'DSuccess', "", this.props.selectedAgent?.agentName)}>Xác nhận</button>
                                                            </div>
                                                        ) : (
                                                            <div className="bottom-btn">
                                                                <button className="btn btn-primary disabled" id="btn-user-agree">Xác nhận</button>
                                                            </div>
                                                        )
                                                        }
                                                    </div>
                                                </div>
                                            </div>
                                        )}
                                        {/* {this.state.showAuthorizeSuccess &&
                    <ThanksGeneralPopup closePopup={() => closeAuthorizeSuccess()}
                        msg={`<p>GỬI YÊU CẦU THÀNH CÔNG</p>`}
                    />
                } */}

                                        {this.state.showAuthorizeSuccess &&
                                            <ThanksPopupSDK
                                                msg={'<p>Đại lý sẽ nhận thông tin và liên hệ đến Quý khách</p>'}
                                                closeThanks={() => closeAuthorizeSuccess()} />
                                        }
                                    </div>
                                </div>
                            )}
                        </section>

                    </div>
                </main>
            )
        )
    }
}

export default CreateAuthorization;