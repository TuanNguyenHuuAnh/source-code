import React, { useState, useEffect } from 'react';
import {
    ACCESS_TOKEN,
    API_BASE_URL,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    COMPANY_KEY2,
    PageScreen,
    USER_LOGIN,
    WEB_BROWSER_VERSION,
    COMPANY_KEY3,
    PUBLIC_KEY_DLVN,
    DEVICE_ID,
    FE_BASE_URL,
    OMIDOCS_TYPE_MAPPING,
    RECORDING_BASE_URL,
    NUM_OF_RETRY_OMI_DOCS,
    E_POLICY_POL_SDK
} from './sdkConstant';
import { CPSaveLog, getEPolicyPdf, getEdocument, getOmiDocs, getRecordingToken, getDocTypePermission, GetDocumentDetails, getIBPSDocumentList } from './sdkAPI';
import { getDeviceId, getSession, trackingEvent, genPassword, setSession, ddMMyyyyToDate } from './sdkCommon';
import LoadingIndicator from './LoadingIndicator2';
import AES256 from 'aes-everywhere';
import b64toBlob from 'b64-to-blob';
import NodeForge from 'node-forge';
import './sdk.css';
import PdfViewer from './PdfViewer';
import AlertPopup from './AlertPopup';

const EDocumentDetailWrapperSingle = (props) => {
    const [state, setState] = useState({
        ruleProfile: null,
        pdfProfile: null,
        policyID: props.policyID,
        isEmpty: false,
        pdfUrl: '',
        DocTypeID: '',
        DocTypeName: '',
        StartPage: '',
        displayPdf: false,
        showRuleDetail: false,
        selectedIndex: 0,
        PDFFile: null,
        loading: false,
        apiToken: props.apiToken,
        deviceId: props.deviceId,
        clientId: props.clientId,
        omiDocProfile: null,
        recordingFile: null,
        docTypePermission: null,
        viewingReadonlyUrlPdf: '',
        mimeType: '',
        loadedOmidocs: false,
        loadedRecording: false,
        ePolicyPdfBase64: null,
        startPage: 1,
        permission: '',
        loadedTypePermission: false,
        loadedPdfProfile: false,
        loadedRuleProfile: false,
        loadedRecordingFile: false,
        loadedOmiDocProfile: false,
        noDocument: false
    });

    function decodeHtmlEntity(str) {
        const txt = document.createElement("textarea");
        txt.innerHTML = str;
        return txt.value;
    }

    useEffect(() => {
        if (props.data) {
            let dParam = decodeHtmlEntity(props.data);
            console.log("props.data=", props.data);
            // dParam = dParam.replace(/ /g, "+");
            let data = JSON.parse(AES256.decrypt(dParam, COMPANY_KEY3));
            console.log("data=", data);
            console.log('policyNo=', data.policyNo);
            if (data.policyNo) {
                // getEpolicyPdfHeaderForPartner(data);
                // cpGetEPolicyRules(data.policyNo);
                setState(prevState => ({ ...prevState, policyID: data.policyNo, viewingReadonlyUrlPdf: '', pdfProfile: null, ruleProfile: null, ePolicyPdfBase64: null, permission: '', showRuleDetail: '', loading: true }));
                getDocTypePermissionProfile(data);
                getRecodingFileAndTokenWithOmidocs(data);
                // getDoc(data);
                
            }
        } else {
            let data = {
                sourceSystem: "DConnect",
                group: "GROUP_EXISTING_CLIENT",
                policyNo: props.policyID,
                proposalNo: "",
                deviceId: state.deviceId?state.deviceId : getDeviceId()
            };
            setState(prevState => ({ ...prevState, policyID: data.policyNo, viewingReadonlyUrlPdf: '', pdfProfile: null, ruleProfile: null, ePolicyPdfBase64: null, permission: '', loading: true }));
            getDocTypePermissionProfile(data);
            getEpolicyPdfHeader(props.policyID);
            cpGetEPolicyRules(props.policyID);
        }
        cpSaveLog(`Web_Open_${PageScreen.E_POLICY_POL_SDK}`);
        trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.E_POLICY_POL_SDK}`, `Web_Open_${PageScreen.E_POLICY_POL_SDK}`);

        return () => {
            cpSaveLog(`Web_Close_${PageScreen.E_POLICY_POL_SDK}`);
            trackingEvent("Thư viện tài liệu", `Web_Close_${PageScreen.E_POLICY_POL_SDK}`, `Web_Close_${PageScreen.E_POLICY_POL_SDK}`);
        };
    }, [props.data, props.policyID]);

    useEffect(() => {
        console.log(state.policyID + '|'+ props.policyID)
        if (state.policyID !== props.policyID) {
            setState(prevState => ({ ...prevState, policyID: props.policyID, viewingReadonlyUrlPdf: '', pdfProfile: null, ruleProfile: null, ePolicyPdfBase64: null, permission: '', showRuleDetail: '', loading: true }));
            if (props.data) {
                let data = JSON.parse(AES256.decrypt(props.data, COMPANY_KEY3));
                console.log("data=", data);
                console.log('policyNo=', data.policyNo);
                if (data.policyNo) {
                    // getEpolicyPdfHeaderForPartner(data);
                    // cpGetEPolicyRules(data.policyNo);
                    getDocTypePermissionProfile(data);
                    getRecodingFileAndTokenWithOmidocs(data);
                }
            } else {
                getEpolicyPdfHeader(props.policyID);
                cpGetEPolicyRules(props.policyID);
            }
        } 

    }, [props.policyID]);

    useEffect(() => {
        // console.log('pdfProfile=,', state.pdfProfile);
        // console.log('omiDocProfile=,', state.omiDocProfile);
        // console.log('recordingFile=,', state.recordingFile);
        // console.log('docTypePermission=,', state.docTypePermission);
        // console.log('state.loadedRecording=', state.loadedRecording);
        // console.log('state.loadedOmidocs=', state.loadedOmidocs);
        // console.log('!state.docTypePermission=', !state.docTypePermission);
        // console.log('state.isEmpty=', state.isEmpty);
    }, [state.pdfProfile, state.omiDocProfile, state.recordingFile, state.docTypePermission]);

    const showRuleDetail = (index) => {
        setState(prevState => ({ ...prevState, showRuleDetail: true, selectedIndex: index }));
    }

    const cpSaveLog=(functionName) => {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: state.clientId,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: state.clientId
            }
        }
        CPSaveLog(masterRequest).then(res => {
        }).catch(error => {
        });
    }

    const cpSaveLogPartner=(functionName, policyNo, apiToken) => {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: apiToken,
                Authentication: AUTHENTICATION,
                ClientID: policyNo,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: policyNo
            }
        }
        CPSaveLog(masterRequest).then(res => {
        }).catch(error => {
        });
    }


    const getEpolicyPdfHeader = (policyID) => {
        const submitRequest = {
            jsonDataInput: {
                Offset: '1',
                Action: 'GetPDFIndex',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: state.clientId,
                Authentication: AUTHENTICATION,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                PolicyNo: policyID,
                DeviceToken: 'Web',
                UserLogin: state.clientId,
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ClientProfile) {
                setState(prevState => ({ ...prevState, isEmpty: false, pdfProfile: Response.ClientProfile, loadedPdfProfile: true }));
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();
                // props.history.push({
                //     pathname: '/home', state: {authenticated: false, hideMain: false}

                // })
                // alert('invalid token');

            } else {
                setState(prevState => ({ ...prevState, isEmpty: true, pdfProfile: null, loadedPdfProfile: true }));
            }
        }).catch(error => {
            // alert(error);
            console.log(error);
            setState(prevState => ({ ...prevState, loadedPdfProfile: true }));
            // props.history.push('/maintainence');
        });
    }

    const getEpolicyPdfBinary = (url, startPage=1, permission) => {
        if (state.ePolicyPdfBase64) {
            openOmiDoc(state.ePolicyPdfBase64, 'application/pdf', startPage, permission);
            return;
        }
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetPDFFile',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: state.clientId,
                Authentication: AUTHENTICATION,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                FileName: url,
                DeviceToken: 'Web',
                UserLogin: state.clientId,
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                openOmiDoc(Response.Message, 'application/pdf', startPage, permission);
                setState(prevState => ({ ...prevState, ePolicyPdfBase64: Response.Message }));
                
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();
                // props.history.push({
                //     pathname: '/home', state: {authenticated: false, hideMain: false}

                // })
                // alert('invalid token');

            } 
        }).catch(error => {
            console.log(error);
            // props.history.push('/maintainence');
        });
    }

    const getEpolicyPdfRuleBinary = (url, startPage=1, permission) => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetPDFFile',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: state.clientId,
                Authentication: AUTHENTICATION,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                FileName: url,
                DeviceToken: 'Web',
                UserLogin: state.clientId,
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                console.log('rule----------------------');
                console.log(Response.Message);
                openOmiDoc(Response.Message, 'application/pdf', startPage, permission);
                setState(prevState => ({ ...prevState, ePolicyPdfBase64: Response.Message, permission: permission }));
                
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();
                // props.history.push({
                //     pathname: '/home', state: {authenticated: false, hideMain: false}

                // })
                // alert('invalid token');

            } 
        }).catch(error => {
            console.log(error);
            // props.history.push('/maintainence');
        });
    }


    const downloadPdfBinary = (url) => {
        if (state.ePolicyPdfBase64) {
            openDownload(state.ePolicyPdfBase64);
            return;
        }
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetPDFFile',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: state.clientId,
                Authentication: AUTHENTICATION,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                FileName: url,
                DeviceToken: 'Web',
                UserLogin: state.clientId,
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                openDownload(Response.Message);
                setState(prevState => ({ ...prevState, ePolicyPdfBase64: Response.Message }));
                
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();

            } 
        }).catch(error => {
            // alert(error);
            console.log(error);
            // props.history.push('/maintainence');
        });
    }

    const getEpolicyPdfHeaderForPartner = (data) => {
        const submitRequest = {
            jsonDataInput: {
                Offset: '1',
                Action: 'GetPDFIndex',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: data.policyNo?data.policyNo:data.proposalNo,
                Authentication: AUTHENTICATION,
                DeviceId: data.deviceId?data.deviceId : getDeviceId(),
                PolicyNo: data.policyNo,
                DeviceToken: 'Web',
                UserLogin: data.policyNo?data.policyNo:data.proposalNo
            }
        }
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
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        getEPolicyPdf(submitRequest).then(Res => {
            //console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ClientProfile) {
                setState(prevState => ({ ...prevState, pdfProfile: Response.ClientProfile, loadedPdfProfile: true }));
            } else if (Response.ErrLog === 'APIToken is invalid') {
                //Gen API token
                getEdocument(jsonData, encryptedText).then(Res => {
                    let Response = Res.Response;
                    if (Response.Result === 'true' && Response.ErrLog === 'Successful') {
                        console.log('gen success=' + Response.NewAPIToken);
                        getEpolicyPdfHeaderForPartnerRefresh(data, Response.NewAPIToken);
                        setState(prevState => ({ ...prevState, isEmpty: false, apiToken: Response.NewAPIToken }));
                    } else if (Response.ErrLog === 'Token is invalid') {
                        console.log('Gen token error');
                        setState(prevState => ({ ...prevState, loadedPdfProfile: true }));
                    }
                }).catch(error => {
                    setState(prevState => ({ ...prevState, loadedPdfProfile: true, loadedOmiDocProfile: true }));
                });
               
            } else {
                setState(prevState => ({ ...prevState, isEmpty: true, pdfProfile: null, loadedOmiDocProfile: true }));
            }
        }).catch(error => {
            setState(prevState => ({ ...prevState, isEmpty: true, pdfProfile: null, loadedOmiDocProfile: true }));
        });

        getOmiDocs(jsonData, encryptedText).then(Response => {
            // let Response = Res.Response;
            if (Response.responseCode === '00' && Response.responseMessage === 'Successful' && Response.DocumentList) {
                setState(prevState => ({ ...prevState, omiDocProfile: Response.DocumentList, loadedOmiDocProfile: true }));
            } else {
                console.log('Empty or error');
                setState(prevState => ({ ...prevState, loadedOmiDocProfile: true }));
            }
        }).catch(error => {
            console.log(error);
            setState(prevState => ({ ...prevState, loadedOmiDocProfile: true }));
        });
    }

    const getEpolicyPdfHeaderForPartnerRefresh = (data, token) => {
        const submitRequest = {
            jsonDataInput: {
                Offset: '1',
                Action: 'GetPDFIndex',
                Project: 'mcp',
                APIToken: token,
                ClientID: data.policyNo?data.policyNo:data.proposalNo,
                Authentication: AUTHENTICATION,
                DeviceId: data.deviceId?data.deviceId : getDeviceId(),
                PolicyNo: data.policyNo,
                DeviceToken: 'Web',
                UserLogin: data.policyNo?data.policyNo:data.proposalNo
            }
        }
        setSession(ACCESS_TOKEN, token);
        setSession(USER_LOGIN, data.policyNo?data.policyNo:data.proposalNo);
        setSession(DEVICE_ID, data.deviceId?data.deviceId : getDeviceId());
        getEPolicyPdf(submitRequest).then(Res => {
            //console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ClientProfile) {
                setState(prevState => ({ ...prevState, isEmpty: false, pdfProfile: Response.ClientProfile, loadedPdfProfile: true }));
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                console.log(Response.ErrLog);
            } else {
                setState(prevState => ({ ...prevState, isEmpty: true, pdfProfile: null, loadedPdfProfile: true }));
            }
        }).catch(error => {
            setState(prevState => ({ ...prevState, loadedPdfProfile: true }));
        });
    }

    const getRecodingFileAndTokenWithOmidocs = (data) => {
        const submitRequest = {
            jsonDataInput: {
                Offset: '1',
                Action: 'GetRecordingFile',
                Project: 'mcp',
                APIToken: state.apiToken,
                ClientID: data.policyNo?data.policyNo:data.proposalNo,
                Authentication: AUTHENTICATION,
                DeviceId: data.deviceId?data.deviceId : getDeviceId(),
                PolicyNo: data.policyNo,
                ProposalNo: data.proposalNo,
                DeviceToken: 'Web',
                UserLogin: data.policyNo?data.policyNo:data.proposalNo,
                Group: data.group
            }
        }
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
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        console.log("data=", data);
        console.log("key=", encryptedText);
        getEPolicyPdf(submitRequest).then(Res => {
            //console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                setState(prevState => ({ ...prevState, isEmpty: false, recordingFile: Response.Message, loadedRecording: true, loading: false, loadedRecordingFile: true }));
            } else if (Response.ErrLog === 'APIToken is invalid') {
                //Gen API token
                getEdocument(jsonData, encryptedText).then(Res => {
                    let Response = Res.Response;
                    if (Response.Result === 'true' && Response.ErrLog === 'Successful') {
                        console.log('gen success=' + Response.NewAPIToken);
                        cpSaveLogPartner(`Web_Open_${PageScreen.E_POLICY_POL_SDK}`, data.policyNo?data.policyNo:data.proposalNo, Response.NewAPIToken);
                        trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.E_POLICY_POL_SDK}`, `Web_Open_${PageScreen.E_POLICY_POL_SDK}`);
                        getRecodingFile(data, Response.NewAPIToken);
                        setState(prevState => ({ ...prevState, apiToken: Response.NewAPIToken }));
                    } else {
                        console.log('Gen token error');
                        setState(prevState => ({ ...prevState, isEmpty: true, loadedRecording: true, loading: false, loadedRecordingFile: true }));
                    }
                }).catch(error => {
                    setState(prevState => ({ ...prevState, isEmpty: true, loadedRecording: true,  loading: false, loadedRecordingFile: true }));
                });
               
            } else {
                setState(prevState => ({ ...prevState, isEmpty: true, recordingFile: null, loadedRecording: true, loading: false, loadedRecordingFile: true }));
            }
        }).catch(error => {
            setState(prevState => ({ ...prevState, isEmpty: true, recordingFile: null, loadedRecording: true, loading: false, loadedRecordingFile: true }));
        });
        let remainRetryTimes = NUM_OF_RETRY_OMI_DOCS; // first request + num_of_retry.
        //Change to load defalt doctype
        getOmiDocuments(jsonData, encryptedText, remainRetryTimes);
    }

    const getOmiDocuments = (jsonData, encryptedText, remainRetryTimes) => {
          
                console.log('remainRetryTimes=' + remainRetryTimes);
                try {
                    getIBPSDocumentList(jsonData, encryptedText).then(Response => {
                        // let Response = Res.Response;
                        if (Response.responseCode === '00' && Response.responseMessage === 'Successful') {
                            if (Response.DocumentList) {
                                console.log('Response.DocumentList=', Response.DocumentList);
                                setState(prevState => ({ ...prevState, isEmpty: false, omiDocProfile: Response.DocumentList, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                            } else {
                                setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                            }
                        } else {
                            console.log('Empty or error');
                            if (remainRetryTimes > 0) {
                                remainRetryTimes--;
                                setState(prevState => ({ ...prevState, loadedOmiDocProfile: true }));
                                setTimeout(() => {
                                    getOmiDocuments(jsonData, encryptedText, remainRetryTimes);
                                }, 1000);
                            } else {
                                setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                            }
                        }
                    }).catch(error => {
                        console.log(error);
                        setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                    });

                } catch (error) {
                    console.log(error);
                    setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                } finally {
                    // remainRetryTimes--;
                }


    }

    const getOneOmiDocAndOpen = (docTypeCode, workflowId, documentIndex) => {
        try {
            if (props.data) {
                let dParam = decodeHtmlEntity(props.data);
                let data = JSON.parse(AES256.decrypt(dParam, COMPANY_KEY3));
                console.log("data=", data);
                console.log('policyNo=', data.policyNo);
                if (data.policyNo) {
                    data.docTypeCode = docTypeCode;
                    data.workflowId = workflowId;
                    data.documentIndex = documentIndex;
                    console.log('data=', data);
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
                    const encryptedText = NodeForge.util.encode64(encryptedBytes);
                    //end rsa
                    let jsonData = {
                        data: ensData
                    }
                    GetDocumentDetails(jsonData, encryptedText).then(Response => {
                        // let Response = Res.Response;
                        if (Response.responseCode === '00' && Response.responseMessage === 'Successful') {
                            if (Response.DocumentList) {
                                let item = Response.DocumentList[0];
                                if (item) {
                                    setState(prevState => ({ ...prevState, isEmpty: false, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true }));
                                    openOmiDoc(item.content, item.mimeType, 1, item.Permission);
                                } else {
                                    setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
                                }
                                
                            } else {
                                setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
                            }
                        } else {
                            setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
                        } 
                    }).catch(error => {
                        console.log(error);
                        setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
                    });
                }
            }
        } catch (error) {
            console.log(error);
            setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
        } finally {

        }


    }

    const getRecodingFile = (data, token) => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetRecordingFile',
                Project: 'mcp',
                APIToken: token,
                ClientID: data.policyNo,
                Authentication: AUTHENTICATION,
                DeviceId: data.deviceId?data.deviceId : getDeviceId(),
                PolicyNo: data.policyNo,
                DeviceToken: 'Web',
                UserLogin: data.policyNo,
                Group: data.group
            }
        }

        getEPolicyPdf(submitRequest).then(Res => {
            //console.log(JSON.stringify(Res));
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                setState(prevState => ({ ...prevState, isEmpty: false, recordingFile: Response.Message, loadedRecording: true, loading: false, loadedRecordingFile: true }));
            }  else {
                setState(prevState => ({ ...prevState, isEmpty: true, recordingFile: null, loadedRecording: true, loading: false, loadedRecordingFile: true}));
            }
        }).catch(error => {
            setState(prevState => ({ ...prevState, isEmpty: true, recordingFile: null, loadedRecording: true, loading: false, loadedRecordingFile: true}));
        });

    }

    const getDocTypePermissionProfile = (data) => {
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
        const encryptedText = NodeForge.util.encode64(encryptedBytes);
        console.log('Enscrypted=');
        console.log(encryptedText);
        //end rsa
        let jsonData = {
            data: ensData
        }
        getDocTypePermission(jsonData, encryptedText).then(Res => {
            console.log('permission=', Res);
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.DocTypePermission ) {
                setState(prevState => ({ ...prevState, isEmpty: false, docTypePermission: Response.DocTypePermission, loading: false, loadedTypePermission: true }));
            } else {
                setState(prevState => ({ ...prevState, docTypePermission: null, loading: false, loadedTypePermission: true }));
            }
        }).catch(error => {
            console.log(error);
            setState(prevState => ({ ...prevState, docTypePermission: null, loading: false, loadedTypePermission: true }));
        });
    }

    const cpGetEPolicyRules = (polID) => {
        const submitRequest = {
            jsonDataInput: {
                Action: 'GetPDFAppendix',
                Company: COMPANY_KEY,
                PolicyNo: polID,
                OS: WEB_BROWSER_VERSION,
                APIToken: state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: state.clientId,
                DeviceId: state.deviceId?state.deviceId : getDeviceId(),
                Project: 'mcp',
                UserLogin: state.clientId,
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            let Response = Res.Response;
            console.log('GetPDFAppendix=', Response);
            if (Response.Result === 'true' && Response.ClientProfile) {
                setState(prevState => ({ ...prevState, ruleProfile: Response.ClientProfile, loadedRuleProfile: true }));
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
                // logoutSession();
                // props.history.push({
                //     pathname: '/home', state: {authenticated: false, hideMain: false}

                // })
                console.log('appendix invalid token');

            } else {
                setState(prevState => ({ ...prevState, loadedRuleProfile: true }));
            }

        }).catch(error => {
            console.log(error);
            // props.history.push('/maintainence');
        });
    }

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
    }

    const openDownload = (response) => {
        if (!response) {
            return;
        }
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
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.href = url;
        a.download = 'Hợp đồng bảo hiểm nhân thọ.pdf';
        a.click();
    }

    const getPDFDetail = (url) => {
        const submitRequest = {
            jsonDataInput: {
                OS: WEB_BROWSER_VERSION,
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'GetPDFFile',
                FileName: url,
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN),
            }
        }
        getEPolicyPdf(submitRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.ErrLog === 'SUCCESSFUL' && Response.Message) {
                viewRulePdf(Response.Message);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
            } else {
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });
    }

    const openNewTab = (response) => {
        const contentType = 'application/pdf';
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

        let blob = new Blob(byteArrays, {type: contentType});
        blob = b64toBlob(response, contentType);
        const blobUrl = URL.createObjectURL(blob);
        window.open(blobUrl);
    }

    const viewRulePdf = (PDFFile) => {
        openNewTab(PDFFile);
    }

    const openOmiDoc = (response, mineType, startPage=1, permission) => {
        const contentType = mineType;//'application/pdf';
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

        let blob = new Blob(byteArrays, {type: contentType});
        blob = b64toBlob(response, contentType);
        const blobUrl = URL.createObjectURL(blob);
        // window.open(blobUrl);
        setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: blobUrl, mimeType: mineType, startPage: startPage, permission: permission }));
    }

    const openRecording = (URL, mimeType) => {
        getRecordingToken(URL).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true' && Response.Message) {
                let accessToken = Response.Message;
                let rUrl = RECORDING_BASE_URL + accessToken;
                // window.open(rUrl, '_blank');
                setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: rUrl, mimeType: mimeType }));
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                // showMessage(EXPIRED_MESSAGE);
            } else {
            }
        }).catch(error => {
            //this.props.history.push('/maintainence');
        });

    }

    const isInPermissionList = (DocTypeCode, docTypePermission) => {
        if (!DocTypeCode || !docTypePermission) {
            return null;
        }
        for (let i = 0; i < docTypePermission.length; i++) {
            if (DocTypeCode === docTypePermission[i].DocTypeCode) {
                return docTypePermission[i].Permission;
            }
        }
        return null;
    }

    const getDocTypeName = (DocTypeCode, docTypePermission) => {
        if (!DocTypeCode || !docTypePermission) {
            return null;
        }
        for (let i = 0; i < docTypePermission.length; i++) {
            if (DocTypeCode === docTypePermission[i].DocTypeCode) {
                return docTypePermission[i].DocTypeName;
            }
        }
        return null;
    }

    const haveAccessRight = (item, docTypePermission) => {
        if (!item || !docTypePermission || !item.documentTitle) {
            return null;
        }
        for (let i = 0; i < docTypePermission.length; i++) {
            console.log('item.documentTitle=', item.documentTitle);
            console.log('docTypePermission[i].DocTypeCode=', docTypePermission[i].DocTypeCode);
            console.log('item[docTypePermission.ExpressionKey]=', docTypePermission[i].ExpressionKey);
            console.log('docTypePermission.ExpressionValue=', docTypePermission[i].ExpressionValue);
            if ((item.documentTitle === docTypePermission[i].DocTypeCode) && docTypePermission[i].ExpressionKey) {
                if (!docTypePermission[i].ExpressionValue) {
                    return docTypePermission[i].Permission;
                } else {
                    var docDate = ddMMyyyyToDate(item[docTypePermission[i].ExpressionKey]);
                    docDate.setMonth(docDate.getMonth() + parseInt(docTypePermission[i].ExpressionValue));
                    if (docDate >= new Date()) {
                        return docTypePermission[i].Permission;
                    } 
                }

            } 
        }
        return null;
    }

    const getPermisson = (item, docTypePermission) => {
        if (!item || !docTypePermission || !item.documentTitle) {
            return null;
        }
        for (let i = 0; i < docTypePermission.length; i++) {
            if ((item.documentTitle === docTypePermission[i].DocTypeCode) && docTypePermission[i].Permission) {
                return docTypePermission[i].Permission;
            } 
        }
        return null;
    }

    const closePdf = () => {
        setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: '' }));
    }

    const closeNoDocument = () => {
        setState(prevState => ({ ...prevState, noDocument: false }));
    }

    return (
        <>
            {!state.viewingReadonlyUrlPdf?(
            <section className={state.isEmpty ? "sccontract-warpper no-data" : "sccontract-warpper"}>
                {!props.data &&(
                    <>
                    {state.isEmpty ? (<div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                    <div className="breadcrums__item">
                        <p>Thư viện tài liệu</p>
                        <span>&gt;</span>
                    </div>
                    <div className="breadcrums__item">
                        <p>Bộ hợp đồng</p>
                        <span>&gt;</span>
                    </div>
                    </div>) : (<div className="breadcrums" style={{backgroundColor: '#f1f1f1'}}>
                            <div className="breadcrums__item">
                                <p>Thư viện tài liệu</p>
                                <span>&gt;</span>
                            </div>
                            {state.showRuleDetail ? (
                                <div className="breadcrums__item" style={{cursor: 'pointer'}}
                                        onClick={() => setState(prevState => ({ ...prevState, showRuleDetail: false }))}>
                                    <p>Bộ hợp đồng</p>
                                    <span>&gt;</span>
                                </div>) : (<div className="breadcrums__item">
                                <p>Bộ hợp đồng</p>
                                <span>&gt;</span>
                            </div>)}

                            {state.showRuleDetail && <div className="breadcrums__item">
                                <p>Phụ lục sửa đổi, bổ sung Quy tắc và điều khoản</p>
                                <span>&gt;</span>
                            </div>}
                        </div>
                    )}
                    </>
                )/*:(
                    <div className='padding-top40'></div>
                )*/
                }

                {!props.data && state.policyID &&
                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                    <p>Chọn thông tin</p>
                    <i><img src={FE_BASE_URL+ "/img/icon/return_option.svg"} alt=""/></i>
                </div>
                }

                <LoadingIndicator area="epolicy-pdf"/>

                {!props.data && !state.policyID ? (<div className="insurance">
                    <div className="empty">
                        <div className="icon">
                            <img src={FE_BASE_URL + "/img/no-data(6).svg"} alt="no-data"/>
                        </div>
                        <p style={{paddingTop: '20px'}}>Thông tin chi tiết sẽ hiển thị khi bạn
                            chọn một hợp đồng ở bên trái.</p>
                    </div>
                </div>) : (

                    !state.showRuleDetail ? (<div className="an-hung-page">
                        {state.loadedTypePermission  && state.loadedOmiDocProfile && !state.pdfProfile && !state.ruleProfile && !state.recordingFile && !state.omiDocProfile ?(

                        <div className="empty">
                        <div className="icon">
                            <img src={FE_BASE_URL + "/img/no-data(6).svg"} alt="no-data"/>
                        </div>
                            <p style={{paddingTop: '20px', display: 'flex', justifyContent: 'center'}}>Không tìm thấy chứng từ phù hợp.</p>
                        </div>
                        ):(
                            state.docTypePermission && (state.pdfProfile || state.ruleProfile || state.recordingFile || state.omiDocProfile) &&
                        <>
                        <div className="bo-hop-dong-container">
                            {state.pdfProfile && state.pdfProfile[0] && state.pdfProfile[0].lstPDFIndex.map((item, index) => {
                                let permission = isInPermissionList(item.DocTypeID, state.docTypePermission);
                                return (
                                    permission &&
                                    <>
                                        {<div className={!props.data?"bo-hop-dong-container-body":"bo-hop-dong-container-body-sdk"} key={'pdf' + index}
                                                            style={{cursor: 'pointer'}}
                                                            onClick={() => getEpolicyPdfBinary(state.pdfProfile[0].URL, item.StartPage, permission)}>
                                            <div
                                                className="bo-hop-dong-container-body__content-title" key={'pdf-title' + index}>
                                                {item.DocTypeName}
                                            </div>
                                            <div key={'pdf-arrow' + index}
                                                className="bo-hop-dong-container-body__content-arrow">
                                                <img src={FE_BASE_URL + "/img/icon/arrow-left-grey.svg"}
                                                        alt="arrow-left"
                                                        style={{maxWidth: '14px'}}/>
                                            </div>
                                        </div>}

                                        <div className="border" key={'pdfbg' + index}></div>
                                    </>)
                            })}
                            {state.ruleProfile && state.ruleProfile.map((item, index) => {
                                return (<>

                                    <div className={!props.data?"bo-hop-dong-container-body":"bo-hop-dong-container-body-sdk"} key={'second' + index}
                                            style={{cursor: 'pointer'}}
                                            onClick={() => showRuleDetail(index)}>
                                        <div key={'second-title' + index}
                                            className="bo-hop-dong-container-body__content-rule-title">
                                            {item.Title}
                                        </div>
                                        <div key={'second-arrow' + index}
                                            className="bo-hop-dong-container-body__content-arrow">
                                            <img src={FE_BASE_URL + "/img/icon/arrow-left-brown.svg"}
                                                    alt="arrow-left" style={{
                                                width: '14px', height: '14px', maxWidth: '14px'
                                            }}/>
                                        </div>
                                    </div>
                                    <div className="border" key={'secondbg' + index}></div>
                                </>)
                            })}
                            {state.recordingFile && 
                                <>
                                    <div className={!props.data?"bo-hop-dong-container-body":"bo-hop-dong-container-body-sdk"} key='recording'
                                            style={{cursor: 'pointer'}}
                                            onClick={() => openRecording(state.recordingFile.replaceAll('10.166.5.114', 'hos-dlvn-qml'), 'video/mp4')}>
                                        <div key='recording-title'
                                            className="bo-hop-dong-container-body__content-rule-title">
                                            {getDocTypeName('RecordingFile', state.docTypePermission)}
                                        </div>
                                        <div key='recording-arrow'
                                            className="bo-hop-dong-container-body__content-arrow">
                                            <img src={FE_BASE_URL + "/img/icon/arrow-left-brown.svg"}
                                                    alt="arrow-left" style={{
                                                width: '14px', height: '14px', maxWidth: '14px'
                                            }}/>
                                        </div>
                                    </div>
                                    <div className="border" key='recordingbg'></div>
                                </>
                            }
                            {state.omiDocProfile && state.omiDocProfile.map((item, index) => {
                                return (
                                    <>
                                    <div className={!props.data?"bo-hop-dong-container-body":"bo-hop-dong-container-body-sdk"} key={'omi' + index}
                                            style={{cursor: 'pointer'}}
                                            onClick={() => getOneOmiDocAndOpen(item.documentTitle, item.batchId, item.documentId)}>
                                        <div key={'omi-title' + index}
                                            className="bo-hop-dong-container-body__content-rule-title">
                                            {getDocTypeName(item.documentTitle, state.docTypePermission)}
                                        </div>
                                        <div key={'omi-arrow' + index}
                                            className="bo-hop-dong-container-body__content-arrow">
                                            <img src={FE_BASE_URL + "/img/icon/arrow-left-brown.svg"}
                                                    alt="arrow-left" style={{
                                                width: '14px', height: '14px', maxWidth: '14px'
                                            }}/>
                                        </div>
                                    </div>
                                    <div className="border" key={'omibg' + index}></div>
                                    </>
                                )
                            })}
                        </div>
                        </>
                        )}
                        {!props.data && (
                            <div className="bottom-btn"
                                    onClick={() => downloadPdfBinary(state.pdfProfile[0].URL)}>
                                <button className="btn btn-primary">Tải về</button>
                            </div>
                        )

                        }
                    </div>) : (<div className="an-hung-page">
                        <div className="bo-hop-dong-container">
                            {state.ruleProfile && state.ruleProfile[state.selectedIndex] && state.ruleProfile[state.selectedIndex].lstPDFIndex.map((item, index) => {
                                return (<>

                                    <div className="bo-hop-dong-container-body" key={'rule' + index}
                                            style={{cursor: 'pointer'}}
                                            onClick={() => getEpolicyPdfRuleBinary(item.URL, 1, 'ALL')}>
                                        <div key={'rule-title' + index}
                                            className="bo-hop-dong-container-body__content-title">
                                            {item.Product}
                                        </div>
                                        <div key={'rule-arrow' + index}
                                            className="bo-hop-dong-container-body__content-arrow">
                                            <img src={FE_BASE_URL + "/img/icon/arrow-left-grey.svg"}
                                                    alt="arrow-left"/>
                                        </div>
                                    </div>
                                    <div className="border" key={'rulebg' + index}></div>
                                </>)
                            })}
                        </div>
                    </div>)
                )
                }
            </section>
            ):(
                <PdfViewer pdfUrl={state.viewingReadonlyUrlPdf} mimeType={state.mimeType} initialPage={state.startPage} permission={state.permission} closePdf={()=>closePdf()} />
            )}
            {state.noDocument &&
                <AlertPopup closePopup={()=>closeNoDocument()} msg={'Không tìm thấy chứng từ'} imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}/>
            }
        </>
    );
}

export default EDocumentDetailWrapperSingle;