import { FE_BASE_URL } from './sdkConstant';

export default function LoadingIndicatorBasic() {
    return (
        <div style={{width: "100%", height: "40", display: "flex", justifyContent: "center", alignItems: "center", marginTop: '8px'  }}>
            <img src={FE_BASE_URL + '/img/icon/loading-indicator.svg'} height="20"/>
        </div>
    );
}