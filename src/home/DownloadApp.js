import React from 'react';
import {DOWNLOAD_APP_ANDROID, DOWNLOAD_APP_IOS} from '../constants/index';

function DownloadApp() {
    return (

        <section className="scdownloadapp">
            <div className="container">
                <div className="download-content" style={{ paddingTop: '60px'}}>
                    <p className="title-local basic-semibold" style={{lineHeight: '20px', margin: '0px'}}>
                        Tải ứng dụng Dai-ichi Connect về điện thoại của bạn
                    </p>
                    <br/><br/>
                    <div className="app">
                        <div className="qr">
                            <img src="../img/qr.png" alt=""/>
                        </div>
                        <div className="app__item">
                            <a href={DOWNLOAD_APP_ANDROID} target="_blank" rel="noreferrer"
                            >
                                <div className="icon-warpper top">
                                    <img src="../img/googleplay.png" alt=""/></div
                                >
                            </a>
                            <a href={DOWNLOAD_APP_IOS} target="_blank" rel="noreferrer"
                            >
                                <div className="icon-warpper">
                                    <img src="../img/applestore.png" alt=""/></div
                                >
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default DownloadApp;