import React, {createContext, useContext, useEffect, useState} from 'react';
import {getDeviceId, getSession} from "../util/common";
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN} from "../constants";
import {CPGetClientProfileByCLIID, getNotification} from "../util/APIUtils";

const NotificationContext = createContext();

export const NotificationProvider = ({children}) => {
    const [notificationCount, setNotificationCount] = useState(0);
    const [clientProfileTemp, setClientProfileTemp] = useState(null);

    const convertDataToString = (data) => {
        const filteredData = data
            .filter(item => item.MessageID)
            .map(item => item.MessageID);

        return filteredData.join(',');
    };

    const callNotificationAPI = () => {
        const submitRequest = {
            jsonDataInput: {
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'Notification',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID) ? getSession(CLIENT_ID) : '',
                CreatedDate: '',
                DeviceId: getDeviceId(),
                Company: '',
                Project: 'mcp',
                UserLogin: getSession(USER_LOGIN)
            }
        }
        const apiRequest = Object.assign({}, submitRequest);
        getNotification(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.ErrLog === 'SUCCESSFUL') {
                setClientProfileTemp(Response.ClientProfile);
                let countNotiTemp = 0;
                for (let i = 0; i < Response.ClientProfile.length; i++) {
                    if (Response.ClientProfile[i].IsView === '0') {
                        countNotiTemp++;
                    }
                }
                setNotificationCount(countNotiTemp);
            }
            if (Response.ErrLog === 'EMPTY') {
                setClientProfileTemp(null);
                setNotificationCount(0);
            }
        }).catch(error => {

        });
    }

    const checkAndResetStateNoti = () => {
        setClientProfileTemp(null);
        setNotificationCount(0);
    };

    const handleReadAllNotifications = (requestParam) => {
        const reqNoti = {
            jsonDataInput: {
                Action: 'UpdateNotificationList',
                Project: 'mcp',
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                UserLogin: getSession(USER_LOGIN),
                ClientID: getSession(CLIENT_ID),
                APIToken: getSession(ACCESS_TOKEN),
                FilterStatus: '',
                FilterPolicy: '',
                ListNotificationID: convertDataToString(requestParam),
            }
        };

        const apiRequest = Object.assign({}, reqNoti);

        CPGetClientProfileByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.Result === 'true') {
                callNotificationAPI();
            }

        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    };

    // useEffect(() => {
    //     callNotificationAPI();
    // }, []);

    return (
        <NotificationContext.Provider value={{notificationCount, clientProfileTemp, handleReadAllNotifications, callNotificationAPI, checkAndResetStateNoti}}>
            {children}
        </NotificationContext.Provider>
    );
};

export const useNotificationContext = () => {
    return useContext(NotificationContext);
};
