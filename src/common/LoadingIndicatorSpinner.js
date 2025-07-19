import React from 'react';
import {FE_BASE_URL} from '../constants';

export default function LoadingIndicatorSpinner(props) {
    return (
        <div style={{width: "100%", height: "40", display: "flex", justifyContent: "center", alignItems: "center" }}>
            <img src={FE_BASE_URL + '/img/icon/loading-indicator.svg'} height="20"/>
        </div>
    );
}