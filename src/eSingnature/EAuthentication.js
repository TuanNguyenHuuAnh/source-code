import React, { useState, useEffect } from 'react';
import { ACCESS_TOKEN, E_SINGNATURE_CLIENT_ID, E_SINGNATURE_POLICY_NO, CLIENT_ID } from '../constants';
import EAuthenticationLogin from './EAuthenticationLogin';
import {getSession} from '../util/common';

function EAuthentication(props) {
    const [showAuthentication, setShowAuthentication] = useState(true);


    useEffect(() => {
        const accessToken = getSession(ACCESS_TOKEN);
        const clientID = getSession(CLIENT_ID);
        const eSingnatureclientID = getSession(E_SINGNATURE_CLIENT_ID);
        const policyNO = getSession(E_SINGNATURE_POLICY_NO);
        if (accessToken && clientID && eSingnatureclientID === clientID) {
            setShowAuthentication(false);
        } else {
            setShowAuthentication(true);
        }
        return () => {

        }
    }, []);

    return (
        showAuthentication &&
            <EAuthenticationLogin />

    )
}

export default EAuthentication;