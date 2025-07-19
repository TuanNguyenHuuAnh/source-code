import React, { Component } from 'react';
import Pagination from '../../../Paging';
import {
    ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, COMPANY_KEY, AUTHENTICATION, POL_LIST_CLIENT, POL_EXPIRE_DATE, POL_LI_NAME, POL_POLICY_STATUS, POL_CLASS_CD, EMAIL, VERIFY_EMAIL, POL_LIST_CLIENT4CLAIM, CELL_PHONE, SCREENS, VERIFY_CELL_PHONE, WEB_BROWSER_VERSION, CODE_SUCCESS, FE_BASE_URL, PUBLIC_KEY_DLVN,
    DCID,
    FULL_NAME,
    GENDER,
    TWOFA,
    DOB,
    DEVICE_ID,
    ADDRESS,
    IS_MOBILE,
    POID,
    FROM_SDK, COMPANY_KEY3,
    SYSTEM_DCONNECT,
    SIGNATURE
} from '../../../sdkConstant';
import { CPGetPolicyListByCLIID, logoutSession, getCustomerListByAgentCode, requestClaimRecordInfor, getPermissionByGroupAndProccessType, getTokenPO, getCustomerProfile, getMircoToken } from '../../../sdkAPI';
import LoadingIndicator from '../../../LoadingIndicator2';
import { showMessage, setSession, getSession, getDeviceId, buildMicroRequest, getOperatingSystem, removeAccents, genPassword, clearSession, getOSVersion, getUrlParameter } from '../../../sdkCommon';
import AlertMsgTitlePopup from '../../../components/AlertMsgTitlePopup';
import AuthenticationPopupClaim from '../../../components/AuthenticationPopupClaim';
import AlertPopupClaim from '../../../components/AlertPopupClaim';
import AlertPopupHight from '../../../components/AlertPopupHight';
import AgreeCancelPopup from '../../../components/AgreeCancelPopup';
import parse from 'html-react-parser';
import RequestAuthorization from '../SDKRequestClaimAuthorization/RequestAuthorization';
import { isEmpty, cloneDeep } from "lodash";
import AES256 from 'aes-everywhere';
import NodeForge from 'node-forge';

const PAGE_SIZE = 10;
class CreateRequest extends Component {
    constructor(props) {
        super(props)
        this.state = {
            pageOfItems: [],
            searchInput: '',
            clientProfileSearch: [],
            loading: false,
            enable: false,
            showConfirm: false,
            askCreateAuthorization: false,
            noValidPolicy: false,
            noPhone: false,
            noEmail: false,
            noVerifyPhone: false,
            noVerifyEmail: false,
            requestId: '',
            showAuthorizationRequest: false,
            data: null,
            customerId: '',
            dataDescrypt: null,
            searched: false
        };
        this.onChangePageCom = this.onChangePageCom.bind(this);
        this.handlerOnChangeSearchInput = this.onChangeSearchInput.bind(this);
        this.handleSearch = this.search.bind(this);
        this.handleGetPolList = this.getPolList.bind(this);
    }

    onChangePageCom(pageOfItems) {
        // update state with new page of items
        this.setState({ pageOfItems: pageOfItems });
    }

    onChangeSearchInput(event) {
        this.setState({ searchInput: event.target.value, enable: false });
    }

    getPolList(clientId, deviceId, apiToken) {
        let novalid = false;
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
                        if ((this.props.systemGroupPermission?.[0]?.Role === 'PO') && this.props.systemGroupPermission?.[0]?.AssignTriggerOn) {
                            this.setState({ askCreateAuthorization: true });
                            agentConfirm = false;
                        }
                    }
                    novalid = noValidPol;
                    if (novalid) {
                        this.props.setNoValidPolicy(novalid);
                    } else {
                        this.setState({
                            ClientProfile: profile,
                            noValidPolicy: noValidPol,
                            showConfirm: agentConfirm
                        });
                    }

                    setSession(POL_LIST_CLIENT4CLAIM, JSON.stringify(Response.ClientProfile));
                } else if (Response.ErrLog === 'EMPTY') {
                    novalid = true;
                    this.props.setNoValidPolicy(novalid);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                }
                if (!novalid) {
                    this.setState({ askCreateAuthorization: true });
                }
            }).catch(error => {
                console.log(error);
            });
        } else {
            let noValidPol = true;
            let agentConfirm = false;
            let profile = [];
            if (getSession(POL_LIST_CLIENT4CLAIM) && getSession(POL_LIST_CLIENT4CLAIM) !== 'undefined') {
                profile=JSON.parse(getSession(POL_LIST_CLIENT4CLAIM));
            }
            
            if (profile && profile.length > 0) {
                noValidPol = false;
                agentConfirm = true;
                if ((this.props.systemGroupPermission?.[0]?.Role === 'PO') && this.props.systemGroupPermission?.[0]?.AssignTriggerOn) {
                    this.setState({ askCreateAuthorization: true });
                    agentConfirm = false;
                }
            }
            novalid = noValidPol;
            if (novalid) {
                this.props.setNoValidPolicy(novalid);
            } else {
                this.setState({
                    ClientProfile: profile,
                    noValidPolicy: noValidPol,
                    showConfirm: agentConfirm
                });
            }
            if (!novalid) {
                this.setState({ askCreateAuthorization: true });
            }
        }

        
        // this.cpSaveLog("Danh sách hợp đồng");

    }

    componentDidMount() {
        if ((this.props.systemGroupPermission?.[0]?.Role === 'PO') && this.props.systemGroupPermission?.[0]?.AssignTriggerOn) {//Và cần chỉnh thêm code check là chưa ủy quyền
            // this.setState({ askCreateAuthorization: true });
            if (this.props.clientId && this.props.apiToken) {
                this.getPolList(this.props.clientId, getDeviceId(), this.props.apiToken);
            }
        } else if (this.props.systemGroupPermission?.[0]?.Role === 'PO') {
            this.selfKeyIn();
        } else {
            if (this.props.clientId || getSession(ACCESS_TOKEN)) {
                this.props.handleLoadMaster(this.props.clientId, getDeviceId(), this.props.apiToken, this.props.systemGroupPermission);
                this.props.handleGoNextStep();
                // this.createClaimRequest(this.props.apiToken, this.props.systemGroupPermission, this.props.agentCode, this.props.agentName)
            } else {
                //Get customer list by agentCode
                let data = this.props.data;
                if (this.props.paramKey) {
                    data = this.props.appData;
                }
                this.getTokenForAgent(data);
            }

        }



    }

    getCustomerList = (token) => {
        this.setState({ loading: true });
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: (this.props.sourceSystem ? this.props.sourceSystem : this.props.dataDescrypt?.sourceSystem) || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE),
            accessToken: token
        }

        let data = {
            agentCode: this.props.agentCode ? this.props.agentCode : this.props.dataDescrypt?.agentCode,
            customerId: this.props.agentCode ? this.props.agentCode : this.props.dataDescrypt?.agentCode
            // page: 0,
            // pageSize: 10
        }
        let request = buildMicroRequest(metadata, data);
        getCustomerListByAgentCode(request).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                console.log('resss=', Res.data);
                let profile = Res.data;
                if (profile.length > PAGE_SIZE) {
                    profile = profile.slice(0, PAGE_SIZE);
                }
                this.setState({ data: Res.data });
            }
            this.setState({ loading: false });
            // else if (Res.code === CODE_ERROR) {
            // } else if (Res.code === CODE_EXPIRED_TOKEN) {
            //     showMessage(EXPIRED_MESSAGE);
            //     logoutSession();
            // }
        }).catch(error => {
            console.log(error);
            this.setState({ loading: false });
        });
    }

    getTokenForAgent = (data) => {
        let dycryptData = JSON.parse(AES256.decrypt(data, COMPANY_KEY3));
        dycryptData.poClientId = dycryptData.agentCode;
        //rsa
        let paramData = JSON.stringify(dycryptData);
        let pass = genPassword(32);
        let ensData = AES256.encrypt(paramData, pass);
        const publicKey = NodeForge.pki.publicKeyFromPem(PUBLIC_KEY_DLVN);
        const encryptedBytes = publicKey.encrypt(pass, 'RSA-OAEP', {
            md: NodeForge.md.sha256.create(),
            mgf1: {
                md: NodeForge.md.sha1.create()
            }
        });
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        if (data.deviceId) {
            setSession(DEVICE_ID, data.deviceId);
        }
        //Gen API token
        getMircoToken(jsonData, encryptedText).then(Res => {
            if (Res.code === CODE_SUCCESS && Res.data) {
                let token = Res?.data?.newAccessToken;
                this.props.updateApiToken(token);
                this.props.updateSignature(dycryptData.agentCode);
                setSession(ACCESS_TOKEN, token);
                setSession(SIGNATURE, dycryptData.agentCode);
                this.getCustomerList(token);
            }

        }).catch(error => {
            console.log(error);
        });

    }
    
    search(event) {
        event.preventDefault();

        if (this.state.loading || isEmpty(this.state.data)) return;
        let clientProfile = [];
        if (!this.state.searchInput) {
            // return;
            // let profile = this.state.data;
            // if (profile.length > PAGE_SIZE) {
            //     profile = profile.slice(0, PAGE_SIZE);
            // }
            this.setState({ clientProfileSearch: [], pageOfItems: [] });
        } else {
            clientProfile = this.state.data.filter(item => {
                return (item?.customerId?.indexOf(this.state.searchInput) >= 0) || (removeAccents(item?.customerName?.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(this.state.searchInput?.toLowerCase().replace(/  +/g, ' '))) >= 0) || (item?.email && (removeAccents(item?.email?.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(this.state.searchInput?.toLowerCase().replace(/  +/g, ' '))) >= 0)) || (item?.phone && (item?.phone?.indexOf(this.state.searchInput) >= 0));
            });
            let profile = clientProfile;
            if (profile.length > PAGE_SIZE) {
                profile = profile.slice(0, PAGE_SIZE);
            }
            this.setState({ clientProfileSearch: clientProfile, pageOfItems: profile, searched: true });
        }
    }

    createClaimRequest = (token, systemGroupPermission, agentCode, agentName) => {
        let metadata = {
            clientKey: AUTHENTICATION,
            deviceId: getDeviceId(),
            operationSystem: getOperatingSystem(),
            operatingSystem: getOperatingSystem(),
            operatingSystemVersion: getOSVersion(),
            platform: WEB_BROWSER_VERSION,
            system: systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
            signature: getSession(SIGNATURE),
            accessToken: token
        }
        let link = '';
        let data = {
            sourceSystem: systemGroupPermission?.[0]?.SourceSystem, 
            requestId: '',
            customerId: this.state.customerId,
            customerName: getSession(FULL_NAME),
            agentCode: agentCode,
            agentName: agentName,
            agentPhone: this.props.agentPhone,
            lifeInsuredName: this.props?.selectedCliInfo?.fullName ? this.props?.selectedCliInfo?.fullName : '',
            lifeInsuredId: this.props?.selectedCliInfo?.clientId ? this.props?.selectedCliInfo?.clientId : '',
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
                this.props.handleLoadMaster(this.props.clientId, getDeviceId(), token, systemGroupPermission);
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

    selfKeyIn = () => {
        if (!getUrlParameter("fromApp")) {
            this.props.handlerResetState(getSession(CLIENT_ID), getDeviceId(), getSession(ACCESS_TOKEN), this.props.systemGroupPermission);
        }
        this.setState({ askCreateAuthorization: false });
        this.props.handleSetSelfKeyInConfirm(true);
        this.props.handlerGoToStep(this.props.currentState + 2);
    }

    render() {
        console.log('this.state.noValidPolicy=', this.state.noValidPolicy);
        var logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }
        const cacheCurrentPol = (polExpireDate, policyLIName, policyStatus, PolicyClassCD) => {
            setSession(POL_EXPIRE_DATE, polExpireDate);
            setSession(POL_LI_NAME, policyLIName);
            setSession(POL_POLICY_STATUS, policyStatus);
            setSession(POL_CLASS_CD, PolicyClassCD);
        }
        const clearSearch = () => {
            let pageOfItem = this.state.data;
            this.setState({ clientProfileSearch: this.state.data, pageOfItems: this.state.data, searchInput: '', enable: false });
        }

        const setShowConfirm = (value) => {
            this.setState({ showConfirm: value });
            if (!value) {
                this.props.closeToHome();
            }
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

        const authorizePO = (customerId, fullName) => {
            clearSession();
            this.props.clearState();
            this.props.handlerSetClientId(customerId);
            this.props.handlerSetClientFullName(fullName);
            // event.preventDefault();
            this.setState({customerId: customerId});
            this.props.updateAgentConfirmed(true);
            let data = cloneDeep(this.props.dataDescrypt);
            if (data && !data?.poClientId) {
                data.poClientId = customerId;
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
                            getMircoToken(jsonData, encryptedKey).then(Res => {
                                if (Res.code === CODE_SUCCESS && Res.data) {
                                    console.log('resss=', Res.data);
                                    let token = Res?.data?.newAccessToken;
                                    console.log('gen success=' + token);
                                    // this.cpSaveLogPartner(`Web_Open_${PageScreen.E_CLAIM_SDK}`, data.poClientId, token);
                                    // trackingEvent("Tạo mới yêu cầu ", `Web_Open_${PageScreen.E_CLAIM_SDK}`, `Web_Open_${PageScreen.E_CLAIM_SDK}`);
                                    this.setState({ apiToken: token });
                                    this.props.updateApiToken(token);
                                    this.props.updateSignature(data?.poClientId);
                                    setSession(ACCESS_TOKEN, token);
                                    setSession(SIGNATURE, data?.poClientId);
                                    setSession(FROM_SDK, FROM_SDK);
                                    getClientProfile(data.poClientId, token, Response1.SystemGroupPermission, data?.agentCode, data?.agentName);
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

        const getClientProfile = (customerId, token, systemGroupPermission, agentCode, agentName) => {
            let metadata = {
                clientKey: AUTHENTICATION,
                deviceId: getDeviceId(),
                operationSystem: getOperatingSystem(),
                operatingSystem: getOperatingSystem(),
                operatingSystemVersion: getOSVersion(),                
                platform: WEB_BROWSER_VERSION,
                system: systemGroupPermission?.[0]?.SourceSystem || SYSTEM_DCONNECT,
                signature: getSession(SIGNATURE),//temp use. This is key rsa
                accessToken: token
            }
            let data = {
                customerId: customerId,
            }

            let request = buildMicroRequest(metadata, data);

            getCustomerProfile(request).then(Res => {
                console.log('getCustomerProfile=', Res.data);
                if (Res.code === CODE_SUCCESS && Res.data) {
                    {
                        //clear danh sach hop dong cua user truoc neu dang login
                        if (getSession(POL_LIST_CLIENT)) {
                            sessionStorage.removeItem(POL_LIST_CLIENT);
                        }
                        let item = Res.data?.[0];
                        setSession(CLIENT_ID, item?.customerId);
                        setSession(USER_LOGIN, item?.customerId);
                        setSession(CELL_PHONE, item?.phone?item?.phone: '');
                        setSession(FULL_NAME, item?.customerName);
                        setSession(GENDER, item?.gender);
                        setSession(ADDRESS, item?.address);
                        if (item?.email) {
                            setSession(EMAIL, item?.email);
                        }
                        if (item?.verifyPhone) {
                            setSession(VERIFY_CELL_PHONE, item?.verifyPhone);
                        }
                        if (item?.verifyEmail) {
                            setSession(VERIFY_EMAIL, item?.verifyEmail);
                        }
                        if (item?._2fa) {
                            setSession(TWOFA, item?._2fa);
                        }
                        if (item?.idNum) {
                            setSession(POID, item?.idNum);
                            this.props.updateIdNum(item?.idNum);
                        }
                        if (item?.dob) {
                            setSession(DOB, item?.dob);
                        }
                        if (item?.dcid) {
                            setSession(DCID, item?.dcid);
                        }
                        this.props.handleSetSystemGroupPermission(systemGroupPermission);
                        this.props.handleLoadMaster(this.props.clientId, getDeviceId(), token, systemGroupPermission, true);
                        // this.props.handleGoNextStep();
                    }

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

        const closeCreateAuthorization = () => {
            this.setState({ askCreateAuthorization: false });
            this.props.closeToHome();
        }

        const CreateAuthorized = () => {
            this.setState({ askCreateAuthorization: false });
            if (!this.props.selfKeyInConfirm) {
                this.props.handlerGoToStep(this.props.currentState + 1);
            } else {
                this.props.handlerGoToStep(this.props.currentState + 2);
            }

        }

        const selfKeyIn = () => {
            this.props.handlerResetState(getSession(CLIENT_ID), getDeviceId(), getSession(ACCESS_TOKEN), this.props.systemGroupPermission);
            this.setState({ askCreateAuthorization: false });
            this.props.handleSetSelfKeyInConfirm(true);
            this.props.handlerGoToStep(this.props.currentState + 2);
        }

        const closeNoValidPolicy = () => {
            this.setState({ noValidPolicy: false });
            this.props.closeToHome();
        }

        const closePhoneEmailPopup = () => {
            this.setState({ noPhone: false, noEmail: false, noVerifyPhone: false, noVerifyEmail: false });
        }

        const openConfirm = (customerId) => {
            clearSession();
            this.props.clearState();
            this.setState({ showConfirm: true, customerId: customerId });
            this.props.handlerSetClientId(customerId);
        }
        // if (!getSession(CLIENT_ID)) {
        //     return <Redirect to={{
        //         pathname: '/home'
        //     }} />;
        // }
        return (
            <>
                {this.props.systemGroupPermission?.[0]?.Role === 'PO' ? (
                    <main className={getSession(ACCESS_TOKEN) && !getUrlParameter("fromApp")?'logined': (getUrlParameter("fromApp")?'margin-top0':'')}>
                        <div><LoadingIndicator area="submit-init-claim"/></div>

                        {getUrlParameter("fromApp") ? (
                            <div className='app-header-back'>
                                <i className='margin-left8' onClick={() => this.props.closeToHome()}><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                                <p className='viewer-back-title'>Yêu cầu ĐLBH hỗ trợ</p>
                            </div>
                        ) : (
                            <div className='main-warpper basic-mainflex e-claim'>
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
                            </div>
                        )
                        }
                        {this.state.askCreateAuthorization &&
                            <AlertMsgTitlePopup closePopup={() => closeCreateAuthorization()} go={() => selfKeyIn()}
                                imgPath={FE_BASE_URL + '/img/popup/uyquyen.svg'}
                                msg={'<p>Quý khách bấm “Tiếp tục” để tạo Yêu cầu, hoặc có chọn “Yêu cầu ĐLBH hỗ trợ” để yêu cầu Đại Lý bảo hiểm làm thay Yêu cầu quyền lợi bảo hiểm.</p>'}
                                agreeText='Tiếp tục' notAgreeText='Yêu cầu ĐLBH hỗ trợ' notAgreeFunc={() => CreateAuthorized()} />}
                    </main>

                ) : (
                    // this.state.showDetail ? (
                    //     <RequestAuthorization clientId={this.state.customerId} agentCode={this.props.agentCode} agentName={this.props.agentName} dataDescrypt={this.props.dataDescrypt} data={this.props.data} handleSetClaimRequestId={this.props.handleSetClaimRequestId} systemGroupPermission={this.props.systemGroupPermission} setCurrentState={this.props.setCurrentState} handlerResetState={this.props.handlerResetState} handleSetSystemGroupPermission={this.props.handleSetSystemGroupPermission} handleGoNextStep={this.props.handleGoNextStep} />
                    // ) : (
                        <main className={getUrlParameter("fromApp")?this.props.cssSystem + ' padding-top0': this.props.cssSystem}>
                            {/* <div><LoadingIndicator area="submit-init-claim"/></div> */}
                            <div className='dconnect-sdk'>
                                {getUrlParameter("fromApp") && (this.props.cssSystem !== 'dconnect') && (
                                    <div className='agent-back'>
                                    <i onClick={() => this.props.closeToHome()}><img src={`${FE_BASE_URL}/img/btn-back-agent.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                                    <p className='viewer-back-title'>Trở về danh sách</p>
                                    </div>
                                )}
                                <h4 className='com-title' style={{fontWeight: '500', fontSize: '13px', marginTop: '12px'}}>Tổng số khách hàng: {this.state.clientProfileSearch.length}</h4>

                                <form onSubmit={this.handleSearch}>
                                    <section className='com-search' style={{ paddingRight: '8px' }}>
                                        <div className="common-searchbar">
                                            <div className="search-bar">
                                                <div className="input">
                                                    <div className="input__content">
                                                        <input style={{ fontFamily: 'Inter', fontWeight: '500', fontSize: '16px' }} placeholder="Tìm kiếm" type="search" maxLength="200"
                                                            value={this.state.searchInput} onChange={(event) => this.handlerOnChangeSearchInput(event)} />
                                                    </div>
                                                    <i className="icon" onClick={this.handleSearch} style={{ cursor: 'pointer' }}><img src={FE_BASE_URL + "/img/icon/Search.svg"} alt="search" /></i>
                                                </div>

                                            </div>
                                        </div>
                                    </section>
                                </form>
                                <div><LoadingIndicator area="submit-init-claim"/></div>
                                <section className='card-claim-section'>
                                    <div>
                                        {!this.state.loading && isEmpty(this.state.pageOfItems) && this.state.searched? (
                                            <div className="insurance" style={{ marginTop: '-33px', paddingLeft: '0px', marginLeft: '-6px' }}>
                                                <div className="empty">
                                                    <div className="icon">
                                                        <img src={FE_BASE_URL + "/img/empty-result.svg"} alt="no-data" />
                                                    </div>
                                                    <p style={{ paddingTop: '20px', fontSize: '13px' }}>Chưa tìm thấy kết quả phù hợp.<br /> Vui lòng thử lại từ khoá khác!</p>
                                                </div>
                                            </div>
                                        ) : (

                                            <div class="card-claim-container">
                                                {this.state.pageOfItems && this.state.pageOfItems?.map((item, index) => (
                                                    <div class="card-claim" onClick={() => authorizePO(item?.customerId, item?.customerName)}>
                                                        <div class="row">
                                                            <div class="label">Tên BMBH:</div>
                                                            <div class="value basic-semibold">{item?.customerName}</div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="label">Mã số BMBH:</div>
                                                            <div class="value basic-semibold">{item?.customerId}</div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="label">Email:</div>
                                                            <div class="value basic-semibold">{item?.email}</div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="label">Số điện thoại:</div>
                                                            <div class="value basic-semibold">{item?.phone}</div>
                                                        </div>
                                                    </div>
                                                ))}

                                            </div>
                                        )

                                        }

                                    </div>
                                    <div className="paging-container" id="paging-container-com-id">
                                        {this.state.clientProfileSearch !== null && (
                                            <Pagination items={this.state.clientProfileSearch} onChangePage={this.onChangePageCom} pageSize={8} />
                                        )}
                                    </div>
                                </section>
                            </div>
                        {this.state.noPhone &&
                            <AlertPopupClaim closePopup={() => closePhoneEmailPopup()}
                                msg={'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                imgPath={'img/popup/no-phone.svg'} />
                        }
                        {this.state.noEmail &&
                            <AlertPopupClaim closePopup={() => closePhoneEmailPopup()}
                                msg={'Quý khách chưa có địa chỉ Email để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'}
                                imgPath={'img/popup/no-email.svg'} />
                        }
                        {this.state.noVerifyPhone &&
                            <AuthenticationPopupClaim closePopup={() => closePhoneEmailPopup()}
                                msg={'Số điện thoại của Quý khách chưa được xác thực'}
                                screen={SCREENS.CREATE_CLAIM} />
                        }
                        {this.state.noVerifyEmail &&
                            <AuthenticationPopupClaim closePopup={() => closePhoneEmailPopup()}
                                msg={'Email của Quý khách chưa được xác thực'}
                                screen={SCREENS.CREATE_CLAIM} />
                        }

                        {/* {this.state.showConfirm &&
                            <AgreeCancelPopup closePopup={() => setShowConfirm(false)} agreeFunc={(event) => authorizePO(event)}
                                title={'Cam kết của Đại lý bảo hiểm'}
                                msg={'<p>Tôi hiểu rằng tôi đang thực hiện khai báo thông tin Yêu cầu giải quyết quyền lợi bảo hiểm thay cho Khách hàng của tôi.</p><p>Tôi xác nhận yêu cầu quyền lợi bảo hiểm này đã được thực hiện theo yêu cầu và đồng ý của BMBH/NĐBH, đồng thời tôi cam kết bảo mật tất cả thông tin của Khách hàng trong yêu cầu này.</p>'}
                                imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'} agreeText='Xác nhận' notAgreeText='Thoát' notAgreeFunc={() => setShowConfirm(false)} />} */}

                        {this.state.noValidPolicy &&
                            <AlertPopupHight closePopup={() => closeNoValidPolicy()}
                                msg={'Hợp đồng bảo hiểm của Quý Khách đã hết hiệu lực và không còn trong thời gian nộp YCQL'}
                                imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'}
                            />
                        }
                        </main>

                    // )
                )}
            </>
        )
    }
}

export default CreateRequest;