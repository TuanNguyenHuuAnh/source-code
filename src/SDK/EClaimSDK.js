import React, { useState, useEffect } from 'react';
import { ACCESS_TOKEN, CELL_PHONE, CLIENT_ID, COMPANY_KEY3, USER_LOGIN } from './sdkConstant';
import { getDeviceId, getSession } from './sdkCommon';
import {isEmpty} from "lodash";
import AES256 from 'aes-everywhere';
import DConnectSDK from './DConnectSDK';

const EClaimSDK = (props) => {
    const [state, setState] = useState({
        data: ''
    });

    useEffect(() => {
        let json = {
            sourceSystem: "DConnect",
            group: "PO",
            platform: "Web",
            poClientId: getSession(CLIENT_ID),
            agentPhone: getSession(CELL_PHONE),
            deviceId: getDeviceId(),
            processType: "Claim",
            apiToken: getSession(ACCESS_TOKEN)
        }
        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '311311',
        //     agentPhone: '0967539693',
        //     agentName: 'Hoàng Ngọc Anh',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS05399059",
        //     status: ''
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '1041473',
        //     agentPhone: '0967993568',
        //     agentName: 'Nguyễn Thị Thơm',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS45881528"
        //     status: ''
        // }
        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '577434',
        //     agentPhone: '0973036388',
        //     agentName: 'Phạm Thị Thủy',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS45881528"
        //     status: ''
        // }
        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '555537',
        //     agentPhone: '0984493263',
        //     agentName: 'Lê Thị Hải Hà',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS50791579"
        //     status: 'ALL'
        // }
        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '515198',
        //     agentPhone: '0935717020',
        //     agentName: 'Nguyễn Thị Túy',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS50791579"
        //     status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '514814',
        //     agentPhone: '0901414144',
        //     agentName: 'Dương Phú Cường',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS50791579"
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '1115549',
        //     agentPhone: '0354031813',
        //     agentName: 'Nguyễn Văn Tiệp',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS50791579"
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '1001483',
        //     agentPhone: '0904940840',
        //     agentName: 'Phạm Trường Sơn',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS50791579"
        //     status: 'POREVIEW',
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management"
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0000000141',
        //     agentCode: '209355',
        //     agentPhone: '0939944022',
        //     agentName: 'Đặng Huy Hoàng',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS83733728",
        //     status: '',
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management"
        // }
     
        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0001241621',
        //     // agentCode: '311311',
        //     // poClientId: '0002605636',
        //     // agentCode: '591323',
        //     agentCode: '1073566',
        //     // agentCode: '209355',
        //     agentPhone: '0987786772',
        //     agentName: 'Nguyễn Thị Hạnh',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS14062705"
        //     status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: '0001241621',
        //     agentCode: '583183',
        //     agentPhone: '0912678386',
        //     agentName: 'Nguyễn Thị Ánh Nguyệt',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     callBackCancel: "/policy-claim-management",
        //     callBackSuccess: "/policy-claim-management",
        //     // requestId: "DS14062705"
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "ADPortal",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     poClientId: "0004376279",
        //     agentCode: '1130554',
        //     agentPhone: '0399942122',
        //     agentName: 'Nguyễn Ngọc Phi Hiền',
        //     deviceId: "4090580cc2e64a6ab290153673efc3de",
        //     processType: "Claim"
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '1049413',
        //     agentPhone: '0918643644',
        //     agentName: 'Trương Thị Huỳnh Như',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS86176750"
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '123009',
        //     agentPhone: '0913393389',
        //     agentName: 'Trần Tất Thắng',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS86176750"
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '131597',
        //     agentPhone: '0985668994',
        //     agentName: 'Nguyễn Văn Kỷ',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS20497557",
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '177385',
        //     agentPhone: '0913981056',
        //     agentName: 'Nguyễn Thị Hồng',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS20497557",
        //     status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '262518',
        //     agentPhone: '0819333616',
        //     agentName: 'Lý Thị Sinh',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     requestId: "DS93492290",
        //     // status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '1049413',
        //     agentPhone: '0918643644',
        //     agentName: 'Trương Thị Huỳnh Như',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS20497557",
        //     status: 'ALL'
        // }

        // let json = {
        //     sourceSystem: "DSuccess",
        //     group: "GROUP_AD_AGENT_DLVN",
        //     platform: "Web",
        //     // poClientId: getSession(CLIENT_ID),
        //     agentCode: '506216',
        //     agentPhone: '0989434684',
        //     agentName: 'Ngô Thị Hồng Loan',
        //     deviceId: getDeviceId(),
        //     processType: "Claim",
        //     // requestId: "DS20497557",
        //     // status: 'ALL'
        // }


        let ensData = AES256.encrypt(JSON.stringify(json), COMPANY_KEY3);
        setState(prevState => ({ ...prevState, data: ensData }));

    }, []);

    return (
        <>
            {!isEmpty(state.data) &&
                <DConnectSDK data={state.data}/>
            }
        </>
    );
};

export default EClaimSDK;
