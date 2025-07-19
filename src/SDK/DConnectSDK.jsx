import React, { useState, useEffect } from 'react';
import { AUTHENTICATION } from './sdkConstant';
import { CPSaveLog } from './sdkAPI';
import { getDeviceId, setViewportScale, getUrlParameter, getUrlParameterNoDecode } from './sdkCommon';
import CreateClaimSDK from './Claim/CreateClaim/CreateClaimSDK'
import {isEmpty} from "lodash";

let fromNative = '';
const DConnectSDK = (props) => {
    const [state, setState] = useState({
        loading: false,
        apiToken: props.apiToken,
        deviceId: props.deviceId,
        clientId: props.clientId,
        appType: props.appType,
        key: '',
        paramData: '',
        data: ''
    });

    useEffect(() => {
        if (props.data) {
            if (state.data !== props.data) {
                console.log('props.data=', props.data);
                setState(prevState => ({ ...prevState, data: props.data }));
            }
        } else {
            fromNative = getUrlParameter("fromApp");
            if (fromNative) {
                setViewportScale(1.0);
            }
            let AppType = getUrlParameter("AppType");
            let Key = getUrlParameter("key");
            let ParamData = getUrlParameterNoDecode("Data");
            setState(prevState => ({ ...prevState, appType: AppType, key: Key, paramData: ParamData }));
        }


    }, [props.data, state.key, state.paramData]);

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
            <div id="dc-sdk-version" style={{ display: 'none' }}>1.1.1</div>
            {(props.appType !== 'CLOSE') && (!isEmpty(props.data) || (!isEmpty(state.key) && !isEmpty(state.paramData) )) ? (
                <CreateClaimSDK data={props.data} appType={state.appType} paramKey={state.key} paramData={state.paramData}/>
            ) : (
                <div className="main-warpper insurancepage basic-mainflex">
                </div>
            )}
        </>
    );
};

export default DConnectSDK;
