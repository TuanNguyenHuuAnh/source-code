import React, { useState, useEffect } from 'react';
import {Redirect} from 'react-router-dom';
import { ACCESS_TOKEN, CELL_PHONE, CLIENT_ID, COMPANY_KEY3, USER_LOGIN, REVIEW_LINK } from '../../../sdkConstant';
import { getDeviceId, getSession, setSession, removeSession } from '../../../sdkCommon';
import { isEmpty } from "lodash";
import AES256 from 'aes-everywhere';
import DConnectSDK from '../../../DConnectSDK';

const SDKReceiveLink = (props) => {
    const [state, setState] = useState({
        data: ''
    });

    useEffect(() => {
        //if (getSession(ACCESS_TOKEN) && getSession(REVIEW_LINK)) {
        if (getSession(ACCESS_TOKEN)) {
            let json = {
                sourceSystem: "DConnect",
                group: "PO",
                platform: "Web",
                poClientId: getSession(CLIENT_ID),
                agentPhone: getSession(CELL_PHONE),
                deviceId: getDeviceId(),
                processType: "Claim",
                apiToken: getSession(ACCESS_TOKEN),
                link: (getSession(REVIEW_LINK)?getSession(REVIEW_LINK): window.location.href)?.replaceAll('localhost:3000', 'khuat.dai-ichi-life.com.vn')
            }
            let ensData = AES256.encrypt(JSON.stringify(json), COMPANY_KEY3);
            setState(prevState => ({ ...prevState, data: ensData }));
            removeSession(REVIEW_LINK);
            
        } 

    }, []);

    if (!getSession(ACCESS_TOKEN)) {
        setSession(REVIEW_LINK, window.location.href);
        return <Redirect to={{
            pathname: '/home'
        }} />;
    }
    return (
        <>
            {!isEmpty(state.data) &&
                <DConnectSDK data={state.data} />
            }
        </>
    );
};

export default SDKReceiveLink;