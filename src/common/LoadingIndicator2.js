import React from 'react';
import { usePromiseTracker } from "react-promise-tracker";
import { FE_BASE_URL } from '../constants';

export default function LoadingIndicator2(props) {
    const { promiseInProgress } = usePromiseTracker({area: props.area});
    return (
        promiseInProgress &&
        <div style={{width: "100%", height: "40", display: "flex", justifyContent: "center", alignItems: "center", marginTop: '8px' }}>
            <img src={FE_BASE_URL + '/img/icon/loading-indicator.svg'} height="20"/>
        </div>
    );
}