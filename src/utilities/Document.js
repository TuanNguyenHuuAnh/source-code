import React, {useEffect, useState} from "react";
import axios from 'axios';
import {saveAs} from 'file-saver'

import {
    FE_BASE_URL,
    MAGP_FILE_FOLDER_URL,
    API_BASE_URL,
    COMPANY_KEY2,
    ACCESS_TOKEN,
    AUTHENTICATION, CLIENT_ID, USER_LOGIN, PageScreen
} from '../constants';
import {Helmet} from "react-helmet";
import {CPSaveLog, getDoc} from '../util/APIUtils';
import {getDeviceId, getSession, isLoggedIn, trackingEvent} from "../util/common";
import {useParams} from "react-router-dom/cjs/react-router-dom";
import AES256 from 'aes-everywhere';
import { Link } from "react-router-dom";


const TABS = {
    COMMON: 'COMMON',
    OTHER: 'OTHER'
}

const InsParticipation = (props) => {
    const {key} = useParams();
    const [jsonInput] = useState({
        Action: "GetForm",
    });
    const [currentTab, setCurrentTab] = useState("");
    const [currentSubCategory, setCurrentSubCategory] = useState("");
    const [commonList, setCommonList] = useState([]);
    const [otherList, setOtherList] = useState([]);
    const [isDownloading, setIsDownloading] = useState(false);
    const [renderMeta, setRenderMeta] = useState(false)
    const changeTab = (tabName) => {
        setCurrentTab(tabName);
        setCurrentSubCategory("");
    };

    const expandSubCategory = (tabName, subCategoryId) => {
        setCurrentTab(tabName);
        setCurrentSubCategory(currentSubCategory === subCategoryId ? "" : subCategoryId);
    };

    const openFile = (event, fileUuid) => {
        event.preventDefault();
        window.open(MAGP_FILE_FOLDER_URL + fileUuid);
    };

    const saveFile = (event, title, fileUuid) => {
        event.preventDefault();
        let filePath = MAGP_FILE_FOLDER_URL + fileUuid;//encodeURIComponent(MAGP_FILE_FOLDER_URL + fileUuid);
        filePath = AES256.encrypt(filePath, COMPANY_KEY2);
        // console.log('a1=' + filePath);
        filePath = filePath.replaceAll('/', '|');
        // console.log('a2=' +filePath);
        let path = API_BASE_URL + '/v1/download/' + filePath;
        fetch(path).then(response => {
          response.blob().then(blob => {
            let url = window.URL.createObjectURL(blob);
            let a = document.createElement('a');
            a.href = url;
            a.download = title + '.pdf';
            a.click();
          });
        }).catch(error => {
          //alert(error);
          this.props.history.push('/maintainence');
        });
      }

    useEffect(() => {
        const apiRequest = {...jsonInput};
        getDoc(apiRequest)
            .then(Res => {
                let timeoutId;
                if (Res.data !== null) {
                    setRenderMeta(true);
                    setCurrentTab(TABS.COMMON);
                    timeoutId = setTimeout(() => {
                        scrollToElement(key);
                        setCurrentSubCategory("");
                    }, 500);
                    setCommonList(Res.data.length === 2 ? Res.data[0].children : []);
                    setOtherList(Res.data.length === 2 ? Res.data[1].children : []);

                    return () => {
                        clearTimeout(timeoutId);
                    }
                }
            })
            .catch(error => {
                setRenderMeta(true);
            });
    }, [key, jsonInput]);


    useEffect(() => {
        cpSaveLog(`Web_Open_${PageScreen.DOCUMENT}`);
        trackingEvent(
            "Tiện ích",
            `Web_Open_${PageScreen.DOCUMENT}`,
            `Web_Open_${PageScreen.DOCUMENT}`,
        );
        return () => {
            trackingEvent(
                "Tiện ích",
                `Web_Open_${PageScreen.DOCUMENT}`,
                `Web_Open_${PageScreen.DOCUMENT}`,
            );
            cpSaveLog(`Web_Close_${PageScreen.DOCUMENT}`);
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
            setRenderMeta(true);
        }).catch(error => {
            setRenderMeta(true);
        });
    };

    const scrollToElement = (key) => {
        const element = document.getElementById(key);

        if (element) {
            element.scrollIntoView({behavior: "smooth"});
        }
    };

    return (
        <main className={isLoggedIn() ? "logined" : ""}>
            {renderMeta &&
                <Helmet>
                    <title>Biểu mẫu – Dai-ichi Life Việt Nam</title>
                    <meta name="description"
                          content="Trang thông tin của Dai-ichi Life Việt Nam tổng hợp các biểu mẫu điều chỉnh, thanh toán quyền lợi và thực hiện yêu cầu giải quyết hợp đồng bảo hiểm."/>
                    <meta name="keywords" content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Biểu mẫu"/>
                    <meta name="robots" content="index, follow"/>
                    <meta property="og:type" content="website"/>
                    <meta property="og:url" content={FE_BASE_URL + "/utilities/document"}/>
                    <meta property="og:title" content="Biểu mẫu – Dai-ichi Life Việt Nam"/>
                    <meta property="og:description"
                          content="Trang thông tin của Dai-ichi Life Việt Nam tổng hợp các biểu mẫu điều chỉnh, thanh toán quyền lợi và thực hiện yêu cầu giải quyết hợp đồng bảo hiểm."/>
                    <meta property="og:image"
                          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                    <link rel="canonical" href={FE_BASE_URL + "/utilities/document"}/>
                </Helmet>
            }
            <div className="main-warpper">
                <section className="scbreadcrums">
                    <div className="container">
                        <div className="breadcrums basic-white">
                            <Link to="/" className="breadcrums__item">
                                <p>Trang chủ</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </Link>
                            <Link to="/utilities" className="breadcrums__item">
                                <p>Tiện ích</p>
                                <p className='breadcrums__item_arrow'>></p>
                            </Link>
                            <div className="breadcrums__item">
                                <p>Biểu mẫu</p>
                                <span>&gt;</span>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="scdieukhoan">
                    <div className="container">
                        <h1>Biểu mẫu</h1>
                    </div>
                </section>

                <div className="policy-container bieumau-container">
                    {/* Tabs */}
                    <section className="policy-menu">
                        <button
                            className={currentTab === TABS.COMMON ? "policy-menu__item active" : "policy-menu__item"}
                            onClick={() => changeTab(TABS.COMMON)}>
                            <h2>CÁC BIỂU MẪU THƯỜNG GẶP</h2>
                        </button>
                        <button
                            className={currentTab === TABS.OTHER ? "policy-menu__item active" : "policy-menu__item"}
                            onClick={() => changeTab(TABS.OTHER)}>
                            <h2>CÁC YÊU CẦU VỀ DỊCH VỤ, TIỆN ÍCH KHÁC</h2>
                        </button>
                    </section>

                    {/* Categories */}
                    <section className="policy-content bieumau-content">
                        {/* CÁC BIỂU MẪU THƯỜNG GẶP */}
                        <div className={currentTab === TABS.COMMON ?
                            "policy-content-wrapper policy-letter bieumau-page show" :
                            "policy-content-wrapper policy-letter bieumau-page"}>
                            {/* Sub-category */}
                            {commonList !== null && commonList !== undefined && commonList.length > 0 &&
                                commonList.map((commonCategory, index) => (
                                    <div className="bieumau-page__item">
                                        <h3>{commonCategory.name}</h3>
                                        <div className="content-wrapper">
                                            {commonCategory.children !== null && commonCategory.children !== undefined && commonCategory.children.length > 0 &&
                                                commonCategory.children.map((commonSubCategory, index) => (
                                                    <div
                                                        className={(currentTab === TABS.COMMON && currentSubCategory === commonSubCategory.id) ?
                                                            "content-item dropdown show" : "content-item dropdown"}>
                                                        {/* Sub-categories title*/}
                                                        <div className="dropdown__content"
                                                             onClick={() => expandSubCategory(TABS.COMMON, commonSubCategory.id)}>
                                                            <h4 style={{'width': '90%'}}>{commonSubCategory.name}</h4>
                                                            <div className="arrow">
                                                                <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                            </div>
                                                        </div>
                                                        {/* Documents */}
                                                        {commonSubCategory.children !== null && commonSubCategory.children !== undefined && commonSubCategory.children.length > 0 &&
                                                            commonSubCategory.children.map((document, index) => (
                                                                <div className="dropdown__items" id={commonCategory.name === "Yêu cầu Giải quyết quyền lợi bảo hiểm" ? "ResolveInsuranceContract" : null}>
                                                                    <div className="item">
                                                                        <div className="content">
                                                                            <div className="icon-wrapper">
                                                                                <img
                                                                                    src="/img/icon/10.1/10.1-icon-bieumau-dropdown.svg"
                                                                                    alt=""
                                                                                />
                                                                            </div>
                                                                            <p>{document.title}</p>
                                                                        </div>
                                                                        <div className="button-group">
                                                                            <button
                                                                                className="bieumau-button read-button"
                                                                                onClick={(event) => openFile(event, document.fileUuid)}>
                                                                                Xem
                                                                            </button>
                                                                            <button
                                                                                className="bieumau-button download-button"
                                                                                onClick={(event) => saveFile(event, document.title, document.fileUuid)}>
                                                                                {isDownloading ? "Đang tải..." : "Tải xuống"}
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            ))}
                                                    </div>
                                                ))}
                                        </div>
                                    </div>
                                ))}
                        </div>
                        {/* CÁC YÊU CẦU VỀ DỊCH VỤ, TIỆN ÍCH KHÁC */}
                        <div className={currentTab === TABS.OTHER ?
                            "policy-content-wrapper commitment-letter dichvu-page show" :
                            "policy-content-wrapper commitment-letter dichvu-page"}>
                            {/* Categories */}
                            {/* Sub-category */}
                            {otherList !== null && otherList !== undefined && otherList.length > 0 &&
                                otherList.map((otherCategory, index) => (
                                    <div className="bieumau-page__item">
                                        <h3>{otherCategory.name}</h3>
                                        <div className="content-wrapper">
                                            {otherCategory.children !== null && otherCategory.children !== undefined && otherCategory.children.length > 0 &&
                                                otherCategory.children.map((otherSubCategory, index) => (
                                                    <div
                                                        className={(currentTab === TABS.OTHER && currentSubCategory === otherSubCategory.id) ?
                                                            "content-item dropdown show" : "content-item dropdown"}>
                                                        {/* Sub-categories title*/}
                                                        <div className="dropdown__content"
                                                             onClick={() => expandSubCategory(TABS.OTHER, otherSubCategory.id)}>
                                                            <h4 style={{'width': '90%'}}>{otherSubCategory.name}</h4>
                                                            <div className="arrow">
                                                                <img src="/img/icon/dropdown-arrow.svg" alt=""/>
                                                            </div>
                                                        </div>
                                                        {/* Documents */}
                                                        {otherSubCategory.children !== null && otherSubCategory.children !== undefined && otherSubCategory.children.length > 0 &&
                                                            otherSubCategory.children.map((document, index) => (
                                                                <div className="dropdown__items">
                                                                    <div className="item">
                                                                        <div className="content">
                                                                            <div className="icon-wrapper">
                                                                                <img
                                                                                    src="/img/icon/10.1/10.1-icon-bieumau-dropdown.svg"
                                                                                    alt=""
                                                                                />
                                                                            </div>
                                                                            <p>{document.title}</p>
                                                                        </div>
                                                                        <div className="button-group">
                                                                            <button
                                                                                className="bieumau-button read-button"
                                                                                onClick={(event) => openFile(event, document.fileUuid)}>
                                                                                Xem
                                                                            </button>
                                                                            <button
                                                                                className="bieumau-button download-button"
                                                                                onClick={(event) => saveFile(event, document.title, document.fileUuid)}>
                                                                                {isDownloading ? "Đang tải..." : "Tải xuống"}
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            ))}
                                                    </div>
                                                ))}
                                        </div>
                                    </div>
                                ))}
                        </div>
                    </section>
                </div>
            </div>
        </main>
    );
};

export default InsParticipation;
