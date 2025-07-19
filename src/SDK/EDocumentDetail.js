import React, { useState, useEffect } from 'react';
import { AUTHENTICATION, SDK_MODE_SINGLE } from './sdkConstant';
import { CPSaveLog } from './sdkAPI';
import { getDeviceId } from './sdkCommon';
import EDocumentDetailWrapper from "./EDocumentDetailWrapper.jsx";
import EDocumentDetailWrapperSingle from "./EDocumentDetailWrapperSingle.jsx";

const EDocumentDetail = (props) => {
    const [state, setState] = useState({
        ruleProfile: null,
        pdfProfile: null,
        policyID: props.policyID,
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
        appType: props.appType,
        data: null
    });

    // useEffect(() => {
    //     // cpSaveLog(`Web_Open_${PageScreen.E_POLICY_POL_VIEW}`);
    //     // trackingEvent("Thư viện tài liệu", `Web_Open_${PageScreen.E_POLICY_POL_VIEW}`, `Web_Open_${PageScreen.E_POLICY_POL_VIEW}`,);

    //     return () => {
    //         // cpSaveLog(`Web_Close_${PageScreen.E_POLICY_POL_VIEW}`);
    //         // trackingEvent("Thư viện tài liệu", `Web_Close_${PageScreen.E_POLICY_POL_VIEW}`, `Web_Close_${PageScreen.E_POLICY_POL_VIEW}`,);
    //     };
    // }, []);

    useEffect(() => {
        if ((state.policyID !== props.policyID) || (state.data !== props.data)) {
            setState(prevState => ({ ...prevState, policyID: props.policyID, data: props.data }));
        }
    }, [props.policyID, props.data]);

    const cpSaveLog = (functionName) => {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: state.apiToken,
                Authentication: AUTHENTICATION,
                ClientID: state.clientId,
                DeviceId: state.deviceId ? state.deviceId : getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: state.clientId
            }
        };
        CPSaveLog(masterRequest).then(res => {
        }).catch(error => {
        });
    };

    let logined = false;
    if (state.apiToken) {
        logined = true;
    }

    return (
        <>
            {props.appType !== 'CLOSE' ? (
                <main className={logined ? "logined" : "logined nodata"} id="main-id">
                    <div className="main-warpper insurancepage basic-mainflex">
                        {SDK_MODE_SINGLE === 1?(
                            <EDocumentDetailWrapperSingle
                            appType={state.appType}
                            clientId={state.clientId}
                            deviceId={state.deviceId}
                            apiToken={state.apiToken}
                            policyID={state.policyID} 
                            data={props.data}
                        />
                        ):(
                            <EDocumentDetailWrapper
                            appType={state.appType}
                            clientId={state.clientId}
                            deviceId={state.deviceId}
                            apiToken={state.apiToken}
                            policyID={state.policyID} 
                            data={props.data}
                        />
                        )}

                    </div>
                </main>
            ) : (
                SDK_MODE_SINGLE === 1?(
                    <EDocumentDetailWrapperSingle
                    appType={state.appType}
                    clientId={state.clientId}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    policyID={state.policyID} 
                    data={props.data}
                />
                ):(
                    <EDocumentDetailWrapper
                    appType={state.appType}
                    clientId={state.clientId}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    policyID={state.policyID} 
                    data={props.data}
                />
                )
            )}
        </>
    );
};

export default EDocumentDetail;
