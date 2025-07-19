import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
//import './index.css';
import App from './app/App';
import Routes from "./Routes";
import {NotificationProvider} from "./Notification/NotificationProvider";

ReactDOM.render(
    <BrowserRouter>
        <NotificationProvider>
            <Routes/>
            <App/>
        </NotificationProvider>
    </BrowserRouter>,
    document.getElementById('root')
);



