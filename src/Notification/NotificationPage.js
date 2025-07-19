// import 'antd/dist/antd.min.css';
// import '../claim.css';


import React, {useEffect, useState} from "react";

import {getDeviceId, getEnsc, getLinkPartner, getSession, isLoggedIn, trackingEvent} from "../util/common";
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    DCID,
    EDOCTOR_CODE,
    EDOCTOR_ID,
    FE_BASE_URL,
    PageScreen,
    SUB_CATEGORY_ICON,
    USER_LOGIN
} from "../constants";
import {CPSaveLog, getNotification, synchronizedNotiAPIService} from '../util/APIUtils';
import HTMLReactParser from "html-react-parser";
import {cloneDeep} from "lodash/lang";
import iconBellNoti from "../img/notificationIcon/iconBellNoti.svg";
import iconMarkAllRead from "../img/icon/iconMarkAllRead.svg";
import {useNotificationContext} from "./NotificationProvider";
import {withRouter} from "react-router-dom";

export const TABS = Object.freeze({
    CONTRACT: 'contract',
    HEALTHWELLBEING: 'health',
    OTHER: 'other',
});

const NotificationPage = () => {
    const {handleReadAllNotifications, clientProfileTemp} = useNotificationContext();
    const [currentTab, setCurrentTab] = useState(window.location.pathname === '/utilities/policy-payment-la' ? TABS.HEALTHWELLBEING : TABS.CONTRACT);

    const [notiRequestData] = useState({
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
    });

    const [displayCounts, setDisplayCounts] = useState({
        contract: 8,
        health: 8,
        other: 8,
    });
    const [displayedNotifications, setDisplayedNotifications] = useState([]);

    useEffect(() => {
        const filterAndSliceNotifications = () => {
            const filteredNotifications = clientProfileTemp !== null
                ? clientProfileTemp.filter(item => {
                    switch (currentTab) {
                        case TABS.CONTRACT:
                            return (parseInt(item.Type, 10) <= 5 && item.NotifGroup && item.NotifGroup !== 'HEALTH_WELLBEING');
                        case TABS.HEALTHWELLBEING:
                            return (item.NotifGroup && item.NotifGroup === 'HEALTH_WELLBEING');
                        case TABS.OTHER:
                            return ((item.NotifGroup && item.NotifGroup !== 'HEALTH_WELLBEING') || !item.NotifGroup);
                        default:
                            return false;
                    }
                })
                : [];

            const newDisplayedNotifications = filteredNotifications.slice(0, displayCounts[currentTab]);
            setDisplayedNotifications(newDisplayedNotifications);
        };

        filterAndSliceNotifications();
    }, [clientProfileTemp, currentTab, displayCounts]);

    const handleLoadMore = () => {
        setDisplayCounts({
            ...displayCounts,
            [currentTab]: displayCounts[currentTab] + 8,
        });
    };

    useEffect(() => {
        cpSaveLog(`Web_Open_${PageScreen.GUIDE_PAY_FEE}`);
        trackingEvent(
            "Tiện ích",
            `Web_Open_${PageScreen.GUIDE_PAY_FEE}`,
            `Web_Open_${PageScreen.GUIDE_PAY_FEE}`,
        );
        return () => {
            cpSaveLog(`Web_Close_${PageScreen.GUIDE_PAY_FEE}`);
            trackingEvent(
                "Tiện ích",
                `Web_Close_${PageScreen.GUIDE_PAY_FEE}`,
                `Web_Close_${PageScreen.GUIDE_PAY_FEE}`,
            );
        };
    }, []);

    const cpSaveLog = (functionName) => {
        const masterRequest = {
            jsonDataInput: {
                OS: "Web",
                APIToken: getSession(ACCESS_TOKEN),
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                DeviceToken: "",
                function: functionName,
                Project: "mcp",
                UserLogin: getSession(USER_LOGIN)
            }
        }
        CPSaveLog(masterRequest).then(res => {
            // console.log(res);
        }).catch(error => {
            // console.log(error);
        });
    };

    const onChangeTab = (tabId) => {
        setCurrentTab(tabId);
    };

    const formatDate = (inputDate) => {
        const parts = inputDate.split('-');

        if (parts.length === 3) {
            const day = parseInt(parts[0], 10);
            const month = parseInt(parts[1], 10);
            const year = parseInt(parts[2], 10);

            const currentDate = new Date();
            const inputDateObj = new Date(year, month - 1, day); // Lưu ý: Tháng trong JavaScript là từ 0 đến 11, nên trừ đi 1

            if (
                inputDateObj.getDate() === currentDate.getDate() &&
                inputDateObj.getMonth() === currentDate.getMonth() &&
                inputDateObj.getFullYear() === currentDate.getFullYear()
            ) {
                return 'Hôm nay';
            }

            return `${day}/${month}/${year}`;
        }

        return inputDate;
    };

    const formatString = (inputString) => {
        return inputString.charAt(0).toUpperCase() + inputString.slice(1).toLowerCase();
    }

    const getSubCategoryIcon = (subCategoryName) => {
        let dynamicPath = require(`../img/notificationIcon/icon_logo.svg`).default;
        const foundSubCategory = SUB_CATEGORY_ICON.find(item => removeAccentsAndLowercase(item.subCategoryName) === removeAccentsAndLowercase(subCategoryName));

        if (foundSubCategory) {
            dynamicPath = require(`../img/notificationIcon/${foundSubCategory.subCategoryIcon}`).default;
            return dynamicPath;
        }

        return dynamicPath;
    };

    const removeAccentsAndLowercase = (str) => {
        return str
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .replace(/đ/g, "d")
            .toLowerCase();
    };

    const updateNotiAPI = (MessageID) => {
        let notiRequestCloneDeep = cloneDeep(notiRequestData);
        notiRequestCloneDeep.jsonDataInput.NotificationID = MessageID;

        const apiRequest = Object.assign({}, notiRequestCloneDeep);
        return getNotification(apiRequest);
    };

    const isValidHTML = (htmlString) => {
        try {
            new DOMParser().parseFromString(htmlString, 'text/html');
            return true;
        } catch (e) {
            return false;
        }
    };

    const synchronizedNotiAPI = (MessageID) => {
        const requestData = {
            dcId: getSession(DCID) ? getSession(DCID) : "",
            notificationId: MessageID,
        };
        return synchronizedNotiAPIService(requestData)
    }

    const viewNoti = async (index, item, path) => {
        let pathUrl = path;

        switch (item.Type) {
            case '1':
                pathUrl = "/mypolicyinfo";
                break;
            case '4':
                pathUrl = "/followup-claim-info"
                break;
            default:
                break;
        }

        if (item?.Project && (item?.Project.toUpperCase() === "EDOCTOR")) {
            try {
                const resultUpdateNotiAPI = await updateNotiAPI(item?.MessageID);
                const resultSynchronizedNotiAPI = await synchronizedNotiAPI(item?.TrackingID);

                if (resultUpdateNotiAPI?.Response?.Result === 'true' && resultSynchronizedNotiAPI.code === 104000) {
                    setTimeout(() => {
                        if (path !== '/') {
                            let request = '';
                            if (!getSession(ACCESS_TOKEN)) {
                                request = {
                                    company: "DLVN",
                                    partner_code: EDOCTOR_CODE,
                                    deviceid: getDeviceId(),
                                    timeinit: new Date().getTime()
                                }
                                getEnsc(request, FE_BASE_URL + path);
                            } else {
                                request = {
                                    company: "DLVN",
                                    partner_code: EDOCTOR_CODE,
                                    partnerid: "",
                                    deviceid: getDeviceId(),
                                    dcid: getSession(DCID),
                                    token: getSession(ACCESS_TOKEN),
                                    having_partnerid: "NO",
                                    timeinit: new Date().getTime()
                                }
                                getLinkPartner(EDOCTOR_ID, FE_BASE_URL + path);

                            }
                        } else {
                            window.location.href = path;
                        }
                    }, 500);
                }
            } catch (error) {
                console.error("Error:", error);
            }
        } else {
            try {
                const resultUpdateNotiAPI = await updateNotiAPI(item?.MessageID);
                if (resultUpdateNotiAPI?.Response?.Result === 'true') {
                    if (item?.NotifAction && item?.NotifAction === "OPEN_LINK") {
                        window.open(item?.Direction, '_blank');
                    } else {
                        setTimeout(() => {
                            window.location.href = path;
                        }, 500);
                    }
                }
            } catch (error) {
                console.error("Error:", error);
            }
        }
    };

    const isValidURL = (url) => {
        try {
            new URL(url);
            return true;
        } catch (error) {
            return false;
        }
    };

    const renderItem = (item, index, path, notificationTexts) => {
        const customCSS = (node, isView) => {
            if (node.name === 'p' && node.attribs.class === 'notif_title') {
                if (isView !== '0') {
                    return <p style={{fontWeight: '600', fontSize: '14px'}}>{node.children[0]?.data}</p>;
                } else {
                    return <p style={{fontWeight: '800', fontSize: '14px'}}>{node.children[0]?.data}</p>;
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

        return (
            <div
                style={{cursor: 'pointer'}}
                className="notice-tab"
                onClick={() => {
                    viewNoti(index, item, path);
                }}
            >
                <img style={{marginRight: 6}}
                     src={getSubCategoryIcon(item.NotifCategory)}
                     alt="icon-noti-contact"/>
                <div style={{width: 'calc(100% - 12px)', fontSize: '14px'}}>
                    {!item.ContentFull ? (
                        <p className="notification_content" style={{fontSize: '14px'}}>
                            {isValidHTML(item.Content) ? parsedHTML(item.Content) : item.Content}
                        </p>
                    ) : (
                        notificationTexts &&
                        notificationTexts.map((text, index) => (
                            <div key={index}>
                                <p className="notification_content"
                                   style={{fontSize: '14px'}}>{isValidHTML(text) ? parsedHTML(text) : text}</p>
                                {index < notificationTexts.length - 1 && <br/>}
                            </div>
                        ))
                    )}
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        {item.NotifCategory && <p style={{fontSize: '12px', color: '#727272'}}>
                            {`${formatString(item.NotifCategory)} - `}
                        </p>}
                        <p className="notification_date" style={{fontSize: '12px', color: '#727272'}}>
                            {formatDate(item.CreatedDate)}
                        </p>
                    </div>

                </div>
                {item.IsView === '0' && <div className="dot"></div>}
            </div>
        );
    }

    return (
        <main className={isLoggedIn() ? "logined" : ""}>
            <div className="main-warpper page-ten">
                {/* Route display */}
                <section className="scbreadcrums">
                    <div className="container">
                        <div className="breadcrums basic-white">
                            <div className="breadcrums__item">
                                <p>Trang chủ</p>
                                <span>&gt;</span>
                            </div>
                            <div className="breadcrums__item">
                                <p>Thông báo</p>
                                <span>&gt;</span>
                            </div>
                        </div>
                    </div>
                </section>
                {/* Banner */}
                <div className="scthongbao">
                    <h1>Thông báo</h1>
                </div>
                {/* Main page */}
                <div className="notification-page">
                    <div className="tab-pane-container">
                        {/* Tabs */}
                        <section className="policy-menu">
                            <button
                                className={currentTab === TABS.CONTRACT ? "policy-menu__item active" : "policy-menu__item"}
                                onClick={() => onChangeTab(TABS.CONTRACT)}>
                                <h2 className="notification-card-wrapper">HỢP
                                    ĐỒNG{clientProfileTemp?.filter(item => (parseInt(item.Type, 10) <= 5 && item.NotifGroup && item.NotifGroup !== 'HEALTH_WELLBEING' && item.IsView === '0')).length > 0 &&
                                        <div className="circle-frame">{
                                            clientProfileTemp?.filter(item => (parseInt(item.Type, 10) <= 5 && item.NotifGroup && item.NotifGroup !== 'HEALTH_WELLBEING' && item.IsView === '0')).length > 9 ? "9+" :
                                                clientProfileTemp?.filter(item => (parseInt(item.Type, 10) <= 5 && item.NotifGroup && item.NotifGroup !== 'HEALTH_WELLBEING' && item.IsView === '0')).length
                                        }</div>}</h2>

                            </button>

                            <button
                                className={currentTab === TABS.HEALTHWELLBEING ? "policy-menu__item active" : "policy-menu__item"}
                                onClick={() => onChangeTab(TABS.HEALTHWELLBEING)}>
                                <h2 className="notification-card-wrapper">SỐNG VUI
                                    KHỎE {clientProfileTemp?.filter(item => item.NotifGroup === "HEALTH_WELLBEING" && item.IsView === '0').length > 0 &&
                                        <div
                                            className="circle-frame">{clientProfileTemp?.filter(item => item.NotifGroup === "HEALTH_WELLBEING" && item.IsView === '0')?.length > 9 ? '9+' : clientProfileTemp?.filter(item => item.NotifGroup === "HEALTH_WELLBEING" && item.IsView === '0')?.length}</div>}</h2>
                            </button>

                            <button
                                className={currentTab === TABS.OTHER ? "policy-menu__item active" : "policy-menu__item"}
                                onClick={() => onChangeTab(TABS.OTHER)}>
                                <h2 className="notification-card-wrapper">KHÁC{clientProfileTemp?.filter(item => (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup) && item.IsView === '0').length > 0 ?
                                    <div className="circle-frame">{
                                        clientProfileTemp?.filter(item => (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup) && item.IsView === '0').length > 9 ? "9+" : clientProfileTemp?.filter(item => ((item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup) && item.IsView === '0')).length
                                    }</div> : <div className="circle-frame" style={{display: 'none'}}/>}</h2>
                            </button>

                        </section>
                        <div className="tab-pane-container-content">
                            <div id="noti_contract"
                                 className={currentTab === TABS.CONTRACT ? "tabpane-tab active" : "tabpane-tab"}>
                                <div className="tab-pane-container-content-one" style={{padding: '24px 24px'}}>
                                    <div className="notification-card__body">
                                        <div className="notice-info-wrapper" id="contract-info-noti"
                                        >
                                            {clientProfileTemp !== null && clientProfileTemp.some((item) => {
                                                    return parseInt(item.Type, 10) <= 5 && item.NotifGroup !== 'HEALTH_WELLBEING';
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{
                                                        padding: '16px 0',
                                                        cursor: 'pointer',
                                                        justifyContent: 'flex-end'
                                                    }}
                                                    onClick={() => handleReadAllNotifications(clientProfileTemp !== null && clientProfileTemp.filter((item) => {
                                                        return parseInt(item.Type, 10) <= 5 && item.NotifGroup !== 'HEALTH_WELLBEING'}))}>
                                                        <span className="arrow">
                                                                    <img src={iconMarkAllRead} alt="" style={{
                                                                        transform: 'rotate(90deg)',
                                                                        width: 20,
                                                                        height: 20
                                                                    }}/>
                                                                </span>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label"
                                                       style={{fontSize: 14}}>Đọc
                                                        tất cả</p>
                                                </div>
                                            }
                                            {displayedNotifications.map((item, index) => {
                                                const parsedUrl = item?.Direction && isValidURL(item?.Direction) ? new URL(item?.Direction) : null;
                                                const path = parsedUrl?.pathname ? parsedUrl?.pathname : "/";
                                                const notificationTexts = item?.ContentFull?.split('</p>');
                                                return renderItem(item, index, path, notificationTexts);
                                            })}
                                            {clientProfileTemp !== null &&
                                                clientProfileTemp.length > 8 && displayedNotifications.length >= displayCounts.contract && // Check if the length is greater than to 1
                                                clientProfileTemp.some((item) => {
                                                    return parseInt(item.Type, 10) <= 5 && item.NotifGroup !== 'HEALTH_WELLBEING';
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{padding: '16px 0', cursor: 'pointer'}}
                                                    onClick={() => handleLoadMore()}>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label">Xem
                                                        thêm</p>
                                                    <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""
                                                                         style={{transform: 'rotate(90deg)'}}/>
                                                                </span>
                                                </div>
                                            }
                                            {clientProfileTemp !== null &&
                                                !clientProfileTemp.some((item) => {
                                                    return parseInt(item.Type, 10) <= 5 && item.NotifGroup !== 'HEALTH_WELLBEING';
                                                }) &&
                                                <div className="flex-center"
                                                     style={{flexDirection: "column", marginTop: "4rem"}}>
                                                    <img src={iconBellNoti} alt="icon-bell-noti"/>
                                                    <p style={{
                                                        fontSize: '18px',
                                                        color: '#727272',
                                                        fontWeight: 500,
                                                        lineHeight: '24px',
                                                        marginTop: 16
                                                    }}>Không có thông báo nào</p>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="noti_healthWellBeing"
                                 className={currentTab === TABS.HEALTHWELLBEING ? "tabpane-tab active" : "tabpane-tab"}>
                                <div className="tab-pane-container-content-two" style={{padding: '24px 24px'}}>
                                    <div className="notification-card__body">
                                        <div className="notice-info-wrapper" id="contract-info-noti"
                                        >
                                            {clientProfileTemp !== null && clientProfileTemp.some((item) => {
                                                    return item.NotifGroup === 'HEALTH_WELLBEING';
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{
                                                        padding: '16px 0',
                                                        cursor: 'pointer',
                                                        justifyContent: 'flex-end'
                                                    }}
                                                    onClick={() => handleReadAllNotifications(clientProfileTemp !== null && clientProfileTemp.filter((item) => {
                                                        return item.NotifGroup === 'HEALTH_WELLBEING'}))}>
                                                        <span className="arrow">
                                                                    <img src={iconMarkAllRead} alt="" style={{
                                                                        transform: 'rotate(90deg)',
                                                                        width: 20,
                                                                        height: 20
                                                                    }}/>
                                                                </span>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label"
                                                       style={{fontSize: 14}}>Đọc
                                                        tất cả</p>
                                                </div>
                                            }

                                            {displayedNotifications.map((item, index) => {
                                                const parsedUrl = item?.Direction && isValidURL(item?.Direction) ? new URL(item?.Direction) : null;
                                                const path = parsedUrl?.pathname ? parsedUrl?.pathname : "/";
                                                const notificationTexts = item?.ContentFull?.split('</p>');
                                                return renderItem(item, index, path, notificationTexts);
                                            })}
                                            {clientProfileTemp !== null &&
                                                clientProfileTemp.length > 8 && displayedNotifications.length >= displayCounts.health && // Check if the length is greater than to 1
                                                clientProfileTemp.some((item) => {
                                                    return item.NotifGroup === 'HEALTH_WELLBEING';
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{padding: '16px 0', cursor: 'pointer'}}
                                                    onClick={() => handleLoadMore()}>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label">Xem
                                                        thêm</p>
                                                    <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""
                                                                         style={{transform: 'rotate(90deg)'}}/>
                                                                </span>
                                                </div>
                                            }
                                            {clientProfileTemp !== null &&
                                                !clientProfileTemp.some((item) => {
                                                    return item.NotifGroup === 'HEALTH_WELLBEING';
                                                }) &&
                                                <div className="flex-center"
                                                     style={{flexDirection: "column", marginTop: "4rem"}}>
                                                    <img src={iconBellNoti} alt="icon-bell-noti"/>
                                                    <p style={{
                                                        fontSize: '18px',
                                                        color: '#727272',
                                                        fontWeight: 500,
                                                        lineHeight: '24px',
                                                        marginTop: 16
                                                    }}>Không có thông báo nào</p>
                                                </div>
                                            }

                                        </div>
                                        <div className="notice-info-wrapper basic-displaynone"
                                             id="general-info-noti"></div>
                                    </div>
                                </div>
                            </div>

                            <div id="noti_healthWellBeing"
                                 className={currentTab === TABS.OTHER ? "tabpane-tab active" : "tabpane-tab"}>
                                <div className="tab-pane-container-content-three" style={{padding: '24px 24px'}}>
                                    <div className="notification-card__body">
                                        <div className="notice-info-wrapper" id="contract-info-noti"
                                        >
                                            {clientProfileTemp !== null && clientProfileTemp.some((item) => {
                                                    return (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup);
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{
                                                        padding: '16px 0',
                                                        cursor: 'pointer',
                                                        justifyContent: 'flex-end'
                                                    }}
                                                    onClick={() => handleReadAllNotifications(clientProfileTemp !== null && clientProfileTemp.filter((item) => {
                                                            return (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup)}))}>
                                                        <span className="arrow">
                                                                    <img src={iconMarkAllRead} alt="" style={{
                                                                        transform: 'rotate(90deg)',
                                                                        width: 20,
                                                                        height: 20
                                                                    }}/>
                                                                </span>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label"
                                                       style={{fontSize: 14}}>Đọc
                                                        tất cả</p>
                                                </div>
                                            }
                                            {displayedNotifications.map((item, index) => {
                                                const parsedUrl = item?.Direction && isValidURL(item?.Direction) ? new URL(item?.Direction) : null;
                                                const path = parsedUrl?.pathname ? parsedUrl?.pathname : "/";
                                                const notificationTexts = item?.ContentFull?.split('</p>');
                                                return renderItem(item, index, path, notificationTexts);
                                            })}
                                            {clientProfileTemp !== null &&
                                                clientProfileTemp.length > 8 && displayedNotifications?.length >= displayCounts.other && // Check if the length is greater than to 1
                                                clientProfileTemp.some((item) => {
                                                    return (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup);
                                                }) &&
                                                <div
                                                    className="flex-center card__footer-claim-details-wrapper"
                                                    style={{padding: '16px 0', cursor: 'pointer'}}
                                                    onClick={() => handleLoadMore()}>
                                                    <p className="primary-text basic-semibold card__footer-claim-details-label">Xem
                                                        thêm</p>
                                                    <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""
                                                                         style={{transform: 'rotate(90deg)'}}/>
                                                                </span>
                                                </div>
                                            }
                                            {clientProfileTemp !== null &&
                                                !clientProfileTemp.some((item) => {
                                                    return (item.NotifGroup !== 'HEALTH_WELLBEING' || !item.NotifGroup);
                                                }) &&
                                                <div className="flex-center"
                                                     style={{flexDirection: "column", marginTop: "4rem"}}>
                                                    <img src={iconBellNoti} alt="icon-bell-noti"/>
                                                    <p style={{
                                                        fontSize: '18px',
                                                        color: '#727272',
                                                        fontWeight: 500,
                                                        lineHeight: '24px',
                                                        marginTop: 16
                                                    }}>Không có thông báo nào</p>
                                                </div>
                                            }
                                        </div>
                                        <div className="notice-info-wrapper basic-displaynone"
                                             id="general-info-noti"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
};

export default NotificationPage;
