import React, { Component } from 'react';
import Pagination2 from '../../../Paging2';
import {
    PUBLIC_KEY_DLVN, AUTHENTICATION, WEB_BROWSER_VERSION, CODE_SUCCESS, FE_BASE_URL, ACCESS_TOKEN, COMPANY_KEY,
    CELL_PHONE,
    CLIENT_ID,
    DCID,
    EMAIL,
    USER_LOGIN,
    VERIFY_CELL_PHONE,
    VERIFY_EMAIL,
    FULL_NAME,
    GENDER,
    TWOFA,
    DOB,
    POL_LIST_CLIENT,
    DEVICE_ID,
    ADDRESS,
    OTHER_ADDRESS,
    POID,
    POL_LIST_CLIENT4CLAIM,
    EXPIRED_MESSAGE,
    FROM_SDK,
    SYSTEM_DCONNECT,
    SIGNATURE
} from '../../../sdkConstant';
import { CPGetPolicyListByCLIID, logoutSession, authorizationRecordInfor, requestClaimRecordInfor, getPermissionByGroupAndProccessType, getTokenPO, checkAccount, getCustomerProfile } from '../../../sdkAPI';
import LoadingIndicator from '../../../LoadingIndicator2';
import { getUrlParameter, getDeviceId, buildMicroRequest, getOperatingSystem, genRequestId, genPassword, getSession, setSession, getOSAndBrowserInfo, showMessage, getOSVersion } from '../../../sdkCommon';
import AlertMsgTitlePopup from '../../../components/AlertMsgTitlePopup';
import AgreeCancelPopup from '../../../components/AgreeCancelPopup';
import AES256 from 'aes-everywhere';
import NodeForge from 'node-forge';

let from = '';
class RequestAuthorization extends Component {
    constructor(props) {
        super(props)
        this.state = {
            showAuthorizationRequest: false,
            showConfirm: false
        };

    }

    componentDidMount() {
        // this.setState({showAuthorizationRequest: true});
    }



    render() {
        const closeShowAuthorizationRequest = () => {
            this.setState({ showAuthorizationRequest: false });
        }
        const createAuthorization = (agentCode) => {
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),                
                platform: WEB_BROWSER_VERSION,
                system: this.props.sourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),
                accessToken: this.props.apiToken
            }

            let reqNo = genRequestId();
            this.setState({ requestId: reqNo });

            let data = {
                requestId: reqNo,
                customerId: this.props.clientId,
                authorizadCode: agentCode,
                processType: this.props.processType,
                link: "",
                status: 'Request',
                role: this.props.systemGroupPermission?.[0]?.Role
            }

            let request = buildMicroRequest(metadata, data);
            console.log(JSON.stringify(request));

            //test
            from = getUrlParameter("fromApp");
            let paramJson = {
                sourceSystem: "DConnect",
                group: "PO",
                platform: from ? from : "Web",
                poClientId: this.props.clientId,
                agentCode: this.props.agentCode,
                agentName: this.props.agentName,
                agentPhone: this.props.agentPhone,
                deviceId: getDeviceId(),
                processType: this.props.processType,
                authorizedId: '01eadddddddddd'
            }
            //rsa
            let paramData = JSON.stringify(paramJson);
            let pass = genPassword(32);
            let ensData = AES256.encrypt(paramData, pass);
            const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
            const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
                md: NodeForge.md.sha256.create(),
                mgf1: {
                    md: NodeForge.md.sha1.create()
                }
            });
            const encryptedKey = NodeForge.util.encode64(encryptedBytes);
            //end rsa
            let link = '/dconnect-sdk/?key=' + encryptedKey + '&Data=' + ensData;
            console.log("link=", link);
            //end test

            authorizationRecordInfor(request).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    this.setState({ authorizedId: Res.data?.[0]?.authorizedId });
                    from = getUrlParameter("fromApp");
                    let paramJson = {
                        sourceSystem: "DConnect",
                        group: "PO",
                        platform: from ? from : "Web",
                        poClientId: this.props.customerId,
                        agentCode: this.props.agentCode,
                        agentName: this.props.agentName,
                        agentPhone: this.props.agentPhone,
                        deviceId: getDeviceId(),
                        processType: this.props.processType,
                        authorizedId: Res.data?.[0]?.authorizedId
                    }
                    //rsa
                    let paramData = JSON.stringify(paramJson);
                    let pass = genPassword(32);
                    let ensData = AES256.encrypt(paramData, pass);
                    const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
                    const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
                        md: NodeForge.md.sha256.create(),
                        mgf1: {
                            md: NodeForge.md.sha1.create()
                        }
                    });
                    const encryptedKey = NodeForge.util.encode64(encryptedBytes);
                    //end rsa
                    let link = '/?key=' + encryptedKey + '&Data=' + ensData;
                    console.log("link=", link);


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

        const authorizePO = (event) => {
            // event.preventDefault();
            let data = this.props.dataDescrypt;
            if (data && !data?.poClientId) {
                data.poClientId = this.props.clientId;
                //rsa
                let paramData = JSON.stringify(data);
                let pass = genPassword(32);
                let ensData = AES256.encrypt(paramData, pass);
                const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
                const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
                    md: NodeForge.md.sha256.create(),
                    mgf1: {
                        md: NodeForge.md.sha1.create()
                    }
                });
                const encryptedKey = NodeForge.util.encode64(encryptedBytes);
                //end rsa
                let jsonData = {
                    data: ensData
                }

                if (data.deviceId) {
                    setSession(DEVICE_ID, data.deviceId);
                }

                getPermissionByGroupAndProccessType(jsonData, encryptedKey).then(Resp => {
                    let Response1 = Resp.Response;
                    console.log('Response1=', Response1);
                    if ((Response1.Result === 'true') && (Response1.ErrLog === 'SUCCESSFUL') && Response1.SystemGroupPermission) {
                        // const initState = Response1.SystemGroupPermission?.[0]?.StepOrder;
                        //Gen API token
                        if (!data?.apiToken) {
                            getTokenPO(jsonData, encryptedKey).then(Res => {
                                let Response = Res.Response;
                                if (Response.Result === 'true' && Response.ErrLog === 'Successful') {
                                    let token = Response.NewAPIToken;
                                    console.log('gen success=' + token);
                                    // this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, data.poClientId, token);
                                    // trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                                    this.setState({ apiToken: token });
                                    setSession(FROM_SDK, FROM_SDK);
                                    setSession(ACCESS_TOKEN, Response.NewAPIToken);
                                    let profileRequest = {
                                        jsonDataInput: {
                                            Company: COMPANY_KEY,
                                            Action: 'GetClientProfile',
                                            APIToken: token,
                                            Authentication: AUTHENTICATION,
                                            DeviceId: data.deviceId,
                                            UserLogin: data.poClientId,
                                            ClientID: data.poClientId,
                                            OS: getOSAndBrowserInfo(),
                                            Project: 'mcp',
                                        }
                                    };
                                    console.log('aaaa=', profileRequest);
                                    checkAccount(profileRequest).then(response => {
                                        if (response.Response.Result === 'true' && response.Response.ErrLog === 'Successfull' && response.Response.ClientProfile) {
                                            //clear danh sach hop dong cua user truoc neu dang login
                                            if (getSession(POL_LIST_CLIENT)) {
                                                sessionStorage.removeItem(POL_LIST_CLIENT);
                                            }
                                            setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
                                            //setSession(USER_LOGIN, UserLogin);
                                            setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix impact for rewamp username
                                            // setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
                                            setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
                                            setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
                                            setSession(GENDER, response.Response.ClientProfile[0].Gender);
                                            setSession(ADDRESS, response.Response.ClientProfile[0].Address);
                                            setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
                                            setSession(EMAIL, response.Response.ClientProfile[0].Email);
                                            setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
                                            setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
                                            setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
                                            const isPhoneVerified = response.Response.ClientProfile[0]?.VerifyCellPhone === '1';
                                            const isEmailVerified = response.Response.ClientProfile[0]?.VerifyEmail === '1';
                                            const hasCellPhone = response.Response.ClientProfile[0].CellPhone;
                                            const hasEmail = response.Response.ClientProfile[0].Email;
                                            // setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
                                            // setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
                                            if (response.Response.ClientProfile[0].POID) {
                                                setSession(POID, response.Response.ClientProfile[0].POID);
                                            }
                                            if (response.Response.ClientProfile[0].DOB) {
                                                setSession(DOB, response.Response.ClientProfile[0].DOB);
                                            }
                                            if (response.Response.ClientProfile[0].DCID) {
                                                setSession(DCID, response.Response.ClientProfile[0].DCID);
                                            }
                                            this.props.handleSetSystemGroupPermission(Response1.SystemGroupPermission);
                                            // this.setState({ systemGroupPermission: Response1.SystemGroupPermission });
                                            // this.loadState(data.poClientId, data.deviceId, token, isPhoneVerified, isEmailVerified, hasCellPhone, hasEmail);
                                            createClaimRequest(token, Response1.SystemGroupPermission);
                                        }
                                    }).catch(error => {
                                        console.log(error);
                                    });
                                } else {
                                    console.log('Gen token error');
                                }
                            }).catch(error => {
                                console.log(error);
                            });
                        } else {
                            this.props.handleSetSystemGroupPermission(Response1.SystemGroupPermission);
                            // this.setState({ systemGroupPermission: Response1.SystemGroupPermission, currentState: initState });
                        }

                    }
                }).catch(error => {
                    console.log(error);
                });
            }
        }

        const createClaimRequest = (token, systemGroupPermission) => {
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),  
                platform: WEB_BROWSER_VERSION,
                system: this.props.sourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),
                accessToken: token
            }
            let link = '';
            let data = {
                sourceSystem: systemGroupPermission?.[0]?.SourceSystem,
                requestId: '',
                customerId: this.props.clientId,
                customerName: getSession(FULL_NAME),
                agentCode: this.props?.agentCode,
                agentName: this.props?.agentName,
                processType: this.props.processType,
                status: 'REQUEST',
                link: link,
                lockingBy: 'Agent',
                jsonState: ''
            }

            let request = buildMicroRequest(metadata, data);

            requestClaimRecordInfor(request).then(Res => {
                if (Res.code === CODE_SUCCESS && Res.data) {
                    // this.setState({requestId: Res.data?.[0]?.requestId});
                    this.props.handlerResetState(this.props.clientId, getDeviceId(), token, systemGroupPermission);
                    // this.handleLoadState(this.props.clientId, getDeviceId(), token);
                    this.props.handleSetClaimRequestId(Res.data?.[0]?.requestId);
                    this.props.handleGoNextStep();
                    // getPolList (this.props.clientId, getDeviceId(), token);
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

        const getPolList = (clientId, deviceId, apiToken) => {
            let noPhone = getSession(CELL_PHONE) ? false : true;
            let noEmail = getSession(EMAIL) ? false : true;
            let noVerifyPhone = (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) === '1')) ? false : true;
            let noVerifyEmail = (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) === '1')) ? false : true;

            if (!getSession(POL_LIST_CLIENT4CLAIM)) {
                let apiRequest = {
                    jsonDataInput: {
                        Company: COMPANY_KEY,
                        Authentication: AUTHENTICATION,
                        Action: 'GetPolicy4Claim',
                        DeviceId: deviceId,
                        APIToken: apiToken,
                        Project: 'mcp',
                        ClientID: clientId,
                        UserLogin: clientId
                    }
                };
                CPGetPolicyListByCLIID(apiRequest).then(Res => {
                    let Response = Res.Response;
                    if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {


                        let noValidPol = true;
                        let agentConfirm = false;
                        let profile = Response.ClientProfile;
                        if (profile && profile.length > 0) {
                            noValidPol = false;
                            agentConfirm = true;
                        }
                        this.setState({
                            ClientProfile: profile,
                            noValidPolicy: noValidPol,
                            noPhone: noPhone,
                            noEmail: noEmail,
                            noVerifyPhone: noVerifyPhone,
                            noVerifyEmail: noVerifyEmail,

                            // showConfirm: agentConfirm
                        });
                        setSession(POL_LIST_CLIENT4CLAIM, JSON.stringify(Response.ClientProfile));
                    } else if (Response.ErrLog === 'EMPTY') {
                        this.setState({
                            noValidPolicy: true,
                            noPhone: noPhone,
                            noEmail: noEmail,
                            noVerifyPhone: noVerifyPhone,
                            noVerifyEmail: noVerifyEmail,
                            // showConfirm: false
                        });
                    } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                        showMessage(EXPIRED_MESSAGE);
                        logoutSession();
                        // this.props.history.push({
                        //     pathname: '/home', state: {authenticated: false, hideMain: false}

                        // })

                    }
                }).catch(error => {
                    // alert(error);
                });
            } else {
                let noValidPol = true;
                let agentConfirm = false;
                let profile = JSON.parse(getSession(POL_LIST_CLIENT4CLAIM));
                if (profile && profile.length > 0) {
                    noValidPol = false;
                    agentConfirm = true;
                }
                this.setState({
                    ClientProfile: profile,
                    noValidPolicy: noValidPol,
                    noPhone: noPhone,
                    noEmail: noEmail,
                    noVerifyPhone: noVerifyPhone,
                    noVerifyEmail: noVerifyEmail,
                    // showConfirm: agentConfirm
                });
            }
            this.props.setCurrentState(this.props.SystemGroupPermission?.[1].StepOrder);
            // this.cpSaveLog("Danh sách hợp đồng");

        }

        const setShowConfirm = (value) => {
            this.setState({ showConfirm: value });
        }

        const cancelConfirm = () => {
            this.setState({ showConfirm: false });
            let data = this.props.dataDescrypt;
            if (data && data?.platform === 'Web') {
                window.location.href = '/';
            } else if (data) {
                let obj = {
                    Action: "CANCEL_ND13_" + data?.proccessType,
                    ClientID: this.props.clientId
                    // PolicyNo: this.props.polID,
                    // TrackingID: this.state.trackingId
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

        return (
            <>
                <main className={this.props.cssSystem}>
                    <div className='dconnect-sdk'>
                        <div className="breadcrums">
                            <div className="breadcrums__item">
                                <p>Yêu cầu quyền lợi</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </div>
                            <div className="breadcrums__item">
                                <p>Tạo mới yêu cầu</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </div>

                        </div>
                        <div style={{ fontSize: '14px', display: 'flex' }}>
                            <button class="back-circle-bg" tabindex="0" type="button" style={{ borderRadius: '24px' }}>
                                <span>
                                    <svg class="back-arrow" focusable="false" viewBox="0 0 24 24" aria-hidden="true">
                                        <path d="M21 11H6.83l3.58-3.59L9 6l-6 6 6 6 1.41-1.41L6.83 13H21z"></path>
                                    </svg>
                                </span>
                                <span></span>
                            </button>
                            &emsp; <b style={{ marginTop: '16px' }}>Chi tiết thông tin khách hàng</b>
                        </div>
                        <div className='client-border'>
                            <div className='client-info'>
                                <p className='basic-red'>Trịnh Văn Hiếu</p>
                                <div style={{ display: 'flex', alignItems: 'center', minWidth: '225px', fontSize: '14px' }}>
                                    <div>Hiếu chân dài</div>
                                    <img src="https://localhost:3000/img/icon/iconEdit.svg"></img>
                                </div>
                                <p class='basic-red'>Điểm thưởng: 20.0</p>
                            </div>
                            <div class="rect-dropdown">
                                <div class="client-dropdown Mui-expanded" tabindex="0" role="button" aria-disabled="false" aria-expanded="true">
                                    <div class="padding8 Mui-expanded"><span style={{ fontFamily: 'Helvetica Bold' }}>Thông tin khách hàng</span></div>
                                    <div class="client-dropdown MuiIconButton-root-1062 client-expand Mui-expanded MuiIconButton-edgeEnd-1064" aria-disabled="false" aria-hidden="true">
                                        <span class="MuiIconButton-label-1069">
                                            <svg class="MuiSvgIcon-root MuiSvgIcon-fontSizeMedium jss2216 css-vubbuv" focusable="false" viewBox="0 0 24 24" aria-hidden="true" data-testid="ExpandLessIcon">
                                                <path d="m12 8-6 6 1.41 1.41L12 10.83l4.59 4.58L18 14z"></path>
                                            </svg>
                                        </span>
                                        <span class="MuiTouchRipple-root-1602"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="btn-wrapper" style={{ display: 'flex', justifyContent: 'center' }}>
                            <button className="btn btn-primary" id="btn-user-agree"
                                onClick={() => setShowConfirm(true)}>Tạo yêu cầu quyền lợi</button>


                        </div>
                    </div>
                </main>

                {this.state.showAuthorizationRequest &&
                    <AlertMsgTitlePopup closePopup={() => closeShowAuthorizationRequest()} go={() => createAuthorization(this.props.agentCode)}
                        title={'Yêu cầu BMBH ủy quyền thực hiện yêu cầu'} imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'}
                        msg={'<p>Vui lòng gửi yêu cầu ủy quyền đến BMBH để tiếp tục thực hiện tạo yêu cầu</p>'}
                        agreeText='Gửi yêu cầu' notAgreeText='Thoát' notAgreeFunc={() => closeShowAuthorizationRequest()} />}

                {this.state.showConfirm &&
                    <AgreeCancelPopup closePopup={() => setShowConfirm(false)} agreeFunc={(event) => authorizePO(event)}
                    title={'Cam kết của Đại lý bảo hiểm'}    
                    msg={'<p>Tôi hiểu rằng tôi đang thực hiện khai báo thông tin Yêu cầu giải quyết quyền lợi bảo hiểm thay cho Khách hàng của tôi.</p><p>Tôi xác nhận yêu cầu quyền lợi bảo hiểm này đã được thực hiện theo yêu cầu và đồng ý của BMBH/NĐBH, đồng thời tôi cam kết bảo mật tất cả thông tin của Khách hàng trong yêu cầu này.</p>'}
                        imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} agreeText='Xác nhận' notAgreeText='Thoát' notAgreeFunc={() => cancelConfirm()} />}

            </>
        )
    }
}

export default RequestAuthorization;