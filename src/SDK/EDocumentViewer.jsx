import React, { useState, useEffect } from 'react';
import {
    AUTHENTICATION,
    PageScreen,
    COMPANY_KEY3,
    PUBLIC_KEY_DLVN,
    FE_BASE_URL,
    IS_MOBILE
} from './sdkConstant';
import { CPSaveLog, getDocTypePermission, GetDocumentPremium } from './sdkAPI';
import { getDeviceId, getSession, trackingEvent, genPassword, getUrlParameter, getUrlParameterNoDecode } from './sdkCommon';
import LoadingIndicator from './LoadingIndicator2';
import AES256 from 'aes-everywhere';
import b64toBlob from 'b64-to-blob';
import NodeForge from 'node-forge';
// import './sdk.css';
import PdfViewer from './PdfViewer';
import AlertPopup from './AlertPopup';

let from = ''
const EDocumentViewer = (props) => {
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
        from = getUrlParameter("fromApp");
        if (props.data) {
            let dParam = decodeHtmlEntity(props.data);
            console.log("props.data=", props.data);
            // dParam = dParam.replace(/ /g, "+");
            let data = JSON.parse(AES256.decrypt(dParam, COMPANY_KEY3));
            console.log("data=", data);
            console.log('policyNo=', data.serialNo);
            if (data.serialNo) {
                // setState(prevState => ({ ...prevState, policyID: data.policyNo, viewingReadonlyUrlPdf: '', pdfProfile: null, ruleProfile: null, ePolicyPdfBase64: null, permission: '', showRuleDetail: '', loading: true }));
                getPremiumDocument();
            }
        } else {
            let key = getUrlParameter("key");
            let paramData = getUrlParameterNoDecode("data");
            if (key && paramData) {
                getPremiumDocumentLink(key, paramData);
            }
        }
        // cpSaveLog(`Web_Open_${PageScreen.PREMIUM_LETTER_SDK}`);
        // trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.PREMIUM_LETTER_SDK}`, `Web_Open_${PageScreen.PREMIUM_LETTER_SDK}`);

        return () => {
            cpSaveLog(`Web_Close_${PageScreen.PREMIUM_LETTER_SDK}`);
            trackingEvent("Thư viện tài liệu", `Web_Close_${PageScreen.PREMIUM_LETTER_SDK}`, `Web_Close_${PageScreen.PREMIUM_LETTER_SDK}`);
        };
    }, []);

    // useEffect(() => {
    //     console.log(state.policyID + '|'+ props.policyID)
    //     if (state.policyID !== props.policyID) {
    //         setState(prevState => ({ ...prevState, policyID: props.policyID, viewingReadonlyUrlPdf: '', pdfProfile: null, ruleProfile: null, ePolicyPdfBase64: null, permission: '', showRuleDetail: '', loading: true }));
    //         if (props.data) {
    //             let data = JSON.parse(AES256.decrypt(props.data, COMPANY_KEY3));
    //             console.log("data=", data);
    //             console.log('policyNo=', data.policyNo);
    //             if (data.policyNo) {
    //                 // getEpolicyPdfHeaderForPartner(data);
    //                 // cpGetEPolicyRules(data.policyNo);
    //                 getDocTypePermissionProfile(data);
    //                 getRecodingFileAndTokenWithOmidocs(data);
    //             }
    //         } else {
    //             getEpolicyPdfHeader(props.policyID);
    //             cpGetEPolicyRules(props.policyID);
    //         }
    //     } 

    // }, [props.policyID]);


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



    const getPremiumDocument = () => {
        try {
            if (props.data) {
                let dParam = decodeHtmlEntity(props.data);
                let data = JSON.parse(AES256.decrypt(dParam, COMPANY_KEY3));
                console.log("data=", data);
                console.log('policyNo=', data.policyNo);
                if (data.serialNo) {
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
                    GetDocumentPremium(jsonData, encryptedText).then(Response => {
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

    const getPremiumDocumentLink = (key, data) => {
        try {
            let jsonData = {
                data: data
            }
            GetDocumentPremium(jsonData, key).then(Response => {
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

        } catch (error) {
            console.log(error);
            setState(prevState => ({ ...prevState, isEmpty: true, loadedOmidocs: true, loading: false, loadedOmiDocProfile: true, noDocument: true }));
        } finally {

        }


    }

    const goBack = () => {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.toggle("nodata");
        }
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

    const closePdf = () => {
        let obj = {
            Action: "BACK_LETTER",
        };
        if (from && (from === "Android")) {//for Android
            setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: '' }));
            if (window.AndroidAppCallback) {
                window.AndroidAppCallback.postMessage(JSON.stringify(obj));
            }
        } else if (from && (from === "IOS")) {//for IOS
            setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: '' }));
            if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
            }
        }
        
    }

    const closeNoDocument = () => {
        setState(prevState => ({ ...prevState, noDocument: false }));
        let obj = {
            Action: "BACK_NO_LETTER",
        };
        if (from && (from === "Android")) {//for Android
            setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: '' }));
            if (window.AndroidAppCallback) {
                window.AndroidAppCallback.postMessage(JSON.stringify(obj));
            }
        } else if (from && (from === "IOS")) {//for IOS
            setState(prevState => ({ ...prevState, viewingReadonlyUrlPdf: '' }));
            if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
            }
        }
    }

    return (
        <>
            {state.viewingReadonlyUrlPdf &&
                <PdfViewer pdfUrl={state.viewingReadonlyUrlPdf} mimeType={state.mimeType} initialPage={state.startPage} permission={state.permission} closePdf={()=>closePdf()} hideBackButton={!from} title={'Thư xác nhận đóng phí'}/>
            }
            {state.noDocument &&
                <AlertPopup closePopup={()=>closeNoDocument()} msg={'Không tìm thấy chứng từ'} imgPath={FE_BASE_URL + '/img/popup/no-policy.svg'}/>
            }
        </>
    );
}

export default EDocumentViewer;