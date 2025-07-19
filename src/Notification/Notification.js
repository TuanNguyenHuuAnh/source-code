import 'antd/dist/antd.min.css';
import React, { Component } from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    DCID,
    FE_BASE_URL,
    USER_LOGIN,
    EDOCTOR_CODE,
    EDOCTOR_ID,
    DATA_SECTION,
    POL_LI_LISTCLAIM_ND13_CLIENT,
    LIITEM,
    COMPANY_KEY
} from '../constants';
import { fetchTagsData, getNotification, synchronizedNotiAPIService } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import { getDeviceId, getEnsc, getSession, getLinkPartner, setSession } from '../util/common';
import HTMLReactParser from "html-react-parser";
import { isEmpty } from "lodash";

class Notification extends Component {
    constructor(props) {
        super(props);
        const today = new Date(),
            date = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
        this.state = {
            jsonInput: {
                jsonDataInput: {
                    NotificationID: '',
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'UpdateNotification',
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    CreatedDate: '',
                    DeviceId: getDeviceId(),
                    Company: 'mcp_dlvn',
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'Notification',
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    CreatedDate: '',
                    DeviceId: getDeviceId(),
                    Company: '',
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonResponse: null,
            ClientProfile: [],
            isTTHD: true,
            isTTC: false,
            isView: '',
            gotoClaim: false,
            gotoPolicy: false,
            gotoPayment: false,
            isClaim: false,
            isPolicy: false,
            isPayment: false,
            isMobile: window.innerWidth <= 768,

        }
        this.handlerSetWrapperRef = this.setWrapperRef.bind(this);
        this.checkHideNotification = this.checkHideNotification.bind(this);
    }

    updateClientProfile(ClientProfile) {
        if (ClientProfile) {
            this.setState({ ClientProfile });
        } else {
            this.setState({ ClientProfile: [] });
        }
    }

    componentDidMount() {
        document.addEventListener("mousedown", this.checkHideNotification);
        window.addEventListener('resize', this.handleResize);
        this.updateClientProfile(this.props.clientProfile);

    }

    componentWillUnmount() {
        document.removeEventListener("mousedown", this.checkHideNotification);
        window.removeEventListener('resize', this.handleResize);
    }

    componentDidUpdate(prevProps, prevState) {
        if (this.props.clientProfile !== prevProps.clientProfile) {
            this.updateClientProfile(this.props.clientProfile);
        }
    }

    handleResize = () => {
        this.setState({
            isMobile: window.innerWidth <= 768,
        });
    };


    setWrapperRef(node) {
        this.wrapperSucceededRef = node;
    }

    checkHideNotification = (event) => {
        if (this.wrapperSucceededRef && !this.wrapperSucceededRef.contains(event.target)) {
            this.props.hideNotification();
        }
    }

    render() {

        const jsonState = this.state;
        const buttonClickTTHD = () => {
            document.getElementById('TTHDTab').className = "notification-card__head-item active";
            document.getElementById('TTCTab').className = "notification-card__head-item";
            jsonState.isTTHD = true;
            jsonState.isTTC = false;
            this.setState(jsonState);
        };
        const buttonClickTTC = () => {
            document.getElementById('TTHDTab').className = "notification-card__head-item";
            document.getElementById('TTCTab').className = "notification-card__head-item active";
            jsonState.isTTHD = false;
            jsonState.isTTC = true;
            this.setState(jsonState);
        };

        const getPathByDirection = (notification, data) => {
            const direction = notification.Direction;
            console.log("direction", direction);
            console.log('data=', data);
            let path = '';

            if (direction?.toString() === "34.2") {
                path = '/utilities/policy-payment-la';
            }
            else if (direction?.toString() === "43") {
                path = '/utilities/contact';
            } else if (direction?.toString() === "7.24") {
                path = '/claim-history';
            } else if (direction?.toString() === "5.20") {
                path = '/followup-claim-info';
            } else if (direction?.toString() === "48") {
                path = '/reinstatement';
                if (notification?.JumpToIndex) {
                    path = path + '/' + notification?.JumpToIndex;
                }
            } else if (direction?.toString() === "2.1") {
                path = '/mypolicyinfo';
            } else if (direction?.toString() === "74") {
                path = '/update-personal-info';
            }
            else {
                const numericPart = direction.split('.');
                if (!isEmpty(numericPart)) {
                    // Check if the direction includes '2'
                    const shouldJumpToIndex = direction.includes(numericPart[0]);

                    // Find the matching data
                    const matchedData = data.find(item => item.index === direction);

                    // Get the path from the matched data
                    path = matchedData ? matchedData.path : '/';

                    // If shouldJumpToIndex is true and path exists, append 'JumpToIndex'
                    if (shouldJumpToIndex && path && notification.JumpToIndex && numericPart[1]) {
                        path += '/' + notification.JumpToIndex + '/' + numericPart[1];
                    }

                }
            }
            return path;
        }

        const viewNoti = async (index, item, path) => {
            let pathUrl = path;
            console.log('data', item);
            console.log('path', path);
            switch (item.Type) {
                case '1':
                    pathUrl = "/mypolicyinfo";
                    break;
                case '4':
                    pathUrl = "/followup-claim-info";
                    break;
                default:
                    break;
            }

            if (item?.Project && item?.Project.toUpperCase() === "EDOCTOR") {
                try {
                    const resultUpdateNotiAPI = await updateNotiAPI(item?.MessageID);
                    const resultSynchronizedNotiAPI = await synchronizedNotiAPI(item?.TrackingID);

                    if (resultUpdateNotiAPI?.Response?.Result === 'true' && resultSynchronizedNotiAPI.code === 104000) {
                        setTimeout(() => {
                            handleEdoctorRedirect(path);
                        }, 500);
                    }
                } catch (error) {
                    console.error("Error:", error);
                }
            } else if (item?.Project && item?.Project.toUpperCase() === "AKTIVOLABS") {
                // Do nothing
            } else {
                try {
                    const resultUpdateNotiAPI = await updateNotiAPI(item?.MessageID);
                    if (resultUpdateNotiAPI?.Response?.Result === 'true') {
                        handleNonEdoctorRedirect(item, pathUrl);
                    }
                } catch (error) {
                    console.error("Error:", error);
                }
            }
        };

        const handleEdoctorRedirect = (path) => {
            if (path !== '/') {
                const request = !getSession(ACCESS_TOKEN)
                    ? {
                        company: "DLVN",
                        partner_code: EDOCTOR_CODE,
                        deviceid: getDeviceId(),
                        timeinit: new Date().getTime()
                    }
                    : {
                        company: "DLVN",
                        partner_code: EDOCTOR_CODE,
                        partnerid: "",
                        deviceid: getDeviceId(),
                        dcid: getSession(DCID),
                        token: getSession(ACCESS_TOKEN),
                        having_partnerid: "NO",
                        timeinit: new Date().getTime()
                    };

                !getSession(ACCESS_TOKEN) ? getEnsc(request, FE_BASE_URL + path) : getLinkPartner(EDOCTOR_ID, FE_BASE_URL + path);
            } else {
                window.location.href = path;
            }
        };

        const handleNonEdoctorRedirect = (item, path) => {
            const notifAction = item?.NotifAction;
            if (notifAction === "OPEN_LINK") {
                window.open(item?.Direction, '_blank');
            } else if ((notifAction === "OPEN_FUNCTION") || (notifAction === "OPEN_SDK")) {
                let customPath = path;
                if ((item.Type === '4') && item?.JumpToIndex) {
                    let jumpArray = item?.JumpToIndex?.split('@');
                    if (!isEmpty(jumpArray)) {
                        let proccessType = jumpArray[0];
                        if (proccessType === 'CLAIM') {
                            if (item?.JumpToIndex) {
                                if (!isEmpty(jumpArray)) {
                                    let clientIds = jumpArray[1];
                                    let claimID = jumpArray[3];
                                    if (clientIds) {
                                        customPath = '/myclaim';
                                        customPath = customPath + '/' + clientIds + '-' + claimID;
                                    }
                                }
                            }
                        }
                    }


                } else {
                    if (!isEmpty(item?.Direction) && item?.Direction?.toString() !== "20") {
                        customPath = getPathByDirection(item, DATA_SECTION);

                    }
                }
                queueMicrotask(() => {
                    window.location.href = customPath;
                });

            } else if (notifAction === "OPEN_SURVEY") {
                const templateID = 1;
                const localURL = `/survey?TemplateID=${templateID}&TrackingID=${item.TrackingID}`;
                window.open(localURL, '_blank');
            } else {
                queueMicrotask(() => {
                    window.location.href = path;
                });
            }
        };


        const updateNotiAPI = (MessageID) => {
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.NotificationID = MessageID;
            const apiRequest = Object.assign({}, this.state.jsonInput);
            const result = getNotification(apiRequest);
            this.props.parentCallback(true);
            jsonState.isView = '1';
            this.setState(jsonState);
            return result;
        };

        const synchronizedNotiAPI = (MessageID) => {
            const jsonState = this.state;
            const requestData = {
                dcId: getSession(DCID) ? getSession(DCID) : "",
                notificationId: MessageID,
            };
            const result = synchronizedNotiAPIService(requestData);
            this.props.parentCallback(true);
            jsonState.isView = '1';
            this.setState(jsonState);
            return result
        }

        const isValidURL = (url) => {
            try {
                new URL(url);
                return true;
            } catch (error) {
                return false;
            }
        };

        const formatDate = (inputDate) => {
            const parts = inputDate.split('-');
            if (parts.length === 3) {
                const day = parts[0];
                const month = parts[1];
                const year = parts[2];
                return `${day}/${month}/${year}`;
            }
            return inputDate;
        }

        const formatString = (inputString) => {
            return inputString.charAt(0).toUpperCase() + inputString.slice(1).toLowerCase();
        }

        const renderItem = (item, index, path, notificationTexts) => {
            // const customCSS = (node, isView) => {
            //     if (node.name === 'p' && node.attribs.class === 'notif_title') {
            //         const requestNumberRegex = /Số yêu cầu:\s*<b>(\w+)<\/b>/;
            //         const matches = node.children[0]?.data.match(requestNumberRegex);
            //
            //         if (matches && matches.length === 2) {
            //             const requestNumber = matches[1];
            //             if (isView !== '0') {
            //                 return (
            //                     <div>
            //                         <p style={{ fontWeight: '600', fontSize: '14px' }}>{node.children[0]?.data}</p>
            //                         <p style={{ fontWeight: '800', fontSize: '14px' }}>Request Number: {requestNumber}</p>
            //                     </div>
            //                 );
            //             } else {
            //                 return (
            //                     <div>
            //                         <p style={{ fontWeight: '800', fontSize: '14px' }}>{node.children[0]?.data}</p>
            //                         <p style={{ fontWeight: '600', fontSize: '14px' }}>Request Number: {requestNumber}</p>
            //                     </div>
            //                 );
            //             }
            //         }
            //     }
            //     return node;
            // };
            const customCSS = (node, isView) => {
                if (node.name === 'p' && node.attribs.class === 'notif_title') {
                    if (isView !== '0') {
                        return <p style={{ fontWeight: '600', fontSize: '16px' }}>{node.children[0]?.data}</p>;
                    } else {
                        return <p style={{ fontWeight: '800', fontSize: '16px' }}>{node.children[0]?.data}</p>;
                    }
                }
                return node;
            };

            const parsedHTML = (content) => {
                return HTMLReactParser(content, {
                    replace: (node) => {
                        return customCSS(node, item.IsView);
                    },
                });
            }
            if (item.Type === '15') {
                let poID = '';
                let liItem = null;
                let customPath = path + 'myclaim';
                let isActiveLink = false;
                console.log('item?.Content=', item?.Content);
                console.log('item?.JumpToIndex=', item?.JumpToIndex);
                let jumpArray = item?.JumpToIndex?.split('@');
                if (!isEmpty(jumpArray)) {
                    let proccessType = jumpArray[0];
                    if (proccessType === 'CLAIM') {
                        const liClientProfile = this.props.LIClientProfile ? this.props.LIClientProfile : JSON.parse(getSession(POL_LI_LISTCLAIM_ND13_CLIENT));
                        console.log('liClientProfile=', liClientProfile);
                        if (!isEmpty(liClientProfile)) {
                            if (item?.JumpToIndex) {
                                if (!isEmpty(jumpArray)) {
                                    let clientIds = jumpArray[1];
                                    let claimID = jumpArray[3];
                                    if (clientIds) {
                                        let clientIdsArr = clientIds.split(',');
                                        if (!isEmpty(clientIdsArr)) {
                                            poID = clientIdsArr[0];
                                            customPath = '/myclaim';
                                            customPath = customPath + '/' + poID + '-' + claimID;
                                        }
                                    }
                                }
                            }


                        }
                    } else if (proccessType === 'CCI') {
                        if (item?.JumpToIndex) {
                            if (!isEmpty(jumpArray)) {
                                let trackingID = jumpArray[2];
                                if (trackingID) {
                                    customPath = path + 'update-personal-info' + '/' + trackingID;;
                                    console.log('customPath=', customPath);
                                }
                            }
                        }
                    } else {
                        customPath = path + 'update-policy-info';
                        let clientIds = jumpArray[1];
                        let userLogin = '';
                        if (clientIds) {
                            let clientIdsArr = clientIds.split(',');
                            if (!isEmpty(clientIdsArr)) {
                                userLogin = clientIdsArr[0];
                            }
                        }
                        let trackingId = jumpArray[2];
                        if (userLogin && trackingId) {
                            customPath = customPath + '/' + proccessType + '-' + userLogin + '-' + trackingId;
                        }
                    }
                }


                // if (isActiveLink) {
                return (
                    <div
                        style={{ cursor: 'pointer' }}
                        className="notice-tab"
                        id={"dataTab" + (item.NotifGroup ? (item.NotifGroup === 'HEALTH_WELLBEING' ? 'TTC' : 'TTHD') : item.Type <= '4' ? 'TTHD' : 'TTC')}
                    >
                        <Link style={{ display: 'flex' }} to={customPath} onClick={() => { setSession(LIITEM + poID.trim(), JSON.stringify(liItem)); this.props.hideNotification() }}>
                            <div
                                // onClick={() => onClickItemLink(item.Type, path)}
                                // to={getItemLink(item.Type, path)}
                                style={{ width: 'calc(100% - 12px)' }}
                            >
                                {!item.ContentFull ? (
                                    <p className="notification_content">
                                        {parsedHTML(item.Content)}
                                    </p>
                                ) : (
                                    notificationTexts &&
                                    notificationTexts.map((text, index) => (
                                        <div key={index}>
                                            {parsedHTML(text)}
                                            {index < notificationTexts.length - 1 && <br />}
                                        </div>
                                    ))
                                )}

                                <div style={{ display: 'flex', alignItems: 'center' }}>
                                    {item.NotifCategory && <p style={{ fontSize: '12px', color: '#727272' }}>
                                        {`${formatString(item.NotifCategory)} - `}
                                    </p>}
                                    <p className="notification_date" style={{ fontSize: '12px', color: '#727272' }}>
                                        {formatDate(item.CreatedDate)}
                                    </p>
                                </div>

                            </div>
                            {item.IsView === '0' && <div className="dot"></div>}
                        </Link>
                    </div>
                );

                // } 
            } else {
                return (
                    <div
                        style={{ cursor: 'pointer' }}
                        className="notice-tab"
                        id={"dataTab" + (item.NotifGroup ? (item.NotifGroup === 'HEALTH_WELLBEING' ? 'TTC' : 'TTHD') : item.Type <= '4' ? 'TTHD' : 'TTC')}
                        onClick={() => {
                            viewNoti(index, item, path);
                        }}
                    >
                        <div
                            // onClick={() => onClickItemLink(item.Type, path)}
                            // to={getItemLink(item.Type, path)}
                            style={{ width: 'calc(100% - 12px)', fontSize: '16px', fontWeight: '500', lineHeight: '22px' }}
                        >
                            {!item.ContentFull ? (
                                <p className="notification_content">
                                    {parsedHTML(item.Content)}
                                </p>
                            ) : (
                                notificationTexts &&
                                notificationTexts.map((text, index) => (
                                    <div key={index}>
                                        {parsedHTML(text)}
                                        {index < notificationTexts.length - 1 && <br />}
                                    </div>
                                ))
                            )}

                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                {item.NotifCategory && <p style={{ fontSize: '12px', color: '#727272' }}>
                                    {`${formatString(item.NotifCategory)} - `}
                                </p>}
                                <p className="notification_date" style={{ fontSize: '12px', color: '#727272' }}>
                                    {formatDate(item.CreatedDate)}
                                </p>
                            </div>

                        </div>
                        {item.IsView === '0' && <div className="dot"></div>}
                    </div>
                );
            }
        }

        return (
            <div>
                <div className="notification-card" ref={this.handlerSetWrapperRef}>
                    <div className="notification-card__head">
                        <div className="notification-card__head-item active" onClick={() => buttonClickTTHD()}
                            id="TTHDTab">
                            <p>thông tin hợp đồng</p>
                        </div>
                        <div className="notification-card__head-item" onClick={() => buttonClickTTC()} id="TTCTab">
                            <p>thông tin chung</p>
                        </div>
                    </div>
                    <div className="notification-card__body">
                        <div className="notice-info-wrapper" id="contract-info-noti"
                            style={{ height: '300px', overflow: 'auto' }}>
                            {this.state.ClientProfile !== null && this.state.ClientProfile.map((item, index) => {
                                const parsedUrl = item?.Direction && isValidURL(item?.Direction) ? new URL(item?.Direction) : null;
                                // const path = parsedUrl?.pathname ? parsedUrl?.pathname : "/";
                                const path = parsedUrl?.pathname ? parsedUrl.href.replace(FE_BASE_URL, '') : "/";
                                const notificationTexts = item?.ContentFull?.split('</p>');

                                return (
                                    <div key={index}>
                                        {(item.NotifGroup ? item.NotifGroup !== 'HEALTH_WELLBEING' : parseInt(item.Type, 10) <= 4) && this.state.isTTHD === true && (
                                            renderItem(item, index, path, notificationTexts, console.log('nah', path))
                                        )}

                                        {(item.NotifGroup ? (item.NotifGroup === 'HEALTH_WELLBEING') : parseInt(item.Type, 10) > 4) && this.state.isTTC === true && (
                                            renderItem(item, index, path, notificationTexts)
                                        )}
                                    </div>
                                );
                            })}
                        </div>
                        <div className="notice-info-wrapper basic-displaynone" id="general-info-noti"></div>
                    </div>
                </div>
            </div>
        );
    }

}


export default Notification;